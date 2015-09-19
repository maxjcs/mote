/**
 * Created by Feil.Wang on 2015/7/16.
 */
(function (w, $) {
    'use strict';
    $(function () {
        /**
         * 导入教师数据
         */
        $('#importTeacherBtn').click(function () {
            var $btn = $(this);
            var orgId = $btn.data('orgid');
            var d = dialog({
                title: '上传',
                content: '<form id="importTeacherForm" action="' + WEB_SERVER + 'front/organization/importTeacher" method="post" enctype="multipart/form-data">' +
                '<input type="hidden" name="orgId" value="' + orgId + '">' +
                '<input type="file" name="txtFile"></form>',
                cancelValue: '取消',
                cancel: function () {
                },
                okValue: '确定',
                ok: function () {
                    $('#importTeacherForm').submit();
                    return false;
                }

            });
            d.show();
        });
        /**
         * 删除教师
         */
        $('.J_removeBtn').click(function () {
            var $btn = $(this);
            var orgId = $btn.data('orgid');
            var teacherId = $btn.data('teacherid');
            var d = dialog({
                title: '提示',
                content: '确定删除该教师吗？',
                quickClose: true,
                cancelValue: '取消',
                cancel: function () {
                },
                okValue: '确定',
                ok: function () {
                    var tips = dialog().show();
                    $.ajax({
                        method: 'post',
                        dataType: 'json',
                        url: WEB_SERVER + 'front/organization/removeTeacher',
                        data: {
                            orgId: orgId,
                            teacherId: teacherId
                        },
                        success: function (res) {
                            console.log(res);
                            tips.content(res.message);
                            if (res.code == 1) {
                                setTimeout(function () {
                                    w.location.reload()
                                }, 1000);
                            } else {
                                setTimeout(function () {
                                    tips.close().remove();
                                }, 1000);
                            }
                        }
                    });
                }

            });
            d.show();
        });
    });
}(window, jQuery));