package edu.pdx.cs410j.riley34.airlineapp;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * The XmlParser class for the CS410J airline Project
 */

public class XmlParser implements AirlineParser {

	private String xmlFileName;

	/**
	 * a constructor that sets file name of the xml parser for the XmlParser class
	 *
	 * @param fileName a String containing the file name from the command line
	 */

	public XmlParser(String fileName) {
		this.xmlFileName = fileName;
	}

	/**
	 * reads in data from an xml file and creates an airline anf flights from it.
	 * @return and airline made from data that has been read in from an xml file or null if the file doesnt exist
	 * @throws ParserException
	 */

	@Override
	public AbstractAirline parse() throws ParserException {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFileName);
			doc.getDocumentElement().normalize();

			NodeList rootList = doc.getElementsByTagName("name");
			Node airlineNode = rootList.item(0);
			String airlineName = airlineNode.getTextContent();
			Airline airline = new Airline(airlineName);

			NodeList flightList = doc.getElementsByTagName("flight");

			for (int i = 0; i < flightList.getLength(); i++) {
				Node currentNode = flightList.item(i);

				if(currentNode.getNodeType() == Node.ELEMENT_NODE) {

					Element elem = (Element) currentNode;

					Node numNode = elem.getElementsByTagName("number").item(0);
					int flightNum = Integer.parseInt(numNode.getTextContent());

					Node sourceNode = elem.getElementsByTagName("src").item(0);
					String source = sourceNode.getTextContent();

					Node departDate = elem.getElementsByTagName("date").item(0);
					String departDateStr = ((Element) departDate).getAttribute("day") + "/" +
							((Element) departDate).getAttribute("month") + "/" + ((Element) departDate).getAttribute("year");

					Node departTime = elem.getElementsByTagName("time").item(0);
					String departTimeStr = ((Element) departTime).getAttribute("hour") +
							":" + ((Element) departTime).getAttribute("minute");

					int departHour = Integer.parseInt(((Element) departTime).getAttribute("hour"));
					String departSuffix;
					if(departHour < 11) {
						departSuffix = "am";
					} else {
						departSuffix = "pm";
					}

					Node destNode = elem.getElementsByTagName("dest").item(0);
					String dest = destNode.getTextContent();

					Node arriveDate = elem.getElementsByTagName("date").item(1);
					String arriveDateStr = ((Element) arriveDate).getAttribute("day") + "/" +
							((Element) arriveDate).getAttribute("month") + "/" + ((Element) arriveDate).getAttribute("year");

					Node arriveTime = elem.getElementsByTagName("time").item(1);
					String arriveTimeStr = ((Element) arriveTime).getAttribute("hour") +
							":" + ((Element) arriveTime).getAttribute("minute");

					int arriveHour = Integer.parseInt(((Element) arriveTime).getAttribute("hour"));
					String arriveSuffix;
					if(arriveHour < 11) {
						arriveSuffix = "am";
					} else {
						arriveSuffix = "pm";
					}

					Flight flight = new Flight(flightNum, source, departDateStr, departTimeStr,
							departSuffix, dest, arriveDateStr, arriveTimeStr, arriveSuffix);
					airline.addFlight(flight);
				}
			}
			return airline;

		} catch (SAXException | ParserConfigurationException ex) {
			throw new ParserException(ex.getMessage());
		} catch (IOException e) {
			return null;
		}
	}

	public Airline parseStream(InputStream xmlData) throws ParserException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlData);
			doc.getDocumentElement().normalize();

			NodeList rootList = doc.getElementsByTagName("name");
			Node airlineNode = rootList.item(0);
			String airlineName = airlineNode.getTextContent();
			Airline airline = new Airline(airlineName);

			NodeList flightList = doc.getElementsByTagName("flight");

			for (int i = 0; i < flightList.getLength(); i++) {
				Node currentNode = flightList.item(i);

				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					Element elem = (Element) currentNode;

					Node numNode = elem.getElementsByTagName("number").item(0);
					int flightNum = Integer.parseInt(numNode.getTextContent());

					Node sourceNode = elem.getElementsByTagName("src").item(0);
					String source = sourceNode.getTextContent();

					Node departDate = elem.getElementsByTagName("date").item(0);
					String departDateStr = ((Element) departDate).getAttribute("day") + "/" +
							((Element) departDate).getAttribute("month") + "/" + ((Element) departDate).getAttribute("year");

					Node departTime = elem.getElementsByTagName("time").item(0);
					String departTimeStr = ((Element) departTime).getAttribute("hour") +
							":" + ((Element) departTime).getAttribute("minute");

					int departHour = Integer.parseInt(((Element) departTime).getAttribute("hour"));
					String departSuffix;
					if (departHour < 11) {
						departSuffix = "am";
					} else {
						departSuffix = "pm";
					}

					Node destNode = elem.getElementsByTagName("dest").item(0);
					String dest = destNode.getTextContent();

					Node arriveDate = elem.getElementsByTagName("date").item(1);
					String arriveDateStr = ((Element) arriveDate).getAttribute("day") + "/" +
							((Element) arriveDate).getAttribute("month") + "/" + ((Element) arriveDate).getAttribute("year");

					Node arriveTime = elem.getElementsByTagName("time").item(1);
					String arriveTimeStr = ((Element) arriveTime).getAttribute("hour") +
							":" + ((Element) arriveTime).getAttribute("minute");

					int arriveHour = Integer.parseInt(((Element) arriveTime).getAttribute("hour"));
					String arriveSuffix;
					if (arriveHour < 11) {
						arriveSuffix = "am";
					} else {
						arriveSuffix = "pm";
					}

					Flight flight = new Flight(flightNum, source, departDateStr, departTimeStr,
							departSuffix, dest, arriveDateStr, arriveTimeStr, arriveSuffix);
					airline.addFlight(flight);
				}
			}
			return airline;

		} catch (SAXException | ParserConfigurationException | IOException ex) {
			return null;
		}
	}

	}
