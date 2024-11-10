package com.art.e_learning.generic;

import java.util.List;

public interface IBaseService<T> {
    List<T> getAll();
    T getById(Integer id);
    T create(T entity);
    boolean delete(Integer id);
}
