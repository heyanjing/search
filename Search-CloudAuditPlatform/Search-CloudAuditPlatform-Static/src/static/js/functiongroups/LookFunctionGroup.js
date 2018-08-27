search.parse();

var grid = search.get("datagrid");
var aptitudesFile = {arr : [], index : 0}, idcardFile = {arr : [], index : 0}
/**
 * 页面加载。
 */
function setData(data){
	$.ajax({
		url : CTX+"/functiongroups/gfgibi",
		data : {sid : data.sid},
		success : function(result){
			if (result.status) {
				// 用户信息
				var searchViews = $(".search-view");
				$.each(searchViews, function() {
					var $this = $(this);
					var key = $this.attr("id");
					var val = result.result[key];
					// 项目有关
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
