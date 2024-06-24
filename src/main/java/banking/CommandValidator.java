package banking;

public class CommandValidator {
	public Bank bank;
	public CreateCommandValidator createCommandValidator;
	public DepositCommandValidator depositCommandValidator;
	public PassTimeCommandValidator passTimeCommandValidator;
	public WithdrawCommandValidator withdrawCommandValidator;
	public TransferCommandValidator transferCommandValidator;

	public CommandValidator(Bank bank) {
		this.bank = bank;
		this.createCommandValidator = new CreateCommandValidator(bank);
		this.depositCommandValidator = new DepositCommandValidator(bank);
		this.passTimeCommandValidator = new PassTimeCommandValidator();
		this.withdrawCommandValidator = new WithdrawCommandValidator(bank);
		this.transferCommandValidator = new TransferCommandValidator(bank);
	}

	public boolean validate(String command) {
		String commandCase = command.toLowerCase();
		String[] inputs = commandCase.split(" ");
		if (inputs.length == 0) {
			return false;
		}

		switch (inputs[0]) {
		case "create":
			return createCommandValidator.validate(commandCase);
		case "deposit":
			return depositCommandValidator.validate(commandCase);
		case "pass":
			return passTimeCommandValidator.validate(commandCase);
		case "withdraw":
			return withdrawCommandValidator.validate(commandCase);
		case "transfer":
			return transferCommandValidator.validate(commandCase);
		default:
			return false;
		}
	}
}
