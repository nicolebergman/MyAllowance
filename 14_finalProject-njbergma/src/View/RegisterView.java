package View;

/**
*
* Register View: user can sign up and create their account with a username, password, profile picture, 
* starting balance, and currency
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class RegisterView {
	
	private VBox mainPane;
	private Scene scene;
	private Main main;

	
	String username, password, userCurrency;
	double userBalance;
	Image profilePic;
	User newUser;
	Parser parse;
	
	public RegisterView(Main main) {
		this.main = main;
		mainPane = new VBox();
		mainPane.setStyle("-fx-background-color: #A3D492");
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setSpacing(25);
		scene = new Scene(mainPane, 1050, 650);
		mainPane.setPadding(new Insets(10, 10, 40, 10));
		
		parse = new Parser();
		setUp();
	}
	
	public void setUp() {
		
		// logo
		Image logo = new Image("/Images/logo.jpg");
		ImageView logoView = new ImageView(logo);
		logoView.setPreserveRatio(true);
		logoView.setFitHeight(175);
		BorderPane pane = new BorderPane();
		pane.setPrefSize(400, 300);
		pane.setTop(logoView);
		BorderPane.setAlignment(logoView, Pos.CENTER);
		mainPane.getChildren().addAll(pane);
		// accessibility
        logoView.setAccessibleText("Logo for MyAllowance");
        logoView.setFocusTraversable(true);
		
		// label
		Label label = new Label("Please enter your personal information below:");
		label.setFont(Font.font("Verdana", 16));
		mainPane.getChildren().add(label);
		
		// user input
		HBox mainh = new HBox(50);
		label.setLabelFor(mainh);
        // user fields
		VBox left = new VBox();
        VBox userFields = new VBox(15);
        userFields.setAlignment(Pos.CENTER);
        userFields.setSpacing(8);
        // username field
        Label userLabel = new Label("Username");
        userLabel.setFont(Font.font("Verdana", 16));
        TextField userField = new TextField();
        userField.setMaxWidth(200);
        userFields.getChildren().addAll(userLabel, userField);
        // accessibility
        userField.setAccessibleHelp("This is the username text field");
        userLabel.setLabelFor(userField);
        
        // pass fields
        VBox passFields = new VBox(15);
        passFields.setAlignment(Pos.CENTER);
        passFields.setSpacing(8);
        // password field
        Label passLabel = new Label("Password");
        passLabel.setFont(Font.font("Verdana", 16));
        PasswordField passField = new PasswordField();
        passField.setMaxWidth(200);
        passFields.getChildren().addAll(passLabel, passField);
     // accessibility
        passField.setAccessibleHelp("This is the password text field");
        passLabel.setLabelFor(passField);
        
        left.getChildren().addAll(userFields, passFields);
        left.setPadding(new Insets(0, 50, 20, 50));
        left.setSpacing(15);
        left.setAlignment(Pos.CENTER);
        
        // user image
        VBox right = new VBox();
        right.setSpacing(10);
        right.setPadding(new Insets(0, 50, 20, 50));
        // default icon
        Label profileLabel = new Label("User icon");
        profileLabel.setFont(Font.font("Verdana", 16));
        ImageView icon = new ImageView();
        icon.setAccessibleText("Select an image to set as your profile picture");
        Image def = new Image("/Images/default.png");
        Image boy1 = new Image("/Images/boy.png");
        Image boy2 = new Image("/Images/boy2.png");
        Image girl1 = new Image("/Images/girl.png");
        Image girl2 = new Image("/Images/girl2.png");
        Image panda = new Image("/Images/panda.png");

        ComboBox<String> profPics = new ComboBox<String>();
        profPics.getItems().addAll("Default", "Boy 1", "Boy 2", "Girl 1", "Girl 2", "Panda");
        profPics.getSelectionModel().select(0);
        profPics.setOnAction(e -> {
        		if (profPics.getValue().equals("Default")) {
        			icon.setImage(def);
        		}
        		else if (profPics.getValue().equals("Boy 1")) {
        			icon.setImage(boy1);
        		}
        		else if (profPics.getValue().equals("Boy 2")) {
        			icon.setImage(boy2);
        		}
        		else if (profPics.getValue().equals("Girl 1")) {
        			icon.setImage(girl1);
        		}
        		else if (profPics.getValue().equals("Girl 2")) {
        			icon.setImage(girl2);
        		}
        		else if (profPics.getValue().equals("Panda")) {
        			icon.setImage(panda);
        		}
        });
        icon.setPreserveRatio(true);
        icon.setFitHeight(150);
        right.getChildren().addAll(profileLabel, icon, profPics);
        mainh.getChildren().addAll(left, right);
        right.setSpacing(15);
        mainh.setAlignment(Pos.CENTER);
        mainPane.getChildren().add(mainh);
        
        // account balance
        VBox acctbal = new VBox();
        acctbal.setAlignment(Pos.CENTER);
        acctbal.setSpacing(8);
        // balance field
        HBox balance = new HBox(5);
        // spinner
        VBox spin = new VBox(5);
        Label spinLabel = new Label("Account balance");
        spinLabel.setFont(Font.font("Verdana", 16));
        Spinner<Double> spinner = new Spinner<Double>(0.00, 10000.00, 500.00, 0.5);
        spinner.setEditable(true);
        spinner.setAccessibleText("Input your account balance");
        spin.getChildren().addAll(spinLabel, spinner);
        // currency dropdown
        ComboBox<String> currency = new ComboBox<String>();
        currency.getItems().addAll("USD","BGD");
        currency.getSelectionModel().select(0);
        balance.getChildren().addAll(spin, currency);
        balance.setAlignment(Pos.CENTER);
        acctbal.getChildren().addAll(balance);
        mainPane.getChildren().add(acctbal);
        
        // buttons
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        // submit button
        Button submitButton = new Button("Submit");
        submitButton.setAlignment(Pos.CENTER);
        submitButton.setDefaultButton(true);
        submitButton.setOnAction(e -> {
        		if (userField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Please enter a username.");
                return;
        		}
        		username = userField.getText();
        		if (passField.getText().isEmpty()) {
        			showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Please enter a password.");
        			return;
        		}
        		if (parse.getUsersMap().containsKey(userField.getText())) {
        			showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Username already taken.");
        			return;
        		}
        		password = passField.getText();
        		userBalance = spinner.getValue();
        		userCurrency = currency.getSelectionModel().getSelectedItem().toString();
        		newUser = new User(username, password);
        		newUser.setBalance(userBalance);
        		newUser.setCurrency(userCurrency);
        		String profileIcon = "";
        		if (profPics.getValue().equals("Default")) {
        			icon.setImage(def);
        			profileIcon = "/Images/default.png";
        		}
        		else if (profPics.getValue().equals("Boy 1")) {
        			icon.setImage(boy1);
        			profileIcon = "/Images/boy.png";
        		}
        		else if (profPics.getValue().equals("Boy 2")) {
        			icon.setImage(boy2);
        			profileIcon = "/Images/boy2.png";
        		}
        		else if (profPics.getValue().equals("Girl 1")) {
        			icon.setImage(girl1);
        			profileIcon = "/Images/girl.png";
        		}
        		else if (profPics.getValue().equals("Girl 2")) {
        			icon.setImage(girl2);
        			profileIcon = "/Images/girl2.png";
        		}
        		else if (profPics.getValue().equals("Panda")) {
        			icon.setImage(panda);
        			profileIcon = "/Images/panda.png";
        		}
        		newUser.setProfilePic(profileIcon);
        		parse.getUsersMap().put(newUser.getUsername(), newUser);
        		newUser = parse.getUsersMap().get(userField.getText());
        		main.setUsername(newUser.getUsername());
        		parse.writeUserToJson(newUser);
        		main.loadDashboard();
        });
        // back button
        Button back = new Button("Go back");
        back.setOnAction(e -> {
        		main.loadStartView();
        });
        buttons.getChildren().addAll(submitButton, back);
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
