package com.art.e_learning.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public abstract class BaseService<T> implements IBaseService<T> {

    public final JpaRepository<T, Integer> repository;

    public BaseService(JpaRepository<T, Integer> repository){
        this.repository = repository;
    }

    @Override
    public List<T> getAll() {
        return this.repository.findAll();
    }


    public T getById(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    public T create(T entity) {
        return this.repository.save(entity);
    }

    public boolean delete(Integer id) {
        if(getById(id) == null) return false;

        this.repository.deleteById(id);

        return true;
    }
}
