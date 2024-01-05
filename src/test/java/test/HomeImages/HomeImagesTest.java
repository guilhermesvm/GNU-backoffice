package test.HomeImages;


import org.junit.jupiter.api.Test;
import services.Environment;
import java.io.File;

import static services.HomeImageService.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static services.LoginService.login;
import static constants.Data.*;
import static constants.Endpoints.*;

public class HomeImagesTest extends Environment{
	public String accessToken = login();
	
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
		File img = new File(filePNG);
		
		Integer id =
		given()
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("file", img, "image/png")
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
		.and()
			.extract().path("content.id")
			;
		deleteImage(id);
	}

	
	@Test
	public void naoCriarImagemSemToken() {
		File img = new File(filePNG);
		
		given()
			.header("Authorization", "Bearer " + emptyToken)
			.contentType("multipart/form-data")
			.multiPart("file", img, "image/png")
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
	}
	
	@Test
	public void naoCriarImagemFormatoInvalidoGIF() {
		File gif = new File(invalidFileGIF);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("file", gif, "image/gif")
		.when()
			.post(HOME_IMAGES)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("File type not supported"))
			.statusCode(400);
	}
	
	@Test
	public void naoCriarImagemFormatoInvalidoMP3() {
		File mp3 = new File(invalidFileGIF);
		
		given()
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("file", mp3, "image/mpeg")
		.when()
			.post(HOME_IMAGES)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("File type not supported"))
			.statusCode(400);
	}
	
	@Test
	public void deletarImagemSaudacao() {
		Integer id = createImage();
		
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
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Image deleted"))
			.statusCode(200);
		}
	
	@Test
	public void naoDeletarImagemSemToken() {
		Integer id = createImage();
		
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
			.statusCode(401)
			;
			deleteImage(id);
		}
	
	@Test
	public void naoDeletarImagemComIdInvalido() {
		given()
			.pathParam("id", invalidId)
			.header("Authorization", "Bearer " + accessToken)
		.when()	
			.delete(HOME_IMAGES_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Image not found"))
			.statusCode(400);
		}
	
	@Test
	public void naoDeletarImagemSaudacaoQueJaFoiDeletada() {
		Integer id = createImage();
		
		deleteImage(id);
		
		given()
			.pathParam("id", id)
			.header("Authorization", "Bearer " + accessToken)
		.when()
			.delete(HOME_IMAGES_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Image not found"))
			.statusCode(400);
	;
		
		
		
		}

}
