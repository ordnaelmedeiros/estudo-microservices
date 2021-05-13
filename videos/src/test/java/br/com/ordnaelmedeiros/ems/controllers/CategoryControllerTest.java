package br.com.ordnaelmedeiros.ems.controllers;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.Category;
import io.quarkus.test.common.RestAssuredURLManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CategoryControllerTest {

	@BeforeEach
	public void beforeAll() {
		RestAssuredURLManager.setURL(false, "/categories");
	}
	
	@Test
	public void fieldsNotNull() {
		Category category = new Category();
		when().body(category).post().then()
           .statusCode(400)
           .body(containsString("name: não deve ser nulo"));
	}
	
	@Test
	public void crud() {
		
		Category category = new Category();
		category.name = "test";
		category.description = "teste description";
		
		category = when().body(category).post().then()
			.statusCode(201)
			.extract().as(Category.class);
        
        when().get("/"+category.id).then()
	    	.statusCode(200)
	    	.body("id", is(category.id.toString()))
	    	.body("isActive", is(true))
	    	.body("createdAt", notNullValue())
	    	.body("updatedAt", notNullValue())
	    	.body("name", is(category.name))
	    	.body("description", is(category.description));
        
        category.name = "test 2";
        category.isActive = false;
        
        when().body(category).put("/"+category.id).then()
	    	.statusCode(204);
        
        when().get("/"+category.id).then()
	    	.statusCode(200)
	    	.body("id", is(category.id.toString()))
	    	.body("name", is(category.name))
	    	.body("isActive", is(false));
        
        when().delete("/"+category.id).then()
	    	.statusCode(204);
        
        when().get("/"+category.id).then()
	    	.statusCode(404);
	        		
	}
	
	@Test
	public void list() {
		
		for (int i = 0; i < 10; i++) {
			
			Category cat = new Category();
			cat.name = "test " + i;
			
			when().body(cat).post().then()
	           .statusCode(201);
			
		}
		
		when().get().then()
			.statusCode(200)
			.body("size()", is(10));
		
	}
	
}
