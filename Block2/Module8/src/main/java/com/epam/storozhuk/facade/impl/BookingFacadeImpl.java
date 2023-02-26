package com.epam.storozhuk.facade.impl;

import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.facade.BookingFacade;
import com.epam.storozhuk.pdf.PdfGenerator;
import com.epam.storozhuk.service.EventService;
import com.epam.storozhuk.service.TicketService;
import com.epam.storozhuk.service.UserService;
import com.itextpdf.text.DocumentException;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@inheritDoc}
 */
public class BookingFacadeImpl implements BookingFacade {

    private final Logger logger = LoggerFactory.getLogger(BookingFacadeImpl.class);

    private UserService userService;
    private EventService eventService;
    private TicketService ticketService;
    private PdfGenerator pdfGenerator;

    public BookingFacadeImpl(UserService userService, EventService eventService, TicketService ticketService,
            PdfGenerator pdfGenerator) {
        this.userService = userService;
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.pdfGenerator = pdfGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket bookTicket(TicketDto ticketDto) {
        Event event = eventService.getEventById(ticketDto.getEventId());
        LocalDateTime currentTime = LocalDateTime.now();
        Ticket createdTicket = null;
        if (!currentTime.isAfter(event.getDate())) {
            Ticket ticket = Ticket.builder()
                    .userId(ticketDto.getUserId())
                    .eventId(ticketDto.getEventId())
                    .category(ticketDto.getCategory())
                    .place(ticketDto.getPlace())
                    .cinemaName(ticketDto.getCinemaName())
                    .cinemaAddress(ticketDto.getCinemaAddress())
                    .cinemaFacilities(ticketDto.getCinemaFacilities())
                    .cinemaPhones(ticketDto.getCinemaPhones())
                    .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                    .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                    .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                    .build();
            createdTicket = ticketService.bookTicket(ticket);
            userService.updateDate(createdTicket.getUserId(), currentTime);
        }
        return createdTicket;
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
