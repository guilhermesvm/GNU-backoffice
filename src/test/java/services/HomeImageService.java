package services;

import static constants.Endpoints.HOME_IMAGES;
import static constants.Endpoints.HOME_IMAGES_ID;
import static io.restassured.RestAssured.given;
import static utils.Data.file;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeImageService {
	
	public static Integer createFile() {
		String accessToken = LoginService.login();
		
		File img = new File(file);
		
		Integer id =
		given()
			.multiPart("file", img)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
		.when()
			.post(HOME_IMAGES)
		.then()
			.extract().path("content.id");
		return id;
		
	}
	
	public static void deleteFile(Integer id) {
		String accessToken = LoginService.login();
		
		given()
			.pathParam("id", id)
			.header("Authorization", "Bearer " + accessToken)
		.when()	
			.delete(HOME_IMAGES_ID)
		;
	}

}
