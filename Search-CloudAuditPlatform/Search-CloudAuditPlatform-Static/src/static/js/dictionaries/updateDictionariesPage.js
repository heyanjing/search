search.parse();
var form = new Form("Dictionaries");
var itype = search.get("itype");

var itypeData = [
	{value : 0 , text : "请选择"},
    {value : 1  , text:"机构资质"},
    {value : 2  , text:"人员资质"},
    {value : 3  , text:"职务"}
];

itype.loadData(itypeData);
$(function() {
})

/**
 * 父级页面传值
 * @param data
 */
function setData(data) {
	if (data.action == "edit") {
		$.ajax({ url : CTX + "/dictionaries/getById", data : { id : data.id }, success : function(result) {
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
	if(itype.getValue() == 0){
		top.search.error({ content : "请选择类型!" });
		return;
	}
	$.post( CTX + "/dictionaries/save", form.getData(), function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else if( !result.status){
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
