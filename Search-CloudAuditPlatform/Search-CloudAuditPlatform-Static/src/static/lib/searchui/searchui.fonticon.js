var search = new function() {
	//私有属性
	this._controls = {};
	this._loadingBox = null;
	this._manualLoadingBox = false;
	
	/**
	 * 窗口大小变化事件处理方法。
	 */
	this._resized = function() {
		for (var key in this._controls) {
			this._controls[key].resized();
		}
	}
	
	/**
	 * 显示提示框
	 * @param params 对话框的参数显示
	 * @param type 提示框的类型
	 */
	this._showPrompt = function(params, type) {
		var title = (params && params.title) ? params.title : "提示";
		var content = (params && params.content) ? params.content : "";
		var btnl = (params && params.btnl) ? params.btnl : "确认";
		var btnr = (params && params.btnr) ? params.btnr : "取消";
		var container = (params && params.container) ? params.container : $(document.body);
		var zindex = nextZindex(container);
		
		var icon = null;
		switch(type) {
			case "info":
				icon = "fa-exclamation-circle";
				break;
			case "warn":
				icon = "fa-exclamation-triangle";
				break;
			case "error":
				icon = "fa-times-circle";
				break;
			case "confirm":
				icon = "fa-check-circle";
				break;
			default:
				break;
		}
		
		var cancel = "";
		if(type === "confirm"){
			cancel = "<input property='cancel' class='cancel' type='button' value='" + btnr + "' style='display:none;'/>";
		}
		
		var startLeft = container.width() / 2, startTop = container.height() / 2;
		var endLeft = (container.width() - 300) / 2, endTop = (container.height() - 150) / 2;
		
		var html =	"<div class='search_message_container' style='z-index: " + zindex + ";'>" + 
					"<div class='search_message_mask'></div>" + 
					"<div class='search_prompt_box' style='left:" + startLeft + "px;top:" + startTop + "px;width:0;height:0;'>" + 
					"<div class='search-body' style='display:none;'><span><i class='fa " + icon + "'/><p>" + content + "</p></span></div>" + 
					"<input property='OK' class='information' type='button' value='" + btnl + "' style='display:none;'/>" + 
					cancel + 
					"</div>" + 
					"</div>";
		
		var dialog = $(html);
		var box = dialog.find("div.search_prompt_box"), body = dialog.find("div.search-body"), button = dialog.find("input");
		dialog.find("span[property='close']").click(function() {
			dialog.close();
			if(params && params.funr){
				params.funr.call(this);
			}
		});
		dialog.find("input[property='OK']").click(function() {
			dialog.close();
			if(params && params.funl){
				params.funl.call(this); 
			}
		});
		dialog.find("input[property='cancel']").click(function(){
			dialog.close();
			if(params && params.funr){
				params.funr.call(this);
			}
		});
		dialog.close = function() {
			body.hide();
			button.hide();
			
			box.animate({ left : startLeft, top : startTop, width : 0, height : 0 }, "normal", null, function() {
				dialog.remove();
			});
		};
		
		container.append(dialog);
		box.animate({ left : endLeft, top : endTop, width : 300, height : 150 }, "normal", null, function() {
			body.show();
			button.show();
		});
	}
	
	/**
	 * 显示信息提示框
	 * @param params 对话框的参数显示
	 */
	this.info = function(params) {
		this._showPrompt(params, "info");
	}
	
	/**
	 * 显示警告提示框
	 * @param params 对话框的参数显示
	 */
	this.warn = function(params) {
		this._showPrompt(params, "warn");
	}
	
	/**
	 * 显示错误提示框
	 * @param params 对话框的参数显示
	 */
	this.error = function(params) {
		this._showPrompt(params, "error");
	}
	
	/**
	 * 显示确认提示框
	 * @param params 对话框的参数显示
	 */
	this.confirm = function(params) {
		this._showPrompt(params, "confirm");
	}
	
	/**
	 * 显示加载提示框
	 */
	this.showLoading = function(params) {
		if (this._loadingBox) return;
		
		var content = (params && params.content) ? params.content : "正在加载中...";
		var container = (params && params.container) ? params.container : $(window.top.document.body);
		var zindex = nextZindex(container);
		
		var left = (container.width() - 32) / 2;
		var top = (container.height() - 32) / 2;
		
		var html = "<div class='search_message_container' style='z-index: " + zindex + ";'>" + 
				   "<div class='search_message_mask'></div>" + 
				   "<div class = 'search_loading_box' style='left:" + left + "px;top:" + top + "px;'>" + 
				   "<span><span class = 'search_loading loading'/><p>" + content + "</p></span>" + 
				   "</div>" + 
				   "</div>";
		
		this._loadingBox = $(html);
		container.append(this._loadingBox);
	}
	
	/**
	 * 关闭加载提示框
	 */
	this.closeLoading = function() {
		if (!this._loadingBox) return;
		if (this._manualLoadingBox) return;
		
		//检查是否手动显示加载提示框且不显示加载提示框
		if (typeof(this._loadingBox) != "boolean") this._loadingBox.remove();
		
		this._loadingBox = null;
	}
	
	/**
	 * 手动显示加载提示框。
	 * @param notShow 不显示加载提示框。
	 */
	this.manualShowLoading = function(notShow) {
		this._manualLoadingBox = true;
		if (notShow) this._loadingBox = true;
		else this.showLoading();
	}
	
	/**
	 * 手动关闭加载提示框。
	 */
	this.manualCloseLoading = function() {
		this._manualLoadingBox = false;
		this.closeLoading();
	}
	
	/**
	 * 显示对话框
	 * @param params 对话框的参数
	 */
	this.popDialog = function(params) {
		var id = (params && params.id) ? (" id = '" + params.id + "' name = '" + params.id + "'") : "";
		var title = (params && params.title) ? params.title : "对话框";
		var url = (params && params.url) ? params.url : "about:blank";
		var width = (params && params.width) ? params.width : "800";
		var height = (params && params.height) ? params.height : "600";
		var modal = (params && typeof(params.modal) === "boolean") ? params.modal : true;
		var container = (params && params.container) ? params.container : $(document.body);
		var zindex = nextZindex(container);
		
		var startLeft = container.width() / 2, startTop = container.height() / 2;
		var endLeft = (container.width() - width) / 2, endTop = (container.height() - height) / 2;
		
		var titleHtml = "";
		var ifHeight = height;
		var noRadius = "noradius";
		if (modal) {
			titleHtml = "<div class='title'>" + 
						"<h1 style='width:" + (width - 50) + "px;'>" + title + "</h1><i class='button size fa fa-window-maximize'></i><i class='button close fa fa-close'></i>" + 
						"</div>";
			ifHeight -= 41;
			noRadius = "";
		}
		
		var html =  "<div class='search_dialog_container' style='z-index: " + zindex + ";'>" + 
					"<div class='search_dialog_mask'></div>" + 
					"<div class='search_dialog_box " + noRadius + "' style='width:0;height:0;left:" + startLeft + "px;top:" + startTop +"px;'>" + 
					titleHtml + 
					"<iframe" + id + " class='cont " + noRadius + "' style='width:" + width + "px;height:" + ifHeight + "px;' border='0' frameborder='no'></iframe>" +
					"</div>" + 
					"</div>";
		
		var dialog = $(html);
		var title = (modal) ? dialog.find("div.title") : null;
		var iframe = dialog.find("iframe");
		var box = dialog.children("div.search_dialog_box");
		var doc = $(container.get(0).ownerDocument);
		
		//缓存对话框位置
		dialog.size = {pos : {left : startLeft, top : startTop}};
		
		if (modal) {
			//对话框移动事件
			dialog.find("h1").bind({
				mousedown : function(e) {
					var os = $(this).offset();
					var dx = e.pageX - os.left, dy = e.pageY - os.top;
					doc.on("mousemove.drag", function(e) {
						var start = box.position();
						
						box.offset({left : e.pageX - dx, top : e.pageY - dy});
						
						var pos = box.position();
						
						if (pos.left < 0) box.css("left", 0);
						else if (pos.left > container.width() - width) box.css("left", container.width() - width);
						
						if (pos.top < 0) box.css("top", 0);
						else if (pos.top > container.height() - height) box.css("top", container.height() - height);
						
						//计算并缓存对话框移动后的位置
						var end = box.position(), dl = end.left - start.left, dt = end.top - start.top;
						dialog.size.pos.left += dl, dialog.size.pos.top += dt;
					});
				}, 
				mouseup : function(e) {
					doc.off("mousemove.drag");
				}
			});
		}
		
		if (modal) {
			//对话框大小按钮点击事件
			dialog.find("i.size").bind({
				click : function() {
					var sender = $(this), cls = sender.attr("class");
					if (cls.containsClass("fa-window-maximize")) {			//最大化
						//修改按钮样式
						sender.removeClass("fa-window-maximize").addClass("fa-window-restore");
						
						//缓存对话框原始大小
						var pos = box.position();
						dialog.size.box = {left : pos.left, top : pos.top, width : box.width(), height : box.height()};
						dialog.size.iframe = {width : iframe.width(), height : iframe.height()};
						
						//改变对话框大小
						var containerw = container.width(), containerh = container.height();
						box.animate({ left : 0, top : 0, width : containerw, height : containerh }, "normal", null, function() {
							iframe.css({width : containerw, height : containerh - 41});
						});
					} else if (cls.containsClass("fa-window-restore")) {	//还原
						//修改按钮样式
						sender.removeClass("fa-window-restore").addClass("fa-window-maximize");
						
						//还原对话框大小
						box.animate({ left : dialog.size.box.left, top : dialog.size.box.top, width : dialog.size.box.width, height : dialog.size.box.height }, "normal", null, function() {
							iframe.css({width : dialog.size.iframe.width, height : dialog.size.iframe.height});
						});
					}
				}
			});
			
			//对话框关闭按钮点击事件
			dialog.find("i.close").bind({
				click : function() {
					var window = iframe.get(0).contentWindow;
					window.onCloseClick();
				}
			});
		}
		
		//对话框加载完成事件
		iframe.bind({
			load : function() {
				//忽略默认空页面加载
				if (!this.src) return;
				
				var window = this.contentWindow;
				
				//添加关闭按钮点击事件方法
				if (!window.onCloseClick) {
					window.onCloseClick = function() {
						window.closeWindow("cancel");
					}
				}
				
				//添加关闭窗口方法
				window.closeWindow = function(result) {
					dialog.close();
					
					//调用销毁完成回调方法
					if (params && params.ondestroy) {
						params.ondestroy.call(this, result);
					}
				}
				
				//调用加载完成回调方法
				if (params && params.onload) {
					window.waitForAjax(this, params.onload, window);
				}
			}
		});
		
		//对话框关闭方法
		dialog.close = function() {
			if (title) title.hide();
			iframe.hide();
			
			box.animate({ left : dialog.size.pos.left, top : dialog.size.pos.top, width : 0, height : 0 }, "normal", null, function() {
				iframe.remove();
				dialog.remove();
			});
		};
		
		container.append(dialog);
		box.animate({ left : endLeft, top : endTop, width : width, height : height }, "normal", null, function() {
			iframe.attr("src", url);
		});
	}
	
	/**
	 * 关闭上下文菜单。
	 * @param 事件发送者。
	 */
	this.closeContextMenu = function(sender) {
		var menu = $("div.search-contextmenu");
		if (menu.length <= 0) {
			var iframes = $("iframe");
			for (var index = 0; index < iframes.length; index ++) {
				var window = iframes[index].contentWindow;
				if (window.search) window.search.closeContextMenu(sender);
			}
			return;
		}
		
		if (sender) {
			var objs = sender.parents();
			for (var index = 0; index < objs.length; index ++) {
				var cls = $(objs[index]).attr("class");
				if (cls && cls.containsClass("search-contextmenu")) {
					return;
				}
			}
		}
		
		menu.remove();
	}
	
	/**
	 * 解析。
	 */
	this.parse = function() {
		var exists = {};
		var frame = this;
		
		$("div").each(function(index, element) {
			var e = $(element);
			var id = e.attr("id");
			var cls = e.attr("class");
			
			//检查控件有效性
			if (!id || !cls || !cls.startWith("search")) return;
			
			//记录存在的控件
			exists[id] = id;
			
			//控件已存在
			if (frame._controls[id]) return;
			
			var ctl = null;
			if (cls.containsClass("search-datagrid")) {
				//创建数据表格
				ctl = new Datagrid();
			} else if (cls.containsClass("search-groupdatagrid")) {
				//创建分组数据表格
				ctl = new GroupDatagrid();
			} else if (cls.containsClass("search-treedatagrid")) {
				//创建树形数据表格
				ctl = new TreeDatagrid();
			} else if (cls.containsClass("search-tree")) {
				//创建树形
				ctl = new Tree();
			} else if (cls.containsClass("search-select")){
				//创建下拉列表
				ctl = new Select();
			} else if (cls.containsClass("search-treeselect")){
				//创建树形下拉列表
				ctl = new TreeSelect();
			} else if (cls.containsClass("search-textbox")){
				//创建单行文本框
				ctl = new Textbox();
			} else if (cls.containsClass("search-textarea")){
				//创建多行文本框
				ctl = new Textarea();
			} else if (cls.containsClass("search-textpassword")){
				//创建密码框
				ctl = new Textpassword();
			} else if (cls.containsClass("search-textradio")){
				//创建单选按钮
				ctl = new Textradio();
			} else if (cls.containsClass("search-textcheck")){
				//创建复选框
				ctl = new Textcheck();
			} else if (cls.containsClass("search-texthide")){
				//创建隐藏
				ctl = new Texthide();
			} else if (cls.containsClass("search-datepicker")) {
				//创建日期选取器
				ctl = new Datepicker();
			} else if (cls.containsClass("search-button")){
				//创建按钮
				ctl = new Button();
			} else if (cls.containsClass("search-label")) {
				//创建标签
				ctl = new Label();
			}
			
			//初始化控件
			if (ctl && ctl.initialize(e)) {
				frame._controls[id] = ctl;
				if (ctl.url) ctl.load();
			}
		});
		
		//删除页面不存在的控件
		for (var key in this._controls) {
			if (!exists[key]) delete this._controls[key];
		}
	}
	
	/**
	 * 获取控件对象。
	 * @param ctlid 控件标识。
	 */
	this.get = function(ctlid) {
		return this._controls[ctlid];
	}
	
	/**
	 * 删除控件。
	 * @param ctlid 控件标识。
	 * @param reserve 是否保留HTML代码。
	 */
	this.remove = function(ctlid, reserve) {
		var ctl = this._controls[ctlid];
		if (!ctl) return;
		
		if (!reserve) ctl.control.remove();
		
		delete this._controls[ctlid];
	}
	
	/**
	 * 删除所有控件。
	 * @param excludes 要排除删除的控件ID。
	 */
	this.removeAll = function(excludes) {
		for (var key in this._controls) {
			if (excludes && excludes.length > 0 && $.inArray(key, excludes) != -1) continue;
			
			var ctl = this._controls[key];
			if (!ctl) continue;
			
			ctl.control.remove();
			delete this._controls[key];
		}
	}
	
	/**
	 * 表单验证规则。
	 */
	var validateRules = {
		required : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				e.message = "不允许为空";
				e.pass = false;
			}
		}, 
		number : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (!/^(\+|\-)?(0|([1-9]\d*))(\.\d+)?$/.test(e.value)) {
				e.message = "必须是数字";
				e.pass = false;
			}
		}, 
		int : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (!/^(\-|\+)?(0|([1-9]\d*))$/.test(e.value)) {
				e.message = "必须是整数";
				e.pass = false;
				return;
			}
			
			if (param) {
				var array = param.split("-");
				var min = (array.length > 0) ? parseInt(array[0]) : null, max = (array.length > 1) ? parseInt(array[1]) : null, temp = parseInt(e.value);
				if (min != null && temp < min && max == null) {
					e.message = "必须是大于等于" + min + "的整数";
					e.pass = false;
				} else if ((min != null && temp < min) || (max != null && temp > max)) {
					e.message = "必须是大于等于" + min + "且小于等于" + max + "的整数";
					e.pass = false;
				}
			}
		}, 
		float : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (!/^(\+|\-)?(0|([1-9]\d*))(\.\d+)$/.test(e.value)) {
				e.message = "必须是小数";
				e.pass = false;
			}
		}, 
		money : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (!/^(0|([1-9]\d*))(\.\d{1,2})?$/.test(e.value)) {
				e.message = "无效的金额";
				e.pass = false;
			}
		}, 
		nonzeromoney : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (!/^0(\.\d{1,2})$|^([1-9]\d*)(\.\d{1,2})?$/.test(e.value)) {
				e.message = "无效的金额";
				e.pass = false;
			}
		}, 
		millionmoney : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (!/^(0|([1-9]\d*))(\.\d{1,6})?$/.test(e.value)) {
				e.message = "无效的金额";
				e.pass = false;
			}
		}, 
		twofloat : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (!/^(\+|\-)?(0|([1-9]\d*))(\.\d{1,2})?$/.test(e.value)) {
				e.message = "不能超过两位小数";
				e.pass = false;
			}
		}, 
		threefloat : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (!/^(\+|\-)?(0|([1-9]\d*))(\.\d{1,3})?$/.test(e.value)) {
				e.message = "不能超过三位小数";
				e.pass = false;
			}
		}, 
		mobile : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if(!/^1[34578]\d{9}$/.test(e.value)){
				e.message = "无效的手机号码";
				e.pass = false;
			}
		}, 
		email : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(e.value)){
				e.message = "请输入有效的邮箱";
				e.pass = false;
			}
		}, 
		telephone : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (!/^(0\d{2,3}-?)?\d{7,8}$/.test(e.value)) {
				e.message = "无效的座机号码";
				e.pass = false;
			}
		}, 
		date : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (param) {
				var regExp = new RegExp("^" + param.replace(/Y/g, "\\d{1}").replace(/M/g, "\\d{1}").replace(/D/g, "\\d{1}").replace(/H/g, "\\d{1}").replace(/S/g, "\\d{1}") + "$");
				if (!regExp.test(e.value)) {
					e.message = "日期时间格式不正确";
					e.pass = false;
				}
			}
		}, 
		length : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if (param) {
				var array = param.split("-");
				var top = (array.length > 1) ? parseInt(array[1]) : parseInt(array[0]);
				var bottom = (array.length > 1) ? parseInt(array[0]) : 0 ;
				
				var length = e.value.length;
				if (length < bottom || length > top) {
					if (bottom <= 0) {
						e.message = "不能超过" + top + "个字符";
					} else {
						e.message = "必须为" + bottom + "到" + top + "个字符";
					}
					e.pass = false;
				}
			}
		}, 
		name : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if(!/^([\u4E00-\u9FA5\uF900-\uFA2D]+|[a-zA-Z\.\s]+)$/.test(e.value)){
				e.message = "无效的人名";
				e.pass = false;
			}
		}, 
		postcode : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if(!/^[1-9]\d{5}$/.test(e.value)){
				e.message = "无效的邮政编码";
				e.pass = false;
			}
		}, 
		phonenumber : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if(!/^(0\d{2,3}-?)?\d{7,8}$|^1[34578]\d{9}$/.test(e.value)){
				e.message = "无效的电话号码";
				e.pass = false;
			}
		}, 
		bankaccount : function(e, param) {
			if (typeof(e.value) === "undefined" || e.value == null || e.value === "") {
				return;
			}
			
			if(!/^\d+$/.test(e.value)){
				e.message = "无效的银行账号";
				e.pass = false;
			}
		}
	};
	
	/**
	 * 判断一个对象是否是数组，是数组对象返回true，否则返回false。
	 * @param obj 待检查对象。
	 */
	function isArray(obj) {
		if (Array.isArray) {
			return Array.isArray(obj);
		} else {
			return Object.prototype.toString.call(obj) === "[object Array]";
		}
	}
	
	/**
	 * 复制对象。
	 * @param src 源对象。
	 * @param des 目标对象。
	 */
	function copyObject(src, des) {
		for (var key in src) {
			if (isArray(src[key])) {
				des[key] = [];
				copyArray(src[key], des[key]);
				continue;
			}
			
			if (typeof(src[key]) === "object") {
				des[key] = {};
				copyObject(src[key], des[key]);
			} else {
				des[key] = src[key];
			}
		}
	}
	
	/**
	 * 复制数组。
	 * @param src 源数组。
	 * @param des 目标数组。
	 */
	function copyArray(src, des) {
		for (var index = 0; index < src.length; index ++) {
			var item = src[index];
			if (isArray(item)) {
				des[index] = [];
				copyArray(item, des[index]);
				continue;
			}
			
			if (typeof(item) === "object") {
				des[index] = {};
				copyObject(item, des[index]);
			} else {
				des[index] = item;
			}
		}
	}
	
	/**
	 * 查找方法。
	 * @param name 方法名。
	 */
	function findMethod(name) {
		try {
			if (name) {
				var method = eval(name);
				if (typeof(method) === "function") return method;
				
				console.log(name + " is not method.");
			}
		} catch (e) {
			console.log(e);
		}
		
		return null;
	}
	
	/**
	 * 获取属性值。
	 * @param jqobj JQuery对象。
	 * @param name 属性名。
	 * @param defvalue 默认值。
	 * @returns 属性值，属性值无效时返回默认值。
	 */
	function getAttrValue(jqobj, name, defvalue) {
		var value = jqobj.attr(name);
		if (typeof(value) === "undefined" || !value) return defvalue;
		return value;
	}
	
	/**
	 * 获取Boolean属性值。
	 * @param jqobj JQuery对象。
	 * @param name 属性名。
	 * @param defvalue 默认值。
	 * @returns Boolean属性值，属性为true时返回true，否则返回false。
	 */
	function getBooleanAttrValue(jqobj, name, defvalue) {
		var value = jqobj.attr(name);
		if (typeof(value) === "undefined") return defvalue;
		return value.toBoolean();
	}
	
	/**
	 * 获取Integer属性值。
	 * @param jqobj JQuery对象。
	 * @param name 属性名。
	 * @param defvalue 默认值。
	 * @returns Integer属性值，属性为有效数字时返回相应整数值，否则返回默认值。
	 */
	function getIntegerAttrValue(jqobj, name, defvalue) {
		var value = jqobj.attr(name);
		if (typeof(value) === "undefined") return defvalue;
		
		value = parseInt(value);
		if (isNaN(value)) return defvalue;
		
		return value;
	}
	
	/**
	 * 判断当前浏览器是否是IE浏览器。
	 * @returns 如果是IE浏览器返回true，否则返回false。
	 */
	function isMsie() {
		return /msie/.test(navigator.userAgent.toLowerCase());
	}
	
	/**
	 * 判断当前浏览器是否为IE8浏览器。
	 * @returns 如果是IE8浏览器返回true，否则返回false。
	 */
	function isMsie8() {
		if (isMsie()) {
			var avi = navigator.appVersion.split(";");
			var version = avi[1].replace(/[ ]/g, "");
			if (version === "MSIE8.0") return true;
		}
		return false;
	}
	
	/**
	 * 获取指定容器中下一个Z-INDEX值（最小为100）。
	 * @param container 容器对象。
	 */
	function nextZindex(container) {
		var zindexs = $.map(container.find("*"), function (e, n) {
			var obj = $(e);
	        if (obj.css("position") == "absolute"){
	        	var temp = parseInt(obj.css("z-index"));
	            if (!isNaN(temp)) return temp;
	        }
		});
		zindexs.push(99);
		
		var zindex = Math.max.apply(null, zindexs);
		return zindex + 1;
	}
	
	/**
	 * 检查顶级项。
	 * @param list 数据集合。
	 * @param id ID名称。
	 * @param pid PID名称。
	 */
	function checkTopItem(list, id, pid) {
		for (var i = 0; i < list.length; i ++) {
			var item = list[i];
			if (item[pid] == null) continue;
			
			var exists = false;
			for (var j = 0; j < list.length; j ++) {
				if (item[pid] == list[j][id]) {
					exists = true;
					break;
				}
			}
			
			if (!exists) item[pid] = null;
		}
	}
	
	/**
	 * 计算百分比。
	 * @param size 大小。
	 * @param parent 父级大小。
	 */
	function calculatePercent(size, parent) {
		var index = size.indexOf("%");
		if (index == -1) return size;
		
		var scale = parseFloat(size.substring(0, index)) / 100.0;
		size = (parent * scale).toString(); 
		
		return size;
	}
	
	//绑定窗口大小变化事件
	var invoker = this;
	$(window).resize(function(){ invoker._resized.call(invoker); });
	
	/******************** 控件定义开始 ********************/
	
	/**
	 * 控件构造方法。
	 * @param parent 父级控件。
	 */
	function Control(parent) {
		//公共属性
		this.id = null;
		this.rules = null;
		this.enabled = true;
		this.editabled = true;
		this.displayabled = true;
		this.parent = parent;
		this.control = null;
		
		//私有属性
		this._validateActive = true;
		this._contextMenu = null;
		
		//事件
		this.onvalidate = null;
	}
	
	/**
	 * 初始化。
	 * @param jqobj JQuery对象。
	 * @returns 初始化结果，正常完成为true，否则为false。
	 */
	Control.prototype.initialize = function(jqobj) {
		//保存控件jQuery对象
		this.control = jqobj;
		
		//初始化属性
		this.id = getAttrValue(jqobj, "id", this.id);
		this.rules = getAttrValue(jqobj, "rules", this.rules);
		this.enabled = getBooleanAttrValue(jqobj, "enabled", this.enabled);
		this.editabled = getBooleanAttrValue(jqobj,"editabled",this.editabled);
		this.displayabled = getBooleanAttrValue(jqobj,"displayabled",this.displayabled);
		
		//初始化上下文菜单
		var cmobj = jqobj.children("div[property='contextmenu']");
		if (cmobj.length > 0) {
			var cm = new ContextMenu(this);
			if (cm.initialize(cmobj)) this._contextMenu = cm;
			
			//清除定义
			cmobj.remove();
		}
		
		//初始化事件
		this.onvalidate = findMethod(jqobj.attr("onvalidate"));
		
		return true;
	}
	
	/**
	 * 控件大小改变回调方法。
	 */
	Control.prototype.resized = function() { }
	
	/**
	 * 控件值验证。
	 * @returns 验证通过时返回true，否则返回false。
	 */
	Control.prototype.validate = function() {
		var e = new ValidateEventArgs(this.getValue());
		
		if (this.onvalidate) {
			this.onvalidate.call(this, e);
			this.validated(e);
		} else {
			if (this.rules) {
				var array = this.rules.split(",");
				for (var index = 0; index < array.length; index ++) {
					var param = null;
					var name = array[index];
					
					var pos = name.indexOf(":");
					if (pos != -1) {
						var temp = name;
						name = temp.substring(0, pos);
						param = temp.substring(pos + 1);
					}
					
					var rule = validateRules[name];
					if (!rule) continue;
					
					
					rule.call(this, e, param);
					this.validated(e);
					
					if (!e.pass) break;
				}
			}
		}
		
		return e.pass;
	}
	
	/**
	 * 控件验证完成。
	 * @param e 验证事件参数对象。
	 */
	Control.prototype.validated = function(e) { }
	
	/**
	 * 获取控件启用状态。
	 * @returns 控件启用状态。
	 */
	Control.prototype.getEnabled = function() {
		return this.enabled;
	}
	
	/**
	 * 设置控件启用状态。
	 * @param enabled 控件启用状态值。
	 * @returns 控件启用状态是否有变化。
	 */
	Control.prototype.setEnabled = function(enabled) {
		if (this.enabled == enabled) return false;
		
		this.enabled = enabled;
		return true;
	}
	
	/**
	 * 获取控件可编辑状态。
	 * @returns 控件可编辑状态。
	 */
	Control.prototype.getEditabled = function() {
		return this.editabled;
	}
	
	/**
	 * 设置控件可编辑状态。
	 * @param enabled 控件可编辑状态值。
	 * @returns 控件可编辑状态是否有变化。
	 */
	Control.prototype.setEditabled = function(editabled){
		if(this.editabled == editabled) return false;
		
		this.editabled = editabled;
		return true;
	}
	
	/**
	 * 获取控件显示状态。
	 * @returns 控件显示状态。
	 */
	Control.prototype.getDisplayabled = function() {
		return this.displayabled;
	}
	
	/**
	 * 设置控件显示状态。
	 * @param displayabled 控件显示状态值。
	 * @returns 控件显示状态是否有变化。
	 */
	Control.prototype.setDisplayabled = function(displayabled) {
		if (this.displayabled == displayabled) return false;
		
		this.displayabled = displayabled;
		return true;
	}
	
	/**
	 * 激活验证。
	 * @param active 激活状态。
	 */
	Control.prototype.activateValidate = function(active) {
		this._validateActive = active;
	}
	
	/**
	 * 跳过验证。
	 * @returns 验证处于激活状态时返回false，否则返回true。
	 */
	Control.prototype.skipValidate = function() {
		return (!this._validateActive);
	}
	
	/**
	 * 显示对比值
	 * @param showvalue 显示值
	 */
	Control.prototype.setContrastValue = function(showvalue) { }
	
	/**
	 * 检查控件是否有上下文菜单。
	 * @returns 控件是否有上下文菜单。
	 */
	Control.prototype.hasContextMenu = function() {
		return (!(!this._contextMenu));
	}
	
	/**
	 * 添加上下文菜单项。
	 * @param id 编号。
	 * @param type 类型。
	 * @param text 文本。
	 * @param icon 图标。
	 * @param data 数据。
	 * @param enabled 是否启用。
	 * @param displayabled 是否显示。
	 * @param click 点击事件处理方法。
	 */
	Control.prototype.addContextMenuItem = function(id, type, text, icon, data, enabled, displayabled, click) {
		//验证编号和类型
		if (!id || !/^(menu|separater)$/.test(type)) return;
		
		//创建上下文菜单
		if (!this._contextMenu) this._contextMenu = new ContextMenu(this);
		
		//创建上下文菜单项
		var item = new ContextMenuItem(this._contextMenu);
		item.id = id;
		item.type = type;
		item.text = text;
		item.icon = icon;
		item.data = data;
		item.enabled = enabled;
		item.displayabled = displayabled;
		item.click = findMethod(click);
		this._contextMenu.items.push(item);
	}
	
	/**
	 * 弹出上下文菜单。
	 * @param pos 显示位置。
	 * @param html HTML对象。
	 * @param origin 事件源。
	 */
	Control.prototype.popContextMenu = function(pos, html, origin) {
		this._contextMenu.create(pos, html, origin);
	}
	
	/**
	 * 获取自定义数据。
	 * @param key 数据的键。
	 * @returns 自定义数据。
	 */
	Control.prototype.getCustomData = function(key) {
		if (!key || !this.control) return null;
		
		return this.control.attr(key);
	}
	
	/******************** 控件定义结束 ********************/
	
	/******************** 上下文菜单定义开始 ********************/
	
	/**
	 * 上下文菜单构造方法。
	 * @param parent 父级控件。
	 */
	function ContextMenu(parent) {
		Control.call(this, parent);
		
		//公共属性
		this.items = new Array();
	}
	
	/**
	 * 继承父类。
	 */
	ContextMenu.prototype = new Control(null);
	ContextMenu.prototype.constructor = ContextMenu;
	
	/**
	 * @see Control#initialize(any)
	 */
	ContextMenu.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//查找菜单项配置
		var mobjs = jqobj.children("div");
		if (mobjs.length <= 0) return false;
		
		//初始化菜单项
		for (var index = 0; index < mobjs.length; index ++) {
			var mobj = $(mobjs[index]);
			
			var item = new ContextMenuItem(this);
			if (item.initialize(mobj)) this.items.push(item);
		}
		if (this.items.length <= 0) return false;
		
		return true;
	}
	
	/**
	 * 创建上下文菜单。
	 * @param pos 显示位置。
	 * @param html HTML对象。
	 * @param origin 事件源。
	 */
	ContextMenu.prototype.create = function(pos, html, origin) {
		var scrollTop = $(document).scrollTop();
		
		//创建菜单
		var mhtml = "<div id='contextmenu$" + this.parent.id + "' class='search-contextmenu' style='top: " + (pos.top + scrollTop) + "px; left: " + pos.left + "px;'>" + 
					"<div class='panel'><ul></ul></div>" + 
					"</div>";
		var menu = $(mhtml);
		
		//创建菜单项
		var items = menu.find("ul"), count = 0;
		for (var index = 0; index < this.items.length; index ++) {
			var item = this.items[index].create(html, origin);
			if (!item) continue;
			
			items.append(item);
			count ++;
		}
		if (count <= 0) return;
		
		$(document.body).append(menu);
		
		//计算高度并设置滚动样式
		var winWidth = $(window).width(), winHeight = $(window).height(), menuWidth = menu.outerWidth(), menuHeight = winHeight, maxHeight = items.outerHeight() + 10;
		if (maxHeight <= menuHeight) menuHeight = maxHeight;
		else items.find("span.separater").addClass("narrow");
		
		//重新定位
		if (pos.left + menuWidth > winWidth) menu.css("left", winWidth - menuWidth);
		if (pos.top + menuHeight > winHeight) menu.css("top", winHeight - menuHeight + scrollTop);
		
		//动画显示
		menu.children("div.panel").animate({ height : menuHeight - 10 }, "normal", null, null);
	}
	
	/******************** 上下文菜单定义结束 ********************/
	
	/******************** 上下文菜单项定义开始 ********************/
	
	/**
	 * 上下文菜单项构造方法。
	 * @param parent 父级控件。
	 */
	function ContextMenuItem(parent) {
		Control.call(this, parent);
		
		//公共属性
		this.type = "";
		this.text = "";
		this.icon = "";
		this.autoDisable = "false";
		this.autoConceal = "false";
		this.data = null;
		
		//事件
		this.click = null;
	}
	
	/**
	 * 继承父类。
	 */
	ContextMenuItem.prototype = new Control(null);
	ContextMenuItem.prototype.constructor = ContextMenuItem;
	
	/**
	 * @see Control#initialize(any)
	 */
	ContextMenuItem.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//初始化并验证菜单类型
		this.type = getAttrValue(jqobj, "type", this.type);
		if (!/^(menu|separater)$/.test(this.type)) return false;
		
		if (this.type === "menu") {
			//初始化菜单属性
			this.text = getAttrValue(jqobj, "text", this.text);
			this.icon = getAttrValue(jqobj, "icon", this.icon);
			this.autoDisable = getAttrValue(jqobj, "autoDisable", this.autoDisable);
			this.autoConceal = getAttrValue(jqobj, "autoConceal", this.autoConceal);
			
			//解析自定义数据
			var data = getAttrValue(jqobj, "data", null);
			if (data) this.data = eval("(" + data + ")");
			
			//初始化事件
			this.click = findMethod(jqobj.attr("onclick"));
		}
		
		return true;
	}
	
	/**
	 * 创建菜单项。
	 * @param html HTML对象。
	 * @param origin 事件源。
	 * @returns 菜单项对象。
	 */
	ContextMenuItem.prototype.create = function(html, origin) {
		var ismenu = (this.type === "menu");
		
		if (ismenu) {
			//检查菜单项显示状态
			if (!this.displayabled) return null;
			
			//检查菜单是否自动隐藏
			var rst = eval(this.autoConceal.replace("{record}", "origin.record"));
			if (typeof(rst) === "boolean" && rst) return null;
		}
		
		//检查菜单启用状态
		var clsname = null, clickable = false;
		if (ismenu) {
			if (this.enabled) {
				var rst = eval(this.autoDisable.replace("{record}", "origin.record"));
				if (typeof(rst) === "boolean" && rst) clsname = " disable";
				else {
					clsname = " enable";
					clickable = true;
				}
			} else clsname = " disable";
		} else clsname = " separater";
		
		//创建菜单项
		var menu = $("<li id='menuitem$" + this.id + "' class='item" + clsname + "' title='" + this.text + "'></li>");
		if (ismenu) {
			menu.append($("<i class='" + this.icon + "'></i><span class='menu'>" + this.text + "</span>"));
			
			//绑定鼠标悬停事件
			menu.hover(
				function(e) {
					$(this).addClass("hover");
				}, 
				function(e) {
					$(this).removeClass("hover");
				}
			);
			
			//绑定鼠标点击事件
			if (clickable && this.click) {
				var invoker = this, cmcea = new ContextMenuClickEventArgs(html, origin, this.data);
				menu.click(function(e) {
					invoker.click.call(invoker, cmcea);
					search.closeContextMenu(null);
				});
			}
		} else menu.append($("<span class='separater'></span>"));
		
		return menu;
	}
	
	/******************** 上下文菜单项定义结束 ********************/
	
	/******************** 数据表格定义开始 ********************/
	
	/**
	 * 数据表格构造方法。
	 */
	function Datagrid() {
		Control.call(this, null);
		
		//公共属性
		this.url = null;
		this.idField = null;
		this.showCheckBox = false;
		this.multiSelect = false;
		this.alternatingRows = true;
		this.selectedRows = true;
		this.allowRowSelect = true;
		this.showPager = true;
		this.pageIndex = 0;
		this.pageSize = 50;
		this.sortField = null;
		this.sortOrder = null;
		this.rows = new Array();
		this.columns = new Array();
		
		//私有属性
		this._head = null;
		this._scroll = null;
		this._body = null;
		this._foot = null;
		this._pager = null;
		this._allCheck = null;
		this._customParams = null;
		this._requestParams = null;
		this._data = null;
		this._tempSelecteds = null;
		this._headView = null;
		this._bodyView = null;
		
		//事件
		this.loaded = null;
		this.drawcell = null;
		this.selectchanged = null;
		this.onrowloaded = null;
	}
	
	/**
	 * 继承父类。
	 */
	Datagrid.prototype = new Control(null);
	Datagrid.prototype.constructor = Datagrid;
	
	/**
	 * 布局。
	 */
	Datagrid.prototype.doLayout = function() {
		if (this._bodyView) {
			var pagerHeight = 1;
			if (this.showPager) pagerHeight = this._foot.outerHeight();
			
			//计算并设置表格身体高度
			var bodyHeight = this.control.height() - this._headView.outerHeight() - pagerHeight;
			this._bodyView.height(bodyHeight);
			
			//检查滚动条
			this.checkScroller();
		}
	}
	
	/**
	 * 初始化表格框架。
	 * @param jqobj JQuery对象。
	 */
	Datagrid.prototype._initGridFrame = function(jqobj) {
		//创建表格头部
		var head = "<div class='head-view'>" + 
				   "<table border='0' cellspacing='0' cellpadding='0'><thead></thead></table>" + 
				   "</div>";
		var dghead = $(head);
		jqobj.append(dghead);
		this._head = dghead.find("thead");
		
		//创建表格滚动
		this._scroll = $("<div class='scroll-view'></div>");
		jqobj.append(this._scroll);
		
		//创建表格身体
		var border = (this.showPager) ? "" : " body-view-border";
		var body = "<div class='body-view" + border + "'>" + 
				   "<table border='0' cellspacing='0' cellpadding='0'><tbody></tbody></table>" + 
				   "</div>";
		var dgbody = $(body);
		dgbody.scroll(function(){ dghead.scrollLeft(dgbody.scrollLeft()); });
		jqobj.append(dgbody);
		this._body = dgbody.find("tbody");
		
		//创建表格脚部
		if (this.showPager) {
			this._foot = $("<div class='foot-view'></div>");
			jqobj.append(this._foot);
		}
	}
	
	/**
	 * 请求数据。
	 */
	Datagrid.prototype._requestData = function() {
		var invoker = this;
		
		//发起异步请求
		$.ajax({
			url : this.url,
			data : this._requestParams,
			success : function(data, status) {
				if (data.status) {
					invoker._renderData(data.result);
					invoker._checkCachedSelecteds();
				} else {
					search.info({content : data.result});
				}
				
				//调用加载完成事件
				if (invoker.loaded) {
					var lea = new LoadedEventArgs(data);
					invoker.loaded.call(this, lea);
				}
			},
			error : function(xhr, msg, e) {
				console.log("status:" + xhr.status, "statusText:" + xhr.statusText);
			}
		});
	}
	
	/**
	 * 渲染数据。
	 * @param result 请求结果对象。
	 * @param ignoreCache 是否忽略缓存数据。
	 */
	Datagrid.prototype._renderData = function(result, ignoreCache) {
		//设置分页
		if (this.showPager) this._pager.set(result.total);
		
		//清空原有数据
		this._body.empty();
		if (this.rows.length > 0) this.rows = new Array();
		
		var records = (result.data) ? result.data : result;
		if (records.length == 0) this._initEmptyGrid();
		else {
			for (var ri = 0; ri < records.length; ri ++) {
				var record = records[ri];
				
				//生成数据行
				var bodyrow = $("<tr></tr>");
				for (var ci = 0; ci < this.columns.length; ci ++) {
					var column = this.columns[ci];
					
					//生成单元格HTML
					var html = null;
					var value = record[column.field];
					if (typeof(value) === "undefined" || value == null) value = "";
					
					//自定义单元格绘制
					if (this.drawcell) {
						var dea = new DrawcellEventArgs(record, column);
						this.drawcell.call(this, dea);
						html = dea.html;
					}
					
					if (html == null) html = value;
					if (typeof(html) === "boolean") html = html.toString();
					
					//计算序号
					var order = ri;
					if (this.showPager) order = this.pageIndex * this.pageSize + ri;
					
					//创建数据单元格
					bodyrow.append(column.genDataCell(order, html));
				}
				
				//设置奇偶行样式
				if (this.alternatingRows && ri % 2 > 0) bodyrow.addClass("alternating");
				
				//保存数据行
				var row = new DatagridRow(this, ri, record);
				row.initialize(bodyrow);
				this.rows.push(row);
				
				//处理表格行的加载事件
				if (this.onrowloaded) {
					var position = (ri == 0) ? ((ri == records.length - 1) ? "one" : "first") : ((ri == records.length - 1) ? "last" : "middle");
					var rlea = new RowLoadedEventArgs(row, bodyrow, position);
					this.onrowloaded.call(this, rlea);
				}
				
				this._body.append(bodyrow);
			}
		}
		
		//检查滚动条
		this.checkScroller();
		
		//缓存数据
		if (!ignoreCache) this._data = result;
	}
	
	/**
	 * 初始化空表格。
	 */
	Datagrid.prototype._initEmptyGrid = function() {
		//设置表格身体宽度和高度
		this._body.parent().width(this._head.parent().outerWidth());
		this._body.parent().height(1);
	}
	
	/**
	 * 全选状态改变事件处理方法。
	 */
	Datagrid.prototype._allCheckChanged = function() {
		var checked = this._allCheck.checked;
		for (var index = 0; index < this.rows.length; index ++) {
			var row = this.rows[index];
			if (checked) {
				if (!row.selected) row.select();
			} else {
				if (row.selected) row.deselect();
			}
		}
	}
	
	/**
	 * 检查是否所有行都选中。
	 * @param rows 行集合。
	 * @returns 所有行都选中则返回true，否则返回false。
	 */
	Datagrid.prototype._isAllChecked = function(rows) {
		var checkall = true;
		for (var index = 0; index < rows.length; index ++) {
			if (!this.rows[index].selected) {
				checkall = false;
				break;
			}
		}
		return checkall;
	}
	
	/**
	 * 初始化属性。
	 * @param jqobj JQuery对象。
	 */
	Datagrid.prototype._initProperty = function(jqobj) {
		//初始化属性
		this.url = getAttrValue(jqobj, "url", this.url);
		this.idField = getAttrValue(jqobj, "idField", this.idField);
		this.showCheckBox = getBooleanAttrValue(jqobj, "showCheckBox", this.showCheckBox);
		this.multiSelect = getBooleanAttrValue(jqobj, "multiSelect", this.multiSelect);
		this.alternatingRows = getBooleanAttrValue(jqobj, "alternatingRows", this.alternatingRows);
		this.selectedRows = getBooleanAttrValue(jqobj, "selectedRows", this.selectedRows);
		this.allowRowSelect = getBooleanAttrValue(jqobj, "allowRowSelect", this.allowRowSelect);
		this.showPager = getBooleanAttrValue(jqobj, "showPager", this.showPager);
		this.pageSize = getIntegerAttrValue(jqobj, "pageSize", this.pageSize);
	}
	
	/**
	 * 初始化表头单元格。
	 * @param headrow 表头行对象。
	 * @param column 列对象。
	 * @param coldef 列定义。
	 * @returns 单元格宽度。
	 */
	Datagrid.prototype._initHeadCell = function(headrow, column, coldef) {
		var width = 0;
		
		//创建表头单元格
		var cell = column.genHeadCell(coldef.html());
		if (cell) {
			width = cell.width();
			headrow.append(cell);
		}
		
		return width;
	}
	
	/**
	 * 取消所有选中。
	 */
	Datagrid.prototype._deselectAll = function() {
		for (var index = 0; index < this.rows.length; index ++) {
			var row = this.rows[index];
			if (row.selected) row.deselect();
		}
	}
	
	/**
	 * 初始化表头行。
	 * @param level 行层级。
	 * @param coldefs 列定义集合。
	 * @param hiderow 隐藏行对象（用于解决table-layout为fixed、第一行有合并单元格时，其下级单元格设置宽度无效的问题）。
	 * @returns 上级行列信息对象。
	 */
	Datagrid.prototype._initHeadRow = function(level, coldefs, hiderow) {
		if (!hiderow) {
			//创建隐藏行对象
			hiderow = $("<tr level='-1'></tr>");
			this._head.append(hiderow);
		}
		
		//查找或创建当前层级行对象
		var headrow = this._head.children("tr[level='" + level + "']");
		if (headrow.length <= 0) {
			headrow = $("<tr level='" + level + "'></tr>");
			this._head.append(headrow);
		}
		
		var width = 0;
		var colspan = 0;
		var rowspan = 1;
		
		for (var index = 0; index < coldefs.length; index ++) {
			var coldef = $(coldefs[index]);
			
			if (coldef.attr("property") === "group") {
				//读取分组列高度
				var height = coldef.attr("headHeight");
				if (typeof(height) === "undefined" || height == null) height = "28px";
				
				//读取分组列文本
				var text = coldef.attr("text");
				if (typeof(text) === "undefined" || text == null) text = "";
				
				//读取分组列对齐方式
				var align = coldef.attr("headAlign");
				if (typeof(align) === "undefined" || align == null || align === "") align = "center";
				
				//初始化下级行
				var children = coldef.children("div");
				var obj = this._initHeadRow(level + 1, children, hiderow);
				
				//累计列宽和列数
				width += obj.width;
				colspan += obj.colspan;
				
				//保存最大行数
				if (obj.rowspan > rowspan) rowspan = obj.rowspan;
				
				//创建分组单元格
				var group = $("<td property='group' style='width: " + obj.width + "px; text-align: " + align + ";'>" + text + "</td>");
				if (obj.colspan > 1) group.attr("colspan", obj.colspan);
				group.attr("title", group.text());
				group.css("height", height);
				headrow.append(group);
			} else {
				//创建列
				var column = new DatagridColumn(this);
				if (column.initialize(coldef)) {
					//保存列并创建数据单元格
					this.columns.push(column);
					var hcw = this._initHeadCell(headrow, column, coldef);
					
					//隐藏行添加单元格
					if (hcw > 0) hiderow.append($("<td style='width: " + hcw + "px; height: 0px; border-top: none; border-bottom: none;'></td>"));
					
					//累计列宽和列数
					width += hcw;
					colspan ++;
				}
			}
		}
		
		//计算并设置数据单元格跨越行数
		var temp = parseInt(headrow.children("td[property='group']").attr("growspan"));
		if (!isNaN(temp) && temp > rowspan) rowspan = temp;
		if (rowspan > 1) {
			headrow.children("td[property='group']").attr("growspan", rowspan);
			headrow.children("td[property='data']").attr("rowspan", rowspan);
		}
		
		//组装列信息对象并返回
		var colinfo = { width : width, colspan : colspan, rowspan : rowspan + 1 };
		return colinfo;
	}
	
	/**
	 * 缓存选中行。
	 */
	Datagrid.prototype._cacheSelecteds = function() {
		var selecteds = this.getSelecteds();
		if (selecteds.length > 0) {
			this._tempSelecteds = new Array();
			for (var i = 0; i < selecteds.length; i ++) {
				this._tempSelecteds.push(selecteds[i].record[this.idField]);
			}
		}
	}
	
	/**
	 * 检查已缓存的选中行。
	 */
	Datagrid.prototype._checkCachedSelecteds = function() {
		if (this._tempSelecteds) {
			this._recoverySelecteds(this._tempSelecteds);
			this._tempSelecteds = null;
		}
	}
	
	/**
	 * 恢复行选中。
	 * @param selecteds 选中行集合。
	 */
	Datagrid.prototype._recoverySelecteds = function(selecteds) {
		var index = 0;
		for (var i = 0; i < selecteds.length; i ++) {
			var id = selecteds[i];
			
			for (; index < this.rows.length; index ++) {
				var row = this.rows[index];
				
				if (row.record[this.idField] === id) {
					row.select();
					index ++;
					break;
				}
			}
		}
		
		this.checkSelectChange(false);	//检查选中改变
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	Datagrid.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//初始化属性
		this._initProperty(jqobj);
		
		//查找列定义
		var colset = jqobj.children("div[property='columns']");
		if (colset.length > 0) {
			var coldefs = colset.children("div");
			if (coldefs.length > 0) {
				this._initGridFrame(jqobj);
				
				//初始化表头行并获取表头高度
				this._initHeadRow(0, coldefs, null);
				this._headView = this._head.parent().parent();
				var headHeight = this._headView.outerHeight();
				
				//多选时查找全选元素并绑定点击事件
				if (this.showCheckBox && this.multiSelect) {
					var checkobj = this._head.find("input[property='check']");
					this._allCheck = checkobj.get(0);
					
					var invoker = this;
					checkobj.bind({click : function(e) { invoker._allCheckChanged.call(invoker); }});
				}
				
				//清除原有定义
				colset.remove();
				
				var pagerHeight = 1;
				if (this.showPager) {
					//初始化分页控件
					this._pager = new DatagridPager(this);
					this._pager.initialize(this._foot);
					
					//获取分页高度
					pagerHeight = this._foot.outerHeight();
				}
				
				//保存表格身体jQuery对象
				this._bodyView = this._body.parent().parent();
				
				//计算并设置表格身体及滚动区域高度
				var bodyHeight = jqobj.height() - headHeight - pagerHeight;
				this._bodyView.height(bodyHeight);
				this._scroll.height(headHeight - 2);
			}
		}
		
		//初始化事件
		this.loaded = findMethod(jqobj.attr("onloaded"));
		this.drawcell = findMethod(jqobj.attr("ondrawcell"));
		this.selectchanged = findMethod(jqobj.attr("onselectchanged"));
		this.onrowloaded = findMethod(jqobj.attr("onrowloaded"));
		
		return true;
	}
	
	/**
	 * 检查滚动条。
	 */
	Datagrid.prototype.checkScroller = function() {
		//检查有无垂直滚动条
		var hasScroller = false;
		var bodyView = this._body.prevObject;
		if (bodyView.scrollTop() > 0) {
			hasScroller = true;
		} else {
			bodyView.scrollTop(1);
			if (bodyView.scrollTop() > 0) {
				hasScroller = true;
				bodyView.scrollTop(0);
			}
		}
		
		//修改表头样式
		var headView = this._head.prevObject;
		var total = headView.parent().width();
		if (hasScroller) {
			headView.css("width", total - 19);
			headView.css("float", "left");
			this._scroll.css("display", "block");
		} else {
			headView.css("width", "");
			headView.css("float", "");
			this._scroll.css("display", "none");
		}
	}
	
	/**
	 * @see Control#resized()
	 */
	Datagrid.prototype.resized = function() {
		Control.prototype.resized.call(this);
		
		//检查滚动条
		this.checkScroller();
	}
	
	/**
	 * 加载。
	 * @param params 自定义参数。
	 */
	Datagrid.prototype.load = function(params) {
		if (params) {
			//缓存自定义参数
			this._customParams = {};
			copyObject(params, this._customParams);
		}
		
		//创建请求参数
		this._requestParams = {
			pageIndex : (this.showPager) ? this._pager.pageIndex : 0,
			pageSize : this.pageSize,
			sortField : this.sortField,
			sortOrder : this.sortOrder
		};
		
		//复制自定义参数
		if (this._customParams) copyObject(this._customParams, this._requestParams);
		
		//请求数据
		if (this.url) this._requestData();
	}
	
	/**
	 * 重新加载。
	 */
	Datagrid.prototype.reload = function() {
		this._cacheSelecteds();
		this._requestData();
	}
	
	/**
	 * 加载数据。
	 * @param data 数据对象。
	 */
	Datagrid.prototype.loadData = function(data) {
		this._renderData(data);
		
		//调用加载完成事件
		if (this.loaded) {
			var lea = new LoadedEventArgs(data);
			this.loaded.call(this, lea);
		}
	}
	
	/**
	 * 获取选中行。
	 */
	Datagrid.prototype.getSelected = function() {
		var selected = null;
		for (var index = 0; index < this.rows.length; index ++) {
			if (this.rows[index].selected) {
				selected = this.rows[index];
				break;
			}
		}
		return selected;
	}
	
	/**
	 * 获取所有选中行。
	 */
	Datagrid.prototype.getSelecteds = function() {
		var selecteds = new Array();
		for (var index = 0; index < this.rows.length; index ++) {
			if (this.rows[index].selected) selecteds.push(this.rows[index]);
		}
		return selecteds;
	}
	
	/**
	 * 获取数据表格的值。
	 * @returns 数据表格的值。
	 */
	Datagrid.prototype.getValue = function() {
		var value = "";
		
		for (var index = 0; index < this.rows.length; index ++) {
			var row = this.rows[index];
			if (!row.selected) continue;
			
			if (!this.showCheckBox || !this.multiSelect) return row.record[this.idField];
			value += row.record[this.idField] + ",";
		}
		
		if (value.length > 0) {
			value = value.substring(0, value.length - 1);
		}
		
		return value;
	}
	
	/**
	 * 设置数据表格的值。
	 * @param value 数据表格的值。
	 */
	Datagrid.prototype.setValue = function(value) {
		this._deselectAll();	//取消所有选中
		
		//检查传入参数是否包含多个值
		var values = null;
		if (typeof(value) === "string") {
			values = value.split(",");
		} else {
			values = new Array();
			values.push(value);
		}
		
		for (var vi = 0; vi < values.length; vi ++) {
			for (var index = 0; index < this.rows.length; index ++) {
				var row = this.rows[index];
				if (row.selected) continue;
				
				if (row.record[this.idField] == values[vi]) {
					row.select();
					break;
				}
			}
		}
		
		this.checkSelectChange(false);	//检查选中改变
	}
	
	/**
	 * 获取最后一次排序列。
	 * @returns 最后一次排序列对象。
	 */
	Datagrid.prototype.getLastSortColumn = function() {
		var column = null;
		if (this.sortField) {
			for (var index = 0; index < this.columns.length; index ++) {
				if (this.columns[index].field == this.sortField) {
					column = this.columns[index];
					break;
				}
			}
		}
		return column;
	}
	
	/**
	 * 设置全选。
	 * @param selected 选中状态。
	 */
	Datagrid.prototype.setAllSelect = function(selected) {
		//检查全选元素是否存在
		if (!this._allCheck) return;
		
		//取消选中必然取消全选
		if (!selected) {
			this._allCheck.checked = false;
			return;
		}
		
		//所有行均选中时才选中全选元素
		if (this._isAllChecked(this.rows)) this._allCheck.checked = true;
	}
	
	/**
	 * 检查选中改变。
	 * @param isdeselect 是否为取消选中。
	 */
	Datagrid.prototype.checkSelectChange = function(isdeselect) {
		if (this.multiSelect) return;	//暂时屏蔽多选情况下选中改变的处理
		
		if (!this.selectchanged) return;
		
		var row = (isdeselect) ? null : this.getSelected();
		var value = (row == null) ? null : row.record[this.idField];
		
		var invoker = this;
		setTimeout(function() {
			var dscea = new DatagridSelectChangedEventArgs(value, row);
			invoker.selectchanged.call(invoker, dscea);
		}, 10);
	}
	
	/**
	 * 获取表格数据。
	 * @returns 表格数据。
	 */
	Datagrid.prototype.getData = function() {
		if (!this._data) return null;
		return (this._data.data) ? this._data.data : this._data;
	}
	
	/**
	 * 删除行。
	 * @param id 行数据标识。
	 */
	Datagrid.prototype.removeRow = function(id) {
		if (!this._data) return;
		
		var arr = (this._data.data) ? this._data.data : this._data;
		if (arr.length <= 0) return;
		
		var len = arr.length;
		for (var index = 0; index < len; index ++) {
			if (id === arr[index][this.idField]) {
				arr.splice(index, 1);
				if (this._data.total) this._data.total --;
				this._renderData(this._data, true);
				return;
			}
		}
	}
	
	/**
	 * 过滤。
	 * @param filters 过滤器。
	 * @param reserved 保留选中数据。
	 * @returns 符合条件的记录数。
	 */
	Datagrid.prototype.doFilter = function(filters, reserved) {
		if (!filters || filters.length <= 0) return -1;	//无可用过滤器
		
		if (this.showPager) return -1;		//有分页时不支持过滤
		
		//缓存选中数据
		var selecteds = null, selectedids = null;
		if (reserved) {
			selecteds = this.getSelecteds();
			selectedids = new Array();
		}
		
		var data = new Array();
		var records = (this._data.data) ? this._data.data : this._data;
		for (var ri = 0; ri < records.length; ri ++) {
			var record = records[ri];
			
			//处理选中数据
			if (selecteds && selecteds.length > 0) {
				var si = 0, found = false;
				for (; si < selecteds.length; si ++) {
					var sr = selecteds[si].record;
					if (record[this.idField] === sr[this.idField]) {
						found = true;
						break;
					}
				}
				if (found) {
					data.push(record);
					selecteds.splice(si, 1);
					selectedids.push(record[this.idField]);
					continue;
				}
			}
			
			//处理过滤数据
			var matches = 0;
			for (var fi = 0; fi < filters.length; fi ++) {
				var filter = filters[fi], value = record[filter.field];
				if (filter.doFilter(value)) matches ++;
			}
			if (matches == filters.length) {
				data.push(record);
			}
		}
		
		//渲染数据
		var result = { total : data.length, data : data };
		this._renderData(result, true);
		
		//恢复选中状态
		if (selectedids && selectedids.length > 0) this._recoverySelecteds(selectedids);
		
		return data.length;
	}
	
	/******************** 数据表格定义结束 ********************/
	
	/******************** 数据表格行定义开始 ********************/
	
	/**
	 * 数据表格行构造方法。
	 * @param parent 父级控件。
	 * @param index 行索引。
	 * @param record 行记录。
	 */
	function DatagridRow(parent, index, record) {
		Control.call(this, parent);
		
		//公共属性
		this.index = index;
		this.record = record;
		this.selected = false;
		
		//私有属性
		this._row = null;
		this._check = null;
	}
	
	/**
	 * 继承父类。
	 */
	DatagridRow.prototype = new Control(null);
	DatagridRow.prototype.constructor = DatagridRow;
	
	/**
	 * 鼠标悬停事件处理方法。
	 * @param hover 鼠标是否悬停。
	 */
	DatagridRow.prototype._mouseHover = function(hover) {
		if (hover) this._row.addClass("hover");
		else this._row.removeClass("hover");
	}
	
	/**
	 * 鼠标点击事件处理方法。
	 */
	DatagridRow.prototype._mouseClick = function() {
		var selecteds = this.parent.getSelecteds();
		if (this.parent.showCheckBox && this.parent.multiSelect) {
			//多选时点击已选中行则取消选中并返回
			for (var index = 0; index < selecteds.length; index ++) {
				if (selecteds[index].index == this.index) {
					selecteds[index].deselect();
					return;
				}
			}
		} else {
			//单选时取消选中所有已选中行并当点击已选中行时返回
			var clickself = false;
			for (var index = 0; index < selecteds.length; index ++) {
				selecteds[index].deselect();
				
				if (selecteds[index].index == this.index) clickself = true;
			}
			if (clickself) {
				this.parent.checkSelectChange(true);	//检查选中改变
				return;
			}
		}
		
		this.select();							//选中当前行
		this.parent.checkSelectChange(false);	//检查选中改变
	}
	
	/**
	 * 初始化子元素。
	 */
	DatagridRow.prototype._initChildElement = function() {
		if (this.parent.showCheckBox) this._check = this._row.find("input[property='check']").get(0);
	}
	
	/**
	 * 绑定事件。
	 */
	DatagridRow.prototype._bindEvent = function() {
		var invoker = this;
		this._row.bind({
			mouseenter : function(e) { invoker._mouseHover(true); }, 
			mouseleave : function(e) { invoker._mouseHover(false); }, 
			click : function(e) {
				if (!invoker.parent.allowRowSelect && $(e.target).attr("property") != "check") return;
				
				invoker._mouseClick.call(invoker);
			}});
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	DatagridRow.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		this._row = jqobj;			//保存行对象
		this._initChildElement();	//初始化子元素
		this._bindEvent();			//保存行对象
		
		//绑定鼠标右键事件
		var invoker = this.parent, origin = this;
		jqobj.mousedown(function(e) {
			if (e.which == 3) {
				search.closeContextMenu(null);
				
				if (invoker.hasContextMenu()) {
					invoker.popContextMenu(new Position(e.clientY, e.clientX), jqobj, origin);
				}
				return false;
			}
		});
		
		return true;
	}
	
	/**
	 * 选中。
	 */
	DatagridRow.prototype.select = function() {
		//标记选中状态
		this.selected = true;
		if (this._check) this._check.checked = true;
		
		//添加选中样式
		if (this.parent.selectedRows) this._row.addClass("selected");
		
		//设置全选
		this.parent.setAllSelect(true);
	}
	
	/**
	 * 取消选中。
	 */
	DatagridRow.prototype.deselect = function() {
		//标记选中状态
		this.selected = false;
		if (this._check) this._check.checked = false;
		
		//删除选中样式
		if (this.parent.selectedRows) this._row.removeClass("selected");
		
		//设置全选
		this.parent.setAllSelect(false);
	}
	
	/**
	 * 显示。
	 */
	DatagridRow.prototype.show = function() {
		this._row.css("display", "");
	}
	
	/**
	 * 隐藏。
	 */
	DatagridRow.prototype.hide = function() {
		this._row.css("display", "none");
	}
	
	/******************** 数据表格行定义结束 ********************/
	
	/******************** 数据表格列定义开始 ********************/
	
	/**
	 * 数据表格列构造方法。
	 * @param parent 父级控件。
	 */
	function DatagridColumn(parent) {
		Control.call(this, parent);
		
		//公共属性
		this.type = "datacolumn";
		this.field = null;
		this.width = "50px";
		this.headAlign = "left";
		this.headHeight = "28px";
		this.textAlign = "left";
		this.allowSort = false;
		this.showSummary = false;
		
		//私有属性
		this._headCell = null;
	}
	
	/**
	 * 继承父类。
	 */
	DatagridColumn.prototype = new Control(null);
	DatagridColumn.prototype.constructor = DatagridColumn;
	
	/**
	 * 排序。
	 */
	DatagridColumn.prototype._sort = function() {
		//检查当前字段是否首次排序
		if (!this.parent.sortField || this.parent.sortField != this.field) {
			//清除最后一次排序标识
			var last = this.parent.getLastSortColumn();
			if (last) last._removeSort();
			
			//设置排序属性
			this.parent.sortField = this.field;
			this.parent.sortOrder = "asc";
			
			//添加排序标识
			this._addSort(false);
		} else {
			if (this.parent.sortOrder == "asc") {
				//设置排序属性
				this.parent.sortOrder = "desc";
				
				//添加排序标识
				this._addSort(true);
			} else {
				//设置排序属性
				this.parent.sortOrder = "asc";
				
				//添加排序标识
				this._addSort(false);
			}
		}
		
		this.parent.load();
	}
	
	/**
	 * 添加排序标识。
	 * @param order 排序方向，false表示升序，true表示降序。
	 */
	DatagridColumn.prototype._addSort = function(order) {
		//查找排序标识
		var sort = this._headCell.children("span[property='sort']");
		if (sort.length <= 0) {
			//创建排序标识
			sort = $("<span property='sort'></span>");
			this._headCell.append(sort);
		}
		
		//设置排序标识样式
		if (order) {
			sort.removeClass("ascending").addClass("descending");
		} else {
			sort.removeClass("descending").addClass("ascending");
		}
	}
	
	/**
	 * 移除排序标识。
	 */
	DatagridColumn.prototype._removeSort = function() {
		var sort = this._headCell.children("span[property='sort']");
		if (sort.length > 0) sort.remove();
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	DatagridColumn.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//初始化属性
		this.type = getAttrValue(jqobj, "type", this.type);
		this.field = getAttrValue(jqobj, "field", this.field);
		this.width = getAttrValue(jqobj, "width", this.width);
		this.headAlign = getAttrValue(jqobj, "headAlign", this.headAlign);
		this.headHeight = getAttrValue(jqobj, "headHeight", this.headHeight);
		this.textAlign = getAttrValue(jqobj, "textAlign", this.textAlign);
		this.allowSort = getBooleanAttrValue(jqobj, "allowSort", this.allowSort);
		this.showSummary = getBooleanAttrValue(jqobj, "showSummary", this.showSummary);
		
		return true;
	}
	
	/**
	 * 是否是选择列。
	 */
	DatagridColumn.prototype.isCheckColumn = function() {
		return (this.type === "checkcolumn");
	}
	
	/**
	 * 是否是序号列。
	 */
	DatagridColumn.prototype.isIndexColumn = function() {
		return (this.type === "indexcolumn");
	}
	
	/**
	 * 生成占位单元格。
	 */
	DatagridColumn.prototype.genPlaceholderCell = function() {
		var cell = $("<td class='placeholder'></td>");
		
		//设置公共属性
		if (this.width) cell.css("width", this.width);
		
		switch (this.type) {
			case "checkcolumn":
				if (!this.parent.showCheckBox) return null;
				
				//设置公共属性
				cell.css("width", "20px");
				break;
			case "indexcolumn":
			case "datacolumn":
				break;
			default:
				return null;
		}
		
		return cell;
	}
	
	/**
	 * 生成表头单元格。
	 * @param value 显示值。
	 * @returns 表头单元格对象。
	 */
	DatagridColumn.prototype.genHeadCell = function(value) {
		this._headCell = $("<td property='data'></td>");
		
		//设置公共属性
		if (this.width) this._headCell.css("width", this.width);
		if (this.headAlign) this._headCell.css("text-align", this.headAlign);
		if (this.headHeight) this._headCell.css("height", this.headHeight);
		
		switch (this.type) {
			case "checkcolumn":
				if (!this.parent.showCheckBox) return null;
				
				//设置公共属性
				this._headCell.css("width", "20px");
				this._headCell.css("text-align", "center");
				
				//创建选择框
				if (this.parent.multiSelect) {
					this._headCell.append($("<input property='check' type='checkbox'/>"));
				}
				break;
			case "indexcolumn":
				this._headCell.html(value);
				break;
			case "datacolumn":
				this._headCell.html(value);
				
				//绑定排序事件
				if (this.allowSort) {
					var invoker = this;
					this._headCell.bind({click : function(e) { invoker._sort.call(invoker); }});
				}
				break;
			default:
				return null;
		}
		
		this._headCell.attr("title", this._headCell.text());
		return this._headCell;
	}
	
	/**
	 * 生成数据单元格。
	 * @param index 行索引。
	 * @param value 显示值。
	 * @returns 数据单元格对象。
	 */
	DatagridColumn.prototype.genDataCell = function(index, value) {
		var cell = $("<td></td>");
		
		//设置公共属性
		if (this.width) cell.css("width", this.width);
		if (this.textAlign) cell.css("text-align", this.textAlign);
		
		switch (this.type) {
			case "checkcolumn":
				if (!this.parent.showCheckBox) return null;
				
				//设置公共属性
				cell.css("width", "20px");
				cell.css("text-align", "center");
				
				//创建选择框
				if (this.parent.multiSelect) {
					cell.append($("<input property='check' type='checkbox'/>"));
				} else {
					cell.append($("<input property='check' type='radio'/>"));
				}
				break;
			case "indexcolumn":
				value = index + 1;
				
				cell.html(value);
				break;
			case "datacolumn":
				cell.html(value);
				break;
			default:
				return null;
		}
		
		cell.attr("title", cell.text());
		return cell;
	}
	
	/******************** 数据表格列定义结束 ********************/
	
	/******************** 数据表格分页定义开始 ********************/
	
	/**
	 * 数据表格分页构造方法。
	 * @param parent 父级控件。
	 */
	function DatagridPager(parent) {
		Control.call(this, parent);
		
		//公共属性
		this.pageIndex = 0;
		
		//私有属性
		this._panel = null;
		this._recordValue = null;
		this._choseCurrent = null;
		this._pageValue = null;
		this._pageInput = null;
		this._firstPage = null;
		this._prevPage = null;
		this._nextPage = null;
		this._lastPage = null;
		this._goPage = null;
		this._totalPage = null;
	}
	
	/**
	 * 继承父类。
	 */
	DatagridPager.prototype = new Control(null);
	DatagridPager.prototype.constructor = DatagridPager;
	
	/**
	 * 激活按钮。
	 * @param jqobj JQuery对象。
	 * @param defclass 按钮默认样式。
	 * @param mouseenter 鼠标移入事件处理方法。
	 * @param mouseleave 鼠标移出事件处理方法。
	 * @param click 鼠标点击事件处理方法。
	 */
	DatagridPager.prototype._activateButton = function(jqobj, defclass, mouseenter, mouseleave, click) {
		//移除按钮默认样式
		jqobj.removeClass(defclass);
		
		//绑定鼠标事件
		jqobj.bind({mouseenter : mouseenter, mouseleave : mouseleave, click : click});
	}
	
	/**
	 * 禁用按钮。
	 * @param jqobj JQuery对象。
	 * @param defclass 按钮默认样式。
	 * @param hovclass 鼠标悬停样式。
	 */
	DatagridPager.prototype._disableButton = function(jqobj, defclass, hovclass) {
		//添加按钮默认样式
		jqobj.addClass(defclass);
		
		//移除鼠标悬停样式
		if (jqobj.hasClass(hovclass)) jqobj.removeClass(hovclass);
		
		//解绑鼠标事件
		jqobj.unbind("mouseenter").unbind("mouseleave").unbind("click");
	}
	
	/**
	 * 翻页。
	 * @param action 翻页动作。
	 */
	DatagridPager.prototype._turnPage = function(action) {
		this.pageIndex = this.parent.pageIndex;
		
		switch (action) {
			case "first":
				this.pageIndex = 0;
				break;
			case "prev":
				this.pageIndex = this.pageIndex - 1;
				break;
			case "next":
				this.pageIndex = this.pageIndex + 1;
				break;
			case "last":
				this.pageIndex = this._totalPage - 1;
				break;
			case "go":
				this.pageIndex = parseInt(this._pageInput.val()) - 1;
				break;
			default:
				return;
		}
		
		this.parent.load();
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	DatagridPager.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//定义分页控件HTML代码
		var html = "<div class='record-container'>" + 
				   "&nbsp;共<span id='recordValue' class='record-value'>0</span>条数据" + 
				   "</div>" + 
				   "<div class='chose-container'>" + 
				   "<a id='firstPage' class='fa fa-step-backward chose-button chose-button-default'></a>" + 
				   "<a id='prevPage' class='fa fa-chevron-left chose-button chose-button-default'></a>" + 
				   "<span id='choseCurrent' class='chose-current'>1</span>" + 
				   "<a id='nextPage' class='fa fa-chevron-right chose-button chose-button-default'></a>" + 
				   "<a id='lastPage' class='fa fa-step-forward chose-button chose-button-default'></a>" + 
				   "</div>" + 
				   "<div class='go-container'>" + 
				   "共<em id='pageValue' class='page-value'>1</em>页&nbsp;&nbsp;&nbsp;" + 
				   "跳转到<input id='pageInput' class='page-input' type='number' value='1' min='1'>页&nbsp;&nbsp;" + 
				   "<a id='goPage' class='go-button go-button-default'>GO</a>" + 
				   "</div>";
		
		//创建分页控件
		this._panel = $(html);
		jqobj.append(this._panel);
		
		//初始化显示及操作控件
		this._recordValue = this._panel.find("span[id='recordValue']");
		this._choseCurrent = this._panel.find("span[id='choseCurrent']");
		this._pageValue = this._panel.find("em[id='pageValue']");
		this._pageInput = this._panel.find("input[id='pageInput']");
		this._firstPage = this._panel.find("a[id='firstPage']");
		this._prevPage = this._panel.find("a[id='prevPage']");
		this._nextPage = this._panel.find("a[id='nextPage']");
		this._lastPage = this._panel.find("a[id='lastPage']");
		this._goPage = this._panel.find("a[id='goPage']");
		
		return true;
	}
	
	/**
	 * 设置分页控件。
	 * @param total 总记录数。
	 */
	DatagridPager.prototype.set = function(total) {
		//计算总页数、当前页索引、当前页数
		this._totalPage = (total <= 0) ? 1 : Math.ceil(total / this.parent.pageSize);
		this.parent.pageIndex = this.pageIndex;
		if (this.parent.pageIndex >= this._totalPage) this.parent.pageIndex = this._totalPage - 1;
		var cp = this.parent.pageIndex + 1;
		
		//显示分页信息
		this._recordValue.text(total);
		this._choseCurrent.text(cp);
		this._pageValue.text(this._totalPage)
		this._pageInput.attr("max", this._totalPage);
		
		var invoker = this;
		
		//启/禁用首页、上一页按钮
		if (this.parent.pageIndex == 0) {
			if (!this._firstPage.hasClass("chose-button-default"))
				this._disableButton(this._firstPage, "chose-button-default", "chose-button-hover");
			
			if (!this._prevPage.hasClass("chose-button-default"))
				this._disableButton(this._prevPage, "chose-button-default", "chose-button-hover");
		} else {
			if (this._firstPage.hasClass("chose-button-default"))
				this._activateButton(this._firstPage, "chose-button-default", 
									 function(e) { invoker._firstPage.addClass("chose-button-hover"); }, 
									 function(e) { invoker._firstPage.removeClass("chose-button-hover"); }, 
									 function(e) { invoker._turnPage.call(invoker, "first"); });
			
			if (this._prevPage.hasClass("chose-button-default"))
				this._activateButton(this._prevPage, "chose-button-default", 
									 function(e) { invoker._prevPage.addClass("chose-button-hover"); }, 
									 function(e) { invoker._prevPage.removeClass("chose-button-hover"); }, 
									 function(e) { invoker._turnPage.call(invoker, "prev"); });
		}
		
		//启/禁用尾页、下一页按钮
		if (this.parent.pageIndex == this._totalPage - 1) {
			if (!this._nextPage.hasClass("chose-button-default"))
				this._disableButton(this._nextPage, "chose-button-default", "chose-button-hover");
			
			if (!this._lastPage.hasClass("chose-button-default"))
				this._disableButton(this._lastPage, "chose-button-default", "chose-button-hover");
		} else {
			if (this._nextPage.hasClass("chose-button-default"))
				this._activateButton(this._nextPage, "chose-button-default", 
									 function(e) { invoker._nextPage.addClass("chose-button-hover"); }, 
									 function(e) { invoker._nextPage.removeClass("chose-button-hover"); }, 
									 function(e) { invoker._turnPage.call(invoker, "next"); });
			
			if (this._lastPage.hasClass("chose-button-default"))
				this._activateButton(this._lastPage, "chose-button-default", 
									 function(e) { invoker._lastPage.addClass("chose-button-hover"); }, 
									 function(e) { invoker._lastPage.removeClass("chose-button-hover"); }, 
									 function(e) { invoker._turnPage.call(invoker, "last"); });
		}
		
		//启/禁用跳转按钮
		if (this._totalPage > 1) {
			if (this._goPage.hasClass("go-button-default"))
				this._activateButton(this._goPage, "go-button-default", 
									 function(e) { invoker._goPage.addClass("go-button-hover"); }, 
									 function(e) { invoker._goPage.removeClass("go-button-hover"); }, 
									 function(e) { invoker._turnPage.call(invoker, "go"); });
		} else {
			if (!this._goPage.hasClass("go-button-default"))
				this._disableButton(this._goPage, "go-button-default", "go-button-hover");
		}
	}
	
	/******************** 数据表格分页定义结束 ********************/
	
	/******************** 分组数据表格定义开始 ********************/
	
	/**
	 * 分组数据表格构造方法。
	 */
	function GroupDatagrid() {
		Datagrid.call(this);
		
		//公共属性
		this.parentField = null;
		this.textField = null;
		this.expandGroup = true;
		this.showSummary = true;
	}
	
	/**
	 * 继承父类。
	 */
	GroupDatagrid.prototype = new Datagrid();
	GroupDatagrid.prototype.constructor = GroupDatagrid;
	
	/**
	 * 查找所有选中行。
	 * @param rows 行集合。
	 * @param selecteds 选中行集合。
	 */
	GroupDatagrid.prototype._findSelecteds = function(rows, selecteds) {
		for (var index = 0; index < rows.length; index ++) {
			var row = rows[index];
			if (row.selected && row.type === "data") selecteds.push(row);
			if (row.children.length > 0) this._findSelecteds(row.children, selecteds);
		}
	}
	
	/**
	 * 选中所有行。
	 * @param rows 行集合。
	 * @param checked 选中状态。
	 */
	GroupDatagrid.prototype._checkAll = function(rows, checked) {
		for (var index = 0; index < rows.length; index ++) {
			var row = rows[index];
			if (row.type === "data") {
				if (checked) {
					if (!row.selected) row.select();
				} else {
					if (row.selected) row.deselect();
				}
			}
			if (row.children.length > 0) this._checkAll(row.children, checked);
		}
	}
	
	/**
	 * 取消所有选中。
	 */
	GroupDatagrid.prototype._deselectAll = function() {
		for (var gi = 0; gi < this.rows.length; gi ++) {
			var group = this.rows[gi];
			for (var index = 0; index < group.children.length; index ++) {
				var row = group.children[index];
				if (row.type === "data" && row.selected) row.deselect();
			}
		}
	}
	
	/**
	 * @see Datagrid#_initProperty(any)
	 */
	GroupDatagrid.prototype._initProperty = function(jqobj) {
		Datagrid.prototype._initProperty.call(this, jqobj);
		
		this.multiSelect = true;
		this.showPager = false;
		this.pageSize = 0;
		this.parentField = getAttrValue(jqobj, "parentField", this.parentField);
		this.textField = getAttrValue(jqobj, "textField", this.textField);
		this.expandGroup = getBooleanAttrValue(jqobj, "expandGroup", this.expandGroup);
		this.showSummary = getBooleanAttrValue(jqobj, "showSummary", this.showSummary);
	}
	
	/**
	 * @see Datagrid#_renderData(any, any)
	 */
	GroupDatagrid.prototype._renderData = function(result, ignoreCache) {
		var checkColumn = null, indexColumn = null;
		
		//清空原有数据
		this._body.empty();
		if (this.rows.length > 0) this.rows = new Array();
		
		var records = result;
		if (records.length == 0) this._initEmptyGrid();
		else{
			//生成占位行
			var phrow = $("<tr></tr>");
			for (var ci = 0; ci < this.columns.length; ci ++) {
				var column = this.columns[ci];
				
				//暂存选择列和序号列
				if (this.showCheckBox && column.isCheckColumn()) checkColumn = column;
				else if (column.isIndexColumn()) indexColumn = column;
				
				//创建占位单元格
				phrow.append(column.genPlaceholderCell());
			}
			this._body.append(phrow);
			
			//计算分组单元格占据列数
			var colspan = phrow.find("td").length;
			if (checkColumn) colspan --;
			if (indexColumn) colspan --;
			
			var index = 0;
			var gorder = 0;
			var tempgrow = null;
			var tempghtml = null;
			
			for (var gi = 0; gi < records.length; gi ++) {
				var gr = records[gi];
				if (gr[this.parentField]) continue;
				
				//处理第一行和中间行的加载事件
				if (this.onrowloaded && tempgrow) {
					var position = (gorder == 1) ? "first" : "middle";
					var rlea = new RowLoadedEventArgs(tempgrow, tempghtml, position);
					this.onrowloaded.call(this, rlea);
				}
				
				//生成分组行
				var grow = $("<tr class='group'></tr>");
				if (checkColumn) grow.append(checkColumn.genDataCell(null, null));
				if (indexColumn) grow.append(indexColumn.genDataCell(gorder, 0));
				var gcell = "<td colspan='" + colspan + "'>" + 
				"<span property='group' class='default'></span>" + 
				"<span property='text' class='summary'>" + gr[this.textField] + "</span>" + 
				"<span class='summary'>[</span>" + 
				"<span property='summary' class='summary'></span>" + 
				"<span class='summary'>]</span>" + 
				"</td>";
				grow.append($(gcell));
				this._body.append(grow);
				
				//保存分组行
				var group = new GroupDatagridRow(this, index, gr, "group");
				group.initialize(grow);
				this.rows.push(group);
				
				tempgrow = group;
				tempghtml = grow;
				
				index ++;
				gorder ++;
				
				var order = 0;
				var sum = null;
				var temprow = null;
				var temphtml = null;
				var gid = gr[this.idField];
				
				for (var ri = 0; ri < records.length; ri ++) {
					var record = records[ri];
					var pid = record[this.parentField];
					if (!pid || pid != gid) continue;
					
					//处理第一行和中间行的加载事件
					if (this.onrowloaded && temprow) {
						var position = (order == 1) ? "first" : "middle";
						var rlea = new RowLoadedEventArgs(temprow, temphtml, position);
						this.onrowloaded.call(this, rlea);
					}
					
					//生成数据行
					var bodyrow = $("<tr></tr>");
					for (var ci = 0; ci < this.columns.length; ci ++) {
						var column = this.columns[ci];
						
						//生成单元格HTML
						var html = null;
						var value = record[column.field];
						if (typeof(value) === "undefined" || value == null) value = "";
						
						//自定义单元格绘制
						if (this.drawcell) {
							var dea = new DrawcellEventArgs(record, column);
							this.drawcell.call(this, dea);
							html = dea.html;
						}
						
						if (html == null) html = value;
						if (typeof(html) === "boolean") html = html.toString();
						
						//汇总数据
						if (this.showSummary) {
							if (!sum) sum = {};
							
							if (column.showSummary) {
								var total = value;
								if (sum.hasOwnProperty(column.field)) total += sum[column.field];
								sum[column.field] = total;
							}
						}
						
						//创建数据单元格
						var cell = column.genDataCell(order, html);
						if (column.field == this.textField)
							cell.css("text-align", "left").css("padding-left", group.getChildIndent());
						bodyrow.append(cell);
					}
					
					//设置奇偶行样式
					if (this.alternatingRows && order % 2 > 0) bodyrow.addClass("alternating");
					
					this._body.append(bodyrow);
					
					//保存数据行
					var row = new GroupDatagridRow(this, index, record, "data");
					row.initialize(bodyrow);
					group.addChild(row);
					
					temprow = row;
					temphtml = bodyrow;
					
					index ++;
					order ++;
				}
				
				//处理最后一行的加载事件
				if (this.onrowloaded && temprow) {
					var position = (this.showSummary) ? ((order == 1) ? "first" : "middle") : ((order == 1) ? "one" : "last");
					var rlea = new RowLoadedEventArgs(temprow, temphtml, position);
					this.onrowloaded.call(this, rlea);
				}
				
				//统计数据行数
				grow.find("span[property='summary']").text(order);
				
				//生成汇总行
				if (this.showSummary) {
					var srow = $("<tr class='summary'></tr>");
					for (var ci = 0; ci < this.columns.length; ci ++) {
						var column = this.columns[ci];
						
						//生成单元格HTML
						var html = null;
						var value = null;
						if (sum && sum.hasOwnProperty(column.field)) value = sum[column.field];
						else if (column.field == this.textField) value = "合计：" + order;
						
						//自定义单元格绘制
						if (this.drawcell && value != null) {
							var record = {};
							record[column.field] = value;
							
							var dea = new DrawcellEventArgs(record, column);
							this.drawcell.call(this, dea);
							html = dea.html;
						}
						
						if (html == null) html = value;
						if (typeof(html) === "boolean") html = html.toString();
						
						//创建数据单元格
						var cell = column.genDataCell(order, html);
						if (column.field == this.textField)
							cell.css("text-align", "left").css("padding-left", group.getChildIndent());
						srow.append(cell);
					}
					
					this._body.append(srow);
					
					//保存汇总行
					var summary = new GroupDatagridRow(this, index, null, "summary");
					summary.initialize(srow);
					group.addChild(summary);
					
					//处理汇总行的加载事件
					if (this.onrowloaded) {
						var position = (order == 0) ? "one" : "last";
						var rlea = new RowLoadedEventArgs(summary, srow, position);
						this.onrowloaded.call(this, rlea);
					}
					
					index ++;
				}
				
				//设置默认状态
				group.setDefaultStatus();
			}
			
			//处理最后一行的加载事件
			if (this.onrowloaded && tempgrow) {
				var position = (gorder == 1) ? "one" : "last";
				var rlea = new RowLoadedEventArgs(tempgrow, tempghtml, position);
				this.onrowloaded.call(this, rlea);
			}
			
			if (!this.expandGroup) {
				//设置为折叠状态
				for (var index = 0; index < this.rows.length; index ++) {
					var grow = this.rows[index];
					grow.collapse();
				}
			}
		}
		
		//检查滚动条
		this.checkScroller();
		
		//缓存数据
		if (!ignoreCache) this._data = result;
	}
	
	/**
	 * @see Datagrid#_allCheckChanged()
	 */
	GroupDatagrid.prototype._allCheckChanged = function() {
		this._checkAll(this.rows, this._allCheck.checked);
	}
	
	/**
	 * @see Datagrid#_isAllChecked(any)
	 */
	GroupDatagrid.prototype._isAllChecked = function(rows) {
		for (var index = 0; index < rows.length; index ++) {
			var row = rows[index];
			if (row.type === "data" && !row.selected) return false;
			if (row.children.length > 0 && !this._isAllChecked(row.children)) return false;
		}
		return true;
	}
	
	/**
	 * @see Datagrid#_recoverySelecteds(any)
	 */
	GroupDatagrid.prototype._recoverySelecteds = function(selecteds) {
		var gi = 0, di = 0;
		for (var i = 0; i < selecteds.length; i ++) {
			var id = selecteds[i];
			
			for (; gi < this.rows.length; gi ++) {
				var children = this.rows[gi].children;
				if (children.length <= 0) continue;
				
				var found = false;
				for (; di < children.length; di ++) {
					var row = children[di];
					if (row.type != "data") continue;
					
					if (row.record[this.idField] === id) {
						found = true;
						row.select();
						di ++;
						break;
					}
				}
				if (found) {
					if (di == children.length) {
						gi ++;
						di = 0;
					}
					break;
				}
				
				di = 0;
			}
		}
	}
	
	/**
	 * @see Datagrid#getSelected()
	 */
	GroupDatagrid.prototype.getSelected = function() {
		return null;
	}
	
	/**
	 * @see Datagrid#getSelecteds()
	 */
	GroupDatagrid.prototype.getSelecteds = function() {
		var selecteds = new Array();
		this._findSelecteds(this.rows, selecteds);
		return selecteds;
	}
	
	/**
	 * @see Datagrid#getValue()
	 */
	GroupDatagrid.prototype.getValue = function() {
		if (!this.showCheckBox) return null;
		
		var value = "";
		
		for (var gi = 0; gi < this.rows.length; gi ++) {
			var group = this.rows[gi];
			for (var index = 0; index < group.children.length; index ++) {
				var row = group.children[index];
				if (!row.selected || row.type != "data") continue;
				
				value += row.record[this.idField] + ",";
			}
		}
		
		if (value.length > 0) {
			value = value.substring(0, value.length - 1);
		}
		
		return value;
	}
	
	/**
	 * @see Datagrid#setValue(any)
	 */
	GroupDatagrid.prototype.setValue = function(value) {
		this._deselectAll();	//取消所有选中
		
		//检查传入参数是否包含多个值
		var values = null;
		if (typeof(value) === "string") {
			values = value.split(",");
		} else {
			values = new Array();
			values.push(value);
		}
		
		for (var vi = 0; vi < values.length; vi ++) {
			var found = false;
			for (var gi = 0; gi < this.rows.length; gi ++) {
				if (found) break;
				
				var group = this.rows[gi];
				for (var index = 0; index < group.children.length; index ++) {
					var row = this.rows[index];
					if (row.selected || row.type != "data") continue;
					
					if (row.record[this.idField] == values[vi]) {
						found = true;
						row.select();
						break;
					}
				}
			}
		}
	}
	
	/**
	 * @see Datagrid#getData()
	 */
	GroupDatagrid.prototype.getData = function() {
		if (!this._data) return null;
		return this._data;
	}
	
	/**
	 * @see Datagrid#removeRow(any)
	 */
	GroupDatagrid.prototype.removeRow = function(id) { }
	
	/**
	 * @see Datagrid#doFilter(any, any)
	 */
	GroupDatagrid.prototype.doFilter = function(filters, reserved) { return -1; }
	
	/******************** 分组数据表格定义结束 ********************/
	
	/******************** 分组数据表格行定义开始 ********************/
	
	/**
	 * 分组数据表格行构造方法。
	 * @param parent 父级控件。
	 * @param index 行索引。
	 * @param record 行记录。
	 * @param type 行类型。
	 */
	function GroupDatagridRow(parent, index, record, type) {
		DatagridRow.call(this, parent, index, record);
		
		//公共属性
		this.type = type;
		this.expanded = true;
		this.children = new Array();
		
		//私有属性
		this._group = null;
		this._parent = null;
		this._summary = null;
	}
	
	/**
	 * 继承父类。
	 */
	GroupDatagridRow.prototype = new DatagridRow(null, null, null);
	GroupDatagridRow.prototype.constructor = GroupDatagridRow;
	
	/**
	 * 选中子级行。
	 * @param selected 选中状态。
	 */
	GroupDatagridRow.prototype._selectChildren = function(selected) {
		for (var index = 0; index < this.children.length; index ++) {
			var row = this.children[index];
			if (selected) {
				if (!row.selected) row.select();
			} else {
				if (row.selected) row.deselect();
			}
		}
	}
	
	/**
	 * 设置分组选中。
	 * @param selected 选中状态。
	 */
	GroupDatagridRow.prototype._setGroupSelect = function(selected) {
		//检查是否是分组行
		if (this.type != "group") return;
		
		if (!selected) {
			//取消分组选中
			this.selected = false;
			this._check.checked = false;
			
			//取消汇总选中
			if (this._summary) {
				this._summary.selected = false;
				this._summary._check.checked = false;
			}
			return;
		}
		
		//检查是否所有数据行均选中
		var checkall = true;
		for (var index = 0; index < this.children.length; index ++) {
			var row = this.children[index];
			if (row.type === "data" && !row.selected) {
				checkall = false;
				break;
			}
		}
		
		if (checkall) {
			//选中分组
			this.selected = true;
			this._check.checked = true;
			
			//选中汇总
			if (this._summary) {
				this._summary.selected = true;
				this._summary._check.checked = true;
			}
		}
	}
	
	/**
	 * 检查是否有子级行。
	 * @returns 有子级行返回true，否则返回false。
	 */
	GroupDatagridRow.prototype._hasChild = function() {
		var length = this.children.length;
		if (this.parent.showSummary) length --;
		return (length > 0);
	}
	
	/**
	 * 分组行鼠标点击事件处理方法。
	 */
	GroupDatagridRow.prototype._groupRowClick = function() {
		if (this.expanded) this.collapse();
		else this.expand();
	}
	
	/**
	 * 分组选择鼠标点击事件处理方法。
	 */
	GroupDatagridRow.prototype._groupCheckClick = function() {
		if (this._check.checked) this.select();
		else this.deselect();
	}
	
	/**
	 * 汇总选择鼠标点击事件处理方法。
	 */
	GroupDatagridRow.prototype._summaryCheckClick = function() {
		if (this._check.checked) this.select();
		else this.deselect();
	}
	
	/**
	 * @see DatagridRow#_initChildElement()
	 */
	GroupDatagridRow.prototype._initChildElement = function() {
		DatagridRow.prototype._initChildElement.call(this)
		
		if (this.type === "group") this._group = this._row.find("span[property='group']");
	}
	
	/**
	 * @see DatagridRow#_bindEvent()
	 */
	GroupDatagridRow.prototype._bindEvent = function() {
		var invoker = this;
		switch (this.type) {
			case "group":
				this._row.bind({ click : function(e) {
					if ($(e.target).attr("property") === "check") invoker._groupCheckClick.call(invoker);
					else invoker._groupRowClick.call(invoker);
				}});
				break;
			case "data":
				this._row.bind({
					mouseenter : function(e) { invoker._mouseHover(true); }, 
					mouseleave : function(e) { invoker._mouseHover(false); }});
				
				if (this.parent.showCheckBox)
					this._row.bind({ click : function(e) {
						if (!invoker.parent.allowRowSelect && $(e.target).attr("property") != "check") return;
						
						invoker._mouseClick.call(invoker);
					}});
				break;
			case "summary":
				this._row.bind({ click : function(e) {
					if ($(e.target).attr("property") === "check") invoker._summaryCheckClick.call(invoker);
				}});
				break;
			default:
				return;
		}
	}
	
	/**
	 * @see DatagridRow#select()
	 */
	GroupDatagridRow.prototype.select = function() {
		//标记选中状态
		this.selected = true;
		this._check.checked = true;
		
		switch (this.type) {
			case "group":
				this._selectChildren(true);	//选中所有子级行
				break;
			case "data":
				if (this.parent.selectedRows) this._row.addClass("selected");		//添加选中样式
				this._parent._setGroupSelect(true);	//设置分组选中
				this.parent.setAllSelect(true);		//设置全选
				break;
			case "summary":
				this._parent.select();	//相当于分组选中
				break;
			default:
				return;
		}
	}
	
	/**
	 * @see DatagridRow#deselect()
	 */
	GroupDatagridRow.prototype.deselect = function() {
		//标记选中状态
		this.selected = false;
		this._check.checked = false;
		
		switch (this.type) {
			case "group":
				this._selectChildren(false);	//取消选中所有子级行
				break;
			case "data":
				if (this.parent.selectedRows) this._row.removeClass("selected");		//删除选中样式
				this._parent._setGroupSelect(false);	//取消分组选中
				this.parent.setAllSelect(false);		//设置全选
				break;
			case "summary":
				this._parent.deselect();	//相当于分组取消选中
				break;
			default:
				return;
		}
	}
	
	/**
	 * 添加子级行。
	 * @param row 子级行对象。
	 */
	GroupDatagridRow.prototype.addChild = function(child) {
		child._parent = this;
		if (child.type === "summary") this._summary = child;
		
		this.children.push(child);
	}
	
	/**
	 * 获取子级行缩进。
	 * @returns 子级行缩进样式值。
	 */
	GroupDatagridRow.prototype.getChildIndent = function() {
		return "23px";
	}
	
	/**
	 * 设置默认状态。
	 */
	GroupDatagridRow.prototype.setDefaultStatus = function() {
		if (!this._hasChild()) return;
		
		//修改样式
		this._group.removeClass("default").addClass("expand");
	}
	
	/**
	 * 折叠。
	 */
	GroupDatagridRow.prototype.collapse = function() {
		if (!this._hasChild()) return;
		
		//修改属性
		this.expanded = false;
		this._group.removeClass("expand").addClass("collapse");
		
		//隐藏数据行
		for (var index = 0; index < this.children.length; index ++) {
			var drow = this.children[index];
			if (drow.type != "data") continue;
			
			drow.hide();
		}
		
		//检查滚动条
		this.parent.checkScroller();
	}
	
	/**
	 * 展开。
	 */
	GroupDatagridRow.prototype.expand = function() {
		if (!this._hasChild()) return;
		
		//修改属性
		this.expanded = true;
		this._group.removeClass("collapse").addClass("expand");
		
		//显示数据行
		for (var index = 0; index < this.children.length; index ++) {
			var drow = this.children[index];
			if (drow.type != "data") continue;
			
			drow.show();
		}
		
		//检查滚动条
		this.parent.checkScroller();
	}
	
	/******************** 分组数据表格行定义结束 ********************/
	
	/******************** 树形数据表格定义开始 ********************/
	
	/**
	 * 树形数据表格构造方法。
	 */
	function TreeDatagrid() {
		Datagrid.call(this);
		
		//公共属性
		this.parentField = null;
		this.textField = null;
		this.expandLevel = 0;
	}
	
	/**
	 * 继承父类。
	 */
	TreeDatagrid.prototype = new Datagrid();
	TreeDatagrid.prototype.constructor = TreeDatagrid;
	
	/**
	 * 渲染行。
	 * @param records 记录集合。
	 * @param parent 父级行。
	 * @param index 当前行索引。
	 * @returns 下一行索引。
	 */
	TreeDatagrid.prototype._renderRow = function(records, parent, index) {
		var pid = null;		//父级ID
		var indent = null;	//缩进
		if (parent) {
			pid = parent.record[this.idField];
			indent = parent.getChildIndent();
		}
		
		var order = 0;
		var temprow = null;
		var temphtml = null;
		
		for (var ri = 0; ri < records.length; ri ++) {
			var record = records[ri];
			if (record[this.parentField] != pid) continue;
			
			//处理第一行和中间行的加载事件
			if (this.onrowloaded && temprow) {
				var position = (order == 1) ? "first" : "middle";
				var rlea = new RowLoadedEventArgs(temprow, temphtml, position);
				this.onrowloaded.call(this, rlea);
			}
			
			//生成数据行
			var bodyrow = $("<tr></tr>");
			for (var ci = 0; ci < this.columns.length; ci ++) {
				var column = this.columns[ci];
				if (column.type === "checkcolumn") continue;
				
				//生成单元格HTML
				var html = null;
				var value = record[column.field];
				if (typeof(value) === "undefined" || value == null) value = "";
				
				//自定义单元格绘制
				if (this.drawcell) {
					var dea = new DrawcellEventArgs(record, column);
					this.drawcell.call(this, dea);
					html = dea.html;
				}
				
				if (html == null) html = value;
				if (typeof(html) === "boolean") html = html.toString();
				
				//创建数据单元格
				var cell = column.genDataCell(order, html);
				if (column.field == this.textField) {
					if (this.showCheckBox) {
						//设置为显示选择框时对文本列添加单/复选框
						if (this.multiSelect) cell.prepend($("<input property='check' type='checkbox'/>"));
						else cell.prepend($("<input property='check' type='radio'/>"));
					}
					
					//添加分组元素
					var group = $("<span property='group' class='default'></span>");
					cell.prepend(group);
					
					//设置样式
					cell.css("text-align", "left");
					if (indent) cell.css("padding-left", indent);
				}
				bodyrow.append(cell);
			}
			this._body.append(bodyrow);
			
			//保存数据行
			var row = new TreeDatagridRow(this, index, record);
			row.initialize(bodyrow);
			if (parent) parent.addChild(row);
			else this.rows.push(row);
			
			temprow = row;
			temphtml = bodyrow;
			
			index ++;
			order ++;
			
			index = this._renderRow(records, row, index);	//渲染子级行
			row.setDefaultStatus();							//设置默认状态
		}
		
		//处理最后一行的加载事件
		if (this.onrowloaded && temprow) {
			var position = (order == 1) ? "one" : "last";
			var rlea = new RowLoadedEventArgs(temprow, temphtml, position);
			this.onrowloaded.call(this, rlea);
		}
		
		return index;
	}
	
	/**
	 * 设置子级行样式。
	 * @param parent 父级行对象。
	 * @param order 当前行序号。
	 * @returns 下一行序号。
	 */
	TreeDatagrid.prototype._setChildRowStyle = function(parent, order) {
		for (var index = 0; index < parent.children.length; index ++) {
			var child = parent.children[index];
			
			//设置当前行样式
			if (order % 2 > 0) child.setAlternatingStyle(true);
			else child.setAlternatingStyle(false);
			
			order ++;
			
			//设置子级行样式
			if (child.expanded) order = this._setChildRowStyle(child, order);
		}
		
		return order;
	}
	
	/**
	 * 选中所有行。
	 * @param rows 行集合。
	 * @param checked 选中状态。
	 */
	TreeDatagrid.prototype._checkAll = function(rows, checked) {
		for (var index = 0; index < rows.length; index ++) {
			var row = rows[index];
			if (checked) {
				if (!row.selected) row.select();
			} else {
				if (row.selected) row.deselect();
			}
			if (row.children.length > 0) this._checkAll(row.children, checked);
		}
	}
	
	/**
	 * 查找选中行。
	 * @param rows 行集合。
	 * @returns 查找到的第一个选中行。
	 */
	TreeDatagrid.prototype._findSelected = function(rows) {
		var selected = null;
		for (var index = 0; index < rows.length; index ++) {
			var row = rows[index];
			
			if (row.selected) selected = row;
			else selected = this._findSelected(row.children);
			
			if (selected) break;
		}
		return selected;
	}
	
	/**
	 * 查找所有选中行。
	 * @param rows 行集合。
	 * @param multiple true表示多选模式，false表示单选模式。
	 * @param selecteds 选中行集合。
	 */
	TreeDatagrid.prototype._findSelecteds = function(rows, multiple, selecteds) {
		for (var index = 0; index < rows.length; index ++) {
			var row = rows[index];
			
			//添加当前行到选中行
			if (row.selected) selecteds.push(row);
			
			//满足有子级行 且 (单选模式 或 (多选模式 且 当前行已选中))条件时检查子级行
			if (row.children.length > 0 && (!multiple || (multiple && row.selected)))
				this._findSelecteds(row.children, multiple, selecteds);
		}
	}
	
	/**
	 * 获取数据表格的值。
	 * @param children 子行集合。
	 * @returns 数据表格的值。
	 */
	TreeDatagrid.prototype._getValue = function(children) {
		var value = "";
		
		for (var index = 0; index < children.length; index ++) {
			var row = children[index];
			
			if (row.selected) {
				if (!this.showCheckBox || !this.multiSelect) return row.record[this.idField];
				value += row.record[this.idField] + ",";
			}
			
			var temp = this._getValue(row.children);
			if (temp == "") continue;
			
			if (!this.showCheckBox || !this.multiSelect) return temp;
			value += temp;
		}
		
		return value;
	}
	
	/**
	 * 设置数据表格的值。
	 * @param value 数据表格的值。
	 * @param children 子行集合。
	 * @returns 子行集合中查找到对应值时返回true，否则返回false。
	 */
	TreeDatagrid.prototype._setValue = function(value, children) {
		var found = false;
		
		for (var index = 0; index < children.length; index ++) {
			var row = children[index];
			
			if (value == row.record[this.idField]) {
				found = true;
				row.selectOnlySelf();
				break;
			}
			
			found = this._setValue(value, row.children);
			if (found) break;
		}
		
		return found;
	}
	
	/**
	 * 取消所有选中。
	 * @param rows 行对象集合。
	 */
	TreeDatagrid.prototype._deselectAll = function(rows) {
		for (var index = 0; index < rows.length; index ++) {
			var row = rows[index];
			if (row.selected) row.deselect();
			
			this._deselectAll(row.children);
		}
	}
	
	/**
	 * 查找子级数据索引。
	 * @param parent 父级行数据标识。
	 * @param indices 子级数据索引集合。
	 */
	TreeDatagrid.prototype._findChildIndex = function(parent, indices) {
		for (var index = 0; index < this._data.length; index ++) {
			if (parent === this._data[index][this.parentField]) {
				indices.push(index);
				this._findChildIndex(this._data[index][this.idField], indices);
			}
		}
	}
	
	/**
	 * 恢复树选中。
	 * @param indexs 索引对象。
	 * @param rows 行集合。
	 * @param selected 选中标识。
	 * @returns 是否查找到匹配行。
	 */
	TreeDatagrid.prototype._recoveryTreeSelecteds = function(indexs, rows, selected) {
		for (var index = indexs.index; index >= 0; index --) {
			var row = rows[index];
			
			if (row.record[this.idField] === selected) {
				if (!row.selected) row.select();
				indexs.index = index - 1;
				return true;
			}
			
			if (row.children.length <= 0) continue;
			
			indexs.child = { index : row.children.length - 1, child : null };
			if (this._recoveryTreeSelecteds(indexs.child, row.children, selected)) {
				if (!row.expanded) row.expand();
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @see Datagrid#_initProperty(any)
	 */
	TreeDatagrid.prototype._initProperty = function(jqobj) {
		Datagrid.prototype._initProperty.call(this, jqobj);
		
		this.showPager = false;
		this.pageSize = 0;
		this.parentField = getAttrValue(jqobj, "parentField", this.parentField);
		this.textField = getAttrValue(jqobj, "textField", this.textField);
		this.expandLevel = getIntegerAttrValue(jqobj, "expandLevel", this.expandLevel);
	}
	
	/**
	 * @see Datagrid#_initHeadCell(any, any, any)
	 */
	TreeDatagrid.prototype._initHeadCell = function(headrow, column, coldef) {
		var width = 0;
		
		//树形表格不单独生成选择列
		if (column.type === "checkcolumn") return width;
		
		//创建表头单元格
		var cell = column.genHeadCell(coldef.html());
		if (cell) {
			width = cell.width();
			
			if (column.field == this.textField && this.showCheckBox && this.multiSelect) {
				//对文本列在多选时添加复选框
				cell.prepend($("<input property='check' type='checkbox'/>"));
			}
			headrow.append(cell);
		}
		
		return width;
	}
	
	/**
	 * @see Datagrid#_renderData(any, any)
	 */
	TreeDatagrid.prototype._renderData = function(result, ignoreCache) {
		//设置无父级项的父级字段为空
		checkTopItem(result, this.idField, this.parentField);
		
		//清空原有数据
		this._body.empty();
		if (this.rows.length > 0) this.rows = new Array();
		
		var records = result;
		if (records.length == 0) this._initEmptyGrid();
		else{
			//处理第一级父级不为空的情况
			for (var ci = 0; ci < records.length; ci ++) {
				var crp = records[ci][this.parentField];
				if (!crp) continue;		//跳过父级为空的情况
				
				var hp = false;
				for (var pi = 0; pi < records.length; pi ++) {
					if (crp == records[pi][this.idField]) {		//查找到父级
						hp = true;
						break;
					}
				}
				if (!hp) records[ci][this.parentField] = null;	//未找到父级时设置父级属性为空
			}
			
			//渲染行
			this._renderRow(records, null, 0);
		}
		
		this.setAlternatingStyle();	//设置交替行样式
		this.checkScroller();		//检查滚动条
		
		//缓存数据
		if (!ignoreCache) this._data = result;
	}
	
	/**
	 * @see Datagrid#_allCheckChanged()
	 */
	TreeDatagrid.prototype._allCheckChanged = function() {
		this._checkAll(this.rows, this._allCheck.checked);
	}
	
	/**
	 * @see Datagrid#_isAllChecked(any)
	 */
	TreeDatagrid.prototype._isAllChecked = function(rows) {
		for (var index = 0; index < rows.length; index ++) {
			var row = rows[index];
			if (!row.selected) return false;
			if (row.children.length > 0 && !this._isAllChecked(row.children)) return false;
		}
		return true;
	}
	
	/**
	 * @see Datagrid#_recoverySelecteds(any)
	 */
	TreeDatagrid.prototype._recoverySelecteds = function(selecteds) {
		var indexs = { index : this.rows.length - 1, child : null };
		for (var i = selecteds.length - 1; i >= 0; i --) {
			var id = selecteds[i];
			
			if (!this._recoveryTreeSelecteds(indexs, this.rows, id)) break;
		}
	}
	
	/**
	 * @see Datagrid#getSelected()
	 */
	TreeDatagrid.prototype.getSelected = function() {
		return this._findSelected(this.rows);
	}
	
	/**
	 * @see Datagrid#getSelecteds()
	 */
	TreeDatagrid.prototype.getSelecteds = function() {
		var selecteds = new Array();
		this._findSelecteds(this.rows, (this.showCheckBox && this.multiSelect), selecteds);
		return selecteds;
	}
	
	/**
	 * @see Datagrid#getValue()
	 */
	TreeDatagrid.prototype.getValue = function() {
		var value = "";
		
		for (var index = 0; index < this.rows.length; index ++) {
			var row = this.rows[index];
			
			if (row.selected) {
				if (!this.showCheckBox || !this.multiSelect) return row.record[this.idField];
				value += row.record[this.idField] + ",";
			}
			
			var temp = this._getValue(row.children);
			if (temp == "") continue;
			
			if (!this.showCheckBox || !this.multiSelect) return temp;
			value += temp;
		}
		
		if (value.length > 0) {
			value = value.substring(0, value.length - 1);
		}
		
		return value;
	}
	
	/**
	 * @see Datagrid#setValue(any)
	 */
	TreeDatagrid.prototype.setValue = function(value) {
		this._deselectAll(this.rows);	//取消所有选中
		
		//检查传入参数是否包含多个值
		var values = null;
		if (typeof(value) === "string") {
			values = value.split(",");
		} else {
			values = new Array();
			values.push(value);
		}
		
		for (var vi = 0; vi < values.length; vi ++) {
			for (var index = 0; index < this.rows.length; index ++) {
				var row = this.rows[index];
				
				if (values[vi] == row.record[this.idField]) {
					row.selectOnlySelf();
					break;
				}
				
				var found = this._setValue(values[vi], row.children);
				if (found) break;
			}
		}
		
		this.checkSelectChange(false);	//检查选中改变
	}
	
	/**
	 * 设置交替行样式。
	 */
	TreeDatagrid.prototype.setAlternatingStyle = function() {
		if (!this.alternatingRows) return;
		
		var order = 0;
		for (var index = 0; index < this.rows.length; index ++) {
			var row = this.rows[index];
			
			//设置当前行样式
			if (order % 2 > 0) row.setAlternatingStyle(true);
			else row.setAlternatingStyle(false);
			
			order ++;
			
			//设置子级行样式
			if (row.expanded) order = this._setChildRowStyle(row, order);
		}
	}
	
	/**
	 * @see Datagrid#getData()
	 */
	TreeDatagrid.prototype.getData = function() {
		if (!this._data) return null;
		return this._data;
	}
	
	/**
	 * @see Datagrid#removeRow(any)
	 */
	TreeDatagrid.prototype.removeRow = function(id) {
		if (!this._data || this._data.length <= 0) return;
		
		var indices = new Array();
		for (var index = 0; index < this._data.length; index ++) {
			if (id === this._data[index][this.idField]) {
				indices.push(index);
				this._findChildIndex(id, indices);
				break;
			}
		}
		
		if (indices.length <= 0) return;
		
		indices.sort(function(l, r) {
			return (r - l);
		});
		for (var index = 0; index < indices.length; index ++) {
			this._data.splice(indices[index], 1);
		}
		this._renderData(this._data, true);
	}
	
	/**
	 * @see Datagrid#doFilter(any, any)
	 */
	TreeDatagrid.prototype.doFilter = function(filters, reserved) {
		if (!filters || filters.length <= 0) return -1;	//无可用过滤器
		
		//缓存选中数据
		var selecteds = null, selectedids = null;
		if (reserved) {
			selecteds = this.getSelecteds();
			selectedids = new Array();
		}
		
		var data = new Array();
		var records = this._data;
		for (var ri = 0; ri < records.length; ri ++) {
			var record = records[ri];
			
			//处理选中数据
			if (selecteds && selecteds.length > 0) {
				var si = 0, found = false;
				for (; si < selecteds.length; si ++) {
					var sr = selecteds[si].record;
					if (record[this.idField] === sr[this.idField]) {
						found = true;
						break;
					}
				}
				if (found) {
					data.push(record);
					selecteds.splice(si, 1);
					selectedids.push(record[this.idField]);
					continue;
				}
			}
			
			//处理过滤数据
			var matches = 0;
			for (var fi = 0; fi < filters.length; fi ++) {
				var filter = filters[fi], value = record[filter.field];
				if (filter.doFilter(value)) matches ++;
			}
			if (matches == filters.length) {
				data.push(record);
			}
		}
		
		//渲染数据
		this._renderData(data, true);
		
		//恢复选中状态
		if (selectedids && selectedids.length > 0) this._recoverySelecteds(selectedids);
		
		return data.length;
	}
	
	/******************** 树形数据表格定义结束 ********************/
	
	/******************** 树形数据表格行定义开始 ********************/
	
	/**
	 * 树形数据表格行构造方法。
	 * @param parent 父级控件。
	 * @param index 行索引。
	 * @param record 行记录。
	 */
	function TreeDatagridRow(parent, index, record) {
		DatagridRow.call(this, parent, index, record);
		
		//公共属性
		this.level = 0;
		this.expanded = true;
		this.children = new Array();
		
		//私有属性
		this._group = null;
		this._parent = null;
		this._timer = null;
	}
	
	/**
	 * 继承父类。
	 */
	TreeDatagridRow.prototype = new DatagridRow(null, null, null);
	TreeDatagridRow.prototype.constructor = TreeDatagridRow;
	
	/**
	 * 选中父级行。
	 */
	TreeDatagridRow.prototype._selectParent = function() {
		if (this._parent && !this._parent.selected) {
			//标记选中状态
			this._parent.selected = true;
			if (this._parent._check) this._parent._check.checked = true;
			
			//添加选中样式
			if (this.parent.selectedRows) this._parent._row.addClass("selected");
			
			//选中父级行
			this._parent._selectParent();
		}
	}
	
	/**
	 * 选中子级行。
	 */
	TreeDatagridRow.prototype._selectChildren = function() {
		for (var index = 0; index < this.children.length; index ++) {
			var child = this.children[index];
			
			//标记选中状态
			child.selected = true;
			if (child._check) child._check.checked = true;
			
			//添加选中样式
			if (this.parent.selectedRows) child._row.addClass("selected");
			
			//选中子级行
			child._selectChildren();
		}
	}
	
	/**
	 * 取消选中父级行。
	 */
	TreeDatagridRow.prototype._deselectParent = function() {
		if (this._parent && this._parent.selected) {
			//检查是否所有子级均已取消选中
			var alldeselected = true;
			for (var index = 0; index < this._parent.children.length; index ++) {
				if (this._parent.children[index].selected) {
					alldeselected = false;
					break;
				}
			}
			if (!alldeselected) return;
			
			//标记选中状态
			this._parent.selected = false;
			if (this._parent._check) this._parent._check.checked = false;
			
			//删除选中样式
			if (this.parent.selectedRows) this._parent._row.removeClass("selected");
			
			//取消选中父级行
			this._parent._deselectParent();
		}
	}
	
	/**
	 * 取消选中子级行。
	 */
	TreeDatagridRow.prototype._deselectChildren = function() {
		for (var index = 0; index < this.children.length; index ++) {
			var child = this.children[index];
			
			//标记选中状态
			child.selected = false;
			if (child._check) child._check.checked = false;
			
			//删除选中样式
			if (this.parent.selectedRows) child._row.removeClass("selected");
			
			//取消选中子级行
			child._deselectChildren();
		}
	}
	
	/**
	 * 折叠或展开子级行。
	 * @param children 子级行集合。
	 * @param expand true表示展开，false表示折叠。
	 */
	TreeDatagridRow.prototype._collapseOrExpandChild = function(children, expand) {
		for (var index = 0; index < children.length; index ++) {
			var child = children[index];
			
			//显示或隐藏当前行
			if (expand) child.show();
			else child.hide();
			
			//当前行为折叠时不检查子级
			if (!child.expanded) continue;
			
			//折叠或展开子级行
			if (child.children.length > 0) this._collapseOrExpandChild(child.children, expand);
		}
	}
	
	/**
	 * 鼠标双击事件处理方法。
	 */
	TreeDatagridRow.prototype._mouseDblClick = function() {
		if (this.expanded) this.collapse();
		else this.expand();
	}
	
	/**
	 * @see DatagridRow#_initChildElement()
	 */
	TreeDatagridRow.prototype._initChildElement = function() {
		DatagridRow.prototype._initChildElement.call(this)
		
		this._group = this._row.find("span[property='group']");
	}
	
	/**
	 * @see DatagridRow#_bindEvent()
	 */
	TreeDatagridRow.prototype._bindEvent = function() {
		var invoker = this;
		this._row.bind({
			mouseenter : function(e) { invoker._mouseHover(true); }, 
			mouseleave : function(e) { invoker._mouseHover(false); }, 
			click : function(e) {
				if (invoker._timer) clearTimeout(invoker._timer);
				
				var property = $(e.target).attr("property");
				if (property === "group") invoker._mouseDblClick.call(invoker);
				else {
					if (!invoker.parent.allowRowSelect && property != "check") return;
					
					invoker._timer = setTimeout(function() { invoker._mouseClick.call(invoker); }, 300);
				}
			}, 
			dblclick : function(e) {
				if (invoker._timer) clearTimeout(invoker._timer);
				invoker._mouseDblClick.call(invoker);
			}});
	}
	
	/**
	 * @see DatagridRow#select()
	 */
	TreeDatagridRow.prototype.select = function() {
		//标记选中状态
		this.selected = true;
		if (this._check) this._check.checked = true;
		
		//添加选中样式
		if (this.parent.selectedRows) this._row.addClass("selected");
		
		if (this.parent.showCheckBox && this.parent.multiSelect) {
			this._selectParent();	//选中父级行
			this._selectChildren();	//选中子级行
		}
		
		//设置全选
		this.parent.setAllSelect(true);
	}
	
	/**
	 * @see DatagridRow#deselect()
	 */
	TreeDatagridRow.prototype.deselect = function() {
		//标记选中状态
		this.selected = false;
		if (this._check) this._check.checked = false;
		
		//删除选中样式
		if (this.parent.selectedRows) this._row.removeClass("selected");
		
		if (this.parent.showCheckBox && this.parent.multiSelect) {
			this._deselectParent();		//取消选中父级行
			this._deselectChildren();	//取消选中子级行
		}
		
		//设置全选
		this.parent.setAllSelect(false);
	}
	
	/**
	 * 仅选中本身。
	 */
	TreeDatagridRow.prototype.selectOnlySelf = function() {
		//标记选中状态
		this.selected = true;
		if (this._check) this._check.checked = true;
		
		//添加选中样式
		if (this.parent.selectedRows) this._row.addClass("selected");
		
		//设置全选
		this.parent.setAllSelect(true);
	}
	
	/**
	 * 添加子级行。
	 * @param child 子级行对象。
	 */
	TreeDatagridRow.prototype.addChild = function(child) {
		child.level = this.level + 1;
		child._parent = this;
		
		this.children.push(child);
	}
	
	/**
	 * 获取子级行缩进。
	 * @returns 子级行缩进样式值。
	 */
	TreeDatagridRow.prototype.getChildIndent = function() {
		return (((this.level + 1) * 20) + "px");
	}
	
	/**
	 * 设置默认状态。
	 */
	TreeDatagridRow.prototype.setDefaultStatus = function() {
		//检查本身的展开状态-展开层级小于0时全部展开/本身层级大于展开层级时不展开
		if (this.parent.expandLevel >= -1 && this.parent.expandLevel < this.level) {
			this.expanded = false;
		}
		
		//存在子级行时修改展开/折叠(同时隐藏子级行)样式
		if (this.children.length > 0) {
			this._group.removeClass("default");
			if (this.expanded) this._group.addClass("expand");
			else {
				this._group.addClass("collapse");
				for (var index = 0; index < this.children.length; index ++) {
					this.children[index].hide();
				}
			}
		}
	}
	
	/**
	 * 设置交替行样式。
	 * @param add true表示添加样式，false表示移除样式。
	 */
	TreeDatagridRow.prototype.setAlternatingStyle = function(add) {
		if (add) {
			if (!this._row.hasClass("alternating")) this._row.addClass("alternating");
		} else {
			if (this._row.hasClass("alternating")) this._row.removeClass("alternating");
		}
	}
	
	/**
	 * 折叠。
	 */
	TreeDatagridRow.prototype.collapse = function() {
		if (this.children.length <= 0) return;
		
		this.expanded = false;									//修改属性
		this._group.removeClass("expand").addClass("collapse");	//修改样式
		this._collapseOrExpandChild(this.children, false);		//折叠或展开子级行
		this.parent.setAlternatingStyle();						//设置交替行样式
		this.parent.checkScroller();							//检查滚动条
	}
	
	/**
	 * 展开。
	 */
	TreeDatagridRow.prototype.expand = function() {
		if (this.children.length <= 0) return;
		
		this.expanded = true;									//修改属性
		this._group.removeClass("collapse").addClass("expand");	//修改样式
		this._collapseOrExpandChild(this.children, true);		//折叠或展开子级行
		this.parent.setAlternatingStyle();						//设置交替行样式
		this.parent.checkScroller();							//检查滚动条
	}
	
	/******************** 树形数据表格行定义结束 ********************/
	
	/******************** 事件参数定义开始 ********************/
	
	/**
	 * 事件参数构造方法。
	 */
	function EventArgs() {}
	
	/******************** 事件参数定义结束 ********************/
	
	/******************** 加载完成事件参数定义开始 ********************/
	
	/**
	 * 加载完成事件参数构造方法。
	 */
	function LoadedEventArgs(data) {
		EventArgs.call(this);
		
		//公共属性
		this.data = data;
	}
	
	/**
	 * 继承父类。
	 */
	LoadedEventArgs.prototype = new EventArgs();
	LoadedEventArgs.prototype.constructor = LoadedEventArgs;
	
	/******************** 加载完成事件参数定义结束 ********************/
	
	/******************** 单元格绘制事件参数定义开始 ********************/
	
	/**
	 * 单元格绘制事件参数构造方法。
	 * @param record 记录对象。
	 * @param column 列对象。
	 */
	function DrawcellEventArgs(record, column) {
		EventArgs.call(this);
		
		//公共属性
		this.html = null;
		this.record = record;
		this.column = column;
	}
	
	/**
	 * 继承父类。
	 */
	DrawcellEventArgs.prototype = new EventArgs();
	DrawcellEventArgs.prototype.constructor = DrawcellEventArgs;
	
	/******************** 单元格绘制事件参数定义结束 ********************/
	
	/******************** 行加载完成事件参数定义开始 ********************/
	
	/**
	 * 行加载完成事件参数构造方法。
	 * @param row 行对象。
	 * @param html 行HTML对象。
	 * @param position 行位置（one | first | middle | last）。
	 */
	function RowLoadedEventArgs(row, html, position){
		EventArgs.call(this);
		
		//公共属性
		this.row = row;
		this.html = html;
		this.position = position;
	}
	
	/**
	 * 继承父类。
	 */
	RowLoadedEventArgs.prototype = new EventArgs();
	RowLoadedEventArgs.prototype.constructor = RowLoadedEventArgs;
	
	/******************** 行加载完成事件参数定义结束 ********************/
	
	/******************** 树形节点点击事件参数定义开始 ********************/
	
	/**
	 * 树形节点点击事件参数构造方法。
	 * @param record 记录对象。
	 */
	function TreeNodeClickEventArgs(record) {
		EventArgs.call(this);
		
		//公共属性
		this.record = record;
	}
	
	/**
	 * 继承父类。
	 */
	TreeNodeClickEventArgs.prototype = new EventArgs();
	TreeNodeClickEventArgs.prototype.constructor = TreeNodeClickEventArgs;
	
	/******************** 树形节点点击事件参数定义结束 ********************/
	
	/******************** 验证事件参数定义开始 ********************/
	
	/**
	 * 验证事件参数构造方法。
	 * @param value 表单值。
	 */
	function ValidateEventArgs(value) {
		EventArgs.call(this);
		
		//公共属性
		this.value = value;
		this.message = null;
		this.pass = true;
	}
	
	/**
	 * 继承父类。
	 */
	ValidateEventArgs.prototype = new EventArgs();
	ValidateEventArgs.prototype.constructor = ValidateEventArgs;
	
	/******************** 验证事件参数定义结束 ********************/
	
	/******************** 数据表格选中改变事件参数定义开始 ********************/
	
	/**
	 * 数据表格选中改变事件参数构造方法。
	 * @param value 改变后的选中值。
	 * @param row 改变后的选中行。
	 */
	function DatagridSelectChangedEventArgs(value, row) {
		EventArgs.call(this);
		
		this.value = value;
		this.row = row;
	}
	
	/**
	 * 继承父类。
	 */
	DatagridSelectChangedEventArgs.prototype = new EventArgs();
	DatagridSelectChangedEventArgs.prototype.constructor = DatagridSelectChangedEventArgs;
	
	/******************** 数据表格选中改变事件参数定义结束 ********************/
	
	/******************** 按键抬起事件参数定义开始 ********************/
	
	/**
	 * 按键抬起事件参数构造方法。
	 * @param value 控件的值。
	 * @param text 控件的文本。
	 */
	function KeyupEventArgs(value, text) {
		EventArgs.call(this);
		
		this.value = value;
		this.text = text;
	}
	
	/**
	 * 继承父类。
	 */
	KeyupEventArgs.prototype = new EventArgs();
	KeyupEventArgs.prototype.constructor = KeyupEventArgs;
	
	/******************** 按键抬起事件参数定义结束 ********************/
	
	/******************** 失去焦点事件参数定义开始 ********************/
	
	/**
	 * 失去焦点事件参数构造方法。
	 * @param value 控件的值。
	 * @param text 控件的文本。
	 */
	function BlurEventArgs(value, text) {
		EventArgs.call(this);
		
		this.value = value;
		this.text = text;
	}
	
	/**
	 * 继承父类。
	 */
	BlurEventArgs.prototype = new EventArgs();
	BlurEventArgs.prototype.constructor = BlurEventArgs;
	
	/******************** 失去焦点事件参数定义结束 ********************/
	
	/******************** 值改变事件参数定义开始 ********************/
	
	/**
	 * 值改变事件参数构造方法。
	 * @param value 改变后的值。
	 * @param before 改变前的值。
	 */
	function ValueChangedEventArgs(value, before) {
		EventArgs.call(this);
		
		this.value = value;
		this.before = before;
	}
	
	/**
	 * 继承父类。
	 */
	ValueChangedEventArgs.prototype = new EventArgs();
	ValueChangedEventArgs.prototype.constructor = ValueChangedEventArgs;
	
	/******************** 值改变事件参数定义结束 ********************/
	
	/******************** 上下文菜单点击事件参数定义开始 ********************/
	
	/**
	 * 上下文菜单点击事件参数构造方法。
	 * @param html HTML对象。
	 * @param origin 事件源。
	 * @param custom 自定义数据。
	 */
	function ContextMenuClickEventArgs(html, origin, custom) {
		EventArgs.call(this);
		
		this.html = html;
		this.origin = origin;
		this.custom = custom;
	}
	
	/**
	 * 继承父类。
	 */
	ContextMenuClickEventArgs.prototype = new EventArgs();
	ContextMenuClickEventArgs.prototype.constructor = ContextMenuClickEventArgs;
	
	/******************** 上下文菜单点击事件参数定义结束 ********************/
	
	/******************** 树形定义开始 ********************/
	
	/**
	 * 树形构造方法。
	 */
	function Tree(){
		Control.call(this);
		
		//公共属性
		this.width = "150";
		this.url = null;
		this.idField = null;
		this.parentField = null;
		this.textField = null;
		this.expandLevel = 0;
		this.multiSelect = false;
		this.showIcon = false;
		this.nodes = new Array();
		
		//私有属性
		this._blured = true;
		this._expanded = false;
		this._dblclick = false;
		this._treeContainer = null;
		this._requestParams = null;
		
		//事件
		this.loaded = null;
		this.nodeclick = null;
	}
	
	/**
	 * 继承父类。
	 */
	Tree.prototype = new Control(null);
	Tree.prototype.constructor = Tree;
	
	/**
	 * 请求数据。
	 */
	Tree.prototype._requestData = function() {
		var invoker = this;
		
		//发起异步请求
		$.ajax({
			url : this.url,
			data : this._requestParams,
			success : function(data, status) {
				if (data.status) {
					invoker._renderData(data.result);
				} else {
					
				}
				
				//调用加载完成事件
				if (invoker.loaded) {
					var lea = new LoadedEventArgs(data);
					invoker.loaded.call(this, lea);
				}
			},
			error : function(xhr, msg, e) {
				console.log("status:" + xhr.status, "statusText:" + xhr.statusText);
			}
		});
	}
	
	/**
	 * 渲染数据。
	 * @param result 请求结果对象。
	 */
	Tree.prototype._renderData = function(result) {
		//设置无父级项的父级字段为空
		checkTopItem(result, this.idField, this.parentField);
		
		//清空原有数据
		this._treeContainer.empty();
		if(this.nodes.length > 0) {
			this.nodes = new Array();
		}
		
		var tree = null;
		var colspan = 4;
		if (!this.multiSelect) colspan --;
		if (!this.showIcon) colspan --;
		
		//加载数据
		var curnode = null;
		var currtid = null;
		var firstid = null;
		for (var index = 0; index < result.length; index ++) {
			var record = result[index];
			if (record[this.parentField]) continue;
			
			//创建树
			if (!tree) {
				var thtml = "<table width='100%' border='0' cellpadding='0' cellspacing='0' " + 
							"style='margin:0px; padding:0px;'></table>";
				tree = $(thtml);
			}
			
			//创建子级树
			var cnode = null;
			var ctree = this._renderChildren(result, record, colspan, null, (curnode == null));
			if (ctree) {
				//创建子级节点
				cnode = $("<tr><td colspan='" + colspan + "'></td></tr>");
				cnode.find("td").append(ctree);
				tree.append(cnode);
			} else {
				//子级图标
				var cicon = "";
				if (this.showIcon) 
					cicon = "<td property='icon' class='standartTreeImageLeaf' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>";
				
				//创建子级节点
				var text = record[this.textField];
				var line = (curnode == null) ? "standartTreeImageLevel" : "standartTreeImageLeveline";
				var chtml = "<tr property='node'>" + 
							"<td property='line' class='" + line + "' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>" + 
						    cicon + 
							"<td property='text' class='standartTreeRow'>" + 
							"<span class='selectedTreeRow' style='width:"+ (this.width - 100) +"px;'>" + text + "</span>" + 
							"</td>" + 
							"</tr>";
				cnode = $(chtml);
				tree.append(cnode);
				
				//设置提示
				var cnodetd = cnode.children("td[property='text']");
				cnodetd.attr("title", cnodetd.children("span").text());
				
				//构造子级节点对象
				var cnobj = new TreeNode(this, record, null);
				cnobj.initialize(cnode);
				this.nodes.push(cnobj);
			}
			
			//保存临时数据
			curnode = cnode;
			currtid = record[this.idField];
			if (firstid == null) firstid = currtid;
		}
		
		if (curnode) {
			//查找节点/集合元素并检查当前节点是否有子节点
			var settd = null;
			var haschild = false;
			var nodetd = curnode.children("td[property='line']");
			if (nodetd.length <= 0) {
				haschild = true;
				var nodetable = curnode.children("td").children("table");
				nodetd = nodetable.children("tr[property='node']").children("td[property='line']");
				settd = nodetable.children("tr[property='set']").children("td[property='line']");
			}
			
			//检查是否只有一个节点（第一个节点ID与当前节点ID相同时只有一个节点）
			if (firstid == currtid) {
				if (!haschild) nodetd.removeClass("standartTreeImageLevel").addClass("standartTreeImageLenone");
				else {
					nodetd.removeClass("standartTreeImageerjiLeout").addClass("standartTreeImagefirstunwind");
					settd.removeClass("line");
				}
			} else {
				if (!haschild) nodetd.removeClass("standartTreeImageLeveline").addClass("standartTreeImageLeout");
				else {
					nodetd.removeClass("standartTreeImagefolder").addClass("standartTreeImageerjiunwind");
					settd.removeClass("line");
				}
			}
		}
		
		for(var index = 0; index < this.nodes.length; index ++){
			var node = this.nodes[index];
			node.setDefaultStatus();
		}
		
		if (tree) this._treeContainer.append(tree);
	}
	
	/**
	 * 渲染子级节点。
	 * @param data 数据对象。
	 * @param precord 父记录。
	 * @param colspan 合并列数。
	 * @param ppnobj 父级父节点对象。
	 * @param firstnode 是否第一个节点。
	 * @returns 父级树。
	 */
	Tree.prototype._renderChildren = function(data, precord, colspan, ppnobj, firstnode) {
		var ptree = null;
		var pnobj = null;
		var children = null;
		
		//加载数据
		var curnode = null;
		for (var index = 0; index < data.length; index ++) {
			var record = data[index];
			if (record[this.parentField] != precord[this.idField]) continue;
			
			//创建父级树
			if (!ptree) {
				var pthtml = "<table width='100%' border='0' cellpadding='0' cellspacing='0' " + 
							 "style='margin:0px; padding:0px;'></table>";
				ptree = $(pthtml);
				//父级图标
				var picon = "";
				if (this.showIcon) 
					picon = "<td property='icon' class='standartTreeImagefolderOpen' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>";
				
				//创建父级节点
				var text = precord[this.textField];
				var line = (ppnobj) ? "standartTreeImagefolder" : ((firstnode) ? "standartTreeImageerjiLeout" : "standartTreeImagefolder");
				var pnhtml = "<tr property='node'>" + 
							 "<td property='line' class='" + line + "' align='absmiddle'>" + 
							 "<span></span>" + 
							 "</td>" + 
							 picon + 
							 "<td property='text' class='standartTreeRow'>" + 
							 "<span class='selectedTreeRow' style='width:"+ (this.width - 100) +"px;'>" + text + "</span>" + 
							 "</td>" + 
							 "</tr>";
				var pnode = $(pnhtml);
				ptree.append(pnode);
				
				//设置提示
				var pnodetd = pnode.children("td[property='text']");
				pnodetd.attr("title", pnodetd.children("span").text());
				
				//创建子级节点集合
				var nshtml = "<tr property='set'>" + 
							 "<td property='line' class='line'></td>" + 
							 "<td colspan='" + (colspan - 1) + "'>" + 
							 "<table width='100%' border='0' cellpadding='0' cellspacing='0' " + 
							 "style='margin:0px; padding:0px;'></table>" + 
							 "</td>" + 
							 "</tr>";
				var nodes = $(nshtml);
				children = nodes.find("table");
				ptree.append(nodes);
				
				//构造父级节点对象
				pnobj = new TreeNode(this, precord, nodes);
				pnobj.initialize(pnode);
				if (ppnobj) ppnobj.addChild(pnobj);
				else this.nodes.push(pnobj);
			}
			
			//创建子级树
			var cnode = null;
			var ctree = this._renderChildren(data, record, colspan, pnobj, false);
			if (ctree) {
				//创建子级节点
				cnode = $("<tr><td colspan='" + colspan + "'></td></tr>");
				cnode.find("td").append(ctree);
				children.append(cnode);
			} else {
				//子级图标
				var cicon = "";
				if (this.showIcon) 
					cicon = "<td property='icon' class='standartTreeImageLeaf' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>";
				
				//创建子级节点
				var text = record[this.textField];
				var chtml = "<tr property='node'>" + 
							"<td property='line' class='standartTreeImageLeveline' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>" + 
							cicon + 
							"<td property='text' class='standartTreeRow'>" + 
							"<span class='selectedTreeRow' style='width:"+ (this.width - 100) +"px;'>" + text + "</span>" + 
							"</td>" + 
							"</tr>";
				cnode = $(chtml);
				children.append(cnode);
				
				//设置提示
				var cnodetd = cnode.children("td[property='text']");
				cnodetd.attr("title", cnodetd.children("span").text());
				
				//构造子级节点对象
				var cnobj = new TreeNode(this, record, null);
				cnobj.initialize(cnode);
				pnobj.addChild(cnobj);
			}
			
			curnode = cnode;	//保存临时数据
		}
		
		if (curnode) {
			//查找节点/集合元素并检查当前节点是否有子节点
			var settd = null;
			var haschild = false;
			var nodetd = curnode.children("td[property='line']");
			if (nodetd.length <= 0) {
				haschild = true;
				var nodetable = curnode.children("td").children("table");
				nodetd = nodetable.children("tr[property='node']").children("td[property='line']");
				settd = nodetable.children("tr[property='set']").children("td[property='line']");
			}
			
			if (!haschild) nodetd.removeClass("standartTreeImageLeveline").addClass("standartTreeImageLeout");
			else {
				nodetd.removeClass("standartTreeImagefolder").addClass("standartTreeImageerjiunwind");
				settd.removeClass("line");
			}
		}
		
		return ptree;
	}
	
	/**
	 * 加载
	 * @param params 自定义参数
	 */
	Tree.prototype.load = function(params) {
		this._requestParams = {};
		if (params) copyObject(params, this._requestParams);
		
		//请求数据
		if (this.url) this._requestData();
	}
	
	/**
	 * 重新加载
	 */
	Tree.prototype.reload = function() {
		this._requestData();
	}
	
	/**
	 * 加载数据
	 * @param data 数组类型
	 */
	Tree.prototype.loadData = function(data){
		this._renderData(data);
		
		//调用加载完成事件
		if (this.loaded) {
			var lea = new LoadedEventArgs(data);
			this.loaded.call(this, lea);
		}
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	Tree.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//初始化属性
		this.url = getAttrValue(jqobj, "url", this.url);
		this.width = getAttrValue(jqobj, "width", this.width);
		this.idField = getAttrValue(jqobj, "idField", this.idField);
		this.parentField = getAttrValue(jqobj, "parentField", this.parentField);
		this.textField = getAttrValue(jqobj, "textField", this.textField);
		this.multiSelect = getBooleanAttrValue(jqobj, "multiSelect", this.multiSelect);
		this.showIcon = getBooleanAttrValue(jqobj,"showIcon",this.showIcon);
		this.expandLevel = getIntegerAttrValue(jqobj,"expandLevel",this.expandLevel);
		
		//初始化事件
		this.loaded = findMethod(jqobj.attr("onloaded"));
		this.nodeclick = findMethod(jqobj.attr("onnodeclick"));
		
		jqobj.css("width", this.width + "px");
		
		//树形框架
		var Thtml = "<div class='treebox'>" +
					"<div property = 'tree' class='treebox_tree'>" +
					"</div>" + 
					"</div>";
		
		var Tree = $(Thtml);
		jqobj.append(Tree);
		
		//初始化组件元素
		this._treeContainer = Tree.find("div[property='tree']");
		
		return true;
	}
	
	/******************** 树形列表节点定义开始 ********************/
	
	/**
	 * 树形下拉列表节点构造方法。
	 * @param parent 父级控件。
	 * @param record 节点记录。
	 * @param childset 子级集合。
	 */
	function TreeNode(parent, record, childset) {
		Control.call(this, parent);
		
		//公共属性
		this.level = 0;
		this.expanded = true;
		this.selected = false;
		this.children = new Array();
		this.record = record;
		
		//私有属性
		this._line = null;
		this._check = null;
		this._icon = null;
		this._text = null;
		this._timer = null;
		this._childset = childset;
	}
	
	/**
	 * 继承父类。
	 */
	TreeNode.prototype = new Control(null);
	TreeNode.prototype.constructor = TreeNode;
	
	/**
	 * 折叠。
	 */
	TreeNode.prototype._collapse = function() {
		this._childset.hide();	//隐藏子级
		
		if (this._line) {
			var oldcls = this._line.attr("class");
			this._line.removeClass(oldcls);
			
			switch(oldcls){
				case "standartTreeImagefirstunwind":
					this._line.addClass("standartTreeImagefirstshrink");
					break;
				case "standartTreeImagefolder":
					this._line.addClass("standartTreeImageplus");
					break;
				case "standartTreeImageerjiLeout":
					this._line.addClass("standartTreeImageerjiLevel");
					break;
				case "standartTreeImageerjiunwind":
					this._line.addClass("standartTreeImageerjiLenone");
					break;
			}
		}
		
		if (this._icon) 
			this._icon.removeClass("standartTreeImagefolderOpen").addClass("standartTreeImagefolderClosed");
	};
	
	/**
	 * 展开。
	 */
	TreeNode.prototype._expand = function() {
		this._childset.show();	//显示子级
		
		if (this._line){
			var oldcls = this._line.attr("class");
			this._line.removeClass(oldcls);
			
			switch(oldcls){
				case "standartTreeImagefirstshrink":
					this._line.addClass("standartTreeImagefirstunwind");
					break;
				case "standartTreeImageplus":
					this._line.addClass("standartTreeImagefolder");
					break;
				case "standartTreeImageerjiLevel":
					this._line.addClass("standartTreeImageerjiLeout");
					break;
				case "standartTreeImageerjiLenone":
					this._line.addClass("standartTreeImageerjiunwind");
					break;
			}
		}
		
		if (this._icon) 
			this._icon.removeClass("standartTreeImagefolderClosed").addClass("standartTreeImagefolderOpen");
	}
	
	/**
	 * 折叠或展开子级。
	 */
	TreeNode.prototype._collapseOrExpandChild = function() {
		if (!this._childset) return;
		
		if (this.expanded) {
			this.expanded = false;
			this._collapse();
		} else {
			this.expanded = true;
			this._expand();
		}
	}
	
	/**
	 * 选中或取消选中。
	 */
	TreeNode.prototype._selectOrDeselect = function() {
		if (this.parent.multiSelect) {
			if (this.selected) this.deselect();
			else this.select();
		}
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	TreeNode.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		var invoker = this;
		
		//初始化组件元素并添加事件
		this._line = jqobj.find("td[property='line']");
		this._line.bind({click : function(e) {
			invoker._collapseOrExpandChild.call(invoker);
		}});
		if (this.parent.showIcon) {
			this._icon = jqobj.find("td[property='icon']");
			this._icon.bind({dblclick : function(e) {
				invoker._collapseOrExpandChild.call(invoker);
			}});
		}
		this._text = jqobj.find("td[property='text']");
		this._text.bind({
			click : function(e) {
				if (invoker._timer) clearTimeout(invoker._timer);
				invoker._timer = setTimeout(function() {
					invoker._selectOrDeselect.call(invoker);
					
					if (invoker.parent.nodeclick) {
						var tncea = new TreeNodeClickEventArgs(invoker.record);
						invoker.parent.nodeclick.call(invoker, tncea);
					}
				}, 300);
			}, 
			dblclick : function(e) {
				if (invoker._timer) clearTimeout(invoker._timer);
				invoker._collapseOrExpandChild.call(invoker);
			}
		});
		
		return true;
	}
	
	/**
	 * 添加子级节点。
	 * @param child 子级节点对象。
	 */
	TreeNode.prototype.addChild = function(child) {
		child.level = this.level + 1;
		this.children.push(child);
	}
	
	/**
	 * 设置默认状态。
	 */
	TreeNode.prototype.setDefaultStatus = function() {
		//检查本身的展开状态-展开层级小于0时全部展开/本身层级大于展开层级时不展开
		if (this.parent.expandLevel >= -1 && this.parent.expandLevel < this.level) {
			this.expanded = false;
		}
		
		if (this._childset) {
			if(!this.expanded) this._collapse();
			
			for(var index = 0; index < this.children.length; index ++){
				var child = this.children[index];
				child.setDefaultStatus();
			}
		}
	}
	
	/**
	 * 选中。
	 */
	TreeNode.prototype.select = function() {
		this.selected = true;
		if (this._check) 
			this._check.removeClass("standartTreeImagelUncheckAll").addClass("standartTreeImagelCheckAll");
	}
	
	/**
	 * 取消选中。
	 */
	TreeNode.prototype.deselect = function() {
		this.selected = false;
		if (this._check) 
			this._check.removeClass("standartTreeImagelCheckAll").addClass("standartTreeImagelUncheckAll");
	}
	
	/******************** 树形列表节点定义结束 ********************/

	/******************** 树形定义结束 ********************/
	
	/******************** 下拉列表定义开始 ********************/
	
	/**
	 * 下拉列表构造方法。
	 */
	function Select() {
		Control.call(this);
		
		//公共属性
		this.width = "150";
		this.height = "24";
		this.dropdownHeight = "200";
		this.url = null;
		this.idField = null;
		this.textField = null;
		this.multiSelect = false;
		this.defaultValue = "";
		this.items = new Array();
		
		//私有属性
		this._blured = true;
		this._expanded = false;
		this._respBlur = true;
		this._textContainer = null;
		this._itemContainer = null;
		this._requestParams = null;
		this._tempValue = null;
		
		//事件
		this.loaded = null;
		this.onvaluechanged = null;
		this.keyup = null;
		this.blur = null;
	}
	
	/**
	 * 继承父类。
	 */
	Select.prototype = new Control(null);
	Select.prototype.constructor = Select;
	
	/**
	 * 请求数据。
	 */
	Select.prototype._requestData = function() {
		var invoker = this;
		
		//发起异步请求
		$.ajax({
			url : this.url,
			data : this._requestParams,
			success : function(data, status) {
				if (data.status) {
					invoker._renderData(data.result);
					invoker._innerSetValue(invoker.defaultValue);
				} else {
					
				}
				
				//调用加载完成事件
				if (invoker.loaded) {
					var lea = new LoadedEventArgs(data);
					invoker.loaded.call(this, lea);
				}
			},
			error : function(xhr, msg, e) {
				console.log("status:" + xhr.status, "statusText:" + xhr.statusText);
			}
		});
	}
	
	/**
	 * 渲染数据
	 * @param result 请求结果对象。
	 */
	Select.prototype._renderData = function(result) {
		//清空原有数据
		var ul = this._itemContainer.find("ul");
		ul.empty();
		if(this.items.length > 0){
			this.items = new Array();
		}
		
		//加载数据
		for (var index = 0; index < result.length; index ++) {
			var record = result[index];
			
			//检查是否组装复选框元素
			var img = "";
			if(this.multiSelect){
				img = "<img property='check'/>";
			}
			
			//创建下拉选项
			var text = record[this.textField];
			var html = $("<li> " + img + "<a href='javascript:void(0);'>" + text + "</a></li>");
			ul.append(html);
			
			//设置提示
			var showitem = html.children("a");
			showitem.attr("title", showitem.text());
			
			//构造下拉选项对象
			var item = new SelectItem(this, record);
			item.initialize(html);
			this.items.push(item);
		}
		
		//调整下拉面板高度
		if (result.length <= 0) {
			this._itemContainer.parent().height(parseInt(ul.css("min-height")) + ul.outerHeight());
		} else {
			var actheight = ul.find("li").outerHeight() * result.length + ul.outerHeight();
			if (actheight < parseInt(this.dropdownHeight)) {
				this._itemContainer.parent().height(actheight);
			} else {
				this._itemContainer.parent().height(this.dropdownHeight);
			}
		}
	}
	
	/**
	 * 取消所有选中。
	 */
	Select.prototype._deselectAll = function() {
		for (var index = 0; index < this.items.length; index ++) {
			var item = this.items[index];
			if (item.selected) item.deselect();
		}
	}
	
	/**
	 * 设置下拉列表的值。
	 * @param value 下拉列表的值。
	 */
	Select.prototype._innerSetValue = function(value) {
		this.setValue(value);
		this.checkValueChange();
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	Select.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//初始化属性
		this.url = getAttrValue(jqobj, "url", this.url);
		this.width = getAttrValue(jqobj, "width", this.width);
		this.height = getAttrValue(jqobj, "height", this.height);
		this.dropdownHeight = getAttrValue(jqobj, "dropdownHeight", this.dropdownHeight);
		this.idField = getAttrValue(jqobj, "idField", this.idField);
		this.textField = getAttrValue(jqobj, "textField", this.textField);
		this.multiSelect = getBooleanAttrValue(jqobj, "multiSelect", this.multiSelect);
		this.defaultValue = getAttrValue(jqobj, "defaultValue", this.defaultValue);
		
		//初始化事件
		this.loaded = findMethod(jqobj.attr("onloaded"));
		this.onvaluechanged = findMethod(jqobj.attr("onvaluechanged"));
		this.keyup = findMethod(jqobj.attr("onkeyup"));
		this.blur = findMethod(jqobj.attr("onblur"));
		
		//计算百分比大小
		this.width = calculatePercent(this.width, jqobj.parent().width());
		
		jqobj.css("width", this.width + "px");
		jqobj.css("height", this.height + "px");
		
		var holder = getAttrValue(jqobj, "placeHolder", "");
		if (holder != "") holder = "placeholder='" + holder + "'";
		
		//创建下拉列表框架
		var enabled = (this.enabled) ? "" : "disabled='disabled'";
		var editabled = (this.editabled) ? "" :  "readonly='readonly'" ;
		var html = "<div class='checkbox' style='width:"+this.width+"px;'>" + 
				   "<div class='select_province' style='width:"+this.width+"px;'>" + 
				   "<span property='click' style='background-position: " + (this.width - 15) + "px "+ (-2+(this.height -25)) +"px; height: "+this.height+"px;'>" + 
				   "<input property='text' " + enabled +" "+ editabled +" style='width: " + (this.width - 40) + "px; height: " + this.height + "px ;' " + holder + "/>" + 
				   "</span>" + 
				   "<div class='select' style='width: " + (this.width) + "px; height: "+ (this.dropdownHeight) + "px; margin-top:"+ (this.height - (-2))+"px;' >" +
				   "<div class='select_tree'  property='item'><ul></ul></div>" +
				   "</div>" + 
				   "</div>" + 
				   "</div>";
		var select = $(html);
		jqobj.append(select);
		
		//初始化组件元素
		this._textContainer = select.find("input[property='text']");
		this._itemContainer = select.find("div[property='item']");
		
		var invoker = this;
		
		//下拉列表添加点击事件
		if(this.enabled){
			select.find("span[property='click']").bind({"click":function(){
				invoker.showItems();
			}});
		}
		
		//文本容器添加失去焦点事件
		this._textContainer.bind(
			{"blur":function(){
				if (!invoker._respBlur) return;		//文本容器无需响应失去焦点事件
				
				if ($(":focus").children("div[property='item']").length > 0) {
					setTimeout(function() {
						invoker.setBlurState(true);
						invoker._textContainer.focus();
					}, 100);
					return;
				}
				
				setTimeout(function(){
					if (invoker._blured) {
						invoker.hideItem();
						
						//触发失去焦点事件
						if (invoker.blur) {
							var bea = new BlurEventArgs(invoker.getValue(), invoker._textContainer.val());
							invoker.blur.call(invoker, bea);
						}
					} else {
						invoker.setBlurState(true);
						invoker._textContainer.focus();
					}
				}, 100);
			}, 
			"keyup":function(){
				//触发按键抬起事件
				if (invoker.keyup) {
					var kea = new KeyupEventArgs(invoker.getValue(), invoker._textContainer.val());
					invoker.keyup.call(invoker, kea);
				}
			}}
		);
		
		//选项容器添加鼠标按下和抬起事件
		this._itemContainer.bind({
			mousedown : function(e) {
				//鼠标按下时（点击事件未触发）设置文本容器无需响应失去焦点事件
				invoker._respBlur = false;
			}, 
			mouseup : function(e) {
				//鼠标抬起时（点击事件已触发）设置文本容器可以响应失去焦点事件
				invoker._respBlur = true;
				
				//触发文本容器的失去焦点事件
				invoker._textContainer.blur();
			}
		});
		
		return true;
	}
	
	/**
	 * 加载
	 * @param params 自定义参数
	 */
	Select.prototype.load = function(params) {
		this._requestParams = {};
		if (params) copyObject(params, this._requestParams);
		
		//请求数据
		if (this.url) this._requestData();
	}
	
	/**
	 * 重新加载
	 */
	Select.prototype.reload = function() {
		this._requestData();
	}
	
	/**
	 * 加载数据
	 * @param data 数组类型
	 */
	Select.prototype.loadData = function(data){
		this._renderData(data);
		this._innerSetValue(this.defaultValue);
		
		//调用加载完成事件
		if (this.loaded) {
			var lea = new LoadedEventArgs(data);
			this.loaded.call(this, lea);
		}
	}
	
	/**
	 * 显示列表选项
	 */
	Select.prototype.showItems = function(){
		if(this._expanded){
			this._expanded = false;
			this._textContainer.blur();
			this._itemContainer.parent().hide();
		}else{
			this._expanded = true;
			this._textContainer.focus();
			this._itemContainer.parent().show();
		}
	}
	
	/**
	 * 隐藏列表选项
	 */
	Select.prototype.hideItem = function() {
		this._expanded = false;
		this._itemContainer.parent().hide();
	}
	
	/**
	 * 显示列表文本
	 */
	Select.prototype.showText = function(){
		var text = "";
		for(var index = 0; index < this.items.length; index ++){
			var item = this.items[index];
			
			if(!item.selected) continue;
			
			text += $("<span>" + item.record[this.textField] + "</span>").text() + ",";
		}
		
		if (text.length > 0) {
			text = text.substring(0, text.length - 1);
		}
		
		this._textContainer.val(text);
	}
	
	/**
	 * 清除选中
	 * @param itemid 当前选中项的ID
	 */
	Select.prototype.clearSelect = function(itemid){
		for(var index = 0; index < this.items.length; index ++){
			var item = this.items[index];
			if(itemid != item.record[this.idField]){
				item.deselect(); //设置不选中
			}
		}
	}
	
	/**
	 * 设置文本容器失去焦点状态。
	 * @param state 状态值。
	 */
	Select.prototype.setBlurState = function(state) {
		this._blured = state;
	}
	
	/**
	 * @see Control#validated(any)
	 */
	Select.prototype.validated = function(e) {
		if (e.pass) {
			this._textContainer.parent().removeClass("validate-no");
			this._textContainer.parent().removeAttr("title");
		} else {
			this._textContainer.parent().addClass("validate-no");
			this._textContainer.parent().attr("title", e.message);
		}
	}
	
	/**
	 * @see Control#setContrastValue(any)
	 */
	Select.prototype.setContrastValue = function(showvalue){
		this._textContainer.parent().css("border","1px dashed red");
		this._textContainer.parent().attr("title",showvalue);
	}
	
	/**
	 * @see Control#setEnabled(any)
	 */
	Select.prototype.setEnabled = function(enabled) {
		if (!Control.prototype.setEnabled.call(this, enabled)) return;
		
		if (this.enabled) {
			var invoker = this;
			this._textContainer.removeAttr("disabled");
			this._textContainer.parent().bind({"click":function(){
				invoker.showItems();
			}});
		} else {
			this._textContainer.attr("disabled", "disabled");
			this._textContainer.parent().unbind("click");
		}
	}
	
	/**
	 * 获取选中项。
	 * @returns 选中项对象。
	 */
	Select.prototype.getSelectedItem = function() {
		var selected = null;
		for(var index = 0; index < this.items.length; index ++) {
			var item = this.items[index];
			if (item.selected) {
				selected = item;
				break;
			}
		}
		return selected;
	}
	
	/**
	 * 获取选中项。
	 * @returns 选中项集合。
	 */
	Select.prototype.getSelectedItems = function() {
		var selecteds = new Array();
		for(var index = 0; index < this.items.length; index ++) {
			var item = this.items[index];
			if (item.selected) selecteds.push(item);
		}
		return selecteds;
	}
	
	/**
	 * 获取下拉列表的文本。
	 * @returns 下拉列表的文本。
	 */
	Select.prototype.getText = function() {
		return this._textContainer.val();
	}
	
	/**
	 * 设置下拉列表的文本。
	 * @param text 下拉列表的文本。
	 */
	Select.prototype.setText = function(text) {
		this._textContainer.val(text);
	}
	
	/**
	 * 获取下拉列表的值。
	 * @returns 下拉列表的值。
	 */
	Select.prototype.getValue = function() {
		var value = "";
		
		for(var index = 0; index < this.items.length; index ++){
			var item = this.items[index];
			
			if(!item.selected) continue;
			
			if (!this.multiSelect) return item.record[this.idField];	//单选时直接返回选中在第一项
			value += item.record[this.idField] + ",";					//多选时连接所有选中项
		}
		
		if (value.length > 0) {
			value = value.substring(0, value.length - 1);
		}
		
		return value;
	}
	
	/**
	 * 设置下拉列表的值。
	 * @param value 下拉列表的值。
	 */
	Select.prototype.setValue = function(value) {
		this._deselectAll();	//取消所有选中
		
		//检查传入参数是否包含多个值
		var values = null;
		if (typeof(value) === "string") {
			values = value.split(",");
		} else {
			values = new Array();
			values.push(value);
		}
		
		var text = "";
		
		for (var vi = 0; vi < values.length; vi ++) {
			for (var index = 0; index < this.items.length; index ++) {
				var item = this.items[index];
				
				if (values[vi] == item.record[this.idField]) {
					item.select();
					text += item.record[this.textField] + ",";
					break;
				}
			}
		}
		
		if (text.length > 0) {
			text = text.substring(0, text.length - 1);
		}
		
		this._textContainer.val(text);
		
		this.checkValueChange();	//检查值变化
	}
	
	/**
	 * 检查值改变。
	 */
	Select.prototype.checkValueChange = function() {
		if (!this.onvaluechanged) return;
		
		var current = this.getValue();
		if (current === this._tempValue) return;
		
		var vcea = new ValueChangedEventArgs(current, this._tempValue);
		
		this._tempValue = current;				//保持当前值
		this.onvaluechanged.call(this, vcea);	//触发事件
	}
	
	/******************** 下拉列表定义结束 ********************/
	
	/******************** 下拉列表项定义开始 ********************/
	
	/**
	 * 下拉列表项构造方法。
	 * @param parent 父级控件。
	 * @param record 项记录。
	 */
	function SelectItem(parent, record){
		Control.call(this,parent);
		
		//公共属性
		this.record = record;
		this.selected = false;
		
		//私有属性
		this._checkbox = null;
	}
	
	/**
	 * 继承父类。
	 */
	SelectItem.prototype = new Control(null);
	SelectItem.prototype.constructor = SelectItem;
	
	/**
	 * @see Control#initialize(any)
	 */
	SelectItem.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//初始化组件元素
		this._checkbox = jqobj.find("img[property='check']");
		
		//下拉项添加点击事件
		var invoker = this;
		jqobj.bind({"click":function(){
			if(invoker.parent.multiSelect){
				invoker.parent.setBlurState(false);
				
				if(invoker.selected){
					invoker.deselect();
				}else{
					invoker.select();
				}
			}else{
				invoker.parent.clearSelect(invoker.record[invoker.parent.idField]);
				invoker.select();
				invoker.parent.showItems();
			}
			
			invoker.parent.showText();
			invoker.parent.checkValueChange();
		}});
		
		return true;
	}
	
	/**
	 * 选中。
	 */
	SelectItem.prototype.select = function() {
		this.selected = true;
		if (this.parent.multiSelect) this._checkbox.addClass("provinceCheckAll");
	}
	
	/**
	 * 取消选中。
	 */
	SelectItem.prototype.deselect = function() {
		this.selected = false;
		if (this.parent.multiSelect) this._checkbox.removeClass("provinceCheckAll");
	}
	
	/******************** 下拉列表项定义结束 ********************/
	
	/******************** 树形下拉列表定义开始 ********************/
	
	/**
	 * 树形下拉列表构造方法。
	 */
	function TreeSelect() {
		Control.call(this);
		
		//公共属性
		this.width = "150";
		this.height = "24";
		this.dropdownHeight = "200";
		this.url = null;
		this.idField = null;
		this.parentField = null;
		this.textField = null;
		this.expandLevel = 0;
		this.multiSelect = false;
		this.showIcon = false;
		this.leafSelect = false;
		this.defaultValue = "";
		this.nodes = new Array();
		
		//私有属性
		this._blured = true;
		this._expanded = false;
		this._dblclick = false;
		this._respBlur = true;
		this._textContainer = null;
		this._treeContainer = null;
		this._requestParams = null;
		this._tempValue = null;
		
		//事件
		this.loaded = null;
		this.onvaluechanged = null;
		this.keyup = null;
		this.blur = null;
	}
	
	/**
	 * 继承父类。
	 */
	TreeSelect.prototype = new Control(null);
	TreeSelect.prototype.constructor = TreeSelect;
	
	/**
	 * 请求数据。
	 */
	TreeSelect.prototype._requestData = function() {
		var invoker = this;
		
		//发起异步请求
		$.ajax({
			url : this.url,
			data : this._requestParams,
			success : function(data, status) {
				if (data.status) {
					invoker._renderData(data.result);
					invoker._innerSetValue(invoker.defaultValue);
				} else {
					
				}
				
				//调用加载完成事件
				if (invoker.loaded) {
					var lea = new LoadedEventArgs(data);
					invoker.loaded.call(this, lea);
				}
			},
			error : function(xhr, msg, e) {
				console.log("status:" + xhr.status, "statusText:" + xhr.statusText);
			}
		});
	}
	
	/**
	 * 渲染数据。
	 * @param result 请求结果对象。
	 */
	TreeSelect.prototype._renderData = function(result) {
		//设置无父级项的父级字段为空
		checkTopItem(result, this.idField, this.parentField);
		
		//清空原有数据
		this._treeContainer.empty();
		if(this.nodes.length > 0) {
			this.nodes = new Array();
		}
		
		var tree = null;
		var colspan = 4;
		if (!this.multiSelect) colspan --;
		if (!this.showIcon) colspan --;
		
		//加载数据
		var curnode = null;
		var currtid = null;
		var firstid = null;
		for (var index = 0; index < result.length; index ++) {
			var record = result[index];
			if (record[this.parentField]) continue;
			
			//创建树
			if (!tree) {
				var thtml = "<table width='100%' border='0' cellpadding='0' cellspacing='0' " + 
							"style='margin:0px; padding:0px;'></table>";
				tree = $(thtml);
			}
			
			//创建子级树
			var cnode = null;
			var ctree = this._renderChildren(result, record, colspan, null, (curnode == null));
			if (ctree) {
				//创建子级节点
				cnode = $("<tr><td colspan='" + colspan + "'></td></tr>");
				cnode.find("td").append(ctree);
				tree.append(cnode);
			} else {
				//子级复选框
				var ccheck = "";
				if (this.multiSelect) 
					ccheck = "<td property='check' class='standartTreeImagelUncheckAll' align='absmiddle'>" + 
							 "<span></span>" + 
							 "</td>";
				
				//子级图标
				var cicon = "";
				if (this.showIcon) 
					cicon = "<td property='icon' class='standartTreeImageLeaf' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>";
				
				//创建子级节点
				var text = record[this.textField];
				var line = (curnode == null) ? "standartTreeImageLevel" : "standartTreeImageLeveline";
				var chtml = "<tr property='node'>" + 
							"<td property='line' class='" + line + "' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>" + 
							ccheck + cicon + 
							"<td property='text' class='standartTreeRow'>" + 
							"<span class='selectedTreeRow' style='width:"+ (this.width - 100) +"px;'>" + text + "</span>" + 
							"</td>" + 
							"</tr>";
				cnode = $(chtml);
				tree.append(cnode);
				
				//设置提示
				var cnodetd = cnode.children("td[property='text']");
				cnodetd.attr("title", cnodetd.children("span").text());
				
				//构造子级节点对象
				var cnobj = new TreeSelectNode(this, record, null);
				cnobj.initialize(cnode);
				this.nodes.push(cnobj);
			}
			
			//保存临时数据
			curnode = cnode;
			currtid = record[this.idField];
			if (firstid == null) firstid = currtid;
		}
		
		if (curnode) {
			//查找节点/集合元素并检查当前节点是否有子节点
			var settd = null;
			var haschild = false;
			var nodetd = curnode.children("td[property='line']");
			if (nodetd.length <= 0) {
				haschild = true;
				var nodetbody = curnode.children("td").children("table").children("tbody");
				nodetd = nodetbody.children("tr[property='node']").children("td[property='line']");
				settd = nodetbody.children("tr[property='set']").children("td[property='line']");
			}
			
			//检查是否只有一个节点（第一个节点ID与当前节点ID相同时只有一个节点）
			if (firstid == currtid) {
				if (!haschild) nodetd.removeClass("standartTreeImageLevel").addClass("standartTreeImageLenone");
				else {
					nodetd.removeClass("standartTreeImageerjiLeout").addClass("standartTreeImagefirstunwind");
					settd.removeClass("line");
				}
			} else {
				if (!haschild) nodetd.removeClass("standartTreeImageLeveline").addClass("standartTreeImageLeout");
				else {
					nodetd.removeClass("standartTreeImagefolder").addClass("standartTreeImageerjiunwind");
					settd.removeClass("line");
				}
			}
		}
		
		for(var index = 0; index < this.nodes.length; index ++){
			var node = this.nodes[index];
			node.setDefaultStatus();
		}
		
		if (tree) this._treeContainer.append(tree);
	}
	
	/**
	 * 渲染子级节点。
	 * @param data 数据对象。
	 * @param precord 父记录。
	 * @param colspan 合并列数。
	 * @param ppnobj 父级父节点对象。
	 * @param firstnode 是否第一个节点。
	 * @returns 父级树。
	 */
	TreeSelect.prototype._renderChildren = function(data, precord, colspan, ppnobj, firstnode) {
		var ptree = null;
		var pnobj = null;
		var children = null;
		
		//加载数据
		var curnode = null;
		for (var index = 0; index < data.length; index ++) {
			var record = data[index];
			if (record[this.parentField] != precord[this.idField]) continue;
			
			//创建父级树
			if (!ptree) {
				var pthtml = "<table width='100%' border='0' cellpadding='0' cellspacing='0' " + 
							 "style='margin:0px; padding:0px;'></table>";
				ptree = $(pthtml);
				
				//父级复选框
				var pcheck = "";
				if (this.multiSelect) 
					pcheck = "<td property='check' class='standartTreeImagelUncheckAll' align='absmiddle'>" + 
							 ((this.leafSelect) ? "" : "<span></span>") + 
							 "</td>";
				
				//父级图标
				var picon = "";
				if (this.showIcon) 
					picon = "<td property='icon' class='standartTreeImagefolderOpen' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>";
				
				//创建父级节点
				var text = precord[this.textField];
				var line = (ppnobj) ? "standartTreeImagefolder" : ((firstnode) ? "standartTreeImageerjiLeout" : "standartTreeImagefolder");
				var pnhtml = "<tr property='node'>" + 
							 "<td property='line' class='" + line + "' align='absmiddle'>" + 
							 "<span></span>" + 
							 "</td>" + 
							 pcheck + picon + 
							 "<td property='text' class='standartTreeRow'>" + 
							 "<span class='selectedTreeRow' style='width:"+ (this.width - 100) +"px;'>" + text + "</span>" + 
							 "</td>" + 
							 "</tr>";
				var pnode = $(pnhtml);
				ptree.append(pnode);
				
				//设置提示
				var pnodetd = pnode.children("td[property='text']");
				pnodetd.attr("title", pnodetd.children("span").text());
				
				//创建子级节点集合
				var nshtml = "<tr property='set'>" + 
							 "<td property='line' class='line'></td>" + 
							 "<td colspan='" + (colspan - 1) + "'>" + 
							 "<table width='100%' border='0' cellpadding='0' cellspacing='0' " + 
							 "style='margin:0px; padding:0px;'></table>" + 
							 "</td>" + 
							 "</tr>";
				var nodes = $(nshtml);
				children = nodes.find("table");
				ptree.append(nodes);
				
				//构造父级节点对象
				pnobj = new TreeSelectNode(this, precord, nodes);
				pnobj.initialize(pnode);
				if (ppnobj) ppnobj.addChild(pnobj);
				else this.nodes.push(pnobj);
			}
			
			//创建子级树
			var cnode = null;
			var ctree = this._renderChildren(data, record, colspan, pnobj,false);
			if (ctree) {
				//创建子级节点
				cnode = $("<tr><td colspan='" + colspan + "'></td></tr>");
				cnode.find("td").append(ctree);
				children.append(cnode);
			} else {
				//子级复选框
				var ccheck = "";
				if (this.multiSelect) 
					ccheck = "<td property='check' class='standartTreeImagelUncheckAll' align='absmiddle'>" + 
							 "<span></span>" + 
							 "</td>";
				
				//子级图标
				var cicon = "";
				if (this.showIcon) 
					cicon = "<td property='icon' class='standartTreeImageLeaf' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>";
				
				//创建子级节点
				var text = record[this.textField];
				var chtml = "<tr property='node'>" + 
							"<td property='line' class='standartTreeImageLeveline' align='absmiddle'>" + 
							"<span></span>" + 
							"</td>" + 
							ccheck + cicon + 
							"<td property='text' class='standartTreeRow'>" + 
							"<span class='selectedTreeRow' style='width:"+ (this.width - 100) +"px;'>" + text + "</span>" + 
							"</td>" + 
							"</tr>";
				cnode = $(chtml);
				children.append(cnode);
				
				//设置提示
				var cnodetd = cnode.children("td[property='text']");
				cnodetd.attr("title", cnodetd.children("span").text());
				
				//构造子级节点对象
				var cnobj = new TreeSelectNode(this, record, null);
				cnobj.initialize(cnode);
				pnobj.addChild(cnobj);
			}
			
			curnode = cnode;	//保存临时数据
		}
		
		if (curnode) {
			//查找节点/集合元素并检查当前节点是否有子节点
			var settd = null;
			var haschild = false;
			var nodetd = curnode.children("td[property='line']");
			if (nodetd.length <= 0) {
				haschild = true;
				var nodetbody = curnode.children("td").children("table").children("tbody");
				nodetd = nodetbody.children("tr[property='node']").children("td[property='line']");
				settd = nodetbody.children("tr[property='set']").children("td[property='line']");
			}
			
			if (!haschild) nodetd.removeClass("standartTreeImageLeveline").addClass("standartTreeImageLeout");
			else {
				nodetd.removeClass("standartTreeImagefolder").addClass("standartTreeImageerjiunwind");
				settd.removeClass("line");
			}
		}
		
		return ptree;
	}
	
	/**
	 * 清除选中。
	 * @param nodeid 当前选中节点的ID。
	 * @param children 子节点集合。
	 */
	TreeSelect.prototype._clearSelect = function(nodeid, children) {
		for (var index = 0; index < children.length; index ++) {
			var node = children[index];
			if (nodeid != node.record[this.idField]) {
				node.deselect();
			}
			
			this._clearSelect(nodeid, node.children);
		}
	}
	
	/**
	 * 显示列表文本。
	 * @param children 子节点集合。
	 * @returns 显示文本字符串。
	 */
	TreeSelect.prototype._showText = function(children) {
		var text = "";
		for (var index = 0; index < children.length; index ++) {
			var node = children[index];
			
			if (node.selected) text += $("<span>" + node.record[this.textField] + "</span>").text() + ",";
			text += this._showText(node.children);
		}
		return text;
	}
	
	/**
	 * 获取选中节点。
	 * @param children 子节点集合。
	 * @returns 选中节点对象。
	 */
	TreeSelect.prototype._getSelectedNode = function(children) {
		var selected = null;
		
		for (var index = 0; index < children.length; index ++) {
			var node = children[index];
			
			if (node.selected) {
				selected = node;
				break;
			}
			
			selected = this._getSelectedNode(node.children);
			if (selected) break;
		}
		
		return selected;
	}
	
	/**
	 * 获取选中节点。
	 * @param children 子节点集合。
	 * @param selecteds 选中节点集合。
	 */
	TreeSelect.prototype._getSelectedNodes = function(children, selecteds) {
		for (var index = 0; index < children.length; index ++) {
			var node = children[index];
			if (node.selected) selecteds.push(node);
			this._getSelectedNodes(node.children, selecteds);
		}
	}
	
	/**
	 * 获取树形下拉列表的值。
	 * @param children 子节点集合。
	 * @returns 树形下拉列表的值。
	 */
	TreeSelect.prototype._getValue = function(children) {
		var value = "";
		
		for (var index = 0; index < children.length; index ++) {
			var node = children[index];
			
			if (node.selected) {
				if (!this.multiSelect) return node.record[this.idField];	//单选时直接返回选中在第一项
				value += node.record[this.idField] + ",";					//多选时连接所有选中项
			}
			
			var temp = this._getValue(node.children);
			if (temp == "") continue;	//子级无选中项
			
			if (!this.multiSelect) return temp;	//单选时直接返回子级选中项
			value += temp;						//多选时连接所有选中项
		}
		
		return value;
	}
	
	/**
	 * 设置树形下拉列表的值。
	 * @param value 树形下拉列表的值。
	 * @param children 子节点集合。
	 * @returns 显示文本字符串。
	 */
	TreeSelect.prototype._setValue = function(value, children) {
		var text = "";
		for (var index = 0; index < children.length; index ++) {
			var node = children[index];
			
			if (value == node.record[this.idField]) {
				if (this.leafSelect && node.children.length > 0) break;
				
				node.select();
				text = node.record[this.textField] + ",";
				break;
			}
			
			text = this._setValue(value, node.children);
			if (text) break;
		}
		return text;
	}
	
	/**
	 * 取消所有选中。
	 * @param nodes 节点对象集合。
	 */
	TreeSelect.prototype._deselectAll = function(nodes) {
		for (var index = 0; index < nodes.length; index ++) {
			var node = nodes[index];
			if (node.selected) node.deselect();
			
			this._deselectAll(node.children);
		}
	}
	
	/**
	 * 设置树形下拉列表的值。
	 * @param value 树形下拉列表的值。
	 */
	TreeSelect.prototype._innerSetValue = function(value) {
		this.setValue(value);
		this.checkValueChange();
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	TreeSelect.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//初始化属性
		this.url = getAttrValue(jqobj, "url", this.url);
		this.width = getAttrValue(jqobj, "width", this.width);
		this.height = getAttrValue(jqobj, "height", this.height);
		this.dropdownHeight =  getAttrValue(jqobj, "dropdownHeight", this.dropdownHeight);
		this.idField = getAttrValue(jqobj, "idField", this.idField);
		this.parentField = getAttrValue(jqobj, "parentField", this.parentField);
		this.textField = getAttrValue(jqobj, "textField", this.textField);
		this.multiSelect = getBooleanAttrValue(jqobj, "multiSelect", this.multiSelect);
		this.showIcon = getBooleanAttrValue(jqobj, "showIcon", this.showIcon);
		this.leafSelect = getBooleanAttrValue(jqobj, "leafSelect", this.leafSelect);
		this.defaultValue = getAttrValue(jqobj, "defaultValue", this.defaultValue);
		this.expandLevel = getIntegerAttrValue(jqobj, "expandLevel", this.expandLevel);
		
		//初始化事件
		this.loaded = findMethod(jqobj.attr("onloaded"));
		this.onvaluechanged = findMethod(jqobj.attr("onvaluechanged"));
		this.keyup = findMethod(jqobj.attr("onkeyup"));
		this.blur = findMethod(jqobj.attr("onblur"));
		
		jqobj.css("width", this.width + "px");
		
		var holder = getAttrValue(jqobj, "placeHolder", "");
		if (holder != "") holder = "placeholder='" + holder + "'";
		
		//创建树形下拉列表框架
		var enabled = (this.enabled) ? "" : "disabled='disabled'";
		var editabled = (this.editabled) ? "" : "readonly='readonly'";
		var html = "<div class='treeselect_box' style='width:"+this.width+"px;'>" + 
				   "<div class='treeselect_title' style='width:"+this.width+"px;'>" + 
				   "<span property='click' style='width:"+(this.width - 4)+"px; background-position:"+(this.width - 15)+"px " + (-2+(this.height -25)) +"px;'>" + 
				   "<input property='text' "+ enabled +" "+ editabled +" style='width:"+(this.width - 30)+"px; height:" + this.height + "px; border:none;' " + holder + "/>" + 
				   "</span>" + 
				   "</div>" + 
				   "<div class='treeselect' style='width:"+this.width+"px; height:"+ this.dropdownHeight +"px; margin-top:"+(this.height-(-2))+"px;'>" + 
				   "<div property='tree' class='treeselect_tree'></div>" + 
				   "</div>" + 
				   "</div>";
		var treeselect = $(html);
		jqobj.append(treeselect);
		
		//初始化组件元素
		this._textContainer = treeselect.find("input[property='text']");
		this._treeContainer = treeselect.find("div[property='tree']");
		
		var invoker = this;
		
		//树形下拉列表添加点击事件
		if(this.enabled){
			treeselect.find("span[property='click']").bind({click : function(e) {
				invoker.showTree();
			}});
		}
		
		//文本容器添加失去焦点事件
		this._textContainer.bind(
			{blur : function(e) {
				if (!invoker._respBlur) return;		//文本容器无需响应失去焦点事件
				
				if ($(":focus").children("div[property='tree']").length > 0) {
					setTimeout(function(){
						invoker.setBlurState(true);
						invoker._textContainer.focus();
					}, 100);
					return;
				}
				
				setTimeout(function(){
					if (invoker._blured) {
						invoker.hideTree();
						
						//触发失去焦点事件
						if (invoker.blur) {
							var bea = new BlurEventArgs(invoker.getValue(), invoker._textContainer.val());
							invoker.blur.call(invoker, bea);
						}
					} else {
						invoker._dblclick = true;
						invoker.setBlurState(true);
						invoker._textContainer.focus();
					}
				}, 100);
			}, 
			keyup : function(e) {
				//触发按键抬起事件
				if (invoker.keyup) {
					var kea = new KeyupEventArgs(invoker.getValue(), invoker._textContainer.val());
					invoker.keyup.call(invoker, kea);
				}
			}}
		);
		
		//树容器添加点击事件、鼠标按下和抬起事件
		this._treeContainer.bind({
			click : function(e) {
				if (isMsie()) {
					if (!invoker._dblclick) {
						invoker.setBlurState(false);
					}
					
					setTimeout(function() {
						if (invoker._dblclick) {
							invoker._dblclick = false;
						}
					}, 200);
				} else {
					invoker.setBlurState(false);
				}
			}, 
			mousedown : function(e) {
				//鼠标按下时（点击事件未触发）设置文本容器无需响应失去焦点事件
				invoker._respBlur = false;
			}, 
			mouseup : function(e) {
				//鼠标抬起时（点击事件已触发）设置文本容器可以响应失去焦点事件
				invoker._respBlur = true;
				
				//触发文本容器的失去焦点事件
				invoker._textContainer.blur();
			}
		});
		
		return true;
	}
	
	/**
	 * 加载。
	 * @param params 自定义参数。
	 */
	TreeSelect.prototype.load = function(params) {
		this._requestParams = {};
		if (params) copyObject(params, this._requestParams);
		
		//请求数据
		if (this.url) this._requestData();
	}
	
	/**
	 * 重新加载。
	 */
	TreeSelect.prototype.reload = function() {
		this._requestData();
	}
	
	/**
	 * 加载数据
	 * @param data 数组类型
	 */
	TreeSelect.prototype.loadData = function(data){
		this._renderData(data);
		this._innerSetValue(this.defaultValue);
		
		//调用加载完成事件
		if (this.loaded) {
			var lea = new LoadedEventArgs(data);
			this.loaded.call(this, lea);
		}
	}
	
	/**
	 * 显示列表树。
	 */
	TreeSelect.prototype.showTree = function() {
		if(this._expanded) {
			this._expanded = false;
			this._textContainer.blur();
			this._treeContainer.parent().hide();
		} else {
			this._expanded = true;
			this._textContainer.focus();
			this._treeContainer.parent().show();
		}
	}
	
	/**
	 * 隐藏列表树。
	 */
	TreeSelect.prototype.hideTree = function() {
		this._expanded = false;
		this._treeContainer.parent().hide();
	}
	
	/**
	 * 设置文本容器失去焦点状态。
	 * @param state 状态值。
	 */
	TreeSelect.prototype.setBlurState = function(state) {
		this._blured = state;
	}
	
	/**
	 * 清除选中。
	 * @param nodeid 当前选中节点的ID。
	 */
	TreeSelect.prototype.clearSelect = function(nodeid) {
		for (var index = 0; index < this.nodes.length; index ++) {
			var node = this.nodes[index];
			if (nodeid != node.record[this.idField]) {
				node.deselect();
			}
			
			this._clearSelect(nodeid, node.children);
		}
	}
	
	/**
	 * 显示列表文本。
	 */
	TreeSelect.prototype.showText = function() {
		var text = "";
		for (var index = 0; index < this.nodes.length; index ++) {
			var node = this.nodes[index];
			
			if (node.selected) text += $("<span>" + node.record[this.textField] + "</span>").text() + ",";
			text += this._showText(node.children);
		}
		
		if (text.length > 0) {
			text = text.substring(0, text.length - 1);
		}
		
		this._textContainer.val(text);
	}
	
	/**
	 * @see Control#validated(any)
	 */
	TreeSelect.prototype.validated = function(e) {
		if (e.pass) {
			this._textContainer.parent().removeClass("validate-no");
			this._textContainer.parent().removeAttr("title");
		} else {
			this._textContainer.parent().addClass("validate-no");
			this._textContainer.parent().attr("title", e.message);
		}
	}
	
	
	/**
	 * @see Control#setContrastValue(any)
	 */
	TreeSelect.prototype.setContrastValue = function(showvalue){
		this._textContainer.parent().css("border","1px dashed red");
		this._textContainer.parent().attr("title",showvalue);
	}
	
	/**
	 * @see Control#setEnabled(any)
	 */
	TreeSelect.prototype.setEnabled = function(enabled) {
		if (!Control.prototype.setEnabled.call(this, enabled)) return;
		
		if (this.enabled) {
			var invoker = this;
			this._textContainer.removeAttr("disabled");
			this._textContainer.parent().bind({click : function(e) {
				invoker.showTree();
			}});
		} else {
			this._textContainer.attr("disabled", "disabled");
			this._textContainer.parent().unbind("click");
		}
	}
	
	/**
	 * 获取选中节点。
	 * @returns 选中节点对象。
	 */
	TreeSelect.prototype.getSelectedNode = function() {
		var selected = null;
		
		for (var index = 0; index < this.nodes.length; index ++) {
			var node = this.nodes[index];
			
			if (node.selected) {
				selected = node;
				break;
			}
			
			selected = this._getSelectedNode(node.children);
			if (selected) break;
		}
		
		return selected;
	}
	
	/**
	 * 获取选中节点。
	 * @returns 选中节点集合。
	 */
	TreeSelect.prototype.getSelectedNodes = function() {
		var selecteds = new Array();
		
		for (var index = 0; index < this.nodes.length; index ++) {
			var node = this.nodes[index];
			if (node.selected) selecteds.push(node);
			this._getSelectedNodes(node.children, selecteds);
		}
		
		return selecteds;
	}
	
	/**
	 * 获取树形下拉列表的文本。
	 * @returns 树形下拉列表的文本。
	 */
	TreeSelect.prototype.getText = function() {
		return this._textContainer.val();
	}
	
	/**
	 * 设置树形下拉列表的文本。
	 * @param text 树形下拉列表的文本。
	 */
	TreeSelect.prototype.setText = function(text) {
		this._textContainer.val(text);
	}
	
	/**
	 * 获取树形下拉列表的值。
	 * @returns 树形下拉列表的值。
	 */
	TreeSelect.prototype.getValue = function() {
		var value = "";
		
		for (var index = 0; index < this.nodes.length; index ++) {
			var node = this.nodes[index];
			
			if (node.selected) {
				if (!this.multiSelect) return node.record[this.idField];	//单选时直接返回选中在第一项
				value += node.record[this.idField] + ",";					//多选时连接所有选中项
			}
			
			var temp = this._getValue(node.children);
			if (temp == "") continue;	//子级无选中项
			
			if (!this.multiSelect) return temp;	//单选时直接返回子级选中项
			value += temp;						//多选时连接所有选中项
		}
		
		if (value.length > 0) {
			value = value.substring(0, value.length - 1);
		}
		
		return value;
	}
	
	/**
	 * 设置树形下拉列表的值。
	 * @param value 树形下拉列表的值。
	 */
	TreeSelect.prototype.setValue = function(value) {
		this._deselectAll(this.nodes);	//取消所有选中
		
		//检查传入参数是否包含多个值
		var values = null;
		if (typeof(value) === "string") {
			values = value.split(",");
		} else {
			values = new Array();
			values.push(value);
		}
		
		var text = "";
		
		for (var vi = 0; vi < values.length; vi ++) {
			for (var index = 0; index < this.nodes.length; index ++) {
				var node = this.nodes[index];
				
				if (values[vi] == node.record[this.idField]) {
					if (this.leafSelect && node.children.length > 0) break;
					
					node.select();
					text += node.record[this.textField] + ",";
					break;
				}
				
				var temp = this._setValue(values[vi], node.children);
				if (temp) {
					text += temp;
					break;
				}
			}
		}
		
		if (text.length > 0) {
			text = text.substring(0, text.length - 1);
		}
		
		this._textContainer.val(text);
		
		this.checkValueChange();	//检查值变化
	}
	
	/**
	 * 检查值改变。
	 */
	TreeSelect.prototype.checkValueChange = function() {
		if (!this.onvaluechanged) return;
		
		var current = this.getValue();
		if (current === this._tempValue) return;
		
		var vcea = new ValueChangedEventArgs(current, this._tempValue);
		
		this._tempValue = current;				//保持当前值
		this.onvaluechanged.call(this, vcea);	//触发事件
	}
	
	/******************** 树形下拉列表定义结束 ********************/
	
	/******************** 树形下拉列表节点定义开始 ********************/
	
	/**
	 * 树形下拉列表节点构造方法。
	 * @param parent 父级控件。
	 * @param record 节点记录。
	 * @param childset 子级集合。
	 */
	function TreeSelectNode(parent, record, childset) {
		Control.call(this, parent);
		
		//公共属性
		this.level = 0;
		this.expanded = true;
		this.selected = false;
		this.children = new Array();
		this.record = record;
		this.parentNode = null;
		
		//私有属性
		this._line = null;
		this._check = null;
		this._icon = null;
		this._text = null;
		this._timer = null;
		this._childset = childset;
	}
	
	/**
	 * 继承父类。
	 */
	TreeSelectNode.prototype = new Control(null);
	TreeSelectNode.prototype.constructor = TreeSelectNode;
	
	/**
	 * 折叠。
	 */
	TreeSelectNode.prototype._collapse = function() {
		this._childset.hide();	//隐藏子级
		
		if (this._line) {
			var oldcls = this._line.attr("class");
			this._line.removeClass(oldcls);
			
			switch(oldcls){
				case "standartTreeImagefirstunwind":
					this._line.addClass("standartTreeImagefirstshrink");
					break;
				case "standartTreeImagefolder":
					this._line.addClass("standartTreeImageplus");
					break;
				case "standartTreeImageerjiLeout":
					this._line.addClass("standartTreeImageerjiLevel");
					break;
				case "standartTreeImageerjiunwind":
					this._line.addClass("standartTreeImageerjiLenone");
					break;
			}
		}
		
		if (this._icon) 
			this._icon.removeClass("standartTreeImagefolderOpen").addClass("standartTreeImagefolderClosed");
	};
	
	/**
	 * 展开。
	 */
	TreeSelectNode.prototype._expand = function() {
		this._childset.show();	//显示子级
		
		if (this._line){
			var oldcls = this._line.attr("class");
			this._line.removeClass(oldcls);
			
			switch(oldcls){
				case "standartTreeImagefirstshrink":
					this._line.addClass("standartTreeImagefirstunwind");
					break;
				case "standartTreeImageplus":
					this._line.addClass("standartTreeImagefolder");
					break;
				case "standartTreeImageerjiLevel":
					this._line.addClass("standartTreeImageerjiLeout");
					break;
				case "standartTreeImageerjiLenone":
					this._line.addClass("standartTreeImageerjiunwind");
					break;
			}
		}
		
		if (this._icon) 
			this._icon.removeClass("standartTreeImagefolderClosed").addClass("standartTreeImagefolderOpen");
	}
	
	/**
	 * 折叠或展开子级。
	 */
	TreeSelectNode.prototype._collapseOrExpandChild = function() {
		if (!this._childset) return;
		
		if (this.expanded) {
			this.expanded = false;
			this._collapse();
		} else {
			this.expanded = true;
			this._expand();
		}
	}
	
	/**
	 * 选中或取消选中。
	 */
	TreeSelectNode.prototype._selectOrDeselect = function() {
		if (this.parent.leafSelect && this.children.length > 0) return;
		
		if (this.parent.multiSelect) {
			if (this.selected) this.deselect();
			else this.select();
		} else {
			this.parent.clearSelect(this.record[this.parent.idField]);
			this.select();
			this.parent.showTree();
		}
		
		this.parent.showText();
		this.parent.checkValueChange();
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	TreeSelectNode.prototype.initialize = function(jqobj) {
		if (!Control.prototype.initialize.call(this, jqobj)) return false;
		
		var invoker = this;
		
		//初始化组件元素并添加事件
		this._line = jqobj.find("td[property='line']");
		this._line.bind({click : function(e) {
			invoker._collapseOrExpandChild.call(invoker);
		}});
		if (this.parent.multiSelect) {
			this._check = jqobj.find("td[property='check']");
			this._check.bind({click : function(e) {
				invoker._selectOrDeselect.call(invoker);
			}});
		}
		if (this.parent.showIcon) {
			this._icon = jqobj.find("td[property='icon']");
			this._icon.bind({dblclick : function(e) {
				invoker._collapseOrExpandChild.call(invoker);
			}});
		}
		this._text = jqobj.find("td[property='text']");
		this._text.bind({
			click : function(e) {
				if (invoker._timer) clearTimeout(invoker._timer);
				invoker._timer = setTimeout(function() { invoker._selectOrDeselect.call(invoker); }, 300);
			}, 
			dblclick : function(e) {
				if (invoker._timer) clearTimeout(invoker._timer);
				invoker._collapseOrExpandChild.call(invoker);
			}
		});
		
		return true;
	}
	
	/**
	 * 添加子级节点。
	 * @param child 子级节点对象。
	 */
	TreeSelectNode.prototype.addChild = function(child) {
		child.level = this.level + 1;
		child.parentNode = this;
		this.children.push(child);
	}
	
	/**
	 * 设置默认状态。
	 */
	TreeSelectNode.prototype.setDefaultStatus = function() {
		if (this.parent.expandLevel>= -1 && this.parent.expandLevel < this.level) {
			this.expanded = false;
		}
		
		if (this._childset) {
			if(!this.expanded) this._collapse();
			
			for(var index = 0; index < this.children.length; index ++){
				var child = this.children[index];
				child.setDefaultStatus();
			}
		}
	}
	
	/**
	 * 选中。
	 */
	TreeSelectNode.prototype.select = function() {
		this.selected = true;
		if (this._check) 
			this._check.removeClass("standartTreeImagelUncheckAll").addClass("standartTreeImagelCheckAll");
	}
	
	/**
	 * 取消选中。
	 */
	TreeSelectNode.prototype.deselect = function() {
		this.selected = false;
		if (this._check) 
			this._check.removeClass("standartTreeImagelCheckAll").addClass("standartTreeImagelUncheckAll");
	}
	
	/******************** 树形下拉列表节点定义结束 ********************/
	
	/******************** 文本框定义开始 ********************/
	
	/**
	 * 定义单行构造方法
	 */
	function Textbox(){
		Control.call(this);
		
		//公共属性
		this.width = "300";
		this.height = "30";
		
		//私有属性
		this._textboxhtml = null;
		this._tempValue = null;
		
		//事件
		this.onvaluechanged = null;
		this.keyup = null;
		this.blur = null;
	}
	
	/**
	 * 继承父类。
	 */
	Textbox.prototype = new Control(null);
	Textbox.prototype.constructor = Textbox;
	
	/**
	 * 检查值改变。
	 */
	Textbox.prototype._checkValueChange = function() {
		if (!this.onvaluechanged) return;
		
		var current = this.getValue();
		if (current === this._tempValue) return;
		
		var vcea = new ValueChangedEventArgs(current, this._tempValue);
		
		this._tempValue = current;				//保持当前值
		this.onvaluechanged.call(this, vcea);	//触发事件
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	Textbox.prototype.initialize = function(jqobj){
		if(!Control.prototype.initialize.call(this,jqobj)) return false;
		
		//初始化属性
		this.width = getAttrValue(jqobj,"width",this.width);
		this.height = getAttrValue(jqobj,"height",this.height);
		
		var holder = getAttrValue(jqobj, "placeHolder", "");
		if (holder != "") holder = "placeholder='" + holder + "'";
		
		//初始化事件
		this.onvaluechanged = findMethod(jqobj.attr("onvaluechanged"));
		this.keyup = findMethod(jqobj.attr("onkeyup"));
		this.blur = findMethod(jqobj.attr("onblur"));
		
		//创建单行框架
		var enabled = (this.enabled) ? "" : "disabled='disabled'";
		var editabled = (this.editabled) ? "" : "readonly='readonly'";
		this._textboxhtml = $("<input class='search-text' type='text' "+ enabled +" "+ editabled +" style='width:"+ this.width +"px; height:" + this.height + "px;' " + holder + "/>");
		jqobj.append(this._textboxhtml);
		
		//输入框绑定输入事件
		var invoker = this;
		this._textboxhtml.on("change", function(e) {
			invoker._checkValueChange();
		});
		this._textboxhtml.bind(
			{keyup : function(e) {
				//触发按键抬起事件
				if (invoker.keyup) {
					var kea = new KeyupEventArgs(invoker.getValue(), invoker._textboxhtml.val());
					invoker.keyup.call(this, kea);
				}
			}, 
			blur : function(e) {
				//触发失去焦点事件
				if (invoker.blur) {
					var bea = new BlurEventArgs(invoker.getValue(), invoker._textboxhtml.val());
					invoker.blur.call(this, bea);
				}
			}}
		);
		
		this.setValue(getAttrValue(jqobj, "defaultValue", ""));
		
		return true;
	}
	
	/**
	 * @see Control#validated(any)
	 */
	Textbox.prototype.validated = function(e) {
		if (e.pass) {
			this._textboxhtml.removeClass("validate-no");
			this._textboxhtml.removeAttr("title");
		} else {
			this._textboxhtml.addClass("validate-no");
			this._textboxhtml.attr("title", e.message);
		}
	}
	
	/**
	 * @see Control#setContrastValue(any)
	 */
	Textbox.prototype.setContrastValue = function(showvalue) {
		this._textboxhtml.css("border", "1px dashed red");
		this._textboxhtml.attr("title", showvalue);
	}
	
	/**
	 * @see Control#setEnabled(any)
	 */
	Textbox.prototype.setEnabled = function(enabled) {
		if (!Control.prototype.setEnabled.call(this, enabled)) return;
		
		if (this.enabled) this._textboxhtml.removeAttr("disabled");
		else this._textboxhtml.attr("disabled", "disabled");
	}
	
	/**
	 * @param getValue 返回值
	 */
	Textbox.prototype.getValue = function(){
		return this._textboxhtml.val();
	}
	
	/**
	 * 
	 * @param value 文本框变量赋值
	 */
	Textbox.prototype.setValue = function(value){
		this._textboxhtml.val(value);
		
		this._checkValueChange();	//检查值变化
	}
	
	/******************** 文本框定义结束 ********************/
	
	/******************** 隐藏文本框定义开始 ********************/
	
	/**
	 * 定义隐藏文本框方法
	 */
	function Texthide (){
		Control.call(this);
		
		//私有属性
		this._texthidehtml = null;
	}
	
	/**
	 * 继承父类
	 */
	Texthide.prototype = new Control(null);
	Texthide.prototype.constructor = Texthide;
	
	/**
	 * @see Control#initialize(any)
	 */
	Texthide.prototype.initialize = function(jqobj){
		if(!Control.prototype.initialize.call(this,jqobj)) return false;
		
		//创建隐藏框架
		this._texthidehtml = $("<input class='search-hide' type='hidden'></input>");
		jqobj.append(this._texthidehtml);
		
		this.setValue(getAttrValue(jqobj, "defaultValue", ""));
		
		return true;
	}
	
	/**
	 * @see Control#setContrastValue(any)
	 */
	Texthide.prototype.setContrastValue = function(showvalue){
		this._texthidehtml.css("border","1px dashed red");
		this._texthidehtml.attr("title",showvalue);
	}
	
	/**
	 * @param getValue 返回值
	 */
	Texthide.prototype.getValue = function(){
		return this._texthidehtml.val();
	}
	
	/**
	 * 
	 * @param value 文本框变量赋值
	 */
	Texthide.prototype.setValue = function(value){
		this._texthidehtml.val(value);
	}
	
	/******************** 隐藏文本框定义结束 ********************/
	
	
	
	/******************** 多行文本框定义开始 ********************/
	
	/**
	 * 定义多行构造方法
	 */
	function Textarea(){
		Control.call(this);
		
		//公共属性
		this.width = "300";
		this.height = "100";
		
		//私有属性
		this._textareahtml = null;
		this._tempValue = null;
		
		//事件
		this.onvaluechanged = null;
		this.keyup = null;
		this.blur = null;
	}
	
	/**
	 * 继承父类
	 */
	Textarea.prototype = new Control(null);
	Textarea.prototype.constructor = Textarea;
	
	/**
	 * 检查值改变。
	 */
	Textarea.prototype._checkValueChange = function() {
		if (!this.onvaluechanged) return;
		
		var current = this.getValue();
		if (current === this._tempValue) return;
		
		var vcea = new ValueChangedEventArgs(current, this._tempValue);
		
		this._tempValue = current;				//保持当前值
		this.onvaluechanged.call(this, vcea);	//触发事件
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	Textarea.prototype.initialize = function(jqobj){
		if(!Control.prototype.initialize.call(this,jqobj)) return false;
		
		//初始化属性
		this.width = getAttrValue(jqobj,"width",this.width);
		this.height = getAttrValue(jqobj,"height",this.height);
		
		var holder = getAttrValue(jqobj, "placeHolder", "");
		if (holder != "") holder = "placeholder='" + holder + "'";
		
		//初始化事件
		this.onvaluechanged = findMethod(jqobj.attr("onvaluechanged"));
		this.keyup = findMethod(jqobj.attr("onkeyup"));
		this.blur = findMethod(jqobj.attr("onblur"));
		
		//创建多行框架
		var enabled = (this.enabled) ? "" : "disabled='disabled'";
		var editabled = (this.editabled) ? "" : "readonly='readonly'";
		this._textareahtml = $("<textarea class = 'search-area' "+ enabled +" "+ editabled +" style='width:"+ this.width +"px; height:" + this.height + "px;' " + holder + "/>");
		jqobj.append(this._textareahtml);
		
		//输入框绑定输入事件
		var invoker = this;
		this._textareahtml.on("change", function(e) {
			invoker._checkValueChange();
		});
		this._textareahtml.bind(
			{keyup : function(e) {
				//触发按键抬起事件
				if (invoker.keyup) {
					var kea = new KeyupEventArgs(invoker.getValue(), invoker._textareahtml.val());
					invoker.keyup.call(this, kea);
				}
			}, 
			blur : function(e) {
				//触发失去焦点事件
				if (invoker.blur) {
					var bea = new BlurEventArgs(invoker.getValue(), invoker._textareahtml.val());
					invoker.blur.call(this, bea);
				}
			}}
		);
		
		this.setValue(getAttrValue(jqobj, "defaultValue", ""));
		
		return true;
	}
	
	/**
	 * @see Control#validated(any)
	 */
	Textarea.prototype.validated = function(e) {
		if (e.pass) {
			this._textareahtml.removeClass("validate-no");
			this._textareahtml.removeAttr("title");
		} else {
			this._textareahtml.addClass("validate-no");
			this._textareahtml.attr("title", e.message);
		}
	}
	
	/**
	 * @see Control#setContrastValue(any)
	 */
	Textarea.prototype.setContrastValue = function(showvalue){
		this._textareahtml.css("border","1px dashed red");
		this._textareahtml.attr("title",showvalue);
	}
	
	/**
	 * @see Control#setEnabled(any)
	 */
	Textarea.prototype.setEnabled = function(enabled) {
		if (!Control.prototype.setEnabled.call(this, enabled)) return;
		
		if (this.enabled) this._textareahtml.removeAttr("disabled");
		else this._textareahtml.attr("disabled", "disabled");
	}
	
	/**
	 * @param getValue 返回值
	 */
	Textarea.prototype.getValue = function(){
		return this._textareahtml.val();
	}
	
	/**
	 * 
	 * @param value 多行文本框变量赋值
	 */
	Textarea.prototype.setValue = function(value){
		this._textareahtml.val(value);
		
		this._checkValueChange();	//检查值变化
	}
	/******************** 多行文本框定义结束 ********************/
	
	/******************** 密码框定义开始 ********************/
	
	/**
	 * 定义密码框构造方法
	 */
	function Textpassword(){
		Control.call(this);
		
		//公共属性
		this.width = "300";
		this.height = "30";
		
		//私有属性
		this._textpasswordhtml = null;
		this._tempValue = null;
		
		//事件
		this.onvaluechanged = null;
		this.keyup = null;
		this.blur = null;
	}
	
	/**
	 * 继承父类
	 */
	Textpassword.prototype = new Control(null);
	Textpassword.prototype.constructor = Textpassword;
	
	/**
	 * 检查值改变。
	 */
	Textpassword.prototype._checkValueChange = function() {
		if (!this.onvaluechanged) return;
		
		var current = this.getValue();
		if (current === this._tempValue) return;
		
		var vcea = new ValueChangedEventArgs(current, this._tempValue);
		
		this._tempValue = current;				//保持当前值
		this.onvaluechanged.call(this, vcea);	//触发事件
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	Textpassword.prototype.initialize = function(jqobj){
		if(!Control.prototype.initialize.call(this,jqobj)) return false;
		
		//初始化属性
		this.width = getAttrValue(jqobj,"width",this.width);
		this.height = getAttrValue(jqobj,"height",this.height);
		
		var holder = getAttrValue(jqobj, "placeHolder", "");
		if (holder != "") holder = "placeholder='" + holder + "'";
		
		//初始化事件
		this.onvaluechanged = findMethod(jqobj.attr("onvaluechanged"));
		this.keyup = findMethod(jqobj.attr("onkeyup"));
		this.blur = findMethod(jqobj.attr("onblur"));
		
		//创建密码框框架
		var enabled = (this.enabled) ? "" : "disabled='disabled'";
		var editabled = (this.editabled) ? "" : "readonly='readonly'";
		this._textpasswordhtml = $("<input class='search-password' type='password' "+ enabled +" "+ editabled +" style='width:" + this.width +"px; height:" + this.height +"px;' " + holder + "/>");
		jqobj.append(this._textpasswordhtml);
		
		//输入框绑定输入事件
		var invoker = this;
		this._textpasswordhtml.on("change", function(e) {
			invoker._checkValueChange();
		});
		this._textpasswordhtml.bind(
			{keyup : function(e) {
				//触发按键抬起事件
				if (invoker.keyup) {
					var kea = new KeyupEventArgs(invoker.getValue(), invoker._textpasswordhtml.val());
					invoker.keyup.call(this, kea);
				}
			}, 
			blur : function(e) {
				//触发失去焦点事件
				if (invoker.blur) {
					var bea = new BlurEventArgs(invoker.getValue(), invoker._textpasswordhtml.val());
					invoker.blur.call(this, bea);
				}
			}}
		);
		
		this.setValue(getAttrValue(jqobj, "defaultValue", ""));
		
		return true;
	}
	
	/**
	 * @see Control#validated(any)
	 */
	Textpassword.prototype.validated = function(e){
		if(e.pass){
			this._textpasswordhtml.removeClass("validate-no");
			this._textpasswordhtml.removeAttr("title");
		} else {
			this._textpasswordhtml.addClass("validate-no");
			this._textpasswordhtml.attr("title",e.message);
		}
	}
	
	/**
	 * @see Control#setEnabled(any)
	 */
	Textpassword.prototype.setEnabled = function(enabled) {
		if (!Control.prototype.setEnabled.call(this, enabled)) return;
		
		if (this.enabled) this._textpasswordhtml.removeAttr("disabled");
		else this._textpasswordhtml.attr("disabled", "disabled");
	}
	
	/**
	 * @param getvalue 返回值
	 */
	Textpassword.prototype.getValue = function(){
		return this._textpasswordhtml.val();
	}
	/**
	 * 
	 * @param value 密码框变量赋值
	 */
	Textpassword.prototype.setValue = function(value){
		this._textpasswordhtml.val(value);
		
		this._checkValueChange();	//检查值变化
	}
	
	/******************** 密码框定义结束 ********************/
	
	/******************** 单选定义开始 ********************/
	
	/**
	 * 定义单选构造方法
	 */
	function Textradio(){
		Control.call(this);
		
		//公共属性
		this.width = "300";
		this.itemWidth = "100";
		this.height = "30";
		this.url = null;
		this.idField = null;
		this.textField = null;
		this.defaultValue = "";
		this.verticalRank = false;
		this.groupName = null;
		this.items = null;
		
		//私有属性
		this._textradiohtml = null;
		this._requestParams = null;
		this._tempValue = null;
		this._itemRecords = {};
		
		//事件
		this.loaded = null;
		this.onvaluechanged = null;
	}
	/**
	 * 继承父类
	 */
	Textradio.prototype = new Control(null);
	Textradio.prototype.constructor = Textradio;
	
	/**
	 * 请求数据。
	 */
	Textradio.prototype._requestData = function() {
		var invoker = this;
		
		//发起异步请求
		$.ajax({
			url : this.url,
			data : this._requestParams,
			success : function(data, status) {
				if (data.status) {
					invoker._renderData(data.result);
					invoker.setValue(invoker.defaultValue);
				} else {
					
				}
				
				//调用加载完成事件
				if (invoker.loaded) {
					var lea = new LoadedEventArgs(data);
					invoker.loaded.call(this, lea);
				}
			},
			error : function(xhr, msg, e) {
				console.log("status:" + xhr.status, "statusText:" + xhr.statusText);
			}
		});
	}
	
	/**
	 * 渲染数据。
	 * @param result 请求结果对象。
	 */
	Textradio.prototype._renderData = function(result){
		//清空原有数据
		this._textradiohtml.empty();
		this._itemRecords = {};
		
		var invoker = this;
		var br = (this.verticalRank) ? "</br>" : "" ;
		
		//加载数据
		for(var index = 0; index < result.length; index ++){
			var record = result[index];
			var value = record[this.idField];
			var checked = (this.defaultValue == value) ? "checked = 'checked'" : "";
			
			//创建单选 
			var enabled = (this.enabled) ? "" : "disabled='disabled'";
			var editabled = (this.editabled) ? "" : "readonly='readonly'";
			var vhtml = "<lable style='width:"+this.itemWidth+"px;cursor:default; text-align:left;'><input class='search-radio' type='radio' " + enabled + " "+ editabled +" value='" + value + "' name='"+this.groupName+"' " + checked + "/><span>" + record[this.textField] + "</span></lable>";
			if(index < result.length - 1){
				vhtml += br;
			}
			var radioobj = $(vhtml);
			radioobj.bind({click : function() {
				if (invoker.enabled) {
					if (isMsie8()) $(this).get(0).checked = true;
					else $(this).children("input").get(0).checked = true;
					invoker._checkValueChange();
				}
			}});
			this._textradiohtml.append(radioobj);
			
			//保存项记录。
			this._itemRecords[value] = record;
		}
		
		this.items = this._textradiohtml.find("input[type='radio']");
	}
	
	/**
	 * 检查值改变。
	 */
	Textradio.prototype._checkValueChange = function() {
		if (!this.onvaluechanged) return;
		
		var current = this.getValue();
		if (current === this._tempValue) return;
		
		var vcea = new ValueChangedEventArgs(current, this._tempValue);
		
		this._tempValue = current;				//保持当前值
		this.onvaluechanged.call(this, vcea);	//触发事件
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	Textradio.prototype.initialize = function(jqobj){
		if(!Control.prototype.initialize.call(this,jqobj)) return false;
		
		//初始化属性
		this.url = getAttrValue(jqobj, "url", this.url);
		this.width = getAttrValue(jqobj,"width",this.width);
		this.itemWidth = getAttrValue(jqobj,"itemWidth",this.itemWidth);
		this.height = getAttrValue(jqobj,"height",this.height);
		this.idField = getAttrValue(jqobj,"idField",this.idField);
		this.textField = getAttrValue(jqobj,"textField",this.textField);
		this.defaultValue = getAttrValue(jqobj, "defaultValue", this.defaultValue);
		this.verticalRank = getBooleanAttrValue(jqobj,"verticalRank",this.verticalRank);
		this.groupName = getAttrValue(jqobj,"groupName",this.groupName);
		
		//初始化事件
		this.loaded = findMethod(jqobj.attr("onloaded"));
		this.onvaluechanged = findMethod(jqobj.attr("onvaluechanged"));
		
		//创建单选框架
		jqobj.css({width:this.width+"px",height:this.height+"px"});
		this._textradiohtml = jqobj;
		
		return true;
	}
	
	/**
	 * 加载
	 * @param params 自定义参数
	 */
	Textradio.prototype.load = function(params) {
		this._requestParams = {};
		if (params) copyObject(params, this._requestParams);
		
		//请求数据
		if (this.url) this._requestData();
	}
	
	/**
	 * 重新加载
	 */
	Textradio.prototype.reload = function() {
		this._requestData();
	}
	
	/**
	 * 加载数据
	 * @param data 数组类型
	 */
	Textradio.prototype.loadData = function(data){
		this._renderData(data);
		
		//调用加载完成事件
		if (this.loaded) {
			var lea = new LoadedEventArgs(data);
			this.loaded.call(this, lea);
		}
	}
	
	/**
	 * @see Control#setContrastValue(any)
	 */
	Textradio.prototype.setContrastValue = function(showvalue){
		for (var index = 0 ; index <this.items.length ; index ++){
			var item = this.items.get(index);
			if(item.checked){
				var parent = $(item).parent();
				parent.css("color","red");
				parent.attr("title",showvalue);
				break;
			}
		}
	}
	
	/**
	 * @see Control#setEnabled(any)
	 */
	Textradio.prototype.setEnabled = function(enabled) {
		if (!Control.prototype.setEnabled.call(this, enabled)) return;
		
		for (var index = 0; index < this.items.length; index ++) {
			var item = $(this.items.get(index));
			if (this.enabled) item.removeAttr("disabled");
			else item.attr("disabled", "disabled");
		}
	}
	
	/**
	 * @param getvalue 返回值
	 */
	Textradio.prototype.getValue = function(){
		var value = null;
		for (var index = 0; index < this.items.length; index ++) {
			var item = this.items.get(index);
			if (item.checked) {
				value = item.value;
				break;
			}
		}
		return value;
	}
	/**
	 * 
	 * @param value 单选框变量赋值
	 */
	Textradio.prototype.setValue = function(value){
		for (var index = 0; index < this.items.length; index ++){
			var item = this.items.get(index);
			if(item.value == value){
				item.checked = true;
				break;
			}else{
				item.checked = false;
			}
		}
		
		this._checkValueChange();	//检查值变化
	}
	
	/**
	 * 获取项记录。
	 * @param value 项的值。
	 */
	Textradio.prototype.getItemRecord = function(value) {
		return this._itemRecords[value];
	}
	
	/**
	 * 获取选中记录。
	 * @returns 选中记录。
	 */
	Textradio.prototype.getSelectedRecord = function() {
		var value = this.getValue();
		if (value == null) return null;
		return this._itemRecords[value];
	}
	
	/******************** 单选定义结束 ********************/
	
	/******************** 复选定义结束 ********************/
	
	/**
	 * 定义复选框构造方法
	 */
	function Textcheck(){
		Control.call(this);
		
		//公共属性
		this.width = "300";
		this.height = "30";
		this.itemWidth = "100";
		this.idField = null;
		this.textField = null;
		this.defaultValue = "";
		this.groupName = null;
		this.items = null;		
		this.verticalRank = false;
		
		//私有属性
		this._textcheckhtml = null;
		this._requestParams = null;
		this._itemRecords = {};
		
		//事件
		this.loaded = null;
		this.onvaluechanged = null;
		
	}
	
	//继承父类
	Textcheck.prototype = new Control(null);
	Textcheck.prototype.constructor = Textcheck;
	
	/**
	 * 请求数据。
	 */
	Textcheck.prototype._requestData = function() {
		var invoker = this;
		
		//发起异步请求
		$.ajax({
			url : this.url,
			data : this._requestParams,
			success : function(data, status) {
				if (data.status) {
					invoker._renderData(data.result);
					invoker.setValue(invoker.defaultValue);
				} else {
					
				}
				
				//调用加载完成事件
				if (invoker.loaded) {
					var lea = new LoadedEventArgs(data);
					invoker.loaded.call(this, lea);
				}
			},
			error : function(xhr, msg, e) {
				console.log("status:" + xhr.status, "statusText:" + xhr.statusText);
			}
		});
	}
	
	/**
	 * 渲染数据
	 * @param result 请求数据结果
	 */
	Textcheck.prototype._renderData = function(result){
		//清空原有数据
		this._textcheckhtml.empty();
		this._itemRecords = {};
		
		var invoker = this;
		var br = (this.verticalRank) ? "</br>" : "";
		
		//加载数据
		for(index = 0;index < result.length ; index ++){
			var record = result[index];
			var value = record[this.idField];
			var checkbox = (this.defaultValue == value) ? "checkbox = 'checkbox'" : "";
			
			//创建复选
			var enabled = (this.enabled) ? "" : "disabled='disabled'";
			var editabled = (this.editabled) ? "" : "readonly='readonly'";
			var khtml = "<lable style='width:"+this.itemWidth+"px;cursor:default;'><input class = 'search-check' type = 'checkbox' " + enabled + " "+ editabled +" value = '" + value + "' name='"+this.groupName+"' "+ checkbox +"/><span>" + record[this.textField] + "</span></lable>"
			if(index < result.length - 1){
				khtml += br;
			}
			var checkobj = $(khtml);
			checkobj.children("span").bind({click : function() {
				if (invoker.enabled) {
					var input = $(this).parent().children("input").get(0);
					input.checked = (input.checked) ? false : true;
					invoker._checkValueChange();
				}
			}});
			this._textcheckhtml.append(checkobj);
			
			//保存项记录。
			this._itemRecords[value] = record;
		}
		
		this.items = this._textcheckhtml.find("input[type='checkbox']");
	}
	
	/**
	 * 检查值改变。
	 */
	Textcheck.prototype._checkValueChange = function() {
		if (!this.onvaluechanged) return;
		
		var current = this.getValue();
		if (current === this._tempValue) return;
		
		var vcea = new ValueChangedEventArgs(current, this._tempValue);
		
		this._tempValue = current;				//保持当前值
		this.onvaluechanged.call(this, vcea);	//触发事件
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	Textcheck.prototype.initialize = function(jqobj){
		if(!Control.prototype.initialize.call(this,jqobj)) return false;
		
		//初始化属性
		this.url = getAttrValue(jqobj, "url", this.url);
		this.width = getAttrValue(jqobj,"width",this.width);
		this.height = getAttrValue(jqobj,"height",this.width);
		this.idField = getAttrValue(jqobj,"idField",this.idField);
		this.textField = getAttrValue(jqobj,"textField",this.textField);
		this.itemWidth = getAttrValue(jqobj,"itemWidth",this.itemWidth);
		this.defaultValue = getAttrValue(jqobj,"defaultValue",this.defaultValue);
		this.groupName = getAttrValue(jqobj,"groupName",this.groupName);
		this.verticalRank = getBooleanAttrValue(jqobj,"verticalRank",this.verticalRank);
		
		//初始化事件
		this.loaded = findMethod(jqobj.attr("onloaded"));
		this.onvaluechanged = findMethod(jqobj.attr("onvaluechanged"));
		
		//创建复选框
		jqobj.css({width:this.width+"px",height:this.height+"px"});
		this._textcheckhtml = jqobj;
		
		return true;
	}
	
	/**
	 * 加载
	 * @param params 自定义参数
	 */
	Textcheck.prototype.load = function(params) {
		this._requestParams = {};
		if (params) copyObject(params, this._requestParams);
		
		//请求数据
		if (this.url) this._requestData();
	}
	
	/**
	 * 重新加载
	 */
	Textcheck.prototype.reload = function() {
		this._requestData();
	}
	
	/**
	 * 加载数据
	 * @param data 数组类型
	 */
	Textcheck.prototype.loadData = function(data){
		this._renderData(data);
		
		//调用加载完成事件
		if (this.loaded) {
			var lea = new LoadedEventArgs(data);
			this.loaded.call(this, lea);
		}
	}
	
	/**
	 * @see Control#setContrastValue(any)
	 */
	Textcheck.prototype.setContrastValue = function(showvalue){
		for (var index = 0 ; index <this.items.length ; index ++){
			var item = this.items.get(index);
			
			if(item.checked){
				var parent = $(item).parent();
				parent.css("color","red");
				parent.attr("title",showvalue);
				continue;
			}
		}
	}
	
	/**
	 * @see Control#setEnabled(any)
	 */
	Textcheck.prototype.setEnabled = function(enabled) {
		if (!Control.prototype.setEnabled.call(this, enabled)) return;
		
		for (var index = 0; index < this.items.length; index ++) {
			var item = $(this.items.get(index));
			if (this.enabled) item.removeAttr("disabled");
			else item.attr("disabled", "disabled");
		}
	}
	
	/**
	 * @param getvalue 返回值
	 */
	Textcheck.prototype.getValue = function(){
		var value = "";
		for (var index = 0; index < this.items.length; index ++) {
			var item = this.items.get(index);
			
			if(!item.checked) continue;
			
			value += item.value + ",";
		}
		if (value.length > 0) {
			value = value.substring(0, value.length - 1);
		}
		return value;
	}
	/**
	 * 
	 * @param value 单选框变量赋值
	 */
	Textcheck.prototype.setValue = function(value){
		for(var index = 0 ; index < this.items.length; index++){
			this.items.get(index).checked = false ;
		}
		
		var values = null;
		if(typeof(value) === "string"){
			values = value.split(",");
		} else {
			values = new Array();
			values.push(value);
		}
		for(var val = 0 ; val < values.length; val ++){
			for (var index = 0; index < this.items.length; index ++){
				var item = this.items.get(index);
				if(item.value == values[val]){
					item.checked = true;
				}
			}
		}
		
		this._checkValueChange();	//检查值变化
	}
	
	/**
	 * 获取项记录。
	 * @param value 项的值。
	 * @returns 项记录。
	 */
	Textcheck.prototype.getItemRecord = function(value) {
		return this._itemRecords[value];
	}
	
	/**
	 * 获取选中记录。
	 * @returns 选中记录。
	 */
	Textcheck.prototype.getSelectedRecords = function() {
		var records = new Array();
		
		var value = this.getValue();
		if (value === "") return records;
		
		var arr = value.split(",");
		for (var i = 0; i < arr.length; i ++) {
			records.push(this._itemRecords[arr[i]]);
		}
		
		return records;
	}
	
	/******************** 复选定义结束 ********************/
	
	/******************** 日期选取器定义开始 ********************/
	
	/**
	 * 日期选取器构造方法。
	 */
	function Datepicker() {
		Control.call(this);
		
		//公共属性
		this.width = "300";
		this.height = "30";
		this.format = "yyyy-MM-dd";
		
		//私有属性
		this._type = "date";
		this._ctlhtml = null;
		this._tempValue = null;
		this._clickSelector = false;
		
		//事件
		this.onvaluechanged = null;
		this.keyup = null;
		this.blur = null;
	}
	
	/**
	 * 继承父类。
	 */
	Datepicker.prototype = new Control(null);
	Datepicker.prototype.constructor = Datepicker;
	
	/**
	 * 检查值改变。
	 */
	Datepicker.prototype._checkValueChange = function() {
		if (!this.onvaluechanged) return;
		
		var current = this.getValue();
		if (current === this._tempValue) return;
		
		var vcea = new ValueChangedEventArgs(current, this._tempValue);
		
		this._tempValue = current;				//保持当前值
		this.onvaluechanged.call(this, vcea);	//触发事件
	}
	
	/**
	 * 验证格式。
	 */
	Datepicker.prototype._validateFormat = function() {
		switch (this.format) {
			case "yyyy":
				this._type = "year";
				break;
			case "yyyy-MM":
				this._type = "month";
				break;
			case "yyyy-MM-dd":
				return;
			case "yyyy-MM-dd HH:mm:ss":
				this._type = "datetime";
				break;
			case "HH:mm:ss":
				this._type = "time";
				break;
			default:
				this.format = "yyyy-MM-dd";
				this._type = "date";
				break;
		}
	}
	
	/**
	 * 验证值。
	 * @param value 值对象。
	 */
	Datepicker.prototype._validateValue = function(value) {
		if (typeof(value) === "undefined" || value == null || value === "") return "";
		
		switch (this.format) {
			case "yyyy":
				if (/^\d{4}$/.test(value)) return value;
				break;
			case "yyyy-MM":
				if (/^\d{4}-\d{2}$/.test(value)) return value;
				break;
			case "yyyy-MM-dd":
				if (/^\d{4}-\d{2}-\d{2}$/.test(value)) return value;
				break;
			case "yyyy-MM-dd HH:mm:ss":
				if (/^\d{4}-\d{2}-\d{2}\s{1}\d{2}:\d{2}:\d{2}$/.test(value)) return value;
				break;
			case "HH:mm:ss":
				if (/^\d{2}:\d{2}:\d{2}$/.test(value)) return value;
				break;
		}
		
		var tv = parseInt(value);
		if (!isNaN(tv)) {
			var date = new Date(tv);
			switch (this.format) {
			case "yyyy":
				return date.getFullYear();
			case "yyyy-MM":
				return date.getFullYear() + "-" + (date.getMonth() + 1).padLeft(2);
			case "yyyy-MM-dd":
				return date.getFullYear() + "-" + (date.getMonth() + 1).padLeft(2) + "-" + date.getDate().padLeft(2);
			case "yyyy-MM-dd HH:mm:ss":
				return date.getFullYear() + "-" + (date.getMonth() + 1).padLeft(2) + "-" + date.getDate().padLeft(2) + " " + 
					   date.getHours().padLeft(2) + ":" + date.getMinutes().padLeft(2) + ":" + date.getSeconds().padLeft(2);
			case "HH:mm:ss":
				return date.getHours().padLeft(2) + ":" + date.getMinutes().padLeft(2) + ":" + date.getSeconds().padLeft(2);
			}
		}
		
		return "";
	}
	
	/**
	 * @see Control#initialize(any)
	 */
	Datepicker.prototype.initialize = function(jqobj) {
		if(!Control.prototype.initialize.call(this,jqobj)) return false;
		
		//初始化属性
		this.width = getAttrValue(jqobj, "width", this.width);
		this.height = getAttrValue(jqobj, "height", this.height);
		this.format = getAttrValue(jqobj, "format", this.format);
		
		var holder = getAttrValue(jqobj, "placeHolder", "");
		if (holder != "") holder = "placeholder='" + holder + "'";
		
		//初始化事件
		this.onvaluechanged = findMethod(jqobj.attr("onvaluechanged"));
		this.keyup = findMethod(jqobj.attr("onkeyup"));
		this.blur = findMethod(jqobj.attr("onblur"));
		
		//创建控件
		var enabled = (this.enabled) ? "" : "disabled='disabled'";
		var editabled = (this.editabled) ? "" : "readonly='readonly'";
		this._ctlhtml = $("<input class='search-text' type='text' " + editabled + " " + enabled + " style='width: " + this.width + "px; height: " + this.height + "px; ' " + holder + "/>");
		jqobj.append(this._ctlhtml);
		
		//输入框绑定输入事件
		var invoker = this;
		this._ctlhtml.on("change", function(e) {
			invoker._checkValueChange();
		});
		this._ctlhtml.bind(
			{keyup : function(e) {
				//触发按键抬起事件
				if (invoker.keyup) {
					var kea = new KeyupEventArgs(invoker.getValue(), invoker._ctlhtml.val());
					invoker.keyup.call(this, kea);
				}
			}, 
			blur : function(e) {
				setTimeout(function() {
					//忽略日期时间选择面板被点击的情况
					if (invoker._clickSelector) return;
					
					//触发失去焦点事件
					if (invoker.blur) {
						var bea = new BlurEventArgs(invoker.getValue(), invoker._ctlhtml.val());
						invoker.blur.call(this, bea);
					}
				}, 100);
			}}
		);
		
		//验证格式
		this._validateFormat();
		
		this.setValue(getAttrValue(jqobj, "defaultValue", ""));
		
		//渲染选取器
		laydate.render({
			elem : this._ctlhtml.get(0), 
			type : this._type, 
			ready : function(date) {
				$(".layui-laydate").bind({
					mousedown : function() {
						invoker._clickSelector = true;
					}, 
					mouseup : function() {
						setTimeout(function() { invoker._clickSelector = false; }, 500);
					}
				});
			}, 
			done : function(value, date, endDate) {
				invoker.setValue(value);
			}
		});
		
		return true;
	}
	
	/**
	 * @see Control#validated(any)
	 */
	Datepicker.prototype.validated = function(e) {
		if (e.pass) {
			this._ctlhtml.removeClass("validate-no");
			this._ctlhtml.removeAttr("title");
		} else {
			this._ctlhtml.addClass("validate-no");
			this._ctlhtml.attr("title", e.message);
		}
	}
	
	/**
	 * @see Control#setContrastValue(any)
	 */
	Datepicker.prototype.setContrastValue = function(showvalue) {
		this._ctlhtml.css("border", "1px dashed red");
		this._ctlhtml.attr("title", showvalue);
	}
	
	/**
	 * @see Control#setEnabled(any)
	 */
	Datepicker.prototype.setEnabled = function(enabled) {
		if (!Control.prototype.setEnabled.call(this, enabled)) return;
		
		if (this.enabled) this._ctlhtml.removeAttr("disabled");
		else this._ctlhtml.attr("disabled", "disabled");
	}
	
	/**
	 * @param getValue 返回值
	 */
	Datepicker.prototype.getValue = function(){
		return this._ctlhtml.val();
	}
	
	/**
	 * 
	 * @param value 文本框变量赋值
	 */
	Datepicker.prototype.setValue = function(value){
		this._ctlhtml.val(this._validateValue(value));
		
		this._checkValueChange();	//检查值变化
	}
	
	/******************** 日期选取器定义结束 ********************/
	
	/******************** 按钮控件 定义开始 *************************/
	function Button(){
		Control.call(this);
		
		//公共属性
		this.width = null;
		this.height = "35";
		this.text = "按钮";
		
		this._btnhtml = null;
		
		//事件
		this.click = null;
	}
	
	/**
	 * 继承父类。
	 */
	Button.prototype = new Control(null);
	Button.prototype.constructor = Button;
	
	/**
	 * @see Control#initialize(any)
	 */
	Button.prototype.initialize = function(jqobj){
		if(!Control.prototype.initialize.call(this,jqobj)) return false;
		
		//初始化
		this.width = getAttrValue(jqobj, "width", this.width);
		this.height = getAttrValue(jqobj, "height", this.height);
		this.text = getAttrValue(jqobj, "text", this.text);
		
		//事件初始化
		this.click = findMethod(jqobj.attr("onclick"));
		
		//绑定点击事件
		var invoker = this;
		jqobj.bind({click:function(){
			if (invoker.enabled) invoker.click.call(invoker);
		}});
		
		if (this.width) jqobj.css("width", this.width + "px");
		jqobj.css("height", this.height + "px");
		jqobj.css("line-height", this.height + "px");
		jqobj.html(this.text);
		
		if (!this.enabled) jqobj.addClass("disabled");
		
		this._btnhtml = jqobj;
		
		return true;
	}
	
	/**
	 * @see Control#setEnabled(any)
	 */
	Button.prototype.setEnabled = function(enabled){
		if(!Control.prototype.setEnabled.call(this , enabled)) return;
		
		if(this.enabled) this._btnhtml.removeClass("disabled");
		else this._btnhtml.addClass("disabled");
	}
	
	/******************** 按钮控件 定义结束 *************************/
	
	/******************** 标签控件定义开始 *************************/
	
	/**
	 * 标签构造方法。
	 */
	function Label() {
		Control.call(this);
		
		//公共属性
		this.width = "300";
		this.height = "30";
		this.multiline = false;
		
		//私有属性
		this._labelhtml = null;
	}
	
	/**
	 * 继承父类。
	 */
	Label.prototype = new Control(null);
	Label.prototype.constructor = Label;
	
	/**
	 * @see Control#initialize(any)
	 */
	Label.prototype.initialize = function(jqobj) {
		if(!Control.prototype.initialize.call(this, jqobj)) return false;
		
		//初始化属性
		this.width = getAttrValue(jqobj, "width", this.width);
		this.height = getAttrValue(jqobj, "height", this.height);
		this.multiline = getBooleanAttrValue(jqobj, "multiline", this.multiline);
		
		//创建标签
		this._labelhtml = $("<span class='text' style='width:" + this.width + "px;height:" + this.height + "px;'></span>");
		if (this.multiline) this._labelhtml.addClass("multiple");
		else {
			this._labelhtml.addClass("single");
			this._labelhtml.css("line-height", this.height + "px");
		}
		jqobj.append(this._labelhtml);
		
		this.setValue(getAttrValue(jqobj, "defaultValue", ""));
		
		return true;
	}
	
	/**
	 * 获取标签的值。
	 * @returns 标签的值。
	 */
	Label.prototype.getValue = function() {
		return this._labelhtml.text();
	}
	
	/**
	 * 设置标签的值。
	 * @param value 标签的值。
	 */
	Label.prototype.setValue = function(value) {
		if (!value) value = "";
		this._labelhtml.text(value);
		if (!this.multiline) this._labelhtml.attr("title", value);
	}
	
	/******************** 标签控件定义结束 *************************/
}

/******************** 位置定义开始 ********************/

/**
 * 位置构造方法。
 * @param top 上边距。
 * @param left 左边距。
 */
function Position(top, left) {
	//公共属性
	this.top = top;
	this.left = left;
}

/******************** 位置定义结束 ********************/

/******************** 数据表格过滤器定义开始 ********************/

/**
 * @param field 字段名。
 * @param value 字段值。
 * @param accurate 是否精确匹配。
 */
function DatagridFilter(field, value, accurate) {
	//公共属性
	this.field = field;
	
	//私有属性
	this._value = value;
	this._accurate = accurate;
	
	/**
	 * 过滤。
	 * @param value 将要过滤的值。
	 * @returns 满足过滤条件时返回true，否则返回false。
	 */
	this.doFilter = function(value) {
		if (this._accurate) {	//精确匹配
			if (value === this._value) return true;
		} else {				//模糊匹配
			if (typeof(value) === "string" && (this._value === "" || value.indexOf(this._value) != -1)) return true;
		}
		return false;
	}
}

/******************** 数据表格过滤器定义结束 ********************/

/******************** 表单定义开始 ********************/

/**
 * 表单构造方法。
 * @param cid 表单容器标识。
 */
function Form(cid) {
	//私有属性
	this._cid = cid;
}

/**
 * 查找表单对象。
 * @returns 表单对象集合。
 */
Form.prototype._findForms = function() {
	var forms = {};
	
	var container = $("#" + this._cid);
	if (container.length <= 0) return forms;
	
	container.find("div").each(function(index, element) {
		var e = $(element);
		var id = e.attr("id");
		var cls = e.attr("class");
		
		//检查控件有效性
		if (!id || !cls) return;
		
		if (cls.containsClass("search-select") || 			//下拉列表
			cls.containsClass("search-treeselect") || 		//树形下拉列表
			cls.containsClass("search-textbox") || 			//单行文本框
			cls.containsClass("search-textarea") || 		//多行文本框
			cls.containsClass("search-textpassword") || 	//密码文本框
			cls.containsClass("search-textradio") || 		//单选按钮
			cls.containsClass("search-texthide")  ||        //隐藏文本框 
			cls.containsClass("search-textcheck") || 		//复选框
			cls.containsClass("search-datepicker") || 		//日期选取器
			cls.containsClass("search-label")) {			//标签
			//检查控件是否已解析
			var form = search.get(id);
			if (!form) return;
			
			forms[id] = form;
		}
	});
	
	return forms;
}

/**
 * 表单验证。
 * @param full 是否完全验证。
 * @returns 验证通过时返回true，否则返回false。
 */
Form.prototype.validate = function(full) {
	var forms = this._findForms();
	
	var pass = true;
	for (var key in forms) {
		var form = forms[key];
		if (!form.skipValidate() && !form.validate()) {
			pass = false;
			if (!full) break;
		}
	}
	return pass;
}

/**
 * 获取表单数据。
 * @returns 表单数据对象。
 */
Form.prototype.getData = function() {
	var forms = this._findForms();
	
	var data = {};
	for (var key in forms) {
		var form = forms[key];
		var value = form.getValue();
		if (typeof(value) === "string") value = value.trim();
		data[form.id] = value;
	}
	return data;
}

/**
 * 设置表单数据。
 * @param data 表单数据对象。
 */
Form.prototype.setData = function(data, contrastdata) {
	if (!data) {
		this.clearData();
		return;
	}
	
	var forms = this._findForms();
	
	for (var key in data) {
		var form = forms[key];
		if (!form) continue;
		
		var value = data[key];
		form.setValue(value);
		
		if (!contrastdata) continue;
		
		var cvalue = contrastdata[key];
		var savevalue = null, showvalue = null;
		if (cvalue == null || typeof(cvalue) != "string" || cvalue.indexOf(":") < 0) {
			savevalue = cvalue;
			showvalue = cvalue;
		} else {
			var arrs = cvalue.split(":");
			savevalue = arrs[0];
			showvalue = arrs[1];
		}
		
		if (value != savevalue) form.setContrastValue(showvalue);
	}
}

/**
 * 设置表单值。
 */
Form.prototype.clearData = function(){
	
	var forms = this._findForms();
	
	for(var key in forms){
		var form = forms[key];
		form.setValue("");
	}
}

/**
 * 设置表单启用状态。
 * @param enabled 表单启用状态。
 * @param ctlids 要排除设置启用状态的表单控件ID。
 */
Form.prototype.setEnabled = function(enabled, excludes) {
	var forms = this._findForms();
	
	for (var key in forms) {
		if (excludes && excludes.length > 0 && $.inArray(key, excludes) != -1) continue;
		
		var form = forms[key];
		form.setEnabled(enabled);
	}
}

/******************** 表单定义结束 ********************/

/**
 * 字符串转换为Boolean对象。
 * @returns Boolean对象，字符串为true时返回true，否则返回false。
 */
String.prototype.toBoolean = function() {
	return /^true$/.test(this);
}

/**
 * 检查字符串是否包含指定样式。
 * @returns 包含指定样式时返回true，否则返回false。
 */
String.prototype.containsClass = function(cls) {
	var pattern = new RegExp("^" + cls + "$|^.*\\s+" + cls + "$|^" + cls + "\\s+.*$|^.*\\s+" + cls + "\\s+.*$");
	return pattern.test(this);
}

/**
 * 检查字符串是否以指定字符开头。
 * @param str 匹配字符。
 * @returns 以指定字符开头返回true，否则返回false。
 */
String.prototype.startWith = function(str) {
	var pattern = new RegExp("^" + str);
	return pattern.test(this);
}

/**
 * 移除所有前导空白字符和尾部空白字符。
 * @returns 删除所有空白字符后剩余的字符串。
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 使用指定字符左填充对齐。
 * @param width 对齐宽度。
 * @param char 填充字符（默认使用0填充）。
 */
Number.prototype.padLeft = function(width, char) {
	var str = this.toString();
	if (str.length >= width) return str;
	
	if (typeof(char) === "undefined" || char == null || char === "") char = "0";
	
	return Array(width - str.length + 1).join(char) + str;
}

/**
 * 从数组中删除指定元素。
 * @param value 要删除的元素值。
 */
Array.prototype.remove = function(value) {
	var index = this.indexOf(value);
	if (index != -1) {
		this.splice(index, 1);
	}
}

/**
 * 监听AJAX事件。
 */
window.listenAjaxEvent = function() {
	window.noAjax = true;
	
	var winobj = $(window);
	winobj.ajaxStart(function(e) {
		window.noAjax = false;
		search.showLoading();
	});
	winobj.ajaxStop(function(e) {
		window.noAjax = true;
		search.closeLoading();
	});
}();

/**
 * 等待AJAX请求完成。
 * @param invoker 回调方法调用对象。
 * @param method 回调方法对象。
 * @param params 回调方法调用参数。
 */
window.waitForAjax = function(invoker, method, params) {
	if (window.noAjax) method.call(invoker, params);
	else setTimeout(function() { window.waitForAjax(invoker, method, params) }, 5);
}

/**
 * 绑定文档对象的上下文菜单事件，屏蔽输入框以外元素的鼠标右键事件。
 * @param e 事件数据。
 */
document.oncontextmenu = function(e) {
	return ((/^INPUT$/.test(e.target.tagName) && !/^(radio|checkbox)$/.test(e.target.type)) || /^TEXTAREA$/.test(e.target.tagName));
}

/**
 * 监听鼠标按下事件。
 */
document.listenMousedownEvent = function() {
	$(document).bind("mousedown", function(e) {
		if (window.top.search) window.top.search.closeContextMenu($(e.target));	//关闭上下文菜单
	});
}();
