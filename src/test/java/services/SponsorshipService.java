package services;

import static constants.Data.*;
import static constants.DataFaker.*;
import static constants.Endpoints.SPONSORSHIPS;
import static constants.Endpoints.SPONSORSHIP_ID;
import static io.restassured.RestAssured.given;
import static services.LoginService.login;

import model.Banner;
import java.io.File;


public class SponsorshipService {
	public static Banner banner = new Banner();
	public static File img = new File(bannerFile);
	public static String accessToken = login();
	
	public static Integer createBanner() {
		banner.setFile(img);
		banner.setLink(linkFaker);
		banner.setType(typeFaker);//not working
	
		Integer id =
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
	        .multiPart("file", banner.getFile())
	        .multiPart("link", banner.getLink())
	        .multiPart("type", banner.getType())
		.when()
			.put(SPONSORSHIPS)
		.then()
			.log().all()
			.statusCode(201)
			.extract().path("content.id");
		
			return id;
		}
	
	public static void deleteBanner(Integer id) {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.delete(SPONSORSHIP_ID)
		.then()
			.statusCode(200);
	}
}
