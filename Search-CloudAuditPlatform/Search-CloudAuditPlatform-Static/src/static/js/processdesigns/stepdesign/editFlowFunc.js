search.parse();
var form = new Form("form");
var sfunctionid = search.get("sfunctionid");
var processid;

function sfunctionidValidate(e){
	if(e.value == -1){
		e.massage = "请选择";
		e.pass = false;
	}
}

/**
 * 页面加载。
 */
function setData(data){
	processid = data.processid;
	sfunctionid.url = CTX+"/stepdesign/gticff";
	sfunctionid.load({"isupportproject":data.isupportproject});
}
/**
 * 保存数据。
 */
function saveData(){
	if (form.validate()){
		var funcid = sfunctionid.getValue();
		$.ajax({
			url:CTX+"/stepdesign/udtff",
			data:{"processid":processid,"funcid":funcid},
			success:function(e){
				if(e.status){
					window.closeWindow({"isnext":true,"pfrefid":e.result.pfrefid,"funcid":funcid,"tables":e.result.tables});
				}else{
					search.error({content:"系统错误!"});
				}
			}
		});
	}
}
/**
 * 关闭窗口。
 */
function windowClose(){
	window.closeWindow();
}
