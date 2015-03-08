package obi1.fi.business.to;

import obi1.fi.business.entity.AbstractEntity;
import obi1.fi.common.to.DataTableTO;

public abstract class AbstractTO<E extends AbstractEntity> {

	private DataTableTO resultTable;
	
	private E entity;
	
	public final E getEntity() {
		return entity;
	}
	
	public final void setEntity(E entity) {
		this.entity = entity;
	}
	
	public final DataTableTO getResultTable() {
		return resultTable;
	}

	public final void setResultTable(DataTableTO resultTable) {
		this.resultTable = resultTable;
	}

}
