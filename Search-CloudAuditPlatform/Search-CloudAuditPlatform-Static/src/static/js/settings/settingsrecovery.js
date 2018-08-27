search.parse();
var grid = search.get("datagrid");

// 页面加载完成执行
$(function() {
	grid.url = CTX + "/settings/pageLogSettings";
	grid.load();
})


// 格式化表格内容
function datagriddrawcell(e) {
	// 是否启用用户数量控制
	if (e.column.field == "isupportusernumber" && e.record[e.column.field] != "") {
		$.each(Globle.constant.YesOrNo, function() {
			if (this.value == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 用户数量不受控制单位类型
	if (e.column.field == "sorgtype" && e.record[e.column.field]) {
		var html = "";
		$.each(Globle.constant.OrgType, function() {
			if (e.record[e.column.field].indexOf(this.value) != -1) {
				html += (html != "" ? "," + this.text : this.text);
			}
		})
		e.html = html;
	}
	// 创建时间
	if (e.column.field == "ldtcreatetime" && e.record[e.column.field]) {
		e.html = Globle.fun.format(e.record[e.column.field]);
	}
}

// 还原
function recovery() {
	var selects = grid.getSelecteds();
	if (!selects || selects.length != 1) {
		search.warn({ content : "请选择一条数据!" });
		return;
	}
	top.search.confirm({ content : "确定要恢复？", funl : function() {
		$.post(CTX + "/settings/recoverySettingsByLogSettingsId", { id : grid.getValue() }, function(result) {
			if (result.status) {
				top.search.info({ content : result.msg });
			}
			grid.reload();
		})
	} })
}