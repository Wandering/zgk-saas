var tnId = Common.cookie.getCookie('tnId');
var taskId = Common.cookie.getCookie('taskId');
function SelectCourseSettings() {
    this.init();
}

SelectCourseSettings.prototype = {
    constructor: SelectCourseSettings,
    init: function () {

    },
    // 拉取课程集合  /saas/selectCourse/getSelectCourses.do
    getSelectCourses: function () {
        Common.ajaxFun('/saas/selectCourse/getSelectCourses.do', 'GET', {
            "taskId": taskId
        }, function (res) {
           /* var res = {
                "rtnCode": "0000000",
                "msg": "",
                bizData: [{

                    "courses": "课程集合json，数据格式：[{"id":"1","name":"物理"},{"id":"2","name":"化学"}]",
                    "selectCount": "可选门数",
                    "type": "课程类型  0：高考科目  1：校本课程",
                    "taskId": taskId
                }]
            }*/
            if (res.rtnCode == "0000000") {
                $('#select-course-list').html('');
                var myTemplate = Handlebars.compile($("#task-template").html());
                Handlebars.registerHelper("addOne", function (index, options) {
                    return parseInt(index) + 1;
                });
                Handlebars.registerHelper('reStatus', function (v) {
                    //console.log(v)
                    // 0：选课未设置 1：选课未开始 2：学生选课中 3：选课结果待使用 4：选课结果已使用"
                    var result = '';
                    switch (v) {
                        case 0: // 选课未设置
                            result = '<a href="javascript: void(0);" class="start-schedule-btn">选课未设置</a>';
                            break;
                        case 1: // 选课未开始
                            result = '排课失败&nbsp;&nbsp;<a href="javascript: void(0);" class="again-schedule-btn btn-split">选课未开始</a>';
                            break;
                        case 2: // 学生选课中
                            result = '学生选课中';
                            break;
                        case 3: // 选课结果待使用
                            result = '<a href="/course-scheduling-step3" class="look-course">选课结果待使用</a>';
                            break;
                        case 4: // 选课结果已使用
                            result = '<a href="/course-scheduling-step3" class="look-course">选课结果已使用</a>';
                            break;
                        default:
                            break;
                    }

                    return result;
                });
                $('#select-course-list').html(myTemplate(res));
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    // 提交选课信息  /saas/selectCourse/saveSelectCourse.do
    saveSelectCourse: function () {

    }


};

var SelectCourseSettingsIns = new SelectCourseSettings();

$(function () {


});