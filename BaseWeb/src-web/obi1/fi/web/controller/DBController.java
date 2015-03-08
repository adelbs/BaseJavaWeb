package obi1.fi.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import obi1.fi.business.datasource.DataSourceProperties;
import obi1.fi.business.service.DbConnectService;
import obi1.fi.business.to.DBTO;
import obi1.fi.web.exception.FiAjaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("DB")
@Scope("request")
public final class DBController extends AbstractController {

	@Autowired
	private DbConnectService service;

	private DBTO dbTO;
	
	public DBController() {
		dbTO = new DBTO();
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request) {
		DataSourceProperties dsProperties = new DataSourceProperties();
		
		dbTO.setDbHost(dsProperties.getDbHost());
		dbTO.setDbName(dsProperties.getDbName());
		dbTO.setDbPort(dsProperties.getDbPort());
		dbTO.setUsername(dsProperties.getUsername());
		
		return "tiles.connectDB";
	}

	@RequestMapping("connect")
	@ResponseBody
	public String connect(DBTO dbTO, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			DataSourceProperties dsProperties = new DataSourceProperties();
			
			dsProperties.setDbHost(dbTO.getDbHost());
			dsProperties.setDbName(dbTO.getDbName());
			dsProperties.setDbPort(dbTO.getDbPort());
			dsProperties.setUsername(dbTO.getUsername());
			dsProperties.setPassword(dbTO.getPassword());
			dsProperties.setUrl("");
			dsProperties.setDriverClassName("");
			
			try {
				dsProperties.configDataSource();
				service.checkSchema(dsProperties);
				service.checkDefaultRecords(dsProperties);
			//	PruvoUtils.redirectPage(request, response, "/Login/logout");
			}
			catch (Exception x) {
				setErrorMessage(request, x.getMessage());
			}
		}
		catch (Exception x) {
			throw new FiAjaxException(x);
		}

		return getEmptyJson();
	}

	private void setErrorMessage(HttpServletRequest request, String msg) {
		setMessageError(request, "Não foi possível conectar. Verifique os parametros de conexão. " + msg);
	}
	
	@ModelAttribute("DBTO")
	public DBTO getDBTO() {		
		return dbTO;
	}
}
