package com.art.e_learning.models;

import com.art.e_learning.generic.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lecture extends BaseEntity {
    @NotBlank(message = "Name is required")
    private String name;

    @ManyToOne
    @JoinColumn(name = "section_id")
    @JsonManagedReference
    private Section section;

    @OneToOne(mappedBy = "lecture")
    @JsonBackReference
    @JsonIgnore
    private Resource resource;
}
