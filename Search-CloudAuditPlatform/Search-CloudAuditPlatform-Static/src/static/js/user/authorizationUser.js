search.parse();
var form = new Form("authorizationform");
var grid = search.get("datagrid");
var userid = "";
var orgs = search.get("orgs")

var authorized = new Array();//已授权的数组
var oid = "";//
var groupsjson = {};// 定义一个json对象
var funjson = {}
var funrefs = funref.split(",");
groupsjson.array1 = [];
funjson.array1 = [];
grid.doLayout();


//记录需要添加的功能组id
var functiongroupsids = "";

$(function() {
	if(type == 1){//管理员
		$("#org").hide();
		grid.url = CTX + "/user/getUserAndFunctions";
		grid.load({orgid : orgid});
	}else {
		if(orgdepartment == 2 || orgdepartment == 1){
			$("#org").hide();
			grid.url = CTX + "/user/getUserAndFunctions";
			grid.load();
		}else{
			//加载机构
			orgs.url = CTX + "/user/findOrgs";
			orgs.load();
			grid.url = CTX + "/user/getUserAndFunctions";
			grid.load({orgid : orgid});
			
		}
	}
	
})

function rloaded(e){
	orgs.setValue(osid);
}

/**
 * 父级页面传值
 * 
 * @param data
 */
function setData(data) {
}

function onvalue(e){
	var orgid = orgs.getValue();
	var orgsid = orgid == "" ? osid : orgid;
	if(orgid != "" || osid != ""){
		
		//添加功能到对象数组
		var funarr = new Array();
		//将已勾选的数据存入数组 
      $.each($('input:checkbox:checked'),function(i){
          funarr[i] = $(this).val();
      });
		
	      $.post(CTX +"/user/getFunctions", {orgid : orgsid}, function(result) {// 保存
	  		if (result.status) {
	  			search.remove("datagrid",true);
	  			var str = "<div property='columns'> <div field='sname' name = 'sname' width='50' headAlign='center'  textAlign='center' allowSort='true'>用户名称</div>";
	  			for(var i =0;i<result.result.length;i++){
	  				str += "<div field='fid_"+result.result[i].sid+"' width='150' headAlign='center'  textAlign='center' allowSort='true'>"+result.result[i].sname+"</div> ";
	  			}
	  			str += "</div>"
	  	      	$("#datagrid").empty().append($(str));
	  	      	search.parse();
	  	      var datagrid = search.get("datagrid");
	  	      datagrid.url = CTX + "/user/getUserAndFunctions";
	  	      datagrid.load({orgid : orgsid});
	  		} else {
	  			top.search.error({
	  				content : "系统错误!"
	  			});
	  		}
	  	})
  	
      	
		if(funarr.length !=0){
			var val = e.before == "" ? osid : e.before;
			funjson.array1[funjson.array1.length]={"orgid":val,"funarr":funarr};// 数组追加一个元素
		}
	    
		
	}
}

//格式化表格内容
function datagriddrawcell(e) {
	var column = e.column.field;
	if (column.indexOf("fid_") != -1) {
		e.html = "<input type='checkbox' id='funid' name='funid' value='" +e.record[column]+ "' />";
		for(var i = 0;i<funrefs.length;i++){
			if(funrefs[i] == e.record[column]){
				var autbool = true;
				for(var j =0;j<authorized.length;j++){
					if(authorized[j] == funrefs[i]){
						autbool = false;
					}
				}
				if(autbool){
					authorized.push(e.record[column]);
				}
				e.html = "<input type='checkbox' id='funid' name='funid' checked = 'checked' value='" +e.record[column]+ "' />";
			}
		}
		if(funjson.array1.length !=0){
			//循环数组是否有已选中的数据。
			for(var i=0;i<funjson.array1.length;i++){
				if(orgs.getValue() == funjson.array1[i].orgid){
					var fun = funjson.array1[i].funarr;
					var bool = true;
					for(var j=0;j<fun.length;j++){
						if(e.record[column] == fun[j]){
							e.html = "<input type='checkbox' id='funid' name='funid' checked = 'checked' value='" +e.record[column]+ "' />";
							bool = false;
						}
					}
					if(bool){
						e.html = "<input type='checkbox' id='funid' name='funid' value='" +e.record[column]+ "' />";
					}
				}
			}
		}
		
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
	//添加功能到对象数组
	if(funarr.length !=0){
		funjson.array1[funjson.array1.length]={"orgid":orgs.getValue(),"funarr":funarr};// 数组追加一个元素
	}
	var last=JSON.stringify(funjson); //将JSON对象转化为JSON字符
	var authorizedstr = authorized.length == 0 ? "" : JSON.stringify(authorized);
	$.post(CTX +"/user/funSave", {funjson : last,authorized:authorizedstr}, function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
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
