search.parse();
var form = new Form("setting");

// 页面加载完成执行
$(function() {
	$.post(CTX + "/usersettings/findOne", function(result) {
		if (result.status && result.result) {
			form.setData(result.result);
		}
	});
})

// 保存数据
function saveData() {
	if (!form.validate())
		return;
	$.post(CTX + "/usersettings/insertOrUpdateUserSettings", form.getData(), function(result) {
		if (result.status) {
			search.get("sid").setValue(result.result.sid);
			top.search.info({ content : result.msg});
		}
	})
}