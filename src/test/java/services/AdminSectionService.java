package services;

import static io.restassured.RestAssured.*;
import static constants.Data.apiKey;
import static constants.Data.accessToken;
import static constants.DataFaker.fakerAdminRole;
import static constants.DataFaker.fakerAdminSection;
import static constants.Endpoints.ADMIN_SECTIONS;
import static constants.Endpoints.ADMIN_SECTIONS_ID;
import model.AdminSection;

public class AdminSectionService {
	public static AdminSection section = new AdminSection();
	
	public static AdminSection creatingAdminSection() {
		section.setName(fakerAdminSection);
		section.setRoles(fakerAdminRole);
		return section;
	
	}
	
	public static Integer createAdminSection() {
		section = creatingAdminSection();
	
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.header("x-Api-Key", apiKey)
			.body(section)
		.when()
			.post(ADMIN_SECTIONS)
		.then()
			.statusCode(201)
		.and()
			.extract().path("content.id")
			;
		return id;	
	}
	
	public static void deleteAdminSection(Integer id) {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.delete(ADMIN_SECTIONS_ID)
		.then()
			.log().all()
		;

		
	}

}
