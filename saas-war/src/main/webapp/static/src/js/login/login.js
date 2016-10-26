/**
 {
  "bizData": {
    "isInit": 0,
    "isSuperManager": "0",
    "meuns": [
      {
        "meunId": 1,
        "meunName": "基础信息",
        "meunUrl": "http://1.png",
        "sonMeuns": [
          {
            "meunId": 2,
            "meunName": "用户管理",
            "meunUrl": "http://2.png"
          }
        ]
      }
    ],
    "roles": [
      "用户管理员"
    ],
    "status": 0,
    "tnAddr": "西安市雁塔区科技三路与唐延路十字",
    "tnName": "高新一中",   "学校租户名称",
    "userAccount": "zhangsan",
    "userId": 1,
    "userName": "三儿"
  },
  "rtnCode": "0000000",
  "ts": 1477014129206
}
 */

$('#login-btn').on('click', function () {
    var username = $('#user-name').val();
    var pwd = $('#password').val();
    Common.ajaxFun('/account/login/' + username + '/' + pwd + '.do', 'GET', {}, function (res) {
        console.log(res)
        if (res.rtnCode == "0000000") {
            var data = res.bizData;
            var isInit = data.isInit;
            var isSuperManager = data.isSuperManager;
            Common.cookie.setCookie('tnName', data.tnName);
            Common.cookie.setCookie('tnId', data.userId);
            switch (isInit) {
                case 0:
                    window.location.href = '/index';
                    break;
                case 1:
                    window.location.href = '/seting-process1';
                    break;
                case 2:
                    window.location.href = '/seting-process2';
                    break;
                case 3:
                    window.location.href = '/seting-process3';
                    break;
                case 4:
                    window.location.href = '/seting-process4';
                    break;
                case 5:
                    window.location.href = '/seting-process5';
                    break;
                default:
                    break;
            }
        }
    }, function (res) {
        alert("出错了");
    }, 'true');
});