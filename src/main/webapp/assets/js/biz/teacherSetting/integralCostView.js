/**
 * Created by Feil.Wang on 2015/4/7.
 */


$(function () {

    $('.X_btn-edit').click(function () {
        var $btn = $(this);
        var $value = $btn.closest('.panel').find('.form-control-txt');
        if ($value.siblings('.form-control').length > 0) return;
        $value.each(function () {
            var val = $(this).text();
            var name = $(this).data('name');
            var inputElem = '<input type="number" class="form-control" name="' + name + '">';
            $(this).after(inputElem).addClass('hide');
            $(this).siblings('.form-control').val(val);
        })
    });

    /**
     * 1.每日任务 积分、费 设置
     * 2.抢答 积分、费 设置
     * 3.音频分成
     */
    $('button.X_btn-save').click(function () {
        var loadingDlg = dialog();
        var $btn = $(this);
        var value = $btn.data('value');
        var $input = $btn.closest('.panel').find('.form-control');
        if ($input.length == 0) return;
        var formData = $btn.closest('.panel').find('form').serializeArray();
        var isValid = true;
        $input.each(function(){
            if($(this).val()==''){
                var tips = dialog({content:'请输入数字'}).show();
                isValid = false;
                setTimeout(function(){
                    tips.close().remove();
                },1500);
                return false;
            }
        });
        if(!isValid) return;
        loadingDlg.show();
        var apiPath = '';
        switch (value) {
            case 1:
                apiPath = 'saveEveryDayTeacherConfig';
                break;
            case 2:
                apiPath = 'saveFeudPointFeeConfig';
                break;
            case 3:
                apiPath = 'saveAudioSalesInfoConfig';
                break;
            default :
                break;
        }
        $.ajax({
            url: WEB_SERVER + 'front/teacherSetting/' + apiPath,
            method: 'POST',
            dataType: 'json',
            data: formData,
            success: function (data) {
                $input.each(function () {
                    var val = $(this).val();
                    $(this).siblings('.form-control-txt').text(val).removeClass('hide');
                    $(this).remove();
                });
                var resultDlg = dialog({
                    title: '提示', content: data['message'], ok: function () {
                    }
                });
                loadingDlg.close();
                resultDlg.show();

            }
        });

    });
});