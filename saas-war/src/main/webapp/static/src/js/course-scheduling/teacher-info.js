/**
 * Created by machengcheng on 16/12/5.
 */
//教师信息构造函数及其原型
function TeacherInfo () {

}
TeacherInfo.prototype = {
    constructor: TeacherInfo,
    init: function () {

    },
    addOrUpdateTeacher: function (title) {
        var that = this;
        var addTeacherContentHtml = [];
        addTeacherContentHtml.push('<div class="add-teacher-box">');
        addTeacherContentHtml.push('<div class="teacher-box">');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="title"><i>*</i>教师姓名：</span><input type="text" id="teacher-keywords" />所授课程：语文');
        addTeacherContentHtml.push('<ul class="like-teacher-list">');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾静静</a></li>');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾玲</a></li>');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾斌</a></li>');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾宝玉</a></li>');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾珍</a></li>');
        addTeacherContentHtml.push('</ul>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="title"><i>*</i>最大带班数：</span>');
        addTeacherContentHtml.push('<select id="max-class-count">');
        addTeacherContentHtml.push('<option value="00">选择最大带班数</option>');
        addTeacherContentHtml.push('<option value="1">1</option>');
        addTeacherContentHtml.push('<option value="2">2</option>');
        addTeacherContentHtml.push('<option value="3">3</option>');
        addTeacherContentHtml.push('<option value="4">4</option>');
        addTeacherContentHtml.push('<option value="5">5</option>');
        addTeacherContentHtml.push('</select>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="title">所带班级：</span>');
        addTeacherContentHtml.push('<ul class="teaching-class-list">');
        addTeacherContentHtml.push('<li><input type="checkbox" id="item1" /><label for="item1">通用技术1班</label></li>');
        addTeacherContentHtml.push('<li><input type="checkbox" id="item2" /><label for="item2">通用技术2班</label></li>');
        addTeacherContentHtml.push('<li><input type="checkbox" id="item3" /><label for="item3">通用技术3班</label></li>');
        addTeacherContentHtml.push('</ul>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="tips">若需要设置某老师必须带某班时，则设置“所带班级”项</span>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<button type="button" id="save-course-btn">保存</button>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span class="layer-title">' + title + "</span>",
            offset: 'auto',
            area: ['471px', '303px'],
            content: addTeacherContentHtml.join('')
        });
    }
};


$(function () {

    var teacherInfo = new TeacherInfo();
    $(document).on('click', '#add-teacher-btn', function () {
        teacherInfo.addOrUpdateTeacher('添加教师');
    });

    $(document).on('mouseover', '#teacher-keywords', function () {
        $('.like-teacher-list').show();
    });

    $(document).on('click', function (e) {
        $('.like-teacher-list').hide();
        e.stopPropagation();
    });

    $(document).on('click', '#modify-teacher-btn', function () {
        teacherInfo.addOrUpdateTeacher('修改教师');
    });

});