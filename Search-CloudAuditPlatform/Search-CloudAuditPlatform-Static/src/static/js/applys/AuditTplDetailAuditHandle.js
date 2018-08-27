search.parse();

var form = new Form('apply');
var ipass = search.get('ipass');
var parentData = {};
// 页面加载完成执行
$(function() {
	ipass.loadData(Globle.constant.YesOrNo);
})

// 父页面传值
function setData(data) {
	parentData = data;
	form.setEnabled(false, data.editfield);
	$.post(CTX + '/applys/findAuditTplDetailCopysBySid', {sid : data.sid}, function(result) {
		var formData = result.result;
		formData.ipass = (formData.ipass == 2 ? 2 : 1);
		form.setData(formData);
	})
}

// 保存
function saveData() {
	$.post(CTX + '/applys/updateAuditTplDetailAudit', $.extend(form.getData(), {sid : parentData.sid}), function(result) {
		if (result.status) {
			top.search.info({ content : result.result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}