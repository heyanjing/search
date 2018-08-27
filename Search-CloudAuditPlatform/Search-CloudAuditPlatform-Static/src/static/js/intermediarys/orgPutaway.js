search.parse();

var grid = search.get("datagrid");

// 页面加载完成执行
$(function() {
	grid.url = CTX + "/intermediarys/findAuditOrg";
	grid.load({ istate : intermediarysState, module : _module });
})

// 格式化表格内容
function datagriddrawcell(e) {
	var record = { origin : { record : e.record } };
	// 创建时间
	if (e.column.field == "ldtcreatetime" && e.record[e.column.field]) {
		e.html = Globle.fun.format(e.record[e.column.field]);
	}

	// 更新时间
	if (e.column.field == "ldtupdatetime" && e.record[e.column.field]) {
		e.html = Globle.fun.format(e.record[e.column.field]);
	}

	// 操作
	if (e.column.field == "oper") {
		GridFuncUtil.oper(e, record);
	}
}

// 申请入库
function apply() {
	top.search.popDialog({ url : CTX + "/intermediarys/goOrgApplyWarehousPage", title : "申请入库", width : 420, height : 350, onload : function(window) {
		window.setData({ module : _module });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} })
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
	top.search.popDialog({ url : CTX + "/intermediarys/goOrgAuditViewPage", title : "机构详情", width : 800, height : 550, onload : function(window) {
		window.setData({ sid : record.orgid });
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
	grid.load($.extend(form.getData(), { istate : intermediarysState, module : _module }));
}