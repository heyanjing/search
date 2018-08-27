search.parse();

var grid = search.get("datagrid");
var idcardFile = {arr : [], index : 0}
var filelist = new Array();
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
						if(val != Globle.constant.OrgType[0].value){
							$("#org1").show();
							$("#org2").show();
						}else{
							$("#org1").hide();
							$("#org2").hide();
						}
						var itype = result.result[key].split(",");
						var str = "";
						for(var i=0;i<itype.length;i++){
							$.each(Globle.constant.OrgType, function() {
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
					// 是否部门
					if (key == "iisdepartment") {
						if(val == Globle.constant.YesOrNo[1].value){
							$("#user1").show();
							$("#user2").show();
							$("#phone1").show();
							$("#phone2").show();
						}else{
							$("#user1").hide();
							$("#user2").hide();
							$("#phone1").hide();
							$("#phone2").hide();
						}
						$.each(Globle.constant.YesOrNo, function() {
							if (this.value == val) {
								val = this.text;
							}
						})
					}
					
					$this.html(val);
				})
				// 身份证
				if (result.result && result.result.attch1 && result.result.attch1.length > 0) {
					$.each(result.result.attch1, function(index) {
						var $img = $('<img width="200" src="' + NETWORK_ROOT + this.spath + '">');
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
				grid.loadData(result.result.attch2);
				$.each(result.result.attch2, function() {
					filelist.push({sname : this.sname, spath : this.spath, sdesc : this.sdesc});
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


// 绘制表格
var aptitudesFileIndex = 0;
function datagriddrawcell(e) {
	// 操作
	if (e.column.field == "oper") {
		e.html = '<a href="javascript:void(0);" style="color: blue;" onclick="aptitudesFileView(' + aptitudesFileIndex + ');">资质图片</a>';
		aptitudesFileIndex++;
	}
}

// 查看资质文件
function aptitudesFileView(index) {
	var aptitudesFile = {arr : [], index : 0};
	aptitudesFile.index = 0;
	aptitudesFile.arr.push({sname : filelist[index].sname, spath : filelist[index].spath, sdesc : filelist[index].sdesc});
	Globle.fun.preview(aptitudesFile);
}

// 重绘表格
function reDrawDatagrid() {
	grid.doLayout();
}