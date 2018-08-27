search.parse();

/*---------------------------------项目库立项和可研开始------------------------------------------*/
/**
 * 实例控制器。
 */
function UpdateProjectLidHandle(){
	this._approval_Form = new Form("ApprovalForm"), this._bility_Form = new Form("BilityForm");
	this._approval_sid = "", this._bility_sid = "", this.iApprovalFile = {arr : [], index : 0}, this.iBilityFile = {arr : [], index : 0};
	var lx = true,ky = true;
	/**
	 * 招标核准。
	 */
	this.getBidApproved = function(way){
		var itype= Globle.constant.ApprovalApproved;
		var text = "";
		for(var i = 0 ; i < itype.length; i++){
			if(way == itype[i].value) text = itype[i].text;
		}
		return text;
	};
	
	/**
	 * 加载立项详情。
	 */
	this.loadApprovalData = function(params){
		$.post(CTX + "/projectlib/getApprovalObjBySid", { sid : params, itype: true }, function(result) {
			if(result.status){
				if(!lx){
					return;
				}
				lx = false;
				instance._approval_Form.setData(result.result.mapdata);
				instance._approval_sid = result.result.mapdata.sid;
				search.get("iprospectingtype").setValue(instance.getBidApproved(result.result.mapdata.iprospectingtype));
				search.get("idesigntype").setValue(instance.getBidApproved(result.result.mapdata.idesigntype));
				search.get("isupervisiontype").setValue(instance.getBidApproved(result.result.mapdata.isupervisiontype));
				search.get("iconstructiontype").setValue(instance.getBidApproved(result.result.mapdata.iconstructiontype));
				search.get("iintermediarytype").setValue(instance.getBidApproved(result.result.mapdata.iintermediarytype));
				
				if (result.result && result.result.attachList && result.result.attachList.length > 0) {
					$.each(result.result.attachList, function(index) {
						var $img = $('<img src="' + NETWORK_ROOT + this.spath + '">');
						$img.data(this);
						$img.data("index", index);
						$("#approval_attach").append($img);
						instance.iApprovalFile.arr.push({sname : this.sname, spath : this.spath});
					})
					$("#approval_attach").on("dblclick", "img", function() {
						var $this = $(this);
						instance.iApprovalFile.index = $this.data("index");
						Globle.fun.preview(instance.iApprovalFile);
					})
				}
			}else top.search.error({ content : "系统错误！" });
		})
	};
	
	/**
	 * 加载可研详情数据。
	 */
	this.loadFeasibilityData = function(params){
		$.post(CTX + "/projectlib/getFeasibilityObjBySid", {sid : params, itype: true }, function(result) {
			console.log(result);
			if(result.status){
				if(!ky){
					return;
				}
				ky = false;
				instance._bility_Form.setData(result.result.mapdata);
				instance._bility_sid = result.result.mapdata.sid_0;
				search.get("iprospectingtype_0").setValue(instance.getBidApproved(result.result.mapdata.iprospectingtype_0));
				search.get("idesigntype_0").setValue(instance.getBidApproved(result.result.mapdata.idesigntype_0));
				search.get("isupervisiontype_0").setValue(instance.getBidApproved(result.result.mapdata.isupervisiontype_0));
				search.get("iconstructiontype_0").setValue(instance.getBidApproved(result.result.mapdata.iconstructiontype_0));
				search.get("iintermediarytype_0").setValue(instance.getBidApproved(result.result.mapdata.iintermediarytype_0));
				
				if (result.result && result.result.attachList && result.result.attachList.length > 0) {
					$.each(result.result.attachList, function(index) {
						var $img = $('<img src="' + NETWORK_ROOT + this.spath + '">');
						$img.data(this);
						$img.data("index", index);
						$("#Feasibility_attach").append($img);
						instance.iBilityFile.arr.push({sname : this.sname, spath : this.spath});
					})
					$("#Feasibility_attach").on("dblclick", "img", function() {
						var $this = $(this);
						instance.iBilityFile.index = $this.data("index");
						Globle.fun.preview(instance.iBilityFile);
					})
				}
			}else top.search.error({ content : "系统错误！" });
		})
	};
	
}

var instance = new UpdateProjectLidHandle();

/**
 * 立项调整历史。
 */
function getApprovalHistory(){
	var data = {
		url : CTX+"/projectlib/gotoAdjustmenHistoryPage",
		title : "立项调整历史",
		width : 600,
		height : 450,
		onload : function(window){
			window.instance.loadHistoryData({sid: instance._approval_sid});
		},
		ondestroy : function(){
			instance.loadApprovalData(_prolib_id);
		}
	}
	top.search.popDialog(data);
}

/**
 * 可研调整历史。
 */
function getFeasibilityHistory(){
	var data = {
		url : CTX+"/projectlib/gotoAdjustmenHistoryPage",
		title : "可研调整历史",
		width : 600,
		height : 450,
		onload : function(window){
			window.instance.loadHistoryData({sid: instance._bility_sid});
		},
		ondestroy : function(){
			instance.loadFeasibilityData();
		}
	}
	top.search.popDialog(data);
}

/**
 * 返回列表。
 */
function goBack(){
	window.location.href = CTX + "/projectlib/gotoProjectLibMgrPage?id=" + __funcid;
}

/**
 * 页面加载完加载。
 */
$(function(){
	instance.loadApprovalData(_prolib_id);
})

/**
 * 切换标题栏。
 */
$('#construction').on('click', 'li', function () {
    var $li = $(this), index = $li.index(), $constructionBd = $('#construction-bd').children().eq(index);
    $li.addClass('active').siblings().removeClass('active');
    $constructionBd.addClass('thisclass').siblings().removeClass('thisclass');
    if($li.index() == 0) instance.loadApprovalData(_prolib_id);
    else if($li.index() == 1) instance.loadFeasibilityData(_prolib_id);
    else if($li.index() == 2) {
    	calculation.grid.doLayout();
    	calculation.loadCalculationMgrData();
    }else if($li.index() == 3){
    	budget.grid.doLayout();
    	budget.loadBudgetMgrData();
    }else if($li.index() == 4){
    	bid.grid.doLayout();
    	bid.loadBidMgrData();
    }
    
});

/*---------------------------------项目库立项和可研结束------------------------------------------*/

/*-------------------------------------项目库概算开始--------------------------------------------*/
/**
 * 概算控制器。
 */
function CalculationMgrHandle(){
	this.grid = search.get("CalculationGrid");

	this.loadCalculationMgrData = function(){
		this.grid.url = CTX + "/calculat/getCalculationList";
		this.grid.load({sid: _prolib_id});
	};

	/**
	 * 关键字查询。
	 */
	this.queryCalculation = function(){
		var keyword = search.get("calculationkeyword").getValue();
		this.grid.load({sid: _prolib_id, keyword : keyword});
	};

}

/**
 * 实例化概算控制器。
 */
var calculation = new CalculationMgrHandle();

/**
 * 查看概算详情。
 */
function queryCalculDetail(params){
	var sid = "";
	if(typeof params === 'object') sid = params.origin.record.sid;
	else sid = params;
	top.search.popDialog({ url : CTX + "/calculat/gotoCalculationViewPage", title : "详情", width : 650, height : 500,
		onload : function(window) {
			window.instance.loadData({sid: sid});
		}, ondestroy : function(result){}
	});
};

/**
 * 绘制概算列表单元格。
 * @param e
 */
function onCalculatDrawCell(e){
	var html = "";
	switch (e.column.field) {
	    case "sname":
	    	html = "<span style='color: blue;' onclick='calculation.editCalculat(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	break;
		case "operation":
				html = '<a class="del" href="javascript:void(0);" onclick="calculation.delCalculat(\'' + e.record.sid + '\');">删除</a>';
			break;
	}
	if(html)e.html = html;
}

/*-------------------------------------项目库概算结束--------------------------------------------*/

/*-------------------------------------项目库预算开始--------------------------------------------*/
/**
 * 预算控制器。
 */
function BudgetMgrHandle(){
	this.grid = search.get("BudgetGrid");

	/**
	 * 加载预算数据。
	 */
	this.loadBudgetMgrData = function(){
		this.grid.url = CTX + "/budget/getBudgetList";
		this.grid.load({sid: _prolib_id});
	};
	
	/**
	 * 关键字查询。
	 */
	this.queryBudget = function(){
		var keyword = search.get("budgetkeyword").getValue();
		this.grid.load({sid: _prolib_id, keyword : keyword});
	};
	
}

/**
 * 实例化预算。
 */
var budget = new BudgetMgrHandle();

/**
 * 查看预算详情。
 */
function queryBudgetDetail(params){
	var sid = "";
	if(typeof params === 'object') sid = params.origin.record.sid;
	else sid = params;
	top.search.popDialog({ url : CTX + "/budget/gotoBudgetViewPage", title : "详情", width : 650, height : 500,
		onload : function(window) {
			window.instance.loadData({sid: sid});
		}, ondestroy : function(result){}
	});
}

/**
 * 绘制预算列表单元格。
 * @param e
 */
function onBudgetDrawCell(e){
	var html = "";
	switch (e.column.field) {
	    case "sname":
	    	html = "<span style='color: blue;' onclick='budget.editBudget(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	break;
		case "operation":
				html = '<a class="del" href="javascript:void(0);" onclick="budget.delBudget(\'' + e.record.sid + '\');">删除</a>';
			break;
	}
	if(html)e.html = html;
}
/*-------------------------------------项目库预算结束--------------------------------------------*/

/*------------------------------------项目库招投标开始-------------------------------------------*/
/**
 * 施工招投标控制器。
 */
function BidMgrHandle(){
	this.grid = search.get("BidGrid");

	/**
	 * 加载招投标数据。
	 */
	this.loadBidMgrData = function(){
		this.grid.url = CTX + "/bid/getBidsPage";
		this.grid.load({sid: _prolib_id});
	};
	
	/**
	 * 招标方式。
	 */
	this.getBiddingTypeText= function(type){
		var itype= Globle.constant.iBiddingType;
		var text = "";
		for(var i = 0 ; i < itype.length; i++){
			if(type == itype[i].value) text = itype[i].text;
		}
		
		return text;
	};
	
	/**
	 * 关键字查询。
	 */
	this.queryBid = function(){
		var keyword = search.get("bidkeyword").getValue();
		this.grid.load({sid: _prolib_id, keyword : keyword});
	};
	
}

/**
 * 实例化招投标。
 */
var bid = new BidMgrHandle();

/**
 * 查看详情。
 */
function queryBidDetail(params){
	var sid = "";
	if(typeof params === 'object') sid = params.origin.record.sid;
	else sid = params;
	top.search.popDialog({ url : CTX + "/bid/gotoBidViewPage", title : "详情", width : 650, height : 500,
		onload : function(window) {
			window.instance.loadData({sid: sid});
		}, ondestroy : function(result){}
	});
}

/**
 * 绘制招投标列表单元格。
 * @param e
 */
function onBidDrawCell(e){
	var html = "";
	switch (e.column.field) {
	    case "sname":
	    	html = "<span style='color: blue;' onclick='bid.editBid(\"" + e.record.sid + "\");'>"+e.record.sname+"</span>";
	    	break;
	    case "ibiddingtype":
	    	if(e.record.ibiddingtype != null && e.record.ibiddingtype != "") html = bid.getBiddingTypeText(e.record.ibiddingtype);
	    	break;
		case "operation":
				html = '<a class="del" href="javascript:void(0);" onclick="bid.delBid(\'' + e.record.sid + '\');">删除</a>';
			break;
	}
	if(html)e.html = html;
}
/*------------------------------------项目库招投标结束-------------------------------------------*/