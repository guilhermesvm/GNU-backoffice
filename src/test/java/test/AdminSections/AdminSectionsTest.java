package test.AdminSections;

import org.junit.jupiter.api.Test;
import model.AdminSection;
import static services.AdminSectionService.*;
import services.Environment;

import static services.AdminSectionService.deleteAdminSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static constants.Data.*;
import static constants.DataFaker.*;
import static constants.Endpoints.*;
import static services.LoginService.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AdminSectionsTest extends Environment {
	public String accessToken = login();
	public AdminSection section = new AdminSection();
	
	@Test
	public void listarAdminSections() {
		given()
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
	public void listarAdminSectionId() {
		given()
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
	public void naoListarAdminSectionIdComIdInvalido() {
		given()
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
	public void naoCriarAdminSectionComNomeDeSectionJaExistente_BUG() {
		List<String> esportiva = new ArrayList<>(Arrays.asList("esportiva"));
		
		section.setName("Esportiva");
		section.setRoles(esportiva);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			//.statusCode(400)
			.extract().path("content.id")
			;
		deleteAdminSection(id);
	}
	
	@Test
	public void naoCriarAdminSectionComEmojiNoNome_BUG() {
		section.setName(emoji);
		section.setRoles(fakerAdminRole);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			//.statusCode(400)
			.extract().path("content.id")
			;
		deleteAdminSection(id);
	}
	
	@Test
	public void naoCriarAdminSectionComCaracterEspecialNoNome_BUG() {
		section.setName(specialChar);
		section.setRoles(fakerAdminRole);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			//.statusCode(400)
			.extract().path("content.id")
			;
		deleteAdminSection(id);
	}
	
	
	@Test
	public void naoCriarAdminSectionComEspaçosEmBrancoNoNome_MELHORIA() {
		section.setName(empty);
		section.setRoles(fakerAdminRole);
		

		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all();
//		.assertThat()
//			.statusCode(400)
//			.extract().path("content.id")
//			;
//		deleteAdminSection(id);
	}
	
	@Test
	public void naoCriarAdminSectionComNomeVazio_MELHORIA() {
		section.setName(empty);
		section.setRoles(fakerAdminRole);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all();
//		.assertThat()
//			.statusCode(400)
//			.extract().path("content.id")
//			;
	}
	
	@Test
	public void naoCriarAdminSectionRoleComEmoji_BUG() {
		List<String> role = new ArrayList<>(Arrays.asList(emoji));
		
		section.setName(fakerAdminSection);
		section.setRoles(role);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			//.statusCode(400)
			.extract().path("content.id")
			;
		deleteAdminSection(id);
	}
	
	@Test
	public void naoCriarAdminSectionRoleComCaracterEspecial_BUG() {
		List<String> role = new ArrayList<>(Arrays.asList(specialChar));
		
		section.setName(fakerAdminSection);
		section.setRoles(role);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			//.statusCode(400)
			.extract().path("content.id")
			;
		deleteAdminSection(id);
	}

	@Test
	public void naoCriarAdminSectionRoleVazia_BUG() {
		List<String> role = new ArrayList<>(Arrays.asList(empty));
		
		section.setName(fakerAdminSection);
		section.setRoles(role);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			//.statusCode(400)
			.extract().path("content.id")
			;
		deleteAdminSection(id);
	}
	
	@Test
	public void naoCriarAdminSectionRoleComEspaçoEmBranco_BUG() {
		List<String> role = new ArrayList<>(Arrays.asList(blank));
		
		section.setName(fakerAdminSection);
		section.setRoles(role);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.log().all()
		.assertThat()
			//.statusCode(400)
			.extract().path("content.id")
			;
		deleteAdminSection(id);
	}
	
	@Test
	public void deletarAdminSection() {
		Integer id = createAdminSection();
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.delete(ADMIN_SECTIONS_ID)
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void naoDeletarAdminSectionSemToken() {
		//Integer id = createAdminSection();
		given()
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
	public void naoDeletarAdminSectionComIdInvalido() {
		given()
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
