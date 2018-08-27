search.parse();
grid = search.get("orgGrid");
var iisdepartment = search.get("iisdepartment");
var itype = search.get("itype");
var sname = search.get("sname");
var orgtype = [{text:"全部",value:null}];
$.each(Globle.constant.OrgType, function() {
	orgtype.push({text:this.text,value:this.value});
});
var yesorno = [{text:"全部",value:null}];
$.each(Globle.constant.YesOrNo, function() {
	yesorno.push({text:this.text,value:this.value});
});
iisdepartment.loadData(yesorno);
itype.loadData(orgtype);
function OrgsMgrHandle(){
	grid.url = CTX + "/orgs/qorgs";
	var str = null;
	if(sname.getValue()!=null&&sname.getValue()!=""){
		str = getstr(sname.getValue());
	}
	grid.load({state : __state,iisdepartment2:iisdepartment.getValue(),itype:itype.getValue(),sname:str});
}
function getstr(sname){
	if(sname!=null){
		var strlist = sname.split(" ");
		sname = " and (";
		var snamestr = " (";
//		var itypestr = " or (";
//		var iisdepartmentstr = " or (";
//		var ityptflag = true;
//		var iisdepartmentflag = true;
		for(var i = 0;i<strlist.length;i++){
			if(i == 0){
				snamestr += " o.sname like '%"+strlist[i]+"%' ";
			}else{
				snamestr += " or o.sname like '%"+strlist[i]+"%' ";
			}
//			var type= Globle.constant.OrgType;
//			for(var j = 0;j<type.length;j++){
//				if(type[j].text.indexOf(strlist[i])>=0){
//					if(ityptflag){
//						itypestr += " o.itype like '%"+type[j].value+"%' ";
//						ityptflag = false;
//					}else{
//						itypestr += " or o.itype like '%"+type[j].value+"%' ";
//					}
//				}
//			}
//			var yesorno= Globle.constant.YesOrNo;
//			for(var j = 0;j<yesorno.length;j++){
//				if(yesorno[j].text.indexOf(strlist[i])>=0){
//					if(iisdepartmentflag){
//						iisdepartmentstr += " o.iisdepartment = "+yesorno[j].value+" ";
//						iisdepartmentflag = false;
//					}else{
//						iisdepartmentstr += " or o.iisdepartment = "+yesorno[j].value+" ";
//					}
//				}
//			}
		}
		snamestr += ")";
//		itypestr += ")";
//		iisdepartmentstr += ")";
		sname += snamestr;
//		if(itypestr!=" or ()"){
//			sname += itypestr;
//		}
//		if(iisdepartmentstr!=" or ()"){
//			sname += iisdepartmentstr;
//		}
		sname += ")";
	}
	return sname;
}
/**
 * 授权。
 * @returns
 */
function authorization(){
	if(__usertype == Globle.constant.userTypes[0].id){
		search.warn({content:"admin用户不能批量操作管理员权限!"});
		return;
	}
	var data = {
		url : CTX+"/orgs/gabo",
		title : "授权",
		width : 800,
		height : 550,
		onload : function(window){
			window.setData();
		},
		ondestroy : function(){
			initalQuery();
		}
	}
	top.search.popDialog(data);
}
/**
 * 新增。
 * @returns
 */
function add(){
	var select = grid.getSelected();
	if(select){
		if(select.record.iisdepartment==Globle.constant.YesOrNo[0].value){
			search.warn({content:"部门无法新增子机构!"});
			return;
		}
		var data = {
			url : CTX+"/orgs/guorg?orgid="+select.record.sid,
			title : "新增",
			width : 800,
			height : 550,
			onload : function(window){
				window.setData({action:"add", sid: select.record.sid});
			},
			ondestroy : function(){
				initalQuery();
			}
		}
	}else{
		var data = {
			url : CTX+"/orgs/guorg",
			title : "新增",
			width : 800,
			height : 550,
			onload : function(window){
				window.setData({action:"add", sid: null});
			},
			ondestroy : function(){
				initalQuery();
			}
		}
	}
	top.search.popDialog(data);
}

/**
 * 编辑。
 * @returns
 */
function edit(params){
	var sid = params.origin.record.sid;
	var isedit = params.origin.record.isedit;
	if(isedit==Globle.constant.YesOrNo[1].value){
		search.warn({content:"只能修改用户所属机构及部门!"});
		return;
	}
	var data = {
		url : CTX+"/orgs/guorg?orgid="+sid,
		title : "编辑",
		width : 800,
		height : 550,
		onload : function(window){
			window.setData({action:"edit", sid: sid});
		},
		ondestroy : function(){
			initalQuery();
		}
	}
	top.search.popDialog(data);
}
/**
 * 查看用户。
 * @returns
 */
function usersinfo(params){
	var sid = params.origin.record.sid;
	if (params.origin.record.iisdepartment==Globle.constant.YesOrNo[0].value) {
		top.search.warn({ content : "无法查看部门用户！" });
		return;
	}
	var data = {
		url : CTX+"/orgs/gou",
		title : "查看用户",
		width : 800,
		height : 550,
		onload : function(window){
			window.setData({ sid: sid});
		},
		ondestroy : function(){
			initalQuery();
		}
	}
	top.search.popDialog(data);
}
/**
 * 编辑org。
 * @returns
 */
function editorg(sid,isedit){
	if(isedit==Globle.constant.YesOrNo[1].value){
		search.warn({content:"只能修改用户所属机构及部门!"});
		return;
	}
	var data = {
		url : CTX+"/orgs/guorg?orgid="+sid,
		title : "编辑",
		width : 800,
		height : 550,
		onload : function(window){
			window.setData({action:"edit", sid: sid});
		},
		ondestroy : function(){
			initalQuery();
		}
	}
	top.search.popDialog(data);
}
/**
 * 查看资质。
 * @returns
 */
function lookqua(sid){
	var data = {
		url : CTX+"/orgs/lookqua",
		title : "查看资质",
		width : 800,
		height : 550,
		onload : function(window){
			window.setData({ sid: sid});
		},
		ondestroy : function(){
			initalQuery();
		}
	}
	top.search.popDialog(data);
}
/**
 * 删除
 */
function delData(params){
	var id = "";
	if (typeof params === 'object'){
		id = params.origin.record.sid;
	}else{
		var select = grid.getSelected();
		if(!select){
			search.warn({content:"请选择一条数据!"});
			return;
		}
		id = select.record.sid;
	}
	top.search.confirm({ content : "确定要删除？", funl : function() {
		$.ajax({
			url : CTX+"/orgs/eodo",
			data : {sid: id, istate: 99},
			success : function(result){
				if(result.status){
					search.info({content:result.result.meg,funl:function(){
						initalQuery();
					}});
				}else search.error({content:"系统错误!"});
			}
		});
	}})
}
/**
 * 删除org
 */
function delorg(sid,istate){
	if(istate == 1){
		search.warn({content:"请选择一条禁用数据!"});
		return;
	}
	top.search.confirm({ content : "确定要删除？", funl : function() {
		$.ajax({
			url : CTX+"/orgs/eodo",
			data : {sid: sid, istate: 99},
			success : function(result){
				if(result.status){
					search.info({content:result.result.meg,funl:function(){
						initalQuery();
					}});
				}else search.error({content:"系统错误!"});
			}
		});
	}})
}
/**
 * 启用
 */
function restData(params){
	var id = "";
	if (typeof params === 'object'){
		id = params.origin.record.sid;
	}else{
		var select = grid.getSelected();
		if(!select){
			search.warn({content:"请选择一条数据!"});
			return;
		}
		id = select.record.sid;
	}
	top.search.confirm({ content : "确定要恢复？", funl : function() {
		$.ajax({
			url : CTX+"/orgs/eodo",
			data : {sid: id, istate: 1},
			success : function(result){
				if(result.status){
					search.info({content:result.result.meg,funl:function(){
						initalQuery();
					}});
				}else search.error({content:"系统错误!"});
			}
		});
	}})
}
/**
 * 启用org
 */
function enableorg(sid,istate){
	if(istate == 1){
		search.warn({content:"请选择一条禁用数据!"});
		return;
	}
	top.search.confirm({ content : "确定要恢复？", funl : function() {
		$.ajax({
			url : CTX+"/orgs/eodo",
			data : {sid: sid, istate: 1},
			success : function(result){
				if(result.status){
					search.info({content:result.result.meg,funl:function(){
						initalQuery();
					}});
				}else search.error({content:"系统错误!"});
			}
		});
	}})
}
/**
 * 禁用
 */
function disable(){
	var select = grid.getSelected();
	if(!select){
		search.warn({content:"请选择一条启用数据!"});
		return;
	}
	if(select.record.istate == 2){
		search.warn({content:"请选择一条启用数据!"});
		return;
	}
	top.search.confirm({ content : "确定要禁用？", funl : function() {
		$.ajax({
			url : CTX+"/orgs/eodo",
			data : {sid: select.record.sid, istate: 2},
			success : function(result){
				if(result.status){
					search.info({content:result.result.meg,funl:function(){
						initalQuery();
					}});
				}else search.error({content:"系统错误!"});
			}
		});
	}})
}
/**
 * 禁用org
 */
function disableorg(sid,istate){
	if(istate == 2){
		search.warn({content:"请选择一条启用数据!"});
		return;
	}
	top.search.confirm({ content : "确定要禁用？", funl : function() {
		$.ajax({
			url : CTX+"/orgs/eodo",
			data : {sid: sid, istate: 2},
			success : function(result){
				if(result.status){
					search.info({content:result.result.meg,funl:function(){
						initalQuery();
					}});
				}else search.error({content:"系统错误!"});
			}
		});
	}})
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
		url : CTX+"/orgs/glorg",
		title : "详情",
		width : 800,
		height : 550,
		onload : function(window){
			window.setData({sid: id});
		},
		ondestroy : function(){
			initalQuery();
		}
	}
	top.search.popDialog(data);
}
/**
 * 初始化查询。
 * @returns
 */
function initalQuery(){
	OrgsMgrHandle();
}

/**
 * 绘制单元格。
 * @param e
 * @returns
 */
function ondrawcell(e){
	var html = "";
	switch (e.column.field) {
		case "sname":
			if(__state == 1){
	    		if(_all_quick_map.get(e.column.field) == "") html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
		    	else{
		    		if(_quick_map.get(e.column.field) == "" ) html = "<span >"+e.record.sname+"</span>";
		    		else html = "<span style='color: blue;' onclick='"+ _quick_map.get(e.column.field)+"(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
		    	}
	    	}else if(__state == 2) html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	break;
		case "itype":
			var text = "";
			var orgtype = e.record.itype.split(",");
			var type= Globle.constant.OrgType;
			var orgitype = "";
			for(var i = 0;i<orgtype.length;i++){
				for(var j = 0 ; j < type.length; j++){
					if(orgtype[i]==type[j].value){
						if(i==0){
							orgitype += type[j].text;
						}else{
							orgitype += ","+type[j].text;
						}
					}
				}
			}
//			for(var i = 0 ; i < itype.length; i++){
//				if(e.record.itype == itype[i].value) text = itype[i].text;
//			}
			html = orgitype;
			break;
		case "iisdepartment":
			var itype= Globle.constant.YesOrNo;
			var text = "";
			for(var i = 0 ; i < itype.length; i++){
				if(e.record.iisdepartment == itype[i].value) text = itype[i].text;
			}
			html = text;
			break;
		case "isupportshow":
			var itype= Globle.constant.YesOrNo;
			var text = "";
			for(var i = 0 ; i < itype.length; i++){
				if(e.record.isupportshow == itype[i].value) text = itype[i].text;
			}
			html = text;
			break;
		case "isupportleaf":
			var itype= Globle.constant.YesOrNo;
			var text = "";
			for(var i = 0 ; i < itype.length; i++){
				if(e.record.isupportleaf == itype[i].value) text = itype[i].text;
			}
			html = text;
			break;
		case "istate":
			var itype= Globle.constant.funcState;
			var text = "";
			for(var i = 0 ; i < itype.length; i++){
				if(e.record.istate == itype[i].value) text = itype[i].text;
			}
			html = text;
			break;
		case "lusernumber":
			var itype= Globle.constant.UserNumber;
			var text = "";
			for(var i = 0 ; i < itype.length; i++){
				if(e.record.lusernumber == itype[i].value) text = itype[i].text;
			}
			html = text;
			break;
		case "oper":
			var str = "";
			if (__state == 1) {
				str = "<a class='edit' href='javascript:void(0)' onclick='editorg(\""+e.record.sid+"\","+e.record.isedit+");'>编辑</a>" +
				"<a class='btn-disable' href='javascript:void(0)' onclick='disableorg(\""+e.record.sid+"\","+e.record.istate+");' style='margin-left:5px;'>禁用</a>";
			}else if (__state == 2) {
				str = "<a class='del' href='javascript:void(0)' onclick='delorg(\""+e.record.sid+"\","+e.record.istate+");' style='margin-left:5px;'>删除</a>" +
				"<a class='btn-enable' href='javascript:void(0)' onclick='enableorg(\""+e.record.sid+"\","+e.record.istate+");' style='margin-left:5px;'>恢复</a>";
			}
			html = str;
			break;
	}
	if(html)e.html = html;
}

//回收站
function recycle() {
	var iframe = window.parent.document.getElementById(window.parent.Index.currentDivId + "main_iframe");
	iframe.src = iframe.src + Globle.constant.recycle;
}

// 返回
//function back() {
//	window.parent.Index.classify2.back();
//}
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
	search.get("orgGrid").doLayout();
}
