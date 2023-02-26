package com.epam.storozhuk.cache.impl;

import com.epam.storozhuk.cache.CacheService;
import com.epam.storozhuk.cache.stats.CacheStats;
import com.epam.storozhuk.cache.stats.impl.CacheStatsImpl;
import com.epam.storozhuk.data.CacheData;
import com.epam.storozhuk.listener.RemovalListener;
import com.epam.storozhuk.notification.impl.RemovalNotificationImpl;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Implementation of java-based cache
 */
public class JavaCacheService implements CacheService {

    private final String timeoutNotificationReason = "access timeout";

    private final String lfuNotificationReason = "LFU rule";

    private Map<Integer, CacheData> cacheDataMap;

    private int maxSize;

    private long timeout;

    private RemovalListener removalListener;

    private CacheStats cacheStats;

    private final Object lock;

    /**
     * Default constructor. Sets map of cached data with default timeout and max size
     */
    public JavaCacheService() {
        timeout = 5000;
        maxSize = 100_000;
        lock = new Object();
        cacheDataMap = new HashMap<>();
        cacheStats = new CacheStatsImpl();
    }

    @Override
    public void put(CacheData cacheData) {
        long startTime = System.nanoTime();
        synchronized (lock) {
            removeLFUData(cacheData);
            removeTimeOutData(cacheData);
            cacheData.setLastAccessTime(System.currentTimeMillis());
            cacheDataMap.put(cacheData.getText().hashCode(), cacheData);
            long averageTimeToPutData = cacheStats.getAveragePutTimeNanos();
            averageTimeToPutData += (System.nanoTime() - startTime);
            cacheStats.setAveragePutTimeNanos(averageTimeToPutData / 2);
        }
    }

    @Override
    public CacheData get(String text) throws NoSuchElementException {
        synchronized (lock) {
            int searchHashCode = text.hashCode();
            CacheData searchData = cacheDataMap.get(searchHashCode);
            if (searchData == null) {
                throw new NoSuchElementException(String.format("%s is not present in cache", text));
            }
            searchData.incrementReferencesCount();
            searchData.setLastAccessTime(System.currentTimeMillis());
            return searchData;
        }
    }

    private void removeLFUData(CacheData cacheData) {
        if (cacheDataMap.size() == maxSize && !cacheDataMap.containsKey(cacheData.getText().hashCode())) {
            int minReference = cacheDataMap.get(cacheDataMap.keySet().iterator().next()).getReferencesCount();
            CacheData lfuData = null;
            CacheData tempData;
            for (int hashedTextKey : cacheDataMap.keySet()) {
                tempData = cacheDataMap.get(hashedTextKey);
                if (tempData.getReferencesCount() < minReference) {
                    lfuData = tempData;
                    minReference = lfuData.getReferencesCount();
                }
            }
            if (lfuData != null) {
                cacheDataMap.remove(lfuData.getText().hashCode());
                notifyRemovalListener(lfuData.toString(), lfuNotificationReason);
                cacheStats.incrementEvictionsCount();
            }
        }
    }

    private void removeTimeOutData(CacheData notToRemoveData) {
        List<Integer> timeOutData = new LinkedList<>();
        cacheDataMap.forEach((text, data) -> {
            if (System.currentTimeMillis() - data.getLastAccessTime() >= timeout) {
                timeOutData.add(data.getText().hashCode());
            }
        });
        try {
            timeOutData.remove(notToRemoveData.getText().hashCode());
        } catch (IndexOutOfBoundsException ex) {
        }
        timeOutData.forEach(hashText -> {
            notifyRemovalListener(cacheDataMap.remove(hashText).toString(), timeoutNotificationReason);
            cacheStats.incrementEvictionsCount();
        });
    }

    private void notifyRemovalListener(String key, String cause) {
        if (removalListener != null) {
            removalListener.onRemoval(new RemovalNotificationImpl(key, cause));
        }
    }

    public CacheStats getCacheStats() {
        return cacheStats;
    }

    public int getSize() {
        return cacheDataMap.size();
    }

    public static Builder builder() {
        return new JavaCacheService().new Builder();
    }

    public class Builder {

        public Builder() {
        }

        public JavaCacheService build() {
            return JavaCacheService.this;
        }

        public Builder maxSize(int maxSize) {
            JavaCacheService.this.maxSize = maxSize;
            return this;
        }

        public Builder timeout(long timeout) {
            JavaCacheService.this.timeout = timeout;
            return this;
        }

        public Builder removalListener(RemovalListener removalListener) {
            JavaCacheService.this.removalListener = removalListener;
            return this;
        }
    }
}
