search.parse();
var grid = search.get("datagrid");

var istate = search.get("istate");
var querystate = [{text:"全部",value:null}];
querystate.push({text:Globle.constant.state[0].text,value:Globle.constant.state[0].id});
querystate.push({text:Globle.constant.state[2].text,value:Globle.constant.state[2].id});
istate.loadData(querystate);

// 页面加载完成执行
$(function() {
	queryAuditTpls();
})
function queryAuditTpls(){
	grid.url = CTX + "/audittpls/qats";
	grid.load({sname:search.get("sname").getValue(),istate:istate.getValue(),state : __state});
}
// 格式化表格内容
function datagriddrawcell(e) {
	// 状态
	if (e.column.field == "istate" && e.record[e.column.field] != "") {
		$.each(Globle.constant.state, function() {
			if (this.id == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 模版类型
	if (e.column.field == "itype" && e.record[e.column.field] != "") {
		$.each(Globle.constant.TplsType, function() {
			if (this.value == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 操作
	if (e.column.field == "oper") {
		if (__state == 1) {
			e.html = ' <a class="btn-disable" href="javascript:void(0);" onclick="disable(\'' + e.record.sid + '\');">禁用</a>'
			+ ' <a class="btn-enable" href="javascript:void(0);" onclick="enablepd(\'' + e.record.sid + '\','+e.record.istate+',\'' + e.record.sfromorgid + '\');">启用</a>';
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
		url : CTX+"/audittpls/gati",
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
	top.search.popDialog({ url : CTX + "/audittpls/guat", title : "新增送审模版", width : 800, height : 550,
		onload : function(window) {
			window.setData({ action : "add" });
		}, ondestroy : function(result) {
			grid.reload();
		} });
}

// 编辑
function edit(params) {
	var id= "";
	if (typeof params === 'object'){
		id = params.origin.record.sid;
	}
	if(params.origin.record.istate!=3){
		search.warn({content:"请选择一条设计状态的数据!"});
		return;
	}
	top.search.popDialog({ url : CTX + "/audittpls/guat", title : "编辑送审模版", width : 800, height : 550,
		onload : function(window) {
			window.setData({ action : "edit", sid : id });
		}, ondestroy : function(result) {
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
			top.search.warn({ content : "请选择一条禁用状态的数据！" });
			return;
		}
		id = grid.getValue();
	}else{
		id = params;
	}
	top.search.confirm({ content : "确定要删除？", funl : function() {
		$.post(CTX + "/audittpls/uati", { sid : id, istate : 99 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.result.meg });
			}
			grid.reload();
		})
	} })
}

// 恢复
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
			top.search.warn({ content : "请选择一条禁用状态的数据！" });
			return;
		}
		id = grid.getValue();
	}else{
		id = params;
	}
	top.search.confirm({ content : "确定要恢复？", funl : function() {
		$.post(CTX + "/audittpls/uati", { sid : id, istate : 3 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.result.meg });
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
		id = grid.getValue();
	}
	top.search.confirm({ content : "确定要禁用？", funl : function() {
		$.post(CTX + "/audittpls/uati", { sid : id, istate : 2 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.result.meg });
			}
			grid.reload();
		})
	} })
}

//启用
function enablepd(id,istate,sfromorgid) {
	if(istate==1){
		search.warn({content:"请选择一条设计状态的数据!"});
		return;
	}
	top.search.confirm({ content : "确定要启用？", funl : function() {
		$.post(CTX + "/audittpls/uati", { sid : id, istate : 1 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.result.meg });
			}
			grid.reload();
		})
	} })
}
//启用
function enable(params) {
	id=""
	if (typeof params === 'object'){
		id = params.origin.record.sid;
	}
	if(params.origin.record.istate==1){
		search.warn({content:"请选择一条设计状态的数据!"});
		return;
	}
	top.search.confirm({ content : "确定要启用？", funl : function() {
		$.post(CTX + "/audittpls/uati", { sid : id, istate : 1 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.result.meg });
			}
			grid.reload();
		})
	} })
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
