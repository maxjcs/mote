/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015.08.06 */
(function (W, A, $) {
    'use strict';
    W.vm = A.define({
        $id: 'activityCtrl',
        initialized: false,
        chargetNotice: {},
        listRecharge: [],
        listLastId: 0
    });
    A.scan(document.body);
    document.addEventListener('touchmove', function (e) {
        e.preventDefault();
    }, false);
    /**
     *  异步请求
     */
    var Http = {
        addRecharge: function (phoneNumber, cb) {
            $.ajax({
                method: 'post',
                dataType: 'json',
                data: {
                    token: TOKEN,
                    studentPhoneNumber: phoneNumber
                },
                url: WEB_SERVER + 'api/teacherrecharge/addRecharge',
                success: function (res) {
                    console.log(res);
                    cb(res);
                }
            });
        },
        getChargetNotice: function (cb) {
            $.ajax({
                method: 'post',
                dataType: 'json',
                data: {token: TOKEN},
                url: WEB_SERVER + 'api/teacherrecharge/getChargetNotice',
                success: function (res) {
                    console.log(res);
                    cb(res);
                }
            });
        },
        getListRecharge: function (lastId, cb) {
            $.ajax({
                method: 'post',
                dataType: 'json',
                data: {
                    token: TOKEN,
                    lastId: lastId
                },
                url: WEB_SERVER + 'api/teacherrecharge/listRecharge',
                success: function (res) {
                    console.log(res);
                    cb(res);
                }
            });
        }
    };
    Http.getChargetNotice(function (res) {
        if (res.success) {
            vm.chargetNotice = res.data;
        }
    });
    Http.getListRecharge(0, function (res) {
        if (res.success) {
            vm.listRecharge = res.data;
            vm.listLastId = res.data[res.data.length - 1].id;
        }
    });
    $(function () {
        FastClick.attach(document.body);
        //文字定位
        var windowWidth = $(window).width();
        var barPos = {};
        var mainTop = 345 / 640 * windowWidth;
        var textScale = 1 / 320 * windowWidth;
        barPos.top = 255 / 640 * windowWidth;
        barPos.left = 240 / 640 * windowWidth;
        barPos.right = 44 / 640 * windowWidth;
        $('#titileBar').css({
            'top': barPos.top,
            'left': barPos.left,
            'right': barPos.right,
            '-webkit-transform': 'scale(' + textScale + ') rotate(-4deg)',
            'transform': 'scale(' + textScale + ') rotate(-4deg)'
        });

        $('.main-container').css('top', mainTop);
        W.scroller = new Scroller('#recordScroller', {
            pullToRefresh: true,
            onPullToRefresh: function (callback) {
                Http.getListRecharge(0, function (res) {
                    if (res.success) {
                        vm.listRecharge = res.data;
                        callback(null)
                    }
                });
            },
            pullToLoadMore: true,
            onPullToLoadMore: function (callback) {
                Http.getListRecharge(vm.listLastId, function (res) {
                    if (res.success) {
                        if (res.data.length != 0) {
                            vm.listRecharge = vm.listRecharge.$model.concat(res.data);
                            vm.listLastId = res.data[res.data.length - 1].id;
                            callback(null);
                        } else {
                            callback(null);
                        }
                    }
                });
            }
        });
        vm.initialized = true;
        /**
         * 提交充值
         */
        $('#submitBtn').on('click', function () {
            var phoneNumber = $.trim($('#phoneNumber').val());
            if (phoneNumber == '' || !(/^1[3|4|5|7|8][0-9]{9}$/.test(phoneNumber))) {
                alert('请输入正确的11位手机号！')
            } else {
                Http.addRecharge(phoneNumber, function (res) {
                    alert(res.msg);
                    if (res.success) {

                    } else {

                    }
                });
            }
        })
    });
}(window, avalon, Zepto));