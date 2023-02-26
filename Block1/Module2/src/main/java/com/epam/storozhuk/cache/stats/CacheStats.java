package com.epam.storozhuk.cache.stats;

/**
 * Interface that provides contract for using CacheStats
 */
public interface CacheStats {

    long getAveragePutTimeNanos();

    void setAveragePutTimeNanos(long averagePutTimeNanos);

    int getEvictionsCount();

    void incrementEvictionsCount();

}
