package services;

import lombok.Getter;
import lombok.Setter;
import model.Authentication;

import static constants.Data.*;
import static constants.Endpoints.AUTHENTICATION;
import static io.restassured.RestAssured.given;

@Getter
@Setter
public class LoginService {
	private static Authentication login = new Authentication();
	
	public static Authentication loginAccount() {
		login.setLogin(validLogin);
		login.setPassword(validPassword);
		return login;
	}
	
	public static String login() {
		login = loginAccount();
		
		String token =
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.extract().path("content.token");
		return token;
	}
	
	


}
