package com.art.e_learning.services;

import com.art.e_learning.dtos.CourseDto;
import com.art.e_learning.dtos.LectureDto;
import com.art.e_learning.dtos.SectionDto;
import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Section;
import com.art.e_learning.repositories.LectureRepository;
import com.art.e_learning.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LectureServiceTest {

    @Mock
    private LectureRepository repository;
    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private LectureService service;

    private Lecture lecture;

    private List<Lecture> lectures;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.lecture = new Lecture("TestLecture", new Section(),null);
        this.lecture.setId(1);
        this.lecture.getSection().setId(2);
        this.lectures = new ArrayList<>();
        this.lectures.add(lecture);
    }

    @Test
    void getAll(){
        when(this.repository.findAll()).thenReturn(this.lectures);

        List<LectureDto> findLectures = this.service.getAll();

        assertEquals(findLectures.size(), this.lectures.size());
        assertEquals(findLectures.getFirst().id(), this.lectures.getFirst().getId());
        assertEquals(findLectures.getFirst().name(), this.lectures.getFirst().getName());
        assertEquals(findLectures.getFirst().sectionId(), this.lectures.getFirst().getSection().getId());

        verify(this.repository, times(1)).findAll();
    }

    @Test
    void get_by_id(){
        int id = 1;
        when(this.repository.findById(id)).thenReturn(Optional.of(this.lecture));

        LectureDto lectureDto = this.service.getById(id);

        assertEquals(lectureDto.id(), this.lecture.getId());
        assertEquals(lectureDto.name(), this.lecture.getName());
        assertNotNull(this.lecture.getSection());
        assertEquals(lectureDto.sectionId(), this.lecture.getSection().getId());

        verify(this.repository, times(1)).findById(id);
    }

    @Test
    void get_by_id_not_found(){
        int id = 1;
        when(this.repository.findById(id)).thenReturn(Optional.empty());

        LectureDto lectureDto = this.service.getById(id);

        assertNull(lectureDto);

        verify(this.repository, times(1)).findById(id);
    }

    @Test
    void create(){
        Integer courseId = this.lecture.getSection().getId();
        when(this.sectionRepository.findById(courseId)).thenReturn(Optional.of(this.lecture.getSection()));
        when(this.repository.save(any(Lecture.class))).thenReturn(this.lecture);

        LectureDto newLecture = this.service.create(LectureDto.toResponse(this.lecture));

        assertEquals(newLecture.id(), this.lecture.getId());
        assertEquals(newLecture.name(), this.lecture.getName());
        assertNotNull(this.lecture.getSection());
        assertEquals(newLecture.sectionId(), this.lecture.getSection().getId());


        verify(this.repository, times(1)).save(any(Lecture.class));
        verify(this.sectionRepository, times(1)).findById(courseId);
    }

    @Test
    void create_section_not_found() {
        int courseId = this.lecture.getSection().getId();
        when(this.sectionRepository.findById(courseId)).thenReturn(Optional.empty());

        LectureDto lectureDto = this.service.create(LectureDto.toResponse(this.lecture));

        assertEquals(lectureDto.id(), -1);

        verify(this.sectionRepository, times(1)).findById(courseId);
    }

    @Test
    void delete(){
        int deleteId = 1;
        when(this.repository.findById(deleteId)).thenReturn(Optional.of(this.lecture));

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
