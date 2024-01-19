package test.Health;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

import static constants.Data.apiKey;
import static constants.Data.invalidApiKey;
import static constants.Endpoints.HEALTH;
import static io.restassured.RestAssured.*;
import services.Environment;

public class HealthTest extends Environment {
	
	@Test
	public void listarStatusAPI() {
		given()
			.header("x-Api-Key", apiKey)
		.when()
			.get(HEALTH)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.body(containsString("exception"))
			.body(containsString("status"))
			.body("status", is(not(nullValue())))
			.body("status", is("OK"))
			.body("messages[0].text", is("API active"))
			.statusCode(200);
	}
	
	@Test
	public void naoListarStatusAPISemApiKey_BUG() {
		given()
			.header("x-Api-Key", invalidApiKey)
		.when()
			.get(HEALTH)
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
