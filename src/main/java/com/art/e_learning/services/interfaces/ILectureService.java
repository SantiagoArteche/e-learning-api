package com.art.e_learning.services.interfaces;

import com.art.e_learning.models.Lecture;

import java.util.List;

public interface ILectureService {
    List<Lecture> getAll();

    Lecture getById(Integer id);

    Lecture create(Lecture lecture);

    boolean delete(Integer id);
}
