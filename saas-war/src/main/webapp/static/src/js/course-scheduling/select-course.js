var tnId = Common.cookie.getCookie('tnId');
function SelectCourse() {
    this.init();
}

SelectCourse.prototype = {
    constructor: SelectCourse,
    init: function () {
        //this.querySelectCourseTask();
        this.queryGradeInfo();
    },
    // 任务列表
    querySelectCourseTask: function () {
        Common.ajaxFun('/saas/selectCourse/getSelectCourseTasks.do', 'GET', {
            "tnId":"租户ID"
        }, function (res) {
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
    // 查询年级信息
    queryGradeInfo: function () {
        var grade = [];
        grade.push('<option value="00">请选择年级</option>');
        Common.ajaxFun('/scheduleTask/queryGradeInfo.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var resData = res.bizData;
                $.each(resData, function (i, v) {
                    grade.push('<option gradeV="' + v['value'] + '" value="' + v['key'] + '">' + v['value'] + '</option>');
                });
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        return grade.join('');
    },
    // 弹层
    addOrUpdateSelectCourse: function (title, isUpdate) {
        var that = this;
        var addSelectCourseContentHtml = [];
        addSelectCourseContentHtml.push('<div class="add-select-course-box">');
        addSelectCourseContentHtml.push('<div class="select-course-box">');
        addSelectCourseContentHtml.push('<div class="box-row">');
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>名称：</span><input id="task-name" placeholder="最多20个字" type="text" />');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<div class="box-row">');
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>年级：</span><select id="grade-list">' + that.queryGradeInfo() + '</select>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<div class="box-row">');
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>设置选课开始时间：</span><input  class="date-picker" id="LAY_demorange_s" type="text" placeholder="设置选课开始时间"/>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<div class="box-row">');
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>设置选课结束时间：</span><input class="date-picker" id="LAY_demorange_e" type="text"  placeholder="设置选课结束时间"/>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<br><div class="box-row">');
        addSelectCourseContentHtml.push('<button type="button" id="save-select-course-btn">保存</button>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span class="layer-title">' + title + "</span>",
            offset: 'auto',
            area: ['500px', '350px'],
            content: addSelectCourseContentHtml.join(''),
            success: function () {
                that.getYears();
            }
        });
        layui.use('laydate', function(){
            var laydate = layui.laydate;

            var start = {
                min: laydate.now()
                ,max: '2099-06-16 23:59:59'
                ,istoday: false
                ,choose: function(datas){
                    end.min = datas; //开始日选好后，重置结束日的最小日期
                    end.start = datas //将结束日的初始值设定为开始日
                },
                elem: this,
                istime: true,
                format: 'YYYY-MM-DD hh:mm:ss'
            };

            var end = {
                min: laydate.now()
                ,max: '2099-06-16 23:59:59'
                ,istoday: false
                ,choose: function(datas){
                    start.max = datas; //结束日选好后，重置开始日的最大日期
                },
                elem: this,
                istime: true,
                format: 'YYYY-MM-DD hh:mm:ss'
            };

            document.getElementById('LAY_demorange_s').onclick = function(){
                start.elem = this;
                laydate(start);
            }
            document.getElementById('LAY_demorange_e').onclick = function(){
                end.elem = this
                laydate(end);
            }

        });
    },
    // 学期年份  当前年往前推5年
    getYears: function () {
        var years = [];
        var date = new Date();
        var year = date.getFullYear();
        for (var i = (year - 2); i < (year + 1); i++) {
            years.push('<option value="' + i + '">' + i + '</option>');
        }
        $('#term-year').append(years.join(''));
    },
    // 保存 更新
    addSelectCourseTask: function (id, selectCourseName, grade, startTime, endTime) {
        var that = this;
        if (id) {
            Common.ajaxFun('/saas/selectCourse/updateSelectCourseTask.do', 'POST', {
                "data": {
                    "id": id,
                    "tnId": tnId,
                    "name": selectCourseName,
                    "grade": grade,
                    "startTime": startTime,
                    "endTime": endTime
                }
            }, function (res) {
                if (res.rtnCode == "0000000" && res.bizData == true) {
                    that.querySelectCourseTask();
                    layer.closeAll();
                    layer.msg("保存成功");
                } else {
                    layer.msg(res.msg);
                }
            }, function (res) {
                layer.msg(res.msg);
            });
        } else {
            Common.ajaxFun('/saas/selectCourse/addSelectCourseTask.do', 'POST', {
                "data": {
                    "tnId": tnId,
                    "name": selectCourseName,
                    "grade": grade,
                    "startTime": startTime,
                    "endTime": endTime
                }
            }, function (res) {
                if (res.rtnCode == "0000000" && res.bizData == true) {
                    that.querySelectCourseTask();
                    layer.closeAll();
                    layer.msg("保存成功");
                    history.go(0);
                } else {
                    layer.msg(res.msg);
                }
            }, function (res) {
                layer.msg(res.msg);
            });
        }
    },
    // 删除任务
    deleteSelectCourseTask: function (id) {
        var that = this;
        Common.ajaxFun('/saas/selectCourse/deleteSelectCourseTask.do', 'GET', {
            'id': id
        }, function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                layer.msg('删除成功!');
                that.querySelectCourseTask();
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }


};

var SelectCourseIns = new SelectCourse();

$(function () {

    // 新建选课任务
    $('#addTask-btn').on('click', function () {
        SelectCourseIns.addOrUpdateSelectCourse('新建选课任务');
    });

    // 新建任务保存
    $('body').on('click', '#save-select-course-btn', function () {
        var id = $(this).attr('dataid');
        var taskName = $.trim($('#task-name').val());
        var gradeV = $('#grade-list').val();
        var startDate = $.trim($('#start-date').val());
        var endDate = $.trim($('#end-date').val());
        if (taskName == '') {
            layer.tips('请输入任务名称!', '#task-name');
            return false;
        }
        if (taskName.length > 20) {
            layer.tips('任务名称不能超过20个字!', '#task-name');
            return false;
        }
        if (gradeV == "00") {
            layer.tips('请选择年级!', '#grade-list');
            return false;
        }

        if (startDate == "") {
            layer.tips('请设置选课开始时间!', '#start-date');
            return false;
        }
        if (endDate == "") {
            layer.tips('请设置选课结束时间!', '#end-date');
            return false;
        }
        SelectCourseIns.addSelectCourseTask(id, taskName, gradeV, startDate, endDate);
    });

    // 修改选课任务
    $('#updateTask-btn').on('click', function () {
        var checkboxLen = $('#select-course-list input:checked').length;
        var selectCourseV = $('#select-course-list input:checked')
        if (checkboxLen == 0) {
            layer.tips('选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('修改只能选择一项', $(this));
            return false;
        }
        schedule.addOrUpdateSelectCourse('更新选课任务', true);
        var id = selectCourseV.attr('dataid');
        var schedulename = selectCourseV.attr('selectCourseName');
        var gradename = selectCourseV.attr('gradename');
        var year = selectCourseV.attr('year');
        var termname = selectCourseV.attr('termname');
        $('#task-name').val(schedulename);
        $('#grade-list').children('option[gradeV="' + gradename + '"]').attr('selected', 'selected');
        $('#term-year').val(year);
        $('#term-list').children('option[termV="' + termname + '"]').attr('selected', 'selected');
        $('#save-schedule-btn').attr('dataId', id);
    });
    // 删除选课任务
    $('#deleteTask-btn').on('click', function () {
        var checkboxLen = $('#select-course-list input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('至少选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('删除只能选择一项', $(this));
            return false;
        }
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            var id = $('#select-course-list input:checked').attr('dataid');
            SelectCourseIns.deleteSelectCourseTask(id);
        }, function () {
            layer.closeAll();
        });
    });

});