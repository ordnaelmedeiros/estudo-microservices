package br.com.ordnaelmedeiros.ems;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class TestUtils {

	public static RequestSpecification when() {
		return given()
				.accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.when();
	}
	
}
