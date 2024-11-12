package com.art.e_learning.models;

import com.art.e_learning.generic.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Resource extends BaseEntity {
    private String name;

    private int size;

    private String url;

    @OneToOne(mappedBy = "resource")
    @JsonBackReference
    private Lecture lecture;
}
