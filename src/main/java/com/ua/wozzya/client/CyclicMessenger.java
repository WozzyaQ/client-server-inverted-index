package com.ua.wozzya.client;

/**
 * Generic interface that represents cyclic messaging
 * client-to-server. That means, that class, that
 * implements this interface, MUST cycle the
 * messaging.
 * <p>
 * Example of such cycle:
 * get msg from client->pass it to server -> get response from server ->
 * -> write something to client -> get msg from client -> ...
 *
 * @param <A> type of input data from client
 * @param <B> type of data that will be prepared and send to server
 * @param <C> type of data received from server
 * @param <D> type of data returned to client
 */
public interface CyclicMessenger<A, B, C, D> {
    A readFromClient();

    void writeToServer(B payload);

    C receiveFromServer();

    void writeToClient(D payload);

    /**
     * "Chat's" mainloop
     */
    void startMessaging();
}
