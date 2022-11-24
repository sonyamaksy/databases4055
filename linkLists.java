/**
 * Class that initializes the ArrayList and does SQL queries, for now you can only search by city
 */
package application;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class linkLists {

	private ObservableList<Visitor> visitors;
    private static final String INSERT_QUERY = "INSERT INTO visitor_info (name, email, city, state, zipcode,"
    											+ " date_of_visit, group_amount, reason)"
    									        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	public linkLists() {
		visitors = FXCollections.observableArrayList();
	}

	//method to insert values into database
	public void insertRecord(String name, String email, String city, String state, int zip,
							 String date, int people, String reason)
	{
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/guestbook", "root", "");
            PreparedStatement preparedStatement = con.prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, city);
            preparedStatement.setString(4, state);
            preparedStatement.setInt(5, zip);
            preparedStatement.setString(6, date);
            preparedStatement.setInt(7, people);
            preparedStatement.setString(8, reason);
            
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
		}
		
		catch (SQLException e) {
            // print SQL exception information
			printSQLException(e);
        }	
	}
	
	//throwing an error if something in sql went wrong
	 public static void printSQLException(SQLException ex) 
	 {
		 for (Throwable e: ex) 
		 {
	            if (e instanceof SQLException) 
	            {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
		 }
	}   
	
	//method for getting the values from database
	public void updateVisitorsList(String city)
	{
		ObservableList<Visitor> newList = FXCollections.observableArrayList();
		
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/guestbook", "root", "");
			
			String updateString = "SELECT name, email, city, state, zipcode, date_of_visit, reason FROM "+
								"visitor_info WHERE city LIKE ?";
			
			PreparedStatement ps = con.prepareStatement(updateString);
			ps.setString(1, "%" + city + "%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				newList.add(new Visitor(rs.getString("name"), rs.getString("email"),
										rs.getString("city"), rs.getString("state"),
										rs.getInt("zipcode"), rs.getString("date_of_visit"),
										rs.getString("reason")));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		visitors = newList;
		
	}
	public ObservableList<Visitor> getVisitors() {
		return visitors;
	}

	public void setVisitors(ObservableList<Visitor> visitors) {
		this.visitors = visitors;
	}
	
}
