search.parse();
var form = new Form("fileform");

var tdg = search.get("fileAttgrid");
var idcardFile = {arr : [], index : 0}

var countdown=180;
var itype = search.get("itype");

var itypeData = [
	{value : 0 , text : "请选择"},
    {value : 101  , text:"资料送审"},
    {value : 102  , text:"审计实施方案"},
    {value : 103  , text:"其他"}
];
itype.loadData(itypeData);
$(function() {
	
	$("#btnok").on("click", function() {
		windowClose("ok");
	});
})
//var table =document.getElementById("tb1");
//var rows = table.rows.length;
var num = 1;

function datagriddrawcell(e) {
	if (e.column.field == 'action') {
		var attach = e.record.spathattach
		var str = attach.split(".");
		var strFilter="jpeg|gif|jpg|png|bmp|pic|";
        if(strFilter.indexOf(str[1])>-1)
        {
        	e.html = "<font><a class='edit' href=\"javascript:void(0)\" onclick=\"preview(\'" + e.record.snameattach + "\',\'" + e.record.spathattach + "\')\">图片预览</a></font>";
        }else{
        	e.html = "<font><a class='edit' href=\"javascript:void(0)\" onclick=\"preview(\'" + e.record.snameattach + "\',\'" + e.record.spathattach + "\')\">文件预览</a></font>";
        }
	}
}

function preview(snameattach,spathattach){
	idcardFile.arr.push({sname : snameattach, spath : spathattach});
	Globle.fun.preview(idcardFile);
}

/**
 * 父级页面传值
 * 
 * @param data
 */
function setData(data) {
	 $.ajax({ url : CTX + "/filetpls/getFiletplsByid", data : { id : data.id },
		 success : function(result) {
			 if (result) {
				 form.setData(result.result);
				 var id = result.result.sid;
				 tdg.url = CTX + "/filetpls/getAttachs";
				 tdg.load({id : id});
			}else {
				top.search.error({ content : "系统错误!" });
			} 
		 }
	 });
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
      flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><strong id='jb' style='position:absolute;left:40%;'>基本信息</strong>\n";
      flowVar += "</div>\n";
    }else{
    	if(i == 2){
    		str = "上传附件";
    	}
      flowVar += "<div class='flowList "+flowFor +"' style='position:relative'>\n";
      flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><strong id='file' style='position:absolute;left:40%;'>"+str+"</strong>\n";
      flowVar += "</div>\n";
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
	
//    $("#btnBack").addClass("disabled");
	 /*默认进来隐藏上一步按钮*/
//	$("#btnBack").hide();	
	/*下一步点击事件*/ 
    $("#file").click(function () {	
        methodBtn(index++, 'forward', false);
		if(index>maxstep){
			maxstep=index;
			 
			}
        if (index != 1) {
			/*非第一步的时候，显示上一步*/
//            $("#btnBack").removeClass("disabled");
//			$("#btnBack").show();
        }
        if (index >= count) {  
		/*到最后一步时 去掉下一步 显示上一步和完成*/
//            $("#btnNext").hide();
//            $("#btnBack").show();
        }
        refreshBack(index);
    });
	/*上一步点击事件*/
    $("#jb").click(function () {
        if (refreshBack(index) > 1) {
            methodBtn(index--, 'back', false);
//			 $("#btnNext").show();
//            if (index == 1) {
//				/*如果当前为第一步 则给上一步添加disabled属性*/
//                $("#btnBack").addClass("disabled");
//            } 
			
        }
    });
}
/*上一步*/
function refreshBack(index) {
    return index;

}

