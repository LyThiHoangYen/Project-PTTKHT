package dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class UserDao {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    //get user table max row
    public int getMaxRow(){
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select max(sid) from staff");
            while(rs.next()){
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    //check email already exists
    public boolean isEmailExist(String email){
        try {
            ps = con.prepareStatement("select * from staff where semail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //check phone number already exists
    public boolean isPhoneExist(String phone){
        try {
            ps = con.prepareStatement("select * from staff where sphone = ?");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //insert data into table staff
    public void insert(int id, String username, String email, String pass, String phone,
            String address, String seq, String ans)
    {
        String sql = "insert into staff values(?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setString(7, seq);
            ps.setString(8, ans);
            
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "User added successfully");
            } else {
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //update user data
    public void update(int id, String username, String email, String pass, String phone,
            String address, String seq, String ans){
        String sql = "update staff set  sid = ?, sname = ?, semail = ?, spassword = ?, sphone = ?, saddress = ?, sques = ?, sans = ?";
        try {
            ps = con.prepareStatement (sql);
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setString(7, seq);
            ps.setString(8, ans);
            if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "User data successfully updated");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //delete user
    public void delete(int id){
        int x = JOptionPane.showConfirmDialog(null, "Are you sure to delete this account?","Delete account", JOptionPane.OK_CANCEL_OPTION,0);
        if(x == JOptionPane.OK_OPTION){
            try {
                ps = con.prepareStatement("delete from staff wwhere sid = ?");
                ps.setInt(1, id);
                if(ps.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Account deleted");
                }
            } catch (SQLException ex){
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE,null, ex);
            }
        }
    }
    
    //get user value
    public String[] getUserValue(int id){
        String[] value = new String[8];
        try {
            ps = con.prepareStatement("select * from staff where sid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
                value[5] = rs.getString(6);
                value[6] = rs.getString(7);
                value[7] = rs.getString(8);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }
    
    //get user id
    public int getUserId(String email){
        int id = 0;
        try {
            ps = con.prepareStatement("select sid from staff where semail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
}
