
<script type="text/javascript">
	$(function() {
	});

</script>

<div id="divNotificationArea" style="float: right; width: 360px; height: 32px; margin-left: 5px; border: #CDCDCD 1px solid;">
	<div id="divUserVersion" style="float: left;">
		<div class="divStatusTitle">Usuário:</div>
		<div class="divStatusValue">
			${sessionScope.PmCdUsuarioUSUA.usuaDsNome}
			<input type="hidden" id="idUsuarioLogado" value="${sessionScope.PmCdUsuarioUSUA.idUsuaCdUsuario}" />
		</div>
		<div class="divStatusTitle">Versão:</div>
		<div class="divStatusValue">1.12.3</div>
	</div>
</div>