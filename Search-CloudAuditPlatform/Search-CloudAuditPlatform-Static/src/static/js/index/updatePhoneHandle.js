search.parse();

function updatePhoneHandle(){
	this.vform = new Form("sphoneValidate");
	this.newPhoneFrom = new Form("newPhoneFrom");
	this.sphone = "";
	this.susername = "";
	this.email = "";
	this.newphone = search.get("newphone");
	this.emailVa_data = [{text: '手机验证', value: '1'},
            {text: '邮箱验证', value: '2'}];
	this.emailVa = search.get("emailVa");
	this.loadData = function(data){
		this.sphone = data.sphone;
		this.susername = data.sname;
		this.email = data.email;
		if(data.sphone != null && data.sphone != "") $('#sphone').text(data.sphone.substr(0,3)+"****"+data.sphone.substr(7));
		if(data.email != null && data.email != "") $('#email').text(data.email.replace(/(.{2}).+(.{2}@.+)/g, "$1****$2"))
	};
	
	/**
	 * 发送短信验证。
	 */
	this.sendVerifica = function(spanid,flag){
		if(flag){
			if(instance.newphone.getValue() == ""){
				search.warn({content:"请输入新绑定的手机号"});
				return;
			}
			instance.sphone = instance.newphone.getValue();
		}
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
					instance.validation(spanid);
				}
			}
		});
	}
	
	/**
	 * 重新发送短信。
	 */
	this.validation = function(spanid){
		var time = 60;
		$(spanid).text("重新发送(" + time + ")").removeAttr('onclick').css("color", "#fff");
		var sendmobileinterval = window.setInterval(function(e) {
			time--;
			$(spanid).text("重新发送(" + time + ")");
			if (time <= 0) {
				window.clearInterval(sendmobileinterval);
				$(spanid).text("发送验证码").attr('onclick','instance.sendVerifica()').css("color", "#fff");
			}
		}, 1000);
	}
	
	/**
	 * 验证短信。
	 */
	this.sphoneValidate = function(){
		var flag = false;
		if(instance.emailVa.getValue() == "1"){
			if (!instance.vform.validate())return false;
			$.ajax({
				async:false,
				url : CTX+"/home/sphoneValidate",
				data: {validate: search.get("validate").getValue(), itype: 1},
				success : function(result){
					if(result.status){
						if(result.validate) flag = true; 
						else search.warn({content:"验证失败!"});
					}
				}
			});
			
			return flag;
		}else if(instance.emailVa.getValue() == "2"){
			if(instance.email == null || instance.email == ""){
				search.warn({content:"暂未绑定邮箱,不能发送邮件!"});
				return ;
			}
			$.ajax({
				url : CTX+"/home/sendEmail",
				data: {email: instance.email, itype: 1},
				async:false,
				success : function(result){
					if(result.status){
						$("#flow-item1").addClass("contentList");
						$("#flow-item3").removeClass("contentList");
						$("#flow-step1").removeClass("flow-step-current");
						$("#flow-step3").addClass("flow-step-current");
						$("#btnok").css("display","block");
						$("#btnNext").css("display","none");
					}else search.warn({content:"系统异常!"});
				}
			});
			return false;
		}
	}
	
	/**
	 * 更换手机
	 */
	this.updatePhone = function(){
		var flag = false;
		if (!instance.newPhoneFrom.validate())return false;
		$.ajax({
			async:false,
			url : CTX+"/home/updatePhone",
			data: {validate: search.get("validate").getValue(), newphone: instance.newphone.getValue()},
			success : function(result){
				if(result.status){
					if(result.validate){
						if(result.result.state) flag = true; 
						else search.warn({content: result.result.isSuccess});
					}else search.warn({content:"验证失败!"});
				}
			}
		});
		
		return flag;;
	}
	
	/**
	 * 结束流程
	 */
	this.endFlow = function(){
		window.closeWindow();
	}
}

var instance = new updatePhoneHandle();

$(function() {
	Step.init();
	instance.emailVa.loadData(instance.emailVa_data);
	$('#tr-email').hide();
})

/**
 * 改变下拉框值时发生的事件。
 * @param e
 */
function onValueChanged(e){
	if(e.value == 1){
		$('#tr-sphone').show();
		$('#tr-validate').show();
		$('#tr-email').hide();
	}else{
		$('#tr-sphone').hide();
		$('#tr-validate').hide();
		$('#tr-email').show();
	}
}