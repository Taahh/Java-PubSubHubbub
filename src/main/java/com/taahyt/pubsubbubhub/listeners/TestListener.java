package com.taahyt.pubsubbubhub.listeners;

import com.taahyt.pubsubbubhub.events.WebEvent;
import com.taahyt.pubsubbubhub.events.impl.FeedEvent;
import com.taahyt.pubsubbubhub.events.impl.SubscriptionEvent;

public class TestListener {

    @WebEvent
    public void onSubscription(SubscriptionEvent event)
    {
        System.out.println(event.getStatusCode());
    }

    @WebEvent
    public void onFeed(FeedEvent event)
    {
        System.out.println(event.getFeed());
    }

}
