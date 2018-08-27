search.parse();
form = new Form("form");
var grid = search.get("datagrid");
var itype = search.get("itype");
var iisdepartment = search.get("iisdepartment");
var sid = search.get("sid");
var sparentid = search.get("sparentid");
var sauditorgid = search.get("sauditorgid");
var str1 = [];
var str2 = [];
var action = "";
var upload1;
var flag2 = true;
var flag3 = true;
var sparenttype = new Array();
var delarr = new Array();
var urls = new Array();
var filedata = new Array();
var shenjiju = Globle.constant.OrgType[0].value;
var __orgtype = new Array();
var __orgid = "";
var __parentid = "";
var __iisdepartment = 1;
var no = Globle.constant.YesOrNo[1].value;
var area = search.get("area");
var list = new Array();
var headlist = new Array();
var check = new Array();
var flag = false;
/**
 * 页面加载。
 */
function setData(data){
	$('#btn').on('click', function () {
        Upload.uploader.upload();
    });
	$("#org1").hide();
	$("#org2").hide();
	$("#user1").hide();
	$("#user2").hide();
	$("#phone1").hide();
	$("#phone2").hide();
	$("#area1").hide();
	$("#area2").hide();
	$("#orgtype1").hide();
	$("#orgtype2").hide();
	document.getElementById("alert").style.display = "block";
	$.ajax({
		url : CTX+"/orgs/gorgs",
		success : function(result){
			sparenttype = result.result;
		}
	});
	area.url = CTX+"/orgs/gas";
	area.load();
	itype.loadData(Globle.constant.OrgType);
	iisdepartment.loadData(Globle.constant.YesOrNo);
	action = data.action;
	if(data.action == "add"){//新增按钮进入时加载数据
		count = 4;
		loadFlow(count);
		$("#tab4").hide();
		sparentid.url = CTX+"/orgs/guop";
		sparentid.load();
		sparentid.loaded = function(e){
			if(__usertype != Globle.constant.userTypes[0].id){
				$.ajax({
					url : CTX+"/orgs/gui",
					success : function(result){
						__orgtype = result.result.orgtype;
						__orgid = result.result.orgid;
						__parentid = result.result.parentid;
						__iisdepartment = result.result.iisdepartment;
						__areaid = result.result.areaid;
						if(__orgtype[0] == Globle.constant.OrgType[0].value){
							if(data.sid!=null){
								sparentid.setValue(data.sid);
							}else{
								sparentid.setValue(__orgid);
							}
							if(__parentid == null){
								$("#area1").show();
								$("#area2").show();
							}else{
								area.setValue(__areaid);
								area.setEnabled(false);
							}
						}else{
							$("#tab4").hide();
							sparentid.setValue(__orgid);
							sparentid.setEnabled(false);
							iisdepartment.setValue(Globle.constant.YesOrNo[0].value);
							iisdepartment.setEnabled(false);
						}
					}
				});
			}else{
				sparentid.setValue(data.sid);
				
				$("#area1").show();
				$("#area2").show();
			}
		}
		changsparentid();
		sauditorgid.url = CTX+"/orgs/grorgs";
		sauditorgid.load();
	}else if(data.action == "edit"){//编辑按钮进入后加载项
		count= 4; 
		loadFlow(count);
		sparentid.url = CTX+"/orgs/guop?sid="+data.sid;
		sparentid.load();
		sauditorgid.url = CTX+"/orgs/grorgs?sid="+data.sid;
		sauditorgid.load();
		sparentid.loaded = function(e){
			$.ajax({
				url : CTX+"/orgs/gobs",
				data : {sid : data.sid},
				success : function(result){
					form.setData(result.result);
					var sparent = result.result.sparent;
					var sitype = result.result.itype;
					area.setValue(result.result.sareaid);
					if(__usertype != Globle.constant.userTypes[0].id){
						$.ajax({
							url : CTX+"/orgs/gui",
							success : function(result){
								__orgtype = result.result.orgtype;
								__orgid = result.result.orgid;
								__parentid = result.result.parentid;
								__iisdepartment = result.result.iisdepartment;
								if(__orgtype[0] == Globle.constant.OrgType[0].value){
									sparentid.setValue(sparent);
									if(__parentid == null){
										$("#area1").show();
										$("#area2").show();
									}else{
										area.setEnabled(false);
									}
									if(__orgid!=null&&__orgid==search.get("sid").getValue()){
										$("#tab4").hide();
									}
								}else{
									$("#tab4").hide();
									sparentid.setValue(sparent);
									sparentid.setEnabled(false);
									iisdepartment.setEnabled(false);
								}
								changsparentid();
								itype.setValue(sitype);
							}
						});
					}else{
						sparentid.setValue(result.result.sparent);
						$("#area1").show();
						$("#area2").show();
					}
					changsparentid();
					itype.setValue(result.result.itype);
					changiisdepartment();
					//加载数据为审计机构时直接显示是否显示该库选项并加载数据
					if(result.result.itype==shenjiju){
						$("#org1").hide();
						$("#org2").hide();
					}
					//封装附件List
					for(var i =0;i<result.result.attch1.length;i++){
						str1.push({sid:result.result.attch1[i].sid,sname:result.result.attch1[i].sname,spath:result.result.attch1[i].spath});
					}
					for(var i =0;i<result.result.attch2.length;i++){
						str2.push({sid:result.result.attch2[i].sid,sname:result.result.attch2[i].sname,spath:result.result.attch2[i].spath,sdesc:result.result.attch2[i].sdesc,sdictionarieid:result.result.attch2[i].sdictionarieid});
					}
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
	if(flag2&&flag3){
		sumbit();
	}else if(!flag2&&flag3){
		upload1.upload();
	}else{
		Upload2.upload();
	}
}
//提交数据
function sumbit(){
	var sname = new Array();
	var spath = new Array();
	var filesname = new Array();
	var filespath = new Array();
	var filesdesc = new Array();
	var filesdictionarieid = new Array();
	var filesid = new Array();
	var del = new Array();
	var arrdel = new Array()
	del.push(delarr);
	//封装附件数据
	if(urls!=null&&urls.length>0){
		for(var i = 0;i<urls.length;i++){
			if(urls[i].sname==null||urls[i].sname==""){
				filesname.push("0");
			}else{
				filesname.push(urls[i].sname);
			}
			if(urls[i].spath==null||urls[i].spath==""){
				filespath.push("0");
			}else{
				filespath.push(urls[i].spath);
			}
			if(urls[i].sdesc==null||urls[i].sdesc==""){
				filesdesc.push("0");
			}else{
				filesdesc.push(urls[i].sdesc);
			}
			if(urls[i].sdictionarieid==null||urls[i].sdictionarieid==""){
				filesdictionarieid.push("0");
			}else{
				filesdictionarieid.push(urls[i].sdictionarieid);
			}
			if(urls[i].sid==null||urls[i].sid==""){
				filesid.push("0");
			}else{
				filesid.push(urls[i].sid);
			}
		}
	}
	if(!flag2){
		for(var i = 0;i<filedata.length;i++){
			sname.push(filedata[i].sname);
			spath.push(filedata[i].spath);
		}
		arrdel = upload1.delArr;
	}
	var funarr = new Array();
    $.each($('input:checkbox:checked'),function(i){
        funarr[i] = $(this).val();
    });
	$.post(CTX + "/orgs/uorg",
		{
		sid : sid.getValue(),
		sname : search.get("sname").getValue(),
		sdes : search.get("sdes").getValue(),
		sparentid : sparentid.getValue(),
		iisdepartment : iisdepartment.getValue(),
		itype : itype.getValue(),
//		sdictionarieid : sdictionarieid.getValue(),
		urlsname : sname.join(","),
		urlspath : spath.join(","),
		filesname : filesname.join(","),
		filespath : filespath.join(","),
		filesdesc : filesdesc.join(","),
		filesdictionarieid : filesdictionarieid.join(","),
		filesid : filesid.join(","),
		del : arrdel.join(","),
		delqua : del.join(","),
		sauditorgid : sauditorgid.getValue(),
		username : search.get("username").getValue(),
		userphone : search.get("userphone").getValue(),
		areaid : area.getValue(),
		sfunctiongroupid : funarr.join(","),
		flag : flag
		},
		function(result) {
		if(result.status){
			search.info({content:result.result.isSuccess,funl:function(){
				if(result.result.state) window.closeWindow();
			}});
		}else search.error({content:"系统错误!"});
	})
}
//控制审计机构的显示与隐藏
function changsauditorgid(){
	var type = itype.getValue();
	var typelist = type.split(",");
	for(var i = 0;i<typelist.length;i++){
		if(typelist[i]==shenjiju){
			itype.setValue(shenjiju);
		}
	}
}
//父级机构值改变事件
function changsparentid(){
	if(search.get("sparentid").getValue()!=""){
		var str = "";
		for(var i =0;i<sparenttype.length;i++){
			if(search.get("sparentid").getValue()==sparenttype[i].sid){
				str = sparenttype[i].sparenttype;
			}
		}
		var orgtype = str.split(",");
		itype.setValue(orgtype);
	}else{
		itype.setValue(Globle.constant.AuditOrgType[0].value);
	}
}
//是否部门值变化触发事件
function changiisdepartment(){
	var iisdepartment = search.get("iisdepartment").getValue();
	if(iisdepartment==1){
		$("#user1").hide();
		$("#user2").hide();
		$("#phone1").hide();
		$("#phone2").hide();
		$("#tab4").hide();
	}else{
		$("#user1").show();
		$("#user2").show();
		$("#phone1").show();
		$("#phone2").show();
		$("#tab4").show();
	}
	if(__orgid!=""&&__orgid==search.get("sid").getValue()){
		$("#tab4").hide();
	}
}
//验证审计机构不能为空
function sauditorgidValidate(e){
//	var type = itype.getValue();
//	if(type!=shenjiju&&sauditorgid.getValue()==""){
//		e.message = "请选择审计机构";
//		e.pass = false;
//	}

}
//验证联系人不能为空
function nameValidate(e){
	var name = search.get("username").getValue();
	var flag = iisdepartment.getValue();
	if(flag==no&&name==""){
		e.message = "联系人不能为空";
		e.pass = false;
	}

}
//验证联系电话不能为空
function phoneValidate(e){
	var phone = search.get("userphone").getValue();
	var flag = iisdepartment.getValue();
	if(flag==no&&phone==""){
		e.message = "联系电话不能为空";
		e.pass = false;
	}
	if (flag==no&&!/^(0\d{2,3}-?)?\d{7,8}$|^1[34578]\d{9}$/.test(e.value)) {
		e.message = "必须是正确的手机号码";
		e.pass = false;
	}
}
//验证所属区县不能为空
function areaValidate(e){
	if(__usertype == Globle.constant.userTypes[0].id||(__orgtype[0] == Globle.constant.OrgType[0].value&&__parentid == null)){
		if(area.getValue()==""){
			e.message = "所属区县不能为空";
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
var count;
 
//确定步骤的宽度占比
function fixWidth(count) {
    var part = parseInt(100 / count) + "%";
    $(".flowListBox .flowList").css("width", part);
}
//加载步骤
function loadFlow(count){
  var flowFor;
  var flowVar="";
  for(var i=1;i<=count;i++){
    flowFor="for"+String.fromCharCode(i+64);
    if(i==1){
      flowVar += "<div id='tab"+i+"' data='" + i + "' class='flowList for-cur "+flowFor +"' style='position:relative'>\n";
      flowVar += "<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;' onclick='ButtonClick("+i+");'>基本信息</strong>\n";
      flowVar += "</div>\n";
    }else if(i==2){
      flowVar += "<div id='tab"+i+"' data='" + i + "' class='flowList "+flowFor +"' style='position:relative'>\n";
      flowVar += "<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;' onclick='ButtonClick("+i+");'>营业执照</strong>\n";
      flowVar += "</div>\n";
    }else if(i==3){
		flowVar += "<div id='tab"+i+"' data='" + i + "' class='flowList "+flowFor +"' style='position:relative'>\n";
		flowVar += "<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;' onclick='ButtonClick("+i+");'>机构资质</strong>\n";
		flowVar += "</div>\n";
    }else{
    	flowVar += "<div id='tab"+i+"' data='" + i + "' class='flowList "+flowFor +"' style='position:relative'>\n";
		flowVar += "<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;' onclick='ButtonClick("+i+");'>用户授权</strong>\n";
		flowVar += "</div>\n";
    }

  }
  $(".flowListBox").html(flowVar);
  fixWidth(count);
  ButtonClick(1);
  bindBottonEvent();
}


//步骤点击效果
function ButtonClick(index){
	if(index == 1){
		
			$("#btnBack").hide();
			$("#btnNext").show();
			$("#btnok").show();
		
		}else if(index == count){
			
			$("#btnBack").show();
			$("#btnNext").hide();
			$("#btnok").show();
			
		}else{
				
			$("#btnBack").show();
			$("#btnNext").show();
			$("#btnok").show();
				
		}
		if(index == 3&&iisdepartment.getValue()==Globle.constant.YesOrNo[0].value){
			$("#btnNext").hide();
		}else{
			$("#btnNext").show();
		}
		if(index == 3&&__orgid!=""&&__orgid==search.get("sid").getValue()){
			$("#btnNext").hide();
		}
		if(action == "add"){
			if(index == 4){
				$("#btnNext").hide();
				$.ajax({
					url : CTX+"/orgs/gtufg",
					success : function(result){
						if(result.result.list.length!=0){
							result.result.map.id = "";
							list[0] = result.result.map;
							grid.loadData(list);
							headlist = result.result.list;
							search.remove("datagrid",true);
				  			var str = "<div property='columns'>";
				  			for(var i =0;i<headlist.length;i++){
				  				str += "<div field='"+headlist[i].head+"' width='100' headAlign='center'  textAlign='center' allowSort='true'>"+headlist[i].sname+"</div> ";
				  			}
				  			str += "</div>"
				  	      	$("#datagrid").empty().append($(str));
				  	      	search.parse();
				  	      	grid = search.get("datagrid");
							grid.loadData(list);
						}else{
							search.warn({content:"没有可授权功能组!"});
							return;
						}
					}
				});
			}
		}else if(iisdepartment.getValue() == Globle.constant.YesOrNo[1].value && action == "edit"){
			if(index == 4){
				$("#btnNext").hide();
				$.ajax({
					url : CTX+"/orgs/gtufg?orgid="+search.get("sid").getValue(),
					success : function(result){
						if(result.result.list.length!=0){
							result.result.map.id = "";
							list[0] = result.result.map;
							headlist = result.result.list;
							check = result.result.check;
							search.remove("datagrid",true);
				  			var str = "<div property='columns'>";
				  			for(var i =0;i<headlist.length;i++){
				  				str += "<div field='"+headlist[i].head+"' width='100' headAlign='center'  textAlign='center' allowSort='true'>"+headlist[i].sname+"</div> ";
				  			}
				  			str += "</div>"
				  	      	$("#datagrid").empty().append($(str));
				  	      	search.parse();
				  	      	grid = search.get("datagrid");
							grid.loadData(list);
						}else{
							search.warn({content:"没有可授权功能组!"});
							return;
						}
					}
				});
			}
		}
		if(index==4){
			flag = true;
		}
		//确定流程颜色状态
		for(var i = 1 ; i <= count; i++){
			
				var checkColor = $("#tab"+i);
				var em = $(checkColor.find("em"));
				var strong = $(checkColor.find("strong"));
				
				if(i < index){
						checkColor.removeClass("for-cur");
						em.css({ "background-color": "#ccc"});
						strong.css({"color":"#ccc"});
					}else if(i > index ){
						checkColor.removeClass("for-cur");
						em.css({ "background-color": "#34495e"});
						strong.css({"color":"#34495e"});
					}else{
						checkColor.addClass("for-cur");
						em.css({ "background-color": "#1ABB9C"});
						strong.css({"color":"#1ABB9C"});
					}	
			}
			
			//加载内容详情
			var cur = $("#cont"+index);
			cur.show();
			cur.siblings().hide();
			
			if(action == "add"){
            	if(index==2){
            		if(flag2){
            			upload1 = Upload.upload({
            				relativePath: Globle.constant.upload.org,
                            uploadFinished: function (datas) {
                	            filedata = datas;
                	            sumbit();
                	        }
                	    });
            			flag2 = false;
            		}
                }
                if(index==3){
                	if(flag3){
                		Upload2.init({
                		    callback: function (resultArr) {
                		    	urls = resultArr;
                		    	if(flag2&&!flag3){
                		    		sumbit();
                		    	}else{
                		    		upload1.upload();
                		    	}
                		    },
                		    url: "/orgs/gorgqua",
            		        data: {},
            		        wbOpts: {
            		            relativePath: Globle.constant.upload.org
            		        }
                		});
                		flag3 = false;
                	}
                }
            }else if(action == "edit"){
            	if(index==2){
            		if(flag2){
            			upload1 = Upload.upload({
            				relativePath: Globle.constant.upload.org,
                            uploadFinished: function (datas) {
                	            filedata = datas;
                	            sumbit();
                	        },
            		        initFiles: {
            		            rootPath: "/upload",
            		            files: str1
            		        }
                	    });
            			flag2 = false;
            		}
                }
                if(index==3){
                	if(flag3){
                		Upload2.init({
                		    callback: function (resultArr,delArr) {
                		    	urls = resultArr;
                		    	delarr = delArr;
                		    	if(flag2&&!flag3){
                		    		sumbit();
                		    	}else{
                		    		upload1.upload();
                		    	}
                		    },
            		        url: "/orgs/gorgqua",
            		        data: {},
            		        initData: str2,
            		        wbOpts: {
            		            relativePath: Globle.constant.upload.org
            		        }
                		});
                		flag3 = false;
                	}
                }
            }
	}

//上一步下一步按钮点击事件
function bindBottonEvent(){
		$("#btnBack").click(function(e) {
			var index = parseInt($(".for-cur").attr("data"));
            ButtonClick(index - 1);
        });
		$("#btnNext").click(function(e) {
			var index = parseInt($(".for-cur").attr("data"));
            ButtonClick(index + 1);
        });
	}
function drawcell(e){
	for(var i = 0;i<headlist.length;i++){
		if(e.column.field==headlist[i].head){
			var html = "<input type='checkbox' id='funid' name='funid' value='"+headlist[i].head+"' />";
			for(var j = 0;j<check.length;j++){
				if(check[j].sfunctiongroupid == headlist[i].head){
					html = "<input type='checkbox' id='funid' name='funid' value='"+headlist[i].head+"' checked='checked' />";
				}
			}
			e.html = html;
		}
	}
}
