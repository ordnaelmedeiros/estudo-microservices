package br.com.ordnaelmedeiros.ems.repository;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import br.com.ordnaelmedeiros.ems.models.CastMember;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class CastMemberRespository implements PanacheRepositoryBase<CastMember, UUID> {

	@Override
	public void delete(CastMember entity) {
		entity.markDeleted();
	}
	
	@Override
	public boolean deleteById(UUID id) {
		findByIdOptional(id).ifPresent(this::delete);
		return true;
	}
	
}
