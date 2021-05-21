package br.com.ordnaelmedeiros.ems.models.validation.video;

import static java.util.stream.Collectors.toList;
import static javax.persistence.FlushModeType.COMMIT;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.ordnaelmedeiros.ems.core.exception.RestException;
import br.com.ordnaelmedeiros.ems.models.Category;
import br.com.ordnaelmedeiros.ems.models.Genre;
import br.com.ordnaelmedeiros.ems.models.Video;
import br.com.ordnaelmedeiros.ems.models.validation.VideoValidation;
import br.com.ordnaelmedeiros.ems.repository.GenreRespository;

@ApplicationScoped
public class VideoCategoryInGenreValidation implements VideoValidation {

	public static final String MSG = "Categorias selecionadas devem pertencer aos Gêneros";
	
	@Inject
	GenreRespository genreRespository;
	
	@Override
	public void valid(Video video) {
		if (!video.getGenres().isEmpty() && !video.getCategories().isEmpty()) {
			genreRespository.getEntityManager().setFlushMode(COMMIT);
			if (!categoriesIdOfGenres(genresId(video)).containsAll(categoriesId(video))) 
				throw new RestException(MSG);
		}
	}
	
	private List<UUID> genresId(Video video) {
		return video.getGenres().stream().map(Genre::getId).collect(toList());
	}
	
	private List<UUID> categoriesId(Video video) {
		return video.getCategories().stream().map(Category::getId).collect(toList());
	}
	
	private List<UUID> categoriesIdOfGenres(List<UUID> genresId) {
		return genreRespository.find("id in (?1)", genresId).stream()
				.flatMap(i -> i.getCategories().stream())
				.map(i -> i.getId())
				.collect(Collectors.toList());
	}
	

}
