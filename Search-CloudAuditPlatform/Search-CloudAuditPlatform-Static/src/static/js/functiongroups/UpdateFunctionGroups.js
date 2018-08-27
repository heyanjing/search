search.parse();
var form = new Form("form");
var isupportproject = search.get("isupportproject");
var sfromorgid = search.get("sfromorgid");
var __orgtype = "";
var sorgid = search.get("sorgid");
// 页面加载完成执行
$(function() {
	Step.init();
	sfromorgid.url = CTX + "/orgs/gao";
	sfromorgid.load();
	sorgid.url = CTX + "/orgs/gindo";
	sorgid.load();
	isupportproject.loadData(Globle.constant.YesOrNo);
	if (__usertype == Globle.constant.userTypes[0].id) {
		sfromorgid.setEnabled(false);
		sfromorgid.rules = "";
		$("#org3").show();
		$("#org4").show();
	} else {
		$.ajax({
			url : CTX+"/orgs/gui",
			success : function(result){
				__orgtype = result.result.orgtype;
				if(__orgtype !=  Globle.constant.OrgType[0].value){
					$("#org1").show();
					$("#org2").show();
				}
			}
		});
	}
})

// 父页面传值
function setData(data) {
	if (data.action == "edit") {
		// 编辑加载数据
		$.post(CTX + "/functiongroups/getFunctionGroupsById", {id : data.sid}, function(result) {
			if (result.status) {
				form.setData(result.result.functiongroups);
			} else {
				top.search.error({ content : "系统错误！" });
			}
		})
	}
}


// 保存数据
function saveData() {
	if (!form.validate())return;
	$.post(CTX + "/functiongroups/ufg", {sid:search.get("sid").getValue(),sname:search.get("sname").getValue(),sfromorgid:search.get("sfromorgid").getValue(),sdesc:search.get("sdesc").getValue(),isupportproject:search.get("isupportproject").getValue(),sorgid:sorgid.getValue()}, function(result) {
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}

//验证来源机构不能为空
function sfromorgidValidate(e){
	if(__usertype != Globle.constant.userTypes[0].id&&sfromorgid.getValue()==""&&__orgtype !=  Globle.constant.OrgType[0].value){
		e.message = "请选择来源机构";
		e.pass = false;
	}

}
/**
 * 关闭窗口。
 */
function windowClose(){
	window.closeWindow();
}