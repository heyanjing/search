/**
 * Created by heyanjing on 2018/4/13 16:20.
 */
function setData(data) {
    console.log(data);
    Preview.init(data);
}
var Preview = {
    event: function () {
        var me = this, $preLeft = $('#pre-left'), $preRight = $('#pre-right'), $pre = $('#pre'), $next = $('#next'), $imgBox = $('#imgBox');
        //旋转，关闭，下载
        $('#action').on('click', 'div', function () {
            var index = $(this).index();
            switch (index) {
                case 0:
                    var $selected = $('i.selected'), spathArr = [];
                    if ($selected.length === 0) {
                        //下载当前文件
                        console.log("未选择", me.index, $imgBox.find('i').eq(me.index).data('spath'));
                        var currentI = $imgBox.find('i').eq(me.index), spath = currentI.data('spath');
                        if (!me.isIMG(spath)) {
                            spath = currentI.data('realPath');
                        }
                        spathArr.push(spath);
                    } else {
                        //打包下载
                        $selected.each(function () {
                            var $this = $(this), spath = $this.data('spath');
                            if (!me.isIMG(spath)) {
                                spath = $this.data('realPath');
                            }
                            spathArr.push(spath);
                        });
                        console.log("选择" + $selected.length, spathArr);
                    }
                    // window.location.href = CTX + "/file/download?params=" + spathArr;
                    //文件下载
                    $.ajax({
                        url: CTX + '/file/download/readyDownload',
                        data: {
                            params: spathArr
                        },
                        success: function (result) {
                            console.log(result);
                            if (result.status) {
                                if (result.result.serverName) {
                                    window.location.href = CTX + "/" + result.result.serverName + "/file/download?fileName=" + result.result.fileName;
                                } else {
                                    window.location.href = CTX + "/file/download?fileName=" + result.result.fileName;
                                }
                            } else {

                            }
                        },
                        error: function () {

                        }
                    });
                    return;
                case 1:
                    me.imgRotation(-90);
                    return;
                case 2:
                    me.imgRotation(90);
                    break;

                case 3:
                    window.onCloseClick();
                    break;
            }
        });
        //缩略图左右移动
        $pre.on('click', function () {
            var position = me.imgBox.position(), leftVal = position.left;
            if (me.imgBoxWidth + leftVal > 560 * 2) {
                me.imgBox.css('left', (leftVal - 560) + 'px');
            } else {
                me.imgBox.css('left', -(me.imgBoxWidth - 560) + 'px');
            }
        });
        $next.on('click', function () {
            var position = me.imgBox.position(), leftVal = position.left;
            if (-leftVal > 560) {
                me.imgBox.css('left', (leftVal + 560) + 'px');
            } else {
                me.imgBox.css('left', 0 + 'px');
            }
        });
        //大图左右移动
        $('#pre-left').on('click', function () {
            console.log(me.arrLength);
            me.load(me.arr[++me.index]);
            if (me.index !== me.arrLength - 1) {
                $preLeft.show();
            } else {
                $preLeft.hide();
            }
            if (me.index !== 0) {
                $preRight.show();
            } else {
                $preRight.hide();
            }
            console.log(me.index);
            if (me.index % 8 === 0) {
                $pre.trigger('click');
            }
        });
        $('#pre-right').on('click', function () {
            if (me.index % 8 === 0) {
                $next.trigger('click');
            }
            me.load(me.arr[--me.index]);
            if (me.index !== 0) {
                $preRight.show();
            } else {
                $preRight.hide();
            }
            if (me.index !== me.arrLength) {
                $preLeft.show();
            } else {
                $preLeft.hide();
            }
            console.log(me.index);
        });
        $imgBox.on('click', 'img', function () {
            me.index = $imgBox.find('img').index(this);
            me.load(me.arr[me.index]);
        });
        $imgBox.on('click', 'i', function () {
            var $this = $(this);
            if ($this.hasClass('selected')) {
                $this.removeClass('selected');
            } else {
                $this.addClass('selected');
            }
        });
    },
    isWOPI: function (spath) {
        if (spath.slice(-5).toLowerCase() === '.docx' || spath.slice(-5).toLowerCase() === '.xlsx' || spath.slice(-5).toLowerCase() === '.pptx' || spath.slice(-4).toLowerCase() === '.pdf') {
            return true;
        }
        return false;
    },
    isIMG: function (spath) {
        if (spath.slice(-4).toLowerCase() === '.jpg' || spath.slice(-5).toLowerCase() === '.jpeg' || spath.slice(-4).toLowerCase() === '.png' || spath.slice(-4).toLowerCase() === '.gif' || spath.slice(-4).toLowerCase() === '.bmp') {
            return true;
        }
        return false;

    },
    load: function (current) {
        var me = this, spath = current.spath, sname = current.sname, sdesc = current.sdesc;
        var $rotation = $('.rotation'), $iframe = $('#iframe'), $imgDiv = $('#imgDiv'), $img = $('#img'), $name = $('#name'), $desc = $('#desc');
        me['iframe'] = $iframe;
        if (me.isWOPI(spath)) {
            $rotation.hide();
            $imgDiv.hide();
            $iframe.attr('src', spath);
            $iframe.show();
        } else {
            $iframe.hide();
            var isImg = false;
            if (me.isIMG(spath)) {
                isImg = true;
                $rotation.show();
                $img.attr('src', NETWORK_ROOT + spath);
                $imgDiv.show();
            } else {
                $rotation.hide();
                $img.attr('src', IMG + '/common/defaultmax.png');
                $imgDiv.show();
            }
            // $iframe.attr('src', CTX + '/common/img?spath=' + spath + '&isImg=' + isImg);
        }
        $name.html(sname);
        $desc.html(sdesc ? sdesc : "");
        me.imgBox.find('img').removeClass('active').eq(me.index).addClass('active');
        var $preLeft = $('#pre-left'), $preRight = $('#pre-right');
        if (me.index !== me.arrLength - 1) {
            $preLeft.show();
        } else {
            $preLeft.hide();
        }
        if (me.index !== 0) {
            $preRight.show();
        } else {
            $preRight.hide();
        }
    },
    eachMini: function (arr) {
        var me = this, imgs = "";
        $.each(arr, function (i, v) {
            // console.log(v);
            //jpg jpeg png gif bmp
            var spath = v.spath, realPath = '', img = '';
            if (me.isIMG(v.spath)) {
                img = NETWORK_ROOT + spath;
            } else {
                realPath = v.realPath;
                if (spath.slice(-5).toLowerCase() === '.docx') {
                    img = IMG + '/common/docx.png';
                } else if (spath.slice(-5).toLowerCase() === '.xlsx') {
                    img = IMG + '/common/xlsx.png';
                } else if (spath.slice(-5).toLowerCase() === '.pptx') {
                    img = IMG + '/common/pptx.png';
                } else if (spath.slice(-5).toLowerCase() === '.pdf') {
                    img = IMG + '/common/pdf.png';
                } else if (spath.slice(-4).toLowerCase() === '.rar') {
                    img = IMG + '/common/rar.png';
                } else if (spath.slice(-4).toLowerCase() === '.zip') {
                    img = IMG + '/common/zip.png';
                } else {
                    img = IMG + '/common/default.png';
                }
            }
            imgs += '<a href="javascript:void(0)"><img src="' + img + '"><span><i data-spath="' + spath + '" data-realPath="' + realPath + '" class="fa fa-check-square"></i></span></a>';
        });
        return imgs;
    },
    imgInit: function () {
        var me = this;
        me['supportTransition'] = (function () {
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                    'WebkitTransition' in s ||
                    'MozTransition' in s ||
                    'msTransition' in s ||
                    'OTransition' in s;
            s = null;
            return r;
        })();
        me['img'] = $('#img');
        me['deg'] = 0;
        me.imgEvent();
    },
    imgEvent: function () {
        var me = this, $imgDiv = $('#imgDiv'), $img = $('#img'), mouseWheel = function mouseWheel(e) {
            if (e) {
                e.preventDefault();
            }
            var e = window.event || e;
            var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));
            if (me.imgwidth === $img.width() && delta === -1) {
                return;
            }
            $img.css('width', Math.max(me.imgwidth, Math.min(10000, $img.width() + (40 * delta))) + "px");
            console.log(me.imgwidth, $img.width());
            // if ($img.width() > me.imgwidth) {
            $img.css('top', ($img.position().top - (40 * delta * me.ratio) / 2) + "px");
            $img.css('left', ($img.position().left - (40 * delta) / 2) + "px");
            // }
        };
        $img.on(
            {
                mousewheel: mouseWheel,
                DOMMouseScroll: mouseWheel,
                onmousewheel: mouseWheel,
                dblclick: function () {
                    $img.css('width', ( $img.width() + (40 * 1) ) + "px");
                    $img.css('top', ($img.position().top - (40 * me.ratio) / 2) + "px");
                    $img.css('left', ($img.position().left - 40 / 2) + "px");
                },
                load: function (e) {
                    var $this = $(this);
                    $this.removeAttr("style");
                    $this.css('position', 'absolute');
                    var height = $this.height(), width = $this.width();
                    me['imgheight'] = height;
                    me['imgwidth'] = width;
                    me['ratio'] = height / width;
                    $this.css('top', (document.documentElement.clientHeight - height) / 2);
                    $this.css('left', (document.documentElement.clientWidth - width) / 2);
                    console.log("图片加载完成", width, height, e);
                },
                mousedown: function (e) {
                    console.log("鼠标点下", e);
                    if (e) {
                        e.preventDefault();
                    }
                    e = e;
                    me['down'] = true;
                    me['dx'] = e.clientX;
                    me['dy'] = e.clientY;
                    me['sx'] = parseInt($(this).position().left);
                    me['sy'] = parseInt($(this).position().top);
                },
                mouseup: function (e) {
                    console.log("鼠标抬起");
                    me['down'] = false;
                }
            }
        );
        $imgDiv.on({
            mousemove: function (e) {
                console.log("鼠标移动");
                if (me.down) {
                    var ev = e;
                    $img.css('top', ev.clientY - (me.dy - me.sy) + 'px');
                    $img.css('left', ev.clientX - (me.dx - me.sx) + 'px');
                }
            },
            click: function () {
                console.log(11);
            }
        });
    },
    imgRotation: function (deg) {
        var me = this;
        me.deg += deg;
        if (me.supportTransition) {
            deg = 'rotate(' + me.deg + 'deg)';
            me.img.css({
                '-webkit-transform': deg,
                '-mos-transform': deg,
                '-o-transform': deg,
                'transform': deg
            });
        } else {
            me.img.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((me.deg / 90) % 4 + 4) % 4) + ')');
        }
    },
    init: function (data) {
        var me = this, arr = data.arr, arrLength = arr.length, index = data.index, current = arr[index];
        var $imgBox = $('#imgBox'), $picInfoBtn = $('#pic-infobtn'), $preLeft = $('#pre-left'), $preRight = $('#pre-right'), $maxBtn = $('div.max-btn'), $miniBtn = $('div.mini-btn');
        me['arr'] = arr;
        me['index'] = index;
        me['arrLength'] = arrLength;
        me['imgBox'] = $imgBox;
        me['imgBoxWidth'] = arrLength * 70;
        if (arrLength <= 1) {
            $picInfoBtn.hide();
            $maxBtn.hide();
        } else {
            if (index !== 0) {
                $preRight.show();
            }
            if (index !== arrLength) {
                $preLeft.show();
            }
            if (arrLength <= 8) {
                $picInfoBtn.show();
                $miniBtn.hide();
            } else {
                $picInfoBtn.show();
                $miniBtn.show();
            }
            var imgs = me.eachMini(arr);
            $imgBox.empty().append(imgs);
        }
        me.load(current);
        me.event();
        me.imgInit();
    }
}
