package com.epam.storozhuk.notification.impl;

import com.epam.storozhuk.notification.RemovalNotification;

/**
 * Removal Notification for Java-based cache
 */
public class RemovalNotificationImpl implements RemovalNotification {

    private String key;

    private String cause;

    public RemovalNotificationImpl(String key, String cause) {
        this.key = key;
        this.cause = cause;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getCause() {
        return cause;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setCause(String cause) {
        this.cause = cause;
    }
}
