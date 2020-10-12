package edu.pdx.cs410j.riley34.airlineapp;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * the Flight class for the CS410J airline Project
 */

public class Flight extends AbstractFlight implements java.lang.Comparable<Flight> {

	//private class variables
	private String sourceAirport;
	private String destinationAirport;
	private Date departDate;
	private Date arriveDate;
	private int flightNumber;

	/**
	 * constructor for the flight class. error checks arguments then
	 * creates a flight if they meet approved criteria.
	 *
	 * @param sourceAirport      string that holds a three letter airport code
	 * @param destinationAirport string that holds a three letter airport code
	 * @param departureDate      string that holds a MM/DD/YYYY format date
	 * @param arrivalDate        string that holds a MM/DD/YYYY format date
	 * @param arrivalTime        string that holds a 00:00 24 hr format time
	 * @param departureTime      string that holds a 00:00 24 hr format time
	 * @param flightNumber       int that holds a three digit flight number
	 */

	public Flight(int flightNumber, String sourceAirport, String departureDate, String departureTime, String departSuffix,
	              String destinationAirport, String arrivalDate, String arrivalTime, String arriveSuffix) {
		//check to make sure can be no more than four numbers and no less than one
		if(flightNumber > 9999 || flightNumber < 1) {
			throw new IllegalArgumentException("Invalid Flight Number!");
		} else {
			this.flightNumber = flightNumber;
		}
		//get a map of valid names for testing
		Map<String, String> validNames = AirportNames.getNamesMap();
		//check to make sure source and destination airports are not the same
		if(sourceAirport.equalsIgnoreCase(destinationAirport)) {
			throw new IllegalArgumentException("Duplicate Source and Destination Airports!");
		} //check to make sure source and destination airports are three characters
		else if(sourceAirport.length() != 3 || destinationAirport.length() != 3) {
			throw new IllegalArgumentException("Invalid Airport Code");
		} //check to make sure source and destination airports can only be characters
		else if(!sourceAirport.matches("^[a-zA-Z]*$") || !destinationAirport.matches("^[a-zA-Z]*$")) {
			throw new IllegalArgumentException("Invalid Airport Code");
		} else if(!validNames.containsKey(sourceAirport) || !validNames.containsKey(destinationAirport)) {
			throw new IllegalArgumentException("Invalid Airport Code");
		} else {
			this.sourceAirport = sourceAirport.toUpperCase();
			this.destinationAirport = destinationAirport.toUpperCase();

			//check to make sure dates are in the correct format for casting them to dates
			SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy hh:mm a");

			String concatDepartDate = departureDate + " " + departureTime + " " + departSuffix;
			String concatArriveDate = arrivalDate + " " + arrivalTime + " " + arriveSuffix;

			try {
				departDate = dateFormat.parse(concatDepartDate);
				arriveDate = dateFormat.parse(concatArriveDate);
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Invalid Date or Time Format! \n(Usage MM/DD/YYYY HH:MM)");
			}
			if((arriveDate.getTime() - departDate.getTime()) < 1) {
				throw new IllegalArgumentException("Departure time is after arrival time!");
			}
		}
	}

	/**
	 * access method for the flightNumber variable
	 *
	 * @return Flight classes flightNumber variable
	 */

	@Override
	public int getNumber() {
		return flightNumber;
	}

	/**
	 * access method for the sourceAirport variable
	 *
	 * @return Flight classes sourceAirport variable
	 */

	@Override
	public String getSource() {
		return sourceAirport;
	}

	public long getDepartTime() {
		return departDate.getTime();
	}

	public Date getDepartDate() {
		return departDate;
	}

	/**
	 * access method for the departureTime and departureDate variables
	 *
	 * @return Flight classes departureTime and departureDate variables with a space between them
	 */

	@Override
	public String getDepartureString() {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		return dateFormat.format(departDate);
	}

	/**
	 * access method for the destinationAirport variable
	 *
	 * @return Flight classes destinationAirport variable
	 */

	@Override
	public String getDestination() {
		return destinationAirport;
	}

	/**
	 * access method for the arrivalTime and arrivalDate variables
	 *
	 * @return Flight classes arrivalTime and arrivalDate variable with a space between them
	 */

	public long getArriveTime() {
		return arriveDate.getTime();
	}

	public Date getArriveDate() {
		return arriveDate;
	}

	@Override
	public String getArrivalString() {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		return dateFormat.format(arriveDate);
	}

	/**
	 * access method for all of flights variables in write friendly format
	 *
	 * @return Flight classes variables with a space between them and after
	 */

	public String getWriteData() {
		//convert date to Calendar to output in correct format
		Calendar depart = Calendar.getInstance();
		depart.setTime(departDate);
		Calendar arrive = Calendar.getInstance();
		arrive.setTime(arriveDate);
		return flightNumber + " " + sourceAirport + " " + depart.get(Calendar.DAY_OF_MONTH) + "/" + (depart.get(Calendar.MONTH) + 1) + "/"
				+ depart.get(Calendar.YEAR) + " " + depart.get(Calendar.HOUR) + ":" + depart.get(Calendar.MINUTE) + " " +
				(depart.get(Calendar.AM_PM) == Calendar.PM ? "pm" : "am") + " " + destinationAirport + " " + arrive.get(Calendar.DAY_OF_MONTH) + "/" +
				(arrive.get(Calendar.MONTH) + 1) + "/" + arrive.get(Calendar.YEAR) + " " + arrive.get(Calendar.HOUR) + ":"
				+ arrive.get(Calendar.MINUTE) + " " + (arrive.get(Calendar.AM_PM) == Calendar.PM ? "pm" : "am");
	}

	/**
	 * Formats the classes data into a format for the PrettyPrint class
	 *
	 * @return a String containing the Airline name and all flights it contains in a pretty print friendly format
	 */

	public String getPrettyData() {
		long diffMinutes = (arriveDate.getTime() - departDate.getTime()) / (60 * 1000);
		DateFormat format = DateFormat.getDateTimeInstance(
				DateFormat.LONG, DateFormat.SHORT);
		String formattedDepart = format.format(departDate);
		String formattedArrive = format.format(arriveDate);
		return "Flight " + flightNumber + " That Departs " + sourceAirport + " on " + formattedDepart + " and arrives at " +
				destinationAirport + " on " + formattedArrive + " for a total flight time of " + diffMinutes + " minutes";
	}

	/**
	 * return 1 if the local flight is alphabetically first compared to the arg flight, -1 if second and 0 if equal
	 *
	 * @param flight a flight to be compared to th local flight
	 * @return an int telling the caller how the local and arg flight compare
	 */
	@Override
	public int compareTo(Flight flight) {
		int compareValue = this.sourceAirport.compareTo(flight.getSource());
		if(compareValue > 0) {
			return 1;
		} else if(compareValue < 0) {
			return -1;
		} else {
			long compareTime = this.getDepartTime() - flight.getDepartTime();
			if(compareTime > 0) {
				return 1;
			} else if(compareTime < 0) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
