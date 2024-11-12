package com.art.e_learning.dtos;

import com.art.e_learning.models.Course;
import static org.junit.jupiter.api.Assertions.*;
import com.art.e_learning.models.Section;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class SectionDtoTest {

    Section section = new Section("TestSection", 5, new Course(), null);
    SectionDto sectionDto;

    {
        section.getCourse().setId(1);
    }

    @Test
    void toResponse(){
        this.sectionDto = SectionDto.toResponse(this.section);

        assertEquals(this.section.getName(), this.sectionDto.name());
        assertEquals(this.section.getSectionOrder(), this.sectionDto.sectionOrder());
        assertEquals(this.section.getCourse().getId(), this.sectionDto.courseId());
        assertInstanceOf(SectionDto.class, this.sectionDto);
    }

    @Test
    void toListResponse(){
        List<Section> sections = new ArrayList<>();
        sections.add(this.section);
        List<SectionDto> sectionDtos = SectionDto.toResponseList(sections);

        assertEquals(sectionDtos.size(), sections.size());
        assertEquals(sectionDtos.getFirst().name(), sections.getFirst().getName());
        assertEquals(sectionDtos.getFirst().sectionOrder(), sections.getFirst().getSectionOrder());
        assertNotNull(sections.getFirst().getCourse());
        assertEquals(sectionDtos.getFirst().courseId(), sections.getFirst().getCourse().getId());
    }
}
