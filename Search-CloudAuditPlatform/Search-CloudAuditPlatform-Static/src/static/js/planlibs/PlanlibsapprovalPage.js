search.parse();
var tdg = search.get("planlibsGrid");
var orgid = search.get("orgid");

//新增
function add(){
	$.ajax({ url : CTX + "/processdesigns/checkWhetherOperableProcessDesigns",data : {id : id},
		 success : function(result) {
//			 if(result.state){
				 var data = {
							url : CTX +"/Planlibs/goUpdatePlanlibsPage?id="+id,
							title : "创建",
							width : 1400,
							height : 800,
							onload : function(window){
								
							},
							ondestroy : function(){
								reload();
							}
						}
					top.search.popDialog(data);
//			 }else{
//				 top.search.error({ content : "没有权限操作!" });
//			 }
		 } });
	
}
//编辑
function edit(params){
	if(params){
		var sid = "";
		if(params != null && params != ""){
			if(typeof params === 'object')sid = params.origin.record.sid;
			else sid = params;
		}
		top.search.popDialog({ 
			url : CTX +"/Planlibs/goUpdatePlanlibsPage",
			title : "编辑", 
			width : 1400,
			height : 800, 
			onload : function(window) {
			window.setData({ action : "edit", id : sid});
		}, ondestroy : function(result) {
				reload();
		} });
	}else{
		var select = tdg.getSelected();
		if(select){
			if(select.record.pid != null){
				search.warn({content:"请选择父级!"});
				return;
			}
			top.search.popDialog({ 
				url : CTX +"/Planlibs/goUpdatePlanlibsPage",
				title : "编辑", 
				width : 1400,
				height : 800, 
				onload : function(window) {
				window.setData({ action : "edit", id : select.record.sid});
			}, ondestroy : function(result) {
					reload();
			} });
		}else{
			search.warn({content:"请选择一条数据!"});
		}
	}
	
}

function restData(){
	onState(1);
}
function disable(){
	onState(2);
//		var devHeight = 0;
//		if ($(".develop").css("display") == "none"){
//			$(".develop").show();
//			devHeight = $(".develop").outerHeight();
//		}else{
//			$(".develop").hide();
//		}
//		var toolHeight = $(".mini-tools").outerHeight();
//		$(".mini-fit").css("top", (devHeight + toolHeight) + "px");
//		search.get("dictionariesgrid").doLayout();
	
}

//启用、 禁用
function onState(state,sid) {
	var ids = "";
	var str = "";
	if(state == 1){
		str = "启用";
	}else if (state == 2){
		str = "禁用";
	}
	if(sid){
		ids = sid;
		
		top.search.confirm({ content : "确定要"+str+"吗？", funl : function() {
			$.post(CTX +"/dictionaries/updateState", { id : ids,state : state}, function() {
				tdg.reload();
			})
		} })
	}else{
		var select = tdg.getSelecteds();
		for(var i = 0;i<select.length;i++){
			ids += select[i].record.sid + ",";
		}
		ids = ids.substring(0,ids.length-1);
		if(select.length != 0){ 
			top.search.confirm({ content : "确定要"+str+"吗？", funl : function() {
				$.post(CTX +"/dictionaries/updateState", { id : ids,state : state}, function() {
					tdg.reload();
				})
			} })
		}else{
			search.warn({content:"请选择一条数据!"});
		}
	}
}

//删除
function delData(sid){
	var ids = "";
	if(sid){
		ids = sid;
		top.search.confirm({ content : "确定要删除吗？", funl : function() {
			$.post(CTX +"/dictionaries/updateState", { id : ids,state : 99}, function() {
				tdg.reload();
			})
		} })
	}else{
		var select = tdg.getSelecteds();
		for(var i = 0;i<select.length;i++){
			ids += select[i].record.sid + ",";
		}
		ids = ids.substring(0,ids.length-1);
		if(select){
			top.search.confirm({ content : "确定要删除吗？", funl : function() {
				$.post(CTX +"/dictionaries/updateState", { id : ids,state : 99}, function() {
					tdg.reload();
				})
			} })
		}else{
			search.warn({content:"请选择一条数据!"});
		}
	}
}

function datagriddrawcell(e) {
	if (e.column.field == "sname") {
		if(__state == 1){
    		if(_all_quick_map.get(e.column.field) == "") html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	else{
	    		if(_quick_map.get(e.column.field) == "" ) html = "<span >"+e.record.sname+"</span>";
	    		else html = "<span style='color: blue;' onclick='"+ _quick_map.get(e.column.field)+"(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	}
    	}else if(__state == 2) html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
		
		e.html = html;
	}
	if (e.column.field == "itype") {
		if(e.record.itype==1){
			e.html = "<font>机构资质</font>";
		}else if(e.record.itype==2){
			e.html = "<font >人员资质</font>";
		}else if(e.record.itype==3){
			e.html = "<font>职务</font>";
		}
	}
	if (e.column.field == "istate") {
		e.html = "<font>已完结</font>";
	}
	if(e.column.field == "ldstartdate"){
		e.html = Globle.fun.formatDate(e.record[e.column.field]);
	}
	if(e.column.field == "ldenddate"){
		e.html = Globle.fun.formatDate(e.record[e.column.field]);
	}
//	if (e.column.field == 'action') {
//		if (__state == 1) {
//			e.html = "<font><a class='edit' href=\"javascript:void(0)\" onclick=\"edit(\'" + e.record.sid + "\')\">编辑</a></font>"
//			+"<font style='margin-left: 5px;'><a class='btn-disable' href=\"javascript:void(0)\" onclick=\"onState(2,\'" + e.record.sid + "\')\">禁用</a></font>"
//		} else if (__state == 2) {
//			e.html = '<a class="del" href="javascript:void(0);" onclick="delData(\'' + e.record.sid + '\');">删除</a>'
//			 + " <font style='margin-left: 5px;'><a class='btn-enable' href=\"javascript:void(0)\" onclick=\"onState(1,\'" + e.record.sid + "\')\">恢复</a></font>"
//		}
//	}
}

//查看详情
function queryDetail(params) {
	var sid = "";
	if(params != null && params != ""){
		if(typeof params === 'object')sid = params.origin.record.sid;
		else sid = params;
	}
	top.search.popDialog({ url : CTX + "/Planlibs/goplanView", title : "详情", width : 1400, height : 800,
		onload : function(window) {
			window.setData({ action : "edit",id : sid });
		}, ondestroy : function(result) {
			if (result && result != "cancel")
				grid.reload();
		} })
}

//回收站
function recycle() {
	var iframe = window.parent.document.getElementById(window.parent.Index.currentDivId + "main_iframe");
	iframe.src = iframe.src + Globle.constant.recycle;
}

//返回
function back() {
	window.parent.Index.classify2.back();
}

//"<font style='margin-left: 5px;'><a class='enable' href=\"javascript:void(0)\" onclick=\"onState(1,\'" + e.record.sid + "\')\">启用</a></font>"
/**
 * 重新加载页面数据
 */
function reload(){
	tdg.reload();
}
$(function(){
	tdg.url = CTX + "/Planlibs/getPlanlibsapprovalData";
	tdg.load({state : __state});
});

//展开查询
function doQuery() {
	var devHeight = 0;
	if ($(".develop").css("display") == "none"){
		$(".develop").show();
		devHeight = $(".develop").outerHeight();
	}else{
		$(".develop").hide();
	}
	var toolHeight = $(".mini-tools").outerHeight();
	$(".mini-fit").css("top", (devHeight + toolHeight) + "px");
	search.get("dictionariesgrid").doLayout();
}

// 查询
function searchGrid() {
	tdg.url = CTX +  "/Planlibs/getPlanlibsapprovalData";
	var names = "";
	var keyword = search.get("keyword").getValue();
//	var str = keyword.split(' ');
//	for(var i=0;i<str.length;i++){
//		var itypes = "";
//		for(var j=0;j<itypeData.length;j++){
//			if(itypeData[j].text.indexOf(str[i]) != -1){
//				itypes += itypeData[j].value +",";
//			}
//		}
//		if(itypes != ""){
//			itypes = itypes.substring(0,itypes.length-1);
//			names += itypes + " ";
//		}else{
//			names += str[i]+" ";
//		}
//	}
//	names = names.substring(0,names.length-1);
	var param = {
			keyword : keyword,
			orgid : search.get("orgid").getValue(),
			state : __state
		}
	tdg.load(param);
}