package br.com.ordnaelmedeiros.ems.controllers;

import java.util.UUID;

import br.com.ordnaelmedeiros.ems.models.CastMember;
import br.com.ordnaelmedeiros.ems.repository.CastMemberRespository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;


@ResourceProperties(hal = true, path = "cast-members", paged = true)
public interface CastMemberController extends PanacheRepositoryResource<CastMemberRespository, CastMember, UUID> { 
}
