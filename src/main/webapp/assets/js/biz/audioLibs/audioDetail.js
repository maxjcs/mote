/**
 * Created by Feil.Wang on 2015/4/24.
 */
(function (W, $) {
    $(function () {
        $('#unShelveAudioBtn').click(unShelveAudio);
        $('#submitApprovalButton').click(submitApproval);
    });
    function unShelveAudio() {
        var preventReClick = true;
        var d = dialog({
            title: '操作',
            content: '确定要执行此操作吗？',
            okValue: '确定',
            ok: function () {
                var that = this;
                var tips = dialog().show();
                if (preventReClick) {
                    preventReClick = false;
                    $.ajax({
                        method: 'post',
                        dataType: 'json',
                        url: WEB_SERVER + 'front/audioLibs/offlineAudio',
                        data: {audioId: audioId},
                        success: function (res) {
                            that.close().remove();
                            tips.content('操作成功！');
                            setTimeout(function () {
                                window.location.reload();
                            }, 500)
                        }
                    });
                }
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        d.showModal();
    }

    function submitApproval() {
        var radioValue = $('input:radio[name="approveStatus"]:checked').val();
        var reasonValue = $('#content').val();
        if (radioValue === undefined) {
            var tip1 = dialog({content: '请选择复审意见！'}).show();
            setTimeout(function () {
                tip1.close().remove();
            }, 1500);
            return;
        }
        if (radioValue == 1 && $.trim(reasonValue) == '') {
            var tip2 = dialog({content: '请输入复审意见！'}).show();
            setTimeout(function () {
                tip2.close().remove();
            }, 1500);
            return;
        }
        if (reasonValue.length > 30) {
            var tip3 = dialog({content: '复审意见不能超过30个字！'}).show();
            setTimeout(function () {
                tip3.close().remove();
            }, 1500);
            return;
        }
        var submitFlag = true;
        var confirmDlg = dialog({
            title: '提示',
            content: '确定提交？',
            okValue: '确定',
            ok: function () {
                if (submitFlag) {
                    submitFlag = false;
                    var tip4 = dialog().show();
                    $.ajax({
                        method: 'post',
                        dataType: 'json',
                        url: WEB_SERVER + 'front/audioLibs/upateAudioEvalApprove',
                        data: {
                            audioId: audioId,
                            evaluate: radioValue,
                            reason: reasonValue
                        },
                        success: function (data) {
                            tip4.content(data.message);
                            setTimeout(function () {
                                window.location.reload()
                            }, 1500);
                        }
                    });
                }
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        confirmDlg.showModal()
    }
}(window, jQuery));
