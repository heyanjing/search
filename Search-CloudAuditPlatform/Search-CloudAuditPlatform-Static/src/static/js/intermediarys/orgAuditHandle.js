search.parse();

var grid;
var sourceData = [];

// 页面加载完成执行
$(function() {
	Step.init();
})

// 父页面传值
function setData(data) {
	var records = data.records;
	sourceData = records;
	if (!records || records.length <= 0) {
		return;
	}
	$.post(CTX + "/intermediarys/findFunctionGroupsByOrgid", { orgid : records[0].sauditorgid }, function(result) {
		search.remove("datagrid", true);
		var datagridhtml = '<div property="columns"><div field="username" name="username" width="120"'
				+ 'headAlign="center" textAlign="center">用户名称</div>';
		if (result.result && result.result.length > 0) {
			$.each(result.result, function() {
				datagridhtml += '<div field="func_' + this.sid + '" width="150" headAlign="center" textAlign="center">' + this.sname + '</div>';
			})
		}
		datagridhtml += '</div>';
		$("#datagrid").html(datagridhtml);
		search.parse();
		grid = search.get("datagrid");
		grid.loadData(records);
	})
}

// 绘制表格
function datagriddrawcell(e) {
	if (e.column.field.indexOf("func_") != -1) {
		e.html = '<input type="checkbox" name="funid" value="' + e.record.sid + '_' + e.column.field.replace("func_", "") + '"/>';
	}
}

// 审核通过
function auditWillLeave() {
	$("#reject").hide();
	audit(true);
}

// 重绘表格
function reDrawDatagrid() {
	grid.doLayout();
}

// 审核驳回
$("#reject").on("click", function() {
	audit(false);
})

// 审核处理
function audit(pass) {
	var sids = new Array();
	$.each(sourceData, function() {
		sids.push(this.sid);
	})
	$.post(CTX + "/intermediarys/updateOrgAuditHandle", { sids : sids.join(","), pass : pass }, function(result) {
		if (result.status) {
			if (!pass) {
				top.search.info({ content : result.msg, funl : function() {
					window.closeWindow("ok");
				} });
			}
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}

// 保存权限
function saveData() {
	var funcgroupids = new Array();
	$.each($('input[type=checkbox]:checked'), function() {
		var $this = $(this);
		funcgroupids.push($this.val());
	})
	$.post(CTX + "/intermediarys/insertAuthorization", { funcgroupidandorgids : funcgroupids.join(","), sauditorgid : sourceData[0].sauditorgid },
			function(result) {
				if (result.status) {
					top.search.info({ content : result.msg, funl : function() {
						window.closeWindow("ok");
					} });
				} else {
					top.search.error({ content : "系统错误！" });
				}
			})
}