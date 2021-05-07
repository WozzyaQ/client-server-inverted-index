package com.ua.wozzya.server;

import com.ua.wozzya.index.Index;

import java.io.*;
import java.net.Socket;
import java.util.Set;

/**
 * Simple index client handler
 * that delegates searching client queries
 * to index
 */
public class IndexClientHandler extends Thread {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String TERMINATOR = "$";
    private static final String EOF = LINE_SEPARATOR + TERMINATOR;

    private final Socket clientSocket;
    private final Index index;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private int clientId;

    private IndexClientHandler(Index index, Socket clientSocket, int clientId) {
        this.index = index;
        this.clientSocket = clientSocket;
        this.clientId = clientId;
    }

    private void initIO() {
        try {
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IndexClientHandler create(Index index, Socket clientSocket, int id) {
        return new IndexClientHandler(index, clientSocket, id);
    }

    @Override
    public void run() {
        initIO();
        String msgFromClient = "";

        //on accept, send termination line, client is awaiting for it
        sendTerminationLine();


        while ((msgFromClient != null) && !msgFromClient.equalsIgnoreCase("/exit")) {
            try {
                msgFromClient = fromClient.readLine();
                if ((msgFromClient == null) || msgFromClient.equalsIgnoreCase("/exit")) {
                    toClient.println("[finished]" + EOF);

                } else {
                    var response = makeStringResponse(index.search(msgFromClient));
                    toClient.println(response + EOF);
                    toClient.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        close();
    }

    private void close() {
        System.out.println("[client #" + clientId + " disconnected]");
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendTerminationLine() {
        toClient.println(TERMINATOR);
        toClient.flush();
    }

    /**
     * Format response to client
     *
     * @param foundSet query result
     * @return formatted {@link String} representation of query
     */
    private String makeStringResponse(Set<String> foundSet) {
        if (foundSet.isEmpty()) {
            return "NOT FOUND";
        }

        var sb = new StringBuilder();
        foundSet.forEach(s -> sb.append(s).append(LINE_SEPARATOR));
        sb.delete(sb.lastIndexOf(LINE_SEPARATOR), sb.length());
        return sb.toString();
    }
}

