package edu.pdx.cs410j.riley34.airlineapp;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The XmlDumper class for the CS410J airline Project
 *
 */

public class XmlDumper implements AirlineDumper {

	private String xmlFileName;

	/**
	 * a constructor that sets file name of the xml dumper for the XmlDumper class
	 *
	 * @param fileName
	 */

	public XmlDumper(String fileName) {
		this.xmlFileName = fileName;
	}

	/**
	 * creates the airline section of the xml document and writes the data of the airline and its flights to it
	 * @param doc the Document object that is being written to
	 * @param airline the airline object to be written to the xml file
	 */

	private static void createAirline(Document doc, Airline airline) {
		Element root = doc.createElement("airline");
		doc.appendChild(root);
		Node name = root.appendChild(doc.createElement("name"));
		name.appendChild(doc.createTextNode(airline.getName()));
		ArrayList<String> flights = (ArrayList<String>) airline.getFlights();
		for (Object flightObj : flights) {
			Flight flight = (Flight) flightObj;
			Node flightNode = createFlight(doc, flight.getNumber(), flight.getSource(), flight.getDepartDate(), flight.getDestination(), flight.getArriveDate());
			root.appendChild(flightNode);
		}
	}

	/**
	 * creates a flight section in the xml document and writes the data of the airlines passed in flight to it
	 * @param doc the Document object that is being written to
	 * @param flightNumber int containing the flight's number
	 * @param sourceAirport string that holds a three letter airport code of the source airport
	 * @param departDate Date object containing the date and time the flight departs
	 * @param destAirport string that holds a three letter airport code of the destination airport
	 * @param arriveDate Date object containing the date and time the flight arrives
	 * @return a Node containing the flights data in xml format
	 */

	private static Node createFlight(Document doc, int flightNumber, String sourceAirport, Date departDate, String destAirport, Date arriveDate) {
		Element flight = doc.createElement("flight");
		Node number = flight.appendChild(doc.createElement("number"));
		number.appendChild(doc.createTextNode(Integer.toString(flightNumber)));
		Node source = flight.appendChild(doc.createElement("src"));
		source.appendChild(doc.createTextNode(sourceAirport));
		Node depart = flight.appendChild(doc.createElement("depart"));
		Calendar departCal = Calendar.getInstance();
		departCal.setTime(departDate);
		Element DateForDepart = doc.createElement("date");
		DateForDepart.setAttribute("day", Integer.toString(departCal.get(Calendar.DAY_OF_MONTH)));
		DateForDepart.setAttribute("month", Integer.toString(departCal.get(Calendar.MONTH) + 1));
		DateForDepart.setAttribute("year", Integer.toString(departCal.get(Calendar.YEAR)));
		Element timeForDepart = doc.createElement("time");
		timeForDepart.setAttribute("hour", Integer.toString(departCal.get(Calendar.HOUR_OF_DAY)));
		timeForDepart.setAttribute("minute", Integer.toString(departCal.get(Calendar.MINUTE)));
		depart.appendChild(DateForDepart);
		depart.appendChild(timeForDepart);
		Node dest = flight.appendChild(doc.createElement("dest"));
		dest.appendChild(doc.createTextNode(destAirport));
		Node arrive = flight.appendChild(doc.createElement("arrive"));
		Calendar arriveCal = Calendar.getInstance();
		arriveCal.setTime(arriveDate);
		Element DateForArrive = doc.createElement("date");
		DateForArrive.setAttribute("day", Integer.toString(arriveCal.get(Calendar.DAY_OF_MONTH)));
		DateForArrive.setAttribute("month", Integer.toString(arriveCal.get(Calendar.MONTH) + 1));
		DateForArrive.setAttribute("year", Integer.toString(arriveCal.get(Calendar.YEAR)));
		Element timeForArrive = doc.createElement("time");
		timeForArrive.setAttribute("hour", Integer.toString(arriveCal.get(Calendar.HOUR_OF_DAY)));
		timeForArrive.setAttribute("minute", Integer.toString(arriveCal.get(Calendar.MINUTE)));
		arrive.appendChild(DateForArrive);
		arrive.appendChild(timeForArrive);
		return flight;
	}

	/**
	 * creates an xml document and writes the data of an airline and its flights to it
	 * @param abstractAirline an abstractAirline object to be written to an xml document
	 * @throws IOException
	 */

	@Override
	public void dump(AbstractAirline abstractAirline) throws IOException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			if(abstractAirline != null) {
				createAirline(doc, (Airline) abstractAirline);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transF = transformerFactory.newTransformer();
			transF.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");
			transF.setOutputProperty(OutputKeys.INDENT, "yes");
			transF.setOutputProperty(OutputKeys.ENCODING, "us-ascii");
			DOMSource source = new DOMSource(doc);
			File myFile = new File(xmlFileName);

			StreamResult file = new StreamResult(myFile);
			transF.transform(source, file);

		} catch (ParserConfigurationException | TransformerException ex) {
			throw new IOException(ex);
		}
	}

	String dumpToString(AbstractAirline abstractAirline) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			if(abstractAirline != null) {
				createAirline(doc, (Airline) abstractAirline);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transF = transformerFactory.newTransformer();
			transF.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");
			transF.setOutputProperty(OutputKeys.INDENT, "yes");
			transF.setOutputProperty(OutputKeys.ENCODING, "us-ascii");

			StringWriter sw = new StringWriter();
			transF.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();

		} catch (ParserConfigurationException | TransformerException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
