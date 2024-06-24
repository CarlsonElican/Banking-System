package banking;

import java.util.ArrayList;
import java.util.List;

public class PassTimeCommandProcessor {
	public Bank bank;

	public PassTimeCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		String[] inputs = command.toLowerCase().split(" ");
		for (int i = 0; i < Integer.parseInt(inputs[1]); i++) {
			processOneMonth();
		}
	}

	public void processOneMonth() {
		List<String> accountsToRemove = new ArrayList<>();
		for (Account account : new ArrayList<>(bank.getAccounts().values())) {
			processAccountForMonth(account, accountsToRemove);
		}
		for (String id : accountsToRemove) {
			bank.deleteAccount(id);
		}
	}

	public void processAccountForMonth(Account account, List<String> accountsToRemove) {
		if (account.getBalance() == 0) {
			accountsToRemove.add(account.getId());
			return;
		}

		applyMonthlyFee(account);
		applyAPR(account);
		updateWithdrawalCapability(account);
	}

	public void applyMonthlyFee(Account account) {
		if (account.getBalance() > 0 && account.getBalance() < 100) {
			account.withdraw(25);
		}
	}

	public void applyAPR(Account account) {
		if (account.getAccountType().equals("cd")) {
			applyCdAPR(account);
		} else {
			account.addMonths(1);
			double aprCalc = calculateApr(account);
			account.deposit(aprCalc);
		}
	}

	public void applyCdAPR(Account account) {
		account.addMonths(1);
		for (int j = 0; j < 4; j++) {
			double aprCalc = calculateApr(account);
			account.deposit(aprCalc);
		}
	}

	public double calculateApr(Account account) {
		return account.getBalance() * (account.getApr() / 100) / 12;
	}

	public void updateWithdrawalCapability(Account account) {
		if (account.getAccountType().equals("savings")) {
			account.setCanWithdraw(true);
		}

		if (account.getAccountType().equals("cd") && account.getMonthsPassed() >= 12) {
			account.setCanWithdraw(true);
		}
	}

}
