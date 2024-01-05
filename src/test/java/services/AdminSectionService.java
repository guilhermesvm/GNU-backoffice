package services;

import static io.restassured.RestAssured.*;
import static constants.DataFaker.fakerAdminRole;
import static constants.DataFaker.fakerAdminSection;
import static constants.Endpoints.ADMIN_SECTIONS;
import static constants.Endpoints.ADMIN_SECTIONS_ID;
import static services.LoginService.login;
import model.AdminSection;

public class AdminSectionService {
	public static AdminSection section = new AdminSection();
	public static String accessToken = login();
	
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
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.delete(ADMIN_SECTIONS_ID)
		.then()
			.log().all()
		;

		
	}

}
