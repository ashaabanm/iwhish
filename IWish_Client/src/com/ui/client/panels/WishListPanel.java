package com.ui.client.panels;

import com.client.helper.Helper;
import com.client.helper.ServerConnection;
import com.dto.HeaderDTO;
import com.dto.HeaderMapDTO;
import com.dto.ItemDTO;
import com.dto.MyWishListDTO;
import com.dto.RemoveItemDTO;
import com.dto.ShowItemDTO;
import com.dto.TagType;
import com.google.gson.Gson;
import java.io.IOException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class WishListPanel extends javax.swing.JPanel {

    ServerConnection connection;
    DefaultListModel model;

    public WishListPanel(ServerConnection connection) {
        initComponents();
        this.connection = connection;
        model = new DefaultListModel();
        this.wishList.setModel(model);

    }

    public void myWishList() {

        HeaderDTO header = new HeaderDTO();
        header.tag = TagType.show_wish_list;
        header.fromId = Helper.mainUserObj.id;

        MyWishListDTO obj = new MyWishListDTO();
        obj.header = header;
        System.out.println(header.fromId);

        connection.pos.println(Helper.convertToJson(obj));

        SwingWorker sw = new SwingWorker() {
            @Override
            protected Object doInBackground() {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String response = connection.dis.readLine();//read from server
                            System.out.println(response);

                            // map header which is come from server
                            HeaderMapDTO headerMap = new Gson().fromJson(response, HeaderMapDTO.class);
                            if (headerMap.header.tag == TagType.show_wish_list) {
                                MyWishListDTO mapDTO = new Gson().fromJson(response, MyWishListDTO.class);

                                model = new DefaultListModel();
                                wishList.setModel(model);
                                for (ItemDTO item : mapDTO.listOfWishes) {
                                    model.addElement(item.name + "/" + "ID" + "/" + item.id + "/" + " : Remaining Price: " + item.price + "$");

                                }
                            }

                        } catch (IOException ex) {
                            System.out.println("here error2");
                        }
                    }
                });
                t.start();
                return null;
            }

        };
        sw.execute();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        wishList = new javax.swing.JList<>();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("My Wish List");

        wishList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wishListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(wishList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void wishListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wishListMouseClicked
        if (wishList.getSelectedValue() == null) {
            return;
        }
        int wish_id = Integer.parseInt(wishList.getSelectedValue().toString().split("/")[2]);
        int opt = JOptionPane.showConfirmDialog(this, "Do you want to delete this item?",
                "Delete Item", JOptionPane.OK_CANCEL_OPTION);
        HeaderDTO headerDTO = new HeaderDTO();
        headerDTO.tag = TagType.remove_from_wish_list;
        headerDTO.fromId = Helper.mainUserObj.id;
        RemoveItemDTO removeditem = new RemoveItemDTO();
        removeditem.header = headerDTO;
        removeditem.item_id = wish_id;
        if (opt == 0) {
            connection.pos.println(Helper.convertToJson(removeditem));
            System.out.println("OK");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String response = connection.dis.readLine();//read from server
                        System.out.println(response);
                    // map header which is come from server
                        //RemoveItemDTO wishitem = new Gson().fromJson(response, RemoveItemDTO.class);
                        HeaderMapDTO headerMap = new Gson().fromJson(response, HeaderMapDTO.class);
                        if (headerMap.header.toId == Helper.mainUserObj.id && headerMap.header.tag == TagType.remove_from_wish_list) {
                            System.out.println("OK2");
                            JOptionPane.showMessageDialog(null, "Removed successfully");
                            myWishList();
                        }
                    } catch (IOException ex) {
                        System.out.println("here error");
                    }
                }
            });
            t.start();
        }


    }//GEN-LAST:event_wishListMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> wishList;
    // End of variables declaration//GEN-END:variables
}
