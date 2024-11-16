package com.art.e_learning.services;

import com.art.e_learning.generic.BaseResourceService;
import com.art.e_learning.models.Video;
import com.art.e_learning.repositories.LectureRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoService extends BaseResourceService<Video> {
    public VideoService(JpaRepository<Video, Integer> repository, LectureRepository lectureRepository) {
        super(repository, lectureRepository);
    }
}
