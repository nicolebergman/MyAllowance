package View;

/**
*
* Dashboard: Most of the application functionality is in this scene. User can view their current balance, add a transaction,
* view the distribution of their expenses, and view transaction history. From this scene, they can also navigate to other
* pages.
* 
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Window;

public class Dashboard {
	
	private BorderPane mainPane;
	private Scene scene;
	private Main main;
	private Parser parse;
	
	User loggedInUser;
	ArrayList<Transaction> trans = new ArrayList<Transaction>();
	StringBuilder typed = new StringBuilder();
	
	
	public Dashboard(Main main) {
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
		v.setSpacing(15);
		v.setAlignment(Pos.CENTER);
		
		// for top half with balance and transactions
		HBox topHalf = new HBox();
		topHalf.setSpacing(30);
		topHalf.setAlignment(Pos.TOP_CENTER);
		
		// balance box
		VBox leftContainer = new VBox();
		Label currBalance = new Label("Current Balance");
		currBalance.setFont(Font.font("Verdana", 18));
		Label bal = new Label(loggedInUser.getBalance() + "    " + loggedInUser.getCurrency());
//		Label bal = new Label("1000 USD");
		bal.setAccessibleHelp("Your current balance is 1000 USD");
		bal.setFont(Font.font("Verdana", 20));
		bal.setTextFill(Color.WHITE);
		leftContainer.getChildren().addAll(currBalance, bal);
		leftContainer.setStyle("-fx-background-color: #A3D492; -fx-background-radius: 10");
		leftContainer.setPadding(new Insets(50,15,50,15));
		leftContainer.setAlignment(Pos.CENTER);
		leftContainer.setSpacing(10);
		
		// transaction box
		VBox right = new VBox();
		right.setStyle("-fx-background-color: #A3D492; -fx-background-radius: 10");
		right.setPadding(new Insets(15,15,15,15));
		right.setAlignment(Pos.CENTER);
		right.setSpacing(10);
		// container
		HBox rightContainer = new HBox();
		rightContainer.setSpacing(10);

		// title
		Label add = new Label("Add Transaction");
		add.setFont(Font.font("Verdana", 18));
		right.getChildren().add(add);
		
		// labels
		VBox leftSide = new VBox();		
		leftSide.setSpacing(22);
		// UI controls
		VBox rightSide = new VBox();		
		rightSide.setSpacing(10);
		// expense
		Label expense = new Label("Expense");
		expense.setFont(Font.font("Verdana", 12));
		TextField expenseText = new TextField();
		expense.setLabelFor(expenseText);
		// sums
		Label sum = new Label("Sum");	// change to take only dollas
		sum.setFont(Font.font("Verdana", 12));
		TextField sumText = new TextField();
		sum.setLabelFor(sumText);
		// dates
		Label date = new Label("Date");
		date.setFont(Font.font("Verdana", 12));
		DatePicker picker = new DatePicker();
		date.setLabelFor(picker);
		// categories
		Label category = new Label("Category");
		category.setFont(Font.font("Verdana", 12));
		ComboBox<String> categories = new ComboBox<String>();
		categories.getItems().addAll(
        	    "Shopping",
        	    "Dining",
        	    "Travel",
        	    "Bills & Utilities",
        	    "Miscellaneous"
        	);
		categories.setStyle("-fx-font: 12px \"Verdana\";");
        categories.getSelectionModel().select(0);
        category.setLabelFor(categories);
        
        // submit button
        Button addButton = new Button("Add");
        addButton.setFont(Font.font("Verdana", 12));
        addButton.setOnAction(e -> {
	        	if (expenseText.getText().isEmpty()) {
	                showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Please enter an expense");
	                return;
	        		}
	        	if (sumText.getText().isEmpty()) {
	        		showAlert(Alert.AlertType.ERROR, mainPane.getScene().getWindow(), "Form Error!", "Please enter a sum");
	        		return;
	        	}
	        	trans = loggedInUser.getMyTransactions();
	        	Category myCategory = null;
	        	if (categories.getValue().equals("Shopping")) {
	        		myCategory = Category.SHOPPING;
	        	}
	        	else if (categories.getValue().equals("Dining")) {
	        		myCategory = Category.DINING;
	        	}
	        	else if (categories.getValue().equals("Travel")) {
	        		myCategory = Category.TRAVEL;
	        	}
	        	else if (categories.getValue().equals("Bills & Utilities")) {
	        		myCategory = Category.BILLS;
	        	}
	        	else {
	        		myCategory = Category.MISC;
	        	}
	        	String myDate = picker.getValue().toString();
	        	double mySum = Double.parseDouble(sumText.getText());
	        	Transaction t = new Transaction(expenseText.getText(), myCategory, mySum, myDate, loggedInUser.getCurrency());
	        	trans.add(t);
	        	loggedInUser.decreaseBalance(mySum);
	        	parse.writeTransactionToJson(loggedInUser.getBalance(), loggedInUser.getUsername(), t);
	        	main.loadDashboard();
        });
        
		leftSide.getChildren().addAll(expense, sum, date, category);		
		rightSide.getChildren().addAll(expenseText, sumText, picker, categories);		
		rightContainer.getChildren().addAll(leftSide, rightSide);
		right.getChildren().addAll(rightContainer, addButton);
		topHalf.getChildren().addAll(leftContainer, right);
		v.getChildren().add(topHalf);
		
		// expense tracker
		HBox expensesTracker = new HBox();
		expensesTracker.setSpacing(10);
		expensesTracker.setAlignment(Pos.CENTER);
		
		// shopping
		// circle
		StackPane shopping = new StackPane();
		Circle c1 = new Circle();
		c1.setRadius(70);
		c1.setFill(Color.LAVENDER);
        // labels
        VBox shopL = new VBox();
        shopL.setAlignment(Pos.CENTER);
        shopL.setSpacing(5);
        Label shop = new Label("Shopping");
        shop.setFont(Font.font("Verdana", 15));
        Label shopExp = new Label(loggedInUser.getShoppingExpense() + "  " + loggedInUser.getCurrency());
//        Label shopExp = new Label("100.00 USD");
        shopExp.setAccessibleText("Your shopping expense is 100 USD");
        shopExp.setFont(Font.font("Verdana", 14));
        shopL.getChildren().addAll(shop, shopExp);
        shopping.getChildren().addAll(c1, shopL);

        // Dining
        // circle
        StackPane dining = new StackPane();
        Circle c2 = new Circle();
        c2.setRadius(70);
        c2.setFill(Color.PALEVIOLETRED);
        // labels
        VBox dineL = new VBox();
        dineL.setAlignment(Pos.CENTER);
        dineL.setSpacing(5);
        Label dine = new Label("Dining");
        dine.setFont(Font.font("Verdana", 14));
        Label dineExp = new Label(loggedInUser.getDiningExpense() + "  " + loggedInUser.getCurrency());
//        Label dineExp = new Label("100.00 USD");
        dineExp.setAccessibleText("Your dining expense is 100 USD");
        dineExp.setFont(Font.font("Verdana", 15));
        dineL.getChildren().addAll(dine, dineExp);
        dining.getChildren().addAll(c2, dineL);
        
        // Travel
        // circle
        StackPane travels = new StackPane();
        Circle c3 = new Circle();
        c3.setRadius(70);
        c3.setFill(Color.DARKSEAGREEN);
        // labels
        VBox travelL = new VBox();
        travelL.setAlignment(Pos.CENTER);
        travelL.setSpacing(5);
        Label travel = new Label("Travel");
        travel.setFont(Font.font("Verdana", 15));
        Label travelExp = new Label(loggedInUser.getTravelExpense() + "  " + loggedInUser.getCurrency());
//        Label travelExp = new Label("100.00 USD");
        travelExp.setFont(Font.font("Verdana", 14));
        travelExp.setAccessibleText("Your travel expense is 100 USD");
        travelL.getChildren().addAll(travel, travelExp);
        travels.getChildren().addAll(c3, travelL);
        
        // Bills & Utilities
        // circle
        StackPane bills = new StackPane();
        Circle c4 = new Circle();
        c4.setRadius(70);
        c4.setFill(Color.CADETBLUE);
        // labels
        VBox billsL = new VBox();
        billsL.setAlignment(Pos.CENTER);
        billsL.setSpacing(5);
        Label bill = new Label("Bills & Utilities");
        bill.setFont(Font.font("Verdana", 15));
        	Label billExp = new Label(loggedInUser.getBillsExpense() + "  " + loggedInUser.getCurrency());
//        Label billExp = new Label("100.00 USD");
        billExp.setAccessibleText("Your bills and utilities expense is 100 USD");
        billExp.setFont(Font.font("Verdana", 14));
        billsL.getChildren().addAll(bill, billExp);
        bills.getChildren().addAll(c4, billsL);

        // Miscellaneous
        // circle
        StackPane misc = new StackPane();
        Circle c5 = new Circle();
        c5.setRadius(70);
        c5.setFill(Color.PINK);
        // labels
        VBox miscL = new VBox();
        miscL.setAlignment(Pos.CENTER);
        miscL.setSpacing(5);
        Label mis = new Label("Miscellaneous");
        mis.setFont(Font.font("Verdana", 15));
        Label miscExp = new Label(loggedInUser.getMiscExpense() + "  " + loggedInUser.getCurrency());
//        Label miscExp = new Label("100.00 USD");
        miscExp.setFont(Font.font("Verdana", 14));
        miscExp.setAccessibleText("Your miscellaneous expenses are 100 USD");
        miscL.getChildren().addAll(mis, miscExp);
        misc.getChildren().addAll(c5, miscL);
        
        // add all
        expensesTracker.getChildren().addAll(shopping, dining, travels, bills, misc);
        v.getChildren().add(expensesTracker);


		// for transaction history
		// recent transactions container
		VBox recentContainer = new VBox();
		recentContainer.setStyle("-fx-background-color: #A3D492; -fx-background-radius: 10");
		recentContainer.setPadding(new Insets(10,10,10,10));
		recentContainer.setSpacing(15);
		recentContainer.setMaxSize(750, 150);
		
		// transaction table
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setMaxSize(750, 400);
		
		// title
		Label title = new Label("Recent Transactions");
		title.setLabelFor(grid);
		title.setFont(Font.font("Verdana", 18));
		title.setAlignment(Pos.TOP_CENTER);
		
		int numTransactions = loggedInUser.getMyTransactions().size();
		Label subtitle = new Label("Expense                           Category                            Sum                     Date");
		subtitle.setFont(Font.font("Verdana", 16));
		subtitle.setAlignment(Pos.TOP_LEFT);
		String myExpense = "";
		String myCategory = "";
		double mySum = 0;
		String myDate1 = "";
		for (int i=numTransactions-1; i>=0; i--) {
			myExpense = loggedInUser.getExpense(i);
			Label exp = new Label(myExpense);
			exp.setFont(Font.font("Verdana", 12));
			grid.add(exp, 0, numTransactions-i-1);

			myCategory = loggedInUser.getCategory(i);
			Label cat = new Label(myCategory);
			cat.setFont(Font.font("Verdana", 12));
			grid.add(cat, 12, numTransactions-i-1);

			mySum = loggedInUser.getSum(i);
			String convertedSum = Double.toString(mySum);
			Label s = new Label(convertedSum);
			s.setFont(Font.font("Verdana", 12));
			grid.add(s, 28, numTransactions-i-1);

			myDate1 = loggedInUser.getDate(i);
			Label d = new Label(myDate1);
			d.setFont(Font.font("Verdana", 12));
			grid.add(d, 38, numTransactions-i-1);
		}
		
		ScrollPane scroll = new ScrollPane(grid);
		scroll.setPadding(new Insets(5,5,5,5));
	    scroll.setFitToWidth(true);
	    scroll.setMinSize(750, 150);
	    
		recentContainer.getChildren().addAll(title, subtitle, scroll);
		v.getChildren().add(recentContainer);
		
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
