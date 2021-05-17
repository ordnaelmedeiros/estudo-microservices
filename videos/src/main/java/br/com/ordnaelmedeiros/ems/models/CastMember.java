package br.com.ordnaelmedeiros.ems.models;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "cast_members")
@Where(clause = "deleted_at is null")
public class CastMember extends EntityBase {
	
	@NotNull
	@Size(max = 255)
	private String name;
	
	@NotNull
	@Enumerated(STRING)
	private Type type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	public enum Type {
		DIRECTOR,
		ACTOR;
	}
	
}
