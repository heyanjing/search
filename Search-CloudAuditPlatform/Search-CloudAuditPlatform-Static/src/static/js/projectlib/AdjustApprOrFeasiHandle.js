search.parse();

/**
 * 实例控制器。
 */
function AdjustApprOrFeasiHandle(){
	this.approval_form = new Form("ApprovalForm");
	this.appr_ipros_type = search.get("iprospectingtype");
	this.appr_idesi_type = search.get("idesigntype");
	this.appr_isupe_type = search.get("isupervisiontype");
	this.appr_icons_type = search.get("iconstructiontype");
	this.appr_iinte_type = search.get("iintermediarytype");
	this.sproprietororgid = search.get("sproprietororgid");
	this.sdeputyorgid = search.get("sdeputyorgid");
	this.sapprovalorgid = search.get("sapprovalorgid");
	this.table_data = {};
	this.formData = {},this.proLibUpload, this.resultData;
	
	/**
	 * 加载立项项目业主、代建单位绑定下拉框。
	 */
	this.loadApprovalSelectData = function(params){
		$.ajax({
			url : CTX+"/projectlib/getProjectOwnerOrConstruction",
			data : {auditid: params},
			success : function(result){
				instance.sproprietororgid.loadData(result.result);
				instance.sdeputyorgid.loadData(result.result);
			}
		});
	}
	
	/**
	 * 招标核准。
	 */
	this.getBidApproved = function(way){
		var itype= Globle.constant.ApprovalApproved;
		var text = "";
		for(var i = 0 ; i < itype.length; i++){
			if(way == itype[i].value) text = itype[i].text;
		}
		return text;
	};
	
	/**
	 * 页面加载数据。
	 */
	this.loadApprovalData = function(data){
		$.ajax({
			url : CTX+"/projectlib/getApprovalObjBySid",
			data:{sid: data.sid, itype: false},
			success : function(result){
				instance.table_data = result.result.mapdata;
				instance.approval_form.setData(result.result.mapdata);
				$("#appr-sname").text(instance.table_data.sname == null?"":instance.table_data.sname);
				$("#appr-ownername").text(instance.table_data.ownername == null?"":instance.table_data.ownername);
				$("#appr-constname").text(instance.table_data.constname == null?"":instance.table_data.constname);
				$("#appr-auditname").text(instance.table_data.auditname == null?"":instance.table_data.auditname);
				$("#appr-sapprovalnum").text(instance.table_data.sapprovalnum == null?"":instance.table_data.sapprovalnum);
				$("#appr-saddress").text(instance.table_data.saddress == null?"":instance.table_data.saddress);
				$("#appr-ldapprovaldate").text(instance.table_data.ldapprovaldate == null?"":instance.table_data.ldapprovaldate);
				$("#appr-scapitalsource").text(instance.table_data.scapitalsource == null?"":instance.table_data.scapitalsource);
				$("#appr-destimateamount").text(instance.table_data.destimateamount == null?"":instance.table_data.destimateamount);
				$("#appr-iprospectingtype").text(instance.table_data.iprospectingtype == null?"":instance.getBidApproved(instance.table_data.iprospectingtype));
				$("#appr-idesigntype").text(instance.table_data.idesigntype == null?"":instance.getBidApproved(instance.table_data.idesigntype));
				$("#appr-isupervisiontype").text(instance.table_data.isupervisiontype == null?"":instance.getBidApproved(instance.table_data.isupervisiontype));
				$("#appr-iconstructiontype").text(instance.table_data.iconstructiontype == null?"":instance.getBidApproved(instance.table_data.iconstructiontype));
				$("#appr-iintermediarytype").text(instance.table_data.iintermediarytype == null?"":instance.getBidApproved(instance.table_data.iintermediarytype));
				$("#appr-sdesc").text(instance.table_data.sdesc == null?"":instance.table_data.sdesc);
				
				instance.resultData = result.result;
			}
		});
	};
	
	/**
	 * 查询可研数据。
	 */
	this.loadFeasibilityData = function(data){
		$.ajax({
			url : CTX+"/projectlib/getFeasibilityObjBySid",
			data:{sid: data.sid, itype: false},
			success : function(result){
				instance.table_data = result.result.mapdata;
				instance.approval_form.setData(result.result.mapdata);
				$("#appr-sname").text(instance.table_data.sname == null?"":instance.table_data.sname);
				$("#appr-ownername").text(instance.table_data.ownername == null?"":instance.table_data.ownername);
				$("#appr-constname").text(instance.table_data.constname == null?"":instance.table_data.constname);
				$("#appr-auditname").text(instance.table_data.auditname == null?"":instance.table_data.auditname);
				$("#appr-sapprovalnum").text(instance.table_data.sapprovalnum == null?"":instance.table_data.sapprovalnum);
				$("#appr-saddress").text(instance.table_data.saddress == null?"":instance.table_data.saddress);
				$("#appr-ldapprovaldate").text(instance.table_data.ldapprovaldate == null?"":instance.table_data.ldapprovaldate);
				$("#appr-scapitalsource").text(instance.table_data.scapitalsource == null?"":instance.table_data.scapitalsource);
				$("#appr-destimateamount").text(instance.table_data.destimateamount == null?"":instance.table_data.destimateamount);
				$("#appr-iprospectingtype").text(instance.table_data.iprospectingtype == null?"":instance.getBidApproved(instance.table_data.iprospectingtype));
				$("#appr-idesigntype").text(instance.table_data.idesigntype == null?"":instance.getBidApproved(instance.table_data.idesigntype));
				$("#appr-isupervisiontype").text(instance.table_data.isupervisiontype == null?"":instance.getBidApproved(instance.table_data.isupervisiontype));
				$("#appr-iconstructiontype").text(instance.table_data.iconstructiontype == null?"":instance.getBidApproved(instance.table_data.iconstructiontype));
				$("#appr-iintermediarytype").text(instance.table_data.iintermediarytype == null?"":instance.getBidApproved(instance.table_data.iintermediarytype));
				$("#appr-sdesc").text(instance.table_data.sdesc == null?"":instance.table_data.sdesc);
				$("#appr-ldstartdate").text(instance.table_data.ldstartdate == null?"":instance.table_data.ldstartdate);
				$("#appr-ldenddate").text(instance.table_data.sdesc == null?"":instance.table_data.ldenddate);
				
				instance.resultData = result.result;
			}
		});
	}
	
	/**
	 * 初始化立项加载文件上传。
	 */
	this.initiApprovalFileUpload = function(){
		if(!this.proLibUpload){
			var options = {relativePath : Globle.constant.upload.approval, accept:{}, uploadFinished : function(datas) {
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
				instance.saveForm();
			}};

			if (this.resultData && this.resultData.attachList && this.resultData.attachList.length > 0) {
				options = $.extend(options, { initFiles : { rootPath : NETWORK_ROOT, files : this.resultData.attachList }});
			}
			this.proLibUpload = Upload.upload(options);
		}
	};
	
	/**
	 * 组装调整内容。
	 */
	this.sContent = function(tltie, tab_param, lib_param){
		var tab_str = tab_param == null?"": tab_param, lib_str = lib_param == null?"":lib_param;
		var text = tltie + ": 由 <span style='color: green;'>“"+ lib_str +"”</span>调整为 <span style='color: blue;'>“"+ tab_str +"”</span>";
		return text;
	};
	
	/**
	 * 保存数据。
	 */
	this.saveForm = function(){
		var table_Form = instance.approval_form.getData();
		var history_Arr = new Array();
		if(table_Form.sname != this.table_data.sname){
			history_Arr.push(this.sContent("项目名称", table_Form.sname, this.table_data.sname));
		}
		if(table_Form.sproprietororgid != this.table_data.sproprietororgid){
			var str = "";
			if(table_Form.sproprietororgid != "-1") str = this.sproprietororgid.getText();
			history_Arr.push(this.sContent("项目业主", str, this.table_data.ownername));
		}
		if(table_Form.sapprovalorgid != this.table_data.sapprovalorgid){
			var str = "";
			if(table_Form.sapprovalorgid != "-1") str = this.sapprovalorgid.getText();
			history_Arr.push(this.sContent("审批单位", str, this.table_data.auditname));
		}
		if(table_Form.sdeputyorgid != this.table_data.sdeputyorgid){
			var str = "";
			if(table_Form.sdeputyorgid != "-1") str = this.sdeputyorgid.getText();
			history_Arr.push(this.sContent("代建单位 ", str, this.table_data.constname));
		}
		if(table_Form.sapprovalnum != this.table_data.sapprovalnum){
			history_Arr.push(this.sContent("审批文号", table_Form.sapprovalnum, this.table_data.sapprovalnum));
		}
		if(table_Form.saddress != this.table_data.saddress){
			history_Arr.push(this.sContent("项目地址", table_Form.saddress, this.table_data.saddress));
		}
		if(table_Form.ldapprovaldate != (this.table_data.ldapprovaldate == null?"":this.table_data.ldapprovaldate)){
			history_Arr.push(this.sContent("审批时间", table_Form.ldapprovaldate, this.table_data.ldapprovaldate));
		}
		if(table_Form.scapitalsource != this.table_data.scapitalsource){
			history_Arr.push(this.sContent("资金来源", table_Form.scapitalsource, this.table_data.scapitalsource));
		}
		if(table_Form.destimateamount != this.table_data.destimateamount){
			history_Arr.push(this.sContent("项目估算总金额(万元)", table_Form.destimateamount, this.table_data.destimateamount));
		}
		if(table_Form.iprospectingtype != this.table_data.iprospectingtype){
			var str = "",  lib_str = this.table_data.iprospectingtype == null?"":"勘察(" + this.getBidApproved(this.table_data.iprospectingtype) + ")";
			if(table_Form.iprospectingtype != "-1") str = "勘察(" + this.getBidApproved(this.appr_ipros_type.getValue()) + ")";
			history_Arr.push(this.sContent("招标核准", str, lib_str));
		}
		if(table_Form.idesigntype != this.table_data.idesigntype){
			var str = "", lib_str = this.table_data.idesigntype == null?"":"设计(" + this.getBidApproved(this.table_data.idesigntype) + ")";
			if(table_Form.idesigntype != "-1") str = "设计(" + this.getBidApproved(this.appr_idesi_type.getValue()) + ")";
			history_Arr.push(this.sContent("招标核准", str, lib_str));
		}
		if(table_Form.isupervisiontype != this.table_data.isupervisiontype){
			var str = "", lib_str = this.table_data.isupervisiontype == null?"":"监理(" + this.getBidApproved(this.table_data.isupervisiontype) + ")";
			if(table_Form.isupervisiontype != "-1") str = "监理(" + this.getBidApproved(this.appr_isupe_type.getValue()) + ")";
			history_Arr.push(this.sContent("招标核准", str, lib_str));
		}
		if(table_Form.iconstructiontype != this.table_data.iconstructiontype){
			var str = "", lib_str = this.table_data.iconstructiontype == null?"":"施工(" + this.getBidApproved(this.table_data.iconstructiontype) + ")";
			if(table_Form.iconstructiontype != "-1") str = "施工(" + this.getBidApproved(this.appr_icons_type.getValue()) + ")";
			history_Arr.push(this.sContent("招标核准", str, lib_str));
		}
		if(table_Form.iintermediarytype != this.table_data.iintermediarytype){
			var str = "", lib_str = this.table_data.iintermediarytype == null?"": "咨询(" + this.getBidApproved(this.table_data.iintermediarytype) + ")";
			if(table_Form.iintermediarytype != "-1") str = "咨询(" + this.getBidApproved(this.appr_iinte_type.getValue()) + ")";
			history_Arr.push(this.sContent("招标核准", str, lib_str));
		}
		if(table_Form.sdesc != this.table_data.sdesc){
			history_Arr.push(this.sContent("建设规模和内容", table_Form.sdesc, this.table_data.sdesc));
		}
		if(__pagestate == "2"){
			if(table_Form.ldstartdate != (this.table_data.ldstartdate == null?"":this.table_data.ldstartdate)){
				history_Arr.push(this.sContent("计划开工时间", table_Form.ldstartdate, this.table_data.ldstartdate));
			}
			
			if(table_Form.ldenddate != (this.table_data.ldenddate == null?"":this.table_data.ldenddate)){
				history_Arr.push(this.sContent("计划竣工时间", table_Form.ldenddate, this.table_data.ldenddate));
			}
		}
		
		if(history_Arr.length == 0){
			search.warn({content: "暂无调整数据!"})
			return;
		}
		if(__pagestate == "2"){
			this.formData = $.extend(this.formData, {scontent : history_Arr});
			$.post(CTX + "/projectlib/insertBilityHistorys", instance.formData, function(result) {
				if(result.status){
					search.info({content: result.result.isSuccess,funl:function(){
						if(result.result.state) window.closeWindow();
					}});
				}else search.error({content:"系统错误!"});
			});
		}else{
			this.formData = $.extend(this.formData, {scontent : history_Arr});
			$.post(CTX + "/projectlib/insertApprovalHistorys", instance.formData, function(result) {
				if(result.status){
					search.info({content: result.result.isSuccess,funl:function(){
						if(result.result.state) window.closeWindow();
					}});
				}else search.error({content:"系统错误!"});
			});
		}
	};
}

var instance = new AdjustApprOrFeasiHandle();

/**
 * 保存调整数据。
 */
function saveData(){
	if(!instance.approval_form.validate())return;
	instance.formData = $.extend(instance.formData, instance.approval_form.getData());
	if(!instance.proLibUpload) instance.saveForm();
	else instance.proLibUpload.upload();
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
 * 关闭窗口。
 */
function windowClose(){
	window.closeWindow();
}

/**
 * 页面加载完执行。
 */
$(function (){
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
	
	instance.loadApprovalSelectData(__sauditid);
	
	instance.sapprovalorgid.url = CTX+"/projectlib/getProjectApprovalOrgList";
	instance.sapprovalorgid.load({auditid: __sauditid});
});

$('#table-construction').on('click', 'li', function () {
    var $li = $(this), index = $li.index(), $constructionBd = $('#table-construction-bd').children().eq(index);
    $li.addClass('table-active').siblings().removeClass('table-active');
    $constructionBd.addClass('table-thisclass').siblings().removeClass('table-thisclass');
    if($li.index() == 1) instance.initiApprovalFileUpload();
});