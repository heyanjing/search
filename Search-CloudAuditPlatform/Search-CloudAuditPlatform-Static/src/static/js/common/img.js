/**
 * Created by heyanjing on 2018/4/13 16:20.
 */
$(function () {
    Img.init();
});
var Img = {

    rotation: function (deg) {
        console.log(deg);
        var me = this;
        Img.deg += deg;
        if (me.supportTransition) {
            deg = 'rotate(' + Img.deg + 'deg)';
            me.img.css({
                '-webkit-transform': deg,
                '-mos-transform': deg,
                '-o-transform': deg,
                'transform': deg
            });
        } else {
            me.img.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((Img.deg / 90) % 4 + 4) % 4) + ')');
        }
    },
    mouseWheel: function (e) {
        var $img = $('#img');
        e.preventDefault();
        var e = window.event || e;
        var delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));
        $img.css('width', Math.max(160, Math.min(10000, $img.width() + (30 * delta))) + "px");
    },
    event: function () {
        var me = this, $html = $('html');
        $html.on(
            {
                mousewheel: me.mouseWheel,
                DOMMouseScroll: me.mouseWheel,
                onmousewheel: me.mouseWheel,
                // mousewheel: function () {
                //     console.log("mousewheel");
                // },
                // DOMMouseScroll: function () {
                //     console.log("DOMMouseScroll");
                // },
                // onmousewheel: function () {
                //     console.log("onmousewheel");
                // },
                click: function () {
                    console.log(111);
                }
            }
        );
    },
    init: function () {
        var me = this;
        Img['supportTransition'] = (function () {
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                    'WebkitTransition' in s ||
                    'MozTransition' in s ||
                    'msTransition' in s ||
                    'OTransition' in s;
            s = null;
            return r;
        })();
        Img['img'] = $('#img');
        Img['deg'] = 0;
        me.event();
    }
}