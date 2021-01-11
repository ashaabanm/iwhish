/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui.client.panels;

import com.client.helper.Helper;
import com.client.helper.ServerConnection;
import com.dto.AddFriendDTO;
import com.dto.AddItemDTO;
import com.dto.FriendListDTO;
import com.dto.HeaderDTO;
import com.dto.HeaderMapDTO;
import com.dto.RemoveFriendDTO;
import com.dto.Show_friend_wish_listDTO;
import com.dto.Show_friend_wish_listDTO;
import com.dto.TagType;
import com.dto.UserDTO;
import com.google.gson.Gson;
import com.ui.client.ShowWishListForm;
import com.ui.client.WelcomeForm;
import java.awt.CardLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Ahmed
 */
public class FriendListPanel extends javax.swing.JPanel {

    ServerConnection connection;
    DefaultListModel model;
    CardLayout cardLayout;

    /**
     * Creates new form FriendListPanel
     */
    public FriendListPanel(ServerConnection connection) {
        initComponents();
        this.connection = connection;
        model = new DefaultListModel();
        this.friendlist.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btn_addFriend = new javax.swing.JButton();
        txt_addFriend = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        friendlist = new javax.swing.JList<>();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("My Friend List");

        btn_addFriend.setText("Add Friend");
        btn_addFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addFriendActionPerformed(evt);
            }
        });

        friendlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendlistMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(friendlist);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_addFriend, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btn_addFriend))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_addFriend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_addFriend))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_addFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addFriendActionPerformed

        HeaderDTO header = new HeaderDTO();
        header.tag = TagType.add_friend;
        header.fromId = Helper.mainUserObj.id;

        AddFriendDTO obj = new AddFriendDTO();
        obj.search_email = txt_addFriend.getText();
        obj.header = header;

        connection.pos.println(Helper.convertToJson(obj));

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String response = connection.dis.readLine();//read from server
                    System.out.println(response);
                    if (response.contains("header")) {
                        // map header which is come from server
                        HeaderDTO mapDTO = new Gson().fromJson(response, HeaderDTO.class);

                        if (mapDTO.tag == TagType.add_friend && mapDTO.fromId == Helper.mainUserObj.id) {

                            JOptionPane paneRequest = new JOptionPane(mapDTO.msg);

                        }
                    }

                } catch (IOException ex) {
                    System.out.println("here error2");
                }
            }
        });
        t.start();


    }//GEN-LAST:event_btn_addFriendActionPerformed

    private void friendlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendlistMouseClicked
        if (friendlist.getSelectedValue() == null) {
            return;
        }
        int friend_id = Integer.parseInt(friendlist.getSelectedValue().toString().split("@")[1]);
        Object[] options = {
            "Remove_friend", "Show wish list", "Cancel"
        };
        int opt = JOptionPane.showOptionDialog(this, "HELLO!", "option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

        HeaderDTO removeheaderDTO = new HeaderDTO();
        removeheaderDTO.tag = TagType.remove_friend;
        removeheaderDTO.fromId = Helper.mainUserObj.id;
        RemoveFriendDTO removeFriendDTO = new RemoveFriendDTO();
        removeFriendDTO.header = removeheaderDTO;
        removeFriendDTO.Remove_id = friend_id;

        if (opt == 0) {
            connection.pos.println(Helper.convertToJson(removeFriendDTO));
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String response = connection.dis.readLine();//read from server
                        System.out.println(response);
                        // map header which is come from server

                        HeaderDTO removeObj = new Gson().fromJson(response, HeaderDTO.class);
                        if (removeObj.toId == Helper.mainUserObj.id && removeObj.tag == TagType.remove_friend) {
                            JOptionPane.showMessageDialog(null, "Friend removed successfully");
                            getMyFriendList();
                        }

                    } catch (IOException ex) {
                        System.out.println("here error");
                    }
                }
            });
            t.start();
        } else if (opt == 1) {
            ShowWishListForm listForm = new ShowWishListForm(friend_id);
            listForm.setVisible(true);
        }


    }//GEN-LAST:event_friendlistMouseClicked

    public void getMyFriendList() {
        HeaderDTO header = new HeaderDTO();
        header.tag = TagType.show_friend_list;
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
                            System.out.println("show_friend_list: " + response);
                            if (response.contains("header")) {
                                HeaderMapDTO headerMap = new Gson().fromJson(response, HeaderMapDTO.class);

                                // check status and if there are user id or not 
                                if (headerMap.header.toId == Helper.mainUserObj.id && headerMap.header.tag == TagType.show_friend_list) {
                                    FriendListDTO friendListDTO = new Gson().fromJson(response, FriendListDTO.class);
                                    model = new DefaultListModel();
                                    friendlist.setModel(model);
                                    for (UserDTO user : friendListDTO.usersList) {
                                        model.addElement(user.userName + "@" + user.id);
                                    }
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addFriend;
    private javax.swing.JList<String> friendlist;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txt_addFriend;
    // End of variables declaration//GEN-END:variables
}
