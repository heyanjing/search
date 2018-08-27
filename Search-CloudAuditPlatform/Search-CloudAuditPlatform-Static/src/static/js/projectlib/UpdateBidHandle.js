search.parse();

/**
 * 招投标控制器。
 */
function UpdateBidHandle(){
	this._bid_form = new Form("BidForm");
	this.sproprietororgid = search.get("sproprietororgid");
	this.sdeputyorgid = search.get("sdeputyorgid");
	this.ibiddingtype = search.get("ibiddingtype");
	this.ssectionid = search.get("ssectionid");
	this.sagencyorgid = search.get("sagencyorgid");
	this.formData = {},this.proLibUpload, this.resultData;
	
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
		this.sagencyorgid.url = CTX+"/projectlib/getSagencyOrgId";
		this.sagencyorgid.load({auditid: params});
	};
	
	/**
	 * 根据id查询招投标数据。
	 */
	this.loadBudgetData = function(data){
		$.ajax({
			url : CTX+"/bid/getBidObjBySid",
			data:{sid: data.sid, action: data.action},
			success : function(result){
				instance._bid_form.setData(result.result.mapdata);
				instance.resultData = result.result;
				
				instance.ssectionid.loadData(result.result.SectList);
				if(data.action == "edit") instance.ssectionid.setValue(result.result.mapdata.ssectionid);
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
						fileData["Commonattachs[" + i + "].itype"] = 5;
						instance.formData = $.extend(instance.formData, fileData);
					})
				}

				if (deleteids && deleteids.length > 0) instance.formData = $.extend(instance.formData, { deleteids : deleteids.join(",") }); // 删除附件ID
				instance.saveBidForm();
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
	this.saveBidForm = function(){
		$.post(CTX + "/bid/insertBids", instance.formData, function(result) {
			if(result.status){
				search.info({content: result.result.isSuccess,funl:function(){
					if(result.result.state) window.closeWindow();
				}});
			}else search.error({content:"系统错误!"});
		})
	};
}

//身份证校验
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

/**
 * 实例化。
 */
var instance = new UpdateBidHandle();

/**
 * 页面加载。
 */
$(function(){
	instance.loadSelectData(_pro_lib_auditid);
	instance.ibiddingtype.loadData(Globle.constant.iBiddingType);
	instance.ibiddingtype.setValue(Globle.constant.iBiddingType[0].value);
})

/**
 * 保存数据。
 */
function saveData(){
	if(!instance._bid_form.validate())return;
	instance.formData = $.extend(instance.formData, instance._bid_form.getData());
	if(!instance.proLibUpload) instance.saveBidForm();
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