package test.Authentication;

import org.junit.jupiter.api.Test;

import model.Authentication;
import services.Environment;

import static constants.Data.*;
import static constants.Endpoints.PASSWORD_RESET_CHECK;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class ResetCheckTest extends Environment {
	private static Authentication resetCheck = new Authentication();
	
	@Test
	public void usarCodigoDeReset() {
		resetCheck.setLogin(validLogin);
		resetCheck.setValidationCode(validCode);
		
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
			.statusCode(200);
	}
	
	@Test
	public void naoUsarCodigoDeResetInvalido() {
		 resetCheck.setLogin(validLogin);
		 resetCheck.setValidationCode(invalidCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("Invalid code"))
			.statusCode(400);
	}
	
	@Test
	public void naoUsarCodigoDeResetVazio() {
		 resetCheck.setLogin(validLogin);
		 resetCheck.setValidationCode(blankCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("Invalid code"))
			.statusCode(400);
	}
	
	@Test
	public void naoUsarCodigoDeResetEmBranco() {
		 resetCheck.setLogin(validLogin);
		 resetCheck.setValidationCode(emptyCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("BadRequest"))
			.body("messages[0].text", is("Invalid code"))
			.statusCode(400);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailInvalido() {
		 resetCheck.setLogin(invalidLogin);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailEmCAPSLOCK() {
		 resetCheck.setLogin(invalidLoginCAPSLOCK);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailEmCAPSLOCKAntesdoArroba() {
		 resetCheck.setLogin(invalidLoginCAPSLOCK2);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailEmCAPSLOCKDepoisdoArroba() {
		 resetCheck.setLogin(invalidLoginCAPSLOCK3);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailSemArroba() {
		 resetCheck.setLogin(invalidLoginWithoutAT);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailEmBranco() {
		 resetCheck.setLogin(blankLogin);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailVazio() {
		 resetCheck.setLogin(emptyLogin);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailSemDominio_BUG() {
		 resetCheck.setLogin(invalidLoginWithoutExtension);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailComCaracterEspecialAntesDoArroba() {
		 resetCheck.setLogin(invalidLoginEmoji);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailComCaracterEspecialDepoisDoArroba() {
		 resetCheck.setLogin(invalidLoginEmoji2);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}
	
	@Test
	public void naoUsarCodigoDeResetValidoComEmailComEspaçosEmBranco() {
		 resetCheck.setLogin(invalidLoginWithBlankChars);
		 resetCheck.setValidationCode(validCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("NotFound"))
			.body("messages[0].text", is("User not found"))
			.statusCode(404);
	}


}
