package View;

/**
*
* Settings: user can edit their password, and change their currency.
*
* @author Nicole Bergman
* ITP 368, Spring 2018
* Final Project
* njbergma@usc.edu
*/

import java.util.ArrayList;
import java.util.HashMap;
import Controller.Main;
import Controller.Parser;
import Model.Transaction;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class Settings {
	
	private BorderPane mainPane;
	private Scene scene;
	private Main main;
	
	User loggedInUser;
	Parser parse;
	
	public Settings(Main main) {
		this.main = main;
		mainPane = new BorderPane();
		
		parse = new Parser();
		loggedInUser = parse.getUsersMap().get(main.getUsername());
		
		mainPane.setStyle("-fx-background-color: #dcf2d5"); //$NON-NLS-1$
		scene = new Scene(mainPane, 1050, 650);
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
		Label title = new Label(Messages.getString("Settings.settingsLabel")); //$NON-NLS-1$
		title.setFont(Font.font("Verdana", 30)); //$NON-NLS-1$
		v.getChildren().add(title);
		
		// change currency container
		VBox currContainer = new VBox();
		currContainer.setMaxSize(500, 500);
		currContainer.setStyle("-fx-background-color: #A3D492; -fx-background-radius: 10"); //$NON-NLS-1$
		currContainer.setPadding(new Insets(15,15,15,15));
		currContainer.setSpacing(8);
		// label 
		Label change = new Label(Messages.getString("Settings.changeCurrency")); //$NON-NLS-1$
		change.setFont(Font.font("Verdana", 18)); //$NON-NLS-1$
		// combo button
		ComboBox<String> curr = new ComboBox<String>();
		curr.getItems().addAll("US Dollar (USD)", "British Pound (GBP)"); //$NON-NLS-1$ //$NON-NLS-2$
		curr.getSelectionModel().select(1);
		curr.setStyle("-fx-font: 16px \"Verdana\";"); //$NON-NLS-1$
		curr.setAccessibleText(Messages.getString("Settings.selectCurrency")); //$NON-NLS-1$
		// submit button
		Button submit = new Button(Messages.getString("Settings.submitButton")); //$NON-NLS-1$
		submit.setOnAction(e -> {
			ArrayList<Transaction> transactions = loggedInUser.getMyTransactions();
			if (curr.getSelectionModel().getSelectedItem().equals("US Dollar (USD)")) {
				if (loggedInUser.getCurrency().equals("GBP")) {
					loggedInUser.setBalance(loggedInUser.getBalance()*.73);
					loggedInUser.setCurrency("USD");
					for (Transaction t : transactions) {
						double sum  = t.getSum()*1.35784;
						sum = Math.round(sum * 100);
						sum = sum/100;
						t.setSum(sum);
						t.setCurrency("USD");
					}
				}
			}
			else {	// set to GBP
				if (loggedInUser.getCurrency().equals("USD")) {
					loggedInUser.setBalance(loggedInUser.getBalance()*1.38);
					loggedInUser.setCurrency("GBP");
					for (Transaction t : transactions) {
						double sum  = t.getSum()*0.736461;
						sum = Math.round(sum * 100);
						sum = sum/100;
						t.setSum(sum);
						t.setCurrency("GBP");
					}
				}
			}
			parse.editTransactionForJson(loggedInUser);
			main.loadDashboard();
		});
		currContainer.getChildren().addAll(change, curr, submit);
		
		// change password container
		VBox passContainer = new VBox();
		passContainer.setMaxSize(500, 500);
		passContainer.setStyle("-fx-background-color: #A3D492; -fx-background-radius: 10"); //$NON-NLS-1$
		passContainer.setPadding(new Insets(15,15,15,15));
		passContainer.setSpacing(8);
		// labels + textboxes + button
		Label pass = new Label(Messages.getString("Settings.changePass")); //$NON-NLS-1$
		pass.setFont(Font.font("Verdana", 18)); //$NON-NLS-1$
		Label newPass = new Label(Messages.getString("Settings.newPassLabel")); //$NON-NLS-1$
		newPass.setFont(Font.font("Verdana", 16)); //$NON-NLS-1$
		PasswordField password = new PasswordField();
		password.setFont(Font.font("Verdana", 16)); //$NON-NLS-1$
		newPass.setLabelFor(password);
		Label newPass2 = new Label(Messages.getString("Settings.repeatPassLabel")); //$NON-NLS-1$
		newPass2.setFont(Font.font("Verdana", 16)); //$NON-NLS-1$
		PasswordField password2 = new PasswordField();
		password2.setFont(Font.font("Verdana", 16)); //$NON-NLS-1$
		newPass2.setLabelFor(password2);
		Button submitPass = new Button(Messages.getString("Settings.submitButton")); //$NON-NLS-1$
		submitPass.setOnAction(e -> {
			if (!password.getText().equals(password2.getText())) {
                showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), Messages.getString("Settings.error"), Messages.getString("Settings.passDoesntMatch")); //$NON-NLS-1$ //$NON-NLS-2$
                return;
        		}
			loggedInUser.setPassword(password.getText());
			parse.editPasswordForJson(loggedInUser);
			main.loadDashboard();
		});
		passContainer.getChildren().addAll(pass, newPass, password, newPass2, password2, submitPass);
		container.getChildren().addAll(currContainer, passContainer);
		v.getChildren().add(container);
		
		return v;
	}
	
	private Node makeNavBar() {
		HashMap<String, User> myUsersMap = parse.getUsersMap();
		loggedInUser = myUsersMap.get(main.getUsername());
		
		VBox v = new VBox();
		v.setStyle("-fx-background-color: #A3D492"); //$NON-NLS-1$
		v.setSpacing(85);
		v.setPrefWidth(250);
		v.setAlignment(Pos.CENTER);
		
		// user container
		VBox container = new VBox(8);
		container.setAlignment(Pos.CENTER);
		// logo
		Image logo = new Image("/Images/logo.jpg"); //$NON-NLS-1$
		ImageView logoView = new ImageView(logo);
		logoView.setPreserveRatio(true);
		logoView.setFitHeight(175);
		// user image
		ImageView userImage = new ImageView(new Image(loggedInUser.getProfilePic())); //$NON-NLS-1$
		userImage.setPreserveRatio(true);
		userImage.setFitHeight(75);
		// user label
		Label welcomeLabel = new Label("Hello, " + loggedInUser.getUsername() + "!");
		welcomeLabel.setFont(Font.font("Verdana", 15)); //$NON-NLS-1$
		container.getChildren().addAll(logoView, userImage, welcomeLabel);
		v.getChildren().add(container);
		
		// buttons
		VBox buttons = new VBox(5);
		buttons.setAlignment(Pos.CENTER);
		Button dashboard = new Button(Messages.getString("Settings.homepage")); //$NON-NLS-1$
		dashboard.setFont(Font.font("Verdana", 15)); //$NON-NLS-1$
		dashboard.setOnAction(e -> {
			main.loadDashboard();
		});
		Button deposit = new Button(Messages.getString("Settings.depositPage")); //$NON-NLS-1$
		deposit.setFont(Font.font("Verdana", 15)); //$NON-NLS-1$
		deposit.setOnAction(e -> {
			main.loadDeposit();
		});
		Button settings = new Button(Messages.getString("Settings.settingsPage")); //$NON-NLS-1$
		settings.setFont(Font.font("Verdana", 15)); //$NON-NLS-1$
		settings.setOnAction(e -> {
			main.loadSettings();
		});
		buttons.getChildren().addAll(dashboard, deposit, settings);
		v.getChildren().add(buttons);
		
		// log off button
		Button signout = new Button(Messages.getString("Settings.signoutPage")); //$NON-NLS-1$
		signout.setFont(Font.font("Verdana", 15)); //$NON-NLS-1$
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
