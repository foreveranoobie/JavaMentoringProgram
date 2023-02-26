package com.epam.storozhuk.facade;

import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Ticket;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Main facade responsible mostly for ticket-related operations
 */
public interface BookingFacade {

    /**
     * Books ticket for the user for the event
     *
     * @param ticketDto to book
     * @return booked ticket
     */
    Ticket bookTicket(TicketDto ticketDto);

    /**
     * Returns tickets for certain user, paginated
     *
     * @param userId users to get tickets for
     * @param pageNum page number
     * @param count tickets per page
     * @return retrieved list
     */
    List<Ticket> getBookedTicketsForUser(long userId, int pageNum, int count);

    /**
     * Returns tickets for certain event, paginated
     *
     * @param eventId events to get tickets for
     * @param pageNum page number
     * @param count tickets per page
     * @return retrieved list
     */
    List<Ticket> getBookedTicketsForEvent(long eventId, int pageNum, int count);

    /**
     * Converts tickets' list to PDF format
     *
     * @param tickets initial list
     * @return converted to PDF
     * @throws DocumentException in case of problems during PDF conversion
     */
    ByteArrayInputStream getInputStreamOfBookedTickets(List<Ticket> tickets) throws DocumentException;
}