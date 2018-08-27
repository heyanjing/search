search.parse();

/**
 * 实例。
 */
function emailValidateHandle(){
	this.form = new Form("form");
	this.newphone = search.get("newphone");
	this.validate = search.get("validate");
	this._send_verifica = "#send-verifica";
	this.judgeTpye = function(__flag, type){
		if(__flag){
			if(type == "1") $('#phone-id').show();
			else $('#email-id').show();
		}else search.error({content:"邮件已过期,请重新发送!!"});
	}
	
	/**
	 * 更新邮箱。
	 */
	this.updateMail = function(){
		$.ajax({
			url : CTX+"/home/updateEmail",
			data:{code: __code},
			success : function(result){
				if(result.status) search.warn({content: result.result.isSuccess});
				else search.warn({content:"系统错误!"});
			}
		});
	}
	
	/**
	 * 发送短信获取验证码。
	 */
	this.sendVerifica = function(){
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
		$(instance._send_verifica).text("重新发送(" + time + ")").removeAttr('onclick').css("color", "#999");
		var sendmobileinterval = window.setInterval(function(e) {
			time--;
			$(instance._send_verifica).text("重新发送(" + time + ")");
			if (time <= 0) {
				window.clearInterval(sendmobileinterval);
				$(instance._send_verifica).text("发送验证码").attr('onclick','instance.sendVerifica()').css("color", "#3CC51F");
			}
		}, 1000);
	}
	
	/**
	 * 更改手机号。
	 */
	this.updatePhone = function(){
		if (!instance.form.validate())return;
		$.ajax({
			async:false,
			url : CTX+"/home/updatePhoneByEmail",
			data: {validate: instance.validate.getValue(), newphone: instance.newphone.getValue(), code: __code},
			success : function(result){
				if(result.status){
					if(result.validate) search.warn({content:"更改手机号码成功!"});
					else search.error({content:"验证失败!"});
				}
			}
		});
	}
}

/**
 * 初始化实例。
 */
var instance = new emailValidateHandle();

$(function(){
	instance.judgeTpye(__flag, __ityp);
});