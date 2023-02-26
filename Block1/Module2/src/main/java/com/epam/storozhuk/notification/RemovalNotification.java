package com.epam.storozhuk.notification;

/**
 * The notification to be sent to {@link com.epam.storozhuk.listener.RemovalListener}
 */
public interface RemovalNotification {

    String getKey();

    String getCause();

    void setKey(String key);

    void setCause(String cause);
}
