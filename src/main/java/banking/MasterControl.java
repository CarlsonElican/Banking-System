package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MasterControl {
	public Bank bank;
	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private CommandStorage commandStorage;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage, Bank bank) {
		this.bank = bank;
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	private String capitalizeFirst(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.process(command);
			} else {
				commandStorage.addCommand(command);
			}
		}
		return totalOutputs();
	}

	public List<String> totalOutputs() {
		List<String> outputs = new ArrayList<>();
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);

		Map<String, Account> accounts = bank.getAccounts();
		for (Account account : accounts.values()) {
			String accountDetails = String.format("%s %s %s %s", capitalizeFirst(account.getAccountType()),
					account.getId(), decimalFormat.format(account.getBalance()),
					decimalFormat.format(account.getApr()));
			outputs.add(accountDetails);
			outputs.addAll(account.getTransactions());
		}
		outputs.addAll(commandStorage.getCommands());
		return outputs;
	}

}
