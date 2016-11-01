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
        console.log(JSON.stringify(res))
        if (res.rtnCode == "0000000") {
            var data = res.bizData;
            var isInit = data.isInit;
            //var meuns = data.meuns;
            var meuns = [
                {
                    "meunId": 28,
                    "meunName": "服务列表",
                    "meunUrl": "",
                    "sonMeuns": []
                },
                {
                    "meunId": 25,
                    "meunName": "账号信息",
                    "meunUrl": "",
                    "sonMeuns": [
                        {
                            "meunId": 27,
                            "meunName": "账号管理",
                            "meunUrl": ""
                        },
                        {
                            "meunId": 26,
                            "meunName": "角色管理",
                            "meunUrl": ""
                        }
                    ]
                },
                //{
                //    "meunId": 18,
                //    "meunName": "基础信息管理",
                //    "meunUrl": "",
                //    "sonMeuns": [
                //        {
                //            "meunId": 24,
                //            "meunName": "学生管理",
                //            "meunUrl": ""
                //        },
                //        {
                //            "meunId": 23,
                //            "meunName": "升学率设置",
                //            "meunUrl": ""
                //        },
                //        {
                //            "meunId": 22,
                //            "meunName": "教师管理",
                //            "meunUrl": ""
                //        },
                //        {
                //            "meunId": 21,
                //            "meunName": "教室管理",
                //            "meunUrl": ""
                //        },
                //        {
                //            "meunId": 20,
                //            "meunName": "班级管理",
                //            "meunUrl": ""
                //        },
                //        {
                //            "meunId": 19,
                //            "meunName": "年级管理",
                //            "meunUrl": ""
                //        }
                //    ]
                //},
                //{
                //    "meunId": 17,
                //    "meunName": "教师测评",
                //    "meunUrl": "",
                //    "sonMeuns": []
                //},
                //{
                //    "meunId": 16,
                //    "meunName": "专业测评",
                //    "meunUrl": "",
                //    "sonMeuns": []
                //},
                //{
                //    "meunId": 15,
                //    "meunName": "一键排课系统",
                //    "meunUrl": "",
                //    "sonMeuns": []
                //},
                {
                    "meunId": 10,
                    "meunName": "数据查询",
                    "meunUrl": "",
                    "sonMeuns": [
                        {
                            "meunId": 14,
                            "meunName": "职业信息",
                            "meunUrl": ""
                        },
                        {
                            "meunId": 13,
                            "meunName": "专业信息",
                            "meunUrl": ""
                        },
                        {
                            "meunId": 12,
                            "meunName": "院校招生计划",
                            "meunUrl": ""
                        },
                        {
                            "meunId": 11,
                            "meunName": "院校录取数据",
                            "meunUrl": ""
                        }
                    ]
                },
                {
                    "meunId": 6,
                    "meunName": "成绩分析",
                    "meunUrl": "",
                    "sonMeuns": [
                        {
                            "meunId": 9,
                            "meunName": "班级成绩分析",
                            "meunUrl": ""
                        },
                        {
                            "meunId": 8,
                            "meunName": "学校成绩分析",
                            "meunUrl": ""
                        },
                        {
                            "meunId": 7,
                            "meunName": "学习成绩管理",
                            "meunUrl": ""
                        }
                    ]
                },
                {
                    "meunId": 1,
                    "meunName": "高考改革",
                    "meunUrl": " ",
                    "sonMeuns": [
                        {
                            "meunId": 5,
                            "meunName": "政策解读",
                            "meunUrl": ""
                        },
                        {
                            "meunId": 4,
                            "meunName": "选课分析",
                            "meunUrl": ""
                        },
                        {
                            "meunId": 3,
                            "meunName": "三位一体招生",
                            "meunUrl": ""
                        },
                        {
                            "meunId": 2,
                            "meunName": "选课指导",
                            "meunUrl": ""
                        }
                    ]
                }
            ]
            var isSuperManager = data.isSuperManager;
            Common.cookie.setCookie('tnName', data.tnName);
            Common.cookie.setCookie('tnId', data.userId);
            Common.cookie.setCookie('isInit', isInit);




            Common.cookie.setCookie('meuns', JSON.stringify(meuns));

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
        layer.msg('出错了');
    }, 'true');
});
