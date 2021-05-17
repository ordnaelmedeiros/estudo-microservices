package br.com.ordnaelmedeiros.ems.controllers;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.Video;
import br.com.ordnaelmedeiros.ems.models.Video.Rating;
import io.quarkus.test.common.RestAssuredURLManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class VideoControllerTest {

	@BeforeEach
	void beforeAll() {
		RestAssuredURLManager.setURL(false, "/videos");
	}
	
	@Test
	void fieldsNotNull() {
		Video e = new Video();
		when().body(e).post().then()
           .statusCode(400)
           .body(containsString("title: não deve ser nulo"));
	}
	
	@Test
	void crud() {
		
		Video entity = new Video();
		entity.setTitle("test");
		entity.setDescription("teste description");
		entity.setDuration(200);
		entity.setYearLaunched(1986);
		
		UUID id = when().body(entity).post().then()
			.statusCode(201)
			.extract().as(Video.class).getId();
        
        when().get("/"+id).then()
	    	.statusCode(200)
	    	.body("id", is(id.toString()))
	    	.body("title", is(entity.getTitle()))
	    	.body("description", is(entity.getDescription()))
	    	.body("duration", is(entity.getDuration()))
	    	.body("yearLaunched", is(entity.getYearLaunched()))
	    	.body("opened", is(false))
	    	.body("rating", is(Rating.L.name()))
	    	;
        
        entity.setTitle("test 2");
        entity.setDescription("teste description 2");
        entity.setDuration(300);
        entity.setYearLaunched(2000);
        entity.setOpened(true);
        entity.setRating(Rating.C14);
        
        when().body(entity).put("/"+id).then()
	    	.statusCode(204);
        
        when().get("/"+id).then()
	    	.statusCode(200)
	    	.statusCode(200)
	    	.body("id", is(id.toString()))
	    	.body("title", is(entity.getTitle()))
	    	.body("description", is(entity.getDescription()))
	    	.body("duration", is(entity.getDuration()))
	    	.body("yearLaunched", is(entity.getYearLaunched()))
	    	.body("opened", is(entity.getOpened()))
	    	.body("rating", is(entity.getRating().name()));
        
        when().delete("/"+id).then()
	    	.statusCode(204);
        
        when().get("/"+id).then()
	    	.statusCode(404);
	        		
	}
	
	@Test
	void list() {
		
		for (int i = 0; i < 10; i++) {
			
			Video e = new Video();
			e.setTitle("test " + i);
			
			when().body(e).post().then()
	           .statusCode(201);
			
		}
		
		when().get().then()
			.statusCode(200)
			.body("size()", is(10));
		
	}
	
}
