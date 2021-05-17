package br.com.ordnaelmedeiros.ems.controllers;

import java.util.UUID;

import br.com.ordnaelmedeiros.ems.models.Video;
import br.com.ordnaelmedeiros.ems.repository.VideoRespository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;
import io.quarkus.rest.data.panache.ResourceProperties;


@ResourceProperties(hal = true, path = "videos", paged = true)
public interface VideoCRUDController extends PanacheRepositoryResource<VideoRespository, Video, UUID> { 
}
