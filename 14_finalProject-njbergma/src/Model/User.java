package Model;

import java.util.ArrayList;

/**
*
* User: Object for all of my users.
*
* @author Nicole Bergman
* ITP 368, Spring 2018
* Final Project
* njbergma@usc.edu
*/


public class User {
	
	private String username;
	private String password;
	private double balance;
	private String profilePic;
	private String currency;
	private ArrayList<Transaction> myTransactions;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		balance = 0.0;
		profilePic = "file:/Images/default.png";
		myTransactions = new ArrayList<>();
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		balance = Math.round(balance * 100);
		balance = balance/100;
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the profilePic
	 */
	public String getProfilePic() {
		return profilePic;
	}

	/**
	 * @param profile the profilePic to set
	 */
	public void setProfilePic(String profile) {
		this.profilePic = profile;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", balance=" + balance + ", profilePic="
				+ profilePic + ", currency=" + currency + ", myTransactions=" + myTransactions + "]";
	}

	public double getShoppingExpense() {
		double sum = 0.0;
		for (Transaction t : myTransactions) {
			if (t.getCategory().equals(Category.SHOPPING)) {
				sum += t.getSum();
				sum = Math.round(sum * 100);
				sum = sum/100;
			}
		}
		return sum;
	}
	
	
	public double getTravelExpense() {
		double sum = 0.0;
		for (Transaction t : myTransactions) {
			if (t.getCategory().equals(Category.TRAVEL)) {
				sum += t.getSum();
				sum = Math.round(sum * 100);
				sum = sum/100;
			}
		}
		return sum;
	}
	
	public double getDiningExpense() {
		double sum = 0.0;
		for (Transaction t : myTransactions) {
			if (t.getCategory().equals(Category.DINING)) {
				sum += t.getSum();
				sum = Math.round(sum * 100);
				sum = sum/100;
			}
		}
		return sum;
	}
	
	public double getBillsExpense() {
		double sum = 0.0;
		for (Transaction t : myTransactions) {
			if (t.getCategory().equals(Category.BILLS)) {
				sum += t.getSum();
				sum = Math.round(sum * 100);
				sum = sum/100;
			}
		}
		return sum;
	}
	
	public double getMiscExpense() {
		double sum = 0.0;
		for (Transaction t : myTransactions) {
			if (t.getCategory().equals(Category.MISC)) {
				sum += t.getSum();
				sum = Math.round(sum * 100);
				sum = sum/100;
			}
		}
		return sum;
	}

	/**
	 * @return the myTransactions
	 */
	public ArrayList<Transaction> getMyTransactions() {
		return myTransactions;
	}

	/**
	 * @param myTransactions the myTransactions to set
	 */
	public void setMyTransactions(ArrayList<Transaction> myTransactions) {
		this.myTransactions = myTransactions;
	}

	public String getExpense(int index) {
		String expense = "";
		for (int i=0; i<index+1; i++) {
			Transaction transaction = myTransactions.get(i);
			expense = transaction.getExpense();
		}
		return expense;
	}

	public String getCategory(int index) {
		String category = "";
		for (int i=0; i<index+1; i++) {
			Transaction transaction = myTransactions.get(i);
			Category cat = transaction.getCategory();
			if (cat.equals(Category.SHOPPING)) {
				category = "Shopping";
			}
			else if (cat.equals(Category.DINING)) {
				category = "Dining";
			}
			else if (cat.equals(Category.TRAVEL)) {
				category = "Travel";
			}
			else if (cat.equals(Category.BILLS)) {
				category = "Bills & Utilities";
			}
			else if (cat.equals(Category.MISC)) {
				category = "Miscellaneous";
			}
			else {
				category = "Salary";
			}
		}
		return category;
	}

	public double getSum(int index) {
		double sum = 0;
		for (int i=0; i<index+1; i++) {
			Transaction transaction = myTransactions.get(i);
			sum = transaction.getSum();
		}
		return sum;
	}

	public String getDate(int index) {
		String date = "";
		for (int i=0; i<index+1; i++) {
			Transaction transaction = myTransactions.get(i);
			date = transaction.getDate();
		}
		return date;
	}

	public void increaseBalance(double mySum) {
		balance += mySum;	
	}
	public void decreaseBalance(double mySum) {
		balance -= mySum;	
	}
}
