search.parse();

/**
 * 实例控制器。
 */
function ProjectLibMgrHandle(){
	this.grid = search.get("proLibGrid");
	
	this.loadProLibsMgrData = function() {
		this.grid.url = CTX + "/projectlib/getProjectLibList";
		this.grid.load({istate: __state});
	};
	
	/**
	 * 修改项目状态。
	 */
	this.updateProjectLibState = function(sid, text, istate){
		search.confirm({ content : "确定要"+text+"?", funl : function() {
		 　$.ajax({
				url : CTX+"/projectlib/updateProjectLibState",
				data : {sid: sid, istate: istate},
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
	
}

var instance = new ProjectLibMgrHandle();

/**
 * 新增。
 */
function addProLid(){
	window.location.href = CTX + "/projectlib/gotoUpdateProjectLidPage?funcid="+__funcid;
}

/**
 * 编辑。
 */
function editProLid(params){
	var sid = "",sauditorgid = "";
	if(params != null && params != ""){
		if(typeof params === 'object'){
			sid = params.origin.record.sid;
			sauditorgid = params.origin.record.sauditorgid;
		}else{
			sid = params.split("_")[0]; 	
			sauditorgid = params.split("_")[1]; 	
		} 
	}else{
		var select = instance.grid.getSelected();
		if(!select){
			search.warn({content:"请选择一条数据!"});
			return;
		}
		sid = select.record.sid;
		sauditorgid = select.record.sauditorgid;
	}
	
	window.location.href = CTX + "/projectlib/gotoUpdateProjectLidPage?sid="+sid+"&funcid="+__funcid+"&sauditorgid="+sauditorgid;
}

/**
 * 删除。
 */
function delProLid(params){
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
		sid = instance.grid.getValue();
	}
	
	instance.updateProjectLibState(sid, "删除", 2);
}

/**
 * 回收站删除
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
		sid = instance.grid.getValue();
	}
	
	instance.updateProjectLibState(sid, "删除", 99);
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
		sid = instance.grid.getValue();
	}
	instance.updateProjectLibState(sid, "恢复", 1);
}

/**
 * 回收站。
 */
function recycleBin(){
	var iframe = window.parent.document.getElementById(window.parent.Index.currentDivId + "main_iframe");
	iframe.src = iframe.src + Globle.constant.recycle;
}

/**
 * 展开查询。
 */
function doQuery(){
	var devHeight = 0;
	if ($(".develop").css("display") == "none"){
		$(".develop").show();
		devHeight = $(".develop").outerHeight();
	}else $(".develop").hide();
	
	var toolHeight = $(".mini-tools").outerHeight();
	$(".mini-fit").css("top", (devHeight + toolHeight) + "px");
	search.get("proLibGrid").doLayout();
}

/**
 * 关键字查询。
 */
function queryProLib(){
	var keyword = search.get("keyword").getValue();
	instance.grid.load({istate: __state,keyword : keyword});
}

/**
 * 查看详情。
 * @param params 功能ID。
 * @returns
 */
function queryDetail(params){
	var sid = "";
	if(typeof params === 'object') sid = params.origin.record.sid;
	else sid = params;
	window.location.href = CTX + "/projectlib/gotoProjectLibViewPage?sid="+sid+"&funcid="+__funcid;
}

/**
 * 绘制单元格。
 * @param e 单元格。
 */
function onDrawCell(e){
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
	case "operation":
		if (__state == 1) html = Globle.fun.generaTetailBut(tailButList, e.record.sid);
		else if (__state == 2) {
			html = '<a class="del" href="javascript:void(0);" onclick="delData(\'' + e.record.sid + '\');">删除</a>'
			 + ' <a class="btn-enable" href="javascript:void(0);" onclick="restData(\'' + e.record.sid + '\');">恢复</a>';
		}
		break;
}
	if(html)e.html = html;
}


$(function (){
	initalQuery();
});

/**
 * 加载数据。
 */
function initalQuery(){
	instance.loadProLibsMgrData();
}