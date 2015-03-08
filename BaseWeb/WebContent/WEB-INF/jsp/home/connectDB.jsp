<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<style>

	input, .txtPWD, select {
		width: 200px;
	}

	label.error {
		width: 300px;
		margin-bottom: 5px;
	}

</style>

<script type="text/javascript">
	$(function() {
		showTitle("<spring:message code='bancodados.title'/>", "<spring:message code='bancodados.subtitle.edit'/>");
		
		centerScreen(430, 480);
		
		setWaitMessage("<spring:message code='bancodados.message.wait'/>");
	});

	function saveAction() {
		showDialog(TYPE_QUESTION, "<spring:message code='bancodados.title'/>", "<spring:message code='bancodados.message.connect'/>", function(){
			$("#dbForm").sumit();
		});
	}
	
	function afterSave(data) {
		openUrl("../Home/load");
	}

</script>

<form:form action="../DB/connect" modelAttribute="DBTO" id="dbForm" name="dbForm">

	<div class="cssTableLabel">Aplicação: </div>
	<div class="cssTableField">
		<form:select path="appType" id="cmbAppType" >
			<form:option value="server">Server</form:option>
			<form:option value="client">Client</form:option>
		</form:select>
	</div>
	<form:hidden path="dbHost" id="dbHost" value="0" />
	<form:hidden path="dbPort" id="dbPort" value="0" />
	<form:hidden path="dbName" id="dbName" value="0" />
	<form:hidden path="username" id="username" value="0" />
	<form:hidden path="password" id="password" value="0" />
	
	<div style="float: right; border: 0px solid; padding: 10px; text-align: right; margin-top: 30px;">
		<button type="submit" class="btnSave"><spring:message code='bancodados.btn.connect'/></button>
	</div>

</form:form>