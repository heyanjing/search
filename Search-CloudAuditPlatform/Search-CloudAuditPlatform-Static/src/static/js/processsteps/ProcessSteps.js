var _stepsGrid, _usersGrid, _opinionForm, _opinion, _resultData = {}, _parentData = {};

var $historyOpinionField = $('#StepHistoryOpinionField'), $opinionField = $('#StepOpinionField'), $processStep = $("#StepProcessStep");

var _initcallback;

// 页面加载完成执行
$(function() {
	search.parse();
	_stepsGrid = search.get("StepStepsGrid");
	_usersGrid = search.get("StepUsersGrid");
	_opinionForm = new Form("StepOpinionForm");
	_opinion = search.get("StepOpinion");
	
	_opinion.loadData([ { id : 1, text : "同意" }, { id : 2, text : "驳回" } ]);
})

// 查询步骤
function getProcessSteps(data) {
	_stepsGrid.url = CTX + "/processsteps/qps";
	_stepsGrid.load(data);
}

// 查询步骤执行人
function getUsers(e) {
	if (e.value == null || e.value == "") {
		_usersGrid.loadData(new Array());
		return;
	}
	_usersGrid.url = CTX + "/processsteps/qubpsi";
	_usersGrid.load({ processstepsid : _stepsGrid.getValue() });
}

// 父页面传值
function setDatas(data, callback) {
	if (callback && typeof callback == 'function') {
		_initcallback = callback;
	}
	_parentData = data;
	if (data.processinstancesid) {
		$.post(CTX + "/processsteps/findOpinionDetailsByInstancesid", { instancesid : data.processinstancesid }, function(result) {
			if (result.result.isupportopinion == 2) {
				$opinionField.hide(), $historyOpinionField.hide();
			} else {
				var html = "";
				var htmlArr = new Array();
				$.each(result.result.processinstancenodes, function() {
					if (this.sresult) {
						var resultname = (this.sresult == 2 ? "回退" : "通过");
						var opinion = this.sname + " " + Globle.fun.format(this.ldtcreatetime) + "<br>" + resultname + " " + (this.sdesc || '');
						htmlArr.push(opinion);
					}
				})
				$historyOpinionField.find('div').html(htmlArr.join('<br>'));
			}
			_resultData = $.extend(_resultData, {resultData : result});
			getProcessSteps(data);
		})
	} else {
		$opinionField.hide(), $historyOpinionField.hide();
		getProcessSteps(data);
	}
}

// 绘制步骤表格
function processstepsdrawcell(e) {
	var html = "";
	switch (e.column.field) {
		case "sname":
			html = "<span style='color: blue;'>" + e.record.sname + "</span>";
			break;
	}
	if (html)
		e.html = html;
}

// 绘制步骤执行人表格
function usersdrawcell(e) {

}

// 步骤表格加载完成执行
function processstepsloaded(e) {
	_resultData = $.extend(_resultData, {statuscode : e.data.code});
	if (e.data.code == 100001) {
		// 最后一步
		$processStep.hide();
		if (_initcallback && typeof _initcallback == 'function') {
			_initcallback.apply(this, [_resultData]);
		}
		return;
	}
	if (e.data.result.length > 0) {
		// 默认选中第一个
		_stepsGrid.setValue(e.data.result[0].sid);
		if (e.data.code == 100002) {
			_resultData = $.extend(_resultData, {sprocessdesignid : e.data.result[0].sprocessdesignid});
		}
	}
	if (_initcallback && typeof _initcallback == 'function') {
		_initcallback.apply(this, [_resultData]);
	}
}

// 获取页面数据
function getDatas() {
	var formdata = _opinionForm.getData();
	return { sdesc : formdata.StepSdesc, opinion : formdata.StepOpinion, processstepsid : _stepsGrid.getValue(), 
		stepoperatorsid : _usersGrid.getValue(), sprocessdesignid : _resultData.sprocessdesignid};
}

// 回退步骤
function rollbackStep(callback) {
	$.post(CTX + "/processsteps/updateRollbackStep", { sprocessinstanceid : _parentData.processinstancesid }, function(result) {
		if (!result.status) {
			top.search.error({ content : "系统错误！" });
		} else {
			if (callback && typeof callback == 'function') {
				callback.apply(null, [result]);
			}
		}
	})
}