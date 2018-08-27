search.parse();

function updateUserMessHandle(){
	this.userFrom = new Form("userFrom")
	this.igender = search.get("igender");
	this.loadData = function(data){
		$.ajax({
			url : CTX+"/home/getUserMessage",
			success : function(result){
				instance.userFrom.setData(result.result);
			}
		});
	};
	
	/**
	 * 保存用户信息。
	 */
	this.updateUser = function(){
		var flag = false;
		if (!instance.userFrom.validate())return false;
		var data = instance.userFrom.getData();
		$.ajaxSettings.async = false;
		$.post(CTX+"/home/updateUserMess", data, function(result) {
			if(result.status){
				if(result.result.state) flag = true;
				else search.warn({content: result.result.isSuccess});
			}else search.error({content:"系统错误!"});
		});
		return flag;
	}
	
	/**
	 * 结束流程
	 */
	this.endFlow = function(){
		window.closeWindow();
	}
}

var instance = new updateUserMessHandle();

$(function() {
	Step.init();
	instance.igender.loadData(Globle.constant.sex);
})