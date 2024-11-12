package com.art.e_learning.dtos;

import com.art.e_learning.models.Lecture;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record LectureDto(
        Integer id,
        @NotBlank(message = "Name is required")
        String name,
        Integer sectionId,
        Integer resourceId) {

    public static LectureDto toResponse(Lecture lecture){
        return new LectureDto(lecture.getId(), lecture.getName(),
                lecture.getSection() != null ? lecture.getSection().getId() : null,
                lecture.getResource() != null ? lecture.getResource().getId() : null);
    }

    public static List<LectureDto> toResponseList(List<Lecture> lectures){
        return lectures.stream().map(LectureDto::toResponse).toList();
    }
}
