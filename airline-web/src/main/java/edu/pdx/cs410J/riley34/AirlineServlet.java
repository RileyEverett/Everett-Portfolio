package edu.pdx.cs410J.riley34;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
	static final String WORD_PARAMETER = "word";
	static final String DEFINITION_PARAMETER = "definition";

	private final Map<String, String> dictionary = new HashMap<>();
	private final Collection<Airline> airlines = new ArrayList<>();


	/**
	 * Handles an HTTP GET request from a client by writing the definition of the
	 * word specified in the "word" HTTP parameter to the HTTP response.  If the
	 * "word" parameter is not specified, all of the entries in the dictionary
	 * are written to the HTTP response.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");

		String airlineName = getParameter("airline", request);
		if(airlineName == null) {
			missingRequiredParameter(response, "airline");
		} else {
			String src = getParameter("src", request);
			String dest = getParameter("dest", request);
			if(src == null || dest == null) {
				writeAllAirlineEntries(airlineName, response);
			} else {
				findConnectingFlights(airlineName, src, dest, response);
			}
		}
	}

	private void findConnectingFlights(String airlineName, String src, String dest, HttpServletResponse response) throws IOException {
		XmlDumper dumper = new XmlDumper();
		boolean validAirlineFlag = false;
		Object[] alList = airlines.toArray();
		Airline targetAirline = null;
		for (Object alObject : alList) {
			Airline currentAirline = (Airline) alObject;
			if(currentAirline.getName().equalsIgnoreCase(airlineName)) {
				targetAirline = currentAirline;
				validAirlineFlag = true;
			}
		}

		if(!validAirlineFlag) {
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Airline Not Found");
		} else {
			boolean flightFoundFlag = false;
			Collection flights = targetAirline.getFlights();
			Collection foundFlights = new ArrayList<>();
			Object[] flightList = flights.toArray();
			for(Object flight : flightList) {
				Flight currentFlight = (Flight) flight;
				if(currentFlight.getSource().equals(src) && currentFlight.getDestination().equals(dest)) {
					foundFlights.add(currentFlight);
					flightFoundFlag = true;
				}
			}
			if(!flightFoundFlag) {
				response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "No Flights Found Between" + src + " and " + dest + ".");
			} else {
				dumper.dumpConnectingFlightsToServer(targetAirline, foundFlights, response);
				response.setStatus(HttpServletResponse.SC_OK);
			}
		}
	}


	private void writeAllAirlineEntries(String airlineName, HttpServletResponse response) throws IOException {
		XmlDumper dumper = new XmlDumper();
		boolean validAirlineFlag = false;
		Object[] alList = airlines.toArray();
		for (Object alObject : alList) {
			Airline currentAirline = (Airline) alObject;
			if(currentAirline.getName().equalsIgnoreCase(airlineName)) {
				dumper.dumpToServer((Airline) alObject, response);
				validAirlineFlag = true;
			}
		}

		if(!validAirlineFlag) {
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Airline Not Found");
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

	/**
	 * Handles an HTTP POST request by storing the dictionary entry for the
	 * "word" and "definition" request parameters.  It writes the dictionary
	 * entry to the HTTP response.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");

		String airlineName = getParameter("airlineName", request);
		if(airlineName == null) {
			missingRequiredParameter(response, "airlineName");
			return;
		}
		String flightNumber = getParameter("flightNumber", request);
		if(flightNumber == null) {
			missingRequiredParameter(response, "flightNumber");
			return;
		}
		String source = getParameter("src", request);
		if(source == null) {
			missingRequiredParameter(response, "src");
			return;
		}
		String depart = getParameter("depart", request);
		if(depart == null) {
			missingRequiredParameter(response, "depart");
			return;
		}
		String dest = getParameter("dest", request);
		if(dest == null) {
			missingRequiredParameter(response, "dest");
			return;
		}
		String arrive = getParameter("arrive", request);
		if(arrive == null) {
			missingRequiredParameter(response, "arrive");
			return;
		}

		String[] departList = depart.split(" ");
		String departDate = departList[0];
		String departTime = departList[1];
		String departSuffix = departList[2];

		String[] arriveList = arrive.split(" ");
		String arriveDate = arriveList[0];
		String arriveTime = arriveList[1];
		String arriveSuffix = arriveList[2];
		try {
			Airline airline = new Airline(airlineName);
			Flight flight = new Flight(Integer.parseInt(flightNumber), source, departDate, departTime, departSuffix, dest, arriveDate, arriveTime, arriveSuffix);

			Object[] list = airlines.toArray();
			boolean airlineExistsFlag = false;
			for (Object alObject : list) {
				Airline currentAirline = (Airline) alObject;
				if(currentAirline.getName().equalsIgnoreCase(airlineName)) {
					airlineExistsFlag = true;
					break;
				}
			}
			if(!airlineExistsFlag) {
				airline.addFlight(flight);
				this.airlines.add(airline);
			} else {
				Object[] alList = airlines.toArray();
				for (Object alObject : alList) {
					Airline currentAirline = (Airline) alObject;
					if(currentAirline.getName().equalsIgnoreCase(airlineName)) {
						currentAirline.addFlight(flight);
					}
				}
			}

			PrintWriter pw = response.getWriter();
			if(!airlineExistsFlag) {
				pw.println("Created new Airline " + airlineName + ".");
			}
			pw.println("Added Flight " + flightNumber + " to " + airlineName + ".");
			pw.flush();

		} catch (IllegalArgumentException ex) {
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Input Incorrectly Formatted!");
			return;
		}
		response.setStatus(HttpServletResponse.SC_OK);

	}

	/**
	 * Handles an HTTP DELETE request by removing all dictionary entries.  This
	 * behavior is exposed for testing purposes only.  It's probably not
	 * something that you'd want a real application to expose.
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");

		this.dictionary.clear();

		PrintWriter pw = response.getWriter();
		pw.println(Messages.allDictionaryEntriesDeleted());
		pw.flush();

		response.setStatus(HttpServletResponse.SC_OK);

	}

	/**
	 * Writes an error message about a missing parameter to the HTTP response.
	 * <p>
	 * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
	 */
	private void missingRequiredParameter(HttpServletResponse response, String parameterName)
			throws IOException {
		String message = Messages.missingRequiredParameter(parameterName);
		response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
	}

	/**
	 * Writes the definition of the given word to the HTTP response.
	 * <p>
	 * The text of the message is formatted with
	 * {@link Messages#formatDictionaryEntry(String, String)}
	 */
	private void writeDefinition(String word, HttpServletResponse response) throws IOException {
		String definition = this.dictionary.get(word);

		if(definition == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);

		} else {
			PrintWriter pw = response.getWriter();
			pw.println(Messages.formatDictionaryEntry(word, definition));

			pw.flush();

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

	/**
	 * Writes all of the dictionary entries to the HTTP response.
	 * <p>
	 * The text of the message is formatted with
	 * {@link Messages#formatDictionaryEntry(String, String)}
	 */
	private void writeAllDictionaryEntries(HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		Messages.formatDictionaryEntries(pw, dictionary);

		pw.flush();

		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * Returns the value of the HTTP request parameter with the given name.
	 *
	 * @return <code>null</code> if the value of the parameter is
	 * <code>null</code> or is the empty string
	 */
	private String getParameter(String name, HttpServletRequest request) {
		String value = request.getParameter(name);
		if(value == null || "".equals(value)) {
			return null;

		} else {
			return value;
		}
	}

	@VisibleForTesting
	String getDefinition(String word) {
		return this.dictionary.get(word);
	}
}
