/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/6/29. */
(function (W, A, $) {
    'use strict';
    var teacherId = Util.getUrlParam('teacherId');
    W.root = A.define({
        $id: 'root',
        pageTitle: '',
        defaultAvatarUrl: WEB_SERVER + '/static/images/default-avatar@2x.png',
        initialized: false
    });
    W.teacherHome = A.define({
        $id: "teacherHome",
        teacherId: teacherId,
        teacherInfo: {},
        showMore: function () {
            var $li = $(this);
            if ($li.hasClass('hasmore')) {
                $li.toggleClass('ellipsis')
            }
        },
        goToQRCodeView: function () {
            console.log("跳转到二维码页");
            W.external.goToQRCodeView()
        },
        goToFollowed:function(){
            console.log("跳转到关注我的学生页");
            W.external.goToFollowed()
        }
    });
    W.workbook = A.define({
        $id: 'workbook',
        wbList: [],
        wbItemDetail: function (id) {
            console.log('跳转到习题集详情：', id);
            W.external.workbookDetail(id);
        },
        pageNo: 0,
        /** 加载更多习题集 */
        moreWorkbook: function () {
            workbook.pageNo++;
            Http.getWorkBookList(workbook.pageNo, function (res) {
                workbook.wbList = workbook.wbList.concat(res.data);
                console.log('加载更多习题集：', workbook.wbList);
            });
        },
        goWbList: function () {
            console.log('跳转到习题集列表');
            W.external.workbookList();
        }
    });
    W.guessbook = A.define({
        $id: 'guessbook',
        gbCount: '',
        gbReplyer: '',
        gbList: [],
        deleteMessage: function (e, id) {
            e.stopPropagation();
            console.log('删除留言：id=', id);
            W.external.deleteMessage(id)
        },
        goMessageList: function () {
            console.log('进入留言列表页');
            W.external.goMessageList();
        },
        goMessageDetail: function (id) {
            console.log('进入该条留言详情页：id=', id);
            W.external.goMessageDetail(id);
        }
    });

    A.scan(document.body);

    /**异步请求**/
    var Http = {
        /** 获取老师基本信息 */
        getTeacherInfo: function () {
            $.ajax({
                method: 'post',
                dataType: 'json',
                data: {teacherId: teacherId},
                url: API_SERVER + '/api/user/getTeacherById',
                success: function (res) {
                    console.log('基本信息：', res);
                    if (res.success) {
                        root.initialized = true;
                        if (!res.data) {
                            $(document.body).html('<div class="error-layer"><p>该教师不存在！</p></div>');
                        } else {
                            root.pageTitle = res.data.isOrg ? res.data.nickname : (res.data.nickname + '老师');
                            teacherHome.teacherInfo = res.data;
                            Util.ellipsis();
                        }
                        W.external.hideLoading();
                    } else {
                        W.external.errorTips('基本信息加载失败！');
                    }
                }
            });
        },
        /**
         * 获取习题集
         * @param pageNo
         * @param callback
         */
        getWorkBookList: function (pageNo, callback) {
            $.ajax({
                method: 'post',
                dataType: 'json',
                url: API_SERVER + '/api/audioset/listAudioSetByTeacherId',
                data: {
                    teacherId: teacherId,
                    pageNo: pageNo
                },
                success: function (res) {
                    console.log('习题集：', res);
                    if (res.success) {
                        callback(res);
                    } else {
                        W.external.errorTips('习题集加载失败！');
                    }
                }
            });
        },
        /** 获取留言 */
        getLeaveMessages: function (callback) {
            $.ajax({
                method: 'post',
                dataType: 'json',
                url: API_SERVER + '/api/teacherMessage/summaryTeacherMessageForH5',
                data: {
                    teacherId: teacherId
                },
                success: function (res) {
                    console.log('留言：', res);
                    if (res.success) {
                        callback(res);
                    } else {
                        W.external.errorTips('留言加载失败！');
                    }
                }
            });
        }
    };
    //获取教师信息
    Http.getTeacherInfo();
    //获取留言
    Http.getLeaveMessages(function (res) {
        guessbook.gbCount = res.data.countMessage;
        guessbook.gbReplyer = res.data.countReplyer;
        guessbook.gbList = res.data.messages;
    });
    //获取习题集列表
    Http.getWorkBookList(0, function (res) {
        workbook.wbList = res.data;
        Util.clipWbTxt();
    });

    /**
     * 刷新留言
     */
    function refreshMessage() {
        Http.getLeaveMessages(function (res) {
            guessbook.gbCount = res.data.countMessage;
            guessbook.gbReplyer = res.data.countReplyer;
            guessbook.gbList = res.data.messages;
        });
    }

    /**
     * 外部调用接口
     * @param type
     * @param data
     * @constructor
     */
    W.jsInvoke = function (type, data) {
        console.log(type, ':', data);
        switch (type) {
            case "refreshMessage"://刷新留言
                refreshMessage(data);
                break;
            default:
                console.warn("externalMessage: type -" + type + " is unknown", data);
        }
    };

    //document ready
    $(function () {
        W.external.hideLoading();
    });
}(window, avalon, Zepto));