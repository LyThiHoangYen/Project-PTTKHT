
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class SupplierDao {
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    
    //get supplier table max row
    public int getMaxRow(){
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select max(rid) from supplier");
            while(rs.next()){
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }
    
    //check email already exists
    public boolean isEmailExist(String email){
        try {
            ps = con.prepareStatement("select * from supplier where remail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //check phone number already exists
    public boolean isPhoneExist(String phone){
        try {
            ps = con.prepareStatement("select * from supplier where rphone = ?");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    //check supplier username already exists
    public boolean isUsernameExist(String name){
        try {
            ps = con.prepareStatement("select * from supplier where rname = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //insert data into supplier table
    public void insert(int id, String username, String email, String pass, String phone,
            String address)
    {
        String sql = "insert into supplier values(?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.setString(5, phone);
            ps.setString(6, address);
            
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Supplier added successfully");
            } else {
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //update supplier data
    public void update(int id, String username, String email, String pass, String phone, String address){
        String sql = "update supplier set  rid = ?, rname = ?, remail = ?, rpassword = ?, rphone = ?, raddress = ?";
        try {
            ps = con.prepareStatement (sql);
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.setString(5, phone);
            ps.setString(6, address);
            if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Supplier data successfully updated");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //delete supplier
    public void delete(int id){
        int x = JOptionPane.showConfirmDialog(null, "Are you sure to delete this account?","Delete account", JOptionPane.OK_CANCEL_OPTION,0);
        if(x == JOptionPane.OK_OPTION){
            try {
                ps = con.prepareStatement("delete from supplier where rid = ?");
                ps.setInt(1, id);
                if(ps.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Account deleted");
                }
            } catch (SQLException ex){
                Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE,null, ex);
            }
        }
    }
    
    //get supplier data
    public void getSupplierValue(JTable table, String search){
        String sql = "select * from supplier where concat(rid, rname, remail) like ? order by rid desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+search +"%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next()){
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int countSuppliers() {
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total' from supplier");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    public String[] getSuppliers() {
        String[] suppliers = new String[countSuppliers()];
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from supplier");
            int i = 0;
            while (rs.next()) {
                suppliers[i] = rs.getString(2);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return suppliers;
    }
}
