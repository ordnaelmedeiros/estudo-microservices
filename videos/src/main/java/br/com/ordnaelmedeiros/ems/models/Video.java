package br.com.ordnaelmedeiros.ems.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "videos")
@Where(clause = "deleted_at is null")
public class Video extends EntityBase {
	
	@NotNull
	@Size(max = 255)
	private String title;
	
	@Column(columnDefinition = "text")
	private String description;
	
	private Integer yearLaunched;
	
	private Boolean opened;
	
	@Enumerated(EnumType.STRING)
	private Rating rating;
	
	private Integer duration;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String name) {
		this.title = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getYearLaunched() {
		return yearLaunched;
	}
	public void setYearLaunched(Integer yearLaunched) {
		this.yearLaunched = yearLaunched;
	}

	public Boolean getOpened() {
		return opened;
	}
	public void setOpened(Boolean opened) {
		this.opened = opened;
	}

	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public enum Rating {
		L, C10, C12, C14, C16, C18
	}
	
	@PrePersist
	@PreUpdate
	private void defaultValues() {
		if (opened == null)
			opened = false;
		if (rating == null)
			rating = Rating.L;
	}
	
}
