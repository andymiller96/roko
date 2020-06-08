package com.kendrick.angularspringboot.roko.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

import java.time.Year;
import java.util.List;
import java.util.Set;

@Builder(toBuilder = true)
@Accessors(fluent = true)
@AllArgsConstructor
@Value
public class Anime {
    long id;
    String nameEnglish;
    String nameJapanese;
    Float rating;
    Year year;
    String description;
    List<Season> seasons;
    Boolean seriesOngoing;
    Set<SourceRef> sourceRefSet;

    public Boolean seriesWatched() {
        return seasons.stream().allMatch(Season::watched);
    }





}































