package banking;

public class WithdrawCommandProcessor {
	public Bank bank;
	public Account account;

	public WithdrawCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		ProcessorUtility.processTransaction(bank, command, false);

	}
}
