search.parse();

var grid = search.get("datagrid");

// 页面加载完成执行
$(function() {

	if (type != 1) {// 管理员
		grid.url = CTX + "/user/findPageUsers";
		grid.load({ state : __state });
	} else {
		var orgs = search.get("orgs");
		orgs.url = CTX + "/orgs/orgaudit";
		orgs.load();
	}
})

function onvalue(e) {
	var orgs = search.get("orgs");
	if (orgs.getValue() != "") {
		grid.url = CTX + "/user/findPageUsers";
		grid.load({ state : __state, orgid : orgs.getValue() });
	}
	// var orgid = orgs.getValue();
	// var orgsid = orgid == "" ? osid : orgid;
	// if(orgid != "" || osid != ""){
	//		
	// //添加功能到对象数组
	// var funarr = new Array();
	// //将已勾选的数据存入数组
	// $.each($('input:checkbox:checked'),function(i){
	// funarr[i] = $(this).val();
	// });
	//		
	// $.post(CTX +"/user/getFunctions", {orgid : orgsid}, function(result) {//
	// 保存
	// if (result.status) {
	// search.remove("datagrid",true);
	// var str = "<div property='columns'> <div field='sname' name = 'sname'
	// width='50' headAlign='center' textAlign='center'
	// allowSort='true'>用户名称</div>";
	// for(var i =0;i<result.result.length;i++){
	// str += "<div field='fid_"+result.result[i].sid+"' width='150'
	// headAlign='center' textAlign='center'
	// allowSort='true'>"+result.result[i].sname+"</div> ";
	// }
	// str += "</div>"
	// $("#datagrid").empty().append($(str));
	// search.parse();
	// var datagrid = search.get("datagrid");
	// datagrid.url = CTX + "/user/getUserAndFunctions";
	// datagrid.load({orgid : orgsid});
	// } else {
	// top.search.error({
	// content : "系统错误!"
	// });
	// }
	// })
	//  	
	//      	
	// if(funarr.length !=0){
	// var val = e.before == "" ? osid : e.before;
	// funjson.array1[funjson.array1.length]={"orgid":val,"funarr":funarr};//
	// 数组追加一个元素
	// }
	//	    
	//		
	// }
}

// 格式化表格内容
function datagriddrawcell(e) {
	// 姓名
	if (e.column.field == "sname" && e.record[e.column.field]) {
		if (__state == 1) {
			if (_all_quick_map.get(e.column.field) == "") {
				e.html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>" + e.record.sname + "</span>";
			} else {
				if (_quick_map.get(e.column.field) == "") {
					e.html = "<span >" + e.record.sname + "</span>";
				} else {
					e.html = "<span style='color: blue;' onclick='" + _quick_map.get(e.column.field) + "(\"" + e.record.sid + "\");'>"
							+ e.record.sname + "</span>";
				}
			}
		} else if (__state == 2) {
			e.html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>" + e.record.sname + "</span>";
		}
	}
	// 毕业时间
	if (e.column.field == "ldgraduationdate" && e.record[e.column.field]) {
		e.html = Globle.fun.formatDate(e.record[e.column.field]);
	}
	// 出生年月日
	if (e.column.field == "ldbirthday" && e.record[e.column.field]) {
		e.html = Globle.fun.formatDate(e.record[e.column.field]);
	}
	// 性别
	if (e.column.field == "igender" && e.record[e.column.field] != "") {
		$.each(Globle.constant.sex, function() {
			if (this.id == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 用户类型
	if (e.column.field == "itype" && e.record[e.column.field] != "") {
		e.html = e.record.smanagerid ? "管理员" : "普通用户";
	}
	// 操作
	if (e.column.field == "oper") {
		var html = "";
		if (__state == 1) {
			if (tailButList && tailButList.length > 0) {
				$.each(tailButList, function() {
					html += ' <a class="' + this.sicon + '" href="javascript:void(0);" onclick="' + this.spcmethod + '(\'' + e.record.sid + '\');">'
							+ this.sname + '</a>'
				})
			}
			if (!e.record.smanagerid) {
				html += ' <a class="btn-enable" href="javascript:void(0);" onclick="upManager(\'' + e.record.sid + '\');">升为管理员</a>'
			}
			html += ' <a class="btn-enable" href="javascript:void(0);" onclick="shiftuser(\'' + e.record.sid + '\');">切换</a>';
		} else if (__state == 2) {
			html += '<a class="del" href="javascript:void(0);" onclick="delData(\'' + e.record.sid + '\');">删除</a>'
					+ ' <a class="btn-enable" href="javascript:void(0);" onclick="restData(\'' + e.record.sid + '\', \'' + e.record.orgstate
					+ '\');">恢复</a>';
		}
		e.html = html;
	}
}

function shiftuser(userid){
	top.location.href=CTX+"/login?userId="+userid; 
}

// 新增
function add() {
	top.search.popDialog({ url : CTX + "/user/goUsersEditPage", title : "新增用户", width : 840, height : 550, onload : function(window) {
		window.setData({ action : "add" });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} });
}

// 编辑
function edit(params) {
	var id = "";
	if (!params) {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			search.warn({ content : "请选择一条数据!" });
			return;
		}
		id = grid.getValue();
	} else {
		if (typeof params === 'object')
			id = params.origin.record.sid;
		else
			id = params;
	}
	top.search.popDialog({ url : CTX + "/user/goUsersEditPage", title : "编辑用户", width : 840, height : 550, onload : function(window) {
		window.setData({ action : "edit", sid : id });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} })
}

// 删除
function delData(params) {
	var id = "";
	if (!params) {
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
	} else {
		if (typeof params === 'object')
			id = params.origin.record.sid;
		else
			id = params;
	}
	top.search.confirm({ content : "确定要删除？", funl : function() {
		$.post(CTX + "/user/updateUsersStateById", { ids : id, state : 99 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.msg });
			}
			grid.reload();
		})
	} })
}

// 启用
function restData(params, orgstate) {
	var id = "";
	if (!params) {
		var selects = grid.getSelecteds();
		var ispass = true, msg = "请选择一条或多条禁用状态的数据！";
		if (selects && selects.length > 0) {
			$.each(selects, function() {
				if (this.record.istate != 2) {
					ispass = false;
					return;
				}

				if (this.record.orgstate != 1) {
					ispass = false;
					msg = "该用户机构未启用，无法恢复！";
					return;
				}
			})
		} else {
			ispass = false;
		}

		if (!ispass) {
			top.search.warn({ content : msg });
			return;
		}
		id = grid.getValue();
	} else {
		if (typeof params === 'object') {
			if (params.origin.record.orgstate != 1) {
				top.search.warn({ content : "该用户机构未启用，无法恢复！" });
				return;
			}
			id = params.origin.record.sid;
		} else {
			if (orgstate != 1) {
				top.search.warn({ content : "该用户机构未启用，无法恢复！" });
				return;
			}
			id = params;
		}
	}
	top.search.confirm({ content : "确定要启用？", funl : function() {
		$.post(CTX + "/user/updateUsersStateById", { ids : id, state : 1 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.msg });
			}
			grid.reload();
		})
	} })
}

// 禁用
function disable(params) {
	var id = "";
	if (!params) {
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
	} else {
		if (typeof params === 'object')
			id = params.origin.record.sid;
		else
			id = params;
	}
	top.search.confirm({ content : "确定要禁用？", funl : function() {
		$.post(CTX + "/user/updateUsersStateById", { ids : id, state : 2 }, function(result) {
			if (result.status) {
				top.search.info({ content : result.msg });
			}
			grid.reload();
		})
	} })
}

// 用户授权
function authorization() {
	var selects = grid.getSelecteds();
	var soid = "";
	if (type != 1) {// 管理员
		soid = userorgid;
	} else {
		var orgs = search.get("orgs");
		soid = orgs.getValue()
	}
	top.search.popDialog({ url : CTX + "/user/getAuthorizationUser?orgid=" + soid, title : "授权", width : 800, height : 550,
		onload : function(window) {
			window.setData({ action : "edit", sid : "", orgid : soid });
		}, ondestroy : function(result) {
			if (result && result != "cancel")
				grid.reload();
		} })
}


// 回收站
function recycle() {
	var iframe = window.parent.document.getElementById(window.parent.Index.currentDivId + "main_iframe");
	iframe.src = iframe.src + Globle.constant.recycle;
}

// 查看详情
function queryDetail(params) {
	var id = "";
	if (typeof params === 'object')
		id = params.origin.record.sid;
	else
		id = params;
	top.search.popDialog({ url : CTX + "/user/goUsersViewPage", title : "查看用户", width : 800, height : 550, onload : function(window) {
		window.setData({ sid : id });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} })
}

// 展开查询
function doQuery() {
	var devHeight = 0;
	if ($(".develop").css("display") == "none") {
		$(".develop").show();
		devHeight = $(".develop").outerHeight();
	} else {
		$(".develop").hide();
	}
	var toolHeight = $(".mini-tools").outerHeight();
	$(".mini-fit").css("top", (devHeight + toolHeight) + "px");
	search.get("datagrid").doLayout();
}

// 查询
var form = new Form("userParams");
function query() {
	var orgs = search.get("orgs");
	if (type != 1) {// 管理员
		grid.load($.extend({ state : __state }, form.getData()));
	} else {
		grid.load($.extend({ state : __state,orgid : orgs.getValue() }, form.getData()));
	}
}

// 升为管理员
function upManager(sid) {
	var orgs = search.get("orgs"), orgid = null;
	if (orgs) {
		orgid = orgs.getValue();
	}
	top.search.confirm({ content : "确定要升为管理员？", funl : function() {
		$.post(CTX + "/user/updateUsersAdminById", { sid : sid, orgid : orgid }, function(result) {
			if (result.status) {
				top.search.info({ content : result.msg });
			}
			grid.reload();
		})
	} })
}