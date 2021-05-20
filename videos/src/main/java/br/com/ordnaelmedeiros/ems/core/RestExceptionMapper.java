package br.com.ordnaelmedeiros.ems.core;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {
	@Override
	public Response toResponse(RestException exception) {
		return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
}
