package test.Authentication;

import org.junit.jupiter.api.Test;

import services.Environment;
import model.Authentication;
import static constants.Endpoints.*;
import static utils.Data.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class PasswordResetTest extends Environment{
	private static Authentication reset = new Authentication();
	
	
	@Test
	public void resetarSenha() {
		reset.setLogin(ValidLogin);
	
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
			.statusCode(200);
	}
	
	
	@Test
	public void naoResetarSenhaComLoginInexistente() {
		reset.setLogin(InvalidLogin);
	
		given()
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
		reset.setLogin(InvalidLoginCAPSLOCK);
	
		given()
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
		reset.setLogin(InvalidLoginCAPSLOCK2);
	
		given()
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
		reset.setLogin(InvalidLoginCAPSLOCK3);
	
		given()
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
		reset.setLogin(InvalidLoginWithoutAT);
	
		given()
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
		reset.setLogin(BlankLogin);
	
		given()
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
		reset.setLogin(EmptyLogin);
	
		given()
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
		reset.setLogin(InvalidLoginWithoutExtension);
	
		given()
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
		reset.setLogin(InvalidLoginEmoji);
	
		given()
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
		reset.setLogin(InvalidLoginEmoji2);
	
		given()
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
		reset.setLogin(InvalidLoginWithBlankChars);
	
		given()
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
