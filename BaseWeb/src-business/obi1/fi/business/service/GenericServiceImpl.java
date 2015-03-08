package obi1.fi.business.service;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import obi1.fi.business.entity.AbstractEntity;
import obi1.fi.business.to.AbstractTO;
import obi1.fi.business.to.NumeradoraTO;
import obi1.fi.common.exception.FiException;
import obi1.fi.common.util.ConfigEnum;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("rawtypes")
@Repository
@Transactional
public final class GenericServiceImpl extends AbstractService implements GenericService {

	private static final HashMap<String, NumeradoraTO> NUMERADORA = new HashMap<String, NumeradoraTO>();
	private static final Integer DEFAULT_NUME_APP_BUFFER = 50;
	private static final Integer NUME_FIRST_ID = 1;

	@Autowired
	private ConfigService configService;

	@SuppressWarnings("unchecked")
	@Override
	public void save(AbstractTO baseTO) {
		try {
			AbstractEntity entity;
			
			Class entityClass = baseTO.getEntity().getEntityClass();
			Integer id = baseTO.getEntity().getId();
			boolean newRecord = (baseTO.getEntity().getId() == null || baseTO.getEntity().getId() < 0);
			
			if (!newRecord) {
				entity = getEM().find(entityClass, id);
				PropertyUtils.copyProperties(entity, baseTO.getEntity());
				id = entity.getId();
			}
			else {
				entity = baseTO.getEntity();
				id = getNextId(entity);
			}
			
			entity.setId(id);
			getEM().merge(entity);
		}
		catch (Exception x) {
			throw new FiException(x, "Erro salvando "
					+ ((baseTO == null) ? "(null)" : baseTO.getClass().toString()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(AbstractTO baseTO) {
		try {
			AbstractEntity entity = getEM().find(baseTO.getEntity().getEntityClass(), baseTO.getEntity().getId());
			getEM().remove(entity);
		}
		catch (Exception x) {
			throw new FiException(x, "Erro excluindo "
					+ ((baseTO == null) ? "(null)" : baseTO.getClass().toString()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fillTO(AbstractTO baseTO) {
		try {
			if (baseTO.getEntity().getId() != null && baseTO.getEntity().getId() > 0) {
				AbstractEntity entity = getEM().find(baseTO.getEntity().getEntityClass(), baseTO.getEntity().getId());
				baseTO.setEntity(entity);
			}
			else {
				baseTO.getEntity().setId(null);
			}
		}
		catch (Exception x) {
			throw new FiException(x, "Erro recuperando "
					+ ((baseTO == null) ? "(null)" : baseTO.getClass().toString()));
		}
	}
	
	@Override
	public void fillDataQueryBaseTO(AbstractTO baseTOInstance) {
		String entityName = getEntityName(baseTOInstance.getEntity());
		
		StringBuffer whereCondition = new StringBuffer(" where ")
			.append(getPKField(baseTOInstance.getEntity()))
			.append(" > -1 ");
		
		StringBuffer sql = new StringBuffer("from ").append(entityName).
				append(" ").append(entityName).append(whereCondition);
		
		StringBuffer sqlCount = new StringBuffer("select count(*) from ").append(entityName).
				append(" ").append(entityName).append(whereCondition);
		
		Query query = getEM().createQuery(sql.toString());
		Query queryCount = getEM().createQuery(sqlCount.toString());
		
		fillPaginateTO(baseTOInstance, query, queryCount, configService.getInt(ConfigEnum.PARAMETROS_NUM_PAGINACAO));
	}

	@Override
	public <T extends AbstractEntity> T getEntity(Class<T> entityClass, Integer id) throws InstantiationException, IllegalAccessException {
		T entity = entityClass.newInstance();
		entity.setId(id);
		return super.getEntity(entity);
	}
	
	@Override
	public <T extends AbstractEntity> List<T> getListALLEntity(T entityInstance) {
		return super.getListALLEntity(entityInstance);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer getNextId(AbstractEntity entity) {
		SqlRowSet rowSet;
		String entityName = getEntityName(entity);
		NumeradoraTO numeradoraTO;
		Integer nextVal;
		
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("SELECT NUME_NR_NEXTVAL, NUME_NR_APPBUFFER FROM FI_NG_NUMERADORA_NUME WHERE NUME_DS_ENTITY = '").append(entityName).append("'");
		
		StringBuffer sqlUpdate = new StringBuffer();
		sqlUpdate.append("UPDATE FI_NG_NUMERADORA_NUME SET NUME_NR_NEXTVAL = NUME_NR_NEXTVAL + NUME_NR_APPBUFFER WHERE NUME_DS_ENTITY = '").append(entityName).append("'");

		
		//Se ainda não tiver com o valor armazenado, pega do banco
		if (NUMERADORA.get(entityName) == null) {
			//Se não existir no banco, cria na hora
			rowSet = getJDBC().queryForRowSet(sqlQuery.toString());
			if (!rowSet.next()) {
				StringBuffer sqlInsert = new StringBuffer();
				sqlInsert.append("INSERT INTO FI_NG_NUMERADORA_NUME").
					append("(NUME_DS_ENTITY, NUME_NR_NEXTVAL, NUME_NR_APPBUFFER) VALUES('").
					append(entityName).append("', ").append(DEFAULT_NUME_APP_BUFFER).append(", ").append(DEFAULT_NUME_APP_BUFFER).append(")");
				
				getJDBC().execute(sqlInsert.toString());
				
				NUMERADORA.put(entityName, new NumeradoraTO(entityName, NUME_FIRST_ID, DEFAULT_NUME_APP_BUFFER - 1));
			}
			else {
				//Pega o próximo range
				getJDBC().execute(sqlUpdate.toString());
				NUMERADORA.put(entityName, new NumeradoraTO(entityName, rowSet.getInt("NUME_NR_NEXTVAL"), 
						(rowSet.getInt("NUME_NR_NEXTVAL") + rowSet.getInt("NUME_NR_APPBUFFER")) - 1));
			}
			
		}
		
		numeradoraTO = NUMERADORA.get(entityName);
		nextVal = numeradoraTO.getNextVal();

		//Se chegar no limite, atualiza o banco e pega o próximo range
		if (nextVal == numeradoraTO.getMaxBuffer().intValue()) {
			rowSet = getJDBC().queryForRowSet(sqlQuery.toString());
			rowSet.next();

			//Pega o próximo range
			getJDBC().execute(sqlUpdate.toString());
			numeradoraTO.setMaxBuffer((rowSet.getInt("NUME_NR_NEXTVAL") + rowSet.getInt("NUME_NR_APPBUFFER")) - 1);
		}
		
		numeradoraTO.setNextVal(nextVal + 1);
		NUMERADORA.put(entityName, numeradoraTO);
		
		return nextVal;
	}
}
