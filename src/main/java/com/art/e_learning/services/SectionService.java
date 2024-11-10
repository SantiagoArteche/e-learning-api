package com.art.e_learning.services;

import com.art.e_learning.models.Section;
import com.art.e_learning.repositories.SectionRepository;

import java.util.List;

public class SectionService {

    private final SectionRepository repository;

    public SectionService(SectionRepository sectionRepository){
        this.repository = sectionRepository;
    }


    public List<Section> getAll() {
        return List.of();
    }


    public Section getById(Integer id) {
        return null;
    }


    public Section create(Section entity) {
        return null;
    }


    public boolean delete(Integer id) {
        return false;
    }
}
