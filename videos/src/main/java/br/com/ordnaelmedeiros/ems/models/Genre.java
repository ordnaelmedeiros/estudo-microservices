package br.com.ordnaelmedeiros.ems.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "genres")
@Where(clause = "deleted_at is null")
public class Genre extends EntityBase {

	@NotNull
	@Size(max = 255)
	public String name;
	
}
