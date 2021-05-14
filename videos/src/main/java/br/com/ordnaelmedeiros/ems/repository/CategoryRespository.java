package br.com.ordnaelmedeiros.ems.repository;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import br.com.ordnaelmedeiros.ems.models.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class CategoryRespository implements PanacheRepositoryBase<Category, UUID> {

	@Override
	public void delete(Category entity) {
		System.out.println("CategoryRespository::markDeleted");
		entity.markDeleted();
	}
	
	@Override
	public boolean deleteById(UUID id) {
		System.out.println("CategoryRespository::deleteById");
		findByIdOptional(id).ifPresent(Category::markDeleted);
		return true;
	}
	
}
