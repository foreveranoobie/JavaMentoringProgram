package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.Ticket;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO-layer to work with {@link Ticket}-related data
 */
@Repository
public interface TicketDao extends CrudRepository<Ticket, Long> {

    List<Ticket> findByUserId(Long userId);

    List<Ticket> findByEventId(Long eventId);
}
