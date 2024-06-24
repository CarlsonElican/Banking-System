package banking;

public class DepositCommandProcessor {
	public Bank bank;

	public DepositCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		ProcessorUtility.processTransaction(bank, command, true);

	}
}