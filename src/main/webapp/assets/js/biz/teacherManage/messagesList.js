/**
 * Created by Feil.Wang on 2015/8/3.
 */
(function (W, $) {
    'use strict';
    /**
     * 删除留言异步请求
     * @param msgIds
     * @param cb
     */
    var httpRemoveMessage = function (msgIds, cb) {
        $.ajax({
            method: 'POST',
            dataType: 'json',
            url: WEB_SERVER + 'front/teacherManage/removeMessage',
            data: {
                ids: msgIds
            },
            success: function (res) {
                console.log(res);
                if (res.success) {
                    cb();
                }else{
                    var error = dialog({content:'删除失败！'}).show();
                    setTimeout(function () {
                        error.close().remove();
                    },800);
                }
            }
        });
    };


    $(function () {
        /**
         * 单个删除
         */
        $('.J_del-msg-btn').on('click', function () {
            var $btn = $(this);
            var id = $btn.data('id');
            var d = dialog({
                title: '提示',
                content: '确定删除该条留言？',
                padding: '30px 50px',
                okValue: '确定',
                ok: function () {
                    var tips = dialog().show();
                    httpRemoveMessage(id, function () {
                        tips.content('删除成功!');
                        setTimeout(function () {
                            tips.close().remove();
                            $btn.closest('tr').find('input[type=checkbox]').remove();
                            $btn.after('<span>已删除</span>').remove();
                        }, 800)
                    });
                },
                cancelValue: '取消',
                cancel: function () {
                }
            });
            d.showModal();

        });
        /**
         * 全选操作
         */
        var $checkboxList = $("input[name='checkbox-item']").not(":disabled");
        $("#selectAll").click(function () {
            var $checkboxList = $("input[name='checkbox-item']").not(":disabled");
            var isChecked = $(this).prop("checked");
            $checkboxList.each(function () {
                $(this).prop("checked", isChecked);
            });
        });
        $checkboxList.click(function () {
            var ckbListLen = $checkboxList.filter(":checked").length;
            var isSelectedAll = $checkboxList.length == ckbListLen;
            $("#selectAll").prop("checked", isSelectedAll);
        });

        /**
         * 批量删除
         */
        $('#batchRemoveBtn').on('click', function () {
            var selectedArr = [];
            var ids = '';
            $checkboxList.filter(':checked').each(function () {
                var id = $(this).val();
                selectedArr.push(id);
            });
            ids = selectedArr.join(',');
            if (selectedArr.length == 0) {
                var warning = dialog({
                    content:'未选中任何留言！'
                }).show();
                setTimeout(function () {
                    warning.close().remove();
                },800)
            } else {
                var d = dialog({
                    title: '提示',
                    content: '确定批量删除这些留言？',
                    padding: '30px 50px',
                    okValue: '确定',
                    ok: function () {
                        var tips = dialog().show();
                        httpRemoveMessage(ids, function () {
                            tips.content('删除成功!');
                            setTimeout(function () {
                                tips.close().remove();
                                $checkboxList.filter(':checked').each(function () {
                                    var $self = $(this);
                                    $self.closest('tr').find('.J_del-msg-btn').after('<span>已删除</span>').remove();
                                    $self.prop('disabled', true).remove();
                                });
                            }, 800)
                        });
                    },
                    cancelValue: '取消',
                    cancel: function () {
                    }
                });
                d.showModal();
            }
        });
    });

})(window, jQuery);