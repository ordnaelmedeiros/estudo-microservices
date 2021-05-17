package br.com.ordnaelmedeiros.ems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "categories")
@Where(clause = "deleted_at is null")
public class Category extends EntityBase {
	
	@NotNull
	@Size(max = 255)
	private String name;
	
	@Column(columnDefinition = "text")
	private String description;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
