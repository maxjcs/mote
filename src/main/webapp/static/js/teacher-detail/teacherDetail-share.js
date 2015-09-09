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
        }
    });
    W.workbook = A.define({
        $id: 'workbook',
        wbList: [],
        pageNo: 0,
        /** 加载更多习题集 */
        moreWorkbook: function () {
            workbook.pageNo++;
            Http.getWorkBookList(workbook.pageNo, function (res) {
                workbook.wbList = workbook.wbList.concat(res.data);
                console.log('加载更多习题集：', workbook.wbList);
            });
        }
    });
    W.guessbook = A.define({
        $id: 'guessbook',
        gbCount: '',
        gbReplyer: '',
        gbList: [],
        pageNo: 1,
        isLoading: false,
        isLastPage: false,
        moreLeaveMsg: function () {
            if (!guessbook.isLoading) {
                guessbook.isLoading = true;
                guessbook.pageNo++;
                Http.getLeaveMessages(guessbook.pageNo, function (res) {
                    var pageTotal = Math.ceil(res.data.teacherMessageCount / 10);
                    console.log('当前页：', guessbook.pageNo, '总页数：', pageTotal);
                    if (guessbook.pageNo == pageTotal) {
                        guessbook.isLastPage = true;
                    }
                    var oldData = guessbook.gbList;
                    var newData = res.data.teacherMessage;
                    var spliceNewData = res.data.teacherMessage;
                    for (var i = 0; i < newData.length; i++) {
                        for (var j = 0; j < oldData.length; j++) {
                            if (oldData[j].id == newData[i].id) {
                                spliceNewData.splice(i, 1);
                            }
                        }
                    }
                    guessbook.gbList.pushArray(spliceNewData);
                    guessbook.isLoading = false;
                });
            }
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
                    } else {
                        alert('基本信息加载失败！');
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
                        alert('习题集加载失败！');
                    }
                }
            });
        },
        /** 获取留言 */
        getLeaveMessages: function (pageNo, callback) {
            $.ajax({
                method: 'post',
                dataType: 'json',
                url: API_SERVER + '/api/teacherMessage/summaryTeacherMessageForH5',
                data: {
                    teacherId: teacherId,
                    pageNo: pageNo
                },
                success: function (res) {
                    console.log('留言：', res);
                    if (res.success) {
                        callback(res);
                    } else {
                        alert('留言加载失败！');
                    }
                }
            });
        }
    };
    //获取教师信息
    Http.getTeacherInfo();
    //获取留言
    Http.getLeaveMessages(1, function (res) {
        guessbook.gbCount = res.data.countMessage;
        guessbook.gbReplyer = res.data.countReplyer;
        guessbook.gbList = res.data.messages;
        if (guessbook.gbCount <= 10) {
            guessbook.isLastPage = true;
        }
    });
    //获取习题集列表
    Http.getWorkBookList(0, function (res) {
        workbook.wbList = res.data;
        Util.clipWbTxt();
    });

    $(function () {
        $(document.body).on('click', '.app-dl-link', function () {
            W.location.href = 'http://www.xuexibao.cn/html/download.html';
        });
    })
}(window, avalon, Zepto));
