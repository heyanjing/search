search.parse();
form = new Form("form");
var grid = search.get("treegrid");
var list = new Array();
var sparentidlist = new Array();
var num = 1 ;
/**
 * 页面加载。
 */
function setData(data){
	document.getElementById("alert").style.display = "block";
	loadFlow(count);
	$.ajax({
		url : CTX+"/audittpls/qati",
		data : {sid : data.sid},
		success : function(result){
			var searchViews = $(".search-view");
			$.each(searchViews, function() {
				var $this = $(this);
				var key = $this.attr("id");
				var val = result.result[key];
				if (key == "itype") {
					$.each(Globle.constant.TplsType, function() {
						if (this.value == val) {
							val = this.text;
						}
					})
				}
				$this.html(val);
			})
			list = result.result.list;
			$.each(list, function(i,v) {
				if(v.itype == Globle.constant.TplDetailType[0].value){
					sparentidlist.push(v);
				}
			})
			grid.loadData(list);
		}
	});
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
			if (!form.validate())return;
			var index = parseInt($(".for-cur").attr("data"));
            ButtonClick(index + 1);
        });
	}
