package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO-layer to work with {@link Event}-related data
 */
@Repository
public interface EventDao extends CrudRepository<Event, Long> {

}
