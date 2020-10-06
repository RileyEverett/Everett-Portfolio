package edu.pdx.cs410J.riley34;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */

public class FlightTest {

	@Test
	public void testThatToStringPrintsCorrectFlightData() {
		Flight flight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
		assertThat(flight.toString(), equalTo("Flight 117 departs PDX at 1/1/20, 8:00 AM arrives LAX at 1/1/20, 12:00 PM"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestThatSourceAirportAndDestinationAirportCantBeTheSame() {
		Flight flight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "PDX", "1/1/2020", "12:00", "pm");
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestThatAirportCodeCanOnlyBeThreeLetters() {
		Flight flight = new Flight(117, "PDXX", "1/1/2020", "8:00", "am", "LAXx", "1/1/2020", "12:00", "pm");
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestThatAirportCodeCanOnlyBeLetters() {
		Flight flight = new Flight(117, "111", "1/1/2020", "8:00", "am", "222", "1/1/2020", "12:00", "pm");
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestThatFlightNumbersCanBeNoGreaterThanFourNumbers() {
		Flight flight = new Flight(11117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestThatFlightNumbersCanBeNoLessOne() {
		Flight flight = new Flight(-117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestThatTimesAreInTheCorrectFormatWithColons() {
		Flight flight = new Flight(117, "PDX", "1/1/2020", "800", "am", "LAX", "1/1/2020", "1200", "pm");
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestThatTimesAreInTheCorrectFormatAndNotTooLongOrTooShort() {
		Flight flight = new Flight(117, "PDX", "1/1/2020", "-8", "am", "LAX", "1/1/2020", "13320034", "pm");
	}

	@Test(expected = IllegalArgumentException.class)
	public void TestThatDateAreInTheCorrectFormat() {
		Flight flight = new Flight(117, "PDX", "1/", "8:00", "am", "LAX", "1/1/202020", "12:00", "pm");
	}

	@Test
	public void TestThatGetNumberReturnsFlightNumber() {
		Flight flight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
		assertThat(flight.getNumber(), equalTo(117));
	}

	@Test
	public void TestThatGetSourceReturnsSourceAirportCode() {
		Flight flight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
		assertThat(flight.getSource(), equalTo("PDX"));
	}

	@Test
	public void TestThatGetDestinationReturnsDestinationAirportCode() {
		Flight flight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
		assertThat(flight.getDestination(), equalTo("LAX"));
	}

	@Test
	public void TestThatGetArrivalStringReturnsArrivalTimeAndArrivalDate() {
		Flight flight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
		assertThat(flight.getArrivalString(), equalTo("1/1/20, 12:00 PM"));
	}

	@Test
	public void TestThatGetDestinationStringReturnsArrivalTimeAndDestinationDate() {
		Flight flight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
		assertThat(flight.getDepartureString(), equalTo("1/1/20, 8:00 AM"));
	}
}
