package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.EventDao;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@inheritDoc}
 */
public class EventServiceImpl implements EventService {

    private final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventDao eventDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Event getEventById(Long id) {
        logger.info("Requested Event having id {}", id);
        Event returnEvent = eventDao.get(id);
        logger.info("Returned following Event {}", returnEvent);
        return returnEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Event createEvent(Event event) {
        Event createdEvent = eventDao.save(event);
        logger.info("Created event {} ", createdEvent);
        return createdEvent;
    }

    @Autowired
    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}
