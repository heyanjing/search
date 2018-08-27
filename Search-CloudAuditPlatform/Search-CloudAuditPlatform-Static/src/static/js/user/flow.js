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
    		str = "上传身份证";
    	}else{
    		str = "上传资质";
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
//加载内容详情
function loadFlowDiv(index){ 

 if(index==1){$("#contA").removeClass("contentList");$("#contA").siblings().addClass("contentList")}
 if(index==2){
	 $("#contB").removeClass("contentList");$("#contB").siblings().addClass("contentList")
	 //上传身份证
	 upload1 = Upload.upload({
		 relativePath: '/search/user',
	     uploadFinished: function (datas) {
	    	 for(var i = 0; i<datas.length;i++){
	    		 identificationpath = datas[i].spath + ",";
	    		 identificationsNams = datas[i].sname + ",";
	    	 }
	    	 identificationpath = identificationpath.substring(0,identificationpath.length-1);
	    	 identificationsNams = identificationsNams.substring(0,identificationsNams.length-1);
	         
	         //资质上传
	         Upload2.upload();
	     }
	 })
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
    	var yzm = search.get("yzm").getValue();
    	var bool = false;
    	if(yzm == ""){
    		$("#code").attr('disabled',false);
    		top.search.info({ content : "请输入验证码", funl : function() {
    		} });
    		return;
    		
    	}else{
    		if(yzm != code){
	    			top.search.info({ content : "验证码错误", funl : function() {
					} });
	    			return;
	    		}
				
				var phone = search.get("sphone").getValue();
	    		if(sphone != phone){
	    			top.search.info({ content : "手机号码不相同", funl : function() {
	        		} });
	    			return;
	    		}
    		
    	}
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
			$("#btnok").show();
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
