/**
 * Created by Feil.Wang on 2015/5/20.
 */
(function(W,$){
    $(function(){
        $('#addVerInfoBtn').click(addVerInfoBox);
    });
    function addVerInfoBox() {
        var btnDisabel = false;
        var d = dialog({
            title: '新增App版本描述',
            width: '500px',
            content: '<form id="addVerInfoBoxForm" class="form-horizontal">' +
            '    <div class="form-group">' +
            '        <label class="control-label col-sm-3">设备类型</label>' +
            '        <div class="col-sm-9">' +
            '            <select class="form-control w150 required" name="appType">' +
            '                <option value="1">Android</option>' +
            '                <option value="2">iOS</option>' +
            '            </select>' +
            '        </div>' +
            '    </div>' +
            '    <div class="form-group">' +
            '        <label class="control-label col-sm-3">下载地址</label>' +
            '        <div class="col-sm-9">' +
            '            <input name="downloadUrl" class="form-control w350 required" type="text" value="http://">' +
            '        </div>' +
            '    </div>' +
            '    <div class="form-group">' +
            '        <label class="control-label col-sm-3">version_code</label>' +
            '        <div class="col-sm-9">' +
            '            <input name="versionCode" class="form-control w150 required" type="text">' +
            '        </div>' +
            '    </div>' +
            '    <div class="form-group">' +
            '        <label class="control-label col-sm-3">version_name</label>' +
            '        <div class="col-sm-9">' +
            '            <input name="versionName" class="form-control w150 required" type="text">' +
            '        </div>' +
            '    </div>' +
            '    <div class="form-group">' +
            '        <label class="control-label col-sm-3">是否需要更新</label>' +
            '        <div class="col-sm-9">' +
            '           <label class="radio-inline"><input type="radio" name="needUpdate" value="Y" checked>是</label>' +
            '           <label class="radio-inline"><input type="radio" name="needUpdate" value="N">否</label>' +
            '        </div>' +
            '    </div>' +
            '    <div class="form-group">' +
            '        <label class="control-label col-sm-3">是否强制更新</label>' +
            '        <div class="col-sm-9">' +
            '           <label class="radio-inline"><input type="radio" name="forceUpdate" value="Y" checked>是</label>' +
            '           <label class="radio-inline"><input type="radio" name="forceUpdate" value="N">否</label>' +
            '        </div>' +
            '    </div>' +
            '    <div class="form-group">' +
            '        <label class="control-label col-sm-3">版本描述</label>' +
            '        <div class="col-sm-9">' +
            '            <textarea name="description" rows="5" class="form-control w350 required"></textarea>' +
            '        </div>' +
            '    </div>' +
            '</form>',
            okValue: '提交',
            ok: function () {
                $('#addVerInfoBoxForm').submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        d.addEventListener('show', function () {
            $.validator.messages.required = '必填字段';
            $("form#addVerInfoBoxForm").validate({
                submitHandler: function (form) {
                    if (!btnDisabel) {
                        btnDisabel = true;
                        var formData = $(form).serializeArray();
                        console.log(formData);
                        var tips = dialog().show();
                        $.ajax({
                            url: WEB_SERVER + "front/teacherSetting/addAppVersionInfo",
                            method: 'POST',
                            dataType: 'json',
                            data: formData,
                            success: function (resp) {
                                console.log(resp);
                                if(resp.code==1){
                                    tips.content(resp.message);
                                    setTimeout(function () {
                                        window.location.reload()
                                    },1000)
                                }else{
                                    tips.content(resp.message);
                                    setTimeout(function () {
                                        tips.close().remove();
                                    },1000)
                                }
                                btnDisabel = false;
                            }
                        });
                    }
                }
            });
        });
        d.showModal();
    }
}(window,jQuery));