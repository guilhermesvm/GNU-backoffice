package test.Authentication;

import org.junit.jupiter.api.Test;

import services.Environment;
import model.Authentication;

import static constants.Data.*;
import static constants.Endpoints.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class PasswordResetTest extends Environment{
	private static Authentication reset = new Authentication();
	
	@Test
	public void resetarSenha() {
		reset.setLogin(validLogin2);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("OK"))
			.body("messages[0].text", is("E-mail sent"))
			.statusCode(200);
	}
	
	@Test
	public void naoResetarSenhaSemApiKey() {
		reset.setLogin(validLogin2);
	
		given()
			.header("x-Api-Key", invalidApiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.statusCode(401)
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);

	}
	
	@Test
	public void naoResetarSenhaComLoginInexistente() {
		reset.setLogin(invalidLogin);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaComEmailCAPSLOCK() {
		reset.setLogin(invalidLoginCAPSLOCK);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaComEmailCAPSLOCKAntesDoArroba() {
		reset.setLogin(invalidLoginCAPSLOCK2);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaComEmailCAPSLOCKDepoisDoArroba() {
		reset.setLogin(invalidLoginCAPSLOCK3);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaComEmailSemArroba() {
		reset.setLogin(invalidLoginWithoutAT);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
			.assertThat()
		.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaComEmailEmBranco() {
		reset.setLogin(blankLogin);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaComEmailVazio() {
		reset.setLogin(emptyLogin);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaComEmailSemPontoCom() {
		reset.setLogin(invalidLoginWithoutExtension);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaCaracteresEspeciaisNoEmailAntesDoArroba() {
		reset.setLogin(invalidLoginEmoji);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaCaracteresEspeciaisNoEmailDepoisDoArroba() {
		reset.setLogin(invalidLoginEmoji2);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoResetarSenhaEnviandoEmailComEspacos() {
		reset.setLogin(invalidLoginWithBlankChars);
	
		given()
			.header("x-Api-Key", apiKey)
			.body(reset)
		.when()
			.post(PASSWORD_RESET)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}

}
