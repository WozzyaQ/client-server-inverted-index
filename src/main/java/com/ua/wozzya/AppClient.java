package com.ua.wozzya;


import com.ua.wozzya.client.SimpleConsoleCyclicMessenger;

public class AppClient {
    public static void main(String[] args) {
        SimpleConsoleCyclicMessenger client = new SimpleConsoleCyclicMessenger("127.0.0.1", 2236);
        client.connect();
    }
}
