package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class AdminSection {
	
	private String name;
	private List<String> roles;
	
	@JsonProperty(value= "id", access = Access.WRITE_ONLY)
	private Integer id;

}
