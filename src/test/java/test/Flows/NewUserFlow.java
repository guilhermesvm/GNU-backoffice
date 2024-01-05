package test.Flows;

import org.junit.jupiter.api.Test;
import model.Authentication;
import model.User;
import services.Environment;

import static constants.Data.*;
import static constants.Endpoints.*;
import static services.LoginService.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class NewUserFlow extends Environment {
	public static Authentication login = new Authentication();
	public static User user = new User();
	public static String accessToken = login();
	public static String firstAccessToken = "";
	
	@Test
	public void newUserSucessFlow() {
		
	}
	/*@Test
	public void criarTokenDePrimeiroAcesso() {
		Integer id = creatingUser();
		given()
			.header("Authorization", " Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.post(CREATE_USER_EMAIL_FIRST_ACCESS)
		.then()
			.log().all()
		.assertThat()
		.statusCode(200)
		;
		deletingUser(id);
	}*/
	
	@Test
	public void checarTokenDePrimeiroAcesso_BUG() {
		given()
			.header("Authorization", " Bearer " + firstAccessToken)
		.when()
			.get(CHECK_USER_FIRSTACCESS)
		.then()
			.log().all()
		.assertThat()
//			.body(is(not(nullValue())))
//			.body(containsString("content"))
//			.body("content", is(not(nullValue())))
//			.body(containsString("messages"))
//			.body("messages[0].text", is("Valid code"))
//			.statusCode(200)
			;
	}
	
	@Test
	public void naoChecarTokendePrimeiroAcessoTokenVazio() {
		given()
			.header("Authorization", " Bearer " + emptyToken)
		.when()
			.get(CHECK_USER_FIRSTACCESS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401)
			;
	}
	
	@Test
	public void naoChecarTokendePrimeiroAcessoInvalido() {
		given()
			.header("Authorization", " Bearer " + invalidToken)
		.when()
			.get(CHECK_USER_FIRSTACCESS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401)
			;
	}
	
	@Test
	public void naoChecarTokendePrimeiroAcessoJaUsado() {
		given()
			.header("Authorization", " Bearer " + usedFirstAccessToken)
		.when()
			.get(CHECK_USER_FIRSTACCESS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401)
			;
	}
	
//	@Test
//	public void usarTokenDePrimeiroAcesso() {
//		user.setName(" ");
//		user.setEmail(" ");
//		
//		given()
//			.header("Authorization", " Bearer " + firstAccessToken)
//			.body(user)
//		.when()
//			.post(INSERT_FIRSTACCESS_TOKEN)
//		.then()
//			.statusCode(200)
//		;
//	}
	
	@Test
	public void naoUsarTokenDePrimeiroAcessoComEmailNaoCorrespondente() {
		user.setName("juninho");
		user.setEmail("jasduiasdiasdiasdkasjkdjk2324@gmail.com");
		
		given()
			.body(user)
			.header("Authorization", " Bearer " + firstAccessToken)
		.when()
			.post(INSERT_FIRSTACCESS_TOKEN)
		.then()
			.log().all()
			.statusCode(401);	
	}
	
	@Test
	public void naoUsarTokenDePrimeiroAcessoSemToken() {
		user.setName("juninho");
		user.setEmail("jasduiasdiasdiasdkasjkdjk2324@gmail.com");
		
		given()
			.body(user)
			.header("Authorization", " Bearer " + emptyToken)
		.when()
			.post(INSERT_FIRSTACCESS_TOKEN)
		.then()
			.log().all()
			.statusCode(401);	
	}
	

	
}
