search.parse();

var grid = search.get("datagrid");
/**
 * 页面加载。
 */
function setData(data){
	$.ajax({
		url : CTX+"/orgs/goi",
		data : {sid : data.sid},
		success : function(result){
			if (result.status) {
				// 用户信息
				var searchViews = $(".search-view");
				$.each(searchViews, function() {
					var $this = $(this);
					var key = $this.attr("id");
					var val = result.result[key];
					// 用户类型
					if (key == "itype") {
						var itype = result.result[key].split(",");
						var str = "";
						for(var i=0;i<itype.length;i++){
							$.each(Globle.constant.SpecialOrgType, function() {
								if (this.value == itype[i]) {
									if(i==0){
										str += this.text;
									}else{
										str += ","+this.text;
									}
								}
							})
						}
						val = str;
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
