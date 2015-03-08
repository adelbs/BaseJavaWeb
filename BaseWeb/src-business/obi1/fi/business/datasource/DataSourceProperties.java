package obi1.fi.business.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import obi1.fi.common.exception.FiException;
import obi1.fi.common.util.Encrypt;

import org.apache.log4j.Logger;

public final class DataSourceProperties {

	private final String fileName = "/connection.properties";

	private Logger logger = Logger.getLogger(DataSourceProperties.class);

	private String filePath;

	private Properties prop = new Properties();
	private OutputStream output;
	private InputStream input;

	//Propriedades de conexão
	private String driverClassName;
	private String dbHost;
	private String dbPort;
	private String dbName;
	private String url;
	private String username;
	private String password;

	public DataSourceProperties() {

		//Localizando local da máquina onde deve estar o arquivo de configuração para conexão
		this.filePath = System.getProperty("user.home") +"/obi1";
		if (!new File(filePath).exists()) {
			new File(filePath).mkdir();
		}

		if (!(filePath != null && new File(filePath + fileName).exists())) {
			setDriverClassName("");
			setUrl("");
			setUsername("");
			setPassword("");
		}
		else {
			loadProperties();
		}
	}

	public void setDriverClassName(String value) {
		setPropertie("driverClassName", value);
		driverClassName = value;
	}

	public void setDbPort(String value) {
		setPropertie("dbPort", value);
		dbPort = value;
	}

	public void setDbHost(String value) {
		setPropertie("dbHost", value);
		dbHost = value;
	}

	public void setDbName(String value) {
		setPropertie("dbName", value);
		dbName = value;
	}

	public void setUrl(String value) {
		setPropertie("url", value);
		url = value;
	}

	public void setUsername(String value) {
		setPropertie("username", value);
		username = value;
	}

	public void setPassword(String value) {
		setPropertie("password", Encrypt.encrypt("PRUVO", value));
		password = value;
	}

	public String getDriverClassName() {
		if (driverClassName == null || "".equals(driverClassName)) {
			driverClassName = "org.apache.derby.jdbc.EmbeddedDriver";
		}
		return driverClassName;
	}

	public String getDbHost() {
		return dbHost;
	}

	public String getDbPort() {
		return dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public String getUrl() {
		if (url == null || "".equals(url)) {
			url = "jdbc:mysql://serverx:9999/database";
		}
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return Encrypt.decrypt("PRUVO", password);
	}

	private void setPropertie(String key, String value) {
		try {
			output = new FileOutputStream(filePath + fileName);
			prop.setProperty(key, value);
			prop.store(output, null);
		}
		catch (IOException io) {
			logger.error(io);
		}
		finally {
			if (output != null) {
				try {
					output.close();
				}
				catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}

	private void loadProperties() {
		try {
			input = new FileInputStream(filePath + fileName);
			prop.load(input);

			driverClassName = prop.getProperty("driverClassName");

			dbHost = prop.getProperty("dbHost");
			dbPort = prop.getProperty("dbPort");
			dbName = prop.getProperty("dbName");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");

		}
		catch (IOException io) {
			logger.error(io);
		}
		finally {
			if (input != null) {
				try {
					input.close();
				}
				catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}

	public void configDataSource() {
		try {
			StringBuffer urlConn = null;
			urlConn = new StringBuffer();
		/*	urlConn.append("jdbc:mysql://").
					append(getDbHost()).append(":").
					append(getDbPort()).append("/").
					append(getDbName());*/

			//setDriverClassName("com.mysql.jdbc.Driver");

			urlConn.append("jdbc:derby:"+ filePath +"/DB;create=true;user=me;password=mine/");
			setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");

			setUrl(urlConn.toString());
		}
		catch (Exception x) {
			throw new FiException(x, "Erro configurando parametros de conexão (" + x.getMessage() + ")");
		}
	}
}
