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
                            "id": "4",
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
        var paramsData = [];
        for (var i = 0; i < $('#japq-list tr').length; i++) {
            var data = $('input[name="japq-'+ i +'"]:checked').val();
            paramsData.push({
                "importantType":data.split('/')[0],
                "id":data.split('/')[1],
            })
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
                            "id": "12",
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
                            "id": "11",
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
                            "id": "5",
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
        var paramsData = [];
        for (var i = 0; i < $('#zrk-list tr').length; i++) {
            var data = $('input[name="zrk-'+ i +'"]:checked').val();
            paramsData.push({
                "importantType":data.split('/')[0],
                "id":data.split('/')[1],
                "weekType":$('input[name="zrk-weekType-'+ i +'"]:checked').val()
            })
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


//日任课
var RRKModule = {
    get: function (courseId) {
        Common.ajaxFun('/baseRuleController/selectDayByCourseId.do', 'get', {
            'tnId': GLOBAL_CONSTANT.tnId,
            'taskId': GLOBAL_CONSTANT.taskId,
            'courseId': courseId || ''
        }, function (res) {
            var res = {
                "bizData": {
                    "baseRuleList": [
                        {
                            "courseId": "1",
                            "courseName": "化学",
                            "createDate": "",
                            "id": "1",
                            "importantType": "3",//重要程度
                            "taskId": "1",
                            "teacherId": "1",
                            "teacherName": "韩寒",
                            "tnId": "1",
                            "dayType": "2"//日内分布
                        },
                        {
                            "courseId": "1",
                            "courseName": "语文",
                            "createDate": "",
                            "id": "2",
                            "importantType": "2",//重要程度
                            "taskId": "1",
                            "teacherId": "1",
                            "teacherName": "韩寒1",
                            "tnId": "1",
                            "dayType": "1"//日内分布
                        }
                    ]
                },
                "rtnCode": "0000000",
                "ts": 1481081711303
            }
            if (res.rtnCode == '0000000') {
                var tpl = Handlebars.compile($('#rrk-list-tpl').html());
                $('#rrk-list').html(tpl(res.bizData.baseRuleList));
            } else {
                layer.msg(res.msg);
            }
        });
    },
    set: function () {
        var paramsData = [];
        for (var i = 0; i < $('#rrk-list tr').length; i++) {
            var data = $('input[name="rrk-'+ i +'"]:checked').val();
            paramsData.push({
                "importantType":data.split('/')[0],
                "id":data.split('/')[1],
                "dayType":$('input[name="rrk-dayType-'+ i +'"]:checked').val()
            })
        }
        Common.ajaxFun('/baseRuleController/updateDayById.do', 'post', {'baseRuleList': JSON.stringify(paramsData)}, function (res) {
            if (res.rtnCode === '0000000') {
                layer.msg('保存成功');
            } else {
                layer.msg(res.msg);
            }
        })
    }
}


//连上
var LSModule = {
    get: function (courseId) {
        Common.ajaxFun('/baseRuleController/selectDayConByCourseId.do', 'get', {
            'tnId': GLOBAL_CONSTANT.tnId,
            'taskId': GLOBAL_CONSTANT.taskId,
            'courseId': courseId || ''
        }, function (res) {
            var res = {
                "bizData": {
                    "baseRuleList": [
                        {
                            "courseId": "1",
                            "courseName": "屋里",
                            "createDate": "",
                            "id": "1",
                            "importantType": "3",//重要程度
                            "taskId": "1",
                            "teacherId": "1",
                            "teacherName": "巴拉巴拉小魔法",
                            "tnId": "1",
                            "dayConType": "1"//周内分布
                        },
                        {
                            "courseId": "1",
                            "courseName": "历史",
                            "createDate": "",
                            "id": "1",
                            "importantType": "1",//重要程度
                            "taskId": "1",
                            "teacherId": "1",
                            "teacherName": "巴拉巴拉小魔法1",
                            "tnId": "1",
                            "dayConType": "2"//周内分布
                        },
                        {
                            "courseId": "1",
                            "courseName": "政治",
                            "createDate": "",
                            "id": "1",
                            "importantType": "2",//重要程度
                            "taskId": "1",
                            "teacherId": "1",
                            "teacherName": "巴拉巴拉小魔法2",
                            "tnId": "1",
                            "dayConType": "1"//周内分布
                        },
                        {
                            "courseId": "1",
                            "courseName": "地理",
                            "createDate": "",
                            "id": "1",
                            "importantType": "3",//重要程度
                            "taskId": "1",
                            "teacherId": "1",
                            "teacherName": "巴拉巴拉小魔法3",
                            "tnId": "1",
                            "dayConType": "2"//周内分布
                        }
                    ]
                },
                "rtnCode": "0000000",
                "ts": 1481081711303
            }
            if (res.rtnCode == '0000000') {
                var tpl = Handlebars.compile($('#ls-list-tpl').html());
                $('#ls-list').html(tpl(res.bizData.baseRuleList));
            } else {
                layer.msg(res.msg);
            }
        });
    },
    set: function () {
        var paramsData = [];
        for (var i = 0; i < $('#ls-list tr').length; i++) {
            var data = $('input[name="ls-'+ i +'"]:checked').val();
            paramsData.push({
                "importantType":data.split('/')[0],
                "id":data.split('/')[1],
                "dayConType":$('input[name="ls-dayConType-'+ i +'"]:checked').val()
            })
        }
        Common.ajaxFun('/baseRuleController/updateDayConById.do', 'post', {'baseRuleList': JSON.stringify(paramsData)}, function (res) {
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
        JAPQModule.get(App.subjectListData[0].courseId);
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
            $('#course-list').attr('tabIndex', index);
            if (!$(this).hasClass('on')) {
                $('.rule-tab-list li a').removeClass('active').eq($(this).index()).addClass('active');
                $('.base-rule-content').stop(true, true).hide().eq($(this).index()).show();
            }
            var tpl = Handlebars.compile($('#course-list-tpl').html());
            $('#course-list').html(tpl(App.subjectListData));   //节流操作
            switch (parseInt(index)) {
                case 0:
                    JAPQModule.get(App.subjectListData[0].courseId);
                    break;
                case 1:
                    ZRKModule.get(App.subjectListData[0].courseId);
                    break;
                case 2:
                    RRKModule.get(App.subjectListData[0].courseId);
                    break;
                case 3:
                    LSModule.get(App.subjectListData[0].courseId);
                    break;
            }
        });
        $(document).on('change', '#course-list', function () {
            switch (parseInt($(this).attr('tabIndex'))) {
                case 0:
                    JAPQModule.get($(this).val());
                    break;
                case 1:
                    ZRKModule.get($(this).val());
                    break;
                case 2:
                    RRKModule.get($(this).val());
                    break;
                case 3:
                    LSModule.get($(this).val());
                    break;
            }
        })
        //教案平齐
        $('#btn-teaching-plan').click(function () {
            JAPQModule.set();
        })
        //周任课
        $('#btn-week-save').click(function () {
            ZRKModule.set();
        })
        //日任课
        $('#btn-day-save').click(function () {
            RRKModule.set();
        })
        //连上
        $('#btn-base-rule-save').click(function () {
            LSModule.set();
        })
    }
}
App.init();

