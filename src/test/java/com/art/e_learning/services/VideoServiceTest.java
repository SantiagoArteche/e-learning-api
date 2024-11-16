package com.art.e_learning.services;

import com.art.e_learning.dtos.InheritedBaseResourceDto;
import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Section;
import com.art.e_learning.models.Video;
import com.art.e_learning.repositories.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class VideoServiceTest {
    @InjectMocks
    private VideoService service;

    @Mock
    private JpaRepository<Video, Integer> repository;

    @Mock
    private LectureRepository lectureRepository;

    private Video video;
    private List<Video> videos;
    private Lecture lecture;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        this.lecture = new Lecture("TestLecture", new Section(),null);
        this.lecture.setId(3);

        this.video = new Video(3000);
        this.video.setId(3);
        this.video.setName("TestFileName");
        this.video.setUrl("TestFileUrl");
        this.video.setSize(30);
        this.video.setLecture(this.lecture);


        this.videos = new ArrayList<>();
        this.videos.add(this.video);
    }

    @Test
    void get_all(){
        when(this.repository.findAll()).thenReturn(this.videos);

        List<InheritedBaseResourceDto<Video>> allVideos = this.service.getAll("Video").
                stream().map(video -> (InheritedBaseResourceDto<Video>) video).toList();


        assertEquals(allVideos.size(), this.videos.size());
        assertEquals(allVideos.getFirst().inherited(), this.videos.getFirst().getLength());
        assertEquals(allVideos.getFirst().url(), this.videos.getFirst().getUrl());
        assertEquals(allVideos.getFirst().name(), this.videos.getFirst().getName());
        assertEquals(allVideos.getFirst().size(), this.videos.getFirst().getSize());
        assertEquals(allVideos.getFirst().lectureId(), this.videos.getFirst().getLecture().getId());

        verify(this.repository, times(1)).findAll();
    }

    @Test
    void get_by_id(){
        int id = 2;
        when(this.repository.findById(id)).thenReturn(Optional.of(this.video));

        InheritedBaseResourceDto findFile = this.service.getById(id, "Video");

        assertEquals(findFile.inherited(), this.video.getLength());
        assertEquals(findFile.url(), this.video.getUrl());
        assertEquals(findFile.name(), this.video.getName());
        assertEquals(findFile.size(), this.video.getSize());
        assertEquals(findFile.lectureId(), this.video.getLecture().getId());

        verify(this.repository, times(1)).findById(id);
    }

    @Test
    void get_by_id_not_found(){
        int id = -1;
        when(this.repository.findById(id)).thenReturn(Optional.empty());

        InheritedBaseResourceDto findFile = this.service.getById(id, "File");

        assertNull(findFile);

        verify(this.repository, times(1)).findById(id);
    }

    @Test
    void create() {
        Integer lectureId = this.lecture.getId();

        when(this.lectureRepository.findById(lectureId)).thenReturn(Optional.of(this.lecture));
        when(this.repository.save(any(Video.class))).thenReturn(this.video);


        InheritedBaseResourceDto inputDto = new InheritedBaseResourceDto(
                null,
                this.video.getName(),
                this.video.getSize(),
                this.video.getUrl(),
                lectureId,
                this.video.getLength()
        );

        InheritedBaseResourceDto newFile = this.service.create(inputDto, "Video");

        assertNotNull(newFile);
        assertEquals(this.video.getId(), newFile.id());
        assertEquals(this.video.getName(), newFile.name());
        assertEquals(this.video.getSize(), newFile.size());
        assertEquals(this.video.getUrl(), newFile.url());
        assertEquals(this.video.getLecture().getId(), newFile.lectureId());
        assertEquals(this.video.getLength(), newFile.inherited());


        verify(this.lectureRepository, times(1)).findById(lectureId);
        verify(this.repository, times(1)).save(any(Video.class));
    }

    @Test
    void delete(){
        int deleteId = 1;
        when(this.repository.findById(deleteId)).thenReturn(Optional.of(this.video));

        boolean isDeleted = this.service.delete(deleteId);

        assertTrue(isDeleted);

        verify(this.repository, times(1)).findById(deleteId);
        verify(this.repository, times(1)).deleteById(deleteId);
    }

    @Test
    void delete_not_found() {
        int deleteId = 1;
        when(this.repository.findById(deleteId)).thenReturn(Optional.empty());

        boolean isDeleted = this.service.delete(deleteId);

        assertFalse(isDeleted);

        verify(this.repository, times(1)).findById(deleteId);
    }
}
