var steps = null, lines = null, method = null, invoker = null, stepUrl = null, stepWidth = null, stepHeight = null , dataOrigin = null, customParams = null;
var canvas = $(".flow-canvas"),menu = $(".flow-canvas-menu-container"), stepsMenu = $(".step-menu-container"),formPermissionsMenu = $(".form-permissions-menu");
var insertLines = new Array(), delSteps = new Array(), delLines = new Array();
var startStepType = 101, normalStepType = 102, moreStepType = 103, endStepType = 104; 
var canvasMenuHeight = 150, stepMenuHeight = 80, formPermissionsMenuHeight = 25;
var stepDefaultColor = "#878787",stepDesignColor = "#008000",
lineDefaultColor = "rgb(135,135,135)", lineFillDefaultColor = "#878787",lineDesignColor = "rgb(255,255,255)",lineFillDesignColor = "#ffffff";
var menuCanvas = 1, menuStepsDesign = 2, menuStepsFormPermissions = 3;
var showStepsDesignMenu = 1, noShowStepsMenu = 2, showStepsFormPermissions = 3 ;

//绘制方法
function draw(bindEvent){
	if (!steps) steps = {};
	if (!lines) lines = {};
	
	//加载已存在的步骤
	var hasStart = false, hasEnd = false;
	for (var key in steps){
		var step = steps[key];
		if (step.type == startStepType) hasStart = true;
		else if (step.type == endStepType) hasEnd = true;
		
		drawStep(step,stepDesignColor,false,showStepsDesignMenu);
	}
	
	//加载已存在的步骤链接
	for (var key in lines){
		var line = lines[key];
		drawLines(line,lineDesignColor,lineFillDesignColor);
	}
	
	if (bindEvent){
		//绑定菜单点击事件
		$("#title-save").bind("click",saveDesign);
		$("#title-refresh").bind("click",refresh);
		$("#menu-save").bind("click",saveDesign);
		$("#menu-refresh").bind("click",refresh);
		var startMenu = $("#menu-start"), endMenu = $("#menu-end");
		if (!hasStart) startMenu.bind("click",function(e){
			addStep(startStepType,"开始");
		});
		else disableMenu(startMenu, false); 
		if (!hasEnd) endMenu.bind("click",function(e){
			addStep(endStepType,"结束");
		});
		else disableMenu(endMenu,false);
		$("#menu-normal").bind("click",function(e){
			addStep(normalStepType,"普通步骤");
		});
		$("#menu-more").bind("click",function(e){
			addStep(moreStepType,"会签步骤");
		});
		
		$("#step-cog").click(function(){
			openBox(1);
			closeMenu(stepsMenu);
		});
		$("#step-share").click(function(e){
			var cls = $(e.target).attr("class");
			if(cls && cls.containsClass("fa-share-alt")){
				return;
			}
			openBox(2);
			closeMenu(stepsMenu);
		});
		$("#step-trash").click(function(e){
			search.confirm({title:"提示信息",content:"确认删除？",funl:function(){
				var selectStep = $("div[selected='selected']");
				var selectStepId = selectStep.attr("id"), selectStepData = selectStep.attr("data");
				
				selectStep.remove();
				delete steps[selectStepId];
				if (selectStepData) delSteps.push(selectStepData);
				
				var starts = $("line[sid='" + selectStepId + "']");
				var ends = $("line[eid='" + selectStepId + "']");
				for (var i = 0; i < starts.length; i++){
					var start = $(starts[i]).parent();
					deleteLine(start);
				}
				for (var i = 0; i < ends.length; i++){
					var end = $(ends[i]).parent();
					deleteLine(end);
				}
				
			}});
			closeMenu(stepsMenu);
		});
	}
}

function deleteLine(selectLine){
	var selectLineId = selectLine.attr("id"), selectLineData = selectLine.attr("data");
	
	selectLine.remove();
	insertLines.remove(selectLineId);
	delete lines[selectLineId];
	if (selectLineData) delLines.push(selectLineData);
}

//表单权限
function loadDataFormPermissions(data,edit,custom){
	steps = data.steps;
	lines = data.lines;
	stepUrl = edit.url;
	stepWidth = edit.width;
	stepHeight = edit.Height;
	
	customParams = custom;
	
	//隐藏按钮
	$("#title-save").hide();
	$("#title-refresh").hide();
	$("#step-color-note").hide();
	
	//绑定菜单按钮
	$("#form-permissions").click(function(){
		openFormPermissions();
		closeMenu(formPermissionsMenu);
	});
	
	//加载已存在的步骤
	for (var key in steps){
		var step = steps[key];
		drawStep(step,stepDesignColor,false,showStepsFormPermissions);
	}
	
	//加载已存在的步骤链接
	for (var key in lines){
		var line = lines[key];
		drawLines(line,lineDesignColor,lineFillDesignColor);
	}
	
	//绑定鼠标按下事件
	canvas.mousedown(function(e){
		closeMenu(formPermissionsMenu);
	});
}

//表单权限弹框
function openFormPermissions(){
	var selectStep = $("div[selected='selected']");
	var stepId = selectStep.attr("id");
	var stepObj = steps[stepId];

	search.popDialog({
		id : "FormPermissions",
		title : "表单权限",
		url : stepUrl,
		width : stepWidth,
		height : stepHeight,
		modal : true,
		onload : function(window){
			window.initPage(stepObj.data,customParams);
		}
	});
}

//标签菜单弹框
function openBox(menuIndex){
	var selectStep = $("div[selected='selected']");
	var stepId = selectStep.attr("id");
	var stepObj = steps[stepId];
	
	search.popDialog({
		id : "StepProperty",
		title : "步骤属性",
		url : stepUrl,
		width : stepWidth,
		height : stepHeight,
		modal : true,
		onload : function(window){
			window.initPage(menuIndex, stepObj.data, stepObj.name,stepObj.type,stepObj.extend,customParams);
		},
		ondestroy : function(result){
			if (result.action === "save") {
				stepObj.name = result.stepName;
				stepObj.extend = result.stepExtend;
				selectStep.find("span[property='name']").text(result.stepName);
			}
		}
	});
}

//加载流程状态数据
function loadDataDesign(data,changColorData){
	steps = data.steps;
	lines = data.lines;
	
	//隐藏按钮
	$("#title-save").hide();
	$("#title-refresh").hide();
	
	//加载已存在的步骤
	for(var key in steps){
		var step = steps[key];
		
		var stepColor = null;
		
		if(step.data == changColorData.handling) stepColor = "#00739d";
		else if (step.data == changColorData.exception) stepColor = "#FF0000";
		
		if (!stepColor) {
			var finalsSteps = changColorData.finals.steps;
			if (finalsSteps.indexOf(step.data) != -1) stepColor = "#008000";
		}
		
		if (!stepColor) {
			var invalidsSteps = changColorData.invalids.steps;
			if (invalidsSteps.indexOf(step.data) != -1) stepColor ="#b86c00";
		}
		
		if (!stepColor) {
			var backwardsSteps = changColorData.backwards.steps;
			if (backwardsSteps.indexOf(step.data) != -1) stepColor="#496b00";
		}
		
		if (!stepColor) stepColor = stepDefaultColor;
		drawStep(step,stepColor,false,noShowStepsMenu);
	}
	
	//加载已存在的连接
	for(var key in lines){
		var line = lines[key];
		
		var lineStartId = line.start.id;
		var lineEndId = line.end.id;
		var lineStartStepsId = steps[lineStartId].data;
		var lineEndStepsId = steps[lineEndId].data;
		
		var lineColor = null;
		var lineFillColor = null;
		
		if (!lineColor) {
			var finalsLines = changColorData.finals.lines;
			for (var i = 0; i < finalsLines.length; i ++) {
				var finalsLine = finalsLines[i];
				if (finalsLine.start == lineStartStepsId && finalsLine.end == lineEndStepsId) {
					lineColor = "rgb(0,128,0)";
					lineFillColor = "#008000";
					
					break;
				}
			}
		}
		
		if (!lineColor) {
			var invalidsLines = changColorData.invalids.lines;
			for (var i = 0; i < invalidsLines.length; i ++){
				var invalidsLine = invalidsLines[i];
				if (invalidsLine.start == lineStartStepsId && invalidsLine.end == lineEndStepsId) {
					lineColor = "rgb(184,108,0)";
					lineFillColor = "#b86c00";
					
					break;
				}
			}
		}
		
		if (!lineColor) {
			var backwardsLines = changColorData.backwards.lines;
			for (var i = 0; i < backwardsLines.length; i ++){
				var backwardsLine = backwardsLines[i];
				if (backwardsLine.start == lineStartStepsId && backwardsLine.end == lineEndStepsId) {
					lineColor = "rgb(73,107,0)";
					lineFillColor = "#496b00";
					
					break;
				}
			}
		}
		
		if (!lineColor) lineColor = lineDefaultColor;
		if (!lineFillColor) lineFillColor = lineFillDefaultColor;
		drawLines(line,lineColor,lineFillColor);
	}
}

function loadData(data,save,edit,custom) {
	steps = data.steps;
	lines = data.lines;
	method = save.method;
	invoker = save.invoker;
	stepUrl = edit.url;
	stepWidth = edit.width;
	stepHeight = edit.Height;
	
	dataOrigin = {};
	$.extend(true,dataOrigin,data);
	
	//隐藏颜色注释
	$("#step-color-note").hide();
	
	customParams = custom;
	
	//绑定鼠标按下事件
	canvas.mousedown(function(e){
		closeMenu(menu);
		closeMenu(stepsMenu);
		if(e.which == 3){
			showMenu(e.clientY, e.clientX, menu,canvasMenuHeight,null,menuCanvas);
			return false;
		}
	});
	
	draw(true);
}

//右键菜单
function showMenu(top,left,menuObj,menuHeight,type,FormPermissions){
	var winWidth = $(window).width();
	var winHeight = $(window).height() - $(".flow-title").outerHeight();
	var menuWidth = menuObj.outerWidth();
	
	if (left + menuWidth > winWidth) left = winWidth - menuWidth;
	if (top + menuHeight > winHeight) top = winHeight - menuHeight;
	
	//标签上的右键菜单
	if (FormPermissions == menuCanvas){
		menuObj.css("left", left);
		menuObj.css("top", top);
		menuObj.animate({height : menuHeight});
		
	}else if (FormPermissions == menuStepsDesign){
		if (type){
			menuObj.css("left", left);
			menuObj.css("top",top);
			menuObj.animate({height : menuHeight});
			
			var share = $("#step-share"), sharei = share.find("i");
			if ( type == 101 || type == 104){
				share.addClass("step-table");
				sharei.addClass("step-table-i");
			}else{
				share.removeClass("step-table");
				sharei.removeClass("step-table-i");
			}
		}
	}else if(FormPermissions == menuStepsFormPermissions){
		menuObj.css("left", left);
		menuObj.css("top", top);
		menuObj.animate({height : menuHeight});
	}
}

function closeMenu(menuObj){
	menuObj.css("height", "0");
}

//绘制标签
function drawStep(data,stepcolor,newstep,menutype){
	var divcls = "", icls = "";
	
	if (data.type == startStepType) {
		
		divcls = "flow-start-prismatic btn";
		icls = "fa fa-star";
		
	} else if (data.type == normalStepType) {
		
		divcls = "flow-plain-prismatic btn";
		icls = "fa fa-plus";
		
	} else if (data.type == moreStepType) {
		
		divcls = "flow-sgin-prismatic btn";
		icls = "fa fa-retweet";
		
	} else if (data.type ==endStepType) {
		
		divcls = "flow-end-prismatic btn";
		icls = "fa fa-dot-circle-o";
	}
	
	var id = data.id, dbid = data.data;
	if (!id) id = "step_" + new Date().getTime();
	if (!dbid) dbid = "";
	
	var stephtml = $("<div data = '" + dbid + "' id = '" + id + "' type = '" + data.type + "' class='" + divcls + "' style = 'top : " + data.pos.top + "px; left : " + data.pos.left + "px; background:"+ stepcolor + ";'>" +
					"<span><i class='" + icls + "'></i><span property = 'name'>" + data.name + "</span></span>" +
					"</div>");
	canvas.append(stephtml);
	
	bindDrawEvent(stephtml);
	bindDrawLineEvent(stephtml.find("i"));
	
	//绑定鼠标按下事件
	stephtml.mousedown(function(e){
		closeMenu(menu);
		closeMenu(stepsMenu);
		if (e.which == 3){
			stephtml.attr("selected", true);
			stephtml.siblings().removeAttr("selected"); 
			if (menutype == showStepsDesignMenu ) showMenu(e.clientY, e.clientX, stepsMenu,stepMenuHeight, data.type,menuStepsDesign);
			else if (menutype == showStepsFormPermissions && data.type != startStepType && data.type != endStepType) showMenu(e.clientY, e.clientX, formPermissionsMenu,formPermissionsMenuHeight, null,menuStepsFormPermissions);
			return false;
		}
	})
	
	//标签画过后的缓存
	if (newstep){
		var step = {id : id, type : data.type, pos : {top : data.pos.top, left : data.pos.left}, name : data.name, data : dbid, extend : {}};
		steps[id] = step;
	}
}

//解绑画布标签
function disableMenu(menuObj,boundEvent){
	if (boundEvent) menuObj.unbind("click");
	menuObj.css({background:"none", cursor:"default", color:"#ccc"});
	menuObj.find("i").css("color","#ccc");
}

//步骤
function addStep(type,name){
	closeMenu(menu);
	
	var pos = menu.position();
	var data = {type : type, pos : {top : pos.top - 30, left : pos.left}, name : name};
	
	drawStep(data,stepDesignColor, true,showStepsDesignMenu);
	
	if (type == startStepType) disableMenu($("#menu-start"), true);
	if (type == endStepType) disableMenu($("#menu-end"), true);
}

//保存设计
function saveDesign(){
	closeMenu(menu);
	
	var changed = {delSteps : delSteps, insertLines : insertLines, delLines : delLines};
	method.call(invoker, {steps : steps, lines : lines}, changed, window, saveCallback, customParams);
}

//刷新
function refresh(){
	closeMenu(menu);
	canvas.empty();
	
	steps = {}, lines = {};
	$.extend(true, steps, dataOrigin.steps);
	$.extend(true, lines, dataOrigin.lines);
	
	draw(false);
	insertLines = new Array(), delSteps = new Array(), delLines = new Array();
}

function saveCallback(result){
	if(result){
		dataOrigin = {};
		$.extend(true,dataOrigin,result);
		
		steps = {}, lines = {};
		$.extend(true, steps, dataOrigin.steps);
		$.extend(true, lines, dataOrigin.lines);
	}
}

//面板拖拽按扭
function bindDrawEvent(step){
	var doc = $(document);
	var width = step.outerWidth();
	var height = step.outerHeight();
	
	step.bind({
		mousedown : function(e){
			if (e.which == 3) return false;
			
			var os = $(this).offset();
			var dx = e.pageX - os.left, dy = e.pageY - os.top;
			
			doc.on("mousemove.drag", function(e){
				var before = step.position();
				step.offset({left : e.pageX - dx, top : e.pageY - dy});
				
				var pos = step.position();
				
				if (pos.left < 0) step.css("left", 0);
				else if (pos.left > canvas.width() - width) step.css("left", canvas.width() - width);
				
				if (pos. top < 0) step.css("top", 0);
				else if (pos.top > canvas.height() - height) step.css("top", canvas.height() - height);
				
				var id = step.attr("id");
				var after = step.position();
				var ldx = after.left - before.left, ldy = after.top - before.top;
				
				steps[id].pos.top = after.top;
				steps[id].pos.left = after.left;
				
				//绘制标签拖动线随着拖动
				var starts = $("line[sid='" + id + "']");
				for (var i = 0; i < starts.length; i++){
					var start = $(starts[i]);
					var lid = start.parent().attr("id");
					var line = lines[lid];
					var estep = $("#" + start.attr("eid"));
					var ewidth = estep.outerWidth(), eheight = estep.outerHeight(), eafter = estep.position();
					var p1 = {x : after.left + width / 2, y : after.top + height / 2};
					var p2 = {x : eafter.left + ewidth / 2, y : eafter.top + eheight / 2};
					
					var lineStart = {}, lineEnd = {};
					var pdx = p1.x - p2.x, pdy = p1.y - p2.y;
					
					if (Math.abs(pdx) >= Math.abs(pdy)){
						
						//计算起点坐标
						if (pdx > 0) lineStart.x = after.left;  //左边
						else lineStart.x = after.left + width;  //右边
						lineStart.y = after.top + height / 2;
						
						//计算终点坐标
						if (pdx > 0) lineEnd.x = eafter.left + ewidth;   //右边
						else lineEnd.x = eafter.left;                    //左边
						lineEnd.y = eafter.top + eheight / 2;
						
					}else{
						//计算起点坐标
						lineStart.x = after.left + width / 2;
						if (pdy > 0) lineStart.y = after.top;         //上边
						else lineStart.y = after.top + height;        //下边
						
						//计算终点坐标
						lineEnd.x = eafter.left + ewidth / 2;
						if (pdy > 0) lineEnd.y = eafter.top + eheight;  //下边
						else lineEnd.y = eafter.top;                    //上边
					}
					
					//计算实际终点坐标
					var d = Math.sqrt(Math.pow((lineEnd.x - lineStart.x), 2) + Math.pow((lineEnd.y - lineStart.y), 2));
					pdx = (lineEnd.x - lineStart.x) * 11 / d, pdy = (lineEnd.y - lineStart.y) * 11 / d;
					lineEnd.x -= pdx, lineEnd.y -= pdy;
					
					line.start.x = lineStart.x;
					line.start.y = lineStart.y;
					line.end.x = lineEnd.x;
					line.end.y = lineEnd.y;
					start.attr("x1",lineStart.x);
					start.attr("y1",lineStart.y);
					start.attr("x2",lineEnd.x);
					start.attr("y2",lineEnd.y);
				}
				
				var ends = $("line[eid = '" + id + "']"); 
				for (var i = 0; i < ends.length; i++){
					var end = $(ends[i]);
					var lid = end.parent().attr("id");
					var line = lines[lid];
					var sstep = $("#" + end.attr("sid"));
					var swidth = sstep.outerWidth(), sheight = sstep.outerHeight(), safter = sstep.position();
					var p1 = {x : safter.left + swidth / 2, y : safter.top + sheight / 2};
					var p2 = {x : after.left + width / 2, y : after.top + height / 2};
					
					var lineStart = {}, lineEnd = {};
					var pdx = p1.x - p2.x, pdy = p1.y - p2.y;
					
					if (Math.abs(pdx) >= Math.abs(pdy)){
						
						//计算起点坐标
						if (pdx > 0) lineStart.x = safter.left;   //右边
						else lineStart.x = safter.left + swidth;  //左边
						lineStart.y = safter.top + sheight / 2;
						
						//计算终点坐标
						if (pdx > 0) lineEnd.x = after.left + width;    //左边
						else lineEnd.x = after.left;                    //右边
						lineEnd.y = after.top + height / 2;
						
					}else{
						//计算起点坐标
						lineStart.x = safter.left + swidth / 2;
						if (pdy > 0) lineStart.y = safter.top;          //下边
						else lineStart.y = safter.top + sheight;        //上边
						
						//计算终点坐标
						lineEnd.x = after.left + width / 2;
						if (pdy > 0) lineEnd.y = after.top + height;   //上边
						else lineEnd.y = after.top;                    //下边
					}
					
					//计算实际终点坐标
					var d = Math.sqrt(Math.pow((lineEnd.x - lineStart.x), 2) + Math.pow((lineEnd.y - lineStart.y), 2));
					pdx = (lineEnd.x - lineStart.x) * 11 / d, pdy = (lineEnd.y - lineStart.y) * 11 / d;
					lineEnd.x -= pdx, lineEnd.y -= pdy;
					
					line.start.x = lineStart.x;
					line.start.y = lineStart.y;
					line.end.x = lineEnd.x;
					line.end.y = lineEnd.y;
					end.attr("x1",lineStart.x);
					end.attr("y1",lineStart.y);
					end.attr("x2",lineEnd.x);
					end.attr("y2",lineEnd.y);
				}
				
			});
		},
		mouseup : function(e){
			if (e.which == 3) return false;
			
			doc.off("mousemove.drag");
		}
	});
}

//线条
var start = null, end = null, startType = null;

function bindDrawLineEvent(i){
	i.bind({
		mousedown : function(e){
			var step = i.parent().parent();
			var pos = step.position(), width = step.outerWidth(), height = step.outerHeight();
			startType = step.attr("type");
			
			//记录鼠标按下流程节点相关属性
			start = { c : { x : pos.left + width / 2, y : pos.top + height / 2}, p : { x : pos.left, y : pos.top}, s : { w : width, h : height}, id : step.attr("id")};
			return false;
		},
		mouseup :function(e){
			var step = i.parent().parent();
			var pos = step.position(), width = step.outerWidth(), height = step.outerHeight();
			
			//判断结束
			var endType = step.attr("type");
			if ( startType == startStepType && ( endType == moreStepType || endType == endStepType)) return false;
			if ( startType == normalStepType && endType == startStepType) return false;
			if ( startType == moreStepType && ( endType == startStepType || endType == endStepType)) return false;
			if ( startType == endStepType) return false;
			
			//记录鼠标放开流程节点相关属性
			end = { c : { x : pos.left + width / 2, y : pos.top + height / 2}, p : { x : pos.left, y : pos.top}, s : { w : width, h : height}, id : step.attr("id")};
			
			drawLine();
			return false;
		}
		
	});
}

//计算线的起点与终点
function drawLine(){
	if (!start || !end || (start.c.x == end.c.x && start.c.y == end.c.y)) return;    //无起点或终点或起点与终点相同
	
	var lineStart = {}, lineEnd = {};
	var dx = start.c.x - end.c.x, dy = start.c.y - end.c.y;
	if (Math.abs(dx) >= Math.abs(dy)){          //起点和终点位于流程节点左右两边
		//计算起点坐标
		if (dx > 0) lineStart.x = start.p.x;  //左边
		else lineStart.x = start.p.x + start.s.w;  //右边
		lineStart.y = start.p.y + start.s.h /2;
		
		//计算终点坐标
		if (dx > 0) lineEnd.x = end.p.x + end.s.w;  //右边
		else lineEnd.x = end.p.x;    //左边
		lineEnd.y = end.p.y + end.s.h / 2;
	} else {
		//计算起点坐标
		lineStart.x = start.p.x + start.s.w / 2;   
		if (dy > 0) lineStart.y = start.p.y;    //上边
		else lineStart.y = start.p.y + start.s.h  //下边
		
		//计算终点坐标
		lineEnd.x = end.p.x + end.s.w / 2;
		if (dy > 0) lineEnd.y = end.p.y + end.s.h;  //下边
		else lineEnd.y = end.p.y;   //上边
	}
	
	//计算实际终点坐标
	var d = Math.sqrt(Math.pow((lineEnd.x - lineStart.x),2) + Math.pow((lineEnd.y - lineStart.y),2));
	dx = (lineEnd.x - lineStart.x) * 11 / d, dy = (lineEnd.y - lineStart.y) * 11 / d;
	lineEnd.x -= dx, lineEnd.y -= dy;
	
	//绘制箭头
	var data = { start : { x : lineStart.x, y : lineStart.y, id : start.id}, end : { x : lineEnd.x, y : lineEnd.y, id : end.id}};
	drawLines(data,lineDesignColor,lineFillDesignColor,true);
	
	//清空起点和终点信息
	start = null, end = null;
}

//绘制连接线
function drawLines(data,linecolor,lineFillColor,newline){
	var lineid = data.id, lbid = data.data;
	if (!lineid) lineid = "line_" + new Date().getTime();
	if (!lbid) lbid = "";
	
	var linehtml = $("<svg data = '"+ lbid +"' id = '" + lineid + "' style='width:100%; height:100%; position:absolute; top:0; left:0; z-index:1;'>" +
				   "<marker id = 'arrow_"+ lineid + "' markerWidth = '10' markerHeight = '10' refy = '3' orient = 'auto' markerUnits = 'strokeWidth'><path d = 'M0,0 L0, 6 L6,3 z' fill = '" + lineFillColor + "' /></marker>" +
				   "<line sid = '" + data.start.id + "' eid = '" + data.end.id + "' x1 = '" + data.start.x + "' y1 = '" + data.start.y + "' x2 = '" + data.end.x + "' y2 = '" + data.end.y + "' style='stroke:" + linecolor + ";stroke-width:2'  marker-end='url(#arrow_" + lineid + ")'/>" +
				   "</svg>");
	
	canvas.append(linehtml);
	
	//绑定鼠标按下事件
	linehtml.mousedown(function(e){
		closeMenu(menu);
		closeMenu(stepsMenu);
		if (e.target.nodeName == "line" && e.which == 3){
			search.confirm({title:"提示信息",content:"确认删除？",funl:function(){
				var line = $(e.target).parent();
				deleteLine(line);
			}});
			return false;
		}
	});
	
	//线画过后的缓存
	if (newline){
		 var line = {start : {x : data.start.x, y : data.start.y, id : data.start.id}, end : {x : data.end.x, y : data.end.y, id : data.end.id}, id : lineid, data : lbid};
		 lines[lineid] = line;
		 
		 insertLines.push(lineid);
	}
}



//关闭对话框
function onClose(){
	window.closeWindow("ok");
}