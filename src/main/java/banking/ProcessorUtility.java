package banking;

public class ProcessorUtility {
	public Bank bank;

	public static void processTransaction(Bank bank, String command, boolean isDeposit) {
		String[] inputs = command.split(" ");
		String accountId = inputs[1];
		double amount = Double.parseDouble(inputs[2]);

		if (isDeposit) {
			bank.depositBalance(accountId, amount);
		} else {
			bank.withdrawBalance(accountId, amount);
		}

		bank.getAccounts().get(accountId).addTransaction(command);
	}
}
