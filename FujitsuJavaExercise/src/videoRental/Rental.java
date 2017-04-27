package videoRental;

import java.time.LocalDate;

public class Rental implements InterfaceRental {
	
	/* Prices */
	public static final int BASIC_PRICE = 3;
	public static final int PREMIUM_PRICE = 4;
	
	private int length, price;
	private LocalDate date;
	private Film film;
	
	/*
	 * Sets parameters for the rental
	 * Calculates total price taking into account the type of the film
	 */
	public Rental (Film film, LocalDate date, int length) {
		this.length = length;
		this.film = film;
		this.date = date;
		this.price = calculatePrice(film, length);
		
	}
	
	/* checks film type, returns total price */
	public int calculatePrice (Film film, int length) {
		
		/* New film */
		if (film.getType() == 1) {
			
			/* Each day is at premium rate */
			return PREMIUM_PRICE * length;
			
		/* Regular Film */
		} else if (film.getType() == 0) {
			
			/* First 3 days cost BASIC_PRICE */
			if (length <= 3) {
				return BASIC_PRICE;
				
			/* BASIC_PRICE + BASIC_PRICE times the number of days over 3 */
			} else {
				return BASIC_PRICE + (BASIC_PRICE * (length-3));
			}
			
		/* Old film */
		} else if (film.getType() == -1) {
			
			/* First 5 days cost BASIC_PRICE */
			if (length <= 5) {
				return BASIC_PRICE;
				
			/* BASIC_PRICE + BASIC_PRICE times the number of days over 5 */	
			} else {
				return BASIC_PRICE + (BASIC_PRICE * (length-5));
			}
		}
		
		/* If code gets here there must be an invalid film type (not -1, 0 or 1) */	
		return -123;
	}
	
	@Override
	public Film getFilm() {
		return film;
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public int getPrice() {
		return price;
	}
	
	/* 
	 * Returns custom string for the information about the rental
	 * E.g. Matrix 11(New release) 1 days 4 EUR
	 */	
	@Override
	public String toString() {
		String type = "error";
		if (film.getType() == -1) {
			type = "Old film";
		} else if (film.getType() == 0) {
			type = "Regular rental";
		} else if (film.getType() == 1) {
			type = "New release";
		}
		return film.getName()+ "(" + type + ") " + this.length + 
				" days " + this.price + " EUR";
	}
}
