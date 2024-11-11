package com.art.e_learning.dtos;

import com.art.e_learning.models.Lecture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InheritedBaseResource {
    private Integer id;

    private String name;

    private int size;

    private String url;

    private Lecture lecture;

    private Integer length;

    private String content;

    private String type;
}
