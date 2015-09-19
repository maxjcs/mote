/**
 * Created by Feil.Wang on 2015/4/24.
 */
(function (W, $) {
    function updateOrgStatus(orgId, orgStatus) {
        var operation = orgStatus == 0 ? '禁用' : '启用';
        var reqStatus = orgStatus == 0 ? 1 : 0;
        var d = dialog({
            title: '操作',
            content: '确定要' + operation + '此账户吗？',
            okValue: '确定',
            ok: function () {
                var that = this;
                $.ajax({
                    url: WEB_SERVER + 'front/organization/updateOrgStatus',
                    method: 'POST',
                    dataType: 'json',
                    data: {
                        id: orgId,
                        status: reqStatus
                    },
                    success: function (data) {
                        var tips = dialog({content: data['message']}).show();
                        setTimeout(function () {
                            tips.close();
                        }, 1500);
                        if (orgStatus == 0) {
                            $("#updateOrgStatusBtn")
                                .removeClass('btn-red')
                                .addClass('btn-success')
                                .data('status', 1)
                                .find('span').text('启用账户');
                            $("#statusText").html('<span class="text-danger">已封号</span>');
                        } else {
                            $("#updateOrgStatusBtn")
                                .removeClass('btn-success')
                                .addClass('btn-red')
                                .data('status', 2)
                                .find('span').text('禁用账户');
                            $("#statusText").html('<span class="text-secondary">正常</span>');
                        }


                        that.close().remove();
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
        $('#updateOrgStatusBtn').click(function () {
            var orgId = $(this).data('id');
            var orgStatus = $(this).data('status');
            updateOrgStatus(orgId, orgStatus);
        });
    });
}(window, jQuery));
