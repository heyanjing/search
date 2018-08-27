search.parse();
var grid = search.get('datagrid');

// 父页面传值
function setData(data) {
	grid.url = CTX + '/applys/findApplysAttachsForPageByApplyid';
	grid.load({ applyid : data.sid });
}

// 表格绘制
function datagriddrawcell(e) {
	// 类型
	if (e.column.field == "stypeattach") {
		var value = e.record.snameattach;
		e.html = (value.substring(value.lastIndexOf('.'), value.length));
	}
	
	// 大小
	if (e.column.field == "isizeattach") {
		if (e.record[e.column.field] >= 0) {
			e.html = WebUploader.formatSize(e.record[e.column.field]);
		} else {
			e.html = '未知大小';
		}
	}
	
	// 操作
	if (e.column.field == "oper") {
		var $html = $('<a id="download_attachs_' + e.record.sid + '" class="download_attachs" href="javascript:void(0);">附件下载</a>&emsp;' 
				+ '<a id="view_attachs_' + e.record.sid + '" class="view_attachs" href="javascript:void(0);">附件预览</a>');
		if (e.record.exists) {
			// 存在文件
			$html.addClass('edit');
			$html.on('click', function() {
				var $this = $(this);
				if ($this.attr('id').indexOf('download_attachs_') != -1) {
					// 附件下载
					$this.attr('href', CTX + '/applys/downloadApplysAttach?name=' + e.record.snameattach + '&path=' + e.record.spathattach); 
				} else if ($this.attr('id').indexOf('view_attachs_') != -1) {
					// 附件预览
					$.post(CTX + '/applys/findOnlineEditFileUrl', {relativePath : e.record.spathattach, isedit : false, iscopy : false}, function(result) {
						if (result.status) {
							top.search.popDialog({ url : CTX + '/applys/goOnlineEditFilePage', title : "查看", width : 900, height : 550, onload : function(window) {
								window.setData({ url : result.result.operurl });
							}, ondestroy : function(result) {
							} });
						}
					}) 
				}
			})
		} else {
			// 不存在文件
			$html.addClass('btn-disable');
		}
		e.html = $html;
	}
}