search.parse();

var form = new Form('applys');

var treegrid = search.get('treedatagrid');
var sprojectlibid = search.get('sprojectlibid');
var itype = search.get('itype');

var instanceStep, parentData = {}, resultData = {}, delFilePath = new Array(), delFileId = new Array(), editField = new Array();

// 页面加载完成执行
$(function() {
	instanceStep = Step.initTab();
	
	itype.loadData(Globle.constant.AuditTplsType);
})

// 父页面传值
function setData(data) {
	parentData = data;
	if (data.action != 'add') {
		// 编辑
		sprojectlibid.setEnabled(false);
		itype.setEnabled(false);
		
		$.post(CTX + '/applys/findApplysBySid', {sid : data.sid}, function(result) {
			if (result.status) {
				sprojectlibid.loadData([{id : result.result.sprojectlibid, text : parentData.record.projectname, sauditorgid : parentData.record.sauditorgid}]);
				form.setData(result.result);
				resultData = result.result;
				if (data.action == 'submit') {
					instanceStep.stepClick(3, true);
				}
				
				setDatas({processdesignsid : parentData.processdesignsid, processinstancesid : parentData.processinstancesid,
					functionid : parentData.functionid, sauditorgid : sprojectlibid.getSelectedItem().record.sauditorgid}, function(result) {
						if (result.statuscode != 100000 && result.resultData.result.isupportback == 1) {
							$('#auditBack').show();
						}
					});
			} else {
				top.search.error({ content : "系统错误！" });
			}
		})
	} else {
		sprojectlibid.url = CTX + '/applys/findProjectLibsForSelect';
		sprojectlibid.load();
		
		setDatas({processdesignsid : parentData.processdesignsid, processinstancesid : parentData.processinstancesid}, function(result) {
			if (result.statuscode != 100000 && result.resultData.result.isupportback == 1) {
				$('#auditBack').show();
			}
		});
	}
	
	// 查询可编辑字段
	$.post(CTX + '/applys/findFieldsByFunctionidAndProcessdesignidOrProcessinstanceid', {processdesignid : parentData.processdesignsid,
		processinstanceid : parentData.processinstancesid, functionid : parentData.functionid}, function(result) {
		if (result.status && result.result && result.result.length > 0) {
			$.each(result.result, function() {
				editField.push(this.sfieldname.toLowerCase());
			})
			form.setEnabled(false, editField);
		} else {
			form.setEnabled(false);
		}
	})
}

// 项目加载完成执行
function sprojectlibidloaded(e) {
	if (e && e.data && e.data.result && e.data.result.length > 0) {
		sprojectlibid.setValue(e.data.result[0].id);
	}
}

// 即将进入送审标准
var auditTplDetailType, auditTplDetailProject;
function auditTplDetailWillEnter() {
	var type = itype.getValue(), project = sprojectlibid.getSelectedItem();
	if (parentData.action != 'add') {
		if (type && project && project.record && (auditTplDetailType != type || auditTplDetailProject != project.record.sauditorgid)) {
			treegrid.url = CTX + '/applys/findAuditTplDetailCopysForTreeBySapplyid';
			treegrid.load({applyid : resultData.sid});
			
			auditTplDetailType = type;
			auditTplDetailProject = project.record.sauditorgid;
		}
	} else {
		if (type && project && project.record && (auditTplDetailType != type || auditTplDetailProject != project.record.sauditorgid)) {
			treegrid.url = CTX + '/applys/findAuditTplsForTreeByItypeAndSorgid';
			treegrid.load({type : type, orgid : project.record.sauditorgid});
			
			auditTplDetailType = type;
			auditTplDetailProject = project.record.sauditorgid;
		}
	}
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
	
	// 是否必填
	if (e.column.field == "imust") {
		$.each(Globle.constant.YesOrNo, function() {
			if (this.value == e.record[e.column.field]) {
				e.html = this.text;
				return true;
			}
		})
	}
	
	// 是否电子文档
	if (e.column.field == "ipaper") {
		var $html = '';
		if (e.record.itype == '102') {
			if (e.record[e.column.field] && e.record[e.column.field] == 2) {
				$html = $('<input type="checkbox" class="ipaper_checkbox" data-type="102" value="' + e.record.sid + '"/>');
			} else {
				$html = $('<input type="checkbox" class="ipaper_checkbox" data-type="102" value="' + e.record.sid + '" checked/>');
			}
		} else {
			$html = $('<input type="hidden" class="ipaper_checkbox" data-type="101" value="' + e.record.sid + '"/>');
		}
		if (editField.indexOf('ipaper') < 0) {
			$html.attr('disabled', true);
		}
		e.html = $html;
	}
	
	// 操作
	if (e.column.field == "oper" && e.record.itype == '102') {
		if (editField.indexOf('ipaper') >= 0) {
			var html = '';
			if (e.record.ipaper != 2) {
				html = '<div id="filedata_' + e.record.sid + '" class="file_data" style="height: auto;">';
				if (!e.record.sfiletplid && e.record.attachs) {
					$.each(e.record.attachs, function() {
						html += '<div><p style="width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; float: left;"'
								+ 'title="' + this.snameattach + '">' + this.snameattach + '</p>&emsp;<a href="javascript:void(0);"'
								+ 'title="删除" onclick="delFile(this, \'' + this.sid + '\', true)">删除</a></div><br>';
					})
				}
				html += '</div>';
				if (e.record.sfiletplid) {
					html += '<a href="javascript:void(0);" class="online_edit edit" data-id="' + e.record.sid + '"'
						+ 'onclick="onlineEdit(this, \'' + e.record.spathattach + '\')">在线编辑</a>';
				} else {
					html += '<div id="upload_' + e.record.sid + '" class="upload_file" style="display: inline-flex; width: 68px;' 
						+ ' height: 23px;" data-id="' + e.record.sid + '" data-hide="false">选择文件</div>';
				}
			} else {
				html = '<div id="filedata_' + e.record.sid + '" class="file_data" style="display: none; height: auto;"></div>';
				if (e.record.sfiletplid) {
					html += '<a href="javascript:void(0);" class="online_edit edit" data-id="' + e.record.sid + '" style="display: none;"' 
						+ ' onclick="onlineEdit(this, \'' + e.record.spathattach + '\')">在线编辑</a>';
				} else {
					html += '<div id="upload_' + e.record.sid + '" class="upload_file" style="display: inline-flex; width: 68px;' 
						+ ' height: 23px;" data-id="' + e.record.sid + '" data-hide="true">选择文件</div>';
				}
			}
			e.html = html;
		} else if (editField.indexOf('ipaper') < 0) {
			var $html = $('<div><a id="view_attachs_' + e.record.sid + '" class="attach" href="javascript:void(0);">附件下载</a>' 
					+ '&emsp;<a id="audit_' + e.record.sid + '" class="audit" href="javascript:void(0);">审批意见</a></div>');
			if (e.record.attachs != null && e.record.attachs.length > 0) {
				$html.find('a').addClass('edit');
				$html.find('.attach').on('click', function() {
					top.search.popDialog({ url : CTX + "/applys/goApplysAttachsViewPage", title : "申请附件详情", width : 600, height : 450, onload : function(window) {
						window.setData({ sid : e.record.sid });
					}, ondestroy : function(result) {
					} })
				});
			} else {
				$html.find('.attach').addClass('btn-disable');
				$html.find('.audit').addClass('edit');
			}
			$html.find('.audit').on('click', function() {
				top.search.popDialog({ url : CTX + "/applys/goAuditTplDetailAuditPage", title : "审批意见", width : 450, height : 280, onload : function(window) {
					window.setData({ sid : e.record.sid, editfield : editField, record : e.record });
				}, ondestroy : function(result) {
				} })
			});
			e.html = $html;
		}
	}
}

// 页面加载完成执行
function treedatagridloaded(e) {
	var $uploadFile = $('.upload_file');
	if ($uploadFile && $uploadFile.length > 0) {
		$.each($uploadFile, function() {
			var $this = $(this);
			new applyUploader().createWebUploader('#filedata_' + $this.data('id'), '#upload_' + $this.data('id'));
			var $webuploaderPick = $($this.children('.webuploader-pick')[0]);
			$webuploaderPick.css({'padding' : '5px 10px', 'border-radius' : '0'});
			if ($this.data('hide')) {
				$this.hide();
			}
		})
	}
	
	var $onlineEdit = $('.online_edit');
	
}

// 是否电子文档改变事件
$('#treedatagrid').on('change', '.ipaper_checkbox', function() {
	var $this = $(this);
	var $nexttd = $this.parent('td').next('td');
	if($this.is(':checked')) {
		$nexttd.find('*').show();
		$nexttd.find('.upload_file').css('display', 'inline-flex');
		$($nexttd.find('.upload_file').children('div')[1]).css({'width' : '68px', 'height' : '23px'});
	} else {
		$nexttd.find('*').hide();
	}
})

// 保存
function saveData(issubmit) {
	if (!form.validate()) {
		// 验证不通过
		if (instanceStep.stepNum != 1) {
			instanceStep.stepClick(1, false);
		}
		return;
	}
	var stepData = getDatas();
	var formData = form.getData();
	if (issubmit && (!stepData.processstepsid || !stepData.stepoperatorsid) && stepData.statuscode != 100001) {
		// 提交，未选择步骤和人
		top.search.error({ content : "请选择下一步骤和处理人!" });
		return;
	} else if (issubmit && stepData.processstepsid && stepData.stepoperatorsid) {
		// 提交，已选择步骤和人
		formData = $.extend(stepData, form.getData(), {stepsdesc : stepData.sdesc});
	}
	
	formData = $.extend(formData, {issubmit : issubmit});
	if (parentData.action == 'add') {
		formData = $.extend(formData, {processdesignsid : parentData.processdesignsid});
	} else {
		formData = $.extend(formData, {processinstancesid : parentData.processinstancesid, processdesignsid : stepData.sprocessdesignid});
	}
	var type = itype.getValue(), project = sprojectlibid.getSelectedItem();
	var $ipaper = $('.ipaper_checkbox');
	var audittpldetailArr = new Array();
	if (editField.indexOf('ipaper') < 0) {
		if (auditTplDetailType == type && auditTplDetailProject == project.record.sauditorgid && $ipaper && $ipaper.length > 0) {
			$.each($ipaper, function() {
				var $this = $(this);
				var rowsid = $this.val() || '';
				if ($this.data('type') == '102') {
					var paper = ($this.is(':checked') ? 1 : 2) || '';
					var audittpldetail = rowsid + ',' + paper;
					if (paper == 1) {
						var attach = $('#filedata_' + rowsid).data('filedata');
						if (attach && attach.length > 0) {
							audittpldetail += (',' + attach.join(','));
						}
					}
					audittpldetailArr.push(audittpldetail);
				} else {
					audittpldetailArr.push(rowsid);
				}
			});
		}
	}
	formData = $.extend(formData, {sid : parentData.sid, audittpldetails : audittpldetailArr.join(';'), 
		delfileids : delFileId.join(','), delfilepaths : delFilePath.join(','), pathpre : Globle.constant.upload.applys});

	$.post(CTX + '/applys/insertOrUpdateApplysAndAuditTplDetailCopys', formData, function(result) {
		if (result.status) {
			top.search.info({ content : result.result.msg, funl : function() {
				window.closeWindow("ok");
			} });
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}

//图片上传
var applyUploader = function () {
	var obj = new Object();
	obj.createWebUploader = function(list, picker) {
		obj.list = list, obj.picker = picker;
		var fileType = 'doc,docx,xls,xlsx';
		var $ = jQuery, $list = $(list),
		// Web Uploader实例
		uploader;
	
		// 初始化Web Uploader
		uploader = WebUploader.create({
	
			// 文件上传请求的参数表，每次发送都会发送此对象中的参数。
			formData : {relativePath : Globle.constant.upload.applys},
				
			// 自动上传。
			auto : true,
		
			// swf文件路径
			swf : LIB + '/webuploader/Uploader.swf',
		
			// 文件接收服务端。
			server : CTX + "/file/upload/settingsUpload",
		
			// 选择文件的按钮。可选。
			// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			pick : { id : picker, multiple : true },
		
			// 只允许选择文件，可选。
			accept : { extensions : fileType } });
	
		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
		uploader.on('uploadSuccess', function(file, response) {
			if (response && response.result) {
				var $fileelem = $('<div><p style="width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; float: left;"'
						+ 'title="' + file.name + '">' + file.name + '</p>&emsp;<a href="javascript:void(0);" title="删除"'
						+ 'onclick="delFile(this, \'' + response.result.spath + '\', false)">删除</a></div><br>');
				$list.append($fileelem);
				var filedata = $list.data('filedata') || new Array(), fileArr = new Array();
				fileArr.push(file.name);
				fileArr.push(response.result.spath);
				filedata.push(fileArr.join('|'));
				$list.data('filedata', filedata);
			}
		});
	
		// 文件上传失败，现实上传出错。
		uploader.on('uploadError', function(file) {
			
		});
		
		// 文件添加队列异常
		uploader.onError = function( code ) {
            switch( code ) {
            	case 'Q_EXCEED_NUM_LIMIT':
            		top.search.info({ content : '最多可上传' + uploader.option('fileNumLimit') + '个文件' });
            		break;
            	case 'Q_EXCEED_SIZE_LIMIT':
            		top.search.info({ content : '文件总大小不能超过' + WebUploader.formatSize(uploader.option('fileSizeLimit')) });
            		break;
            	case 'Q_TYPE_DENIED':
            		top.search.info({ content : '文件类型不允许上传(允许上传类型：' + uploader.option('accept')[0].extensions + ')' });
            		break;F_DUPLICATE
            	case 'F_DUPLICATE':
            		top.search.info({ content : '文件在队列中已存在' });
            		break;
            	default:
            		top.search.error({ content : "未知错误！" });
            }
        };
		
		return obj;
	};
	obj.setValue = function(img) {
		var $list = $(obj.list);
	};
	return obj;
}

// 删除文件
function delFile(elem, param, isid) {
	var $elem = $(elem);
	if (isid) {
		delFileId.push(param);
	} else {
		delFilePath.push(param);
		var $list = $elem.parents('.file_data');
		var fileDatas = $list.data('filedata'), newFileDatas = new Array();
		for (var i = 0; i < fileDatas.length; i++) {
			if (fileDatas[i].indexOf(param) == -1) {
				newFileDatas.push(fileDatas[i]);
			}
		}
		$list.data('filedata', newFileDatas);
	}
	$elem.parent('div').remove();
}

// 在线编辑
function onlineEdit(elem, path) {
	var $this = $(elem);
	$.post(CTX + '/applys/findOnlineEditFileUrl', {relativePath : path, isedit : true, iscopy : (parentData.action != 'edit'), pathpre : Globle.constant.upload.applys}, function(result) {
		if (result.status) {
			top.search.popDialog({ url : CTX + '/applys/goOnlineEditFilePage', title : "编辑", width : 900, height : 550, onload : function(window) {
				window.setData({ url : result.result.operurl });
			}, ondestroy : function(result) {
			} });
			
			if (parentData.action != 'edit') {
				var filedata = new Array();
				filedata.push(result.result.filename + '|' + result.result.filepath);
				$this.prev('.file_data').data('filedata', filedata);
			}
		}
	}) 
}

// 即将进入审核意见
function opinionWillEnter() {
}

// 回退
function auditBack() {
	rollbackStep(function(result) {
		if (result.status) {
			saveData(false);
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}