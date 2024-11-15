package com.art.e_learning.services;

import com.art.e_learning.dtos.SectionDto;
import com.art.e_learning.models.Course;
import com.art.e_learning.models.Section;
import com.art.e_learning.repositories.CourseRepository;
import com.art.e_learning.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SectionServiceTest {

    @Mock
    private SectionRepository repository;
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private SectionService service;

    private List<Section> sections;
    private Section section;
    private Course course;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.course = new Course("TestCourseTitle", "TestDescription", new ArrayList<>(), null);
        this.course.setId(2);

        this.section = new Section("TestSectionTitle", 100, this.course, null);
        this.section.setId(1);

        this.sections = new ArrayList<>();
        this.sections.add(this.section);
    }

    @Test
    void get_all(){
        when(this.repository.findAll()).thenReturn(this.sections);

        List<SectionDto> allSections = this.service.getAll();

        assertEquals(allSections.size(), this.sections.size());
        assertEquals(allSections.getFirst().name(), this.sections.getFirst().getName());
        assertEquals(allSections.getFirst().sectionOrder(), this.sections.getFirst().getSectionOrder());
        assertEquals(allSections.getFirst().courseId(), this.sections.getFirst().getCourse().getId());

        verify(this.repository, times(1)).findAll();
    }

    @Test
    void get_by_id(){
        int id = 1;
        when(this.repository.findById(id)).thenReturn(Optional.of(this.section));

        SectionDto sectionDto = this.service.getById(id);

        assertEquals(sectionDto.id(), this.section.getId());
        assertEquals(sectionDto.name(), this.section.getName());
        assertNotNull(this.section.getCourse());
        assertEquals(sectionDto.courseId(), this.section.getCourse().getId());
        assertEquals(sectionDto.sectionOrder(), this.section.getSectionOrder());

        verify(this.repository, times(1)).findById(id);
    }

    @Test
    void get_by_id_not_found(){
        int id = 1;
        when(this.repository.findById(id)).thenReturn(Optional.empty());

        SectionDto sectionDto = this.service.getById(id);

        assertNull(sectionDto);

        verify(this.repository, times(1)).findById(id);
    }

    @Test
    void create(){
        Integer courseId = this.section.getCourse().getId();
        when(this.courseRepository.findById(courseId)).thenReturn(Optional.of(this.course));
        when(this.repository.save(any(Section.class))).thenReturn(this.section);

        SectionDto newSection = this.service.create(SectionDto.toResponse(this.section));

        assertEquals(newSection.id(), this.section.getId());
        assertEquals(newSection.name(), this.section.getName());
        assertNotNull(this.section.getCourse());
        assertEquals(newSection.courseId(), this.section.getCourse().getId());
        assertEquals(newSection.sectionOrder(), this.section.getSectionOrder());

        verify(this.repository, times(1)).save(any(Section.class));
        verify(this.courseRepository, times(1)).findById(courseId);
    }

    @Test
    void create_course_not_found(){
        Integer courseId = this.section.getCourse().getId();
        when(this.courseRepository.findById(courseId)).thenReturn(Optional.empty());

        SectionDto newSection = this.service.create(SectionDto.toResponse(this.section));

        assertEquals(newSection.id(), -1);

        verify(this.courseRepository, times(1)).findById(courseId);
    }


    @Test
    void delete(){
        int deleteId = 1;
        when(this.repository.findById(deleteId)).thenReturn(Optional.of(this.section));

        boolean isDeleted = this.service.delete(deleteId);

        assertTrue(isDeleted);

        verify(this.repository, times(1)).findById(deleteId);
        verify(this.repository, times(1)).deleteById(deleteId);
    }

    @Test
    void delete_not_found() {
        int deleteId = 1;
        when(this.repository.findById(deleteId)).thenReturn(Optional.empty());

        boolean isDeleted = this.service.delete(deleteId);

        assertFalse(isDeleted);

        verify(this.repository, times(1)).findById(deleteId);
    }

}
