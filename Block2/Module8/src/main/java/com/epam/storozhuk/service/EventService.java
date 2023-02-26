package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.Event;

/**
 * Works with {@link Event}
 */
public interface EventService {

    /**
     * @param id of event
     * @return {@link Event} instance
     */
    Event getEventById(Long id);

    /**
     * @param event to save
     * @return saved {@link Event}
     */
    Event createEvent(Event event);
}
