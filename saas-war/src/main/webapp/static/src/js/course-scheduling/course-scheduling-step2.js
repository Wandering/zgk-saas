/**
 * Created by machengcheng on 16/12/6.
 */
var taskId = Common.cookie.getCookie('taskId');

function ArrangeCourse () {
    this.types = ['class', 'teacher', 'course'];
    this.classids = [];
    this.init();
}
ArrangeCourse.prototype = {
    constructor: ArrangeCourse,
    init: function () {
        this.getNoArrangeCourseList(this.types[0], '');
    },
    //获取不排课信息
    getNoArrangeCourseInfo: function (type, id) {
        var that = this;
        if (id != undefined) {
            Common.ajaxFun('/disSelectRule/getRule/' + taskId + '/' + type + '/' + id + '.do', 'GET', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    var data = res.bizData;
                    if (data.length != 0) {
                        data = res.bizData[0];
                        var weeks = [],
                            weeksData = [],
                            week0 = data.mon, //周一
                            week1 = data.tues, //周二
                            week2 = data.wed, //周三
                            week3 = data.thur, //周四
                            week4 = data.fri, //周五
                            week5 = data.sut, //周六
                            week6 = data.sun,  //周日
                            classCount = week0.split('').length ||
                                week1.split('').length ||
                                week2.split('').length ||
                                week3.split('').length ||
                                week4.split('').length ||
                                week5.split('').length ||
                                week6.split('').length;
                        if (week0 != '') {
                            weeks.push('星期一');
                            weeksData.push(week0);
                        }
                        if (week1 != '') {
                            weeks.push('星期二');
                            weeksData.push(week1);
                        }
                        if (week2 != '') {
                            weeks.push('星期三');
                            weeksData.push(week2);
                        }
                        if (week3 != '') {
                            weeks.push('星期四');
                            weeksData.push(week3);
                        }
                        if (week4 != '') {
                            weeks.push('星期五');
                            weeksData.push(week4);
                        }
                        if (week5 != '') {
                            weeks.push('星期六');
                            weeksData.push(week5);
                        }
                        if (week6 != '') {
                            weeks.push('星期日');
                            weeksData.push(week6);
                        }
                        var weekHtml = [],//表头
                            classesHtml = [];//表内容
                        weekHtml.push('<tr>');
                        weekHtml.push('<th width="135px">&nbsp;</th>');
                        $.each(weeks, function (i, k) {
                            weekHtml.push('<th>' + k + '</th>');
                        });
                        weekHtml.push('</tr>');
                        $('#no-assign-table thead').html(weekHtml.join(''));

                        for (var i = 0; i < classCount; i++) {
                            if (i != 0) {
                                classesHtml.push('<tr>');
                                classesHtml.push('<td class="order">' + (i + 1) + '</td>');
                                if (weeks.length != 0) {
                                    for (var j = 0; j < weeks.length; j++) {
                                        var curIndex = "week" + j;
                                        var tempCourse = weeksData[j].split('')[i] == 1 ? '排课' : '不排课';
                                        classesHtml.push('<td week=' + curIndex + '>' + tempCourse + '</td>');
                                    }
                                }
                            } else {
                                classesHtml.push('<tr>');
                                classesHtml.push('<td class="order">' + (i + 1) + '</td>');
                                if (weeks.length != 0) {
                                    for (var j = 0; j < weeks.length; j++) {
                                        var curIndex = "week" + j;
                                        var tempCourse = weeksData[j].split('')[i] == 1 ? '排课' : '不排课';
                                        classesHtml.push('<td weekcourse="weekcourse" week=' + curIndex + '>' + tempCourse + '</td>');
                                    }
                                }
                            }
                            classesHtml.push('</tr>');
                        }
                        $('#no-assign-table tbody').html(classesHtml.join(''));
                    } else {
                        that.getTeachTime();
                    }
                }
            }, function (res) {
                layer.msg("出错了", {time: 1000});
            }, true);
        } else {
            that.getTeachTime();
        }
    },
    //不排课列表查询
    getNoArrangeCourseList: function (type, teacherCourse) {
        var that = this;
        var paramsObj = {};
        if (type == 'teacher') {
            paramsObj['teacherCourse'] = teacherCourse;
        }
        Common.ajaxFun('/disSelectRule/list/' + type + '/' + taskId, 'GET', paramsObj, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                var contentHtml = [];
                switch(type) {
                    case 'class':
                        contentHtml.push('<li>');
                        contentHtml.push('<a href="javascript: void(0);" class="active">全部班级</a>');
                        contentHtml.push('</li>');
                        $.each(data, function (i, k) {
                            contentHtml.push('<li>');
                            contentHtml.push('<a href="javascript: void(0);" gradeid="' + k.grade + '" classid="' + k.id + '">' + k.name + '</a>');
                            contentHtml.push('</li>');
                            that.classids.push(k.id);
                        });
                        $('#class-list').html(contentHtml.join(''));
                        var classId = $('#class-list a[class="active"]').attr('classid');
                        if (classId == '' || classId == undefined || classId == null) {
                            classId = $('#class-list a[class="active"]').parent().next().find('a').attr('classid');
                        }
                        that.getNoArrangeCourseInfo('class', classId);
                        $('.class-list li').click(function(){
                            if(!$(this).hasClass('on')){
                                $('.class-list li a').removeClass('active').eq($(this).index()).addClass('active');
                            }
                            var classId = $('#class-list a[class="active"]').attr('classid');
                            if (classId == '' || classId == undefined || classId == null) {
                                classId = null;
                            }
                            that.getNoArrangeCourseInfo('class', classId);
                        });
                        break;
                    case 'teacher':
                        $.each(data, function (i, k) {
                            if (i != 0) {
                                contentHtml.push('<li>');
                                contentHtml.push('<a href="javascript: void(0);" gradeid="' + k.grade + '" teacherid="' + k.id + '">' + k.name + '</a>');
                                contentHtml.push('</li>');
                            } else {
                                contentHtml.push('<li>');
                                contentHtml.push('<a href="javascript: void(0);" class="active" gradeid="' + k.grade + '" teacherid="' + k.id + '">' + k.name + '</a>');
                                contentHtml.push('</li>');
                            }
                        });
                        $('#teacher-list').html(contentHtml.join(''));
                        var teacherId = $('#teacher-list a[class="active"]').attr('teacherid');
                        that.getNoArrangeCourseInfo('teacher', teacherId);
                        $('.teacher-list li').click(function(){
                            if(!$(this).hasClass('on')){
                                $('.teacher-list li a').removeClass('active').eq($(this).index()).addClass('active');
                            }
                            var teacherId = $('#teacher-list a[class="active"]').attr('teacherid');
                            that.getNoArrangeCourseInfo('teacher', teacherId);
                        });
                        break;
                    case 'course':
                        $.each(data, function (i, k) {
                            if (i != 0) {
                                contentHtml.push('<li>');
                                contentHtml.push('<a href="javascript: void(0);" gradeid="' + k.grade + '" courseid="' + k.id + '">' + k.name + '</a>');
                                contentHtml.push('</li>');
                            } else {
                                contentHtml.push('<li>');
                                contentHtml.push('<a href="javascript: void(0);" class="active" gradeid="' + k.grade + '" courseid="' + k.id + '">' + k.name + '</a>');
                                contentHtml.push('</li>');
                            }
                        });
                        $('#course-list').html(contentHtml.join(''));
                        var courseId = $('#course-list a[class="active"]').attr('courseid');
                        that.getNoArrangeCourseInfo('course', courseId);
                        $('.course-list li').click(function(){
                            if(!$(this).hasClass('on')){
                                $('.course-list li a').removeClass('active').eq($(this).index()).addClass('active');
                            }
                            var courseId = $('#course-list a[class="active"]').attr('courseid');
                            that.getNoArrangeCourseInfo('course', courseId);
                        });
                        break;
                    default:
                        break;
                }
            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, false);
    },
    updateNoArrangeCourse: function () {
        var that = this;
        var type = $('#no-course-time').val();
        var ids = '',
            week0 = [], //周一
            week1 = [], //周二
            week2 = [], //周三
            week3 = [], //周四
            week4 = [], //周五
            week5 = [], //周六
            week6 = [];  //周日
        switch (type) {
            case 'class':
                ids = $('#class-list a[class="active"]').attr('classid');
                if (ids == '' || ids == undefined || ids == null) {
                    ids = that.classids.join(',');
                }
                break;
            case 'teacher':
                ids = $('#teacher-list a[class="active"]').attr('teacherid');
                break;
            case 'course':
                ids = $('#course-list a[class="active"]').attr('courseid');
                break;
            default:
                break;
        }
        //alert('td[week=' + ('"' + "week" + 0 + '"') + ']' + ', ' + $('td[week=' + ('"' + "week" + 0 + '"') + ']').length);return;
        for (var i = 0; i < $('td[weekcourse="weekcourse"]').length; i++) {
            //alert(i + ', ' + $('td[week=' + ('"' + "week" + i + '"') + ']').length);
            for (var j = 0; j < $('td[week=' + ('"' + "week" + i + '"') + ']').length; j++) {
                var weekItem = $('td[week=' + ('"' + "week" + i + '"') + ']').eq(j).text();
                switch(i) {
                    case 0:
                        if (weekItem == '排课') {
                            week0.push(1);
                        } else {
                            week0.push(0);
                        }
                        break;
                    case 1:
                        if (weekItem == '排课') {
                            week1.push(1);
                        } else {
                            week1.push(0);
                        }
                        break;
                    case 2:
                        if (weekItem == '排课') {
                            week2.push(1);
                        } else {
                            week2.push(0);
                        }
                        break;
                    case 3:
                        if (weekItem == '排课') {
                            week3.push(1);
                        } else {
                            week3.push(0);
                        }
                        break;
                    case 4:
                        if (weekItem == '排课') {
                            week4.push(1);
                        } else {
                            week4.push(0);
                        }
                        break;
                    case 5:
                        if (weekItem == '排课') {
                            week5.push(1);
                        } else {
                            week5.push(0);
                        }
                        break;
                    case 6:
                        if (weekItem == '排课') {
                            week6.push(1);
                        } else {
                            week6.push(0);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        console.info('ids: ' + ids);
        console.info('week0: ' + week0.join(''));
        console.info('week1: ' + week1);
        console.info('week2: ' + week2);
        console.info('week3: ' + week3);
        console.info('week4: ' + week4);
        console.info('week5: ' + week5);
        console.info('week6: ' + week6);
        Common.ajaxFun('/disSelectRule/addOrUpdateRule/' + taskId + '/' + type, 'POST', {
            ids: ids,
            mon: week0.join(''),
            tues: week1.join(''),
            wed: week2.join(''),
            thur: week3.join(''),
            fri: week4.join(''),
            sut: week5.join(''),
            sun: week6.join('')
        }, function (res) {
            if (res.rtnCode == "0000000") {

            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, false);
    },
    //查询学习时间
    getTeachTime: function () {
        Common.ajaxFun('/teachTime/queryTeachTime.do', 'GET', {
            taskId: taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                var weeks = data.teachDate.split('|');
                var weekHtml = [],//表头
                    classesHtml = [];//表内容
                weekHtml.push('<tr>');
                weekHtml.push('<th width="135px">&nbsp;</th>');
                $.each(weeks, function (i, k) {
                    weekHtml.push('<th>' + k + '</th>');
                });
                weekHtml.push('</tr>');
                $('#no-assign-table thead').html(weekHtml.join(''));

                var classes = parseInt(data.teachTime);
                var a = parseInt(classes - ((Math.floor(classes/10))*10));//个位
                var b = parseInt((classes - ((Math.floor(classes/100))*100) - a)/10);//十位数
                var c = parseInt((classes - ((Math.floor(classes/1000))*1000)-(b*10)-a)/100);//百位上的数字
                for (var i = 0; i < a + b + c; i++) {
                    if (i != 0) {
                        classesHtml.push('<tr>');
                        classesHtml.push('<td class="order">' + (i + 1) + '</td>');
                        if (weeks.length != 0) {
                            for (var j = 0; j < weeks.length; j++) {
                                var curIndex = "week" + j;
                                classesHtml.push('<td week=' + curIndex + '>排课</td>');
                            }
                        }
                    } else {
                        classesHtml.push('<tr>');
                        classesHtml.push('<td class="order">' + (i + 1) + '</td>');
                        if (weeks.length != 0) {
                            for (var j = 0; j < weeks.length; j++) {
                                var curIndex = "week" + j;
                                classesHtml.push('<td weekcourse="weekcourse" week=' + curIndex + '>排课</td>');
                            }
                        }
                    }
                    classesHtml.push('</tr>');
                }
                $('#no-assign-table tbody').html(classesHtml.join(''));
            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, false);
    }, //不排课教师课程列表
    getTeacherCourseList: function () {
        var that = this;
        Common.ajaxFun('disSelectRule/teacherCourseList/' + taskId, 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                if (data.length != 0) {
                    var courseHtml = [];
                    $.each(data, function (i, k) {
                        courseHtml.push('<option value="' + k + '">' + k + '</option>');
                    });
                    $('#teacher-course').html(courseHtml.join(''));
                }
                var teacherCourse = $('#teacher-course').val().trim();
                that.getNoArrangeCourseList('teacher', teacherCourse);
            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, true);
    }
};

$(function () {

    var arrangeCourse = new ArrangeCourse();

    $(document).on('change', '#no-course-time', function () {
        var itemVal = $(this).val().trim();
        switch(itemVal) {
            case 'class':
                $('#class-no-array').show();
                $('#teacher-no-array').hide();
                $('#course-no-array').hide();
                arrangeCourse.getNoArrangeCourseList('class', '');
                break;
            case 'teacher':
                $('#class-no-array').hide();
                $('#teacher-no-array').show();
                $('#course-no-array').hide();
                arrangeCourse.getTeacherCourseList();
                break;
            case 'course':
                $('#class-no-array').hide();
                $('#teacher-no-array').hide();
                $('#course-no-array').show();
                arrangeCourse.getNoArrangeCourseList('course', '');
                break;
            default:
                break;
        }
    });

    $(document).on('click', '.no-assign-table tr td:not(.order)', function () {
        var curText = $(this).text().trim();
        if (curText == '排课') {
            $(this).text('不排课');
        } else {
            $(this).text('排课');
        }
        arrangeCourse.updateNoArrangeCourse();
    });

    $(document).on('click', '#btn-save-assign', function () {

    });

});