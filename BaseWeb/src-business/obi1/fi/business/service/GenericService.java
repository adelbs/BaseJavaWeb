package obi1.fi.business.service;

import java.util.List;

import obi1.fi.business.entity.AbstractEntity;
import obi1.fi.business.to.AbstractTO;

@SuppressWarnings("rawtypes")
public interface GenericService {

	void save(AbstractTO baseTO);
	
	void delete(AbstractTO baseTO);
	
	void fillTO(AbstractTO baseTO);

	void fillDataQueryBaseTO(AbstractTO baseTO);

	<T extends AbstractEntity> List<T> getListALLEntity(T entityInstance);

	Integer getNextId(AbstractEntity entity);
	
	<T extends AbstractEntity> T getEntity(Class<T> entityClass, Integer id) throws InstantiationException, IllegalAccessException;
	
}
