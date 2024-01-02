package services;

import lombok.Getter;
import lombok.Setter;
import model.Authentication;
import model.User;
import static constants.Endpoints.DELETE_USER;
import static constants.Endpoints.USERS;
import static io.restassured.RestAssured.given;
import static utils.DataFaker.*;
import static services.LoginService.login;

@Getter
@Setter
public class UserService {
	public static User user = new User();
	public static User alteracao = new User();
	public static Authentication login = new Authentication();

	
	public static User createUser() {
		user.setEmail(fakerEmail);
		user.setName(fakerName);
		user.setAdminSectionIds(fakerAdminSectionIds);
		user.setSubjectIds(fakerSubjectsIds);
		return user;
	}
	
	public static Integer creatingUser() {
		String accessToken = login();

		user.setEmail(fakerEmail);
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
			.extract().path("content.id")
			;
		return id;
	}
	
	public static User updateUser(boolean status) {
		alteracao.setName(fakerName);
		alteracao.setActive(status);
		alteracao.setAdminSectionIds(fakerAdminSectionIds);
		alteracao.setSubjectIds(fakerSubjectsIds);
		return alteracao;
	}
	
	
	
	public static void deletingUser(Integer id) {
		String accessToken = login();
		given()
			.pathParam("id", id)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.delete(DELETE_USER)
			;
	}
}