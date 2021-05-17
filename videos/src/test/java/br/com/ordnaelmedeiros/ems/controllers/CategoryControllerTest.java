package br.com.ordnaelmedeiros.ems.controllers;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.Category;
import io.quarkus.test.common.RestAssuredURLManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class CategoryControllerTest {

	@BeforeEach
	void beforeAll() {
		RestAssuredURLManager.setURL(false, "/categories");
	}
	
	@Test
	void fieldsNotNull() {
		Category category = new Category();
		when().body(category).post().then()
           .statusCode(400)
           .body(containsString("name: não deve ser nulo"));
	}
	
	@Test
	void crud() {
		
		Category category = new Category();
		category.setName("test");
		category.setDescription("teste description");
		
		category = when().body(category).post().then()
			.statusCode(201)
			.extract().as(Category.class);
        
        when().get("/"+category.getId()).then()
	    	.statusCode(200)
	    	.body("id", is(category.getId().toString()))
	    	.body("name", is(category.getName()))
	    	.body("description", is(category.getDescription()));
        
        category.setName("test 2");
        
        when().body(category).put("/"+category.getId()).then()
	    	.statusCode(204);
        
        when().get("/"+category.getId()).then()
	    	.statusCode(200)
	    	.body("id", is(category.getId().toString()))
	    	.body("name", is(category.getName()));
        
        when().delete("/"+category.getId()).then()
	    	.statusCode(204);
        
        when().get("/"+category.getId()).then()
	    	.statusCode(404);
	        		
	}
	
	@Test
	void list() {
		
		for (int i = 0; i < 10; i++) {
			
			Category cat = new Category();
			cat.setName("test " + i);
			
			when().body(cat).post().then()
	           .statusCode(201);
			
		}
		
		when().get().then()
			.statusCode(200)
			.body("size()", is(10));
		
	}
	
}
