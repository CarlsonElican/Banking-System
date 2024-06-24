package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CheckingTest {

	public static final String ID = "12345678";
	public static final double APR = 5.4;

	@Test
	public void create_checking_account_with_empty_balance() {
		Account account = new Checking(ID, APR);
		double actual = account.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void withdraw_account_starts_with_0_months() {
		Account account = new Checking(ID, APR);
		double actual = account.getMonthsPassed();
		assertEquals(0, actual);
	}

	@Test
	public void get_account_type_returns_withdraw() {
		Account account = new Checking(ID, APR);
		String actual = account.getAccountType();
		assertEquals("checking", actual);
	}

	@Test
	public void get_can_withdraw_is_true() {
		Account account = new Checking(ID, APR);
		boolean actual = account.getCanWithdraw();
		assertTrue(actual);
	}
}
