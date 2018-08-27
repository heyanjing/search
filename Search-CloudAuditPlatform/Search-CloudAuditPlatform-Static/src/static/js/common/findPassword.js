search.parse();
var form = new Form("findtypeform");
var changepasswdform = new Form("changepasswdform");

var type = search.get("type");
var flag = search.get("flag");

var stepInstance, flagName;

// 页面加载完成执行
$(function() {
	stepInstance = Step.init();

	type.loadData([ { id : 1, text : "手机短信" }, { id : 2, text : "电子邮箱" } ]);
})

// 找回方式点击下一步执行
function findtype() {
	if (form.validate()) {
		$.post(CTX + "/common/getUsersByPhoneOrEmail", form.getData(), function(result) {
			if (result.result) {
				if (type.getValue() == 1) {
					$("#phone-content").show();
					$("#email-content").hide();
				} else if (type.getValue() == 2) {
					$("#phone-content").hide();
					$("#email-content").show();
				}
				stepInstance.stepClick(2, false);
			} else {
				top.search.info({ content : result.msg || "没有找到" + flagName + "绑定的用户！" });
			}
		})
	}
	return false;
}

// 找回方式改变事件
function typechanged(e) {
	if (e.value == 1) {
		// 手机短信
		flagName = "手机号码";
		$("#flag").parents("tr").find("label").html(flagName + "：");
		flag.rules = "required,mobile";
		$("#btnok").html("保存");
		$("#btnNext").html("下一步");
		$("#flow-step2 .flow-title").html("找回密码");
	} else if (e.value == 2) {
		// 电子邮箱
		flagName = "电子邮箱";
		$("#flag").parents("tr").find("label").html(flagName + "：");
		flag.rules = "required,email";
		$("#btnok").html("前往首页");
		$("#btnNext").html("发送邮件");
		$("#flow-step2 .flow-title").html("发送成功");
	}
}

// 发送验证码
function sendSMS(elem) {
	var $this = $(elem);
	var time = 60;
	$this.text("重新发送(" + time + ")").attr("disabled", true);
	var sendmobileinterval = window.setInterval(function(e) {
		time--;
		$this.text("重新发送(" + time + ")");
		if (time <= 0) {
			window.clearInterval(sendmobileinterval);
			$this.text("获取验证码").attr("disabled", false);
		}
	}, 1000);
	$.post(CTX + "/common/sendSMS", { phone : flag.getValue() }, function(result) {
		if (result.status) {

		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}

// 保存
function saveData() {
	if (type.getValue() == 1) {
		if (search.get("newpasswdone").getValue() != search.get("newpasswdtwo").getValue()) {
			top.search.info({ content : "两次密码不一致！" });
			return;
		}
		changepasswdform.validate();
		$.post(CTX + "/common/changePasswdByPhone", $.extend(changepasswdform.getData(), { phone : flag.getValue() }), function(result) {
			if (result.status) {
				if (result.result) {
					top.search.info({ content : "密码修改成功！", funl : function() {
						location.href = CTX + "/";
					} });
				} else {
					top.search.info({ content : result.msg });
				}
			} else {
				top.search.error({ content : "系统错误！" });
			}
		})
	} else if (type.getValue() == 2) {
		location.href = CTX + "/";
	}
}