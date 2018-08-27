search.parse();
var form = new Form("Planform");
var idcardFile = {arr : [], index : 0}

var orgs = search.get("sorgid");
var user = search.get("userid_1");
var prolib = search.get("prolib_1");

var countdown=180;
var userData = "";
var prolibData = "";
var orgidData = "";
$(function() {
	if(admin == 1){
		orgs.url = CTX + "/orgs/orgaudit";
		orgs.load();
	}else{
		user.url = CTX + "/user/findUsersOrgid";
		user.load();
		
//		prolib.url = CTX + "/projectlib/getProjectlibSelect";
//		prolib.load();
		
	}
	
	var orgid  = search.get("org_1");
	orgid.url = CTX + "/orgs/orgsaudit";
	orgid.load();
	
	$("#btnok").on("click", function() {
		windowClose("ok");
	});
	//设置初始化状态为删除
	var state = search.get("state_1");
	state.setValue(0);
})

function onUserByOrgValue(e){
	var str = this.id;
	str = str.split("_")[1];
	if(e.value != ""){
		var user = search.get("userid_"+str);
		user.url = CTX + "/user/findUsersOrgid";
		user.load({orgid : e.value });
	}
}

function onOrgValue(e){
	var orgid = orgs.getValue();
	if(orgid != 0){
		user.url = CTX + "/user/findUsersOrgid";
		user.load({orgid : orgid });
		
		prolib.url = CTX + "/projectlib/getProjectlibSelect";
		prolib.load({orgid : orgid });
	}
}

function onvalue(e){
	var year = search.get("iyear")
	if(year){
		if(year.getValue() != ""){
			if(admin == 1 && orgs.getValue() == 0){
//				alert("先选择审计机构");
				return;
			}
			var name = "";
			if(admin == 1){
				name = orgs.getText();
			}else{
				name = orgname;
			}
			var str = name+year.getValue()+"年度审计计划";
			search.get("sname").setValue(str);
		}
		
	}
}

//var table =document.getElementById("tb1");
//var rows = table.rows.length;
var num = 1;

/**
 * 父级页面传值
 * 
 * @param data
 */
function setData(data) {
	 if (data.action == "edit") {
//		 setDatas({processinstancesid:data.sProcessInstanceId});
		 $.ajax({ url : CTX + "/Planlibs/getPlanLibsByid", data : { id : data.id },
		 success : function(result) {
		 if (result) {
			 $.ajax({ url : CTX + "/Planlibs/getProcessInstancesByid", data : { id : data.id },
				 success : function(result) {
					 setDatas({processinstancesid:result.result.sid});
				 }
			 });
			 
			 form.setData(result.result);
			 var id = result.result.sid;
			 $.ajax({ url : CTX + "/Planlibs/getPlanlibprojects",data : {id : id},
				 success : function(lib) {
					 if(lib.result.length == 0){
						 num = 1;
						 search.get("state_1").setValue(0);
					 }else{
						 num = lib.result.length;
					 }
					if(admin == 1){
						orgid = orgs.getText();
					}else if(data.sub == 1){
						orgid = result.result.sorgid;
					}else{
						orgid = orgid;
					}
					
					prolib.url = CTX + "/projectlib/getProjectlibSelect?orgid="+orgid+"&plantype="+data.type;
					prolib.load();
					
					$.ajax({ url : CTX + "/projectlib/getProjectlibSelect",data : {orgid : orgid,plantype:data.type},
						 success : function(proData) {
							 prolibData = proData.result;
//							 alert(prolibData);
							 
							//得到机构data
							 $.ajax({ url : CTX + "/orgs/orgsaudit",
								 success : function(orgData) {
									 orgidData = orgData.result;
									 var size = lib.result.length;
									 var ssorgid = "";
									 if(lib.result[1]){
										 ssorgid = lib.result[1].sorgid;
									 }
									//得到用户data
									 $.ajax({ url : CTX + "/user/findUsersOrgid",data : {orgid : ssorgid},
										 success : function(usersData) {
											 userData = usersData.result;
											 
											 for(var i =1;i<=lib.result.length;i++){
												 if(i==1){
													 var orgids = search.get("org_1");
													 orgids.onvaluechanged= onUserByOrgValue;
													 orgids.setValue(lib.result[i-1].sorgid);
													 
													 var user = search.get("userid_1");
													 user.defaultValue = lib.result[i-1].suserid;
													 user.loadData(userData);
													 
													 search.get("prolib_1").setValue(lib.result[i-1].sprojectlibid);
//													 search.get("org_1").setValue(lib.result[i-1].sorgid);
//													 search.get("userid_1").setValue(lib.result[i-1].suserid);
													 search.get("ldstartdate_1").setValue(lib.result[i-1].ldstartdate);
													 search.get("ldenddate_1").setValue(lib.result[i-1].ldenddate);
													 search.get("sreason_1").setValue(lib.result[i-1].sreason);
													 search.get("state_1").setValue(0);
													 search.get("prosid_1").setValue(lib.result[i-1].sid);
												 }else{
													 var trHTML = "<tr id='t_"+i+"'><td align='center' class='bo'><div id='prolib_"+i+"' class='search-select' idField='sid' textField='sname' width='100' placeHolder='请选择' enabled = 'false' defaultValue='"+lib.result[i-1].sprojectlibid+"'></div></td>" +
														"<td align='center' class='bo'><div id='org_"+i+"' class='search-treeselect' idField='sid' parentField='sparentid' textField='sname' width='100' placeHolder='请选择' enabled = 'false' defaultValue='"+lib.result[i-1].sorgid+"'></div></td>"+
														"<td align='center' class='bo'><div id='userid_"+i+"' class='search-select' width='100' idField='sid' textField='sname' placeHolder='请选择' enabled = 'false' defaultValue='"+lib.result[i-1].suserid+"'></div></td>" +
														"<td align='center'><div id='ldstartdate_"+i+"' class='search-datepicker' width='100' style='float: left;' enabled = 'false' ></div></td>" +
														"<td align='center'><div id='ldenddate_"+i+"' class='search-datepicker' width='100' style='float: left;' enabled = 'false'></div></td>" +
														"<td align='center'><div id='sreason_"+i+"' class='search-textbox' width='160' enabled = 'false'></div></td>" +
														"<td align='center' width='100'>" +
														"</td></tr>";
														$("#t_"+(i-1)).after(trHTML);
														search.parse();
//														var state = search.get("state_"+i);
//														
//														state.setValue(0);
//														search.get("prosid_"+i).setValue(lib.result[i-1].sid);
														search.get("ldstartdate_"+i).setValue(lib.result[i-1].ldstartdate);
														search.get("ldenddate_"+i).setValue(lib.result[i-1].ldenddate);
														search.get("sreason_"+i).setValue(lib.result[i-1].sreason);
//														search.get("state_"+i).setValue(0);
														
											        	var users = search.get("userid_"+i);
														var prolibs = search.get("prolib_"+i);
														var orgids  = search.get("org_"+i);
														orgids.onvaluechanged= onUserByOrgValue;
														prolibs.loadData(prolibData);	
														orgids.loadData(orgidData);
														users.loadData(userData);
												 }
											 }
										 } });
									 
								 } });
							
						 } });
					 $.ajax({ url : CTX + "/Planlibs/getAttachs",data : {id : id},
						 success : function(ach) {
							 $.each(ach.result, function(index) {
									var $img = $('<img width="200" src="' + NETWORK_ROOT + this.spathattach + '">');
									$img.data(this);
									$img.data("index", index);
									$("#idCard").append($img);
									idcardFile.arr.push({sname : this.snameattach, spath : this.spathattach});
								})
								// 身份证照片点击事件
								$("#idCard").on("dblclick", "img", function() {
									var $this = $(this);
									idcardFile.index = $this.data("index");
									Globle.fun.preview(idcardFile);
								})
						 } });
			} });
			 
			 
		 } else {
			 top.search.error({ content : "系统错误!" });
		 }
		 } });
	 }
}

// 保存操作
function save() {
	if (!form.validate()) {
		return;
	}
	// 参数拼接
	var str = "?ationpath=" + ationpath
			+ "&ationsNams=" + ationsNams
			+ "&num=" + num;	
	$.post(CTX + "/Planlibs/save" + str, form.getData(), function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else if( result.status == "false"){
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

var colorList;
var count;

$(function(){
  count= 3;
  loadFlow(count);
  checkColor(colorList);
    
})
 

//页面跳转
function methodBtn(index, method, end) {
    var fFor;
    if (end != true) {
        if (method == "back") {
            if (index == 1) {
                fFor = ".for" + String.fromCharCode(index + 65);
            } else {
                fFor = ".for" + String.fromCharCode(index + 64);
            }
            $(fFor).removeClass("for-cur");
            loadFlowDiv(index);
            checkColor("default");
        } else if (method == "forward") {
            fFor = ".for" + String.fromCharCode(index + 65);
            $(fFor).addClass("for-cur");
            loadFlowDiv(index+1);
            checkColor(index+1);
        }
    } else if (end == true) {
       
    }

}
//确定流程颜色状态
function checkColor(color) {
    if (color != "default") {
        $(".flowList.for-cur").css({ "border": "none"});
        $(".flowListBox .for-cur em").css({ "background-color": "#1ABB9C"});
        $(".flowListBox .for-cur em").css({ "border": "0px none #000" });
		$(".flowListBox .for-cur strong").css({"color":"#1ABB9C"}); 
    } else {
        $this = $('.flowList:not(.for-cur)');
        $this.css({ "border": "none", "background-color": "none" });
        $this.children("em").css({ "background-color": "#ccc" });
		$this.children("strong").css({"color":"#ccc"}); 
    }
    /*让当前选中步骤变为深蓝色*/
	 var obj=$('.for-cur:last');
	 obj.css({ "border": "none", "background-color": "none" });
	 obj.children("em").css({ "background-color": "#34495e" });
	 obj.children("strong").css({"color":"#34495e"}); 
}
//确定步骤的宽度占比
function fixWidth(count) {
    var part = parseInt(100 / count) + "%";
    $(".flowListBox .flowList").css("width", part);
}
//加载步骤
function loadFlow(count){
  var flowFor;
  var flowVar="";
  var str = "";
  for(var i=1;i<=count;i++){
    flowFor="for"+String.fromCharCode(i+64);
    if(i==1){
      flowVar += "<div class='flowList for-cur "+flowFor +"' style='position:relative'>\n";
      flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><strong id='jb' style='position:absolute;left:40%;'>基本信息</strong>\n";
      flowVar += "</div>\n";
    }else{
    	if(i == 2){
    	  flowVar += "<div class='flowList "+flowFor +"' style='position:relative'>\n";
	      flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><strong id='file' style='position:absolute;left:40%;'>上传附件</strong>\n";
	      flowVar += "</div>\n";
    	}else{
    		flowVar += "<div class='flowList "+flowFor +"' style='position:relative'>\n";
    	      flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><strong id='audit' style='position:absolute;left:40%;'>审核意见</strong>\n";
    	      flowVar += "</div>\n";
    	}
    }

  }
  $(".flowListBox").html(flowVar);
  fixWidth(count);
  loadFlowDiv(1);
  checkBtn(1,count);
}
//加载内容详情
function loadFlowDiv(index){ 

 if(index==1){$("#contA").removeClass("contentList");$("#contA").siblings().addClass("contentList")}
 if(index==2){
	 $("#contB").removeClass("contentList");$("#contB").siblings().addClass("contentList")
 }
 if(index==3){
	 $("#contC").removeClass("contentList");$("#contC").siblings().addClass("contentList")
 }
// if(index==4){$("#contD").removeClass("contentList");$("#contD").siblings().addClass("contentList")}


}
//上一步下一步按钮点击事件
var maxstep=1;
function checkBtn(index, count) {
	
    $("#btnBack").addClass("disabled");
	 /*默认进来隐藏上一步按钮*/
	$("#btnBack").hide();	
	/*下一步点击事件*/ 
    $("#file").click(function () {	
        methodBtn(1, 'forward', false);
    });
    
    $("#audit").click(function () {	
        methodBtn(2, 'forward', false);
    });
	/*上一步点击事件*/
    $("#jb").click(function () {
            methodBtn(1, 'back', false);
			 $("#btnNext").show();
            if (index == 1) {
				/*如果当前为第一步 则给上一步添加disabled属性*/
                $("#btnBack").addClass("disabled");
            } 
			
    });
}
/*上一步*/
function refreshBack(index) {
    return index;

}

