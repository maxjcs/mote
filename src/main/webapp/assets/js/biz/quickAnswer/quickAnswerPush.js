/**
 * Created by Feil.Wang on 2015/5/14.
 */
(function (W, $) {
    $(function () {
        $('#newPushButton').click(newPush);
    });
    function newPush() {
        var d = dialog({
            title: '推送新题',
            padding: '20px 50px',
            okValue: '确定',
            ok: function () {
                $("form#newPushForm").submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {

            }
        });
        d.addEventListener('show', function () {
            $.ajax({
                method: 'post',
                dataType: 'json',
                url: WEB_SERVER + 'front/quickAnswer/queryQuickAnswerPushCount',
                success: function (res) {
                    console.log(res)
                    var html = '<form id="newPushForm" class="form-inline new-push-qa">';
                    var result = res.data;
                    for (var i in result) {
                        var item = result[i];
                        if (item.realSubject != 0 && item.realSubject != 3) {
                            html += '   <div class="form-group">' +
                                '       <label class="control-label">' + item.subjectName + '</label>' +
                                '       <input type="text" class="form-control text-center required digits" size="5" max="'+item.count+'" name="subject_' + item.realSubject + '">' +
                                '       <label class="control-label">题</label>' +
                                '       <span class="control-label text-muted text-small">（可推送' + item.count + '题）</span>' +
                                '       <span class="error-tips"></span>' +
                                '   </div>';
                        }
                    }
                    html += '</form>';
                    d.content(html);
                    $.validator.messages.required = '请输入推送数量';
                    $.validator.messages.digits = '请输入整数';
                    $.validator.messages.max = '已超过可推送的最大数量';
                    $("form#newPushForm").validate({
                        errorPlacement: function(error, element) {
                            error.appendTo( element.siblings('.error-tips'));
                        },
                        submitHandler: function (form) {
                            var tips = dialog();
                            var formData = $(form).serializeArray();
                            tips.show();
                            $.ajax({
                                url: WEB_SERVER + 'front/quickAnswer/quickAnswerSubjectPush',
                                method: 'POST',
                                dataType: 'json',
                                data: formData,
                                success: function (res) {
                                    console.log(res);
                                    if (res.success) {
                                        tips.content(res.data);
                                        d.close().remove();
                                        window.location.reload()
                                    } else {
                                        tips.content(res.data);
                                    }
                                    setTimeout(function () {
                                        tips.close().remove();
                                    }, 1500)
                                }
                            });
                        }
                    });
                }

            })
        });
        d.showModal();
    }
}(window, jQuery));