package br.com.ordnaelmedeiros.ems.core.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator;

@MappedSuperclass
@JsonIdentityInfo(generator = PropertyGenerator.class, property = "id")
public class EntityBase {

	@Id
	private UUID id;

	private Boolean isActive;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	private LocalDateTime deletedAt;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}
	
	
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
		if (getIsActive()==null)
			setIsActive(true);
	}
	
}
