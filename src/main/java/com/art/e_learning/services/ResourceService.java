package com.art.e_learning.services;

import com.art.e_learning.dtos.ResourceDto;
import static com.art.e_learning.dtos.ResourceDto.*;
import com.art.e_learning.models.Resource;
import com.art.e_learning.repositories.ResourceRepository;
import com.art.e_learning.services.interfaces.IResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService implements IResourceService {

    private final ResourceRepository repository;

    public ResourceService(ResourceRepository resourceRepository){
        this.repository = resourceRepository;
    }


    @Override
    public List<ResourceDto> getAll() {
        return toListResponse(this.repository.findAll());
    }


    @Override
    public ResourceDto getById(Integer id) {
        Resource findResource = this.repository.findById(id).orElse(null);

        if(findResource == null) return null;

        return toResponse(findResource);
    }

}