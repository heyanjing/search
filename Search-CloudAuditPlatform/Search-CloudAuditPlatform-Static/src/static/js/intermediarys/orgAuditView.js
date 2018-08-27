search.parse();

var grid = search.get("datagrid");

var aptitudesFile = {arr : [], index : 0}, businessFile = {arr : [], index : 0}

// 页面加载完成执行
$(function() {
	Step.initTab();
})

// 父页面传值
function setData(data) {
	$.post(CTX + "/intermediarys/findOrgDetailsById", { id : data.sid }, function(result) {
		if (result.status) {
			var $searchViews = $(".search-view");
			$.each($searchViews, function() {
				var $this = $(this);
				var field = $this.attr("id");
				if (field == "itype" && result.result[field]) {
					var itypeArr = result.result[field].split(",");
					var itypeNameArr = new Array();
					$.each(itypeArr, function(index, value) {
						$.each(Globle.constant.OrgType, function() {
							if (this.value == value) {
								itypeNameArr.push(this.text);
							}
						})
					})
					$this.html(itypeNameArr.join(","));
				} else {
					$this.html(result.result[field]);
				}
			})
			
			// 营业执照
			if (result.result && result.result.business && result.result.business.length > 0) {
				$.each(result.result.business, function(index) {
					var $img = $('<img src="' + NETWORK_ROOT + this.spath + '">');
					$img.data(this);
					$img.data("index", index);
					$("#business").append($img);
					businessFile.arr.push({sname : this.sname, spath : this.spath});
				})
				// 营业执照点击事件
				$("#business").on("dblclick", "img", function() {
					var $this = $(this);
					businessFile.index = $this.data("index");
					Globle.fun.preview(businessFile);
				})
			}
			
			// 机构资质
			if (result.result && result.result.aptitudes && result.result.aptitudes.length > 0) {
				grid.loadData(result.result.aptitudes);
				$.each(result.result.aptitudes, function() {
					if (this.spath) {
						aptitudesFile.arr.push({sname : this.sname, spath : this.spath, sdesc : this.sdesc});
					}
				})
			}
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}

// 绘制表格
var aptitudesFileIndex = 0;
function datagriddrawcell(e) {
	// 操作
	if (e.column.field == "oper") {
		if (e.record.spath) {
			e.html = '<a href="javascript:void(0);" style="text-decoration: none;" onclick="aptitudesFileView(\'' + e.record.spath + '\', ' + aptitudesFileIndex + ');">资质图片</a>';
			aptitudesFileIndex++;
		} else {
			e.html = '<span style="color:#c0c0c0; cursor:default;">资质图片</span>';
		}
	}
}

// 查看资质文件
function aptitudesFileView(path, index) {
	aptitudesFile.index = index;
	Globle.fun.preview(aptitudesFile);
}

// 重绘表格
function reDrawDatagrid() {
	grid.doLayout();
}