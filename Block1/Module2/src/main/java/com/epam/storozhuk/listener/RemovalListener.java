package com.epam.storozhuk.listener;

import com.epam.storozhuk.notification.RemovalNotification;

/**
 * Interface that provides contract for removal listeners
 */
public interface RemovalListener {

    void onRemoval(RemovalNotification notification);
}
