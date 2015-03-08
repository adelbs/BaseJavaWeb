package obi1.fi.business.service;

import javax.persistence.Query;

import obi1.fi.business.entity.FiCdConfiguracaoCONF;
import obi1.fi.business.exception.FiBusinessException;
import obi1.fi.business.to.ConfigTO;
import obi1.fi.business.util.ConfigHolder;
import obi1.fi.common.util.ConfigEnum;
import obi1.fi.common.util.Encrypt;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public final class ConfigServiceImpl extends AbstractService implements ConfigService {

	private final String dcrptKEY = "PRUVO";
	
	@Override
	public void save(ConfigTO configTO) {
		save(ConfigEnum.PARAMETROS_NUM_PAGINACAO, configTO.getParametroNumPaginacao());
		
		save(ConfigEnum.SYSTEM_MAIL_PROTOCOL, configTO.getSystemMailProtocol());
		save(ConfigEnum.SYSTEM_MAIL_HOST, configTO.getSystemMailHost());
		save(ConfigEnum.SYSTEM_MAIL_PORT, configTO.getSystemMailPort());
		save(ConfigEnum.SYSTEM_MAIL_AUTH, configTO.getSystemMailAuth());
		save(ConfigEnum.SYSTEM_MAIL_FROM, configTO.getSystemMailFrom());
		save(ConfigEnum.SYSTEM_MAIL_USER, configTO.getSystemMailUser());
		
		if (!"".equals(configTO.getSystemMailPwd())) {
			save(ConfigEnum.SYSTEM_MAIL_PWD, Encrypt.encrypt(dcrptKEY, configTO.getSystemMailPwd()));
		}
	}

	@Override
	public String getString(ConfigEnum configEnum) {
		return getValue(configEnum);
	}

	@Override
	public Integer getInt(ConfigEnum configEnum) {
		String valConf = getValue(configEnum);
		return (valConf != null && !"".equals(valConf) ? Integer.parseInt(getValue(configEnum)) : 0);
	}
	
	private String getValue(ConfigEnum configEnum) {
		String value;
		
		if (ConfigHolder.get(configEnum) != null) {
			value = ConfigHolder.get(configEnum);
		}
		else {
			StringBuffer sql = new StringBuffer();
			sql.append("from FiCdConfiguracaoCONF CONF where CONF.idConfCdConfiguracao=:idConfig ");
			
			Query query = getEM().createQuery(sql.toString());
			query.setParameter("idConfig", configEnum.getId());
			
			FiCdConfiguracaoCONF confEntity = (FiCdConfiguracaoCONF) query.getSingleResult();
			value = confEntity.getConfDsValor();
			
			ConfigHolder.put(configEnum, value);
		}
		
		if (configEnum == ConfigEnum.SYSTEM_MAIL_PWD) {
			value = Encrypt.decrypt(dcrptKEY, value);
		}
		
		return value;
	}
	
	@Override
	public void fillTO(ConfigTO configTO) {
		
		configTO.setParametroNumPaginacao(getValue(ConfigEnum.PARAMETROS_NUM_PAGINACAO));

		configTO.setSystemMailProtocol(getValue(ConfigEnum.SYSTEM_MAIL_PROTOCOL));
		configTO.setSystemMailHost(getValue(ConfigEnum.SYSTEM_MAIL_HOST));
		configTO.setSystemMailPort(getValue(ConfigEnum.SYSTEM_MAIL_PORT));
		configTO.setSystemMailAuth(getValue(ConfigEnum.SYSTEM_MAIL_AUTH));
		configTO.setSystemMailFrom(getValue(ConfigEnum.SYSTEM_MAIL_FROM));
		configTO.setSystemMailUser(getValue(ConfigEnum.SYSTEM_MAIL_USER));
		//configTO.setSystem_mail_pwd(getValue(ConfigEnum.SYSTEM_MAIL_PWD));

	}
	
	@Override
	public void save(ConfigEnum config, String value) {
		try {
			FiCdConfiguracaoCONF confEntity = getEM().find(FiCdConfiguracaoCONF.class, config.getId());
			confEntity.setConfDsValor(value);
			getEM().merge(confEntity);
			
			ConfigHolder.put(config, value);
		}
		catch (Exception x) {
			throw new FiBusinessException(x, "Erro salvando configuração.");
		}
	}
}
