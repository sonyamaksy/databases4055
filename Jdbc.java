package mydb;

import java.sql.*;

public class Jdbc {
   public static void main(String[] args) {
      Connection conn = null;
      Statement stmt = null;
      try
      {
         try 
         {
            Class.forName("com.mysql.cj.jdbc.Driver");
         } 
         catch (Exception e) 
         {
            System.out.println(e);
         }
         
         conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/Guestbook", "root", "");
         System.out.println("Connection is created successfully:");
         
         stmt = (Statement) conn.createStatement();
         String query1;      
         stmt.executeUpdate(query1);

         System.out.println("Record is inserted in the table successfully");
      	} 
      catch (SQLException excep) 
      {
      	excep.printStackTrace();
      } 
      catch (Exception excep) 
      {
      	excep.printStackTrace();
      } 
      finally 
      {
         try 
         {
            if (stmt != null)
               conn.close();
         } 
         
         catch (SQLException se) {}
         
         try 
         {
            if (conn != null)
               conn.close();
         } 
         
         catch (SQLException se) 
         {
            se.printStackTrace();
         }  
      }
      
      System.out.println("Please check it in the MySQL Table");
   }
}