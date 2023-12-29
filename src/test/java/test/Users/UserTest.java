package test.Users;

import org.junit.jupiter.api.BeforeAll;
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
import java.util.List;

import static io.restassured.RestAssured.*;

public class UserTest extends Environment {
	public static Authentication login = new Authentication();
	public static User user = new User();
	public static User alteracao = new User();
	public String accessToken = login.getToken();
	static List<Integer> randomAdminSectionIds = randomAdminSections();
	static List<Integer> randomSubjectsIds = randomSubjects();
	
	@BeforeAll
	public static void fazerLogin() {
		String token = login();
		login.setToken(token);
	}

	
	@Test
	public void listarUsers() {
		given()
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(USERS)
		.then()
			.log().all()
			.statusCode(200);
			;
	}
	
	@Test
	public void naoListarUsersSemToken() {
		given()
			.header("Authorization", "Bearer " + emptyToken)
		.when()
			.get(USERS)
		.then()
			.log().all()
			.statusCode(401);
			;
	}
	
	@Test
	public void listarUserPorId() {
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 1)
		.when()
			.get(USER_ID)
		.then()
			.log().all()
			.statusCode(200);
			;
	}
	
	@Test
	public void naoListarUserPorIdInvalido() {
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 5235534)
		.when()
			.get(USER_ID)
		.then()
			.log().all()
			.statusCode(400);
			;
	}
	
	@Test
	public void naoListarUserPorIdSemToken() {
		given()
			.header("Authorization", "Bearer " + emptyToken)
			.pathParam("id", 1)
		.when()
			.get(USER_ID)
		.then()
			.log().all()
			.statusCode(401);
			;
	}
	
	@Test
	public void listarUserSectionPorId() {
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 1)
		.when()
			.get(USER_ID_SECTIONS)
		.then()
			.log().all()
			.statusCode(200);
			;
	}
	
	@Test
	public void naoListarUserSectionPorIdSemToken() {
		given()
			.header("Authorization", "Bearer " + emptyToken)
			.pathParam("id", 1)
		.when()
			.get(USER_ID_SECTIONS)
		.then()
			.log().all()
			.statusCode(401);
			;
	}
	
	@Test
	public void naoListarUserSectionPorIdInvalido() {
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", InvalidId)
		.when()
			.get(USER_ID_SECTIONS)
		.then()
			.log().all()
			.statusCode(400);
			;
	}

	@Test
	public void criarUsuario() {
		user.setEmail(emailFaker);
		user.setName(nameFaker);
		user.setAdminSectionIds(randomAdminSectionIds);
		user.setAdminSectionIds(randomSubjectsIds);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
			.statusCode(201)
			.extract().path("content.id")
			;
		deletingUser(id);
	}
		
	@Test
	public void naoCriarUsuarioSemToken() {
		user = createUser();
		
		given()
			.header("Authorization", "Bearer " + emptyToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void naoCriarUsuarioComEmailJaExistente() {	
		user.setEmail("guilherme.machado@digitalbusiness.com.br");
		user.setName(nameFaker);
		user.setAdminSectionIds(randomAdminSectionIds);
		user.setAdminSectionIds(randomSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
			.statusCode(400);
	}
	
	@Test
	public void criarTokenPrimeiroAcesso() {
		user.setEmail(emailFaker);
		user.setName(nameFaker);
		user.setAdminSectionIds(randomAdminSectionIds);
		user.setAdminSectionIds(randomSubjectsIds);
		
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
		alteracao.setAdminSectionIds(randomAdminSectionIds);
		alteracao.setSubjectIds(randomSubjectsIds);
		
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
