search.parse();

var treegrid = search.get('treedatagrid');
var instanceStep;

//页面加载完成执行
$(function() {
	instanceStep = Step.initTab();
})

// 父页面传值
function setData(data) {
	treegrid.url = CTX + '/applys/findAuditTplDetailCopysForTreeBySapplyid';
	treegrid.load({applyid : data.sid});
	
	$.post(CTX + '/applys/findApplysDetailsBySid', {sid : data.sid}, function(result) {
		if (result.status) {
			var $searchViews = $('.search-view');
			$.each($searchViews, function() {
				var $this = $(this);
				var field = $this.attr('id');
				var value = result.result[field];
				
				if (field == 'itype') {
					$.each(Globle.constant.AuditTplsType, function() {
						if (this.id == value) {
							value = this.text;
							return true;
						}
					})
				}
				
				$this.html(value);
			})
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}

// 送审标准绘制表格
function treedatagriddrawcell(e) {
	// 资料类型
	if (e.column.field == "itype" && e.record[e.column.field]) {
		$.each(Globle.constant.AuditTplDetailsType, function() {
			if (this.id == e.record[e.column.field]) {
				e.html = this.text;
				return true;
			}
		})
	}
	
	// 是否必填|是否电子文档
	if (e.column.field == "imust" || e.column.field == "ipaper") {
		if (e.record.itype == '102') {
			$.each(Globle.constant.YesOrNo, function() {
				if (this.value == e.record[e.column.field]) {
					e.html = this.text;
					return true;
				}
			})
		} else {
			e.html = '';
		}
	}
	
	// 操作
	if (e.column.field == "oper" && e.record.itype == '102') {
		var $html = $('<a id="view_attachs_' + e.record.sid + '" href="javascript:void(0);">附件下载</a>');
		if (e.record.attachs != null && e.record.attachs.length > 0) {
			$html.addClass('edit');
			$html.on('click', function() {
				top.search.popDialog({ url : CTX + "/applys/goApplysAttachsViewPage", title : "申请附件详情", width : 600, height : 450, onload : function(window) {
					window.setData({ sid : e.record.sid });
				}, ondestroy : function(result) {
				} })
			});
		} else {
			$html.addClass('btn-disable');
		}
		e.html = $html;
	}
}