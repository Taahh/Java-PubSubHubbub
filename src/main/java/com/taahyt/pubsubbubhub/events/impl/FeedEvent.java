package com.taahyt.pubsubbubhub.events.impl;

import com.rometools.rome.feed.synd.SyndFeed;

public class FeedEvent {
    private final SyndFeed feed;

    /**
     * This event is called when a topic's feed is updated
     * @param feed - Returns the info of the feed
     */
    public FeedEvent(SyndFeed feed)
    {
        this.feed = feed;
    }

    public SyndFeed getFeed() {
        return feed;
    }
}
