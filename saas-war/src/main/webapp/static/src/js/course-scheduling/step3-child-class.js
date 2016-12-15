var tnId = Common.cookie.getCookie('tnId');
var taskId = Common.cookie.getCookie('taskId');
function ClassRoomTable() {
    this.init();
    this.courseTxt = '';
    this.teacherId = '';
    this.teacherTxt = '';
    this.className = '';
    this.studentName = '';
    this.classId = '';
    this.studentId = '';
}
ClassRoomTable.prototype = {
    constructor: ClassRoomTable,
    init: function () {
        // this.getClassRoom();
        // this.getQueryCourse();
        // this.getQueryTeacher();
        // this.getQueryClass();
        // this.getQueryStudent();
        this.getAllQueryCourse();

        //this.getClassRoomTable();
    },
    // 拉取教室
    getClassRoom: function () {
        Common.ajaxFun('/baseResult/queryRoom.do', 'GET', {
            "taskId": taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-class option:gt(0)').remove();
                var classRoom = [];
                $.each(result.bizData, function (i, v) {
                    classRoom.push('<option value="' + v.id + '">' + v.roomName + '</option>')
                });
                $('#select-class').append(classRoom);
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    },
    // 拉取课程
    getQueryCourse: function () {
        Common.ajaxFun('/baseResult/queryCourse.do', 'GET', {
            "taskId": taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-queryCourse option:gt(0)').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    queryCourse.push('<option value="' + v.id + '">' + v.courseName + '</option>')
                });
                $('#select-queryCourse').append(queryCourse);
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    },
    // 拉取所有课程(总课表) ====
    getAllQueryCourse: function () {
        Common.ajaxFun('/scheduleTask/all/course/result.do', 'GET', {
            "taskId": taskId
        }, function (res) {
        // res = {
        //     "bizData": {
        //         "result": {
        //             "day": [
        //
        //                     [["一号姓名(通用技术)", "李洋20(通用技术)", "李洋13(英语)", "李洋11(语文)", "李洋16(生物)", "李洋14(物理)", "李洋17(政治)"],
        //                     ["一号姓名(物理)", "李洋19(地理)", "李洋11(语文)", "李洋11(语文)", "李洋11(语文)", "李洋14(物理)", "李洋19(地理)"],
        //                     ["一号姓名(英语)", "李洋20(通用技术)", "李洋19(地理)", "李洋15(化学)", "李洋14(物理)", "李洋11(语文)", "李洋14(物理)"]],
        //
        //                     [["二号姓名(语文)", "李洋18(历史)", "李洋13(英语)", "李洋19(地理)", "李洋11(语文)", "李洋20(通用技术)", "李洋11(语文)"],
        //                     ["二号姓名(历史)", "李洋19(地理)", "李洋15(化学)", "李洋20(通用技术)", "李洋15(化学)", "李洋19(地理)", "李洋13(英语)"],
        //                     ["二号姓名(历史)", "李洋20(通用技术)", "李洋11(语文)", "李洋16(生物)", "李洋20(通用技术)", "李洋17(政治)", "李洋15(化学)"]],
        //
        //                     [["san号姓名(语文)", "李洋18(历史)", "李洋13(英语)", "李洋19(地理)", "李洋11(语文)", "李洋20(通用技术)", "李洋11(语文)"],
        //                     ["二san号姓名号姓名(历史)", "李洋19(地理)", "李洋15(化学)", "李洋20(通用技术)", "李洋15(化学)", "李洋19(地理)", "李洋13(英语)"],
        //                     ["san号姓名(历史)", "李洋20(通用技术)", "李洋11(语文)", "李洋16(生物)", "李洋20(通用技术)", "李洋17(政治)", "李洋15(化学)"]],
        //
        //             ],
        //             "room": "教室1|教室2|教室3",
        //             "teachDate": "星期一|星期二|星期三",
        //             "teachTime": "430"
        //         }
        //     }, "rtnCode": "0000000", "ts": 1481699074431
        // }
        if (res.rtnCode == "0000000") {

            res.bizData.result.room = (res.bizData.result.room).split('|')
            res.bizData.result.teachDate = (res.bizData.result.teachDate).split('|')

            Handlebars.registerHelper('addOne',function(data){
                return data = parseInt(data) +1
            })

            Handlebars.registerHelper('creatClass',function(r1,r2){
                var foo = [];
                $.each(r2[r1],function(i,v){
                    foo.push('<th class="no-p-m">');
                    for(var j in v){
                        foo.push('<span class="create-class-number">'+v[j]+'</span>');
                    }
                    foo.push('</th>');
                })
                return foo.join('');
            })

            var tpl = Handlebars.compile($('#all-timetable-tpl').html());
            $('#all-timetable').html(tpl(res.bizData.result))


            console.info('res.bizData.result.day',res.bizData.result.day)

































            // //渲染header
            // console.info('dataJson',dataJson)
            // var tpl = Handlebars.compile($('#all-timetable-head-tpl').html());
            // $('#all-timetable-head').html(tpl((dataJson.teachDate).split('|')))
            //
            // //渲染body
            // var tpl = Handlebars.compile($('#all-timetable-body-tpl').html());
            // // $('#all-timetable-body').html(tpl())
        } else {
            layer.msg(res.msg);
        }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    // 拉取老师
    getQueryTeacher: function () {
        Common.ajaxFun('/baseResult/queryTeacher.do', 'GET', {
            "taskId": taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-teacher option:gt(0)').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    queryCourse.push('<option value="' + v.id + '">' + v.teacherName + '</option>')
                });
                $('#select-teacher').append(queryCourse);
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result);
        }, true);
    },
    // 拉取班级
    getQueryClass: function () {
        Common.ajaxFun('/baseResult/queryClass.do', 'GET', {
            "taskId": taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-classes option:gt(0)').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    queryCourse.push('<option value="' + v.id + '">' + v.className + '</option>')
                });
                $('#select-classes').append(queryCourse);
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    },
    // 拉取班级
    getQueryStudent: function () {
        Common.ajaxFun('/baseResult/queryStudent.do', 'GET', {
            "taskId": taskId,
            "classId": '1'
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-student option:gt(0)').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    queryCourse.push('<option value="' + v.id + '">' + v.studentName + '</option>')
                });
                $('#select-student').append(queryCourse);
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    },
    // 拉取课表
    getClassRoomTable: function (urlType, param) {
        Common.ajaxFun('/scheduleTask/' + urlType + '/course/result.do', 'GET', {
            "taskId": taskId,
            "param": JSON.stringify(param)
        }, function (result) {
            console.log(result)
            if (result.rtnCode == "0000000") {
                var res = result.bizData.result;
                Handlebars.registerHelper("addOne", function (index) {
                    return parseInt(index) + 1;
                });
                Handlebars.registerHelper("createN", function (res) {
                    var str = '';
                    for (var i = 1; i <= res; i++) {
                        str += '<p class="tbody-item">' + i + '</p>'
                    }
                    return str;
                });
                var weekList = res.teachDate.split('|');
                var theadTemplate = Handlebars.compile($("#" + urlType + "-thead-list-template").html());
                $("#" + urlType + "-thead-list").html(theadTemplate(weekList));
                var tbodyTemplate = Handlebars.compile($("#" + urlType + "-tbody-list-template").html());
                $("#" + urlType + "-tbody-list").html(tbodyTemplate(res.week));
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    }

};

var ClassRoomTableIns = new ClassRoomTable();

$(function () {
    // 选择教室
    $("#select-class").change(function () {
        var selectedTxt = $(this).children('option:selected').text();
        var selectedV = $(this).children('option:selected').val();
        $('.scheduling-name').text(selectedTxt);
        ClassRoomTableIns.getClassRoomTable('room', {'room': selectedV}, selectedV);
    });

    // 选择课程
    $("#select-queryCourse").change(function () {
        ClassRoomTableIns.courseTxt = $(this).children('option:selected').text();
        var selectedV = $(this).children('option:selected').val();
        $('.course-label').text(ClassRoomTableIns.courseTxt);
        ClassRoomTableIns.getClassRoomTable('teacher', {
            'course': ClassRoomTableIns.courseTxt,
            'teacherId': ClassRoomTableIns.teacherId
        });
    });

    // 选择老师
    $("#select-teacher").change(function () {
        ClassRoomTableIns.teacherTxt = $(this).children('option:selected').text();
        ClassRoomTableIns.teacherId = $(this).children('option:selected').val();
        $('.teacher-label').text(ClassRoomTableIns.teacherTxt + "老师");
        ClassRoomTableIns.getClassRoomTable('teacher', {
            'course': ClassRoomTableIns.courseTxt,
            'teacherId': ClassRoomTableIns.teacherId
        });
    });

    // 选择班级
    $("#select-classes").change(function () {
        ClassRoomTableIns.className = $(this).children('option:selected').text();
        ClassRoomTableIns.classId = $(this).children('option:selected').val();
        $('.classes-label').text(ClassRoomTableIns.className);
        ClassRoomTableIns.getClassRoomTable('student', {
            'classId': ClassRoomTableIns.classId,
            'studentId': ClassRoomTableIns.studentId
        });
    });

    // 选择学生
    $("#select-student").change(function () {
        ClassRoomTableIns.studentName = $(this).children('option:selected').text();
        ClassRoomTableIns.studentId = $(this).children('option:selected').val();
        $('.student-label').text(ClassRoomTableIns.studentName + "学生");
        ClassRoomTableIns.getClassRoomTable('student', {
            'classId': ClassRoomTableIns.classId,
            'studentId': ClassRoomTableIns.studentId
        });
    });

    // // 拉取所有课表
    // $("#role-scheduling-tab li").eq(3).click(function () {
    //     ClassRoomTableIns.getAllQueryCourse();
    // });
});



