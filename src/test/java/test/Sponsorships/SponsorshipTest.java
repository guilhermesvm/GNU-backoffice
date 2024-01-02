package test.Sponsorships;

import org.junit.jupiter.api.Test;
import model.Authentication;
import model.Banner;
import services.BannerService;
import services.Environment;
import static utils.DataFaker.*;
import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static services.LoginService.login;
import static constants.Endpoints.*;
import static utils.Data.*;

public class SponsorshipTest extends Environment{
	public static Authentication login = new Authentication();
	public static Banner banner = new Banner();
	public static File img = new File(bannerFile);
	public String accessToken = login();
	//public String accessToken = login.getToken();
	
	/*@BeforeAll
	public static void fazerLogin() {
		String token = login();
		login.setToken(token);
	}*/
	
	@Test
	public void listarBanners() {
		given()
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(SPONSORSHIPS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("count"))
			.body("count", is(not(nullValue())))
			.body(containsString("pageCount"))
			.body("pageCount", is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
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
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
	}
	
	@Test
	public void alterarBanner() {
		banner.setId(117);
		//banner.setFile(img);
		//banner.setLink(linkFaker);
		//banner.setType(typeFaker);//not working
		System.out.println(typeFaker);

		given()
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("id", banner.getId())
	       // .multiPart("file", banner.getFile())
	        //.multiPart("link", banner.getLink())
	        //.multiPart("type", "venue")
		.when()
			.put(SPONSORSHIPS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.body("content.id", is(not(nullValue())))
			.body("content.id", is(instanceOf(Integer.class)))
			.body("content.image", is(not(nullValue())))
			.body("content.image", is(instanceOf(String.class)))
			.body("content.link", is(not(nullValue())))
			.body("content.type", is(instanceOf(String.class)))
			.body(containsString("status"))
			.body("status", is("OK"))
			.statusCode(200);
		}
	
	@Test
	public void deletarBanner() {
		Integer id = BannerService.createBanner();
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.delete(SPONSORSHIP_ID)
		.then()
			.log().all()
		.assertThat()
			.statusCode(200)
			;
	}
}
