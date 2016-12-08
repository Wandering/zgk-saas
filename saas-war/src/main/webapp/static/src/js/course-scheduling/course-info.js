/**
 * Created by machengcheng on 16/12/5.
 */
//课程信息构造函数及其原型

var taskId = Common.cookie.getCookie('taskId');

function CourseInfo() {
    this.init();
}
CourseInfo.prototype = {
    constructor: CourseInfo,
    init: function () {
        this.queryCourseInfoByTaskId(taskId);
    },
    addCourse: function (id,coursename,time) {
        var that = this;
        var addCourseContentHtml = [];
        addCourseContentHtml.push('<div class="add-course-box">');
        addCourseContentHtml.push('<div class="course-box">');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span class="title">课程名称：</span><span>'+ coursename +'</span>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span class="title"><i>*</i>每周课时：</span><input class="week-input" value="'+ time +'" type="text" />节/周');
        addCourseContentHtml.push('<span class="tips">“4+1”表示4节单堂一节连堂共六课时</span>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<button type="button" id="save-course-btn" dataid="'+ id +'" coursename="'+ coursename +'" time="'+ time +'">保存</button>');
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
                var res = {
                    "bizData": [
                        {
                            "courseId": "课程ID",
                            "courseName": "课程名",
                            "time": "课时"
                        },
                        {
                            "courseId": "课程ID",
                            "courseName": "课程名",
                            "time": "课时"
                        },
                        {
                            "courseId": "课程ID",
                            "courseName": "课程名",
                            "time": "课时"
                        }
                    ],
                    "rtnCode": "0000000",
                    "ts": 1480990693884
                };
                if (res.rtnCode == "0000000") {
                    var myTemplate = Handlebars.compile($("#teacher-template").html());
                    Handlebars.registerHelper("addOne", function (index, options) {
                        return parseInt(index) + 1;
                    });
                    $('#teacher-list').html(myTemplate(res));
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
        var scheduleV= $('#schedule-list input:checked')
        if (checkboxLen == 0) {
            layer.tips('选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('修改只能选择一项', $(this));
            return false;
        }
        var id = scheduleV.attr('dataid');
        var coursename = scheduleV.attr('coursename');
        var time = scheduleV.attr('time');
        CourseInfoIns.addCourse(id,coursename,time);
    });

    // 保存
    $('body').on('click', '#save-course-btn', function () {
        var weekInput = $.trim($('.week-input').val());
        if (weekInput == "") {
            layer.tips('请输入每周课时', '.week-input');
            return false;
        }
        var courseId = $(this).attr('dataid');
        var times = $(this).attr('time');
        CourseInfoIns.saveOrUpdateCourseTime(taskId, courseId, times);
    });
});