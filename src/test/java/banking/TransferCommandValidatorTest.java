package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandValidatorTest {
	CommandValidator commandValidator;
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void valid_withdraw_between_two_checking_accounts() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 800);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 250");
		assertTrue(actual);

	}

	@Test
	void valid_withdraw_between_two_checking_accounts_max_amount() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 200);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 400");
		assertTrue(actual);

	}

	@Test
	void valid_withdraw_over_remaining_balance() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 200);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 400");
		assertTrue(actual);

	}

	@Test
	void valid_withdraw_between_two_savings_accounts() {
		Account account = new Savings("12345678", 5.0);
		Account account2 = new Savings("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 500);
		bank.depositBalance("87654321", 750);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 1000");
		assertTrue(actual);
	}

	@Test
	void valid_withdraw_between_savings_and_checking() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Savings("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 500);
		bank.depositBalance("87654321", 750);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 400");
		assertTrue(actual);

	}

	@Test
	void invalid_withdraw_withdrawing_negative_from_checking() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 500);
		bank.depositBalance("87654321", 750);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 -5");
		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_withdrawing_too_high_from_checking() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 500);
		bank.depositBalance("87654321", 750);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 500");
		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_withdrawing_negative_from_savings() {
		Account account = new Savings("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 500);
		bank.depositBalance("87654321", 750);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 -5");
		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_withdrawing_too_high_from_savings() {
		Account account = new Savings("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 500);
		bank.depositBalance("87654321", 750);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 1100");
		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_withdrawing_multiple_times_in_savings() {
		Account account = new Savings("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 500);
		bank.depositBalance("87654321", 750);
		commandValidator.validate("transfer 12345678 87654321 200");
		boolean actual1 = commandValidator.validate("transfer 12345678 87654321 200");

		assertFalse(actual1);
	}

	@Test
	void valid_withdraw_withdrawing_multiple_times_in_savings() {
		Account account = new Savings("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 500);
		bank.depositBalance("87654321", 750);
		commandValidator.validate("transfer 12345678 87654321 200");
		commandProcessor.process("pass 1");
		boolean actual1 = commandValidator.validate("transfer 12345678 87654321 200");

		assertTrue(actual1);
	}

	@Test
	void invalid_withdraw_withdrawing_with_cd_account() {
		Account account = new Cd("12345678", 5.0, 1500);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 200");

		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_depositing_into_cd_account() {
		Account account2 = new Cd("12345678", 5.0, 1500);
		Account account = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 200");

		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_missing_arguements() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 800);
		boolean actual = commandValidator.validate("transfer 12345678 87654321");
		assertFalse(actual);

	}

	@Test
	void invalid_withdraw_typos() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 800);
		boolean actual = commandValidator.validate("trantfer 12345678 87654321 300");
		assertFalse(actual);

	}

	@Test
	void valid_withdraw_with_different_capitalizations() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 800);
		boolean actual = commandValidator.validate("tranSFeR 12345678 87654321 300");
		assertTrue(actual);

	}

	@Test
	void valid_withdraw_with_leading_spaces() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 800);
		boolean actual = commandValidator.validate("transfer 12345678 87654321 300    ");
		assertTrue(actual);

	}

	@Test
	void invalid_withdraw_with_extra_spaces_between() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 200);
		boolean actual = commandValidator.validate("transfer   12345678 87654321    400");
		assertFalse(actual);

	}

}
