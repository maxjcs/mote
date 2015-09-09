/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/6/3. */
(function (W, A, $) {
    'use strict';
    var Util = {
        getUrlParam: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        },
        ellipsis: function () {
            $('.other-info li').each(function () {
                var $li = $(this);
                var padding = parseInt($li.css('padding-left')) + parseInt($li.css('padding-right'));
                var emWidth = $li.children('em').width();
                var spanWidth = $li.children('span').width();
                var limitWidth = $li.width() - padding - emWidth;
                if (spanWidth > limitWidth) {
                    $li.addClass('ellipsis hasmore')
                }
            });
        },
        clipWbTxt: function () {
            $('.workbook-list .wb-txt').each(function () {
                var $wrap = $(this);
                var $txt = $wrap.find('span');
                var wrapWidth = $wrap.width();
                var txtWidth = $txt.width();
                var limitWidth = wrapWidth - 20;
                if (txtWidth >= limitWidth) {
                    $txt.width(limitWidth);
                }
            });
        }
    };
    A.filters.centToYuan = function (str) {
        var y = str / 100;
        y = y.toFixed(2);
        return y;
    };
    A.filters.dateDiff = function (dateTimeStamp) {
        var result = '';
        var minute = 1000 * 60;
        var hour = minute * 60;
        var day = hour * 24;
        var month = day * 30;
        var year = month * 12;
        var now = new Date().getTime();
        var diffValue = now - dateTimeStamp;
        var yearC = diffValue / year;
        var monthC = diffValue / month;
        var weekC = diffValue / (7 * day);
        var dayC = diffValue / day;
        var hourC = diffValue / hour;
        var minC = diffValue / minute;
        if (yearC >= 1) {
            result = parseInt(yearC) + "年前";
        }
        if (monthC >= 1) {
            result = parseInt(monthC) + "个月前";
        }
        else if (weekC >= 1) {
            result = parseInt(weekC) + "周前";
        }
        else if (dayC >= 1) {
            result = parseInt(dayC) + "天前";
        }
        else if (hourC >= 1) {
            result = parseInt(hourC) + "小时前";
        }
        else if (minC >= 1) {
            result = parseInt(minC) + "分钟前";
        } else {
            result = "刚刚";
        }
        return result;
    };
    //debug mode
    if (Util.getUrlParam('debug')) {
        W.console = console;
        W.external = (function () {
            var c = {};
            var funs = [
                "goToQRCodeView",
                "workbookDetail",
                "workbookList",
                "hideLoading",
                "errorTips",
                "deleteMessage",
                "goMessageList",
                "goMessageDetail",
                "goToFollowed"
            ];
            for (var i in funs) {
                var fun = funs[i];
                c[fun] = function (e) {
                };
            }
            return c;
        })();
    } else {
        W.console = (function () {
            var c = {};
            var funs = ["log", "debug", "info", "warn", "error"];
            for (var i in funs) {
                var fun = funs[i];
                c[fun] = function (e) {
                };
            }
            return c;
        })();
    }
    W.Util = Util;


})(window, avalon, Zepto);