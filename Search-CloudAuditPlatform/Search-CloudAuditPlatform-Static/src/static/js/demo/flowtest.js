var dataOrigin = {}, invoker = window , dataColor = null;

function openFlowDesign() {
	var win = $(window), width = win.width(), height = win.height();
	
	search.popDialog({
		id : "flowDesigner1",
		title : "流程设计器",
		url : CTX + "/common/flow",
		width : width,
		height : height,
		modal : false,
		onload : function(window){
			window.loadData(dataOrigin, {invoker : invoker, method : saveData}, {width : 800, height : 600, url : CTX + "/demo/flowstep"}, {});
		},
		ondestroy : function(result) {
			//console.log("data->", result);
		}
	});
}

function openColorFlowDesign() {
	var win = $(window), width = win.width(), height = win.height();
	
	search.popDialog({
		id : "flowDesigner2",
		title : "流程设计器",
		url : CTX + "/common/flow",
		width : width,
		height : height,
		modal : false,
		onload : function(window){
			window.loadDataDesign(dataOrigin,dataColor);
		},
		ondestroy : function(result) {
			//console.log("data->", result);
		}
	});
}

function openformpermissions() {
	var win = $(window), width = win.width(), height = win.height();
	
	search.popDialog({
		id : "flowDesigner3",
		title : "流程设计器",
		url : CTX + "/common/flow",
		width : width,
		height : height,
		modal : false,
		onload : function(window){
			window.loadDataFormPermissions(dataOrigin, {width : 800, height : 600, url : CTX + "/demo/flowstep"}, {});
		},
		ondestroy : function(result) {
			//console.log("data->", result);
		}
	});
}

function saveData(data,changed,invoker,callback) {
	var stepDbid = new Date().getTime();
	
	for (var key in data.steps) {
		var step = data.steps[key];
		if (!step.extend) continue;
		
		step.data = stepDbid;
		delete step.extend;
		stepDbid ++;
	}
	
	for (var i = 0; i < changed.insertLines.length; i++){
		var changedLinesId = changed.insertLines[i];
		data.lines[changedLinesId].data = new Date().getTime();
	}
	
	dataOrigin = {};
	$.extend(true,dataOrigin,data);
	
	callback.call(invoker,data);
	
	dataColor = {};
	var dataColorSteps = Object.getOwnPropertyNames(data.steps);
	var dataColorLines = Object.getOwnPropertyNames(data.lines);
	
	if (dataColorSteps.length >= 8 && dataColorLines.length >= 6){
		var line0 = data.lines[dataColorLines[0]],line1 = data.lines[dataColorLines[1]],line2 = data.lines[dataColorLines[2]],line3 = data.lines[dataColorLines[3]],line4 = data.lines[dataColorLines[4]],line5 = data.lines[dataColorLines[5]];

		dataColor.handling = data.steps[dataColorSteps[0]].data;
		dataColor.exception = data.steps[dataColorSteps[1]].data;
		
		dataColor.finals = {};
		dataColor.finals.steps = [data.steps[dataColorSteps[2]].data,data.steps[dataColorSteps[3]].data];
		dataColor.finals.lines = [{start:data.steps[line0.start.id].data, end:data.steps[line0.end.id].data},{start:data.steps[line1.start.id].data,end:data.steps[line1.end.id].data}];
		
		dataColor.invalids = {};
		dataColor.invalids.steps = [data.steps[dataColorSteps[4]].data,data.steps[dataColorSteps[5]].data];
		dataColor.invalids.lines = [{start:data.steps[line2.start.id].data,end:data.steps[line2.end.id].data},{start:data.steps[line3.start.id].data,end:data.steps[line3.end.id].data}];
		
		dataColor.backwards = {};
		dataColor.backwards.steps = [data.steps[dataColorSteps[6]].data,data.steps[dataColorSteps[7]].data];
		dataColor.backwards.lines = [{start:data.steps[line4.start.id].data,end:data.steps[line4.end.id].data},{start:data.steps[line5.start.id].data,end:data.steps[line5.end.id].data}];
	}
	
}
