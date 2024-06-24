package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	public void add_invalid_commands_to_storage() {
		commandStorage.addCommand("create");
		List<String> commandList = commandStorage.getCommands();
		assertTrue(commandList.contains("create"));
	}

	@Test
	public void add_multiple_invalid_commands_to_storage() {
		commandStorage.addCommand("create");
		commandStorage.addCommand("bla bla");

		List<String> commandList = commandStorage.getCommands();
		assertTrue(commandList.contains("create"));
		assertTrue(commandList.contains("bla bla"));
	}

	@Test
	public void invalid_commands_are_stored_in_order_of_add() {
		commandStorage.addCommand("test1");
		commandStorage.addCommand("test2");
		List<String> commandList = commandStorage.getCommands();
		assertEquals("test1", commandList.get(0));
		assertEquals("test2", commandList.get(1));
	}

	@Test
	public void invalid_commands_are_stored_in_order_of_add_3() {
		commandStorage.addCommand("test1");
		commandStorage.addCommand("test2");
		commandStorage.addCommand("test3");
		List<String> commandList = commandStorage.getCommands();
		assertEquals("test1", commandList.get(0));
		assertEquals("test2", commandList.get(1));
		assertEquals("test3", commandList.get(2));

	}

}
