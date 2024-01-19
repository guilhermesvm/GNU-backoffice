package test.AdminSections;

import org.junit.jupiter.api.Test;
import model.AdminSection;
import services.Environment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static services.AdminSectionService.*;
import static services.AdminSectionService.deleteAdminSection;
import static constants.Data.*;
import static constants.DataFaker.*;
import static constants.Endpoints.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AdminSectionsTest extends Environment {
	public AdminSection section = new AdminSection();
	
	@Test
	public void listarAdminSections() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(ADMIN_SECTIONS)
		.then()
			.log().all()
			.assertThat()
			.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("count"))
			.body("count", is(not(nullValue())))
			.body(containsString("pageCount"))
			.body("pageCount", is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.body("content[0].name", is("Master"))
			.body("content[1].name", is("Marketing"))
			.body("content[2].name", is("Eventos"))
			.body("content[3].name", is("Ouvidoria"))
			.body("content[4].name", is("Esportiva"))
			.statusCode(200)
		;
	}
	
	@Test
	public void naoListarAdminSectionsSemToken() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + emptyToken)
		.when()
			.get(ADMIN_SECTIONS)
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
	public void naoListarAdminSectionsSemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(ADMIN_SECTIONS)
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
	public void listarAdminSectionId() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 1)
		.when()
			.get(ADMIN_SECTIONS_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.body("content.id", is(not(nullValue())))
			.body("content.name", is(not(nullValue())))
			.body("content.id", is(instanceOf(Integer.class)))
			.body("content.name", is(instanceOf(String.class)))
			.body("content.roles[0]", is("master"))
			.statusCode(200)
		;
	}
	
	@Test
	public void naoListarAdminSectionIdSemToken() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + emptyToken)
			.pathParam("id", 1)
		.when()
			.get(ADMIN_SECTIONS_ID)
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
	public void naoListarAdminSectionIdSemApiToken() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", 1)
		.when()
			.get(ADMIN_SECTIONS_ID)
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
	public void naoListarAdminSectionIdComIdInvalido() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", invalidId)
		.when()
			.get(ADMIN_SECTIONS_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Section not found"))
			.statusCode(400);
		;
	}
	
	@Test
	public void criarAdminSection() {
		section.setName(fakerAdminSection);
		section.setRoles(fakerAdminRole);
	
		Integer id =
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.body("content.id", is(not(nullValue())))
			.body("content.name", is(not(nullValue())))
			.body("content.id", is(instanceOf(Integer.class)))
			.body("content.name", is(instanceOf(String.class)))
			.body("content.roles[0]", is(not(nullValue())))
			.body("content.roles[0]", is(instanceOf(String.class)))
			.body("status", is("Created"))
			.statusCode(201)
		.and()
			.extract().path("content.id")
			;
		deleteAdminSection(id);
	}
	
	@Test
	public void naoCriarAdminSectionSemToken() {
		 section = creatingAdminSection();

		 given()
		 	.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + emptyToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
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
	public void naoCriarAdminSectionSemApiToken() {
		 section = creatingAdminSection();

		 given()
		 	.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
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
	public void naoCriarAdminSectionSemApiKey() {
		 section = creatingAdminSection();

		 given()
		 	.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
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
	public void naoCriarAdminSectionComNomeDeSectionJaExistente() {
		List<String> esportiva = new ArrayList<>(Arrays.asList("esportiva"));
		
		section.setName("Esportiva");
		section.setRoles(esportiva);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("AdminSection/Group already registered"))
			.statusCode(400);
			;

	}
	
	@Test
	public void naoCriarAdminSectionComEmojiNoNome() {
		section.setName(emoji);
		section.setRoles(fakerAdminRole);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in invalid format"))
			.statusCode(400);

	}
	
	@Test
	public void naoCriarAdminSectionComCaracterEspecialNoNome() {
		section.setName(specialChar);
		section.setRoles(fakerAdminRole);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in invalid format"))
			.statusCode(400);
			
	}
	
	
	@Test
	public void naoCriarAdminSectionComEspaçosEmBrancoNoNome() {
		section.setName(empty);
		section.setRoles(fakerAdminRole);
		

		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in invalid format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarAdminSectionComNomeVazio() {
		section.setName(empty);
		section.setRoles(fakerAdminRole);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Name in invalid format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarAdminSectionRoleComEmoji() {
		List<String> role = new ArrayList<>(Arrays.asList(emoji));
		
		section.setName(fakerAdminSection);
		section.setRoles(role);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Role in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarAdminSectionRoleComCaracterEspecial() {
		List<String> role = new ArrayList<>(Arrays.asList(specialChar));
		
		section.setName(fakerAdminSection);
		section.setRoles(role);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Role in wrong format"))
			.statusCode(400);
	}

	@Test
	public void naoCriarAdminSectionRoleVazia() {
		List<String> role = new ArrayList<>(Arrays.asList(empty));
		
		section.setName(fakerAdminSection);
		section.setRoles(role);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Role in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarAdminSectionRoleComEspaçoEmBrancos() {
		List<String> role = new ArrayList<>(Arrays.asList(blank));
		
		section.setName(fakerAdminSection);
		section.setRoles(role);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Role in wrong format"))
			.statusCode(400);
	}
	
	@Test
	public void deletarAdminSection() {
		Integer id = createAdminSection();
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.delete(ADMIN_SECTIONS_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Section deleted successfully"))
			.statusCode(200);
	}
	
	@Test
	public void naoDeletarAdminSectionSemToken() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + emptyToken)
			.pathParam("id", invalidId)
		.when()
			.delete(ADMIN_SECTIONS_ID)
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
	public void naoDeletarAdminSectionSemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", invalidId)
		.when()
			.delete(ADMIN_SECTIONS_ID)
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
	public void naoDeletarAdminSectionComIdInvalido() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", invalidId)
		.when()
			.delete(ADMIN_SECTIONS_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Section not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoDeletarAdminSectionQueJaFoiDeletada() {
		Integer id = createAdminSection();
		
		deleteAdminSection(id);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.delete(ADMIN_SECTIONS_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Section not found"))
			.statusCode(400);
	}

}
