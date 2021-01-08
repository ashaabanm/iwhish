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
import java.util.logging.Level;
import java.util.logging.Logger;

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
        HeaderDTO header = new HeaderDTO();
        User userdb = new User(DbHelper.getDbCon()); // user db 

        try {
            while (true) {
                String str = dis.readLine();
                UserDTO userObj;
                HeaderMapDTO data = new Gson().fromJson(str, HeaderMapDTO.class);
                if (data.header == null) {
                    data.header = new Gson().fromJson(str, HeaderDTO.class);
                }

                switch (data.header.tag) {
                    case signup:
                        userObj = new Gson().fromJson(str, UserDTO.class);

                        userObj.id = userdb.insert(userObj);
                        header.toId = userObj.id;
                        header.tag = TagType.signup;
                        header.actionType = TagType.success;
                        userObj.header = header;
                        sendMessageToAll(Helper.convertToJson(userObj));
                        break;
                    case login:
                        userObj = new Gson().fromJson(str, UserDTO.class);
                        userObj = userdb.getByUserNamePass(userObj.userName, userObj.password);
                        if (userObj != null && userObj.id > 0) {
                            header.toId = userObj.id;
                            header.tag = TagType.login;
                            header.actionType = TagType.success;
                        } else {
                            header.actionType = TagType.fail;
                        }
                        userObj.header = header;

                        sendMessageToAll(Helper.convertToJson(userObj));
                        break;

                    case friend_requests:
                        FriendListDTO friendListDTO = new FriendListDTO();
                        HeaderDTO headerDTO = new Gson().fromJson(str, HeaderDTO.class);

                        User userDb = new User(DbHelper.getDbCon());

                        friendListDTO.header = new HeaderDTO();
                        friendListDTO.header.toId = headerDTO.fromId;
                        friendListDTO.header.tag = TagType.friend_requests;
                        friendListDTO.header.actionType = TagType.success;

                        friendListDTO.usersList = userDb.getFriendRequestsById(headerDTO.fromId);

                        sendMessageToAll(Helper.convertToJson(friendListDTO));
                        break;

                    case accept_friend:
                        FriendRquestDTO reqDTO = new Gson().fromJson(str, FriendRquestDTO.class);
                        System.out.println(str);
                        userdb.updateFriendRequestStatus(reqDTO.requestOwnerId, reqDTO.header.fromId, reqDTO.status);
                        
                        headerDTO = new HeaderDTO();
                        headerDTO.tag = TagType.accept_friend;
                        headerDTO.toId = reqDTO.header.fromId;
                        headerDTO.actionType = TagType.success;
                        
                        sendMessageToAll(Helper.convertToJson(headerDTO));
                        
                        break;
                }
            }
        } catch (SocketException ex) {
            pos.close();
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
