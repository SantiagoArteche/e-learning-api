package com.art.e_learning.dtos;

import com.art.e_learning.models.Author;
import com.art.e_learning.models.Course;
import com.art.e_learning.models.Section;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CourseDtoTest {

    List<Section> sections = new ArrayList<>();
    List<Author> authors = new ArrayList<>();
    Course course = new Course("TestTitle", "TestDescription", authors, sections);
    CourseDto courseDto;

    {
        Section section = new Section("TestSection",1, new Course(),null);
        section.setId(1);
        sections.add(section);
        Author author = new Author("TestAuthorFn", "TestAuthorLn", "test@gmail.com", 27);
        author.setId(1);
        authors.add(author);
    }

    @Test
    void toResponse(){
        this.courseDto = CourseDto.toResponse(course);

        assertEquals(this.courseDto.description(), this.course.getDescription());
        assertEquals(this.courseDto.title(), this.course.getTitle());
        assertNotNull(this.courseDto.authors());
        assertEquals(this.courseDto.authors().getFirst(), this.course.getAuthors().getFirst().getId());
    }

    @Test
    void toListResponse(){
        List<Course> courses = new ArrayList<>();
        courses.add(this.course);
        List<CourseDto> courseDtos = CourseDto.toListResponse(courses);

        assertEquals(courseDtos.size(), courses.size());
        assertInstanceOf(CourseDto.class, courseDtos.getFirst());
        assertEquals(courseDtos.getFirst().title(), courses.getFirst().getTitle());
        assertEquals(courseDtos.getFirst().description(), courses.getFirst().getDescription());
        assertEquals(courseDtos.getFirst().authors().getFirst(), courses.getFirst().getAuthors().getFirst().getId());
    }
}
