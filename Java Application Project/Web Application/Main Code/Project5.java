package edu.pdx.cs410J.riley34;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

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
					System.out.println("Author: Riley Everett \n Project Name: Project 1 \n Last Date Updated: 3/4/2020 \n This program " +
							"builds airlines and their corresponding flights based off of arguments from the command line.\n " +
							"The flights and airlines can be accessed and modified using their methods that are described in the documentation.\n " +
							"The command line arguments are the options -Readme (Prints this message) -Print (Prints the airlines info)\n" +
							"-xmlFile (Reads an airline and its corresponding flights in from a XML file) must be followed by the file name \n" +
							"-textFile (Reads an airline and its corresponding flights in from a text file) must be followed by the file name \n" +
							"-pretty (creates a nicely-formatted textual presentation of an airline’s flights) can be followed by a file name to output to\n" +
							"-host (specifies the Host computer on which the server runs) must be followed by a hostname \n" +
							"-port (specifies the Port on which the server runs) must be followed by a port number \n" +
							"-search (Search for flights between two airports) must be followed by source and destination airport codes \n" +
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
							!arg.equalsIgnoreCase("-textFile") && !arg.equalsIgnoreCase("-xmlFile") &&
							!arg.equalsIgnoreCase("-") && !arg.equalsIgnoreCase("-host") &&
							!arg.equalsIgnoreCase("-port") && !arg.equalsIgnoreCase("-search")) {
						printErrorMessageAndExit("Unknown flag argument!");
					}
				}
			}

			//value to know if a valid flag was parsed
			boolean textFileFlag = false;
			boolean xmlFileFlag = false;
			boolean prettyFlag = false;
			boolean printFlag = false;
			boolean commandLineFlag = false;
			boolean hostFlag = false;
			boolean portFlag = false;
			boolean searchFlag = false;
			String hostName = "";
			int portNum = 0;
			Airline airline = null;
			Flight argsFlight = null;
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
				if(args[i].equalsIgnoreCase("-xmlFile")) {
					xmlFileFlag = true;
				}
				if(args[i].equalsIgnoreCase("-host")) {
					hostFlag = true;
				}
				if(args[i].equalsIgnoreCase("-port")) {
					portFlag = true;
				}
				if(args[i].equalsIgnoreCase("-search")) {
					searchFlag = true;
				}
				if(args[i].equalsIgnoreCase("-textFile")) {
					if(xmlFileFlag) {
						printErrorMessageAndExit("Both -TextFile and -XmlFile Cannot Be Selected!");
					}
					textFileFlag = true;
				}
			}

			//check to see if a xml file need to be read in
			if(xmlFileFlag) {
				for (int i = 0; i < args.length; i++) {
					if(args[i].equalsIgnoreCase("-xmlFile")) {
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
							XmlDumper dumper = new XmlDumper();
							dumper.dump(airline);
						} catch (ParserException | IOException ex) {
							printErrorMessageAndExit("Xml File " + args[i + 1] + " is in Incorrect Format!");
						}
					}
				}
			}

			//get the host name if one was input
			if(hostFlag) {
				for (int i = 0; i < args.length; i++) {
					if(args[i].equalsIgnoreCase("-host")) {
						if(!portFlag) {
							printErrorMessageAndExit("Must include both a -Host and -Port name");
						}
						hostName = args[i + 1];
					}
				}
			}

			//get the port name if one was input
			if(portFlag) {
				for (int i = 0; i < args.length; i++) {
					if(args[i].equalsIgnoreCase("-port")) {
						if(!hostFlag) {
							printErrorMessageAndExit("Must include both a -Host and -Port name");
						}
						try {
							portNum = Integer.parseInt(args[i + 1]);
						} catch (NumberFormatException ex) {
							printErrorMessageAndExit("Port \"" + args[i + 1] + "\" must be an integer");
							return;
						}
					}
				}
			}

			if(searchFlag) {
				for (int i = 0; i < args.length; i++) {
					if(args[i].equalsIgnoreCase("-search")) {
						String searchTarget = args[i + 1];
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
				if(hostFlag) {
					i += 2;
				}
				if(portFlag) {
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
							argsFlight = new Flight(flightNumber, args[indexOffset + 2], args[indexOffset + 3], args[indexOffset + 4],
									args[indexOffset + 5], args[indexOffset + 6], args[indexOffset + 7], args[indexOffset + 8], args[indexOffset + 9]); //create a new flight with command line args
							airline.addFlight(argsFlight); //add the created flight to the airline
							airline.sortFlights();
							if(!fileName.equals("")) {
								if(xmlFileFlag) {
									XmlDumper dumper = new XmlDumper();
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

			if(hostFlag && portFlag) {
				try {
					AirlineRestClient client = new AirlineRestClient(hostName, portNum);
					if(searchFlag) {
						if(args.length != 8) {
							printErrorMessageAndExit("Incorrect Number Of Arguments!");
						}
						String airlineName = args[5];
						String src = args[6];
						String dest = args[7];
						Map<String, String> searchKeys = new HashMap<>();
						searchKeys.put("airline", airlineName);
						searchKeys.put("src", src);
						searchKeys.put("dest", dest);
						String response = client.getAirlineData(searchKeys);
						InputStream responseStream = new ByteArrayInputStream(response.getBytes());
						XmlParser parser = new XmlParser("null");
						Airline parsedAirline =  parser.parseStream(responseStream);
						PrettyPrinter pretty = new PrettyPrinter("null");
						System.out.println(pretty.commandLinePrint(parsedAirline));
					} else if(args.length == 14) {
						Map<String, String> parameters = new HashMap<>();
						parameters.put("airlineName", airline.getName());
						parameters.put("flightNumber", Integer.toString(argsFlight.getNumber()));
						parameters.put("src", argsFlight.getSource());
						parameters.put("depart", argsFlight.getDepartureString().replaceAll("[,]", ""));
						parameters.put("dest", argsFlight.getDestination());
						parameters.put("arrive", argsFlight.getArrivalString().replaceAll("[,]", ""));
						HttpRequestHelper.Response response = client.postToMyURL(parameters);
						String responseString = response.getContent();
						System.out.println(responseString);
					} else if(args.length == 5) {
						try {
							Map<String, String> parameters = new HashMap<>();
							parameters.put("airline", args[4]);
							String response = client.getAirlineData(parameters);
							InputStream responseStream = new ByteArrayInputStream(response.getBytes());
							XmlParser parser = new XmlParser("null");
							Airline parsedAirline = parser.parseStream(responseStream);
							PrettyPrinter pretty = new PrettyPrinter("null");
							System.out.println(pretty.commandLinePrint(parsedAirline));
						} catch (AirlineRestClient.AirlineRestException ex) {
							printErrorMessageAndExit("Airline Not Found");
						}
					} else {
						printErrorMessageAndExit("Incorrect Number Of Arguments!");
					}

				} catch (IOException | ParserException ex) {
					printErrorMessageAndExit("While contacting server: " + ex);
					return;
				} catch (AirlineRestClient.AirlineRestException ex) {
					printErrorMessageAndExit("No Flights Found");
					return;
				}
			}

			if(printFlag) {
				for (String arg : args) {
					//check to see if the print flag is selected
					if(arg.equalsIgnoreCase("-PRINT")) {
						if(airline != null) {
							System.out.println(airline.toString()); //print airline
						}
						if(argsFlight != null) {
							System.out.println(argsFlight.toString()); //print command line flight
						}
					}
				}
			}

			//check to see if pretty flag is selected
			if(prettyFlag) {
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

	/*
		public static final String MISSING_ARGS = "Missing command line arguments";

		public static void main(String... args) {
			String hostName = null;
			String portString = null;
			String word = null;
			String definition = null;

			for (String arg : args) {
				if (hostName == null) {
					hostName = arg;

				} else if ( portString == null) {
					portString = arg;

				} else if (word == null) {
					word = arg;

				} else if (definition == null) {
					definition = arg;

				} else {
					usage("Extraneous command line argument: " + arg);
				}
			}

			if (hostName == null) {
				usage( MISSING_ARGS );

			} else if ( portString == null) {
				usage( "Missing port" );
			}

			int port;
			try {
				port = Integer.parseInt( portString );

			} catch (NumberFormatException ex) {
				usage("Port \"" + portString + "\" must be an integer");
				return;
			}

			AirlineRestClient client = new AirlineRestClient(hostName, port);

			String message;
			try {
				if (word == null) {
					// Print all word/definition pairs
					Map<String, String> dictionary = client.getAllDictionaryEntries();
					StringWriter sw = new StringWriter();
					Messages.formatDictionaryEntries(new PrintWriter(sw, true), dictionary);
					message = sw.toString();

				} else if (definition == null) {
					// Print all dictionary entries
					message = Messages.formatDictionaryEntry(word, client.getDefinition(word));

				} else {
					// Post the word/definition pair
					client.addDictionaryEntry(word, definition);
					message = Messages.definedWordAs(word, definition);
				}

			} catch ( IOException ex ) {
				error("While contacting server: " + ex);
				return;
			}

			System.out.println(message);

			System.exit(0);
		}
		*/
}