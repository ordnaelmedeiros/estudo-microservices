package br.com.ordnaelmedeiros.ems.controllers;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.ordnaelmedeiros.ems.repository.CastMemberRespository;
import br.com.ordnaelmedeiros.ems.repository.Teste;

@Tag(name = "Hello")
@Path("/hello-resteasy")
public class GreetingResource {
	
//	@Inject
//	CastMemberRespository castMemberRespository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
    	try {
//    		castMemberRespository.teste();
//    		castMemberRespository.deleteById(UUID.randomUUID());
    		new CastMemberRespository().teste();
    		new Teste().teste();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        return "Hello RESTEasy";
    }
}