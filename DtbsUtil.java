/**
 * Class that initializes the ArrayList and does SQL queries, 
 * for now you can only search by city and zip code
 */
package application;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DtbsUtil {

	private ObservableList<Visitor> visitors;
	private static final String INSERT_QUERY = "INSERT INTO visitor_info (name, email, city, state, zipcode,"
			+ " date_of_visit, group_amount, reason)"
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	public DtbsUtil() 
	{
		visitors = FXCollections.observableArrayList();
	}
	
	//method to display queries
	public void displayfilters(String metro, String city, String zip, LocalDate date, String reason) {
		ObservableList<Visitor> newList = FXCollections.observableArrayList();
		String query = "SELECT * FROM visitor_info WHERE ";

		ArrayList<String> filters = new ArrayList<>();
		if (!metro.isEmpty()) {
			filters.add("state LIKE '%" + metro + "%'");
		}

		if (!city.isEmpty()) {
			filters.add("city LIKE '%" + city + "%'");
		}

		if (!zip.isEmpty()) {
			filters.add("zipcode = " + Integer.parseInt(zip));
		}

		if (date != null) {
			filters.add("date_of_visit = '" + date + "'");
		}

		if (!reason.isEmpty()) {
			filters.add("reason LIKE '%" + reason + "%'");
		}

		query += filters.get(0);

		for (int i = 1; i < filters.size(); i++) {
			query += " AND " + filters.get(i);
		}
		query += ";";

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/guestbook", "root", "");
			ResultSet rs = con.createStatement().executeQuery(query);
			while (rs.next()) {
				newList.add(new Visitor(rs.getString("name"), rs.getString("email"),
						rs.getString("city"), rs.getString("state"),
						rs.getInt("zipcode"), rs.getString("date_of_visit"),
						rs.getInt("group_amount"), rs.getString("reason")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		visitors = newList;
	}

	// method to insert values into the database
	public void insertRecord(String name, String email, String city, String state, int zip,
			String date, int group_amount, String reason) {
		try {
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

	// throwing an error if something in the sql went wrong
	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
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

	public ObservableList<Visitor> getVisitors() {
		return visitors;
	}

	public void setVisitors(ObservableList<Visitor> visitors) {
		this.visitors = visitors;
	}

}