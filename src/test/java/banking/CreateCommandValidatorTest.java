package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void creating_a_valid_checking_account() {
		boolean actual = commandValidator.validate("create checking 12345678 1.5");

		assertTrue(actual);
	}

	@Test
	void creating_a_valid_savings_account() {
		boolean actual = commandValidator.validate("create savings 12345678 1.5");

		assertTrue(actual);

	}

	@Test
	void creating_savings_account_with_edges_of_apr() {
		boolean actual1 = commandValidator.validate("create savings 12345678 0");
		boolean actual2 = commandValidator.validate("create savings 12345678 10");

		assertTrue(actual1);
		assertTrue(actual2);
	}

	@Test
	void create_a_valid_cd_account() {
		boolean actual = commandValidator.validate("create cd 12345678 1.5 2000");

		assertTrue(actual);

	}

	@Test
	void create_a_cd_account_with_bordering_balance() {
		boolean actual1 = commandValidator.validate("create cd 12345678 1.5 1000");
		boolean actual2 = commandValidator.validate("create cd 12345678 1.5 10000");

		assertTrue(actual1);
		assertTrue(actual2);
	}

	@Test
	void create_invalid_command_with_missing_arguments() {
		boolean actual = commandValidator.validate("create");
		boolean actual2 = commandValidator.validate("create checking");
		boolean actual3 = commandValidator.validate("create checking 12345678");
		boolean actual4 = commandValidator.validate("create cd 12345678 1.5");
		boolean actual5 = commandValidator.validate("create 12345678 1.5");
		boolean actual6 = commandValidator.validate("checking 12345678 1.6");
		assertFalse(actual);
		assertFalse(actual2);
		assertFalse(actual3);
		assertFalse(actual4);
		assertFalse(actual5);
		assertFalse(actual6);
	}

	@Test
	void create_invalid_command_with_typos() {
		boolean actual = commandValidator.validate("cbleate checking 12345678 1.5");
		boolean actual2 = commandValidator.validate("create checdking 12345678 1.5");

		assertFalse(actual);
		assertFalse(actual2);
	}

	@Test
	void create_valid_command_with_differening_capitalizations() {
		boolean actual = commandValidator.validate("creAtE CheckinG 12345678 1.5");

		assertTrue(actual);
	}

	@Test
	void create_invalid_command_with_extra_spaces() {
		boolean actual = commandValidator.validate(" create checking 12345678 1.5");
		boolean actual2 = commandValidator.validate("create  checking 12345678 1.5");

		assertFalse(actual);
		assertFalse(actual2);
	}

	@Test
	void create_invalid_command_that_adds_balance_to_checking_savings() {
		boolean actual = commandValidator.validate("create checking 12345678 1.5 5000");
		boolean actual2 = commandValidator.validate("create savings 12345678 1.5 5000");

		assertFalse(actual);
		assertFalse(actual2);
	}

	@Test
	void create_invalid_command_with_invalid_id_input() {
		boolean actual = commandValidator.validate("create checking 123456789 1.5");
		boolean actual2 = commandValidator.validate("create checking 1234567 1.5");
		boolean actual3 = commandValidator.validate("create checking -12345678 1.5");
		boolean actual4 = commandValidator.validate("create checking adsd 1.5");
		boolean actual5 = commandValidator.validate("create checking 12345678.0 1.5");

		assertFalse(actual);
		assertFalse(actual2);
		assertFalse(actual3);
		assertFalse(actual4);
		assertFalse(actual5);
	}

	@Test
	void create_invalid_command_id_already_exists() {
		Account account = new Checking("12345678", 1.5);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("create checking 12345678 1.5");
		assertFalse(actual);

	}

	@Test
	void create_invalid_command_invalid_apr() {
		boolean actual = commandValidator.validate("create checking 12345678 -5");
		boolean actual2 = commandValidator.validate("create checking 12345678 44");

		assertFalse(actual);
		assertFalse(actual2);
	}

	@Test
	void create_invalid_command_invalid_balance_for_cd() {
		boolean actual = commandValidator.validate("create cd 12345678 5 100");
		boolean actual2 = commandValidator.validate("create cd 12345678 5 100000");

		assertFalse(actual);
		assertFalse(actual2);

	}
}