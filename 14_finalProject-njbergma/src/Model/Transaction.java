package Model;

/**
*
* Transaction: Object for the User's transactions.
*
* @author Nicole Bergman
* ITP 368, Spring 2018
* Final Project
* njbergma@usc.edu
*/

import Model.Category;

public class Transaction {
	
	private String expense;			// description of transaction
	private Category category;		
	private double sum;				// dollar amount of transaction
	private String date;				// date of transaction (year-month-date)
	private String currency;			// currency type (USD, GBP, etc.)
	
	public Transaction(String expense, Category category, double sum, String date, String currency) {
		this.expense = expense;
		this.category = category;
		this.sum = sum;
		this.date = date;
		this.currency = currency;
	}

	/**
	 * @return the expense
	 */
	public String getExpense() {
		return expense;
	}

	/**
	 * @param expense the expense to set
	 */
	public void setExpense(String expense) {
		this.expense = expense;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the sum
	 */
	public double getSum() {
		return sum;
	}

	/**
	 * @param sum the sum to set
	 */
	public void setSum(double sum) {
		this.sum = sum;
	}

	/**
	 * @return the transactionDate
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
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
		return "Transaction [expense=" + expense + ", category=" + category + ", sum=" + sum + ", date=" + date
				+ ", currency=" + currency + "]";
	}	
	
	


}
