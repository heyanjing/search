$(function () {
    $(".change-login li").on("click", function () {
        var index = $(this).index();
        $(this).addClass("cur").siblings().removeClass("cur");
        console.log($(".from-tab  div.login").length);
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
    $('#phonecode').on('click', function () {
        $.ajax({
            url: CTX + "/sendPhoneVcode",
            data: {userName: $('#userName').val()},
            success: function (result) {
                console.log(result);
            },
            error: function () {

            }
        });
    })


});
