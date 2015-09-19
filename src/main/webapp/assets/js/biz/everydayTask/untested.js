/**
 * Created by Feil.Wang on 2015/4/24.
 */
(function (W, $) {
    function rewardIntegral() {
        var d = dialog({
            title: '奖励积分',
            padding: '20px 20px 20px 40px',
            content: '<div class="form-inline">' +
            '   <div class="form-group">' +
            '       <label class="control-label">老师当前积分：'+teacherPoint+'</label>' +
            '   </div>' +
            '   <br>' +
            '   <div class="form-group">' +
            '       <label class="control-label">奖励积分：</label>' +
            '       <input id="integralInput" type="number" class="form-control" size="11">' +
            '   </div>' +
            '</div>',
            okValue: '确定',
            ok: function () {
                var integralValue = $('#integralInput').val();
                if ($.trim(integralValue) == '') {
                    var tips = dialog({content: '请输入奖励的积分！'}).show();
                    setTimeout(function () {
                        tips.close();
                    }, 1500);
                    return false;
                }
                if (parseFloat(integralValue) < 0) {
                    var tips = dialog({content: '不能为负数！'}).show();
                    setTimeout(function () {
                        tips.close();
                    }, 1500);
                    return false;
                }
                $.ajax({
                    url: WEB_SERVER + 'front/everydayTask/givepoint/' + teacherId,
                    method: 'post',
                    dataType: 'json',
                    data: {
                        teacherId: teacherId,
                        point: integralValue
                    },
                    success: function (resp) {
                        if (resp.msg) {
                            var tips = dialog({content: resp.msg}).show();
                            setTimeout(function () {
                                tips.close();
                            }, 1500);
                            return;
                        }
                        if (resp.success) {
                            window.location.reload();
                        }
                    }
                });
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        d.showModal();
    }

    $(function () {
        $('#rewardIntegralBtn').click(rewardIntegral);
    });
}(window, jQuery));
