package com.ui.client.panels;

import com.client.helper.Helper;
import com.client.helper.ServerConnection;
import com.dto.FriendListDTO;
import com.dto.FriendRquestDTO;
import com.dto.HeaderDTO;
import com.dto.HeaderMapDTO;
import com.dto.TagType;
import com.dto.UserDTO;
import com.google.gson.Gson;
import com.ui.client.WelcomeForm;
import java.awt.Button;
import java.awt.List;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.SwingWorker;

public class FriendRequestPanel extends javax.swing.JPanel {

    ServerConnection connection;
    DefaultListModel model;

    public FriendRequestPanel() {
        initComponents();
        connection = new ServerConnection();
        model = new DefaultListModel();
        this.requestList.setModel(model);
    }

    public void getFreindRequest() {
        HeaderDTO header = new HeaderDTO();
        header.tag = TagType.friend_requests;
        header.fromId = Helper.mainUserObj.id;

        connection.pos.println(Helper.convertToJson(header));

        // create thread to accept responses from server
        SwingWorker sw = new SwingWorker() {
            @Override
            protected String doInBackground() {
                // define what thread will do here 
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response = connection.dis.readLine();//read from server

                            // map header which is come from server
                            FriendListDTO friendListDTO = new Gson().fromJson(response, FriendListDTO.class);

                            // check status and if there are user id or not 
                            if (friendListDTO.header.toId == Helper.mainUserObj.id && friendListDTO.header.tag == TagType.friend_requests
                                    && friendListDTO.header.actionType == TagType.success) {
                                model = new DefaultListModel();
                                requestList.setModel(model);
                                for (UserDTO user : friendListDTO.usersList) {
                                    model.addElement(user.userName + "@" + user.id);
                                }
                            }

                        } catch (IOException ex) {
                            System.out.println("here error");
                        }
                    }
                });
                t.start();
                return null;
            }

            @Override
            protected void done() {
                //System.out.println("SwingWorker done function");
            }
        };
        sw.execute();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        requestList = new javax.swing.JList();

        requestList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        requestList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                requestListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(requestList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void requestListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_requestListMouseClicked
        int user_id = Integer.parseInt(requestList.getSelectedValue().toString().split("@")[1]);
        int opt = JOptionPane.showConfirmDialog(this, "Press Yes for Accept \nPress No for Decline",
                "Accept Reqest", JOptionPane.YES_NO_CANCEL_OPTION);
        HeaderDTO headerDTO = new HeaderDTO();
        headerDTO.tag = TagType.accept_friend;
        headerDTO.fromId = Helper.mainUserObj.id;
        FriendRquestDTO rquestDTO = new FriendRquestDTO();
        rquestDTO.header = headerDTO;
        rquestDTO.requestOwnerId = user_id;
        if (opt == 0) {
            rquestDTO.status = 0;
            connection.pos.println(Helper.convertToJson(rquestDTO));
        } else {
            if (opt == 1) {
                rquestDTO.status = 1;
                connection.pos.println(Helper.convertToJson(rquestDTO));
            }
        }
        
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String response = connection.dis.readLine();//read from server
                    System.out.println(response);
                    // map header which is come from server
                    HeaderDTO header = new Gson().fromJson(response, HeaderDTO.class);
                    if (header.toId == Helper.mainUserObj.id && header.tag == TagType.accept_friend) {
                        getFreindRequest();
                    }
                } catch (IOException ex) {
                    System.out.println("here error");
                }
            }
        });
        t.start();
    }//GEN-LAST:event_requestListMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList requestList;
    // End of variables declaration//GEN-END:variables
}
