/**
 * Created by machengcheng on 16/12/6.
 */
var scheduleName = Common.cookie.getCookie('scheduleName');
var gradeName = Common.cookie.getCookie('gradeName');
$('.scheduleName').text(scheduleName);
$('.gradeName').text(gradeName);


/**
 * 公用全局变量
 * @type {{taskId: *, cType: number}}
 */
var GLOBAL_PARAMS = {
    taskId: Common.cookie.getCookie('taskId'),
    cType: -1  //【年级排课、班级排课】-1、0;
}


function ArrangeCourse() {
    this.types = ['class', 'teacher', 'course', 'grade'];  //类型：班级|教师|课程|年级
    this.classids = [];
    this.init();
}
ArrangeCourse.prototype = {
    constructor: ArrangeCourse,
    init: function () {
        this.getNoArrangeCourseList(this.types[0], '');
    },
    /**
     * 拉取数据填充表格
     * @param type
     * @param id
     */
    getNoArrangeCourseInfo: function (type, id) {
        var that = this;
        if (id != undefined) {
            Common.ajaxFun('/disSelectRule/getRule/' + GLOBAL_PARAMS.taskId + '/' + type + '/' + id + '.do', 'GET', that.dataParams, function (res) {
                if (res.rtnCode == "0000000") {
                    var data = res.bizData;
                    if (data.length != 0) {
                        //调用渲染比对table
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


                        var fooTag = ['排课', '不排课']
                        if (type === 'grade') {
                            fooTag = ['排课', gradeName + '不排课'];
                        }
                        for (var i = 0; i < classCount; i++) {
                            if (i != 0) {
                                classesHtml.push('<tr>');
                                classesHtml.push('<td class="order">' + (i + 1) + '</td>');
                                if (weeks.length != 0) {
                                    for (var j = 0; j < weeks.length; j++) {
                                        var curIndex = "week" + j;
                                        var tempCourse = weeksData[j].split('')[i] == 1 ? fooTag[0] : fooTag[1];
                                        if (tempCourse !== '排课') {
                                            classesHtml.push('<td week=' + curIndex + ' class="no-assign-course">' + tempCourse + '</td>');
                                        } else {
                                            classesHtml.push('<td week=' + curIndex + '>' + tempCourse + '</td>');
                                        }
                                    }
                                }
                            } else {
                                classesHtml.push('<tr>');
                                classesHtml.push('<td class="order">' + (i + 1) + '</td>');
                                if (weeks.length != 0) {
                                    for (var j = 0; j < weeks.length; j++) {
                                        var curIndex = "week" + j;
                                        var tempCourse = weeksData[j].split('')[i] == 1 ? fooTag[0] : fooTag[1];
                                        if (tempCourse !== '排课') {
                                            classesHtml.push('<td weekcourse="weekcourse" week=' + curIndex + ' class="no-assign-course">' + tempCourse + '</td>');
                                        } else {
                                            classesHtml.push('<td weekcourse="weekcourse" week=' + curIndex + '>' + tempCourse + '</td>');
                                        }
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
    getCompareTableBody: function (type, id, classId) {
        var that = this;
        if (id != undefined) {
            console.log(that.dataParams.classType);
            Common.ajaxFun('/disSelectRule/getRule/' + GLOBAL_PARAMS.taskId + '/' + type + '/' + id + '.do', 'GET', that.dataParams, function (res) {
                if (res.rtnCode == "0000000") {
                    var data = res.bizData;
                    if (data.length != 0) {
                        console.log("ppp:"+that.dataParams.classType);
                        that.compareRenderTableBody(type, res.bizData[0], classId,that.dataParams.classType);
                    } else {
                        that.getTeachTime();
                    }
                }
            }, function (res) {
                layer.msg("出错了", {time: 1000});
            }, true);
        } else {
            // that.getTeachTime();
        }
    },
    //比较渲染table主体
    compareRenderTableBody: function (type, data, classId,classType) {
        var that = this;
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
        /**
         * 再拉取年级不排课数据进行比对
         * 确定最后展示的信息
         * type == grad 说明是年级不排课【全局】
         * type != grad 说明是班级不排课【局部】
         * 局部服从全局
         */
        var fooTag = ['排课', '不排课']
        if (type === 'grade') {
            fooTag = ['排课', gradeName + '不排课'];
        }
        if (type == 'grade') {
            console.info('年级不排课')
            for (var i = 0; i < classCount; i++) {
                if (i != 0) {
                    classesHtml.push('<tr class="assign-line">');
                    classesHtml.push('<td class="order">' + (i + 1) + '</td>');
                    if (weeks.length != 0) {
                        for (var j = 0; j < weeks.length; j++) {
                            var curIndex = "week" + j;
                            var tempCourse = weeksData[j].split('')[i] == 1 ? fooTag[0] : fooTag[1];
                            if (tempCourse !== '排课') {
                                classesHtml.push('<td week=' + curIndex + ' class="no-assign-course disabled-grade" isgrade="0" type="disabled">' + tempCourse + '</td>');
                            } else {
                                classesHtml.push('<td week=' + curIndex + '>' + tempCourse + '</td>');
                            }
                        }
                    }
                } else {
                    classesHtml.push('<tr class="assign-line">');
                    classesHtml.push('<td class="order">' + (i + 1) + '</td>');
                    if (weeks.length != 0) {
                        for (var j = 0; j < weeks.length; j++) {
                            var curIndex = "week" + j;
                            var tempCourse = weeksData[j].split('')[i] == 1 ? fooTag[0] : fooTag[1];
                            if (tempCourse !== '排课') {
                                classesHtml.push('<td weekcourse="weekcourse" week=' + curIndex + ' class="no-assign-course disabled-grade" isgrade="0" type="disabled">' + tempCourse + '</td>');
                            } else {
                                classesHtml.push('<td weekcourse="weekcourse" week=' + curIndex + '>' + tempCourse + '</td>');
                            }
                        }
                    }
                }
                classesHtml.push('</tr>');
            }
            //年级不排课渲染完了，再渲染班级不排课
            $('#no-assign-table tbody').html(classesHtml.join(''));
            that.getCompareTableBody('class', classId);
            console.log('classType=='+classType)
        } else {

            console.info('班级不排课', data)
            if ($('#no-assign-table .assign-line').length != 0) {
                if (type == 'class') {
                    for (var i = 0; i < $('#no-assign-table .assign-line').length; i++) {
                        for (var j = 0; j < $('#no-assign-table .assign-line').eq(i).find('td').length - 1; j++) {
                            var tempNode = $('#no-assign-table .assign-line').eq(i).find('td').eq(j + 1);
                            var tempArr = (weeksData[j] + '').split('');
                            console.info('weeksData', weeksData[j]);
                            if (tempNode.attr('class') != 'order') {
                                var id = tempArr[i];
                                if (tempNode.attr('isgrade') != 0) {
                                    tempNode.html(id == 0 ? '<span class="no-assign-course">不排课</span>' : '排课');
                                }
                            }

                        }
                    }

                }
            }

        }


    },
    //不排课列表查询
    getNoArrangeCourseList: function (type, teacherCourse) {
        var that = this;
        var paramsObj = {};
        if (type == 'teacher') {
            paramsObj['teacherCourse'] = teacherCourse;
        }
        /**
         * ===================
         * 渲染年级下面的所有班级
         * ===================
         */
        that.getTeachTime();
        Common.ajaxFun('/disSelectRule/list/' + type + '/' + GLOBAL_PARAMS.taskId, 'GET', paramsObj, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                var tpl = [];
                switch (type) {
                    case 'class':
                        tpl.push('<li type="grade">');
                        tpl.push('<a href="javascript: void(0);" class="active">年级排课</a>');
                        tpl.push('</li>');
                        $.each(data, function (i, k) {
                            tpl.push('<li>');
                            tpl.push('<a href="javascript: void(0);" classType="' + k.classType + '" gradeid="' + k.grade + '" classid="' + k.id + '">' + k.name + '</a>');
                            tpl.push('</li>');
                            that.classids.push(k.id);
                        });
                        $('#class-list').html(tpl.join(''));


                        /**
                         * 初始化渲染
                         * 1、渲染空表格
                         * 2、拉取年级数据填充表格
                         */
                        // that.getTeachTime();  //初始化渲染默认年级课表
                        that.getNoArrangeCourseInfo('grade', -1);


                        /**
                         * =====================
                         * 年级-年级所在班级【切换】
                         * =====================
                         */
                        $('.class-list li').click(function () {


                            var _this = $(this);
                            _this.find('a').addClass('active').parent().siblings().find('a').removeClass('active');
                            //年级排课、班级排课切换
                            !_this.find('a').attr('classid') ? GLOBAL_PARAMS.cType = -1 : GLOBAL_PARAMS.cType = 0;

                            if (_this.attr('type') == 'grade') {
                                that.getNoArrangeCourseInfo('grade', -1);
                            } else {
                                that.dataParams.classType = $(this).find('a').attr('classType');
                                that.getCompareTableBody('grade', -1, $(this).find('a').attr('classId'));
                            }


                        });
                        break;
                    case 'teacher':
                        $.each(data, function (i, k) {
                            if (i != 0) {
                                tpl.push('<li>');
                                tpl.push('<a href="javascript: void(0);" gradeid="' + k.grade + '" teacherid="' + k.id + '">' + k.name + '</a>');
                                tpl.push('</li>');
                            } else {
                                tpl.push('<li>');
                                tpl.push('<a href="javascript: void(0);" class="active" gradeid="' + k.grade + '" teacherid="' + k.id + '">' + k.name + '</a>');
                                tpl.push('</li>');
                            }
                        });
                        $('#teacher-list').html(tpl.join(''));
                        var teacherId = $('#teacher-list a[class="active"]').attr('teacherid');
                        that.getNoArrangeCourseInfo('teacher', teacherId);
                        $('.teacher-list li').click(function () {
                            if (!$(this).hasClass('on')) {
                                $('.teacher-list li a').removeClass('active').eq($(this).index()).addClass('active');
                            }
                            var teacherId = $('#teacher-list a[class="active"]').attr('teacherid');
                            that.getNoArrangeCourseInfo('teacher', teacherId);
                        });
                        break;
                    case 'course':
                        $.each(data, function (i, k) {
                            if (i != 0) {
                                tpl.push('<li>');
                                tpl.push('<a href="javascript: void(0);" gradeid="' + k.grade + '" courseid="' + k.id + '">' + k.name + '</a>');
                                tpl.push('</li>');
                            } else {
                                tpl.push('<li>');
                                tpl.push('<a href="javascript: void(0);" class="active" gradeid="' + k.grade + '" courseid="' + k.id + '">' + k.name + '</a>');
                                tpl.push('</li>');
                            }
                        });
                        $('#course-list').html(tpl.join(''));
                        var courseId = $('#course-list a[class="active"]').attr('courseid');
                        that.getNoArrangeCourseInfo('course', courseId);
                        $('.course-list li').click(function () {
                            if (!$(this).hasClass('on')) {
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
            classType = '',
            week0 = [], //周一
            week1 = [], //周二
            week2 = [], //周三
            week3 = [], //周四
            week4 = [], //周五
            week5 = [], //周六
            week6 = [];  //周日
        switch (type) {
            case 'class':
                classType = $('#class-list a[class="active"]').attr('classType');
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
                switch (i) {
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

        var whichType = GLOBAL_PARAMS.cType == -1 ? 'grade' : type  //年级排课于非年级排课
        Common.ajaxFun('/disSelectRule/addOrUpdateRule/' + GLOBAL_PARAMS.taskId + '/' + whichType, 'POST', {
            ids: ids,
            mon: week0.join(''),
            tues: week1.join(''),
            wed: week2.join(''),
            thur: week3.join(''),
            fri: week4.join(''),
            sut: week5.join(''),
            sun: week6.join(''),

            classType: classType
        }, function (res) {
            if (res.rtnCode == "0000000") {

            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, false);
    },

    /**
     * 拉取课表的规格：一天几节、一周几天
     * 【空表格未填充数据】
     */
    getTeachTime: function () {
        var that = this;
        Common.ajaxFun('/teachTime/queryTeachTime.do', 'GET', {
            taskId: GLOBAL_PARAMS.taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                var weeks = data.teachDate.split('|');
                var weekHtml = [],//表头
                    classesHtml = [];//表内容
                weekHtml.push('<tr>');
                weekHtml.push('<th width="135px">&nbsp;</th>');

                //数据组装=========//数据组装=========//数据组装=========//数据组装=========//数据组装=========//数据组装=========//数据组装=========//数据组装=========
                var foo = 0;
                var fooStr = '';
                for(var i in data.teachTime.split('')){
                    foo += parseInt(data.teachTime.split('')[i])
                }
                for(var i=0;i<foo;i++){
                    fooStr += '1'
                }
                weeks = ['星期一','星期二','星期三','星期四','星期五','星期六']
                var fooTag = {
                    '星期一':'mon',
                    '星期二':'tues',
                    '星期三':'wed',
                    '星期四':'thur',
                    '星期五':'fri',
                    '星期六':'sut',
                    '星期日':'sun'
                }
                that.dataParams = {
                    'classType': '行政班'
                }
                for(var i in weeks){
                    that.dataParams[fooTag[weeks[i]]] = fooStr
                }
                //数据组装=========//数据组装=========//数据组装=========//数据组装=========//数据组装=========//数据组装=========//数据组装=========//数据组装=========




                $.each(weeks, function (i, k) {
                    weekHtml.push('<th style="min-width: 110px">' + k + '</th>');
                });
                weekHtml.push('</tr>');
                $('#no-assign-table thead').html(weekHtml.join(''));
                var classes = parseInt(data.teachTime);
                var a = parseInt(classes - ((Math.floor(classes / 10)) * 10));//个位
                var b = parseInt((classes - ((Math.floor(classes / 100)) * 100) - a) / 10);//十位数
                var c = parseInt((classes - ((Math.floor(classes / 1000)) * 1000) - (b * 10) - a) / 100);//百位上的数字
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
        Common.ajaxFun('disSelectRule/teacherCourseList/' + GLOBAL_PARAMS.taskId, 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                if (data.length != 0) {
                    var courseHtml = [];
                    $.each(data, function (i, k) {
                        courseHtml.push('<option value="' + k + '">' + k + '</option>');
                    });
                    $('#teacher-course').html(courseHtml.join(''));
                }
                if ($('#teacher-course').val()) {
                    var teacherCourse = $('#teacher-course').val().trim();
                    that.getNoArrangeCourseList('teacher', teacherCourse);
                }
            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, true);
    }
};

$(function () {
    // 开发暂时注释
    // var flag = Common.checkInfoIsPerfect(GLOBAL_PARAMS.taskId);
    // if (!flag) {
    //     window.location.href = '/course-scheduling-step1';
    // }

    var arrangeCourse = new ArrangeCourse();

    $(document).on('change', '#no-course-time', function () {
        var itemVal = $(this).val().trim();
        switch (itemVal) {
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

    $(document).on('change', '#teacher-course', function () {
        var teacherCourse = $(this).val().trim();
        arrangeCourse.getNoArrangeCourseList('teacher', teacherCourse);
    });


    /**
     * 触发排课or不开课事件
     */
    $(document).on('click', '.no-assign-table tr td:not(.order)', function () {
        if ($(this).attr('type') == 'disabled') {
            layer.msg('已设置年级不排课，所以不能再设置班级排课');
            return false
        }
        var curText = $(this).text().trim(), foo = ['不排课', '排课'];
        //年级不排课
        if ($('#no-course-time').val() == 'class' && GLOBAL_PARAMS.cType == -1) {
            foo = [gradeName + '不排课', '排课']
        }

        if (curText == '排课') {
            $(this).html('<span class="no-assign-course">' + foo[0] + '</span>');
        } else {
            $(this).html('<span class="assign-course">' + foo[1] + '</span>');
        }
        arrangeCourse.updateNoArrangeCourse();
    });

    $(document).on('click', '#btn-save-assign', function () {

    });


    /**
     * 班级不排课|教师不排课|课程不排课
     * select-change
     */
    $(document).on('change', '#no-course-time', function () {
        if ($(this).val() != 'class') {
            GLOBAL_PARAMS.cType = 0;
        }
    })
});