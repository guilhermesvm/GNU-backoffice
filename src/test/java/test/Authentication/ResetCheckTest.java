package test.Authentication;

import org.junit.jupiter.api.Test;

import model.Authentication;
import services.Environment;
import static utils.Data.*;
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
		resetCheck.setLogin(ValidLogin);
		resetCheck.setValidationCode(ValidCode);;
		
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
		 resetCheck.setLogin(ValidLogin);
		 resetCheck.setValidationCode(InvalidCode);
		 
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
		 resetCheck.setLogin(ValidLogin);
		 resetCheck.setValidationCode(BlankCode);
		 
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
		 resetCheck.setLogin(ValidLogin);
		 resetCheck.setValidationCode(EmptyCode);
		 
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
		 resetCheck.setLogin(InvalidLogin);
		 resetCheck.setValidationCode(ValidCode);
		 
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
		 resetCheck.setLogin(InvalidLoginCAPSLOCK);
		 resetCheck.setValidationCode(ValidCode);
		 
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
		 resetCheck.setLogin(InvalidLoginCAPSLOCK2);
		 resetCheck.setValidationCode(ValidCode);
		 
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
		 resetCheck.setLogin(InvalidLoginCAPSLOCK3);
		 resetCheck.setValidationCode(ValidCode);
		 
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
		 resetCheck.setLogin(InvalidLoginWithoutAT);
		 resetCheck.setValidationCode(ValidCode);
		 
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
		 resetCheck.setLogin(BlankLogin);
		 resetCheck.setValidationCode(ValidCode);
		 
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
		 resetCheck.setLogin(EmptyLogin);
		 resetCheck.setValidationCode(ValidCode);
		 
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
	public void naoUsarCodigoDeResetValidoComEmailSemDominio() {
		 resetCheck.setLogin(InvalidLoginWithoutExtension);
		 resetCheck.setValidationCode(ValidCode);
		 
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
		 resetCheck.setLogin(InvalidLoginEmoji);
		 resetCheck.setValidationCode(ValidCode);
		 
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
		 resetCheck.setLogin(InvalidLoginEmoji2);
		 resetCheck.setValidationCode(ValidCode);
		 
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
		 resetCheck.setLogin(InvalidLoginWithBlankChars);
		 resetCheck.setValidationCode(ValidCode);
		 
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
