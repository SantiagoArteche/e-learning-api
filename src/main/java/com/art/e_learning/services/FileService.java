package com.art.e_learning.services;

import com.art.e_learning.generic.BaseResourceService;
import com.art.e_learning.models.File;
import com.art.e_learning.repositories.LectureRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FileService extends BaseResourceService<File> {
    public FileService(JpaRepository<File, Integer> repository, LectureRepository lectureRepository) {
        super(repository, lectureRepository);
    }
}

