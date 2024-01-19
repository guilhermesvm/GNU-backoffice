package test.Flows;

import static constants.Data.*;
import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.statusCode(200)
			;
		
		resetCheck.setLogin(email);
		resetCheck.setValidationCode(validCode);
		
		String passwordToken =
		given()
			.header("x-Api-Key", apiKey)
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.statusCode(200)
		.and()
			.extract().path("content.token")
			;
		
		user.setNewPassword("GNU@123g");
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + passwordToken)
			.body(user)
		.when()
			.post(CHANGE_PASSWORD)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Password updated"))
			.statusCode(200);
	}
	
	@Test
	public void naoResetarSenhaMenorQue8Caracteres() {
		resetCheck.setLogin(email);
		resetCheck.setValidationCode(validCode);
		
		String passwordToken =
		given()
			.header("x-Api-Key", apiKey)
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.extract().path("content.token")
			;
		
		user.setNewPassword("GNU");
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + passwordToken)
			.body(user)
		.when()
			.post(CHANGE_PASSWORD)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("This password is weak"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaSemLetraMaiuscula() {
		resetCheck.setLogin(email);
		resetCheck.setValidationCode(validCode);
		
		String passwordToken =
		given()
			.header("x-Api-Key", apiKey)
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.extract().path("content.token")
			;
		
		user.setNewPassword("gnu@123g");
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + passwordToken)
			.body(user)
		.when()
			.post(CHANGE_PASSWORD)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("This password is weak"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaSemLetraMinuscula() {
		resetCheck.setLogin(email);
		resetCheck.setValidationCode(validCode);
		
		String passwordToken =
		given()
			.header("x-Api-Key", apiKey)
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.extract().path("content.token")
			;
		
		user.setNewPassword("GNU@123G");
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + passwordToken)
			.body(user)
		.when()
			.post(CHANGE_PASSWORD)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("This password is weak"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaSemNumero() {
		resetCheck.setLogin(email);
		resetCheck.setValidationCode(validCode);
		
		String passwordToken =
		given()
			.header("x-Api-Key", apiKey)
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.extract().path("content.token")
			;
		
		user.setNewPassword("GNU@GGGG");
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + passwordToken)
			.body(user)
		.when()
			.post(CHANGE_PASSWORD)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("This password is weak"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaSemCaracterEspecial() {
		resetCheck.setLogin(email);
		resetCheck.setValidationCode(validCode);
		
		String passwordToken =
		given()
			.header("x-Api-Key", apiKey)
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.extract().path("content.token")
			;
		
		user.setNewPassword("GNUG123G");
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + passwordToken)
			.body(user)
		.when()
			.post(CHANGE_PASSWORD)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("This password is weak"))
			.statusCode(400);
	}
	
}
