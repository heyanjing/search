search.parse();

var grid = search.get("datagrid");

var itype = search.get("itype");

// 页面加载完成执行
$(function() {
	grid.url = CTX + "/intermediarys/findOrg";
	grid.load({ module : _module });

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
	if (e.column.field == "orgname" && e.record[e.column.field]) {
		GridFuncUtil.normal(e, record);
	}

	// 机构类型
	if (e.column.field == "itype" && e.record[e.column.field]) {
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
	if (e.column.field == "oper") {
		GridFuncUtil.oper(e, record);
	}
}

// 审核
function audit(params) {
	var records = new Array();
	if (!params) {
		var selects = grid.getSelecteds();
		if (!selects || selects.length == 0) {
			top.search.warn({ content : "请选择一条数据!" });
			return;
		} else if (selects.length == 1) {
			records.push(selects[0].record);
		} else {
			var sauditorgid = "", pass = true;
			$.each(selects, function() {
				if (sauditorgid == "" || sauditorgid == this.record.sauditorgid) {
					sauditorgid = this.record.sauditorgid;
					records.push(this.record);
				} else {
					pass = false;
				}
			})
			if (!pass) {
				top.search.warn({ content : "选择多条数据审计机构不相同!" });
				return;
			}
		}
	} else {
		records.push(params.origin.record);
	}
	top.search.popDialog({ url : CTX + "/intermediarys/goOrgAuditHandlePage", title : "审核", width : 840, height : 550, onload : function(window) {
		window.setData({ records : records });
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
	grid.load($.extend(form.getData(), { module : _module }));
}