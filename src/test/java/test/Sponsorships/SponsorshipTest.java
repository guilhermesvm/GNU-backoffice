package test.Sponsorships;

import org.junit.jupiter.api.Test;
import model.Banner;
import services.SponsorshipService;
import services.Environment;
import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static services.LoginService.login;
import static services.SponsorshipService.*;
import static constants.Data.*;
import static constants.DataFaker.*;
import static constants.Endpoints.*;

public class SponsorshipTest extends Environment{
	public static Banner banner = new Banner();
	public String accessToken = login();

	@Test
	public void listarBanners() {
		given()
			.header("x-Api-Key", apiKey)
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
			.header("x-Api-Key", apiKey)
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
	public void naoListarBannersSemApiKey() {
		given()
			.header("x-Api-Key", invalidApiKey)
			.header("Authorization", "Bearer " + accessToken)
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
		File img = new File(bannerFile2);
		
		banner.setId(117);
		banner.setLink("www.google.com.br");
	    banner.setType(typeFaker);//not working
	    
	    System.out.println(typeFaker);
	    
	    given()
	    	.header("x-Api-Key", apiKey)
	        .header("Authorization", "Bearer " + accessToken)
	        .contentType("multipart/form-data")
	        .multiPart("id", banner.getId())
	        .multiPart("file", img, "image/jpg")
	        .multiPart("link", banner.getLink())
	        .multiPart("content.type", "events")
	    .when()
	        .put(SPONSORSHIPS)
	    .then()
	        .log().all()
	       .assertThat()
//	        .body(is(not(nullValue())))
//	        .body(containsString("content"))
//	        .body("content", is(not(nullValue())))
//	        .body("content.id", is(not(nullValue())))
//	        .body("content.id", is(instanceOf(Integer.class)))
//	        .body("content.image", is(not(nullValue())))
//	        .body("content.image", is(instanceOf(String.class)))
//	        .body("content.link", is(not(nullValue())))
//	        .body("content.link", is(instanceOf(String.class)))
//	        .body("content.type", is(instanceOf(String.class)))
//	        .body(containsString("status"))
//	        .body("status", is("OK"))
	        .statusCode(200);
	}

	
//	@Test
//	public void alterarBannerApenasFile() {
//		banner.setId(117);
//		banner.setFile(img);
//		
//		given()
//	.header("x-Api-Key", apiKey)
//			.header("Authorization", "Bearer " + accessToken)
//			.contentType("multipart/form-data")
//			.multiPart("id", banner.getId())
//	        .multiPart("file", banner.getFile())
//		.when()
//			.put(SPONSORSHIPS)
//		.then()
//			.log().all()
//		.assertThat()
//			.body(is(not(nullValue())))
//			.body(containsString("content"))
//			.body("content", is(not(nullValue())))
//			.body("content.id", is(not(nullValue())))
//			.body("content.id", is(instanceOf(Integer.class)))
//			.body("content.image", is(not(nullValue())))
//			.body("content.image", is(instanceOf(String.class)))
//			.body(containsString("status"))
//			.body("status", is("OK"))
//			.statusCode(200);
//		}
	
//	@Test
//	public void alterarBannerApenasFileArquivoInvalido() {
//		banner.setFile(img2);
//		banner.setId(117);
//		
//		given()
//		.header("x-Api-Key", apiKey)
//			.header("Authorization", "Bearer " + accessToken)
//			.contentType("multipart/form-data")
//			.multiPart("id", banner.getId())
//			.multiPart("file", banner.getFile())
//		.when()
//			.put(SPONSORSHIPS)
//		.then()
//			.log().all()
//		.assertThat()
////			.body(is(not(nullValue())))
////			.body(containsString("content"))
////			.body("content", is(not(nullValue())))
////			.body("content.id", is(not(nullValue())))
////			.body("content.id", is(instanceOf(Integer.class)))
////			.body("content.image", is(not(nullValue())))
////			.body("content.image", is(instanceOf(String.class)))
////			.body(containsString("status"))
////			.body("status", is("OK"))
//			.statusCode(200);
//		}
	
	@Test
	public void alterarBannerLink() {
		banner.setId(117);
		banner.setLink(linkFaker);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("id", banner.getId())
	        .multiPart("link", banner.getLink())
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
			.body("content.link", is(instanceOf(String.class)))
			.body("content.type", is(instanceOf(String.class)))
			.body(containsString("status"))
			.body("status", is("OK"))
			.statusCode(200);
		}
	
	@Test
	public void alterarBannerLinkInvalidoEmoji() {
		banner.setId(117);
		banner.setLink(invalidLinkEmoji);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("id", banner.getId())
	        .multiPart("link", banner.getLink())
		.when()
			.put(SPONSORSHIPS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is(not(nullValue())))
			.body("messages[0].text", is("Link in invalid format"))
			.statusCode(400);
		}
	
	@Test
	public void alterarBannerLinkInvalidoSemDominio() {
		banner.setId(117);
		banner.setLink(invalidLinkWithoutDomain);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("id", banner.getId())
	        .multiPart("link", banner.getLink())
		.when()
			.put(SPONSORSHIPS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is(not(nullValue())))
			.body("messages[0].text", is("Link in invalid format"))
			.statusCode(400);
		}
	
	@Test
	public void alterarBannerLinkInvalidoComCaracterEspecial() {
		banner.setId(117);
		banner.setLink(invalidLinkSpecial);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("id", banner.getId())
	        .multiPart("link", banner.getLink())
		.when()
			.put(SPONSORSHIPS)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is(not(nullValue())))
			.body("messages[0].text", is("Link in invalid format"))
			.statusCode(400);
		}
	
	@Test
	public void alterarBannerType() {
		banner.setId(117);
		banner.setType(typeFaker);//not working
		System.out.println(typeFaker);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.contentType("multipart/form-data")
			.multiPart("id", banner.getId())
	        .multiPart("type", banner.getType())
		.when()
			.put(SPONSORSHIPS)
		.then()
			.log().all()
		.assertThat()
//			.body(is(not(nullValue())))
//			.body(containsString("content"))
//			.body("content", is(not(nullValue())))
//			.body("content.id", is(not(nullValue())))
//			.body("content.id", is(instanceOf(Integer.class)))
//			.body("content.image", is(not(nullValue())))
//			.body("content.image", is(instanceOf(String.class)))
//			.body("content.type", is(instanceOf(String.class)))
//			.body(containsString("status"))
//			.body("status", is("OK"))
			.statusCode(200);
		}
	
	@Test
	public void deletarBanner() {
		Integer id = SponsorshipService.createBanner();
		
		given()
			.header("x-Api-Key", apiKey)
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
	
	@Test
	public void naoDeletarBannerSemToken() {
		Integer id = SponsorshipService.createBanner();
		
		given()
			.header("x-Api-Key", apiKey)
			.pathParam("id", id)
		.when()
			.delete(SPONSORSHIP_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("Messages"))
			.body("Messages", is(not(nullValue())))
			.body("Messages[0].Text", is("Unauthorized Access"))
			.statusCode(401)
			;
	}
	
	@Test
	public void naoDeletarBannerComIdInvalido() {
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", invalidId)
		.when()
			.delete(SPONSORSHIP_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Banner not found"))
			.statusCode(400);
	}
	
	@Test
	public void naoDeletarBannerQueJaFoiDeletado() {
		Integer id = SponsorshipService.createBanner();
		
		deleteBanner(id);
		
		given()
			.header("x-Api-Key", apiKey)
			.header("Authorization", "Bearer " + accessToken)
			.pathParam("id", id)
		.when()
			.delete(SPONSORSHIP_ID)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("messages"))
			.body("messages", is(not(nullValue())))
			.body("messages[0].text", is("Banner not found"))
			.statusCode(400);
	}
}
