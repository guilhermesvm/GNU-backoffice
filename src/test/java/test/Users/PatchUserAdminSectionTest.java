package test.Users;

import org.junit.jupiter.api.Test;
import model.Authentication;
import model.User;
import services.Environment;

import static constants.Data.*;
import static constants.Endpoints.*;
import static services.LoginService.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PatchUserAdminSectionTest extends Environment {
	public static Authentication login = new Authentication();
	public static User user = new User();
	public static User alteracao = new User();
	public String accessToken = login();
	
	@Test
	public void alterarUsuarioApenasAdminSectionMaster() {
		alteracao.setAdminSectionsIds(Master);

		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("OK"))
			.statusCode(200);
	}
	
	@Test
	public void alterarUsuarioApenasAdminSectionMarketing() { 
		alteracao.setAdminSectionsIds(Marketing);

		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("OK"))
			.statusCode(200);
	}
	
	@Test
	public void alterarUsuarioApenasAdminSectionEventos() { 
		alteracao.setAdminSectionsIds(Eventos);

		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("OK"))
			.statusCode(200);
	}
	
	@Test
	public void alterarUsuarioApenasAdminSectionOuvidoria() { 
		alteracao.setAdminSectionsIds(Ouvidoria);

		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("OK"))
			.statusCode(200);
	}
	
	@Test
	public void alterarUsuarioApenasAdminSectionEsportiva() { 
		alteracao.setAdminSectionsIds(Esportiva);

		given()
			.header("Authorization", "Bearer " + accessToken)
			.body(alteracao)
			.pathParam("id", 34)
		.when()
			.patch(UPDATE_USER)
		.then()
			.log().all()
		.assertThat()
			.body(is(not(nullValue())))
			.body(containsString("status"))
			.body("status", is("OK"))
			.statusCode(200);
	}
	

}
