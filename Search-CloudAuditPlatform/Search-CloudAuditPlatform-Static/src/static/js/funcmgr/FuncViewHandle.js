search.parse();

/**
 * 控制器。
 */
function FuncViewHandle(){
	this.list = new Array();
	$.ajax({
		url : CTX+"/communal/tables",
		data : {sid : this.sid},
		success : function(result){
			instance.list = result.result;
		}
	});
	this.loadData = function(data){
		$.post(CTX + "/funcmgr/getFuncDetail", { sid : data.sid }, function(result) {
			if(result.status){
				// 用户信息
				var searchViews = $(".search-view");
				$.each(searchViews, function() {
					var $this = $(this);
					var key = $this.attr("id");
					var val = result.result[key];
					if (key == "itype") {
						$.each(Globle.constant.funcType, function() {
							if (this.value == val){
								 val = this.text;
							}
						})
					}
					if (key == "isupportphone") {
						$.each(Globle.constant.YesOrNo, function() {
							if (this.value == val){
								 val = this.text;
							}
						})
					}
					if (key == "isupportproject") {
						$.each(Globle.constant.YesOrNo, function() {
							if (this.value == val){
								 val = this.text;
							}
						})
					}
					if (key == "ijoinprocess") {
						$.each(Globle.constant.IJoinProcess, function() {
							if (this.value == val){
								 val = this.text;
							}
						})
					}
					if (key == "sjoinprocesstable") {
						if(val!=null){
							var strlist = val.split(",");
							var str = "";
							for(var i=0;i<strlist.length;i++){
								$.each(instance.list, function() {
									if (this.tableName == strlist[i]) {
										if(i==0){
											str += this.comments;
										}else{
											str += ","+this.comments;
										}
									}
								})
							}
							val = str ;
						}
					}
					$this.html(val);
				})
			}else{
				top.search.error({ content : "系统错误！" });
			}
		})
	}
}

var instance = new FuncViewHandle();

/**
 * 关闭窗口。
 */
function windowClose(){
	window.closeWindow();
}