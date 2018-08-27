search.parse();

function BudgetViewHandle(){
	this.form = new Form("BudgeForm");
	this._attach_list = {}, this.aptitudesFile = {arr : [], index : 0}, this.iFile = {arr : [], index : 0};
	this.loadData = function(data){
		$.post(CTX + "/budget/getBudgetViewObjBySid", { sid : data.sid }, function(result) {
			if(result.status){
				instance._attach_list = result.result;
				instance.form.setData(result.result.budget);
				
				if(result.result.sectList && result.result.sectList.length > 0){
					var sectList = result.result.sectList;
					for(var i = 0; i < sectList.length; i++){
						var tr = '<tr id="sect_tr_'+i+'">'+
							'<td>'+sectList[i].sname+'</td> ' +
							'<td>'+sectList[i].dbudgetamount+'</td> ' +
							'<td>'+sectList[i].dengineeringcost+'</td> ' +
							'<td>'+sectList[i].dcommissioncost+'</td></tr>';
						if(i == 0)$("#tr_bud").after(tr);
						else $("#sect_tr_"+ (i - 1)).after(tr);
						if(i != sectList.length -1) $("#add_"+i).hide();
						search.parse();
					}
				}
			}else top.search.error({ content : "系统错误！" });
		})
	};
	
	/**
	 * 加载附件数据。
	 */
	this.loadFileAttach = function(){
		if (this._attach_list && this._attach_list.attachList && this._attach_list.attachList.length > 0) {
			$.each(this._attach_list.attachList, function(index) {
				var $img = $('<img src="' + NETWORK_ROOT + this.spath + '">');
				$img.data(this);
				$img.data("index", index);
				$("#attach").append($img);
				instance.iFile.arr.push({sname : this.sname, spath : this.spath});
			})
			$("#attach").on("dblclick", "img", function() {
				var $this = $(this);
				instance.iFile.index = $this.data("index");
				Globle.fun.preview(instance.iFile);
			})
		}
	};
	
}

var instance = new BudgetViewHandle();

/**
 * 关闭窗口。
 */
function windowClose(){
	window.closeWindow();
}

$('#table-construction').on('click', 'li', function () {
    var $li = $(this), index = $li.index(), $constructionBd = $('#table-construction-bd').children().eq(index);
    $li.addClass('table-active').siblings().removeClass('table-active');
    $constructionBd.addClass('table-thisclass').siblings().removeClass('table-thisclass');
    if($li.index() == 1) instance.loadFileAttach();
});