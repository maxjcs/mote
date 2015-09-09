/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/7/13. */
(function (W) {
    'use strict';
    W.addEventListener("touchmove", function (e) {
        e.preventDefault();
    }, false);

    W.onload = function () {
        var viewWidth = document.body.clientWidth;
        var viewHeight = document.body.clientHeight;
        var picHeight, descHeight;
        if (viewHeight <= 500) {
            descHeight = 150;
            picHeight = viewHeight - descHeight;
        } else {
            picHeight = viewWidth * 350 / 320;
            descHeight = viewHeight - picHeight;
        }

        var picContainer = document.querySelectorAll('.guide-pic');
        var slideshow = document.querySelectorAll('.desc-container');
        for (var i = 0; i < picContainer.length; i++) {
            picContainer[i].style.height = picHeight + 'px';
        }
        for (var j = 0; j < slideshow.length; j++) {
            slideshow[j].style.height = descHeight + 'px';
        }
        var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            paginationClickable: true,
            loop: true,
            preloadImages: false,
            lazyLoading: true
        });
    };
}(window));





