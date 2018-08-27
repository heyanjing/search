/**
 * Created by heyanjing on 2018/5/18 16:08.
 */
$('#login').on('click', function () {
    console.log($('#susername').val(),$('#spassword').val(),$('#vcode').val());
    $.ajax({
        url: CTX + "/api/browser/login",
        data: {
            susername: $('#susername').val(),
            spassword: $('#spassword').val(),
            vcode: $('#vcode').val()
        },
        success: function (result) {
            if(result.status){
                Globle.fun.info(result.msg);
            }else{
                Globle.fun.warn(result.msg);
            }
        },
        error: function () {

        }
    });
});