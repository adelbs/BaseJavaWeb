package obi1.fi.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import obi1.fi.common.util.ConfigEnum;

@Entity
@Table(name = "FI_CD_CONFIGURACAO_CONF")
public class FiCdConfiguracaoCONF extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_CONF_CD_CONFIGURACAO", unique = true, nullable = false)
	private Integer idConfCdConfiguracao;	

	@Column(name = "CONF_DS_CHAVE", length = 100)
	private String confDsChave;

	@Column(name = "CONF_DS_VALOR", length = 255)
	private String confDsValor;
	
	public FiCdConfiguracaoCONF() { }
	
	public FiCdConfiguracaoCONF(ConfigEnum configEnum) {
		this(configEnum, configEnum.getDefaultValue());
	}
	
	public FiCdConfiguracaoCONF(ConfigEnum configEnum, String confDsValor) {
		this.idConfCdConfiguracao = configEnum.getId();
		this.confDsChave = configEnum.getKey();
		this.confDsValor = confDsValor;
	}
	
	@Override
	public Class<FiCdConfiguracaoCONF> getEntityClass() {
		return FiCdConfiguracaoCONF.class;
	}

	@Override
	public Integer getId() {
		return getIdConfCdConfiguracao();
	}

	@Override
	public void setId(Integer id) {
		setIdConfCdConfiguracao(id);
	}
	
	public Integer getIdConfCdConfiguracao() {
		return idConfCdConfiguracao;
	}

	public void setIdConfCdConfiguracao(Integer idConfCdConfiguracao) {
		this.idConfCdConfiguracao = idConfCdConfiguracao;
	}

	public String getConfDsChave() {
		return confDsChave;
	}

	public void setConfDsChave(String confDsChave) {
		this.confDsChave = confDsChave;
	}

	public String getConfDsValor() {
		return confDsValor;
	}

	public void setConfDsValor(String confDsValor) {
		this.confDsValor = confDsValor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
