search.parse();
var changepasswdform = new Form("changepasswdform");

// 保存
function saveData() {
	if (search.get("newpasswdone").getValue() != search.get("newpasswdtwo").getValue()) {
		top.search.info({ content : "两次密码不一致！" });
		return;
	}
	changepasswdform.validate();
	$.post(CTX + "/common/changePasswdByEmail", $.extend(changepasswdform.getData(), { email : email, code : code }), function(result) {
		if (result.status) {
			if (result.result) {
				top.search.info({ content : "密码修改成功！", funl : function() {
					location.href = CTX + "/";
				} });
			} else {
				top.search.info({ content : result.msg });
			}
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}