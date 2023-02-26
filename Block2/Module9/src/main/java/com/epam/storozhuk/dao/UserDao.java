package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO-layer to work with {@link User}-related data
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {

}
