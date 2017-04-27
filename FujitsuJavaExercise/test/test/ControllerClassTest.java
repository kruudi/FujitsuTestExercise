package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import videoRental.ControllerClass;
import videoRental.Customer;
import videoRental.Film;
import videoRental.InterfaceRental;
import videoRental.Rental;

public class ControllerClassTest {
	
	@Test
	public void testCalculatePrice() {
		ControllerClass test = new ControllerClass();
		
		ArrayList<InterfaceRental> testOrder = new ArrayList<InterfaceRental>();
		Film testFilm = new Film(1,"Test",-1,false);
		Rental testRental = new Rental(testFilm, LocalDate.now(), 5);
		testOrder.add(testRental);
		
		assertEquals(3, test.calculatePrice(testOrder));
	}

	@Test
	public void testCalculateLateFee() {
		ControllerClass test = new ControllerClass();
		
		ArrayList<InterfaceRental> testOrder = new ArrayList<InterfaceRental>();
		Film testFilm = new Film(1,"Test",-1,false);
		Rental testRental = new Rental(testFilm, LocalDate.of(2017, 4, 20), 5);
		testOrder.add(testRental);
		
		assertEquals(6, test.calculateLateFee(testOrder));
	}

	@Test
	public void testAddBonusPoints() {
		ControllerClass test = new ControllerClass();
		
		ArrayList<InterfaceRental> testOrder = new ArrayList<InterfaceRental>();
		Film testFilm = new Film(1,"Test",1,false);
		Rental testRental = new Rental(testFilm, LocalDate.of(2017, 4, 20), 5);
		Customer testCustomer = new Customer(1, 0);
		testOrder.add(testRental);
		test.addBonusPoints(testOrder, testCustomer);
		
		assertEquals(2, testCustomer.getBonus());
	}

	@Test
	public void testPayWithBonus() {
		ControllerClass test = new ControllerClass();
		
		ArrayList<InterfaceRental> testOrder = new ArrayList<InterfaceRental>();
		Film testFilm = new Film(1,"Test",1,false);
		Rental testRental = new Rental(testFilm, LocalDate.of(2017, 4, 20), 2);
		Customer testCustomer = new Customer(1, 63);
		testOrder.add(testRental);
		test.payWithBonus(testOrder, testCustomer);
		
		assertEquals(13, testCustomer.getBonus());
	}

	@Test
	public void testSetType() {
		ControllerClass test = new ControllerClass();
		
		Film testFilm = new Film(1,"Test",1,false);
		test.addFilm(testFilm);
		test.setType(1, Film.REGULAR_FILM);
		
		assertEquals(Film.REGULAR_FILM, testFilm.getType());
	}

	@Test
	public void testChangeRented() {
		ControllerClass test = new ControllerClass();
		
		Film testFilm = new Film(1,"Test",1,false);
		test.addFilm(testFilm);
		test.changeRented(1);
		
		assertEquals(true, testFilm.isRented());
	}

	@Test
	public void testAddFilm() {
		ControllerClass test = new ControllerClass();
		
		Film testFilm = new Film(1,"Test",1,false);
		assertEquals(0, test.inventory.size());
		test.addFilm(testFilm);
		assertEquals(1, test.inventory.size());
	}

	@Test
	public void testRemoveFilm() {
		ControllerClass test = new ControllerClass();
		
		Film testFilm = new Film(1,"Test",1,false);
		test.addFilm(testFilm);
		assertEquals(1, test.inventory.size());
		test.removeFilm(1);
		assertEquals(0, test.inventory.size());
	}

}
