/**
 * Class that initializes the ArrayList and does SQL queries, for now you can only search by city
 */
package application;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class linkLists {

	private ObservableList<Visitor> visitors;
	
	public linkLists() {
		visitors = FXCollections.observableArrayList();
	}

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
