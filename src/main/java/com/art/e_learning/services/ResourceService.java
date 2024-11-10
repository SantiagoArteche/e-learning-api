package com.art.e_learning.services;

import com.art.e_learning.models.Resource;
import com.art.e_learning.repositories.ResourceRepository;


import java.util.List;

public class ResourceService{

    private final ResourceRepository repository;

    public ResourceService(ResourceRepository resourceRepository){
        this.repository = resourceRepository;
    }


    public List<Resource> getAll() {
        return this.repository.findAll();
    }


    public Resource getById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }


    public Resource create(Resource resource) {
        return this.repository.save(resource);
    }


    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
