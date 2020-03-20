package edu.pdx.cs410J.riley34;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link XmlParser} class.
 */

public class XmlParserTest {

	@Test
	public void textParserCorrectlyBuildsAirline() throws ParserException {
		XmlParser parser = new XmlParser("riley34/airline.xml");
		Airline airline = (Airline) parser.parse();
		assertThat(airline.getName(), containsString("air1"));
	}

}
