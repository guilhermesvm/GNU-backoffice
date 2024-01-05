package model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

@Data
public class User {
	
	private String email;
	private String name;
	private List<Integer> adminSectionsIds;
	private List<Integer> subjectIds;
	
	
	private Boolean active;
	private Boolean activeFaker;
	
	
	private String newPassword;
	
	@JsonProperty(value ="id", access = Access.WRITE_ONLY) 
	private Integer id;	

}
