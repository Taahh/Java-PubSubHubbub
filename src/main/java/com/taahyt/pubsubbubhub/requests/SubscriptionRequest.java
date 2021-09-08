package com.taahyt.pubsubbubhub.requests;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.taahyt.pubsubbubhub.PubSubHubbub;
import com.taahyt.pubsubbubhub.events.impl.FeedEvent;
import com.taahyt.pubsubbubhub.util.DocumentConverter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionRequest
{
    /**
     * Calls the SubscriptionEvent
     * @param body - Body sometimes does not get posted, but if so, it'll contain the XML of the feed
     */
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_ATOM_XML_VALUE)
    public void handleSubscription(@RequestBody(required = false) String body)
    {
        if (body != null)
        {
            SyndFeedInput input = new SyndFeedInput();
            try {
                SyndFeed feed = input.build(DocumentConverter.createDocument(body));
                FeedEvent event = new FeedEvent(feed);
                PubSubHubbub.getServer().callEvent(event);
            } catch (FeedException e) {
                e.printStackTrace();
            }

        }
    }

}
