package com.epam.storozhuk.cache.stats.impl;

import com.epam.storozhuk.cache.stats.CacheStats;

/**
 * Java-based cache's stats
 */
public class CacheStatsImpl implements CacheStats {

    private long averagePutTimeNanos;

    private int evictionsCount;

    public CacheStatsImpl() {
        averagePutTimeNanos = 0;
        evictionsCount = 0;
    }

    @Override
    public long getAveragePutTimeNanos() {
        return averagePutTimeNanos;
    }

    @Override
    public void setAveragePutTimeNanos(long averagePutTimeNanos) {
        this.averagePutTimeNanos = averagePutTimeNanos;
    }

    @Override
    public int getEvictionsCount() {
        return evictionsCount;
    }

    @Override
    public void incrementEvictionsCount() {
        evictionsCount++;
    }
}
