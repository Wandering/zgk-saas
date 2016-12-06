/**
 * machengcheng 2016-12-05
 */

//排课构造函数及其原型
function Schedule () {

}
Schedule.prototype = {
    constructor: Schedule,
    init: function () {

    },
    addOrUpdateSchedule: function (title) {
        var that = this;
        var addScheduleContentHtml = [];
        addScheduleContentHtml.push('<div class="add-schedule-box">');
        addScheduleContentHtml.push('<div class="schedule-box">');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<span><i>*</i>名称：</span><input id="name" type="text" />');
        addScheduleContentHtml.push('</div>');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<span><i>*</i>年级：</span><select id="grade-list"><option value="00">请选择年级</option></select>');
        addScheduleContentHtml.push('</div>');
        addScheduleContentHtml.push('<div class="box-row">');
        addScheduleContentHtml.push('<span><i>*</i>学期：</span><input id="term-year" type="text" />年');
        addScheduleContentHtml.push('<select id="term-list">');
        addScheduleContentHtml.push('<option value="00">请选择学期</option>');
        addScheduleContentHtml.push('<option value="第一学期">第一学期</option>');
        addScheduleContentHtml.push('<option value="第二学期">第二学期</option>');
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
            content: addScheduleContentHtml.join('')
        });
    }
};

var schedule = new Schedule();

$(function () {

    $(document).on('click', '#addRole-btn', function () {
        schedule.addOrUpdateSchedule('新建排课任务');
    });

    $(document).on('click', '#updateRole-btn', function () {
        schedule.addOrUpdateSchedule('更新排课任务');
    });

});