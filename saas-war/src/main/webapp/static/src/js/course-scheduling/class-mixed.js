/**
 * @wiki: http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44458344
 * 全局常量
 * @type {{tnId: *, typ: string}}
 */
var GLOBAL_CONSTANT = {
    tnId: Common.cookie.getCookie('tnId'), //租户ID
    taskId: Common.cookie.getCookie('taskId'), //租户ID
}

/**
 * 排课任务 -> 排课规则设置 ->合班
 * @type {{init: MixedClass.init, fetchAndRenderSubject: MixedClass.fetchAndRenderSubject, fetchBelongClass: MixedClass.fetchBelongClass}}
 */
var MixedClass = {
    init: function () {
        this.fetchAndRenderSubject();
        this.fetchBelongClass();
        console.info(Common.cookie.getCookie('taskId'));
    },
    fetchAndRenderSubject: function () {
        Common.ajaxFun('/scheduleTask/queryCourseInfoByTaskId.do', 'get', {
            'taskId': GLOBAL_CONSTANT.taskId
        }, function (res) {
            if (res.rtnCode == '0000000') {
                var tpl = Handlebars.compile($('#course-select-tpl').html());
                $('#course-select').html(tpl(res.bizData));
            } else {
                layer.msg(res.msg);
            }
        });
    },
    fetchBelongClass: function () {
        Common.ajaxFun('/manage/class/' + GLOBAL_CONSTANT.tnId + '/getTenantCustomData.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = Handlebars.compile($('#choose-class-list-tpl').html());
                $('#choose-class-list').html(tpl(res.bizData.result));
            }
        });
    },
    addEvent: function () {
        var that = this;
        $('.mixed-class-btn').click(function () {
            that.submitMixedClass();
        });
    },
    submitMixedClass: function () {
        var that = this;
        Common.ajaxFun('/mergeClassController/addMergeInfo.do', 'get', {
            'tnId': GLOBAL_CONSTANT.tnId,
            'taskId': GLOBAL_CONSTANT.taskId,
            'courseId': '',
            'classIds': '',//班级Id，多个班级有英文逗号分隔
        }, function (res) {
            if (res.rtnCode === '0000000') {
                console.info(res);
                that.fetchResult();
            } else {
                layer.msg(res.msg);
            }
        });
    },
    fetchResult: function () {
        var that = this;
        Common.ajaxFun('/mergeClassController/getMergeInfo.do', 'get', {
            'tnId': GLOBAL_CONSTANT.tnId,
            'taskId': GLOBAL_CONSTANT.taskId,
            'grade': '',
        }, function (res) {
            if (res.rtnCode === '0000000') {
                that.renderResult(res.bizData)
            } else {
                layer.msg(res.msg);
            }
        });
    },
    renderResult:function(dataJson){
        console.info('dataJson',dataJson);
    }
}
MixedClass.init();