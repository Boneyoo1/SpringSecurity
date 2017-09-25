package com.denkensol.universaladmission.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.denkensol.universaladmission.dto.SortCriteriaData;

public interface BaseDao<PK, T> {

	/**
	 * Save object.
	 *
	 * @param entity
	 *            the entity
	 * @return the long
	 * @throws CommonException
	 *             the common exception
	 */
	long save(T entity);

	/**
	 * Update object.
	 *
	 * @param entity
	 *            the entity
	 * @throws CommonException
	 *             the common exception
	 */
	void update(T entity);

	/**
	 * Save or update object.
	 *
	 * @param entity
	 *            the entity
	 * @throws CommonException
	 *             the common exception
	 */
	void saveOrUpdate(T entity);

	/**
	 * Delete object.
	 *
	 * @param entity
	 *            the entity
	 * @throws CommonException
	 *             the common exception
	 */
	void delete(T entity);

	/**
	 * Gets the object.
	 *
	 * @param <T>
	 *            the generic type
	 * @param entityClass
	 *            the entity class
	 * @param entityId
	 *            the entity id
	 * @return the object
	 * @throws CommonException
	 *             the common exception
	 */
	T getById(PK entityId);

	List<T> getAll(SortCriteriaData sortCriteria);

	/**
	 * Gets the objects.
	 *
	 * @param entityClass
	 *            the entity class
	 * @param criterions
	 *            the criterions
	 * @param orderByField
	 *            the order by field
	 * @return the objects
	 * @throws CommonException
	 *             the common exception
	 */
	List<T> getAllByConditions(List<Criterion> criterions, SortCriteriaData sortCriteria);

	List<T> getAllByPrimaryKeys(SortCriteriaData sortCriteria, PK... primaryKey);

	int size();

	T getByConditions(List<Criterion> criterions);

	int executeUpdateNativeNamedQuery(String queryName, Map<String, Object> parameters);

	List<?> executeNativeNamedQuery(String queryName, Map<String, Object> parameters, Class<?> tansformerClass);

	T getByCondition(Criterion criterion);

	void saveOrUpdateBatch(List<T> entities);

	void deleteBatch(List<T> entities);

	void saveOrUpdateBatchAsManualCommit(List<T> entities);

	List<T> getAllByPage(SortCriteriaData sortCriteria, Integer pageNo, Integer pageLimit);

	void updateBatch(List<T> entities);

	void mergeBatch(List<T> entities);

	void saveBatch(List<T> entities);

	List<T> getAllByCondition(Criterion criterion, SortCriteriaData sortCriteria);

	List<T> getAllByCondition(Criterion criterion, SortCriteriaData sortCriteria, Class<?> tansformerClass);

}
