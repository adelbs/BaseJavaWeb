
var fillTreeTimer;
function loadTreeITMO(divTreeSelector) {
	waitMessage("Carregando...");
	getComponentes(0, $(divTreeSelector));
	fillTreeTimer = setInterval(function(){fillTree(divTreeSelector);}, 1000);
}

function fillTree(divTreeSelector) {
	if (ajaxProcess == 0) {
		clearInterval(fillTreeTimer);
		waitMessage(null);

		$(divTreeSelector).jstree({
			"core" : {"multiple" : true},
			"checkbox" : {
		      "keep_selected_style" : false
		    },
		    "plugins" : [ "checkbox" ]
		});
		
		$(divTreeSelector).show();
	}
}

var ajaxProcess = 0;
function getComponentes(idParent, objAppend){
	ajaxProcess++;

	callAjax("../Componente/getListComponentes", "idParent="+ idParent, function(data){
		var root = $("<ul></ul>");
		
		for(var i = 0; i < data.resultList.length; i++){
			var objComp = data.resultList[i];
			var newItem;
			
			newItem = getNewItem(objComp.pmCdTpcomponenteTPCO.tpcoDsIcone, objComp.id, 0, objComp.compDsComponente);
			getComponentes(objComp.id, newItem);
			getComponenteItemMonitor(objComp.id, newItem);

			root.append(newItem);
		}
		
		objAppend.append(root);
		ajaxProcess--;
	});
}

function getComponenteItemMonitor(idComp, objAppend){
	ajaxProcess++;

	callAjax("../Script/getItemMonitorByComponente", "idComponente="+ idComp, function(data){
		var root = $("<ul></ul>");
		var hasItems = false;
		
		for(var i = 0; i < data.resultList.length; i++){
			var objItmo = data.resultList[i];
			var newItem;
			
			newItem = getNewItem("executeIcon.png", 0, objItmo.id, objItmo.itmoDsItemmonitor);
			root.append(newItem);
			hasItems = true;
		}
		
		if(!hasItems) root = "";
		objAppend.append(root);
		ajaxProcess--;
	});
}

function getNewItem(icon, idComp, idItmo, desc){
	var pathIcon = "data-jstree='{\"icon\":\"../../img/icon/"+ icon +"\"}'"
	var ids = "<input type='hidden' value='"+ idComp +"' class='idComp'/><input type='hidden' value='"+ idItmo +"' class='idItmo'/>";
	return $("<li "+ pathIcon +">"+ ids + desc +"</li>");
}

function printSelectedItens(divTreeSelector) {
	$(divTreeSelector).jstree("open_all");
	$(".jstree-clicked").each(function(){
    	var value = $(this).find(".idItmo").val();
    	var inputItmo = $("<input type='hidden' name='idItmoCdItemmonitor' value='"+ value +"' />");
    	
    	if (value > 0) {
    		$("form").append(inputItmo);
    	}
    });
	$(divTreeSelector).jstree("close_all");
}