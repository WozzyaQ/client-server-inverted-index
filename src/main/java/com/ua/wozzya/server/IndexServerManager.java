package com.ua.wozzya.server;

import com.ua.wozzya.index.Index;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class IndexServerManager {
    //TODO consider reading from properties
    private final int port;
    private final int MAX_CLIENTS = 10;

    private ServerSocket serverSocket;
    private Index index;

    public IndexServerManager(int port, Index index) {
        if (!index.isReady()) {
            throw new IllegalStateException("index should be built before supplying");
        }
        this.index = index;
        this.port = port;
    }

    public void start() {
        init();
        accept();
        close();
    }

    private void accept() {
        Socket clientSocket = null;
        List<Thread> threadList = new ArrayList<>();
        while (true) {
            try {
                System.out.println("waiting on client");
                clientSocket = serverSocket.accept();
                IndexClientResponser responser = IndexClientResponser.create(index, clientSocket);
                responser.start();
                threadList.add(responser);
                System.out.println("clinet accepted");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void init() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
