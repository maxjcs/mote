/**
 * Created by Feil.Wang on 2015/6/16.
 */
(function (W, $) {
    'use strict';
    function recommTeacher(oldPhoneNum) {
        var dlg = dialog({
            title: '更换推荐老师',
            width: '400px',
            content: '<form class="form-group form-horizontal" id="recommTeacherForm">' +
            '<input type="hidden" name="oldPhoneNumber" value="' + oldPhoneNum + '">' +
            '<label class="control-label col-lg-3">手机号：</label>' +
            '<div class="col-lg-8"><input id="newPhone" class="form-control" name="newPhoneNumber" type="text"></div>' +
            '</form>',
            cancelValue: '取消',
            cancel: function () {
            },
            okValue: '更换',
            ok: function () {
                $('#recommTeacherForm').submit();
                return false;
            }
        });
        dlg.addEventListener('show', function () {
            $("form#recommTeacherForm").validate({
                rules: {
                    newPhoneNumber: {
                        required: true,
                        digits:true,
                        rangelength:[11,11]
                    }
                },

                messages: {
                    newPhoneNumber: {
                        required: '请输入手机号',
                        digits:'请输入正确的手机号',
                        rangelength:'手机号为11位'
                    }
                },

                submitHandler: function (form) {
                    var tips = dialog().show();
                    var formData = $(form).serializeArray();
                    $.ajax({
                        method: 'POST',
                        dataType: 'json',
                        url: WEB_SERVER + 'front/teacherManage/addRecommTeacher',
                        data: formData,
                        success: function (res) {
                            console.log(res);
                            if (res.code==1) {
                                tips.content('更换成功');
                                setTimeout(function () {
                                    window.location.reload()
                                }, 100);
                            }else{
                                tips.content(res.message);
                                setTimeout(function () {
                                    tips.close().remove()
                                }, 1500);
                            }
                        }
                    });
                }
            });
        });
        dlg.showModal();
    }

    W.recommTeacher = recommTeacher;
}(window, jQuery));