package br.com.ordnaelmedeiros.ems.repository;

import java.util.List;
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
		findByIdOptional(id).ifPresent(this::delete);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Genre> deleted() {
		return (List<Genre>) getEntityManager()
				.createNativeQuery("select * from genres where deleted_at is not null", Genre.class)
				.getResultList();
//		return find("deletedAt is not null")
//			.list();
	}
	
}
