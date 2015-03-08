package obi1.fi.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FI_CD_USUARIO_USUA")
public class FiCdUsuarioUSUA extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_USUA_CD_USUARIO", unique = true, nullable = false)
	private Integer idUsuaCdUsuario;	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_GRUS_CD_GRUPOUSUARIO", nullable = false)
	private FiCdGrupousuarioGRUS fiCdGrupousuarioGRUS;
	
	@Column(name = "USUA_DS_NOME", length = 50)
	private String usuaDsNome;

	@Column(name = "USUA_DS_TELEFONE", length = 50)
	private String usuaDsTelefone;

	@Column(name = "USUA_DS_EMAIL", length = 50)
	private String usuaDsEmail;

	@Column(name = "USUA_IN_INATIVO", length = 1)
	private String usuaInInativo;

	@Column(name = "USUA_DS_LOGIN", unique = true, length = 10)
	private String usuaDsLogin;

	@Column(name = "USUA_DS_PWD", length = 100)
	private String usuaDsPwd;
	
	@Column(name = "USUA_NR_TENTATIVAS", length = 1)
	private Integer usuaNrTentativas;
	
	public FiCdUsuarioUSUA() { }
	
	public FiCdUsuarioUSUA(Integer idUsuaCdUsuario, FiCdGrupousuarioGRUS fiCdGrupousuarioGRUS, String usuaDsNome, String usuaDsTelefone, 
			String usuaDsEmail, String usuaDsLogin, String usuaDsPwd) {
		
		this.idUsuaCdUsuario = idUsuaCdUsuario;
		this.fiCdGrupousuarioGRUS = fiCdGrupousuarioGRUS;
		this.usuaDsNome = usuaDsNome;
		this.usuaDsTelefone = usuaDsTelefone;
		this.usuaDsEmail = usuaDsEmail;
		this.usuaInInativo = "n";
		this.usuaDsLogin = usuaDsLogin;
		this.usuaDsPwd = usuaDsPwd;
		this.usuaNrTentativas = 0;
	}
	
	@Override
	public Class<FiCdUsuarioUSUA> getEntityClass() {
		return FiCdUsuarioUSUA.class;
	}

	@Override
	public Integer getId() {
		return getIdUsuaCdUsuario();
	}

	@Override
	public void setId(Integer id) {
		setIdUsuaCdUsuario(id);
	}
	
	public Integer getIdUsuaCdUsuario() {
		return idUsuaCdUsuario;
	}

	public void setIdUsuaCdUsuario(Integer idUsuaCdUsuario) {
		this.idUsuaCdUsuario = idUsuaCdUsuario;
	}

	public String getUsuaDsNome() {
		return usuaDsNome;
	}

	public void setUsuaDsNome(String usuaDsNome) {
		this.usuaDsNome = usuaDsNome;
	}

	public String getUsuaDsTelefone() {
		return usuaDsTelefone;
	}

	public void setUsuaDsTelefone(String usuaDsTelefone) {
		this.usuaDsTelefone = usuaDsTelefone;
	}

	public String getUsuaDsEmail() {
		return usuaDsEmail;
	}

	public void setUsuaDsEmail(String usuaDsEmail) {
		this.usuaDsEmail = usuaDsEmail;
	}

	public String getUsuaInInativo() {
		return usuaInInativo;
	}

	public void setUsuaInInativo(String usuaInInativo) {
		this.usuaInInativo = usuaInInativo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUsuaDsLogin() {
		return usuaDsLogin;
	}

	public void setUsuaDsLogin(String usuaDsLogin) {
		this.usuaDsLogin = usuaDsLogin;
	}

	public String getUsuaDsPwd() {
		return usuaDsPwd;
	}

	public void setUsuaDsPwd(String usuaDsPwd) {
		this.usuaDsPwd = usuaDsPwd;
	}

	public FiCdGrupousuarioGRUS getFiCdGrupousuarioGRUS() {
		return fiCdGrupousuarioGRUS;
	}

	public void setFiCdGrupousuarioGRUS(FiCdGrupousuarioGRUS fiCdGrupousuarioGRUS) {
		this.fiCdGrupousuarioGRUS = fiCdGrupousuarioGRUS;
	}

	public Integer getUsuaNrTentativas() {
		return usuaNrTentativas;
	}

	public void setUsuaNrTentativas(Integer usuaNrTentativas) {
		this.usuaNrTentativas = usuaNrTentativas;
	}

}
