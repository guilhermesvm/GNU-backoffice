package test.Authentication;

import org.junit.jupiter.api.Test;

import model.Authentication;
import services.Environment;
import static utils.Data.*;
import static constants.Endpoints.PASSWORD_RESET_CHECK;
import static io.restassured.RestAssured.*;

public class ResetCheckTest extends Environment {
	private static Authentication resetCheck = new Authentication();
	
	@Test
	public void usarCodigoDeReset() {
		resetCheck.setLogin(ValidLogin);
		resetCheck.setValidationCode(ValidCode);;
		
		given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
			.statusCode(200);
	}
	
	@Test
	public void naoUsarCodigoDeResetInvalido() {
		 resetCheck.setLogin(ValidLogin);
		 resetCheck.setValidationCode(InvalidCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
			.statusCode(400);
	}
	
	@Test
	public void naoUsarCodigoDeResetVazio() {
		 resetCheck.setLogin(ValidLogin);
		 resetCheck.setValidationCode(BlankCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
			.statusCode(400);
	}
	
	@Test
	public void naoUsarCodigoDeResetEmBranco() {
		 resetCheck.setLogin(ValidLogin);
		 resetCheck.setValidationCode(EmptyCode);
		 
		 given()
			.body(resetCheck)
		.when()
			.post(PASSWORD_RESET_CHECK)
		.then()
			.log().all()
			.statusCode(400);
	}


}
