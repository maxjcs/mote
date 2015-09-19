/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/8/27. */
(function (w, $, d) {
    'use strict';
    function modifySalaryStatus() {
        d({
            title: '修改发放状态',
            width: '350px',
            content: '<form class="form-horizontal">\
                    <div class="form-group">\
                        <label class="col-sm-4 control-label">状态修改为：</label>\
                        <div class="col-sm-8 form-control-txt">已发放</div>\
                    </div>\
                    <div class="form-group">\
                        <label class="col-sm-4 control-label">修改对象：</label>\
                        <div class="col-sm-8"><textarea id="teacherPhoneList" class="form-control" rows="10" placeholder="请输入老师手机号，每行一个"></textarea></div>\
                    </div>\
                </form>',
            okValue: '修改',
            ok: function () {
                var phoneList = $('#teacherPhoneList').val();
                phoneList = phoneList.replace(/\n/g, ',').replace(/\s/g, '');
                var tips = dialog().show();
                $.ajax({
                    url: WEB_SERVER + 'front/revenueManage/updatePaidList',
                    method: 'POST',
                    dataType: 'json',
                    data: {
                        paidTeacherIdList: phoneList
                    },
                    success: function (res) {
                        tips.content(res.msg);
                        if (res.success) {
                            setTimeout(function () {
                                w.location.reload();
                            }, 1500)
                        } else {
                            setTimeout(function () {
                                tips.close().remove();
                            }, 1500)
                        }

                    }
                });
                return false;
            },
            cancelValue: '取消',
            cancel: function () {

            }

        }).showModal();
    }

    function rewardOrDeduct() {
        var teacherId = $(this).data('teacherid');
        d({
            title: '设置奖惩',
            width: '350px',
            content: '<form id="rewardOrDeductForm" class="form-horizontal">\
                    <input type="hidden" name="teacherId" value="' + teacherId + '"> \
                    <input type="hidden" name="payMonth" value="' + payMonth + '">\
                    <div class="form-group">\
                        <label class="col-sm-2 control-label"></label>\
                        <div class="col-sm-10">\
                            <label class="radio-inline">\
                                <input type="radio" name="rewardDeduct" value="1"> 奖励\
                            </label>\
                            <label class="radio-inline">\
                                <input type="radio" name="rewardDeduct" value="2"> 惩罚\
                            </label>\
                            <div class="error-tips"></div>\
                        </div>\
                    </div>\
                    <div class="form-group">\
                        <label class="col-sm-2 control-label">金额</label>\
                        <div class="col-sm-10"><input class="form-control" name="price" placeholder="单位（元）"></div>\
                    </div>\
                    <div class="form-group">\
                        <label class="col-sm-2 control-label">原因</label>\
                        <div class="col-sm-10"><textarea class="form-control" name="description" placeholder="最多150个字" rows="5"></textarea></div>\
                    </div>\
                </form>',
            onshow: function () {
                $('#rewardOrDeductForm').validate({
                    errorPlacement: function (error, element) {
                        if (element[0].name == 'rewardDeduct') {
                            error.appendTo(element.parent().siblings('.error-tips'));
                        } else {
                            error.insertAfter(element)
                        }
                    },
                    rules: {
                        rewardDeduct: {
                            required: true
                        },
                        price: {
                            required: true,
                            number: true,
                            min: 0
                        },
                        description: {
                            required: true,
                            maxlength: 150
                        }
                    },
                    messages: {
                        rewardDeduct: {
                            required: '请选择奖惩'
                        },
                        price: {
                            required: '请输入金额',
                            number: '金额必须为数字',
                            min: '金额必须大于0'

                        },
                        description: {
                            required: '请输入奖惩原因',
                            maxlength: '最多不超过150个字符'
                        }
                    },
                    submitHandler: function (form) {
                        var tips = dialog().show();
                        var formData = $(form).serializeArray();
                        formData[3].value = formData[3].value * 100;
                        $.ajax({
                            url: WEB_SERVER + 'front/revenueManage/rewardOrDeduct',
                            method: 'POST',
                            dataType: 'json',
                            data: formData,
                            success: function (res) {
                                tips.content(res.messages);
                                if (res.success) {
                                    setTimeout(function () {
                                        w.location.reload();
                                    }, 1500)
                                } else {
                                    setTimeout(function () {
                                        tips.close().remove();
                                    }, 1500)
                                }

                            }
                        });
                    }
                });
            },
            okValue: '确定',
            ok: function () {
                $("#rewardOrDeductForm").submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {

            }
        }).showModal();
    }

    $(function () {
        $('#modifyStatusBtn').on('click', modifySalaryStatus);
        $('.J_rewardOrDeduct').on('click', rewardOrDeduct);
    })
}(window, jQuery, dialog));