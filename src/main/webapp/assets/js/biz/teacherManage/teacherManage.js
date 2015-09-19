/**
 * Created by Feil.Wang on 2015/4/7.
 */
(function (W, $) {
    function addTeacher() {
        var dlg = dialog({
            id: 'addTeacher-dialog',
            width: '550px',
            title: '新建教师',
            url: WEB_SERVER + 'front/teacherManage/addTeacher',
            onclose: function () {
                if (this.returnValue.length > 0) {
                }
            },
            cancelDisplay:false,
            cancelValue: '取消',
            cancel: function () {
            }
        });
        dlg.showModal();
    }

    $(function () {
        $('#addTeacherBtn').click(addTeacher);
    });

}(window, jQuery));
