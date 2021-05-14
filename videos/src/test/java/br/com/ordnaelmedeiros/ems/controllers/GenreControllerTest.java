package br.com.ordnaelmedeiros.ems.controllers;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.Genre;
import io.quarkus.test.common.RestAssuredURLManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GenreControllerTest {

	@BeforeEach
	void beforeAll() {
		RestAssuredURLManager.setURL(false, "/genres");
	}
	
	@Test
	void fieldsNotNull() {
		Genre genre = new Genre();
		when().body(genre).post().then()
           .statusCode(400)
           .body(containsString("name: não deve ser nulo"));
	}
	
	@Test
	void crud() {
		
		Genre genre = new Genre();
		genre.name = "test";
		
		genre = when().body(genre).post().then()
           .statusCode(201)
           .extract().as(Genre.class);
        
        when().get("/"+genre.getId()).then()
	    	.statusCode(200)
	    	.body("id", is(genre.getId().toString()))
	    	.body("name", is(genre.name))
	    	.body("isActive", is(true))
	    	.body("createdAt", notNullValue())
	    	.body("updatedAt", notNullValue())
	    	.body("deletedAt", nullValue());
        
        genre.name = "test 2";
        genre.setIsActive(false);
        
        when().body(genre).put("/"+genre.getId()).then()
	    	.statusCode(204);
        
        when().get("/"+genre.getId()).then()
	    	.statusCode(200)
	    	
	    	.body("id", is(genre.getId().toString()))
	    	.body("name", is(genre.name))
	    	.body("isActive", is(false));
        
        when().delete("/"+genre.getId()).then()
	    	.statusCode(204);
        
        when().get("/"+genre.getId()).then()
	    	.statusCode(404);
        
        when().get("/deleted").then()
    		.statusCode(200)
    		.body("size()", is(1))
    		//.body("get(0).id", is(genre.getId().toString()))
    		.body("id", hasItem(genre.getId().toString()));
	        		
	}
	
	@Test
	void list() {
		
		for (int i = 0; i < 10; i++) {
			
			Genre gen = new Genre();
			gen.name = "test " + i;
			
			when().body(gen).post().then()
	           .statusCode(201);
			
		}
		
		when().get().then()
			.statusCode(200)
			.body("size()", is(10));
		
	}
	
}
