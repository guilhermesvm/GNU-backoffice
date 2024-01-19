package test.EventSpaceReservation;


import static constants.Data.*;
import static constants.Endpoints.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.Test;
import model.Authentication;
import services.Environment;


public class EventSpaceReservationTest extends Environment {
	public static Authentication login = new Authentication();

	
	
	@Test
	public void listarReservas() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(EVENT_RESERVATION)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("count"))
			.body("count", is(not(nullValue())))
			.body(containsString("pageCount"))
			.body("pageCount", is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.statusCode(200);
	}
	
	@Test
	public void naoListarReservasSemToken() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + emptyToken)
		.when()
			.get(EVENT_RESERVATION)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
	
	@Test
	public void naoListarReservasSemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(EVENT_RESERVATION)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
	
	@Test
	public void listarReservasPorId() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 2)
		.when()
			.get(EVENT_RESERVATION_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is(not(nullValue())))
			.body("status", is("OK"))
			.statusCode(200);
	}
	
	@Test
	public void naoListarReservasPorIdSemToken() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + emptyToken)
			.pathParam("id", 2)
		.when()
			.get(EVENT_RESERVATION_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
	
	@Test
	public void naoListarReservasPorIdSemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 2)
		.when()
			.get(EVENT_RESERVATION_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
	
	@Test
	public void naoListarReservasComIdInvalido() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", invalidId)
		.when()
			.get(EVENT_RESERVATION_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Reservation not found"))
			.statusCode(400);
	}
	

}
