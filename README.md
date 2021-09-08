# Java-PubSubHubbub
This tool is an implementation of the PubSubHubbub protocol created by Google. Information can be found @ https://developers.google.com/youtube/v3/guides/push_notifications

## Basics
```java
PSHServer server = PSHServer.create(MainClass.class, "external / public ip here", port);
server.subscribe("topic / youtube url, can be found on the above link");
```

## Events
Current events are:
- [FeedEvent](https://github.com/Taahh/Java-PubSubHubbub/blob/master/src/main/java/com/taahyt/pubsubbubhub/events/impl/FeedEvent.java)
- [SubscriptionEvent](https://github.com/Taahh/Java-PubSubHubbub/blob/master/src/main/java/com/taahyt/pubsubbubhub/events/impl/SubscriptionEvent.java)
- [ValidationRequestEvent](https://github.com/Taahh/Java-PubSubHubbub/blob/master/src/main/java/com/taahyt/pubsubbubhub/events/impl/ValidationRequestEvent.java)
### How to use Events
In any class, you may add a method such as:
```java
@WebEvent
public void onFeedEvent(FeedEvent event)
{
  System.out.println(event.getFeed());
}

@WebEvent
public void onSubscriptionEvent(SubscriptionEvent event)
{
  System.out.println(event.getFeed());
}

@WebEvent
public void onFeedEvent(ValidationRequestEvent event)
{
  System.out.println(event.isSuccessful());
}
```
The method names don't matter.
