/**
 * Created by Feil.Wang on 2015/7/2.
 */
(function (W, $) {
    function editCardInfo(card) {
        var data = card;
        var d = dialog({
            title: '修改银行卡信息',
            width: '400px',
            content: '<form role="form" id="editInfoForm" class="form-horizontal">' +
            '   <input type="hidden" name="id" value="' + data.id + '">' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">银行卡号：</label>' +
            '       <div class="col-xs-8"><input type="text" class="form-control" name="bankCard" value="' + data.cardNumber + '"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">开户名：</label>' +
            '       <div class="col-xs-8"><input type="text" class="form-control" name="bankUserName" value="' + data.accountName + '"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">开户行：</label>' +
            '       <div class="col-xs-8"><input type="text" class="form-control" name="bank" value="' + data.bankName + '"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">身份证号：</label>' +
            '       <div class="col-xs-8"><input type="text" class="form-control {required:true,isIdCardNo:true}" name="idNumber" value="' + data.idNumber + '"></div>' +
            '   </div>' +
            '   <div class="form-group" id="schoolSelectEl">' +
            '       <label class="control-label col-xs-3">修改原因：</label>' +
            '       <div class="col-xs-8"><textarea class="form-control" name="reason" rows="3"></textarea></div>' +
            '   </div>' +
            '</form>',
            okValue: '确定',
            ok: function () {
                $('#editInfoForm').submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        d.addEventListener('show', function () {

            $('#editInfoForm').validate({
                rules: {
                    bankCard: {
                        required: true,
                        rangelength: [13, 19]

                    },
                    bankUserName: {
                        required: true,
                        rangelength: [2, 15]
                    },
                    bank: {
                        required: true
                    }
                },

                messages: {
                    bankCard: {
                        required: '请填写银行卡号',
                        rangelength: '银行卡号为13-19位数字'
                    },
                    bankUserName: {
                        required: '请填写本人姓名',
                        rangelength: '开户名为2-15个汉字'
                    },
                    bank: {
                        required: '请填写开户行'
                    },
                    idNumber: {
                        required: '请填写身份证号'
                    }
                },

                submitHandler: function (form) {
                    var formData = $(form).serializeArray();
                    var tips = dialog().show();
                    $.ajax({
                        url: WEB_SERVER + 'front/teacherManage/modifyBankCard',
                        method: 'POST',
                        dataType: 'json',
                        data: formData,
                        success: function (res) {
                            console.log(res);
                            if (res.code == 1) {
                                tips.content(res.message);
                                setTimeout(function () {
                                    window.location.reload();
                                }, 200)
                            } else {
                                tips.content(res.message);
                                setTimeout(function () {
                                    tips.close().remove();
                                }, 500)
                            }
                        }
                    });
                }
            });
        });
        d.showModal();
    }

    $(function () {
        $('.J_editCardInfo').click(function () {
            var $btn = $(this);
            var $tr = $btn.closest('tr');
            var card = {
                id: $.trim($tr.find('td').eq(0).data("id")),
                cardNumber: $.trim($tr.find('.J_cardNumber').text()),
                accountName: $.trim($tr.find('.J_accountName').text()),
                bankName: $.trim($tr.find('.J_bankName').text()),
                idNumber: $.trim($tr.find('.J_idNumber').text())
            };
            editCardInfo(card)
        });

        var btnDisabel = false;
        $("#exportExcel").click(function () {
            if (!btnDisabel) {
                btnDisabel = true;
                var formData = $('#searchForm').serializeArray();
                var tips = dialog().show();
                $.ajax({
                    url: WEB_SERVER + "front/teacherManage/exportCardList",
                    method: 'POST',
                    dataType: 'json',
                    data: formData,
                    success: function (resp) {
                        console.log(resp);
                        if (resp.code == 1) {
                            var relative = resp.data;
                            window.location.href = WEB_SERVER + relative;
                            tips.content(resp.message);
                            setTimeout(function () {
                                tips.close().remove();
                            }, 1500);
                        }
                        btnDisabel = false;
                    }
                });
            }
            return false;
        });
    })
}(window, jQuery));
