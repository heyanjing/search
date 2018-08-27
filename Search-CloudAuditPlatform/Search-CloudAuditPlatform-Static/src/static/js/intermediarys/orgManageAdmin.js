search.parse();

var form = new Form("users");
var grid;

var parentPageData = {}, authObj = {};

// 页面加载完成执行
$(function() {
	Step.initTab();
})

// 父页面传值
function setData(data) {
	parentPageData = data;
	$.post(CTX + "/intermediarys/findFunctionGroupsByOrgid", { orgid : data.record.pid }, function(result) {
		search.remove("datagrid", true);
		if (result.result && result.result.length > 0) {
			var datagridhtml = '<div property="columns"><div field="sname" name="sname" width="80"'
					+ 'headAlign="center" textAlign="center">用户名称</div>';
			$.each(result.result, function() {
				datagridhtml += '<div field="func_' + this.sid + '" width="150" headAlign="center" textAlign="center">' + this.sname + '</div>';
			})
			datagridhtml += '</div>';
			$("#datagrid").html(datagridhtml);
			search.parse();
			grid = search.get("datagrid");
		}
	})
}

// 表格绘制
function datagriddrawcell(e) {
	if (e.column.field.indexOf("func_") != -1) {
		var funcgroup = e.column.field.replace("func_", "");
		if (authObj[funcgroup] == undefined) {
			authObj[funcgroup] = false;
		}
		if (authObj[funcgroup]) {
			e.html = '<input type="checkbox" class="funcgroupid" value="' + funcgroup + '" checked/>';
		} else {
			e.html = '<input type="checkbox" class="funcgroupid" value="' + funcgroup + '"/>';
		}
	}
}

// 功能组复选框点击事件
$(document).on("click", ".funcgroupid", function() {
	var $this = $(this);
	authObj[$this.val()] = $this.is(":checked");
})

// 即将进入授权
function authwillenter() {
	return form.validate();
}

// 已经进入授权
function authdidenter() {
	grid.loadData([ { sid : search.get("sphone").getValue(), sname : search.get("sname").getValue() } ]);
	grid.doLayout();
}

// 保存
function saveData() {
	if (!form.validate()) {
		return;
	}
	var formData = form.getData();
	var auths = new Array();
	for ( var functiongroupid in authObj) {
		if (authObj[functiongroupid]) {
			auths.push(functiongroupid);
		}
	}
	$.post(CTX + "/intermediarys/insertOrgAdminUser", $.extend(formData, { orgid : parentPageData.record.sid, auditorgid : parentPageData.record.pid,
		functiongroupids : auths.join(",") }), function(result) {
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			top.search.error({ content : result.msg || "系统错误！" });
		}
	})
}