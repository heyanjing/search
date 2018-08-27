search.parse();
var treegrid = search.get("treedatagrid");
var form = new Form("users");

var igender = search.get("igender");
var ipermissionlevel = search.get("ipermissionlevel");
var sorgid = search.get("sorgid");
var sdictionariesid = search.get("sdictionariesid");
var chargeorgs = search.get("chargeorgs");

var advanced, stepInstance;

var formData = {}, resultData, idCardUpload, aptitudeUpload;

// 页面记载完成执行
$(function() {
	stepInstance = Step.initTab();

	igender.loadData(Globle.constant.sex);
	ipermissionlevel.loadData(Globle.constant.permissionLevel);
	sorgid.url = CTX + "/user/findListMapOrgs";
	sdictionariesid.url = CTX + "/user/findListMapDictionariesByItype";
	sdictionariesid.load({ type : 3 });
	chargeorgs.url = CTX + "/user/findListMapDepartmentByOrgid";
})

// 表单校验
function usersvalidate() {
	return form.validate();
}

// 初始化身份证上传
function initidcardupload() {
	if (!idCardUpload) {
		var options = { relativePath : Globle.constant.upload.user, uploadFinished : function(datas) {
			var deleteids = idCardUpload.delArr;
			if (datas && datas.length > 0) {
				// 上传成功附件信息
				$.each(datas, function(i) {
					var filetotal = formData.filetotal ? ++formData.filetotal : 1;
					var fileData = {};
					formData["filetotal"] = filetotal;
					fileData["aptitudesandattach[" + filetotal + "].sname"] = this.sname;
					fileData["aptitudesandattach[" + filetotal + "].spath"] = this.spath;
					fileData["aptitudesandattach[" + filetotal + "].itype"] = 3;
					formData = $.extend(formData, fileData);
				})
			}

			if (deleteids && deleteids.length > 0) {
				// 删除附件ID
				formData = $.extend(formData, { commonattachsids : deleteids.join(",") });
			}
			if (aptitudeUpload) {
				// 有资质上传组件
				Upload2.upload();
			} else {
				saveForm();
			}
		} };

		if (resultData && resultData.idcard && resultData.idcard.length > 0) {
			options = $.extend(options, { initFiles : { rootPath : NETWORK_ROOT, files : resultData.idcard } });
		}
		idCardUpload = Upload.upload(options);
	}
}

// 初始化资质上传
function initaptitudeupload() {
	if (!aptitudeUpload) {
		var options = { wbOpts : { relativePath : Globle.constant.upload.user }, url : "/user/findListMapDictionariesByItype", data : { type : 2 },
			callback : function(datas, deleteids) {
				if (datas && datas.length > 0) {
					// 上传成功附件信息
					$.each(datas, function(i) {
						var filetotal = formData.filetotal ? ++formData.filetotal : 1;
						var fileData = {};
						formData["filetotal"] = filetotal;
						fileData["aptitudesandattach[" + filetotal + "].sid"] = this.sid;
						fileData["aptitudesandattach[" + filetotal + "].sdesc"] = this.sdesc;
						fileData["aptitudesandattach[" + filetotal + "].sdictionarieid"] = this.sdictionarieid;
						fileData["aptitudesandattach[" + filetotal + "].sname"] = this.sname;
						fileData["aptitudesandattach[" + filetotal + "].spath"] = this.spath;
						fileData["aptitudesandattach[" + filetotal + "].itype"] = 4;
						formData = $.extend(formData, fileData);
					})
				}
				if (deleteids && deleteids.length > 0) {
					// 删除附件ID
					formData = $.extend(formData, { orgoruseranddictionarierefsids : deleteids.join(",") });
				}
				// 保存数据
				saveForm();
			} }
		if (resultData && resultData.aptitudes && resultData.aptitudes.length > 0) {
			options = $.extend(options, { initData : resultData.aptitudes });
		}
		aptitudeUpload = Upload2.init(options);
	}
}

// 保存表单数据
function saveForm() {
	$.post(CTX + "/user/insertOrUpdateUsers", formData, function(result) {
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			if (result.msg) {
				top.search.info({ content : result.msg });
			} else {
				top.search.error({ content : "系统错误！" });
			}
		}
	})
}

// 父页面传值
var parentData;
function setData(data) {
	advanced = data.advanced;
	parentData = data;
	sorgid.load({ advanced : advanced });
}

// 机构加载完成执行
function sorgidloaded(e) {
	if (parentData.action == "edit") {
		$.post(CTX + "/user/findMapUsersBySid", { sid : parentData.sid }, function(result) {
			if (result.status) {
				form.setData(result.result);
				resultData = result.result;
			} else {
				top.search.error({ content : "系统错误！" });
			}
		})
	}
}

// 保存数据
function saveData() {
	if (!form.validate()) {
		// 验证不通过
		if (stepInstance.stepNum != 1) {
			stepInstance.stepClick(1, false);
		}
		return;
	}
	formData = $.extend(formData, form.getData());
	if (!idCardUpload && !aptitudeUpload) {
		// 身份证和资质上传组件未初始化
		saveForm();
	} else if (idCardUpload) {
		// 有身份证上传组件
		idCardUpload.upload();
	} else if (aptitudeUpload) {
		// 有资质上传组件
		Upload2.upload();
	}
}

// 权限级别改变事件
function ipermissionlevelchange(e) {
	if (e.value == 2) {
		chargeorgs.setEnabled(true);
	} else {
		chargeorgs.setEnabled(false);
		chargeorgs.setValue("");
	}
}

// 机构改变事件
function sorgidchange(e) {
	if (e.value)
		chargeorgs.load({ orgid : e.value });
}

// 分管机构加载完成执行
function chargeorgsloaded(e) {
	if (resultData) {
		chargeorgs.setValue(resultData.chargeorgs);
	}
}

// 身份证校验
function sidcardvalidate(e) {
	if (typeof (e.value) === "undefined" || e.value == null || e.value === "") {
		e.message = "不允许为空";
		e.pass = false;
		return;
	}

	if (!Idcard.isValid(e.value)) {
		e.message = "不是有效的身份证号码";
		e.pass = false;
		return;
	}
}