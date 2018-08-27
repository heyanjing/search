search.parse();

var form = new Form("orgs");

var sparentid = search.get("sparentid");
var itype = search.get("itype");
var sauditorgid = search.get("sauditorgid");
var createadmin = search.get("createadmin");

var snameblursuccess = true, orgname = "", sauditorgidVal = "";

var instanceStep, licenseUpload, aptitudesUpload;

var formData = {}, attachData = {}, resultData = {}, parentPageData = {};

// 页面加载完成执行
$(function() {
	instanceStep = Step.initTab();

	sauditorgid.url = CTX + "/intermediarys/findAuditOrgByUsertype";
	sauditorgid.load();

	createadmin.loadData(Globle.constant.YesOrNo);
})

// 父页面传值
function setData(data) {
	parentPageData = data;
	sparentid.url = CTX + "/intermediarys/findParentOrgByUsertype";
	sparentid.load({ module : data.module });

	if (parentPageData.module == "1") {
		itype.loadData(Globle.constant.BuildOrgType);
	} else {
		itype.loadData(Globle.constant.AgencyOrgType);
		itype.setEnabled(false);
		itype.setValue("102");
	}
	if (parentPageData.action == "edit") {
		$.post(CTX + "/intermediarys/findOrgDetailsByAuditOrgIdAndIntermediaryOrgId", { sauditorgid : parentPageData.record.sauditorgid,
			sintermediaryorgid : parentPageData.record.sid }, function(result) {
			if (result.result) {
				resultData = $.extend(resultData, result.result);
				if (!result.result.sparentid) {
					resultData.sparentid = -1;
				}
				createadmin.setValue(result.result.userid ? 1 : 2);
				sauditorgid.setValue(parentPageData.record.sauditorgid);
				form.setData(resultData);
			}
		})
	}
}

// 机构名称移除焦点
function snameblur(e) {
	if (parentPageData.action == "edit" || e.value == orgname) {
		return;
	}
	orgname = e.value;
	snameblursuccess = false;
	$.ajax({ type : "POST", async : true, url : CTX + "/intermediarys/findOrgDetailsByOrgname", data : { orgname : e.value },
		success : function(result) {
			if (result.result) {
				resultData = $.extend(resultData, result.result);
				if (!result.result.sparentid) {
					resultData.sparentid = -1;
				}
				if (result.result.userid) {
					createadmin.setValue(1);
					createadmin.setEnabled(false);
				} else {
					createadmin.setValue(2);
					createadmin.setEnabled(true);
				}

				if (result.result.istate == 1) {
					form.setEnabled(false, [ "sname", "sauditorgid" ]);
				}

				if (parentPageData.module == "2") {
					resultData.itype = "102";
				}
				form.setData(resultData);
				if (!itype.getValue()) {
					itype.setEnabled(true);
				}
			} else {
				resultData = { business : [], aptitudes : [] };
				form.setEnabled(true);
				if (parentPageData.module == "2") {
					form.setData({ itype : "102" });
					itype.setEnabled(false);
				} else {
					form.setData(null);
				}
				createadmin.setValue(2);
				sparentid.setValue(-1);
				search.get("sname").setValue(orgname);
			}
			snameblursuccess = true;
		} })
}

// 即将进入授权
function authwillenter() {
	if (!form.validate()) {
		// 验证不通过
		if (instanceStep.stepNum != 1) {
			instanceStep.stepClick(1, false);
		}
		return false;
	}
}

// 已经进入授权
function authdidenter() {
	if (sauditorgidVal == sauditorgid.getValue()) {
		return;
	}
	sauditorgidVal = sauditorgid.getValue();
	$.post(CTX + "/intermediarys/findFunctionGroupsByOrgid", { orgid : sauditorgid.getValue() }, function(result) {
		search.remove("datagrid", true);
		if (result.result && result.result.length > 0) {
			var datagridhtml = '<div property="columns"><div field="sname" name="sname" width="80"'
					+ 'headAlign="center" textAlign="center">用户名称</div>';
			$.each(result.result, function() {
				datagridhtml += '<div field="func_' + this.sid + '" width="150" headAlign="center" textAlign="center">' + this.sname + '</div>';
			})
			datagridhtml += '</div>';
			$("#datagrid").html(datagridhtml);
			search.parse();
			var grid = search.get("datagrid");
			grid.loadData([ { sid : search.get("userid").getValue(), sname : search.get("username").getValue() } ]);
			grid.doLayout();
		} else {
			$("#datagrid").empty();
		}
	})
}

// 创建管理员改变值
var usernameVal = "", userphoneVal = "";
function createadminchange(e) {
	if (e.value == 1) {
		// 是
		$("#adminperson").show();
		instanceStep.showStep("#auth");
		search.get("username").rules = "required";
		search.get("userphone").rules = "required,phonenumber";
		search.get("username").setValue(usernameVal);
		search.get("userphone").setValue(userphoneVal);
		usernameVal = userphoneVal = "";
	} else if (e.value == 2) {
		// 否
		$("#adminperson").hide();
		instanceStep.hideStep("#auth");
		search.get("username").rules = "";
		search.get("userphone").rules = "";
		usernameVal = search.get("username").getValue();
		userphoneVal = search.get("userphone").getValue();
		search.get("username").setValue("");
		search.get("userphone").setValue("");
	}
}

// 已进入营业执照上传
function licensedidenter() {
	var licenseUploadIndex = setInterval(function() {
		if (snameblursuccess) {
			if (!licenseUpload) {
				var options = { relativePath : Globle.constant.upload.org, initFiles : { rootPath : NETWORK_ROOT, files : resultData.business },
					uploadFinished : function(datas) {
						var deleteids = licenseUpload.delArr;
						if (datas && datas.length > 0) {
							// 上传成功附件信息
							$.each(datas, function(i) {
								var filetotal = attachData.filetotal ? ++attachData.filetotal : 1;
								attachData["filetotal"] = filetotal;
								attachData["aptitudesandattach[" + filetotal + "].sname"] = this.sname;
								attachData["aptitudesandattach[" + filetotal + "].spath"] = this.spath;
								attachData["aptitudesandattach[" + filetotal + "].itype"] = 1;
							})
						}

						if (deleteids && deleteids.length > 0) {
							// 删除附件ID
							formData = $.extend(formData, { licensedels : deleteids.join(",") });
						}
						if (aptitudesUpload) {
							// 有资质上传组件
							Upload2.upload();
						} else {
							saveForm();
						}
					} };
				licenseUpload = Upload.upload(options);
			} else if (resultData.business != null) {
				licenseUpload.setData(resultData.business);
			}
			resultData.business = null;
			clearInterval(licenseUploadIndex);
		}
	}, 100);
}

// 已进入机构资质上传
function aptitudesdidenter() {
	var aptitudesUploadIndex = setInterval(function() {
		if (snameblursuccess) {
			if (!aptitudesUpload) {
				var options = { wbOpts : { relativePath : Globle.constant.upload.org }, url : "/user/findListMapDictionariesByItype",
					data : { type : 1 }, callback : function(datas, deleteids) {
						if (datas && datas.length > 0) {
							// 上传成功附件信息
							$.each(datas, function(i) {
								var filetotal = attachData.filetotal ? ++attachData.filetotal : 1;
								attachData["filetotal"] = filetotal;
								attachData["aptitudesandattach[" + filetotal + "].sid"] = this.sid;
								attachData["aptitudesandattach[" + filetotal + "].sdesc"] = this.sdesc;
								attachData["aptitudesandattach[" + filetotal + "].sdictionarieid"] = this.sdictionarieid;
								attachData["aptitudesandattach[" + filetotal + "].sname"] = this.sname;
								attachData["aptitudesandattach[" + filetotal + "].spath"] = this.spath;
								attachData["aptitudesandattach[" + filetotal + "].itype"] = 2;
							})
						}
						if (deleteids && deleteids.length > 0) {
							// 删除附件ID
							formData = $.extend(formData, { aptitudesdels : deleteids.join(",") });
						}
						// 保存数据
						saveForm();
					} }
				options = $.extend(options, { initData : resultData.aptitudes });
				aptitudesUpload = Upload2.init(options);
			} else if (resultData.aptitudes != null) {
				Upload2.setData(resultData.aptitudes);
			}
			resultData.aptitudes = null;
			clearInterval(aptitudesUploadIndex);
		}
	}, 100);
}

// 授权表格绘制
function datagriddrawcell(e) {
	if (e.column.field.indexOf("func_") != -1) {
		var funcgroup = e.column.field.replace("func_", "");
		var html = "";
		if (resultData.auth != null) {
			$.each(resultData.auth, function() {
				if (this.sfunctiongroupid == funcgroup) {
					html = "<input type='checkbox' class='funcgroupid' value='" + funcgroup + "' checked/>";
				}
			})
		}
		if (html == "") {
			html = "<input type='checkbox' class='funcgroupid' value='" + funcgroup + "'/>";
		}
		e.html = html;
	}
}

// 保存
function saveData() {
	if (!form.validate()) {
		// 验证不通过
		if (instanceStep.stepNum != 1) {
			instanceStep.stepClick(1, false);
		}
		return;
	}
	var saveData = form.getData();
	if (parentPageData.action == "edit") {
		saveData = $.extend(saveData, { intermediarysid : parentPageData.record.id });
	}
	$.post(CTX + "/intermediarys/insertOrUpdateOrgCheck", $.extend(saveData, { action : parentPageData.action, module : parentPageData.module }),
			function(result) {
				if (result.status) {
					attachData = {};
					formData = form.getData();
					if (!licenseUpload && !aptitudesUpload) {
						// 营业执照和资质上传组件未初始化
						saveForm();
					} else if (licenseUpload) {
						// 有营业执照上传组件
						licenseUpload.upload();
					} else if (aptitudesUpload) {
						// 有资质上传组件
						Upload2.upload();
					}
				} else {
					top.search.error({ content : result.msg || "系统错误！" });
				}
			})
}

// 保存所有数据
function saveForm() {
	var $funcgroups = $(".funcgroupid:checked"), funcgroupids = new Array();
	if ($funcgroups && $funcgroups.length > 0) {
		$.each($funcgroups, function() {
			var $this = $(this);
			funcgroupids.push($this.val());
		})
	}
	var url = "/intermediarys/insertOrgsAndIntermediarys";
	var saveData = $.extend(formData, attachData, { funcgroupids : funcgroupids.join(",") });
	if (parentPageData.action == "edit") {
		saveData = $.extend(saveData, { intermediarysid : parentPageData.record.id });
		if (!sauditorgidVal) {
			// 没有进入授权
			if (parentPageData.record.sauditorgid == sauditorgid.getValue()) {
				// 审计机构未改变
				if (resultData.auth != null) {
					$.each(resultData.auth, function() {
						funcgroupids.push(this.sfunctiongroupid);
					})
					saveData = $.extend(saveData, { funcgroupids : funcgroupids.join(",") });
				}
			}
		}
		url = "/intermediarys/updateOrgsAndIntermediarys";
	}

	$.post(CTX + url, saveData, function(result) {
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			top.search.error({ content : result.msg || "系统错误！" });
		}
	})
}