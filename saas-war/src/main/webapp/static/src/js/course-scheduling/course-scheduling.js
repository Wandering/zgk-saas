/**
 * machengcheng 2016-12-05
 */

//排课构造函数及其原型
function Schedule () {
    this.init();
}

Schedule.prototype = {
    constructor: Schedule,
    init: function () {
        this.queryScheduleTask();
        this.queryGradeInfo();
    },
    // 任务列表
    queryScheduleTask:function(){
        Common.ajaxFun('/scheduleTask/queryScheduleTask.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {

            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        },true);
    },
    // 查询年级信息
    queryGradeInfo:function(){
        var grade = [];
        Common.ajaxFun('/scheduleTask/queryGradeInfo.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var resData = res.bizData;
                grade.push('<option value="00">请选择年级</option>');
                $.each(resData,function(i,v){
                    grade.push('<option value="'+ v['key'] +'">'+ v['value'] +'</option>');
                });
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        },true);
        return grade.join('');
    },
    addOrUpdateSchedule: function (title) {
        var that = this;
        var addScheduleContentHtml = [];
        addScheduleContentHtml.push('<div class="add-schedule-box">');
        addScheduleContentHtml.push('<div class="schedule-box">');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<span><i>*</i>名称：</span><input id="task-name" placeholder="最多20个字" type="text" />');
        addScheduleContentHtml.push('</div>');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<span><i>*</i>年级：</span><select id="grade-list">'+ that.queryGradeInfo() +'</select>');
        addScheduleContentHtml.push('</div>');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<span><i>*</i>学期：</span><input class="term-year" placeholder="请选择年份" readonly="readonly" data-date-format="yyyy" type="text" />年');
        addScheduleContentHtml.push('<select id="term-list">');
        addScheduleContentHtml.push('<option value="00">请选择学期</option>');
        addScheduleContentHtml.push('<option value="1">第一学期</option>');
        addScheduleContentHtml.push('<option value="2">第二学期</option>');
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
            success:function(){
                $('#term-year').datepicker(
                    { format: 'yy'},
                    {autoclose: true}
                );
            }
        });
    }
};

var schedule = new Schedule();

$(function () {



    // 新建任务
    $(document).on('click', '#addRole-btn', function () {
        schedule.addOrUpdateSchedule('新建排课任务');
    });
    // 新建任务保存

    $('body').on('click','#save-schedule-btn',function(){
        var taskName = $.trim($('#task-name').val());
        var gradeV = $('#grade-list').val();
        if(taskName==''){
            layer.tips('请输入任务名称!', '#task-name');
            return false;
        }
        if(taskName.length>20){
            layer.tips('任务名称不能超过20个字!', '#task-name');
            return false;
        }
        if(gradeV=="00"){
            layer.tips('请选择年级!', '#grade-list');
            return false;
        }



    });

    $(document).on('click', '#updateRole-btn', function () {
        schedule.addOrUpdateSchedule('更新排课任务');
    });

});