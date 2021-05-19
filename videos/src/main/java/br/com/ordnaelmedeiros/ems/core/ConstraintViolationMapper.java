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
		var msg = exception.getMessage();
		if (exception.getConstraintViolations()!=null && !exception.getConstraintViolations().isEmpty())
			msg = exception.getConstraintViolations().stream()
	        	.map(this::cvToMsg)
	        	.collect(Collectors.joining(", "));
		return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
	}
	
	private String cvToMsg(ConstraintViolation<?> cv) {
		return Joiner.on(": ").join(lastName(cv.getPropertyPath()), cv.getMessage());
	}

	private String lastName(Path path) {
		var iterator = path.iterator();
		var name = "";
		while(iterator.hasNext()) {
			name = iterator.next().toString();
		}
		return name;
	}
	
}
