search.parse();
form = new Form("form");
var sorgid = search.get("sorgid");
var sid = search.get("sid");
var sfromorgid = search.get("sfromorgid");
var isupportproject= search.get("isupportproject");
var action = "";
/**
 * 页面加载。
 */
function setData(data){
	$("#sfromorgid1").hide();
	$("#sfromorgid2").hide();
	if(__usertype != Globle.constant.userTypes[0].id){
		$("#sorgid1").hide();
		$("#sorgid2").hide();
		sfromorgid.url = CTX+"/orgs/gao";
		sfromorgid.load();
/*		$.ajax({
			url : CTX+"/orgs/gui",
			success : function(result){
				__orgtype = result.result.orgtype;
				if(__orgtype[0] == Globle.constant.OrgType[0].value){
					$("#sfromorgid1").hide();
					$("#sfromorgid2").hide();
				}
			}
		});*/
	}else{
		sfromorgid.url = CTX+"/orgs/gaos";
		sfromorgid.load();
	}
	sorgid.url = CTX+"/orgs/gorgs";
	sorgid.load();
	isupportproject.loadData(Globle.constant.YesOrNo);
	action = data.action;
	if(data.action=="edit"){
		sorgid.loaded = function(e){
			$.ajax({
				url : CTX+"/processdesigns/qpdi",
				data : {sid : data.sid},
				success : function(result){
					form.setData(result.result);
				}
			});
		}
	}
}
/**
 * 保存。
 */
function save(){
	if (!form.validate())return;
	sumbit();
}
//提交数据
function sumbit(){
	$.post(CTX + "/processdesigns/upda",
		{
		sid : sid.getValue(),
		sname : search.get("sname").getValue(),
		sdesc : search.get("sdesc").getValue(),
		sorgid : sorgid.getValue(),
		isupportproject : isupportproject.getValue(),
		sfromorgid : sfromorgid.getValue()
		},
		function(result) {
		if(result.status){
			search.info({content:result.result.meg,funl:function(){
				if(result.status){
					window.closeWindow({"isnext":"ok","sid":result.result.processid,"orgid":result.result.sorgid,"isupportproject":isupportproject.getValue(),"sfromorgid":result.result.sfromorgid});
				}
			}});
		}else search.error({content:"系统错误!"});
	})
}
////验证来源机构不能为空
//function sfromorgidValidate(e){
//	if(__usertype != Globle.constant.userTypes[0].id&&__orgtype[0] != Globle.constant.OrgType[0].value){
//		if(sfromorgid.getValue()==""){
//			e.message = "来源机构不能为空";
//			e.pass = false;
//		}
//	}
//}
//验证所属机构不能为空
function sorgidValidate(e){
	if(__usertype == Globle.constant.userTypes[0].id){
		if(sorgid.getValue()==""){
			e.message = "所属机构不能为空";
			e.pass = false;
		}
	}
}
/**
 * 保存数据。
 */
function saveData(){
	save();
}

/**
 * 关闭窗口。
 */
function windowClose(){
	window.closeWindow();
}
