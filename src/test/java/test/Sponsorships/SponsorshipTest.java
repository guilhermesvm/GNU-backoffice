package test.Sponsorships;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import model.Authentication;
import model.Banner;
import services.Environment;
import java.io.File;
import static io.restassured.RestAssured.*;
import static services.LoginService.login;
import static constants.Endpoints.*;
import static utils.Data.*;
import static utils.DataFaker.linkFaker;

public class SponsorshipTest extends Environment{
	public static Authentication login = new Authentication();
	public static Banner banner = new Banner();
	public static File img = new File(file);
	public String accessToken = login.getToken();
	
	@BeforeAll
	public static void fazerLogin() {
		String token = login();
		login.setToken(token);
	}
	
	@Test
	public void listarBanners() {
		given()
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(SPONSORSHIPS)
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void naoListarBannersSemToken() {
		given()
			.header("Authorization", "Bearer " + emptyToken)
		.when()
			.get(SPONSORSHIPS)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void alterarBanner() {
		banner.setId(119);
		banner.setFile(img);
		banner.setLink(linkFaker);
		banner.setType("fraternity"); ///not working
	
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("id", banner.getId())
			.multiPart("type", banner.getType())
	        .multiPart("link", banner.getLink())
	        .multiPart("file", banner.getFile())
		.when()
			.put(SPONSORSHIPS)
		.then()
			.log().all()
			.statusCode(200);
	}
}
