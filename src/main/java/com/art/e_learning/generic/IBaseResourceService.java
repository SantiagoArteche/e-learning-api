package com.art.e_learning.generic;

import com.art.e_learning.dtos.InheritedBaseResourceDto;

import java.util.List;

public interface IBaseResourceService<T> {
    List<Object> getAll(String nameClass);
    InheritedBaseResourceDto getById(Integer id, String nameClass);
    InheritedBaseResourceDto create(T entity, String className);
    boolean delete(Integer id);
}
