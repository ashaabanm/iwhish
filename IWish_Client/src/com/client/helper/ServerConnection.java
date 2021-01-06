package com.client.helper;

import com.ui.client.ClientForm;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import javax.swing.JOptionPane;

public class ServerConnection {

    Socket server;
    public DataInputStream dis;
    public PrintStream pos;
    JOptionPane jop;
    Thread thread;
    public ServerConnection() {
        try {
            server = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(server.getInputStream());
            pos = new PrintStream(server.getOutputStream());
            
        } catch (ConnectException ex) {
            ClientForm from = new ClientForm();
            jop.showMessageDialog(from, "server is slept");
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
