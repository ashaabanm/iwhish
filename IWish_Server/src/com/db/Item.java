package com.db;

import com.DTOs.ItemDTO;
import com.DTOs.MyWishListDTO;
import com.DTOs.UserDTO;
import static com.db.DbHelper.con;
import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Item {

    Connection con;

    public Item() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "iwish", "iwish");

        } catch (SQLException ex) {
            System.out.println("item db sql exception");
        }
    }

    public int getNextId() {
        int id = 0;
        try {
            Statement stmt = con.createStatement();
            String queryString = new String("Select NVL(MAX(ID),0)+1 from ITEM");
            ResultSet rs = stmt.executeQuery(queryString);
            while (rs.next()) {
                id = rs.getInt(1);
            }
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public int insert(ItemDTO itemDTO) {
        int newId = this.getNextId();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO ITEM ( ID ,NAME ,PRICE ,DESCRIPTION ,QUANTITY)\n"
                    + "    VALUES (? , ? ,? , ? ,? )");

            pst.setInt(1, newId);
            pst.setString(2, itemDTO.name);
            pst.setInt(3, itemDTO.price);
            pst.setString(4, itemDTO.description);
            pst.setInt(5, itemDTO.quantity);

            ResultSet rs = pst.executeQuery();
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return newId;

    }

    public Vector<ItemDTO> getAllItems() {
        Vector<ItemDTO> items = new Vector<>();
        try {
            PreparedStatement pst = con.prepareStatement("Select * from Item order by id", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet itemsList = pst.executeQuery();

            while (itemsList.next()) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.id = itemsList.getInt("ID");
                itemDTO.name = itemsList.getString("NAME");
                itemDTO.price = itemsList.getInt("PRICE");
                itemDTO.description = itemsList.getString("DESCRIPTION");
                itemDTO.quantity = itemsList.getInt("QUANTITY");
                items.add(itemDTO);
            }
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return items;
    }

    public Vector<ItemDTO> myWishList(int from_id) {
        Vector<ItemDTO> wishItems = new Vector<>();

        try {
            PreparedStatement pst = con.prepareStatement("Select * from Item,wishlist where user_id = ? and item_id = id order by id ");
            pst.setInt(1, from_id);

            ResultSet wishList = pst.executeQuery();

            while (wishList.next()) {
                ItemDTO wishDTO = new ItemDTO();

                wishDTO.id = wishList.getInt("ID");
                wishDTO.name = wishList.getString("NAME");
                wishDTO.price = wishList.getInt("PRICE");
                wishDTO.description = wishList.getString("DESCRIPTION");
                wishDTO.quantity = 1;
                wishItems.add(wishDTO);
            }
            System.out.println(from_id);
            System.out.println(pst.executeQuery());
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }

        return wishItems;
    }

    public void insert_wish(int user_id, int item_id) {
        int price = 0;

        try {
            PreparedStatement pst1 = con.prepareStatement("SELECT PRICE FROM ITEM WHERE ID = ?");

            pst1.setInt(1, item_id);
            ResultSet rs1 = pst1.executeQuery();
            while (rs1.next()) {
                price = rs1.getInt(1);
            }
            pst1.close();

            PreparedStatement pst2 = con.prepareStatement("INSERT INTO WISHLIST VALUES(?,?,?)");
            pst2.setInt(1, user_id);
            pst2.setInt(2, item_id);
            pst2.setInt(3, price);
            pst2.executeQuery();
            pst2.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void removeItem(int user_id, int item_id) {

        try {
            PreparedStatement pst = con.prepareStatement("DELETE FROM WISHLIST where USER_ID = ? AND ITEM_ID = ?");

            pst.setInt(1, user_id);
            pst.setInt(2, item_id);

            pst.execute();

            System.out.println(pst.execute());

            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
