//Controller class nothing fancy
package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class sceneController {

	private linkLists model = new linkLists();
    @FXML
    Button switch_database, switch_visitor;
    
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
    void btnSubmitClicked(ActionEvent event) {

    }

    @FXML
    void switchToVisitor() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("visitors_view.fxml"));
    	Stage window = (Stage) switch_visitor.getScene().getWindow();
    	window.setScene(new Scene(root));
    }
    
    @FXML
    void switchToDatabase() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("database_.fxml"));
    	Stage window = (Stage) switch_database.getScene().getWindow();
    	window.setScene(new Scene(root));
    }
    
    @FXML
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
}
