search.parse();
var grid = search.get("datagrid");

var istate = search.get("istate");
var querystate = [{text:"全部",value:null}];
querystate.push({text:Globle.constant.state[0].text,value:Globle.constant.state[0].id});
querystate.push({text:Globle.constant.state[2].text,value:Globle.constant.state[2].id});
istate.loadData(querystate);

// 页面加载完成执行
$(function() {
	ProcessDesigns();
})
function ProcessDesigns(){
	grid.url = CTX + "/processdesigns/qpd";
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
	// 项目相关
	if (e.column.field == "isupportproject" && e.record[e.column.field] != "") {
		$.each(Globle.constant.YesOrNo, function() {
			if (this.value == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 操作
	if (e.column.field == "oper") {
		if (__state == 1) {
			e.html = ' <a class="btn-disable" href="javascript:void(0);" onclick="disable(\'' + e.record.sid + '\');">禁用</a>'
			+ ' <a class="btn-enable" href="javascript:void(0);" onclick="enablepd(\'' + e.record.sid + '\','+e.record.istate+',\'' + e.record.sorgid + '\');">启用</a>';
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
		url : CTX+"/processdesigns/gpdi",
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
	top.search.popDialog({ url : CTX + "/processdesigns/gupd", title : "新增流程设计", width : 800, height : 550,
		onload : function(window) {
			window.setData({ action : "add" });
		}, ondestroy : function(result) {
			grid.reload();
			if(result && result.isnext == "ok"){
				openFlowDesign(result.sid,result.orgid,result.isupportproject,result.sfromorgid);
			}
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
	top.search.popDialog({ url : CTX + "/processdesigns/gupd", title : "编辑流程设计", width : 800, height : 550,
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
		$.post(CTX + "/processdesigns/updi", { sid : id, istate : 99 }, function(result) {
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
		$.post(CTX + "/processdesigns/updi", { sid : id, istate : 3 }, function(result) {
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
		$.post(CTX + "/processdesigns/updi", { sid : id, istate : 2 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.result.meg });
			}
			grid.reload();
		})
	} })
}

//启用
function enablepd(id,istate,sorgid) {
	if(istate==1){
		search.warn({content:"请选择一条设计状态的数据!"});
		return;
	}
	top.search.confirm({ content : "确定要启用？", funl : function() {
		$.post(CTX + "/processdesigns/updi", { sid : id, istate : 1, sorgid : sorgid }, function(result) {
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
		$.post(CTX + "/processdesigns/updi", { sid : id, istate : 1, sorgid : params.origin.record.sorgid }, function(result) {
			if (result.status) {
				top.search.info({ content : result.result.meg });
			}
			grid.reload();
		})
	} })
}

function openFlowDesign(sid,orgid,isupportproject,sfromorgid){
	var dataOrigin = {},invoker = window;
	var win = $(top.window), width = win.width(), height = win.height();
	top.search.popDialog({
		id : "flowDesigner",
		title : "流程设计器",
		url : CTX + "/common/flow",
		width : width,
		height : height,
		modal : false,
		onload : function(window){
			window.loadData(dataOrigin, {invoker : invoker, method : saveData}, {width : 800, height : 600, url : CTX + "/stepdesign/gefr"},{processid:sid,orgid:orgid,isupportproject:isupportproject,sfromorgid:sfromorgid});
		},
		ondestroy : function(result) {
		}
	});
}

//设置步骤
function step() {
	var select = grid.getSelected();
	if(select){
		var dataOrigin = {},invoker = window;
		if(select.record.sjsondata){
			dataOrigin = JSON.parse(select.record.sjsondata);
		}
		var win = $(top.window), width = win.width(), height = win.height();
		top.search.popDialog({
			id : "flowDesigner",
			title : "流程设计器",
			url : CTX + "/common/flow",
			width : width,
			height : height,
			modal : false,
			onload : function(window){
				window.loadData(dataOrigin, {invoker : invoker, method : saveData}, {width : 800, height : 600, url : CTX + "/stepdesign/gefr"},{processid:select.record.sid,orgid:select.record.sorgid,isupportproject:select.record.isupportproject,sfromorgid:select.record.sfromorgid});
			},
			ondestroy : function(result) {
				grid.reload();
			}
		});
	}else{
		search.warn({content:"请选择一条数据!"});
	}
}
//设置步骤后返回保存
function saveData(data,changed,invoker,callback,exdata) {
	$.post(CTX+"/stepdesign/upstep",{processdesignid:exdata.processid,datas:JSON.stringify(data),changes:JSON.stringify(changed)},function(e){
		if(e.status){
			callback.call(invoker,JSON.parse(e.result));
			top.search.info({content:"操作成功!"});
		}else{
			top.search.warn({content:"系统错误!"});
		}
	});
}
//设置流程对应功能
function setFlowFunc(){
	var select = grid.getSelected();
	if(select && select.record.sjsondata){
		var dataOrigin = JSON.parse(select.record.sjsondata);
		var processid = select.record.sid;
		var win = $(top.window), width = win.width(), height = win.height();
		top.search.popDialog({
			id : "flowDesigner",
			title : "流程对应功能",
			url : CTX + "/stepdesign/geffp",
			width : 400,
			height : 200,
			modal : true,
			onload : function(window){
				window.setData({processid:select.record.sid,"isupportproject":select.record.isupportproject});
			},
			ondestroy : function(result) {
				if(result && result.isnext == true){
					top.search.popDialog({
						id : "flowDesigner3",
						title : "流程设计器",
						url : CTX + "/common/flow",
						width : width,
						height : height,
						modal : false,
						onload : function(window){
							window.loadDataFormPermissions(dataOrigin, {width : 800, height : 600, url : CTX + "/stepdesign/gefieldp"}, {"processid":processid,"pfrefid":result.pfrefid,"funcid":result.funcid,"tables":result.tables});
						},
						ondestroy : function(result) {
						}
					});
				}
			}
		});
	}else{
		if(!select.record.sjsondata){
			search.warn({content:"请先设置流程设计!"});
		}else{
			search.warn({content:"请选择一条数据!"});
		}
	}
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
