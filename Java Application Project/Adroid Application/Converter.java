package edu.pdx.cs410j.riley34.airlineapp;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;

public class Converter {


	public static void main(String[] argv) {
		if(argv.length < 2) {
			usage();
		}
		try {
			String textFileName = argv[0];
			String xmlFileName = argv[1];
			convert(textFileName, xmlFileName);
		} catch (IOException | ParserException ex) {
			System.out.println("Text File or Xml File Has a Malformation");
			System.exit(1);
		}
		System.exit(0);
	}

	private static void convert(String textFileName, String xmlFileName) throws IOException, ParserException {
		TextParser parser = new TextParser(textFileName);
		XmlDumper dumper = new XmlDumper(xmlFileName);
		Airline airline = (Airline) parser.parse();
		dumper.dump(airline);
	}

	private static void usage() {
		System.out.println("Invalid Input!");
		System.out.println("Usage: java edu.pdx.cs410J.riley34.Converter textFile xmlFile");
		System.exit(1);
	}
}
