package banking;

public class CreateCommandValidator {
	public Bank bank;

	public CreateCommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] inputs = command.split(" ");
		return isValidCommand(inputs) && hasValidFields(inputs);
	}

	public boolean isValidCommand(String[] inputs) {
		switch (inputs.length) {
		case 4:
			return (inputs[1].equals("checking") || inputs[1].equals("savings"))
					&& ValidatorUtility.isValidID(inputs[2], bank, false) && isValidAPR(inputs[3]);
		case 5:
			return inputs[1].equals("cd") && ValidatorUtility.isValidID(inputs[2], bank, false) && isValidAPR(inputs[3])
					&& isValidBalance(inputs[4]);
		default:
			return false;
		}
	}

	public boolean hasValidFields(String[] inputs) {
		if (inputs.length >= 3 && !ValidatorUtility.isValidID(inputs[2], bank, false)) {
			return false;
		}
		if (inputs.length >= 4 && !isValidAPR(inputs[3])) {
			return false;
		}
		return inputs.length != 5 || isValidBalance(inputs[4]);
	}

	public boolean isValidAPR(String apr) {
		try {
			double aprValue = Double.parseDouble(apr);
			return aprValue >= 0 && aprValue <= 10;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isValidBalance(String balance) {
		try {
			double balanceValue = Double.parseDouble(balance);
			return balanceValue >= 1000 && balanceValue <= 10000;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}