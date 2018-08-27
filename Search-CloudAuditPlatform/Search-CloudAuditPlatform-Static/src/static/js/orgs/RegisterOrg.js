search.parse();
form = new Form("form");
var itype = search.get("itype");
var sid = search.get("sid");
var sparentid = search.get("sparentid");
var sauditorgid = search.get("sauditorgid");
var iisdepartment = search.get("iisdepartment");
var upload1;
var flag2 = true;
var flag3 = true;
var sparenttype = new Array();
var no = Globle.constant.YesOrNo[1].value;
var sparentidlist = new Array();
/**
 * 页面加载。
 */
function setData(){
//	$('#btn').on('click', function () {
//        Upload.uploader.upload();
//    });
//	$("#org1").hide();
//	$("#org2").hide();
//	$("#user1").hide();
//	$("#user2").hide();
//	$("#phone1").hide();
//	$("#phone2").hide();
	$("#iisdepartment1").hide();
	$("#iisdepartment2").hide();
	document.getElementById("alert").style.display = "block";
	$.ajax({
		url : CTX+"/orgs/gorgs",
		success : function(result){
			sparenttype = result.result;
		}
	});
	itype.loadData(Globle.constant.RegisterOrgType);
	$.ajax({
		url : CTX+"/orgs/gporgs",
		success : function(result){
			sparentid.loadData(result.result);
			sparentidlist = result.result;
		}
	});
//		sparentid.setValue(data.sid);
	sauditorgid.url = CTX+"/orgs/grorgs";
	sauditorgid.load();
	iisdepartment.loadData(Globle.constant.YesOrNo);
}
//控制审计机构显示与隐藏
//function changsauditorgid(){
//	var type = itype.getValue();
//	if(type==2){
//		$("#org1").show();
//		$("#org2").show();
//	}else{
//		$("#org1").hide();
//		$("#org2").hide();
//	}
//}
//验证选择中介机构时审计机构不能为空
//function sauditorgidValidate(e){
//	var type = itype.getValue();
//	if(type==2&&sauditorgid.getValue()==""){
//		e.message = "中介机构必须选择审计机构";
//		e.pass = false;
//	}
//
//}
/**
 * 保存。
 */
function save(){
	if (!form.validate())return;
	upload1.upload();
}
/**
 * 提交方法。
 */
function sumbit(urls,delArr){
	var sname = new Array();
	var spath = new Array();
	var filesname = new Array();
	var filespath = new Array();
	var filesdesc = new Array();
	var filesdictionarieid = new Array();
	var filesid = new Array();
	var del = new Array();
	del.push(delArr);
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
	if(filedata!=null&&filedata.length>0){
		for(var i = 0;i<filedata.length;i++){
			sname.push(filedata[i].sname);
			spath.push(filedata[i].spath);
		}
	}
	$.post(CTX + "/orgs/uorg",
		{
		sid : sid.getValue(),
		sname : search.get("sname").getValue(),
		sdes : search.get("sdes").getValue(),
		sparentid : sparentid.getValue(),
		itype : itype.getValue(),
		urlsname : sname.join(","),
		urlspath : spath.join(","),
		filesname : filesname.join(","),
		filespath : filespath.join(","),
		filesdesc : filesdesc.join(","),
		filesdictionarieid : filesdictionarieid.join(","),
		filesid : filesid.join(","),
		del : upload1.delArr.join(","),
		delqua : del.join(","),
		register : "register",
		sauditorgid : sauditorgid.getValue(),
		username : search.get("username").getValue(),
		userphone : search.get("userphone").getValue(),
		iisdepartment : iisdepartment.getValue()
		},
		function(result) {
		if(result.status){
			search.info({content:result.result.isSuccess,funl:function(){
				if(!result.result.flag) {
//					window.closeWindow();
				}
				
			}});
		}else search.error({content:"系统错误!"});
	})
}
//父级机构查询匹配
function querysparentid(e){
	var splist = new Array();
	for(var i = 0;i<sparentidlist.length;i++){
		if(sparentidlist[i].sname.indexOf(e.text)!=-1){
			splist.push({"sname":sparentidlist[i].sname,"sid":sparentidlist[i].sid,"sparentid":sparentidlist[i].sparentid});
		}
	}
	sparentid.loadData(splist);
	sparentid.setText(e.text);
}
//父级机构判断是否清空输入
function parentid(e){
	var flag = true;
	for(var i = 0;i<sparentidlist.length;i++){
		if(sparentidlist[i].sname==e.text){
			flag = false;
			sparentid.setValue(sparentidlist[i].sid);
		}
	}
	if(flag){
		sparentid.loadData(sparentidlist);
	}
}
//父级机构值改变事件
function changsparentid(){
//	if(search.get("sparentid").getValue()!=""&&search.get("sparentid").getValue()!="-1"){
//		var str = "";
//		for(var i =0;i<sparenttype.length;i++){
//			if(search.get("sparentid").getValue()==sparenttype[i].sid){
//				str = sparenttype[i].sparenttype;
//			}
//		}
//		var orgtype = str.split(",");
//		var type= Globle.constant.RegisterOrgType;
//		var orgitype = new Array();
//		for(var i = 0;i<orgtype.length;i++){
//			for(var j = 0 ; j < type.length; j++){
//				if(orgtype[i]==type[j].value){
//					orgitype.push({text:type[j].text,value:type[j].value});
//				}
//			}
//		}
//		itype.loadData(orgitype);
//	}else{
//		itype.loadData(Globle.constant.RegisterOrgType);
//	}
}
//是否部门值变化触发事件
function changiisdepartment(){
	var iisdepartment = search.get("iisdepartment").getValue();
	if(iisdepartment==1){
		$("#user1").hide();
		$("#user2").hide();
		$("#phone1").hide();
		$("#phone2").hide();
	}else{
		$("#user1").show();
		$("#user2").show();
		$("#phone1").show();
		$("#phone2").show();
	}
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
//选择父级机构
function getOrgs(){
	var data = {
		url : CTX+"/orgs/ggo",
		title : "新增",
		width : 800,
		height : 550,
		onload : function(window){
			window.setData({action:"add"});
		},
		ondestroy : function(id){
			if(id!='cancel'){
				sparentid.setValue(id);
			}
			
		}
	}
	top.search.popDialog(data);
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
var colorList;
var count;

$(function(){
  count= 3; 
  loadFlow(count);
  checkColor(colorList);
  setData();
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
  for(var i=1;i<=count;i++){
    flowFor="for"+String.fromCharCode(i+64);
    if(i==1){
        flowVar += "<div class='flowList for-cur "+flowFor +"' style='position:relative'>\n";
        flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><br/><strong style='position:absolute;left:40%;margin-top:-15px;'>基本信息</strong>\n";
        flowVar += "</div>\n";
      }else if(i==2){
        flowVar += "<div class='flowList "+flowFor +"' style='position:relative'>\n";
        flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><br/><strong style='position:absolute;left:40%;margin-top:-15px;'>营业执照</strong>\n";
        flowVar += "</div>\n";
      }else{
  		flowVar += "<div class='flowList "+flowFor +"' style='position:relative'>\n";
  		flowVar += "	<em style='position:absolute;left:15%'>"+i+"</em><br/><strong style='position:absolute;left:40%;margin-top:-15px;'>机构资质</strong>\n";
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
 if(index==2){$("#contB").removeClass("contentList");$("#contB").siblings().addClass("contentList")}
 if(index==3){$("#contC").removeClass("contentList");$("#contC").siblings().addClass("contentList")}

}
//上一步下一步按钮点击事件
var maxstep=1;
function checkBtn(index, count) {
	
    $("#btnBack").addClass("disabled");
	 /*默认进来隐藏上一步按钮*/
	$("#btnBack").hide();	
	/*下一步点击事件*/ 
    $("#btnNext").click(function () {
    	if (!form.validate())return;
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
        if(index==2){
        	if(flag2){
    			upload1 = Upload.upload({
    				relativePath: Globle.constant.upload.org,
                    uploadFinished: function (datas) {
        	            filedata = datas;
        	            Upload2.upload();
        	        }
        	    });
    			flag2 = false;
    		}
        }
        if(index==3){
        	if(flag3){
        		Upload2.init({
        		    callback: function (resultArr,delArr) {
        		    	sumbit(resultArr,delArr);
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

