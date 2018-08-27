search.parse();
var form = new Form("fileform");

var istate = "";

var countdown=180;

var itype = search.get("itype");

var itypeData = [
    {value : 101  , text:"资料送审"},
    {value : 102  , text:"审计实施方案"},
    {value : 103  , text:"其他"}
];
itype.loadData(itypeData);

$(function() {
	
	$("#btnok").on("click", function() {
		// console.log(upload1.delArr);// 获取删除附件的id
		istate = 1;
		if(upload1){
			upload1.upload();
		}else{
			save();
		}
	});
})

/**
 * 父级页面传值
 * 
 * @param data
 */
function setData(data) {
	 if (data.action == "edit") {
		 $.ajax({ url : CTX + "/filetpls/getFiletplsByid", data : { id : data.id },
			 success : function(result) {
				 if (result) {
					 form.setData(result.result);
					 var id = result.result.sid;
					 $.ajax({ url : CTX + "/filetpls/getAttachs",data : {id : id},
						 success : function(ach) {
							 for(var i =0;i<ach.result.length;i++){
									str1.push({sid:ach.result[i].sid,sname:ach.result[i].snameattach,spath:ach.result[i].spathattach});
								}
						 } });
				}else {
					top.search.error({ content : "系统错误!" });
				} 
			 }
		 });
			 
			 
		 }
}

// 保存操作
function save() {
	if (!form.validate()) {
		return;
	}
	if(itype.getValue() == 0){
		top.search.error({ content : "请选择类型!" });
		return;
	}
	
	// 参数拼接
	var str = "?ationpath=" + ationpath
			+ "&ationsNams=" + ationsNams
			+ "&del=" + upload1.delArr
	$.post(CTX + "/filetpls/save" + str, form.getData(), function(result) {// 保存
		if (result.status) {
			top.search.info({ content : result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else if( !result.status){
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
  count= 2;
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
			 relativePath: '/search/filetpls',
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
			 },
			 accept:{}
		 })
	 }
	 iRet = 1;
 }
// if(index==3){
//	 $("#contC").removeClass("contentList");$("#contC").siblings().addClass("contentList")
// }
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



