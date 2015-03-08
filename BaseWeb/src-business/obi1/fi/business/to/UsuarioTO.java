package obi1.fi.business.to;

import java.util.ArrayList;
import java.util.List;

import obi1.fi.business.entity.FiCdUsuarioUSUA;
import obi1.fi.common.to.DataTableTO;

public final class UsuarioTO extends AbstractTO<FiCdUsuarioUSUA> {

	private List<FiCdUsuarioUSUA> listGrupoUsuario = new ArrayList<FiCdUsuarioUSUA>();
	
	
	//Atributos para utilização do filtro
	private String nomeUsua;
	
	private String login;
	
	private Integer grupoUsua;
	
	public UsuarioTO() {
		setResultTable(new DataTableTO("usuaDsNome", "usuaDsTelefone", "usuaDsEmail", "usuaInInativo")); 
		setEntity(new FiCdUsuarioUSUA());
	}

	public List<FiCdUsuarioUSUA> getListGrupoUsuario() {
		return listGrupoUsuario;
	}

	public void setListGrupoUsuario(List<FiCdUsuarioUSUA> listGrupoUsuario) {
		this.listGrupoUsuario = listGrupoUsuario;
	}

	public String getNomeUsua() {
		return nomeUsua;
	}

	public void setNomeUsua(String nomeUsua) {
		this.nomeUsua = nomeUsua;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getGrupoUsua() {
		return grupoUsua;
	}

	public void setGrupoUsua(Integer grupoUsua) {
		this.grupoUsua = grupoUsua;
	}
}
