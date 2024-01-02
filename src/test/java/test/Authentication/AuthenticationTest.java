package test.Authentication;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static constants.Endpoints.*;
import static utils.Data.*;
import static services.LoginService.*;

import model.Authentication;
import org.junit.jupiter.api.Test;
import services.Environment;

public class AuthenticationTest extends Environment{
	private static Authentication login = new Authentication();
	
	@Test
	public void fazerLogin() {
		login = loginAccount();
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
		.assertThat()
			.statusCode(200)
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content.token", is(not(nullValue())))
			.body("content.token", matchesPattern("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]+$"))
			;
	}		
	
	@Test
	public void naoFazerLoginComEmailInvalido() {
		login.setLogin(InvalidLogin);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}

	@Test
	public void naoFazerLoginComSenhaInvalida() {
		login.setLogin(ValidLogin);
		login.setPassword(InvalidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailEmCAPSLOCK() {
		login.setLogin(InvalidLoginCAPSLOCK);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailEmCAPSLOCKAntesDoArroba() {
		login.setLogin(InvalidLoginCAPSLOCK2);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailEmCAPSLOCKDepoisDoArroba() {
		login.setLogin(InvalidLoginCAPSLOCK3);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCredenciaisInvalidas() {
		login.setLogin(InvalidLogin);
		login.setPassword(InvalidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginSemArroba() {
		login.setLogin(InvalidLoginWithoutAT);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCredenciaisEmBranco() {
		login.setLogin(BlankLogin);
		login.setPassword(BlankPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailEmBranco() {
		login.setLogin(BlankLogin);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComSenhaEmBranco() {
		login.setLogin(ValidLogin);
		login.setPassword(BlankPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCredenciaisVazias() {
		login.setLogin(EmptyLogin);
		login.setPassword(EmptyPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEmailVazio() {
		login.setLogin(EmptyLogin);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComSenhaVazia() {
		login.setLogin(ValidLogin);
		login.setPassword(EmptyPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginEmailCadastradoSemDominio() {
		login.setLogin(InvalidLoginWithoutExtension);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCaracteresEspeciaisNoEmailAntesDoArroba() {
		login.setLogin(InvalidLoginEmoji);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComCaracteresEspeciaisNoEmailDepoisDoArroba() {
		login.setLogin(InvalidLoginEmoji2);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoFazerLoginComEspacosNoEmail() {
		login.setLogin(InvalidLoginWithBlankChars);
		login.setPassword(ValidPassword);
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(401);
	}
}
