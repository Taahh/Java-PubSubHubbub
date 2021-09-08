package com.taahyt.pubsubbubhub.requests;

import com.taahyt.pubsubbubhub.PubSubHubbub;
import com.taahyt.pubsubbubhub.events.impl.ValidationRequestEvent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationRequest
{

    /**
     * Calls the ValidationRequestEvent
     * @param challenge - According to multiple documentations on Google's PubSubHubbub, when you subscribe or unsubscribe it will send a "challenge" to the callback url. The callback expects for you to send it back.
     * @return
     */
    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public String getValidationKey(@RequestParam(name = "hub.challenge") String challenge)
    {
        ValidationRequestEvent event = new ValidationRequestEvent(challenge);
        PubSubHubbub.getServer().callEvent(event);
        return challenge;
    }



}
