$('#login-btn').on('click', function () {
    var username = $('#user-name').val();
    var pwd = $('#password').val();
    Common.ajaxFun('/account/login/' + username + '/' + pwd + '.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            var data = res.bizData;
            var siderMenu = data.meuns;
            var siderMenuJson = {};
            for(var i=0;i<siderMenu.length;i++){
                siderMenuJson[i]=siderMenu[i];
            }
            siderMenuJson = JSON.stringify(siderMenuJson);
            Common.cookie.setCookie('tnName', data.tnName);
            Common.cookie.setCookie('isSuperManager', data.isSuperManager);
            Common.cookie.setCookie('tnId', data.userId);
            Common.cookie.setCookie('isInit', data.isInit);
            Common.cookie.setCookie('siderMenu', siderMenuJson);
            if(data.isInit==0){
                window.location.href = '/index';
            }else{
                window.location.href = '/seting-process'+data.isInit;
            }
        } else {
            layer.msg(res.msg);
        }
    }, function (res) {
        layer.msg('出错了');
    });
});
