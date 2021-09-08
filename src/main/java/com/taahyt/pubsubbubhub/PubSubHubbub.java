package com.taahyt.pubsubbubhub;

import com.taahyt.pubsubbubhub.server.PSHServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PubSubHubbub
{
    private static PSHServer server;

    public static void main(String[] args)
    {
        server = PSHServer.create(PubSubHubbub.class, "ip here", 8806);
        server.subscribe("topic url");
    }

    public static PSHServer getServer() {
        return server;
    }
}
