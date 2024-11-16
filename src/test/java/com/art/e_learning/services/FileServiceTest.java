package com.art.e_learning.services;

import com.art.e_learning.dtos.InheritedBaseResourceDto;
import com.art.e_learning.models.File;
import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Section;
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
import static org.mockito.Mockito.*;


public class FileServiceTest {

    @InjectMocks
    private FileService service;

    @Mock
    private JpaRepository<File, Integer> repository;

    @Mock
    private LectureRepository lectureRepository;


    private File file;
    private Lecture lecture;

    private List<File> files;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        this.lecture = new Lecture("TestLecture", new Section(),null);
        this.lecture.setId(3);

        this.file = new File("TestType");
        file.setId(2);
        file.setName("TestFileName");
        file.setUrl("TestFileUrl");
        file.setSize(20);
        file.setLecture(this.lecture);


        this.files = new ArrayList<>();
        this.files.add(this.file);
    }



    @Test
    void get_all(){
        when(this.repository.findAll()).thenReturn(this.files);

        List<InheritedBaseResourceDto<File>> allFiles = this.service.getAll("File").
                stream().map(file -> (InheritedBaseResourceDto<File>) file).toList();

        assertEquals(allFiles.size(), this.files.size());
        assertEquals(allFiles.getFirst().inherited(), this.files.getFirst().getType());
        assertEquals(allFiles.getFirst().url(), this.files.getFirst().getUrl());
        assertEquals(allFiles.getFirst().name(), this.files.getFirst().getName());
        assertEquals(allFiles.getFirst().size(), this.files.getFirst().getSize());
        assertEquals(allFiles.getFirst().lectureId(), this.files.getFirst().getLecture().getId());

        verify(this.repository, times(1)).findAll();
    }

    @Test
    void get_by_id(){
        int id = 2;
        when(this.repository.findById(id)).thenReturn(Optional.of(this.file));

        InheritedBaseResourceDto findFile = this.service.getById(id, "File");

        assertEquals(findFile.inherited(), this.file.getType());
        assertEquals(findFile.url(), this.file.getUrl());
        assertEquals(findFile.name(), this.file.getName());
        assertEquals(findFile.size(), this.file.getSize());
        assertEquals(findFile.lectureId(), this.file.getLecture().getId());

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
        when(this.repository.save(any(File.class))).thenReturn(this.file);


        InheritedBaseResourceDto inputDto = new InheritedBaseResourceDto(
                null,
                this.file.getName(),
                this.file.getSize(),
                this.file.getUrl(),
                lectureId,
                this.file.getType()
        );

        InheritedBaseResourceDto newFile = this.service.create(inputDto, "File");

        assertNotNull(newFile);
        assertEquals(this.file.getId(), newFile.id());
        assertEquals(this.file.getName(), newFile.name());
        assertEquals(this.file.getSize(), newFile.size());
        assertEquals(this.file.getUrl(), newFile.url());
        assertEquals(this.file.getLecture().getId(), newFile.lectureId());
        assertEquals(this.file.getType(), newFile.inherited());


        verify(this.lectureRepository, times(1)).findById(lectureId);
        verify(this.repository, times(1)).save(any(File.class));
    }


    @Test
    void delete(){
        int deleteId = 1;
        when(this.repository.findById(deleteId)).thenReturn(Optional.of(this.file));

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
