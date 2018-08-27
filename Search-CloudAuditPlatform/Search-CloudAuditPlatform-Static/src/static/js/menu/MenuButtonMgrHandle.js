function MenuButtonMgrHandle(){
	this.functArr = new Array();
	this.onDrawMenuButtonData = function(id){
		$.ajax({
			url : CTX+"/home/getFuncTabOrButtonData",
			data: {sid: id, itype: 5},
			success : function(result){
				var data = result.result;
				if(data.length > 0){
					for(var i = 0; i < data.length; i++) {
						var button = data[i];
						if(button.sbtnlocation.match(RegExp(/101/))){
							var __span = $("<div id='"+button.sid+"' class='search-button blue' onclick='"+button.spcmethod+"' text='"+button.sname+"' style='margin-left:10px;'></div>");
							$("#button-id").append(__span);
						}
						if(button.sbtnlocation.match(RegExp(/102/))) butt.functArr.push(button);
					}
					search.parse();
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR.responseText, "系统提示", null);
			}
		});
	}
}

var butt = new MenuButtonMgrHandle();

$(function(){
	butt.onDrawMenuButtonData(__funcid);
});