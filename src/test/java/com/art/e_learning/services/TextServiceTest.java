package com.art.e_learning.services;

import com.art.e_learning.dtos.InheritedBaseResourceDto;
import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Section;
import com.art.e_learning.models.Text;
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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class TextServiceTest {
    @InjectMocks
    private TextService service;

    @Mock
    private JpaRepository<Text, Integer> repository;

    @Mock
    private LectureRepository lectureRepository;

    private Text text;
    private Lecture lecture;

    private List<Text> texts;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        this.lecture = new Lecture("TestLecture", new Section(),null);
        this.lecture.setId(3);

        this.text = new Text("TestContent");
        text.setId(1);
        text.setName("TestTextName");
        text.setUrl("TestTextUrl");
        text.setSize(10);
        text.setLecture(this.lecture);

        this.texts = new ArrayList<>();
        this.texts.add(text);
    }

    @Test
    void get_all(){
        when(this.repository.findAll()).thenReturn(this.texts);

        List<InheritedBaseResourceDto<Text>> allTexts = this.service.getAll("Text").
                stream().map(text -> (InheritedBaseResourceDto<Text>) text).toList();

        assertEquals(allTexts.size(), this.texts.size());
        assertEquals(allTexts.getFirst().inherited(), this.texts.getFirst().getContent());
        assertEquals(allTexts.getFirst().url(), this.texts.getFirst().getUrl());
        assertEquals(allTexts.getFirst().name(), this.texts.getFirst().getName());
        assertEquals(allTexts.getFirst().size(), this.texts.getFirst().getSize());
        assertEquals(allTexts.getFirst().lectureId(), this.texts.getFirst().getLecture().getId());

        verify(this.repository, times(1)).findAll();
    }

    @Test
    void get_by_id(){
        int id = 2;
        when(this.repository.findById(id)).thenReturn(Optional.of(this.text));

        InheritedBaseResourceDto findFile = this.service.getById(id, "Text");

        assertEquals(findFile.inherited(), this.text.getContent());
        assertEquals(findFile.url(), this.text.getUrl());
        assertEquals(findFile.name(), this.text.getName());
        assertEquals(findFile.size(), this.text.getSize());
        assertEquals(findFile.lectureId(), this.text.getLecture().getId());

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
        when(this.repository.save(any(Text.class))).thenReturn(this.text);


        InheritedBaseResourceDto inputDto = new InheritedBaseResourceDto(
                null,
                this.text.getName(),
                this.text.getSize(),
                this.text.getUrl(),
                lectureId,
                this.text.getContent()
        );

        InheritedBaseResourceDto newFile = this.service.create(inputDto, "Text");

        assertNotNull(newFile);
        assertEquals(this.text.getId(), newFile.id());
        assertEquals(this.text.getName(), newFile.name());
        assertEquals(this.text.getSize(), newFile.size());
        assertEquals(this.text.getUrl(), newFile.url());
        assertEquals(this.text.getLecture().getId(), newFile.lectureId());
        assertEquals(this.text.getContent(), newFile.inherited());


        verify(this.lectureRepository, times(1)).findById(lectureId);
        verify(this.repository, times(1)).save(any(Text.class));
    }

    @Test
    void delete(){
        int deleteId = 1;
        when(this.repository.findById(deleteId)).thenReturn(Optional.of(this.text));

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
