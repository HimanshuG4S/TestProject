package stepDefinations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddLocation;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class stepDefination extends Utils {
	RequestSpecification req;
	ResponseSpecification resSpec;
	RequestSpecification res;
	Response response;
	static String place_id;
	TestDataBuild data = new TestDataBuild();
	
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {

	res=given().spec(requestSpecifications()).body(data.addPlacePayLoad(name,language,address));
}

@When("user calls {string} with {string} http request")
public void user_calls_with_Post_http_request(String resource, String method) {
	
	APIResources resourcesAPI = APIResources.valueOf(resource);
	System.out.println(resourcesAPI.getResource());
	resSpec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    
	if(method.equalsIgnoreCase("POST")) 
	response =res.when().post(resourcesAPI.getResource());
	else if(method.equalsIgnoreCase("GET"))
	response =res.when().get(resourcesAPI.getResource());
}

@Then("the API calls got success with status code {int}")
public void the_API_calls_got_success_with_status_code(Integer int1) {

	assertEquals(response.getStatusCode(),200);
}

@And("{string} in response body is {string}")
public void in_response_body_is(String keyValue, String Expectedvalue) {
 
	assertEquals(getJsonPath(response, keyValue), Expectedvalue);
}


@Then("verify place_Id created maps to {string} using {string}")
public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
    // Write code here that turns the phrase above into concrete actions
	
	place_id = getJsonPath(response,"place_id");
	res=given().spec(requestSpecifications()).queryParam("place_id", place_id);
	user_calls_with_Post_http_request(resource, "GET");
	String actualName = getJsonPath(response, "name");
	assertEquals(actualName,expectedName);

}

@Given("DeletePlace Payload")
public void deleteplace_Payload() throws IOException {
    // Write code here that turns the phrase above into concrete actions
	res=given().spec(requestSpecifications()).body(data.deletePlacePayload(place_id));

}


}
