// var Upload = {
//     defaultOptions: {
//         checkChunkUrl: CTX + '/file/upload/checkChunk',//检查分块的url
//         fileLimtFun: function (stateCode) {//文件受限的回调
//             if (stateCode === 201) {
//                 alert("文件格式不正确");
//             } else if (stateCode === 202) {
//                 alert("文件大小超出限制");
//             } else if (stateCode === 500) {
//                 alert("服务器繁忙");
//             } else {
//                 alert("文件受限");
//             }
//         },
//         uploadUrl: CTX + '/file/upload/upload',//文件上传的url
//         mergeChunkUrl: CTX + '/file/upload/mergeChunk',//分块合并的url
//         uploadFinished: function (urls) {//所有文件上传完成的回调
//             console.error(urls);
//         },
//         initFiles: {
//             rootPath: '/upload',
//             files: []
//         }
//     },
//     upload: function (opts) {
//         var me = this, map = new Globle.obj.Map(), resultArr = [];
//         //监听分块上传过程中的三个时间点
//         WebUploader.Uploader.register({
//             "before-send-file": "beforeSendFile",
//             "before-send": "beforeSend",
//             "after-send-file": "afterSendFile"
//         }, {
//             //时间点1：所有分块进行上传之前调用此函数
//             beforeSendFile: function (file) {
//                 console.log("beforeSendFile---读取文件信息");
//                 console.log(file);
//                 var deferred = WebUploader.Deferred();
//                 //1、使用md5计算文件的唯一标记，用于断点续传
//                 (new WebUploader.Uploader()).md5File(file)//(file,0,10*1024*1024)
//                     .progress(function (percentage) {
//                         $('#item1').find("p.state").text("正在读取文件信息...");
//                     })
//                     .then(function (val) {
//                         map.put(file.id, val);
//                         // formData.fileMd5 = val;
//                         $('#item1').find("p.state").text("成功获取文件信息...");
//                         //获取文件信息后进入下一步
//                         deferred.resolve();
//                     });
//                 //调用deferred.resolve();无效
//                 return deferred.promise();
//             },
//             //时间点2：如果有分块上传，则每个分块上传之前调用此函数
//             beforeSend: function (block) {
//                 console.log("beforeSend----检查文件分块是否存在，是否完整", block);
//                 var fileMd5 = map.get(block.file.id);
//                 // console.error("1次md5打印" + fileMd5);
//                 var formData = this.owner.options.formData;
//                 var deferred = WebUploader.Deferred();
//                 $.ajax({
//                     abortOnRetry: false,
//                     type: "POST",
//                     url: opts.checkChunkUrl || me.defaultOptions.checkChunkUrl,// CTX + '/file/upload/checkChunk', // HEINFO:2018/3/22 15:17 检查分块url
//                     data: {
//                         //文件唯一标记
//                         fileMd5: fileMd5,
//                         // fileMd5: formData.fileMd5,
//                         //当前分块下标
//                         chunk: block.chunk,
//                         //当前分块大小
//                         chunkSize: block.end - block.start,
//                         //总大小
//                         totalSize: block.total,
//                         //扩展名
//                         ext: block.file.ext
//                     },
//                     // async: false,  // 同步
//                     dataType: "json",
//                     success: function (result) {
//                         console.log("检查结果", result);
//                         //分块存在
//                         if (result.success) {
//                             console.log("跳过第" + block.chunk + "分块*********************************************");
//                             //分块存在，跳过
//                             deferred.reject();
//                         } else {
//                             if (result.code === 201 || result.code === 202) {
//                                 console.error("文件格式不正确或大小超出限制");
//                                 uploader.stop(true);
//                                 // HEINFO:2018/3/22 15:17 文件受限回调
//                                 opts.fileLimtFun(result.code);
//                             } else {
//                                 formData.fileMd5 = map.get(block.file.id);
//                                 // console.error("2次md5打印" + map.get(block.file.id));
//                                 console.log("上传第" + block.chunk + "分块*********************************************");
//                                 //分块不存在或不完整，重新发送该分块内容
//                                 deferred.resolve();
//                             }
//                         }
//                     },
//                     //任何形式的验证失败，都触发重新上传
//                     error: function (jqXHR, textStatus, errorThrown) {
//                         console.error("检查出错");
//                         uploader.stop(true);
//                         // HEINFO:2018/3/22 15:17 文件受限回调
//                         opts.fileLimtFun(500);
//                         deferred.resolve();
//                     }
//                 });
//                 return deferred.promise();
//             },
//             //时间点3：所有分块上传成功后调用此函数
//             afterSendFile: function (file) {
//                 console.log("afterSendFile---发送合并请求", file);
//                 var formData = this.owner.options.formData;
//                 // 如果分块上传成功，则通知后台合并分块
//                 $.ajax({
//                     async: false,  // 同步
//                     type: "POST",
//                     url: opts.mergeChunkUrl || me.defaultOptions.mergeChunkUrl,//CTX + '/file/upload/mergeChunk', // HEINFO:2018/3/22 15:17 合并分块的url
//                     data: {
//                         fileMd5: map.get(file.id),
//                         // fileMd5: formData.fileMd5,
//                         fileName: file.name
//                     },
//                     success: function (result) {
//                         resultArr.push(result.data);
//                         console.warn("合并结果", result, --fileCount);
//                     }
//                 });
//             }
//         });
//         var $wrap = $('#uploader'),
//             // 图片容器
//             $queue = $('<ul class="filelist"></ul>').appendTo($wrap.find('.queueList')),
//             // 状态栏，包括进度和控制按钮
//             $statusBar = $wrap.find('.statusBar'),
//             // 文件总体选择信息。
//             $info = $statusBar.find('.info'),
//             // 上传按钮
//             $upload = $wrap.find('.uploadBtn'),
//             // 没选择文件之前的内容。
//             $placeHolder = $wrap.find('.placeholder'),
//             // 总体进度条
//             $progress = $statusBar.find('.progress').hide(),
//             // 添加的文件数量
//             fileCount = 0,
//             // 添加的文件总大小
//             fileSize = 0,
//             // 优化retina, 在retina下这个值是2
//             ratio = window.devicePixelRatio || 1,
//             // 缩略图大小
//             thumbnailWidth = 110 * ratio,
//             thumbnailHeight = 110 * ratio,
//             // 可能有pedding, ready, uploading, confirm, done.
//             state = 'pedding',
//             // 所有文件的进度信息，key为file id
//             percentages = {},
//             supportTransition = (function () {
//                 var s = document.createElement('p').style,
//                     r = 'transition' in s ||
//                         'WebkitTransition' in s ||
//                         'MozTransition' in s ||
//                         'msTransition' in s ||
//                         'OTransition' in s;
//                 s = null;
//                 return r;
//             })(),
//
//             // WebUploader实例
//             uploader;
//
//         if (!WebUploader.Uploader.support()) {
//             alert('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
//             throw new Error('WebUploader does not support the browser you are using.');
//         }
//
//         // 实例化
//         me['uploader'] = uploader = WebUploader.create({
//             dnd: '#uploader .queueList',
//             disableGlobalDnd: true,
//             paste: document.body,
//             pick: {
//                 id: '#filePicker',
//                 innerHTML: '点击选择文件',
//                 multiple: true
//             },
//             //文件的限制类型
//             // accept: {
//             //     title: 'Images',
//             //     extensions: 'gif,jpg,jpeg,bmp,png',
//             //     mimeTypes: 'image/*'
//             // },
//             //缩略图
//             // thumb:{
//             //     width: 110,
//             //     height: 110,
//             //     // 图片质量，只有type为`image/jpeg`的时候才有效。
//             //     quality: 70,
//             //     // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
//             //     allowMagnify: true,
//             //     // 是否允许裁剪。
//             //     crop: true,
//             //     // 为空的话则保留原有图片格式。
//             //     // 否则强制转换成指定的类型。
//             //     type: 'image/jpeg'
//             // },
//             // 配置压缩的图片的选项。如果此选项为false, 则图片在上传前不进行压缩
//             // compress:{
//             //     width: 1600,
//             //     height: 1600,
//             //     // 图片质量，只有type为`image/jpeg`的时候才有效。
//             //     quality: 90,
//             //     // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
//             //     allowMagnify: false,
//             //     // 是否允许裁剪。
//             //     crop: false,
//             //     // 是否保留头部meta信息。
//             //     preserveHeaders: true,
//             //     // 如果发现压缩后文件大小比原来还大，则使用原来图片
//             //     // 此属性可能会影响图片自动纠正功能
//             //     noCompressIfLarger: false,
//             //     // 单位字节，如果图片大小小于此值，不会采用压缩。
//             //     compressSize: 0
//             // },
//             // [默认值：false] 设置为 true 后，不需要手动调用上传，有文件选择即开始上传。
//             auto: false,
//             prepareNextFile: true,
//             chunked: true,
//             chunkSize: 10485760,//10M
//             chunkRetry: 2,
//             threads: 1,
//             //去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
//             duplicate: true,
//             swf: LIB + '/webuploader/Uploader.swf',
//             server: opts.uploadUrl || me.defaultOptions.uploadUrl//CTX + '/file/upload/upload',
//         });
//
//         // 添加“添加文件”的按钮，
//         uploader.addButton({
//             id: '#filePicker2',
//             label: '继续添加'
//         });
//
//         // HEINFO:2018/3/22 16:33 初始化文件渲染回调
//         // var fileArr = [{id: "idxxx", name: "文件名", src: "/upload/search/a/f1.JPG"}];
//         var initFiles = opts.initFiles || me.defaultOptions.initFiles;
//         if (initFiles.files.length > 0) {
//             $placeHolder.addClass('element-invisible');//隐藏原始拖拽div
//             $statusBar.show();//显示状态div
//             var rootPath = initFiles.rootPath;
//             $.each(initFiles.files, function (i, v) {
//                 addViewFile(rootPath, v);
//             })
//
//         }
//
//         function addViewFile(root, file) {
//             var $li = $('<li id="' + file.sid + '">' +
//                     '<p class="title">' + file.sname + '</p>' +
//                     '<p class="imgWrap"></p>' +
//                     '<p class="progress"><span></span></p>' +
//                     '</li>'),
//
//                 $btns = $('<div class="file-panel">' +
//                     '<span class="cancel">删除</span>' +
//                     // '<span class="rotateRight">向右旋转</span>' +
//                     // '<span class="rotateLeft">向左旋转</span>' +
//                     '</div>').appendTo($li),
//                 $wrap = $li.find('p.imgWrap'),
//                 img = $('<img src="' + root + file.spath + '">');
//             $wrap.empty().append(img);
//
//             $li.on('mouseenter', function () {
//                 $btns.stop().animate({height: 30});
//             });
//
//             $li.on('mouseleave', function () {
//                 $btns.stop().animate({height: 0});
//             });
//
//             $btns.on('click', 'span', function () {
//                 var index = $(this).index(), deg;
//                 switch (index) {
//                     case 0:
//                         console.log($(this).parent().parent().attr('id'));
//                         return;
//
//                     case 1:
//                         file.rotation += 90;//
//                         break;
//
//                     case 2:
//                         file.rotation -= 90;
//                         break;
//                 }
//
//                 if (supportTransition) {
//                     deg = 'rotate(' + file.rotation + 'deg)';
//                     $wrap.css({
//                         '-webkit-transform': deg,
//                         '-mos-transform': deg,
//                         '-o-transform': deg,
//                         'transform': deg
//                     });
//                 } else {
//                     $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
//                 }
//
//
//             });
//             $li.appendTo($queue);
//             $placeHolder.addClass('element-invisible');
//             $('#filePicker2').removeClass('element-invisible');
//             $queue.parent().addClass('filled');
//             $queue.show();
//             $statusBar.removeClass('element-invisible');
//         }
//
//         // 当有文件添加进来时执行，负责view的创建
//         function addFile(file) {
//             var $li = $('<li id="' + file.id + '">' +
//                     '<p class="title">' + file.name + '</p>' +
//                     '<p class="imgWrap"></p>' +
//                     '<p class="progress"><span></span></p>' +
//                     '</li>'),
//
//                 $btns = $('<div class="file-panel">' +
//                     '<span class="cancel">删除</span>' +
//                     // '<span class="rotateRight">向右旋转</span>' +
//                     // '<span class="rotateLeft">向左旋转</span>' +
//                     '</div>').appendTo($li),
//                 $prgress = $li.find('p.progress span'),
//                 $wrap = $li.find('p.imgWrap'),
//                 $info = $('<p class="error"></p>'),
//
//                 showError = function (code) {
//                     switch (code) {
//                         case 'exceed_size':
//                             text = '文件大小超出';
//                             break;
//
//                         case 'interrupt':
//                             text = '上传暂停';
//                             break;
//                         case 'limt':
//                             text = '文件受限';
//                             break;
//
//                         default:
//                             text = '上传失败，请重试';
//                             break;
//                     }
//
//                     $info.text(text).appendTo($li);
//                 };
//
//             if (file.getStatus() === 'invalid') {
//                 showError(file.statusText);
//             } else {
//                 // @todo lazyload
//                 $wrap.text('预览中');
//                 uploader.makeThumb(file, function (error, src) {
//                     if (error) {
//                         $wrap.text('不能预览');
//                         return;
//                     }
//
//                     var img = $('<img src="' + src + '">');
//                     $wrap.empty().append(img);
//                 }, thumbnailWidth, thumbnailHeight);
//
//                 percentages[file.id] = [file.size, 0];
//                 file.rotation = 0;
//             }
//
//             file.on('statuschange', function (cur, prev) {
//                 if (prev === 'progress') {
//                     $prgress.hide().width(0);
//                 } else if (prev === 'queued') {
//                     $li.off('mouseenter mouseleave');
//                     $btns.remove();
//                 }
//
//                 if (cur === 'interrupt' && prev === 'progress') {
//                     showError('limt');
//                 } else if (cur === 'error' || cur === 'invalid') {
//                     showError(file.statusText);
//                     percentages[file.id][1] = 1;
//                 } else if (cur === 'interrupt') {
//                     showError('interrupt');
//                 } else if (cur === 'queued') {
//                     percentages[file.id][1] = 0;
//                 } else if (cur === 'progress') {
//                     $info.remove();
//                     $prgress.css('display', 'block');
//                 } else if (cur === 'complete') {
//                     $li.append('<span class="success"></span>');
//                 }
//
//                 $li.removeClass('state-' + prev).addClass('state-' + cur);
//             });
//
//             $li.on('mouseenter', function () {
//                 $btns.stop().animate({height: 30});
//             });
//
//             $li.on('mouseleave', function () {
//                 $btns.stop().animate({height: 0});
//             });
//
//             $btns.on('click', 'span', function () {
//                 var index = $(this).index(), deg;
//                 switch (index) {
//                     case 0:
//                         uploader.removeFile(file);//移除文件
//                         return;
//
//                     case 1:
//                         file.rotation += 90;//
//                         break;
//
//                     case 2:
//                         file.rotation -= 90;
//                         break;
//                 }
//
//                 if (supportTransition) {
//                     deg = 'rotate(' + file.rotation + 'deg)';
//                     $wrap.css({
//                         '-webkit-transform': deg,
//                         '-mos-transform': deg,
//                         '-o-transform': deg,
//                         'transform': deg
//                     });
//                 } else {
//                     $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
//                     // use jquery animate to rotation
//                     // $({
//                     //     rotation: rotation
//                     // }).animate({
//                     //     rotation: file.rotation
//                     // }, {
//                     //     easing: 'linear',
//                     //     step: function( now ) {
//                     //         now = now * Math.PI / 180;
//
//                     //         var cos = Math.cos( now ),
//                     //             sin = Math.sin( now );
//
//                     //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
//                     //     }
//                     // });
//                 }
//
//
//             });
//
//             $li.appendTo($queue);
//         }
//
//         // 负责view的销毁
//         function removeFile(file) {
//             var $li = $('#' + file.id);
//
//             delete percentages[file.id];
//             updateTotalProgress();
//             $li.off().find('.file-panel').off().end().remove();
//         }
//
//         function updateTotalProgress() {
//             var loaded = 0,
//                 total = 0,
//                 spans = $progress.children(),
//                 percent;
//
//             $.each(percentages, function (k, v) {
//                 total += v[0];
//                 loaded += v[0] * v[1];
//             });
//
//             percent = total ? loaded / total : 0;
//
//             spans.eq(0).text(Math.round(percent * 100) + '%');
//             spans.eq(1).css('width', Math.round(percent * 100) + '%');
//             updateStatus();
//         }
//
//         function updateStatus() {
//             var text = '', stats;
//
//             if (state === 'ready') {
//                 text = '选中' + fileCount + '张图片，共' +
//                     WebUploader.formatSize(fileSize) + '。';
//             } else if (state === 'confirm') {
//                 stats = uploader.getStats();
//                 if (stats.uploadFailNum) {
//                     text = '已成功上传' + stats.successNum + '张照片至XX相册，' +
//                         stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
//                 }
//
//             } else {
//                 stats = uploader.getStats();
//                 text = '共' + fileCount + '张（' +
//                     WebUploader.formatSize(fileSize) +
//                     '），已上传' + stats.successNum + '张';
//
//                 if (stats.uploadFailNum) {
//                     text += '，失败' + stats.uploadFailNum + '张';
//                 }
//             }
//
//             $info.html(text);
//         }
//
//         function setState(val) {
//             var stats;
//
//             if (val === state) {
//                 return;
//             }
//
//             // $upload.removeClass('state-' + state);
//             // $upload.addClass('state-' + val);
//             state = val;
//
//             switch (state) {
//                 case 'pedding':
//                     $placeHolder.removeClass('element-invisible');
//                     $queue.parent().removeClass('filled');
//                     $queue.hide();
//                     $statusBar.addClass('element-invisible');
//                     uploader.refresh();
//                     break;
//
//                 case 'ready':
//                     $placeHolder.addClass('element-invisible');
//                     $('#filePicker2').removeClass('element-invisible');
//                     $queue.parent().addClass('filled');
//                     $queue.show();
//                     $statusBar.removeClass('element-invisible');
//                     uploader.refresh();
//                     break;
//
//                 case 'uploading':
//                     $('#filePicker2').addClass('element-invisible');
//                     $progress.show();
//                     // $upload.text('暂停上传');
//                     break;
//
//                 case 'paused':
//                     $progress.show();
//                     // $upload.text('继续上传');
//                     break;
//
//                 case 'confirm':
//                     $progress.hide();
//                     // $upload.text('开始上传').addClass('disabled');
//
//                     stats = uploader.getStats();
//                     if (stats.successNum && !stats.uploadFailNum) {
//                         setState('finish');
//                         return;
//                     }
//                     break;
//                 case 'finish':
//                     stats = uploader.getStats();
//                     if (stats.successNum) {
//                         // alert( '上传成功' );
//                     } else {
//                         // 没有成功的图片，重设
//                         state = 'done';
//                         location.reload();
//                     }
//                     break;
//             }
//
//             updateStatus();
//         }
//
// //上传过程中触发，携带上传进度。
//         uploader.on('uploadProgress', function (file, percentage) {
//             var $li = $('#' + file.id),
//                 $percent = $li.find('.progress span');
//
//             $percent.css('width', percentage * 100 + '%');
//             percentages[file.id][1] = percentage;
//             updateTotalProgress();
//         });
// //当文件被加入队列以后触发
//         uploader.on('fileQueued', function (file) {
//             fileCount++;
//             fileSize += file.size;
//
//             if (fileCount === 1) {
//                 $placeHolder.addClass('element-invisible');//隐藏原始拖拽div
//                 $statusBar.show();//显示状态div
//             }
//
//             addFile(file);
//             setState('ready');
//             updateTotalProgress();
//         });
// //当文件被移除队列后触发
//         uploader.on('fileDequeued', function (file) {
//             fileCount--;
//             fileSize -= file.size;
//
//             if (!fileCount) {
//                 setState('pedding');
//             }
//
//             removeFile(file);
//             updateTotalProgress();
//         });
// //当validate不通过时
//         uploader.on('error', function (code) {
//             alert('Eroor: ' + code);
//         });
//         uploader.on('uploadComplete', function (file) {
//             // console.error(file);
//         });
//
//         uploader.on('uploadError', function (file, reason) {
//             console.warn("文件上传出错", file, reason);
//         });
//         uploader.on('uploadSuccess', function (file, response) {
//             console.warn("文件单次完成", file, response);
//         });
//         uploader.on('uploadFinished', function () {
//             console.error("所有文件上传完成");
//             // HEINFO:2018/3/22 15:57 所有文件上传完成后的回调
//             opts.uploadFinished(resultArr.join(","));
//         });
//
//         uploader.on('all', function (type) {
//             var stats;
//             switch (type) {
//                 case 'uploadFinished':
//                     setState('confirm');
//                     break;
//
//                 case 'startUpload':
//                     setState('uploading');
//                     break;
//
//                 case 'stopUpload':
//                     setState('paused');
//                     break;
//
//             }
//         });
//
//
//         // $upload.on('click', function () {
//         //     if ($(this).hasClass('disabled')) {
//         //         return false;
//         //     }
//         //
//         //     if (state === 'ready') {
//         //         uploader.upload();
//         //     } else if (state === 'paused') {
//         //         uploader.upload();
//         //     } else if (state === 'uploading') {
//         //         uploader.stop();
//         //     }
//         // });
//
//         $info.on('click', '.retry', function () {
//             uploader.retry();
//         });
//
//         $info.on('click', '.ignore', function () {
//             alert('todo');
//         });
//
//         // $upload.addClass('state-' + state);
//         updateTotalProgress();
//     }
// }


$(function () {

    $('#btn1').on('click', function () {
        console.log(upload1.delArr);//获取删除附件的id
        upload1.upload();
    });
    $('#btn2').on('click', function () {
        console.log(upload2.delArr);//获取删除附件的id
        upload2.upload();
    });
    $('#btn3').on('click', function () {
        console.log(upload3.delArr);//获取删除附件的id
        upload3.upload();
    });
    var upload1 = Upload.upload({
        uploadFinished: function (datas) {
            console.log(datas);
        }

        ,
        // regWidget:true,
        name:'upload1',
        containerId: 'uploader',
        onePickerId: 'filePicker',
        twoPickerId: 'filePicker2'


        //    ,
        //    initFiles: {
        //        rootPath: "/upload",
        //        files: [
        //            {
        //                sid: "xxx1",
        //               sname: "wocao",
        //               spath: "/search/a/f1.JPG"
        //            },
        //            {
        //                sid: "xxx2",
        //                sname: "wocao",
        //                spath: "/search/a/f2.JPG"
        //           }
        //      ]
        // }

    });
    var upload2 = Upload.upload({
        uploadFinished: function (datas) {
            console.log(datas);
        },
        name:'upload2',
        containerId: 'uploaderx',
        onePickerId: 'filePickerx',
        twoPickerId: 'filePicker2x'
          ,
          initFiles: {
              rootPath: "/upload",
              files: [
                  {
                      sid: "xxx3",
                     sname: "wocao",
                     spath: "/nodedev/he/merger/2018/04/23/20180423163440568_f1.jpg"
                  },
                  {
                      sid: "xxx4",
                      sname: "wocao",
                     spath: "/nodedev/he/merger/2018/04/23/20180423163754361_f2.jpg"
                 }
            ]
       }

    });
    setTimeout(function () {
        upload2.setData([
            {
                sid: "xxx3",
                sname: "wocao",
                spath: "/nodedev/he/merger/2018/04/23/20180423163916879_f3.jpg"
            }
        ]);
        console.log("定时2秒");
    },2000);
    var upload3 = Upload.upload({
        uploadFinished: function (datas) {
            console.log(datas);
        },
        name:'upload3',
        containerId: 'uploaderxx',
        onePickerId: 'filePickerxx',
        twoPickerId: 'filePicker2xx'
    });

});