package br.com.ordnaelmedeiros.ems.models;

import static javax.persistence.EnumType.STRING;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "cast_members")
@Where(clause = "deleted_at is null")
public class CastMember {

	@Id
	public UUID id;
	
	@NotNull
	@Size(max = 255)
	public String name;
	
	@NotNull
	@Enumerated(STRING)
	public Type type;
	
	public Boolean isActive;
	
	@CreationTimestamp
	@Column(updatable = false)
	public LocalDateTime createdAt;
	
	@UpdateTimestamp
	public LocalDateTime updatedAt;
	
	public LocalDateTime deletedAt;
	
	public void markDeleted() {
		this.deletedAt = LocalDateTime.now();
	}
	
	@PrePersist
	private void prePersist() {
		id = UUID.randomUUID();
		preUpdate();
	}
	@PreUpdate
	private void preUpdate() {
		if (isActive==null)
			isActive = true;
	}
	
	public static enum Type {
		DIRECTOR,
		ACTOR;
	}
	
}
