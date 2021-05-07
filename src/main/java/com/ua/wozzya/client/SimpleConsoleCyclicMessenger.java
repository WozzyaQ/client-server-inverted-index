package com.ua.wozzya.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Implementation of {@link CyclicMessenger}
 * Talking to a server via text data
 */
public final class SimpleConsoleCyclicMessenger
        extends AbstractConsoleClient
        implements CyclicMessenger<String, String, String, String> {

    // termination of server out
    private String terminator;
    private final PrintStream clientOut = System.out;

    public SimpleConsoleCyclicMessenger(String ip, int port) {
        super(ip, port);
    }

    @Override
    public void connect() {
        establishConnection();
        startMessaging();
    }

    private void establishConnection() {
        try {
            socket = new Socket(ip, port);
            // aquire in/out object to speak with server
            serverOut = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverIn = new PrintWriter(socket.getOutputStream());

            writeToClient(ActionConstants.CONNECTION_ACQUIRED);
        } catch (UnknownHostException e) {
            writeToClient(ActionConstants.UNKNOWN_HOST);
            System.exit(-2);
        } catch (IOException e) {
            writeToClient(ActionConstants.CANT_ACQUIRE_CONNECTION);
            System.exit(-1);
        }
    }

    @Override
    public void writeToClient(String msg) {
        clientOut.println(msg);
    }

    @Override
    public String readFromClient() {
        String payload = null;
        try {
            payload = clientIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payload;
    }

    /**
     * Reads until termination line occurs
     *
     * @return formatted string message from the server
     */
    @Override
    public String receiveFromServer() {
        var sb = new StringBuilder();
        String response;
        try {
            while (!(response = serverOut.readLine()).equals(terminator)) {
                sb.append(response).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    @Override
    public void writeToServer(String payload) {
        serverIn.println(payload);
        serverIn.flush();
    }

    //receive and memorize termination line from server
    //on start
    private void receiveTerminationLine() {
        try {
            terminator = serverOut.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startMessaging() {
        receiveTerminationLine();

        var clientMsg = "";
        do {
            clientMsg = readFromClient();
            writeToServer(clientMsg);
            writeToClient(receiveFromServer());

        } while (!clientMsg.equalsIgnoreCase("EXIT"));
    }
}
