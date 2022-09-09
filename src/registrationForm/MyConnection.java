package registrationForm;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class MyConnection {
   static final String DB_URL = "jdbc:mysql://localhost:3306/";
   static final String DB_DB = "USERS";
   static final String USER = "admin";
   static final String PASS = "Admin^^123";
   InfoDialog info;
   Connection con;
   public MyConnection(InfoDialog info){
       this.info=info;
   }

   public MyConnection(){
    this.info=null;
    }
   public void createConnection() {
      // Open a connection
      
      try{
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Connection con = DriverManager.getConnection(DB_URL + DB_DB, USER, PASS);
            Statement stmt = con.createStatement();		      

            String createTable = "CREATE TABLE REGISTEREDUSERS( first_name VARCHAR (255) NOT NULL, last_name VARCHAR (255) NOT NULL, email VARCHAR (255) NOT NULL PRIMARY KEY, gender VARCHAR (20) NOT NULL, dateofbirth VARCHAR (50) NOT NULL, address VARCHAR (255) NOT NULL, password VARCHAR (40) NOT NULL);";
         
            stmt.executeUpdate(createTable);
            System.out.println("Database created successfully...");  
           
            PreparedStatement ps = con.prepareCall("INSERT INTO REGISTEREDUSERS(first_name, last_name, email, gender, address, password) VALUES(?,?,?,?,?,?)");
            ps.setString(1, info.getFirstName());
            ps.setString(2, info.getLastName());
            ps.setString(3, info.getEmail());
            ps.setString(4, info.getGender());
            // ps.setDate(5, (Date) info.getDateOfBirth());
            ps.setString(5, info.getAddress());
            ps.setString(6, info.getPassword());
            ps.executeUpdate();
            System.out.println("Data inserted");
            con.close();
              
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception: "+ex.getMessage());
        }
   }

   public boolean readfromconnection(String emaill, String passwordd){
    ResultSet rs = null;
    String email = "";
    String password = "";
    try{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(DB_URL + DB_DB, USER, PASS);
        Statement stmt2 = conn.createStatement();	
        System.out.println("Connection established");  	  

      // our SQL SELECT query. 
      // if you only need a few columns, specify them by name instead of using "*"
      String query = "SELECT email,password FROM REGISTEREDUSERS WHERE email = '"+emaill+"' && password = '"+passwordd+"'";
      // execute the query, and get a java resultset
      rs = stmt2.executeQuery(query);
      
      if(rs == null){
        return false;
      }
      while (rs.next())
        {
            email = rs.getString("email");
            System.out.println("Email"+email);
            password = rs.getString("password");
            System.out.println("password"+password);
        
            // print the results
            System.out.format("%s, %s\n", email, password);
            if((email.equals(emaill)) && (password.equals(passwordd))){
                System.out.println("Found it in database. The user can login");
                stmt2.close();
                return true;
            }else{
                System.out.println("Did not find it in database. The user cannot login");
                stmt2.close();
                return false;
            }
        }
     
      // iterate through the java resultset
      return false;
    
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println("Exception: "+ex.getMessage());
        return false;
    }

   }
}    
