package day2;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import io.opentelemetry.api.trace.StatusCode;

public class DifferentWaysToCreatePostRequestBody {
	// post request using hashmap
	int id;
	
	@Test(priority = 1)
	public void postRequestBodyUsingHashmap() {
		
		String courseArray[]= {"java","javascript"};
		HashMap data = new HashMap();
		
		data.put("name", "Imran");
		data.put("location","Hyderabad");
		data.put("mobile no", "9911222");
		data.put("surname", "Mohammed");
		data.put("cousres",courseArray);
		
		
	String newid=given()
		.contentType("application/json")
		.body(data)
		
		.when()
		.post("http://localhost:3000/students")
		
		
		.then()
		.statusCode(201)
		.body("name",equalTo("Imran"))
		.header("Content-Type","application/json; charset=utf-8") 
				.log().all()
				.extract().path("id").toString(); 	
	            id=Integer.parseInt(newid);
		
	}
	@Test(priority = 2,dependsOnMethods = {"postRequestBodyUsingHashmap"})
	public void deletePostUser() {
		given()
		
	.when()
	.delete("http://localhost:3000/students/"+id)
	
	.then()
		.statusCode(200);
	
	}
	// post request using org.json library
	@Test(priority = 3)
	public void postStudentDataUsingJsonObject() {
		
	JSONObject data=new JSONObject();
	data.put("FirstName","Mohammed");
	data.put("LastName", "Imran");
	data.put("phoneno","122345" );
	String[] courses= {"Java","RestAssured API"};
	data.put("Courses",courses );
	
		String newid=given()
		.contentType("application/json")
		.body(data.toString())
		
		.when()
		.post("http://localhost:3000/students")
		
		.then()
		.statusCode(201)
		.body("FirstName",equalTo("Mohammed"))
		.body("Courses[0]",equalTo("Java"))
		.header("Content-Type","application/json; charset=utf-8")
		.log().all()
		.extract().path("id").toString();
		id=Integer.parseInt(newid);
		
	}
	@Test(priority=4,dependsOnMethods = {"postStudentDataUsingJsonObject"})
	public void deletePostUserData() {
		
		given()
		
		.when()
		.delete("http://localhost:3000/students/"+id)
		
		.then()
		.statusCode(200)
		.log().all()
		;
	}
	// post request using pojo class
	@Test(priority = 5)
	public void postUserDataUsingPOJOClass() {
		PojoClassToSetData data=new PojoClassToSetData();
		data.setFirstName("Shaik");
		data.setLastName("Akheel");
		data.setLocation("Hyderabad");
		String Courses[]= {"TypeScript","Python"};
		data.setCourses(Courses);
		
		String pojoid=given()
		.contentType("application/json")
		.body(data)
		
		.when()
		.post("http://localhost:3000/students")
		
		.then()
		.statusCode(201)
		.body("firstName",equalTo("Shaik"))
		.header("Content-Type","application/json; charset=utf-8")
		.log().all()
		.extract().path("id").toString();
		id=Integer.parseInt(pojoid)
		;
		}
	@Test(priority = 6,dependsOnMethods = {"postUserDataUsingPOJOClass"})
	public void deletePojoUser() {
		given()
		
		.when()
		.delete("http://localhost:3000/students/"+id)
		.then()
		.statusCode(200);
	}
	//post user using external json data
	@Test(priority = 7)
	public void postUserUsingExternalJson() throws FileNotFoundException {
		File file =new File(".\\data.json");
		
		FileReader fileReader =new FileReader(file);
		
		JSONTokener tokener=new JSONTokener(fileReader);
		
		JSONObject object =new JSONObject(tokener);
		
		
	String newid=given()
		.contentType("application/json")
		.body(object.toString())
		.when()
		.post("http://localhost:3000/students")
		
		.then()
		.statusCode(201)
		.body("name",equalTo("imran"))
		.header("Content-Type","application/json; charset=utf-8")
		.log().all()
		.extract().path("id").toString();
		id=Integer.parseInt(newid);
		
		;
	}
	@Test(priority = 8,dependsOnMethods = {"postUserUsingExternalJson"})
	public void deletePostResponse() {
		
		given()
		
		.when()
		.delete("http://localhost:3000/students/"+id)
		.then()
		.statusCode(200)
		;
		
		
	}
}
