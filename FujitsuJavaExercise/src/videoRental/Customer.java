package videoRental;

public class Customer implements InterfaceCustomer {
	
	private int ID, bonusPoints;
	
	public Customer (int ID, int bonusPoints) {
		this.ID = ID;
		this.bonusPoints = bonusPoints;
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public int getBonus() {
		return bonusPoints;
	}

	@Override
	public void setBonus(int bonus) {
		this.bonusPoints = bonus;
	}

}
