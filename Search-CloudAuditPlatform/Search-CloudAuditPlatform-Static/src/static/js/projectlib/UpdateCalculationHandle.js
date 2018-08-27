search.parse();

function UpdateCalculationHandle(){
	this._calcu_form = new Form("CalculationForm");
	this.sproprietororgid = search.get("sproprietororgid");
	this.sdeputyorgid = search.get("sdeputyorgid");
	this.sapprovalorgid = search.get("sapprovalorgid");
	
	this.formData = {},this.proLibUpload, this.resultData;
	
	/**
	 * 加载项目业主、代建单位、审批单位。
	 */
	this.loadCalculationSelectData = function(params){
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
	 * 根据id查询概算数据。
	 */
	this.loadCalculationData = function(data){
		$.ajax({
			url : CTX+"/calculat/getCalculationObjBySid",
			data:{sid: data.sid, action: data.action},
			success : function(result){
				instance._calcu_form.setData(result.result.calculat);
				instance.resultData = result.result;
			}
		});
	};
	
	/**
	 * 初始化上传。
	 */
	this.initiCalculationFileUpload = function(){
		if(!this.proLibUpload){
			var options = {relativePath : Globle.constant.upload.calculations, accept:{}, uploadFinished : function(datas) {
				var deleteids = instance.proLibUpload.delArr;
				if (datas && datas.length > 0) {
					// 上传成功附件信息
					$.each(datas, function(i) {
						var filetotal = instance.formData.filetotal ? ++instance.formData.filetotal : 1;
						var fileData = {};
						instance.formData["filetotal"] = filetotal;
						fileData["Commonattachs[" + i + "].sname"] = this.sname;
						fileData["Commonattachs[" + i + "].spath"] = this.spath;
						fileData["Commonattachs[" + i + "].itype"] = 3;
						instance.formData = $.extend(instance.formData, fileData);
					})
				}

				if (deleteids && deleteids.length > 0) instance.formData = $.extend(instance.formData, { deleteids : deleteids.join(",") }); // 删除附件ID
				instance.saveCalcuForm();
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
	this.saveCalcuForm = function(){
		$.post(CTX + "/calculat/insertCalculations", instance.formData, function(result) {
			if(result.status){
				search.info({content: result.result.isSuccess,funl:function(){
					if(result.result.state) window.closeWindow();
				}});
			}else search.error({content:"系统错误!"});
		})
	}
}

var instance = new UpdateCalculationHandle();

/**
 * 页面加载。
 */
$(function(){
	instance.loadCalculationSelectData(_pro_lib_auditid);
})

/**
 * 保存数据。
 */
function saveData(){
	if(!instance._calcu_form.validate())return;
	instance.formData = $.extend(instance.formData, instance._calcu_form.getData());
	if(!instance.proLibUpload) instance.saveCalcuForm();
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
    if($li.index() == 1) instance.initiCalculationFileUpload();
});