package com.ua.wozzya;

import com.ua.wozzya.client.SimpleConsoleCyclicMessenger;

public class AppClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("error: requires 2 arguments");
            System.out.println("usage: ./runclient <ip> <port>");
            return;
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        SimpleConsoleCyclicMessenger client = new SimpleConsoleCyclicMessenger(ip, port);
        client.connect();
    }
}
