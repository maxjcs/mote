/**
 * Created by Feil.Wang on 2015/4/14.
 */

(function (w, $) {
    w.everydayTaskSetting = function () {
        var $everydayTaskAnswerNum = $('#everydayTaskAnswerNum');
        var $completeTaskReward = $('#completeTaskReward');
        var dlg = dialog({
            title: '修改设置',
            width: '400px',
            content: '<div class="form-horizontal">' +
            '   <div class="form-group">' +
            '       <label class="col-sm-5 control-label">每日任务录题数量</label>' +
            '       <div class="col-sm-6"><input id="everyDayQuestionNum" type="text" class="form-control" value="' + $everydayTaskAnswerNum.text() + '"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-5 control-label">完成任务奖励积分</label>' +
            '       <div class="col-sm-6"><input id="completeTaskReturnPoint" type="text" class="form-control" value="' + $completeTaskReward.text() + '"></div>' +
            '   </div>' +
            '</div>',
            okValue: '确定',
            ok: function () {
                var tips = dialog();
                tips.show();
                var that = this;
                var everyDayQuestionNum = $('#everyDayQuestionNum').val();
                var completeTaskReturnPoint = $('#completeTaskReturnPoint').val();
                tips.show();
                $.ajax({
                    url: WEB_SERVER + 'front/teacherSetting/saveEveryDayTaskConfig',
                    method: 'POST',
                    dataType: 'json',
                    data: {
                        'everyDayQuestionNum': everyDayQuestionNum,
                        'completeTaskReturnPoint': completeTaskReturnPoint
                    },
                    success: function (data) {
                        tips.content(data['message']);
                        var returnEveryDayQuestionNum = data['data']['everyDayQuestionNum'];
                        var returnCompleteTaskReturnPoint = data['data']['completeTaskReturnPoint'];
                        $everydayTaskAnswerNum.text(returnEveryDayQuestionNum);
                        $completeTaskReward.text(returnCompleteTaskReturnPoint);
                        that.close().remove();
                        setTimeout(function () {
                            tips.close().remove();
                        }, 1500)
                    },
                    error: function (xhr, status, error) {
                        tips.content('修改失败');
                        setTimeout(function () {
                            tips.close().remove();
                        }, 1500)
                    }
                });
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        dlg.showModal();
    }
}(window, jQuery));

