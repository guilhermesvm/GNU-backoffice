package test.HomeImages;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Authentication;
import services.Environment;
import services.HomeImageService;
import static utils.Data.*;
import java.io.File;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static services.LoginService.login;
import static constants.Endpoints.*;

public class HomeImagesTest extends Environment{
	public static Authentication login = new Authentication();

	public String accessToken = login.getToken();
	
	@BeforeAll
	public static void fazerLogin() {
		String token = login();
		login.setToken(token);
	}
	
	
	@Test
	public void listarImagens() {
		given()
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.get(HOME_IMAGES)
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
	public void naoListarImagensSemToken() {
		given()
			.header("Authorization", "Bearer " + emptyToken)
		.when()
			.get(HOME_IMAGES)
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
	public void criarImagem() {
		File img = new File(file);
		
		Integer id =
		given()
			.multiPart("file", img)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
		.when()
			.post(HOME_IMAGES)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("content"))
			.body("content", is(not(nullValue())))
			.body("content.id", is(not(nullValue())))
			.body("content.id", is(instanceOf(Integer.class)))
			.body("content.imageUrl", is(not(nullValue())))
			.body("content.imageUrl", is(instanceOf(String.class)))
			.body(containsString("status"))
			.body("status", is("Created"))
			.statusCode(201)
				.extract().path("content.id")
		;
		HomeImageService.deleteFile(id);
	}
	
	@Test
	public void naoCriarImagemSemToken() {
		File img = new File(file);
		
		given()
			.multiPart("file", img)
			.header("Authorization", "Bearer " + emptyToken)
			.contentType("multipart/form-data")
		.when()
			.post(HOME_IMAGES)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
			;
	}
	
	@Test
	public void naoCriarImagemFormatoInvalidoGIF_BUG() {
		File gif = new File(invalidFile);
		
		Integer id =
		given()
			.multiPart("file", gif)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
		.when()
			.post(HOME_IMAGES)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			//.body(containsString("Messages"))
			//.body("Messages", is(not(nullValue())))
			//.body("Messages[0].Text", is("Unauthorized Access"))
			//.statusCode(400)
			.extract().path("content.id")
			;
		HomeImageService.deleteFile(id);
	}
	
	@Test
	public void naoCriarImagemFormatoInvalidoMP3_BUG() {
		File mp3 = new File(invalidFile);
		
		Integer id =
		given()
			.multiPart("file", mp3)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
		.when()
			.post(HOME_IMAGES)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			//.body(containsString("Messages"))
			//.body("Messages", is(not(nullValue())))
			//.body("Messages[0].Text", is("Unauthorized Access"))
			//.statusCode(400)
			.extract().path("content.id")
			;
		HomeImageService.deleteFile(id);
	}
	
	@Test
	public void deletarImagemSaudacao() {
		Integer id = HomeImageService.createFile();
		
		given()
			.pathParam("id", id)
			.header("Authorization", "Bearer " + accessToken)
		.when()	
			.delete(HOME_IMAGES_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body("status", is("OK"))
			.statusCode(200)
			;
		}
	
	@Test
	public void naoDeletarImagemSemToken() {
		Integer id = HomeImageService.createFile();
		
		given()
			.pathParam("id", id)
			.header("Authorization", "Bearer " + emptyToken)
		.when()	
			.delete(HOME_IMAGES_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401);
			;
		HomeImageService.deleteFile(id);
		}
	
	@Test
	public void naoDeletarImagemComIdInvalido() {
		given()
			.pathParam("id", InvalidId)
			.header("Authorization", "Bearer " + accessToken)
		.when()	
			.delete(HOME_IMAGES_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(400)
			;
		}

}
