/*! Created by Feil.Wang(wangfeilong@xuexibao.cn) on 2015/8/28. */
(function (w, $, d) {
    'use strict';
    $(function () {
        $('#generateSalaryForm').validate({
            errorPlacement: function (error, element) {
                if (element[0].name == 'teacherIdentify') {
                    error.appendTo(element.parent().siblings('.error-tips'));
                } else {
                    error.insertAfter(element)
                }
            },
            rules: {
                teacherIdentify: {
                    required: true
                },
                salaryMonth: {
                    required: true
                },
                monthStart: {
                    required: true
                },
                monthEnd: {
                    required: true
                }
            },
            messages: {
                teacherIdentify: {
                    required: '请选择教师身份'
                },
                salaryMonth: {
                    required: '请选择工资月份'
                },
                monthStart: {
                    required: '请选择工资时间段'
                },
                monthEnd: {
                    required: '请选择工资时间段'
                }
            }
        });
        function triggerMonthStartDlg(){
            var d = dialog({
                id: 'monthStartDlg',
                content: '请先选择工资月份！'
            }).show();
            setTimeout(function () {
                d.close().remove();
                $('#salaryMonthInput').focus();
            },500)
        }
        $('#salaryMonthInput').bind('click focus', function () {
            var $monthStartInput = $dp.$('monthStartInput');
            WdatePicker({
                el: 'salaryMonthInput',
                dateFmt: 'yyyy-MM',
                vel: 'salaryMonthHiddenInput',
                onpicked: function () {
                    $monthStartInput.focus();
                }
            })
        });
        $('#monthStartInput').on('click focus', function () {
            if ($('#salaryMonthInput').val() == '') {
                triggerMonthStartDlg();
            } else {
                var $monthEndInput = $dp.$('monthEndInput');
                WdatePicker({
                    el: 'monthStartInput',
                    dateFmt: 'yyyy-MM-dd',
                    alwaysUseStartDate: true,
                    startDate: '#F{$dp.$D(\'salaryMonthHiddenInput\',{M:-1})}',
                    minDate: '#F{$dp.$D(\'salaryMonthHiddenInput\',{M:-1})}',
                    maxDate: '#F{$dp.$D(\'salaryMonthHiddenInput\')}',
                    onpicked: function () {
                        $monthEndInput.focus();
                    }
                });
            }
        });
        $('#monthEndInput').on('click focus', function () {
            if ($('#salaryMonthInput').val() == '') {
                triggerMonthStartDlg();
            } else {
                WdatePicker({
                    el: 'monthEndInput',
                    dateFmt: 'yyyy-MM-dd',
                    alwaysUseStartDate: true,
                    startDate: '#F{$dp.$D(\'monthStartInput\',{d:27})}',
                    minDate: '#F{$dp.$D(\'monthStartInput\',{d:27})}',
                    maxDate: '#F{$dp.$D(\'monthStartInput\',{d:30})}'
                });
            }
        });
    })
}(window, jQuery, dialog));