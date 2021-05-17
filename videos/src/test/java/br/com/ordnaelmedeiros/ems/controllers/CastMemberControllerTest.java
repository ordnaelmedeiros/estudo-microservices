package br.com.ordnaelmedeiros.ems.controllers;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.CastMember;
import io.quarkus.test.common.RestAssuredURLManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@Tag("integration")
class CastMemberControllerTest {

	@BeforeEach
	void beforeAll() {
		RestAssuredURLManager.setURL(false, "/cast-members");
	}
	
	@Test
	void fieldsNotNull() {
		CastMember castMember = new CastMember();
		when().body(castMember).post().then()
           .statusCode(400)
           .body(containsString("name: não deve ser nulo"))
           .body(containsString("type: não deve ser nulo"));
	}
	
	@Test
	void crud() {
		
		CastMember castMember = new CastMember();
		castMember.setName("test");
		castMember.setType(CastMember.Type.DIRECTOR);
		
		castMember = when().body(castMember).post().then()
			.statusCode(201)
			.extract().as(CastMember.class);
        
        when().get("/"+castMember.getId()).then()
	    	.statusCode(200)
	    	.body("id", is(castMember.getId().toString()))
	    	.body("isActive", is(true))
	    	.body("type", is(castMember.getType().toString()))
	    	.body("createdAt", notNullValue())
	    	.body("updatedAt", notNullValue())
	    	.body("name", is(castMember.getName()));
        
        castMember.setName("test 2");
        castMember.setIsActive(false);
        castMember.setType(CastMember.Type.ACTOR);
        
        when().body(castMember).put("/"+castMember.getId()).then()
	    	.statusCode(204);
        
        when().get("/"+castMember.getId()).then()
	    	.statusCode(200)
	    	.body("id", is(castMember.getId().toString()))
	    	.body("name", is(castMember.getName()))
	    	.body("type", is(castMember.getType().toString()))
	    	.body("isActive", is(false));
        
        when().delete("/"+castMember.getId()).then()
	    	.statusCode(204);
        
        when().get("/"+castMember.getId()).then()
	    	.statusCode(404);
	        		
	}
	
	@Test
	void list() {
		
		for (int i = 0; i < 10; i++) {
			
			var item = new CastMember();
			item.setName("test " + i);
			item.setType(CastMember.Type.DIRECTOR);
			
			when().body(item).post().then()
	           .statusCode(201);
			
		}
		
		when().get().then()
			.statusCode(200)
			.body("size()", is(10));
		
	}
	
}
