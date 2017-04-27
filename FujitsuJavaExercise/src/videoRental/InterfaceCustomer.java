package videoRental;

/**
 * API specification for the objects representing various customers.
 */
public interface InterfaceCustomer {
	
	/* Returns customers unique ID. */
	public int getID();
	
	/* Returns amount of bonus points customer has */
	public int getBonus();
	
	/* Sets new amount of bonusPoints for the customer */
	public void setBonus(int bonus);
}
