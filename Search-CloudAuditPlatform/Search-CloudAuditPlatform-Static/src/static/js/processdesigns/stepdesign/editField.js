search.parse();
var datagrid = search.get("datagrid");

var gStepId = null;
var gExData = null;

var loadData = [];

function datagriddrawcell(e){
	if(e.column.field == "action"){
		var show = false;
		$.each(loadData,function(i,v){
			if(e.record.tableName == v){
				show = true;
			}
		});
		if(show){
			e.html = "<select class=\"tableSelect\">" +
			"<option value=\"0\">只读</option>" +
			"<option value=\""+e.record.tableName+"\" selected=\"selected\">编辑</option>" +
			"</select>";
		}else{
			e.html = "<select class=\"tableSelect\">" +
			"<option value=\"0\" selected=\"selected\">只读</option>" +
			"<option value=\""+e.record.tableName+"\">编辑</option>" +
			"</select>";
		}
	}
}

function pageload(stepId,exdata){
	datagrid.url = CTX + "/communal/fields";
	datagrid.load({"tables":exdata.tables});
}

function initPage(stepId,exdata){
	$.post({
		url:CTX + "/stepdesign/gtsfrs",
		data:{"stepid":stepId,"pfrefid":exdata.pfrefid},
		success:function(e){
			if(e.status){
				loadData = e.result;
				pageload(stepId,exdata);
				gStepId = stepId;
				gExData = exdata;
			}else{
				top.search.warn({content:"系统错误!"});
			}
		}
	});
}

function getSelect(){
	var fieldnames = "";
	$.each($("body").find(".tableSelect"),function(i,v){
		var value = $(v).find("option:selected").val();
		if(value != 0){
			if(fieldnames.length > 0){
				fieldnames += ","+value;
			}else{
				fieldnames = value;
			}
		}
	});
	return fieldnames;
}

function closeClick(){
	var fieldnames = getSelect();
	$.post({
		url:CTX+"/stepdesign/utsfrs",
		data:{
			"processid":gExData.processid,
			"funcid":gExData.funcid,
			"stepid":gStepId,
			"pfrefid":gExData.pfrefid,
			"fieldnames":fieldnames
		},
		success:function(e){
			if(e.status){
				top.search.info({content:"操作成功!",funl:function(){
					window.closeWindow();
				}});
			}else{
				top.search.warn({content:"系统错误!"});
			}
		}
	});
}
