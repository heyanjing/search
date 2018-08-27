// var Upload2 = {
//     resultArr: [],
//     allUploader: [],
//     quaList: undefined,
//     line: 0,
//     currentLine: 0,
//     createUploader: function () {
//         var me = this;
//         var uploader = WebUploader.create({
//             swf: LIB + '/webuploader/Uploader.swf',
//             // 文件接收服务端。
//             // server: CTX + '/file/upload/upload',
//             server: CTX + '/file/upload/settingsUpload',
//             pick: {
//                 id: '#picker' + me.line,
//                 multiple: false
//             },
//             resize: true,
//             auto: false,
//             threads: 1,
//         });
//         return uploader;
//     },
//     addQua: function () {
//         var me = this, line = ++me.line;
//         $('#qua').append('<tr> <td colspan="2"> <div id="qua' + line + '" class="search-select" rules="required" multiSelect="false" idField="id" textField="text" width="400" height="40"></div> </td> </tr> <tr> <td> <div id="sdesc' + line + '" class="search-textarea" width="300"></div> </td> <td id="img' + line + '">  <div id="picker' + line + '">选择文件</div> </td> </tr>');
//         search.parse();
//         var qua = search.get("qua" + line).loadData(me.quaList), uploader = me.createUploader();
//         uploader['fileCount'] = 0;
//         uploader.on('fileQueued', function (file) {
//             var fileId = file.id;
//             uploader.makeThumb(file, function (error, src) {
//                 var img = $('<img id="' + fileId + '" data-line="' + line + '" src="' + src + '">');
//                 $('#img' + line).empty().append(img);
//             }, 100, 100);
//             uploader.fileCount++;
//         });
//         uploader.on('fileDequeued', function (file) {
//         });
//         uploader.on('error', function (code) {
//             console.log('Eroor: ' + code);
//         });
//         uploader.on('uploadComplete', function (file) {
//             console.error("uploadComplete", file);
//         });
//         uploader.on('uploadSuccess', function (file, response) {
//             console.log("文件单次完成", file, response);
//             var fileId = file.id, currentLine = $('#' + fileId).attr("data-line");
//             var zz = search.get("qua" + currentLine).getValue();
//             var bz = search.get("sdesc" + currentLine).getValue();
//             me.resultArr.push({
//                 sdictionarieid: zz,
//                 sdesc: bz,
//                 sname: response.result.sname,
//                 spath: response.result.spath
//             });
//             me.upload();
//
//         });
//         me.allUploader.push(uploader);
//     },
//     upload: function () {
//         var me = this;
//         if (me.allUploader.length > me.currentLine) {
//             var uploader = me.allUploader[me.currentLine];
//             me.currentLine++;
//             if (uploader.fileCount > 0) {
//                 uploader.upload();
//             } else {
//                 var zz = search.get("qua" + me.currentLine).getValue();
//                 var bz = search.get("sdesc" + me.currentLine).getValue();
//                 me.resultArr.push({
//                     sdictionarieid: zz,
//                     sdesc: bz
//                 });
//                 me.upload();
//             }
//
//         } else {
//             console.error("回调其他方法", me.resultArr);
//             me.curropts.callback.call(this, me.resultArr);
//         }
//
//     },
//     defaultOpts: {
//         url: '',
//         data: {},
//         callback: $.noop
//     },
//     currentOpts: undefined,
//     init: function (opts) {
//         var me = this;
//         me.curropts = Globle.fun.applyIf(opts, me.defaultOpts);
//         $.ajax({
//             type: 'post',
//             dataType: 'json',
//             url: CTX + "/user/findListMapDictionariesByItype",
//             data: {type: 3},
//             success: function (result) {
//                 console.log(result);
//                 me.quaList = result.result;
//             },
//             error: function () {
//
//             }
//         });
//     }
//
// }

function preview(data) {
    var data = {
        url: CTX + "/common/preview",
        title: "新增",
        width: document.documentElement.clientWidth,
        height: document.documentElement.clientHeight,
        modal: false,
        onload: function (window) {
        	window.setData({
                arr: [{
                    sid: 'img1',
                    sname: 'img1',
                    spath: '/nodedev/search/user/2018/04/17/20180417135421745_彩色几何登录界面.jpg',
                }, {
                    sid: 'img1',
                    sname: 'nodedev_img1',
                    spath: '/nodedev/search/user/2018/04/17/20180417135422513_6.jpg',
                },{
                    sid: 'img1',
                    sname: 'img1',
                    spath: '/nodedev/search/user/2018/04/17/20180417135421745_彩色几何登录界面.jpg',
                }, {
                    sid: 'img1',
                    sname: 'nodedev_img1',
                    spath: '/nodedev/search/user/2018/04/17/20180417135422513_6.jpg',
                }],
                index: 0
            });
        },
        ondestroy: function () {
        }
    }
    top.search.popDialog(data);
}

Upload2.init({
    callback: function (resultArr, delArr) {
        console.log("Upload2上传结果", resultArr, delArr);
    },
    url: "/user/findListMapDictionariesByItype",
    data: {type: 3}
    ,
    initData: [{
        sid: "sid1",
        sdictionarieid: "d83998cd-b98e-4e39-9469-22d81719f0e9",
        sdesc: "a1",
        sname: "name1",
        spath: "/nodedev/he/merger/2018/04/23/20180423163440568_f1.jpg"
    }, {
        sid: "sid2",
        sdictionarieid: "eb0654fa-53c1-4d8a-8eef-a1a69fd12718",
        sdesc: "b1",
        sname: "name2",
        spath: "/nodedev/he/merger/2018/04/23/20180423163754361_f2.jpg"
    }],
    wbOpts: {
        relativePath: '/he/mini'
    }
});
setTimeout(function () {
    console.log("2秒后执行下");
    Upload2.setData([{
        sid: "sid2",
        sdictionarieid: "eb0654fa-53c1-4d8a-8eef-a1a69fd12718",
        sdesc: "bx",
        sname: "name2",
        spath: "/nodedev/he/merger/2018/04/23/20180423163916879_f3.jpg"
    }]);
},2000);


// search.parse();
// var sdictionarieid = search.get("qua1");
// sdictionarieid.url = CTX + "/user/findListMapDictionariesByItype";
// sdictionarieid.load({type: 3});
//
// var uploader = WebUploader.create({
//     swf: LIB + '/webuploader/Uploader.swf',
//     // 文件接收服务端。
//     server: CTX + '/file/upload/upload',
//     pick: {
//         id: '#picker1',
//         multiple: false
//     },
//     resize: true,
//     auto: false,
//     threads: 1,
// }), $imgtd = $('#img1');
// uploader.on('fileQueued', function (file) {
//     uploader.makeThumb(file, function (error, src) {
//         var img = $('<img src="' + src + '">');
//         $imgtd.empty().append(img);
//     }, 100, 100);
// });
//
// var line = 0;
// function addqua() {
//     line++;
//     console.log(line);
//     $('#qua').append('<tr> <td colspan="2"> <div id="qua' + line + '" class="search-select" rules="required" multiSelect="false" idField="id" textField="text" width="400" height="40"></div> </td> </tr> <tr> <td> <div id="sdesc' + line + '" class="search-textarea" width="300"></div> </td> <td id="img' + line + '">  <div id="picker' + line + '">选择文件</div> </td> </tr>');
//
// }


$(function () {

    $('#btn1').on('click', function () {
        console.log(upload1.delArr);//获取删除附件的id
        // search.manualShowLoading();
        upload1.upload();
    });
    $('#btn2').on('click', function () {
        console.log(upload2.delArr);//获取删除附件的id
        upload2.upload();
    });
    $('#btn3').on('click', function () {
    });

    function xx1() {
        $('#qua').append('' +
            '<div id="uploader" class="cl-uploader" style="width: 400px;height: 200px;float: left"> ' +
            '<div class="queueList"> ' +
            '<div id="dndArea" class="placeholder" style="min-height: 120px;"> ' +
            '<div id="filePicker" class="cl-picker"></div> ' +
            '<p>或将照片拖到这里，单次最多可选300张</p> ' +
            '</div> ' +
            '</div> ' +
            '<div class="statusBar" style="display:none;"> ' +
            '<div class="progress"> ' +
            '<span class="text">0%</span> ' +
            '<span class="percentage"></span> ' +
            '</div>' +
            '<div class="info"></div> ' +
            '<div class="btns"> ' +
            '<div id="filePicker2" class="cl-picker2"></div> ' +
            '</div>' +
            '</div> ' +
            '</div>')
    }

    function xx2() {

    }


    var upload1 = Upload.upload({
        relativePath: '/he/merger',
        uploadFinished: function (datas) {
            console.log(datas);
            // upload2.upload();
        },
        accept: {}

        // ,
        // containerId: 'uploader',
        // onePickerId: 'filePicker',
        // twoPickerId: 'filePicker2'


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
        containerId: 'uploaderx',
        onePickerId: 'filePickerx',
        twoPickerId: 'filePicker2x'
        //    ,
        //    initFiles: {
        //        rootPath: "/upload",
        //        files: [
        //            {
        //                sid: "xxx3",
        //               sname: "wocao",
        //               spath: "/search/a/f3.JPG"
        //            },
        //            {
        //                sid: "xxx4",
        //                sname: "wocao",
        //                spath: "/search/a/f4.JPG"
        //           }
        //      ]
        // }

    });

});