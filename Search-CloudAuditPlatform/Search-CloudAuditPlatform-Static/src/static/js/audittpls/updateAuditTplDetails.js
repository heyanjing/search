search.parse();
form = new Form("form");
var sname = search.get("sname");
var sid = search.get("sid");
var itype = search.get("itype");
var imust = search.get("imust");
var sparentid = search.get("sparentid");
var sfiletplid = search.get("sfiletplid");
var flag = true;
var sparentidlist = new Array();
/**
 * 页面加载。
 */
function setData(data){
	sfiletplid.url = CTX+"/audittpls/qft";
	sfiletplid.load();
	imust.loadData(Globle.constant.YesOrNo);
	sparentidlist = data.sparentidlist;
	sparentid.loadData(sparentidlist);
	itype.loadData(Globle.constant.TplDetailType);
	if(data.action=="add"){
		sid.setValue(data.num);
	}
	if(data.action=="edit"){
		flag = data.flag;
		$.ajax({
			url : CTX+"/audittpls/qatdi",
			data : {sid : data.sid},
			success : function(result){
				form.setData(result.result);
			}
		});
	}
}
/**
 * 保存。
 */
function save(){
	if (!form.validate())return;
	sumbit();
}
//提交数据
function sumbit(){
	if(itype.getValue()==Globle.constant.TplDetailType[0].value){
		imust.setValue(null);
	}
	window.closeWindow(form.getData());
}
//资料类型选择触发事件
function itypechang(){
	if(itype.getValue()==Globle.constant.TplDetailType[1].value&&!flag){
		top.search.warn({ content : "该分类已有资料项，不能更改资料类型！" });
		itype.setValue(Globle.constant.TplDetailType[0].value);
	}
	if(itype.getValue()==Globle.constant.TplDetailType[1].value){
		$.each(sparentidlist, function(i,v) {
			if(sid.getValue()==v.sid){
				sparentidlist.splice(i,1);
				sparentid.loadData(sparentidlist);
				return false;
			}
		})
	}
	if(itype.getValue()==Globle.constant.TplDetailType[1].value){
		$("#imust1").show();
		$("#imust2").show();
		$("#sfiletplid1").show();
		$("#sfiletplid2").show();
		$("#sparentid1").show();
		$("#sparentid2").show();
	}else{
		$("#imust1").hide();
		$("#imust2").hide();
		$("#sfiletplid1").hide();
		$("#sfiletplid2").hide();
		$("#sparentid1").hide();
		$("#sparentid2").hide();
	}
}
/**
 * 保存数据。
 */
function saveData(){
	save();
}

/**
 * 关闭窗口。
 */
function windowClose(){
	window.closeWindow();
}
