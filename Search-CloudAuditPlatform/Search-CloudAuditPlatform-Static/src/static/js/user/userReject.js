search.parse();

var grid = search.get("datagrid");
var advanced = false;

// 页面加载完成执行
$(function() {
	if(type != 1){//管理员
		grid.url = CTX + "/user/findUsersReject";
		grid.load();
	}else{
		var orgs = search.get("orgs");
		orgs.url = CTX + "/orgs/orgaudit";
		orgs.load();
	}
})

// 格式化表格内容
function datagriddrawcell(e) {
	// 姓名
	if (e.column.field == "sname" && e.record[e.column.field]) {
		if (__state == 1) {
			if (_all_quick_map.get(e.column.field) == "") {
				e.html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>" + e.record.sname + "</span>";
			} else {
				if (_quick_map.get(e.column.field) == "") {
					e.html = "<span >" + e.record.sname + "</span>";
				} else {
					e.html = "<span style='color: blue;' onclick='" + _quick_map.get(e.column.field) + "(\"" + e.record.sid + "\");'>"
							+ e.record.sname + "</span>";
				}
			}
		} else if (__state == 2) {
			e.html = "<span style='color: blue;' onclick='queryDetail(\"" + e.record.sid + "\");'>" + e.record.sname + "</span>";
		}
	}
	// 毕业时间
	if (e.column.field == "ldgraduationdate" && e.record[e.column.field]) {
		e.html = Globle.fun.formatDate(e.record[e.column.field]);
	}
	// 出生年月日
	if (e.column.field == "ldbirthday" && e.record[e.column.field]) {
		e.html = Globle.fun.formatDate(e.record[e.column.field]);
	}
	// 性别
	if (e.column.field == "igender" && e.record[e.column.field] != "") {
		$.each(Globle.constant.sex, function() {
			if (this.id == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 用户类型
	if (e.column.field == "itype" && e.record[e.column.field] != "") {
		$.each(Globle.constant.userType, function() {
			if (this.id == e.record[e.column.field]) {
				e.html = this.text;
			}
		})
	}
	// 操作
	if (e.column.field == "oper") {
		var html = "";
		if (__state == 1) {
			if (tailButList && tailButList.length > 0) {
				$.each(tailButList, function() {
					html += ' <a class="' + this.sicon + '" href="javascript:void(0);" onclick="' + this.spcmethod + '(\'' + e.record.sid + '\');">'
							+ this.sname + '</a>'
				})
			}
		} else if (__state == 2) {
			html += '<a class="del" href="javascript:void(0);" onclick="delData(\'' + e.record.sid + '\');">删除</a>'
					+ ' <a class="btn-enable" href="javascript:void(0);" onclick="restData(\'' + e.record.sid + '\', \''+ e.record.orgstate +'\');">恢复</a>';
		}
		e.html = html;
	}
}

function onvalue(e){
	var orgs = search.get("orgs");
	if(orgs.getValue() != ""){
		grid.url = CTX + "/user/findUsersReject";
		grid.load({orgid : orgs.getValue() });
	}
}

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

// 查看详情
function queryDetail(params) {
	var id = "";
	if (typeof params === 'object')
		id = params.origin.record.sid;
	else
		id = params;
	top.search.popDialog({ url : CTX + "/user/goUsersViewPage", title : "查看用户", width : 800, height : 550, onload : function(window) {
		window.setData({ sid : id });
	}, ondestroy : function(result) {
		if (result && result != "cancel")
			grid.reload();
	} })
}

