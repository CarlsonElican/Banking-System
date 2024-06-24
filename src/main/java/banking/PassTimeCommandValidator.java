package banking;

public class PassTimeCommandValidator {

	public boolean validate(String command) {
		String[] inputs = command.split(" ");
		if (inputs.length != 2) {
			return false;
		}
		try {
			int months = Integer.parseInt(inputs[1]);
			return months >= 1 && months <= 60;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
