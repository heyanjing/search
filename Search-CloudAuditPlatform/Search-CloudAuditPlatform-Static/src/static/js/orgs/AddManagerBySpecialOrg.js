search.parse();
form1 = new Form("form1");
var grid = search.get("datagrid");
var list = new Array();
var headlist = new Array();
var check = new Array();
var userid = "";
var areaid = "";
var __orgid = "";
var igender = search.get("igender");
var ismanmager = search.get("ismanmager");
var sdictionariesid = search.get("sdictionariesid");
/**
 * 页面加载。
 */
function setData(data){
	$('#btn').on('click', function () {
        Upload.uploader.upload();
    });
	document.getElementById("alert").style.display = "block";
	search.get("sid").setValue(data.sid);
	userid = data.userid;
	areaid = data.areaid;
	ismanmager.loadData(Globle.constant.YesOrNo);
	igender.loadData(Globle.constant.sex);
	sdictionariesid.url = CTX + "/user/findListMapDictionariesByItype";
	sdictionariesid.load({ type : 3 });
	if(__usertype != Globle.constant.userTypes[0].id){
		$.ajax({
			url : CTX+"/orgs/gui",
			success : function(result){
				__orgid = result.result.orgid;
			}
		});
	}
	count= 2; 
	loadFlow(count);
	changismanmager();
}
/**
 * 保存。
 */
function save(){
	if (!form1.validate())return;
	sumbit();
}
//提交数据
function sumbit(){
	var funarr = new Array();
    $.each($('input:checkbox:checked'),function(i){
        funarr[i] = $(this).val();
    });
	$.post(CTX + "/orgs/asom",
		{
		sid : search.get("sid").getValue(),
		username : search.get("username").getValue(),
		userphone : search.get("userphone").getValue(),
		sfunctiongroupid : funarr.join(","),
		refid : userid,
		ismanmager : ismanmager.getValue()
		},
		function(result) {
		if(result.status){
			search.info({content:result.result.isSuccess,funl:function(){
				if(result.result.state) window.closeWindow();
			}});
		}else search.error({content:"系统错误!"});
	})
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
function changismanmager(){
	if(__usertype != Globle.constant.userTypes[0].id&&__orgid==search.get("sid").getValue()&&ismanmager.getValue()==Globle.constant.YesOrNo[0].value){
		ismanmager.setValue(Globle.constant.YesOrNo[1].value);
		search.warn({content:"不能给本机构添加管理员!"});
		return;
	}
	if(ismanmager.getValue()==Globle.constant.YesOrNo[1].value){
		$("#tab2").hide();
		$("#btnNext").hide();
	}else{
		$("#tab2").show();
		$("#btnNext").show();
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
		if(index == 2){
			$.ajax({
				url : CTX+"/orgs/gtabofg?areaid="+areaid,
				success : function(result){
					if(result.result.list!=null&&result.result.list.length!=0){
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
				if (!form1.validate())return;
			}
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