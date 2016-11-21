$('#login-btn').on('click', function () {
    var that = $(this);
    var username = $.trim($('#user-name').val());
    var pwd = $.trim($('#password').val());
    if(username==''){
        layer.tips('请输入用户名!', $('#user-name'));
        return false;
    }
    if(pwd==''){
        layer.tips('请输入密码!', $('#password'));
        return false;
    }
    Common.ajaxFun('/account/login/' + username + '/' + pwd + '.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            var data = res.bizData;
            var siderMenu = data.meuns;
            var siderMenuJson = {};
            var provinceId = (data.countyId+'').substr(0,2)+'0000';
            for(var i=0;i<siderMenu.length;i++){
                siderMenuJson[i]=siderMenu[i];
            }
            siderMenuJson = JSON.stringify(siderMenuJson);
            Common.cookie.setCookie('tnName', data.tnName);
            Common.cookie.setCookie('isSuperManager', data.isSuperManager);
            Common.cookie.setCookie('tnId', data.tnId);
            Common.cookie.setCookie('userId', data.userId);
            Common.cookie.setCookie('isInit', data.isInit);
            Common.cookie.setCookie('siderMenu', siderMenuJson);
            Common.cookie.setCookie('provinceId', provinceId);
            if(data.isInit==0){
                window.location.href = '/course-guide';
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
