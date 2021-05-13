package br.com.ordnaelmedeiros.ems.controllers;

import java.util.UUID;

import br.com.ordnaelmedeiros.ems.models.Category;
import br.com.ordnaelmedeiros.ems.repository.CategoryRespository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;


@ResourceProperties(hal = true, path = "categories", paged = true)
public interface CategoryController extends PanacheRepositoryResource<CategoryRespository, Category, UUID> { 
}
