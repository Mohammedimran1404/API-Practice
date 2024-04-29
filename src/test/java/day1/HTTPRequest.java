package day1;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.sdk.logs.data.Body;

public class HTTPRequest {
	int id;
	
	//@Test
	public void getusers() {
		
		given()
		
		.when()
		.get("https://reqres.in/api/users?page=2")
		
		.then()
		.statusCode(200)
		.body("page",equalTo(2))
		.log().all();
		
		
	
	}
//	@Test
	public void getSingleUser() {
		
		given()
		
		.when()
		.get("https://reqres.in/api/unknown")
		.then()
		.statusCode(200)
		.body("page",equalTo(1))
		.log().all();
		
		
	}
	@Test(priority = 1)
	public void createUser() {
		HashMap<String, String> data= new HashMap<String, String>();
		data.put("name", "Imran");
		data.put("job", "Automation tester");
		
		String newid=given()
		.contentType("application/json")
		.body(data)
		
		.when()
		.post("https://reqres.in/api/users")
	//	.jsonPath().getInt("id");
		
		.then()
		.statusCode(201)
		.log().all()
		.extract().path("id");
		id=Integer.parseInt(newid);
	}
	@Test(priority = 2,dependsOnMethods = {"createUser"})
	public void updateUser() {
		HashMap<String, String> data= new HashMap<String, String>();
		data.put("name", "Imran");
		data.put("job", "SDET");
		
		given()
		.contentType("application/json")
		.body(data)
		
		.when()
		.put("https://reqres.in/api/users/"+id)
		
		.then()
		.statusCode(200)
		.log().all();
	}
	@Test(priority = 3)
	public void deleteUser() {
		
		
		given()
				
		.when()
		.delete("https://reqres.in/api/users/"+id)
		
		.then()
		.statusCode(204)
//		.body("name",equalTo(""))
		.log().all();
		
	}

}
