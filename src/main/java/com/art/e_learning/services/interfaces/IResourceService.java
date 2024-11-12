package com.art.e_learning.services.interfaces;

import com.art.e_learning.dtos.ResourceDto;

import java.util.List;

public interface IResourceService {
    List<ResourceDto> getAll();

    ResourceDto getById(Integer id);
}