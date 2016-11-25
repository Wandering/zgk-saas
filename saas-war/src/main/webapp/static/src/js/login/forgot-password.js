/*
 * saas找回密码
 * @wiki:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44436391
 * @UI:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=42223952
 * */
var ForgotPassword = {
    init: function () {
        this.eventForgotPassword();
        this.eventSendSms();
    },
    fetchSmsCode: function (account, phone) {
        Common.ajaxFun('/account/sendSmsCode/' + account + '/' + phone + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                console.info(res);
            }
        });
    },
    eventSendSms: function () {
        $('#verification-btn').on('click', function () {
            var paramData = ForgotPassword.validateFormItem(0);
            if (paramData) {
                Common.ajaxFun('account/sendSmsCode/' + paramData.account + '/' + paramData.phone + '.do', 'GET', {}, function (res) {
                    if (res.rtnCode == "0000000") {
                        res.bizData.time = 60;
                        var timerCount = res.bizData.time;
                        var $msmTimer = null,
                            $verificationBtn = $('#verification-btn');
                        $verificationBtn.attr('disabled', true).text(timerCount + 's后获取');
                        $msmTimer = setInterval(function () {
                            timerCount--;
                            $verificationBtn.text(timerCount + 's后获取');
                            if (timerCount <= 0) {
                                $verificationBtn.text('重新获取');
                                $verificationBtn.attr('disabled', false);
                                clearTimeout($msmTimer);
                            }
                        }, 1000)
                    } else if(res.rtnCode === '0000003') {
                        layer.msg('同一手机号60秒内不能重复获取');
                    }else{
                        layer.msg(res.msg)
                    }
                });
            }
        })
    },
    eventForgotPassword: function () {
        $('#back-btn').on('click', function () {
            var paramData = ForgotPassword.validateFormItem(1);
            if (paramData) {
                Common.ajaxFun('/account/forgetPwd/' + paramData.account + '/' + paramData.phone + '/' + paramData.smsCode + '/' + $.md5(paramData.newPwd) + '.do', 'GET', {}, function (res) {
                    if (res.rtnCode == "0000000") {
                        console.info(res);
                    } else {
                        layer.msg(res.msg)
                    }
                });
            }
        });
    },
    validateFormItem: function (type) {
        var $account = $('#account').val();
        var $phone = $('#phone').val();
        var $smsCode = $('#verification-code').val();
        var $newPwd = $('#reset-pwd').val();
        var $pwd = $('#confirm').val();
        if (!(/[\S]+/).test($account)) {
            layer.msg('账号输入有误, 1~12位')
            return false;
        }
        if (!(/^1\d{10}$/).test($phone)) {
            layer.msg('11位电话号码输入有误')
            return false;
        }
        if (type === 1) {
            if ($smsCode.length != 6) {
                layer.msg('验证码输入有误')
                return false;
            }
            if ($newPwd.length < 6) {
                layer.msg('重设密码输入有误, 最少6位')
                return false;
            }
            if ($pwd.length < 6) {
                layer.msg('确认密码输入有误, 最少6位')
                return false;
            }
            if ($pwd != $newPwd) {
                layer.msg('两次密码输入不一致')
                return false;
            }
            return {
                account: $account,
                phone: $phone,
                smsCode: $smsCode,
                newPwd: $newPwd,
            }
        }
        return {
            account: $account,
            phone: $phone,
        }
    }
};
ForgotPassword.init();





