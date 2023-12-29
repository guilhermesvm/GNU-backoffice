package model;

import lombok.Data;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Data
public class Banner {
	private String type;
	private String link;
	private File file;
	
	@JsonProperty(value="id", access = Access.WRITE_ONLY)
	private Integer id;
	
}
