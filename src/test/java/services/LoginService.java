package services;

import lombok.Getter;
import lombok.Setter;
import model.Authentication;


import static constants.Endpoints.AUTHENTICATION;
import static io.restassured.RestAssured.given;
import static utils.Data.*;

@Getter
@Setter
public class LoginService {
	private static Authentication login = new Authentication();
	
	public static String login() {
		login.setLogin(ValidLogin);
		login.setPassword(ValidPassword);
		
		String token =
		given()
			.body(login)
		.when()
			.post(AUTHENTICATION)
		.then()
			.extract().path("content.token");
		return token;
	}
	
	public static Authentication loginAccount() {
		login.setLogin(ValidLogin);
		login.setPassword(ValidPassword);
		return login;
		
	}
	
	


}
