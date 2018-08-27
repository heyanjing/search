search.parse();

function AdjustmenHistoryHandle(){
	this.grid = search.get("historyGrid");
	this.sdataid = "";
	
	this.loadHistoryData = function(data) {
		this.sdataid = data.sid;
		this.grid.url = CTX + "/projectlib/getAdjustmenHistoryPage";
		this.grid.load({sdataid: data.sid});
	};
}

var instance = new AdjustmenHistoryHandle();

/**
 * 关键字查询。
 */
function queryHistory(){
	var keyword = search.get("keyword").getValue();
	instance.grid.load({sdataid: instance.sdataid, keyword : keyword});
}