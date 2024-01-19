package test.Utils;

import services.Environment;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static constants.Data.*;
import static constants.Endpoints.UTILS;
import static io.restassured.RestAssured.*;


public class UtilsTest extends Environment {
	
	@Test
	public void listarReportsAPI() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(UTILS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.body(containsString("reservations"))
			//.body("reservations", is(not(nullValue())))
			.body(containsString("tickets"))
			//.body("tickets", is(not(nullValue())))
			.statusCode(200);
	}
	
	@Test
	public void naoListarReportsAPISemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(UTILS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
}
