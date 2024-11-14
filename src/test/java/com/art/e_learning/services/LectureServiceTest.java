package com.art.e_learning.services;

import com.art.e_learning.dtos.LectureDto;
import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Section;
import com.art.e_learning.repositories.FileRepository;
import com.art.e_learning.repositories.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class LectureServiceTest {

    @Mock
    private LectureRepository repository;

    @InjectMocks
    private LectureService service;

    private Lecture lecture;

    private List<Lecture> lectures;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.lecture = new Lecture("TestLecture", new Section(),null);
        this.lecture.setId(1);
        this.lecture.getSection().setId(2);
        this.lectures = new ArrayList<>();
        this.lectures.add(lecture);
    }

    @Test
    void getAll(){
        when(this.repository.findAll()).thenReturn(this.lectures);

        List<LectureDto> findLectures = this.service.getAll();

        assertEquals(findLectures.size(), this.lectures.size());
        assertEquals(findLectures.getFirst().id(), this.lectures.getFirst().getId());
        assertEquals(findLectures.getFirst().name(), this.lectures.getFirst().getName());
        assertEquals(findLectures.getFirst().sectionId(), this.lectures.getFirst().getSection().getId());

        verify(this.repository, times(1)).findAll();
    }
}
