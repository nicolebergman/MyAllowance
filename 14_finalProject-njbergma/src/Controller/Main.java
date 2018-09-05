package Controller;

/**
*
* Main: The unifying class for MyAllowance. Location of my start method and main controller for my UI.
*
* @author Nicole Bergman
* ITP 368, Spring 2018
* Final Project
* njbergma@usc.edu
*/

import Model.User;
import View.Dashboard;
import View.Deposit;
import View.RegisterView;
import View.Settings;
import View.StartView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage stage;
	private StartView sv;
	private RegisterView rv;
	private Dashboard db;
	private Settings s;
	private Deposit d;
	
	User loggedInUser;
	private String username;
	boolean loggedIn = false;


	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		sv = new StartView(this);
		rv = new RegisterView(this);

		loadStartView();
		stage.show();
	}

	public void loadStartView() {
		stage.setScene(sv.getScene());
	}

	public void loadRegisterView() {
		stage.setScene(rv.getScene());
	}

	public void loadDashboard() {
		db = new Dashboard(this);
		s = new Settings(this);
		d = new Deposit(this);
		stage.setScene(db.getScene());
	}

	public void loadSettings() {
		stage.setScene(s.getScene());
	}

	public void loadDeposit() {

		stage.setScene(d.getScene());
	}

	public boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static void main(String[] args) {
		launch(args);

	}




}
