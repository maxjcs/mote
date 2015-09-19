/**
 * Created by Feil.Wang on 2015/4/24.
 */
(function (W, $) {
    $(function () {
        $('#addAccountBtn').click(addAccount);
        $('.x-rest-pwd').click(function () {
            restPassword(this)
        });
        $('.x-account-edit').click(function () {
            editAccount(this)
        })
    });

    /**
     * 添加帐号
     */
    function addAccount() {
        var d = dialog({
            title: '添加帐号',
            width: '350px',
            content: '<form id="addAccountForm" class="form-horizontal" rol="form">' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">角色</label>' +
            '       <div class="col-sm-7">' +
            '           <select class="form-control" name="roleId">' +
            '               <option value="-1">请选择</option>' +
            '               <option value="1">超级管理员</option>' +
            '               <option value="2">运营管理员</option>' +
            '               <option value="3">一般运营人员</option>' +
            '           </select>' +
            '   </div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">账号</label>' +
            '       <div class="col-sm-7"><input type="text" class="form-control" name="userAccount"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">新密码</label>' +
            '       <div class="col-sm-7"><input id="passwordId" type="password" class="form-control" name="password"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">重复新密码</label>' +
            '       <div class="col-sm-7"><input type="password" class="form-control" name="confirm_password"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">姓名</label>' +
            '       <div class="col-sm-7"><input type="text" class="form-control" name="name"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">手机号</label>' +
            '       <div class="col-sm-7"><input type="text" class="form-control" name="phoneNumber"></div>' +
            '   </div>' +
            '</form>',
            okValue: '确定',
            ok: function () {
                $("form#addAccountForm").submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        d.addEventListener('show', function () {
            $("form#addAccountForm").validate({
                rules: {
                    roleId: {
                        required: true,
                        min: 1
                    },
                    userAccount: {
                        required: true
                    },
                    password: {
                        required: true,
                        minlength: 4
                    },
                    confirm_password: {
                        required: true,
                        equalTo: '#passwordId'
                    },
                    name: {
                        required: true
                    },
                    phoneNumber: {
                        required: true,
                        digits: true
                    }
                },

                messages: {
                    roleId: {
                        required: '请选择角色',
                        min: '请选择角色'
                    },
                    userAccount: {
                        required: '请输入帐号'
                    },
                    password: {
                        required: '请输入密码',
                        minlength: '密码长度需大于4位'
                    },
                    confirm_password: {
                        required: '请输入确认密码',
                        equalTo: '两次输入密码不一致'
                    },
                    name: {
                        required: '请输入姓名'
                    },
                    phoneNumber: {
                        required: '请输入手机号'
                    }
                },

                submitHandler: function (form) {
                    var tips = dialog();
                    var formData = $(form).serializeArray();
                    tips.show();
                    $.ajax({
                        url: WEB_SERVER + 'front/systemUser/addSystemUser',
                        method: 'POST',
                        dataType: 'json',
                        data: formData,
                        success: function (res) {
                            console.log(res);
                            if (res.data.code == 1) {
                                tips.content(res.data.message);
                                d.close().remove();
                                window.location.reload()
                            } else {
                                tips.content(res.data.message);
                            }
                            setTimeout(function () {
                                tips.close().remove();
                            }, 1500)
                        }
                    });
                }
            });
        });
        d.showModal();
    }

    /**
     * 重置密码
     * @param t
     */
    function restPassword(t) {
        var $btn = $(t);
        var userAccount = $btn.data('account');
        var d = dialog({
            title: '重置密码',
            width: '350px',
            content: '<form id="resetPwdForm" class="form-horizontal" rol="form">' +
            '   <input type="hidden" name="userAccount" value="' + userAccount + '">' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">密码</label>' +
            '       <div class="col-sm-7"><input id="passwordId" type="password" class="form-control" name="newPassword"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">重复密码</label>' +
            '       <div class="col-sm-7"><input type="password" class="form-control" name="confirm_password"></div>' +
            '   </div>' +
            '</form>',
            okValue: '确定',
            ok: function () {
                $("form#resetPwdForm").submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        d.addEventListener('show', function () {
            $("form#resetPwdForm").validate({
                rules: {
                    newPassword: {
                        required: true,
                        minlength: 4
                    },
                    confirm_password: {
                        required: true,
                        equalTo: '#passwordId'
                    }
                },

                messages: {
                    newPassword: {
                        required: '请输入密码',
                        minlength: '密码长度需大于4位'
                    },
                    confirm_password: {
                        required: '请输入确认密码',
                        equalTo: '两次输入密码不一致'
                    }
                },

                submitHandler: function (form) {
                    var tips = dialog();
                    var formData = $(form).serializeArray();
                    tips.show();
                    $.ajax({
                        url: WEB_SERVER + 'front/systemUser/resetPassword',
                        method: 'POST',
                        dataType: 'json',
                        data: formData,
                        success: function (res) {
                            console.log(res);
                            if (res.success) {
                                tips.content(res.data);
                                d.close().remove();
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
        });
        d.showModal();
    }

    /**
     * 修改信息
     * @param t
     */
    function editAccount(t) {
        var $btn = $(t);
        var userAccount = $btn.data('account');
        var userName = $btn.data('name');
        var userPhone = $btn.data('phone');
        var userRole = $btn.data('role');
        var userStatus = $btn.data('status');
        var d = dialog({
            title: '修改信息',
            width: '350px',
            content: '<form id="editAccountForm" class="form-horizontal" rol="form">' +
            '   <input type="hidden" name="userAccount" value="' + userAccount + '">' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">用户名</label>' +
            '       <div class="col-sm-7 form-control-txt">' + userAccount + '</div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">姓名</label>' +
            '       <div class="col-sm-7 form-control-txt">' + userName + '</div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">手机号码</label>' +
            '       <div class="col-sm-7 form-control-txt">' + userPhone + '</div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">角色</label>' +
            '       <div class="col-sm-7">' +
            '           <select class="form-control" name="roleId">' +
            '               <option value="2" ' + (userRole == 2 ? "selected" : "") + '>运营管理员</option>' +
            '               <option value="3" ' + (userRole == 3 ? "selected" : "") + '>一般运营人员</option>' +
            '           </select>' +
            '       </div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">帐号状态</label>' +
            '       <div class="col-sm-7">' +
            '           <label class="radio-inline">' +
            '               <input type="radio" name="status" value="1" ' + (userStatus == 1 ? "checked" : "") + '>' +
            '               正常' +
            '           </label>' +
            '           <label class="radio-inline">' +
            '               <input type="radio" name="status" value="0" ' + (userStatus == 0 ? "checked" : "") + '>' +
            '               禁用' +
            '           </label>' +
            '        </div>' +
            '   </div>' +
            '</form>',
            okValue: '确定',
            ok: function () {
                var tips = dialog().show();
                var formData = $('#editAccountForm').serializeArray();
                console.log(formData);
                $.ajax({
                    method: 'post',
                    dataType: 'json',
                    data: formData,
                    url: WEB_SERVER + 'front/systemUser/updateSystemUserStatusAndRole',
                    success: function (res) {
                        console.log(res);
                        if (res.success) {
                            d.close().remove();
                            tips.content(res.data);
                            setTimeout(function () {
                                window.location.reload()
                            }, 500)
                        } else {
                            tips.content(res.data);
                            setTimeout(function () {
                                tips.close().remove();
                            }, 500)
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
}(window, jQuery));