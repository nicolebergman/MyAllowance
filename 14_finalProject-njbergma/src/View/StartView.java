package View;

/**
*
* StartViwe: Opening scene for the application: login with credentials to navigate. 
*
* @author Nicole Bergman
* ITP 368, Spring 2018
* Final Project
* njbergma@usc.edu
*/

import Controller.Main;
import Controller.Parser;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class StartView {
	
	private Scene scene;
	private VBox mainPane;
	private Main main;
	
	Parser parse;
	User loggedInUser;
	
	public StartView(Main main) {
		this.main = main;
		parse = new Parser();
		mainPane = new VBox();
		mainPane.setStyle("-fx-background-color: #A3D492");
		mainPane.setSpacing(15);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(0, 10, 30, 10));
		scene = new Scene(mainPane, 1050, 650);
		setUp();
	}
	
	
	
	public void setUp() {
		System.out.println("Create a new user, or sign in with user=kendra pass=kendra");
		// logo
		Image logo = new Image("/Images/logo.jpg");
		ImageView logoView = new ImageView(logo);
		logoView.setPreserveRatio(true);
		logoView.setFitHeight(250);
		BorderPane pane = new BorderPane();
        pane.setPrefSize(400, 300);
        pane.setCenter(logoView);
        mainPane.getChildren().addAll(pane);
        // accessibility
        logoView.setAccessibleText("Logo for MyAllowance");
        logoView.setFocusTraversable(true);
        
        // user input
        // user fields
        VBox userFields = new VBox();
        userFields.setAlignment(Pos.CENTER);
        userFields.setSpacing(8);
        
        // username field
        Label userLabel = new Label("Username");
        userLabel.setFont(Font.font("Verdana", 18));
        TextField userField = new TextField();
        userField.setMaxWidth(200);
        userFields.getChildren().addAll(userLabel, userField);
        // accessibility
        userField.setAccessibleHelp("This is the username text field");
        userLabel.setLabelFor(userField);
        
        // pass fields
        VBox passFields = new VBox();
        passFields.setAlignment(Pos.CENTER);
        passFields.setSpacing(8);
        // password field
        Label passLabel = new Label("Password");
        passLabel.setFont(Font.font("Verdana", 18));
        PasswordField passField = new PasswordField();
        passField.setMaxWidth(200);
        passFields.getChildren().addAll(passLabel, passField);
        mainPane.getChildren().addAll(userFields, passFields);
        // accessibility
        passField.setAccessibleHelp("This is the password text field");
        passLabel.setLabelFor(passField);
        
        // buttons
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        // submit button
        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Verdana", 15));
        submitButton.setDefaultButton(true);
        submitButton.setOnAction(e -> {
        		if (userField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Please enter a username.");
                return;
        		}
        		if (passField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Please enter a password.");
                    return;
        		}
        		if (!passField.getText().equals(parse.getUsersMap().get(userField.getText()).getPassword())) {
        			showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Username and password do not match.");
        			return;
        		}
        		if (!parse.getUsersMap().containsKey(userField.getText())) {
        			showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Username does not exist.");
        			return;
        		}
        		main.setLoggedIn(true);
        		loggedInUser = parse.getUsersMap().get(userField.getText());
        		main.setUsername(loggedInUser.getUsername());
        		userField.clear();
        		passField.clear();
        		main.loadDashboard();
        });
        // register button
        Hyperlink registerLink = new Hyperlink("Register new account");
        registerLink.setFont(Font.font("Verdana", 15));
        registerLink.setOnAction(e -> {
        		main.loadRegisterView();
        });
        buttons.getChildren().addAll(submitButton, registerLink);
        mainPane.getChildren().add(buttons);
	}
	
	private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
        	alert.setTitle(title);
        	alert.setHeaderText(null);
        	alert.setContentText(message);
        	alert.initOwner(owner);
        	alert.show();
    }
	
	public Scene getScene() {
		return scene;
	}



}
