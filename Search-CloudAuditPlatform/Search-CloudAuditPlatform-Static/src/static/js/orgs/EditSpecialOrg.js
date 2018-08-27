search.parse();
form = new Form("form");
var itype = search.get("itype");
var sid = search.get("sid");
var sparentid = search.get("sparentid");
var __orgtype = new Array();
var __orgid = "";
var __parentid = "";
var __iisdepartment = 1;
var sareaid = search.get("sareaid");
/**
 * 页面加载。
 */
function setData(data){
	$.ajax({
		url : CTX+"/orgs/gorgs",
		success : function(result){
			sparenttype = result.result;
		}
	});
	sareaid.url = CTX+"/orgs/gas";
	sareaid.load();
	
	sparentid.url = CTX+"/orgs/qpsos?sid="+data.sid;
	sparentid.load();
	sparentid.loaded = function(e){
		$.ajax({
			url : CTX+"/orgs/gobs",
			data : {sid : data.sid},
			success : function(result){
				form.setData(result.result);
				var sparent = result.result.sparent;
				var sitype = result.result.itype;
				if(__usertype != Globle.constant.userTypes[0].id){
					$.ajax({
						url : CTX+"/orgs/gui",
						success : function(result){
							__parentareaid = result.result.parentareaid;
							if(__parentareaid != ""){
								sareaid.setEnabled(false);
							}
						}
					});
				}
				sparentid.setValue(sparent);
				itype.setValue(sitype);
			}
		});
	}
}
/**
 * 保存。
 */
function save(){
	sumbit();
}
//提交数据
function sumbit(){
	$.post(CTX + "/orgs/eso",
		{
		sid : sid.getValue(),
		sname : search.get("sname").getValue(),
		sdes : search.get("sdes").getValue(),
		sparentid : sparentid.getValue(),
		itype : itype.getValue(),
		areaid : sareaid.getValue()
		},
		function(result) {
		if(result.status){
			search.info({content:result.result.isSuccess,funl:function(){
				if(result.result.state) window.closeWindow();
			}});
		}else search.error({content:"系统错误!"});
	})
}
//父级机构值改变事件
function changsparentid(){
	if(search.get("sparentid").getValue()!=""){
		var str = "";
		for(var i =0;i<sparenttype.length;i++){
			if(search.get("sparentid").getValue()==sparenttype[i].sid){
				str = sparenttype[i].sparenttype;
			}
		}
		var orgtype = str.split(",");
		var type= Globle.constant.SpecialOrgType;
		var orgitype = new Array();
		if(orgtype[0] == Globle.constant.SpecialOrgType[2].value){
			itype.loadData(Globle.constant.SpecialOrgType);
		}else{
			for(var i = 0;i<orgtype.length;i++){
				for(var j = 0 ; j < type.length; j++){
					if(orgtype[i]==type[j].value){
						orgitype.push({text:type[j].text,value:type[j].value});
					}
				}
			}
			itype.loadData(orgitype);
		}
	}else{
		itype.loadData(Globle.constant.SpecialOrgType);
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
