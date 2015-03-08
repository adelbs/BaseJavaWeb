
<style type="text/css">

	#divMenuMobile {
		font-family: verdana;
		font-size: 13px;
		background-color: #DFDFDF;
		height: 20px;
		padding: 5px;
		cursor: pointer;
	}

	#main-menu {
		position:relative;
		width:auto;
	}
	
</style>

<script type="text/javascript">
	
	var showingMenu = false;
	$(function() {
		
		$('#main-menu').smartmenus({
			subMenusSubOffsetX: 1,
			subMenusSubOffsetY: -8,
			showOnClick: true
		});

		$("#divMenuMobile").click(function(){
			if(showingMenu){
				showingMenu = false;
				$("#main-menu").hide();
			}
			else{
				showingMenu = true;
				$("#main-menu").show();
			}
		});
				
	});
	
</script>

<div id="divMenuMobile" style="display: none;"><img class="notificationSignal" style="vertical-align: middle;" src="../../img/alert/greenSignal.png" width="16px" />&nbsp;&nbsp;Menu</div>

<ul id="main-menu" class="sm sm-mint" style="display: none;">
	<li id="menuMensagensMobile" style="display: none;"></li>
	<li onclick="openUrl('../Tasks/list');"><a href="#">Home</a></li>
	<li><a href="#">WebService</a>
		<ul>
			<li onclick="openUrl('../Agendamento/list');"><a href="#">Agendamento</a></li>
			<li onclick="openUrl('../Script/list');"><a href="#">Scripts</a></li>
		</ul>
	</li>
	<li><a href="#">Usuários</a>
		<ul>
			<li onclick="openUrl('../Usuario/changepwd');"><a href="#">Alterar senha</a></li>

			<div class="titleMenu">&nbsp;&nbsp;Usuários</div>
			<li onclick="openUrl('../GrupoUsuarios/list');"><a href="#">Grupo de Usuários</a></li>
			<li onclick="openUrl('../Usuario/list');"><a href="#">Usuários</a></li>
			<li onclick="openUrl('../Permissionamento/edit');"><a href="#">Permissionamento</a></li>
			<li onclick="openUrl('../Funcionalidades/list');"><a href="#">Funcionalidades</a></li>
		</ul>
	</li>
	<li><a href="#">Cadastros</a>
		<ul>
			<li onclick="openUrl('../UnidadeDeMedida/list');"><a href="#">Unidade de Medida</a></li>
			<li onclick="openUrl('../TipoComponente/list');"><a href="#">Tipo de Componente</a></li>
			<li onclick="openUrl('../Componente/list');"><a href="#">Componentes</a></li>
			<li onclick="openUrl('../Plataforma/list');"><a href="#">Plataformas</a></li>
		</ul>
	</li>

	<li onclick="openUrl('../Login/logout');"><a href="#">Sair</a></li>
</ul>
