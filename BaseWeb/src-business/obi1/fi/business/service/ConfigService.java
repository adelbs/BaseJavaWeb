package obi1.fi.business.service;

import obi1.fi.business.to.ConfigTO;
import obi1.fi.common.util.ConfigEnum;

public interface ConfigService {

	Integer getInt(ConfigEnum configEnum);
	
	String getString(ConfigEnum configEnum);
	
	void save(ConfigTO configTO);

	void save(ConfigEnum config, String value);
		
	void fillTO(ConfigTO configTO);
}
