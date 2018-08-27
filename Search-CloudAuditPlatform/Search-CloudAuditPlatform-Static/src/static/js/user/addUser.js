search.parse();
var form = new Form("Usersform");

var igender = search.get("igender");
var orgs = search.get("orgs");
var countdown=180;

// var orgoruseranddictionarierefs = search.get("orgoruseranddictionarierefs");

igender.loadData(Globle.constant.sex);
$(function() {
	orgs.url = CTX + "/orgs/org";
	orgs.load();

//	organduserrefs.url = CTX + "/dictionaries/getDictionariesList?itype=3";
//	organduserrefs.load();

	// orgoruseranddictionarierefs.url =
	// "/main/dictionaries/getDictionariesList?itype=2";
	// orgoruseranddictionarierefs.load();

	$("#btnok").on("click", function() {
		// console.log(upload1.delArr);// 获取删除附件的id
		upload1.upload();
	});

})

// 初始化
Upload2
		.init({
			url : "/user/findListMapDictionariesByItype",
			data : {
				type : 3
			},
			callback : function(resultArr, delArr) {
				console.log("上传结果", resultArr, delArr);
				for (var i = 0; i < resultArr.length; i++) {
					sdictionarieid += resultArr[i].sdictionarieid + ",";
					qualinames += resultArr[i].sname + ",";
					qualificationspath += resultArr[i].spath + ",";
					sdesc += resultArr[i].sdesc + ",";
				}
				sdictionarieid = sdictionarieid.substring(0,
						sdictionarieid.length - 1);
				qualinames = qualinames.substring(0, qualinames.length - 1);
				qualificationspath = qualificationspath.substring(0,
						qualificationspath.length - 1);
				sdesc = sdesc.substring(0,sdesc.length - 1);

				// 保存
				save();
			},
			wbOpts: {
		        relativePath: '/search/user'
		    }
		});

/**
 * 获取验证码
 * @returns
 */
function getCode(){
	var phone = search.get("sphone").getValue();
	if(phone == ""){
		top.search.info({ content : "请输入手机号码", funl : function() {
		} });
		return;
	}
	$.ajax({ url : CTX + "/user/phone", data : { phone : phone },
		 success : function(result) {
			 if (result) {
				 sphone = phone;
				 code = result.result;
				 var obj = $("#code");
				 settime(obj);
			 } else {
				 top.search.error({ content : "发送失败!" });
			 }
		 }
	});
	
}

function settime(obj) {
	if (countdown == 0) { 
		obj.attr('disabled',false); 
        //obj.removeattr("disabled"); 
		obj.val("免费获取验证码");
        countdown = 180; 
        return;
    } else { 
    	obj.attr('disabled',true);
    	obj.val("重新发送(" + countdown + ")");
        countdown--; 
    } 
	setTimeout(function() { 
	    settime(obj) }
	    ,1000)
}

/**
 * 父级页面传值
 * 
 * @param data
 */
function setData(data) {
	// if (data.action == "edit") {
	// $.ajax({ url : "/main/dictionaries/getById", data : { id : data.id },
	// success : function(result) {
	// if (result) {
	// form.setData(result);
	// } else {
	// top.search.error({ content : "系统错误!" });
	// }
	// } });
	// }
}

// 保存操作
function save() {
	if (!form.validate()) {
		return;
	}
	// 参数拼接
	var str = "?identificationpath=" + identificationpath
			+ "&identificationsNams=" + identificationsNams
			+ "&qualificationspath=" + qualificationspath + "&qualinames="
			+ qualinames + "&sdictionarieid=" + sdictionarieid + "&sdesc=" + sdesc;

	$.post(CTX + "/user/save" + str, form.getData(), function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				location.reload();
				window.closeWindow("ok");
			} });
		} else {
			top.search.error({
				content : "系统错误!"
			});
		}
	})
}

var closetitle;
function windowClose(result) {
	top.search.info({
		content : result,
		funl : function() {
			window.closeWindow("ok");
		}
	});
}
