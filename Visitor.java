//Class that stores info about visitors into an object
package application;

public class Visitor {
	private String name;
	private String email;
	private String city;
	private String state;
	private int zipcode;
	private String date;
	private String reason;
	private int group;
	
	public Visitor(String name, String email, String city, String state, int zipcode, String date, int group, String reason) {
		super();
		this.name = name;
		this.email = email;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.date = date;
		this.group = group;
		this.reason = reason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}
	
	
}
