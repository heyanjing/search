search.parse();

function UpdateBudgetHandle(){
	this._budge_form = new Form("BudgeForm");
	this.sproprietororgid = search.get("sproprietororgid");
	this.sdeputyorgid = search.get("sdeputyorgid");
	this.sapprovalorgid = search.get("sapprovalorgid");
	this.formData = {},this.proLibUpload, this.resultData;
	this._tr_row_id = 1;
	this._del_cont = 0;
	
	/**
	 * 加载项目业主、代建单位、审批单位。
	 */
	this.loadSelectData = function(params){
		$.ajax({
			url : CTX+"/projectlib/getProjectOwnerOrConstruction",
			data : {auditid: params},
			success : function(result){
				instance.sproprietororgid.loadData(result.result);
				instance.sdeputyorgid.loadData(result.result);
			}
		});
		
		//加载审批单位。
		instance.sapprovalorgid.url = CTX+"/projectlib/getProjectApprovalOrgList";
		instance.sapprovalorgid.load({auditid: params});
	};
	
	/**
	 * 根据id查询预算数据。
	 */
	this.loadBudgetData = function(data){
		if(data.action == "add"){
			var row = 0;
			var tr = '<tr id="sect_tr_'+row+'">'+
				'<td><div id="sname_'+row+'" rules="required" class="search-textbox" width="130" height="30" ></div></td> ' +
				'<td><div id="dbudgetamount_'+row+'" class="search-textbox" rules="money"  width="100" height="30" ></div></td> ' +
				'<td><div id="dengineeringcost_'+row+'" class="search-textbox" rules="money"  width="100" height="30" ></div></td> ' +
				'<td><div id="dcommissioncost_'+row+'" class="search-textbox" rules="money"  width="100" height="30" ></div></td> ' +
				'<td><i id="add_'+row+'" class="fa fa-plus-circle" onclick="instance.addTr(\''+row+'\');"></i> '+
				'<i id="del_'+row+'" class="fa fa-trash-o" onclick="instance.deleTr(\''+row+'\');"></i>'+
				'<i id="restore_'+row+'" class="fa fa-reply" onclick="instance.restoreTab(\''+row+'\');" ></i>'+
				'<input sid="sid" id="sid_'+row+'" type="text" style="display:none;" value="add"/></td></tr>';
			$("#tr_bud").after(tr);
			$("#restore_"+row).hide();
			search.parse();
		}
		
		$.ajax({
			url : CTX+"/budget/getBudgetObjBySid",
			data:{sid: data.sid, action: data.action},
			success : function(result){
				instance._budge_form.setData(result.result.budget);
				instance.resultData = result.result;
				// 生成标段表格。
				if(result.result.sectList && result.result.sectList.length > 0){
					var sectList = result.result.sectList;
					for(var i = 0; i < sectList.length; i++){
						var tr = '<tr id="sect_tr_'+i+'">'+
							'<td><div id="sname_'+i+'" rules="required" class="search-textbox" width="130" height="30" ></div></td> ' +
							'<td><div id="dbudgetamount_'+i+'" class="search-textbox" rules="money"  width="100" height="30" ></div></td> ' +
							'<td><div id="dengineeringcost_'+i+'" class="search-textbox" rules="money"  width="100" height="30" ></div></td> ' +
							'<td><div id="dcommissioncost_'+i+'" class="search-textbox" rules="money"  width="100" height="30" ></div></td> ' +
							'<td><i id="add_'+i+'" class="fa fa-plus-circle" onclick="instance.addTr(\''+i+'\');"></i> '+
							'<i id="del_'+i+'" class="fa fa-trash-o" onclick="instance.deleTr(\''+i+'\');"></i>'+
							'<i id="restore_'+i+'" class="fa fa-reply" onclick="instance.restoreTab(\''+i+'\');" ></i>'+
							'<input sid="sid" id="sid_'+i+'" type="text" style="display:none;" value="'+sectList[i].sid+'"/></td></tr>';
						if(i == 0)$("#tr_bud").after(tr);
						else $("#sect_tr_"+ (i - 1)).after(tr);
						if(i != sectList.length -1) $("#add_"+i).hide();
						$("#restore_"+i).hide();
						search.parse();
						
						search.get("sname_"+i).setValue(sectList[i].sname);
						search.get("dbudgetamount_"+i).setValue(sectList[i].dbudgetamount);
						search.get("dengineeringcost_"+i).setValue(sectList[i].dengineeringcost);
						search.get("dcommissioncost_"+i).setValue(sectList[i].dcommissioncost);
						
						instance._tr_row_id += 1;
					}
					instance._tr_row_id -= 1; 
				}
			}
		});
	};
	
	/**
	 * 初始化上传。
	 */
	this.initiFileUpload = function(){
		if(!this.proLibUpload){
			var options = {relativePath : Globle.constant.upload.budgets, accept:{}, uploadFinished : function(datas) {
				var deleteids = instance.proLibUpload.delArr;
				if (datas && datas.length > 0) {
					// 上传成功附件信息
					$.each(datas, function(i) {
						var filetotal = instance.formData.filetotal ? ++instance.formData.filetotal : 1;
						var fileData = {};
						instance.formData["filetotal"] = filetotal;
						fileData["Commonattachs[" + i + "].sname"] = this.sname;
						fileData["Commonattachs[" + i + "].spath"] = this.spath;
						fileData["Commonattachs[" + i + "].itype"] = 4;
						instance.formData = $.extend(instance.formData, fileData);
					})
				}

				if (deleteids && deleteids.length > 0) instance.formData = $.extend(instance.formData, { deleteids : deleteids.join(",") }); // 删除附件ID
				instance.saveBudgeForm();
			}};

			if (this.resultData && this.resultData.attachList && this.resultData.attachList.length > 0) {
				options = $.extend(options, { initFiles : { rootPath : NETWORK_ROOT, files : this.resultData.attachList }});
			}
			this.proLibUpload = Upload.upload(options);
		}
	};
	
	/**
	 * 保存。
	 */
	this.saveBudgeForm = function(){
		$.post(CTX + "/budget/insertBudgets", instance.formData, function(result) {
			if(result.status){
				search.info({content: result.result.isSuccess,funl:function(){
					if(result.result.state) window.closeWindow();
				}});
			}else search.error({content:"系统错误!"});
		})
	};
	
	/**
	 *  新增表格。
	 */
	this.addTr = function(tr_id){
		var tr = '<tr id="sect_tr_'+this._tr_row_id+'">'+
			'<td><div id="sname_'+this._tr_row_id+'" rules="required" class="search-textbox" width="130" height="30" ></div></td> ' +
			'<td><div id="dbudgetamount_'+this._tr_row_id+'" class="search-textbox" rules="money"  width="100" height="30" ></div></td> ' +
			'<td><div id="dengineeringcost_'+this._tr_row_id+'" class="search-textbox" rules="money"  width="100" height="30" ></div></td> ' +
			'<td><div id="dcommissioncost_'+this._tr_row_id+'" class="search-textbox" rules="money"  width="100" height="30" ></div></td> ' +
			'<td><i id="add_'+this._tr_row_id+'" class="fa fa-plus-circle" onclick="instance.addTr(\''+this._tr_row_id+'\');"></i> '+
			'<i id="del_'+this._tr_row_id+'" class="fa fa-trash-o" onclick="instance.deleTr(\''+this._tr_row_id+'\');"></i>'+
			'<i id="restore_'+this._tr_row_id+'" class="fa fa-reply" onclick="instance.restoreTab(\''+this._tr_row_id+'\');" ></i>'+
			'<input sid="sid" id="sid_'+this._tr_row_id+'" type="text" style="display:none;" value="add"/></td></tr>';
		$("#sect_tr_"+tr_id).after(tr);
		$("#add_"+tr_id).hide();
		$("#restore_"+this._tr_row_id).hide();
		search.parse();
		this._tr_row_id += 1;
	};
	
	/**
	 *  删除表格。
	 */
	this.deleTr = function(id){
		$("#del_"+id).hide();
		$("#restore_"+id).show();
		var _input_val = $("#sid_"+id).val()+"_del"
		$("#sid_"+id).val(_input_val);
		console.log(_input_val);
		search.get("sname_"+id).setEnabled(false);
		search.get("dbudgetamount_"+id).setEnabled(false);
		search.get("dengineeringcost_"+id).setEnabled(false);
		search.get("dcommissioncost_"+id).setEnabled(false);
		this._del_cont += 1;
	};
	
	/**
	 * 恢复表格。
	 */
	this.restoreTab = function(id){
		$("#del_"+id).show();
		$("#restore_"+id).hide();
		var _input = $("#sid_"+id).val();
		$("#sid_"+id).val(_input.split("_")[0]);
		
		search.get("sname_"+id).setEnabled(true);
		search.get("dbudgetamount_"+id).setEnabled(true);
		search.get("dengineeringcost_"+id).setEnabled(true);
		search.get("dcommissioncost_"+id).setEnabled(true);
		this._del_cont -= 1;
	}
}

var instance = new UpdateBudgetHandle();

/**
 * 页面加载。
 */
$(function(){
	instance.loadSelectData(_pro_lib_auditid);
	$("#restore_0").hide();
})

/**
 * 保存数据。
 */
function saveData(){
	if(!instance._budge_form.validate())return;
	//验证标段。
	var sid = new Array, sname = new Array, dbudgetamount = new Array, dengineeringcost = new Array, dcommissioncost = new Array;
	$("input[sid='sid']").each(function(i){
		sid[i] = $(this).val();
	 });
	for(var i = 0 ; i < instance._tr_row_id; i++){
		// 项目名称。
		if(!search.get("sname_"+i)) sname[i] = 0;
		else sname[i] = search.get("sname_"+i).getValue();
		// 预算工程费(元)。
		if(!search.get("dbudgetamount_"+i)) dbudgetamount[i] = 0;
		else dbudgetamount[i] = search.get("dbudgetamount_"+i).getValue();
		//建设安装工程费(元)
		if(!search.get("dengineeringcost_"+i)) dengineeringcost[i] = 0;
		else dengineeringcost[i] = search.get("dengineeringcost_"+i).getValue();
		//招标代理费(元)
		if(!search.get("dcommissioncost_"+i)) dcommissioncost[i] = 0;
		else dcommissioncost[i] = search.get("dcommissioncost_"+i).getValue();
	}
	
	if(sname.length == instance._del_cont){
		search.warn({content:"必须要填写一个有效标段!"});
		return;
	}
	
	var _b = false;
	var str = sname.join(",")+",";
	for(var i = 0; i < sname.length; i++) {
	    if(str.replace(sname[i]+",","").indexOf(sname[i]+",")>-1) {
		    _b = true;
		    break;
	    }
	}
	
	if(_b){
		search.warn({content: "表单存在两个标段名称：" + sname[i]});
	    return;
	}
	instance.formData = $.extend(instance.formData, {sectid: sid});
	instance.formData = $.extend(instance.formData, {sectname: sname});
	instance.formData = $.extend(instance.formData, {sectamount: dbudgetamount});
	instance.formData = $.extend(instance.formData, {sectdengin: dengineeringcost});
	instance.formData = $.extend(instance.formData, {sectdcommi: dcommissioncost});
	//加入表单数据。
	instance.formData = $.extend(instance.formData, instance._budge_form.getData());
	if(!instance.proLibUpload) instance.saveBudgeForm();
	else instance.proLibUpload.upload();
}

/**
 * 关闭窗口。
 */
function windowClose(){
	window.closeWindow();
}

$('#table-construction').on('click', 'li', function () {
    var $li = $(this), index = $li.index(), $constructionBd = $('#table-construction-bd').children().eq(index);
    $li.addClass('table-active').siblings().removeClass('table-active');
    $constructionBd.addClass('table-thisclass').siblings().removeClass('table-thisclass');
    if($li.index() == 1) instance.initiFileUpload();
});