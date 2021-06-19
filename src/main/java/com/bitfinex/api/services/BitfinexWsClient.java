package com.bitfinex.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import java.net.URI;
import java.time.ZonedDateTime;

public class BitfinexWsClient
{
    private static final Logger wsExchangeLogger = LoggerFactory.getLogger(BitfinexWsClient.class);

    public BitfinexWsClient()
    {
        try {
            // open websocket
            final WSClient clientEndPoint = new WSClient(new URI("wss://api-pub.bitfinex.com/ws/2"));
            clientEndPoint.addMessageHandler(message -> System.out.println("["+ZonedDateTime.now() +"] here is the message: "+message));
            clientEndPoint.sendMessage(getCandleCommand());
            Thread.sleep(1500000);
        }
    catch (Exception ex)
    {
        System.err.println("InterruptedException exception: " + ex.getMessage());
    }

    }

    private String getCandleCommand()
    {
        final JSONObject subscribeJson = new JSONObject();
        subscribeJson.put("event", "subscribe");
        subscribeJson.put("channel", "candles");
        subscribeJson.put("key","trade:1m:tETHEUR");
        return subscribeJson.toString();

    }
}
