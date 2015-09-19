/**
 * Created by Feil.Wang on 2015/4/4.
 */
(function (W, $) {
    function changePassword() {
        var dlg = dialog({
            title: '修改密码',
            width: '400px',
            content: ' <form id="changePwdForm" class="form-horizontal">' +
            '<div class="form-group">' +
            '<label class="col-sm-4 control-label">原密码</label>' +
            '<div class="col-xs-7"><input type="password" class="form-control" size="15" name="oldPassword"></div>' +
            '</div>' +
            '<div class="form-group">' +
            '<label class="col-sm-4 control-label">新密码</label>' +
            '<div class="col-xs-7"><input type="password" class="col-xs-6 form-control" size="15" name="newPassword" id="newPasswordId"></div>' +
            '</div>' +
            '<div class="form-group">' +
            '<label class="col-sm-4 control-label">确认新密码</label>' +
            '<div class="col-xs-7"><input type="password" class="col-xs-6 form-control" size="15" name="confirmNewPassword"></div>' +
            '</div>' +
            '</form>',
            okValue: '修改',
            ok: function () {
                $("form#changePwdForm").submit();
                return false;
            },
            cancelValue: '取消',
            cancel: function () {
            }
        });
        dlg.addEventListener('show', function () {
            $("form#changePwdForm").validate({
                rules: {
                    oldPassword: {
                        required: true
                    },
                    newPassword: {
                        required: true
                    },
                    confirmNewPassword: {
                        required: true,
                        equalTo: '#newPasswordId'
                    }
                },
                messages: {
                    oldPassword: {
                        required: '请输入原密码'
                    },

                    newPassword: {
                        required: '请输入新密码'
                    },
                    confirmNewPassword: {
                        required: '请再次输入新密码',
                        equalTo: '两次输入密码不一致'
                    }
                },
                submitHandler: function (form) {
                    var formData = $(form).serializeArray();
                    var tips = dialog();
                    tips.show();
                    $.ajax({
                        url: WEB_SERVER + "front/systemUser/changePassword",
                        method: 'POST',
                        dataType: 'json',
                        data: formData,
                        success: function (resp) {
                            console.log(resp);
                            tips.content(resp.data);
                            setTimeout(function () {
                                dlg.close().remove();
                                tips.close().remove();
                            }, 1500);
                        }
                    });

                }
            });
        });
        dlg.showModal();
    }

    /**
     * 省份城市二级联动菜单
     * @param opts
     *
     */
    function citySelector(options) {
        var opts = {
            data: [],        //省份城市数据
            pid: '',         //省份select标签的id
            cid: '',         //城市select标签的id
            defaultPid: '',  //默认显示的省份id(数据中的省份id)
            defaultCid: ''   //默认显示的省份id(数据中的省份id)
        };
        opts = options || opts;
        var oProvince = document.getElementById(opts.pid);
        var oCity = document.getElementById(opts.cid);
        var count = 0;
        for (var i = 0; i < opts.data.length; i++) {
            if (opts.data[i].children.length > 0) {
                if (opts.defaultPid == opts.data[i].id) {
                    oProvince.options[count] = new Option(opts.data[i].name, opts.data[i].id, true, true);
                } else {
                    oProvince.options[count] = new Option(opts.data[i].name, opts.data[i].id);
                }
                count++;
            }
        }
        oProvince.length = count;
        changeProvince(opts.defaultPid, opts.defaultCid);
        oProvince.addEventListener('change', function () {
            changeProvince(oProvince.value);
        }, false);
        function changeProvince(provinceValue, cityValue) {
            oCity.options.length = 0;
            for (var j = 0; j < opts.data.length; j++) {
                if (provinceValue == opts.data[j].id) {
                    var cityList = opts.data[j].children;
                    for (var k = 0; k < cityList.length; k++) {
                        oCity.options[k] = new Option(cityList[k].name, cityList[k].id);
                        if (cityValue == cityList[k].id) {
                            oCity.options[k].selected = true;
                        }
                    }
                }
            }
        }
    }

    $(function () {
        var userAccouont = $.cookie("userAccount");
        $("#operatorAccount span").html(userAccouont);
        $("#logoutBtn").click(function () {
            $.ajax({
                method: 'post',
                dataType: 'json',
                url: WEB_SERVER + 'status/logoutByJson',
                success: function () {
                    window.location.reload()
                }
            });
        });
        $('#changePwdBtn').click(changePassword);

        //增加身份证验证
        jQuery.validator.addMethod("isIdCardNo", function (value, element) {
            return this.optional(element) || isIdCardNo(value);
        }, "身份证号码格式不正确");
    });

    //增加身份证验证
    function isIdCardNo(num) {
        var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
        var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
        var varArray = new Array();
        var intValue;
        var lngProduct = 0;
        var intCheckDigit;
        var intStrLen = num.length;
        var idNumber = num;
        // initialize
        if ((intStrLen != 15) && (intStrLen != 18)) {
            return false;
        }
        // check and set value
        for (var i = 0; i < intStrLen; i++) {
            varArray[i] = idNumber.charAt(i);
            if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
                return false;
            } else if (i < 17) {
                varArray[i] = varArray[i] * factorArr[i];
            }
        }
        if (intStrLen == 18) {
            //check date
            var date8 = idNumber.substring(6, 14);
            if (isDate8(date8) == false) {
                return false;
            }
            // calculate the sum of the products
            for (var j = 0; j < 17; i++) {
                lngProduct = lngProduct + varArray[j];
            }
            // calculate the check digit
            intCheckDigit = parityBit[lngProduct % 11];
            // check last digit
            if (varArray[17] != intCheckDigit) {
                return false;
            }
        }
        else {        //length is 15
            //check date
            var date6 = idNumber.substring(6, 12);
            if (isDate6(date6) == false) {
                return false;
            }
        }
        return true;
    }

    function isDate6(sDate) {
        if (!/^[0-9]{6}$/.test(sDate)) {
            return false;
        }
        var year, month, day;
        year = sDate.substring(0, 4);
        month = sDate.substring(4, 6);
        if (year < 1700 || year > 2500) return false;
        if (month < 1 || month > 12) return false;
        return true
    }

    function isDate8(sDate) {
        if (!/^[0-9]{8}$/.test(sDate)) {
            return false;
        }
        var year, month, day;
        year = sDate.substring(0, 4);
        month = sDate.substring(4, 6);
        day = sDate.substring(6, 8);
        var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        if (year < 1700 || year > 2500) return false;
        if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
        if (month < 1 || month > 12) return false;
        if (day < 1 || day > iaMonthDays[month - 1]) return false;
        return true
    }

    W.citySelector = citySelector;
}(window, jQuery));
