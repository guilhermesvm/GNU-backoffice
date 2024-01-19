package test.Users;

import org.junit.jupiter.api.Test;
import model.Authentication;
import model.User;
import services.Environment;

import static services.UserService.*;
import static constants.Data.*;
import static constants.DataFaker.*;
import static constants.Endpoints.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserTest extends Environment {
	public static Authentication login = new Authentication();
	public static User user = new User();
	public static User alteracao = new User();

	@Test
	public void listarUsers() {
		given()
			.header("x-Api-Key", apiKey)
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
			.header("x-Api-Key", apiKey)
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
	}
	
	@Test
	public void naoListarUsersSemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
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
	}
	
	@Test
	public void listarUserPorId() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 31)
		.when()
			.get(USER_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.statusCode(200);
	}
	
	@Test
	public void naoListarUserPorIdInvalido() {
		given()
			.header("x-Api-Key", apiKey)
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
			.statusCode(400);;
	}
	
	@Test
	public void naoListarUserPorIdSemToken() {
		given()
			.header("x-Api-Key", apiKey)
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
			.statusCode(401);
	}
	
	@Test
	public void naoListarUserPorIdSemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
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
			.statusCode(401);
	}
	
	@Test
	public void listarUserSectionPorId() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 31)
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
			.statusCode(200);
	}
	
	@Test
	public void naoListarUserSectionPorIdSemToken() {
		given()
			.header("x-Api-Key", apiKey)
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
			.statusCode(401);
	}
	
	@Test
	public void naoListarUserSectionPorIdSemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
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
			.statusCode(401);
	}
	
	@Test
	public void naoListarUserSectionPorIdInvalido() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", invalidId)
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
	}

	@Test
	public void criarUsuario() {
		user = creatingUser();
		
		Integer id =
		given()
			.header("x-Api-Key", apiKey)
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
		deleteUser(id);
	}
		
	@Test
	public void naoCriarUsuarioSemToken() {
		user = creatingUser();
		
		given()
			.header("x-Api-Key", apiKey)
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
			.statusCode(401);
	}
	
	@Test
	public void naoCriarUsuarioSemApiKey() {
		user = creatingUser();
		
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
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
			.statusCode(401);
	}
	
	@Test
	public void naoCriarUsuarioComEmailJaExistente() {	
		user.setEmail(validLogin);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
		user.setEmail(invalidLoginWithoutAT);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
		user.setEmail(blankLogin);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
		user.setEmail(emptyLogin);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComEmailSemExtensao() {	
		user.setEmail(invalidLoginWithoutExtension);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComEmojiAntesDoArroba() {	
		user.setEmail(invalidLoginEmoji);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
		user.setEmail(invalidLoginEmoji2);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
		user.setEmail(invalidLoginSpecial);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
		user.setEmail(invalidLoginSpecial2);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComEspacosEmBrancoAntesEDepoisDoArroba() {	
		user.setEmail(invalidLoginWithBlankChars);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComEmojiNoNome() {	
		user.setEmail(fakerEmail);
		user.setName(invalidFakerNameEmoji);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComCaracterEspecialNoNome() {	
		user.setEmail(fakerEmail);
		user.setName(invalidFakerNameSpecial);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComNumeroNoNome() {	
		user.setEmail(fakerEmail);
		user.setName(invalidFakerNameNumber);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComNomeEmBranco() {	
		user.setEmail(fakerEmail);
		user.setName(blank);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComNomeVazio() {	
		user.setEmail(fakerEmail);
		user.setName(empty);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComNomeMenorQue2Caracteres() {	
		user.setEmail(fakerEmail);
		user.setName(shortName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
	public void naoCriarUsuarioComNomeMaiorQue50Caracteres() {	
		user.setEmail(fakerEmail);
		user.setName(longName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
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
		Integer id = createUser();
		
		alteracao.setName(fakerName);
		alteracao.setActive(fakerActive);
		alteracao.setAdminSectionsIds(fakerAdminSectionIds);
		alteracao.setSubjectIds(fakerSubjectsIds);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", id)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("OK"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("User updated succesfully"))
			.statusCode(200)
			;
		deleteUser(id);
	}
	

	@Test
	public void naoAlterarUsuarioSemToken() {
		alteracao = updateUser(true);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + emptyToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
	
	@Test
	public void naoAlterarUsuarioSemApiKey() {
		alteracao = updateUser(true);
		
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
	
	@Test
	public void alterarUsuarioApenasNome() {
		alteracao.setName(fakerName);

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("User updated succesfully"))
			.statusCode(200);
	}
	
	@Test
	public void naoAlterarUsuarioApenasNomeComEmoji() {
		alteracao.setName(invalidFakerNameEmoji);

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoAlterarUsuarioApenasNomeComCaracterEspecial() {
		alteracao.setName(invalidFakerNameEmoji);

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoAlterarUsuarioApenasNomeComNumero() {
		alteracao.setName(invalidFakerNameNumber);

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoAlterarUsuarioApenasNomeVazio_BUG() {
		alteracao.setName(empty);

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoAlterarUsuarioApenasNomeEmBranco() {
		alteracao.setName(blank);

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoAlterarUsuarioApenasNomeMenorQue2Caracteres() {
		alteracao.setName(shortName);

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoAlterarUsuarioApenasNomeMaiorQue50Caracteres() {
		alteracao.setName(shortName); alteracao.setActive(fakerActive);

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("BadRequest"))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void alterarUsuarioApenasStatusAtivo() {
		alteracao.setActive(true);

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("User updated succesfully"))
			.statusCode(200);
	}
	
	@Test
	public void alterarUsuarioApenasStatusInativo() {
		alteracao.setActive(false);
		alteracao.setAdminSectionsIds(fakerAdminSectionIds);;

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("User updated succesfully"))
			.statusCode(200);
	}

	@Test 
	public void deletarUsuario() {
		Integer id = createUser();
		given()
			.header("x-Api-Key", apiKey)
			.pathParam("id", id)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.delete(DELETE_USER)
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test 
	public void naoDeletarUsuarioSemToken() {
		given()
			.header("x-Api-Key", apiKey)
			.pathParam("id", fakerId)
			.header("Authorization", "Bearer " + emptyToken)
		.when()
			.delete(DELETE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
	
	@Test 
	public void naoDeletarUsuarioSemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.pathParam("id", fakerId)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.delete(DELETE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
	
	@Test 
	public void naoDeletarUsuarioInexistente() {
		given()
			.header("x-Api-Key", apiKey)
			.pathParam("id", invalidId)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.delete(DELETE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
	
	@Test 
	public void naoDeletarUsuarioQueJaFoiDeletado() {
		Integer id = createUser();
		
		deleteUser(id);
		
		given()
			.header("x-Api-Key", apiKey)
			.pathParam("id", id)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.delete(DELETE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("User not found"))
			.statusCode(400);
	}
}
