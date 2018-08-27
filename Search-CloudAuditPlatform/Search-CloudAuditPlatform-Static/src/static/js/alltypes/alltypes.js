//xx

$('#btn1').on('click', function () {
    $.ajax({
        type: 'post',
        dataType: 'json',
        url: CTX + "/alltypes/findBySql",
        data: {},
        success: function (result) {
            console.log(result);
        }
    });
})