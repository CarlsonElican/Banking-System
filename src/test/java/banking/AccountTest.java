package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

	public static final String ID = "12345678";
	public static final double APR = 5.4;
	public static final double BALANCE = 500.50;

	Account account;

	@BeforeEach
	void setUp() {
		account = new Checking(ID, APR);
	}

	@Test
	public void apr_of_account_is_equal_to_given_value() {
		double actual = account.getApr();
		assertEquals(APR, actual);
	}

	@Test
	public void balance_of_account_is_equal_to_given_value() {
		account.deposit(BALANCE);
		double actual = account.getBalance();
		assertEquals(BALANCE, actual);
	}

	@Test
	public void id_of_account_is_equal_to_given_value() {
		String actual = account.getId();
		assertEquals(ID, actual);
	}

	@Test
	public void depositing_amount_into_checking_account() {
		account.deposit(500.5);
		double actual = account.getBalance();
		assertEquals(500.5, actual);
	}

	@Test
	public void depositing_amount_into_savings_account() {
		Account account2 = new Savings("12345678", 0.6);
		account2.deposit(501.5);
		double actual = account2.getBalance();
		assertEquals(501.5, actual);
	}

	@Test
	public void withdrawing_amount_from_checking_account() {
		account.deposit(750.75);
		account.withdraw(125.50);
		double actual = account.getBalance();
		assertEquals(625.25, actual);
	}

	@Test
	public void withdrawing_exact_amount_from_checking_account() {
		account.deposit(750.75);
		account.withdraw(750.75);
		double actual = account.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void withdrawing_amount_from_savings_account() {
		Account account2 = new Savings("12345678", 0.6);
		account2.deposit(750.50);
		account2.withdraw(125.50);
		double actual = account2.getBalance();
		assertEquals(625, actual);
	}

	@Test
	public void withdrawing_amount_from_checking_account_past_limit() {
		account.deposit(750.50);
		account.withdraw(925.50);
		double actual = account.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void withdrawing_amount_from_savings_account_past_limit() {
		Account account2 = new Savings("12345678", 0.6);
		account2.deposit(550.50);
		account2.withdraw(925.50);
		double actual = account2.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void withdrawing_multiple_times_on_account() {
		account.deposit(550.50);
		account.withdraw(125.50);
		account.withdraw(225.50);
		double actual = account.getBalance();
		assertEquals(199.5, actual);
	}

	@Test
	public void depositing_multiple_times_on_account() {
		account.deposit(550.50);
		account.deposit(550.50);
		double actual = account.getBalance();
		assertEquals(1101, actual);
	}

	@Test
	public void cd_starts_with_empty_months_passed() {
		Account account = new Cd(ID, APR, BALANCE);
		int actual = account.getMonthsPassed();
		assertEquals(0, actual);
	}

	@Test
	public void cd_add_months_works() {
		Account account = new Cd(ID, APR, BALANCE);
		account.addMonths(5);
		int actual = account.getMonthsPassed();
		assertEquals(5, actual);
	}

	@Test
	public void savings_starts_off_empty() {
		Account account2 = new Savings("12345678", 0.6);
		double actual = account2.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void checking_starts_off_empty() {
		double actual = account.getBalance();
		assertEquals(0, actual);
	}
}
