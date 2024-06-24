package banking;

public class CommandProcessor {
	public Bank bank;
	public Account account;
	public CreateCommandProcessor createCommandProcessor;
	public DepositCommandProcessor depositCommandProcessor;
	public PassTimeCommandProcessor passTimeCommandProcessor;
	public WithdrawCommandProcessor withdrawCommandProcessor;
	public TransferCommandProcessor transferCommandProcessor;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
		this.createCommandProcessor = new CreateCommandProcessor(bank);
		this.depositCommandProcessor = new DepositCommandProcessor(bank);
		this.passTimeCommandProcessor = new PassTimeCommandProcessor(bank);
		this.withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
		this.transferCommandProcessor = new TransferCommandProcessor(bank);
	}

	public void process(String command) {
		String commandCase = command.toLowerCase();
		String[] inputs = commandCase.split(" ");
		if (inputs.length == 0) {
			return;
		}

		switch (inputs[0]) {
		case "create":
			createCommandProcessor.process(command);
			break;
		case "deposit":
			depositCommandProcessor.process(command);
			break;
		case "pass":
			passTimeCommandProcessor.process(command);
			break;
		case "withdraw":
			withdrawCommandProcessor.process(command);
			break;
		case "transfer":
			transferCommandProcessor.process(command);
			break;
		default:
			break;
		}
	}
}
