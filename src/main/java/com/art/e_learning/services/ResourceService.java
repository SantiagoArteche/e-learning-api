package com.art.e_learning.services;

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
    public List<Resource> getAll() {
        return this.repository.findAll();
    }


    @Override
    public Resource getById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }


    @Override
    public Resource create(Resource resource) {
        return this.repository.save(resource);
    }


    @Override
    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
