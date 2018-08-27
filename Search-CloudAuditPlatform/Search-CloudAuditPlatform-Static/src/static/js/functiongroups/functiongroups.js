search.parse();
var grid = search.get("datagrid");

var isupportproject = search.get("isupportproject");
var yesorno = [{text:"全部",value:null}];
$.each(Globle.constant.YesOrNo, function() {
	yesorno.push({text:this.text,value:this.value});
});
isupportproject.loadData(yesorno);
// 页面加载完成执行
$(function() {
	FunGroup();
})
function FunGroup(){
	grid.url = CTX + "/functiongroups/pageFunctionGroups";
	var str = null;
	if(search.get("sname").getValue()!=null&&search.get("sname").getValue()!=""){
		str = getstr(search.get("sname").getValue());
	}
	grid.load({state : __state,sname:str,isupportproject:isupportproject.getValue()});
}
function getstr(sname){
	if(sname!=null){
		var strlist = sname.split(" ");
		sname = " and (";
		var snamestr = " (";
//		var isupportprojectstr = " or (";
//		var isupportprojectflag = true;
		for(var i = 0;i<strlist.length;i++){
			if(i == 0){
				snamestr += " fg.sname like '%"+strlist[i]+"%' ";
			}else{
				snamestr += " or fg.sname like '%"+strlist[i]+"%' ";
			}
//			var yesorno= Globle.constant.YesOrNo;
//			for(var j = 0;j<yesorno.length;j++){
//				if(yesorno[j].text.indexOf(strlist[i])>=0){
//					if(isupportprojectflag){
//						isupportprojectstr += " fg.isupportproject = "+yesorno[j].value+" ";
//						isupportprojectflag = false;
//					}else{
//						isupportprojectstr += " or fg.isupportproject = "+yesorno[j].value+" ";
//					}
//				}
//			}
		}
		snamestr += ")";
//		isupportprojectstr += ")";
		sname += snamestr;
//		if(isupportprojectstr!=" or ()"){
//			sname += isupportprojectstr;
//		}
		sname += ")";
	}
	return sname;
}
// 格式化表格内容
function datagriddrawcell(e) {
	// 创建时间
	if (e.column.field == "ldtcreatetime" && e.record[e.column.field]) {
		e.html = Globle.fun.format(e.record[e.column.field]);
	}
	if (e.column.field == "isupportproject" && e.record[e.column.field] != "") {
		$.each(Globle.constant.YesOrNo, function() {
			if (this.value == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 状态
	if (e.column.field == "istate" && e.record[e.column.field] != "") {
		$.each(Globle.constant.state, function() {
			if (this.id == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 操作
	if (e.column.field == "oper") {
		if (__state == 1) {
			e.html = ' <a class="btn-disable" href="javascript:void(0);" onclick="disable(\'' + e.record.sid + '\');">禁用</a>';
		}else if (__state == 2) {
			e.html = '<a class="del" href="javascript:void(0);" onclick="delData(\'' + e.record.sid + '\');">删除</a>'
			 + ' <a class="btn-enable" href="javascript:void(0);" onclick="restData(\'' + e.record.sid + '\');">恢复</a>';
		}
	}
	//名称
	if (e.column.field == "sname" && e.record[e.column.field]) {
		if(__state == 1){
    		if(_all_quick_map.get(e.column.field) == "") e.html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	else{
	    		if(_quick_map.get(e.column.field) == "" ) e.html = "<span >"+e.record.sname+"</span>";
	    		else e.html = "<span style='color: blue;' onclick='"+ _quick_map.get(e.column.field)+"(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	}
    	}else if(__state == 2) e.html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	}
}
/**
 * 详情。
 * @returns
 */
function queryDetail(params){
	var id = "";
	if (typeof params === 'object')
		id = params.origin.record.sid;
	else
		id = params;
	var data = {
		url : CTX+"/functiongroups/gofgi",
		title : "详情",
		width : 800,
		height : 550,
		onload : function(window){
			window.setData({sid: id});
		},
		ondestroy : function(){
			FunGroup();
		}
	}
	top.search.popDialog(data);
}

// 新增
function add() {
	top.search.popDialog({ url : CTX + "/functiongroups/goufg", title : "新增功能组", width : 800, height : 550,
		onload : function(window) {
			window.setData({ action : "add" });
		}, ondestroy : function(result) {
			if (result && result != "cancel")
				grid.reload();
		} });
}

// 编辑
function edit(params) {
	var id = "";
	if (typeof params === 'object'){
		id = params.origin.record.sid;
	}else if(params == undefined){
		var selects = grid.getSelecteds();
		var ispass = true;
		if (!selects || selects.length <= 0) {
			ispass = false;
		}
		
		if (!ispass) {
			top.search.warn({ content : "请选择一条或多条数据！" });
			return;
		}
		id = grid.getValue();
	}
	top.search.popDialog({ url : CTX + "/functiongroups/goufg", title : "编辑功能组", width : 800, height : 550,
		onload : function(window) {
			window.setData({ action : "edit", sid : id });
		}, ondestroy : function(result) {
			if (result && result != "cancel")
				grid.reload();
		} })
}

// 删除
function delData(params) {
	var id = "";
	if (typeof params === 'object'){
		id = params.origin.record.sid;
	}else if(params == undefined){
		var selects = grid.getSelecteds();
		var ispass = true;
		if (!selects || selects.length <= 0) {
			ispass = false;
		}
		
		if (!ispass) {
			top.search.warn({ content : "请选择一条或多条数据！" });
			return;
		}
		id = grid.getValue();
	}else{
		id = params;
	}
	top.search.confirm({ content : "确定要删除？", funl : function() {
		$.post(CTX + "/functiongroups/updataFunctionGroupStateById", { ids : id, state : 99 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.msg });
			}
			grid.reload();
		})
	} })
}

// 启用
function restData(params) {
	var id = "";
	if (typeof params === 'object'){
		id = params.origin.record.sid;
	}else if(params == undefined){
		var selects = grid.getSelecteds();
		var ispass = true;
		if (selects && selects.length > 0) {
			$.each(selects, function() {
				if (this.record.istate != 2) {
					ispass = false;
					return;
				}
			})
		} else {
			ispass = false;
		}
		
		if (!ispass) {
			top.search.warn({ content : "请选择一条或多条禁用状态的数据！" });
			return;
		}
		id = grid.getValue();
	}else{
		id = params;
	}
	top.search.confirm({ content : "确定要恢复？", funl : function() {
		$.post(CTX + "/functiongroups/updataFunctionGroupStateById", { ids : id, state : 1 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.msg });
			}
			grid.reload();
		})
	} })
}

// 禁用
function disable(id) {
	if (!id) {
		var selects = grid.getSelecteds();
		var ispass = true;
		if (selects && selects.length > 0) {
			$.each(selects, function() {
				if (this.record.istate != 1) {
					ispass = false;
					return;
				}
			})
		} else {
			ispass = false;
		}
		
		if (!ispass) {
			top.search.warn({ content : "请选择一条或多条启用状态的数据！" });
			return;
		}
		id = grid.getValue();
	}
	top.search.confirm({ content : "确定要禁用？", funl : function() {
		$.post(CTX + "/functiongroups/updataFunctionGroupStateById", { ids : id, state : 2 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.msg });
			}
			grid.reload();
		})
	} })
}

//新增功能
function addfunction() {
	top.search.popDialog({ url : CTX + "/functiongroups/goufgaf", title : "新增功能", width : 800, height : 550,
		onload : function(window) {
			window.setData({ action : "add" });
		}, ondestroy : function(result) {
			if (result && result != "cancel")
				grid.reload();
		} });
}

//回收站
function recycle() {
	var iframe = window.parent.document.getElementById(window.parent.Index.currentDivId + "main_iframe");
	iframe.src = iframe.src + Globle.constant.recycle;
}

function doQuery(){
	query();
}
//展开查询
function query() {
	var devHeight = 0;
	if ($(".develop").css("display") == "none"){
		$(".develop").show();
		devHeight = $(".develop").outerHeight();
	}else{
		$(".develop").hide();
	}
	var toolHeight = $(".mini-tools").outerHeight();
	$(".mini-fit").css("top", (devHeight + toolHeight) + "px");
	search.get("datagrid").doLayout();
}
