search.parse();

function updatePasswordHandle(){
	this.form = new Form("newPassword");
	this.vfrom = new Form("sphoneValidate");
	this.sphone = "";
	this.susername = "";
	this.__send_verifica = "#send-verifica";
	this.loadData = function(data){
		this.sphone = data.sphone;
		this.susername = data.sname;
		if(data.sphone != null && data.sphone != "")$('#sphone').text(data.sphone.substr(0,3)+"****"+data.sphone.substr(7));
	};
	
	/**
	 * 发送短信获取验证码。
	 */
	this.sendVerifica = function(){
		if(instance.sphone == null || instance.sphone == ""){
			search.warn({content:"暂未绑定手机号,不能发手机验证码!"});
			return ;
		}
		$.ajax({
			url : CTX+"/home/sendVerifica",
			data:{sphone: instance.sphone},
			success : function(result){
				if(result.status){
//					search.warn({content:"发送成功"});
					instance.validation();
				}
			}
		});
	}
	
	/**
	 * 重新发送短信。
	 */
	this.validation = function(){
		var time = 60;
		$(instance.__send_verifica).text("重新发送(" + time + ")").removeAttr('onclick').css("color", "#fff");
		var sendmobileinterval = window.setInterval(function(e) {
			time--;
			$(instance.__send_verifica).text("重新发送(" + time + ")");
			if (time <= 0) {
				window.clearInterval(sendmobileinterval);
				$(instance.__send_verifica).text("发送验证码").attr('onclick','instance.sendVerifica()').css("color", "#3CC51F");
			}
		}, 1000);
	}
	
	/**
	 * 验证短信。
	 */
	this.sphoneValidate = function(){
		var flag = false;
		if (!instance.vfrom.validate())return false;
		$.ajax({
			async:false,
			url : CTX+"/home/sphoneValidate",
			data: {validate: search.get("validate").getValue()},
			success : function(result){
				if(result.status){
					if(result.validate) flag = true; 
					else search.warn({content:"验证失败!"});
				}
			}
		});
		return flag;
	}
	
	/**
	 * 修改密码。
	 */
	this.updatePasswprd = function(){
		var flag = false;
		if (!instance.form.validate())return false;
		var newpass = search.get("newpass").getValue();
		var affirmpass = search.get("affirmpass").getValue();
		if(newpass != affirmpass){
			search.warn({content:"两次输入的密码不一致!"});
			return false;
		}
		
		$.ajax({
			url : CTX+"/home/updatePassword",
			data: {pass: newpass},
			async:false,
			success : function(result){
				if(result.status) flag = true; 
			}
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

var instance = new updatePasswordHandle();


$(function() {
	Step.init();
})