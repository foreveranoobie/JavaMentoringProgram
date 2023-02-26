package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.TicketDao;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.service.TicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Override
    public Ticket bookTicket(Ticket ticket) {
        return ticketDao.save(ticket);
    }

    @Override
    public List<Ticket> getTicketsForUser(Long userId) {
        List<Ticket> tickets = ticketDao.getTicketsForUser(userId);
        return ticketDao.getTicketsForUser(userId);
    }

    @Override
    public List<Ticket> getTicketsForEvent(Long eventId) {
        return ticketDao.getTicketsForEvent(eventId);
    }

    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
}
