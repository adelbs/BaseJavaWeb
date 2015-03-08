package obi1.fi.business.service;

import java.util.List;

import obi1.fi.business.entity.FiCdUsuarioUSUA;
import obi1.fi.business.to.UsuarioTO;

public interface UsuarioService {

	UsuarioTO findFiCdUsuarioUSUA(String usuaDsLogin, String usuaDsPwd);

	void save(UsuarioTO usuarioTO);

	void fillTO(UsuarioTO usuarioTO);
	
	List<FiCdUsuarioUSUA> getListEntityAtivos();
	
	void incrementaNrTentativas(String usuaDsLogin);
	
	void zeraNrTentativas(String usuaDsLogin);

	void fillDataQueryBaseTO(UsuarioTO usuarioTO);
	
}
