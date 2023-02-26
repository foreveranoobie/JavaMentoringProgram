package com.epam.storozhuk.listener.impl;

import com.epam.storozhuk.listener.RemovalListener;
import com.epam.storozhuk.notification.RemovalNotification;
import java.util.logging.Logger;

/**
 * Implementation of java-based cache's removal listener
 */
public class RemovalListenerImpl implements RemovalListener {

    private Logger logger;

    public RemovalListenerImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onRemoval(RemovalNotification notification) {
        logger.info(notification.getKey() + " was removed due to " + notification.getCause());
    }
}
