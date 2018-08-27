$(function () {
    //登录与二维码切换
    $(".change-login li").on("click", function () {
        var index = $(this).index();
        $(this).addClass("cur").siblings().removeClass("cur");
        // console.log($(".from-tab  div.login").length);
        var lg = $(".from-tab  div.login");
        lg.hide();
        lg.eq(index).show();
        var cur = $('.cur').get(0);
        if (index == 1) {
            cur.style.borderRadius = '0 5px 0 0';
        } else {
            cur.style.borderRadius = '5px 0 0 0';
        }


    });
    //获取手机验证码
    $('#phonecode').on('click', function () {
        $.ajax({
            url: CTX + "/sendPhoneVcode",
            data: {userName: $('#userName').val()},
            success: function (result) {
                console.log(result);
                if (result.msg) {
                    $('#errorMsg').html(result.msg);
                }
            },
            error: function () {

            }
        });
    })
    setInterval(function () {
        var qr = $('#qr');
        var milliseconds = Globle.fun.format(new Date(), "YYYYMMDDHHmmss");
        qr.attr('src', qr.data('src') + '?' + milliseconds);
        // console.log(qr.data('src'), milliseconds, qr.attr('src'))
    }, 170000);
    // 定时请求二维码的授权登录信息
    setInterval(function () {
        // console.log("setTimeout")
        search.manualShowLoading(true);
        $.ajax({
            url: CTX + "/api/pc/getUserInfoBeanByVcode",
            data: {
            },
            success: function (result) {
                console.warn(result);
                if (result.status) {
                    var data = result.result, susername = data.susername, spassword = data.spassword;
                    var loginForm = $('#loginForm'), userName = $('#userName'), password = $('#password');
                    userName.val(susername);
                    password.val(spassword);
                    $('#submit').trigger('click');
                    // loginForm.submit();
                }

            },
            error: function (xhr) {

            }
        });
    }, 1000);
    // setTimeout(function () {
    //     var  susername ="admin", spassword = "admin";
    //     var loginForm = $('#loginForm'), userName = $('#userName'), password = $('#password');
    //     userName.val(susername);
    //     password.val(spassword);
    //     // loginForm.submit();
    //     $('#submit').trigger('click');
    //     console.log('提交完成')
    // }, 10000);
});
