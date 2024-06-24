package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CdTest {
	public static final String ID = "12345678";
	public static final double APR = 5.4;
	public static final double BALANCE = 500.50;

	@Test
	public void create_cd_account_with_set_balance() {
		Account accountCd = new Cd(ID, APR, BALANCE);
		double actual = accountCd.getBalance();
		assertEquals(BALANCE, actual);
	}

	@Test
	public void cd_account_starts_with_0_months() {
		Account accountCd = new Cd(ID, APR, BALANCE);
		double actual = accountCd.getMonthsPassed();
		assertEquals(0, actual);
	}

	@Test
	public void get_account_type_returns_cd() {
		Account accountCd = new Cd(ID, APR, BALANCE);
		String actual = accountCd.getAccountType();
		assertEquals("cd", actual);
	}

	@Test
	public void get_can_withdraw_is_false() {
		Account accountCd = new Cd(ID, APR, BALANCE);
		boolean actual = accountCd.getCanWithdraw();
		assertFalse(actual);
	}

}
