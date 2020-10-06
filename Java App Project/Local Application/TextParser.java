package edu.pdx.cs410j.riley34.airlineapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/**
 * The TextParser class for the CS410J airline Project
 */

public class TextParser implements AirlineParser {

	private Scanner scan;
	private File fileName;

	/**
	 * a constructor that sets up the scanner and file parser for the TextParser class
	 *
	 * @param fileName a String containing the file name from the command line
	 */

	public TextParser(String fileName) throws IOException {
		//create a new text file if it does not already exist
		Path path = Paths.get(fileName);
		OutputStream out = Files.newOutputStream(path, CREATE, APPEND);
		out.close();
		//save the file name for parse to open
		this.fileName = new File(fileName);
	}

	/**
	 * @return an Airline with all flights in it from the read in text file
	 * @throws ParserException
	 */

	@Override
	public AbstractAirline parse() throws ParserException {
		try {
			scan = new Scanner(fileName);
			if(scan.hasNext()) {
				String airlineName = scan.next();
				Airline airline = new Airline(airlineName);
				while (scan.hasNext()) {
					int flightNumber = Integer.parseInt(scan.next());
					String sourceAirport = scan.next();
					String departureDate = scan.next();
					String departureTime = scan.next();
					String departSuffix = scan.next();
					String destinationAirport = scan.next();
					String arrivalDate = scan.next();
					String arrivalTime = scan.next();
					String arriveSuffix = scan.next();
					Flight flight = new Flight(flightNumber, sourceAirport, departureDate, departureTime, departSuffix,
							destinationAirport, arrivalDate, arrivalTime, arriveSuffix);
					airline.addFlight(flight);
				}
				scan.close();
				return airline;
			}
		} catch (NumberFormatException | FileNotFoundException ex) {
			throw new ParserException("ParserException");
		}
		scan.close();
		return null;
	}
}
