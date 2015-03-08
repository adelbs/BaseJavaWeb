package obi1.fi.business.service;

import obi1.fi.business.datasource.DataSourceProperties;

public interface DbConnectService {

	void checkSchema(DataSourceProperties dsProperties);
	
	void checkDefaultRecords(DataSourceProperties dsProperties);
	
}
