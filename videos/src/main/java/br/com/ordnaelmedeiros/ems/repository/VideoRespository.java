package br.com.ordnaelmedeiros.ems.repository;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import br.com.ordnaelmedeiros.ems.models.Video;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class VideoRespository implements PanacheRepositoryBase<Video, UUID> {

	@Override
	public void delete(Video entity) {
		entity.markDeleted();
	}
	
	@Override
	public boolean deleteById(UUID id) {
		findByIdOptional(id).ifPresent(this::delete);
		return true;
	}
	
}
