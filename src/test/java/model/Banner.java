package model;

import lombok.Data;
import java.io.File;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Data
public class Banner {
	@JsonProperty(value="id", access = Access.WRITE_ONLY)
	private Integer id;
	
	private File file;
	private String link;
	private String type;
	
	
}
