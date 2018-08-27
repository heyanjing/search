search.parse();
form = new Form("form");
var grid = search.get("treegrid");
var itype = search.get("itype");
var sdesc = search.get("sdesc");
var sid = search.get("sid");
var sorgid = search.get("sorgid");
var ishoworder = search.get("ishoworder");
var sname = search.get("sname");
var list = new Array();
var sparentidlist = new Array();
var num = 1 ;
/**
 * 页面加载。
 */
function setData(data){
	document.getElementById("alert").style.display = "block";
	//加载所属机构
	sorgid.url = CTX+"/orgs/grorgs";
	sorgid.load();
	//加载模版类型
	itype.loadData(Globle.constant.TplsType);
	//不为admin用户时，隐藏所属机构选项
	if(__usertype != Globle.constant.userTypes[0].id){
		$("#sorgid1").hide();
		$("#sorgid2").hide();
	}
	//加载步骤
	loadFlow(count);
	//编辑按钮进入后加载项
	if(data.action == "edit"){
		//获取送审模版对象数据
		$.ajax({
			url : CTX+"/audittpls/qati",
			data : {sid : data.sid},
			success : function(result){
				//储存模版详情对象list
				list = result.result.list;
				//储存模板详情父级对象list
				$.each(list, function(i,v) {
					if(v.itype == Globle.constant.TplDetailType[0].value){
						sparentidlist.push(v);
					}
				})
				//编辑页面赋值
				form.setData(result.result);
				//加载treegrid
				grid.loadData(list);
			}
		});
	}
}
//格式化表格内容
function ondrawcell(e) {
	// 是否必填
	if (e.column.field == "imust" && e.record[e.column.field] != "") {
		$.each(Globle.constant.YesOrNo, function() {
			if (this.value == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 资料类型
	if (e.column.field == "itype" && e.record[e.column.field] != "") {
		$.each(Globle.constant.TplDetailType, function() {
			if (this.value == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 操作
	if (e.column.field == "oper") {
		e.html = '';
		if(e.record.sid.length > 10){
			e.html += ' <a class="edit" href="javascript:void(0);" onclick="edit(\'' + e.record.sid + '\');">编辑</a>';
		}
		e.html += '<a class="del" href="javascript:void(0);" onclick="delData(\'' + e.record.sid + '\');">删除</a>';
	}
}
//新增
function add() {
	//弹出模版详情新增页面
	top.search.popDialog({ url : CTX + "/audittpls/guatd", title : "新增模版详情", width : 800, height : 550,
		onload : function(window) {
			window.setData({ action : "add", sparentidlist : sparentidlist, num : num });
		}, ondestroy : function(result) {
			//新增保存成功后进入
			if(result!=null&&result!="cancel"){
				num++;
				//将数据保存到模版详情list
				list.push(result);
				//重新加载treegrid
				grid.loadData(list);
				//当新增数据为资料分类时，将数据保存到模版父级详情list
				if(result.itype == Globle.constant.TplDetailType[0].value){
					sparentidlist.push(result);
				}
			}
			
		} });
}

// 编辑
function edit(sid) {
	//判断是否拥有子级
	var flag = true;
	$.each(list, function(i,v) {
		if(v.sparentid == sid){
			flag = false;
		}
	})
	//弹出模版详情编辑页面
	top.search.popDialog({ url : CTX + "/audittpls/guatd", title : "编辑模版详情", width : 800, height : 550,
		onload : function(window) {
			window.setData({ action : "edit", sid : sid, sparentidlist : sparentidlist, flag : flag });
		}, ondestroy : function(result) {
			//编辑保存成功后进入
			if(result!=null&&result!="cancel"){
				var newlist = new Array();
				var newsparentidlist = new Array();
				//复制另一个list
				$.each(list, function(i,v) {
					newlist.push(v);
				})
				//复制另一个父级list
				$.each(sparentidlist, function(i,v) {
					newsparentidlist.push(v);
				})
				$.each(newlist, function(i,v) {
					if (v.sid == result.sid) {
						if(v.itype!=result.itype&&result.itype==Globle.constant.TplDetailType[1].value){
							$.each(newsparentidlist, function(j,k) {
								//删除编辑前父级数据
								if(k.sid == result.sid){
									sparentidlist.splice(j,1);
								}
							})
						}
						//删除编辑前数据
						list.splice(i,1);
					}
				})
				//重新添加编辑后数据
				list.push(result);
				//重新加载treegrid
				grid.loadData(list);
				//刷新
				grid.reload();
				//重新添加编辑后父级数据
				if(result.itype == Globle.constant.TplDetailType[0].value){
					sparentidlist.push(result);
				}
			}
		} })
}

// 删除
function delData(sid) {
	//删除确认
	top.search.confirm({ content : "确定要删除？", funl : function() {
		var dellist = new Array();
		var delsparentidlist = new Array();
		//将要删除的数据添加进删除list
		$.each(list, function(i,v) {
			if (v.sid == sid) {
				dellist.push(v);
			}
			if (v.sparentid == sid) {
				dellist.push(v);
			}
		})
		//将要删除的数据添加近删除父级list
		$.each(sparentidlist, function(i,v) {
			if (v.sid == sid) {
				delsparentidlist.push(v);
			}
		})
		//循环删除对应数据
		$.each(dellist, function(i,v) {
			$.each(list, function(j,k) {
				if(v.sid == k.sid){
					list.splice(j,1);
					return false;
				}
			})
		})
		//循环删除对应父级数据
		$.each(delsparentidlist, function(i,v) {
			$.each(sparentidlist, function(j,k) {
				if(v.sid == k.sid){
					sparentidlist.splice(j,1);
					return false;
				}
			})
		})
		//重新加载treegrid
		grid.loadData(list);
	}})
}
/**
 * 保存。
 */
function save(){
	//表单验证
	if (!form.validate())return;
	//验证模版详情不能为空
	if (list.length==0){
		top.search.warn({ content : "请新增模版详情！" });
		return;
	}
	//提交
	sumbit();
}
//提交数据
function sumbit(){
	var snamestr = new Array();
	var sidstr = new Array();
	var itypestr = new Array();
	var imuststr = new Array();
	var sfiletplidstr = new Array();
	var sparentidstr = new Array();
	//替换null为0并将数据封装为多个list
	$.each(list, function(i,v) {
		if(v.sid==null||v.sid==""){
			sidstr.push("0");
		}else{
			sidstr.push(v.sid);
		}
		if(v.imust==null||v.imust==""){
			imuststr.push("0");
		}else{
			imuststr.push(v.imust);
		}
		if(v.sfiletplid==null||v.sfiletplid==""){
			sfiletplidstr.push("0");
		}else{
			sfiletplidstr.push(v.sfiletplid);
		}
		if(v.sparentid==null||v.sparentid==""){
			sparentidstr.push("0");
		}else{
			sparentidstr.push(v.sparentid);
		}
		snamestr.push(v.sname);
		itypestr.push(v.itype);
	})
	//提交数据到后台
	$.post(CTX + "/audittpls/uat",
		{
		sid : sid.getValue(),
		sname : sname.getValue(),
		sdesc : sdesc.getValue(),
		ishoworder : ishoworder.getValue(),
		itype : itype.getValue(),
		sorgid : sorgid.getValue(),
		sidstr : sidstr.join(","),
		snamestr : snamestr.join(","),
		itypestr : itypestr.join(","),
		imuststr : imuststr.join(","),
		sfiletplidstr : sfiletplidstr.join(","),
		sparentidstr : sparentidstr.join(",")
		},
		function(result) {
		if(result.status){
			//操作成功后关闭页面
			search.info({content:result.result.isSuccess,funl:function(){
				if(result.result.state) window.closeWindow();
			}});
		}else search.error({content:"系统错误!"});
	})
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
var count = 2;
 
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
      flowVar += "<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;' onclick='ButtonClick("+i+");'>标准模版</strong>\n";
      flowVar += "</div>\n";
    }else{
    	flowVar += "<div id='tab"+i+"' data='" + i + "' class='flowList "+flowFor +"' style='position:relative'>\n";
		flowVar += "<em style='position:absolute;left:15%'>"+i+"</em><strong style='position:absolute;left:40%;' onclick='ButtonClick("+i+");'>模版详情</strong>\n";
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
		
		}else if(index == count){
			
			$("#btnBack").show();
			$("#btnNext").hide();
			$("#btnok").show();
			
		}else{
				
			$("#btnBack").show();
			$("#btnNext").show();
			$("#btnok").show();
				
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
			//下一步表单验证
			if (!form.validate())return;
			var index = parseInt($(".for-cur").attr("data"));
            ButtonClick(index + 1);
        });
	}
