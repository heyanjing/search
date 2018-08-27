search.parse();
var form = new Form("setting");

var sorgid = search.get("sorgid");

// 页面加载完成执行
$(function() {
	if (sorgid) {
		sorgid.url = CTX + "/auditsettings/findAllOrgs";
		sorgid.load();
	}
	
	if (usertype != 1) {
		findOneByOrgId(null);
	}
})

// 保存数据
function saveData() {
	if (!form.validate())
		return;
	$.post(CTX + "/auditsettings/insertOrUpdateAuditSettings", form.getData(), function(result) {
		if (result.status) {
			search.get("sid").setValue(result.result.sid);
			top.search.info({ content : result.msg });
		}
	})
}

// 查询审计设置
function findOneByOrgId(orgid) {
	$.post(CTX + "/auditsettings/findOneByOrgId", { orgid : orgid }, function(result) {
		if (result.status && result.result) {
			search.get("sid").setValue(result.result.sid);
			search.get("idividingline").setValue(result.result.idividingline);
		} else {
			search.get("sid").setValue("");
			search.get("idividingline").setValue("");
		}
	})
}

// 机构改变事件
function sorgidchange(e) {
	findOneByOrgId(e.value);
}