package com.kamilisler.springwithkafka.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MappedPackage {
    @NotNull
    private Long id;
    @NotNull
    private String createdAt;
    @NotNull
    private String lastUpdatedAt;
    private Integer collectionDuration;
    private Integer deliveryDuration;
    @NotNull
    private Integer eta;
    private Integer leadTime;
    private Boolean orderInTime;
}
