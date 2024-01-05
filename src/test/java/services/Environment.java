package services;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.*;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.builder.RequestSpecBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Environment {
	
	@BeforeAll
	public static void setupEnvironment() {
		baseURI = "https://api-backofficeapp-homologacao.gnu.com.br";
		
		requestSpecification = new RequestSpecBuilder()
				.setContentType(JSON)
				.setAccept(JSON)
				.build();
		
		enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
//	@BeforeEach
//	public static void initializeRest() {
//		BaseRest rest = new BaseRest();
//	}
}
