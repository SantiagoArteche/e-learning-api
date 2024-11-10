package com.art.e_learning.services;

import com.art.e_learning.models.File;
import com.art.e_learning.repositories.FileRepository;
import com.art.e_learning.generic.IBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService implements IBaseService<File> {

    private final FileRepository repository;

    public FileService(FileRepository fileRepository){
        this.repository = fileRepository;
    }

    @Override
    public List<File> getAll() {
        return this.repository.findAll();
    }

    @Override
    public File getById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public File create(File file) {
        return this.repository.save(file);
    }

    @Override
    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
