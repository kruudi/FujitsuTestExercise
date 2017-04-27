package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import videoRental.Film;
import videoRental.Rental;

public class RentalTest {

	@Test
	public void testCalculatePrice() {
		Film testFilm = new Film(1,"Test",-1,false);
		Rental testRental = new Rental(testFilm, LocalDate.of(2017, 4, 20), 7);
		assertEquals(9, testRental.calculatePrice(testFilm, 7));
	}

	@Test
	public void testToString() {
		Film testFilm = new Film(1,"Test",1,false);
		Rental testRental = new Rental(testFilm, LocalDate.of(2017, 4, 20), 2);
		//System.out.println(testRental.toString().indexOf("error"));
		
		//indexOf returns -1 if string not found
		assertEquals(-1, testRental.toString().indexOf("error"));
	}

}
