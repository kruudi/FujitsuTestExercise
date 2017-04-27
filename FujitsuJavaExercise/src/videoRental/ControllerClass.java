package videoRental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Needs 3 primary functions:
 * 1) Have an inventory of films - done
 * 2) Calculate price for rentals
 * 3) Keep track of the customer's "bonus" points
 * 
 * @author Ruudi
 */
public class ControllerClass {
	
	public static final int BONUS_POINT_MULTIPLIER = 25;
	public static final int NEW_RELEASE_BONUS = 2;
	public static final int REGULAR_BONUS = 1;
	
	/* Using HashMap as inventory since we need to pull out films with their unique ID. */
	public HashMap<Integer, InterfaceFilm> inventory = new HashMap<Integer, InterfaceFilm>();
	
	/* Using ArrayList to put together full orders consisting of many rentals */
	public ArrayList<InterfaceRental> fullOrder = new ArrayList<InterfaceRental>();
	
	/* really primitive unique ID generator for this exercise */
	public static int unique_ID = 0;
	private static int getUniqueID () {
		unique_ID++;
		return unique_ID;
	}
	
	/**
	 * Creates a bit of sample data and goes through different
	 * functions to show that they work.
	 * @param args
	 */
	public static void main(String[] args) {
		
		ControllerClass main = new ControllerClass();
		
		//films
		Film a = new Film(getUniqueID(), "Mammu 1", Film.NEW_RELEASE, false);
		Film b = new Film(getUniqueID(), "Mammu 2", Film.OLD_FILM, false);
		Film c = new Film(getUniqueID(), "Mammu 3", Film.REGULAR_FILM, false);
		Film d = new Film(getUniqueID(), "Mammu 4", Film.OLD_FILM, true);
		Film e = new Film(getUniqueID(), "Mammu 5", Film.NEW_RELEASE, false);
		
		main.addFilm(a);
		main.addFilm(b);
		main.addFilm(c);
		main.addFilm(d);
		main.addFilm(e);
		
		
		main.listAllAvailable(); //List all available
		System.out.println();
		main.setType(1, Film.OLD_FILM); //Change type of film with id 1
		main.removeFilm(3); //Remove film with id 3
		main.changeRented(2); //Rent out film with id 1
		
		main.listAll(); //List all
		System.out.println();
		
		//rentals
		Rental f = new Rental(a, LocalDate.now(),4);
		Rental g = new Rental(e, LocalDate.of(2017, 4, 20), 1);
		
		main.fullOrder.add(f);
		main.fullOrder.add(g);
		
		
		main.calculatePrice(main.fullOrder); //calculate price
		System.out.println();
		main.calculateLateFee(main.fullOrder); //late fee
		System.out.println();
		
		//bonus points
		Customer h = new Customer(getUniqueID(), 0);
		
		main.addBonusPoints(main.fullOrder, h);
		main.addBonusPoints(main.fullOrder, h);
		main.addBonusPoints(main.fullOrder, h);
		main.addBonusPoints(main.fullOrder, h);
		main.addBonusPoints(main.fullOrder, h);
		main.addBonusPoints(main.fullOrder, h);
		main.addBonusPoints(main.fullOrder, h);
		main.addBonusPoints(main.fullOrder, h);
		main.addBonusPoints(main.fullOrder, h);
		main.addBonusPoints(main.fullOrder, h);
		//System.out.println(h.getBonus());
		
		main.payWithBonus(main.fullOrder, h);
		
	}

// Price calculations and late fee below

	/* Returns the price total of the full order. */
	public int calculatePrice (ArrayList<InterfaceRental> fullOrder) {
		int price = 0;
		
		/* Loops through full order printing out detailed info and adding price to total */
		for (InterfaceRental rental : fullOrder) {
			System.out.println(rental.toString());
			price += rental.getPrice();
		}
		System.out.println("Total price: " + price + " EUR");
		return price;
	}
	
	/* Returns total cost of late fee */
	public long calculateLateFee (ArrayList<InterfaceRental> fullOrder) {
		long fee = 0;
		LocalDate returnDate = LocalDate.now(); 
		
		/*
		 * Loops through the order looking at the rentals separately.
		 * If difference between rental date and return date is bigger than
		 * the length of the rental, calculates late fee for that rental.
		 * Adds calculated fee to previous fee.
		 */
		for (InterfaceRental rental : fullOrder) {
			long dateDifference = ChronoUnit.DAYS.between(rental.getDate(), returnDate);
			
			if (dateDifference > rental.getLength()) {
				long extraTime = dateDifference - rental.getLength();
				
				/* If film that is late is a new release */
				if (rental.getFilm().getType() == Film.NEW_RELEASE) {
					fee += (extraTime) * Rental.PREMIUM_PRICE;
					
					System.out.println(rental.getFilm().getName() + "(New release) " + 
					extraTime + " extra days " + (extraTime) * Rental.PREMIUM_PRICE + " EUR");
				
				/* If film that is late is a regular film */
				} else if (rental.getFilm().getType() == Film.REGULAR_FILM){
					fee += (dateDifference - rental.getLength()) * Rental.BASIC_PRICE;
					
					System.out.println(rental.getFilm().getName() + "(Regular film) " + 
					extraTime + " extra days " + (extraTime) * Rental.BASIC_PRICE + " EUR");
				
				/* If film that is late is an old film */
				} else if (rental.getFilm().getType() == Film.OLD_FILM) {
					fee += (dateDifference - rental.getLength()) * Rental.BASIC_PRICE;
					
					System.out.println(rental.getFilm().getName() + "(Old film) " + 
					extraTime + " extra days " + (extraTime) * Rental.BASIC_PRICE + " EUR");
				}
			}
			
			/* All films should be set as not rented */
			rental.getFilm().setRented(false);
		}
		
		System.out.println("Total late charge: " + fee + " EUR");
		return fee;
	}
	
// Adding, and paying with bonus points below
	
	/* Goes through the order and adds points accordingly */
	public void addBonusPoints (ArrayList<InterfaceRental> fullOrder, Customer customer) {
		int addedBonus = 0;
		for (InterfaceRental rental : fullOrder) {
			
			/* If film that is rented is a new release */
			if (rental.getFilm().getType() == Film.NEW_RELEASE) {
				addedBonus += NEW_RELEASE_BONUS;
			
			/* Other films */
			} else {
				addedBonus += REGULAR_BONUS;
			}
		}
		customer.setBonus(customer.getBonus() + addedBonus);
	}
	
	/*
	 * Pays for as many films as possible with bonus points and for the rest regularly. 
	 * I made an assumption that it is not possible to pay for a film using both bonus
	 * points and regular money at the same time. 
	 */
	public void payWithBonus (ArrayList<InterfaceRental> fullOrder, Customer customer) {
		int price = 0;
		int totalBonus = customer.getBonus();
		
		for (InterfaceRental rental : fullOrder) {
			
			/* If rented films are not new releases, 
			 * or there are not enough bonus points, pay regularly 
			 */
			if (rental.getFilm().getType() != Film.NEW_RELEASE 
					|| totalBonus < BONUS_POINT_MULTIPLIER) {
				System.out.println(rental.toString());
				price += rental.getPrice();
			
			/* Else check if there are enough points and if there are pay with bonus points */
			} else {
				int neededBonus = rental.getLength() * BONUS_POINT_MULTIPLIER;
				if (neededBonus <= totalBonus) {
					totalBonus -= neededBonus;
					
					System.out.println(rental.getFilm().getName() + "(New release) " + 
					rental.getLength() + " days (Paid with " + neededBonus + " Bonus points)");
				}
			}
		}
		
		/* set new amount of bonus points*/
		customer.setBonus(totalBonus);

		System.out.println("Total price: " + price + " EUR");
		System.out.println("\nRemaining bonus points: " + totalBonus);
		
	}
	
// Everything dealing with inventory is below	
	
	/*
	 * Changes type of the film with ID to newType.
	 */
	public void setType (int ID, int newType) {
		inventory.get(ID).setType(newType);
	}
	
	/*
	 * Changes rented value of the film with given ID to opposite value.
	 * E.g. If the film was rented before running this function
	 * it is not afterwards and the other way around.
	 */
	public void changeRented (int ID) { 
		inventory.get(ID).setRented(!inventory.get(ID).isRented());
	}
	
	/*
	 * Adds a new film into the inventory.
	 */
	public void addFilm (Film film) {
		inventory.put(film.getID(), film);
	}
	
	/*
	 * Removes the film with given ID from the inventory.
	 */
	public void removeFilm (int ID) {
		inventory.remove(ID);
	}
	
	/*
	 * Loops through inventory and prints all info on films.
	 */
	public void listAll () {
		for (InterfaceFilm film : inventory.values()) {
			System.out.println(film.toString());
		}
	}
	
	/*
	 * Loops through inventory, checks if film is rented out or not,
	 * if film is not rented out prints all info on that film
	 */
	public void listAllAvailable () {
		for (InterfaceFilm film : inventory.values()) {
			if (!film.isRented()) {
				System.out.println(film.toString());
			}
		}
	}

}
