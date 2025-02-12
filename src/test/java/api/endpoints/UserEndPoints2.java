package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.response.Response;
public class UserEndPoints2 {
	public static String AccessToken ="ff2f2f6e79ca7f7c0826a8d389b3d956e876f0f2c80fe6e306a6407c12b3a9e0";
	public static ResourceBundle GetUrl()
	{
		ResourceBundle routes = ResourceBundle.getBundle("routes");
		return routes;
	}
	public static Response CreateUser(User payload)
	{	String PostUrl=GetUrl().getString("PostUrl");
		Response res=
		given()
		.header("Authorization","Bearer "+AccessToken)
		.contentType("application/json")
		.body(payload)
		.when()
		.post(PostUrl)
		;
	return res;
	}
	public static Response GetUser(String User_id)
	{String GetUrl= GetUrl().getString("GetUrl");
		Response res=
				given()
				.header("Authorization","Bearer "+AccessToken)
				.pathParam("User_id", User_id)
				.when()
				.get(GetUrl)
				;
		return res;
	}
	public static Response UpdateUser(String User_id,User payload) 
	{	String PutUrl=GetUrl().getString("PutUrl");
		Response res=
		given()
		.header("Authorization","Bearer "+AccessToken)
		.pathParam("User_id", User_id)
		.body(payload)
		.when()
		.put(PutUrl)
		;
		return res;
	}
	public static Response DeleteUser(String User_id) 
	{String DeleteUrl=GetUrl().getString("DeleteUrl");
		Response res=
				given()
				.header("Authorization","Bearer "+AccessToken)
				.pathParam("User_id", User_id)
				.when()
				.delete(DeleteUrl);
		return res;
	}


}
