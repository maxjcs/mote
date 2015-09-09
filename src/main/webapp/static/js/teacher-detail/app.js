/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/7/3. */
(function (W, A, $) {
    'use strict';
    var version;
    if (Util.getUrlParam('version') == null || Util.getUrlParam('viewMode') == null) {
        version = 'share';
    }
    if (Util.getUrlParam('viewMode') == '1') {
        version = 'v1.2';
    }
    if (Util.getUrlParam('version') == '1.3') {
        version = 'v1.3';
    }
    if (Util.getUrlParam('version') == '1.4') {
        version = 'v1.4';
    }
    W.viewMode = Util.getUrlParam('viewMode');

    W.template = A.define({
        $id: 'templateCtrl',
        version: version
    });
    W.version = version;
    $(function(){
        FastClick.attach(document.body);
    });
}(window, avalon, Zepto));