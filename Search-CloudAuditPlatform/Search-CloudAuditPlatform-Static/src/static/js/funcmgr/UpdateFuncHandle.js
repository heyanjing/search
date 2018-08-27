search.parse();

/**
 * 控制器。
 */
function UpdateFuncHandle(){
	this.form = new Form("form");
	this.sparentid = search.get("sparentid");
	this.itype = search.get("itype");
	this.isupportphone = search.get("isupportphone");
	this.isupportproject = search.get("isupportproject");
	this.sbtnlocation = search.get("sbtnlocation");
	this.sbindevent = search.get("sbindevent");
	this.ijoinprocess = search.get("ijoinprocess");
	this.sjoinprocesstable = search.get("sjoinprocesstable");
	
	this.loadData = function(data) {
		this.sid = data.sid;
		if(data.action == "add") this.sparentid.setValue(data.sid);
		else if(data.action == "edit"){
			$.ajax({
				url : CTX+"/funcmgr/getFuncObjBySid",
				data : {sid : this.sid},
				success : function(result){
					instance.form.setData(result.result);
				}
			});
		}
	};
	
	this.saveFunc = function(){
		if (!instance.form.validate())return;
		var data = instance.form.getData();
		var __sbindevent = instance.sbindevent.getValue();
		if(__sbindevent != ""){
			var arr = __sbindevent.split(":"); 
			if(arr.length < 2){
				search.warn({content:"按钮绑定事件填写不规范,请用':'隔开!"});
				return;
			}
		}
		$.post(CTX + "/funcmgr/updateFuncObj", data, function(result) {
			if(result.status){
				search.info({content: result.result.isSuccess,funl:function(){
					if(!result.result.flag) window.closeWindow();
				}});
			}else search.error({content:"系统错误!"});
		})
	}
}

/**
 * 联动选择
 */
function onValueChanged(e){
	if(e.value == 5){
		$("#tr_sbtnlocation").show();
		instance.sbtnlocation.activateValidate(true);
	}else{
		$("#tr_sbtnlocation").hide();
		instance.sbtnlocation.activateValidate(false);
		
	} 
}

/**
 * 流程对应表必填验证
 */
function sjoinprocesstableValidate(e){
	if(instance.ijoinprocess.getValue() != Globle.constant.IJoinProcess[0].value&&instance.ijoinprocess.getValue()!=""&&e.value==""){
		e.message = "参与流程对应表不能为空";
		e.pass = false;
	}
}

var instance = new UpdateFuncHandle();

/**
 * 页面加载。
 */
$(function(){
	instance.sparentid.url = CTX+"/funcmgr/getParentFunc";
	instance.sparentid.load();
	instance.itype.loadData(Globle.constant.funcType);
	instance.isupportphone.loadData(Globle.constant.SupportWay);
	instance.isupportproject.loadData(Globle.constant.YesOrNo);
	instance.sbtnlocation.loadData(Globle.constant.sbtnlocationArr);
	instance.sbtnlocation.setValue(Globle.constant.sbtnlocationArr[0].value);
	instance.ijoinprocess.loadData(Globle.constant.IJoinProcess);
	instance.sjoinprocesstable.url = CTX+"/communal/tables";
	instance.sjoinprocesstable.load();
});

/**
 * 保存数据。
 */
function saveData(){
	instance.saveFunc();
}

/**
 * 关闭窗口。
 */
function windowClose(){
	window.closeWindow();
}