var tnId = Common.cookie.getCookie('tnId');
var taskId = Common.cookie.getCookie('taskId');
var scheduleName = Common.cookie.getCookie('scheduleName');
$('.scheduleName').text(scheduleName);


/**
 * 地址Hash处理
 */
var taskId = Common.cookie.getCookie('taskId');
var scheduleName = Common.cookie.getCookie('scheduleName');
$('.scheduleName').text(scheduleName);
var HashHandle = {
    init: function () {
        this.hashArr = ['#class', '#teacher', '#student', '#all'];
        this.addEvent();
        this.hashOperate();
        this.initStatus();
    },
    addEvent: function () {
        $('#role-scheduling-tab .role-tab li').click(function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            $('#control-jsp .bottom-page').eq(index).removeClass('dh').siblings().addClass('dh');
            window.location.hash = HashHandle.hashArr[index];
        });
    },
    hashOperate: function () {
        // 防止刷新|hash处理
        window.onhashchange = function () {
            if (window.location.hash === '') {
                window.location.reload();
            }
        };
        for (var i = 0; i < HashHandle.hashArr.length; i++) {
            if (window.location.hash === HashHandle.hashArr[i]) {
                $('#role-scheduling-tab .role-tab li').eq(i).addClass('active').siblings().removeClass('active');
                $('#control-jsp .bottom-page').eq(i).removeClass('dh').siblings().addClass('dh');
            }
        }
    },
    initStatus: function () {
        var that = this;
        Common.ajaxFun('/scheduleTask/queryScheduleTaskStatus', 'GET', {
            'taskId': taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                switch (parseInt(data)) {
                    // 1. 没点过没排课  2.排课失败  3.已经点过
                    case 1:
                        $('.btn-one-key').removeClass('dh');
                        break;
                    case 2:
                        break;
                    case 3:
                        $('.btn-one-key').addClass('dh');
                        that.scheduleTaskState();
                        break;
                    default:
                        break;
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    updateStatus: function () {
        //Common.ajaxFun('/scheduleTask/updateScheduleTaskStatus', 'GET', {
        //    'taskId': taskId
        //}, function (res) {
        //    if (res.rtnCode == "0000000") {
        //        var data = res.bizData;
        //        $('#role-scheduling-tab, #step3-child-class').removeClass('dh');
        //        $('.info-modify, .btn-one-key').addClass('dh');
        //    }
        //}, function (res) {
        //    layer.msg("出错了");
        //}, true);
    },
    // 一键排课触发
    scheduleTaskTrigger: function () {
        var that = this;
        Common.ajaxFun('/scheduleTask/trigger.do', 'GET', {
            'taskId': taskId,
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if (res.bizData == true) {
                    that.scheduleTaskState();
                }
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    // 一键排课结果
    scheduleTaskState: function () {
        var that = this;
        Common.ajaxFun('/scheduleTask/state.do', 'GET', {
            'taskId': taskId,
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                // 0:正在排课  1:排课成功   -1 ：排课失败
                var dataNum = res.bizData;
                switch (parseInt(dataNum)) {
                    case 0:
                        console.log("正在努力排课中,预计需要等待5-10分钟才能排出课表,请耐心等待哦");



                        //clearInterval(that.progressTims);
                        //var current = 0;
                        //var totalTime = 1000 * 60 * 10; // 10分钟
                        //that.progressTims = setInterval(function(){
                        //    current++;
                        //    $('#counter').html(current + '%');
                        //    if (current == 100) {
                        //        current=0;
                        //        clearInterval(that.progressTims);
                        //    }
                        //},1000);





                        //var interval = setInterval(increment, 100);
                        //var current = 0;
                        //
                        //function increment() {
                        //    current++;
                        //    $('#counter').html(current + '%');
                        //    if (current == 100) {
                        //        current = 0;
                        //    }
                        //}
                        //
                        //interval = setInterval(increment, 100);













                        $('.scheduling-error,#role-scheduling-tab,#control-jsp').addClass('dh');
                        clearInterval(that.items);
                        that.items = setInterval(function () {
                            that.scheduleTaskState();
                        }, 30000);
                        break;
                    case 1:
                        console.log("排课成功");
                        clearInterval(that.items);
                        $('.one-key-page').addClass('dh');
                        $('#role-scheduling-tab,#control-jsp').removeClass('dh');
                        ClassRoomTableIns.getClassRoom();
                        ClassRoomTableIns.getQueryCourse();
                        ClassRoomTableIns.getQueryClass();
                        break;
                    case -1:
                        console.log("排课失败");
                        $('.btn-one-key,#role-scheduling-tab,#control-jsp').addClass('dh');
                        $('.scheduling-error').removeClass('dh');
                        break;
                    default:
                        break;
                }
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    }

};
HashHandle.init();


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
        //// 拉取教室
        //this.getClassRoom();
        //// 拉取科目
        //this.getQueryCourse();
        //// 拉取班级
        //this.getQueryClass();
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
            "taskId": taskId,

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
                var theadTemplate = Handlebars.compile($("#" + urlType + "-thead-list-template").html());
                Handlebars.registerHelper("thead", function (res) {
                    var resData = res.split('|');
                    var str = '<td></td>';
                    for (var i = 0; i < resData.length; i++) {
                        str += '<td class="center">' + resData[i] + '</td>';
                    }
                    return str;
                });
                $("#" + urlType + "-thead-list").html(theadTemplate(result));
                var tbodyTemplate = Handlebars.compile($("#" + urlType + "-tbody-list-template").html());
                Handlebars.registerHelper("week", function (res) {
                    var wkDate = res.teachTime;
                    var Num1 = parseInt(wkDate.substr(0, 1));
                    var Num2 = parseInt(wkDate.substr(1, 1));
                    var Num3 = parseInt(wkDate.substr(2, 1));
                    var itemCount = Num1 + Num2 + Num3;
                    var wkList = res.week;
                    var trHtml = '';
                    for (var i = 0; i < itemCount; i++) {
                        trHtml += '<tr>';
                        trHtml += '<td class="center">' + (i + 1) + '</td>';
                        for (var j = 0; j < wkList.length; j++) {
                            if (wkList[j][i] == null || wkList[j][i] == "") {
                                trHtml += '<td class="center"></td>';
                            } else {
                                trHtml += '<td class="center">' + wkList[j][i] + '</td>';
                            }
                        }
                        trHtml += '</tr>';
                    }
                    return trHtml;
                });
                $("#" + urlType + "-tbody-list").html(tbodyTemplate(result));
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
            if (res.rtnCode == "0000000") {
                $('.all-time-date-container').css({
                    'width': $(window).width() - 190 - 40,
                    'overflow': 'auto'
                });
                var theadTemplate = Handlebars.compile($("#all-thead-list-template").html());
                Handlebars.registerHelper("thead", function (res) {
                    var wkDate = res.teachTime;
                    var Num1 = parseInt(wkDate.substr(0, 1));
                    var Num2 = parseInt(wkDate.substr(1, 1));
                    var Num3 = parseInt(wkDate.substr(2, 1));
                    var itemCount = Num1 + Num2 + Num3;
                    var resData = res.teachDate.split('|');
                    var str = '<td></td>';
                    for (var i = 0; i < resData.length; i++) {
                        str += '<td class="center" colspan="' + itemCount + '">' + resData[i] + '</td>';
                    }
                    return str;
                });
                $("#all-thead-list").html(theadTemplate(res));


                var tbodyTemplate = Handlebars.compile($("#all-tbody-list-template").html());
                Handlebars.registerHelper("room", function (res) {
                    var rmWk = res.roomData;
                    console.log(rmWk.length)
                    var rmList = res.room.split('|');
                    var trHtml = '';
                    for (var i = 0; i < rmList.length; i++) {
                        trHtml += '<tr>';
                        trHtml += '<td class="center"><div style="width:100px">' + rmList[i] + '</div></td>';
                        console.log(rmWk[i])
                        for (var j = 0; j < rmWk[i].length; j++) {
                            for (var k = 0; k < rmWk[i][j].length; k++) {
                                trHtml += '<td class="center" style="width:100px"><div style="width:100px">' + rmWk[i][j][k] + '</div></td>';
                            }
                        }
                        trHtml += '</tr>';
                    }
                    return trHtml;
                });
                $("#all-tbody-list").html(tbodyTemplate(res));


            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    }
};

var ClassRoomTableIns = new ClassRoomTable();

$(function () {


    // 点击一键排课
    $('.btn-one-key').on('click', function () {
        HashHandle.scheduleTaskTrigger();
    });

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
    $("#role-scheduling-tab li").eq(3).click(function () {
        ClassRoomTableIns.getAllQueryCourse();
    });
    if (window.location.hash == '#all') {
        ClassRoomTableIns.getAllQueryCourse();
    }
});



