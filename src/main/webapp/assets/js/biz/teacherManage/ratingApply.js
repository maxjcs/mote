/**
 * Created by Feil.Wang on 2015/6/29.
 */
(function(W,$){
    function submitRatingApply() {
        var id = $('input[name="id"]').val();
        var radioValue = $('input:radio[name="status"]:checked').val();
        var reasonValue = $('#reason').val();
        if (radioValue === undefined) {
            var tip1 = dialog({id:'tip1',content: '请选择处理意见！'}).show();
            setTimeout(function () {
                tip1.close().remove();
            }, 1500);
            return;
        }
        if (radioValue == 2 && $.trim(reasonValue) == '') {
            var tip2 = dialog({id:'tip2',content: '请输入处理原因！'}).show();
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
                        url: WEB_SERVER + 'front/teacherManage/auditApply',
                        data: {
                            id: id,
                            status: radioValue,
                            content: reasonValue
                        },
                        success: function (data) {
                            console.log(data);
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
        $('#submitApplyButton').click(submitRatingApply);
    });
}(window,jQuery));