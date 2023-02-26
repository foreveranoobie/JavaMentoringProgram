package com.epam.storozhuk.facade.impl;

import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.facade.BookingFacade;
import com.epam.storozhuk.pdf.PdfGenerator;
import com.epam.storozhuk.service.EventService;
import com.epam.storozhuk.service.TicketService;
import com.epam.storozhuk.service.UserService;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@inheritDoc}
 */
@Component
public class BookingFacadeImpl implements BookingFacade {

    private TicketService ticketService;
    private EventService eventService;
    private UserService userService;
    private PdfGenerator pdfGenerator;

    @Autowired
    public BookingFacadeImpl(TicketService ticketService, EventService eventService, UserService userService,
            PdfGenerator pdfGenerator) {
        this.ticketService = ticketService;
        this.eventService = eventService;
        this.userService = userService;
        this.pdfGenerator = pdfGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket bookTicket(TicketDto ticketDto) {
        Event event = eventService.getEventById(ticketDto.getEventId());
        User user = userService.getUserById(ticketDto.getUserId());
        if (!event.getDate().isBefore(user.getCreateDate())) {
            Ticket toCreateTicket = Ticket.builder()
                    .user(user)
                    .event(event)
                    .category(ticketDto.getCategory())
                    .cinemaAddress(ticketDto.getCinemaAddress())
                    .cinemaName(ticketDto.getCinemaName())
                    .place(ticketDto.getPlace())
                    .cinemaFacilities(ticketDto.getCinemaFacilities())
                    .cinemaPhones(ticketDto.getCinemaPhones())
                    .build();
            return ticketService.bookTicket(toCreateTicket);
        }
        throw new IllegalArgumentException("User's creation date is after event's date");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getBookedTicketsForUser(long userId, int pageNum, int count) {
        return getPagedTickets(ticketService.getTicketsForUser(userId), pageNum, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getBookedTicketsForEvent(long eventId, int pageNum, int count) {
        return getPagedTickets(ticketService.getTicketsForEvent(eventId), pageNum, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ByteArrayInputStream getInputStreamOfBookedTickets(List<Ticket> tickets)
            throws DocumentException {
        ByteArrayInputStream bis = pdfGenerator.createTicketsReport(tickets);
        return bis;
    }

    private List<Ticket> getPagedTickets(List<Ticket> initialTickets, int pageNum, int count) {
        if (count == 0) {
            return initialTickets;
        } else {
            List<Ticket> toGenerateTickets = new ArrayList<>(count);
            for (int currentIndex = ((pageNum - 1) * count);
                    currentIndex < (pageNum * count) && currentIndex < initialTickets.size();
                    currentIndex++) {
                toGenerateTickets.add(initialTickets.get(currentIndex));
            }
            return toGenerateTickets;
        }
    }
}
