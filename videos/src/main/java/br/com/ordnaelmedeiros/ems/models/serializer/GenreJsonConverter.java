package br.com.ordnaelmedeiros.ems.models.serializer;

import static java.util.Optional.ofNullable;

import java.util.UUID;

import com.fasterxml.jackson.databind.util.StdConverter;

import br.com.ordnaelmedeiros.ems.models.Genre;

public class GenreJsonConverter {
	
	private GenreJsonConverter() {
	}

	public static class Serialize extends StdConverter<Genre, String> {
		@Override
		public String convert(Genre value) {
			return ofNullable(value).map(Genre::getId).map(UUID::toString).orElse(null);
		}
	}
	
	public static class Deserialize extends StdConverter<String, Genre> {
		@Override
		public Genre convert(String value) {
			return Genre.withId(value);
		}
	}
	
}
