package br.com.ordnaelmedeiros.ems.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.ordnaelmedeiros.ems.models.Genre;
import br.com.ordnaelmedeiros.ems.repository.GenreRespository;

@Path("/genres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreController {
	
	@Inject
	GenreRespository genreRespository;
	
	@GET @Path("/deleted")
	public List<Genre> deleted() {
		return genreRespository.deleted();
	}
	
}
