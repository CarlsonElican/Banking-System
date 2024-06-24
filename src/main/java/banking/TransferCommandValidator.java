package banking;

public class TransferCommandValidator {
	public Bank bank;

	public TransferCommandValidator(Bank bank) {
		this.bank = bank;

	}

	public boolean isValidCdAccount(String accountType1, String accountType2) {
		return !(accountType1.equals("cd") || accountType2.equals("cd"));
	}

	public boolean isValidCheckingAccount(String accountType, double balanceValue) {
		return !(accountType.equals("checking") && (balanceValue > 400 || balanceValue < 0));
	}

	public boolean isValidSavingsAccount(String accountType, double balanceValue, boolean canWithdraw) {
		return !(accountType.equals("savings") && (balanceValue > 1000 || balanceValue < 0 || !canWithdraw));
	}

	public boolean isValidTransferAmount(double amount) {
		return amount >= 0;
	}

	public boolean isValidAccountTypes(String accountType1, String accountType2, double balanceValue,
			boolean canWithdraw) {
		return isValidCdAccount(accountType1, accountType2) && isValidCheckingAccount(accountType1, balanceValue)
				&& isValidSavingsAccount(accountType1, balanceValue, canWithdraw)
				&& isValidCheckingAccount(accountType2, balanceValue)
				&& isValidSavingsAccount(accountType2, balanceValue, true);
	}

	public void updateWithdrawalStatus(String accountType1, String id1) {
		if (accountType1.equals("savings")) {
			bank.getAccounts().get(id1).setCanWithdraw(false);
		}
	}

	public boolean validate(String command) {
		String[] inputs = command.split(" ");

		if (inputs.length != 4 || !ValidatorUtility.isValidID(inputs[1], bank, true)
				|| !ValidatorUtility.isValidID(inputs[2], bank, true)) {
			return false;
		}

		double balanceValue = parseBalance(inputs[3]);
		if (balanceValue < 0) {
			return false;
		}

		boolean canWithdraw = bank.getAccounts().get(inputs[1]).getCanWithdraw();
		String accountType1 = bank.getAccounts().get(inputs[1]).getAccountType();
		String accountType2 = bank.getAccounts().get(inputs[2]).getAccountType();

		if (!isValidTransferAmount(balanceValue)
				|| !isValidAccountTypes(accountType1, accountType2, balanceValue, canWithdraw)) {
			return false;
		}

		updateWithdrawalStatus(accountType1, inputs[1]);

		return true;
	}

	public double parseBalance(String balanceStr) {
		try {
			return Double.parseDouble(balanceStr);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

}
