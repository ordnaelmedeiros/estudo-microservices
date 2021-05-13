package br.com.ordnaelmedeiros.ems.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "categories")
@Where(clause="deleted_at is null")
public class Category {

	@Id
	public UUID id;
	
	@NotNull
	public String name;
	
	@Column(columnDefinition = "text")
	public String description;
	
	//@NotNull
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
	
}
