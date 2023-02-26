package com.epam.storozhuk.facade.it.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.storozhuk.dao.EventDao;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.service.EventService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/ApplicationContext.xml")
@ExtendWith(SpringExtension.class)
public class EventServiceIT {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventDao eventDao;

    @Test
    public void shouldCreateEvent() {
        Event event = Event.builder()
                .title("Film")
                .date(LocalDateTime.now().plusMonths(1L))
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Event createdEvent = eventService.createEvent(event);
        assertAll(() -> {
            assertEquals(event.getTitle(), createdEvent.getTitle());
            assertEquals(event.getDate(), createdEvent.getDate());
            assertEquals(event.getCreateDate(), createdEvent.getCreateDate());
            assertEquals(event.getUpdateDate(), createdEvent.getUpdateDate());
        });

        assertEquals(createdEvent, eventDao.get(createdEvent.getId()));
    }
}
