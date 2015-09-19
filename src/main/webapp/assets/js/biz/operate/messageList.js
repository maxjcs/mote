/**
 * Created by Feil.Wang on 2015/4/24.
 */
(function (W, $) {
    var d;
    W.createMessage = function () {

        d = dialog({
            title: '新建消息',
            width: '450px',
            content: '<form id="addOperateMsgForm" class="form-horizontal"> <div class="form-horizontal">' +
            '   <div class="form-group">' +
            '       <label class="col-sm-3 control-label">消息标题</label>' +
            '       <div class="col-sm-8">' +
            '           <input type="text" name="title" class="form-control" placeholder="必填，最多输入20个字">' +
            '       </div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-3 control-label">消息链接</label>' +
            '       <div class="col-sm-8">' +
            '           <input type="text" name="url" class="form-control" placeholder="必填，例如：http://www.xuexibao.cn">' +
            '       </div>' +
            '   </div>' +
            '</div></form>',
            okValue: '确定',
            ok: function () {
                $("form#addOperateMsgForm").submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        d.addEventListener('show', function () {
            $("form#addOperateMsgForm").validate({
                rules: {
                    title: {
                        required: true,
                        maxlength: 20
                    },
                    url: {
                        required: true,
                        url: true
                    }
                },

                messages: {
                    title: {
                        required: '请输入消息标题',
                        maxlength: '最多输入20个字'
                    },

                    url: {
                        required: '请输入消息链接',
                        url: '消息链接不合法'
                    }
                },

                submitHandler: function (form) {
                    var tips = dialog({
                        quickClose: true
                    });
                    var formData = $(form).serializeArray();
                    tips.show();
                    $.ajax({
                        url: WEB_SERVER + 'front/operate/addOperateMsg',
                        method: 'POST',
                        dataType: 'json',
                        data: formData,
                        success: function (data) {
                            tips.content(data['message']);
                            d.close().remove();
                            setTimeout(function () {
                                tips.close().remove();
                                window.location.reload()
                            }, 1500)
                        }
                    });
                }
            });
        });
        d.showModal();
    }
}(window, jQuery));