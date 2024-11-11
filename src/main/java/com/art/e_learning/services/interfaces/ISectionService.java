package com.art.e_learning.services.interfaces;

import com.art.e_learning.models.Section;

import java.util.List;

public interface ISectionService {
    List<Section> getAll();
    Section getById(Integer id);
    Section create(Section entity);
    boolean delete(Integer id);
}
