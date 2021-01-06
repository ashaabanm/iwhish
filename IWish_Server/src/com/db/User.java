package com.db;

import com.DTOs.UserDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {

    Connection con;

    public User(Connection con) {
        this.con = con;
    }

    public int getNextId() {
        int id = 0;
        try {
            Statement stmt = con.createStatement();
            String queryString = new String("Select NVL(MAX(ID),0)+1 from USERS");
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

    public int insert(UserDTO userDTO) {
        int newId = this.getNextId();
        try {
            PreparedStatement pst = con.prepareStatement("INSERT INTO USERS (ID, USERNAME, PASSWORD,  FIRST_NAME, LAST_NAME,  EMAIL, CITY,BIRTH_DATE, WALLET)\n"
                    + " VALUES (?,   ?,  ?,  ?, ?,  ?,  ?, TO_DATE(?, 'dd/mm/yyyy'), ?)");

            pst.setInt(1, newId);
            pst.setString(2, userDTO.userName);
            pst.setString(3, userDTO.password);
            pst.setString(4, userDTO.firstName);
            pst.setString(5, userDTO.lastName);
            pst.setString(6, userDTO.email);
            pst.setString(7, userDTO.city);
            pst.setString(8, userDTO.birthDate);
            pst.setInt(9, userDTO.wallet);

            ResultSet rs = pst.executeQuery();
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return newId;

    }

    public UserDTO getByUserNamePass(String userName, String password) {
        UserDTO userDTO = new UserDTO();
        try {
            PreparedStatement pst = con.prepareStatement("Select * from USERS where USERNAME = ? and PASSWORD=? ");
            pst.setString(1, userName);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                userDTO.id = rs.getInt("ID");
                userDTO.userName = rs.getString("username");
                userDTO.password = rs.getString("password");
                userDTO.firstName = rs.getString("first_Name");
                userDTO.lastName = rs.getString("last_Name");
                userDTO.email = rs.getString("email");
                userDTO.city = rs.getString("city");
                userDTO.birthDate = rs.getString("birth_date");
                userDTO.wallet = rs.getInt("wallet");
                break;
            }
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userDTO;
    }

}
