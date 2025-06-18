package com.app.drrim.domain;

import com.app.drrim.enums.MedicinesEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor

public class Medication implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Builder.Default
    private String id = new ObjectId().toHexString();

    private String name;
    private MedicinesEnum type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime time;
    private String note;
}
