package com.kendrick.angularspringboot.roko.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@Accessors(fluent = true)
public class Season {

    Long id;
    String name;
    Integer episodeCount;
    Boolean watched;
    ViewingStatus viewingStatus;

    public enum ViewingStatus{UNWATCHED, WATCHING, WATCHED}


}
