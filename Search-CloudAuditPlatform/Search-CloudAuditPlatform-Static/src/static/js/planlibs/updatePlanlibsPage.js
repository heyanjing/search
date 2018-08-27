search.parse();
var form = new Form("Planform");

var orgs = search.get("sorgid"); 
//enabled = 'false'
//var user = search.get("userid_1");
var prolib = search.get("prolib_1");
var istate = "";
var isLoad = true;

var y = search.get("iyear");
y.onvaluechanged = onvalue;
var countdown=180;
var num = 1;
var userData = "";
var prolibData = "";
var orgidData = "";
var isLaststep = false;
var sub = "";
var itype = search.get("itype");
var oname = "";
itype.setValue(type);

var SORGID = "enabled = 'false'";
var SUSERID = "enabled = 'false'";
var LDSTARTDATE = "enabled = 'false'";
var LDENDDATE = "enabled = 'false'";	
var SREASON = "enabled = 'false'";

$(function() {
	if(admin == 1){
		orgs.url = CTX + "/orgs/orgaudit";
		orgs.load();
		
		orgs.onvaluechanged = onOrgValue;
	}else{
//		user.url = CTX + "/user/findUsersOrgid";
//		user.load();
		
		prolib.url = CTX + "/projectlib/getProjectlibSelect?orgid="+oid+"&plantype="+type;
		prolib.load();
	}
	
	var org  = search.get("org_1");
	org.url = CTX + "/orgs/orgsaudit";
	org.load();
	
	org.onvaluechanged= onUserByOrgValue;
	
	$("#btnok").on("click", function() {
		// console.log(upload1.delArr);// 获取删除附件的id
		istate = 1;
		if(upload1){
			upload1.upload();
		}else{
			save();
		}
	});
	
	$("#btnoksub").on("click", function() {
		var data = getDatas();
		if(data.stepoperatorsid == ""){
			top.search.info({ content : "请选择步骤处理人"});
			return;
		}
		
		istate = 2;
		if(upload1){
			upload1.upload();
		}else{
			save();
		}
	});
	//设置初始化状态为删除
	var state = search.get("state_1");
	state.setValue(0);
})

function onUserByOrgValue(e){
	var str = this.id;
	str = str.split("_")[1];
	if(e.value != "" && isLoad){
		var user = search.get("userid_"+str);
		user.url = CTX + "/user/findUsersOrgid";
		user.load({orgid : e.value });
	}
}

function onOrgValue(e){
	var orgid = orgs.getValue();
	if(orgid != 0){
//		user.url = CTX + "/user/findUsersOrgid";
//		user.load({orgid : orgid });
		prolib.url = CTX + "/projectlib/getProjectlibSelect";
		prolib.load({orgid : orgid,plantype : type });
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
//			alert(oname);
//			if(oname == ""){
				search.get("sname").setValue(str);
//			}

		}
		
	}
}

//var table =document.getElementById("tb1");
//var rows = table.rows.length;

//新增
function add(){
	if(admin == 1 && orgs.getValue() == 0){
		alert("先选择审计机构");
		return;
	}
	num += 1;
	var trHTML = "<tr id='t_"+num+"'><td align='center' class='bo'><div id='prolib_"+num+"' class='search-select' idField='sid' textField='sname' width='100' placeHolder='请选择'></div></td>" +
			"<td align='center' class='bo'><div id='org_"+num+"' class='search-treeselect' idField='sid' parentField='sparentid' textField='sname' width='100' placeHolder='请选择' "+SORGID+"></div></td>"+
			"<td align='center' class='bo'><div id='userid_"+num+"' class='search-select' width='100' idField='sid' textField='sname' placeHolder='请选择' "+SUSERID+"></div></td>" +
			"<td align='center'><div id='ldstartdate_"+num+"' class='search-datepicker' width='100' "+LDSTARTDATE+" placeHolder='请选择开始时间'></div></td>" +
			"<td align='center'><div id='ldenddate_"+num+"' class='search-datepicker' width='100' "+LDENDDATE+" placeHolder='请选择结束时间'></div></td>" +
			"<td align='center'><div id='sreason_"+num+"' class='search-textbox' width='100' "+SREASON+"></div></td>" +
			"<td align='center' width='100'><a id='del_"+num+"' href='#' style='color: red;' onclick='del(\"" + num + "\")'><i class='fa fa-close'></i></a>" +
			"<div id='state_"+num+"' class='search-texthide'></div><div id='prosid_"+num+"' class='search-texthide'></div></td></tr>";
	$("#t_"+(num-1)).after(trHTML);
	search.parse();
	var state = search.get("state_"+num);
	state.setValue(0);
	
	var users = search.get("userid_"+num);
	var prolibs = search.get("prolib_"+num);
	if(admin == 1){
		var orgid = orgs.getValue();
		if(orgid != 0){
			prolibs.url = CTX + "/projectlib/getProjectlibSelect";
			prolibs.load({orgid : orgid,plantype : type });
			
//			users.url = CTX + "/user/findUsersOrgid";
//			users.load({orgid : orgid });
		}
	}else{
		
//		users.url = CTX + "/user/findUsersOrgid";
//		users.load();
		
		prolibs.url = CTX + "/projectlib/getProjectlibSelect";
		prolibs.load({orgid : oid,plantype : type});
	}
	
	var orgids  = search.get("org_"+num);
	orgids.url = CTX + "/orgs/orgsaudit";
	orgids.load();
	orgids.onvaluechanged= onUserByOrgValue;
	
//	var orgSid = orgids.getValue();
//	user.url = CTX + "/user/findUsersOrgid";
//	user.load({orgid : orgSid });
}

//删除
function del(iRet){
	var state = search.get("state_"+iRet);
	if(state.getValue() == 0){ //删除
		state.setValue(1);
		$("#del_"+iRet).html("撤销");
		$("#del_"+iRet).empty().append($("<i class='fa fa-mail-reply'></i>"));
	}else if(state.getValue() == 1){//撤销
		state.setValue(0);
		//$("#del_"+iRet).html("删除");
		$("#del_"+iRet).empty().append($("<i class='fa fa-close'></i>"));
	}
}

// 初始化
//Upload2.init({
//			url : "/user/findListMapDictionariesByItype",
//			data : {
//				type : 3
//			},
//			callback : function(resultArr, delArr) {
//				console.log("上传结果", resultArr, delArr);
//				for (var i = 0; i < resultArr.length; i++) {
//					sdictionarieid += resultArr[i].sdictionarieid + ",";
//					qualinames += resultArr[i].sname + ",";
//					qualificationspath += resultArr[i].spath + ",";
//					sdesc += resultArr[i].sdesc + ",";
//				}
//				sdictionarieid = sdictionarieid.substring(0,
//						sdictionarieid.length - 1);
//				qualinames = qualinames.substring(0, qualinames.length - 1);
//				qualificationspath = qualificationspath.substring(0,
//						qualificationspath.length - 1);
//				sdesc = sdesc.substring(0,sdesc.length - 1);
//
//				// 保存
//				save();
//			},
//			wbOpts: {
//		        relativePath: '/search/user'
//		    }
//		});
var processstepid = "";
/**
 * 父级页面传值
 * 
 * @param data
 */
function setData(data) {
	 if (data.action == "edit") {
		 if(data.sub == 1){
			 sub = data.sub;
			 methodBtn(2, 'forward', false);
			 checkBtn(3,3);
			 $("#btnNext").hide();
	         $("#btnBack").show();
//			 $("#btnoksub").show();
//			 $("#back").show();
			 $("#btnBack").removeClass("disabled");
			 
		 }
		 var processinstancesid = search.get("processinstancesid");
		 processinstancesid.setValue(data.sProcessInstanceId);
		 setDatas({processinstancesid:data.sProcessInstanceId,functionid:data.funcid,sauditorgid : orgpid},function (test){
			 if(test.resultData.result.isupportback == 2){
					$("#back").hide();
			 }else{
				 $("#back").show();
				 processstepid = test.resultData.result.sprocessstepid;
			 }
			 if (test.statuscode == 100001) {
					// 最后一步
					$("#btnoksub").hide();
					isLaststep = true;
			 }
		 });
		 $.ajax({ url : CTX + "/Planlibs/getPlanLibsByid", data : { id : data.id },
		 success : function(result) {
		 if (result) {
			 form.setData(result.result);
			 oname = result.result.sname;
//			 alert(oname);
			 search.get("sname").setValue(oname);
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
					}else if(sub == 1){
						orgid = result.result.sorgid;
					}else{
						orgid = orgid;
					}
					 //得到项目data
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
//													 user.setValue(lib.result[i-1].suserid);
													 search.get("ldstartdate_1").setValue(lib.result[i-1].ldstartdate);
													 search.get("ldenddate_1").setValue(lib.result[i-1].ldenddate);
													 search.get("sreason_1").setValue(lib.result[i-1].sreason);
													 search.get("state_1").setValue(0);
													 search.get("prosid_1").setValue(lib.result[i-1].sid);
													 
												 }else{
													 var trHTML = "<tr id='t_"+i+"'><td align='center' class='bo'><div id='prolib_"+i+"' class='search-select' idField='sid' textField='sname' width='100' placeHolder='请选择' defaultValue='"+lib.result[i-1].sprojectlibid+"'></div></td>" +
														"<td align='center' class='bo'><div id='org_"+i+"' class='search-treeselect' "+SORGID+" idField='sid' parentField='sparentid' textField='sname' width='100' placeHolder='请选择' defaultValue='"+lib.result[i-1].sorgid+"'></div></td>"+
														"<td align='center' class='bo'><div id='userid_"+i+"' class='search-select' "+SUSERID+" width='100' idField='sid' textField='sname' placeHolder='请选择' defaultValue='"+lib.result[i-1].suserid+"'></div></td>" +
														"<td align='center'><div id='ldstartdate_"+i+"' class='search-datepicker' "+LDSTARTDATE+" width='100' style='float: left;' ></div></td>" +
														"<td align='center'><div id='ldenddate_"+i+"' class='search-datepicker' "+LDENDDATE+" width='100' style='float: left;' ></div></td>" +
														"<td align='center'><div id='sreason_"+i+"' class='search-textbox' "+SREASON+" width='160'></div></td>" +
														"<td align='center' width='100'><a id='del_"+i+"' href='#' style='color: red;' onclick='del(\"" + i + "\")'><i class='fa fa-close'></i></a>" +
														"<div id='state_"+i+"' class='search-texthide'></div><div id='prosid_"+i+"' class='search-texthide'></div></td></tr>";
														$("#t_"+(i-1)).after(trHTML);
														search.parse();
														var state = search.get("state_"+i);
														
														state.setValue(0);
														search.get("prosid_"+i).setValue(lib.result[i-1].sid);
														search.get("ldstartdate_"+i).setValue(lib.result[i-1].ldstartdate);
														search.get("ldenddate_"+i).setValue(lib.result[i-1].ldenddate);
														search.get("sreason_"+i).setValue(lib.result[i-1].sreason);
														search.get("state_"+i).setValue(0);
														
											        	var users = search.get("userid_"+i);
														var prolibs = search.get("prolib_"+i);
														var orgids  = search.get("org_"+i);
														orgids.onvaluechanged= onUserByOrgValue;
														prolibs.loadData(prolibData);	
														orgids.loadData(orgidData);
														users.loadData(userData);
												 }
												 
												 if(sub == 1){
														$("#del_"+i).hide();
														$("#addid").hide();
												 }
											 }
											 
											 $.ajax({ url : CTX + "/Planlibs/getProcessStepsAndFieldRefsByProcessInstancesIdData",data : {id : data.sProcessInstanceId},
													success : function(data) {
														var iSsorgid = false;
														var iSuserid = false;
														var iSldstartdate = false;
														var iSldenddate = false;	
														var iSreason = false;
														for(var i =0;i<data.result.length;i++){
															if(data.result[i].sfieldname == "SNAME"){
																search.get("sname").setEnabled(true);
															}
															if(data.result[i].sfieldname == "IYEAR"){
																search.get("iyear").setEnabled(true);
															}
															if(data.result[i].sfieldname == "SORGID"){
																if(search.get("org_"+i)){
//																	search.get("org_"+i).setEnabled(true);
																}
																iSsorgid = true;
																SORGID = "enabled = 'true'";
															}
															if(data.result[i].sfieldname == "SUSERID"){
																if(search.get("userid_"+i)){
//																	search.get("userid_"+i).setEnabled(true);
																}
																iSuserid = true;
																SUSERID = "enabled = 'true'";
															}
															if(data.result[i].sfieldname == "LDSTARTDATE"){
																if(search.get("ldstartdate_"+i)){
//																	search.get("ldstartdate_"+i).setEnabled(true);
																}
																iSldstartdate = true;
																LDSTARTDATE = "enabled = 'true'";
															}
															if(data.result[i].sfieldname == "LDENDDATE"){
																if(search.get("ldenddate_"+i)){
//																	search.get("ldenddate_"+i).setEnabled(true);
																}
																iSldenddate = true;
																LDENDDATE = "enabled = 'true'";
															}
															if(data.result[i].sfieldname == "SREASON"){
//																search.get("sreason_"+i).setEnabled(true);
																iSreason = true;
																SREASON = "enabled = 'true'";
															}
														}
														
														for(var i = 1;i<= size;i++){
															if(iSsorgid){
																search.get("org_"+i).setEnabled(true);
															}
															if(iSuserid){
																search.get("userid_"+i).setEnabled(true);
															}
															if(iSldstartdate){
																search.get("ldstartdate_"+i).setEnabled(true);
															}
															if(iSldenddate){
																search.get("ldenddate_"+i).setEnabled(true);
															}
															if(iSreason){
																search.get("sreason_"+i).setEnabled(true);
															}
														}
													} });
										 }
									 });
									 
									 
								 } });
							 
						 } });
					 
					 
					 
//					 setTimeout(function(){
//						 
//					 },6000)
					 $.ajax({ url : CTX + "/Planlibs/getAttachs",data : {id : id},
						 success : function(ach) {
							 for(var i =0;i<ach.result.length;i++){
									str1.push({sid:ach.result[i].sid,sname:ach.result[i].snameattach,spath:ach.result[i].spathattach});
								}
						 } });
			} });
			 
			 
		 } else {
			 top.search.error({ content : "系统错误!" });
		 }
		 } });
	 }else {
//		 setDatas({processdesignsid:processstepssid});
		 setDatas({processdesignsid:processstepssid,functionid:data.funcid,sauditorgid : orgpid},function (test){ });
		 $.ajax({ url : CTX + "/Planlibs/getProcessStepsAndFieldRefsData",data : {id : processstepssid},
				success : function(data) {
					for(var i =0;i<data.result.length;i++){
						if(data.result[i].sfieldname == "SNAME"){
							search.get("sname").setEnabled(true);
						}
						if(data.result[i].sfieldname == "IYEAR"){
							search.get("iyear").setEnabled(true);
						}
						if(data.result[i].sfieldname == "SORGID"){
							search.get("org_1").setEnabled(true);
							SORGID = "enabled = 'true'";
						}
						if(data.result[i].sfieldname == "SUSERID"){
							search.get("userid_1").setEnabled(true);
							SUSERID = "enabled = 'true'";
						}
						if(data.result[i].sfieldname == "LDSTARTDATE"){
							search.get("ldstartdate_1").setEnabled(true);
							LDSTARTDATE = "enabled = 'true'";
						}
						if(data.result[i].sfieldname == "LDENDDATE"){
							search.get("ldenddate_1").setEnabled(true);
							LDENDDATE = "enabled = 'true'";
						}
						if(data.result[i].sfieldname == "SREASON"){
							search.get("sreason_1").setEnabled(true);
							SREASON = "enabled = 'true'";
						}
					}
				} });
	 }
}

// 保存操作
function save(op) {
	if (!form.validate()) {
		return;
	}
	var data = getDatas();
	if(op == 2){
		istate = 0;
//		var processinstancesid = search.get("processinstancesid");
		//回退
		rollbackStep();
	}
//	return;
	// 参数拼接
	var str = "?ationpath=" + ationpath
			+ "&ationsNams=" + ationsNams
			+ "&num=" + num
			+ "&processstepssid=" +processstepssid
			+ "&istate=" +istate
			+ "&sresult=" + data.opinion
			+ "&sdesc=" + data.sdesc
			+ "&sprocessstepid=" +data.processstepsid
			+ "&suserid=" +data.stepoperatorsid
			+ "&del=" + upload1.delArr
			+ "&isLaststep= " + isLaststep;
	$.post(CTX + "/Planlibs/save" + str, form.getData(), function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else if( !result.status ){
			top.search.info({ content : result.msg, funl : function() {
//				window.closeWindow("ok");
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
            loadFlowDiv(index-1);
            checkColor("default");
        } else if (method == "forward") {
            fFor = ".for" + String.fromCharCode(index + 65);
            $(fFor).addClass("for-cur");
            loadFlowDiv(index+1);
            checkColor(colorList);
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
      flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;'>基本信息</strong>\n";
      flowVar += "</div>\n";
    }else{
    	if(i == 2){
    		str = "上传附件";
    	}else{
    		str = "审核意见";
    	}
      flowVar += "<div class='flowList "+flowFor +"' style='position:relative'>\n";
      flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;'>"+str+"</strong>\n";
      flowVar += "</div>\n";
    }

  }
  $(".flowListBox").html(flowVar);
  fixWidth(count);
  loadFlowDiv(1);
  checkBtn(1,count);
}
var iRet = 0;
//加载内容详情
function loadFlowDiv(index){ 

 if(index==1){$("#contA").removeClass("contentList");$("#contA").siblings().addClass("contentList")}
 if(index==2){
	 $("#contB").removeClass("contentList");$("#contB").siblings().addClass("contentList")
	 if(iRet == 0){
		 //上传附件
		 upload1 = Upload.upload({
			 relativePath: '/search/planlibs',
			 uploadFinished: function (datas) {
				 for(var i = 0; i<datas.length;i++){
					 ationpath += datas[i].spath + ",";
					 ationsNams += datas[i].sname + ",";
				 }
				 ationpath = ationpath.substring(0,ationpath.length-1);
				 ationsNams = ationsNams.substring(0,ationsNams.length-1);
				 // 保存
				 save();
			 },initFiles: {
				 rootPath: "/upload",
				 files: str1
			 }
		 })
	 }
	 iRet = 1;
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
    $("#btnNext").click(function () {	
        methodBtn(index++, 'forward', false);
		if(index>maxstep){
			maxstep=index;
			 
			}
        if (index != 1) {
			/*非第一步的时候，显示上一步*/
            $("#btnBack").removeClass("disabled");
			$("#btnBack").show();
        }
        if (index >= count) {  
		/*到最后一步时 去掉下一步 显示上一步和完成*/
            $("#btnNext").hide();
            $("#btnBack").show();
			$("#btnoksub").show();
        }
        refreshBack(index);
    });
	/*上一步点击事件*/
    $("#btnBack").click(function () {
        if (refreshBack(index) > 1) {
            methodBtn(index--, 'back', false);
			 $("#btnNext").show();
            if (index == 1) {
				/*如果当前为第一步 则给上一步添加disabled属性*/
                $("#btnBack").addClass("disabled");
            } 
			
        }
    });
}
/*上一步*/
function refreshBack(index) {
    return index;

}



