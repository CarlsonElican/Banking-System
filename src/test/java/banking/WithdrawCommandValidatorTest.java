package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandValidatorTest {
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
	void withdraw_from_valid_checking_account() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		boolean actual = commandValidator.validate("Withdraw 12345678 400");
		assertTrue(actual);
	}

	@Test
	void withdraw_from_valid_savings_account() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		boolean actual = commandValidator.validate("Withdraw 12345678 500");
		assertTrue(actual);
	}

	@Test
	void invalid_multiple_withdraw_from_savings_account() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		boolean actual = commandValidator.validate("Withdraw 12345678 600");
		boolean actual1 = commandValidator.validate("Withdraw 12345678 200");
		assertFalse(actual1);
	}

	@Test
	void valid_multiple_withdraw_from_savings_account_with_pass_time() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		commandValidator.validate("Withdraw 12345678 600");
		commandProcessor.process("pass 1");
		boolean actual1 = commandValidator.validate("Withdraw 12345678 200");
		assertTrue(actual1);
	}

	@Test
	void invalid_withdraw_on_cd_account_before_12_months() {
		Account account = new Cd("12345678", 5.0, 2000);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("withdraw 12345678 500");
		assertFalse(actual);
	}

	@Test
	void valid_withdraw_on_cd_account_after_12_months() {
		Account account = new Cd("12345678", 5.0, 2000);
		bank.addAccount(account);
		commandProcessor.process("pass 15");
		boolean actual = commandValidator.validate("withdraw 12345678 4000");
		assertTrue(actual);
	}

	@Test
	void invalid_withdraw_on_cd_account_after_6_months() {
		Account account = new Cd("12345678", 5.0, 2000);
		bank.addAccount(account);
		commandProcessor.process("pass 5");
		boolean actual = commandValidator.validate("withdraw 12345678 4000");
		assertFalse(actual);
	}

	@Test
	void valid_withdraw_on_cd_account_after_12_months_exact_amount() {
		Account account = new Cd("12345678", 5.0, 2000);
		bank.addAccount(account);
		commandProcessor.process("pass 15");
		boolean actual = commandValidator.validate("withdraw 12345678 2566.717357007026");
		assertTrue(actual);
	}

	@Test
	void invalid_withdraw_mispelling() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		boolean actual = commandValidator.validate("Withdrdaw 12345678 400");
		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_negative_balance() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		boolean actual = commandValidator.validate("Withdraw 12345678 -5");
		assertFalse(actual);
	}

	@Test
	void invalid_withdraw_less_than_balance_on_cd_account() {
		Account account = new Cd("12345678", 5.0, 2000);
		bank.addAccount(account);
		commandProcessor.process("pass 15");
		boolean actual = commandValidator.validate("withdraw 12345678 1000");
		assertFalse(actual);
	}

	@Test
	void valid_withdraw_of_zero_on_checking_account() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		boolean actual = commandValidator.validate("Withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	void invalid_spaces_on_withdraw() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		boolean actual = commandValidator.validate(" Withdraw 12345678 0");
		boolean actual1 = commandValidator.validate("Withdraw  12345678 0");
		assertFalse(actual);
		assertFalse(actual1);

	}

	@Test
	void valid_extra_leading_spaces_for_withdraw() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		boolean actual = commandValidator.validate("Withdraw 12345678 500   ");
		assertTrue(actual);
	}

	@Test
	void valid_ignore_capitalizations_for_withdraw() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		boolean actual = commandValidator.validate("wItHdrAW 12345678 500");
		assertTrue(actual);

	}

	@Test
	void invalid_withdraw_missing_arguements() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		boolean actual = commandValidator.validate("withdraw 12345678");
		assertFalse(actual);

	}

	@Test
	void invalid_withdraw_extra_arguements() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		boolean actual = commandValidator.validate("withdraw 12345678 300 500");
		assertFalse(actual);
	}

}
