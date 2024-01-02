package services;

import static constants.Endpoints.SPONSORSHIPS;
import static io.restassured.RestAssured.given;
import static utils.DataFaker.*;

import java.io.File;

import static utils.Data.*;

import model.Banner;
import utils.DataFaker;

public class BannerService {
	public static Banner banner = new Banner();
	public static File img = new File(file);
	public static String randomType = DataFaker.typeFaker;
	
	public static Integer createBanner() {
		String accessToken = LoginService.login();
		
		banner.setFile(img);
		banner.setLink(linkFaker);
		banner.setType(randomType);//not working
	
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
	        .multiPart("file", banner.getFile())
	        .multiPart("link", banner.getLink())
	        .multiPart("type", banner.getType())
		.when()
			.put(SPONSORSHIPS)
		.then()
			.log().all()
			.extract().path("content.id");
			return id;
		}
	
}
