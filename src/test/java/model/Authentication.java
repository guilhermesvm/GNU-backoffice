package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

//Struct do Login
@Data
public class Authentication {
	private String login;
	private String password;
	private String validationCode;
	
	@JsonProperty(value = "content.token", access = Access.WRITE_ONLY) //Desserializa o token contido no Json "token: {token}"
	private String token;
}
