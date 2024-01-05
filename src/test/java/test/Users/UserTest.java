package test.Users;

import org.junit.jupiter.api.Test;
import model.Authentication;
import model.User;
import services.Environment;

import static services.UserService.*;
import static constants.Data.*;
import static constants.DataFaker.*;
import static constants.Endpoints.*;
import static services.LoginService.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserTest extends Environment {
	public static Authentication login = new Authentication();
	public static User user = new User();
	public static User alteracao = new User();
	public String accessToken = login();

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
			;
	}

	@Test
	public void criarUsuario() {
		user = creatingUser();
		
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
		deleteUser(id);
	}
		
	@Test
	public void naoCriarUsuarioSemToken() {
		user = creatingUser();
		
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
		user.setEmail(validLogin);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
		user.setEmail(invalidLoginWithoutAT);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
		user.setEmail(blankLogin);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
		user.setEmail(emptyLogin);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void naoCriarUsuarioComEmailSemExtensao() {	
		user.setEmail(invalidLoginWithoutExtension);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void naoCriarUsuarioComEmojiAntesDoArroba() {	
		user.setEmail(invalidLoginEmoji);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
		user.setEmail(invalidLoginEmoji2);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
		user.setEmail(invalidLoginSpecial);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
		user.setEmail(invalidLoginSpecial2);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void naoCriarUsuarioComEspacosEmBrancoAntesEDepoisDoArroba() {	
		user.setEmail(invalidLoginWithBlankChars);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
			.statusCode(400)
			;
	}
	
	@Test
	public void naoCriarUsuarioComEmojiNoNome() {	
		user.setEmail(fakerEmail);
		user.setName(InvalidFakerNameEmoji);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void naoCriarUsuarioComCaracterEspecialNoNome() {	
		user.setEmail(fakerEmail);
		user.setName(InvalidFakerNameSpecial);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void naoCriarUsuarioComNumeroNoNome() {	
		user.setEmail(fakerEmail);
		user.setName(InvalidFakerNameNumber);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void naoCriarUsuarioComNomeEmBranco() {	
		user.setEmail(fakerEmail);
		user.setName(emptyName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void naoCriarUsuarioComNomeVazio() {	
		user.setEmail(fakerEmail);
		user.setName(blankName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void naoCriarUsuarioComNomeMenorQue2Caracteres() {	
		user.setEmail(fakerEmail);
		user.setName(shortName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void naoCriarUsuarioComNomeMaiorQue50Caracteres() {	
		user.setEmail(fakerEmail);
		user.setName(longName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		
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
	public void criarTokenDePrimeiroAcesso() {
		Integer id = createUser();
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
		deleteUser(id);
	}


	@Test
	public void alterarUsuarioCompleto() {
		alteracao.setName(fakerName);
		alteracao.setActive(fakerActive);
		alteracao.setAdminSectionsIds(fakerAdminSectionIds);
		alteracao.setSubjectIds(fakerSubjectsIds);
		
		given()
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
			.body("status", is("OK"))
			.statusCode(200)
			;
	}
	
	@Test
	public void alterarUsuarioApenasNome() {
		alteracao.setName(fakerName);

		given()
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
			.body("status", is("OK"))
			.statusCode(200)
			;
	}
	
	@Test
	public void naoAlterarUsuarioApenasNomeComEmoji() {
		alteracao.setName(InvalidFakerNameEmoji);

		given()
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
		alteracao.setName(InvalidFakerNameEmoji);

		given()
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
		alteracao.setName(InvalidFakerNameNumber);

		given()
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
		alteracao.setName(blankName);

		given()
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
		alteracao.setName(emptyName);

		given()
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
			.body("status", is("OK"))
			.statusCode(200);
	}
	
	@Test
	public void alterarUsuarioApenasStatusInativo() {
		alteracao.setActive(false);
		alteracao.setAdminSectionsIds(fakerAdminSectionIds);;

		given()
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
			.body("status", is("OK"))
			.statusCode(200);
	}

	@Test 
	public void deletarUsuario() {
		Integer id = createUser();
		given()
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
	public void naoDeletarUsuarioInexistente() {
		given()
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
