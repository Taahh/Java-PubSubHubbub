package com.taahyt.pubsubbubhub.server;

import com.taahyt.pubsubbubhub.events.WebEvent;
import com.taahyt.pubsubbubhub.events.impl.SubscriptionEvent;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PSHServer {

    private final String callbackHost;
    private final int callbackPort;

    private final String hubUrl;

    private final List<Object> events = new ArrayList<>();

    protected PSHServer(String callbackHost, int callbackPort) {
        this.callbackHost = callbackHost;
        this.callbackPort = callbackPort;
        this.hubUrl = "https://pubsubhubbub.appspot.com/";
    }

    protected PSHServer(String callbackHost, int callbackPort, String hubUrl) {
        this.callbackHost = callbackHost;
        this.callbackPort = callbackPort;
        this.hubUrl = hubUrl;
    }

    /**
     * @param mainClass    -> Class with the @SpringBootApplication annotation
     * @param callbackHost -> Your external / public IP address to receive feed notifications
     * @param callbackPort -> Port to run Spring Boot on ( must be portforwarded )
     *                     Creates the PubSubHubbub server instance for subscriptions
     */
    public static PSHServer create(Class<?> mainClass, String callbackHost, int callbackPort) {
        SpringApplication application = new SpringApplication(mainClass);
        application.setDefaultProperties(Collections.singletonMap("server.port", callbackPort));
        application.run();
        return new PSHServer(callbackHost, callbackPort);
    }

    /**
     * @param mainClass -> Class with the @SpringBootApplication annotation
     * @param callbackHost -> Your external / public IP address to receive feed notifications
     * @param callbackPort -> Port to run Spring Boot on ( must be portforwarded )
     * @param hubUrl -> By default, the port is https://pubsubhubbub.appspot.com, but you can make it more difficult if you want to.
     * Creates the PubSubHubbub server instance for subscriptions
     */
    public static PSHServer create(Class<?> mainClass, String callbackHost, int callbackPort, String hubUrl) {
        SpringApplication application = new SpringApplication(mainClass);
        application.setDefaultProperties(Collections.singletonMap("server.port", callbackPort));
        application.run();
        return new PSHServer(callbackHost, callbackPort, hubUrl);
    }

    /**
     * Sends a subscription request to the hub url to subscribe to a topic, which sends a request to the callback and back to the hub
     * @param topicUrl - Topic to subscribe to
     */
    public void subscribe(String topicUrl)
    {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(this.hubUrl);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("hub.mode", "subscribe"));
        params.add(new BasicNameValuePair("hub.topic", topicUrl));
        params.add(new BasicNameValuePair("hub.callback", "http://" + this.callbackHost + ":" + this.callbackPort));
        post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
        try {
            CloseableHttpResponse response = client.execute(post);
            SubscriptionEvent event = new SubscriptionEvent(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase(), response.getStatusLine().getStatusCode() == 202, false);
            callEvent(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a unsubscription request to the hub url to unsubscribe to a topic, which sends a request to the callback and back to the hub
     * @param topicUrl - Topic to unsubscribe from
     */
    public void unsubscribe(String topicUrl)
    {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(this.hubUrl);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("hub.mode", "unsubscribe"));
        params.add(new BasicNameValuePair("hub.topic", topicUrl));
        params.add(new BasicNameValuePair("hub.callback", "http://" + this.callbackHost + ":" + this.callbackPort));
        post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
        try {
            CloseableHttpResponse response = client.execute(post);
            SubscriptionEvent event = new SubscriptionEvent(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase(), response.getStatusLine().getStatusCode() == 202, true);
            callEvent(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls an event (methods with the @WebEvent annotation within registered classes)
     * @param event - Event to be called
     */
    public void callEvent(Object event) {
        for (Object e : events) {
            for (Method method : Arrays.stream(e.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(WebEvent.class)).collect(Collectors.toList())) {
                method.setAccessible(true);
                if (method.getParameterCount() == 1 && method.getParameterTypes()[0].getTypeName().equals(event.getClass().getTypeName())) {
                    try {
                        method.invoke(e, event);
                    } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Registers the event
     * @param events - Event(s) to be registered
     */
    public void registerEvent(Object... events) {
        this.events.addAll(Arrays.asList(events));
    }

    public String getCallbackHost() {
        return callbackHost;
    }

    public int getCallbackPort() {
        return callbackPort;
    }

    public String getHubUrl() {
        return hubUrl;
    }
}
