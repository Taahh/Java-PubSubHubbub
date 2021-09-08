package com.taahyt.pubsubbubhub.events.impl;

public class ValidationRequestEvent {
    private final String challenge;

    /**
     * This event is called when a (un)subscription request is created
     * @param challenge - Returns the hub.challenge provided by the request
     */
    public ValidationRequestEvent(String challenge)
    {
        this.challenge = challenge;
    }


    public String getChallenge() {
        return challenge;
    }
}
