var tnId = Common.cookie.getCookie('tnId');
var taskId = Common.cookie.getCookie('taskId');
var scheduleName = Common.cookie.getCookie('scheduleName');



$('.scheduleName').text(scheduleName);
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
        // 拉取教室
        this.getClassRoom();
        // 拉取科目
        this.getQueryCourse();
        // 拉取班级
        this.getQueryClass();
    },
    // 拉取教室
    getClassRoom: function () {
        var that = this;
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
                $('#select-class option:eq(1)').attr('selected', 'selected');
                var selectedV = $('#select-class option:eq(1):selected').val();
                var selectedTxt = $('#select-class option:eq(1):selected').text();
                $('.scheduling-name').show().text(selectedTxt);
                that.getClassRoomTable('room', {'room': selectedV});
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        });
    },
    // 拉取科目
    getQueryCourse: function () {
        var that = this;
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
                $('#select-queryCourse option:eq(1)').attr('selected', 'selected');
                var selectedV = $('#select-queryCourse option:eq(1):selected').val()
                var selectedTxt = $('#select-queryCourse option:eq(1):selected').text();
                $('.course-label').text(selectedTxt);
                that.getQueryTeacher(selectedTxt);
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        });
    },
    // 拉取老师
    getQueryTeacher: function (teacherCourse) {
        var that = this;
        Common.ajaxFun('/baseResult/queryTeacher.do', 'GET', {
            "taskId": taskId,
            "teacherCourse": teacherCourse
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-teacher option').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    queryCourse.push('<option value="' + v.id + '">' + v.teacherName + '</option>')
                });
                $('#select-teacher').append(queryCourse);
                $('#select-teacher option:eq(0)').attr('selected', 'selected');
                var selectedV = $('#select-teacher option:eq(0):selected').val()
                var selectedTxt = $('#select-teacher option:eq(0):selected').text()
                that.getClassRoomTable('teacher', {
                    'course': teacherCourse,
                    'teacherId': selectedV
                });
                $('.teacher-label').text(selectedTxt + "老师");
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result);
        });
    },
    // 拉取班级
    getQueryClass: function () {
        var that = this;
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
                $('#select-classes option:eq(1)').attr('selected', 'selected');
                var selectedV = $('#select-classes option:eq(1):selected').val()
                var selectedTxt = $('#select-classes option:eq(1):selected').text();
                $('.classes-label').text(selectedTxt);
                that.getQueryStudent(selectedV)
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    },
    // 拉取学生
    getQueryStudent: function (classId) {
        var that = this;
        Common.ajaxFun('/baseResult/queryStudent.do', 'GET', {
            "taskId": taskId,
            "classId": classId,
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-student option').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    queryCourse.push('<option value="' + v.id + '">' + v.studentName + '</option>')
                });
                $('#select-student').append(queryCourse);
                $('#select-student option:eq(0)').attr('selected', 'selected');
                var selectedV = $('#select-student option:eq(0):selected').val()
                var selectedTxt = $('#select-student option:eq(0):selected').text();
                $('.student-label').text(selectedTxt + " - ");
                that.getClassRoomTable('student', {
                    'classId': classId,
                    'studentId': selectedV
                });
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
            //console.log(result);
            if (result.rtnCode == "0000000") {
                var theadTemplate = Handlebars.compile($("#"+ urlType +"-thead-list-template").html());
                Handlebars.registerHelper("thead", function (res) {
                    var resData = res.split('|');
                    var str = '<td></td>';
                    for (var i = 0; i < resData.length; i++) {
                        str += '<td class="center">'+ resData[i] +'</td>';
                    }
                    return str;
                });
                $("#"+ urlType +"-thead-list").html(theadTemplate(result));
                var tbodyTemplate = Handlebars.compile($("#"+ urlType +"-tbody-list-template").html());
                Handlebars.registerHelper("week", function (res) {
                    var wkDate = res.teachTime;
                    var Num1 = parseInt(wkDate.substr(0,1));
                    var Num2 = parseInt(wkDate.substr(1,1));
                    var Num3 = parseInt(wkDate.substr(2,1));
                    var itemCount = Num1 + Num2 + Num3;
                    var wkList = res.week;
                    var trHtml = '';
                    for(var i=0;i<itemCount;i++){
                        trHtml += '<tr>';
                        trHtml += '<td class="center">'+ (i+1) +'</td>';
                        for(var j=0;j<wkList.length;j++){
                            if(wkList[j][i]==null || wkList[j][i]==""){
                                trHtml += '<td class="center"></td>';
                            }else{
                                trHtml += '<td class="center">'+ wkList[j][i] +'</td>';
                            }
                        }
                        trHtml += '</tr>';
                    }
                    return trHtml;
                });
                $("#"+ urlType +"-tbody-list").html(tbodyTemplate(result));
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        });
    },
    // 拉取所有课程(总课表) ====
    getAllQueryCourse: function () {
        Common.ajaxFun('/scheduleTask/all/course/result.do', 'GET', {
            "taskId": taskId
        }, function (res) {
            // res = {
            //     "bizData": {
            //         "result": {
            //             "roomData": [
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

                Handlebars.registerHelper('addOne', function (data) {
                    return data = parseInt(data) + 1
                })

                Handlebars.registerHelper('creatClass', function (r1, r2) {
                    var foo = [];
                    $.each(r2[r1], function (i, v) {
                        foo.push('<th class="no-p-m">');
                        for (var j in v) {
                            foo.push('<span class="create-class-number common-span-w">' + v[j] + '</span>');
                        }
                        foo.push('</th>');
                    })
                    return foo.join('');
                })


                var tpl = Handlebars.compile($('#all-timetable-tpl').html());
                $('#all-timetable').html(tpl(res.bizData.result))


                //动态控制生成总课表样式
                var $warpTableWidth = '3000',
                    firstThWidth = '120',
                    $secondThWidth = ($warpTableWidth - firstThWidth) / res.bizData.result.teachDate.length;
                $('.second-th-width').css('width', $secondThWidth + 'px');
                $('#all-timetable').css('width', $warpTableWidth + 'px');
                var $classTimes = res.bizData.result.roomData[0][0].length;
                $('.common-span-w').css({
                    'width': parseInt($('#all-timetable .no-p-m').eq(2).width()) / $classTimes - 2 + 'px'
                })
                $('#all-timetable thead th').eq(0).css('width', firstThWidth);
                $('.no-p-m').find('.create-class-number:last-of-type').css('border-right', 'none');
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    }
};



$(function () {
    var ClassRoomTableIns = new ClassRoomTable();

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
        ClassRoomTableIns.getQueryTeacher(ClassRoomTableIns.courseTxt);
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
        ClassRoomTableIns.getQueryStudent(ClassRoomTableIns.classId);
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

    // 拉取所有课表
    //$("#role-scheduling-tab li").eq(3).click(function () {
    //    ClassRoomTableIns.getAllQueryCourse();
    //});
    //if(window.location.hash == '#all'){
    //    ClassRoomTableIns.getAllQueryCourse();
    //}
});



