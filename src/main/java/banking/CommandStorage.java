package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {

	private List<String> commandList;

	public CommandStorage() {
		commandList = new ArrayList<>();

	}

	public void addCommand(String command) {
		commandList.add(command);
	}

	public List<String> getCommands() {
		return new ArrayList<>(commandList);
	}
}
