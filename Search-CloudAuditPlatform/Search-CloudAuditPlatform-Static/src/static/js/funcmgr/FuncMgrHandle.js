search.parse();

function FunctionMgrHandle(){
	this.form = new Form("funcParams");
	this.grid = search.get("funcGrid");
	this.itype = search.get("itype");
	this.isupportphone = search.get("isupportphone");
	this.isupportproject = search.get("isupportproject");
	this.sname = search.get("sname");
	
	this.loadFuncGrid = function() {
		this.grid.url = CTX + "/funcmgr/getFuncMgrList";
		this.grid.load({istate: __state});
	};
	
	/**
	 * 修改功能状态。
	 */
	this.updateFuncState = function(sid, text, istate){
		search.confirm({content: "确定要"+text+"？", funl : function() {
		 　$.ajax({
				url : CTX+"/funcmgr/updateFuncState",
				data : {sid: sid, state: istate},
				success : function(result){
					if(result.status){
						search.info({content:result.result.meg,funl:function(){
							initalQuery();
						}});
					}else search.error({content:"系统错误!"});
				}
			});
		  }
		})
	};
	
	/**
	 * 功能类型。
	 */
	this.funcType = function(type){
		var itype= Globle.constant.funcType;
		var text = "";
		for(var i = 0 ; i < itype.length; i++){
			if(type == itype[i].value) text = itype[i].text;
		}
		
		return text;
	};
	
	/**
	 * 是否。
	 */
	this.isupportPhone = function(value){
		var itype= Globle.constant.YesOrNo;
		var text = "";
		for(var i = 0 ; i < itype.length; i++){
			if(value == itype[i].value) text = itype[i].text;
		}
		return text;
	};
	
	this.funcState = function(value){
		var itype= Globle.constant.funcState;
		var text = "";
		for(var i = 0 ; i < itype.length; i++){
			if(value == itype[i].value) text = itype[i].text;
		}
		return text;
	};
	
//	/**
//	 * 生成尾部按钮。
//	 */
//	this.generaTetailBut = function(id){
//		var text = '';
//		for(var i = 0; i < tailButList.length; i++){
//			var func = tailButList[i];
//			text += '<a class="'+func.sicon+'" href="javascript:void(0);" onclick="'+func.spcmethod+'(\'' + id + '\');">'+func.sname+'</a> ';
//		}
//		return text;
//	}
}

var instance = new FunctionMgrHandle();

/**
 * 新增。
 * @returns
 */
function addFunct(){
	var select = instance.grid.getSelected();
	if(select){
		var data = {
			url : CTX+"/funcmgr/gotoNewFuncObj",
			title : "新增",
			width : 600,
			height : 470,
			onload : function(window){
				window.instance.loadData({action:"add", sid: select.record.sid});
			},
			ondestroy : function(){
				initalQuery();
			}
		}
	}else{
		var data = {
			url : CTX+"/funcmgr/gotoNewFuncObj",
			title : "新增",
			width : 600,
			height : 470,
			onload : function(window){
				window.instance.loadData({action:"add", sid: "-1"});
			},
			ondestroy : function(){
				initalQuery();
			}
		}
	}
	top.search.popDialog(data);
}

/**
 * 编辑。
 * @returns
 */
function editFunct(params){
	var sid = "";
	if(params != null && params != ""){
		if(typeof params === 'object')sid = params.origin.record.sid;
		else sid = params;
	}else{
		var select = instance.grid.getSelected();
		if(!select){
			search.warn({content:"请选择一条数据!"});
			return;
		}
		sid = select.record.sid;
	}
	
	var data = {
		url : CTX+"/funcmgr/gotoNewFuncObj",
		title : "编辑",
		width : 600,
		height : 470,
		onload : function(window){
			window.instance.loadData({action:"edit", sid: sid});
		},
		ondestroy : function(){
			initalQuery();
		}
	}
	top.search.popDialog(data);
}

/**
 * 删除功能
 */
function delData(params){
	var sid = "";
	if(params != null && params != ""){
		if(typeof params === 'object')sid = params.origin.record.sid;
		else sid = params;
	}else{
		var select = instance.grid.getSelected();
		if(!select){
			search.warn({content:"请选择一条禁用数据!"});
			return;
		}
		sid = select.record.sid;
	}
	
	instance.updateFuncState(sid, "删除", 99);
}

/**
 * 恢复数据
 */
function restData(params){
	var sid = "";
	if(params != null && params != ""){
		if(typeof params === 'object')sid = params.origin.record.sid;
		else sid = params;
	}else{
		var select = instance.grid.getSelected();
		if(!select){
			search.warn({content:"请选择一条禁用数据!"});
			return;
		}
		sid = select.record.sid;
	}
	instance.updateFuncState(sid, "恢复", 1);
}

/**
 * 禁用
 */
function disableFunct(params){
	var sid = "";
	if(params != null && params != ""){
		if(typeof params === 'object')sid = params.origin.record.sid;
		else sid = params;
	}else{
		var select = instance.grid.getSelected();
		if(!select){
			search.warn({content:"请选择一条启用数据!"});
			return;
		}
		sid = select.record.sid;
	}
	instance.updateFuncState(sid, "禁用", 2);
}

/**
 * 回收站。
 */
function recycleBin(){
	var iframe = window.parent.document.getElementById(window.parent.Index.currentDivId + "main_iframe");
	iframe.src = iframe.src + Globle.constant.recycle;
}

/**
 * 查看详情。
 * @param params 功能ID。
 * @returns
 */
function queryDetail(params){
	var sid = "";
	if(typeof params === 'object')sid = params.origin.record.sid;
	else sid = params;
	top.search.popDialog({ url : CTX + "/funcmgr/goFuncViewPage", title : "详情", width : 600, height : 450,
		onload : function(window) {
			window.instance.loadData({sid: sid});
		}, ondestroy : function(result){}
	});
}

/**
 * 展开查询。
 * @returns
 */
function doQuery(){
	var devHeight = 0;
	if ($(".develop").css("display") == "none"){
		$(".develop").show();
		devHeight = $(".develop").outerHeight();
	}else{
		$(".develop").hide();
	}
	var toolHeight = $(".mini-tools").outerHeight();
	$(".mini-fit").css("top", (devHeight + toolHeight) + "px");
	search.get("funcGrid").doLayout();
}

/**
 * 查询。
 * @returns
 */
function onFuncSearch() {
	var names = "";
	var keyword = search.get("keyword").getValue();
	var itype= Globle.constant.funcType;
	var state= Globle.constant.YesOrNo;
	var str = keyword.split(' ');
	if(keyword != ""){
		for(var i=0;i<str.length;i++){
			var itypes = "";
			for(var j=0;j<itype.length;j++){
				if(itype[j].text.indexOf(str[i]) != -1){
					itypes += itype[j].value +",";
				}
			}
			var states = "";
			var isupportphone = "";
			for(var j=0;j<state.length;j++){
				if(state[j].text.indexOf(str[i]) != -1){
					isupportphone += state[j].value +",";
//				states += state[j].value +",";
				}
			}
			if(itypes != ""){
				itypes = itypes.substring(0,itypes.length-1);
				names += itypes + " ";
			}
			
//		if(states != ""){
//			states = states.substring(0,states.length-1);
//			names += states + " ";
//		}
			
			if(isupportphone != ""){
				isupportphone = isupportphone.substring(0,isupportphone.length-1);
				names += isupportphone + " ";
			}
			if(isupportphone == "" && states == "" && itypes == ""){
				names += str[i]+" ";
			}
		}
		names = names.substring(0,names.length-1);
	}else{
		names = "";
	}
	
	
	
	instance.grid.load({
		istate: __state,
		keyword : names,
		itype: instance.itype.getValue()
		});
}

/**
 * 页面加载完后执行。
 */
$(function(){
	initalQuery();
//	instance.itype.loadData(Globle.constant.funcTypeQue);
//	instance.isupportphone.loadData(Globle.constant.FuncYesOrNo);
//	instance.isupportproject.loadData(Globle.constant.FuncYesOrNo);
});

/**
 * 初始化加载尾部功能按钮。
 * @returns
 */
function initalQuery(){
	instance.loadFuncGrid();
	instance.itype.loadData(Globle.constant.funcTypeQue);
}

/**
 * 绘制单元格。
 * @param e
 * @returns
 */
function ondrawcell(e){
	var html = "";
	switch (e.column.field) {
	    case "sname":
	    	if(__state == 1){
	    		if(_all_quick_map.get(e.column.field) == "") html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
		    	else{
		    		if(_quick_map.get(e.column.field) == "" ) html = "<span >"+e.record.sname+"</span>";
		    		else html = "<span style='color: blue;' onclick='"+ _quick_map.get(e.column.field)+"(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
		    	}
	    	}else if(__state == 2) html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	break;
		case "itype":
			html = instance.funcType(e.record.itype);
			break;
		case "isupportphone":
			html = instance.isupportPhone(e.record.isupportphone);
			break;
		case "isupportproject":
			html = instance.isupportPhone(e.record.isupportproject);
			break;
//		case "istate":
//			html = instance.funcState(e.record.istate);
//			break;
		case "operation":
			if (__state == 1)e.html = Globle.fun.generaTetailBut(tailButList, e.record.sid);
			else if (__state == 2) {
				e.html = '<a class="del" href="javascript:void(0);" onclick="delData(\'' + e.record.sid + '\');">删除</a>'
				 + ' <a class="btn-enable" href="javascript:void(0);" onclick="restData(\'' + e.record.sid + '\');">恢复</a>';
			}
			break;
	}
	if(html)e.html = html;
}
