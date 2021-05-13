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
public class CategoryControllerTest {

	@BeforeEach
	public void beforeAll() {
		RestAssuredURLManager.setURL(false, "/categories");
	}
	
	@Test
	public void fieldsNotNull() {
		Category category = new Category() {{
			description = "test";
		}};
		when().body(category).post().then()
           .statusCode(400)
           .body(containsString("name: não deve ser nulo"));
	}
	
	@Test
	public void crud() {
		
		Category category = new Category() {{
			name = "test";
		}};
		
		category = when().body(category).post().then()
			//.body(is("ttt"))
			.statusCode(201)
			.extract().as(Category.class);
        
        when().get("/"+category.id).then()
	    	.statusCode(200)
	    	.body("id", is(category.id.toString()))
	    	.body("name", is(category.name));
        
        category.name = "test 2";
        
        when().body(category).put("/"+category.id).then()
	    	.statusCode(204);
        
        when().get("/"+category.id).then()
	    	.statusCode(200)
	    	.body("id", is(category.id.toString()))
	    	.body("name", is(category.name));
        
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
