package br.com.ordnaelmedeiros.ems;

import static br.com.ordnaelmedeiros.ems.TestUtils.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ordnaelmedeiros.ems.models.Category;
import br.com.ordnaelmedeiros.ems.models.Genre;
import br.com.ordnaelmedeiros.ems.models.Video;
import io.quarkus.test.common.RestAssuredURLManager;

public class JsonTest {

	@Test
	void teste() {
		
		var category1 = new Category();
		category1.setId(UUID.randomUUID());
		category1.setName("Category 1");
		var category2 = new Category();
		category2.setId(UUID.randomUUID());
		category2.setName("Category 2");
		
		var genre1 = new Genre();
		var genre2 = new Genre();
		genre1.setName("Genre 1");
		genre1.getCategories().add(category1);
		genre1.getCategories().add(category2);
		genre2.setName("Genre 2");
		genre2.getCategories().add(category2);
		
		Video entity = new Video();
		entity.setTitle("test");
		entity.setDescription("teste description");
		entity.setDuration(200);
		entity.setYearLaunched(1986);
		entity.getGenres().add(genre1);
		entity.getCategories().add(category1);
		entity.getCategories().add(category2);
		
		try {
			String valor = new ObjectMapper().writeValueAsString(entity);
			System.out.println(valor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
