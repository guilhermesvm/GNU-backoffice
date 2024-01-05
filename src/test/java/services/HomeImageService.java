package services;

import static constants.Data.fileJPG;
import static constants.Endpoints.HOME_IMAGES;
import static constants.Endpoints.HOME_IMAGES_ID;
import static io.restassured.RestAssured.given;
import static services.LoginService.login;


import java.io.File;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeImageService {
		public static String accessToken = login();
		public static File img = new File(fileJPG);
		
	public static Integer createImage() {
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("file", img, "image/jpeg")
		.when()
			.post(HOME_IMAGES)
		.then()
			.statusCode(201)
			.extract().path("content.id");
		return id;
	}
	
	public static void deleteImage(Integer id) {
		given()
			.pathParam("id", id)
			.header("Authorization", "Bearer " + accessToken)
		.when()	
			.delete(HOME_IMAGES_ID)
		.then()
			.statusCode(200);
	}
}
