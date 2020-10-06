package edu.pdx.cs410J.riley34;

import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

/**
 * Unit tests for the {@link PrettyPrinter} class.
 */

public class PrettyPrinterTest {

	@Test
	public void x () throws IOException {
		PrettyPrinter print = new PrettyPrinter("file.txt");
		Airline airline = new Airline("Air1");
		String testStr =  print.commandLinePrint(airline);
		assertThat(testStr, equalTo("The Airline Air1 Includes The Flights: \n"));
	}

}
