package com.ui.server;

import com.DTOs.*;
import com.db.*;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

class ClientHandler extends Thread {

    DataInputStream dis;
    PrintStream pos;
    static Vector<ClientHandler> vector = new Vector<>();

    public ClientHandler(Socket sc) {

        try {
            dis = new DataInputStream(sc.getInputStream());
            pos = new PrintStream(sc.getOutputStream());
            ClientHandler.vector.add(this);
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        HeaderDTO header =new HeaderDTO();
        try {
            while (true) {
                String str = dis.readLine();
                User user = new User(DbHelper.getDbCon());;
                UserDTO userObj;
                System.out.println(str);

                HeaderMapDTO data = new Gson().fromJson(str, HeaderMapDTO.class);
                switch (data.header.tag) {
                    case signup:
                        userObj = new Gson().fromJson(str, UserDTO.class);
                        
                        userObj.id=user.insert(userObj);
                        header.toId =userObj.id;
                        header.tag =TagType.signup;
                        header.actionType =TagType.success;
                        userObj.header = header;
                        sendMessageToAll(Helper.convertToJson(userObj));
                        break;
                    case login:
                        userObj = new Gson().fromJson(str, UserDTO.class);
                        userObj =user.getByUserNamePass(userObj.userName,userObj.password);
                        if(userObj !=null && userObj.id>0){
                        header.toId =userObj.id;
                        header.tag =TagType.login;
                        header.actionType =TagType.success;
                        }
                        else{
                            header.actionType =TagType.fail;
                        }
                        userObj.header =header;

                        sendMessageToAll(Helper.convertToJson(userObj));
                        break;
                }
            }
        } catch (SocketException ex) {
            pos.close();
            System.out.println("error");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessageToAll(String msg) {
        for (ClientHandler ch : vector) {
            ch.pos.println(msg);
        }
    }

}
