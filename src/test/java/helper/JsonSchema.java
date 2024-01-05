package helper;

import java.io.InputStream;
import java.text.MessageFormat;
import io.restassured.module.jsv.JsonSchemaValidator;

public class JsonSchema {
	
	public static InputStream jsonSchemaStream(String schemas, String endpoint, int status) {
		String path = "/schemas/{0}/{1}/{2}.json";
		path = MessageFormat.format(path, schemas, endpoint, status);
		
		return JsonSchema.class.getResourceAsStream(path);
	}
	
	public static JsonSchemaValidator validarJsonSchema(String schemas, String endpoint, int status) {
		InputStream schemaToMatch = jsonSchemaStream(schemas, endpoint, status);
	
		return JsonSchemaValidator.matchesJsonSchema(schemaToMatch);
	}
}
