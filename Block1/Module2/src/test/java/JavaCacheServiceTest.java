import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static util.StringGenerator.generateRandomString;

import com.epam.storozhuk.cache.impl.JavaCacheService;
import com.epam.storozhuk.cache.stats.CacheStats;
import com.epam.storozhuk.data.CacheData;
import com.epam.storozhuk.listener.impl.RemovalListenerImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class JavaCacheServiceTest {

    private JavaCacheService sut;

    private Logger notificationLogger;

    @BeforeEach
    public void setUp() {
        notificationLogger = Logger.getLogger(RemovalListenerImpl.class.getName());
        notificationLogger.setLevel(Level.INFO);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        notificationLogger.addHandler(handler);
        notificationLogger = Mockito.spy(notificationLogger);
    }

    @Test
    public void shouldReturnCorrectCacheStats() {
        sut = JavaCacheService
                .builder()
                .maxSize(5)
                .removalListener(new RemovalListenerImpl(notificationLogger))
                .build();
        List<CacheData> initialCacheDataList = new ArrayList<>();
        CacheData toPut;
        for (int i = 0; i < 5; i++) {
            toPut = new CacheData(generateRandomString());
            initialCacheDataList.add(toPut);
            sut.put(toPut);
        }
        for (int i = 0; i < 4; i++) {
            sut.get(initialCacheDataList.get(1).getText());
        }
        for (int i = 0; i < 2; i++) {
            sut.get(initialCacheDataList.get(3).getText());
            sut.get(initialCacheDataList.get(0).getText());
        }
        sut.get(initialCacheDataList.get(4).getText());

        CacheData lastPutCacheData = new CacheData(generateRandomString());
        sut.put(lastPutCacheData);

        CacheStats cacheStats = sut.getCacheStats();
        Assertions.assertEquals(1, cacheStats.getEvictionsCount());
        Assertions.assertNotEquals(0, cacheStats.getAveragePutTimeNanos());
    }

    @Test
    public void shouldNotifyOnTimeoutDataRemove() throws InterruptedException {
        Random random = new Random();
        final int cacheSize = 10;
        sut = JavaCacheService
                .builder()
                .maxSize(cacheSize)
                .timeout(950)
                .removalListener(new RemovalListenerImpl(notificationLogger))
                .build();
        CacheData toPut;
        List<CacheData> initialCacheData = new ArrayList<>(cacheSize);
        while (sut.getSize() < cacheSize) {
            toPut = new CacheData(generateRandomString());
            sut.put(toPut);
            initialCacheData.add(toPut);
        }
        Thread.sleep(500);
        Set<CacheData> expectedUpdatedCacheData = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(cacheSize);
            sut.get(initialCacheData.get(index).getText());
            expectedUpdatedCacheData.add(initialCacheData.get(index));
        }
        Thread.sleep(500);
        sut.put(expectedUpdatedCacheData.iterator().next());
        initialCacheData.removeAll(expectedUpdatedCacheData);

        verify(notificationLogger, times(cacheSize - expectedUpdatedCacheData.size()))
                .info(Mockito.contains("was removed due to access timeout"));

        initialCacheData.forEach(
                data -> verify(notificationLogger).info(data.toString() + " was removed due to access timeout"));

        Assertions.assertEquals(expectedUpdatedCacheData.size(), sut.getSize());

        Assertions.assertAll("Objects are in cache", () -> {
            expectedUpdatedCacheData.forEach(data ->
                    Assertions.assertDoesNotThrow(() -> sut.get(data.getText())));
        });
    }

    @Test
    public void shouldNotifyOnLFUDataRemove() {
        final int cacheSize = 5;
        sut = JavaCacheService
                .builder()
                .maxSize(cacheSize)
                .removalListener(new RemovalListenerImpl(notificationLogger))
                .build();
        List<CacheData> initialCacheDataList = new ArrayList<>();
        CacheData toPut;
        for (int i = 0; i < 5; i++) {
            toPut = new CacheData(generateRandomString());
            initialCacheDataList.add(toPut);
            sut.put(toPut);
        }
        for (int i = 0; i < 4; i++) {
            sut.get(initialCacheDataList.get(1).getText());
        }
        for (int i = 0; i < 2; i++) {
            sut.get(initialCacheDataList.get(3).getText());
            sut.get(initialCacheDataList.get(0).getText());
        }
        sut.get(initialCacheDataList.get(4).getText());
        CacheData lastPutCacheData = new CacheData(generateRandomString());
        sut.put(lastPutCacheData);
        CacheData resultData = sut.get(lastPutCacheData.getText());

        verify(notificationLogger)
                .info(Mockito.eq(initialCacheDataList.get(2) + " was removed due to LFU rule"));

        Assertions.assertEquals(lastPutCacheData, resultData);
    }

    @Test
    public void shouldReturnAverageTimeToPutData() throws InterruptedException {
        final int threadsCount = 2;
        final int cacheSize = 100000;
        sut = JavaCacheService
                .builder()
                .maxSize(cacheSize)
                .build();
        List<Thread> threads = new ArrayList<>(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            Thread toAddThread = new Thread(() -> {
                CacheData toPut;
                for (int totalTimes = 0; totalTimes < cacheSize; totalTimes++) {
                    toPut = new CacheData(generateRandomString());
                    sut.put(toPut);
                }
                for (int totalTimes = 0; totalTimes < cacheSize; totalTimes++) {
                    try {
                        sut.get(generateRandomString());
                    } catch (NoSuchElementException ex) {
                    }
                }
            });
            threads.add(toAddThread);
            toAddThread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        long averagePutTime = sut.getCacheStats().getAveragePutTimeNanos();
        Assertions.assertNotEquals(0, averagePutTime);
    }

    @Test
    public void shouldRemoveDataOnTimeOut() throws InterruptedException {
        Random random = new Random();
        sut = JavaCacheService
                .builder()
                .maxSize(10)
                .timeout(950)
                .removalListener(new RemovalListenerImpl(notificationLogger))
                .build();
        CacheData toPut;
        List<CacheData> initialCacheData = new ArrayList<>(10);
        while (sut.getSize() < 10) {
            toPut = new CacheData(generateRandomString());
            sut.put(toPut);
            initialCacheData.add(toPut);
        }
        Thread.sleep(500);
        Set<CacheData> expectedUpdatedCacheData = new HashSet<>();
        for (int i = 0; i < 7; i++) {
            int index = random.nextInt(10);
            sut.get(initialCacheData.get(index).getText());
            expectedUpdatedCacheData.add(initialCacheData.get(index));
        }
        Thread.sleep(500);

        sut.put(expectedUpdatedCacheData.iterator().next());

        Assertions.assertEquals(expectedUpdatedCacheData.size(), sut.getSize());

        Assertions.assertAll("Objects are in cache", () -> {
            expectedUpdatedCacheData.forEach(data ->
                    Assertions.assertDoesNotThrow(() -> sut.get(data.getText())));
        });
    }

    @Test
    public void testOnMaxSizeLimitation() {
        sut = JavaCacheService
                .builder()
                .maxSize(5)
                .removalListener(new RemovalListenerImpl(notificationLogger))
                .build();
        List<CacheData> initialCacheDataList = new ArrayList<>();
        CacheData toPut;
        for (int i = 0; i < 5; i++) {
            toPut = new CacheData(generateRandomString());
            initialCacheDataList.add(toPut);
            sut.put(toPut);
        }
        for (int i = 0; i < 4; i++) {
            sut.get(initialCacheDataList.get(1).getText());
        }
        for (int i = 0; i < 2; i++) {
            sut.get(initialCacheDataList.get(3).getText());
            sut.get(initialCacheDataList.get(0).getText());
        }
        sut.get(initialCacheDataList.get(4).getText());

        CacheData lastPutCacheData = new CacheData(generateRandomString());
        sut.put(lastPutCacheData);

        Assertions.assertThrows(NoSuchElementException.class, () -> sut.get(initialCacheDataList.get(2).getText()));

        CacheData resultData = sut.get(lastPutCacheData.getText());
        Assertions.assertEquals(lastPutCacheData, resultData);
    }
}
