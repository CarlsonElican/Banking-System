package banking;

public class TransferCommandProcessor {
	public Bank bank;
	public Account account;

	public TransferCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		String[] inputs = command.split(" ");
		double transferAmount = Double.parseDouble(inputs[3]);
		double transferFrom = bank.getAccounts().get(inputs[1]).getBalance();

		if (transferFrom < transferAmount) {
			transferAmount = transferFrom;
		}

		bank.withdrawBalance(inputs[1], transferAmount);
		bank.depositBalance(inputs[2], transferAmount);
		bank.getAccounts().get(inputs[1]).addTransaction(command);
		bank.getAccounts().get(inputs[2]).addTransaction(command);

	}
}
