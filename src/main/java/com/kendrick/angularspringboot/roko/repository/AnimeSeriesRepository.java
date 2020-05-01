package com.kendrick.angularspringboot.roko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kendrick.angularspringboot.roko.model.AnimeSeries;

@Repository
public interface AnimeSeriesRepository extends JpaRepository<AnimeSeries, Long>{

}
