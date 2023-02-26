package com.epam.storozhuk.facade;

import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Ticket;

public interface BookingFacade {

    Ticket bookTicket(Long userId, Long eventId, TicketDto ticketDto);
}
