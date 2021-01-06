package com.ui.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

    ServerSocket server;

    public Server() {
        try {
            server = new ServerSocket(5005);
            while (true) {
                Socket s = server.accept();
                new ClientHandler(s);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}

