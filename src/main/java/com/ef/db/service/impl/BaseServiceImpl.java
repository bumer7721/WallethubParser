package com.ef.db.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;

import com.ef.db.repository.BaseRepository;
import com.ef.db.service.BaseService;

@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	protected abstract BaseRepository<T> getRepository();
	
	@Override
	public T create(T entity) {
		return getRepository().save(entity);
	}
	
	@Override
	public T update(T entity) {
		return getRepository().saveAndFlush(entity);
	}
	
	@Override
	public void delete(T entity){
		delete(entity);
	}
	
	@Override
	public void delete(Long entityId){
		delete(entityId);
	}
	
	@Override
	public List<T> findAll() {
		return getRepository().findAll();
	}

	@Override
	public List<T> findAll(Specification<T> specification) {
		return getRepository().findAll(specification);
	}
}
