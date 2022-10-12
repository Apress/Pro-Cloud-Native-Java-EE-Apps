package com.example.jwallet.core.control;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
	@Override
	public Response toResponse(ConstraintViolationException exception) {
		StringBuilder builder = new StringBuilder();
		for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
			String path = cv.getPropertyPath().toString().split("\\.")[2];
			builder.append(path).append(" - ").append(cv.getMessage()).append("\n");
		}
		return  Response.status(Response.Status.BAD_REQUEST)
				.entity(builder.toString())
				.build();
	}
}
