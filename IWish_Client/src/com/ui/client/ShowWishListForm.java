/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui.client;

import com.client.helper.Helper;
import com.client.helper.ServerConnection;
import com.dto.FriendListDTO;
import com.dto.HeaderDTO;
import com.dto.ItemDTO;
import com.dto.Show_friend_wish_listDTO;
import com.dto.TagType;
import com.dto.UserDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Reem
 */
public class ShowWishListForm extends javax.swing.JFrame {

    /**
     * Creates new form ShowWishListForm
     */
    DefaultTableModel tableModel;
    ServerConnection connection;
    String[] columnNames;

    public ShowWishListForm(int friendId) {
        initComponents();
        setLocationRelativeTo(null);
        connection = new ServerConnection();

        columnNames = new String[]{"Id", "Item Name", "Price", "Remaining Price"};
        tableModel = new DefaultTableModel(null, columnNames);

        getFriendWishList(friendId);
    }

    public void getFriendWishList(int friend_id) {

        HeaderDTO showHeader = new HeaderDTO();
        showHeader.tag = TagType.show_friend_wishList;
        showHeader.fromId = Helper.mainUserObj.id;

        Show_friend_wish_listDTO show_friendDTO = new Show_friend_wish_listDTO();
        show_friendDTO.header = showHeader;
        show_friendDTO.friend_list_id = friend_id;

        connection.pos.println(Helper.convertToJson(show_friendDTO));
        System.out.println("show btn");
        String[][] items = new String[][]{};

        SwingWorker sw = new SwingWorker() {
            @Override
            protected String doInBackground() {
                // define what thread will do here 
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response = connection.dis.readLine();//read from server
                            System.out.println(response);
                            // map header which is come from server

                            Show_friend_wish_listDTO showObj = new Gson().fromJson(response, Show_friend_wish_listDTO.class
                            );
                            if (showObj.header.toId == Helper.mainUserObj.id && showObj.header.tag == TagType.show_friend_wishList) {
                                int rows = showObj.listOfFriendWishes.size();
                                String[][] items = new String[rows][4];
                                for (int i = 0; i < rows; i++) {
                                    items[i][0] = String.valueOf(showObj.listOfFriendWishes.get(i).id);
                                    items[i][1] = String.valueOf(showObj.listOfFriendWishes.get(i).name);
                                    items[i][2] = String.valueOf(showObj.listOfFriendWishes.get(i).price);
                                    items[i][3] = String.valueOf(showObj.listOfFriendWishes.get(i).price);
                                }
                                tableModel = new DefaultTableModel(items, columnNames);
                                wishListTable.setModel(tableModel);
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
        wishListTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        wishListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        wishListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onItemRowCliecked(evt);
            }
        });
        jScrollPane1.setViewportView(wishListTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 181, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onItemRowCliecked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onItemRowCliecked
        int rowIndex = wishListTable.getSelectedRow();
        System.out.println( wishListTable.getValueAt(rowIndex, 0));
       
    }//GEN-LAST:event_onItemRowCliecked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ShowWishListForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowWishListForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowWishListForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowWishListForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShowWishListForm(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable wishListTable;
    // End of variables declaration//GEN-END:variables
}
