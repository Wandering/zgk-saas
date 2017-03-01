var tnId = Common.cookie.getCookie('tnId');
function SelectCourseResult() {
    this.init();
}

SelectCourseResult.prototype = {
    constructor: SelectCourseResult,
    init: function () {

    },
    // 获取选课概况
    getSelectCourseSurvey:function(){
        Common.ajaxFun('/saas/selectCourse/getSelectCourseSurvey.do', 'GET', {
            "taskId":taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                /*

                 bizData:{
                 "name":"选课名称",
                 "grade":年级编号 1：高一  2：高二  3：高三",
                 "startTime":"开始时间：2017-02-28 13:00:00",
                 "endTime":"结束时间：2017-02-28 13:00:00",
                 "selectedCount":"已选学生数",
                 "unSelectedCount":"未选学生数",
                 "unSelectedList":[{  // 未选学生集合
                 "className":"班级名称",
                 "stuName":"学生名称",
                 "stuNo":"学生学号"
                 }]
                 }

                 */



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
    }

    // 单科选课结果

    // 组合选课结果

    // 查询学生高考课程选课详情（带分页）

    // 修改学生选课信息

    // 确认使用选课数据



};

var SelectCourseResultIns = new SelectCourseResult();

$(function () {



});