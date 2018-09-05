package Controller;

/**
*
* Parser: Main controller for reading my json file, parsing it, and then presenting it on the UI.
* 	Also rewrites to the json file with the user edits the GUI.
*
* @author Nicole Bergman
* ITP 368, Spring 2018
* Final Project
* njbergma@usc.edu
*/

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.Category;
import Model.Transaction;
import Model.User;

public class Parser {
	
	private final String filePath = "users.json";
	User newUser;
	
	private HashMap<String, User> usersMap = new HashMap<String, User>();
	
	public Parser() {
		parseData();
	}
	
	public void parseData() {
		try {
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			long numUsers = (long) jsonObject.get("numusers");
			
			JSONArray myUsers = (JSONArray) jsonObject.get("users");
			Iterator i = myUsers.iterator();
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				String user = (String) innerObj.get("username");
				String pass = (String) innerObj.get("password");
				double bal = (double) innerObj.get("balance");
				String profile = (String) innerObj.get("profilePic");
				String curr = (String) innerObj.get("currency");
				
				// build users
				newUser = new User(user, pass);
				newUser.setBalance(bal);
				newUser.setProfilePic(profile);
				newUser.setCurrency(curr);
				usersMap.put(user, newUser);	
				
				// add transactions
				JSONArray myTransactions = (JSONArray) innerObj.get("transactions");
				Iterator j = myTransactions.iterator();
				while (j.hasNext()) {
					JSONObject innerInnerObj = (JSONObject) j.next();
					String exp = (String) innerInnerObj.get("expense");
					double sum = (double) innerInnerObj.get("sum");
					String date = (String) innerInnerObj.get("date");
					String cat = (String) innerInnerObj.get("category");
					String currency = (String) innerInnerObj.get("currency");
					ArrayList<Transaction> transactions = new ArrayList<Transaction>();
					transactions = newUser.getMyTransactions();
					
					Category category = null;
					if (cat.equals("Category.SHOPPING")) {
						category = Category.SHOPPING;
					} 
					else if (cat.equals("Category.TRAVEL")) {
						category = Category.TRAVEL;
					}
					else if (cat.equals("Category.DINING")) {
						category = Category.DINING;
					}
					else if (cat.equals("Category.BILLS")) {
						category = Category.BILLS;
					}
					else if (cat.equals("Category.MISC")) {
						category = Category.MISC;
					}
					else {
						category = Category.SALARY;
					}
					
					transactions.add(new Transaction(exp, category, sum, date, currency));
//					System.out.println(newUser.toString());
				}
				
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

	}
	
	public void writeDepositToJson(double bal, String username, Transaction t) {
		JSONObject jsonObject = new JSONObject();
		try {

			// read file
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray myUsers = (JSONArray) jsonObject.get("users");

			Iterator i = myUsers.iterator();
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				String user = (String) innerObj.get("username");
				if (user.equalsIgnoreCase(username)) {
					// for updating balance
					innerObj.put("balance", bal);

					// for adding transaction to list of transactions
					JSONArray myTransactions = (JSONArray) innerObj.get("transactions");
					JSONObject newObject = new JSONObject();

					newObject.put("expense", t.getExpense());
					newObject.put("sum", t.getSum());
					newObject.put("category", "Category.SALARY");
					newObject.put("date", t.getDate());
					newObject.put("currency", t.getCurrency());
					myTransactions.add(newObject);
					innerObj.put("transactions", myTransactions);

				}
			}

			// write to file 
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("jsonObject: " + jsonObject);
	}
	
	public void writeTransactionToJson(double bal, String username, Transaction t) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			// read file
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray myUsers = (JSONArray) jsonObject.get("users");
		
			Iterator i = myUsers.iterator();
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				String user = (String) innerObj.get("username");
				if (user.equalsIgnoreCase(username)) {
					// for updating balance
					innerObj.put("balance", bal);

					// for adding transaction to list of transactions
					JSONArray myTransactions = (JSONArray) innerObj.get("transactions");
					JSONObject newObject = new JSONObject();

					Category cat = t.getCategory();
					String category = "";
					if (cat.equals(Category.SHOPPING)) {
						category = "Category.SHOPPING";
					} 
					else if (cat.equals(Category.TRAVEL)) {
						category = "Category.TRAVEL";
					}
					else if (cat.equals(Category.DINING)) {
						category = "Category.DINING";
					}
					else if (cat.equals(Category.BILLS)) {
						category = "Category.BILLS";
					}
					else if (cat.equals(Category.MISC)) {
						category = "Category.MISC";
					}

					newObject.put("expense", t.getExpense());
					newObject.put("sum", t.getSum());
					newObject.put("category", category);
					newObject.put("date", t.getDate());
					newObject.put("currency", t.getCurrency());
					myTransactions.add(newObject);
					innerObj.put("transactions", myTransactions);
				}
			}

			// write to file 
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("jsonObject: " + jsonObject);
		
	}
	
	public void writeUserToJson(User newUser) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			// read file
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray myUsers = (JSONArray) jsonObject.get("users");

			// for adding user to list of users
			JSONObject newObject = new JSONObject();

			newObject.put("username", newUser.getUsername());
			newObject.put("password", newUser.getPassword());
			newObject.put("balance", newUser.getBalance());
			newObject.put("profilePic", newUser.getProfilePic());
			newObject.put("currency", newUser.getCurrency());
			newObject.put("transactions", new JSONArray());

			myUsers.add(newObject);
			jsonObject.put("users", myUsers);
			
			Iterator i = myUsers.iterator();
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				String user = (String) innerObj.get("username");
				if (user.equalsIgnoreCase(newUser.getUsername())) {
					
					// for adding user's transactions
					JSONArray myTransactions = (JSONArray) innerObj.get("transactions");
					JSONObject transactions = new JSONObject();
					
					transactions.put("expense", "Deposit");
					transactions.put("sum", newUser.getBalance());
					transactions.put("category", "Category.SALARY");
					String date = new SimpleDateFormat("yyyy-dd-MM").format(new Date());
					transactions.put("date", date);
					transactions.put("currency", newUser.getCurrency());
					myTransactions.add(transactions);
					innerObj.put("transactions", myTransactions);
				}
			}
			

			// write to file 
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	public void editTransactionForJson(User loggedInUser) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			// read file
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray myUsers = (JSONArray) jsonObject.get("users");
		
			Iterator i = myUsers.iterator();
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				String user = (String) innerObj.get("username");
				if (user.equalsIgnoreCase(loggedInUser.getUsername())) {
					// for updating balance
					innerObj.put("balance", loggedInUser.getBalance());
					innerObj.put("currency", loggedInUser.getCurrency());
					
					JSONArray myTransactions = (JSONArray) innerObj.get("transactions");
					Iterator j = myTransactions.iterator();
					while (j.hasNext()) {
						JSONObject innerInnerObj = (JSONObject) j.next();
					
						ArrayList<Transaction> transactions = loggedInUser.getMyTransactions();
						for (Transaction t : transactions) {
							String expense = (String) innerInnerObj.get("expense");
							if (expense.equalsIgnoreCase(t.getExpense())) {
								// for updating transactions
								innerInnerObj.put("sum", t.getSum());
								innerInnerObj.put("currency", t.getCurrency());
							}
						}
					}
				}
			}

			// write to file 
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void editPasswordForJson(User loggedInUser) {
		JSONObject jsonObject = new JSONObject();
		try {

			// read file
			FileReader reader = new FileReader(filePath);
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray myUsers = (JSONArray) jsonObject.get("users");

			Iterator i = myUsers.iterator();
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				String user = (String) innerObj.get("username");
				if (user.equalsIgnoreCase(loggedInUser.getUsername())) {
					// for updating balance
					innerObj.put("password", loggedInUser.getPassword());

				}
			}

			// write to file 
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(jsonObject.toJSONString());
			fileWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the usersMap
	 */
	public HashMap<String, User> getUsersMap() {
		return usersMap;
	}

	

	

	

}