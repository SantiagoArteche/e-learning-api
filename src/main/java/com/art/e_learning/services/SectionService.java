package com.art.e_learning.services;

import com.art.e_learning.models.Section;
import com.art.e_learning.repositories.SectionRepository;
import com.art.e_learning.services.interfaces.ISectionService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SectionService implements ISectionService {

    private final SectionRepository repository;

    public SectionService(SectionRepository sectionRepository) {
        this.repository = sectionRepository;
    }

    @Override
    public List<Section> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Section getById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public Section create(Section entity) {
        return this.repository.save(entity);
    }

    @Override
    public boolean delete(Integer id) {
        if (getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
