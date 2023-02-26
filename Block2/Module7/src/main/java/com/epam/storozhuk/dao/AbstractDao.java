package com.epam.storozhuk.dao;

import com.epam.storozhuk.memory.EntityStorage;

public abstract class AbstractDao<T> {

    protected String key;
    protected EntityStorage storage;

    public AbstractDao(String key) {
        this.key = key;
    }

    public abstract T get(Long id);

    public abstract T save(T entity);

    public abstract T delete(Long id);

    public void setStorage(EntityStorage storage){
        this.storage = storage;
    }
}
