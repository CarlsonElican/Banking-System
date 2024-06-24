package banking;

public class DepositCommandValidator {
	public Bank bank;

	public DepositCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] inputs = command.split(" ");

		if (inputs.length >= 2 && !isValidID(inputs[1])) {
			return false;
		}

		if (inputs.length >= 3 && !isValidAmount(inputs[1], inputs[2])) {
			return false;
		}

		return inputs.length == 3;
	}

	public boolean isValidID(String id) {
		try {
			int idValue = Integer.parseInt(id);
			Account account = bank.retrieveAccount(id);
			return idValue >= 0 && account != null && !account.getAccountType().equals("cd");
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isValidAmount(String id, String amount) {
		try {
			double amountValue = Double.parseDouble(amount);
			Account account = bank.retrieveAccount(id);

			if (account.getAccountType().equals("savings")) {
				return amountValue >= 0 && amountValue <= 2500;
			}

			if (account.getAccountType().equals("checking")) {
				return amountValue >= 0 && amountValue <= 1000;
			}

			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}