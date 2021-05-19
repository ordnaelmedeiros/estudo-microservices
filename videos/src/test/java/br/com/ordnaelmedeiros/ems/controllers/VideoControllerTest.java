package br.com.ordnaelmedeiros.ems.controllers;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.Category;
import br.com.ordnaelmedeiros.ems.models.Genre;
import br.com.ordnaelmedeiros.ems.models.Video;
import br.com.ordnaelmedeiros.ems.models.Video.Rating;
import io.quarkus.test.common.RestAssuredURLManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class VideoControllerTest {

	static Category category1 = new Category();
	static Category category2 = new Category();
	
	static Genre genre1 = new Genre();
	static Genre genre2 = new Genre();
	
	@BeforeAll
	static void setUp() {
		
		RestAssuredURLManager.setURL(false, "/categories");
		category1.setName("Category 1");
		category2.setName("Category 2");
		category1 = when().body(category1).post().then().extract().as(Category.class);
		category2 = when().body(category2).post().then().extract().as(Category.class);
		
		RestAssuredURLManager.setURL(false, "/genres");
		genre1.setName("Genre 1");
		genre1.getCategories().add(category1);
		genre2.setName("Genre 2");
		genre2.getCategories().add(category2);
		genre1 = when().body(genre1).post().then().extract().as(Genre.class);
		genre2 = when().body(genre2).post().then().extract().as(Genre.class);
		
	}
	
	@BeforeEach
	void beforeEach() {
		RestAssuredURLManager.setURL(false, "/videos");
	}
	
	@Test
	void fieldsNotNull() {
		Video e = new Video();
		when().body(e).post().then()
           .statusCode(400)
           .body(containsString("title: não deve ser nulo"))
           .body(containsString("description: não deve ser nulo"))
           .body(containsString("yearLaunched: não deve ser nulo"))
           .body(containsString("duration: não deve ser nulo"))
           ;
	}
	
	@Test
	void crud() {
		
		Video entity = new Video();
		entity.setTitle("test");
		entity.setDescription("teste description");
		entity.setDuration(200);
		entity.setYearLaunched(1986);
		entity.getCategories().add(category1);
		entity.getCategories().add(category2);
		entity.getGenres().add(genre1);
		
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
	    	.body("categories.size()", is(2))
	    	.body("categories[0].name", is(category1.getName()))
	    	.body("categories[1].name", is(category2.getName()))
	    	.body("genres.size()", is(1))
	    	.body("genres[0].name", is(genre1.getName()))
	    	;
        
        entity.setTitle("test 2");
        entity.setDescription("teste description 2");
        entity.setDuration(300);
        entity.setYearLaunched(2000);
        entity.setOpened(true);
        entity.setRating(Rating.C14);
        entity.getCategories().remove(category1);
        entity.getGenres().add(genre2);
        
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
	    	.body("rating", is(entity.getRating().name()))
	    	.body("categories.size()", is(1))
	    	.body("categories[0].name", is(category2.getName()))
	    	.body("genres.size()", is(2))
	    	.body("genres[0].name", is(genre1.getName()))
	    	.body("genres[1].name", is(genre2.getName()));
        
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
			e.setDescription("teste description");
			e.setDuration(200);
			e.setYearLaunched(1986);
			
			when().body(e).post().then()
	           .statusCode(201);
			
		}
		
		when().get().then()
			.statusCode(200)
			.body("size()", greaterThanOrEqualTo(10));
		
	}
	
}
