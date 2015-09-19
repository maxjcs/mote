/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/9/17. */
(function (w, $, d) {
    'use strict';
    /**
     * 批量下架音频异步请求
     * @param formData
     * @param cb
     */
    var httpOfflineAudio = function (formData, cb) {
        $.ajax({
            method: 'POST',
            dataType: 'json',
            url: WEB_SERVER + 'front/audioLibs/batOfflineAudio',
            data: formData,
            success: function (res) {
                console.log(res);
                if (res.success) {
                    cb();
                } else {
                    var error = dialog({content: '下架失败！'}).show();
                    setTimeout(function () {
                        error.close().remove();
                    }, 800);
                }
            }
        });
    };
    $(function ($) {
        var btnDisabel = false;
        $("#exportExcel").click(function () {
            if (!btnDisabel) {
                btnDisabel = true;
                var formData = $('#searchForm').serializeArray();
                var tips = d().show();
                $.ajax({
                    url: WEB_SERVER + "front/audioLibs/export",
                    method: 'POST',
                    dataType: 'json',
                    data: formData,
                    success: function (resp) {
                        console.log(resp);
                        if (resp.code == 1) {
                            var relative = resp.data;
                            window.location.href = WEB_SERVER + relative;
                            tips.content(resp.message);
                            setTimeout(function () {
                                tips.close().remove();
                            }, 1000);
                        } else {
                            tips.content(resp.message);
                            setTimeout(function () {
                                tips.close().remove();
                            }, 1000);
                        }
                        btnDisabel = false;
                    }
                });
            }
            return false;
        });

        /**
         * 全选操作
         */
        var $checkboxList = $("input[name='checkbox-item']");
        $("#selectAll").click(function () {
            var _$checkboxList = $("input[name='checkbox-item']");
            var isChecked = $(this).prop("checked");
            _$checkboxList.each(function () {
                $(this).prop("checked", isChecked);
            });
        });
        $checkboxList.click(function () {
            var ckbListLen = $checkboxList.filter(":checked").length;
            var isSelectedAll = $checkboxList.length == ckbListLen;
            $("#selectAll").prop("checked", isSelectedAll);
        });

        /**
         * 批量下架
         */
        $('#batchOfflineBtn').on('click', function () {
            var selectedArr = [];
            $checkboxList.filter(':checked').each(function () {
                var id = $(this).val();
                selectedArr.push(id);
            });
            var ids = selectedArr.join(',');
            var num = selectedArr.length;
            if (num == 0) {
                var warning = dialog({
                    content: '未选中任何音频！'
                }).show();
                setTimeout(function () {
                    warning.close().remove();
                }, 800)
            } else {
                var d = dialog({
                    title: '音频下架',
                    width: '300px',
                    content: '<form id="offlineDailog">\
                            <div class="form-group">即将下架<span class="text-danger">' + num + '</span>条讲解，请填写下架原因：</div>\
                            <input type="hidden" value="' + ids + '" name="ids">\
                            <div class="form-group">\
                                <textarea class="form-control" rows="5" name="reason" placeholder="下架原因会展示给老师，请详细填写，最多30个字"></textarea>\
                            </div>\
                        </form>',
                    onshow: function () {
                        $('#offlineDailog').validate({
                            rules: {
                                reason: {
                                    required: true,
                                    maxlength: 30
                                }
                            },

                            messages: {
                                reason: {
                                    required: '请输入下架原因',
                                    maxlength: '最多不能超过30个字'
                                }
                            },

                            submitHandler: function (form) {
                                var tips = dialog().show();
                                var formData = $(form).serializeArray();
                                console.log(formData);
                                httpOfflineAudio(formData, function () {
                                    tips.content('操作成功!');
                                    setTimeout(function () {
                                        tips.close().remove();
                                        d.close().remove();
                                        $checkboxList.filter(':checked').each(function () {
                                            var $self = $(this);
                                            $self.closest('tr').find('.J_status').html('<span class="text-danger">下线</span>');
                                            $self.prop('checked', false).remove();
                                        });
                                    }, 800)
                                });
                            }
                        })
                    },
                    okValue: '确定',
                    ok: function () {
                        $("form#offlineDailog").submit();
                        return false;
                    },
                    cancelValue: '取消',
                    cancel: function () {
                    }
                });
                d.showModal();
            }
        });
    });
}(window, jQuery, dialog));
