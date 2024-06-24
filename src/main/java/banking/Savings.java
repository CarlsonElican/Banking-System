package banking;

public class Savings extends Account {

	public Savings(String id, double apr) {
		super(id, apr);
		this.balance = 0;
		this.monthsPassed = 0;
		this.canWithdraw = true;
	}

	@Override
	public String getAccountType() {
		return "savings";
	}
}
