package com.ua.wozzya.client;

public interface CyclicMessenger<A, B, C, D> {
    A readFromClient();

    void writeToServer(B payload);

    C receiveFromServer();

    void writeToClient(D payload);

    void startMessaging();
}
