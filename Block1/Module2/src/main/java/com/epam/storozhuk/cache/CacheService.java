package com.epam.storozhuk.cache;

import com.epam.storozhuk.data.CacheData;

/**
 * Base interface for integrated services
 */
public interface CacheService {

    void put(CacheData cacheData);

    CacheData get(String text) throws Exception;
}
