package com.art.e_learning.dtos;

import com.art.e_learning.models.Section;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SectionDto(
        Integer id,
        @NotBlank(message = "Name is required")
        String name,
        Integer sectionOrder,
        Integer courseId) {
    public static SectionDto toResponse(Section section){
        return new SectionDto(section.getId(), section.getName(), section.getSectionOrder(), section.getCourse() != null ? section.getCourse().getId() : null);
    }

    public static List<SectionDto> toResponseList(List<Section> sections){
        return sections.stream().map(SectionDto::toResponse).toList();
    }
}
