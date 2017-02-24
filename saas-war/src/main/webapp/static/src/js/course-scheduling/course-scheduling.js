/**
 * machengcheng 2016-12-05
 */

//排课构造函数及其原型
function Schedule() {
    this.init();
}

Schedule.prototype = {
    constructor: Schedule,
    init: function () {
        this.queryScheduleTask();
        this.queryGradeInfo();
    },
    // 任务列表
    queryScheduleTask: function () {
        Common.ajaxFun('/scheduleTask/queryScheduleTask.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                $('#schedule-list').html('');
                var myTemplate = Handlebars.compile($("#task-template").html());
                Handlebars.registerHelper("addOne", function (index, options) {
                    return parseInt(index) + 1;
                });
                Handlebars.registerHelper('reStatus', function (v) {
                    //console.log(v)
                    var result = '';
                    switch (v) {
                        case 1: // 开始排课
                            result = '<a href="javascript: void(0);" class="start-schedule-btn">开始排课</a>';
                            break;
                        case 2: // 排课失败,重新排课
                        case 5: // 排课失败,重新排课
                            result = '排课失败&nbsp;&nbsp;<a href="javascript: void(0);" class="again-schedule-btn btn-split">重新排课</a>';
                            break;
                        case 3: // 排课中
                            result = '排课中';
                            break;
                        case 4: // 查看课表,重新排课
                            result = '<a href="/course-scheduling-step3">查看课表</a><a href="/course-scheduling-step1" class="again-schedule-btn btn-split">重新排课</a>';
                            break;
                        default:
                            break;
                    }
                    return result;
                });
                $('#schedule-list').html(myTemplate(res));
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
    addOrUpdateSchedule: function (title, isUpdate) {
        var that = this;
        var addScheduleContentHtml = [];
        addScheduleContentHtml.push('<div class="add-schedule-box">');
        addScheduleContentHtml.push('<div class="schedule-box">');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<span><i>*</i>名称：</span><input id="task-name" placeholder="最多20个字" type="text" />');
        addScheduleContentHtml.push('</div>');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<span><i>*</i>年级：</span><select id="grade-list">' + that.queryGradeInfo() + '</select>');
        addScheduleContentHtml.push('</div>');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<span><i>*</i>学期：</span>');
        addScheduleContentHtml.push('<select id="term-year">');
        addScheduleContentHtml.push('<option value="00">请选择年份</option>');
        addScheduleContentHtml.push('</select>');
        addScheduleContentHtml.push('年<select id="term-list">');
        addScheduleContentHtml.push('<option value="00">请选择学期</option>');
        addScheduleContentHtml.push('<option termV="第一学期" value="1">第一学期</option>');
        addScheduleContentHtml.push('<option termV="第二学期" value="2">第二学期</option>');
        addScheduleContentHtml.push('</select>');
        addScheduleContentHtml.push('</div>');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<button type="button" id="save-schedule-btn">保存</button>');
        addScheduleContentHtml.push('</div>');
        addScheduleContentHtml.push('</div>');
        addScheduleContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span class="layer-title">' + title + "</span>",
            offset: 'auto',
            area: ['362px', '281px'],
            content: addScheduleContentHtml.join(''),
            success: function () {
                that.getYears();
            }
        });
        if (isUpdate) {
            $('#grade-list, #term-year, #term-list').prop('disabled', true);
            $('#grade-list, #term-year, #term-list').css({'cursor': 'not-allowed'});
        }
    },
    // 学期年份  当前年往前推5年
    getYears:function(){
        var years = [];
        var date = new Date();
        var year = date.getFullYear();
        for(var i = (year-2);i<(year+1);i++){
            years.push('<option value="'+ i +'">'+ i +'</option>');
        }
        $('#term-year').append(years.join(''));
    },
    // 保存 更新
    saveScheduleTask: function (id, scheduleName, grade, year, term) {
        var that = this;
        if (id) {
            Common.ajaxFun('/scheduleTask/updateScheduleTask.do', 'POST', {
                'id': id,
                'scheduleName': scheduleName,
                'grade': grade,
                'year': year,
                'term': term
            }, function (res) {
                if (res.rtnCode == "0000000" && res.bizData == true) {
                    that.queryScheduleTask();
                    layer.closeAll();
                    layer.msg("保存成功");
                    history.go(0);
                } else {
                    layer.msg(res.msg);
                }
            }, function (res) {
                layer.msg(res.msg);
            });
        } else {
            Common.ajaxFun('/scheduleTask/saveScheduleTask.do', 'POST', {
                'scheduleName': scheduleName,
                'grade': grade,
                'year': year,
                'term': term
            }, function (res) {
                if (res.rtnCode == "0000000" && res.bizData == true) {
                    that.queryScheduleTask();
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
    deleteScheduleTask: function (id) {
        var that = this;
        Common.ajaxFun('/scheduleTask/deleteScheduleTask.do', 'GET', {
            'id': id
        }, function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                layer.msg('删除成功!');
                that.queryScheduleTask();
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 查询排课基础信息完整性
    checkTaskBaseInfo: function (taskId) {
        var that = this;
        Common.ajaxFun('/scheduleTask/checkTaskBaseInfo.do', 'GET', {
            'taskId': taskId
        }, function (res) {
            if (res.rtnCode == "0000000" && res.bizData == true) {
                Common.cookie.setCookie('taskId', taskId);
                window.location.href = '/course-scheduling-step1';
            } else if (res.rtnCode == "0500001") {
                layer.open({
                    title:'温馨提示',
                    content: res.msg
                });
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }

};

var schedule = new Schedule();

$(function () {


    var scheduleListlen =  $('#schedule-list tr').length;
    console.log(scheduleListlen)
    if(scheduleListlen=='0'){
        $('#updateTask-btn,#deleteTask-btn,#schedule-table').hide();
    }else{
        $('#updateTask-btn,#deleteTask-btn,#schedule-table').show();
    }

    // 新建任务
    $(document).on('click', '#addTask-btn', function () {
        schedule.addOrUpdateSchedule('新建排课任务', false);
    });
    // 新建任务保存
    $('body').on('click', '#save-schedule-btn', function () {
        var id = $(this).attr('dataid');
        var taskName = $.trim($('#task-name').val());
        var gradeV = $('#grade-list').val();
        var termYear = $('#term-year').val();
        var termV = $('#term-list').val();
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

        if (termV == "00") {
            layer.tips('请选择学期!', '#term-list');
            return false;
        }
        schedule.saveScheduleTask(id, taskName, gradeV, termYear, termV);
    });

    // 修改新建任务
    $(document).on('click', '#updateTask-btn', function () {
        var checkboxLen = $('#schedule-list input:checked').length;
        var scheduleV = $('#schedule-list input:checked')
        if (checkboxLen == 0) {
            layer.tips('选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('修改只能选择一项', $(this));
            return false;
        }
        schedule.addOrUpdateSchedule('更新排课任务', true);
        var id = scheduleV.attr('dataid');
        var schedulename = scheduleV.attr('schedulename');
        var gradename = scheduleV.attr('gradename');
        var year = scheduleV.attr('year');
        var termname = scheduleV.attr('termname');
        $('#task-name').val(schedulename);
        $('#grade-list').children('option[gradeV="' + gradename + '"]').attr('selected', 'selected');
        $('#term-year').val(year);
        $('#term-list').children('option[termV="' + termname + '"]').attr('selected', 'selected');
        $('#save-schedule-btn').attr('dataId', id);
    });
    // 删除
    $('#deleteTask-btn').on('click', function () {
        var checkboxLen = $('#schedule-list input:checked').length;
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
            var id = $('#schedule-list input:checked').attr('dataid');
            schedule.deleteScheduleTask(id);
        }, function () {
            layer.closeAll();
        });
    });
    // 跳转进入流程设置,检测完整性
    $('.start-schedule-btn, .timetable-btn, .again-schedule-btn').on('click', function () {
        var id = $(this).parent().attr('dataid');
        var gradeName = $(this).parent().attr('gradeName');
        var scheduleName = $(this).parent().attr('scheduleName');
        Common.cookie.setCookie('gradeName', gradeName);
        Common.cookie.setCookie('scheduleName', scheduleName);
        Common.cookie.setCookie('taskId', id);
        schedule.checkTaskBaseInfo(id);
    });
});