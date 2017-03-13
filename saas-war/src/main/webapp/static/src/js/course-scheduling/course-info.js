/**
 * Created by machengcheng on 16/12/5.
 */
//课程信息构造函数及其原型

var taskId = Common.cookie.getCookie('taskId');
var scheduleName = Common.cookie.getCookie('scheduleName');
$('.scheduleName').text(scheduleName);



//判断教室信息模块是否展示
Common.ajaxFun('/scheduleTask/getConfigRooms.do', 'GET', {
    'taskId': taskId
}, function (res) {
    if (res.rtnCode == "0000000") {
        var d = res.bizData;
        if (typeof d.msg != 'undefined') {
            $('.base-item-tab').find('li').eq(3).fadeOut().remove();
        }
    }
});







function CourseInfo() {
    this.init();
}
CourseInfo.prototype = {
    constructor: CourseInfo,
    init: function () {
        this.queryCourseInfoByTaskId(taskId);
    },
    addCourse: function (id, coursename, time) {
        var that = this;
        var timeV = '';
        if (time == "0") {
            timeV = '';
        } else {
            timeV = time;
        }
        var addCourseContentHtml = [];
        addCourseContentHtml.push('<div class="add-course-box">');
        addCourseContentHtml.push('<div class="course-box">');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span class="title">课程名称：</span><span>' + coursename + '</span>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span class="title"><i>*</i>每周课时：</span><input class="week-input" value="' + timeV + '" type="text" />节/周');
        addCourseContentHtml.push('<span class="tips">“4+1”表示4节单堂一节连堂共六课时</span>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<button type="button" id="save-course-btn" dataid="' + id + '" coursename="' + coursename + '" time="' + timeV + '">保存</button>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span class="layer-title">设置课时</span>',
            offset: 'auto',
            area: ['383px', '252px'],
            content: addCourseContentHtml.join('')
        });
    },
    // 根据任务ID获取课程信息
    queryCourseInfoByTaskId: function (taskId) {
        Common.ajaxFun('/scheduleTask/queryCourseInfoByTaskId.do', 'GET', {
                'taskId': taskId
            },
            function (res) {
                if (res.rtnCode == "0000000") {

                    var myTemplate = Handlebars.compile($("#schedule-template").html());
                    Handlebars.registerHelper("addOne", function (index, options) {
                        return parseInt(index) + 1;
                    });
                    Handlebars.registerHelper("times", function (v) {
                        if (!v) {
                            return "-";
                        } else {
                            return (v + "节/周");
                        }
                    });

                    $('#schedule-list').html(myTemplate(res));
                }
            }, function (res) {
                layer.msg(res.msg);
            });
    },
    // 添加或修改课程课时信息
    saveOrUpdateCourseTime: function (taskId, courseId, times) {
        var that = this;
        Common.ajaxFun('/scheduleTask/saveOrUpdateCourseTime.do', 'POST', {
                'taskId': taskId,
                'courseId': courseId,
                'time': times
            },
            function (res) {
                if (res.rtnCode == "0000000") {
                    that.queryCourseInfoByTaskId(taskId);
                    layer.closeAll();
                    layer.msg('保存成功!');
                }
            }, function (res) {
                layer.msg(res.msg);
            });
    }
};
var CourseInfoIns = new CourseInfo();
$(function () {
    // 设置
    $(document).on('click', '#settings-class-btn', function () {
        var checkboxLen = $('#schedule-list input:checked').length;
        var scheduleV = $('#schedule-list input:checked')
        if (checkboxLen == 0) {
            layer.tips('选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('设置课时只能选择一项', $(this));
            return false;
        }
        var id = scheduleV.attr('dataid');
        var coursename = scheduleV.attr('coursename');
        var time = scheduleV.attr('time');
        CourseInfoIns.addCourse(id, coursename, time);
    });

    // 保存
    $('body').on('click', '#save-course-btn', function () {
        var weekInput = $.trim($('.week-input').val());
        if (weekInput == "") {
            layer.tips('请输入每周课时', '.week-input');
            return false;
        }
        switch (weekInput.length){
            case 1:
                if (!(/^[1-9]\d*$/).test(weekInput)) {
                    layer.tips('请输入正确的课时', '.week-input');
                    return false;
                }
                break;
            case 3:
                var weekInputPos1 = weekInput.substr(0,1);
                var weekInputPos2 = weekInput.substr(1,1);
                var weekInputPos3 = weekInput.substr(2,1);
                if (!(/^[1-9]\d*$/).test(weekInputPos1) || !(/^[1-9]\d*$/).test(weekInputPos3)) {
                    layer.tips('请输入正确的课时', '.week-input');
                    return false;
                }
                if (weekInputPos2!="+") {
                    layer.tips('请输入正确的课时', '.week-input');
                    return false;
                }
                break;
            default:
                layer.tips('请输入正确的课时', '.week-input');
                return false;
                break;
        }
        var courseId = $(this).attr('dataid');
        CourseInfoIns.saveOrUpdateCourseTime(taskId, courseId, weekInput);
    });

    $(document).on('click', '#rule-settings', function () {
        var flag = Common.checkInfoIsPerfect(taskId);
        if (flag) {
            window.location.href = '/course-scheduling-step2';
        }
    });
    $(document).on('click', '#auto-assign-course', function () {
        var flag = Common.checkInfoIsPerfect(taskId);
        if (flag) {
            window.location.href = '/course-scheduling-step3';
        }
    });
});