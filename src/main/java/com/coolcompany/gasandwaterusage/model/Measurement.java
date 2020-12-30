package com.coolcompany.gasandwaterusage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JsonIgnore
    private User user;

    @PastOrPresent
    @Column
    private LocalDate measurementDate;

    @Schema(required = true)
    @NotNull
    @Positive(message = "Must be greater then zero")
    @Column
    private Integer coldWater;

    @Schema(required = true)
    @NotNull
    @Positive(message = "Must be greater then zero")
    @Column
    private Integer hotWater;

    @Schema(required = true)
    @NotNull
    @Positive(message = "Must be greater then zero")
    @Column
    private Integer gas;
}
