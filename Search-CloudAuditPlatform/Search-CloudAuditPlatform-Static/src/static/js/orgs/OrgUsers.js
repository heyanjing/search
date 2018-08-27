search.parse();
var grid = search.get("datagrid");

function setData(data){
	$.ajax({
		url : CTX+"/orgs/gom?orgid="+data.sid,
		success : function(result){
			var flag = result.result.flag;
			search.remove("datagrid",true);
  			var str = "<div property='columns'>";
  			str += '<div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>'+
            '<div field="username" width="80" headAlign="center" textAlign="left" allowSort="true">姓名</div>'+
            '<div field="orgname" width="200" headAlign="center" textAlign="left" allowSort="true">所属机构</div>'+
            '<div field="sphone" width="200" headAlign="center" textAlign="left" allowSort="true">联系电话</div>'+
            '<div field="iisdepartment" width="100" headAlign="center" textAlign="center" allowSort="true">是否部门</div>'+
            '<div field="smanagerid" width="100" headAlign="center" textAlign="center" allowSort="true">用户类型</div>';
			if(flag){
				str += '<div field="oper" width="150" headAlign="center" textAlign="center" allowSort="true">操作</div>';
			}
			str += "</div>"
  	      	$("#datagrid").empty().append($(str));
  	      	search.parse();
  	      	grid = search.get("datagrid");
  	      	grid.url = CTX + "/orgs/gous";
  	      	grid.load({orgid:data.sid});
		}
	});
}
// 格式化表格内容
function datagriddrawcell(e) {
	var html = "";
	switch (e.column.field) {
		case "iisdepartment":
			var itype= Globle.constant.YesOrNo;
			var text = "";
			for(var i = 0 ; i < itype.length; i++){
				if(e.record.iisdepartment == itype[i].value) text = itype[i].text;
			}
			html = text;
			break;
		case "smanagerid":
			if(e.record.smanagerid == null){
				html = "普通用户";
			}else{
				html = "管理员";
			}
			break;
		case "oper":
			if(e.record.smanagerid == null){
				html = "<a class='edit' href='javascript:void(0)' onclick='ismanager(\""+e.record.sid+"\",\""+e.record.sorgid+"\","+e.record.iisdepartment+",\""+e.record.smanagerid+"\");' style='margin-left:5px;'>升为管理员</a>";
			}
			break;
	}
	if(html)e.html = html;
}

//升为管理员
function ismanager(sid,sorgid,iisdepartment,smanagerid) {
	if (iisdepartment==Globle.constant.YesOrNo[0].value) {
		top.search.warn({ content : "部门用户无法升为管理员！" });
		return;
	}
	if(smanagerid!="null"){
		top.search.warn({ content : "该用户已经是管理员！" });
		return;
	}
	top.search.confirm({ content : "确定将该用户升为管理员？", funl : function() {
		$.post(CTX + "/orgs/uuim", { sid : sid, orgid : sorgid }, function(result) {
			if(result.status){
				search.info({content:result.result.isSuccess,funl:function(){
					grid.reload();
				}});
			}else search.error({content:"系统错误!"});
		})
	} })
}
