package obi1.fi.business.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public final class FiDataSource extends DriverManagerDataSource {

	private static boolean connected;
	
	public FiDataSource(boolean reConnect) {
		loadConnectionProperties(reConnect);
	}
	
	public FiDataSource() {
		this(false);
	}
	
	/**
	 * Método para conectar na base. Se já estiver conectado, não faz nada. Se receber o parametro como true, força a conexão.
	 * @param reConnect boolean caso seja desejada a reconexão
	 */
	private void loadConnectionProperties(boolean reConnect) {
		if (!FiDataSource.connected || reConnect) {
			
			DataSourceProperties dsProperties = new DataSourceProperties();
			
			//Caso o caminho da localização do arquivo de configuração exista, tenta pegar as configurações do arquivo
			if (dsProperties != null) {
				
				setDriverClassName(dsProperties.getDriverClassName());
				setUrl(dsProperties.getUrl());
				setUsername(dsProperties.getUsername());
				setPassword(dsProperties.getPassword());
				
				try {
					super.getConnection().createStatement();
					FiDataSource.connected = true;
				}
				catch (SQLException e) {
					//TODO
					e.printStackTrace();
				}
			}
			
		}
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		loadConnectionProperties(false);
		return super.getConnection();
	}
	
	public static boolean isConnected() {
		return connected;
	}
}
