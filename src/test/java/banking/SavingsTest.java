package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SavingsTest {

	public static final String ID = "12345678";
	public static final double APR = 5.4;

	@Test
	public void create_savings_account_with_empty_balance() {
		Account accountSavings = new Savings(ID, APR);
		double actual = accountSavings.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void withdraw_account_starts_with_0_months() {
		Account account = new Savings(ID, APR);
		double actual = account.getMonthsPassed();
		assertEquals(0, actual);
	}

	@Test
	public void get_account_type_returns_withdraw() {
		Account account = new Savings(ID, APR);
		String actual = account.getAccountType();
		assertEquals("savings", actual);
	}

	@Test
	public void get_can_withdraw_is_true() {
		Account account = new Savings(ID, APR);
		boolean actual = account.getCanWithdraw();
		assertTrue(actual);
	}
}
