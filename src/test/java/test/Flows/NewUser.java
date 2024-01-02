package test.Flows;

import org.junit.jupiter.api.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Authentication;
import model.User;
import services.Environment;

import static services.UserService.*;
import static utils.Data.*;
import static utils.DataFaker.*;
import static constants.Endpoints.*;
import static services.LoginService.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static utils.DataFaker.*;


public class NewUser extends Environment {
	public static Authentication login = new Authentication();
	public static User user = new User();
	public static User alteracao = new User();
	//public String accessToken = login.getToken();
	public String accessToken = login();
	
	@Test
	public void criarTokenPrimeiroAcesso() {
		user = createUser();
		
		Response response =
			    given()
			        .header("Authorization", "Bearer " + accessToken)
			        .body(user)
			    .when()
			        .post(USERS)
			    .then()
			        .extract().response();
		JsonPath jsonPath = response.jsonPath();
		Integer id = jsonPath.getInt("content.id");
		String email = jsonPath.getString("content.email");

		user.setId(id);
		user.setEmail(email); // Define o e-mail no objeto 'user'
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.post(CREATE_USER_EMAILACCESS)
		.then()
			.log().all()
			.statusCode(200)
		;
		given()
		.pathParam("id", id)
		.header("Authorization", "Bearer " + accessToken)
	.when()
		.delete(DELETE_USER)
	.then()
		.statusCode(200);
	}
	
	@Test
	public void checarTokendePrimeiroAcesso() {
		given()
			.header("Authorization", " Bearer " + firstAccessToken)
		.when()
			.get(CHECK_USER_FIRSTACCESS)
		.then()
			.log().all()
			.statusCode(200)
			;
	}
	
	@Test
	public void naoChecarTokendePrimeiroAcessoSemToken() {
		given()
			.header("Authorization", " Bearer " + emptyToken)
		.when()
			.get(CHECK_USER_FIRSTACCESS)
		.then()
			.log().all()
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
			.statusCode(401)
			;
	}
	
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
	
	@Test
	public void definirSenhaNovoUsuario_ERRO() {
		user.setNewPassword(ValidPassword);
		
		given()
			.body(user)
			.header("Authorization", "Bearer " + passwordToken)
		.when()
			.post(CHANGE_PASSWORD)
		.then()
			.log().all()
			.statusCode(200)
		;
	}
	
	@Test
	public void alterarUsuario() {
		Integer id = creatingUser();
		user.setId(id);

		alteracao.setName("joaobatata");
		alteracao.setActive(false);
		alteracao.setAdminSectionIds(fakerAdminSectionIds);
		alteracao.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", user.getId())
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("content.id")
			;
		deletingUser(user.getId());
	}
	
	@Test 
	public void deletarUsuario() {
		Integer id = creatingUser();
		given()
			.pathParam("id", id)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.delete(DELETE_USER)
		.then()
			.log().all()
			.statusCode(200)
			;

	}
}
