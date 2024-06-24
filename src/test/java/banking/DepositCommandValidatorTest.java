package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void deposit_a_valid_checking_account() {
		Account account = new Checking("12345678", 5.5);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("deposit 12345678 500");

		assertTrue(actual);

	}

	@Test
	void deposit_a_valid_checking_account_max_min_values() {
		Account account = new Checking("12345678", 5.5);
		bank.addAccount(account);
		boolean actual1 = commandValidator.validate("deposit 12345678 0");
		boolean actual2 = commandValidator.validate("deposit 12345678 1000");

		assertTrue(actual1);
		assertTrue(actual2);
	}

	@Test
	void deposit_a_valid_savings_account() {
		Account account = new Savings("12345678", 5.5);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("deposit 12345678 700");

		assertTrue(actual);
	}

	@Test
	void deposit_a_valid_savings_account_of_max_min_values() {
		Account account = new Savings("12345678", 5.5);
		bank.addAccount(account);
		boolean actual1 = commandValidator.validate("deposit 12345678 0");
		boolean actual2 = commandValidator.validate("deposit 12345678 2500");

		assertTrue(actual1);
		assertTrue(actual2);
	}

	@Test
	void create_invalid_command_with_missing_arguments() {
		boolean actual = commandValidator.validate("deposit");
		boolean actual2 = commandValidator.validate("deposit 12345678");
		boolean actual3 = commandValidator.validate("deposit 12345678 5000 50");
		assertFalse(actual);
		assertFalse(actual2);
		assertFalse(actual3);
	}

	@Test
	void create_invalid_command_with_typo_counting_capitalization() {
		boolean actual = commandValidator.validate("desdasa 12345678 2500");
		boolean actual2 = commandValidator.validate("Deposit 12345678 2500");

		assertFalse(actual);
		assertFalse(actual2);

	}

	@Test
	void create_invalid_command_with_extra_spaces() {
		boolean actual = commandValidator.validate(" deposit 12345678 2500");
		boolean actual2 = commandValidator.validate("deposit  12345678 2500");

		assertFalse(actual);
		assertFalse(actual2);

	}

	@Test
	void valid_deposit_with_leading_spaces() {
		Account account = new Savings("12345678", 5.5);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("deposit 12345678 700  ");

		assertTrue(actual);
	}

	@Test
	void deposit_invalid_command_depositing_into_cd() {
		Account account = new Cd("12345678", 5.5, 5000);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("deposit 12345678 2500");
		assertFalse(actual);
	}

	@Test
	void create_invalid_command_with_invalid_id_input() {
		Account account = new Checking("12345678", 5.5);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("deposit 123456789 700");
		boolean actual2 = commandValidator.validate("deposit 1234567 700");
		boolean actual3 = commandValidator.validate("deposit -12345678 700");
		boolean actual4 = commandValidator.validate("deposit dasda 700");
		boolean actual5 = commandValidator.validate("deposit 12345678.0 700");

		assertFalse(actual);
		assertFalse(actual2);
		assertFalse(actual3);
		assertFalse(actual4);
		assertFalse(actual5);
	}

	@Test
	void deposit_invalid_command_negative_amount() {
		boolean actual = commandValidator.validate("deposit 12345678 -1");
		assertFalse(actual);

	}

	@Test
	void deposit_invalid_command_checking_amount() {
		Account account = new Checking("12345678", 5.5);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("deposit 12345678 1500");
		assertFalse(actual);

	}

	@Test
	void deposit_invalid_command_savings_amount() {
		Account account = new Savings("12345678", 5.5);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("deposit 12345678 5500");
		assertFalse(actual);

	}

	@Test
	void deposit_invalid_command_account_doesnt_exist() {
		boolean actual = commandValidator.validate("deposit 87654321 800");
		assertFalse(actual);

	}

	@Test
	void deposit_with_varying_capitalization() {
		Account account = new Savings("12345678", 5.5);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("dePOSit 12345678 700");

		assertTrue(actual);
	}

}
