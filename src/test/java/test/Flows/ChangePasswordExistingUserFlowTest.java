package test.Flows;

import static constants.Data.*;
import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.Test;

import model.Authentication;
import services.Environment;

public class ChangePasswordExistingUserFlowTest extends Environment {
	public static Authentication user = new Authentication();
	public static Authentication reset = new Authentication();
	public static Authentication resetCheck = new Authentication();
	public String email = "gnu.teste@mailinator.com";
	
	@Test
	public void resetarSenha() {
		reset.setLogin(email);
	
		given()
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("OK"))
			.body("messages[0].text", is("E-mail sent"))
			.statusCode(200)
			;
		
		resetCheck.setLogin(email);
		resetCheck.setValidationCode(validCode);
		
		String passwordToken =
		given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("content.token", is(not(nullValue())))
			.body("content.token", matchesPattern("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]+$"))
			.body("status", is("OK"))
			.body("messages[0].text", is("Valid code"))
			.statusCode(200)
		.and()
			.extract().path("content.token")
			;
		
		user.setNewPassword("GNU");
		given()
			.header("Authorization", "Bearer " + passwordToken)
			.body(user)
		.when()
			.post(CHANGE_PASSWORD)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.statusCode(200);
	}
}
