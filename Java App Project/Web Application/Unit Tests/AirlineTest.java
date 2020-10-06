package edu.pdx.cs410J.riley34;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Airline} class.
 */

public class AirlineTest {

	@Test (expected = IllegalArgumentException.class)
	public void TestAirlineNameCantBeEmptyAndIllegalArgumentExceptionIsThrown() {
		Airline airline = new Airline("");
	}

	@Test
	public void TestThatGetNameReturnsAirlineName() {
		Airline airline = new Airline("Air1");
		assertThat(airline.getName(), equalTo("Air1"));
	}

	@Test
	public void TestThatAddFlightAddsFlightsToTheAirline() {
		Airline airline = new Airline("Air1");
		Flight flight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
		airline.addFlight(flight);
		assertThat(airline.getFlights().isEmpty(), equalTo(false));
	}

	@Test
	public void TestThatGetFlightsReturnsFlightName() {
		Airline airline = new Airline("Air1");
		Flight flight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
		airline.addFlight(flight);
		assertThat(airline.getFlights().toString(), equalTo("[Flight 117 departs PDX at 1/1/20, 8:00 AM arrives LAX at 1/1/20, 12:00 PM]"));
	}
}
