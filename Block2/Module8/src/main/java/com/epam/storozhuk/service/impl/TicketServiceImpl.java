package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.TicketDao;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.service.TicketService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@inheritDoc}
 */
public class TicketServiceImpl implements TicketService {

    private final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDao ticketDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket bookTicket(Ticket ticket) {
        Ticket bookedTicket = ticketDao.save(ticket);
        logger.info("Booking ticket:{}", bookedTicket);
        return bookedTicket;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getTicketsForUser(Long userId) {
        logger.info("Retrieving tickets for user id {}", userId);
        return ticketDao.getTicketsForUser(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getTicketsForEvent(Long eventId) {
        logger.info("Retrieving tickets for event id {}", eventId);
        return ticketDao.getTicketsForEvent(eventId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket getTicketById(Long ticketId) {
        logger.info("Retrieving ticket by id {}", ticketId);
        return ticketDao.get(ticketId);
    }

    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }
}
