package br.com.ordnaelmedeiros.ems.controllers;

import java.util.UUID;

import br.com.ordnaelmedeiros.ems.models.Genre;
import br.com.ordnaelmedeiros.ems.repository.GenreRespository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;


@ResourceProperties(hal = true, path = "genres", paged = true)
public interface GenreController extends PanacheRepositoryResource<GenreRespository, Genre, UUID> { 
}
