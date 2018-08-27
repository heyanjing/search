/**
 * Created by heyanjing on 2018/1/8 16:52.
 */
(function ($, win) {
    win['pendingRequests'] = win.pendingRequests || {};
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
        // console.log(options);
        if (options.abortOnRetry) {
            var key = options.url + options.data;
            if (!win.pendingRequests[key]) {
                win.pendingRequests[key] = jqXHR;
            } else {
                // console.error("放弃");
                jqXHR.abort();    //放弃后触发的提交
                // pendingRequests[key].abort();   // 放弃先触发的提交
            }

            var complete = options.complete;
            options.complete = function (jqXHR, textStatus) {
                win.pendingRequests[key] = null;
                if ($.isFunction(complete)) {
                    complete.apply(this, arguments);
                }
            };
        }

    });
    $.ajaxSetup({
        abortOnRetry: true,
        cache: false,
        dataType: 'json',
        type: 'post',
        timeout: 5000,
        error: function (xhr, status, e) {
            // console.log(CTX);
            // console.log(xhr);
            // console.log(status);
            // console.log(e);
            if (xhr.status === 403) {
                location.href = CTX + "/";
            } else if (xhr.status === 500) {
                alert("服务器繁忙,请稍后再试");
            }

        }
    });
})(jQuery, window);


var Globle = {
    fun: {
        applyIf: function () {
            var me = this, args = arguments, o = args[0] || {}, r = 1, i = 0;
            if (typeof o == 'boolean' || typeof o == 'number') {
                r = o;
                i = 1;
                o = args[1];
            }
            for (; i < args.length; i++) {
                var c = args[i];
                for (var p in c) {
                    if (typeof o[p] == "undefined") {
                        o[p] = c[p];
                    } else if (typeof o[p] == "object" && r) {
                        if (typeof r == 'number') {
                            r--;
                        }
                        me.applyIf(r, o[p], c[p]);
                    }
                }
            }
            return o;
        },
        keyValueStr: function (key, valueOrFunction) {
            var value = jQuery.isFunction(valueOrFunction) ? valueOrFunction() : valueOrFunction;
            return key + "=" + value;
        },
        buildParams: function (prefix, obj, result) {
            var me = this;
            if (Array.isArray(obj)) {
                $.each(obj, function (i, v) {
                    me.buildParams(prefix + "[" + (typeof v === "object" && v != null ? i : "") + "]", v, result);
                });
            } else if (jQuery.type(obj) === "object") {

                for (name in obj) {
                    me.buildParams(prefix + "[" + name + "]", obj[name], result);
                }
            } else {
                var temp = me.keyValueStr(prefix, obj);
                result.push(temp.replace(/\[([a-zA-Z]+)]/gi, '.$1'));
            }
        },
        format: function (millisecond, pattern) {
            pattern = pattern || 'YYYY-MM-DD HH:mm:ss';
            if (moment(millisecond).isValid()) {
                return moment(millisecond).format(pattern);
            }
            return "";
        },
        formatDate: function (millisecond) {
            return this.format(millisecond, 'YYYY-MM-DD');
        },
        warn: function (content) {
            search.warn({content: content});
        },
        confirm: function (content, okFun) {
            var obj = {
                content: content
            }
            if (okFun) {
                obj['funl'] = okFun;
            }
            search.confirm(obj);
        },
        infoAndClose: function (content) {
            search.info({
                content: content,
                funl: function () {
                    $("#closeWindow").click();
                }
            });
        },
        info: function (content) {
            search.info({
                content: content
            });
        },
        preview: function (data) {
            var page = {
                url: CTX + "/common/preview",
                width: window.parent.document.body.clientWidth,
                height: window.parent.document.body.clientHeight,
                modal: false,
                onload: function (window) {
                    window.setData(data);
                },
                ondestroy: function () {
                }
            }
            top.search.popDialog(page);
        },
        generaTetailBut: function (buttArr, dataid) { //生成尾部按钮。
            var text = '';
            for (var i = 0; i < buttArr.length; i++) {
                var func = buttArr[i];
                text += '<a class="' + func.sicon + '" href="javascript:void(0);" onclick="' + func.spcmethod + '(\'' + dataid + '\');">' + func.sname + '</a> ';
            }
            return text;
        }
    },
    obj: {
        Map: function () {
            this.elements = [];
            this.size = function () {
                return this.elements.length;
            }
            this.isEmpty = function () {
                return (this.elements.length < 1);
            }
            this.clear = function () {
                this.elements = [];
            }
            this.element = function (_index) {
                if (_index < 0 || _index >= this.elements.length) {
                    return null;
                }
                return this.elements[_index];
            }
            this.containsValue = function (_value) {
                var bln = false;
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i].value === _value) {
                        bln = true;
                        break;
                    }
                }
                return bln;
            }
            this.containsKey = function (_key) {
                var bln = false;
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i].key === _key) {
                        bln = true;
                        break;
                    }
                }
                return bln;
            }
            this.put = function (_key, _value) {
                if (this.containsKey(_key)) {
                    if (this.containsValue(_value)) {
                        if (this.remove(_key)) {
                            this.elements.push({
                                key: _key,
                                value: _value
                            });
                        }
                    } else {
                        this.elements.push({
                            key: _key,
                            value: _value
                        });
                    }
                } else {
                    this.elements.push({
                        key: _key,
                        value: _value
                    });
                }
                return this;
            }
            this.remove = function (_key) {
                var bln = false;
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i].key === _key) {
                        this.elements.splice(i, 1);
                        bln = true;
                        break;
                    }
                }
                return bln;
            }
            this.get = function (_key) {
                var val = "";
                for (var i = 0; i < this.elements.length; i++) {
                    if (this.elements[i].key === _key) {
                        val = this.elements[i].value;
                    }
                }
                return val;
            }
            this.keys = function () {
                var arr = [];
                for (var i = 0; i < this.elements.length; i++) {
                    arr.push(this.elements[i].key);
                }
                return arr;
            }
            this.values = function () {
                var arr = [];
                for (var i = 0; i < this.elements.length; i++) {
                    arr.push(this.elements[i].value);
                }
                return arr;
            }
        }
    },
    constant: {
        upload: {
            /**
             * 机构
             */
            org: '/search/org',
            /**
             *用户
             */
            user: '/search/user',
            /**
             *功能
             */
            fcn: '/search/fcn',
            /**
             * 系统
             */
            system: '/search/system',
            /**
             * 项目库立项
             */
            approval: '/search/projectlib/approval',
            /**
             * 项目库可研
             */
            feasibilitys: '/search/projectlib/feasibilitys',
            /**
             * 项目库概算
             */
            calculations: '/search/projectlib/calculations',
            /**
             * 项目库预算
             */
            budgets: '/search/projectlib/budgets',
            /**
             * 申请
             */
            applys: '/search/applys'
        },
        recycle: "&recycle=recycle",
        //1 是 ， 2 否
        YesOrNo: [
            {text: '是', value: '1'},
            {text: '否', value: '2'}
        ],
        SupportWay: [
            {text: '请选择', value: '-1'},
            {text: '是', value: '1'},
            {text: '否', value: '2'}
        ],
        //审计局机构类型
        AuditOrgType: [
            {text: '审计局', value: '101'}
        ],
        //机构类型
        OrgType: [
            {text: '审计局', value: '101'},
            {text: '中介机构', value: '102'},
            {text: '建设业主', value: '104'},
            {text: 'BT单位', value: '105'},
            {text: '设计单位', value: '106'},
            {text: '勘察单位', value: '107'},
            {text: '监理单位', value: '108'},
            {text: '施工单位', value: '109'},
            {text: '招标代理', value: '114'}
        ],
        //注册机构类型
        RegisterOrgType: [
            {text: '中介机构', value: '102'},
            {text: '建设业主', value: '104'},
            {text: 'BT单位', value: '105'},
            {text: '设计单位', value: '106'},
            {text: '勘察单位', value: '107'},
            {text: '监理单位', value: '108'},
            {text: '施工单位', value: '109'},
            {text: '招标代理', value: '114'}
        ],
        //特殊机构类型
        SpecialOrgType: [
            {text: '发改委', value: '111'},
            {text: '财政局', value: '112'},
            {text: '政府机关', value: '113'}
        ],
        //参建机构类型
        BuildOrgType: [
            {text: '建设业主', value: '104'},
            {text: 'BT单位', value: '105'},
            {text: '设计单位', value: '106'},
            {text: '勘察单位', value: '107'},
            {text: '监理单位', value: '108'},
            {text: '施工单位', value: '109'},
            {text: '招标代理', value: '114'}
        ],
        //中介机构类型
        AgencyOrgType: [
            {text: '中介机构', value: '102'}
        ],
        //-2不受限制 ， -1受全局限制
        UserNumber: [
            {text: '不受限制', value: '-2'},
            {text: '受全局限制', value: '-1'}
        ],
        funcType: [
            {text: '分类', value: '1'},
            {text: '模块', value: '2'},
            {text: '节点', value: '3'},
            {text: '标签', value: '4'},
            {text: '按钮', value: '5'}
        ],
        funcTypeQue: [
            {text: '全部', value: '-1'},
            {text: '分类', value: '1'},
            {text: '模块', value: '2'},
            {text: '节点', value: '3'},
            {text: '标签', value: '4'},
            {text: '按钮', value: '5'}
        ],
        sbtnlocationArr: [
            {text: '顶部', value: '101'},
            {text: '尾部', value: '102'},
            {text: '右键菜单', value: '103'}
        ],
        funcState: [
            {text: '启用', value: '1'},
            {text: '禁用', value: '2'}
        ],
        FuncYesOrNo: [
            {text: '全部', value: '-1'},
            {text: '是', value: '1'},
            {text: '否', value: '2'}
        ],
        permissionLevel: [
            {id: '1', text: '全系统所有项目'},
            {id: '2', text: '分管机构所有项目'},
            {id: '4', text: '有授权所有项目'}
        ],
        state: [
            {id: '1', text: '启用'},
            {id: '2', text: '禁用'},
            {id: '3', text: '设计'},
            {id: '97', text: '驳回'},
            {id: '98', text: '申请'},
            {id: '99', text: '删除'},
            {id: '100', text: '其他'},
        ],
        sex: [
            {id: '1', text: '男'},
            {id: '2', text: '女'}
        ],
        userType: [
            {id: '2', text: '管理员'},
            {id: '4', text: '普通用户'}
        ],
        userTypes: [
            {id: '1', text: 'admin'},
            {id: '2', text: '管理员'},
            {id: '4', text: '普通用户'}
        ],
        ApprovalApproved: [
            {value: '-1', text: '-请选择-'},
            {value: '1', text: '公开招标'},
            {value: '2', text: '邀请招标'},
            {value: '3', text: '直接发包'}
        ],
        iBiddingType: [
            {value: '-1', text: '-请选择-'},
            {value: '1', text: '勘察'},
            {value: '2', text: '设计'},
            {value: '3', text: '监理'},
            {value: '3', text: '施工'}
        ],
        isupportback: [
            {value: 1, text: "是"},
            {value: 2, text: "否"}
        ],
        stepType: [
            {value: 101, text: "开始"},
            {value: 102, text: "普通"},
            {value: 103, text: "会签"},
            {value: 104, text: "结束"}
        ],
        AuditTplsType: [
        	{id: 101, text: '结算'},
        	{id: 102, text: '决算'},
        	{id: 103, text: '跟踪审计'}
        ],
        AuditTplDetailsType: [
        	{id: 101, text: '资料分类'},
        	{id: 102, text: '资料项'}
        ],
        TplsType: [
            {value: 101, text: "结算"},
            {value: 102, text: "决算"},
            {value: 103, text: "跟踪审计"}
        ],
        TplDetailType: [
           {value: 101, text: "资料分类"},
           {value: 102, text: "资料项"}
        ],
        IJoinProcess: [
           {value: 2, text: "否"},
           {value: 1, text: "内部流转"},
           {value: 3, text: "下级对上级（审计局）流转"},
           {value: 4, text: "业主对审计局"},
           {value: 5, text: "中介机构对审计局"}
        ]
    },
    reg: {
        phone: /^1(3[0-9]|4[579]|5[0-35-9]|7[1-35-8]|8[0-9]|70)\d{8}$/,
        tel: /^(0[0-9]{2,3})?\d{7,8}$/,
        email: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
    }
}

var Upload = {
    defaultOptions: {
        name: 'custom',
        containerId: 'uploader',
        onePickerId: 'filePicker',
        twoPickerId: 'filePicker2',
        checkChunkUrl: CTX + '/file/upload/checkChunk',//检查分块的url
        fileLimtFun: function (stateCode) {//文件受限的回调
            if (stateCode === 201) {
                alert("文件格式不正确");
            } else if (stateCode === 202) {
                alert("文件大小超出限制");
            } else if (stateCode === 500) {
                alert("服务器繁忙");
            } else {
                alert("文件受限");
            }
        },
        relativePath: '/search/merger',
        uploadUrl: CTX + '/file/upload/upload',//文件上传的url
        mergeChunkUrl: CTX + '/file/upload/mergeChunk',//分块合并的url
        uploadFinished: function (datas) {//所有文件上传完成的回调
            console.error(datas);
        },
        initFiles: {
            rootPath: '/upload',
            files: []
        },
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    },
    upload: function (opts) {
        var me = this, map = new Globle.obj.Map(), resultArr = [], name = opts.name || me.defaultOptions.name;
        console.error("WebUploader主键", WebUploader);
        if (!me.isReg) {
            //监听分块上传过程中的三个时间点
            WebUploader.Uploader.register({
                name: name,
                "before-send-file": "beforeSendFile",
                "before-send": "beforeSend",
                "after-send-file": "afterSendFile"
            }, {
                //时间点1：所有分块进行上传之前调用此函数
                beforeSendFile: function (file) {
                    console.log("当前对象", this);
                    console.log("beforeSendFile---读取文件信息");
                    console.log(file);
                    var deferred = WebUploader.Deferred();
                    //1、使用md5计算文件的唯一标记，用于断点续传
                    (new WebUploader.Uploader()).md5File(file)//(file,0,10*1024*1024)
                        .progress(function (percentage) {
                            $('#item1').find("p.state").text("正在读取文件信息...");
                        })
                        .then(function (val) {
                            console.log('检查md', val);
                            $.ajax({
                                async: false,  // 同步
                                url: CTX + "/file/upload/checkMd5Cache",
                                data: {
                                    fileMd5: val
                                },
                                success: function (result) {
                                    if (result && result.result) {
                                        uploader.nodeMap.put(val, result.result);
                                    } else {
                                        uploader.nodeMap.put(val, undefined);
                                    }
                                    console.log('checkMd5Cache后服务名', uploader.nodeMap.get(val));
                                },
                                error: function () {

                                }
                            });
                            map.put(file.id, val);
                            // formData.fileMd5 = val;
                            $('#item1').find("p.state").text("成功获取文件信息...");
                            //获取文件信息后进入下一步
                            deferred.resolve();
                        });
                    //调用deferred.resolve();无效
                    return deferred.promise();
                },
                //时间点2：如果有分块上传，则每个分块上传之前调用此函数
                beforeSend: function (block) {
                    console.log("beforeSend----检查文件分块是否存在，是否完整", block);
                    var fileMd5 = map.get(block.file.id);
                    // console.error("1次md5打印" + fileMd5);
                    var formData = this.owner.options.formData;
                    var deferred = WebUploader.Deferred();
                    var oldUrl = opts.checkChunkUrl || me.defaultOptions.checkChunkUrl;
                    var newUrl = oldUrl;
                    if (uploader.nodeMap.get(fileMd5) && oldUrl === newUrl) {
                        newUrl = CTX + "/" + uploader.nodeMap.get(fileMd5) + oldUrl.replace(CTX, "");
                    }

                    $.ajax({
                        abortOnRetry: false,
                        type: "POST",
                        url: newUrl,// CTX + '/file/upload/checkChunk', // HEINFO:2018/3/22 15:17 检查分块url
                        data: {
                            //文件唯一标记
                            fileMd5: fileMd5,
                            // fileMd5: formData.fileMd5,
                            //当前分块下标
                            chunk: block.chunk,
                            //当前分块大小
                            chunkSize: block.end - block.start,
                            //总大小
                            totalSize: block.total,
                            //扩展名
                            ext: block.file.ext
                        },
                        // async: false,  // 同步
                        dataType: "json",
                        success: function (result) {
                            console.log("检查结果", result);
                            //分块存在
                            if (result.result) {
                                uploader.nodeMap.put(fileMd5, result.result);
                                var oldUploadUrl = opts.uploadUrl || me.defaultOptions.uploadUrl;
                                uploader.options.server = CTX + "/" + uploader.nodeMap.get(fileMd5) + oldUploadUrl.replace(CTX, "");
                            }
                            console.log('checkChunk后服务名', uploader.nodeMap.get(fileMd5));
                            if (result.status) {
                                console.log("跳过第" + block.chunk + "分块*********************************************");
                                //分块存在，跳过
                                deferred.reject();
                            } else {
                                if (result.code === 201 || result.code === 202) {
                                    console.error("文件格式不正确或大小超出限制");
                                    uploader.stop(true);
                                    // HEINFO:2018/3/22 15:17 文件受限回调
                                    var fun = opts.fileLimtFun || me.defaultOptions.fileLimtFun;
                                    fun.call(me, result.code);
                                } else {
                                    formData.fileMd5 = map.get(block.file.id);
                                    // console.error("2次md5打印" + map.get(block.file.id));
                                    console.log("上传第" + block.chunk + "分块*********************************************");
                                    //分块不存在或不完整，重新发送该分块内容
                                    deferred.resolve();
                                }
                            }
                        },
                        //任何形式的验证失败，都触发重新上传
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("检查出错");
                            uploader.stop(true);
                            // HEINFO:2018/3/22 15:17 文件受限回调
                            // opts.fileLimtFun(500);
                            var fun = opts.fileLimtFun || me.defaultOptions.fileLimtFun;
                            fun.call(me, 500);
                            deferred.resolve();
                        }
                    });
                    return deferred.promise();
                },
                //时间点3：所有分块上传成功后调用此函数
                afterSendFile: function (file) {
                    var currentUploader = this.owner;
                    var fileMd5 = map.get(file.id);
                    console.log("afterSendFile---发送合并请求", file);
                    var formData = this.owner.options.formData;
                    var oldUrl = opts.mergeChunkUrl || me.defaultOptions.mergeChunkUrl;
                    var newUrl = oldUrl;
                    if (uploader.nodeMap.get(fileMd5)) {
                        newUrl = CTX + "/" + uploader.nodeMap.get(fileMd5) + oldUrl.replace(CTX, "");
                    }
                    // 如果分块上传成功，则通知后台合并分块
                    $.ajax({
                        async: false,  // 同步
                        type: "POST",
                        url: newUrl,//CTX + '/file/upload/mergeChunk', // HEINFO:2018/3/22 15:17 合并分块的url
                        data: {
                            fileMd5: fileMd5,
                            // fileMd5: formData.fileMd5,
                            fileName: file.name,
                            relativePath: currentUploader.relativePath
                        },
                        success: function (result) {
                            if (result.status) {
                                currentUploader.resultArr.push(result.result);
                            }
                            console.log('mergeChunk后服务名', uploader.serverName);
                            console.warn("合并结果", result, --fileCount, currentUploader.resultArr);
                        }
                    });
                }
            });
            me.isReg = true;
        }


        // var $wrap = $('#uploader'),
        var cId = opts.containerId || me.defaultOptions.containerId;
        var $wrap = $('#' + cId),
            // 图片容器
            $queue = $('<ul class="filelist"></ul>').appendTo($wrap.find('.queueList')),
            // 状态栏，包括进度和控制按钮
            $statusBar = $wrap.find('.statusBar'),
            // 文件总体选择信息。
            $info = $statusBar.find('.info'),
            // 上传按钮
            $upload = $wrap.find('.uploadBtn'),
            // 没选择文件之前的内容。
            $placeHolder = $wrap.find('.placeholder'),
            // 总体进度条
            $progress = $statusBar.find('.progress').hide(),
            // 添加的文件数量
            fileCount = 0,
            // 成功上传的文件数量
            uploadfileCount = 0,
            // 添加的文件总大小
            fileSize = 0,
            // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,
            // 缩略图大小
            thumbnailWidth = 110 * ratio,
            thumbnailHeight = 110 * ratio,
            // 可能有pedding, ready, uploading, confirm, done.
            state = 'pedding',
            // 所有文件的进度信息，key为file id
            percentages = {},
            supportTransition = (function () {
                var s = document.createElement('p').style,
                    r = 'transition' in s ||
                        'WebkitTransition' in s ||
                        'MozTransition' in s ||
                        'msTransition' in s ||
                        'OTransition' in s;
                s = null;
                return r;
            })(),

            // WebUploader实例
            uploader;

        if (!WebUploader.Uploader.support()) {
            alert('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
            throw new Error('WebUploader does not support the browser you are using.');
        }

        // 实例化
        uploader = WebUploader.create({
            // dnd: '#uploader .queueList',
            dnd: '#' + (opts.containerId || me.defaultOptions.containerId) + ' .queueList',
            disableGlobalDnd: true,
            paste: document.body,
            pick: {
                // id: '#filePicker',
                id: '#' + (opts.onePickerId || me.defaultOptions.onePickerId),
                innerHTML: '点击选择文件',
                multiple: true
            },
            accept: opts.accept || me.defaultOptions.accept,
            //文件的限制类型
            // accept: {
            //     title: 'Images',
            //     extensions: 'gif,jpg,jpeg,bmp,png',
            //     mimeTypes: 'image/*'
            // },
            //缩略图
            // thumb:{
            //     width: 110,
            //     height: 110,
            //     // 图片质量，只有type为`image/jpeg`的时候才有效。
            //     quality: 70,
            //     // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
            //     allowMagnify: true,
            //     // 是否允许裁剪。
            //     crop: true,
            //     // 为空的话则保留原有图片格式。
            //     // 否则强制转换成指定的类型。
            //     type: 'image/jpeg'
            // },
            // 配置压缩的图片的选项。如果此选项为false, 则图片在上传前不进行压缩
            // compress:{
            //     width: 1600,
            //     height: 1600,
            //     // 图片质量，只有type为`image/jpeg`的时候才有效。
            //     quality: 90,
            //     // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
            //     allowMagnify: false,
            //     // 是否允许裁剪。
            //     crop: false,
            //     // 是否保留头部meta信息。
            //     preserveHeaders: true,
            //     // 如果发现压缩后文件大小比原来还大，则使用原来图片
            //     // 此属性可能会影响图片自动纠正功能
            //     noCompressIfLarger: false,
            //     // 单位字节，如果图片大小小于此值，不会采用压缩。
            //     compressSize: 0
            // },
            // [默认值：false] 设置为 true 后，不需要手动调用上传，有文件选择即开始上传。
            runtimeOrder: 'html5,flash',
            auto: false,
            prepareNextFile: true,
            chunked: true,
            chunkSize: 10485760,//10M
            chunkRetry: 2,
            threads: 1,
            //去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
            duplicate: true,
            swf: LIB + '/webuploader/Uploader.swf',
            server: opts.uploadUrl || me.defaultOptions.uploadUrl//CTX + '/file/upload/upload',

        });
        uploader['nodeMap'] = new Globle.obj.Map();
        uploader['resultArr'] = [];
        uploader['relativePath'] = opts.relativePath || me.defaultOptions.relativePath;
        // 添加“添加文件”的按钮，
        uploader.addButton({
            // id: '#filePicker2',
            id: '#' + (opts.twoPickerId || me.defaultOptions.twoPickerId),
            label: '继续添加'
        });
        uploader['delArr'] = [];
        // HEINFO:2018/3/22 16:33 初始化文件渲染回调
        // var fileArr = [{id: "idxxx", name: "文件名", src: "/upload/search/a/f1.JPG"}];
        var initFiles = opts.initFiles || me.defaultOptions.initFiles;
        if (initFiles.files.length > 0) {
            $placeHolder.addClass('element-invisible');//隐藏原始拖拽div
            $statusBar.show();//显示状态div
            var rootPath = initFiles.rootPath;

            $.each(initFiles.files, function (i, v) {
                addViewFile(rootPath, v);
            })

        }
        uploader.setData = function (arr) {
            $queue.empty();
            $placeHolder.addClass('element-invisible');//隐藏原始拖拽div
            $statusBar.show();//显示状态div
            $.each(arr, function (i, v) {
                addViewFile(rootPath, v);
            })
        }

        function addViewFile(root, file) {
            var $li = $('<li id="' + file.sid + '">' +
                '<p class="title">' + file.sname + '</p>' +
                '<p class="imgWrap"></p>' +
                '<p class="progress"><span></span></p>' +
                '</li>'),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    // '<span class="rotateRight">向右旋转</span>' +
                    // '<span class="rotateLeft">向左旋转</span>' +
                    '</div>').appendTo($li),
                $wrap = $li.find('p.imgWrap');
            console.log("图片根路径", root);
            img = $('<img src="' + root + file.spath + '" style="width: 110px;height: 110px;">'),
                $wrap.empty().append(img);

            // uploader.makeThumb(file, function (error, src) {
            //     if (error) {
            //         $wrap.text('不能预览');
            //         return;
            //     }
            //
            //     var  img = $('<img src="' + root + file.spath + '">');
            //     $wrap.empty().append(img);
            // }, thumbnailWidth, thumbnailHeight);

            $li.on('mouseenter', function () {
                $btns.stop().animate({height: 30});
            });

            $li.on('mouseleave', function () {
                $btns.stop().animate({height: 0});
            });

            $btns.on('click', 'span', function () {
                var index = $(this).index(), deg;
                switch (index) {
                    case 0:
                        uploader.delArr.push($li.attr('id'));
                        $li.remove();
                        return;

                    case 1:
                        file.rotation += 90;//
                        break;

                    case 2:
                        file.rotation -= 90;
                        break;
                }

                if (supportTransition) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
                }


            });
            $li.appendTo($queue);
            $placeHolder.addClass('element-invisible');
            // $('#filePicker2').removeClass('element-invisible');
            $('#' + (opts.twoPickerId || me.defaultOptions.twoPickerId)).removeClass('element-invisible');
            $queue.parent().addClass('filled');
            $queue.show();
            $statusBar.removeClass('element-invisible');
        }

        // 当有文件添加进来时执行，负责view的创建
        function addFile(file) {
            var $li = $('<li id="' + file.id + '">' +
                '<p class="title">' + file.name + '</p>' +
                '<p class="imgWrap"></p>' +
                '<p class="progress"><span></span></p>' +
                '</li>'),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    // '<span class="rotateRight">向右旋转</span>' +
                    // '<span class="rotateLeft">向左旋转</span>' +
                    '</div>').appendTo($li),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find('p.imgWrap'),
                $info = $('<p class="error"></p>'),

                showError = function (code) {
                    switch (code) {
                        case 'exceed_size':
                            text = '文件大小超出';
                            break;

                        case 'interrupt':
                            text = '上传暂停';
                            break;
                        case 'limt':
                            text = '文件受限';
                            break;

                        default:
                            text = '上传失败，请重试';
                            break;
                    }

                    $info.text(text).appendTo($li);
                };

            if (file.getStatus() === 'invalid') {
                showError(file.statusText);
            } else {
                // @todo lazyload
                $wrap.text('预览中');
                uploader.makeThumb(file, function (error, src) {
                    if (error) {
                        $wrap.text('不能预览');
                        return;
                    }

                    var img = $('<img src="' + src + '">');
                    $wrap.empty().append(img);
                }, thumbnailWidth, thumbnailHeight);

                percentages[file.id] = [file.size, 0];
                file.rotation = 0;
            }

            file.on('statuschange', function (cur, prev) {
                if (prev === 'progress') {
                    $prgress.hide().width(0);
                } else if (prev === 'queued') {
                    $li.off('mouseenter mouseleave');
                    $btns.remove();
                }

                if (cur === 'interrupt' && prev === 'progress') {
                    showError('limt');
                } else if (cur === 'error' || cur === 'invalid') {
                    showError(file.statusText);
                    percentages[file.id][1] = 1;
                } else if (cur === 'interrupt') {
                    showError('interrupt');
                } else if (cur === 'queued') {
                    percentages[file.id][1] = 0;
                } else if (cur === 'progress') {
                    $info.remove();
                    $prgress.css('display', 'block');
                } else if (cur === 'complete') {
                    $li.append('<span class="success"></span>');
                }

                $li.removeClass('state-' + prev).addClass('state-' + cur);
            });

            $li.on('mouseenter', function () {
                $btns.stop().animate({height: 30});
            });

            $li.on('mouseleave', function () {
                $btns.stop().animate({height: 0});
            });

            $btns.on('click', 'span', function () {
                var index = $(this).index(), deg;
                switch (index) {
                    case 0:
                        uploader.removeFile(file);//移除文件
                        return;

                    case 1:
                        file.rotation += 90;//
                        break;

                    case 2:
                        file.rotation -= 90;
                        break;
                }

                if (supportTransition) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
                    // use jquery animate to rotation
                    // $({
                    //     rotation: rotation
                    // }).animate({
                    //     rotation: file.rotation
                    // }, {
                    //     easing: 'linear',
                    //     step: function( now ) {
                    //         now = now * Math.PI / 180;

                    //         var cos = Math.cos( now ),
                    //             sin = Math.sin( now );

                    //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                    //     }
                    // });
                }


            });

            $li.appendTo($queue);
        }

        // 负责view的销毁
        function removeFile(file) {
            var $li = $('#' + file.id);

            delete percentages[file.id];
            updateTotalProgress();
            $li.off().find('.file-panel').off().end().remove();
        }

        function updateTotalProgress() {
            var loaded = 0,
                total = 0,
                spans = $progress.children(),
                percent;

            $.each(percentages, function (k, v) {
                total += v[0];
                loaded += v[0] * v[1];
            });

            percent = total ? loaded / total : 0;

            spans.eq(0).text(Math.round(percent * 100) + '%');
            spans.eq(1).css('width', Math.round(percent * 100) + '%');
            updateStatus();
        }

        function updateStatus() {
            var text = '', stats;

            if (state === 'ready') {
                text = '选中' + fileCount + '张图片，共' +
                    WebUploader.formatSize(fileSize) + '。';
            } else if (state === 'confirm') {
                stats = uploader.getStats();
                if (stats.uploadFailNum) {
                    text = '已成功上传' + stats.successNum + '张照片至XX相册，' +
                        stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片<!--或<a class="ignore" href="#">忽略</a>-->'
                }

            } else {
                stats = uploader.getStats();
                text = '共' + fileCount + '张（' +
                    WebUploader.formatSize(fileSize) +
                    '），已上传' + stats.successNum + '张';

                if (stats.uploadFailNum) {
                    text += '，失败' + stats.uploadFailNum + '张';
                }
            }

            $info.html(text);
        }

        function setState(val) {
            var stats;

            if (val === state) {
                return;
            }

            // $upload.removeClass('state-' + state);
            // $upload.addClass('state-' + val);
            state = val;

            switch (state) {
                case 'pedding':
                    $placeHolder.removeClass('element-invisible');
                    $queue.parent().removeClass('filled');
                    $queue.hide();
                    $statusBar.addClass('element-invisible');
                    uploader.refresh();
                    break;

                case 'ready':
                    $placeHolder.addClass('element-invisible');
                    // $('#filePicker2').removeClass('element-invisible');
                    $('#' + (opts.twoPickerId || me.defaultOptions.twoPickerId)).removeClass('element-invisible');
                    $queue.parent().addClass('filled');
                    $queue.show();
                    $statusBar.removeClass('element-invisible');
                    uploader.refresh();
                    break;

                case 'uploading':
                    // $('#filePicker2').addClass('element-invisible');
                    $('#' + (opts.twoPickerId || me.defaultOptions.twoPickerId)).addClass('element-invisible');
                    $progress.show();
                    // $upload.text('暂停上传');
                    break;

                case 'paused':
                    $progress.show();
                    // $upload.text('继续上传');
                    break;

                case 'confirm':
                    $progress.hide();
                    // $upload.text('开始上传').addClass('disabled');

                    stats = uploader.getStats();
                    if (stats.successNum && !stats.uploadFailNum) {
                        setState('finish');
                        return;
                    }
                    break;
                case 'finish':
                    stats = uploader.getStats();
                    if (stats.successNum) {
                        // alert( '上传成功' );
                    } else {
                        // 没有成功的图片，重设
                        state = 'done';
                        location.reload();
                    }
                    break;
            }

            updateStatus();
        }

//上传过程中触发，携带上传进度。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            $percent.css('width', percentage * 100 + '%');
            percentages[file.id][1] = percentage;
            updateTotalProgress();
        });
//当文件被加入队列以后触发
        uploader.on('fileQueued', function (file) {
            fileCount++;
            fileSize += file.size;
            console.log("文件个数", fileCount);
            if (fileCount === 1) {
                $placeHolder.addClass('element-invisible');//隐藏原始拖拽div
                $statusBar.show();//显示状态div
            }

            addFile(file);
            setState('ready');
            updateTotalProgress();
        });
//当文件被移除队列后触发
        uploader.on('fileDequeued', function (file) {
            fileCount--;
            fileSize -= file.size;

            if (!fileCount) {
                setState('pedding');
            }

            removeFile(file);
            updateTotalProgress();
        });
//当validate不通过时
        uploader.on('error', function (code) {
            if (code === "Q_EXCEED_NUM_LIMIT ") {
                alert("文件数量超出限制");
            } else if (code === "Q_EXCEED_SIZE_LIMIT") {
                alert("文件总大小超出限制");
            } else if (code === "Q_TYPE_DENIED") {
                alert("文件类型不匹配");
            } else {
                alert(code);
            }

        });
        uploader.on('uploadComplete', function (file) {
            // console.error(file);
        });

        uploader.on('uploadError', function (file, reason) {
            console.warn("文件上传出错", file, reason);
        });
        uploader.on('uploadSuccess', function (file, response) {
            uploadfileCount++;
            console.warn("文件单次完成", file, response);
        });
        uploader.on('uploadFinished', function () {

            console.error("所有文件上传完成", this, uploadfileCount, fileCount);
            // search.manualCloseLoading();
            // HEINFO:2018/3/22 15:57 所有文件上传完成后的回调
            var fun = opts.uploadFinished || me.defaultOptions.uploadFinished;
            fun.call(me, this.resultArr, uploadfileCount !== fileCount);
        });

        uploader.on('all', function (type) {
            var stats;
            switch (type) {
                case 'uploadFinished':
                    setState('confirm');
                    break;

                case 'startUpload':
                    setState('uploading');
                    break;

                case 'stopUpload':
                    setState('paused');
                    break;

            }
        });


        // $upload.on('click', function () {
        //     if ($(this).hasClass('disabled')) {
        //         return false;
        //     }
        //
        //     if (state === 'ready') {
        //         uploader.upload();
        //     } else if (state === 'paused') {
        //         uploader.upload();
        //     } else if (state === 'uploading') {
        //         uploader.stop();
        //     }
        // });

        $info.on('click', '.retry', function () {
            uploadfileCount = 0;
            uploader.retry();
        });

        // $info.on('click', '.ignore', function () {
        //     alert('todo');
        // });

        // $upload.addClass('state-' + state);
        updateTotalProgress();
        return uploader;
    }
}

var Upload2 = {
    delArr: [],
    resultArr: [],
    allUploader: [],
    quaList: undefined,
    line: 0,
    currentLine: 0,
    createUploader: function () {
        var me = this;
        var uploader = WebUploader.create({
            swf: LIB + '/webuploader/Uploader.swf',
            // 文件接收服务端。
            // server: CTX + '/file/upload/upload',
            server: CTX + '/file/upload/settingsUpload',
            pick: {
                id: '#pickers' + me.line,
                multiple: false
            },
            resize: true,
            auto: false,
            threads: 1,
            formData: {relativePath: me.curropts.wbOpts.relativePath},
            accept: me.curropts.accept,
            disableWidgets: ["custom"]

        });
        return uploader;
    },
    addQua: function (data) {
        var me = this, line = ++me.line, sid = data ? data.sid : "";
        // $('#qua').append('<tr> <td colspan="2"> <div id="qua' + line + '" class="search-select" rules="required" multiSelect="false" idField="id" textField="text" width="400" height="40"></div> </td> </tr> <tr> <td> <div id="sdesc' + line + '" class="search-textarea" width="300"></div> </td> <td id="img' + line + '">  <div id="picker' + line + '">选择文件</div> </td> </tr>');
        $('#qua').append('<tr> ' +
            '<td width="70%"><div id="qua' + line + '" class="search-select" rules="required" multiSelect="false" idField="id" textField="text" width="100%" height="30"></div> </td>' +
            '<td id="img' + line + '" rowspan="2" width="15%">' +
            '<div class="placeholder' + line + '">' +
            '<div id="pickers' + line + '">选择文件</div> ' +
            '</div> ' +
            '<div class="queueList' + line + '"> </div> ' +
            '</td>' +
            '<td rowspan="2" width="15%"> <button class="del" data-line="' + line + '" data-sid="' + sid + '" style="border:none;">删除</button> </td>' +
            '</tr> ' +
            '<tr>' +
            '<td><div id="sdesc' + line + '" class="search-textarea" width="300"></div> </td> ' +
            '</tr>');
        var $queueList = $('.queueList' + line), $placeholder = $('.placeholder' + line);
        search.parse();
        var zz = search.get("qua" + line), bz = search.get("sdesc" + line), uploader = me.createUploader();
        zz.loadData(me.quaList);
        uploader['fileCount'] = 0, uploader['uploadFileCount'] = 0, uploader['line'] = line, uploader['effective'] = true;
        var $div = $('<div class="imgIndex' + uploader.fileCount + '" style="position:relative"> </div>'),
            $imgWrap = $('<p id="wrap' + line + '" class="imgWrap"></p>').appendTo($div),
            $btns = $(' <div class="file-panel" style="height:0px;"> <span><i class="fa fa-trash"></i></span> </div>').appendTo($div);

        if (data) {
            zz.setValue(data.sdictionarieid);
            bz.setValue(data.sdesc);
            if (data.spath) {
                $placeholder.addClass('element-invisible');
                var img = $('<img  src="' + NETWORK_ROOT + data.spath + '" data-spath="' + data.spath + '" data-sname="' + data.sname + '" data-sid="' + data.sid + '" style="width: 100px;height: 100px;">');
                $imgWrap.empty().append(img);
                $queueList.append($div);
            }
            uploader.fileCount += 1;
            $div.on('mouseenter', function () {
                $btns.stop().animate({height: 30});
            });

            $div.on('mouseleave', function () {
                $btns.stop().animate({height: 0});
            });
            $btns.on('click', 'span', function () {
                var index = $(this).index();
                switch (index) {
                    case 0:
                        // me.delArr.push($imgWrap.find('img').attr('data-sid'));
                        $btns.stop().animate({height: 0});
                        uploader.fileCount -= 1;
                        $placeholder.removeClass('element-invisible');
                        $div.remove();
                        return;
                }
            });

        }


        uploader.on('fileQueued', function (file) {
            $placeholder.addClass('element-invisible');
            uploader.makeThumb(file, function (error, src) {
                var img = $('<img src="' + src + '" style="width: 100px;height: 100px;">');
                $imgWrap.empty().append(img);
                $queueList.append($div);
            }, 100, 100);
            uploader.fileCount += 1;
            uploader.uploadFileCount += 1;
            $div.on('mouseenter', function () {
                $btns.stop().animate({height: 30});
            });

            $div.on('mouseleave', function () {
                $btns.stop().animate({height: 0});
            });
            $btns.on('click', 'span', function () {
                var index = $(this).index();
                switch (index) {
                    case 0:
                        $btns.stop().animate({height: 0});
                        uploader.removeFile(file);
                        uploader.fileCount -= 1;
                        uploader.uploadFileCount -= 1;
                        $placeholder.removeClass('element-invisible');
                        $div.remove();
                        return;
                }
            });
            // $('#qua').on('click','button', function () {
            //     var delBtn=$(this), tr2 = delBtn.parent().parent(), tr1 = tr2.prev();
            //     tr2.remove();
            //     tr1.remove();
            //     me.allUploader.splice(--line,1);
            // });
        });
        uploader.on('fileDequeued', function (file) {

        });
        uploader.on('error', function (code) {
            console.log('Eroor: ' + code);
        });
        uploader.on('uploadComplete', function (file) {
            console.error("uploadComplete", file);
        });
        uploader.on('uploadSuccess', function (file, response) {
            console.log("文件单次完成", file, response);
            var zz = search.get("qua" + uploader.line).getValue();
            var bz = search.get("sdesc" + uploader.line).getValue();
            var sid = $('#img' + uploader.line).next().children('button').data('sid');
            me.resultArr.push({
                sid: sid === '' ? undefined : sid,
                sdictionarieid: zz,
                sdesc: bz,
                sname: response.result.sname,
                spath: response.result.spath
            });
            me.currentLine++;
            me.upload();
        });
        me.allUploader.push(uploader);
        // if (data) {
        //     search.get("qua" + line).setValue(data.sdictionarieid);
        //     search.get("sdesc" + line).setValue(data.sdesc);
        //     var img = $('<img id="" data-line="' + line + '" src="' + NETWORK_ROOT + data.spath + '">');
        //     $('#img' + line).empty().append(img);
        // }
    },
    upload: function () {
        var me = this;
        if (me.allUploader.length > me.currentLine) {
            var uploader = me.allUploader[me.currentLine];
            if (uploader.effective) {
                if (uploader.uploadFileCount > 0) {
                    // if (!uploader['uploaded']) {
                    uploader.upload();
                    //     uploader.uploaded = true;
                    // }
                } else {
                    var zz = search.get("qua" + uploader.line).getValue();
                    var bz = search.get("sdesc" + uploader.line).getValue();
                    var obj = {
                        sdictionarieid: zz,
                        sdesc: bz
                    }
                    var queue = $('.queueList' + uploader.line);
                    var img = queue.find('img');
                    if (img.length > 0) {
                        obj.sid = img.attr('data-sid');
                        obj.sname = img.attr('data-sname');
                        obj.spath = img.attr('data-spath');
                    } else {
                        obj.sid = queue.parent().next().find('button').data('sid');
                    }
                    me.resultArr.push(obj);
                    me.currentLine++;
                    me.upload();
                }
            } else {
                me.currentLine++;
                me.upload();
            }

        } else {
            console.error("回调其他方法", me.resultArr, me.delArr);
            me.curropts.callback.call(this, me.resultArr, me.delArr);
        }

    },
    defaultOpts: {
        url: '',
        data: {},
        callback: $.noop,
        initData: [/*{
         sdictionarieid: "",
         sdesc: "",
         sid:"",
         sname: "",
         spath: ""
         }*/],
        wbOpts: {
            relativePath: '/search/mini'
        },
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    },
    currentOpts: undefined,
    init: function (opts) {
        var me = this;
        me.curropts = Globle.fun.applyIf(opts, me.defaultOpts);
        if (me.curropts.url && me.curropts.url !== '') {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: CTX + me.curropts.url,
                data: me.curropts.data,
                success: function (result) {
                    me.quaList = result.result;
                    for (var i = 0; i < me.curropts.initData.length; i++) {
                        me.addQua(me.curropts.initData[i]);
                    }
                },
                error: function () {

                }
            });
        }
        $('#qua').on('click', 'button', function () {
            var delBtn = $(this), tr1 = delBtn.parent().parent(), tr2 = tr1.next();
            tr2.remove();
            tr1.remove();
            if (delBtn.data('sid') !== '') {
                me.delArr.push(delBtn.data('sid'));
            }
            me.allUploader[delBtn.attr('data-line') - 1]['effective'] = false;
            // console.log("所有的uploader数", me.allUploader.length);

        });
        return true;
    },
    setData: function (arr) {
        var me = this;
        me.line = 0;
        $('#qua').empty();
        $.each(arr, function (i, v) {
            me.addQua(v);
        });

    }

}

// 表格自定义方法
var GridFuncUtil = {
	countObj : {},
	addCount : function(field) {
		// 生成ID计数器
		var mine = this, count = mine.countObj[field];
		mine.countObj[field] = (count != undefined && count >= 0 ? (count + 1) : 0);
		return mine.countObj[field];
	},
	createFunc : function(funcname, __arguments) {
		// 执行方法，第一个参数作为this
		var clickfunc = eval(funcname);
		if (funcname && typeof(clickfunc) == 'function') {
			var parameters = new Array(), e = null;
			if (__arguments && __arguments.length > 0) {
				e = __arguments[0];
				$.each(__arguments, function(index) {
					if (index > 0) {
						parameters.push(this);
					}
				})
			}
			clickfunc.apply(e, parameters);
		}
	},
	normal : function(e) {
		// 普通列快捷方法
		var mine = this, count = mine.addCount(field);
		var record = { origin : { record : e.record } }, field = e.column.field, __arguments = arguments;
		var $this = $('<span id="' + field + '_' + count + '" style="color: blue; cursor: pointer;">' + e.record[field] + '</span>');
		$this.on('click', function() {
			var funcname = null;
			if (_all_quick_map.get(field) == "" || __state == 2) {
				funcname = 'queryDetail';
			} else if (_quick_map.get(field) != "") {
				funcname = _quick_map.get(field);
			}
			mine.createFunc(funcname, __arguments);
		})
		e.html = $this;
	},
	oper : function(e) {
		// 操作列快捷方法
		var mine = this;
		var record = { origin : { record : e.record } }, field = e.column.field, __arguments = arguments;
		var $htmls = $('<div></div>');
		if (__state == 1) {
			if (tailButList && tailButList.length > 0) {
				$.each(tailButList, function(index) {
					var spcmethod = this.spcmethod, count = mine.addCount(field);
					var $html = $('<a id="' + field + '_'+ count +'" href="javascript:void(0);">' + this.sname + '</a>');
					$html.addClass(this.sicon);
					
					$html.on('click', function() {
						mine.createFunc(spcmethod, __arguments);
					})
					
					if (index > 0) {
						$htmls.append('&emsp;');
					}
					$htmls.append($html);
				})
			}
		} else if (__state == 2) {
			var count = mine.addCount(field);
			var $delhtml = $('<a id="' + field + '_'+ count +'" class="del" href="javascript:void(0);">删除</a> ');
			$delhtml.on('click', function() {
				mine.createFunc('delData', __arguments);
			})
			
			count = mine.addCount(field);
			var $enablehtml = $('<a id="' + field + '_'+ count +'" class="btn-enable" href="javascript:void(0);">恢复</a>');
			$enablehtml.on('click', function() {
				mine.createFunc('restData', __arguments);
			})
			$htmls.append($delhtml).append('&emsp;').append($enablehtml);
		}
		e.html = $htmls;
	}
}