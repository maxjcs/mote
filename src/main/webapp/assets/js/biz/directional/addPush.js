/**
 * Created by Feil.Wang on 2015/4/17.
 */
(function (w, $) {

    var dlg;
    var selectedMembersList = [];

    function selectMembersDialog() {
        dlg.showModal();
    }

    w.removeTableRow = function (self) {
        var selfHTML = $(self).closest('tr').prop('outerHTML');
        selectedMembersList.splice($.inArray('selfHTML',selectedMembersList),1);
        $(self).closest('tr').remove();
    };

    w.removeTableRowDB = function(self){
        var teacherId = $(self).closest('tr').find('input[name="member_item"]').val();
        var teacherName =  $(self).closest('tr').find('label').text();
        var confirmBox = dialog({
            title:'提示',
            content:'确定删除"'+teacherName+'"吗？',
            okValue:'确定',
            ok: function () {
                var loadingBox = dialog({id: 'loadingBox'}).show();
                $.ajax({
                    method: 'GET',
                    dataType: 'JSON',
                    url: WEB_SERVER + 'front/directional/removeTeacherFromOrg/'+teacherId+'/'+currentInstitutionId,
                    success:function(res){
                        loadingBox.close().remove();
                        confirmBox.close().remove();
                        console.log(res);
                        if(res.code==1){
                            window.location.reload();
                        }else{
                            var alertBox = dialog({
                                title: '消息',
                                content: '删除失败，该老师有未完成的任务，暂不能删除！',
                                okValue: '确定',
                                ok: function () {

                                }
                            });
                            alertBox.showModal();
                        }

                    }
                });
                return false;
            },
            cancelValue:'取消',
            cancel: function () {

            }
        });
        confirmBox.showModal();
    };

    function saveMembers() {
        var teacherIdslist = [];
        var strTeacherIds;
        var loadingBox = dialog({id: 'loadingBox'});
        var institutionId = editPushMode?currentInstitutionId:$('select[name="selectInstitutionOps"]').val(); //编辑模式从全局变量里取值
        if (institutionId == -1) {
            var tips = dialog({
                id: 'tips',
                content: '请选择录题机构'
            });
            tips.show();
            $('select[name="selectInstitutionOps"]').focus();
            setTimeout(function () {
                tips.close();
            }, 1500);
            return;
        }
        $('#membersListTable input[name="member_item"]').each(function () {
            var teacherId = $(this).val();
            teacherIdslist.push(teacherId);
        });
        strTeacherIds = teacherIdslist.join(",");
        if (teacherIdslist.length == 0) {
            var t = dialog({content: '请勾选录题人'}).show();
            setTimeout(function () {
                t.close();
            }, 1500);
            return;
        }
        loadingBox.showModal();
        console.log("teacherIds:",strTeacherIds,'\n','selectInstitutionOps:', institutionId);
        $.ajax({
            method: 'POST',
            dataType: 'JSON',
            data: {
                teacherIds: strTeacherIds,
                selectInstitutionOps: institutionId
            },
            url: WEB_SERVER + 'front/directional/saveMembers',
            success: function (res) {
                console.log(res)
                loadingBox.close().remove();
                if (res.code == 1) {
                    var alertBox = dialog({
                        title: '消息',
                        padding: '20px 70px',
                        content: res.message,
                        okValue: '确定',
                        ok: function () {
                            if(editPushMode){
                                window.location.reload();
                            }else{
                                window.location.href = WEB_SERVER + 'front/directional/directionalList';
                            }

                        }
                    });
                    alertBox.showModal();
                }
            }
        });
    }

    $(function () {
        dlg = dialog({
            id: 'selectMembers-dialog',
            width: '1200px',
            height: '650px',
            title: '选择录题人',
            url: WEB_SERVER + 'front/directional/selectMembers',
            onclose: function () {
                if (this.returnValue.length > 0) {
                    var newSelectedMembersList = this.returnValue;
                    selectedMembersList = $.merge(selectedMembersList,newSelectedMembersList);
                    $.unique(selectedMembersList);
                    var memberslistHTML = selectedMembersList.join('');
                    $('#membersListTable>tbody').html(memberslistHTML);
                }
            },
            cancelDisplay:false,
            cancelValue: '取消',
            cancel: function () {
                this.close();
                return false;
            }
        });

        $('#selectMembersBtn').click(selectMembersDialog);
        $('#saveMembersBtn').click(saveMembers);
    });

}(window, jQuery));
