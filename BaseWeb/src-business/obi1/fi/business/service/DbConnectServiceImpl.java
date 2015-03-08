package obi1.fi.business.service;

import java.sql.Connection;
import java.util.Set;

import javax.persistence.metamodel.ManagedType;

import obi1.fi.business.datasource.DataSourceProperties;
import obi1.fi.business.datasource.FiDataSource;
import obi1.fi.business.entity.FiCdConfiguracaoCONF;
import obi1.fi.business.entity.FiCdGrupousuarioGRUS;
import obi1.fi.business.entity.FiCdUsuarioUSUA;
import obi1.fi.common.exception.FiException;
import obi1.fi.common.util.ConfigEnum;
import obi1.fi.common.util.Constantes;

import org.hibernate.cfg.Configuration;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public final class DbConnectServiceImpl extends AbstractService implements DbConnectService {
	
	@Autowired
	private GenericService gService;

	@Override
	public void checkSchema(DataSourceProperties dsProperties) {
		
		try {
			gService.getListALLEntity(new FiCdConfiguracaoCONF());
		}
		catch (Exception x) {
			if (x.getCause() instanceof SQLGrammarException) {
				try {
					Connection connection = new FiDataSource(true).getConnection();
					Configuration config = new Configuration();
					config.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.DefaultNamingStrategy");
					
				//	if (dsProperties.getAppType().equals(AppTypeEnum.CLIENT.getValue())) {
				//		config.setProperty("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
				//	}
				//	else {
						config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
				//	}
					
					final Set<ManagedType<?>> managedTypes = getEM().getMetamodel().getManagedTypes();
					
				    for (ManagedType<?> managedType : managedTypes) {
				    	final Class<?> javaType = managedType.getJavaType();
				    	config.addAnnotatedClass(javaType);
				    }

			    	SchemaExport schema = new SchemaExport(config, connection);
			    	schema.create(false, true);
				}
				catch (Exception e) {
					throw new FiException(e, "Erro ao tentar criar o schema base (" + e.getMessage() + ")");
				}
			}
			else {
				throw new FiException(x, "Erro ao conectar no banco (" + x.getMessage() + ")");
			}
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void checkDefaultRecords(DataSourceProperties dsProperties) {
		
		try {
			if (gService.getListALLEntity(new FiCdConfiguracaoCONF()).size() == 0) {
				
				//GRUPO DE USUÁRIO
				FiCdGrupousuarioGRUS grupoAdm = new FiCdGrupousuarioGRUS(gService.getNextId(new FiCdGrupousuarioGRUS()), "Administração");
				getEM().persist(grupoAdm);
								
				//USUARIO ADMINISTRADOR
				getEM().persist(new FiCdUsuarioUSUA(Constantes.ID_USER_ADM, grupoAdm, "Administrador", "", "", "admin", "NHGNgMJAMnLJD"));
				
				//CONFIGURAÇÕES
				for (ConfigEnum configEnum : ConfigEnum.values()) {
					getEM().persist(new FiCdConfiguracaoCONF(configEnum));
				}
			}
		}
		catch (Exception e) {
			throw new FiException(e, "Erro ao tentar criar os registros base (" + e.getMessage() + ")");
		}
	}
}
