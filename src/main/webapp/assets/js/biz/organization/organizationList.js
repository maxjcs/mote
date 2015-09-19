/**
 * Created by Feil.Wang on 2015/4/17.
 */
(function (w, $) {
    var dlg,
        addDlgElem = ' <form id="addInstitution" class="form-horizontal">' +
            '<div class="form-group">' +
            '<label class="col-sm-3 control-label">帐号</label>' +
            '<div class="col-xs-8"><input type="text" class="col-xs-6 form-control" size="15" name="organizationAccounts" id="organizationAccounts" placeholder="请填写要设为机构的老师帐号"></div>' +
            '</div>' +
            '<div class="form-group">' +
            '<label class="col-sm-3 control-label">机构分类</label>' +
            '<div class="col-xs-8">' +
            '<select class="form-control" name="planType">' +
            '<option value="">请选择</option>' +
            '<option value="A">Plan A</option>' +
            '<option value="B">Plan B</option>' +
            '</select>' +
            '</div>' +
            '</div>' +
            '</form>';

    function addDlg() {
        dlg = dialog({
            width: '350px',
            title: '新建机构',
            content: addDlgElem,
            okValue: '保存',
            ok: function () {
                $("form#addInstitution").submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        dlg.addEventListener('show', function () {
            $("form#addInstitution").validate({
                rules: {
                    organizationAccounts: {
                        required: true,
                        digits: true
                    },
                    planType: {
                        required: true
                    }
                },
                messages: {
                    organizationAccounts: {
                        required: '请输入帐号',
                        digits: '帐号只能为数字'
                    },
                    planType: {
                        required: "请选择机构分类"
                    }
                },
                submitHandler: function (form) {
                    var formData = $(form).serializeArray();
                    var tips = dialog();
                    tips.show();
                    $.ajax({
                        url: WEB_SERVER + "front/organization/addOrganization",
                        method: 'POST',
                        dataType: 'json',
                        data: formData,
                        success: function (resp) {
                            console.log(resp);
                            tips.content(resp.message);
                            if (resp.code == 1) {
                                setTimeout(function () {
                                    w.location.reload();
                                }, 1500);
                            } else {
                                setTimeout(function () {
                                    tips.close().remove();
                                }, 1500);
                            }
                        }
                    });

                }
            });
        });
        dlg.showModal();
    }

    $(function () {
        $('#addInstitutionBtn').click(addDlg)
    });
}(window, jQuery));