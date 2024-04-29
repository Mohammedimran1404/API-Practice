package day3;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;
public class PostQueryAndPathParameters {
	@Test
	public void testQueryAndPathParameters() {
		
		given()
		
		.pathParam("Users","users")
		.queryParam("page", 2)
		.queryParam("id",9)
		
		.when()
		.get("https://reqres.in/api/{Users}")
		
		.then()
		.statusCode(200)
		.log().all()
		;
	}
	@Test
	public void testLocalPathQueryParameter() {
		
		given()
		.pathParam("studentsdata", "students")
		.queryParam("id", 3)
		
		.when()
		.get(" http://localhost:3000/{studentsdata}")
		
		.then()
		.statusCode(200)
		.log().all()
		;
	}
	
	
	
	

}
