// 初始化引导操作
var Step = { init : function() {
	// 默认初始化-引导操作
	return new StepObject(false).init();
}, initTab : function() {
	// 初始化类型-选项卡
	return new StepObject(true).init();
} }

// 创建引导操作
var StepObject = function(isTab, options) {
	var obj = new Object({ count : 0, stepNum : 1 });
	// 设置默认参数
	obj.isTab = typeof (isTab) == "boolean" ? isTab : false;
	options = options || {};
	options = { flowId : options.flowId || "#flowDiv", btnBack : options.btnBack || "#btnBack", btnNext : options.btnNext || "#btnNext",
		btnOk : options.btnOk || "#btnok" };
	
	// 初始化
	obj.init = function() {
		var $flowItems = $(options.flowId + " .flow-item");
		if ($flowItems && $flowItems.length > 0) {
			// 生成步骤
			obj.loadFlow();
			return obj;
		}
		return null;
	};
	
	// 生成步骤
	obj.loadFlow = function() {
		var $flowListBox = $(".flowListBox"), stepNum = 1, defaultStepNum = false;
		
		// 清空步骤
		$flowListBox.empty();
		$.each($(options.flowId + " .flow-item"), function(i, item) {
			// 生成步骤标题
			var $flowItem = $(item);
			if ($flowItem.data('hidden')) {
				return true;
			}
			// 获取标题，没有则默认第stepNum步
			var stepTitle = $flowItem.data('title') ? $flowItem.data('title') : "第" + stepNum + "步";
			// 生成步骤数DOM
			var $stepFlow = $('<div id="flow-step' + stepNum + '" class="flowList"></div>');
			var $stepNum = $('<em class="flow-step">' + stepNum + '</em>');
			var $stepTitle = $('<strong class="flow-title">' + stepTitle + '</strong>');
			$stepFlow.append($stepNum).append($stepTitle);
			$stepFlow.data('item', item);
			$stepFlow.data('index', stepNum);
			
			// 添加步骤数到容器
			$flowListBox.append($stepFlow);
			if (obj.currentItemId && obj.currentItemId == $flowItem.attr('id')) {
				obj.stepNum = stepNum;
				defaultStepNum = true;
			}
			stepNum++;
		});
		obj._layout();

		if (!defaultStepNum) {
			obj.stepNum = 1;
		}
		obj.stepClick(obj.stepNum, false);
		
		// 添加步骤点击事件
		if (obj.isTab) {
			$flowListBox.on('click', '.flowList', function() {
				var $this = $(this);
				obj.stepClick(parseInt($this.data('index')), true);
			})
		}
	};
	
	// 步骤点击处理
	obj.stepClick = function(index, event) {
		// 当前步骤
		var $currentStepFlow = $('#flow-step' + obj.stepNum);
		var $currentItemFlow = $($currentStepFlow.data('item'));
		// 预计步骤
		var $stepFlow = $('#flow-step' + index);
		var $itemFlow = $($stepFlow.data('item'));
		// 上一步骤
		var $beforeStepFlow = $('#flow-step' + (index - 1));
		var $beforeItemFlow = $($beforeStepFlow.data('item'));
		// 下一步骤
		var $afterStepFlow = $('#flow-step' + (index + 1));
		var $afterItemFlow = $($afterStepFlow.data('item'));
		
		// 即将离开当前步骤
		if (event && obj.callEvent($currentItemFlow.data('viewwillleave')) == false) {
			return false;
		}
		// 即将进入预计步骤
		if (event && obj.callEvent($itemFlow.data('viewwillenter')) == false) {
			return false;
		}
		// 当前步骤
		obj.stepNum = index;
		// 移除所有步骤样式
		$("div[id^='flow-step']").removeClass("flow-step-current flow-step-finish");
		if (obj.isTab) {
			// Tab选项卡
			if (index == 1) {
				// 第一步
				// 隐藏上一步按钮
				if ($(options.btnBack) && $(options.btnBack).length > 0) {
					$(options.btnBack).hide();
				}
				// 显示下一步和保存按钮
				$(options.btnNext + ", " + options.btnOk).show();
			} else if (index == obj.count) {
				// 最后一步
				// 隐藏下一步按钮
				$(options.btnNext).hide();
				// 显示上一步和保存按钮
				$(options.btnBack + ", " + options.btnOk).show();
			} else {
				// 其他
				// 显示所有按钮
				$(options.btnBack + ", " + options.btnOk + ", " + options.btnNext).show();
			}
			// 设置当前步骤样式
			$stepFlow.addClass("flow-step-finish");
			// 设置当前步骤之后步骤样式
			$("div[id^='flow-step']:gt(" + (parseInt($stepFlow.data('index')) - 1) + ")").addClass("flow-step-current");
		} else {
			// 引导步骤
			if (index == 1) {
				// 第一步
				// 隐藏上一步和保存按钮
				$(options.btnBack + ", " + options.btnOk).hide();
				// 显示下一步按钮
				$(options.btnNext).show();
			} else if (index == obj.count) {
				// 最后一步
				// 显示上一步和保存按钮
				$(options.btnBack + ", " + options.btnOk).show();
				// 隐藏下一步按钮
				$(options.btnNext).hide();
			} else {
				// 其他
				// 显示上一步和下一步按钮
				$(options.btnBack + ", " + options.btnNext).show();
				// 隐藏保存按钮
				$(options.btnOk).hide();
			}
			// 设置当前步骤样式
			$stepFlow.addClass("flow-step-current");
			// 设置当前步骤之后步骤样式
			$("div[id^='flow-step']:lt(" + (parseInt($stepFlow.data('index')) - 1) + ")").addClass("flow-step-finish");
		}
		// 设置步骤可见内容
		$itemFlow.removeClass("contentList");
		$itemFlow.siblings().addClass("contentList");
		
		obj.currentItemId = $itemFlow.attr('id');
		// 已经离开当前步骤
		if (event && obj.callEvent($currentItemFlow.data('viewdidleave')) == false) {
			return false;
		}
		// 已经进入预计步骤
		if (event && obj.callEvent($itemFlow.data('viewdidenter')) == false) {
			return false;
		}
	};
	
	// 执行事件
	obj.callEvent = function(eventname) {
		if (eventname) {
			var res = eval(eventname);
			if (res instanceof Function) {
				return res.call(this);
			}
		}
		return true;
	};
	
	// 上一步
	$(options.btnBack).click(function(e) {
		obj.stepClick(obj.stepNum - 1, true);
	});
	
	// 下一步
	$(options.btnNext).click(function(e) {
		obj.stepClick(obj.stepNum + 1, true);
	});
	
	// 显示步骤
	obj.showStep = function(fields) {
		if (fields instanceof Array) {
			for (var i = 0; i < fields.length; i++) {
				$(fields[i]).data('hidden', false);
			}
		} else if (typeof fields == 'string') {
			$(fields).data('hidden', false);
		} else {
			$(options.flowId + " .flow-item").data('hidden', false);
		}
		obj.loadFlow();
	}
	
	// 隐藏步骤
	obj.hideStep = function(fields) {
		if (fields instanceof Array) {
			for (var i = 0; i < fields.length; i++) {
				$(fields[i]).data('hidden', true);
			}
		} else if (typeof fields == 'string') {
			$(fields).data('hidden', true);
		} else {
			$(options.flowId + " .flow-item").data('hidden', true);
		}
		obj.loadFlow();
	}
	
	// 调整布局
	obj._layout = function() {
		var $flowList = $('.flowListBox .flowList');
		obj.count = $flowList.length;
		// 调整步骤的宽度占比
		var part = parseInt(100 / obj.count) + "%";
		$flowList.css("width", part);
	}
	return obj;
}