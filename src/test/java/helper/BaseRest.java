package helper;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

//Criação de funções genéricas para métodos GET/POST/PUT/DELETE/PATCH
public class BaseRest {
	
	public Response get(String endpoint, String token) {
		return given()
				.header("Authorization", "Bearer " + token)
				.when()
					.get(endpoint)
				.then()
					.log().all()
					.body(is(not(nullValue())))
					.time(lessThan(3000L))
					.extract().response();
	}
	
	public Response getId(String endpoint, Integer id) {
		return given()
					.pathParam("id", id)
				.when()
					.get(endpoint + "/" + "{id}")
				.then()
				.log().all()
				.body(is(not(nullValue())))
				.time(lessThan(3000L))
				.extract().response();
	}
	
	public Response post(String endpoint, Object object) {
		return given()
					.body(object)
				.when()
					.post(endpoint)
				.then()
					.log().all()
					.body(is(not(nullValue())))
					.time(lessThan(3000L))
					.extract().response();
	}
	
	public Response put(String endpoint, Object update, Integer id) {
		return given()
					.body(update)
					.pathParam("id", id)
				.when()
					.put(endpoint + "/" + "{id}")
				.then()
					.log().all()
					.body(is(not(nullValue())))
					.time(lessThan(3000L))
					.extract().response();
	}
	
	public Response delete(String endpoint, Integer id) {
		return given()
				.pathParam("id", id)
			.when()
				.put(endpoint + "/" + "{id}")
			.then()
				.log().all()
				.body(is(not(nullValue())))
				.time(lessThan(3000L))
				.extract().response();
	}
}
