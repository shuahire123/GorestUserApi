package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payload.User;
import io.restassured.response.Response;
public class UserEndPoints {
	public static String AccessToken ="ff2f2f6e79ca7f7c0826a8d389b3d956e876f0f2c80fe6e306a6407c12b3a9e0";
	public static Response CreateUser(User payload)
	{	Response res=
		given()
		.header("Authorization","Bearer "+AccessToken)
		.contentType("application/json")
		.body(payload)
		.when()
		.post(Routes.PostUrl)
		;
	return res;
	}
	public static Response GetUser(String User_id)
	{
		Response res=
				given()
				.header("Authorization","Bearer "+AccessToken)
				.pathParam("User_id", User_id)
				.when()
				.get(Routes.GetUrl)
				;
		return res;
	}
	public static Response UpdateUser(String User_id,User payload) 
	{	Response res=
		given()
		.header("Authorization","Bearer "+AccessToken)
		.pathParam("User_id", User_id)
		.body(payload)
		.when()
		.put(Routes.PutUrl)
		;
		return res;
	}
	public static Response DeleteUser(String User_id) 
	{
		Response res=
				given()
				.header("Authorization","Bearer "+AccessToken)
				.pathParam("User_id", User_id)
				.when()
				.delete(Routes.DeleteUrl);
		return res;
	}


}
