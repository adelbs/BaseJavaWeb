package obi1.fi.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FI_NG_NUMERADORA_NUME")
public class FiNgNumeradoraNUME extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "NUME_DS_ENTITY", unique = true, nullable = false, length = 50)
	private String numeDsEntity;

	@Column(name = "NUME_NR_NEXTVAL", nullable = false)
	private Integer msgmNrNextval;

	@Column(name = "NUME_NR_APPBUFFER", nullable = false)
	private Integer msgmNrAppbuffer;
	
	@Override
	public Class<FiNgNumeradoraNUME> getEntityClass() {
		return FiNgNumeradoraNUME.class;
	}

	@Override
	public Integer getId() {
		return null;
	}

	@Override
	public void setId(Integer id) { }
	
	public String getNumeDsEntity() {
		return numeDsEntity;
	}

	public void setNumeDsEntity(String numeDsEntity) {
		this.numeDsEntity = numeDsEntity;
	}

	public Integer getMsgmNrNextval() {
		return msgmNrNextval;
	}

	public void setMsgmNrNextval(Integer msgmNrNextval) {
		this.msgmNrNextval = msgmNrNextval;
	}

	public Integer getMsgmNrAppbuffer() {
		return msgmNrAppbuffer;
	}

	public void setMsgmNrAppbuffer(Integer msgmNrAppbuffer) {
		this.msgmNrAppbuffer = msgmNrAppbuffer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
