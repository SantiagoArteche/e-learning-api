package com.art.e_learning.dtos;

import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Resource;
import com.art.e_learning.models.Section;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LectureDtoTest {

    Lecture lecture = new Lecture("TestLecture", new Section(), null);
    LectureDto lectureDto;

    {
       this.lecture.getSection().setId(1);
    }

    @Test
    void toResponse(){
        this.lectureDto = LectureDto.toResponse(this.lecture);

        assertEquals(this.lectureDto.name(), this.lecture.getName());
        assertEquals(this.lectureDto.sectionId(), this.lecture.getSection().getId());
        assertInstanceOf(LectureDto.class, this.lectureDto);
    }

    @Test
    void toListResponse(){
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(this.lecture);
        List<LectureDto> lectureDtos = LectureDto.toListResponse(lectures);

        assertEquals(lectureDtos.size(), lectures.size());
        assertEquals(lectureDtos.getFirst().name(), lectures.getFirst().getName());
        assertEquals(lectureDtos.getFirst().sectionId(), lectures.getFirst().getSection().getId());
    }
}
