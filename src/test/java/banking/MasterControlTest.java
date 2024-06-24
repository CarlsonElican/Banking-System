package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	MasterControl masterControl;
	List<String> input;

	private void printCommands(List<String> actual) {
		System.out.println("Output contents:");
		for (String line : actual) {
			System.out.println(line);
		}
	}

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage(),
				bank);

	}

	@Test
	void returns_single_account_data() {
		input.add("create savings 12345678 0.6");
		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("Savings 12345678 0.00 0.60", actual.get(0));
	}

	@Test
	void returns_single_account_data_with_commands() {
		input.add("create savings 12345678 0.6");
		input.add("Deposit 12345678 800");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 800.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 800", actual.get(1));
	}

	@Test
	void returns_single_account_data_with_capitalization_jumps() {
		input.add("create savings 12345678 0.6");
		input.add("DEPOSIT 12345678 800");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 800.00 0.60", actual.get(0));
		assertEquals("DEPOSIT 12345678 800", actual.get(1));
	}

	@Test
	void returns_single_account_data_with_proper_decimals() {
		input.add("create savings 12345678 0.626411");
		List<String> actual = masterControl.start(input);
		assertEquals(1, actual.size());
		assertEquals("Savings 12345678 0.00 0.62", actual.get(0));
	}

	@Test
	void returns_single_account_data_with_proper_decimals_besides_history() {
		input.add("create savings 12345678 0.626411");
		input.add("deposit 12345678 800.412441");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 800.41 0.62", actual.get(0));
		assertEquals("deposit 12345678 800.412441", actual.get(1));
	}

	@Test
	void returns_multiple_account_data_with_commands() {
		input.add("create savings 12345678 0.6");
		input.add("create checking 87654321 0.2");
		input.add("deposit 12345678 800");
		input.add("deposit 87654321 500");

		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 800.00 0.60", actual.get(0));
		assertEquals("deposit 12345678 800", actual.get(1));
		assertEquals("Checking 87654321 500.00 0.20", actual.get(2));
		assertEquals("deposit 87654321 500", actual.get(3));

	}

	@Test
	void returns_two_transfers_for_both_accounts_when_called() {
		input.add("create savings 12345678 0.6");
		input.add("create checking 87654321 0.2");
		input.add("transfer 12345678 87654321 0");

		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 0.00 0.60", actual.get(0));
		assertEquals("transfer 12345678 87654321 0", actual.get(1));
		assertEquals("Checking 87654321 0.00 0.20", actual.get(2));
		assertEquals("transfer 12345678 87654321 0", actual.get(3));

	}

	@Test
	void returns_multiple_account_data_with_commands_and_invalid_command() {
		input.add("create savings 12345678 0.6");
		input.add("create savingss 55555555 0.6");
		input.add("create checking 87654321 0.2");
		input.add("deposit 12345678 800");
		input.add("deposit 87654321 500");

		List<String> actual = masterControl.start(input);
		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 800.00 0.60", actual.get(0));
		assertEquals("deposit 12345678 800", actual.get(1));
		assertEquals("Checking 87654321 500.00 0.20", actual.get(2));
		assertEquals("deposit 87654321 500", actual.get(3));
		assertEquals("create savingss 55555555 0.6", actual.get(4));
	}

	@Test
	void returns_proper_account_data_when_closed_account() {
		input.add("create savings 12345678 0.6");
		input.add("create checking 87654321 0.2");
		input.add("deposit 12345678 800");
		input.add("deposit 87654321 200");
		input.add("withdraw 87654321 400");
		input.add("pass 1");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 800.40 0.60", actual.get(0));
		assertEquals("deposit 12345678 800", actual.get(1));
	}

	@Test
	void returns_account_but_not_pass_times() {
		input.add("create savings 12345678 0.6");
		input.add("deposit 12345678 800");
		input.add("pass 12");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 804.81 0.60", actual.get(0));
		assertEquals("deposit 12345678 800", actual.get(1));
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

}
