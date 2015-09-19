/**
 * Created by Feil.Wang on 2015/4/24.
 */
(function (W, $) {
    function forbidden(teacherId) {
        var d = dialog({
            title: '操作',
            content: '确定要禁用此账户吗？',
            okValue: '确定',
            ok: function () {
                var that = this;
                $.ajax({
                    url: WEB_SERVER + 'front/teacherManage/closeTeacherAccountByTeacherId/' + teacherId,
                    method: 'POST',
                    dataType: 'json',
                    data: '',
                    success: function (data) {
                        var tips = dialog({content: data['message']});
                        tips.show();
                        setTimeout(function () {
                            tips.close();
                        }, 1500);
                        $(".forbidden-btn button")
                            .removeClass('btn-red')
                            .addClass('btn-success')
                            .attr("onclick", "forOpen('" + teacherId + "')");
                        $(".forbidden-btn button").find('span').text('启用账户');
                        $("#teacherStatus span").html('<span class="text-danger">已禁用</span>');
                        that.close().remove();
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

    function forOpen(teacherId) {
        var d = dialog({
            title: '操作',
            content: '确定要启用此账户吗？',
            okValue: '确定',
            ok: function () {
                var that = this;
                $.ajax({
                    url: WEB_SERVER + 'front/teacherManage/openTeacherAccountByTeacherId/' + teacherId,
                    method: 'POST',
                    dataType: 'json',
                    data: '',
                    success: function (data) {
                        var tips = dialog({content: data['message']});
                        tips.show();
                        setTimeout(function () {
                            tips.close();
                        }, 1500);
                        $(".forbidden-btn button")
                            .removeClass('btn-success')
                            .addClass('btn-red')
                            .attr("onclick", "forbidden('" + teacherId + "')");
                        $(".forbidden-btn button").find('span').text('禁用账户');
                        $("#teacherStatus span").html('<span class="text-success">正常</span>');
                        that.close().remove();
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

    function editInfo(t) {
        var sEl = '   <div class="form-group" id="schoolSelectEl">' +
            '       <label class="control-label col-xs-3">所在大学：</label>' +
            '       <div class="col-xs-8"><select id="schoolSelect" class="form-control" name="schoolId"></select></div>' +
            '   </div>';
        var d = dialog({
            title: '修改教师信息',
            width: '400px',
            content: '<form role="form" id="editInfoForm" class="form-horizontal">' +
            '<input type="hidden" name="id" value="' + teacher.id + '">' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">姓名：</label>' +
            '       <div class="col-xs-8"><input type="text" class="form-control" name="name" value="' + teacher.name + '"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">昵称：</label>' +
            '       <div class="col-xs-8"><input type="text" class="form-control" name="nickname" value="' + teacher.nickname + '"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">身份证：</label>' +
            '       <div class="col-xs-8"><input type="text" class="form-control" name="idNumber" value="' + teacher.idNumber + '"></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">身份：</label>' +
            '       <div class="col-xs-8">' +
            '           <select id="teacherIdentify" class="form-control" name="teacherIdentify">' +
            '               <option value="1" ' + (teacher.teacherIdentify == 1 ? 'selected' : '') + '>教师</option>' +
            '               <option value="2" ' + (teacher.teacherIdentify == 2 ? 'selected' : '') + '>大学生</option>' +
            '           </select>' +
            '       </div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">省份：</label>' +
            '       <div class="col-xs-8"><select id="provinceSelect" class="form-control" name="provinceId"></select></div>' +
            '   </div>' +
            '   <div class="form-group">' +
            '       <label class="control-label col-xs-3">城市：</label>' +
            '       <div class="col-xs-8"><select id="citySelect" class="form-control" name="cityId"></select></div>' +
            '   </div>' +
            '   <div class="form-group" id="schoolSelectEl">' +
            '       <label class="control-label col-xs-3">所在大学：</label>' +
            '       <div class="col-xs-8"><select id="schoolSelect" class="form-control" name="schoolId"></select></div>' +
            '   </div>' +
            '   <div class="form-group" id="schoolSelectEl">' +
            '       <label class="control-label col-xs-3">修改原因：</label>' +
            '       <div class="col-xs-8"><input type="text" class="form-control" name="reason"></div>' +
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
            citySelector({
                data: cityList,          //省份城市数据
                pid: 'provinceSelect',     //省份select标签的id
                cid: 'citySelect',         //城市select标签的id
                defaultPid: teacher.provinceId,             //默认显示的省份id(数据中的省份id)
                defaultCid: teacher.cityId              //默认显示的城市id(数据中的城市id)
            });
            /**
             * 所在学校二级联动
             */
            var oProvince = document.getElementById('provinceSelect');
            var oCity = document.getElementById('citySelect');
            var oSchool = document.getElementById('schoolSelect');
            if (oSchool) {
                changeSchool(oCity.value, teacher.schoolId);
                oProvince.addEventListener('change', function () {
                    changeSchool(oCity.value);
                }, false);
                oCity.addEventListener('change', function () {
                    changeSchool(oCity.value);
                }, false);
            }
            function changeSchool(cityValue, schoolValue) {
                console.log(cityValue, schoolValue);
                var schools = schoolList[cityValue];
                oSchool.options.length = 0;
                if (schools == undefined)return;
                for (var i = 0; i < schools.length; i++) {
                    oSchool.options[i] = new Option(schools[i].name, schools[i].id);
                    if (schoolValue == schools[i].id) {
                        oSchool.options[i].selected = true;
                    }
                }
            }

            function showHideSchoolSelect() {
                if ($('#teacherIdentify').val() == 1) {
                    $('#schoolSelectEl').hide()
                } else {
                    $('#schoolSelectEl').show()
                }
            }

            showHideSchoolSelect();
            $('#teacherIdentify').bind('change', function () {
                showHideSchoolSelect();
            });
            $('#editInfoForm').validate({
                rules: {
                    name: {
                        required: true
                    },
                    nickname: {
                        required: true
                    },
                    idNumber: {
                        required: true
                    },
                    schoolId: {
                        required: true,
                        min: 0
                    },
                    provinceId: {
                        required: true,
                        min: 0
                    },
                    cityId: {
                        required: true,
                        min: 0
                    }
                },

                messages: {
                    name: {
                        required: '请输入姓名'
                    },
                    nickname: {
                        required: '请选择昵称'
                    },
                    idNumber: {
                        required: '请输入身份证号'
                    },
                    schoolId: {
                        required: '请选择学校',
                        min: '请选择学校'
                    },
                    provinceId: {
                        required: '请选择省份',
                        min: '请选择省份'
                    },
                    cityId: {
                        required: '请选择城市',
                        min: '请选择城市'
                    }
                },

                submitHandler: function (form) {
                    var formData = $(form).serializeArray();
                    var tips = dialog().show();
                    $.ajax({
                        url: WEB_SERVER + 'front/teacherManage/modifyTeacher',
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


    W.forbidden = forbidden;
    W.forOpen = forOpen;
    W.editInfo = editInfo;
}(window, jQuery));
