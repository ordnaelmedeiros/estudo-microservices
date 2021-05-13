package br.com.ordnaelmedeiros.ems.core;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.common.base.Joiner;

@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		var msg = exception.getConstraintViolations().stream()
        	.map(this::cvToMsg)
        	.collect(Collectors.joining(", "));
		return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
	}
	
	private String cvToMsg(ConstraintViolation<?> cv) {
		return Joiner.on(": ").join(lastName(cv.getPropertyPath()), cv.getMessage()).toString();
	}

	private String lastName(Path path) {
		var iterator = path.iterator();
		String name = "";
		while(iterator.hasNext()) {
			name = iterator.next().toString();
		}
		return name;
	}
	
}
