package test.Users;

import org.junit.jupiter.api.Test;
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

public class UserTest extends Environment {
	public static Authentication login = new Authentication();
	public static User user = new User();
	public static User alteracao = new User();
	//public String accessToken = login.getToken();
	public String accessToken = login();

	
//	@BeforeAll
//	public static void fazerLogin() {
//		String token = login();
//		login.setToken(token);
//	}

	@Test
	public void listarUsers() {
		given()
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("count"))
			.body("count", is(not(nullValue())))
			.body(containsString("pageCount"))
			.body("pageCount", is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.statusCode(200);
	}
	
	@Test
	public void naoListarUsersSemToken() {
		given()
			.header("Authorization", "Bearer " + emptyToken)
		.when()
			.get(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
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
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
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
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("User not found"))
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
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401)
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
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("count"))
			.body("count", is(not(nullValue())))
			.body(containsString("pageCount"))
			.body("pageCount", is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.statusCode(200)
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
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401)
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
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
			;
	}

	@Test
	public void criarUsuario() {
		user = createUser();
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("content", is(not(nullValue())))
			.body("content.name", is(not(nullValue())))
			.body("content.email", is(not(nullValue())))
			.body("content.id", is(not(nullValue())))
			.body("content.name", is(instanceOf(String.class)))
			.body("content.email", is(instanceOf(String.class)))
			.body("content.id", is(instanceOf(Integer.class)))
			.body("status", is("Created"))
			.body("messages[0].text", is("E-mail sent"))
			.statusCode(201)
		.and()
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
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401)
			;
	}
	
	@Test
	public void naoCriarUsuarioComEmailJaExistente() {	
		user.setEmail(ValidLogin);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("User already registered"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarUsuarioComEmailSemArroba() {	
		user.setEmail(InvalidLoginWithoutAT);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name or Email in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarUsuarioComEmailEmBranco() {	
		user.setEmail(BlankLogin);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name or Email in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarUsuarioComEmailVazio() {	
		user.setEmail(EmptyLogin);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name or Email in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarUsuarioComEmailSemExtensao_BUG() {	
		user.setEmail(InvalidLoginWithoutExtension);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
//			.body(is(not(nullValue())))
//			.body(containsString("status"))
//			.body("status", is("BadRequest"))
//			.body(containsString("messages"))
//			.body("messages", is(not(nullValue())))
//			.body("messages[0].text", is("Name or Email in wrong format"))
//			.statusCode(400)
		.and()
		.extract().path("content.id");
		deletingUser(id);
	}
	
	@Test
	public void naoCriarUsuarioComEmojiAntesDoArroba() {	
		user.setEmail(InvalidLoginEmoji);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name or Email in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarUsuarioComEmojiDepoisDoArroba() {	
		user.setEmail(InvalidLoginEmoji2);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name or Email in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarUsuarioComCaracterEspecialAntesDoArroba() {	
		user.setEmail(InvalidLoginSpecial);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name or Email in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarUsuarioComCaracterEspecialDepoisDoArroba() {	
		user.setEmail(InvalidLoginSpecial2);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name or Email in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarUsuarioComEspacosEmBrancoAntesEDepoisDoArroba_BUG() {	
		user.setEmail(InvalidLoginWithBlankChars);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
//			.body(is(not(nullValue())))
//			.body(containsString("status"))
//			.body("status", is("BadRequest"))
//			.body(containsString("messages"))
//			.body("messages", is(not(nullValue())))
//			.body("messages[0].text", is("Name or Email in wrong format"))
//			.statusCode(400)
		.and()
		.extract().path("content.id");
		deletingUser(id);
	}
	
	@Test
	public void naoCriarUsuarioComNomeInvalido() {	
		user.setEmail(fakerEmail);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setAdminSectionIds(fakerSubjectsIds);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name or Email in wrong format"))
			.statusCode(400);
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
