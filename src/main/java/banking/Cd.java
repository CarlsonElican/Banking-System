package banking;

public class Cd extends Account {

	public Cd(String id, double apr, double balance) {
		super(id, apr);
		this.balance = balance;
		this.monthsPassed = 0;
		this.canWithdraw = false;
	}

	@Override
	public String getAccountType() {
		return "cd";
	}

}
