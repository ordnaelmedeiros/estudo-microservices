package br.com.ordnaelmedeiros.ems.models.serializer;

import static java.util.Optional.ofNullable;

import java.util.UUID;

import com.fasterxml.jackson.databind.util.StdConverter;

import br.com.ordnaelmedeiros.ems.models.Category;

public class CategoryJsonConverter {

	public static class Serialize extends StdConverter<Category, String> {
		@Override
		public String convert(Category value) {
			return ofNullable(value).map(Category::getId).map(UUID::toString).orElse(null);
		}
	}
	
	public static class Deserialize extends StdConverter<String, Category> {
		@Override
		public Category convert(String value) {
			return Category.withId(value);
		}
	}
	
}
