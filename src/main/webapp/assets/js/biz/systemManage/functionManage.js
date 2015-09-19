/**
 * Created by Feil.Wang on 2015/4/24.
 */
(function (W, $) {

    $(function () {
        $('#addAccountBtn').click(addFunction);
        $('.X_delete_func').click(function (e) {
            deleteFunction(e)
        })
    });

    var parentFunctionOptionHtml = '';
    $.ajax({
        method: 'GET',
        dataType: 'json',
        url: WEB_SERVER + 'front/systemUser/getParentFunction',
        success: function (res) {
            var list = res.data;
            for (var i in list) {
                var item = list[i];
                parentFunctionOptionHtml += '<option value="' + item.id + '">' + item.functionName + '</option>'
            }
        }
    });
    /**
     * 添加帐号
     */
    function addFunction() {
        var d = dialog({
            title: '添加功能',
            width: '350px',
            content: '<form id="addFunctionForm" class="form-horizontal" rol="form">' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">功能名称</label>' +
            '       <div class="col-sm-7"><input type="text" class="form-control required" name="functionName"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">功能URL</label>' +
            '       <div class="col-sm-7"><input type="text" class="form-control required" name="functionUrl"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">功能描述</label>' +
            '       <div class="col-sm-7"><input type="text" class="form-control required" name="functionDesc"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-4 control-label">父级菜单</label>' +
            '       <div class="col-sm-7">' +
            '           <select class="form-control required" name="parentId">' + parentFunctionOptionHtml + '</select>' +
            '       </div>' +
            '   </div>' +
            '</form>',
            okValue: '确定',
            ok: function () {
                $("form#addFunctionForm").submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        d.addEventListener('show', function () {
            $.validator.messages.required = '必填字段';
            $("form#addFunctionForm").validate({

                submitHandler: function (form) {
                    var tips = dialog();
                    var formData = $(form).serializeArray();
                    tips.show();
                    $.ajax({
                        url: WEB_SERVER + 'front/systemUser/insertFunction',
                        method: 'POST',
                        dataType: 'json',
                        data: formData,
                        success: function (res) {
                            console.log(res);
                            if (res.success) {
                                tips.content(res.data);
                                d.close().remove();
                                setTimeout(function () {
                                    window.location.reload()
                                }, 500);
                            } else {
                                tips.content(res.data);
                                setTimeout(function () {
                                    tips.close().remove();
                                }, 1500)
                            }
                        }
                    });
                }
            });
        });
        d.showModal();
    }

    function deleteFunction(e) {
        var $btn = $(e.target);
        var functionId = $btn.data('id');
        var name = $btn.data('name');
        var d = dialog({
            title: '提示',
            content: '确定删除“' + name + '”吗？',
            okValue: '确定',
            ok: function () {
                var tips = dialog().show();
                $.ajax({
                    method: 'POST',
                    dataType: 'json',
                    data: {
                        functionId:functionId
                    },
                    url: WEB_SERVER + 'front/systemUser/deleteFunction',
                    success: function (res) {
                        console.log(res);
                        if(res.success){
                            d.close().remove();
                            tips.content(res.data);
                            setTimeout(function () {
                                tips.close().remove();
                                window.location.reload();
                            },1000)
                        }else{
                            tips.content(res.data);
                            setTimeout(function () {
                                tips.close().remove();
                            },1000)
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