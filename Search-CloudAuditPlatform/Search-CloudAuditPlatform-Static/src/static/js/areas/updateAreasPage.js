search.parse();
var form = new Form("Areas");
var sparentid = search.get("sparentid");

sparentid.url = CTX + "/areas/findAreasByPid";
sparentid.load();

$(function() {
})

/**
 * 父级页面传值
 * @param data
 */
function setData(data) {
	if (data.action == "edit") {
		$.ajax({ url : CTX + "/areas/getById", data : { id : data.id }, success : function(result) {
			if (result) {
				form.setData(result.result);
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
	$.post( CTX + "/areas/save", form.getData(), function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else if( result.status == "false"){
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
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
