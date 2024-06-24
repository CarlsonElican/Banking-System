package banking;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
	public String id;
	public double apr;
	public double balance;
	public int monthsPassed;
	public boolean canWithdraw;

	public List<String> transactions = new ArrayList<>();

	protected Account(String id, double apr) {
		this.id = id;
		this.apr = apr;
	}

	public double getApr() {
		return apr;
	}

	public double getBalance() {
		return balance;
	}

	public String getId() {
		return id;
	}

	public void deposit(double amount) {
		this.balance += amount;
	}

	public void withdraw(double amount) {
		this.balance -= amount;
		if (this.balance <= 0) {
			this.balance = 0;
		}
	}

	public abstract String getAccountType();

	public int getMonthsPassed() {
		return monthsPassed;
	}

	public boolean getCanWithdraw() {
		return canWithdraw;
	}

	public void setCanWithdraw(boolean set) {
		canWithdraw = set;
	}

	public void addMonths(int months) {
		monthsPassed += months;
	}

	public void addTransaction(String transaction) {
		transactions.add(transaction);
	}

	public List<String> getTransactions() {
		return transactions;
	}
}
