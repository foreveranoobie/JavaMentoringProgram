package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.Event;

public interface EventService {

    Event getEventById(Long id);

    Event createEvent(Event event);
}
