package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void valid_transfer_between_checking_accounts() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 200);
		commandProcessor.process("transfer 12345678 87654321 250");
		assertEquals(200, bank.getAccounts().get("87654321").getBalance());
		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	public void valid_transfer_between_savings_accounts() {
		Account account = new Savings("12345678", 5.0);
		Account account2 = new Savings("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 500);
		bank.depositBalance("87654321", 500);
		commandProcessor.process("transfer 12345678 87654321 300");
		assertEquals(800, bank.getAccounts().get("87654321").getBalance());
		assertEquals(200, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	public void valid_transfer_between_savings_and_checking_accounts() {
		Account account = new Checking("12345678", 5.0);
		Account account2 = new Savings("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 800);
		bank.depositBalance("87654321", 1500);
		commandProcessor.process("transfer 12345678 87654321 400");
		assertEquals(1900, bank.getAccounts().get("87654321").getBalance());
		assertEquals(400, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	public void valid_multiple_transfer_between_savings_and_checking_accounts() {
		Account account = new Savings("12345678", 5.0);
		Account account2 = new Checking("87654321", 4.0);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance("12345678", 400);
		bank.depositBalance("87654321", 300);
		commandProcessor.process("transfer 12345678 87654321 200");
		commandProcessor.process("transfer 12345678 87654321 300");
		assertEquals(700, bank.getAccounts().get("87654321").getBalance());
		assertEquals(0, bank.getAccounts().get("12345678").getBalance());
	}
}
