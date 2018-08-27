var initauths = {};

// 页面加载完成
$(function() {

})

// 父页面传值
function setData(data) {
	var id = data.id;
	initauths = { auditorgid : id };
	$.post(CTX + "/intermediarys/findFunctionGroupsByOrgid", { orgid : id }, function(result) {
		if (result.result && result.result.length > 0) {
			var datagridhtml = '<div property="columns"><div field="orgname" name="orgname" width="200"'
					+ 'headAlign="center" textAlign="left">机构名称</div>';
			$.each(result.result, function() {
				datagridhtml += '<div field="func_' + this.sid + '" width="150" headAlign="center" textAlign="center">' + this.sname + '</div>';
			})
			datagridhtml += '</div>';
			$("#datagrid").html(datagridhtml);
			search.parse();
			var grid = search.get("datagrid");
			grid.url = CTX + "/intermediarys/findOrgsByAuditOrgId";
			grid.load({ auditorgid : id, module : data.module });
			grid.doLayout();
		}
	})
}

// 授权表格绘制
function datagriddrawcell(e) {
	if (e.column.field.indexOf("func_") != -1) {
		var funcgroupid = e.column.field.replace("func_", "");
		if (e.record[e.column.field] == 1) {
			e.html = '<input type="checkbox" class="funcgroupid" value="' + funcgroupid + '" data-srefid="func_' + e.record.srefid + '" checked/>';
		} else {
			e.html = '<input type="checkbox" class="funcgroupid" value="' + funcgroupid + '" data-srefid="func_' + e.record.srefid + '"/>';
		}
	}
	initauths["func_" + e.record.srefid] = "";
}

// 保存
function save() {
	var $funcgroups = $(".funcgroupid:checked");
	var auths = {};
	auths = $.extend(auths, initauths);
	if ($funcgroups && $funcgroups.length > 0) {
		$.each($funcgroups, function() {
			var $this = $(this);
			if (auths[$this.data("srefid")] != "") {
				auths[$this.data("srefid")] += ",";
			}
			auths[$this.data("srefid")] += $this.val();
		})
	}
	$.post(CTX + "/intermediarys/updateAdminUserAuthByAuditOrgId", auths, function(result) {
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			top.search.error({ content : result.msg || "系统错误！" });
		}
	})
}