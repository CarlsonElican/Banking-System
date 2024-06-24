package banking;

public class ValidatorUtility {

	private ValidatorUtility() {
		throw new UnsupportedOperationException("error");
	}

	public static boolean isValidID(String id, Bank bank, boolean mustExist) {
		if (id == null || id.length() != 8) {
			return false;
		}
		try {
			int idValue = Integer.parseInt(id);
			boolean exists = bank.retrieveAccount(id) != null;
			return idValue >= 0 && (exists == mustExist);
		} catch (NumberFormatException e) {
			return false;
		}
	}
}