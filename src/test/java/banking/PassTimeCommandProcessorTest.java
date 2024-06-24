package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void pass_valid_time_to_close_empty_account() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		commandProcessor.process("pass 20");
		assertNull(bank.getAccounts().get("12345678"));
	}

	@Test
	void pass_valid_time_to_deduct_25_from_below_100_accounts() {
		Account account = new Checking("12345678", 5.0);
		bank.addAccount(account);
		bank.depositBalance("12345678", 75);
		commandProcessor.process("pass 2");
		double actual = bank.getAccounts().get("12345678").getBalance();
		assertEquals(25.313368055555557, actual);
	}

	@Test
	void pass_valid_time_correct_calculation_amounts_for_cd_account() {
		Account account = new Cd("12345678", 5.0, 500);
		bank.addAccount(account);
		commandProcessor.process("pass 7");
		double actual = bank.getAccounts().get("12345678").getMonthsPassed();
		assertEquals(7, actual);
	}

	@Test
	void pass_valid_time_for_multiple_accounts() {
		Account account = new Cd("12345678", 5.0, 1000);
		Account account1 = new Savings("87654321", 5.0);
		bank.addAccount(account);
		bank.addAccount(account1);
		bank.depositBalance("87654321", 500);
		commandProcessor.process("pass 5");
		double actual = bank.getAccounts().get("12345678").getBalance();
		double actual1 = bank.getAccounts().get("87654321").getBalance();
		assertEquals(1086.7158897203421, actual);
		assertEquals(510.5038346661854, actual1);
	}

	@Test
	void turn_can_withdraw_for_savings_to_true_after_pass() {
		Account account1 = new Savings("87654321", 5.0);
		bank.addAccount(account1);
		bank.depositBalance("87654321", 700);
		bank.withdrawBalance("87654321", 500);
		commandProcessor.process("pass 1");
		boolean actual = bank.getAccounts().get("87654321").getCanWithdraw();
		assertTrue(actual);
	}

}
