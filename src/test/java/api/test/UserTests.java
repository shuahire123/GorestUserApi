package api.test;

import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserTests {
	static Faker fr;
	static User userPayload;
	static String User_id ;
	static String jsonSchema;
	public  Logger logger; // for logs
	@BeforeClass
	public  void setup()
	{	
		 fr = new Faker();
		 userPayload = new User();
		userPayload.setName(fr.name().fullName());
		userPayload.setEmail(fr.internet().emailAddress());
		userPayload.setGender("male");
		userPayload.setStatus("active");
		//logs
				logger= LogManager.getLogger(this.getClass());
				
				logger.debug("debugging.....");
		jsonSchema="{\r\n"
				+ "  \"$schema\": \"http://json-schema.org/draft-04/schema#\",\r\n"
				+ "  \"type\": \"object\",\r\n"
				+ "  \"properties\": {\r\n"
				+ "    \"id\": {\r\n"
				+ "      \"type\": \"integer\"\r\n"
				+ "    },\r\n"
				+ "    \"name\": {\r\n"
				+ "      \"type\": \"string\"\r\n"
				+ "    },\r\n"
				+ "    \"email\": {\r\n"
				+ "      \"type\": \"string\"\r\n"
				+ "    },\r\n"
				+ "    \"gender\": {\r\n"
				+ "      \"type\": \"string\"\r\n"
				+ "    },\r\n"
				+ "    \"status\": {\r\n"
				+ "      \"type\": \"string\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"required\": [\r\n"
				+ "    \"id\",\r\n"
				+ "    \"name\",\r\n"
				+ "    \"email\",\r\n"
				+ "    \"gender\",\r\n"
				+ "    \"status\"\r\n"
				+ "  ]\r\n"
				+ "}";
	}
	@Test(priority = 1)
	public  void PostUserTest()
	{	logger.info("*************creating user****************");
		Response res = UserEndPoints2.CreateUser(userPayload);
		Assert.assertEquals(res.getStatusCode(), 201);
		Assert.assertEquals(res.getContentType(), "application/json; charset=utf-8");
		Object ID =res.jsonPath().get("id");
		User_id= ID.toString();
		 System.out.println(ID);
		 logger.info("*************user created****************");
		
	}
	@Test(priority = 2)
	public void ReadUserTest()
	{logger.info("*************reading user****************");
		Response res = UserEndPoints2.GetUser(User_id);
		Assert.assertEquals(res.jsonPath().get("name"),this.userPayload.getName());
		Assert.assertEquals(res.jsonPath().get("email"), this.userPayload.getEmail());
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(res.getContentType(), "application/json; charset=utf-8");
		res.prettyPrint();
		logger.info("*************user info retrive****************");
	}
	@Test(priority = 3)
	public void UpdateUserTest()
	{	logger.info("*************updating user****************");
		userPayload.setName(fr.name().fullName());
		userPayload.setEmail(fr.internet().emailAddress());
		Response res = UserEndPoints2.UpdateUser(User_id, userPayload);
		Assert.assertEquals(res.getStatusCode(), 200);
		//Assert.assertEquals(res.jsonPath().get("name"),userPayload.getName());
		res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
		//Assert.assertEquals(JsonSchemaValidator.matchesJsonSchema(jsonSchema), true); this is incorrect way avoid
		res.prettyPrint();
		logger.info("*************user updated****************");
	}
	@Test(priority = 4)
	public void DeleteUserTest()
	{logger.info("*************deleting user****************");
		Response res = UserEndPoints2.DeleteUser(User_id);
		Assert.assertEquals(res.getStatusCode(), 204);
		logger.info("************user deleted****************");
	}

}
