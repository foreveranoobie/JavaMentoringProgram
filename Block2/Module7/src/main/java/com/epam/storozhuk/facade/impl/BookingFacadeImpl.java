package com.epam.storozhuk.facade.impl;

import com.epam.storozhuk.dto.TicketDto;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.facade.BookingFacade;
import com.epam.storozhuk.service.EventService;
import com.epam.storozhuk.service.TicketService;
import com.epam.storozhuk.service.UserService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BookingFacadeImpl implements BookingFacade {

    private UserService userService;
    private EventService eventService;
    private TicketService ticketService;

    public BookingFacadeImpl(UserService userService, EventService eventService, TicketService ticketService) {
        this.userService = userService;
        this.eventService = eventService;
        this.ticketService = ticketService;
    }


    @Override
    public Ticket bookTicket(Long userId, Long eventId, TicketDto ticketDto) {
        Event event = eventService.getEventById(eventId);
        LocalDateTime currentTime = LocalDateTime.now();
        Ticket createdTicket = null;
        if (!currentTime.isAfter(event.getDate())) {
            Ticket ticket = Ticket.builder()
                    .userId(userId)
                    .eventId(eventId)
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
            userService.updateDate(userId, currentTime);
        }
        return createdTicket;
    }
}
