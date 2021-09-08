package com.taahyt.pubsubbubhub.events.impl;

public class SubscriptionEvent {
    private final int statusCode;
    private final String statusReason;
    private final boolean successful;
    private final boolean unsubscribed;

    /**
     * This event is called when a subscription is made
     * @param statusCode - Returns the status after subscribing
     * @param statusReason - Returns the reason for the status
     * @param successful - Status code = 202 if successful
     */
    public SubscriptionEvent(int statusCode, String statusReason, boolean successful, boolean unsubscribed) {
        this.statusCode = statusCode;
        this.statusReason = statusReason;
        this.successful = successful;
        this.unsubscribed = unsubscribed;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public boolean isUnsubscribed() {
        return unsubscribed;
    }
}
