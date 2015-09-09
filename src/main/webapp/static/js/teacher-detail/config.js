/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/5/29. */
(function (W, undefined) {
    'use strict';
    var WEB_SERVER = '';
    var API_SERVER = '';
    var hostname = W.location.hostname;
    var port = W.location.port == '' ? W.location.port : (':' + W.location.port);
    var _context = window.location.pathname.split('/')[1];
    var context = _context == 'teacherDetail' ? '' : ('/' + _context);
    var ENV = {
        DEV: '192.168.10.7',
        TEST: '192.168.1.187',
        ONLINE: 'teacher.91xuexibao.com'
    };

    switch (hostname) {
        case ENV.DEV || 'localhost':
            WEB_SERVER = 'http://' + hostname + port + context;
            API_SERVER = 'http://' + hostname + port + context;
            break;
        case ENV.TEST:
            WEB_SERVER = 'http://' + hostname + port + context;
            API_SERVER = 'http://' + hostname + port + context;
            break;
        case ENV.ONLINE:
            WEB_SERVER = 'http://' + hostname + port;
            API_SERVER = 'http://' + hostname + port + '/teacher';
            break;
    }
    W.WEB_SERVER = WEB_SERVER;
    W.API_SERVER = API_SERVER;
}(window, undefined));

