package com.art.e_learning.services;

import com.art.e_learning.models.Lecture;
import com.art.e_learning.repositories.LectureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {
    private final LectureRepository repository;

    public LectureService(LectureRepository lectureRepository){
        this.repository = lectureRepository;
    }


    public List<Lecture> getAll() {
        return this.repository.findAll();
    }


    public Lecture getById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }


    public Lecture create(Lecture lecture) {
        return this.repository.save(lecture);
    }


    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
