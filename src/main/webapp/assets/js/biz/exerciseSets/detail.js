/**
 * Created by Feil.Wang on 2015/7/16.
 */
(function (W, $) {
    'use strict';
    var gradeMap = {
        '1': '一年级',
        '2': '二年级',
        '3': '三年级',
        '4': '四年级',
        '5': '五年级',
        '6': '六年级',
        '7': '初一',
        '8': '初二',
        '9': '初三',
        '10': '高一',
        '11': '高二',
        '12': '高三'
    };
    var subjectMap = {
        '1': '数学',
        '2': '语文',
        '3': '英语',
        '4': '政治',
        '5': '历史',
        '6': '地理',
        '7': '物理',
        '8': '化学',
        '9': '生物'
    };
    var gradeCfg = {
        '小学': {
            'grade': ['1', '2', '3', '4', '5', '6'],
            'subject': ['1', '2', '3']
        },
        '初中': {
            'grade': ['7', '8', '9'],
            'subject': ['1', '2', '3', '4', '5', '6', '7', '8', '9']
        },
        '高中': {
            'grade': ['10', '11', '12'],
            'subject': ['1', '2', '3', '4', '5', '6', '7', '8', '9']
        }
    };

    $(function () {
    	
    	$('#updateStatusBtn').click(function(){
    		var setId = $(this).data('setid');
    		// console.info('setId ==> {0}',setId);
    	    var updateDialog = dialog({
    	    	title:'下架习题集',
    	    	widht:'200px',
    	    	content:'你确认要下架此习题集',
    	    	 cancelValue: '取消',
                 cancel: function () {

                 },
                 okValue: '确定',
                 ok:function(){
                	 var tips = dialog().show();
                	 $.ajax({
                		 method: 'post',
                         dataType: 'json',
                         url: WEB_SERVER + 'front/exerciseSets/updateExerciseSetUnShelve',
                         data: {
                             setId: setId
                         },
                         success:function(res){
                        	 console.log(res);
                             tips.content(res.message);
                             if(res.code == 1){
                            	 setTimeout(function () {
                                     W.location.reload()
                                 }, 500) 
                             }
                         }
                	 });
                 }
    	    });
    	    
    	    updateDialog.showModal();
    	});
    	
        $('#updateInfoBtn').click(function () {
            var setId = $(this).data('setid');
            var updateDialog = dialog({
                title: '编辑',
                width: '400px',
                content: '<div id="updateInfoForm">' +
                '   <div class="form-group clearfix">' +
                '       <label class="col-md-2 control-label">年级</label>' +
                '       <div class="col-md-10">' +
                '           <div class="grade-label"><label class="radio-inline"><input type="radio" name="gradeGroup" value="小学"><span>小学</span></label></div>' +
                '           <div class="grade-label"><label class="radio-inline"><input type="radio" name="gradeGroup" value="初中"><span>初中</span></label></div>' +
                '           <div class="grade-label"><label class="radio-inline"><input type="radio" name="gradeGroup" value="高中"><span>高中</span></label></div>' +
                '       </div>' +
                '   </div>' +
                '   <div class="form-group clearfix">' +
                '       <label class="col-md-2 control-label">&nbsp;</label>' +
                '       <div class="col-md-10" id="gradeGroup"></div>' +
                '   </div>' +
                '   <div class="separator"></div>' +
                '   <div class="form-group clearfix">' +
                '       <label class="col-md-2 control-label">学科</label>' +
                '       <div class="col-md-10" id="subjectGroup"></div>' +
                '   </div>' +
                '</div>',
                cancelValue: '取消',
                cancel: function () {

                },
                okValue: '确定',
                ok: function () {
                    var gradeArray = [];
                    $('#gradeGroup').children('div:not(".hide")').find('input[name="grade"]:checked').each(function () {
                        gradeArray.push($(this).val());
                    });
                    var grades = gradeArray.join(',');
                    var subjectArray = [];
                    $('#subjectGroup').children('div:not(".hide")').find('input[name="subject"]:checked').each(function () {
                        subjectArray.push($(this).val());
                    });
                    var subjects = subjectArray.join(',');
                    var tips = dialog().show();
                    $.ajax({
                        method: 'post',
                        dataType: 'json',
                        url: WEB_SERVER + 'front/exerciseSets/updateGradeAndSubject',
                        data: {
                            setId: setId,
                            grades: grades,
                            subjects: subjects
                        },
                        success: function (res) {
                            console.log(res);
                            tips.content(res.message);
                            if (res.code == 1) {
                                setTimeout(function () {
                                    W.location.reload()
                                }, 500)
                            }

                        }
                    });
                    return false;
                }
            });
            updateDialog.addEventListener('show', function () {
                var selectedData = initGradeSelected;
                var gradeStr = '';
                switch (selectedData.grade[0]) {
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                        gradeStr = '小学';
                        break;
                    case '7':
                    case '8':
                    case '9':
                        gradeStr = '初中';
                        break;
                    case '10':
                    case '11':
                    case '12':
                        gradeStr = '高中';
                        break;
                }
                if (selectedData.grade.length == 0) {
                    $('input[name="gradeGroup"]').filter('[value="小学"]').prop('checked', true);
                    for (var key in gradeCfg) {
                        $('#gradeGroup').append('<div ' + (key == '小学' ? "" : "class=\"hide\"") + '>' + createGradesElem(key, selectedData) + '</div>');
                    }
                } else {
                    $('input[name="gradeGroup"]').filter('[value="' + gradeStr + '"]').prop('checked', true);
                    for (var key in gradeCfg) {
                        $('#gradeGroup').append('<div ' + (key == gradeStr ? "" : "class=\"hide\"") + '>' + createGradesElem(key, selectedData) + '</div>');
                    }
                }

                $('#subjectGroup').html(createSubjectElem(selectedData));
                if (gradeStr == '小学') {
                    $('#subjectGroup .grade-label:gt(2)').addClass('hide');
                }

                $('input[name="gradeGroup"]').click(function () {
                    var index = $(this).closest('.grade-label').index();
                    $('#gradeGroup > div').eq(index).removeClass('hide').siblings().addClass('hide');
                    if (index == 0) {
                        $('#subjectGroup .grade-label:gt(2)').addClass('hide');
                    } else {
                        $('#subjectGroup .grade-label').removeClass('hide');
                    }
                });
            });
            updateDialog.showModal();
        });

    });

    function createGradesElem(key, selectedData) {
        var el = '';
        var gradeArr = gradeCfg[key].grade;
        for (var i in gradeArr) {
            var isSelected = $.inArray(gradeArr[i], selectedData.grade) != -1;
            el += '<div class="grade-label"><label class="checkbox-inline"><input type="checkbox" name="grade" value="' + gradeArr[i] + '" ' + (isSelected ? "checked" : "") + '><span>' + gradeMap[gradeArr[i]] + '</span></label></div>\n\r';
        }
        return el;
    }

    function createSubjectElem(selectedData) {
        var el = '';
        var key = 0;
        for (var i in subjectMap) {
            var isSelected = $.inArray(i, selectedData.subject) != -1;
            key++;
            el += '<div class="grade-label">' +
                '   <label class="checkbox-inline">' +
                '       <input type="checkbox" name="subject" value="' + i + '" ' + (isSelected ? "checked" : "") + '>' +
                '       <span>' + subjectMap[i] + '</span>' +
                '   </label>' +
                '</div>\n\r';
        }
        return el;
    }

}(window, jQuery));
