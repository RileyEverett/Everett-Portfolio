package edu.pdx.cs410j.riley34.airlineapp;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AbstractFlight;

import java.lang.reflect.Array;
import java.util.*;

/**
 * the Airline class for the CS410J airline Project
 */

public class Airline extends AbstractAirline {

	private String airlineName;
	private Collection flights;

	/**
	 * constructor for the Airline class. error checks arguments then
	 * create an Airline if they meet approved criteria.
	 *
	 * @param airlineName string that holds the airline name
	 */

	public Airline(String airlineName) {
		if(airlineName.equals("")) {
			throw new IllegalArgumentException("Airline Name Cannot Be Empty!");
		} else {
			this.airlineName = airlineName;
		}
		this.flights = new ArrayList<String>();
	}

	/**
	 * access method for the airlineName variable
	 *
	 * @return Airline classes airlineName variable
	 */

	@Override
	public String getName() {
		return airlineName;
	}

	/**
	 * mutator method for the airlineName variable
	 *
	 * @param airlineName a name to replace the current airlineName variable
	 */

	public void setName(String airlineName) {
		if(airlineName.equals("")) {
			throw new IllegalArgumentException("Airline Name Cannot Be Empty!");
		} else {
			this.airlineName = airlineName;
		}
	}

	/**
	 * mutator method for the flights variable
	 * adds a flight to the Airline classes flights variable
	 */

	@Override
	public void addFlight(AbstractFlight abstractFlight) {
		flights.add(abstractFlight);
	}

	/**
	 * access method for the flights variable
	 *
	 * @return Airline classes flights variable
	 */

	@Override
	public Collection getFlights() {
		return flights;
	}

	/**
	 * A method that sorts all the flights in the airline into alphabetical order using a bubble sort method
	 */

	public void sortFlights() {
		boolean swapFlag = true;
		ArrayList<Flight> flightList = (ArrayList<Flight>) flights;
		while (swapFlag) {
			swapFlag = false;
			for (int i = 0; i < (flightList.size() - 1); i++) {
				if(flightList.get(i).compareTo(flightList.get(i + 1)) > 0) {
					Collections.swap(flightList, i, i + 1);
					swapFlag = true;
				}
			}
		}
		flights = flightList;
	}

}
