
package services;

import lombok.Getter;
import lombok.Setter;
import model.User;

import static constants.DataFaker.*;
import static constants.Endpoints.DELETE_USER;
import static constants.Endpoints.USERS;
import static io.restassured.RestAssured.given;
import static services.LoginService.login;

@Getter
@Setter
public class UserService {
	public static User user = new User();
	public static User alteracao = new User();

	public static User creatingUser() {
		user.setEmail(fakerEmail);
		user.setName(fakerName);
		user.setAdminSectionsIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		return user;
	}
	
	public static Integer createUser() {
		String accessToken = login();

		user = creatingUser();
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(user)
		.when()
			.post(USERS)
		.then()
			.extract().path("content.id")
			;
		return id;
	}
	
	public static User updateUser(boolean status) {
		alteracao.setName(fakerName);
		alteracao.setActive(status);
		alteracao.setActiveFaker(fakerActive);
		alteracao.setAdminSectionsIds(fakerAdminSectionIds);
		alteracao.setSubjectIds(fakerSubjectsIds);
		return alteracao;
	}
	
	
	public static void deleteUser(Integer id) {
		String accessToken = login();
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.delete(DELETE_USER)
		.then()
			.log().all()
			.statusCode(200)
			;
	}
}