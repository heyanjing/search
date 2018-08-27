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
      flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;'>审核</strong>\n";
      flowVar += "</div>\n";
    }else{
    	str = "授权";
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
//加载内容详情
function loadFlowDiv(index){ 

 if(index==1){$("#contA").removeClass("contentList");$("#contA").siblings().addClass("contentList")}
 if(index==2){
	 $("#contB").removeClass("contentList");$("#contB").siblings().addClass("contentList");
 }
// if(index==3){
//	 $("#contC").removeClass("contentList");$("#contC").siblings().addClass("contentList")
// }
// if(index==4){$("#contD").removeClass("contentList");$("#contD").siblings().addClass("contentList")}


}
//上一步下一步按钮点击事件
var maxstep=1;
function checkBtn(index, count) {
	/*下一步点击事件*/ 
    $("#btnBack").click(function () {
    	var orguser = organduserrefs.getValue();
    	if(orguser == ""){
    		top.search.info({ content : "请选择职务" });
    		return;
    	}
    	adopt();
    	
        methodBtn(index++, 'forward', false);
        state = true;
		if(index>maxstep){
			maxstep=index;
			}
        if (index >= count) {  
		/*到最后一步时 去掉下一步 显示上一步和完成*/
            $("#btnNext").hide();
            $("#btnBack").hide();
			$("#btnok").show();
        }
        onvalue();
    });
	/*上一步点击事件*/
    $("#btnNext").click(function () {
    	reject();
    });
}
/*上一步*/
function refreshBack(index) {
    return index;

}
