package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);

	}

	@Test
	void deposit_a_valid_empty_checking_account() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		commandProcessor.process("deposit 12345678 100");
		assertEquals(100, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void deposit_max_for_checking_account() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		commandProcessor.process("deposit 12345678 1000");
		assertEquals(1000, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void depositing_zero() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		commandProcessor.process("deposit 12345678 0");
		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void deposit_a_valid_empty_savings_account() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		commandProcessor.process("deposit 12345678 100");
		assertEquals(100, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void deposit_max_for_savings_account() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		commandProcessor.process("deposit 12345678 2500");
		assertEquals(2500, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void deposit_a_valid_savings_account_with_100_starting_balance() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 100);
		commandProcessor.process("deposit 12345678 100");
		assertEquals(200, bank.getAccounts().get("12345678").getBalance());
	}

}
