
//排课构造函数及其原型
function SelectCourse() {
    this.init();
}

SelectCourse.prototype = {
    constructor: SelectCourse,
    init: function () {
        this.queryScheduleTask();
        this.queryGradeInfo();
    },
    // 任务列表
    queryScheduleTask: function () {

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
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>设置选课开始时间：</span><input class="date-picker" id="start-date" type="text" data-date-format="yyyy-mm-dd" placeholder="设置选课开始时间"/>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<div class="box-row">');
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>设置选课结束时间：</span><input class="date-picker" id="end-date" type="text" data-date-format="yyyy-mm-dd" placeholder="设置选课结束时间"/>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<br><div class="box-row">');
        addSelectCourseContentHtml.push('<button type="button" id="save-schedule-btn">保存</button>');
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
        //$('.date-picker').datepicker({autoclose: true});
        var nowTemp = new Date();
        var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
        var checkin = $('#start-date').fdatepicker({
            onRender: function (date) {
                return date.valueOf() < now.valueOf() ? 'disabled' : '';
            }
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf() > checkout.date.valueOf()) {
                var newDate = new Date(ev.date)
                newDate.setDate(newDate.getDate() + 1);
                checkout.update(newDate);
            }
            checkin.hide();
            $('#end-date')[0].focus();
        }).data('datepicker');
        var checkout = $('#end-date').fdatepicker({
            onRender: function (date) {
                return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
            }
        }).on('changeDate', function (ev) {
            checkout.hide();
        }).data('datepicker');
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
    }


};

var SelectCourseIns = new SelectCourse();

$(function () {

    // 新建选课任务
    $('#addTask-btn').on('click',function(){
        SelectCourseIns.addOrUpdateSelectCourse('新建选课任务');
    });
    // 修改选课任务
    $('#updateTask-btn').on('click',function(){

    });
    // 删除选课任务
    $('#deleteTask-btn').on('click',function(){

    });

});