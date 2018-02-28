package com.ef.db.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public interface BaseService<T> {

	T create(T entity);
	
	List<T> create(List<T> entities);
	
	T update(T entity);
	
	void delete(T entity);
	
	void delete(Long entityId);
	
	List<T> findAll(); 

	List<T> findAll(Specification<T> specification);
}
