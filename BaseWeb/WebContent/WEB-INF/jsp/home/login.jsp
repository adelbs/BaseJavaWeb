<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<style>

	.cssTableField input[type="text"], .cssTableField input[type="password"] {
		width: 200px;
	}

</style>

<script type="text/javascript">
	$(function() {
		showTitle("Login", "Informe usuário e senha para entrar no sistema.");
		
		centerScreen(290, 400);
		
		$("#capsLockAtivo").hide()
		
		$("#usuaDsPwd").keydown(function (e){
			if(e.keyCode == 13 || e.which == 13){
				$("#usuarioForm").submit();
			}
		});
		
		$("#usuaDsLogin").focus();
		
		$("#usuaDsPwd").keypress(function(e) {
			kc = e.keyCode?e.keyCode:e.which;
			sk = e.shiftKey?e.shiftKey:((kc == 16)?true:false);
			if(((kc >= 65 && kc <= 90) && !sk)||((kc >= 97 && kc <= 122) && sk))
			{
				$("#capsLockAtivo").show()
			} 
			else
				$("#capsLockAtivo").hide()
			}
		);
	});
	
	function beforeSave(){
		setWaitMessage("Conectando...");
	}
	
	function afterSave(data) {
		openUrl("../Home/load");
	}
	
</script>

<form:form action="../Login/login" modelAttribute="usuarioTO" id="usuarioForm" name="usuarioForm">

	<div style="float: left; border: 0px solid; width: 90%; height: 55px; padding: 10px;">
		<div class="cssTableLabel">Login: </div>
		<div class="cssTableField"><form:input path="entity.usuaDsLogin" id="usuaDsLogin" maxlength="10" cssClass="required" title="Informe o Login" /></div>
		<div class="cssTableLabel">Password: </div>
		<div class="cssTableField"><form:password path="entity.usuaDsPwd" id="usuaDsPwd" maxlength="10" cssClass="required" title="Informe a Senha" /></div>
		<div class="cssTableField" id="capsLockAtivo"><Label style="color:red; margin-left: 50px">Caps Lock está ativo</Label></div>
	</div>
	
	<div style="float: left; border: 0px solid; width: 290px; padding: 10px; text-align: right; margin: 50px;">
		<button type="submit" class="btnLogin">Login</button>
	</div>

</form:form>