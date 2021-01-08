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
import java.util.Vector;
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

    public void updateFriendRequestStatus(int userId, int friendId, int status) {
        PreparedStatement pst = null;
        try {
            if (status == 0) { //when friend accept this user
                pst = con.prepareStatement("Update FriendList Set REQUEST_STATUS =1  "
                        + "where USER_ID = ? AND  FRIEND_ID = ? ");
            } else {
                if (status == 1) { //when friend decline this user
                    pst = con.prepareStatement("Delete From  FriendList  where USER_ID = ? AND  FRIEND_ID = ?");
                }
            }
            pst.setInt(1, userId);
            pst.setInt(2, friendId);
            
            ResultSet rs = pst.executeQuery();
            
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

    public Vector<UserDTO> getFriendRequestsById(int userId) {
        Vector<UserDTO> users = new Vector<>();
        try {
            PreparedStatement pst = con.prepareStatement("select USERS.ID ,USERNAME ,PASSWORD ,FIRST_NAME,LAST_NAME,EMAIL,CITY ,BIRTH_DATE,WALLET\n"
                    + "From Users , FriendList\n"
                    + "where USERS.ID = FRIENDLIST.USER_ID \n"
                    + "AND FRIENDLIST.FRIEND_ID = ? AND REQUEST_STATUS=0 ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setInt(1, userId);

            ResultSet usersList = pst.executeQuery();

            while (usersList.next()) {
                UserDTO userDTO = new UserDTO();

                userDTO.id = usersList.getInt("ID");
                userDTO.userName = usersList.getString("USERNAME");
                userDTO.password = usersList.getString("PASSWORD");
                userDTO.firstName = usersList.getString("FIRST_NAME");
                userDTO.lastName = usersList.getString("LAST_NAME");
                userDTO.email = usersList.getString("EMAIL");
                userDTO.city = usersList.getString("CITY");
                userDTO.birthDate = new SimpleDateFormat("dd/MM/yyyy").format(usersList.getDate("BIRTH_DATE"));
                userDTO.wallet = usersList.getInt("WALLET");

                users.add(userDTO);
            }
            pst.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }
}
