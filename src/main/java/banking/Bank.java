package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
	private Map<String, Account> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(Account account) {
		accounts.put(account.getId(), account);
	}

	public Account retrieveAccount(String id) {
		return accounts.get(id);
	}

	public void deleteAccount(String id) {
		if (accounts.containsKey(id)) {
			accounts.remove(id);
		}
	}

	public void depositBalance(String id, double amount) {
		Account account = accounts.get(id);
		account.deposit(amount);
	}

	public void withdrawBalance(String id, double amount) {
		Account account = accounts.get(id);
		account.withdraw(amount);
	}
}
