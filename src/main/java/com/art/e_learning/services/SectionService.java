package com.art.e_learning.services;

import com.art.e_learning.models.Section;
import com.art.e_learning.repositories.SectionRepository;
import com.art.e_learning.generic.IBaseService;

import java.util.List;

public class SectionService implements IBaseService<Section> {

    private final SectionRepository repository;

    public SectionService(SectionRepository sectionRepository){
        this.repository = sectionRepository;
    }

    @Override
    public List<Section> getAll() {
        return List.of();
    }

    @Override
    public Section getById(Integer id) {
        return null;
    }

    @Override
    public Section create(Section entity) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
