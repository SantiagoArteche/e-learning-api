package com.art.e_learning.services;

import com.art.e_learning.dtos.CourseDto;
import com.art.e_learning.models.Author;
import com.art.e_learning.models.Course;
import com.art.e_learning.repositories.AuthorRepository;
import com.art.e_learning.repositories.CourseRepository;
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


class CourseServiceTest {

    @Mock
    private CourseRepository repository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private CourseService service;

    private List<Course> courses;
    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.course = new Course("TestNm","TestDesc", new ArrayList<>(),null);
        this.course.getAuthors().add(new Author("TestFn", "TestLn", "TestEmail", 11));
        this.course.setId(1);
        this.course.getAuthors().getFirst().setId(2);

        this.courses = new ArrayList<>();
        this.courses.add(this.course);
    }

    @Test
    void get_all() {
        when(this.repository.findAll()).thenReturn(this.courses);

        List<CourseDto> coursesDto = this.service.getAll();

        assertEquals(this.courses.size(), coursesDto.size());
        assertEquals(this.courses.getFirst().getAuthors().getFirst().getId(), coursesDto.getFirst().authors().getFirst());
        assertEquals(this.courses.getFirst().getTitle(), coursesDto.getFirst().title());
        assertEquals(this.courses.getFirst().getDescription(), coursesDto.getFirst().description());

        verify(this.repository, times(1)).findAll();
    }

    @Test
    void get_by_id() {
        int id = 1;
        when(this.repository.findById(id)).thenReturn(Optional.of(this.course));

        CourseDto courseDto = this.service.getById(1);

        assertEquals(courseDto.id(), this.course.getId());
        assertEquals(courseDto.authors().getFirst(), this.course.getAuthors().getFirst().getId());
        assertEquals(courseDto.title(), this.course.getTitle());
        assertEquals(courseDto.description(), this.course.getDescription());

        verify(this.repository, times(1)).findById(id);
    }

    @Test
    void get_by_id_not_found(){
        int notFound = -1;
        when(this.repository.findById(notFound)).thenReturn(Optional.empty());

        CourseDto courseDto = this.service.getById(notFound);

        assertNull(courseDto);

        verify(this.repository, times(1)).findById(notFound);
    }

    @Test
    void create() {
        int courseId = this.course.getAuthors().getFirst().getId();
        when(this.authorRepository.findById(courseId)).thenReturn(Optional.ofNullable(this.course.getAuthors().getFirst()));
        when(this.repository.save(any(Course.class))).thenReturn(this.course);

        CourseDto courseDto = this.service.create(CourseDto.toResponse(this.course));

        assertEquals(courseDto.id(), this.course.getId());
        assertNotNull(courseDto.authors());
        assertEquals(courseDto.authors().getFirst(), this.course.getAuthors().getFirst().getId());
        assertEquals(courseDto.title(), this.course.getTitle());
        assertEquals(courseDto.description(), this.course.getDescription());

        verify(this.repository, times(1)).save(any(Course.class));
        verify(this.authorRepository, times(1)).findById(courseId);
    }

    @Test
    void create_author_not_found() {
        int courseId = this.course.getAuthors().getFirst().getId();
        when(this.authorRepository.findById(courseId)).thenReturn(Optional.empty());

        CourseDto courseDto = this.service.create(CourseDto.toResponse(this.course));

        assertEquals(courseDto.id(), -1);
        assertEquals(courseDto.authors().size(), 0);
        assertNull(courseDto.description());
        assertNull(courseDto.title());
        verify(this.authorRepository, times(1)).findById(courseId);
    }

    @Test
    void delete() {
        int deleteId = 1;
        when(this.repository.findById(deleteId)).thenReturn(Optional.of(this.course));

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