package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeCommandValidatorTest {
	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void pass_a_valid_time() {
		boolean actual = commandValidator.validate("pass 1");
		boolean actual1 = commandValidator.validate("pass 30");
		boolean actual2 = commandValidator.validate("pass 60");

		assertTrue(actual);
		assertTrue(actual1);
		assertTrue(actual2);
	}

	@Test
	void pass_an_invalid_time() {
		boolean actual = commandValidator.validate("pass 0");
		boolean actual1 = commandValidator.validate("pass -5");
		boolean actual2 = commandValidator.validate("pass 61");
		boolean actual3 = commandValidator.validate("pass 30.0");
		boolean actual4 = commandValidator.validate("pass twenty");

		assertFalse(actual);
		assertFalse(actual1);
		assertFalse(actual2);
		assertFalse(actual3);
		assertFalse(actual4);
	}

	@Test
	void pass_extra_inputs() {
		boolean actual = commandValidator.validate("pass 5 bob");
		assertFalse(actual);
	}

	@Test
	void pass_missing_inputs() {
		boolean actual = commandValidator.validate("pass");
		assertFalse(actual);
	}

	@Test
	void pass_with_spaces_between() {
		boolean actual = commandValidator.validate("pass  5");
		assertFalse(actual);
	}

	@Test
	void pass_extra_leading_spaces() {
		boolean actual = commandValidator.validate("pass 5    ");
		assertTrue(actual);
	}

	@Test
	void pass_differing_capitalization() {
		boolean actual = commandValidator.validate("pASs 5");
		assertTrue(actual);
	}

	@Test
	void pass_with_typo() {
		boolean actual = commandValidator.validate("ptss 5");
		assertFalse(actual);
	}

}
