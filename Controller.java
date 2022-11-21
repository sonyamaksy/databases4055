package application;

import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller
{
	String value;
	
	Jdbc dbutil = new Jdbc();

	//all of the fxml variables from input 
    @FXML
    private TextField fname;

    @FXML
    private TextField midinit;
    
    @FXML
    private TextField lname;
    
    @FXML
    private TextField email;

    @FXML
    private TextArea reason;

    @FXML
    private TextField city;
    
    @FXML
    private TextField state;

    @FXML
    private TextField zip;

    @FXML
    private TextField amount_of_people;
    
    @FXML
    private DatePicker date;
    
	public String getValues()
	{
		return value;
	}
	
    @FXML
    void btnSubmitClicked(ActionEvent event) 
    {
    	//storing all of the values once the submit is clicked
    	String f_name = fname.getText();
    	String m_init = midinit.getText();
    	String l_name = lname.getText();
    	String e_mail = email.getText();
    	
    	String reason1 = reason.getText();
    	String date1 = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	int num_of_people = Integer.parseInt(amount_of_people.getText());
    	
    	String city1 = city.getText();
    	String state1 = state.getText();
    	int zipcode = Integer.parseInt(zip.getText());
    	
       	String full_name = f_name + " " + m_init + " " + l_name;
    	String address = city1 + ", " + state1 + ", " + zipcode;
    	
    	value = "VALUES ('" + full_name + "', '" + e_mail + "', '" + city1 + "', '"
    			+ state1 + "', " + zipcode + ", '" + date1 + "', " + num_of_people + ", '"
    			+ reason1 + "');";
       	
    	System.out.println(getValues());
    	//printing out to see if it works properly
    	System.out.println("Your name is: " + full_name);
    	System.out.println("Your email is: " + e_mail);
    	System.out.println("Your address is: " + address);
    	System.out.println("Your date of visit: " + date1);
    	System.out.println("Reason of visit: " + reason1);
    	System.out.println("Number of people in your group: " + num_of_people);    	
    }    
}
