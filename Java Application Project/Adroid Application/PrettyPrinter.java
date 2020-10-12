package edu.pdx.cs410j.riley34.airlineapp;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class PrettyPrinter implements AirlineDumper {
	private File textFile;

	/**
	 * a constructor that sets of the writer and file dumper for the Project2 class
	 * @param fileName a String containing the file name from the command line
	 */

	PrettyPrinter (String fileName) {
		textFile = new File(fileName);
	}

	/**
	 * A method that dumps the airline and all of its flights into a text file in the pretty print format
	 * @param abstractAirline an Airline with all flights in it to be pretty printed
	 * @throws IOException
	 */

	@Override
	public void dump(AbstractAirline abstractAirline) throws IOException {
		if(abstractAirline != null) {
			FileWriter writer = new FileWriter(textFile);
			writer.write("Flights for " + abstractAirline.getName() + " Include: \n");
			Collection flights = abstractAirline.getFlights();
			ArrayList<Flight> flightList = (ArrayList<Flight>) flights;
			try {
				for (Flight flight : flightList) {
					writer.append(flight.getPrettyData()).append("\n");
				}
				writer.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * A method that creates a String of the airline and all of its flights in the pretty print format
	 * @param abstractAirline an Airline with all flights in it to be pretty printed
	 * @return the String containing the final pretty printed airline and flights
	 */

	public String commandLinePrint(AbstractAirline abstractAirline) {
		StringBuilder sb = new StringBuilder();
		if(abstractAirline != null) {
			sb.append("The Airline " + abstractAirline.getName()).append(" Includes The Flights: \n\n");
			Collection flights = abstractAirline.getFlights();
			ArrayList<Flight> flightList = (ArrayList<Flight>) flights;
				for (Flight flight : flightList) {
					sb.append(flight.getPrettyData()).append("\n\n");
				}
			}
		return sb.toString();
	}
}
