package com.ua.wozzya.server;

import com.ua.wozzya.index.Index;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class IndexClientResponser extends Thread {
    private final Socket clientSocket;
    private Index index;
    private BufferedReader fromClient;
    private PrintWriter toClient;

    private static final String LINESEP = System.lineSeparator();
    private static final String TERMINATOR = "$";
    private static final String EOF = LINESEP + TERMINATOR;


    private IndexClientResponser(Index index, Socket clientSocket) {
        this.index = index;
        this.clientSocket = clientSocket;

    }

    private void initIO() {
        try {
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IndexClientResponser create(Index index, Socket clientSocket) {

        IndexClientResponser responser = new IndexClientResponser(index, clientSocket);
        return responser;
    }

    @Override
    public void run() {
        initIO();
        String msgFromClient;
        sendTerminationLine();

        while (true) {
            try {
                System.out.println("reading from client");
                msgFromClient = fromClient.readLine();
                System.out.println("got msg from clinet-->" + msgFromClient);
                if ((msgFromClient == null) || msgFromClient.equalsIgnoreCase("QUIT")) {
                    clientSocket.close();
                    return;
                } else {
                    String responce = makeStringResponse(index.search(msgFromClient));
                    toClient.println(responce + EOF);
                    toClient.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void sendTerminationLine() {
        toClient.println(TERMINATOR);
        toClient.flush();
    }

    private String makeStringResponse(Set<String> foundSet) {
        System.out.println(foundSet);
        if(foundSet.isEmpty()) {
            return "NOT FOUND";
        }

        StringBuilder sb = new StringBuilder();
        foundSet.stream().forEach(s -> sb.append(s).append(LINESEP));
        sb.delete(sb.lastIndexOf(LINESEP), sb.length());
        return sb.toString();
    }
}

