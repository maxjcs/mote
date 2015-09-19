/**
 * Created by Feil.Wang on 2015/4/24.
 */
(function(W,$){
    function submitApproval() {
        var radioValue = $('input:radio[name="evaluate"]:checked').val();
        var reasonValue = $('#reason').val();
        if (radioValue === undefined) {
            var tip1 = dialog({content: '请选择音频评价！'}).show();
            setTimeout(function () {
                tip1.close().remove();
            }, 1500);
            return;
        }
        if (radioValue == 3 && $.trim(reasonValue) == '') {
            var tip2 = dialog({content: '请输入差评评价意见！'}).show();
            setTimeout(function () {
                tip2.close().remove();
            }, 1500);
            return;
        }
        if (reasonValue.length > 30) {
            var tip3 = dialog({content: '评价意见不能超过30个字！'}).show();
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
                if(submitFlag){
                    submitFlag = false;
                    var tip4 = dialog().show();
                    $.ajax({
                        method: 'post',
                        dataType: 'json',
                        url: WEB_SERVER + 'front/everydayTask/addAudioApprove',
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
    $(function () {
        $('#submitApprovalButton').click(submitApproval);
    });
}(window,jQuery));
