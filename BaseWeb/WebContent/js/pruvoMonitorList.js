/**
 * Função que monta todas as tabelas de dados em todas as telas
 */
var paginationDIV;
var resultArray = new Array();
function setDataTable(divTableSelector, data){
	
	//Limpando qualquer resultado existente
	try {
		$(divTableSelector +" table").remove();
		$(divTableSelector +" div").remove();
		$(divTableSelector +" input").remove();
		paginationDIV.destroy();
	} catch(x){ }

	//Adicionando a tabela padrão em branco
	$(divTableSelector).append($("<table class='resultTable'><thead></thead><tbody></tbody></table>"));
	paginationDIV = $("<div style='float: right; margin-top: 10px;'></div>");
	
	var tableConf;
	var tableTO = data.resultTableTO;
	var resultTR;
	var resultTD;
	var resultTableHeader = $(divTableSelector +" table thead");
	var resultTableBody = $(divTableSelector +" table tbody");
	
	if (tbCondigArray[divTableSelector] != undefined)
		tableConf = tbCondigArray[divTableSelector];
	else
		tableConf = new TableConfig();
	
	//Adicionando a linha de cabeçalho na tabela
	resultTableHeader.append($("<tr></tr>"));
	resultTableHeader = $(divTableSelector +" table thead tr");
	
	for (i = 0; i < tableTO.header.length; i++) {
		resultTD = $("<td></td>");
		resultTD.text(tableTO.header[i]);
		resultTableHeader.append(resultTD);
	}
	
	//Adicionando linhas de resultado
	resultArray[divTableSelector] = new Object();
	if (tableTO.values.length == 0) {
		var colspan = tableTO.fields.length;
		resultTR = $("<tr></tr>")
		resultTD = $("<td colspan='"+ colspan +"' style='text-align: center;'>&nbsp;</td>");
		resultTD.html("Nenhum registro encontrado.");
		resultTR.append(resultTD);
		resultTableBody.append(resultTR);
	}
	else {
		for (i = 0; i < tableTO.values.length; i++) {
			resultTR = $("<tr></tr>")
			
			for (j = 0; j < tableTO.fields.length; j++) {
				resultTD = $("<td></td>");
				
				try {
					resultTD.html(eval("tableTO.values["+ i +"]."+ tableTO.fields[j]));
				}
				catch (x) {
					resultTD.html("");
				}

				//Aplicando as configurações
				if (tableConf.tdPropterty[j] != undefined) {
					resultTD.html(tableConf.tdPropterty[j].getValue(resultTD.html()));
					if (tableConf.tdPropterty[j].size != "") resultTD.css("width", tableConf.tdPropterty[j].size);
				}
				
				resultTR.append(resultTD);
			}
			
			resultTR.attr("id", tableTO.values[i].id);
			resultArray[divTableSelector][tableTO.values[i].id] = tableTO.values[i];
			
			resultTR.click(function(){
				tableConf.click(this);
			});
			
			resultTableBody.append(resultTR);
		}
	}
	
	//Configurando paginação
	if (tableTO.paginate) {
		paginationDIV.pagination({
	        items: tableTO.numTotalItems,
	        itemsOnPage: tableTO.maxItemsPage,
	        currentPage: tableTO.currPage,
	        cssStyle: 'compact-theme',
	        prevText: '<',
	        nextText: '>',
	        "onPageClick": function(pageNum, event) {
	        	$(divTableSelector).append($("<input type='hidden' name='resultTable.currPage' value='"+ pageNum +"'  />"));
	        	tableConf.retrieve();
	        }
	    });
		
		$(divTableSelector).append(paginationDIV);
	}
	
	waitMessage(null);
}

function getResultObj(divTableSelector, id) {
	return resultArray[divTableSelector][id];
}

function retrieve(){
	waitMessage("Pesquisando...");
	callAjax($("form").attr("action") +"/retrieve", formSerialize($("form")), function(data){
		setDataTable("#resultTable", data);
	});
}

/**
 * Ação ao clicar no edit de um item de uma lista de resultado
 */
function editAction(row){
	waitMessage("Carregando...");
	$("form").attr("action", $("form").attr("action") + "/edit");
	$(".txtID").val(row.id);
	$("form")[0].submit();
}

/**
 * Ação ao clicar no botão novo, nas telas de lista de resultados
 */
function newAction(){
	var row = new Object();
	row.id = -1;
	editAction(row);
}

/**
 * Ação ao clicar no botão para remover um item da lista de resultado
 */
function removeAction(){
	if($(".txtID").val() != "") {
		showDialog(TYPE_QUESTION, "Excluir", "Tem certeza que deseja excluir este registro? Essa operação não poderá ser desfeita!", function(){
			waitMessage("Carregando...");
			callAjax($("form").attr("action") +"/delete", formSerialize($("form")), function(data){
				document.location = $("form").attr("action") +"/list";
			});
		});
	}
	else {
		showDialog(TYPE_ERROR, "Excluir", "O registro ainda não foi salvo!");
	}
}

/**
 * Função padrão dos botões cancelar
 */
function cancelAction() {
	showDialog(TYPE_QUESTION, messages["system.dialog.cancelar.title"], messages["system.dialog.cancelar.message"], function() {
		waitMessage("Carregando...");
		document.location = $("form").attr("action") +"/list";
	});
}


//#### Classes para propriedades dos datatables

function TableTD() {
	this.size = "";
	this.getValue = function(value) {return value;};
}

function TableConfig() {
	this.tdPropterty = new Array();
	
	this.td = function(colNum, size, getValue) {
		if (this.tdPropterty[colNum] == undefined)
			this.tdPropterty[colNum] = new TableTD();
		
		this.tdPropterty[colNum].size = size;
		if (getValue != undefined) this.tdPropterty[colNum].getValue = getValue;
	};
	
	this.click = function(row) {editAction(row);};
	this.retrieve = function() {retrieve();};
}

var tbCondigArray = new Array();
function addTableConfig(divTableSelector, tbConfig) {
	tbCondigArray[divTableSelector] = tbConfig;
}