package br.com.ordnaelmedeiros.ems.repository;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import br.com.ordnaelmedeiros.ems.models.Video;
import br.com.ordnaelmedeiros.ems.models.validation.VideoValidation;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class VideoRespository implements PanacheRepositoryBase<Video, UUID> {

	@Inject
	Instance<VideoValidation> validations;
	
	@Override
	public void delete(Video entity) {
		entity.markDeleted();
	}
	
	@Override
	public boolean deleteById(UUID id) {
		findByIdOptional(id).ifPresent(this::delete);
		return true;
	}
	
	public boolean valid(Video video) {
		validations.stream().forEach(v -> v.valid(video));
		return true;
	}
	
}
