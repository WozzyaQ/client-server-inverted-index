package com.ua.wozzya;


import com.ua.wozzya.client.SimpleQueryClient;

public class AppClient {
    public static void main(String[] args) {
        SimpleQueryClient client = new SimpleQueryClient("127.0.0.1", 2236);
        client.connect();
    }
}
