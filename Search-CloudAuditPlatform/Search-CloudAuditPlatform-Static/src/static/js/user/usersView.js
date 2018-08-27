search.parse();

var grid = search.get("datagrid");

var aptitudesFile = {arr : [], index : 0}, idcardFile = {arr : [], index : 0}

// 页面加载完成执行
$(function() {
	Step.initTab();

	grid.url = CTX + "/user/findPageUsersAptitudesById";
})

// 父页面传值
function setData(data) {
	grid.load({ id : data.sid });
	$.post(CTX + "/user/findMapUsersInfoById", { id : data.sid }, function(result) {
		if (result.status) {
			// 用户信息
			var searchViews = $(".search-view");
			$.each(searchViews, function() {
				var $this = $(this);
				var key = $this.attr("id");
				var val = result.result[key];
				// 毕业时间 or 出生年月日
				if (key == "ldgraduationdate" || key == "ldbirthday") {
					val = Globle.fun.formatDate(val);
				}
				// 性别
				if (key == "igender") {
					$.each(Globle.constant.sex, function() {
						if (this.id == val) {
							val = this.text;
						}
					})
				}
				// 用户类型
				if (key == "itype") {
					$.each(Globle.constant.userType, function() {
						if (this.id == val) {
							val = this.text;
						}
					})
				}
				// 权限级别
				if (key == "ipermissionlevel") {
					$.each(Globle.constant.permissionLevel, function() {
						if (this.id == val) {
							val = this.text;
						}
					})
				}
				$this.html(val);
			})
			// 身份证
			if (result.result && result.result.idcard && result.result.idcard.length > 0) {
				$.each(result.result.idcard, function(index) {
					var $img = $('<img src="' + NETWORK_ROOT + this.spath + '">');
					$img.data(this);
					$img.data("index", index);
					$("#idCard").append($img);
					idcardFile.arr.push({sname : this.sname, spath : this.spath});
				})
				// 身份证照片点击事件
				$("#idCard").on("dblclick", "img", function() {
					var $this = $(this);
					idcardFile.index = $this.data("index");
					Globle.fun.preview(idcardFile);
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
	if (e.record.spath) {
		e.html = '<a href="javascript:void(0);" style="text-decoration: none;" onclick="aptitudesFileView(\'' + e.record.spath + '\', ' + aptitudesFileIndex + ');">资质图片</a>';
		aptitudesFileIndex++;
	} else {
		e.html = '<span style="color:#c0c0c0; cursor:default;">资质图片</span>';
	}
}

// 表格加载数据完成执行
function datagridloaded(e) {
	if (e.data.result && e.data.result.data && e.data.result.data.length > 0) {
		$.each(e.data.result.data, function() {
			if (this.spath) {
				aptitudesFile.arr.push({sname : this.sname, spath : this.spath, sdesc : this.sdesc});
			}
		})
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