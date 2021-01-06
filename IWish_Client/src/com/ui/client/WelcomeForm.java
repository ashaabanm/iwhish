package com.ui.client;

import com.ui.client.panels.FriendListPanel;
import com.ui.client.panels.ItemPanel;
import com.ui.client.panels.OthersPanel;
import com.ui.client.panels.WishListPanel;
import com.client.helper.Helper;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class WelcomeForm extends javax.swing.JFrame {

    CardLayout cardLayout;

    public WelcomeForm() {
        initComponents();
        setLocationRelativeTo(null);
        cardLayout = (CardLayout) Panel_container.getLayout();
        this.registerPanels();
        Label_title.setText("Welcome " + Helper.mainUserObj.firstName + " " + Helper.mainUserObj.lastName);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Label_title = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btn_wishList = new javax.swing.JButton();
        btn_showItems = new javax.swing.JButton();
        btn_showOthers = new javax.swing.JButton();
        btn_friendList = new javax.swing.JButton();
        Panel_container = new javax.swing.JPanel();
        DefaultPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Label_title.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Label_title.setText("Welcome");

        jPanel1.setLayout(new java.awt.BorderLayout());

        btn_wishList.setText("My Wish List");
        btn_wishList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_wishListActionPerformed(evt);
            }
        });

        btn_showItems.setText("Show Items");
        btn_showItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showItemsActionPerformed(evt);
            }
        });

        btn_showOthers.setText("Show Others");
        btn_showOthers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showOthersActionPerformed(evt);
            }
        });

        btn_friendList.setText("Friend List");
        btn_friendList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_friendListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_wishList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_friendList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_showOthers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_showItems, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btn_showItems)
                .addGap(18, 18, 18)
                .addComponent(btn_wishList)
                .addGap(18, 18, 18)
                .addComponent(btn_friendList)
                .addGap(18, 18, 18)
                .addComponent(btn_showOthers)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.LINE_START);

        Panel_container.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout DefaultPanelLayout = new javax.swing.GroupLayout(DefaultPanel);
        DefaultPanel.setLayout(DefaultPanelLayout);
        DefaultPanelLayout.setHorizontalGroup(
            DefaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
        DefaultPanelLayout.setVerticalGroup(
            DefaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 272, Short.MAX_VALUE)
        );

        Panel_container.add(DefaultPanel, "card2");

        jPanel1.add(Panel_container, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_showItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showItemsActionPerformed
        this.cardLayout.show(Panel_container, "itemPanel");
    }//GEN-LAST:event_btn_showItemsActionPerformed

    private void btn_wishListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_wishListActionPerformed
        this.cardLayout.show(Panel_container, "wishListPanel");
    }//GEN-LAST:event_btn_wishListActionPerformed

    private void btn_friendListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_friendListActionPerformed
        this.cardLayout.show(Panel_container, "friendListPanel");
    }//GEN-LAST:event_btn_friendListActionPerformed

    private void btn_showOthersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showOthersActionPerformed
        this.cardLayout.show(Panel_container, "othersPanel");
    }//GEN-LAST:event_btn_showOthersActionPerformed

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
            java.util.logging.Logger.getLogger(WelcomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WelcomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WelcomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WelcomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WelcomeForm().setVisible(true);
            }
        });
    }

    private void registerPanels() {
        JPanel itemPanel = new ItemPanel();
        JPanel wishListPanel = new WishListPanel();
        JPanel friendListPanel = new FriendListPanel();
        JPanel othersPanel = new OthersPanel();

        Panel_container.add(itemPanel, "itemPanel");
        Panel_container.add(wishListPanel, "wishListPanel");
        Panel_container.add(friendListPanel, "friendListPanel");
        Panel_container.add(othersPanel, "othersPanel");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DefaultPanel;
    private javax.swing.JLabel Label_title;
    private javax.swing.JPanel Panel_container;
    private javax.swing.JButton btn_friendList;
    private javax.swing.JButton btn_showItems;
    private javax.swing.JButton btn_showOthers;
    private javax.swing.JButton btn_wishList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
