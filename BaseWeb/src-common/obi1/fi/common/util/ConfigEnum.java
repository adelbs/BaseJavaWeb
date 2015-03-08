package obi1.fi.common.util;

public enum ConfigEnum {

	PARAMETROS_NUM_PAGINACAO (1, "parametros.numpaginacao", "15"),
	
	SYSTEM_MAIL_PROTOCOL (100, "system.mail.protocol", "smtp"),
	SYSTEM_MAIL_HOST (101, "system.mail.host", ""),
	SYSTEM_MAIL_PORT (102, "system.mail.port", "587"),
	SYSTEM_MAIL_AUTH (103, "system.mail.auth", "s"),
	SYSTEM_MAIL_FROM (104, "system.mail.from", "from@obi.com.br"),
	SYSTEM_MAIL_USER (105, "system.mail.user", "user@obi.com.br"),
	SYSTEM_MAIL_PWD (106, "system.mail.pwd", "");

	private int id;
	private String key;
	private String defaultValue;
	
	ConfigEnum(int id, String key, String defaultValue) {
		this.id = id;
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public int getId() {
		return id;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
}
