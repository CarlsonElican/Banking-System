package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void withdraw_from_a_valid_checking_account() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		commandProcessor.process("withdraw 12345678 400");
		assertEquals(600, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void withdraw_from_a_valid_savings_account() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 2500);
		commandProcessor.process("withdraw 12345678 1000");
		assertEquals(1500, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void withdraw_multiple_times_from_a_valid_savings_account() {
		Account account = new Savings("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 2500);
		commandProcessor.process("withdraw 12345678 1000");
		commandProcessor.process("pass 1");
		commandProcessor.process("withdraw 12345678 500");
		assertEquals(1006.25, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void withdraw_from_a_cd_account() {
		Account account = new Cd("12345678", 5.0, 5000);
		bank.addAccount(account);
		commandProcessor.process("pass 12");
		commandProcessor.process("withdraw 12345678 7000");
		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void withdraw_zero_is_valid() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 1000);
		commandProcessor.process("withdraw 12345678 0");
		assertEquals(1000, bank.getAccounts().get("12345678").getBalance());
	}

}
