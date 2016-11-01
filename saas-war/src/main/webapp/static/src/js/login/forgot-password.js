/*
* saas找回密码
* wiki:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44436391
* */
var ForgotPassword = {
    init: function () {
        this.eventForgotPassword();
    },
    fetchSmsCode:function(account,phone){
        Common.ajaxFun('/account/sendSmsCode/'+account+'/'+phone+'.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                console.info(res);
            }
        });
    },
    eventForgotPassword:function(){
        $('#back-btn').on('click', function () {
            var account = $('#account').val();
            var phone = $('#phone').val();
            var smsCode = $('#verification-code').val();
            var newPwd = $('#reset-pwd').val();
            var pwd = $('#confirm').val();

            Common.ajaxFun('/account/forgetPwd/' + account + '/'+ phone + '/'+ smsCode + '/'+ newPwd + '/' + pwd + '.do', 'GET', {}, function (res) {
                if (res.rtnCode == "0000000") {
                   console.info(res);
                }
            });
        });
    }
};
ForgotPassword.init();





