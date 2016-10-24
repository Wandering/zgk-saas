/*
* saas登录逻辑
* */
var Login = {
    init: function () {
        $('#login-btn').on('click', function () {
            var username = $('#user-name').val();
            var pwd = $('#password').val();
            Common.ajaxFun('/account/login/' + username + '/' + pwd + '.do', 'GET', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    var data = res.bizData;
                    var isInit = data.isInit;
                    var isSuperManager = data.isSuperManager;
                    Common.cookie.setCookie('tnName', data.tnName);
                    Common.cookie.setCookie('tnId', data.userId);
                    if (isInit == '0') {
                        //if(isSuperManager=="1"){
                        window.location.href = '/seting-process1';
                        //}
                    } else {
                        window.location.href = '/index';
                    }
                }
            });
        });
    }
};
Login.init();





