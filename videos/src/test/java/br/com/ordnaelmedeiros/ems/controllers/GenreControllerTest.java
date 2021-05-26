package br.com.ordnaelmedeiros.ems.controllers;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.Category;
import br.com.ordnaelmedeiros.ems.models.Genre;
import io.quarkus.test.common.RestAssuredURLManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GenreControllerTest {
	
	static Category category1 = new Category();
	static Category category2 = new Category();
	
	@BeforeAll
	static void setUp() {
		
		category1.setName("Category 1");
		category2.setName("Category 2");
		
		RestAssuredURLManager.setURL(false, "/categories");
		category1 = when().body(category1).post().then().extract().as(Category.class);
		category2 = when().body(category2).post().then().extract().as(Category.class);
		
	}
	
	@BeforeEach
	void beforeAll() {
		RestAssuredURLManager.setURL(false, "/genres");
	}
	
	@Test
	void fieldsNotNull() {
		Genre genre = new Genre();
		when().body(genre).post().then()
           .statusCode(400)
           .body(containsString("name: não deve ser nulo"))
           .body(containsString("categories: não deve estar vazio"));
	}
	
	@Test
	void crud() {
		
		Genre genre = new Genre();
		genre.setName("test");
		genre.getCategories().add(category1);
		
		genre = when().body(genre).post().then()
           .statusCode(201)
           .extract().as(Genre.class);
        
        when().get("/"+genre.getId()).then()
	    	.statusCode(200)
	    	.body("id", is(genre.getId().toString()))
	    	.body("name", is(genre.getName()))
	    	.body("isActive", is(true))
	    	.body("createdAt", notNullValue())
	    	.body("updatedAt", notNullValue())
	    	.body("deletedAt", nullValue())
	    	.body("categories_id.size()", is(1))
	    	.body("categories_id", hasItems(category1.getId().toString()))
	    	;
        
        genre.setName("test 2");
        genre.setIsActive(false);
        genre.getCategories().remove(0);
        genre.getCategories().add(category2);
        
        when().body(genre).put("/"+genre.getId()).then()
	    	.statusCode(204);
        
        when().get("/"+genre.getId()).then()
	    	.statusCode(200)
	    	
	    	.body("id", is(genre.getId().toString()))
	    	.body("name", is(genre.getName()))
	    	.body("isActive", is(false))
	    	.body("categories_id.size()", is(1))
	    	.body("categories_id", hasItems(category2.getId().toString()));
        
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
			gen.getCategories().add(category1);
			gen.setName("test " + i);
			
			when().body(gen).post().then()
	           .statusCode(201);
			
		}
		
		when().get().then()
			.statusCode(200)
			.body("size()", greaterThanOrEqualTo(10));
		
	}
	
}
