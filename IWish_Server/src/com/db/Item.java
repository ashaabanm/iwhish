package com.db;

import com.DTOs.ItemDTO;
import com.DTOs.UserDTO;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Item {

    Connection con;

    public Item(Connection con) {
        this.con = con;
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
}
