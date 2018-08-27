search.parse();
var form = new Form("authorizationform");
var grid = search.get("treedatagrid");
var list = new Array();
var checklist = new  Array();
var headlist = new Array();
var funjson = {}
var flag = true;
var sorgid = "";
var areaid = "";
funjson.array1 = [];
function get(areaid,orgid){
	$.post(CTX + "/orgs/gsofg?areaid="+areaid+"&sorgid="+orgid,function(result) {
		if (result.status) {
			list = result.result.list;
			checklist = result.result.checklist;
			headlist = result.result.headlist;
			grid.loadData(list);
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})

}
/**
 * 父级页面传值
 *
 * @param data
 */
function setData(data) {
	if(data==null){
		get(null);
	}else{
		get(data.areaid,data.orgid);
		flag = false;
		sorgid = data.orgid;
		areaid = data.areaid;
	}
}

// 保存操作
function save() {
	if (!form.validate()) {
		return;
	}
	//添加功能到对象数组
	var funarr = new Array();
    $.each($('input:checkbox:checked'),function(i){
        funarr[i] = $(this).val();
    });
	var last=JSON.stringify(funarr); //将JSON对象转化为JSON字符
	$.post(CTX + "/orgs/uaso", {authorization : funarr.join(","),flag:flag,sorgid:sorgid,areaid:areaid}, function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg });
			window.closeWindow("ok");
		}else {
			top.search.error({
				content : "系统错误!"
			});
		}
	})
}
function drawcell(e){
	for(var i = 0;i<headlist.length;i++){
		if(e.column.field==headlist[i].head){
			var html = "";
			html = "<input type='checkbox' name='funid' value='"+e.record[headlist[i].head]+"' />";
			for(var k = 0;k<checklist.length;k++){
				if(e.record[headlist[i].head]==checklist[k].check){
					html = "<input type='checkbox' id='funid' name='funid' value='"+e.record[headlist[i].head]+"' checked='checked'/>";
				}
			}
			e.html = html;
		}
	}
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
