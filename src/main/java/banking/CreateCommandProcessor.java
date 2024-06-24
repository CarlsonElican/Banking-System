package banking;

public class CreateCommandProcessor {
	public Bank bank;
	public Account account;

	public CreateCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		String commandCase = command.toLowerCase();
		String[] inputs = commandCase.split(" ");
		double apr = Double.parseDouble((inputs[3]));
		switch (inputs[1]) {
		case "checking":
			account = new Checking(inputs[2], apr);
			bank.addAccount(account);
			break;
		case "savings":
			account = new Savings(inputs[2], apr);
			bank.addAccount(account);
			break;
		case "cd":
			double startingBalance = Double.parseDouble(inputs[4]);
			account = new Cd(inputs[2], apr, startingBalance);
			bank.addAccount(account);
			break;
		default:
			break;
		}

	}

}
