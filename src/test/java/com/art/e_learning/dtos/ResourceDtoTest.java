package com.art.e_learning.dtos;

import com.art.e_learning.models.Lecture;
import com.art.e_learning.models.Resource;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ResourceDtoTest {


    Resource resource = new Resource("TestResource", 5, "TestUrl", new Lecture());
    ResourceDto resourceDto;


    @Test
    void toResponse(){
        this.resourceDto = ResourceDto.toResponse(this.resource);

        assertEquals(this.resourceDto.name(), this.resource.getName());
        assertEquals(this.resourceDto.size(), this.resource.getSize());
        assertEquals(this.resourceDto.url(), this.resource.getUrl());
        assertInstanceOf(ResourceDto.class, this.resourceDto);
    }

    @Test
    void toListResponse(){
        List<Resource> resources = new ArrayList<>();
        resources.add(this.resource);
        List<ResourceDto> resourcesDtos = ResourceDto.toListResponse(resources);

        assertEquals(resourcesDtos.size(), resources.size());
        assertEquals(resourcesDtos.getFirst().name(), resources.getFirst().getName());
        assertEquals(resourcesDtos.getFirst().size(), resources.getFirst().getSize());
        assertEquals(resourcesDtos.getFirst().url(), resources.getFirst().getUrl());
    }
}
