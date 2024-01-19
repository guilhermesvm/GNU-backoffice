package test.Authentication;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static constants.Data.*;
import static constants.Endpoints.*;
import static services.LoginService.*;

import model.Authentication;
import org.junit.jupiter.api.Test;

import io.restassured.module.jsv.JsonSchemaValidator;
import services.Environment;

public class AuthenticationTest extends Environment{
	private static Authentication login = new Authentication();
	
	@Test
	public void fazerLogin() {
		login = loginAccount();
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
		.assertThat()
			.statusCode(400)
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content.token", is(not(nullValue())))
			.body("content.token", matchesPattern("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]+$"))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/authentication/post/200.json"));
	}
	
	@Test
	public void naoFazerLoginSemApiKey() {
		login = loginAccount();
		
		given()
			.header("x-Api-Key", invalidApiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
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
	public void naoFazerLoginComEmailInvalido() {
		login.setLogin(invalidLogin);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}

	@Test
	public void naoFazerLoginComSenhaInvalida() {
		login.setLogin(validLogin);
		login.setPassword(invalidPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailEmCAPSLOCK() {
		login.setLogin(invalidLoginCAPSLOCK);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailEmCAPSLOCKAntesDoArroba() {
		login.setLogin(invalidLoginCAPSLOCK2);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailEmCAPSLOCKDepoisDoArroba() {
		login.setLogin(invalidLoginCAPSLOCK3);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCredenciaisInvalidas() {
		login.setLogin(invalidLogin);
		login.setPassword(invalidPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginSemArroba() {
		login.setLogin(invalidLoginWithoutAT);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCredenciaisEmBranco() {
		login.setLogin(blankLogin);
		login.setPassword(blankPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailEmBranco() {
		login.setLogin(blankLogin);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComSenhaEmBranco() {
		login.setLogin(validLogin);
		login.setPassword(blankPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCredenciaisVazias() {
		login.setLogin(emptyLogin);
		login.setPassword(emptyPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailVazio() {
		login.setLogin(emptyLogin);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComSenhaVazia() {
		login.setLogin(validLogin);
		login.setPassword(emptyPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginEmailCadastradoSemDominio() {
		login.setLogin(invalidLoginWithoutExtension);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCaracteresEspeciaisNoEmailAntesDoArroba() {
		login.setLogin(invalidLoginEmoji);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCaracteresEspeciaisNoEmailDepoisDoArroba() {
		login.setLogin(invalidLoginEmoji2);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEspacosNoEmail() {
		login.setLogin(invalidLoginWithBlankChars);
		login.setPassword(validPassword);
		
		given()
			.header("x-Api-Key", apiKey)
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
}
