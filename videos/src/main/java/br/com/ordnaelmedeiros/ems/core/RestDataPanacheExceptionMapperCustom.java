package br.com.ordnaelmedeiros.ems.core;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.quarkus.hibernate.orm.rest.data.panache.runtime.RestDataPanacheExceptionMapper;
import io.quarkus.rest.data.panache.RestDataPanacheException;

@Provider
public class RestDataPanacheExceptionMapperCustom extends RestDataPanacheExceptionMapper {
	@Override
	public Response toResponse(RestDataPanacheException exception) {
		if (exception.getCause()!=null && exception.getCause() instanceof RestException) {
			return new RestExceptionMapper().toResponse((RestException) exception.getCause());
		}
		return super.toResponse(exception);
	}
}
