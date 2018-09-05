package View;

/**
*
* Deposit: User can make a deposit to their account.
*
* @author Nicole Bergman
* ITP 368, Spring 2018
* Final Project
* njbergma@usc.edu
*/

import java.util.ArrayList;
import Controller.Main;
import Controller.Parser;
import Model.Category;
import Model.Transaction;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class Deposit {
	
	private BorderPane mainPane;
	private Scene scene;
	private Main main;
	private Parser parse;
	
	User loggedInUser;
	ArrayList<Transaction> trans = new ArrayList<Transaction>();
	
	public Deposit(Main main) {
		this.main = main;
		mainPane = new BorderPane();
		mainPane.setStyle("-fx-background-color: #dcf2d5");
		scene = new Scene(mainPane, 1050, 650);
		
		parse = new Parser();
		loggedInUser = parse.getUsersMap().get(main.getUsername());
		
		mainPane.setLeft(makeNavBar());
		mainPane.setCenter(makeCenter());
	}

	private Node makeCenter() {
		VBox v = new VBox();
		v.setAlignment(Pos.CENTER);
		v.setSpacing(100);
		
		// widget container
		VBox container = new VBox();
		container.setSpacing(25);
		container.setAlignment(Pos.CENTER);
		
		// title
		Label title = new Label("Make a Deposit");
		title.setFont(Font.font("Verdana", 30));
		v.getChildren().add(title);
		
		// deposit container
		VBox deposit = new VBox();
		title.setLabelFor(deposit);
		deposit.setMaxSize(500, 500);
		deposit.setStyle("-fx-background-color: #A3D492; -fx-background-radius: 10");
		deposit.setPadding(new Insets(15,15,15,15));
		deposit.setSpacing(8);
		// labels
        
        // sum
		Label sum = new Label("Sum");
		sum.setFont(Font.font("Verdana", 16));
		TextField textSum = new TextField();
		textSum.setFont(Font.font("Verdana", 16));
		textSum.setMaxWidth(100);
		sum.setLabelFor(textSum);
		
		// date
		Label date = new Label("Date");
		date.setFont(Font.font("Verdana", 16));
		DatePicker picker = new DatePicker();
		date.setLabelFor(picker);
		
		// submit button
		Button submit = new Button("Deposit");
		submit.setFont(Font.font("Verdana", 16));
		submit.setOnAction(e -> {
        		if (sum.getText().isEmpty()) {
        			showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Please enter a sum");
        			return;
        		}
        		trans = loggedInUser.getMyTransactions();
        		String myDate = picker.getValue().toString();
	        	double mySum = Double.parseDouble(textSum.getText());
	        	Transaction t = new Transaction("Deposit", Category.SALARY, mySum, myDate, loggedInUser.getCurrency());
			trans.add(t);
			loggedInUser.increaseBalance(mySum);
			parse.writeDepositToJson(loggedInUser.getBalance(), loggedInUser.getUsername(), t);
			main.loadDashboard();
		});
		
		deposit.getChildren().addAll(sum, textSum, date, picker, submit);
		container.getChildren().addAll(title, deposit);
		v.getChildren().add(container);
		return v;
	}

	private Node makeNavBar() {
		VBox v = new VBox();
		v.setStyle("-fx-background-color: #A3D492");
		v.setSpacing(85);
		v.setPrefWidth(250);
		v.setAlignment(Pos.CENTER);
		
		// user container
		VBox container = new VBox(8);
		container.setAlignment(Pos.CENTER);
		// logo
		Image logo = new Image("/Images/logo.jpg");
		ImageView logoView = new ImageView(logo);
		logoView.setPreserveRatio(true);
		logoView.setFitHeight(175);
		// user image
		ImageView userImage = new ImageView(new Image(loggedInUser.getProfilePic()));
		userImage.setPreserveRatio(true);
		userImage.setFitHeight(75);
		// user label
		Label welcomeLabel = new Label("Hello, " + loggedInUser.getUsername() + "!");
		welcomeLabel.setFont(Font.font("Verdana", 15));
		container.getChildren().addAll(logoView, userImage, welcomeLabel);
		v.getChildren().add(container);
		
		// buttons
		VBox buttons = new VBox(5);
		buttons.setAlignment(Pos.CENTER);
		Button dashboard = new Button("Dashboard");
		dashboard.setFont(Font.font("Verdana", 15));
		dashboard.setOnAction(e -> {
			main.loadDashboard();
		});
		Button deposit = new Button("Deposit");
		deposit.setFont(Font.font("Verdana", 15));
		deposit.setOnAction(e -> {
			main.loadDeposit();
		});
		Button settings = new Button("Settings");
		settings.setFont(Font.font("Verdana", 15));
		settings.setOnAction(e -> {
			main.loadSettings();
		});
		buttons.getChildren().addAll(dashboard, deposit, settings);
		v.getChildren().add(buttons);
		
		// log off button
		Button signout = new Button("Sign Out");
		signout.setFont(Font.font("Verdana", 15));
		signout.setOnAction(e -> {
			main.loadStartView();
		});
		v.getChildren().add(signout);
		
		return v;
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
