//Controller class  
package application;

import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.*;
import javafx.stage.Stage;
import javafx.stage.Window;

public class sceneController {
	
	//object to access database class with sql methods
	private DtbsUtil model = new DtbsUtil();
	
	//all of the buttons in fxml files
    @FXML
    Button switch_database, v_main, b_main, submit_button, search_button,
    	   visitor, bureau;
    Label totalSearchResults;
    
    //takes user to database search side
    @FXML
    void goToBureau(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("database_scene.fxml"));
    	Stage window = (Stage) bureau.getScene().getWindow();
    	window.setScene(new Scene(root));
    }
    
    //takes user to visitor's input side
    @FXML
    void goToVisitor(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("visitors_view.fxml"));
    	Stage window = (Stage) visitor.getScene().getWindow();
    	window.setScene(new Scene(root));
    }
    
    //takes user from bureau side to main menu
    @FXML
    void bureau_toMainMenu() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("start_menu.fxml"));
    	Stage window = (Stage) b_main.getScene().getWindow();
    	window.setScene(new Scene(root));
    }
    
    //takes user from visitor's side to main menu
    @FXML
    void visitor_toMainMenu() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("start_menu.fxml"));
    	Stage window = (Stage) v_main.getScene().getWindow();
    	window.setScene(new Scene(root));
    }
    
    @FXML
    void handleCity(ActionEvent event)
    {
    	handlesearchButton(event);
    }
    
    //searching by city, zipcode, city + zipcode
    @FXML
    void handlesearchButton(ActionEvent event) {
    	setTableView();
    	Window owner = search_button.getScene().getWindow();
    	String date = "";
    	
    	if (date_input.getValue() != null)
    	{
    		date = date_input.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	}

    	if (!city_input.getText().isEmpty() && !zip_input.getText().isEmpty())
    	{
    		model.displayByZipAndCity(zip_input.getText(), city_input.getText(), date, reason_input.getText());
    	}
    	else if (!city_input.getText().isEmpty())
    	{
    		model.displayByCity(city_input.getText(), date, reason_input.getText());
    	}
    	
    	else if(!zip_input.getText().isEmpty())
    	{
    		model.displayByZip(zip_input.getText(), date, reason_input.getText());
    	}
    	
    	//if no people were found, an error window shows up saying that no people were found
    	if (model.getVisitors().isEmpty())
    	{
    		showAlert(Alert.AlertType.ERROR, owner, "No Entries", "No people were found based on your search.");
    	}
    	else
    	{
            tableview.setItems(model.getVisitors());
	    totalSearchResults.setText("Your search has returned " + tableview.getItems().size() + " results.");
    	}
    }

    //sends information to the database from visitor's input
    @FXML
    void btnSubmitClicked(ActionEvent event) throws SQLException{
    	Window owner = submit_button.getScene().getWindow();
    	
    	//throwing an error if not all fields are filled out
    	if (fname.getText().isEmpty() || lname.getText().isEmpty() || midinit.getText().isEmpty()
    		|| city.getText().isEmpty() || state.getText().isEmpty() || email.getText().isEmpty()
    		|| (date.getValue() == null) || amount_of_people.getText().isEmpty() || 
    		zip.getText().isEmpty() || reason.getText().isEmpty())
    	{
    		showAlert(Alert.AlertType.ERROR, owner, "Form Error", "Please fill out all fields.");
    		return;
    	}
    	
    	String zip1 = zip.getText();
    	String amount = amount_of_people.getText();
    	
    	String name = fname.getText() + " " + midinit.getText() + ". " + lname.getText();
    	String email1 = email.getText();
    	String city1 = city.getText();
    	String state1 = state.getText();
    	int zipcode = Integer.parseInt(zip1);
    	String date1 = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	int people = Integer.parseInt(amount);
    	String reason1 = reason.getText();
    	
    	model.insertRecord(name, email1, city1, state1, zipcode, date1, people, reason1);
    	showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
                "You are registered " + name);
    	
    }
    
    //method for displaying an error with input from user's side
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) 
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    //setting table view 
    @FXML
    public void setTableView() {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("Name"));
    	emailColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("Email"));
    	cityColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("City"));
    	stateColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("State"));
    	zipColumn.setCellValueFactory(new PropertyValueFactory<Visitor, Integer>("Zipcode"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("Date"));
    	peopleColumn.setCellValueFactory(new PropertyValueFactory<Visitor, Integer>("Group"));
    	reasonColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("Reason"));
    	tableview.setItems(model.getVisitors());
    }
    
 
    //values for visitor's side
	@FXML
    private TextField amount_of_people;

    @FXML
    private TextField city;

    @FXML
    private DatePicker date;

    @FXML
    private TextField email;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField midinit;

    @FXML
    private TextArea reason;

    @FXML
    private TextField state;

    @FXML
    private TextField zip;
    
    
    //values for database side
    @FXML
    private TextField city_input;

    @FXML
    private DatePicker date_input;

    @FXML
    private TextField metro_input;

    @FXML
    private TextArea reason_input;

    @FXML
    private TextField zip_input;
    
    @FXML
    public TableView<Visitor> tableview;

    @FXML
    private TableColumn<Visitor, String> cityColumn;

    @FXML
    private TableColumn<Visitor, String> dateColumn;

    @FXML
    private TableColumn<Visitor, String> emailColumn;

    @FXML
    private TableColumn<Visitor, String> nameColumn;

    @FXML
    private TableColumn<Visitor, String> reasonColumn;

    @FXML
    private TableColumn<Visitor, String> stateColumn;
    
    @FXML
    private TableColumn<Visitor, Integer> zipColumn;
    
    @FXML
    private TableColumn<Visitor, Integer> peopleColumn;

}
