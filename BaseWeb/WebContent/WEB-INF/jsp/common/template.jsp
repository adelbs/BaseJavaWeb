<%@ include file="/WEB-INF/jsp/common/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>  

<!DOCTYPE html>
<html>
	<head>
	
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<!-- meta http-equiv="Content-Type" content="text/html; charset=utf-8" / -->
		
		<meta http-equiv="Content-Style-Type" content="text/css">
	
		<title>Obi1 - Financas</title>
		<link rel="shortcut icon" href="../../img/pruvoIcon.png" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0" />

		<!-- JQuery -->
		<script src="../../js/jquery-2.0.3.min.js" type="text/javascript"></script >
		<link href="../../js/jquery-ui-1.10.3/themes/base/jquery-ui.css" rel="stylesheet" />
		<script src="../../js/jquery-ui-1.10.3/ui/jquery-ui.js"></script>

		<!-- DateTimePicker -->
		<link href="../../js/dateTimePicker/jquery.datetimepicker.css" rel="stylesheet" type="text/css" />
		<script src="../../js/dateTimePicker/jquery.datetimepicker.js"></script>
		
		<!-- Menu -->
		<link href='../../js/smartmenus-0.9.6/css/sm-core-css.css' rel='stylesheet' type='text/css' />
		<link href='../../js/smartmenus-0.9.6/css/sm-mint/sm-mint.css' rel='stylesheet' type='text/css' />
		<script src="../../js/smartmenus-0.9.6/jquery.smartmenus.js" type="text/javascript"></script>
		
		<!-- Paginação -->
		<link href='../../js/pagination/simplePagination.css' rel='stylesheet' type='text/css' />
		<script src="../../js/pagination/jquery.simplePagination.js" type="text/javascript"></script>
		
		<!-- Validation -->
		<script src="../../js/jquery-validation-1.11.1/jquery.validate.js" type="text/javascript"></script>
		
		<!-- Loading -->
		<script src="../../js/jquery.blockUI.js"></script>
		
		<!-- Pruvo -->
		<link href="../../css/pruvoMonitor.css" rel="stylesheet" />
		<script src="../../js/pruvoMonitor.js" type="text/javascript"></script>

		<style>
			.divStatusTitle, .divStatusValue {
				float: left; 
				margin-top: 2px; 
				margin-left: 5px; 
				font-family: verdana; 
				font-size: 9px;
			}
		
			.divStatusTitle {
				clear: left;
				font-weight: bold;
				width: 50px;
				text-align: right;
			}
			
		</style>

		<script type="text/javascript">
			var messages = new Array();
			messages["system.dialog.cancelar.title"] = "<spring:message code='system.dialog.cancelar.title'/>";
			messages["system.dialog.cancelar.message"] = "<spring:message code='system.dialog.cancelar.message'/>";
		
			$(function() {
				if($("#msgError")[0].value != ""){
					showDialog(TYPE_ERROR, "Erro!", $("#msgError")[0].value);
				}

				if($("#msgSuccess")[0].value != ""){
					showDialog(TYPE_INFORMATION, "PruvoMonitor", $("#msgSuccess")[0].value);
				}

				maximizeContent();
			//	showDialog(TYPE_INFORMATION, "Titulo 123", "Tem certeza que deseja continuar?", function(){alert("");});
			
				resizeTemplate();
				$(window).resize(function () {resizeTemplate();});
			});
			
			//Movimentando o mennu e área de notificação para funcionar em qualquer dispositivo
			function resizeTemplate(){
				//Area de notificação
				if(window.innerWidth > 1038 && window.innerWidth <= 1177){
					$("#divNotificationArea").show();
					$("#divUserVersion").hide();
					$("#divStatusMsg").show();
					$("#divNotificationSignal").css("margin-left", "40px");
					$("#divNotificationArea").css("width", "220px");
				}
				else if(window.innerWidth > 752 && window.innerWidth <= 1038){
					$("#divNotificationArea").show();
					$("#divUserVersion").hide();
					$("#divStatusMsg").hide();
					$("#divNotificationSignal").css("margin-left", "0px");
					$("#divNotificationArea").css("width", "35px");
				}
				else if(window.innerWidth > 720 && window.innerWidth <= 752){
					$("#divNotificationArea").hide();
				}
				else if(window.innerWidth > 685 && window.innerWidth <= 720){
					$("#divNotificationArea").show();
					$("#divUserVersion").hide();
					$("#divStatusMsg").hide();
					$("#divNotificationSignal").css("margin-left", "0px");
					$("#divNotificationArea").css("width", "35px");
				}
				else if(window.innerWidth <= 685){
					$("#divNotificationArea").hide();
				}
				else{
					$("#divNotificationArea").show();
					$("#divUserVersion").show();
					$("#divStatusMsg").show();
					$("#divNotificationSignal").css("margin-left", "40px");
					$("#divNotificationArea").css("width", "360px");
				}
				
				//Menu
				$("#main-menu").show();
				$("#divMenuMobile").hide();
				$("#menuMensagensMobile").hide();
				if(window.innerWidth > 720 && window.innerWidth <= 850){
					$("#divMenu").css("margin-left", "92px");
					$("#divMenu").css("width", "600px");
				}
				else if(window.innerWidth > 640 && window.innerWidth <= 720){
					$("#divMenu").css("margin-left", "92px");
					$("#divMenu").css("width", "530px");
				}
				else if(window.innerWidth <= 640){
					$("#divMenu").css("margin-left", (window.innerWidth - 230) +"px");
					$("#divMenu").css("width", "220px");
					$("#main-menu").hide();
					$("#divMenuMobile").show();
					$("#menuMensagensMobile").show();
				}
				else{
					$("#divMenu").css("margin-left", "92px");
					$("#divMenu").css("width", "700px");
				}
				
				//document.title = window.innerWidth;
			}
			
		</script>
	</head>

	<body>
		<input type="hidden" value="${msgSuccess}" id="msgSuccess"/>
		<input type="hidden" value="${msgError}" id="msgError"/>

		<div id="divWait" style="display: none;">
			<div style="float: left; padding-top: 15px; margin-left: 20px;">
				<img src="../../img/execucao/running.GIF" width="18px"/>
			</div>
			<div style="float: left; height: 100%; margin-left: 25px;">
				<div style="float: left; margin-top: 7px;">
					Aguarde, por favor...
				</div>
				<div style="float: left; clear: left; font-size: 12px; font-style: italic;">
					[MESSAGE]
				</div>
			</div>
		</div>
	
		<div id="dialogTemplate" title="" style="display: none;">
			<div><img src=""/></div>
			<div>Dialogo parão 1232</div>
			<div style="text-align: right;">
				<button type="button" onclick="" class="btnYes">Sim</button>
				<button type="button" onclick="closeDialog();" class="btnNo">Não</button>
				<button type="button" onclick="closeDialog();" class="btnOk">OK</button>
				<button type="button" onclick="showStackTrace();" class="btnDet">Detalhes &gt;&gt;</button>
			</div>
			<div id="divStackTrace" style="margin-top: 20px; display: none;"><textarea style="width: 100%; font-size: 10px;" rows="10" readonly="readonly" id="txtStackTrace"></textarea></div>
		</div>
		
		<div id="divLogo" style="position: absolute; top: 0px; left: 0px;"><img width="60px" src="../../img/logoCA.png" /></div>
		
		<div id="divMenu" style="float: left; width: 700px; height: 32px; margin-left: 92px;">
			<tiles:insertAttribute name="menu" />
		</div>
		
		<tiles:insertAttribute name="notificationArea" />
		
		<div style="float: left; border: #A9CF46 0px solid; background-color: #CCCCCC; height: 1.5px; margin-top: 5px; width: 100%;"></div>
		
		<div id="divContent" style="float: left; border: #CDCDCD 1px solid; width: 100%; overflow: auto;">
			<div id="divSubContent">
				<div style="margin: 10px; display: block;">
					<div id="divIcon" style="float: left; display: none;"><img src="../../img/pruvoIconSmall.png" width="30" /></div>
					<div id="divTitle" style="float: left; font-family: verdana; font-size: 20px; color: #606060; font-weight: bold; display: none;"></div>
				</div>
				<div id="divSubTitle" style="clear: left; margin-left: 10px; font-family: verdana; font-size: 15px; font-style: italic; display: none;"></div><br/>
				<div style="margin: 10px; font-family: verdana; font-size: 20px;">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div>
	</body>

</html>