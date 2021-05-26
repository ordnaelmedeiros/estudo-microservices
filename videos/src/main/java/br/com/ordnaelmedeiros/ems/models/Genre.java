package br.com.ordnaelmedeiros.ems.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.ordnaelmedeiros.ems.core.entity.EntityBase;
import br.com.ordnaelmedeiros.ems.models.serializer.CategoryJsonConverter;

@Entity
@Table(name = "genres")
@Where(clause = "deleted_at is null")
public class Genre extends EntityBase {

	@NotNull
	@Size(max = 255)
	private String name;
	
	@NotEmpty
	@ManyToMany
	@JsonProperty("categories_id")
	@JsonSerialize(contentConverter = CategoryJsonConverter.Serialize.class)
	@JsonDeserialize(contentConverter = CategoryJsonConverter.Deserialize.class)
	List<Category> categories;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Category> getCategories() {
		if (categories == null)
			categories = new ArrayList<>();
		return categories;
	}
	public static Genre withId(String value) {
		if (value == null)
			return null;
		var g = new Genre();
		g.setId(UUID.fromString(value));
		return g;
	}
	
}
