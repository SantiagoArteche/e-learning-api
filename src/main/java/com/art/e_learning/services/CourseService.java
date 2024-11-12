package com.art.e_learning.services;

import com.art.e_learning.dtos.CourseDto;
import static com.art.e_learning.dtos.CourseDto.*;

import com.art.e_learning.generic.BaseEntity;
import com.art.e_learning.models.Author;
import com.art.e_learning.models.Course;
import com.art.e_learning.models.Section;
import com.art.e_learning.repositories.AuthorRepository;
import com.art.e_learning.repositories.CourseRepository;
import com.art.e_learning.services.interfaces.ICourseService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Stream;

@Service
public class CourseService implements ICourseService {

    private final CourseRepository repository;
    private final AuthorRepository authorRepository;

    public CourseService(CourseRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<CourseDto> getAll() {
        return toListResponse(this.repository.findAll());
    }

    @Override
    public CourseDto getById(Integer id) {
        Course findCourse = this.repository.findById(id).orElse(null);

        if(findCourse == null) return null;

        return toResponse(findCourse);
    }

    @Override
    public CourseDto create(CourseDto courseDto) {
        List<Author> authors = courseDto.authors() != null ?
                courseDto.authors().stream().map(authorId -> {
                    Author findAuthor = this.authorRepository.findById(authorId).orElse(null);
                    if (findAuthor == null){
                        Author setError = new Author();
                        setError.setId(-1);
                        return setError;
                    }
                    return findAuthor;
                }).toList() : null;

        List<Author> findError = authors != null ? authors.stream().filter(author -> author.getId() == -1).toList() : null;

        if(findError != null && !findError.isEmpty()){
            Course courseError = new Course();
            courseError.setId(-1);
            return toResponse(courseError);
        }else{
            Course course = new Course(courseDto.title(), courseDto.description(),
                    authors,
                    null);
            return toResponse(this.repository.save(course));
        }
    }

    @Override
    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
