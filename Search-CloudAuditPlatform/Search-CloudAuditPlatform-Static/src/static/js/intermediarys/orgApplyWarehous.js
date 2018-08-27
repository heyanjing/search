search.parse();

var sauditorgid = search.get("sauditorgid");

var form = new Form("orgApply");

var sourceData = {};

// 页面加载完成执行
$(function() {

})

// 父页面传值
function setData(data) {
	sourceData = data;
	sauditorgid.url = CTX + "/intermediarys/findAuditOrgByOrgid";
	sauditorgid.load({ module : data.module });
}

// 保存
function saveData() {
	if (!form.validate()) {
		return;
	}
	$.post(CTX + "/intermediarys/insertIntermediarysBySauditorgid", $.extend(form.getData(), { module : sourceData.module }), function(result) {
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			top.search.error({ content : result.msg });
		}
	})
}