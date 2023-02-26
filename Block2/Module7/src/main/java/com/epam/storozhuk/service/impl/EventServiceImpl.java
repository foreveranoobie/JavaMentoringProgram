package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.EventDao;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;
    
    @Override
    public Event getEventById(Long id) {
        return eventDao.get(id);
    }

    @Override
    public Event createEvent(Event event) {
        return eventDao.save(event);
    }

    @Autowired
    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}
