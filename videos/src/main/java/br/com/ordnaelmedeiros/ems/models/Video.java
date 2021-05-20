package br.com.ordnaelmedeiros.ems.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FlushModeType;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import br.com.ordnaelmedeiros.ems.repository.GenreRespository;
import io.quarkus.arc.Arc;
import io.quarkus.arc.ArcContainer;

@Entity
@Table(name = "videos")
@Where(clause = "deleted_at is null")
public class Video extends EntityBase {
	
	@NotNull
	@Size(max = 255)
	private String title;
	
	@NotNull
	@Column(columnDefinition = "text")
	private String description;
	
	@NotNull
	private Integer yearLaunched;
	
	private Boolean opened;
	
	@Enumerated(EnumType.STRING)
	private Rating rating;
	
	@NotNull
	private Integer duration;
	
	@ManyToMany
	@JsonIncludeProperties(value = {"id", "name"})
	private List<Category> categories;
	
	@ManyToMany
	@JsonIncludeProperties(value = {"id", "name"})
	private List<Genre> genres;
	
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
	
	public List<Category> getCategories() {
		if (categories == null)
			categories = new ArrayList<>();
		return categories;
	}
	public List<Genre> getGenres() {
		if (genres == null)
			genres = new ArrayList<>();
		return genres;
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
		this.valid();
	}
	
	private void valid() {
		try {
			System.out.println("Videos::valid");
			
			if (!getGenres().isEmpty() && !getCategories().isEmpty()) {
				
				ArcContainer arcContainer = Arc.container();
				var genreRespository = arcContainer.instance(GenreRespository.class).get();
				genreRespository.getEntityManager().setFlushMode(FlushModeType.COMMIT);
				//var categoryRespository = arcContainer.instance(CategoryRespository.class).get();
				
				var genresId = getGenres().stream().map(Genre::getId).collect(Collectors.toList());
				var categoriesId = getCategories().stream().map(Category::getId).collect(Collectors.toList());
				var categoriesIdOfGenres = genreRespository.find("id in (?1)", genresId).stream()
						.flatMap(i -> i.getCategories().stream())
						.map(i -> i.getId())
						.collect(Collectors.toList());
				
				var containsAll = categoriesIdOfGenres.containsAll(categoriesId);
				
				System.out.println("categoriesId: " + categoriesId);
				System.out.println("categoriesIdOfGenres: " + categoriesIdOfGenres);
				System.out.println("containsAll: " + containsAll);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//throw new RestException("eroooooo");
		//ArcContainer arcContainer = Arc.container();
		
	}
	
}
