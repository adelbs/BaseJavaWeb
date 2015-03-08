
function fillExecutionTable(idSelecionar){
	waitMessage("Carregando...");
	callAjax("../Execucao/getListScripts", "", function(data){
		for(i = 0; i < data.resultList.length; i++){
			var row = $("<tr id='trITFL"+ data.resultList[i].id +"' idScript='"+ data.resultList[i].pmCdScriptSCPT.id +"'></tr>");
			var idStatus;
			
			if(data.resultList[i].pmDmStatusSTAT == undefined)
				idStatus = 4;
			else
				idStatus = data.resultList[i].pmDmStatusSTAT.idStatCdStatus;
			
			row.append($("<td>"+ getImgStatus(idStatus) +"</td>"));
			row.append($("<td><input type='hidden' value='"+ data.resultList[i].pmCdScriptSCPT.id +"' />"+ data.resultList[i].pmCdScriptSCPT.scptDsScript +"</td>"));
			$(".scriptsTable tbody").append(row);
		}
	
		$(".scriptsTable tbody tr").click(function(){
			$(".scriptsTable tbody tr").css("background-color", "");
			$(this).css("background-color", "#EDEDED");
			
			clearAll();
			$("#idScptCdScript").val($(this).find("input").val());
			selectedScript = $(this).find("input").val();
			$("#dhExec").val(getCurrentDateStr());
			$("#dhExec").change();
		});

		if (idSelecionar != undefined)
			$("tr[idScript='"+ idSelecionar +"']").click();
		
		waitMessage(null);
	});

	setInterval(function(){updateStatus()}, 5000);
}

function updateStatus(){
	callAjax("../Execucao/getListScripts", "", function(data){
		for(i = 0; i < data.resultList.length; i++){
			$("#trITFL"+ data.resultList[i].id).find("td:nth-child(1)").html(getImgStatus(data.resultList[i].pmDmStatusSTAT.idStatCdStatus));
		}
	});
}

function getImgStatus(idStatus){
	if(idStatus == 1)
		return "<img src='../../img/execucao/running.GIF' />";
	else if(idStatus == 2 || idStatus == 3)
		return "<img src='../../img/execucao/error.gif' />";
	else if(idStatus == 4)
		return "<img src='../../img/execucao/sucess.gif' />";
	else
		return "<img src='../../img/execucao/question.gif' />";
}
