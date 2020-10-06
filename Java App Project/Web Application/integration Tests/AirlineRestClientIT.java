package edu.pdx.cs410J.riley34;

import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

  @Test @Order(1)
	public void testThatAirlineCanBePostedToServer() throws IOException {
  	AirlineRestClient client = new AirlineRestClient(HOSTNAME, 8080);
  	Airline airline = new Airline("air1");
  	Flight argsFlight = new Flight(117, "PDX", "1/1/2020", "8:00", "am", "LAX", "1/1/2020", "12:00", "pm");
	  Map<String, String> parameters = new HashMap<>();
	  parameters.put("airlineName", airline.getName());
	  parameters.put("flightNumber", Integer.toString(argsFlight.getNumber()));
	  parameters.put("src", argsFlight.getSource());
	  parameters.put("depart", argsFlight.getDepartureString().replaceAll("[,]", ""));
	  parameters.put("dest", argsFlight.getDestination());
	  parameters.put("arrive", argsFlight.getArrivalString().replaceAll("[,]", ""));
	  HttpRequestHelper.Response response = client.postToMyURL(parameters);
	  String responseString = response.getContent();
	  assertThat(responseString, containsString("Added Flight 117 to air1."));
  }
}
