/***
 * 排课任务-基本信息设置
 */

var GLOBAL_CONSTANT = {
    tnId: Common.cookie.getCookie('tnId'), //租户ID
    taskId: Common.cookie.getCookie('taskId'), //租户ID
}

//教案平齐
var JAPQModule = {
    get: function (courseId) {
        Common.ajaxFun('/baseRuleController/selectJaqpByCourseId.do', 'get', {
            'tnId': GLOBAL_CONSTANT.tnId,
            'taskId': GLOBAL_CONSTANT.taskId,
            'courseId': courseId || ''
        }, function (res) {
            var res = {
                "bizData": {
                    "baseRuleList": [
                        {
                            "courseId": "1",
                            "courseName": "语文",
                            "createDate": "",
                            "id": "1",
                            "importantType": "2",
                            "taskId": "1",
                            "teacherId": "1",
                            "teacherName": "左浩",
                            "tnId": "1"
                        },
                        {
                            "courseId": "1",
                            "courseName": "历史",
                            "createDate": "",
                            "id": "2",
                            "importantType": "3",
                            "taskId": "1",
                            "teacherId": "2",
                            "teacherName": "韩嘉琛",
                            "tnId": "1"
                        },
                        {
                            "courseId": "1",
                            "courseName": "通用技术",
                            "createDate": "",
                            "id": "2",
                            "importantType": "1",
                            "taskId": "1",
                            "teacherId": "2",
                            "teacherName": "pdeng",
                            "tnId": "1"
                        }
                    ]
                },
                "rtnCode": "0000000",
                "ts": 1481080799397
            };
            if (res.rtnCode == '0000000') {
                var tpl = Handlebars.compile($('#japq-list-tpl').html());
                $('#japq-list').html(tpl(res.bizData.baseRuleList));
            } else {
                layer.msg(res.msg);
            }
        });
    },
    set: function () {
        var paramsData = {
            "baseRuleList": [
                {//第一条
                    "id": "1",//各条相应Id
                    "importantType": 1//重要程度
                },
                {//第二条
                    "id": "2",
                    "importantType": 1
                },
                {//第二条
                    "id": "3",
                    "importantType": 1
                }
            ]
        }
        Common.ajaxFun('/baseRuleController/updateJaqpById.do', 'post', {baseRuleList: JSON.stringify(paramsData)}, function (res) {
            if (res.rtnCode === '0000000') {
                layer.msg('保存成功');
            } else {
                layer.msg(res.msg);
            }
        })
    }
}

//周任课
var ZRKModule = {
    get: function (courseId) {
        Common.ajaxFun('/baseRuleController/selectWeekByCourseId.do', 'get', {
            'tnId': GLOBAL_CONSTANT.tnId,
            'taskId': GLOBAL_CONSTANT.taskId,
            'courseId': courseId || ''
        }, function (res) {
            var res = {
                    "bizData": {
                        "baseRuleList": [
                            {
                                "courseId": "1",
                                "courseName": "通用技术",
                                "createDate": "",
                                "id": "1",
                                "importantType": "1",//重要程度
                                "taskId": "1",
                                "teacherId": "1",
                                "teacherName": "小花",
                                "tnId": "1",
                                "weekType": "2"//周内分布
                            },
                            {
                                "courseId": "2",
                                "courseName": "数学",
                                "createDate": "",
                                "id": "2",
                                "importantType": "2",//重要程度
                                "taskId": "2",
                                "teacherId": "2",
                                "teacherName": "韩嘉琛",
                                "tnId": "2",
                                "weekType": "1"//周内分布
                            },
                            {
                                "courseId": "2",
                                "courseName": "数学",
                                "createDate": "",
                                "id": "2",
                                "importantType": "3",//重要程度
                                "taskId": "2",
                                "teacherId": "2",
                                "teacherName": "韩嘉琛",
                                "tnId": "2",
                                "weekType": "2"//周内分布
                            }
                        ]
                    },
                    "rtnCode": "0000000",
                    "ts": 1481081711303
                }
            if (res.rtnCode == '0000000') {
                var tpl = Handlebars.compile($('#zrk-list-tpl').html());
                $('#zrk-list').html(tpl(res.bizData.baseRuleList));
            } else {
                layer.msg(res.msg);
            }
        });
    },
    set: function () {
        var paramsData = {
            "baseRuleList": [
                {//第一条
                    "id": "1",//各条相应Id
                    "importantType": 2//重要程度
                },
                {//第二条
                    "id": "2",
                    "importantType": 2
                },
                {//第二条
                    "id": "3",
                    "importantType": 2
                }
            ]
        }
        Common.ajaxFun('/baseRuleController/updateWeekById.do', 'post', {'baseRuleList': JSON.stringify(paramsData)}, function (res) {
            if (res.rtnCode === '0000000') {
                layer.msg('保存成功');
            } else {
                layer.msg(res.msg);
            }
        })
    }
}

var App = {
    init: function () {
        this.fetchAndRenderSubject();
        JAPQModule.get();
        this.addEvent();
    },
    fetchAndRenderSubject: function () {
        Common.ajaxFun('/scheduleTask/queryCourseInfoByTaskId.do', 'get', {
            'taskId': GLOBAL_CONSTANT.taskId
        }, function (res) {
            if (res.rtnCode == '0000000') {
                App.subjectListData = res.bizData;
                var tpl = Handlebars.compile($('#course-list-tpl').html());
                $('#course-list').html(tpl(res.bizData));
            } else {
                layer.msg(res.msg);
            }
        }, function (err) {
            layer.msg(err)
        }, 'true');
    },
    addEvent: function () {
        var that = this;
        $('.rule-tab-list li').click(function () {
            var index = $(this).index();
            if (!$(this).hasClass('on')) {
                $('.rule-tab-list li a').removeClass('active').eq($(this).index()).addClass('active');
                $('.base-rule-content').stop(true, true).hide().eq($(this).index()).show();
            }
            var tpl = Handlebars.compile($('#course-list-tpl').html());
            $('#course-list').html(tpl(App.subjectListData));   //节流操作
            ZRKModule.get();
        });
        $(document).on('change', '#course-list', function () {
            JAPQModule.get($(this).val());
        })
        $('#btn-teaching-plan').click(function () {
            JAPQModule.set();
        })
        $('#btn-week-save').click(function () {
            ZRKModule.set();
        })
    }
}
App.init();

