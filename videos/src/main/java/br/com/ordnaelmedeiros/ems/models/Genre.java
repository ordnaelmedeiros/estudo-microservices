package br.com.ordnaelmedeiros.ems.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import br.com.ordnaelmedeiros.ems.core.entity.EntityBase;

@Entity
@Table(name = "genres")
@Where(clause = "deleted_at is null")
public class Genre extends EntityBase {

	@NotNull
	@Size(max = 255)
	private String name;
	
	@NotEmpty
	@ManyToMany
	@JsonIncludeProperties(value = {"id", "name"})
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
	
}
