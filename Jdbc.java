package application;

import java.sql.*;

public class Jdbc 
{
   public void upload()
   {
	  Controller values1 = new Controller();
	  
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
         
         conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/guestbook", "root", "");
         System.out.println("Connection is created successfully:");
         
         stmt = (Statement) conn.createStatement();
         String values = values1.getValues();
         System.out.println(values);
         
         String query = "INSERT INTO visitor_info\n" + values;      
         stmt.executeUpdate(query);

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