search.parse();
var tdg = search.get("ProjectLibsgrid");

/**
 * 重新加载页面数据
 */
function reload(){
	tdg.reload();
}
$(function(){
	tdg.url = CTX + "/projectlib/getProjectLibs";
	tdg.load();
});

//展开查询
function doQuery() {
	var devHeight = 0;
	if ($(".develop").css("display") == "none"){
		$(".develop").show();
		devHeight = $(".develop").outerHeight();
	}else{
		$(".develop").hide();
	}
	var toolHeight = $(".mini-tools").outerHeight();
	$(".mini-fit").css("top", (devHeight + toolHeight) + "px");
	search.get("dictionariesgrid").doLayout();
}

// 查询
function searchGrid() {
	var keyword = search.get("keyword").getValue();
	var param = {
			keyword : keyword
		}
	tdg.load(param);
}