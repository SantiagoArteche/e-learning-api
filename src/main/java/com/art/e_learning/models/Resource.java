package com.art.e_learning.models;

import com.art.e_learning.generic.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lecture_id")
    @JsonManagedReference
    private Lecture lecture;
}
