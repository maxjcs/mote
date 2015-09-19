/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/8/12. */
(function (w, $) {
    'use strict';
    $(function () {
        $('#modifySettingBtn').click(function () {
            var status, studentRecharge, teacherReCash, contentEl;
            status = $('#status').data('status');
            studentRecharge = $('#studentRecharge').text();
            teacherReCash = $('#teacherReCash').text();
            contentEl = '<form id="modifyForm" class="form-horizontal">' +
                '   <div class="form-group">' +
                '       <label class="control-label col-md-5">活动状态</label>' +
                '       <div class="col-md-6">' +
                '           <select class="form-control" name="statue">' +
                '               <option value="0" ' + (status == 0 ? "selected" : "") + '>关闭</option>' +
                '               <option value="1" ' + (status == 1 ? "selected" : "") + '>开启</option>' +
                '           </select>' +
                '       </div>' +
                '   </div>' +
                '   <div class="form-group">' +
                '       <label class="control-label col-md-5">学生充值（元）</label>' +
                '       <div class="col-md-6">' +
                '           <input class="form-control" name="recharge" value="' + studentRecharge + '">' +
                '       </div>' +
                '   </div>' +
                '   <div class="form-group">' +
                '       <label class="control-label col-md-5">老师返现（元）</label>' +
                '       <div class="col-md-6">' +
                '           <input class="form-control" name="reCash" value="' + teacherReCash + '">' +
                '       </div>' +
                '   </div>' +
                '</form>';
            var d = dialog({
                title: '修改设置',
                content: contentEl,
                okValue: '确定',
                ok: function () {
                    $("form#modifyForm").submit();
                    return false;
                },
                cancelValue: '取消',
                cancel: function () {
                },
                onshow: function () {
                    $("form#modifyForm").validate({
                        rules: {
                            recharge: {
                                required: true,
                                digits: true
                            },
                            reCash: {
                                required: true,
                                digits: true
                            }
                        },

                        messages: {
                            recharge: {
                                required: '请输入充值金额',
                                digits: '请输入数字'
                            },
                            reCash: {
                                required: '请输入返现金额',
                                digits: '请输入数字'
                            }
                        },

                        submitHandler: function (form) {
                            var tips = dialog().show();
                            var formData = $(form).serializeArray();
                            $.ajax({
                                url: WEB_SERVER + 'front/xxxxxxxxxxxxxxxxxxxxxxxxxxxxx',
                                method: 'POST',
                                dataType: 'json',
                                data: formData,
                                success: function (res) {
                                    console.log(res);
                                    tips.content(res.data.message);
                                    if (res.data.code == 1) {
                                        setTimeout(function () {
                                            window.location.reload()
                                        }, 1000)

                                    } else {
                                        setTimeout(function () {
                                            tips.close().remove();
                                        }, 1000)
                                    }
                                }
                            });
                        }
                    });
                }
            }).showModal();
        });
    });
}(window, jQuery));