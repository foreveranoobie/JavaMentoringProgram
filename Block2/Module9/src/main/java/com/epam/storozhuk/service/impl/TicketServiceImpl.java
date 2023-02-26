package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.TicketDao;
import com.epam.storozhuk.entity.Event;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.TicketService;
import com.epam.storozhuk.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@inheritDoc}
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    private UserService userService;
    private TicketDao ticketDao;

    @Autowired
    public TicketServiceImpl(UserService userService, TicketDao ticketDao) {
        this.userService = userService;
        this.ticketDao = ticketDao;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Ticket bookTicket(Ticket ticket) {
        logger.info("Requested booking of the new ticket: {}", ticket);
        User user = ticket.getUser();
        Event event = ticket.getEvent();
        if (user.getBalance() >= event.getTicketPrice()) {
            LocalDateTime currentTime = LocalDateTime.now();
            ticket.setCreateDate(currentTime);
            ticket.setUpdateDate(currentTime);
            Ticket returnTicket = ticketDao.save(ticket);
            logger.info("Booked new ticket: {}", returnTicket);
            user.setBalance(user.getBalance() - event.getTicketPrice());
            userService.createUser(user);
            return returnTicket;
        }
        throw new IllegalArgumentException(String.format("User (id = %s) is out of money", user.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getTicketsForUser(Long userId) {
        return ticketDao.findByUserId(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getTicketsForEvent(Long eventId) {
        return ticketDao.findByEventId(eventId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket getTicketById(Long ticketId) {
        return ticketDao.findById(ticketId).get();
    }
}
