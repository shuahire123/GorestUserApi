package api.test;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utiliies.DataProvider;
import api.utiliies.GetTestData;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class DDTUserTests {
	static Faker fr;
	static User userPayload;
	static String User_id;
	static String jsonSchema;

	@BeforeClass
	public static void setup() throws EncryptedDocumentException, IOException {
		fr = new Faker();
		jsonSchema = "{\r\n" + "  \"$schema\": \"http://json-schema.org/draft-04/schema#\",\r\n"
				+ "  \"type\": \"object\",\r\n" + "  \"properties\": {\r\n" + "    \"id\": {\r\n"
				+ "      \"type\": \"integer\"\r\n" + "    },\r\n" + "    \"name\": {\r\n"
				+ "      \"type\": \"string\"\r\n" + "    },\r\n" + "    \"email\": {\r\n"
				+ "      \"type\": \"string\"\r\n" + "    },\r\n" + "    \"gender\": {\r\n"
				+ "      \"type\": \"string\"\r\n" + "    },\r\n" + "    \"status\": {\r\n"
				+ "      \"type\": \"string\"\r\n" + "    }\r\n" + "  },\r\n" + "  \"required\": [\r\n"
				+ "    \"id\",\r\n" + "    \"name\",\r\n" + "    \"email\",\r\n" + "    \"gender\",\r\n"
				+ "    \"status\"\r\n" + "  ]\r\n" + "}";
	}

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProvider.class)
	public void PostUserTest(String name, String email, String gender, String status) {
		userPayload = new User();
		userPayload.setName(name+fr.name().firstName());
		userPayload.setEmail(fr.name().firstName()+email);
		userPayload.setGender(gender);
		userPayload.setStatus(status);
		Response res = UserEndPoints.CreateUser(userPayload);
		Assert.assertEquals(res.getStatusCode(), 201);
		Assert.assertEquals(res.getContentType(), "application/json; charset=utf-8");
		Object ID = res.jsonPath().get("id");
		User_id = ID.toString();
		System.out.println(ID);

	}

	@Test(priority = 2)
	public void ReadUserTest() {
		Response res = UserEndPoints.GetUser(User_id);
		Assert.assertEquals(res.jsonPath().get("name"), this.userPayload.getName());
		Assert.assertEquals(res.jsonPath().get("email"), this.userPayload.getEmail());
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(res.getContentType(), "application/json; charset=utf-8");
		res.prettyPrint();
	}

	@Test(priority = 3, dataProvider = "Data", dataProviderClass = DataProvider.class)
	public void UpdateUserTest(String name, String email,String gender, String status) {
		userPayload.setName(fr.name().firstName()+name);
		userPayload.setEmail(fr.name().firstName()+email);
		userPayload.setGender(gender);
		userPayload.setStatus(status);
		Response res = UserEndPoints.UpdateUser(User_id, userPayload);
		Assert.assertEquals(res.getStatusCode(), 200);
		// Assert.assertEquals(res.jsonPath().get("name"),userPayload.getName());
		res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
		// Assert.assertEquals(JsonSchemaValidator.matchesJsonSchema(jsonSchema), true);
		// this is incorrect way avoid
		res.prettyPrint();
	}

	@Test(priority = 4)
	public void DeleteUserTest() {
		Response res = UserEndPoints.DeleteUser(User_id);
		Assert.assertEquals(res.getStatusCode(), 204);
	}

}
