package edu.pdx.cs410j.riley34.airlineapp;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The TextDumper class for the CS410J airline Project
 */

public class TextDumper implements AirlineDumper {

	private FileWriter writer;
	private File textFile;

	/**
	 * a constructor that sets of the writer and file dumper for the Project2 class
	 * @param fileName a String containing the file name from the command line
	 * @throws IOException
	 */

	TextDumper (String fileName) throws IOException {
		textFile = new File(fileName);
		writer = new FileWriter(textFile);
	}

	/**
	 *
	 * @param abstractAirline an Airline with all flights in it from the read in text file to be dumped back into it
	 * @throws IOException
	 */

	@Override
	public void dump(AbstractAirline abstractAirline) throws IOException {
		if(abstractAirline != null) {
			writer.write(abstractAirline.getName() + " ");
			Collection flights = abstractAirline.getFlights();
			ArrayList<Flight> flightList = (ArrayList<Flight>) flights;
			try {
				for (Flight flight : flightList) {
					writer.append(flight.getWriteData()).append(" ");
				}
				writer.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
