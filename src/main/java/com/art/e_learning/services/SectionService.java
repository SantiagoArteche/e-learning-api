package com.art.e_learning.services;

import com.art.e_learning.dtos.SectionDto;

import static com.art.e_learning.dtos.SectionDto.*;

import com.art.e_learning.models.Course;
import com.art.e_learning.models.Section;
import com.art.e_learning.repositories.CourseRepository;
import com.art.e_learning.repositories.SectionRepository;
import com.art.e_learning.services.interfaces.ISectionService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SectionService implements ISectionService {

    private final SectionRepository repository;
    private final CourseRepository courseRepository;

    public SectionService(SectionRepository sectionRepository, CourseRepository courseRepository) {
        this.repository = sectionRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<SectionDto> getAll() {
        return toResponseList(this.repository.findAll());
    }

    @Override
    public SectionDto getById(Integer id) {
        Section findCourse = this.repository.findById(id).orElse(null);

        if(findCourse == null) return null;

        return toResponse(findCourse);
    }

    @Override
    public SectionDto create(SectionDto sectionDto) {
        Section section = new Section();
        section.setName(sectionDto.name());

        if(sectionDto.sectionOrder() != null){
            section.setSectionOrder(sectionDto.sectionOrder());
        }

        if(sectionDto.courseId() != null){
            Course findCourse = this.courseRepository.findById(sectionDto.courseId()).orElse(null);

            if(findCourse != null){
                Course course = new Course();
                course.setId(sectionDto.courseId());
                section.setCourse(course);
            }else{
                section.setId(-1);
                return toResponse(section);
            }
        }


        return toResponse(this.repository.save(section));
    }

    @Override
    public boolean delete(Integer id) {
        if (getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
