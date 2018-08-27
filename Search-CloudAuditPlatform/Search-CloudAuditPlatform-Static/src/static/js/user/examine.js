search.parse();
var form = new Form("Usersform");
var organduserrefs = search.get("organduserrefs");
var funjson = {}
funjson.array1 = [];
$(function() {
	organduserrefs.url = CTX + "/dictionaries/getDictionariesList?itype=3";
	organduserrefs.load();

})

/**
 * 驳回
 * @returns
 */
function reject(){
	$.post(CTX + "/user/examineUser",{userid:sid,orgid:osid}, function(result) {
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			if (result.msg) {
				top.search.info({ content : result.msg });
			} else {
				top.search.error({ content : "系统错误！" });
			}
		}
	})
}

/**
 * 通过
 * @returns
 */
function adopt(){
	var organduser = organduserrefs.getValue();
	$.ajax({
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: CTX + "/user/adoptUser" ,
            data: {userid:sid,orgid:osid,organduser :organduser},
            success: function (result) {
            	if (result.status) {
            		
        		} else {
        		}
            },
            error : function() {
                alert("异常！");
            }
        });
	
//	$.post(CTX + "/user/adoptUser",{userid:sid,orgid:osid,organduser :organduser}, function(result) {
//		if (result.status) {
//			state = result.status;
//		} else {
//			state = false;
//		}
//	})
}


/**
 * 加载功能组
 * @param e
 * @returns
 */
function onvalue(){
      $.post(CTX +"/user/getFunctionsOne", {orgid : osid,type:2}, function(result) {// 保存
  		if (result.status) {
  			    search.remove("datagrid",true);
  			if(result.result.length != 0){
  				var str = "<div property='columns'>";
  				for(var i =0;i<result.result.length;i++){
  					str += "<div field='fid_"+result.result[i].sid+"' width='150' headAlign='center'  textAlign='center' allowSort='true'>"+result.result[i].sname+"</div> ";
  				}
  				str += "</div>"
  				$("#datagrid").empty().append($(str));
  				search.parse();
  				var datagrid = search.get("datagrid");
  				datagrid.url = CTX + "/user/getUserAndFunctionsOne";
  				datagrid.load({orgid : osid,userid:sid});
  			}
  		} else {
  			top.search.error({
  				content : "系统错误!"
  			});
  		}
  	})
}

//格式化表格内容
function datagriddrawcell(e) {
	var column = e.column.field;
	if (column.indexOf("fid_") != -1) {
		e.html = "<input type='checkbox' id='funid' name='funid' value='" +e.record[column]+ "' />";
		
	}
}

//保存操作
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
		funjson.array1[funjson.array1.length]={"orgid":osid,"funarr":funarr};// 数组追加一个元素
	}
	var last=JSON.stringify(funjson); //将JSON对象转化为JSON字符
	var authorizedstr = "";
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

/**
 * 父级页面传值
 * @param data
 */
function setData(data) {
	if (data.action == "edit") {
		sid = data.sid;
		osid = data.osid;
	} 
}

function windowClose(result){
	top.search.info({content : result,funl:function(){
		window.closeWindow("ok");
	}});
}
