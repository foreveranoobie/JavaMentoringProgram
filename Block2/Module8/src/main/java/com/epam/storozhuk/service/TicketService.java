package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.Ticket;
import java.util.List;

/**
 * Works with {@link Ticket}
 */
public interface TicketService {

    /**
     * Books ticket
     *
     * @param ticket to book
     * @return booked {@link Ticket}
     */
    Ticket bookTicket(Ticket ticket);

    /**
     * Method to get tickets for a certain user
     *
     * @param userId id of {@link com.epam.storozhuk.entity.User}
     * @return List of {@link Ticket}
     */
    List<Ticket> getTicketsForUser(Long userId);

    /**
     * Method to get tickets for a certain event
     *
     * @param eventId id of {@link com.epam.storozhuk.entity.Event}
     * @return List of {@link Ticket}
     */
    List<Ticket> getTicketsForEvent(Long eventId);

    /**
     * @param ticketId id of ticket to get
     * @return found {@link Ticket}
     */
    Ticket getTicketById(Long ticketId);
}
