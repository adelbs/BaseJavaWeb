package obi1.fi.business.entity;

import java.io.Serializable;

import javax.persistence.Transient;

public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	public abstract Integer getId();
	
	@Transient
	public abstract void setId(Integer id);

	@SuppressWarnings("rawtypes")
	public abstract Class getEntityClass();

}
