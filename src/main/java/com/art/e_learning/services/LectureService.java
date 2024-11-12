package com.art.e_learning.services;

import com.art.e_learning.dtos.LectureDto;
import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Resource;
import com.art.e_learning.models.Section;
import com.art.e_learning.repositories.LectureRepository;
import com.art.e_learning.repositories.ResourceRepository;
import com.art.e_learning.repositories.SectionRepository;
import com.art.e_learning.services.interfaces.ILectureService;
import org.springframework.stereotype.Service;

import static com.art.e_learning.dtos.LectureDto.*;

import java.util.List;

@Service
public class LectureService implements ILectureService {
    private final LectureRepository repository;
    private final ResourceRepository resourceRepository;
    private final SectionRepository sectionRepository;

    public LectureService(LectureRepository lectureRepository, ResourceRepository resourceRepository, SectionRepository sectionRepository){
        this.repository = lectureRepository;
        this.resourceRepository = resourceRepository;
        this.sectionRepository = sectionRepository;
    }


    @Override
    public List<LectureDto> getAll() {
        return toResponseList(this.repository.findAll());
    }


    @Override
    public LectureDto getById(Integer id) {
        Lecture findCourse = this.repository.findById(id).orElse(null);

        if(findCourse == null) return null;

        return toResponse(findCourse);
    }


    @Override
    public LectureDto create(LectureDto lecture) {
        Lecture newLecture = new Lecture();
        newLecture.setName(lecture.name());

        if (lecture.sectionId() != null) {
            Section findSection = this.sectionRepository.findById(lecture.sectionId()).orElse(null);
            if (findSection != null) {
                newLecture.setSection(findSection);
            } else {
                newLecture.setId(-2);
            }
        }

        if (lecture.resourceId() != null) {
            Resource findResource = this.resourceRepository.findById(lecture.resourceId()).orElse(null);
            if (findResource != null) {
               newLecture.setResource(findResource);
            } else {
                newLecture.setId(-1);
            }
        }

        if(newLecture.getId() != null && newLecture.getId() < 0) return toResponse(newLecture);

        return toResponse(this.repository.save(newLecture));
    }



    @Override
    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
