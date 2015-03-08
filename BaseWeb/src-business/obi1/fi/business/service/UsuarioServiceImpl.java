package obi1.fi.business.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import obi1.fi.business.entity.FiCdUsuarioUSUA;
import obi1.fi.business.exception.FiBusinessException;
import obi1.fi.business.to.UsuarioTO;
import obi1.fi.common.exception.FiException;
import obi1.fi.common.util.ConfigEnum;
import obi1.fi.common.util.Encrypt;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class UsuarioServiceImpl extends AbstractService implements UsuarioService {

	@Autowired
	private GenericService gService;

	@Autowired
	private ConfigService configService;
	
	@Override
	public void fillTO(UsuarioTO usuarioTO) {
		StringBuffer sql = new StringBuffer();
		FiCdUsuarioUSUA fiCdUsuarioUSUA = new FiCdUsuarioUSUA();
		
		try {
			if (usuarioTO.getEntity().getId() > 0) {
				sql.append("from FiCdUsuarioUSUA USUA ").
					append("inner join USUA.fiCdGrupousuarioGRUS GRUS ").
					append("where USUA.idUsuaCdUsuario=:idUsuario");
				Query dataQuery = getEM().createQuery(sql.toString());
				dataQuery.setParameter("idUsuario", usuarioTO.getEntity().getId());
				
				fiCdUsuarioUSUA = (FiCdUsuarioUSUA) ((Object[]) dataQuery.getSingleResult())[0];
			}
			
			usuarioTO.setEntity(fiCdUsuarioUSUA);
		}
		catch (Exception x) {
			throw new FiException(x, "Erro ao tentar localizar o Usuário");
		}
	}
	
	@Override
	public UsuarioTO findFiCdUsuarioUSUA(String usuaDsLogin, String usuaDsPwd) {
		
		StringBuffer sql;
		Query dataQuery;
		UsuarioTO resultUsuarioTO = null;
		
		try {
			//Recuperando o usuário
			sql = new StringBuffer();
			sql.append("select USUA from FiCdUsuarioUSUA USUA ").
				append("inner join USUA.fiCdGrupousuarioGRUS GRUS ").
				append("where USUA.usuaDsLogin=:usuaDsLogin and USUA.usuaDsPwd=:usuaDsPwd and USUA.usuaInInativo<>'s'");
			
			dataQuery = getEM().createQuery(sql.toString());
			dataQuery.setParameter("usuaDsLogin", usuaDsLogin);
			dataQuery.setParameter("usuaDsPwd", Encrypt.encrypt(usuaDsLogin, usuaDsPwd));
			
			FiCdUsuarioUSUA fiCdUsuarioUSUA = (FiCdUsuarioUSUA) dataQuery.getSingleResult();
			
			resultUsuarioTO = new UsuarioTO();
			resultUsuarioTO.setEntity(fiCdUsuarioUSUA);
		}
		catch (NoResultException nre) {			
			
			throw new FiBusinessException(nre, "Senha incorreta");
		}
		catch (Exception x) {		
			throw new FiException(x, "Erro ao tentar localizar o Usuário");
		}
			
		return resultUsuarioTO;
	}

	@Override
	public void save(UsuarioTO usuarioTO) {
		
		FiCdUsuarioUSUA usuaEntity;
		Integer idUsuario = usuarioTO.getEntity().getId();

		try {
			
			if ((idUsuario != null && idUsuario > 0) || (idUsuario != null && idUsuario == -1)) {
				usuaEntity = getEM().find(FiCdUsuarioUSUA.class, idUsuario);
			}
			else {
				usuaEntity = new FiCdUsuarioUSUA();
				idUsuario = gService.getNextId(usuaEntity);
			}
			
			PropertyUtils.copyProperties(usuaEntity, usuarioTO.getEntity());

			//Salvando  usuário
			usuaEntity.setId(idUsuario);
			usuaEntity.setUsuaNrTentativas(0);
			getEM().merge(usuaEntity);
		}
		catch (Exception x) {
			throw new FiException(x, "Erro salvando usuário " + x.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FiCdUsuarioUSUA> getListEntityAtivos() {

		StringBuffer sql = new StringBuffer();
		List<FiCdUsuarioUSUA> result;

		try {
			sql.append(" from FiCdUsuarioUSUA USUA where USUA.usuaInInativo='n' ");
			Query query = getEM().createQuery(sql.toString());
			result = query.getResultList();
		} 
		catch (Exception x) {
			throw new FiException(x, "Erro ao buscar lista de usuarios!");
		}

		return result;
	}
	
	@Override
	public void incrementaNrTentativas(String usuaDsLogin) {
		
		try { 
			Query dataQuery = getEM().createQuery("from FiCdUsuarioUSUA USUA where USUA.usuaDsLogin=:usuaDsLogin");
			dataQuery.setParameter("usuaDsLogin", usuaDsLogin);
			
			FiCdUsuarioUSUA fiCdUsuarioUSUA = (FiCdUsuarioUSUA) (dataQuery.getSingleResult());
			
			if (fiCdUsuarioUSUA.getUsuaNrTentativas() != null) {
				fiCdUsuarioUSUA.setUsuaNrTentativas(fiCdUsuarioUSUA.getUsuaNrTentativas() + 1);
			}
			else {
				fiCdUsuarioUSUA.setUsuaNrTentativas(1);
			}
			
			getEM().merge(fiCdUsuarioUSUA);
		}
		catch (NoResultException nre) {
			throw new FiBusinessException(nre, "Usuário não cadastrado");
		}
		catch (Exception e) {
			throw new FiException(e, "Erro ao tentar localizar o Usuário");
		} 
		
	}
	
	@Override
	public void zeraNrTentativas(String usuaDsLogin) {
		
		try { 
			
			Query dataQuery = getEM().createQuery("from FiCdUsuarioUSUA USUA where USUA.usuaDsLogin=:usuaDsLogin");
			
			dataQuery.setParameter("usuaDsLogin", usuaDsLogin);
			
			FiCdUsuarioUSUA fiCdUsuarioUSUA = (FiCdUsuarioUSUA) (dataQuery.getSingleResult());
			
			fiCdUsuarioUSUA.setUsuaNrTentativas(0);
			
			getEM().merge(fiCdUsuarioUSUA);
			
			//commit
		}
		catch (NoResultException nre) {
			throw new FiBusinessException(nre, "Usuário não cadastrado");
		}
		catch (Exception e) {
			throw new FiException(e, "Erro ao tentar localizar o Usuário");
		} 
		
	}

	@Override
	public void fillDataQueryBaseTO(UsuarioTO usuarioTO) {
		
		try {
			List<String> criteriaList = new ArrayList<String>();
			Map<String, Object> queryParameterMap = new HashMap<String, Object>();
			
			//Construindo a clausula where com os filtros de tela
			StringBuilder whereCondition = new StringBuilder("where USUA.idUsuaCdUsuario > -1 ");
			setCriteriasQueryParameters(usuarioTO, criteriaList, queryParameterMap);
			appendSqlCriterias(whereCondition, criteriaList);

			StringBuffer sql = new StringBuffer("select USUA from FiCdUsuarioUSUA USUA ").append(whereCondition);
			StringBuffer sqlCount = new StringBuffer("select count(*) from FiCdUsuarioUSUA USUA ").append(whereCondition);
			
			Query query = getEM().createQuery(sql.toString());
			setSqlParameters(queryParameterMap, query);
			
			Query queryCount = getEM().createQuery(sqlCount.toString());
			setSqlParameters(queryParameterMap, queryCount);
		
			fillPaginateTO(usuarioTO, query, queryCount, configService.getInt(ConfigEnum.PARAMETROS_NUM_PAGINACAO));
		}
		catch (Exception x) {
			throw new FiException(x, "Erro ao executar a consulta");
		}
		
	}
	
	
	/**
	 * Cria o map com os critérios de busca da query.
	 * @param usuarioTO TO
	 * @param criteriaList lista de critérios
	 * @param parameterMap map prenchido
	 * @throws ParseException 
	 */
	private void setCriteriasQueryParameters(UsuarioTO usuarioTO, List<String> criteriaList, Map<String, Object> parameterMap) throws ParseException {
		
		//Nome do usuario
		if (!StringUtils.isEmpty(usuarioTO.getNomeUsua())) {
			criteriaList.add("USUA.usuaDsNome like :nomeUsuario");
			parameterMap.put("nomeUsuario", usuarioTO.getNomeUsua() + "%");
		}
		
		//Login
		if (!StringUtils.isEmpty(usuarioTO.getLogin())) {
			criteriaList.add("USUA.usuaDsLogin like :login");
			parameterMap.put("login", usuarioTO.getLogin() + "%");
		}
		
		//Grupo Usuarios
		if (usuarioTO.getGrupoUsua() != null && usuarioTO.getGrupoUsua() > 0) {
			criteriaList.add("USUA.fiCdGrupousuarioGRUS.idGrusCdGrupousuario = :grupo");
			parameterMap.put("grupo", usuarioTO.getGrupoUsua());
		}
		
	}
}
