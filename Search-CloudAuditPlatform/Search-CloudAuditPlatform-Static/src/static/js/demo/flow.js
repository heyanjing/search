var steps = null, lines = null, saveMethod = null, saveinvoker = null, dataOrigin = null, saveStepUrl = null; 
var canvas = $(".flow-canvas"), menu = $(".flow-canvas-menu-container"), stepsMenu = $(".step-menu-container");

//测试内容开始
$(function() {
	
	loadData({}, window, testSave,"http://localhost/main");
	
});

function testSave(data, invoker, callback) {
	console.log("data->", data);
	callback.call(invoker, true);
}
//测试内容结束


//绘制方法
function draw(bindEvent){
	if (!steps) steps = {};
	if (!lines) lines = {};
	
	//加载已存在的步骤
	var hasStart = false, hasEnd = false;
	for (var key in steps) {
		var step = steps[key];
		if (step.type == 1) hasStart = true;
		else if (step.type == 4) hasEnd = true;
		
		drawStep(step);
	}
	
	//加载已存在的步骤链接
	for (var key in lines) {
		var line = lines[key];
		drawLines(line);
	}
	
	if (bindEvent) {
		
		//绑定菜单点击事件
		$("#menu-save").bind("click",saveDesign);
		$("#menu-refresh").bind("click",refresh);
		var startMenu = $("#menu-start"), endMenu = $("#menu-end");
		if (!hasStart) startMenu.bind("click", addStartStep);
		else disableMenu(startMenu, false);
		if (!hasEnd) endMenu.bind("click", addEndStep);
		else disableMenu(endMenu, false);
		$("#menu-normal").bind("click", addPlainStep);
		$("#menu-more").bind("click", addSginStep);
		
		$("#step-trash").click(function(){
			search.confirm({title:"提示信息",content:"确认删除？"});
			closeStepsMenu();
		});
		$("#step-cog").click(function(){
			openBox();
			closeStepsMenu();
		});
		$("#step-table").click(function(e){
			if ($(e.target).attr("class").containsClass("step-table")){
				return;
			}
			openBox();
			closeStepsMenu();
		});
		$("#step-share").click(function(e){
			if ($(e.target).attr("class").containClass("step-table")){
				return;
			}
			openBox();
			closeStepsMenu();
		});
	}
}

function openBox(){
	search.popDialog({
		id : "dd",
		title : "流程属性",
		url : saveStepUrl,
		width : 200,
		height : 200,
		modal : true,
		onload : function(window){
			//window.initPage(menuIndex,stepType,stepId, stepName);
		},
		ondestroy : function(result) {
			//console.log("data->", result);
		}
	});
}

function loadData(data,invoker,method,stepUrl) {
	steps = data.steps, lines = data.lines, saveMethod = method, saveInvoker = invoker, saveStepUrl = stepUrl;
	
	dataOrigin = {};
	$.extend(true,dataOrigin,data);
	
	//绑定鼠标按下事件
	canvas.mousedown(function(e) {
		closeStepsMenu();
		if(e.which == 3){
			showMenu(e.clientY, e.clientX);
			return false;
		}
		closeMenu();
	});
	
	draw(true);
}

//标签上的右键菜单
function showStepsMenu(top,left,type){
	var winWidth = $(window).width();
	var winHeight = $(window).height();
	var stepsMenuWidth = stepsMenu.outerWidth();
	var stepsMenuHeight = stepsMenu.outerHeight();
	
	if (left + stepsMenuWidth > winWidth) left = winWidth - stepsMenuWidth;
	if (top + stepsMenuHeight > winHeight) top = winHeight - stepsMenuHeight;
	
	stepsMenu.css("left", left);
	stepsMenu.css("top", top);
	stepsMenu.animate({ height : "100" });
	
	var table = $("#step-table"), tablei = table.find("i");
	var share = $("#step-share"), sharei = share.find("i");
	if (type == 1 || type == 4){
		table.addClass("step-table");
		tablei.addClass("step-table-i");
		share.addClass("step-table");
		sharei.addClass("step-table-i");
	}else{
		table.removeClass("step-table");
		tablei.removeClass("step-table-i");
		share.removeClass("step-table");
		sharei.removeClass("step-table-i");
	}
}

function closeStepsMenu(){
	stepsMenu.css("height" , "0");
}

//画布上的右键菜单
function showMenu(top, left, type) {
	var winWidth = $(window).width();
	var winHeight = $(window).height();
	var menuWidth = menu.outerWidth();
	var menuHeight = menu.outerHeight();
	
	if (left + menuWidth > winWidth) left = winWidth - menuWidth;
	if (top + menuHeight > winHeight) top = winHeight - menuHeight;
	
	menu.css("left", left);
	menu.css("top", top);
	menu.animate({ height : "150" });
	}

function closeMenu(){
	menu.css("height" , "0");
}

//标签的绘制
function drawStep(data, newstep) {
	var divcls = "", icls = "";
	switch (data.type) {
		case 1:
			divcls = "flow-start-prismatic btn";
			icls = "fa fa-star";
			break;
		case 2:
			divcls = "flow-plain-prismatic btn";
			icls = "fa fa-plus";
			break;
		case 3:
			divcls = "flow-sgin-prismatic btn";
			icls = "fa fa-retweet";
			break;
		case 4:
			divcls = "flow-end-prismatic btn";
			icls = "fa fa-dot-circle-o";
			break;
	}
	
	var id = data.id, dbid = data.data;
	if (!id) id = "step_" + Object.getOwnPropertyNames(steps).length ;
	if (!dbid) dbid = "";
	
	var stephtml = $("<div data = '" + dbid + "' id = '" + id + "' type = '" + data.type + "' class='" + divcls + "' style='top:" + data.pos.top + "px;left:" + data.pos.left + "px;'>" +
						"<span><i class='" + icls + "'></i>" + data.name + "</span>" +
					 "</div>");
	canvas.append(stephtml);
	
	bindDragEvent(stephtml);
	bindDrawLineEvent(stephtml.find("i"));
	
	//绑定鼠标按下事件
	stephtml.mousedown(function(e){
		if (e.which == 3){
			showStepsMenu(e.clientY, e.clientX , data.type);
			return false;
		}
		closeStepsMenu();
	});
	
	
	//标签画过后的缓存
	if (newstep) {
		var step = {id : id, type : data.type, pos : {top : data.pos.top, left : data.pos.left}, name : data.name, data : data}; 
		steps[id] = step;
	}
}

//解绑画布标签
function disableMenu(menuObj, boundEvent) {
	if (boundEvent) menuObj.unbind("click");
	menuObj.css({background:"none", cursor:"default", color:"#ccc"});
	menuObj.find("i").css("color","#ccc");
}

//保存设计
function saveDesign(){
	closeMenu();
	
	saveMethod.call(saveInvoker,{steps:steps,lines:lines},window,saveCallback);
}

//刷新
function refresh(){
	closeMenu();
	canvas.empty();
	
	steps = {}, lines = {};
	$.extend(true,steps,dataOrigin.steps);
	$.extend(true,lines,dataOrigin.lines);
	
	draw(false);
}

function saveCallback(result){
	
	if(result) {
		dataOrigin = {};
		$.extend(true,dataOrigin,{steps:steps,lines:lines});
	}
}

//开始步骤
function addStartStep() {
	closeMenu();
	
	var pos = menu.position();
	var data = { type : 1, pos : { top : pos.top - 30, left : pos.left }, name : "开始" };
	drawStep(data, true);
	
	disableMenu($("#menu-start"), true);
}

//普通步骤
function addPlainStep(){
	closeMenu();
	
	var pos = menu.position();
	var data = { type : 2, pos : {top : pos.top - 30, left : pos.left }, name : "普通步骤"};
	drawStep(data, true);
}

//会签步骤
function addSginStep(){
	closeMenu();
	
	var pos = menu.position();
	var data = { type : 3, pos : { top : pos.top - 30, left : pos.left }, name : "会签步骤"};
	drawStep(data, true);
}

//结束步骤
function addEndStep(){
	closeMenu();
	
	var pos = menu.position();
	var data = { type : 4, pos : { top : pos.top - 30, left : pos.left }, name : "结束" };
	drawStep(data, true);
	
	disableMenu($("#menu-end"), true);
}

//面板拖拽按钮
function bindDragEvent(step){
	var doc = $(document);
	var width = step.outerWidth();
	var height = step.outerHeight();
	
	step.bind({
		mousedown : function(e) {
			var os = $(this).offset();
			var dx = e.pageX - os.left, dy = e.pageY - os.top;
			
			doc.on("mousemove.drag", function(e) {
				var before = step.position();
				step.offset({left : e.pageX - dx, top : e.pageY - dy});
				
				var pos = step.position();
				
				if (pos.left < 0) step.css("left", 0);
				else if (pos.left > canvas.width() - width) step.css("left", canvas.width() - width);
				
				if (pos.top < 0) step.css("top", 0);
				else if (pos.top > canvas.height() - height) step.css("top", canvas.height() - height);
				
				var after = step.position();
				var ldx = after.left - before.left, ldy = after.top - before.top;
				
				//绘制标签拖动线随着拖动
				var id = step.attr("id");
				var starts = $("line[sid='" + id + "']");
				for (var i = 0; i < starts.length; i++){
					var start = $(starts[i]);
					var lid = start.parent().attr("id");
					var line = lines[lid];
					var estep = $("#" + start.attr("eid"));
					var ewidth = estep.outerWidth(), eheight = estep.outerHeight(), eafter = estep.position();
					var p1 = {x : after.left + width / 2 , y : after.top + height / 2};
					var p2 = {x : eafter.left + ewidth / 2, y : eafter.top + eheight / 2};
					
					var lineStart = {}, lineEnd = {};
					var pdx = p1.x - p2.x, pdy = p1.y - p2.y;
					
					if (Math.abs(pdx) >= Math.abs(pdy)) {
						
						//计算起点坐标
						if (pdx > 0) lineStart.x = after.left;  //左边
						else lineStart.x = after.left + width;  //右边
						lineStart.y = after.top + height / 2;
						
						//计算终点坐标
						if (pdx > 0) lineEnd.x = eafter.left + ewidth;  //右边
						else lineEnd.x = eafter.left;                   //左边
						lineEnd.y = eafter.top + eheight / 2;
						
					} else {
						
						//计算起点坐标
						lineStart.x = after.left + width / 2;
						if (pdy > 0) lineStart.y = after.top;    //上边
						else lineStart.y = after.top + height;  // 下边
						
						//计算终点坐标
						lineEnd.x = eafter.left + ewidth / 2;
						if (pdy > 0) lineEnd.y = eafter.top + eheight;    //下边
						else lineEnd.y = eafter.top;                      //上边
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
				
				var ends = $("line[eid='" + id + "']");
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
						else lineStart.x = safter.left + swidth;   //左边
						lineStart.y = safter.top + sheight / 2;
						
						//计算终点坐标
						if (pdx > 0) lineEnd.x = after.left + width;   //左边
						else lineEnd.x = after.left;                    //右边
						lineEnd.y = after.top + height / 2;
						
					}else{
						
						//计算起点坐标
						lineStart.x = safter.left + swidth / 2;
						if (pdy > 0) lineStart.y = safter.top;   //下边
						else lineStart.y = safter.top + sheight;  //上边
						
						//计算终点坐标
						lineEnd.x = after.left + width / 2;
						if (pdy > 0) lineEnd.y = after.top + height;    //上边
						else lineEnd.y = after.top;                      //下边
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
		mouseup : function(e) {
			doc.off("mousemove.drag");
		}
	});
}

//线条
var start = null, end = null,startType = null;

function bindDrawLineEvent(i) {
	i.bind({
		mousedown : function(e) {
			var step = i.parent().parent();
			var pos = step.position(), width = step.outerWidth(), height = step.outerHeight();
			startType = step.attr("type");
			
			//判断开始
			//if ( step.attr("type") == "4" ) return false;
			
			//记录鼠标按下流程节点相关属性
			start = { c : { x : pos.left + width / 2, y : pos.top + height / 2 }, p : { x : pos.left, y : pos.top }, s : { w : width, h : height }, id : step.attr("id") };
			return false;
		}, 
		mouseup : function(e) {
			var step = i.parent().parent();
			var pos = step.position(), width = step.outerWidth(), height = step.outerHeight();
			
			//判断结束
			var endType = step.attr("type");
			if ( startType == "1" && ( endType == "3" || endType == "4")) return false;
			if ( startType == "2" && endType == "1") return false;
			if ( startType == "3" && ( endType == "1" || endType == "4")) return false;
			if ( startType == "4") return false;
			
			//记录鼠标放开流程节点相关属性
			end = { c : { x : pos.left + width / 2, y : pos.top + height / 2 }, p : { x : pos.left, y : pos.top }, s : { w : width, h : height }, id : step.attr("id") };
			
			drawLine();
			return false;
		}
	});
}

function drawLine() {
	if (!start || !end || (start.c.x == end.c.x && start.c.y == end.c.y)) return;		//无起点或终点或起点与终点相同
	
	var lineStart = {}, lineEnd = {};
	var dx = start.c.x - end.c.x, dy = start.c.y - end.c.y;
	if (Math.abs(dx) >= Math.abs(dy)) {		//起点和终点位于流程节点左右两边
		//计算起点坐标
		if (dx > 0) lineStart.x = start.p.x;		//左边
		else lineStart.x = start.p.x + start.s.w;	//右边
		lineStart.y = start.p.y + start.s.h / 2;
		
		//计算终点坐标
		if (dx > 0) lineEnd.x = end.p.x + end.s.w;	//右边
		else lineEnd.x = end.p.x;					//左边
		lineEnd.y = end.p.y + end.s.h / 2;
	} else {								//起点和终点位于流程节点上下两边
		//计算起点坐标
		lineStart.x = start.p.x + start.s.w / 2;
		if (dy > 0) lineStart.y = start.p.y;		//上边
		else lineStart.y = start.p.y + start.s.h;	//下边
		
		//计算终点坐标
		lineEnd.x = end.p.x + end.s.w / 2;
		if (dy > 0) lineEnd.y = end.p.y + end.s.h;	//下边
		else lineEnd.y = end.p.y;					//上边
	}
	
	//计算实际终点坐标
	var d = Math.sqrt(Math.pow((lineEnd.x - lineStart.x), 2) + Math.pow((lineEnd.y - lineStart.y), 2));
	dx = (lineEnd.x - lineStart.x) * 11 / d, dy = (lineEnd.y - lineStart.y) * 11 / d;
	lineEnd.x -= dx, lineEnd.y -= dy;
	
	//绘制箭头
	var data = { start : { x : lineStart.x, y : lineStart.y, id : start.id}, end : {x : lineEnd.x, y : lineEnd.y, id : end.id}};
	drawLines(data,true);
	
	//清空起点和终点信息
	start = null, end = null;
}

//绘制连接线
function drawLines(data, newline){
	var lineid = data.id;
	if (!lineid) lineid = "line_" + Object.getOwnPropertyNames(lines).length ;
	
	var linehtml = $("<svg id = '" + lineid + "' style='width:100%; height:100%; position:absolute;top:0;left:0; z-index:1;'>" +
						"<marker id='arrow' markerWidth='10' markerHeight='10' refx='0' refy='3' orient='auto' markerUnits='strokeWidth'><path d='M0,0 L0,6 L6,3 z' fill='#fff' /> </marker> " +
						"<line sid = '" + data.start.id + "' eid = '" + data.end.id + "' x1='" + data.start.x + "' y1='" + data.start.y + "' x2='" + data.end.x + "' y2='" + data.end.y + "' style='stroke:rgb(255,255,255);stroke-width:2'  marker-end='url(#arrow)'/>" +
					"</svg>");
	
	canvas.append(linehtml);
	
	//线画过后的缓存
	if (newline){
		var line = {start : {x : data.start.x, y : data.start.y}, end : {x : data.end.x, y : data.end.y}, id : lineid};
		lines[lineid] = line;
	}
	
}






