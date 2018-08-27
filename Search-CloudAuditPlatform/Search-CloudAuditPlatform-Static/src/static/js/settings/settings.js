search.parse();
var form = new Form("setting");

var isupportusernumber = search.get("isupportusernumber");
var sorgtype = search.get("sorgtype");
var imaxnumber = search.get("imaxnumber");

// 页面加载完成执行
$(function() {
	var siconUpload = new settingUploader().createWebUploader("#siconList", "#siconPicker", "ico", search.get("sicon"));
	var slogoUpload = new settingUploader().createWebUploader("#slogoList", "#slogoPicker", "gif,jpg,jpeg,bmp,png", search.get("slogo"));
	
	isupportusernumber.loadData(Globle.constant.YesOrNo);
	sorgtype.loadData(Globle.constant.OrgType);

	$.post(CTX + "/settings/getSettingsOne", function(result) {
		if (result.status && result.result) {
			form.setData(result.result);
			
			if (result.result.sicon) {
				siconUpload.setValue(NETWORK_ROOT + result.result.sicon);
			}
			
			if (result.result.slogo) {
				slogoUpload.setValue(NETWORK_ROOT + result.result.slogo);
			}
		}
	})
})

// 是否启用用户数量控制改变事件
function isupportusernumberchange(e) {
	if (e.value == "1") {
		imaxnumber.rules = "required";
	} else if (e.value == "2") {
		imaxnumber.rules = "";
	}
}

// 保存数据
function saveData() {
	if (!form.validate())
		return;
	$.post(CTX + "/settings/insertOrUpdateSettings", form.getData(), function(result) {
		if (result.status) {
			top.search.info({ content : result.msg});
		}
	})
}

// 图片上传
var settingUploader = function () {
	var obj = new Object();
	obj.createWebUploader = function(list, picker, fileType, searchInput) {
		obj.list = list, obj.picker = picker, obj.fileType = fileType, obj.searchInput = searchInput;
		var $ = jQuery, $list = $(list),
		// 优化retina, 在retina下这个值是2
		ratio = window.devicePixelRatio || 1,
	
		// 缩略图大小
		thumbnailWidth = 100 * ratio, thumbnailHeight = 100 * ratio,
		
		// Web Uploader实例
		uploader;
	
		// 初始化Web Uploader
		uploader = WebUploader.create({
	
		// 文件上传请求的参数表，每次发送都会发送此对象中的参数。
		formData : {relativePath : Globle.constant.upload.system},
			
		// 自动上传。
		auto : true,
	
		// swf文件路径
		swf : LIB + '/webuploader/Uploader.swf',
	
		// 文件接收服务端。
		server : CTX + "/file/upload/settingsUpload",
	
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick : { id : picker, multiple : false },
	
		// 只允许选择文件，可选。
		accept : { title : 'Images', extensions : fileType, mimeTypes : 'image/*' } });
	
		// 当文件被加入队列之前触发，此事件的handler返回值为false，则此文件不会被添加进入队列。
		uploader.on('beforeFileQueued', function(file) {
			if (fileType.indexOf(file.ext) != -1) {
				var fileQueueds = uploader.getFiles();
				if (fileQueueds.length > 0) {
					// 当上传队列中有文件时，移除队列中的文件
					var fileQueued = fileQueueds[0];
					uploader.removeFile(fileQueued);
					uploader.reset();
		
					// 移除队列文件后，缩略图替换为新文件
					var $li = $("#" + fileQueued.id);
					var $img = $li.find('img');
		
					$li.attr("id", file.id).find(".info").html(file.name);
					createThumbnail(file, $img);
				} else {
					// 当上传队列中没有文件时，创建缩略图所需容器
					$list.find(".defaultImg").remove();
					var $li = $('<div id="' + file.id + '" class="file-item thumbnail"><img><div class="info">' + file.name + '</div></div>');
					var $img = $li.find('img');
		
					$list.append($li);
		
					createThumbnail(file, $img);
				}
			} else {
				top.search.warn({ content : "请选择" + fileType + "格式的文件！" });
			}
		})
	
		// 创建缩略图，并添加到img标签
		function createThumbnail(file, $img) {
			// 创建缩略图
			uploader.makeThumb(file, function(error, src) {
				if (error) {
					$img.replaceWith('<span>不能预览</span>');
					return;
				}
	
				$img.attr('src', src);
			}, thumbnailWidth, thumbnailHeight);
		}
	
		// 文件上传过程中创建进度条实时显示。
		uploader.on('uploadProgress', function(file, percentage) {
			var $li = $('#' + file.id), $percent = $li.find('.progress span');
	
			// 避免重复创建
			if (!$percent.length) {
				$percent = $('<p class="progress"><span></span></p>').appendTo($li).find('span');
			}
	
			$percent.css('width', percentage * 100 + '%');
		});
	
		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
		uploader.on('uploadSuccess', function(file, response) {
			var $li = $('#' + file.id), $error = $li.find('div.success');
	
			// 避免重复创建
			if (!$error.length) {
				$error = $('<div class="success"></div>').appendTo($li);
			}
	
			$error.text('上传成功');
			
			searchInput.setValue(response.result.spath);
		});
	
		// 文件上传失败，现实上传出错。
		uploader.on('uploadError', function(file) {
			var $li = $('#' + file.id), $error = $li.find('div.error');
	
			// 避免重复创建
			if (!$error.length) {
				$error = $('<div class="error"></div>').appendTo($li);
			}
	
			$error.text('上传失败');
		});
	
		// 完成上传完了，成功或者失败，先删除进度条。
		uploader.on('uploadComplete', function(file) {
			$('#' + file.id).find('.progress').remove();
		});
		
		return obj;
	};
	obj.setValue = function(img) {
		var $list = $(obj.list);
		var container = '<div class="file-item thumbnail defaultImg"><p class="imgWrap"><img src="' + img + '"/></p>'
						+ '<div class="file-panel" style="height: 0px;"><span><i class="fa fa-trash"></i></span></div></div>'
		;
		$list.append(container);
		$list.on('mouseenter', '.file-item', function () {
			$list.find(".file-panel").animate({height: 30});
        });

		$list.on('mouseleave', '.file-item', function () {
			$list.find(".file-panel").animate({height: 0});
        });
		
		$list.on('click', 'i.fa-trash', function() {
			$list.find(".defaultImg").remove();
			obj.searchInput.setValue("");
		})
	};
	return obj;
}