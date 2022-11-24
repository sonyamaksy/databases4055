//Controller class nothing fancy 
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

public class sceneController {

	private linkLists model = new linkLists();
	
    @FXML
    Button switch_database, switch_visitor, submit_button;
    
    @FXML
    private Button search_button;
    
    @FXML
    void handleCity(ActionEvent event)
    {
    	handlesearchButton(event);
    }
    
    @FXML
    void handlesearchButton(ActionEvent event) {
    	model.updateVisitorsList(city_input.getText());
    	tableview.setItems(model.getVisitors());
    }

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
    
    //method for displaying an error
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) 
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    @FXML
    void switchToVisitor() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("visitors_view.fxml"));
    	Stage window = (Stage) switch_visitor.getScene().getWindow();
    	window.setScene(new Scene(root));
    }
    
    @FXML
    void switchToDatabase() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("database_scene.fxml"));
    	Stage window = (Stage) switch_database.getScene().getWindow();
    	window.setScene(new Scene(root));
    }
    
    /**@FXML
    public void initialize() {
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("Name"));
    	emailColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("Email"));
    	cityColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("City"));
    	stateColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("State"));
    	zipColumn.setCellValueFactory(new PropertyValueFactory<Visitor, Integer>("Zipcode"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("Date"));
    	reasonColumn.setCellValueFactory(new PropertyValueFactory<Visitor, String>("Reason"));
    	tableview.setItems(model.getVisitors());
    }
    */

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
    private TableView<Visitor> tableview;

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

}
