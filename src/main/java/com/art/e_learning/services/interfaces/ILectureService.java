package com.art.e_learning.services.interfaces;

import com.art.e_learning.dtos.LectureDto;

import java.util.List;

public interface ILectureService {
    List<LectureDto> getAll();

    LectureDto getById(Integer id);

    LectureDto create(LectureDto lecture);

    boolean delete(Integer id);
}
