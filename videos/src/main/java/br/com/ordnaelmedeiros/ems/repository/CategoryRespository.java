package br.com.ordnaelmedeiros.ems.repository;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import br.com.ordnaelmedeiros.ems.models.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class CategoryRespository implements PanacheRepositoryBase<Category, UUID> {

	@Override
	public void delete(Category entity) {
		entity.markDeleted();
	}
	
	@Override
	public boolean deleteById(UUID id) {
		findByIdOptional(id).ifPresent(Category::markDeleted);
		return true;
	}
	
}
