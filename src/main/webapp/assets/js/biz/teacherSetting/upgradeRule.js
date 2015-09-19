/**
 * Created by Administrator on 2015/4/14.
 */
(function (w, $) {
    'use strict';
    w.editRule = function () {
        var contentElem = '<div class="form-inline">' +
            '   <div class="form-group">' +
            '       <label class="control-label">状态：</label>' +
            '       <span class="form-control-txt text-success">执行中</span>' +
            '   </div>' +
            '</div>' +
            '   <div class="separator"></div>' +
            '   <h5>升级规则：</h5>' +
            '<form id="ratingConfigForm" class="form-horizontal">';
        for (var i in w.ratingConfigData) {
            var item = w.ratingConfigData[i];
            if (i > 0) {
                contentElem += '   <div class="form-group">' +
                    '       <label class="col-sm-5 control-label">升级到' + (parseInt(i) + 2) + '星需要积分</label>' +
                    '       <div class="col-sm-6"><input type="number" class="form-control" name="' + item.name + '" value="' + item.value + '"></div>' +
                    '   </div>';
            }
        }
        contentElem += '</form>';
        var tips = dialog({
            quickClose: true
        });
        var dlg = dialog({
            title: '升星规则',
            width: '450px',
            content: contentElem,
            okValue: '修改',
            ok: function () {
                var that = this;
                var formData = $('#ratingConfigForm').serializeArray();
                tips.show();
                $.ajax({
                    url: WEB_SERVER + 'front/teacherSetting/saveRatingConfig',
                    method: 'POST',
                    dataType: 'json',
                    data: formData,
                    success: function (data) {
                        w.ratingConfigData = formData;
                        tips.content(data['message']);
                        that.close().remove();
                        setTimeout(function () {
                            tips.close().remove();
                        }, 1500)
                    }
                });
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
                this.close().remove();
                return false;
            }
        });
        dlg.showModal();
    }
}(window, jQuery));