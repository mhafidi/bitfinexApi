package com.bitfinex.api.services;


import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import javax.servlet.ServletContext;
import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class WSClient {

    Session userSession = null;
    private MessageHandler messageHandler;

    public WSClient(URI endpointURI) {
        try {
            //ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.setDefaultMaxTextMessageBufferSize(32768);//.setMaxTextMessageBufferSize(32768);
            container.setDefaultMaxBinaryMessageBufferSize(32768);
            //ServletContext servletContext = new S
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        System.out.println(reason);
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        System.out.println("sending message: "+message);
        this.userSession.getAsyncRemote().sendText(message);
    }


    public static interface MessageHandler {

        public void handleMessage(String message);
    }
}
