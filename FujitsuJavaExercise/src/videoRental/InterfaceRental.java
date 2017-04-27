package videoRental;

import java.time.LocalDate;

/**
 * API specification for the objects representing various rentals.
 * Rental in this context is considered a process of renting out one film.
 */
public interface InterfaceRental {
	
	/* Returns info related to the film rented */
	public Film getFilm();
	
	/* Returns the date of the rental. */
	public LocalDate getDate();
	
	/* Returns the length of the rental. */
	public int getLength();
	
	/* Returns the price of the rental */
	public int getPrice();
}
