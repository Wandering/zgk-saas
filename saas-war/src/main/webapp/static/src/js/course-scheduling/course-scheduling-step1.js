


function TeachDate(){
    this.init();
}

TeachDate.prototype = {
    constructor: TeachDate,
    init: function () {

    },
    // 查询学习时间
    queryTeachTime:function(taskId){
        Common.ajaxFun('/teachTime/queryTeachTime.do', 'GET', {
            'taskId':taskId
        },
            function (res) {
                console.log(res)
                if (res.rtnCode == "0000000") {
                    var teachDate = res.bizData.teachDate;
                    var teachTime = res.bizData.teachTime;





                }
            }, function (res) {
                layer.msg(res.msg);
            });
    },
    // 保存教学时间
    saveTeachTime:function(taskId,teachDate,teachTime){
        Common.ajaxFun('/teachTime/saveTeachTime.do', 'POST', {
            'taskId':taskId,
            'teachDate':teachDate,
            'teachTime':teachTime
        },
        function (res) {
            if (res.rtnCode == "0000000" && res.bizData==true) {
                layer.msg('保存成功!');
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }
};

var TeachDateIns = new TeachDate();

$(function(){
    var taskId = Common.cookie.getCookie('taskId');
    // 保存
    $('#btn-save-base').on('click',function(){
        var checkboxLen = $('.week-list input:checked').length;
        var weekV= $('.week-list input:checked');
        if (checkboxLen == 0) {
            layer.tips('每周上课天数至少选择一项', $(this));
            return false;
        }
        var teachDate = [];
        $.each(weekV,function(i,v){
            teachDate.push($(this).attr('data'));
        });
        var morningNum = $('#morning-list').val();
        var afternoonNum = $('#afternoon-list').val();
        var eveningNum = $('#evening-list').val();
        var teachTime = morningNum+afternoonNum+eveningNum;
        TeachDateIns.saveTeachTime(taskId,teachDate.join('|'),teachTime);
    });

    //
    TeachDateIns.queryTeachTime(taskId);
});





















////课程信息构造函数及其原型
//function CourseInfo () {
//
//}
//CourseInfo.prototype = {
//    constructor: CourseInfo,
//    init: function () {
//
//    },
//    addCourse: function (title) {
//        var that = this;
//        var addCourseContentHtml = [];
//        addCourseContentHtml.push('<div class="add-course-box">');
//        addCourseContentHtml.push('<div class="course-box">');
//        addCourseContentHtml.push('<div class="box-row">');
//        addCourseContentHtml.push('<span class="title">课程名称：</span><span>语文</span>');
//        addCourseContentHtml.push('</div>');
//        addCourseContentHtml.push('<div class="box-row">');
//        addCourseContentHtml.push('<span class="title"><i>*</i>每周课时：</span><input type="text" />节/周');
//        addCourseContentHtml.push('<span class="tips">“4+1”表示4节单堂一节连堂共六课时</span>');
//        addCourseContentHtml.push('</div>');
//        addCourseContentHtml.push('<div class="box-row">');
//        addCourseContentHtml.push('<button type="button" id="save-course-btn">保存</button>');
//        addCourseContentHtml.push('</div>');
//        addCourseContentHtml.push('</div>');
//        addCourseContentHtml.push('</div>');
//        layer.open({
//            type: 1,
//            title: '<span class="layer-title">' + title + "</span>",
//            offset: 'auto',
//            area: ['383px', '252px'],
//            content: addCourseContentHtml.join('')
//        });
//    }
//};
//
////教师信息构造函数及其原型
//function TeacherInfo () {
//
//}
//TeacherInfo.prototype = {
//    constructor: TeacherInfo,
//    init: function () {
//
//    },
//    addOrUpdateTeacher: function (title) {
//        var that = this;
//        var addTeacherContentHtml = [];
//        addTeacherContentHtml.push('<div class="add-teacher-box">');
//        addTeacherContentHtml.push('<div class="teacher-box">');
//        addTeacherContentHtml.push('<div class="box-row">');
//        addTeacherContentHtml.push('<span class="title"><i>*</i>教师姓名：</span><input type="text" id="teacher-keywords" />所授课程：语文');
//        addTeacherContentHtml.push('<ul class="like-teacher-list">');
//        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾静静</a></li>');
//        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾玲</a></li>');
//        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾斌</a></li>');
//        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾宝玉</a></li>');
//        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾珍</a></li>');
//        addTeacherContentHtml.push('</ul>');
//        addTeacherContentHtml.push('</div>');
//        addTeacherContentHtml.push('<div class="box-row">');
//        addTeacherContentHtml.push('<span class="title"><i>*</i>最大带班数：</span>');
//        addTeacherContentHtml.push('<select id="max-class-count">');
//        addTeacherContentHtml.push('<option value="00">选择最大带班数</option>');
//        addTeacherContentHtml.push('<option value="1">1</option>');
//        addTeacherContentHtml.push('<option value="2">2</option>');
//        addTeacherContentHtml.push('<option value="3">3</option>');
//        addTeacherContentHtml.push('<option value="4">4</option>');
//        addTeacherContentHtml.push('<option value="5">5</option>');
//        addTeacherContentHtml.push('</select>');
//        addTeacherContentHtml.push('</div>');
//        addTeacherContentHtml.push('<div class="box-row">');
//        addTeacherContentHtml.push('<span class="title">所带班级：</span>');
//        addTeacherContentHtml.push('<ul class="teaching-class-list">');
//        addTeacherContentHtml.push('<li><input type="checkbox" id="item1" /><label for="item1">通用技术1班</label></li>');
//        addTeacherContentHtml.push('<li><input type="checkbox" id="item2" /><label for="item2">通用技术2班</label></li>');
//        addTeacherContentHtml.push('<li><input type="checkbox" id="item3" /><label for="item3">通用技术3班</label></li>');
//        addTeacherContentHtml.push('</ul>');
//        addTeacherContentHtml.push('</div>');
//        addTeacherContentHtml.push('<div class="box-row">');
//        addTeacherContentHtml.push('<span class="tips">若需要设置某老师必须带某班时，则设置“所带班级”项</span>');
//        addTeacherContentHtml.push('</div>');
//        addTeacherContentHtml.push('<div class="box-row">');
//        addTeacherContentHtml.push('<button type="button" id="save-course-btn">保存</button>');
//        addTeacherContentHtml.push('</div>');
//        addTeacherContentHtml.push('</div>');
//        addTeacherContentHtml.push('</div>');
//        layer.open({
//            type: 1,
//            title: '<span class="layer-title">' + title + "</span>",
//            offset: 'auto',
//            area: ['471px', '303px'],
//            content: addTeacherContentHtml.join('')
//        });
//    }
//};
//
//
//$(function () {
//
//    //$('.base-item-tab li').click(function(){
//    //    if(!$(this).hasClass('on')){
//    //        $('.base-item-tab li a').removeClass('active').eq($(this).index()).addClass('active');
//    //        $('.base-content').stop(true,true).hide().eq($(this).index()).show();
//    //    }
//    //});
//
//    $('#morning-list').val(4);
//    $('#afternoon-list').val(3);
//    $('#evening-list').val(0);
//
//    var courseInfo = new CourseInfo();
//    $(document).on('click', '#settings-class-btn', function () {
//        courseInfo.addCourse('添加课程');
//    });
//
//    var teacherInfo = new TeacherInfo();
//    $(document).on('click', '#add-teacher-btn', function () {
//        teacherInfo.addOrUpdateTeacher('添加教师');
//    });
//
//    $(document).on('mouseover', '#teacher-keywords', function () {
//        $('.like-teacher-list').show();
//    });
//
//    $(document).on('click', function (e) {
//        $('.like-teacher-list').hide();
//        e.stopPropagation();
//    });
//
//    $(document).on('click', '#modify-teacher-btn', function () {
//        teacherInfo.addOrUpdateTeacher('修改教师');
//    });
//
//});