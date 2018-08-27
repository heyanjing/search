search.parse();
form = new Form("form");
form1 = new Form("form1");
var grid = search.get("datagrid");
var itype = search.get("itype");
var sid = search.get("sid");
var sparentid = search.get("sparentid");
var action = "";
var sparenttype = new Array();
var __orgtype = new Array();
var __orgid = "";
var __parentid = "";
var __iisdepartment = 1;
var __parentareaid = "";
var no = Globle.constant.YesOrNo[1].value;
var area = search.get("area");
var list = new Array();
var headlist = new Array();
var action = "";
/**
 * 页面加载。
 */
function setData(data){
	$('#btn').on('click', function () {
        Upload.uploader.upload();
    });
	document.getElementById("alert").style.display = "block";
	$.ajax({
		url : CTX+"/orgs/gorgs",
		success : function(result){
			sparenttype = result.result;
		}
	});
	action = data.action;
	area.url = CTX+"/orgs/gas";
	area.load();
	sparentid.url = CTX+"/orgs/qpsos";
	sparentid.load();
	if(data.action == "add"){
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
						__parentareaid = result.result.parentareaid;
						if(__parentareaid != null){
							area.setValue(__areaid);
							area.setEnabled(false);
						}
						sparentid.setValue(data.sid);
						for(var i = 0;i<__orgtype.length;i++){
							if(__orgtype[i] == Globle.constant.OrgType[1].value){
								$("#tab2").hide();
								$("#tab3").hide();
								$("#btnNext").hide();
							}
						}
					}
				});
			}else{
				sparentid.setValue(data.sid);
			}
		}
	}else{
		$("#tab2").hide();
		$("#tab3").hide();
		$("#btnNext").hide();
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
								__parentareaid = result.result.parentareaid;
								if(__parentareaid != ""){
									area.setEnabled(false);
								}
							}
						});
					}
					sparentid.setValue(sparent);
					itype.setValue(sitype);
				}
			});
		}
	}
	changsparentid();
}
/**
 * 保存。
 */
function save(){
	if (!form.validate())return;
	if (!form1.validate())return;
	sumbit();
}
//提交数据
function sumbit(){
	var funarr = new Array();
    $.each($('input:checkbox:checked'),function(i){
        funarr[i] = $(this).val();
    });
	$.post(CTX + "/orgs/aso",
		{
		sid : sid.getValue(),
		sname : search.get("sname").getValue(),
		sdes : search.get("sdes").getValue(),
		sparentid : sparentid.getValue(),
		itype : itype.getValue(),
		username : search.get("username").getValue(),
		userphone : search.get("userphone").getValue(),
		areaid : area.getValue(),
		sfunctiongroupid : funarr.join(","),
		action : action
		},
		function(result) {
		if(result.status){
			search.info({content:result.result.isSuccess,funl:function(){
				if(result.result.state) window.closeWindow();
			}});
		}else search.error({content:"系统错误!"});
	})
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
		var type= Globle.constant.SpecialOrgType;
		var orgitype = new Array();
		if(orgtype[0] == Globle.constant.SpecialOrgType[2].value){
			itype.loadData(Globle.constant.SpecialOrgType);
		}else{
			for(var i = 0;i<orgtype.length;i++){
				for(var j = 0 ; j < type.length; j++){
					if(orgtype[i]==type[j].value){
						orgitype.push({text:type[j].text,value:type[j].value});
					}
				}
			}
			itype.loadData(orgitype);
		}
	}else{
		itype.loadData(Globle.constant.SpecialOrgType);
	}
}
//验证联系电话不能为空
function phoneValidate(e){
	var phone = search.get("userphone").getValue();
	var name = search.get("username").getValue();
	if(name!=""&&phone==""){
		e.message = "联系电话不能为空";
		e.pass = false;
	}
}
function itypechange(){
	if(itype.getValue()==Globle.constant.SpecialOrgType[2].value){
		$("#tab2").hide();
		$("#tab3").hide();
		$("#btnNext").hide();
	}else{
		$("#tab2").show();
		$("#tab3").show();
		$("#btnNext").show();
	}
	for(var i = 0;i<__orgtype.length;i++){
		if(__orgtype[i] == Globle.constant.OrgType[1].value){
			$("#tab2").hide();
			$("#tab3").hide();
			$("#btnNext").hide();
		}
	}
	if(action=="edit"){
		$("#tab2").hide();
		$("#tab3").hide();
		$("#btnNext").hide();
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

$(function(){
  count= 3; 
  loadFlow(count);
    
})
 
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
      flowVar += "<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;' >基本信息</strong>\n";
      flowVar += "</div>\n";
    }else if(i==2){
      flowVar += "<div id='tab"+i+"' data='" + i + "' class='flowList "+flowFor +"' style='position:relative'>\n";
      flowVar += "<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;' >用户信息</strong>\n";
      flowVar += "</div>\n";
    }else{
		flowVar += "<div id='tab"+i+"' data='" + i + "' class='flowList "+flowFor +"' style='position:relative'>\n";
		flowVar += "<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;' >用户授权</strong>\n";
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
		if(index == 3){
			$.ajax({
				url : CTX+"/orgs/gtabofg?areaid="+area.getValue(),
				success : function(result){
					if(result.result.map==null){
						search.warn({content:"暂无可授权功能组!"});
						search.remove("datagrid",true);
						$("#datagrid").empty().append($(""));
						search.parse();
						return;
					}
					result.result.map.id = "";
					list[0] = result.result.map;
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
				}
			});
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
			
	}

//上一步下一步按钮点击事件
function bindBottonEvent(){
		$("#btnBack").click(function(e) {
			var index = parseInt($(".for-cur").attr("data"));
            ButtonClick(index - 1);
        });
		$("#btnNext").click(function(e) {
			var index = parseInt($(".for-cur").attr("data"));
			if(index == 1){
				if (!form.validate())return;
			}
			if(index == 2){
				if (!form1.validate())return;
			}
            ButtonClick(index + 1);
        });
	}

function drawcell(e){
	for(var i = 0;i<headlist.length;i++){
		if(e.column.field==headlist[i].head){
			var html = "<input type='checkbox' id='funid' name='funid' value='"+headlist[i].head+"' />";
			e.html = html;
		}
	}
}