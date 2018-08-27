search.parse();
grid = search.get("orgGrid");
var sname = search.get("sname");
function OrgsMgrHandle(){
	grid.url = CTX + "/orgs/getorgs";
	grid.load({sname:sname.getValue()});
}

/**
 * 初始化查询。
 * @returns
 */
function setData(){
	OrgsMgrHandle();
}

function save(){
	var id = "";
	var select = grid.getSelected();
	if(select){
		id = select.record.sid;
	}
	window.closeWindow(id);
}

/**
 * 绘制单元格。
 * @param e
 * @returns
 */
function ondrawcell(e){
	var html = "";
	switch (e.column.field) {
		case "itype":
			var text = "";
			var orgtype = e.record.itype.split(",");
			var type= Globle.constant.OrgType;
			var orgitype = "";
			for(var i = 0;i<orgtype.length;i++){
				for(var j = 0 ; j < type.length; j++){
					if(orgtype[i]==type[j].value){
						if(i==0){
							orgitype += type[j].text;
						}else{
							orgitype += ","+type[j].text;
						}
					}
				}
			}
//			for(var i = 0 ; i < itype.length; i++){
//				if(e.record.itype == itype[i].value) text = itype[i].text;
//			}
			html = orgitype;
			break;
	}
	if(html)e.html = html;
}


