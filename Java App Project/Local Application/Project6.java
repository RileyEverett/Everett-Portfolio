package edu.pdx.cs410j.riley34.airlineapp;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.ParserException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * The main class for the CS410J airline Project
 */
public class Project6 {

	/**
	 * This program builds airlines and their corresponding
	 * flights based off of arguments from the command line.
	 * <p>
	 * main will exit with a code of 0 if no errors were detected during run time
	 * and a code of 1 and an error message otherwise.
	 *
	 * @param args arguments read in from the command line.
	 */

	public static void main(String[] args) {
		//check to see if any command line arguments were passed in
		if(args.length == 0) {
			printErrorMessageAndExit("Missing command line arguments");
		}

		//check to see if a flag was entered in the command line
		if(args[0].charAt(0) == '-') {
			for (String s : args) {
				if(s.equalsIgnoreCase("-README")) {
					System.out.println("Author: Riley Everett \n Project Name: Project 6 \n Last Date Updated: 3/11/2020 \n This program " +
							"builds airlines and their corresponding flights based off of arguments from the command line.\n " +
							"The flights and airlines can be accessed and modified using their methods that are described in the documentation.\n " +
							"The command line arguments are the options -Readme (Prints this message) -Print (Prints the airlines info)\n" +
							"-xmlFile (Reads an airline and its corresponding flights in from a XML file) must be followed by the file name \n" +
							"-textFile (Reads an airline and its corresponding flights in from a text file) must be followed by the file name \n" +
							"-pretty (creates a nicely-formatted textual presentation of an airline’s flights) can be followed by a file name to output to" +
							"the arguments in this order are airlineName flightNumber sourceAirport departureDate departureTime am/pm destinationAirport arrivalDate arrivalTime am/pm.\n");
					System.exit(0);
				}
			}
			//check to see if to many arguments have been passed in
			if(args.length > 14) {
				printErrorMessageAndExit("Too many command line arguments");
			}
			//check to see if unknown command line args
			for (String arg : args) {
				if(arg.charAt(0) == '-') {
					if(!arg.equalsIgnoreCase("-print") && !arg.equalsIgnoreCase("-pretty") &&
							!arg.equalsIgnoreCase("-textFile") && !arg.equalsIgnoreCase("-xmlFile") && !arg.equalsIgnoreCase("-"))
						printErrorMessageAndExit("Unknown flag argument!");
				}
			}
			//value to know if a valid flag was parsed
			boolean textFileFlag = false;
			boolean xmlFileFlag = false;
			boolean prettyFlag = false;
			boolean printFlag = false;
			boolean commandLineFlag = false;
			Airline airline = null;
			Flight argsflight = null;
			String fileName = "";
			//check to see if a text file need to be read in
			for (int i = 0; i < args.length; i++) {
				//flags for later command line parsing
				if(args[i].equalsIgnoreCase("-pretty")) {
					prettyFlag = true;
				}
				if(args[i].equalsIgnoreCase("-print")) {
					printFlag = true;
				}
				if(args[i].equalsIgnoreCase("-xmlFile")){
					xmlFileFlag = true;
				}
				if(args[i].equalsIgnoreCase("-textFile")) {
					if(xmlFileFlag) {
						printErrorMessageAndExit("Both -TextFile and -XmlFile Cannot Be Selected!");
					}
					textFileFlag = true;
					try {
						fileName = args[i + 1];
						TextParser scan = new TextParser(fileName);
						airline = (Airline) scan.parse();
						if(airline != null) {
							airline.sortFlights();
						}
						TextDumper dumper = new TextDumper(fileName);
						dumper.dump(airline);
					} catch (FileNotFoundException ex) {
						printErrorMessageAndExit("Text File " + args[i + 1] + " Not Found!");
					} catch (ParserException ex) {
						printErrorMessageAndExit("Text File " + args[i + 1] + " is in Incorrect Format!");
					} catch (IOException | IllegalArgumentException ex) {
						printErrorMessageAndExit("Text File Contains: " + ex.getMessage());
					}
				}
			}
			//check to see if a xml file need to be read in
			for (int i = 0; i < args.length; i++) {
				if(args[i].equalsIgnoreCase("-xmlFile")) {
					xmlFileFlag = true;
					if(textFileFlag) {
						printErrorMessageAndExit("Both -TextFile and -XmlFile Cannot Be Selected!");
					}
					try {
						fileName = args[i + 1];
						XmlParser parser = new XmlParser(fileName);
						airline = (Airline) parser.parse();
						if(airline != null) {
							airline.sortFlights();
						}
						XmlDumper dumper = new XmlDumper(fileName);
						dumper.dump(airline);
					} catch (ParserException | IOException ex) {
						printErrorMessageAndExit("Xml File " + args[i + 1] + " is in Incorrect Format!");
					}
				}
			}
			//check to see if there is a command line entry to be added to the airline
			if(args.length >= 10) {
				//check to make sure correct arguments are read in
				int i = 1;
				if(textFileFlag) {
					i += 2;
				}
				if(xmlFileFlag) {
					i += 2;
				}
				if(prettyFlag) {
					i += 2;
				}
				if(printFlag) {
					i += 1;
				}
				while (i < (args.length - 1)) {
					if(args[i - 1].charAt(0) != '-' && args[i].charAt(0) != '-' && args[i + 1].charAt(0) != '-' && !commandLineFlag) {
						try {
							int indexOffset = i - 1;
							commandLineFlag = true;
							if(airline == null) { //check to see if airline is already built
								airline = new Airline(args[indexOffset]); //create a new new airline with command line arg
							} else if(!airline.getName().equals(args[indexOffset])) {
								printErrorMessageAndExit("Command Line Airline Name Does Not Match Name in File!");
							}
							int flightNumber = Integer.parseInt(args[indexOffset + 1]); //convert the flight number arg from a string to an int
							argsflight = new Flight(flightNumber, args[indexOffset + 2], args[indexOffset + 3], args[indexOffset + 4],
									args[indexOffset + 5], args[indexOffset + 6], args[indexOffset + 7], args[indexOffset + 8], args[indexOffset + 9]); //create a new flight with command line args
							airline.addFlight(argsflight); //add the created flight to the airline
							airline.sortFlights();
							if(!fileName.equals("")) {
								if(textFileFlag) {
									TextDumper dumper = new TextDumper(fileName);
									dumper.dump(airline);
								}
								if(xmlFileFlag) {
									XmlDumper dumper = new XmlDumper(fileName);
									dumper.dump(airline);
								}
							}
						} catch (NumberFormatException ex) {
							printErrorMessageAndExit("Invalid Flight Number " + ex.getMessage());
						} catch (IllegalArgumentException ex) {
							printErrorMessageAndExit(ex.getMessage());
						} catch (IOException ex) {
							printErrorMessageAndExit("Invalid Input " + ex.getMessage());
						} catch (ArrayIndexOutOfBoundsException ex) {
							printErrorMessageAndExit("Incorrect Number of Arguments!");
						}
					}
					i++;
				}
			}
			for (String arg : args) {
				//check to see if the print flag is selected
				if(arg.equalsIgnoreCase("-PRINT")) {
					if(airline != null) {
						System.out.println(airline.toString()); //print airline
					}
					if(argsflight != null) {
						System.out.println(argsflight.toString()); //print command line flight
					}
				}
			}
			//check to see if pretty flag is selected
			for (int i = 0; i < args.length; i++) {
				if(args[i].equalsIgnoreCase("-pretty")) {
					try {
						PrettyPrinter print = new PrettyPrinter(args[i + 1]);
						if(args[i + 1].charAt(0) == '-') {
							System.out.println(print.commandLinePrint(airline)); //pretty print to standard out
						} else {
							print.dump(airline); //pretty print to text file
						}
					} catch (IOException ex) {
						printErrorMessageAndExit("Invalid Input " + ex.getMessage());
					}
				}
			}
		} else {
			if(args.length < 10) {
				printErrorMessageAndExit("Missing command line arguments");
			} else if(args.length > 10) {
				printErrorMessageAndExit("Too many command line arguments");
			} else {
				try {
					Airline airline = new Airline(args[0]); //create a new new airline with command line arg
					int flightNumber = Integer.parseInt(args[1]); //convert the flight number arg from a string to an int
					Flight flight = new Flight(flightNumber, args[2], args[3], args[4], args[5], args[6], args[7], args[8], args[9]); //create a new flight with command line args
					airline.addFlight(flight); //add the created flight to the airline
				} catch (NumberFormatException ex) {
					printErrorMessageAndExit("Invalid Flight Number " + ex.getMessage());
				} catch (IllegalArgumentException ex) {
					printErrorMessageAndExit(ex.getMessage());
				}
			}
		}
		System.exit(0);
	}

	/**
	 * a method that prints the message to System.err that is taking in as an argument
	 * and then exits the program with code 1 to signify an error.
	 *
	 * @param message a String containing the error message to be displayed upon exit
	 */

	public static void printErrorMessageAndExit(String message) {
		System.err.println(message);
		System.exit(1);
	}

}