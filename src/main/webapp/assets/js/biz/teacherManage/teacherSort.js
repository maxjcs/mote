/**
 * Created by Feil.Wang on 2015/5/28.
 */
(function (W, $) {

    $(function () {
        $('#saveSrotBtn').click(saveTeacherSrot);
        $.validator.messages.digits = '请输入数字';
        $('#teacherSrotFrom').validate({
            submitHandler: function (form) {
                var tips = dialog().show();
                var formData = $(form).serializeArray();
                console.log(form);
                console.log(formData);

                $.ajax({
                    url: WEB_SERVER + 'front/teacherManage/editTeacherSort',
                    method: 'POST',
                    dataType: 'json',
                    data: formData,
                    success: function (res) {
                        if (res.code == 1) {
                            tips.content(res.message);
                            window.location.reload();
                        }
                        setTimeout(function () {
                            tips.close().remove();
                        }, 1500)
                    }
                });
            }
        });
    });

    function saveTeacherSrot(){
        $('#teacherSrotFrom').submit();
    }
    var editStarLevelBox;

    function editStarLevel(self, phoneNumber) {
        var $btn = $(self);
        var starVal = $btn.data('star');
        editStarLevelBox = dialog({
            title: '修改星级',
            width: '400px',
            content: '<form id="editStarLevelForm" class="form-horizontal">' +
            '   <input type="hidden" name="phoneNumber" value="' + phoneNumber + '">' +
            '   <div class="form-group">' +
            '       <label class="col-sm-3 control-label">星级修改为</label>' +
            '       <div class="col-sm-3">' +
            '           <select class="form-control" name="star">' +
            '               <option value="0" ' + (starVal == 0 ? "selected=\"selected\"" : "") + '>0星</option>' +
            '               <option value="1" ' + (starVal == 1 ? "selected=\"selected\"" : "") + '>1星</option>' +
            '               <option value="2" ' + (starVal == 2 ? "selected=\"selected\"" : "") + '>2星</option>' +
            '               <option value="3" ' + (starVal == 3 ? "selected=\"selected\"" : "") + '>3星</option>' +
            '               <option value="4" ' + (starVal == 4 ? "selected=\"selected\"" : "") + '>4星</option>' +
            '               <option value="5" ' + (starVal == 5 ? "selected=\"selected\"" : "") + '>5星</option>' +
            '           </select>' +
            '       </div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="col-sm-3 control-label">修改原因</label>' +
            '       <div class="col-sm-8"><textarea name="reason" type="text" class="form-control" rows="4"></textarea></div>' +
            '   </div>' +
            '</form>',
            okValue: '确定',
            ok: function () {
                $("form#editStarLevelForm").submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        editStarLevelBox.addEventListener('show', function () {
            $("form#editStarLevelForm").validate({
                rules: {
                    reason: {
                        required: true
                    }
                },

                messages: {
                    reason: {
                        required: '请输入修改原因'
                    }
                },

                submitHandler: function (form) {
                    var tips = dialog();
                    var starVal = $('select[name="star"]').val();
                    var formData = $(form).serializeArray();
                    tips.show();
                    $.ajax({
                        url: WEB_SERVER + 'front/teacherManage/updateTeacherStar',
                        method: 'POST',
                        dataType: 'json',
                        data: formData,
                        success: function (res) {
                            editStarLevelBox.close().remove();
                            if (res.code == 1) {
                                tips.content(res.message);
                                $btn.closest('tr').children('.star-level').text(starVal + "星");
                            }
                            setTimeout(function () {
                                tips.close().remove();
                            }, 1500)
                        }
                    });
                }
            });
        });
        editStarLevelBox.showModal();
    }

    W.editStarLevel = editStarLevel;
}(window, jQuery));