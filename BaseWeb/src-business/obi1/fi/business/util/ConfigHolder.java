package obi1.fi.business.util;

import java.util.HashMap;

import obi1.fi.common.util.ConfigEnum;

public final class ConfigHolder {

	private static HashMap<ConfigEnum, String> configValues = new HashMap<ConfigEnum, String>();
	
	public static String get(ConfigEnum configEnum) {
		return configValues.get(configEnum);
	}
	
	public static void put(ConfigEnum configEnum, String value) {
		configValues.put(configEnum, value);
	}
}
