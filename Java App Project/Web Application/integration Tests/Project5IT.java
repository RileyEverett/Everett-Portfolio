package edu.pdx.cs410J.riley34;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.riley34.AirlineRestClient.AirlineRestException;
import org.hamcrest.CoreMatchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

	private MainMethodResult invokeMain(String... args) {
		return invokeMain(Project5.class, args);
	}

    @Test
    public void test0() {
    	MainMethodResult result = invokeMain("-host", "localhost", "-port", "8080", "Air1");
    }
}