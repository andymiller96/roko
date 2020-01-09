package com.kendrick.angularspringboot.roko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kendrick.angularspringboot.roko.model.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long>{

}
