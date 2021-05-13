package br.com.ordnaelmedeiros.ems.controllers;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.Genre;
import io.quarkus.test.common.RestAssuredURLManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GenreControllerTest {

	@BeforeEach
	public void beforeAll() {
		RestAssuredURLManager.setURL(false, "/genres");
	}
	
	@Test
	public void fieldsNotNull() {
		Genre genre = new Genre();
		when().body(genre).post().then()
           .statusCode(400)
           .body(containsString("name: não deve ser nulo"));
	}
	
	@Test
	public void crud() {
		
		Genre genre = new Genre() {{
			name = "test";
		}};
		
		genre = when().body(genre).post().then()
           .statusCode(201)
           .extract().as(Genre.class);
        
        when().get("/"+genre.id).then()
	    	.statusCode(200)
	    	.body("id", is(genre.id.toString()))
	    	.body("name", is(genre.name));
        
        genre.name = "test 2";
        
        when().body(genre).put("/"+genre.id).then()
	    	.statusCode(204);
        
        when().get("/"+genre.id).then()
	    	.statusCode(200)
	    	.body("id", is(genre.id.toString()))
	    	.body("name", is(genre.name));
        
        when().delete("/"+genre.id).then()
	    	.statusCode(204);
        
        when().get("/"+genre.id).then()
	    	.statusCode(404);
	        		
	}
	
	@Test
	public void list() {
		
		for (int i = 0; i < 10; i++) {
			
			Genre gen = new Genre();
			gen.name = "test " + i;
			gen.isActive = true;
			
			when().body(gen).post().then()
	           .statusCode(201);
			
		}
		
		when().get().then()
			.statusCode(200)
			.body("size()", is(10));
		
	}
	
}