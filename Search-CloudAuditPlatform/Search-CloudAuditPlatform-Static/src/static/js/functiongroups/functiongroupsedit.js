search.parse();
var treegrid = search.get("treedatagrid");
var form = new Form("functiongroup");

var sfromorgid = search.get("sfromorgid");

// 页面加载完成执行
$(function() {
	Step.init();
	
	treegrid.url = CTX + "/functiongroups/findListMapFunctionsByOrgid";
	if (__usertype == 1) {
		sfromorgid.setEnabled(false);
		sfromorgid.rules = "";
		
		treegrid.load();
	} else {
		sfromorgid.url = CTX + "/functiongroups/findListMapOrgsByType";
		sfromorgid.load({ type : 1});
	}
})

// 父页面传值
function setData(data) {
	if (data.action == "edit") {
		// 编辑加载数据
		$.post(CTX + "/functiongroups/getFunctionGroupsById", {id : data.sid}, function(result) {
			if (result.status) {
				form.setData(result.result.functiongroups);
				treegrid.loadData(result.result.functions);
				treegrid.setValue(result.result.functionids);
			} else {
				top.search.error({ content : "系统错误！" });
			}
		})
	}
}

// 表单校验
function functiongroupvalidate() {
	return form.validate();
}

// 功能来源改变事件
function sfromorgidchange(e) {
	treegrid.load({ orgid : e.value});
}

// 保存数据
function saveData() {
	$.post(CTX + "/functiongroups/insertOrUpdateFunctionGroups", $.extend(form.getData(), {functionIds : treegrid.getValue()}), function(result) {
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}