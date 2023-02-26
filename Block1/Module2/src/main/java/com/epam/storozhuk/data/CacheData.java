package com.epam.storozhuk.data;

import java.util.Objects;

/**
 * Data to be stored in caches
 */
public class CacheData {

    private String text;

    private int referencesCount;

    private Long lastAccessTime;

    public CacheData() {
        referencesCount = 0;
        lastAccessTime = 0L;
        text = "";
    }

    public CacheData(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object compareTo) {
        if (this == compareTo) {
            return true;
        }
        if (compareTo == null || getClass() != compareTo.getClass()) {
            return false;
        }
        CacheData cacheData = (CacheData) compareTo;
        return referencesCount == cacheData.referencesCount && text.equals(cacheData.text) && lastAccessTime
                .equals(cacheData.lastAccessTime);
    }

    public String getText() {
        return text;
    }

    public void incrementReferencesCount() {
        referencesCount++;
    }

    public void setLastAccessTime(Long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public int getReferencesCount() {
        return referencesCount;
    }

    public Long getLastAccessTime() {
        return lastAccessTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return String.format("Data: {%s}", this.getText());
    }
}
