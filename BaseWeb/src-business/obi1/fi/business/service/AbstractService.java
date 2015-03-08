package obi1.fi.business.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import obi1.fi.business.entity.AbstractEntity;
import obi1.fi.business.to.AbstractTO;
import obi1.fi.common.exception.FiException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final Integer aliasLenght = 4;
	
	protected final String getPKField(AbstractEntity entity) {
		String fieldName = "";
		boolean found = false;
		
		for (Field field : entity.getClass().getDeclaredFields()) {
			for (Annotation ann : field.getDeclaredAnnotations()) {
				if (ann instanceof Id) {
					found = true;
					break;
				}
			}
			
			if (found) {
				fieldName = field.getName();
				break;
			}
		}
		
		return fieldName;
	}
	
	protected final String getEntityName(AbstractEntity entity) {
		String className = entity.getClass().getName();
		className = className.substring(className.indexOf("Fi"));
		return className;
	}
	
	protected final String getAlias(String entityName) {
		return entityName.substring(entityName.length() - aliasLenght);
	}
	
	protected final void removeAll(List<? extends AbstractEntity> entitysList) {
		for (Object obj : entitysList) {
			em.remove(obj);
		}
	}

	@SuppressWarnings("unchecked")
	protected final <T extends AbstractEntity> T getEntity(T entity) {
		AbstractEntity entityReturn = null;
		if (entity != null && entity.getId() != null) {
			entityReturn = em.find(entity.getClass(), entity.getId());
		}
		
		return (T) entityReturn;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractEntity> List<T> getListALLEntity(T entityInstance) {
		List<T> resultList = new ArrayList<T>();
		
		String entityName = getEntityName(entityInstance);
		Query dataQuery = em.createQuery("from " + entityName + " " + getAlias(entityName));
		List<Object> list = dataQuery.getResultList();
		
		try {
			for (Object obj : list) {
				resultList.add((T) obj);
			}
		}
		catch (Exception x) {
			throw new FiException(x.getCause(), "Erro recuperando lista " 
					+ ((entityInstance == null) ? "(null)" : entityInstance.getClass().toString()));
		}
		
		return resultList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void fillPaginateTO(AbstractTO baseTO, Query query, Query queryCount, int maxItems) {
		int currentPage = baseTO.getResultTable().getCurrPage();
		
		query.setFirstResult((currentPage - 1) *  maxItems);
		query.setMaxResults(maxItems);
		
		baseTO.getResultTable().setMaxItemsPage(maxItems);
		baseTO.getResultTable().setNumTotalItems(((Long) queryCount.getSingleResult()).intValue());
		
		baseTO.getResultTable().setValues(query.getResultList());
	}

	protected void appendSqlCriterias(StringBuilder sql, List<String> criterias) {
		boolean hasWhere = true;
		
		if (criterias.size() > 0) {
			if (sql.indexOf("where") == -1) {
				sql.append("where ");
				hasWhere = false;
			}
			
			for (int i = 0; i < criterias.size(); i++) {			
				if (i > 0 || hasWhere) {
					sql.append("and ");
				}
				sql.append(criterias.get(i) + " ");
			}
		}
	}

	protected void setSqlParameters(Map<String, Object> parameterMap, Query query) {
		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	public final EntityManager getEM() {
		return em;
	}

	public final JdbcTemplate getJDBC() {
		return jdbcTemplate;
	}

}
