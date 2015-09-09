/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/8/17. */
(function (W, A, $) {
    'use strict';
    W.vm = A.define({
        $id: 'dynamicCtrl',
        defaultAvatarUrl: WEB_SERVER + '/static/images/default-avatar@2x.png',
        initialized: false,
        dynamicData: {},
        listMessage: []
    });
    A.scan(document.body);
    var Http = {
        getDynamicInfo: function (id, cb) {
            $.post(WEB_SERVER + 'api/dynamic/loadDynamicForH5',
                {id: id},
                function (res) {
                    console.log('getDynamicInfo:', res);
                    if (res.success) {
                        vm.dynamicData = res.data;
                        $('title').text(res.data.teacherNickname+'老师的新动态');
                        vm.initialized = true;
                        if (typeof cb == 'function')cb();
                    }
                }, 'json')
        },
        getListMessage: function (id, cb) {
            $.post(WEB_SERVER + 'api/dynamicmessage/listMessageForH5',
                {dynamicId: id},
                function (res) {
                    console.log('getListMessage:', res);
                    if (res.success) {
                        vm.listMessage = res.data;
                        if (typeof cb == 'function')cb();
                    }
                }, 'json')
        }
    };
    Http.getDynamicInfo(DYNAMIC_ID);
    Http.getListMessage(DYNAMIC_ID);
    $(function () {
        $(document.body).on('click', '.app-dl-link', function () {
            W.location.href = 'http://www.xuexibao.cn/html/download.html';
        });
    })
}(window, avalon, Zepto));