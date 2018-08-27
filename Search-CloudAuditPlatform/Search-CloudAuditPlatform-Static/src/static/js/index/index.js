search.parse();
var Index = {
    event: function () {
        var me = this, $userDetailInfo = $('#userDetailInfo'), sphone = "", susername = "", semail = "";

        //显示个人信息
        // $('#userSimpleInfo').on({
        //     mouseenter: function () {
        //         $(this).children('ul').stop().slideDown();
        //     },
        //     mouseleave: function () {
        //         $(this).children('ul').stop().fadeOut('slow');
        //     }
        // });
        $('#userSimpleInfo').on('click', 'a', function () {
            var $ul = $(this).next();
            if ($ul.children().length !== 0) {
                $ul.slideToggle();
            }
        });
        $('#changeOrg').on('click', function () {
            var data = $(this).next().data('all');
            var cfg = {
                url: CTX + "/home/changeOrg",
                title: "切换机构",
                width: 600,
                height: 400,
                onload: function (window) {
                    window.setData(data);
                },
                ondestroy: function (result) {
                }
            }
            top.search.popDialog(cfg);

        });
        $userDetailInfo.on('click', 'a', function () {
            $(this).next().slideToggle();
        });
        $userDetailInfo.on('mouseleave ', 'div.top-linkstab', function () {
//            $(this).stop().fadeOut('slow')
        });
        $('#detailInfo').on('click', 'li', function () {
            var $li = $(this), index = $li.index(), $correspondingLi = $('#corresponding').children().eq(index);
            $li.addClass('active').siblings().removeClass('active');
            $correspondingLi.addClass('thisclass').siblings().removeClass('thisclass');
        });
        $('#logout').on('click', function () {
            Globle.fun.confirm("确定要退出吗?", function (btn) {
                location.href = CTX + '/logout';
            });
        });
        //修改个人信息。
        $('#baseMsg').on('click', function () {
            top.search.popDialog({
                url: CTX + "/home/goUpdateUserMess", title: "个人信息", width: 600, height: 400,
                onload: function (window) {
                    window.instance.loadData();
                }, ondestroy: function (result) {
                    Index.loadUser();
                }
            });
        });
        //修改密码。
        $('#userPwd').on('click', function () {
            top.search.popDialog({
                url: CTX + "/home/goUpdatePassword", title: "修改密码", width: 500, height: 350,
                onload: function (window) {
                    window.instance.loadData({sphone: Index.event.sphone, susername: Index.event.susername});
                }, ondestroy: function (result) {
                }
            });
        });
        //重新绑定。
        $('#userPhone').on('click', function () {
            top.search.popDialog({
                url: CTX + "/home/goUpdatePhone", title: "重新绑定", width: 500, height: 350,
                onload: function (window) {
                    window.instance.loadData({sphone: Index.event.sphone, susername: Index.event.susername, email: Index.event.semail});
                }, ondestroy: function (result) {
                }
            });
        });
        //绑定邮箱。
        $('#userEmail').on('click', function () {
            top.search.popDialog({
                url: CTX + "/home/goUpdateEmail", title: "绑定邮箱", width: 500, height: 350,
                onload: function (window) {
                    window.instance.loadData({sphone: Index.event.sphone, susername: Index.event.susername, email: Index.event.semail});
                }, ondestroy: function (result) {
                }
            });
        });

        //分类
        $('ul.menus').on('click', 'li.cls', function () {
            var $this = $(this), currentDivId = $this.data('div'), currentDiv = $('#' + currentDivId), currentIframe = $('#' + currentDivId + 'main_iframe');
            me['currentDivId'] = currentDivId;
            me['currentIframe'] = currentIframe;
            if ($this.data('menu')) {
                $this.addClass("nav-itemx").siblings().removeClass("nav-itemx");
            } else {
                var currentId = $this.data('id'), $allLi = $('#menus').find('li');
                $allLi.removeClass("nav-itemx");
                $allLi.each(function (i, v) {
                    var $v = $(v);
                    if (currentId === $v.data('id')) {
                        $v.addClass("nav-itemx");
                        return false;
                    }
                });
            }
            currentDiv.siblings().hide();
            currentDiv.show();
            if (currentDiv.data('type') === 302) {
                $('#' + Index.currentDivId + 'section').css("height", $("#right").height() - $('#' + Index.currentDivId + 'path-tabs').outerHeight());
            }

        });
        //显示隐藏更多分类
        $('#more-icon').on('click', 'a', function () {
            $('#more').stop().slideToggle("slow");
        });
        $('#more').on('mouseleave', function () {
            $(this).stop().slideToggle("slow");
        });

    },
    classify2: {
        event: function () {
            var me = this;
            //菜单栏缩放
            $('li.nav-item>a').on('click').click(function () {
                var $nav = $('#' + Index.currentDivId + 'nav'), $a = $(this), $ul = $a.next();
                if(!$nav.hasClass('nav-mini')){
                    if ($ul.css('display') === "none") {
                        $('#' + Index.currentDivId + ' li.nav-item').children('ul').slideUp("slow");
                        $ul.slideDown("slow");
                        $a.parent().addClass('nav-show').siblings().removeClass('nav-show');
                    } else {
                        $ul.slideUp("slow");
                        $ul.parent().removeClass('nav-show');
                    }
                }
            });
            //菜单栏收窄
            $('div.mini').on('click', function () {
                var $nav = $('#' + Index.currentDivId + 'nav'),$navItem=$nav.find('.nav-item') ,$logoMini = $('#' + Index.currentDivId + 'logo-mini'), $path = $('#' + Index.currentDivId + 'path'),$section = $('#' + Index.currentDivId + 'section');
                if ($nav.hasClass('nav-mini')) {
                    $nav.removeClass('nav-mini');
                    $navItem.removeClass('nav-minione');
                    $logoMini.removeClass('logo-mini-show');
                    $path.removeClass('logo-mini-path');
                    $section.removeClass('section-mini-tab');
                    $(".mini-fitmenu").addClass("menuscroll");
                    $(".mini-fitmenu ul").removeClass('maxheight');
                    
                    var  allLi=$('#' + Index.currentDivId + ' .nav-item');
                    allLi.eq(Index.classify2.currentItem).children('a').trigger('click');
                } else {
                    $('#' + Index.currentDivId + ' .nav-item.nav-show').removeClass('nav-show');
                    $('#' + Index.currentDivId + ' .nav-item').children('ul').removeAttr('style');
                    $nav.addClass('nav-mini');
                    $navItem.addClass('nav-minione');
                    $logoMini.addClass('logo-mini-show');
                    $path.addClass('logo-mini-path');
                    $section.addClass('section-mini-tab');
                    $(".mini-fitmenu").removeClass("menuscroll");
                    $(".mini-fitmenu ul").addClass('maxheight');
                }
            });
            //点击节点
            $('li.node').on('click', function () {
                var  allLi=$('#' + Index.currentDivId + ' .nav-item');
                var $this = $(this),$li=$this.parent().parent(),$allNode=$li.parent().find('.node'), method = $(this).data("method"), $locationSpan = $('#' + Index.currentDivId + 'locatSpan'), $location = $locationSpan.parent().parent();
                Index.classify2['currentItem']=allLi.index($li);
                $allNode.removeClass('active');
                $this.addClass('active').siblings().removeClass('active');
                $locationSpan.empty().append('<span>' + $this.data('parent-name') + '</span>-<span>' + $this.data('name') + '</span>');
                $location.data('module-id', $this.data('parent-id'));
                eval(method);
            });
            //位置
            $('li.location').on({
                click: function () {
                    var moduleId = $(this).data('module-id');
                    $('#' + Index.currentDivId + ' li.nav-item').each(function () {
                        var $this = $(this);
                        if ($this.data('id') === moduleId) {
                            $this.children('a').trigger('click');
                            return false;
                        }
                    });
                    console.log();
                }
            });
            //标签
            $('ul.lables').on({
                click: function () {
                    var $this = $(this);
                    $this.addClass('active').siblings().removeClass('active');
                    me.setIframeSrc($this.data('url'), $this.data('id'));
                },
                mouseover: function () {
                    var $this = $(this);
                    if ($this.attr('class').indexOf('active') === -1) {
                        $this.addClass('hover');
                    }
                },
                mouseout: function () {
                    var $this = $(this);
                    if ($this.attr('class').indexOf('hover') !== -1) {
                        $this.removeClass('hover');
                    }
                }
            }, 'li');
        },
        setIframeSrc: function (url, functionId) {
            if (url.indexOf('?') === -1) {
                url += '?id=' + functionId;
            } else {
                url += "&id=" + functionId;
            }
            Index.currentIframe.attr('src', CTX + url);
        },
        node: function (node, params) {
            var me = this, $node = $(node), nodeId = $node.data('id'), nodeMethod = $node.data("method"), $lables = $('#' + Index.currentDivId + 'lables'), $section = $('#' + Index.currentDivId + 'section'), $tabsover = $lables.parent(), $tabbd = $('#' + Index.currentDivId + 'tab-bd');
            console.log("节点名称", node);
            console.log("参数名称", params);
            if (params) {
                //直接访问页面
                me.setIframeSrc(params, nodeId);
                $lables.hide();
                $tabbd.css('height', $section.height() - $tabsover.outerHeight());
            } else {
                //加载标签
                $.ajax({
                    url: CTX + "/functions/findByParentId",
                    data: {parentId: nodeId},
                    success: function (result) {
                        if (result.status) {
                            var lables = "";
                            $.each(result.result, function (i, v) {
                                var cls = i === 0 ? "active" : "";
                                if (i === 0) {
                                    me.setIframeSrc(v.spcmethod, v.sid);
                                }
                                lables += '<li class="' + cls + '" data-id="' + v.sid + '" data-url="' + v.spcmethod + '">' + v.sname + '</li>';
                            });
                            $lables.empty().append(lables);
                            $lables.show();
                            $tabbd.css('height', $section.height() - $tabsover.outerHeight());
                        }
                    },
                    error: function () {

                    }
                });

            }
        },
        back: function () {
            var me = this;
            console.log("进入back");
            var currentIframe = Index.currentIframe, src = currentIframe.attr('src');
            if (src.indexOf(Globle.constant.recycle) !== -1) {
                src = src.replace(Globle.constant.recycle, "");
            }
            currentIframe.attr('src', src);
        }
    },
    init: function () {
        var me = this, $li = $('li.nav-itemx'), currentDivId = $li.data('div'), currentDiv = $('#' + currentDivId), currentIframe = $('#' + currentDivId + 'main_iframe');
        me['currentDivId'] = currentDivId;
        me['currentIframe'] = currentIframe;
        me.event();
        me.classify2.event();
//        $("div.right-full").css("height", $("#right").height() - $("div.path-tabs").outerHeight());
    },
    loadUser: function (__usertype) {
//    	if(__usertype == "1") return;
        $.ajax({
            url: CTX + "/home/getUserMessage",
            async: false,
            success: function (result) {
                if (result.result != null) {
                    var data = result.result;
                    Index.event.sphone = data.sphone;
                    Index.event.susername = data.susername;
                    Index.event.semail = data.semail;
                    //基本信息
                    $('#susername').val(data.susername);
                    $('#snickname').val(data.snickname);
                    $('#ssignature').val(data.ssignature);
                    //账户安全
                    if (data.sphone != null && data.sphone != "") $('#sphone').text(data.sphone.substr(0, 3) + "****" + data.sphone.substr(7));
                    else $('#sphone').text("未绑定");
                    if (data.semail != null && data.semail != "") $('#semail').text(data.semail);
                    else $('#semail').text("未绑定");
                    //详细信息
                    $('#sname').val(data.sname);
                    var igender = "";
                    if (data.igender == 1) igender = "男";
                    else if (data.igender == 2) igender = "女";
                    $('#igender').val(igender);
                    $('#saddress').val(data.saddress);
                    if (data.sidcard != null && data.sidcard != "") $('#sidcard').val(data.sidcard.substr(0, 3) + "****" + data.sidcard.substr(14));
                    $('#orgname').val(data.orgname);
                    $('#sgraduateschool').val(data.sgraduateschool);
                    $('#ldgraduationdate').val(data.ldgraduationdate);
                }
            }
        });
    }
};

$(function () {
    Index.init();
    Index.loadUser(__usertype);
    // instance.loadMenu();
    // nav();
    //
    // tabs(".toptab-hd", "active", ".toptab-bd");
    // tabs(".tab-hd", "active", ".tab-bd");


//	instance.expandChildren();

    //显示分类对应的div

});


/*
 /!**
 * 控制器。
 *!/
 function mainHome() {
 this.clickState = false;
 this.sphone = "";
 this.susername = "";
 this.semail = "";
 this.loadMenu = function () {
 $.ajax({
 url: CTX + "/home/getLeftNavigatData",
 success: function (result) {
 instance.loadLeftNavigat(result.result);
 }
 });
 };

 /!**
 * 生成左树形菜单。
 *!/
 this.loadLeftNavigat = function (menu) {
 if (!menu || menu.length == 0) return;
 var container = $(".nav");
 var pul = $("<ul></ul>");
 for (var i = 0; i < menu.length; i++) {
 var pmenu = menu[i];
 var pmhtml = $("<li class='nav-item' onclick='instance.expandChildren();'><a href='javascript:void(0);'><i class='" + pmenu.sicon + "'></i><span>" + pmenu.sname +
 "</span><i class='my-icon nav-more'></i></a><ul></ul></li>");
 pul.append(pmhtml);
 var children = pmenu.children;
 var ul = pmhtml.find("ul");
 if (children && children.length > 0) {
 for (var j = 0; j < children.length; j++) {
 var cmenu = children[j];
 var li = $("<li onclick='instance.sonClick(this, \"" + cmenu.sid + "\", \"" + CTX + cmenu.spcmethod + "\", \"" + pmenu.sname + "\", \"" + cmenu.sname + "\", \"" + cmenu.tabcount + "\");'><a href='javascript:void(0);'><span>" + cmenu.sname + "</span></a></li>");
 ul.append(li);
 }
 }
 container.append(pul);
 }
 }

 /!**
 * 子菜单点击
 *!/
 this.sonClick = function (obj, id, path, fname, name, tabcount) {
 var total = $(".tab").height();
 var d = $(".tabs_over").outerHeight(true);

 var __onclick_method = "";
 $("#locatSpan").html("");

 if (tabcount > 0) {//有标签
 $(".tabs_over").show();
 $(".scroll").show();
 this.loadTad(id);
 __onclick_method = "instance.loadTad(\"" + id + "\")";

 $(".thisclass").css("height", (total - d) + "px");
 } else { //没用标签。
 $(".tabs_over").hide();
 $(".scroll").hide();
 this.openIframe(id, path);
 __onclick_method = "instance.openIframe(\"" + id + "\", \"" + path + "\")";

 $(".thisclass").css("height", total + "px");
 }
 $("#locatSpan").html("<span>></span><span onclick='" + __onclick_method + ";'>" + fname + "&nbsp;[&nbsp;<font style='color:blue;'>" + name + "&nbsp;</font>]</span>");
 }

 /!**
 * 加载功能标签。
 *!/
 this.loadTad = function (id) {
 $.ajax({
 url: CTX + "/home/getFuncTabOrButtonData",
 data: {sid: id, itype: 3},
 success: function (result) {
 instance.onDrawTab(result.result);
 }
 });
 }

 /!**
 * 绘制标签。
 *!/
 this.onDrawTab = function (Tab) {
 var __tab_div = $(".tabs_over");
 __tab_div.empty();
 if (Tab.length > 0) {
 var __tab_ul = $("<ul class='tab-hd'></ul>");
 var tables_ul = $(".tab-bd");
 for (var i = 0; i < Tab.length; i++) {
 var tabs = Tab[i];
 var __class = "";
 var __tables_li_clasee = "";
 if (i == 0) {
 __class = "class='active'";
 __tables_li = "class='thisclass'";
 this.openIframe(tabs.sid, CTX + tabs.spcmethod);
 }
 var __tab_li = $("<li " + __class + " onclick='instance.onClickIframe(this,\"" + tabs.sid + "\", \"" + CTX + tabs.spcmethod + "\");'><span>" + tabs.sname + "</span></li>"); //<img src='"+IMG+"/index/del.png' width='16' height='16'>
 __tab_ul.append(__tab_li);

 }
 __tab_div.append(__tab_ul);
 }
 }

 /!**
 * 打开iframe
 *!/
 this.openIframe = function (id, path) {
 var ofm = document.getElementById('main_iframe');
 ofm.contentWindow.location.href = path + "?id=" + id;
 }

 /!**
 * 标签点击事件。
 *!/
 this.onClickIframe = function (obj, id, path) {
 $(".tabs_over").find("li").removeClass("active");
 $(obj).addClass("active");
 this.openIframe(id, path);
 };

 /!**
 * 展开树形菜单。
 *!/
 this.expandChildren = function () {


 /!**
 * nav-mini切换
 *!/
 $('#mini').on('click', function () {
 if (!$('.nav').hasClass('nav-mini')) {
 $('.nav-item.nav-show').removeClass('nav-show');
 $('.nav-item').children('ul').removeAttr('style');
 $('.nav').addClass('nav-mini');
 $('.logo-mini').addClass('logo-mini-show');
 $('.path').addClass('logo-mini-path');
 } else {
 $('.nav').removeClass('nav-mini');
 $('.logo-mini').removeClass('logo-mini-show');
 $('.path').removeClass('logo-mini-path');
 }
 });
 //$('#mini').unbind('click').click(function(){
 //if (!$('.nav').hasClass('nav-mini')) {
 //$('.nav-item.nav-show').removeClass('nav-show');
 // $('.nav-item').children('ul').removeAttr('style');
 // $('.nav').addClass('nav-mini');
 //$('.nav-item').addClass('nav-minione');
 //$('.logo').addClass('logo-minimenu');
 //$('.logo-mini').addClass('logo-mini-show');
 //$('.logo-gl').addClass('logo-gl-hide');
 //$('.right').addClass('logo-mini-right');
 //}else{
 //$('.nav').removeClass('nav-mini');
 //$('.nav-item').removeClass('nav-minione');
 //$('.logo').removeClass('logo-minimenu');
 //$('.logo-mini').removeClass('logo-mini-show');
 //$('.logo-gl').removeClass('logo-gl-hide');
 //$('.right').removeClass('logo-mini-right');
 //}
 //});

 }

 /!**
 * 展开用户信息。
 *!/
 this.expandUser = function () {
 if (__usertype == "1")return;
 if (this.clickState) {
 $('.top-linkstab').hide();
 this.clickState = false;
 return;
 }
 this.clickState = true;
 $('.top-linkstab').show();
 this.loadUser();
 }

 /!**
 * 加载用户个人信息。
 *!/
 this.loadUser = function () {
 $.ajax({
 url: CTX + "/home/getUserMessage",
 success: function (result) {
 if (result.result != null) {
 var data = result.result;
 instance.sphone = data.sphone;
 instance.susername = data.susername;
 instance.semail = data.semail;
 //基本信息
 $('#susername').val(data.susername);
 $('#snickname').val(data.snickname);
 $('#ssignature').val(data.ssignature);
 //账户安全
 $('#sphone').text(data.sphone.substr(0, 3) + "****" + data.sphone.substr(7));
 $('#semail').text(data.semail);
 //详细信息
 $('#sname').val(data.sname);
 var igender = "";
 if (data.igender == 1) igender = "男";
 else if (data.igender == 2) igender = "女";
 $('#igender').val(igender);
 $('#saddress').val(data.saddress);
 $('#sidcard').val(data.sidcard.substr(0, 3) + "****" + data.sidcard.substr(14));
 $('#orgname').val(data.orgname);
 $('#sgraduateschool').val(data.sgraduateschool);
 $('#ldgraduationdate').val(data.ldgraduationdate);
 }
 }
 });
 }

 /!**
 * 修改个人信息。
 *!/
 this.updateBasicMess = function () {
 top.search.popDialog({
 url: CTX + "/home/goUpdateUserMess", title: "个人信息", width: 600, height: 400,
 onload: function (window) {
 window.instance.loadData();
 }, ondestroy: function (result) {
 instance.loadUser();
 }
 });
 }

 /!**
 * 修改密码
 *!/
 this.updateUserPassword = function () {
 top.search.popDialog({
 url: CTX + "/home/goUpdatePassword", title: "修改密码", width: 500, height: 350,
 onload: function (window) {
 window.instance.loadData({sphone: instance.sphone, susername: instance.susername});
 }, ondestroy: function (result) {
 }
 });
 }

 /!**
 * 重新绑定手机。
 *!/
 this.updatePhone = function () {
 top.search.popDialog({
 url: CTX + "/home/goUpdatePhone", title: "重新绑定", width: 500, height: 350,
 onload: function (window) {
 window.instance.loadData({sphone: instance.sphone, susername: instance.susername, email: instance.semail});
 }, ondestroy: function (result) {
 }
 });
 }

 /!**
 * 更换邮箱
 *!/
 this.updateEmail = function () {
 top.search.popDialog({
 url: CTX + "/home/goUpdateEmail", title: "绑定邮箱", width: 500, height: 350,
 onload: function (window) {
 window.instance.loadData({sphone: instance.sphone, susername: instance.susername, email: instance.semail});
 }, ondestroy: function (result) {
 }
 });
 }

 /!**
 * 注销用户。
 *!/
 this.logout = function () {
 search.confirm({
 content: "确定注销该用户吗?",
 funl: function () {
 window.location = CTX + "/logout";
 }
 });
 }
 }

 // var instance = new mainHome();


 // function nav() {
 //     $(".navbar-renonateright>ul>li").hover(function () {
 //         $(this).children("ul").fadeIn(800);
 //     }, function () {
 //         $(this).children("ul").slideUp(500);
 //     });
 // }
 //
 // function tabs(tabTit, on, tabCon) {
 //     $(tabTit).children().hover(function () {
 //         $(this).addClass(on).siblings().removeClass(on);
 //         var index = $(tabTit).children().index(this);
 //         $(tabCon).children().eq(index).show().siblings().hide();
 //     });
 // };
 */
