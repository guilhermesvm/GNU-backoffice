package services;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import model.Authentication;
import model.User;
import static constants.Endpoints.DELETE_USER;
import static constants.Endpoints.USERS;
import static io.restassured.RestAssured.given;
import static utils.DataFaker.*;

@Getter
@Setter
public class UserService {
	public static User user = new User();
	public static User alteracao = new User();
	public static Authentication login = new Authentication();
	static List<Integer> randomAdminSectionIds = randomAdminSections();
	static List<Integer> randomSubjectsIds = randomSubjects();

	
	public static User createUser() {
		user.setEmail(emailFaker);
		user.setName(nameFaker);
		user.setAdminSectionIds(randomAdminSectionIds);
		user.setSubjectIds(randomSubjectsIds);
		return user;
	}
	
	public static User updateUser(boolean status) {
		alteracao.setName(nameFaker);
		alteracao.setActive(status);
		alteracao.setAdminSectionIds(randomAdminSectionIds);
		alteracao.setSubjectIds(randomSubjectsIds);
		return alteracao;
	}
	
	public static Integer creatingUser() {
		String accessToken = LoginService.login();

		
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
			.extract().path("content.id")
			;
		return id;
	}
	
	public static void deletingUser(Integer id) {
		String accessToken = LoginService.login();
		given()
			.pathParam("id", id)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.delete(DELETE_USER)
		.then()
			.log().all()
			;
	}
}