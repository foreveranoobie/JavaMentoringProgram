package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.Event;

public class EventDao extends AbstractDao<Event> {

    public EventDao() {
        super("event");
    }

    @Override
    public Event get(Long id) {
        return (Event) storage.get(key.concat(":" + id));
    }

    @Override
    public Event save(Event entity) {
        return (Event) storage.save(key, entity);
    }

    @Override
    public Event delete(Long id) {
        return (Event) storage.delete(key, id);
    }
}
