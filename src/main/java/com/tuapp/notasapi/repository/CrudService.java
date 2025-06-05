package com.tuapp.notasapi.repository;

import java.util.List;

public interface CrudService<T, ID> {
  List<T> getAll();
  T getById(ID id);
  T save(T entity);
  T update(ID id, T entity);
  void deleteById(ID id);
}
