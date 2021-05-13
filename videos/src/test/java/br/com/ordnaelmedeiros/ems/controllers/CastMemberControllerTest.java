package br.com.ordnaelmedeiros.ems.controllers;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.ordnaelmedeiros.ems.models.CastMember;
import io.quarkus.test.common.RestAssuredURLManager;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CastMemberControllerTest {

	@BeforeEach
	public void beforeAll() {
		RestAssuredURLManager.setURL(false, "/cast-members");
	}
	
	@Test
	public void fieldsNotNull() {
		CastMember castMember = new CastMember();
		when().body(castMember).post().then()
           .statusCode(400)
           .body(containsString("name: não deve ser nulo"))
           .body(containsString("type: não deve ser nulo"));
	}
	
	@Test
	public void crud() {
		
		CastMember castMember = new CastMember();
		castMember.name = "test";
		castMember.type = CastMember.Type.DIRECTOR;
		
		castMember = when().body(castMember).post().then()
			.statusCode(201)
			.extract().as(CastMember.class);
        
        when().get("/"+castMember.id).then()
	    	.statusCode(200)
	    	.body("id", is(castMember.id.toString()))
	    	.body("isActive", is(true))
	    	.body("type", is(castMember.type.toString()))
	    	.body("createdAt", notNullValue())
	    	.body("updatedAt", notNullValue())
	    	.body("name", is(castMember.name));
        
        castMember.name = "test 2";
        castMember.isActive = false;
        castMember.type = CastMember.Type.ACTOR;
        
        when().body(castMember).put("/"+castMember.id).then()
	    	.statusCode(204);
        
        when().get("/"+castMember.id).then()
	    	.statusCode(200)
	    	.body("id", is(castMember.id.toString()))
	    	.body("name", is(castMember.name))
	    	.body("type", is(castMember.type.toString()))
	    	.body("isActive", is(false));
        
        when().delete("/"+castMember.id).then()
	    	.statusCode(204);
        
        when().get("/"+castMember.id).then()
	    	.statusCode(404);
	        		
	}
	
	@Test
	public void list() {
		
		for (int i = 0; i < 10; i++) {
			
			var item = new CastMember();
			item.name = "test " + i;
			item.type = CastMember.Type.DIRECTOR;
			
			when().body(item).post().then()
	           .statusCode(201);
			
		}
		
		when().get().then()
			.statusCode(200)
			.body("size()", is(10));
		
	}
	
}
