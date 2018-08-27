search.parse();

var grid = search.get("datagrid");
/**
 * 页面加载。
 */
function setData(data){
	/*if(__usertype != Globle.constant.userTypes[0].id){
		$.ajax({
			url : CTX+"/orgs/gui",
			success : function(result){
				__orgtype = result.result.orgtype;
				if(__orgtype[0] == Globle.constant.OrgType[0].value){
					$("#sfromorgname1").hide();
					$("#sfromorgname2").hide();
				}
			}
		});
	}else{
		$("#sfromorgname1").hide();
		$("#sfromorgname2").hide();
	}*/
	$("#sfromorgname1").hide();
	$("#sfromorgname2").hide();
	$.ajax({
		url : CTX+"/processdesigns/qpdi",
		data : {sid : data.sid},
		success : function(result){
			if (result.status) {
				var searchViews = $(".search-view");
				$.each(searchViews, function() {
					var $this = $(this);
					var key = $this.attr("id");
					var val = result.result[key];
					if (key == "isupportproject") {
						$.each(Globle.constant.YesOrNo, function() {
							if (this.value == val) {
								val = this.text;
							}
						})
					}
					$this.html(val);
				})
			} else {
				top.search.error({ content : "系统错误！" });
			}
		}
	});
}


$(function(){
	Step.initTab();
})
