package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.EventDao;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@inheritDoc}
 */
@Service
public class EventServiceImpl implements EventService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private EventDao eventDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Event getEventById(Long id) {
        logger.info("Requesting event by id = {}", id);
        Event event = eventDao.findById(id).get();
        return event;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Event createEvent(Event event) {
        logger.info("Saving new event = {}", event);
        return eventDao.save(event);
    }
}
