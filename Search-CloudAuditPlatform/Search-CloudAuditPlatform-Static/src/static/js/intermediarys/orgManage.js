search.parse();

var grid = search.get("datagrid");

var itype = search.get("itype");

// 页面加载完成执行
$(function() {
	grid.url = CTX + "/intermediarys/findOrgManageTree";
	grid.load({ istate : __state, module : _module });

	var orgTypeData = new Array();
	orgTypeData.push({ text : "全部", value : -1 });
	if (_module == "1") {
		itype.loadData(orgTypeData.concat(Globle.constant.BuildOrgType));
	} else {
		itype.loadData(orgTypeData.concat(Globle.constant.AgencyOrgType));
	}
})

// 格式化表格内容
function datagriddrawcell(e) {
	var record = { origin : { record : e.record } };
	// 机构名称
	if (e.column.field == "sname" && e.record[e.column.field] && e.record.itype.indexOf("101") == -1) {
		GridFuncUtil.normal(e, record);
	}

	// 机构类型
	if (e.column.field == "sorgtype" && e.record[e.column.field]) {
		var itypeArr = e.record[e.column.field].split(",");
		var itypeNameArr = new Array();
		$.each(itypeArr, function(index, value) {
			$.each(Globle.constant.OrgType, function() {
				if (this.value == value) {
					itypeNameArr.push(this.text);
				}
			})
		})
		e.html = itypeNameArr.join(",");
	}

	// 操作
	if (e.column.field == "oper" && e.record.itype.indexOf("101") == -1) {
		GridFuncUtil.oper(e, record);
	}
}

// 新增
function add() {
	top.search.popDialog({ url : CTX + "/intermediarys/goOrgManageEditPage", title : "新增机构", width : 840, height : 550, onload : function(window) {
		window.setData({ action : "add", module : _module });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} });
}

// 编辑
function edit(params) {
	var record = {};
	if (!params) {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		}
		record = selects[0].record;
	} else {
		record = params.origin.record;
	}
	if (record.itype.indexOf("101") != -1) {
		top.search.warn({ content : "请选择审计局下的机构数据!" });
		return;
	}
	top.search.popDialog({ url : CTX + "/intermediarys/goOrgManageEditPage", title : "编辑机构", width : 840, height : 550, onload : function(window) {
		window.setData({ action : "edit", record : record, module : _module });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} })
}

// 删除
function delData(params) {
	var record = {};
	if (!params) {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		}
		record = selects[0].record;
	} else {
		record = params.origin.record;
	}
	if (record.itype.indexOf("101") != -1) {
		top.search.warn({ content : "请选择审计局下的机构数据!" });
		return;
	}
	top.search.confirm({ content : "确定要删除？", funl : function() {
		$.post(CTX + "/intermediarys/updateIntermediarysStateById", { id : record.id, state : 99 }, function(result) {
			top.search.info({ content : result.msg });
			if (result.status)
				grid.reload();
		})
	} })
}

// 启用
function restData(params) {
	var record = {};
	if (!params) {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		}
		record = selects[0].record;
	} else {
		record = params.origin.record;
	}
	if (record.itype.indexOf("101") != -1) {
		top.search.warn({ content : "请选择审计局下的机构数据!" });
		return;
	}
	top.search.confirm({ content : "确定要启用？", funl : function() {
		$.post(CTX + "/intermediarys/updateIntermediarysStateById", { id : record.id, state : 1 }, function(result) {
			top.search.info({ content : result.msg });
			if (result.status)
				grid.reload();
		})
	} })
}

// 禁用
function disable(params) {
	var record = {};
	if (!params) {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		}
		record = selects[0].record;
	} else {
		record = params.origin.record;
	}
	if (record.itype.indexOf("101") != -1) {
		top.search.warn({ content : "请选择审计局下的机构数据!" });
		return;
	}
	top.search.confirm({ content : "确定要禁用？", funl : function() {
		$.post(CTX + "/intermediarys/updateIntermediarysStateById", { id : record.id, state : 2 }, function(result) {
			top.search.info({ content : result.msg });
			if (result.status)
				grid.reload();
		})
	} })
}

// 授权
function authorization(params) {
	var record = {}, auditorgid = "";
	if (!_orgid) {
		if (!params) {
			var selects = grid.getSelecteds();
			if (!selects || selects.length != 1) {
				top.search.warn({ content : "请选择一条数据!" });
				return;
			}
			record = selects[0].record;
		} else {
			record = params.origin.record;
		}
		if (record.itype.indexOf("101") != -1) {
			auditorgid = record.id;
		} else {
			auditorgid = record.sauditorgid;
		}
	} else {
		auditorgid = _orgid;
	}
	top.search.popDialog({ url : CTX + "/intermediarys/goOrgManageAuthPage", title : "授权", width : 840, height : 550, onload : function(window) {
		window.setData({ id : auditorgid, module : _module });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} })
}

// 创建管理员
function createAdmin(params) {
	var record = {};
	if (!params) {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		}
		record = selects[0].record;
	} else {
		record = params.origin.record;
	}
	if (record.itype.indexOf("101") != -1) {
		top.search.warn({ content : "请选择审计局下的机构数据!" });
		return;
	}
	if (record.username && record.userphone) {
		top.search.warn({ content : "该机构已存在管理员!" });
		return;
	}
	top.search.popDialog({ url : CTX + "/intermediarys/goOrgManageAdminPage", title : "创建管理员", width : 840, height : 550, onload : function(window) {
		window.setData({ record : record });
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

// 查询详情
function queryDetail(params) {
	var record = {};
	if (!params) {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		}
		record = selects[0].record;
	} else {
		record = params.origin.record;
	}
	if (record.itype.indexOf("101") != -1) {
		top.search.warn({ content : "请选择审计局下的机构数据!" });
		return;
	}
	top.search.popDialog({ url : CTX + "/intermediarys/goOrgAuditViewPage", title : "机构详情", width : 800, height : 550, onload : function(window) {
		window.setData({ sid : record.sid });
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
var form = new Form("orgParams");
function query() {
	grid.load($.extend(form.getData(), { istate : __state, module : _module }));
}