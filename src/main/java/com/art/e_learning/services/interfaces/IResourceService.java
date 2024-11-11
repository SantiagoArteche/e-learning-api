package com.art.e_learning.services.interfaces;

import com.art.e_learning.models.Resource;

import java.util.List;

public interface IResourceService {
    List<Resource> getAll();

    Resource getById(Integer id);

    Resource create(Resource resource);

    boolean delete(Integer id);
}
