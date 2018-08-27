search.parse();
var form = new Form("MailSettingsform");
var ineedauth = search.get("ineedauth");
ineedauth.loadData(Globle.constant.YesOrNo);

$(function() {
	setData();
})

/**
 * 父级页面传值
 *
 * @param data
 */
function setData() {
	 $.ajax({ url : CTX+"/mailsettings/getMailsettingsIsNull",
		 success : function(result) {
	 if (result.status) {
		 form.setData(result.result);
	 }
	 } });
}

// 保存操作
function save() {
	if (!form.validate()) {
		return;
	}

	$.post(CTX+"/mailsettings/save", form.getData(), function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg });
		} else if (result.status) {
			top.search.info({ content : result.msg });
		} else {
			top.search.error({
				content : "系统错误!"
			});
		}
	})
}

var closetitle;
function windowClose(result) {
	top.search.info({
		content : result,
		funl : function() {
			window.closeWindow("ok");
		}
	});
}
