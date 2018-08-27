search.parse();

var grid = search.get("datagrid");

// 页面加载完成执行
$(function() {
	grid.url = CTX + "/applys/findApplysForPage";
	grid.load({ typefield : __typefield });
})

// 格式化表格内容
function datagriddrawcell(e) {
	var record = { origin : { record : e.record } };
	// 机构名称
	if (e.column.field == "projectname" && e.record[e.column.field]) {
		GridFuncUtil.normal(e, record);
	}

	// 送审标准模板类型
	if (e.column.field == "itype" && e.record[e.column.field]) {
		$.each(Globle.constant.AuditTplsType, function() {
			if (this.id == e.record[e.column.field]) {
				e.html = this.text;
				return true;
			}
		})
	}

	// 操作
	if (e.column.field == "oper") {
		GridFuncUtil.oper(e, record);
	}
}

// 新增
function add() {
	$.post(CTX + '/processdesigns/checkWhetherOperableProcessDesigns', {sfunctionid : __functionid}, function(result) {
		if (result.status) {
			top.search.popDialog({ url : CTX + "/applys/goApplysEditPage", title : "新增申请", width : 900, height : 550, onload : function(window) {
				window.setData({ action : "add", processdesignsid : result.result.sid, functionid : __functionid });
			}, ondestroy : function(result) {
				if (result && result != "cancel")
					grid.reload();
			} });
		} else {
			top.search.error({ content : "没有权限操作!" });
		}
	})
}

// 编辑
function edit(params) {
	var record = {};
	if (params) {
		record = params.origin.record;
	} else {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		}
		record = selects[0].record;
	}
	top.search.popDialog({ url : CTX + "/applys/goApplysEditPage", title : "编辑申请", width : 900, height : 550, onload : function(window) {
		window.setData({ action : "edit", sid : record.sid, functionid : __functionid, processinstancesid : record.processinstancesid, record : record });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} })
}

// 查看详情
function queryDetail(params) {
	var record = {};
	if (params) {
		record = params.origin.record;
	} else {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		}
		record = selects[0].record;
	}
	top.search.popDialog({ url : CTX + "/applys/goApplysViewPage", title : "申请详情", width : 900, height : 550, onload : function(window) {
		window.setData({ action : "edit", sid : record.sid, functionid : __functionid, processinstancesid : record.processinstancesid, record : record });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} })
}

// 提交
function submit(params) {
	var record = {};
	if (params) {
		record = params.origin.record;
	} else {
		var selects = grid.getSelecteds();
		if (!selects || selects.length != 1) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		}
		record = selects[0].record;
	}
	top.search.popDialog({ url : CTX + "/applys/goApplysEditPage", title : "提交申请", width : 900, height : 550, onload : function(window) {
		window.setData({ action : "submit", sid : record.sid, functionid : __functionid, processinstancesid : record.processinstancesid, record : record });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} })
}

//展开查询
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
var form = new Form("applyParams");
function query() {
	grid.load($.extend(form.getData(), { typefield : __typefield }));
}