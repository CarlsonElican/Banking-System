package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	public static final String ID = "12345678";
	public static final double APR = 5.5;

	Bank bank;
	Account account;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		account = new Checking(ID, APR);

	}

	@Test
	public void bank_starts_off_empty() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	public void add_account_to_bank() {
		bank.addAccount(account);
		assertEquals(APR, bank.getAccounts().get(ID).getApr());
	}

	@Test
	public void add_two_account_to_bank() {
		Account account2 = new Checking(ID + "1", APR + 1);
		bank.addAccount(account);
		bank.addAccount(account2);
		assertEquals(APR + 1, bank.getAccounts().get(ID + "1").getApr());
	}

	@Test
	public void retrieve_account_from_bank() {
		bank.addAccount(account);
		Account actual = bank.retrieveAccount(ID);
		assertEquals(APR, actual.getApr());
	}

	@Test
	public void deposit_balance_through_id_in_bank() {
		Account account2 = new Checking(ID + "1", APR + 1);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance(ID, 100.75);
		assertEquals(100.75, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	public void deposit_multiple_times_through_id_in_bank() {
		Account account2 = new Checking(ID + "1", APR + 1);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance(ID, 100.75);
		bank.depositBalance(ID + "1", 200);

		assertEquals(100.75, bank.getAccounts().get(ID).getBalance());
		assertEquals(200, bank.getAccounts().get(ID + "1").getBalance());

	}

	@Test
	public void withdraw_balance_through_id_in_bank() {
		Account account2 = new Checking(ID + "1", APR + 1);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance(ID, 500);
		bank.withdrawBalance(ID, 200.5);
		assertEquals(299.5, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	public void deposit_twice_balance_through_id_in_bank() {
		Account account2 = new Checking(ID + "1", APR + 1);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance(ID, 100.75);
		bank.depositBalance(ID, 300);
		assertEquals(400.75, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	public void withdraw_twice_balance_through_id_in_bank() {
		Account account2 = new Checking(ID + "1", APR + 1);
		bank.addAccount(account);
		bank.addAccount(account2);
		bank.depositBalance(ID, 500);
		bank.withdrawBalance(ID, 200.5);
		bank.withdrawBalance(ID, 100.5);
		assertEquals(199, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	public void delete_existing_account() {
		bank.addAccount(account);
		bank.deleteAccount(ID);
		assertNull(bank.getAccounts().get(ID));
	}

	@Test
	public void delete_multiple_existing_account() {
		bank.addAccount(account);
		Account account2 = new Checking(ID + "1", APR + 1);
		bank.deleteAccount(ID);
		bank.deleteAccount(ID + "1");

		assertNull(bank.getAccounts().get(ID));
	}
}
