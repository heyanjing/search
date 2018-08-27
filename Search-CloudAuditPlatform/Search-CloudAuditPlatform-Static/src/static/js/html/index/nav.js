$(function(){
    // nav收缩展开
    $('.nav-item>a').on('click',function(){
        if (!$('.nav').hasClass('nav-mini')) {
            if ($(this).next().css('display') == "none") {
                //展开未展开
                $('.nav-item').children('ul').slideUp(300);
                $(this).next('ul').slideDown(300);
                $(this).parent('li').addClass('nav-show').siblings('li').removeClass('nav-show');
            }else{
                //收缩已展开
                $(this).next('ul').slideUp(300);
                $('.nav-item.nav-show').removeClass('nav-show');
            }
        }
    });
    //nav-mini切换
    $('#mini').on('click',function(){
        if (!$('.nav').hasClass('nav-mini')) {
            $('.nav-item.nav-show').removeClass('nav-show');
            $('.nav-item').children('ul').removeAttr('style');
            $('.nav').addClass('nav-mini');
			$('.nav-item').addClass('nav-minione');
			$('.logo').addClass('logo-minimenu');
			$('.logo-mini').addClass('logo-mini-show');
			$('.logo-gl').addClass('logo-gl-hide');
			$('.right').addClass('logo-mini-right');
        }else{
            $('.nav').removeClass('nav-mini');
			$('.nav-item').removeClass('nav-minione');
			$('.logo').removeClass('logo-minimenu');
			$('.logo-mini').removeClass('logo-mini-show');
			$('.logo-gl').removeClass('logo-gl-hide');
			$('.right').removeClass('logo-mini-right');
        }
   });
   $('.fa-gear').on('click',function(){
	   $('.top-linkstab').show();
	   });
});

$(document).ready(function(){
	nav()
});
function nav(){
	$(".navbar-renonateright>ul>li").hover(function(){
		$(this).children("ul").fadeIn(800);
	},function(){
		$(this).children("ul").slideUp(500);
	});
}