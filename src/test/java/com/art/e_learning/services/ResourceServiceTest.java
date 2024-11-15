package com.art.e_learning.services;

import com.art.e_learning.dtos.LectureDto;
import com.art.e_learning.dtos.ResourceDto;
import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Resource;
import com.art.e_learning.repositories.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceServiceTest {

    @Mock
    private ResourceRepository repository;

    @InjectMocks
    private ResourceService service;

    private List<Resource> resources;
    private Resource resource;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.resource = new Resource("TestResource", 15, "TestUrl", new Lecture());
        this.resource.setId(1);
        this.resource.getLecture().setId(2);
        this.resources = new ArrayList<>();
        this.resources.add(this.resource);
    }

    @Test
    void getAll(){
        when(this.repository.findAll()).thenReturn(this.resources);

        List<ResourceDto> resources = this.service.getAll();

        assertEquals(resources.size(), this.resources.size());
        assertEquals(resources.getFirst().id(), this.resources.getFirst().getId());
        assertEquals(resources.getFirst().name(), this.resources.getFirst().getName());
        assertEquals(resources.getFirst().url(), this.resources.getFirst().getUrl());
        assertEquals(resources.getFirst().size(), this.resources.getFirst().getSize());

        verify(this.repository, times(1)).findAll();
    }

    @Test
    void get_by_id(){
        int id = 1;
        when(this.repository.findById(id)).thenReturn(Optional.of(this.resource));

        ResourceDto resourceDto = this.service.getById(id);

        assertEquals(resourceDto.id(), this.resource.getId());
        assertEquals(resourceDto.name(), this.resource.getName());
        assertEquals(resourceDto.url(), this.resource.getUrl());
        assertEquals(resourceDto.size(), this.resource.getSize());
        assertNotNull(this.resource.getLecture());

        verify(this.repository, times(1)).findById(id);
    }

    @Test
    void get_by_id_not_found(){
        int id = 1;
        when(this.repository.findById(id)).thenReturn(Optional.empty());

        ResourceDto resourceDto = this.service.getById(id);

        assertNull(resourceDto);

        verify(this.repository, times(1)).findById(id);
    }

}
