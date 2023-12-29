package test.EventSpaceReservation;


import static constants.Endpoints.*;

import static io.restassured.RestAssured.*;
import static services.LoginService.login;
import static utils.Data.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import model.Authentication;
import services.Environment;


public class EventSpaceReservationTest extends Environment {
	public static Authentication login = new Authentication();
	public String accessToken = login.getToken();
	
	@BeforeAll
	public static void fazerLogin() {
		String token = login();
		login.setToken(token);
	}
	
	
	@Test
	public void listarReservas() {
		given()
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(EVENT_RESERVATION)
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void naoListarReservasSemToken() {
		given()
			.header("Authorization", "Bearer " + emptyToken)
		.when()
			.get(EVENT_RESERVATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void listarReservasPorId() {
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 2)
		.when()
			.get(EVENT_RESERVATION_ID)
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void naoListarReservasPorIdSemToken() {
		given()
			.header("Authorization", "Bearer " + emptyToken)
			.pathParam("id", 2)
		.when()
			.get(EVENT_RESERVATION_ID)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoListarReservasSemIdValido() {
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", InvalidId)
		.when()
			.get(EVENT_RESERVATION_ID)
		.then()
			.log().all()
			.statusCode(400);
	}
	

}
