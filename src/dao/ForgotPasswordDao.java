
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
import user.ForgotPassword;


public class ForgotPasswordDao {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    ResultSet rs; 
    
    //check email validation
    public boolean isEmailExist(String email){
        try {
            ps = con.prepareStatement("select * from staff where semail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if(rs.next()){
                ForgotPassword.jTextField5.setText(rs.getString(7));
                return true;
            } else{
                JOptionPane.showMessageDialog(null, "Email address doesn't exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean getAns(String email, String newAns){
        try {
            ps = con.prepareStatement("select * from staff where semail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if(rs.next()){
                String oldAns = rs.getString(8);
                System.out.print(rs.getString(8));
                if(newAns.equals(oldAns)){
                    return true;
                }else {
                    JOptionPane.showMessageDialog(null,"Security answer didn't match");
                    return false;
                }
                
            } 
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //set new password
    public void setPassword(String email, String pass){
        String sql = "update staff set spassword = ? where semail = ?";
        try {
            ps = con.prepareStatement (sql);
            ps.setString(1, pass);
            ps.setString(2, email);
            if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Password successfully updated");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
