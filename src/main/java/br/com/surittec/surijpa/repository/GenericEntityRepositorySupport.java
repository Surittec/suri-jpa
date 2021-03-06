/*
 * SURITTEC
 * Copyright 2015, TTUS TECNOLOGIA DA INFORMACAO LTDA, 
 * and individual contributors as indicated by the @authors tag
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package br.com.surittec.surijpa.repository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.surittec.surijpa.criteria.JPQL;
import br.com.surittec.surijpa.util.EntityUtil;

/**
 * Suporte para classes de persist�ncia, com encapsulamento do uso do
 * {@link javax.persistence.EntityManager} e provendo algumas opera��es
 * necess�rias manter ou pesquisar entidades.
 */
public abstract class GenericEntityRepositorySupport {

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// PROTECTED METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	protected abstract EntityManager getEntityManager();

	/**
	 * Create a JPQL support
	 * 
	 * @return jpql
	 */
	protected JPQL jpql() {
		return new JPQL(getEntityManager());
	}

	/**
	 * Cria a JPQL support j� iniciando o select
	 * 
	 * @param select
	 * @return
	 */
	public JPQL select(String... select) {
		return jpql().select(Arrays.asList(select));
	}

	/**
	 * Cria a JPQL support j� iniciando o select
	 * 
	 * @param select
	 * @return
	 */
	public JPQL select(Collection<String> selects) {
		return jpql().select(selects);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// PUBLIC METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Persist (new entity) or merge the given entity. The distinction on
	 * calling either method is done based on the primary key field being null
	 * or not. If this results in wrong behavior for a specific case, consider
	 * using the {@link org.apache.deltaspike.data.api.EntityManagerDelegate}
	 * which offers both {@code persist} and {@code merge}.
	 * 
	 * @param entity
	 *            Entity to save.
	 * @return Returns the modified entity.
	 */
	public <E> E save(E entity) {
		if (EntityUtil.isNew(getEntityManager(), entity)) {
			getEntityManager().persist(entity);
			return entity;
		}
		return getEntityManager().merge(entity);
	}

	/**
	 * Persist (new entities) or merge the given entities. The distinction on
	 * calling either method is done based on the primary key field being null
	 * or not. If this results in wrong behavior for a specific case, consider
	 * using the {@link org.apache.deltaspike.data.api.EntityManagerDelegate}
	 * which offers both {@code persist} and {@code merge}.
	 * 
	 * @param entities
	 *            Entities to save.
	 * @return Returns the modified entity.
	 */
	public <E> void save(Collection<E> entities) {
		for (E e : entities)
			save(e);
	}

	/**
	 * Convenience access to
	 * {@link javax.persistence.EntityManager#remove(Object)}.
	 * 
	 * @param entity
	 *            Entity to remove.
	 */
	public <E> void remove(E entity) {
		getEntityManager().remove(contains(entity) ? entity : getEntityManager().merge(entity));
	}

	/**
	 * Convenience access to
	 * {@link javax.persistence.EntityManager#remove(Object)}.
	 * 
	 * @param entities
	 *            Entities to remove.
	 */
	public <E> void remove(Collection<E> entities) {
		for (E e : entities)
			remove(e);
	}

	/**
	 * Convenience access to
	 * {@link javax.persistence.EntityManager#refresh(Object)}.
	 * 
	 * @param entity
	 *            Entity to refresh.
	 */
	public <E> void refresh(E entity) {
		getEntityManager().refresh(entity);
	}

	/**
	 * Same as {@link EntityRepositorySupport#refresh(Object)} but returns the entity.
	 * 
	 * @param entity
	 *            Entity to refresh.
	 * @return Entity to refresh.
	 */
	public <E> E refreshed(E entity) {
		refresh(entity);
		return entity;
	}

	/**
	 * Convenience access to
	 * {@link javax.persistence.EntityManager#refresh(Object)}.
	 * 
	 * @param entities
	 *            Entities to refresh.
	 */
	public <E> void refresh(Collection<E> entities) {
		for (E e : entities)
			refresh(e);
	}

	/**
	 * Same as {@link EntityRepositorySupport#refresh(Collection)} but returns the
	 * collection.
	 * 
	 * @param entities
	 *            Entities to refresh.
	 * @return Entities refreshed
	 */
	public <T extends Collection<E>, E> T refreshed(T entities) {
		refresh(entities);
		return entities;
	}

	/**
	 * Check if the instance is a managed entity instance belonging to the
	 * current persistence context.
	 * 
	 * @param entity
	 *            entity instance
	 * @return boolean indicating if entity is in persistence context
	 * @throws IllegalArgumentException
	 *             if not an entity
	 */
	public <E> boolean contains(E entity) {
		return getEntityManager().contains(entity);
	}

	/**
	 * Remove the given entity from the persistence context, causing a managed
	 * entity to become detached. Unflushed changes made to the entity if any
	 * (including removal of the entity), will not be synchronized to the
	 * database. Entities which previously referenced the detached entity will
	 * continue to reference it.
	 * 
	 * @param entity
	 *            entity instance
	 * @throws IllegalArgumentException
	 *             if the instance is not an entity
	 */
	public <E> void detach(E entity) {
		getEntityManager().detach(entity);
	}

	/**
	 * Same as {@link EntityRepositorySupport#detach(Object)} but returns the entity.
	 * 
	 * @param entity
	 * @return the entity detached
	 */
	public <E> E detached(E entity) {
		detach(entity);
		return entity;
	}

	/**
	 * Remove the given entities from the persistence context, causing a managed
	 * entities to become detached. Unflushed changes made to the entities if
	 * any (including removal of the entities), will not be synchronized to the
	 * database. Entities which previously referenced the detached entity will
	 * continue to reference it.
	 * 
	 * @param entities
	 *            entities instances
	 * @throws IllegalArgumentException
	 *             if the instance is not an entity
	 */
	public <E> void detach(Collection<E> entities) {
		for (E e : entities)
			detach(e);
	}

	/**
	 * Same as {@link EntityRepositorySupport#detach(Collection)} but returns the
	 * collection.
	 * 
	 * @param entities
	 *            entities instances
	 * @return entities detached
	 * @throws IllegalArgumentException
	 *             if the instance is not an entity
	 */
	public <T extends Collection<E>, E> T detached(T entities) {
		detach(entities);
		return entities;
	}

	/**
	 * Convenience access to {@link javax.persistence.EntityManager#flush()}.
	 */
	public void flush() {
		getEntityManager().flush();
	}

	/**
	 * Entity lookup by primary key. Convenicence method around
	 * {@link javax.persistence.EntityManager#find(Class, Object)}.
	 * 
	 * @param entityClass
	 *            Entity class
	 * @param primaryKey
	 *            DB primary key.
	 * @return Entity identified by primary or null if it does not exist.
	 */
	public <E, PK extends Serializable> E findBy(Class<E> entityClass, PK primaryKey) {
		return getEntityManager().find(entityClass, primaryKey);
	}

	/**
	 * Lookup all existing entities of entity class {@code <E>}.
	 * 
	 * @param entityClass
	 *            Entity class
	 * @return List of entities, empty if none found.
	 */
	public <E> List<E> findAll(Class<E> entityClass) {
		return jpql().from(EntityUtil.getEntityName(getEntityManager(), entityClass)).getResultList(entityClass);
	}

	/**
	 * Lookup a range of existing entities of entity class {@code <E>} with
	 * support for pagination.
	 * 
	 * @param entityClass
	 *            Entity class
	 * @param start
	 *            The starting position.
	 * @param max
	 *            The maximum number of results to return
	 * @return List of entities, empty if none found.
	 */
	public <E> List<E> findAll(Class<E> entityClass, int start, int max) {
		JPQL jpql = jpql().from(EntityUtil.getEntityName(getEntityManager(), entityClass));
		if (start > 0)
			jpql.firstResult(start);
		if (max > 0)
			jpql.maxResults(max);
		return jpql.getResultList(entityClass);
	}

	/**
	 * Find entities by the given named query.
	 * 
	 * @param entityClass
	 *            Entity class
	 * @param namedQuery
	 *            Named Query
	 * @param params
	 *            Named Query parameters
	 * @return List of entities, empty if none found.
	 */
	public <E> List<E> findByNamedQuery(Class<E> entityClass, String namedQuery, Map<String, Object> params) {
		TypedQuery<E> query = getEntityManager().createNamedQuery(namedQuery, entityClass);
		if (params != null) {
			for (String paramName : params.keySet()) {
				query.setParameter(paramName, params.get(paramName));
			}
		}
		return query.getResultList();
	}

	/**
	 * Find any entity by the given named query.
	 * 
	 * @param entityClass
	 *            Entity class
	 * @param namedQuery
	 *            Named Query
	 * @param params
	 *            Named Query parameters
	 * @return Entity
	 */
	public <E> E findAnyByNamedQuery(Class<E> entityClass, String namedQuery, Map<String, Object> params) {
		TypedQuery<E> query = getEntityManager().createNamedQuery(namedQuery, entityClass);
		if (params != null) {
			for (String paramName : params.keySet()) {
				query.setParameter(paramName, params.get(paramName));
			}
		}

		List<E> result = query.getResultList();
		if (result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Find single entity by the given named query.
	 * 
	 * @param entityClass
	 *            Entity class
	 * @param namedQuery
	 *            Named Query
	 * @param params
	 *            Named Query parameters
	 * @return Entity
	 */
	public <E> E findUniqueByNamedQuery(Class<E> entityClass, String namedQuery, Map<String, Object> params) {
		TypedQuery<E> query = getEntityManager().createNamedQuery(namedQuery, entityClass);
		if (params != null) {
			for (String paramName : params.keySet()) {
				query.setParameter(paramName, params.get(paramName));
			}
		}
		return query.getSingleResult();
	}

}
