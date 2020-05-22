package com.kendrick.angularspringboot.roko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kendrick.angularspringboot.roko.model.Anime;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long>{
	Page<Anime> findBySeriesId(Long id, Pageable pageable);
	Optional<Anime> findByIdAndSeriesId(Long id, Long seriesId);
}
