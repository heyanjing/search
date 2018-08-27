search.parse();

/*---------------------------------项目库立项和可研开始------------------------------------------*/
/**
 * 实例控制器。
 */
function UpdateProjectLidHandle(){
	this._li_index = 0;
	this._select_sproprietororgid = ""; // 项目业主ID。
	this._select_sdeputyorgid = ""; // 代建单位ID。
	this._select_sapprovalorgid = ""; //审批单位ID。
	
	/*立项*/
	this._approval_sid = "";
	this.approval_form = new Form("ApprovalForm");
	this.appr_ipros_type = search.get("iprospectingtype");
	this.appr_idesi_type = search.get("idesigntype");
	this.appr_isupe_type = search.get("isupervisiontype");
	this.appr_icons_type = search.get("iconstructiontype");
	this.appr_iinte_type = search.get("iintermediarytype");
	this.sproprietororgid = search.get("sproprietororgid"); // 项目业主。
	this.sdeputyorgid = search.get("sdeputyorgid"); // 代建单位。
	this.sapprovalorgid = search.get("sapprovalorgid"); // 审批单位。
	this.formData = {},this.proLibUpload, this.resultData,this._cont_appr = {}, this._approval_attach = {}, this._auditorgid = "";
	/*可研*/
	this._bility_sid = "";
	this.bility_form = new Form("BilityForm");
	this.appr_ipros_type_0 = search.get("iprospectingtype_0");
	this.appr_idesi_type_0 = search.get("idesigntype_0");
	this.appr_isupe_type_0 = search.get("isupervisiontype_0");
	this.appr_icons_type_0 = search.get("iconstructiontype_0");
	this.appr_iinte_type_0 = search.get("iintermediarytype_0");
	this.sproprietororgid_0 = search.get("sproprietororgid_0"); // 项目业主。
	this.sdeputyorgid_0 = search.get("sdeputyorgid_0"); // 代建单位。
	this.sapprovalorgid_0 = search.get("sapprovalorgid_0"); // 审批单位。
	this.form_bility_data = {},this.bilityUpload, this.result_bility_Data,this._cont_bility = {}, this._bility_attach = {};
	
	
	/**
	 * admin 用户加载项目归属机构。
	 */
	this.loadProAuditOrgSelectData = function(){
		search.get("sauditorgid").url = CTX+"/projectlib/getAuditOrgList";
		search.get("sauditorgid").load();
	}
	
	/**
	 * 立项加载立项项目业主、代建单位绑定下拉框。
	 */
	this.loadApprovalSelectData = function(params){
		//加载项目业主、代建单位。
		$.ajax({
			url : CTX+"/projectlib/getProjectOwnerOrConstruction",
			data : {auditid: params},
			success : function(result){
				instance.sproprietororgid.loadData(result.result);
				instance.sdeputyorgid.loadData(result.result);
			}
		});
		
		// 加载审批单位下拉框。
		instance.sapprovalorgid.url = CTX+"/projectlib/getProjectApprovalOrgList";
		instance.sapprovalorgid.load({auditid: params});
	};
	
	/**
	 * 可研加载立项项目业主、代建单位绑定下拉框。
	 */
	this.loadFeasibilitySelectData = function(params){
		$.ajax({
			url : CTX+"/projectlib/getProjectOwnerOrConstruction",
			data : {auditid: params},
			success : function(result){
				instance.sproprietororgid_0.loadData(result.result);
				instance.sdeputyorgid_0.loadData(result.result);
			}
		});
		
		// 加载审批单位下拉框。
		instance.sapprovalorgid_0.url = CTX+"/projectlib/getProjectApprovalOrgList";
		instance.sapprovalorgid_0.load({auditid: params});
	};
	
	/**
	 * 初始化立项加载文件上传。
	 */
	this.initiApprovalFileUpload = function(){
		if(!this.proLibUpload){
			var options = {relativePath : Globle.constant.upload.approval, accept:{}, uploadFinished : function(datas,error) {
				console.log(datas,error)
				if(error){
					search.warn({content:"上传附件失败!"});
					return;
				}
				var deleteids = instance.proLibUpload.delArr;
				if (datas && datas.length > 0) {
					// 上传成功附件信息
					$.each(datas, function(i) {
						var filetotal = instance.formData.filetotal ? ++instance.formData.filetotal : 1;
						var fileData = {};
						instance.formData["filetotal"] = filetotal;
						fileData["Commonattachs[" + i + "].sname"] = this.sname;
						fileData["Commonattachs[" + i + "].spath"] = this.spath;
						fileData["Commonattachs[" + i + "].itype"] = 1;
						instance.formData = $.extend(instance.formData, fileData);
					})
				}

				if (deleteids && deleteids.length > 0) instance.formData = $.extend(instance.formData, { deleteids : deleteids.join(",") }); // 删除附件ID
				instance.saveApprovalForm();
			}};

			if (this.resultData && this.resultData.attachList && this.resultData.attachList.length > 0) {
				options = $.extend(options, { initFiles : { rootPath : NETWORK_ROOT, files : this.resultData.attachList }});
			}
			this.proLibUpload = Upload.upload(options);
		}
	};
	
	/**
	 * 初始化可研加载文件上传。
	 */
	this.initiFeasibilityFileUpload = function(){
		if(!this.bilityUpload){
			var options = {relativePath : Globle.constant.upload.feasibilitys, containerId:"uploaders", onePickerId:"filePickers", twoPickerId:"filePicker2s", accept:{}, uploadFinished : function(datas,error) {
				if(error){
					search.warn({content:"上传附件失败!"});
					return;
				}
				var deleteids = instance.bilityUpload.delArr;
				if (datas && datas.length > 0) {
					// 上传成功附件信息
					$.each(datas, function(i) {
						var filetotal = instance.form_bility_data.filetotal ? ++instance.form_bility_data.filetotal : 1;
						var fileData = {};
						instance.form_bility_data["filetotal"] = filetotal;
						fileData["Commonattachs[" + i + "].sname"] = this.sname;
						fileData["Commonattachs[" + i + "].spath"] = this.spath;
						fileData["Commonattachs[" + i + "].itype"] = 2;
						instance.form_bility_data = $.extend(instance.form_bility_data, fileData);
					})
				}

				if (deleteids && deleteids.length > 0) instance.form_bility_data = $.extend(instance.form_bility_data, { deleteids : deleteids.join(",") }); // 删除附件ID
				instance.saveFeasibilityForm();
			}};

			if (this.result_bility_Data && this.result_bility_Data.attachList && this.result_bility_Data.attachList.length > 0) {
				options = $.extend(options, { initFiles : { rootPath : NETWORK_ROOT, files : this.result_bility_Data.attachList }});
			}
			this.bilityUpload = Upload.upload(options);
		}
	};
	
	/**
	 * 立项根据项目id查询立项数据。
	 */
	this.loadApprovalObjData = function(params){
		$.ajax({
			url : CTX+"/projectlib/getApprovalObjBySid",
			data:{sid: params, itype: true},
			success : function(result){
				instance.approval_form.setData(result.result.mapdata);
				instance.resultData = result.result;
				instance._approval_sid = result.result.mapdata.sid;
				instance.initiApprovalFileUpload();
				//项目归属审计单位id。
				instance._auditorgid = result.result.mapdata.sauditorgid;
					
				instance._cont_appr = result.result.mapdata;
				instance._approval_attach = result.result.attachList;
				//清空删除ID数组。
				instance.proLibUpload.delArr.splice(0,instance.proLibUpload.delArr.length); 
				
				//admin 用户。
				if(_user_type){
					//赋值项目业主、代建单位。
					instance._select_sproprietororgid = result.result.mapdata.sproprietororgid;
					instance._select_sdeputyorgid = result.result.mapdata.sdeputyorgid;
					instance._select_sapprovalorgid = result.result.mapdata.sapprovalorgid;
					
					//加载项目业主、代建单位。
					instance.loadApprovalSelectData(_pro_lib_auditorgid);
				}
			}
		});
	};
	
	/**
	 * 可研根据项目id查询可研数据。
	 */
	this.loadFeasibilityObjData = function(params){
		$.ajax({
			url : CTX+"/projectlib/getFeasibilityObjBySid",
			data:{sid: params, itype: true},
			success : function(result){
				instance.bility_form.setData(result.result.mapdata);
				instance.result_bility_Data = result.result;
				instance._bility_sid = result.result.mapdata.sid_0;
				instance.initiFeasibilityFileUpload();
				
				instance._cont_bility = result.result.mapdata;
				instance._bility_attach = result.result.attachList;
				//清空删除ID数组。
				instance.bilityUpload.delArr.splice(0,instance.bilityUpload.delArr.length); 
				
				//admin 用户。
				if(_user_type){
					//赋值项目业主、代建单位。
					instance._select_sproprietororgid = result.result.mapdata.sproprietororgid_0;
					instance._select_sdeputyorgid = result.result.mapdata.sdeputyorgid_0;
					instance._select_sapprovalorgid = result.result.mapdata.sapprovalorgid_0;
					
					//加载项目业主、代建单位。
					instance.loadFeasibilitySelectData(_pro_lib_auditorgid);
				}
			}
		});
	};
	
	/**
	 * 保存立项表单数据
	 */
	this.saveApprovalForm = function(){
		$.post(CTX + "/projectlib/insertApprovals", instance.formData, function(result) {
			if(result.status){
				_prolib_id = result.result.pro_lib_sid;
				instance.loadApprovalObjData(_prolib_id);
				search.info({content: result.result.isSuccess});
			}else search.error({content:"系统错误!"});
		})
	};
	
	/**
	 * 保存可研表单数据。
	 */
	this.saveFeasibilityForm = function(){
		$.post(CTX + "/projectlib/insertFeasibilitys", instance.form_bility_data, function(result) {
			if(result.status){
				loadFeasibilitySelectData();
				search.info({content:result.result.isSuccess});
			}else search.error({content:"系统错误!"});
		})
	};
}

var instance = new UpdateProjectLidHandle();

/*----------------------------------立项下拉框加载完成后绑定值---------------------------------------------*/
/**
 * 项目归属下拉框加载完后绑定值。
 * @param e
 */
function onAuditOrgIdLoaded(e){
	if(instance._auditorgid != null && instance._auditorgid != "") search.get("sauditorgid").setValue(instance._auditorgid);
}

/**
 * 项目业主下拉框加载完后绑定值。
 * @param e
 */
function onProprietorOrgidLoaded(e){
	if(instance._select_sproprietororgid != null && instance._select_sproprietororgid != "") instance.sproprietororgid.setValue(instance._select_sproprietororgid);
}

/**
 * 审批单位下拉框加载完后绑定值。
 * @param e
 */
function onApprovalOrgidLoaded(e){
	if(instance._select_sapprovalorgid != null && instance._select_sapprovalorgid != "") instance.sapprovalorgid.setValue(instance._select_sapprovalorgid);
}

/**
 * 代建单位下拉框加载完后绑定值。
 * @param e
 */
function onDeputyOrgidLoaded(e){
	if(instance._select_sdeputyorgid != null && instance._select_sdeputyorgid != "") instance.sdeputyorgid.setValue(instance._select_sdeputyorgid);
}
/*----------------------------------立项下拉框加载完成后绑定值---------------------------------------------*/

/*----------------------------------可研下拉框加载完成后绑定值---------------------------------------------*/
/**
 * 项目业主下拉框加载完后绑定值。
 * @param e
 */
function onProprietorOrgidsLoaded(e){
	if(instance._select_sproprietororgid != null && instance._select_sproprietororgid != "") instance.sproprietororgid_0.setValue(instance._select_sproprietororgid);
}

/**
 * 审批单位下拉框加载完后绑定值。
 * @param e
 */
function onApprovalOrgidsLoaded(e){
	if(instance._select_sapprovalorgid != null && instance._select_sapprovalorgid != "") instance.sapprovalorgid_0.setValue(instance._select_sapprovalorgid);

}

/**
 * 代建单位下拉框加载完后绑定值。
 * @param e
 */
function onDeputyOrgidsLoaded(e){
	if(instance._select_sdeputyorgid != null && instance._select_sdeputyorgid != "") instance.sdeputyorgid_0.setValue(instance._select_sdeputyorgid);
}
/*----------------------------------可研下拉框加载完成后绑定值---------------------------------------------*/

/**
 * 保存立项数据。
 */
function saveApproval(){
	if(!instance.approval_form.validate())return;
	if(_user_type)instance.formData["auditid"] = search.get("sauditorgid").getValue();
	instance.formData = $.extend(instance.formData, instance.approval_form.getData());
	if(!instance.proLibUpload) instance.saveApprovalForm();
	else instance.proLibUpload.upload();
}

/**
 * 保存可研数据。
 */
function saveFeasibility(){
	if(!instance.bility_form.validate())return;
	instance.form_bility_data = $.extend(instance.form_bility_data, instance.bility_form.getData());
	if(!instance.bilityUpload) instance.saveFeasibilityForm();
	else instance.bilityUpload.upload();
}

/**
 * 立项调整数据。
 */
function adjustApprovals(){
	if(instance._approval_sid == null || instance._approval_sid == ""){
		search.warn({content:"暂无可调整!"});
		return;
	}
	var data = {
		url : CTX+"/projectlib/gotoAdjustApprOrFeasiPage?istate=1&sauditid=" + _pro_lib_auditorgid,
		title : "项目立项调整",
		width : 700,
		height : 650,
		onload : function(window){
			window.instance.loadApprovalData({sid: _prolib_id});
		},
		ondestroy : function(){
			instance.loadApprovalObjData(_prolib_id);
		}
	}
	top.search.popDialog(data);
}

/**
 * 立项调整历史。
 */
function getApprovalHistory(){
	if(instance._approval_sid == null || instance._approval_sid == ""){
		search.warn({content:"暂无可调整!"});
		return;
	}
	var data = {
		url : CTX+"/projectlib/gotoAdjustmenHistoryPage",
		title : "立项调整历史",
		width : 600,
		height : 450,
		onload : function(window){
			window.instance.loadHistoryData({sid: instance._approval_sid});
		},
		ondestroy : function(){
			instance.loadApprovalObjData(_prolib_id);
		}
	}
	top.search.popDialog(data);
}

/**
 * 可研调整数据。
 */
function adjustFeasibilitys(){
	if(instance._bility_sid == null || instance._bility_sid == ""){
		search.warn({content:"暂无可调整!"});
		return;
	}
	var data = {
		url : CTX+"/projectlib/gotoAdjustApprOrFeasiPage?istate=2&sauditid=" + _pro_lib_auditorgid,
		title : "项目可研调整",
		width : 700,
		height : 650,
		onload : function(window){
			window.instance.loadFeasibilityData({sid: _prolib_id});
		},
		ondestroy : function(){
			loadFeasibilitySelectData();
		}
	}
	top.search.popDialog(data);
}

/**
 * 可研调整历史。
 */
function getFeasibilityHistory(){
	if(instance._bility_sid == null || instance._bility_sid == ""){
		search.warn({content:"暂无可调整!"});
		return;
	}
	var data = {
		url : CTX+"/projectlib/gotoAdjustmenHistoryPage",
		title : "可研调整历史",
		width : 600,
		height : 450,
		onload : function(window){
			window.instance.loadHistoryData({sid: instance._bility_sid});
		},
		ondestroy : function(){
			loadFeasibilitySelectData();
		}
	}
	top.search.popDialog(data);
}

/**
 * 返回列表。
 */
function goBack(){
	window.location.href = CTX + "/projectlib/gotoProjectLibMgrPage?id=" + __funcid;
}

/**
 * 项目立项归属审计机构联动。
 */
function onApprovalValueChanged(e){
	instance.loadApprovalSelectData(e.value);
	_pro_lib_auditorgid = e.value;
}

/**
 * 验证项目业主。
 * @param e
 */
function proprietororgValidate(e){
	if(e.value == '-1'){
		e.message = '请选择';
		e.pass = false;
	}
}

/**
 * 页面加载完加载。
 */
$(function(){
	instance.appr_ipros_type.loadData(Globle.constant.ApprovalApproved);
	instance.appr_ipros_type.setValue(Globle.constant.ApprovalApproved[0].value);
	instance.appr_idesi_type.loadData(Globle.constant.ApprovalApproved);
	instance.appr_idesi_type.setValue(Globle.constant.ApprovalApproved[0].value);
	instance.appr_isupe_type.loadData(Globle.constant.ApprovalApproved);
	instance.appr_isupe_type.setValue(Globle.constant.ApprovalApproved[0].value);
	instance.appr_icons_type.loadData(Globle.constant.ApprovalApproved);
	instance.appr_icons_type.setValue(Globle.constant.ApprovalApproved[0].value);
	instance.appr_iinte_type.loadData(Globle.constant.ApprovalApproved);
	instance.appr_iinte_type.setValue(Globle.constant.ApprovalApproved[0].value);
	if(_user_type) instance.loadProAuditOrgSelectData();//admin账户
	else instance.loadApprovalSelectData();
	if(_prolib_id == "")instance.initiApprovalFileUpload(); // 初始化上传。
	else instance.loadApprovalObjData(_prolib_id);
})

/**
 * 对比立项数据。
 */
function contrastApprovalData(){
	var page_attach_length = $("#uploader ul li").length;
	var tab_form = instance.approval_form.getData();
	var _istate_flag = false;
	if(tab_form.sname != instance._cont_appr.sname) _istate_flag = true;
	if(tab_form.sproprietororgid != instance._cont_appr.sproprietororgid) _istate_flag = true;
	if(tab_form.sapprovalorgid != (instance._cont_appr.sapprovalorgid == null?"":instance._cont_appr.sapprovalorgid)) _istate_flag = true;
	if(tab_form.sdeputyorgid != (instance._cont_appr.sdeputyorgid == null?"":instance._cont_appr.sdeputyorgid)) _istate_flag = true;
	if(tab_form.sapprovalnum != (instance._cont_appr.sapprovalnum == null?"":instance._cont_appr.sapprovalnum)) _istate_flag = true;
	if(tab_form.saddress != (instance._cont_appr.saddress == null?"":instance._cont_appr.saddress)) _istate_flag = true;
	if(tab_form.ldapprovaldate != (instance._cont_appr.ldapprovaldate == null?"":instance._cont_appr.ldapprovaldate)) _istate_flag = true;
	if(tab_form.scapitalsource != (instance._cont_appr.scapitalsource == null?"":instance._cont_appr.scapitalsource)) _istate_flag = true;
	if(tab_form.destimateamount != (instance._cont_appr.destimateamount == null?"":instance._cont_appr.destimateamount)) _istate_flag = true;
	if((tab_form.iprospectingtype == "-1"?"":tab_form.iprospectingtype) != (instance._cont_appr.iprospectingtype == null?"":instance._cont_appr.iprospectingtype)) _istate_flag = true;
	if((tab_form.idesigntype == "-1"?"":tab_form.idesigntype) != (instance._cont_appr.idesigntype == null?"":instance._cont_appr.idesigntype)) _istate_flag = true;
	if((tab_form.isupervisiontype == "-1"?"":tab_form.isupervisiontype) != (instance._cont_appr.isupervisiontype == null?"":instance._cont_appr.isupervisiontype)) _istate_flag = true;
	if((tab_form.iconstructiontype == "-1"?"":tab_form.iconstructiontype) != (instance._cont_appr.iconstructiontype == null?"":instance._cont_appr.iconstructiontype)) _istate_flag = true;
	if((tab_form.iintermediarytype == "-1"?"":tab_form.iintermediarytype) != (instance._cont_appr.iintermediarytype == null?"":instance._cont_appr.iintermediarytype)) _istate_flag = true;
	if(tab_form.sdesc != (instance._cont_appr.sdesc == null?"":instance._cont_appr.sdesc)) _istate_flag = true;
	if(page_attach_length != instance._approval_attach.length || (instance.proLibUpload.delArr && instance.proLibUpload.delArr.length > 0)) _istate_flag = true;
	return _istate_flag;
}

/**
 * 对比可研数据。
 */
function contrastFeasibilityData(){
	var page_attach_length = $("#uploaders ul li").length;
	var tab_form = instance.bility_form.getData();
	var _istate_flag = false;
	if(tab_form.sname_0 != instance._cont_bility.sname_0) _istate_flag = true;
	if(tab_form.sproprietororgid_0 != instance._cont_bility.sproprietororgid_0) _istate_flag = true;
	if(tab_form.sapprovalorgid_0 != (instance._cont_bility.sapprovalorgid_0 == null?"":instance._cont_bility.sapprovalorgid_0)) _istate_flag = true;
	if(tab_form.sdeputyorgid_0 != (instance._cont_bility.sdeputyorgid_0 == null?"":instance._cont_bility.sdeputyorgid_0)) _istate_flag = true;
	if(tab_form.sapprovalnum_0 != (instance._cont_bility.sapprovalnum_0 == null?"":instance._cont_bility.sapprovalnum_0)) _istate_flag = true;
	if(tab_form.saddress_0 != (instance._cont_bility.saddress_0 == null?"":instance._cont_bility.saddress_0)) _istate_flag = true;
	if(tab_form.ldapprovaldate_0 != (instance._cont_bility.ldapprovaldate_0 == null?"":instance._cont_bility.ldapprovaldate_0)) _istate_flag = true;
	if(tab_form.scapitalsource_0 != (instance._cont_bility.scapitalsource_0 == null?"":instance._cont_bility.scapitalsource_0)) _istate_flag = true;
	if(tab_form.destimateamount_0 != (instance._cont_bility.destimateamount_0 == null?"":instance._cont_bility.destimateamount_0)) _istate_flag = true;
	if((tab_form.iprospectingtype_0 == "-1"?"":tab_form.iprospectingtype_0) != (instance._cont_bility.iprospectingtype_0 == null?"":instance._cont_bility.iprospectingtype_0)) _istate_flag = true;
	if((tab_form.idesigntype_0 == "-1"?"":tab_form.idesigntype_0) != (instance._cont_bility.idesigntype_0 == null?"":instance._cont_bility.idesigntype_0)) _istate_flag = true;
	if((tab_form.isupervisiontype_0 == "-1"?"":tab_form.isupervisiontype_0) != (instance._cont_bility.isupervisiontype_0 == null?"":instance._cont_bility.isupervisiontype_0)) _istate_flag = true;
	if((tab_form.iconstructiontype_0 == "-1"?"":tab_form.iconstructiontype_0) != (instance._cont_bility.iconstructiontype_0 == null?"":instance._cont_bility.iconstructiontype_0)) _istate_flag = true;
	if((tab_form.iintermediarytype_0 == "-1"?"":tab_form.iintermediarytype_0) != (instance._cont_bility.iintermediarytype_0 == null?"":instance._cont_bility.iintermediarytype_0)) _istate_flag = true;
	if(tab_form.sdesc_0 != (instance._cont_bility.sdesc_0 == null?"":instance._cont_bility.sdesc_0)) _istate_flag = true;
	if(tab_form.ldstartdate_0 != (instance._cont_bility.ldstartdate_0 == null?"":instance._cont_bility.ldstartdate_0)) _istate_flag = true;
	if(tab_form.ldenddate_0 != (instance._cont_bility.ldenddate_0 == null?"":instance._cont_bility.ldenddate_0)) _istate_flag = true;
	if((instance._bility_attach != null && page_attach_length != instance._bility_attach.length) || (instance.bilityUpload.delArr && instance.bilityUpload.delArr.length > 0)) _istate_flag = true;
	return _istate_flag;
}

/**
 * 加载可研下拉框值。
 */
function loadFeasibilitySelectData(){
	instance.loadFeasibilitySelectData(_pro_lib_auditorgid);
	
	instance.appr_ipros_type_0.loadData(Globle.constant.ApprovalApproved);
	instance.appr_ipros_type_0.setValue(Globle.constant.ApprovalApproved[0].value);
	instance.appr_idesi_type_0.loadData(Globle.constant.ApprovalApproved);
	instance.appr_idesi_type_0.setValue(Globle.constant.ApprovalApproved[0].value);
	instance.appr_isupe_type_0.loadData(Globle.constant.ApprovalApproved);
	instance.appr_isupe_type_0.setValue(Globle.constant.ApprovalApproved[0].value);
	instance.appr_icons_type_0.loadData(Globle.constant.ApprovalApproved);
	instance.appr_icons_type_0.setValue(Globle.constant.ApprovalApproved[0].value);
	instance.appr_iinte_type_0.loadData(Globle.constant.ApprovalApproved);
	instance.appr_iinte_type_0.setValue(Globle.constant.ApprovalApproved[0].value);
	instance.loadFeasibilityObjData(_prolib_id);
	
}

/**
 * 切换标题栏。
 */
$('#construction').on('click', 'li', function () {
    var $li = $(this), index = $li.index(), $constructionBd = $('#construction-bd').children().eq(index);
    if(instance._li_index == 0){ /*对比立项数据。*/
    	if(instance._approval_sid == null || instance._approval_sid == ""){
    		search.warn({content:"请先添加项目立项数据!"});
    		return;
    	}else{
    		if(contrastApprovalData()){
        		search.warn({content:"请先保存数据!"});
        		return;
        	}
    	}
    	instance._li_index = $li.index();
    }else if(instance._li_index == 1){ /*对比可研*/
    	if(contrastFeasibilityData()){
    		search.warn({content:"请先保存数据!"});
    		return;
    	}
    	instance._li_index = $li.index();
    }else instance._li_index = $li.index();
    $li.addClass('active').siblings().removeClass('active');
    $constructionBd.addClass('thisclass').siblings().removeClass('thisclass');
    if($li.index() == 0) instance.loadApprovalObjData(_prolib_id);
    else if($li.index() == 1) loadFeasibilitySelectData();
    else if($li.index() == 2) {
    	calculation.grid.doLayout();
    	calculation.loadCalculationMgrData();
    }else if($li.index() == 3){
    	budget.grid.doLayout();
    	budget.loadBudgetMgrData();
    }else if($li.index() == 4){
    	bid.grid.doLayout();
    	bid.loadBidMgrData();
    }
    
});

/*---------------------------------项目库立项和可研结束------------------------------------------*/

/*-------------------------------------项目库概算开始--------------------------------------------*/
/**
 * 概算控制器。
 */
function CalculationMgrHandle(){
	this.grid = search.get("CalculationGrid");

	this.loadCalculationMgrData = function(){
		this.grid.url = CTX + "/calculat/getCalculationList";
		this.grid.load({sid: _prolib_id});
	};
	
	/**
	 * 新增概算。
	 */
	this.addCalculat = function(){
		var data = {
			url : CTX+"/calculat/gotoUpdateCalculationPage?sauditid=" + _pro_lib_auditorgid,
			title : "新增",
			width : 750,
			height : 500,
			onload : function(window){
				window.instance.loadCalculationData({action:"add", sid: _prolib_id});
			},
			ondestroy : function(){
				calculation.loadCalculationMgrData();
			}
		}
		top.search.popDialog(data);
	};
	
	/**
	 * 编辑。
	 */
	this.editCalculat = function(params){
		var data = {
			url : CTX+"/calculat/gotoUpdateCalculationPage?sauditid=" + _pro_lib_auditorgid,
			title : "编辑",
			width : 650,
			height : 500,
			onload : function(window){
				window.instance.loadCalculationData({action:"edit", sid: params});
			},
			ondestroy : function(){
				calculation.loadCalculationMgrData();
			}
		}
		top.search.popDialog(data);
	};
	
	/**
	 * 删除。
	 */
	this.delCalculat = function(params){
		search.confirm({ content : "确定要删除?", funl : function() {
		 　$.ajax({
				url : CTX+"/calculat/updateCalculatState",
				data : {sid: params, istate: 99},
				success : function(result){
					if(result.status){
						search.info({content:result.result.meg,funl:function(){
							calculation.loadCalculationMgrData();
						}});
					}else search.error({content:"系统错误!"});
				}
			});
		   }
		})
	};
	

	/**
	 * 关键字查询。
	 */
	this.queryCalculation = function(){
		var keyword = search.get("calculationkeyword").getValue();
		this.grid.load({sid: _prolib_id, keyword : keyword});
	};

}

/**
 * 实例化概算控制器。
 */
var calculation = new CalculationMgrHandle();

/**
 * 查看概算详情。
 */
function queryCalculDetail(params){
	var sid = "";
	if(typeof params === 'object') sid = params.origin.record.sid;
	else sid = params;
	top.search.popDialog({ url : CTX + "/calculat/gotoCalculationViewPage", title : "详情", width : 750, height : 500,
		onload : function(window) {
			window.instance.loadData({sid: sid});
		}, ondestroy : function(result){}
	});
};

/**
 * 绘制概算列表单元格。
 * @param e
 */
function onCalculatDrawCell(e){
	var html = "";
	switch (e.column.field) {
	    case "sname":
	    	html = "<span style='color: blue;' onclick='calculation.editCalculat(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	break;
		case "operation":
				html = '<a class="del" href="javascript:void(0);" onclick="calculation.delCalculat(\'' + e.record.sid + '\');">删除</a>';
			break;
	}
	if(html)e.html = html;
}

/*-------------------------------------项目库概算结束--------------------------------------------*/

/*-------------------------------------项目库预算开始--------------------------------------------*/
/**
 * 预算控制器。
 */
function BudgetMgrHandle(){
	this.grid = search.get("BudgetGrid");

	/**
	 * 加载预算数据。
	 */
	this.loadBudgetMgrData = function(){
		this.grid.url = CTX + "/budget/getBudgetList";
		this.grid.load({sid: _prolib_id});
	};
	
	/**
	 * 新增。
	 */
	this.addBudget = function(){
		var data = {
			url : CTX+"/budget/gotoUpdateBudgetPage?sauditid=" + _pro_lib_auditorgid,
			title : "新增",
			width : 650,
			height : 500,
			onload : function(window){
				window.instance.loadBudgetData({action:"add", sid: _prolib_id});
			},
			ondestroy : function(){
				budget.loadBudgetMgrData();
			}
		}
		top.search.popDialog(data);
	};
	
	/**
	 * 编辑。
	 */
	this.editBudget = function(params){
		var data = {
			url : CTX+"/budget/gotoUpdateBudgetPage?sauditid=" + _pro_lib_auditorgid,
			title : "编辑",
			width : 650,
			height : 500,
			onload : function(window){
				window.instance.loadBudgetData({action:"edit", sid: params});
			},
			ondestroy : function(){
				budget.loadBudgetMgrData();
			}
		}
		top.search.popDialog(data);
	};
	
	/**
	 * 删除。
	 */
	this.delBudget = function(){
		search.confirm({ content : "确定要删除?", funl : function() {
		 　$.ajax({
				url : CTX+"/budget/updateBudgetState",
				data : {sid: params, istate: 99},
				success : function(result){
					if(result.status){
						search.info({content:result.result.meg,funl:function(){
							bid.loadBidMgrData();
						}});
					}else search.error({content:"系统错误!"});
				}
			});
		   }
		})
	};
	
	/**
	 * 关键字查询。
	 */
	this.queryBudget = function(){
		var keyword = search.get("budgetkeyword").getValue();
		this.grid.load({sid: _prolib_id, keyword : keyword});
	};
	
}

/**
 * 实例化预算。
 */
var budget = new BudgetMgrHandle();

/**
 * 查看预算详情。
 */
function queryBudgetDetail(params){
	var sid = "";
	if(typeof params === 'object') sid = params.origin.record.sid;
	else sid = params;
	top.search.popDialog({ url : CTX + "/budget/gotoBudgetViewPage", title : "详情", width : 650, height : 500,
		onload : function(window) {
			window.instance.loadData({sid: sid});
		}, ondestroy : function(result){}
	});
}

/**
 * 绘制预算列表单元格。
 * @param e
 */
function onBudgetDrawCell(e){
	var html = "";
	switch (e.column.field) {
	    case "sname":
	    	html = "<span style='color: blue;' onclick='budget.editBudget(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	break;
		case "operation":
				html = '<a class="del" href="javascript:void(0);" onclick="budget.delBudget(\'' + e.record.sid + '\');">删除</a>';
			break;
	}
	if(html)e.html = html;
}
/*-------------------------------------项目库预算结束--------------------------------------------*/

/*------------------------------------项目库招投标开始-------------------------------------------*/
/**
 * 施工招投标控制器。
 */
function BidMgrHandle(){
	this.grid = search.get("BidGrid");

	/**
	 * 加载招投标数据。
	 */
	this.loadBidMgrData = function(){
		this.grid.url = CTX + "/bid/getBidsPage";
		this.grid.load({sid: _prolib_id});
	};
	
	/**
	 * 招标方式。
	 */
	this.getBiddingTypeText= function(type){
		var itype= Globle.constant.iBiddingType;
		var text = "";
		for(var i = 0 ; i < itype.length; i++){
			if(type == itype[i].value) text = itype[i].text;
		}
		
		return text;
	};
	
	/**
	 * 新增招投标。
	 */
	this.addBid = function(){
		var data = {
			url : CTX+"/bid/gotoUpdateBidPage?sauditid=" + _pro_lib_auditorgid,
			title : "新增",
			width : 650,
			height : 500,
			onload : function(window){
				window.instance.loadBudgetData({action:"add", sid: _prolib_id});
			},
			ondestroy : function(){
				bid.loadBidMgrData();
			}
		}
		top.search.popDialog(data);
	};
	
	/**
	 * 编辑招投标。
	 */
	this.editBid = function(params){
		var data = {
			url : CTX+"/bid/gotoUpdateBidPage?sauditid=" + _pro_lib_auditorgid,
			title : "编辑",
			width : 650,
			height : 500,
			onload : function(window){
				window.instance.loadBudgetData({action:"edit", sid: params});
			},
			ondestroy : function(){
				bid.loadBidMgrData();
			}
		}
		top.search.popDialog(data);
	};
	
	/**
	 * 删除招投标。
	 */
	this.delBid = function(params){
		search.confirm({ content : "确定要删除?", funl : function() {
		 　$.ajax({
				url : CTX+"/bid/updateBidState",
				data : {sid: params, istate: 99},
				success : function(result){
					if(result.status){
						search.info({content:result.result.meg,funl:function(){
							bid.loadBidMgrData();
						}});
					}else search.error({content:"系统错误!"});
				}
			});
		   }
		})
	};
	
	/**
	 * 关键字查询。
	 */
	this.queryBid = function(){
		var keyword = search.get("bidkeyword").getValue();
		this.grid.load({sid: _prolib_id, keyword : keyword});
	};
	
}

/**
 * 实例化招投标。
 */
var bid = new BidMgrHandle();

/**
 * 查看详情。
 */
function queryBidDetail(params){
	var sid = "";
	if(typeof params === 'object') sid = params.origin.record.sid;
	else sid = params;
	top.search.popDialog({ url : CTX + "/bid/gotoBidViewPage", title : "详情", width : 650, height : 500,
		onload : function(window) {
			window.instance.loadData({sid: sid});
		}, ondestroy : function(result){}
	});
}

/**
 * 绘制招投标列表单元格。
 * @param e
 */
function onBidDrawCell(e){
	var html = "";
	switch (e.column.field) {
	    case "sname":
	    	html = "<span style='color: blue;' onclick='bid.editBid(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	break;
	    case "ibiddingtype":
	    	if(e.record.ibiddingtype != null && e.record.ibiddingtype != "") html = bid.getBiddingTypeText(e.record.ibiddingtype);
	    	break;
		case "operation":
				html = '<a class="del" href="javascript:void(0);" onclick="bid.delBid(\'' + e.record.sid + '\');">删除</a>';
			break;
	}
	if(html)e.html = html;
}
/*------------------------------------项目库招投标结束-------------------------------------------*/