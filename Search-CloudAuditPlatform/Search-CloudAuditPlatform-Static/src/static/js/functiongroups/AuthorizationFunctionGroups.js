search.parse();
var form = new Form("authorizationform");
var grid = search.get("treedatagrid");
var list = new Array();
var checklist = new  Array();
var headlist = new Array();
var oid = "";//
var funjson = {}
funjson.array1 = [];
var aorgid = search.get("aorgid");
var sorgid = search.get("sorgid");
var __orgtype = new Array();
//记录需要添加的功能组id
var functiongroupsids = "";
$(function() {
	Step.init();

	if (__usertype == Globle.constant.userTypes[0].id) {
		sorgid.url = CTX + "/orgs/grorgs";
		sorgid.load();
		get();
		$("#org1").hide();
	} else {
		aorgid.url = CTX + "/orgs/gao";
		aorgid.load();
		$.ajax({
			url : CTX+"/orgs/gui",
			success : function(result){
				__orgtype = result.result.orgtype;
				if(__orgtype[0] != Globle.constant.AuditOrgType[0].value){
					$("#org2").hide();
				}else{
					$("#org2").hide();
					$("#org1").hide();
					get();
				}
			}
		});
	}
})
function get(){
	if(__usertype == Globle.constant.userTypes[0].id||__orgtype[0] == Globle.constant.AuditOrgType[0].value||(__orgtype[0] != Globle.constant.AuditOrgType[0].value&&aorgid.getValue()!="")){
		$.post(CTX + "/functiongroups/gfgafboi",{aorgid : aorgid.getValue(),sorgid : sorgid.getValue()}, function(result) {
			if (result.status) {
				if(result.result.headlist.length==0){
					search.warn({content:"该机构没有功能组!"});
					sorgid.setValue("");
					return;
				}
				list = result.result.list;
				checklist = result.result.checklist;
				headlist = result.result.headlist;
				search.remove("treedatagrid",true);
	  			var str = "<div property='columns'> <div field='text' width='200' headAlign='center'  textAlign='center' allowSort='true'>功能名称</div>";
	  			for(var i =0;i<headlist.length;i++){
	  				str += "<div field='"+headlist[i].head+"' width='100' headAlign='center'  textAlign='center' allowSort='true'>"+headlist[i].sname+"</div> ";
	  			}
	  			str += "</div>"
	  	      	$("#treedatagrid").empty().append($(str));
	  	      	search.parse();
	  	      	grid = search.get("treedatagrid");
				grid.loadData(list);
			} else {
				top.search.error({ content : "系统错误！" });
			}
		})
	}
}
/**
 * 父级页面传值
 *
 * @param data
 */
function setData(data) {
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
//	//添加功能到对象数组
//	if(funarr.length !=0){
//		funjson.array1[funjson.array1.length]={"orgid":orgs.getValue(),"funarr":funarr};// 数组追加一个元素
//	}
	var last=JSON.stringify(funarr); //将JSON对象转化为JSON字符
	$.post(CTX + "/functiongroups/uafg", {authorization : funarr.join(",")}, function(result) {// 保存
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
			if(e.record[headlist[i].head]==null){
				html = "<input type='checkbox' id='funid' name='funid' value='"+e.record[headlist[i].head]+"' disabled='disabled' />";
			}else{
				html = "<input type='checkbox' data-pid='"+e.record["pid"+headlist[i].head]+"' data-id='"+e.record[headlist[i].head]+"' id='funid' name='funid' onclick='check(this)' value='"+e.record[headlist[i].head]+"' />";
			}
			for(var k = 0;k<checklist.length;k++){
				if(e.record[headlist[i].head]==checklist[k].check){
					html = "<input type='checkbox' data-pid='"+e.record["pid"+headlist[i].head]+"' data-id='"+e.record[headlist[i].head]+"' id='funid' name='funid' onclick='check(this)' value='"+e.record[headlist[i].head]+"' checked='checked'/>";
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
function check(e){
	var pidlist = new Array();
	pidlist[0] = e.dataset.id;
	var flag = $("input[data-id='"+pidlist[0]+"']").prop("checked");
	for(var j = 0;j==0;){
		var idlist = new Array();
		var k = 0;
		for(var a = 0;a<pidlist.length;a++){
			$.each($('input[type="checkbox"]'),function(i){
		        if($(this).attr('data-pid')==pidlist[a]){
		        	$(this).prop("checked",flag);
		        	idlist[k] = $(this).attr('data-id');
		        	k++;
		        }
		    });
		}
		if(k==0){
			break;
		}
		pidlist.splice(0,pidlist.length);
		pidlist = idlist;
	}
	if(e.dataset.pid!='null'){
		var pid = e.dataset.pid;
		if(flag){
			for(var j = 0;j==0;){
				var id = "";
				$.each($('input[type="checkbox"]'),function(i){
			        if($(this).attr('data-id')==pid){
			        	$(this).prop("checked",flag);
			        	id = $(this).attr('data-pid');
			        }
			    });
				if(id=='null'){
					break;
				}
				pid = id;
			}
		}else{
			for(var j = 0;j==0;){
				var f = true;
				$.each($("input[data-pid='"+pid+"']"),function(i){
					 if($(this).prop('checked')){
						 f = false;
					 }
			    });
				if(f){
					$("input[data-id='"+pid+"']").prop("checked",flag);
					pid = $("input[data-id='"+pid+"']").attr("data-pid");
					if(pid=='null'){
						break;
					}
				}else{
					break;
				}
			}
		}
	}
}