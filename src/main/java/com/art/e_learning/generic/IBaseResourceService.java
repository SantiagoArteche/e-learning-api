package com.art.e_learning.generic;

import com.art.e_learning.dtos.ResourceDto;

import java.util.List;

public interface IBaseResourceService<T> {
    List<Object> getAll(String nameClass);
    ResourceDto getById(Integer id, String nameClass);
    ResourceDto create(T entity, String className);
    boolean delete(Integer id);
}
