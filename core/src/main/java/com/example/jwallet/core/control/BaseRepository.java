package com.example.jwallet.core.control;

import static com.example.jwallet.wallet.wallet.boundary.Database.DB.POSTGRES;

import java.util.List;
import java.util.logging.Logger;

import com.example.jwallet.wallet.wallet.boundary.Database;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public abstract class BaseRepository<E, K> {

	@Inject
	@Database(POSTGRES)
	protected EntityManager em;

	@Inject
	Logger logger;

	Class<E> clazz;

	public BaseRepository() {
	}

	public BaseRepository(Class<E> clazz) {
		this.clazz = clazz;
	}

	public E findById(K id) {
		return em.find(clazz, id);
	}

	List<E> findAll(String querName) {
		return em.createNamedQuery(querName, clazz).getResultList();
	}

	public E save(E entity) {
		em.persist(entity);
		return entity;
	}

	public E merge(E entity) {
		return em.merge(entity);
	}

	public E refresh(E entity) {
		em.refresh(entity);
		return entity;
	}

	public void remove(E entity) {
		E mergedEntity = merge(entity);
		em.remove(mergedEntity);
	}

}
