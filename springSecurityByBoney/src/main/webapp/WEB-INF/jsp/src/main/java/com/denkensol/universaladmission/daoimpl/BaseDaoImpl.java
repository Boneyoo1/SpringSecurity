/*
 * 
 */
package com.denkensol.universaladmission.daoimpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.denkensol.universaladmission.dao.BaseDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;

/**
 * The Class BaseDaoImpl.
 */

public class BaseDaoImpl<PK extends Serializable, T> implements BaseDao<PK, T> {

	private final Class<T> persistentClass;

	private int jdbcBatchSize = 50;

	/** The session factory. */

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public long save(T entity) {
		long savedId = Long.valueOf(0);

		savedId = (Long) getSession().save(entity);
		return savedId;
	}

	@Override
	public void update(T entity) {

		getSession().update(entity);

	}

	@Override
	public void saveOrUpdate(T entity) {
		try {
			getSession().saveOrUpdate(entity);
		} catch (Exception e) {
			getSession().merge(entity);
		}

	}

	@Override
	public void delete(T entity) {

		getSession().delete(entity);

	}

	public Criteria getCriteria() {
		Criteria criteria = getSession().createCriteria(persistentClass);
		return criteria;
	}

	public Criteria getCriteria(SortCriteriaData sortCriteria) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		addSortCriteria(criteria, sortCriteria);
		return criteria;
	}

	private void addSortCriteria(Criteria criteria, SortCriteriaData sortCriteria) {

		if (sortCriteria != null) {
			final String property = sortCriteria.getProperty();
			final boolean ascending = sortCriteria.getIsAscending();
			final Order order = ascending ? Order.asc(property) : Order.desc(property);
			criteria.addOrder(order);
		}
	}

	@Override
	public int size() {
		final Criteria criteria = getCriteria();
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();

	}

	@Override
	public T getById(PK primaryKey) {
		return (T) getSession().get(persistentClass, primaryKey);

	}

	public T getById(String primaryKey) {
		return (T) getSession().get(persistentClass, primaryKey);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(SortCriteriaData sortCriteria) {
		return getCriteria(sortCriteria).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByPrimaryKeys(SortCriteriaData sortCriteria, PK... primaryKeys) {
		Criteria criteria = getCriteria(sortCriteria);
		criteria.add(Restrictions.in(getPrimaryKeyPropertyName(), primaryKeys));
		return criteria.list();

	}

	private String getPrimaryKeyPropertyName() {
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(persistentClass);
		String primaryKeyPropertyName = classMetadata.getIdentifierPropertyName();
		return primaryKeyPropertyName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByConditions(List<Criterion> criterions, SortCriteriaData sortCriteria) {
		List<T> objects = null;
		Criteria criteria = getCriteria(sortCriteria);
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);

			}
		}
		objects = criteria.list();
		return objects;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByCondition(Criterion criterion, SortCriteriaData sortCriteria) {
		List<T> objects = null;
		Criteria criteria = getCriteria(sortCriteria);
		if (criterion != null) {
			criteria.add(criterion);
		}
		objects = criteria.list();
		if (objects != null && !objects.isEmpty()) {
			return objects;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getByCondition(Criterion criterion) {
		List<T> objects = null;
		Criteria criteria = getCriteria();
		if (criterion != null) {
			criteria.add(criterion);
		}
		objects = criteria.list();
		if (objects != null && !objects.isEmpty()) {
			return (T) objects.get(0);
		}
		return null;
	}

	@Override
	public T getByConditions(List<Criterion> criterions) {
		List<T> objects = getAllByConditions(criterions, null);
		if (objects != null && !objects.isEmpty()) {
			return (T) objects.get(0);
		}
		return null;
	}

	@Override
	public int executeUpdateNativeNamedQuery(String queryName, Map<String, Object> parameters) {
		int result;
		Query query = null;
		query = getSession().getNamedQuery(queryName);
		if (parameters != null) {
			for (String name : parameters.keySet())
				query.setParameter(name, parameters.get(name));
		}
		result = query.executeUpdate();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> executeNativeNamedQuery(String queryName, Map<String, Object> parameters, Class<?> tansformerClass) {
		List<T> objects = null;
		Query query = null;
		query = getSession().getNamedQuery(queryName);
		if (parameters != null) {
			for (String name : parameters.keySet())
				query.setParameter(name, parameters.get(name));
		}
		if (tansformerClass != null) {
			query.setResultTransformer(Transformers.aliasToBean(tansformerClass));
		}
		objects = query.list();

		return objects;
	}
	
	

	@Override
	public void saveOrUpdateBatch(List<T> entities) {
		for (int i = 0; i < entities.size(); i++) {
			getSession().saveOrUpdate(entities.get(i));
			if (i % jdbcBatchSize == 0 && i > 0) {
				getSession().flush();
				getSession().clear();
			}

		}

	}

	@Override
	public void deleteBatch(List<T> entities) {
		for (int i = 0; i < entities.size(); i++) {
			getSession().delete(entities.get(i));
			if (i % jdbcBatchSize == 0 && i > 0) {
				getSession().flush();
				getSession().clear();
			}
		}

	}

	@Override
	public void saveOrUpdateBatchAsManualCommit(List<T> entities) {
		Transaction tx = null;
		tx = getSession().beginTransaction();
		for (int i = 0; i < entities.size(); i++) {
			getSession().saveOrUpdate(entities.get(i));
			if (i % jdbcBatchSize == 0 && i > 0) {
				getSession().flush();
				getSession().clear();
			}

		}
		tx.commit();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByPage(SortCriteriaData sortCriteria, Integer pageNo, Integer pageLimit) {
		Criteria criteria = getCriteria();
		criteria.setFirstResult((pageNo - 1) * pageLimit);
		criteria.setMaxResults(pageLimit);
		return criteria.list();
	}

	@Override
	public void saveBatch(List<T> entities) {
		for (int i = 0; i < entities.size(); i++) {
			getSession().save(entities.get(i));
			if (i % jdbcBatchSize == 0 && i > 0) {
				getSession().flush();
				getSession().clear();
			}
		}
	}

	@Override
	public void updateBatch(List<T> entities) {
		for (int i = 0; i < entities.size(); i++) {
			try {
				getSession().update(entities.get(i));
			} catch (Exception e) {
				getSession().merge(entities.get(i));
			}
			if (i % jdbcBatchSize == 0 && i > 0) {
				getSession().flush();
				getSession().clear();
			}
		}

	}

	@Override
	public void mergeBatch(List<T> entities) {
		for (int i = 0; i < entities.size(); i++) {
			getSession().merge(entities.get(i));
			if (i % jdbcBatchSize == 0 && i > 0) {
				getSession().flush();
				getSession().clear();
			}

		}

	}

	@Override
	public List<T> getAllByCondition(Criterion criterion, SortCriteriaData sortCriteria, Class<?> tansformerClass) {
		List<T> objects = null;
		Criteria criteria = getCriteria(sortCriteria);
		if (criterion != null) {
			criteria.add(criterion);
		}
		criteria.setResultTransformer(Transformers.aliasToBean(tansformerClass));
		objects = criteria.list();
		if (objects != null && !objects.isEmpty()) {
			return objects;
		}
		return null;
	}
}
