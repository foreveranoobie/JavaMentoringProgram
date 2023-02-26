package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.Ticket;
import java.util.List;

public interface TicketService {

    Ticket bookTicket(Ticket ticket);

    List<Ticket> getTicketsForUser(Long userId);

    List<Ticket> getTicketsForEvent(Long eventId);
}
