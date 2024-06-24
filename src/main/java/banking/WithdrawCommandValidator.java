package banking;

public class WithdrawCommandValidator {
	public Bank bank;

	public WithdrawCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] inputs = command.split(" ");
		if (inputs.length != 3 || !ValidatorUtility.isValidID(inputs[1], bank, true)) {
			return false;
		}
		return isValidWithdraw(inputs[1], inputs[2]);
	}

	public boolean isValidWithdraw(String id, String amount) {
		try {
			double withdrawAmount = Double.parseDouble(amount);
			if (withdrawAmount < 0) {
				return false;
			}
			return isValidWithdrawRules(id, withdrawAmount);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public void updateSavings(Account account) {
		if (account.getAccountType().equals("savings")) {
			account.setCanWithdraw(false);
		}
	}

	public boolean isValidWithdrawRules(String id, double amount) {
		Account account = bank.retrieveAccount(id);
		double currentBalance = account.getBalance();
		boolean canWithdraw = account.getCanWithdraw();

		switch (account.getAccountType()) {
		case "savings":
			if (!canWithdraw || amount > 1000) {
				return false;
			}
			break;
		case "checking":
			if (amount > 400) {
				return false;
			}
			break;
		case "cd":
			if (!canWithdraw || amount < currentBalance) {
				return false;
			}
			break;
		default:
			break;
		}
		updateSavings(account);
		return true;
	}
}
