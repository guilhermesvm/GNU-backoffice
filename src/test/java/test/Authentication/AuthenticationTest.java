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
			.statusCode(200);
	}
	
	@Test
	public void fazerLoginEVerificarToken() {
		login = loginAccount();
		
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.log().all()
			.statusCode(200)
			.body("content.token", is(not(nullValue())))
			.extract()
				.path("content.token");
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
	public void naoFazerLoginEmailCadastradoSemPontoCom() {
		login.setLogin(InvalidLoginWithoutDotCom);
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
		login.setLogin(InvalidLoginSPECIAL);
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
	public void naoFazerLoginComCaracteresEspeciaisNoEmailDepoisDoArroba_BUG() {
		login.setLogin(InvalidLoginSPECIAL2);
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
