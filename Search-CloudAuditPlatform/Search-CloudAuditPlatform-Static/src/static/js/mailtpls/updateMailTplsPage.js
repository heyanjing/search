search.parse();
var ue = UE.getEditor('container',{
	initialFrameHeight:200,//设置编辑器高度
    scaleEnabled:true});
var form = new Form("mailtpls");
var itype = search.get("itype");

var itypeData = [
	{value : 0 , text : "请选择"},
	{value : 1 , text : "绑定邮箱101"},
    {value : 2  , text:"通过邮箱修改手机号102"}
];
var username = "";
var url = "";
var sysname = "";
itype.loadData(itypeData);
$(function() {
})

function onvalue(){
	var type = itype.getValue();
	if(type!=0){
		if(type == 1){
			username = "test_101";
			url = "baidu.com_101";
			sysname = "systest_101";
		}else if(type == 2){
			username = "test_102";
			url = "baidu.com_102";
			sysname = "systest_102";
		}
		search.get("username").setValue(username);
		search.get("url").setValue(url);
		search.get("sysname").setValue(sysname);
		
		ue.setContent("<p>"+username+"</p><p>&nbsp;&nbsp;&nbsp;&nbsp;"+url+"</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+sysname+"</p>");
	}else{
		search.get("username").setValue("");
		search.get("url").setValue("");
		search.get("sysname").setValue("");
	}
	
}

/**
 * 父级页面传值
 * @param data
 */
function setData(data) {
	if (data.action == "edit") {
		$.ajax({ url : CTX + "/mailtpls/getById", data : { id : data.id }, success : function(result) {
			if (result) {
				form.setData(result.result);
				ue.setContent(result.result.scontent);
			} else {
				top.search.error({ content : "系统错误!" });
			}
		} });
	} 
}

//保存操作
function save(){
	if (!form.validate()) {
		return;
	}
	if(itype.getValue() == 0){
		top.search.error({ content : "请选择类型!" });
		return;
	}
	var scontent = search.get("scontent");
	scontent.setValue(ue.getContent());
	$.post( CTX + "/mailtpls/save", form.getData(), function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else if( !result.status){
			top.search.info({ content : result.msg});
		}
		else {
			top.search.error({ content : "系统错误!" });
		}
	})
}

var closetitle;
function windowClose(result){
	top.search.info({content : result,funl:function(){
		window.closeWindow("ok");
	}});
}
