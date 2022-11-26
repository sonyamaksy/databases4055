/**
 * Class that initializes the ArrayList and does SQL queries, 
 * for now you can only search by city and zip code
 */
package application;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DtbsUtil {

	private ObservableList<Visitor> visitors;
    private static final String INSERT_QUERY = "INSERT INTO visitor_info (name, email, city, state, zipcode,"
    											+ " date_of_visit, group_amount, reason)"
    									        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	String city = "SELECT * FROM visitor_info WHERE city LIKE ?";
	String city_date = "SELECT * FROM visitor_info WHERE city LIKE ? AND date_of_visit = ?";
	String city_reason = "SELECT * FROM visitor_info WHERE city LIKE ? AND reason LIKE ?";
	String city_date_reason = "SELECT * FROM visitor_info WHERE city LIKE ? AND date_of_visit = ? AND reason LIKE ?";
	
	String zip = "SELECT * FROM visitor_info WHERE zipcode LIKE ?";
	String zip_date = "SELECT * FROM visitor_info WHERE zipcode LIKE ? AND date_of_visit = ?";
	String zip_reason = "SELECT * FROM visitor_info WHERE zipcode LIKE ? AND reason LIKE ?";
	String zip_date_reason = "SELECT * FROM visitor_info WHERE zipcode LIKE ? AND date_of_visit = ? AND reason LIKE ?";
	
	String city_zip = "SELECT * FROM visitor_info WHERE city LIKE ? AND zipcode LIKE ?";		
	String city_zip_date = "SELECT * FROM visitor_info WHERE city LIKE ? AND zipcode LIKE ? AND date_of_visit LIKE ?";
	String city_zip_reason = "SELECT * FROM visitor_info WHERE city LIKE ? AND zipcode LIKE ? AND reason LIKE ?";
	String city_zip_date_reason = "SELECT * FROM visitor_info WHERE city LIKE ? AND zipcode LIKE ? AND date_of_visit LIKE ? AND reason LIKE ?";
	
	public DtbsUtil() {
		visitors = FXCollections.observableArrayList();
	}

	//method to insert values into database
	public void insertRecord(String name, String email, String city, String state, int zip,
							 String date, int group_amount, String reason)
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
            preparedStatement.setInt(7, group_amount);
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
	
	//method for getting the values from database based on city
	public void displayByCity(String city, String date, String reason)
	{
		ObservableList<Visitor> newList = FXCollections.observableArrayList();		
		Connection con = null;
		ResultSet rs;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/guestbook", "root", "");
				
			if (!date.isEmpty() && !reason.isEmpty())
            {
    			PreparedStatement ps = con.prepareStatement(city_zip_date);
				ps.setString(1, "%" + city + "%");
				ps.setString(2, "%" + date + "%");
				ps.setString(3, "%" + reason +"%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
            else if (!date.isEmpty())
            {
    			PreparedStatement ps = con.prepareStatement(city_date);
            	ps.setString(1, "%" + city + "%");
				ps.setString(2, "%" + date + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
            else if (!reason.isEmpty())
            {
    			PreparedStatement ps = con.prepareStatement(city_reason);
            	ps.setString(1, "%" + city + "%");
				ps.setString(2, "%" + reason + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
            else
            {
    			PreparedStatement ps = con.prepareStatement(city);
    			ps.setString(1, "%" + city + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }			
			while(rs.next()) {
				newList.add(new Visitor(rs.getString("name"), rs.getString("email"),
										rs.getString("city"), rs.getString("state"),
										rs.getInt("zipcode"), rs.getString("date_of_visit"),
										rs.getInt("group_amount"), rs.getString("reason")));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		visitors = newList;		
	}
	
	//method for getting the values from database based on city and zipcode
	public void displayByZipAndCity(String zipcode, String city, String date, String reason)
	{
		ObservableList<Visitor> newList = FXCollections.observableArrayList();		
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/guestbook", "root", "");
			ResultSet rs;
			if (!date.isEmpty() && !reason.isEmpty())
            {
                PreparedStatement ps = con.prepareStatement(city_zip_date_reason);
                ps.setString(1, "%" + city + "%");
    			ps.setString(2, "%" + zipcode + "%");
    			ps.setString(3, "%" + date + "%");
    			ps.setString(4, "%" + reason + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
            else if (!date.isEmpty())
            {
            	PreparedStatement ps = con.prepareStatement(city_zip_date);
                ps.setString(1, "%" + city + "%");
    			ps.setString(2, "%" + zipcode + "%");
    			ps.setString(3, "%" + date + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
            else if (!reason.isEmpty())
            {
            	PreparedStatement ps = con.prepareStatement(city_zip_reason);
                ps.setString(1, "%" + city + "%");
    			ps.setString(2, "%" + zipcode + "%");
    			ps.setString(3, "%" + reason + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
            else
            {
            	PreparedStatement ps = con.prepareStatement(city_zip);
                ps.setString(1, "%" + city + "%");
    			ps.setString(2, "%" + zipcode + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
			
			while(rs.next()) {
				newList.add(new Visitor(rs.getString("name"), rs.getString("email"),
										rs.getString("city"), rs.getString("state"),
										rs.getInt("zipcode"), rs.getString("date_of_visit"),
										rs.getInt("group_amount"), rs.getString("reason")));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		visitors = newList;		
	}
	
	//method to display values by zip code
	public void displayByZip(String zip, String date, String reason)
	{
		ObservableList<Visitor> newList = FXCollections.observableArrayList();		
		Connection con = null;
		ResultSet rs;
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/guestbook", "root", "");
			
			if (!date.isEmpty() && !reason.isEmpty())
            {
    			PreparedStatement ps = con.prepareStatement(zip_date_reason);
    			ps.setString(1, "%" + zip + "%");
    			ps.setString(2, "%" + date + "%");
    			ps.setString(3, "%" + reason + "%");
				System.out.println(ps);
				rs = ps.executeQuery();
            }
            else if (!date.isEmpty())
            {
    			PreparedStatement ps = con.prepareStatement(zip_date);
    			ps.setString(1, "%" + zip + "%");
       			ps.setString(2, "%" + date + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
            else if (!reason.isEmpty())
            {
    			PreparedStatement ps = con.prepareStatement(zip_reason);
    			ps.setString(1, "%" + zip + "%");
    			ps.setString(2, "%" + reason + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
			
			//added an else statement if neither reason or date were specified
            else
            {
    			PreparedStatement ps = con.prepareStatement(zip);
    			ps.setString(1, "%" + zip + "%");
    			System.out.println(ps);
    			rs = ps.executeQuery();
            }
			
			while(rs.next()) {
				newList.add(new Visitor(rs.getString("name"), rs.getString("email"),
										rs.getString("city"), rs.getString("state"),
										rs.getInt("zipcode"), rs.getString("date_of_visit"),
										rs.getInt("group_amount"), rs.getString("reason")));
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
