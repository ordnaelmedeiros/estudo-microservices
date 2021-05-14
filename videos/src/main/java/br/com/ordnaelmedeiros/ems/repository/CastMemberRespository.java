package br.com.ordnaelmedeiros.ems.repository;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import br.com.ordnaelmedeiros.ems.models.CastMember;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class CastMemberRespository implements PanacheRepositoryBase<CastMember, UUID> {

	//@Override
	public void delete(CastMember entity) {
		System.out.println("CastMemberRespository::markDeleted");
		entity.markDeleted();
	}
	
	//@Override
	public boolean deleteById(UUID id) {
		System.out.println("CastMemberRespository::deleteById");
		findByIdOptional(id).ifPresent(CastMember::markDeleted);
		return true;
	}
	
	public boolean teste() {
		System.out.println("CastMemberRespository:teste");
		return true;
	}
	
}
