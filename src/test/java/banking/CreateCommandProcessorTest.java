package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandProcessorTest {
	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void create_a_valid_checking_account() {
		commandProcessor.process("Create checking 12345678 1.0");
		assertEquals(1.0, bank.getAccounts().get("12345678").getApr());
	}

	@Test
	void create_a_valid_savings_account() {
		commandProcessor.process("create savings 12345678 5.0");
		assertEquals(5.0, bank.getAccounts().get("12345678").getApr());
	}

	@Test
	void create_a_valid_cd_account() {
		commandProcessor.process("create cd 12345678 5.0 2000");
		assertEquals(2000, bank.getAccounts().get("12345678").getBalance());
	}

	@Test
	void create_ignoring_capitalization() {
		commandProcessor.process("CreatE chEckiNg 12345678 1.0");
		assertEquals(1.0, bank.getAccounts().get("12345678").getApr());
	}

	@Test
	void create_a_valid_checking_account_ignoring_extra_leading_spaces() {
		commandProcessor.process("Create checking 12345678 1.0    ");
		assertEquals(1.0, bank.getAccounts().get("12345678").getApr());
	}
}