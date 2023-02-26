package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.Ticket;
import java.util.List;
import java.util.stream.Collectors;

public class TicketDao extends AbstractDao<Ticket> {

    public TicketDao() {
        super("ticket");
    }

    @Override
    public Ticket get(Long id) {
        return (Ticket) storage.get(key.concat(":" + id));
    }

    @Override
    public Ticket save(Ticket entity) {
        return (Ticket) storage.save(key, entity);
    }

    @Override
    public Ticket delete(Long id) {
        return (Ticket) storage.delete(key, id);
    }

    public List<Ticket> getTicketsForUser(Long userId) {
        return storage.getAllValues().stream()
                .filter(ticket -> ((Ticket) ticket).getUserId().equals(userId))
                .map(Ticket.class::cast)
                .collect(Collectors.toList());
    }

    public List<Ticket> getTicketsForEvent(Long eventId) {
        return storage.getAllValues().stream()
                .filter(ticket -> ((Ticket) ticket).getEventId().equals(eventId))
                .map(Ticket.class::cast)
                .collect(Collectors.toList());
    }
}
