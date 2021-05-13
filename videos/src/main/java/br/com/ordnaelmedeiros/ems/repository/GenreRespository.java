package br.com.ordnaelmedeiros.ems.repository;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import br.com.ordnaelmedeiros.ems.models.Genre;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class GenreRespository implements PanacheRepositoryBase<Genre, UUID> {

	@Override
	public void delete(Genre entity) {
		entity.markDeleted();
	}
	
	@Override
	public boolean deleteById(UUID id) {
		findByIdOptional(id).ifPresent(Genre::markDeleted);
		return true;
	}
	
}
