package obi1.fi.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FI_CD_GRUPOUSUARIO_GRUS")
public class FiCdGrupousuarioGRUS extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_GRUS_CD_GRUPOUSUARIO", unique = true, nullable = false)
	private Integer idGrusCdGrupousuario;

	@Column(name = "GRUS_DS_GRUPOUSUARIO", unique = true, length = 100, nullable = false)
	private String grusDsGrupousuario;

	@Column(name = "GRUS_IN_INATIVO", length = 1)
	private String grusInInativo;

	public FiCdGrupousuarioGRUS() { }
	
	public FiCdGrupousuarioGRUS(Integer idGrusCdGrupousuario, String grusDsGrupousuario) {
		this.idGrusCdGrupousuario = idGrusCdGrupousuario;
		this.grusDsGrupousuario = grusDsGrupousuario;
		this.grusInInativo = "n";
	}
	
	@Override
	public Class<FiCdGrupousuarioGRUS> getEntityClass() {
		return FiCdGrupousuarioGRUS.class;
	}

	@Override
	public Integer getId() {
		return getIdGrusCdGrupousuario();
	}

	@Override
	public void setId(Integer id) {
		setIdGrusCdGrupousuario(id);
	}
	
	public Integer getIdGrusCdGrupousuario() {
		return idGrusCdGrupousuario;
	}

	public void setIdGrusCdGrupousuario(Integer idGrusCdGrupousuario) {
		this.idGrusCdGrupousuario = idGrusCdGrupousuario;
	}

	public String getGrusDsGrupousuario() {
		return grusDsGrupousuario;
	}

	public void setGrusDsGrupousuario(String grusDsGrupousuario) {
		this.grusDsGrupousuario = grusDsGrupousuario;
	}

	public String getGrusInInativo() {
		return grusInInativo;
	}

	public void setGrusInInativo(String grusInInativo) {
		this.grusInInativo = grusInInativo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
