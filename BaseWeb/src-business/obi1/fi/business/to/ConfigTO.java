package obi1.fi.business.to;

import obi1.fi.business.entity.FiCdConfiguracaoCONF;

public final class ConfigTO extends AbstractTO<FiCdConfiguracaoCONF> {

	//Configurações server
	private String parametroNumPaginacao;
	
	private String systemMailProtocol;
	private String systemMailHost;
	private String systemMailPort;
	private String systemMailAuth;
	private String systemMailFrom;
	private String systemMailUser;
	private String systemMailPwd;

	public ConfigTO() {
		setEntity(new FiCdConfiguracaoCONF());
	}

	public String getSystemMailProtocol() {
		return systemMailProtocol;
	}

	public void setSystemMailProtocol(String systemMailProtocol) {
		this.systemMailProtocol = systemMailProtocol;
	}

	public String getSystemMailHost() {
		return systemMailHost;
	}

	public void setSystemMailHost(String systemMailHost) {
		this.systemMailHost = systemMailHost;
	}

	public String getSystemMailPort() {
		return systemMailPort;
	}

	public void setSystemMailPort(String systemMailPort) {
		this.systemMailPort = systemMailPort;
	}

	public String getSystemMailAuth() {
		return systemMailAuth;
	}

	public void setSystemMailAuth(String systemMailAuth) {
		this.systemMailAuth = systemMailAuth;
	}

	public String getSystemMailFrom() {
		return systemMailFrom;
	}

	public void setSystemMailFrom(String systemMailFrom) {
		this.systemMailFrom = systemMailFrom;
	}

	public String getSystemMailUser() {
		return systemMailUser;
	}

	public void setSystemMailUser(String systemMailUser) {
		this.systemMailUser = systemMailUser;
	}

	public String getSystemMailPwd() {
		return systemMailPwd;
	}

	public void setSystemMailPwd(String systemMailPwd) {
		this.systemMailPwd = systemMailPwd;
	}

	public String getParametroNumPaginacao() {
		return parametroNumPaginacao;
	}

	public void setParametroNumPaginacao(String parametroNumPaginacao) {
		this.parametroNumPaginacao = parametroNumPaginacao;
	}

}