package com.kendrick.angularspringboot.roko.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

import java.net.URL;

@Value
@AllArgsConstructor
@Accessors(fluent = true)
@Builder(toBuilder = true)
public class SourceRef {

    URL url;
    URL thumbURL;
}
