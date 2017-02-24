var tnId = Common.cookie.getCookie('tnId');
var taskId = Common.cookie.getCookie('taskId');
var scheduleName = Common.cookie.getCookie('scheduleName');
//var resValtaskId = Common.cookie.getCookie('resVal' + taskId);
$('.scheduleName').text(scheduleName);


/**
 * 地址Hash处理
 */


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
        // 拉取科目
        //this.getQueryCourse();
        // 拉取班级
        //this.getQueryClass();
        //$('.one-key-page').addClass('dh');
        //$('#role-scheduling-tab,#control-jsp').removeClass('dh');

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
                    queryCourse.push('<option value="' + v.courseBaseId + '">' + v.courseBaseName + '</option>')
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
            var result = {"bizData":[{"id":1,"class_grade":"高一年级","class_name":"高一1班","class_boss":"\u00A0李璇 ","class_type":"行政班","class_code":"class_adm_14874858922160"},{"id":2,"class_grade":"高一年级","class_name":"高一2班","class_boss":"马舒滟","class_type":"行政班","class_code":"class_adm_14874858922161"},{"id":3,"class_grade":"高一年级","class_name":"高一3班","class_boss":"许青云","class_type":"行政班","class_code":"class_adm_14874858922162"},{"id":4,"class_grade":"高一年级","class_name":"高一4班","class_boss":"徐丹","class_type":"行政班","class_code":"class_adm_14874858922163"},{"id":5,"class_grade":"高一年级","class_name":"高一5班","class_boss":"张奕","class_type":"行政班","class_code":"class_adm_14874858922164"},{"id":6,"class_grade":"高一年级","class_name":"高一6班","class_boss":"田野","class_type":"行政班","class_code":"class_adm_14874858922165"},{"id":7,"class_grade":"高一年级","class_name":"高一7班","class_boss":"彭亚光","class_type":"行政班","class_code":"class_adm_14874858922166"},{"id":8,"class_grade":"高一年级","class_name":"高一8班","class_boss":"黄欢","class_type":"行政班","class_code":"class_adm_14874858922167"},{"id":9,"class_grade":"高一年级","class_name":"高一9班","class_boss":"庞琳","class_type":"行政班","class_code":"class_adm_14874858922168"},{"id":10,"class_grade":"高一年级","class_name":"高一10班","class_boss":"张子腾","class_type":"行政班","class_code":"class_adm_14874858922169"},{"id":11,"class_grade":"高一年级","class_name":"高一11班","class_boss":"姜春阳","class_type":"行政班","class_code":"class_adm_148748589221610"},{"id":12,"class_grade":"高一年级","class_name":"高一12班","class_boss":"刘冰若","class_type":"行政班","class_code":"class_adm_148748589221611"},{"id":13,"class_grade":"高一年级","class_name":"高一13班","class_boss":"何丽","class_type":"行政班","class_code":"class_adm_148748589221612"},{"id":14,"class_grade":"高一年级","class_name":"高一14班","class_boss":"王文 ","class_type":"行政班","class_code":"class_adm_148748589221613"},{"id":15,"class_grade":"高一年级","class_name":"高一15班","class_boss":"\u00A0陈佳敏","class_type":"行政班","class_code":"class_adm_148748589221614"}],"rtnCode":"0000000","ts":1487752675672}
            if (result.rtnCode == "0000000") {
                $('#select-class option:gt(0)').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    console.log(v.id + "==" + v.class_name)
                    queryCourse.push('<option value="' + v.id + '">' + v.class_name + '</option>')
                });
                $('#select-class').append(queryCourse);
                $('#select-class option:eq(1)').attr('selected', 'selected');
                var selectedV = $('#select-class option:eq(1):selected').val()
                var selectedTxt = $('#select-class option:eq(1):selected').text();
                $('.classes-label').text(selectedTxt);
                that.getClassRoomTable('class', {'classId': selectedV}, selectedV);
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
            var result = {"bizData":{"result":{"teachDate":"星期一|星期二|星期三|星期四|星期五","teachTime":"430","week":[["","","物理  胡书琴","生物  范志坚","","语文  \u00A0李璇 ","化学  任娟娟"],["语文  \u00A0李璇 ","","历史  许超丽","政治  谢文婷","","化学  任娟娟","数学  黄欢"],["","","","","英语  曹丽娜","","政治  谢文婷"],["地理  张琪","","数学  黄欢","政治  谢文婷","","英语  曹丽娜",""],["历史  许超丽","物理  胡书琴","","","生物  范志坚","政治  谢文婷","地理  张琪"]]}},"rtnCode":"0000000","ts":1487752474366}


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






var HashHandle = {
    init: function () {
        this.hashArr = ['#all','#class','#teacher'];
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
                    // 1. 没点过没排课  2.排课失败  3.排课中 4.排课成功
                    case 1:
                        console.log("点击排课");
                        $('.btn-one-key').removeClass('dh');
                        break;
                    case 2:
                        console.log("排课失败");
                        $('.btn-one-key,#role-scheduling-tab,#control-jsp,.arranging-course-tips,.scheduling-error2').addClass('dh');
                        $('.scheduling-error').removeClass('dh');
                        that.scheduleTaskError();
                        break;
                    case 3:
                        console.log("正在努力排课中,预计需要等待5-10分钟才能排出课表,请耐心等待哦");
                        $('.arranging-course-tips').removeClass('dh');
                        $('.scheduling-error,#role-scheduling-tab,#control-jsp,.btn-one-key,.scheduling-error,.scheduling-error2').addClass('dh');
                        clearInterval(that.items);
                        that.items = setInterval(function () {
                            that.scheduleTaskState();
                        }, 30000);
                        break;
                    case 4:
                        console.log("排课成功");
                        clearInterval(that.items);
                        $('.one-key-page,.arranging-course-tips,.btn-one-key,.look-origin-schedule,.scheduling-error,.scheduling-error2').addClass('dh');
                        $('#role-scheduling-tab,#control-jsp,.info-modify').removeClass('dh');
                        ClassRoomTableIns.getAllQueryCourse();
                        ClassRoomTableIns.getQueryCourse();
                        ClassRoomTableIns.getQueryClass();
                        break;
                    case 5:
                        console.log("排课失败2");
                        $('.btn-one-key,#role-scheduling-tab,#control-jsp,.arranging-course-tips,.scheduling-error').addClass('dh');
                        $('.scheduling-error2').removeClass('dh');
                        break;
                    default:
                        break;
                }
            }
        }, function (res) {
            layer.msg("出错了");
        });
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
        });
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
                var num=0;
                Common.cookie.setCookie("resVal"+taskId, parseInt(dataNum));
                switch (dataNum) {
                    case "":
                        console.log("正在努力排课中,预计需要等待5-10分钟才能排出课表,请耐心等待哦0");
                        $('.arranging-course-tips').removeClass('dh');
                        $('.scheduling-error,#role-scheduling-tab,#control-jsp,.btn-one-key,.scheduling-error,.scheduling-error2,.info-modify').addClass('dh');
                        if(num==2){
                            console.log("排课任务状态返回错误");
                            clearInterval(that.items);
                            $('.btn-one-key,#role-scheduling-tab,#control-jsp,.arranging-course-tips,.scheduling-error,.info-modify').addClass('dh');
                            $('.scheduling-error2').removeClass('dh');
                        }
                        clearInterval(that.items);
                        that.items = setInterval(function () {
                            that.scheduleTaskState();
                            num ++
                        }, 30000);
                        break;
                    case "0":
                        console.log("正在努力排课中,预计需要等待5-10分钟才能排出课表,请耐心等待哦0");
                        $('.arranging-course-tips').removeClass('dh');
                        $('.scheduling-error,#role-scheduling-tab,#control-jsp,.btn-one-key,.scheduling-error,.scheduling-error2,.info-modify').addClass('dh');
                        clearInterval(that.items);
                        that.items = setInterval(function () {
                            that.scheduleTaskState();
                        }, 30000);
                        break;
                    case "1":
                        console.log("排课成功1");
                        clearInterval(that.items);
                        $('.one-key-page,.arranging-course-tips,.btn-one-key,.look-origin-schedule,.scheduling-error,.scheduling-error2').addClass('dh');
                        $('#role-scheduling-tab,#control-jsp,.info-modify').removeClass('dh');
                        ClassRoomTableIns.getAllQueryCourse();
                        ClassRoomTableIns.getQueryCourse();
                        ClassRoomTableIns.getQueryClass();
                        break;
                    case "-1":
                        console.log("排课失败-1");
                        clearInterval(that.items);
                        $('.btn-one-key,#role-scheduling-tab,#control-jsp,.arranging-course-tips,.scheduling-error,.scheduling-error2,.info-modify').addClass('dh');
                        $('.scheduling-error').removeClass('dh');
                        that.scheduleTaskError();
                        break;
                    case "-2":
                        console.log("排课失败-2");
                        clearInterval(that.items);
                        $('.btn-one-key,#role-scheduling-tab,#control-jsp,.arranging-course-tips,.scheduling-error,.info-modify').addClass('dh');
                        $('.scheduling-error2').removeClass('dh');
                        break;
                    default:
                        break;
                }
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 错误接口 /scheduleTask/error/desc?taskId=37&tnId=10
    scheduleTaskError:function(){
        var that = this;
        Common.ajaxFun('/scheduleTask/error/desc', 'GET', {
            'taskId': taskId,
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var errorBoxList = [];
                for(var i=0;i<res.bizData.length;i++){
                    errorBoxList.push('<li>'+res.bizData[i]+'</li>');
                }
                $('.error-box-list').append(errorBoxList);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 重新排课 /scheduleTask/reload/trigger.do
    scheduleTaskReload:function(){
        var that = this;
        Common.ajaxFun('/scheduleTask/reload/trigger.do', 'GET', {
            'taskId': taskId,
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                that.scheduleTaskState();
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }


};
HashHandle.init();

$(function () {


    // 点击一键排课
    $('.btn-one-key').on('click', function () {
        $('.btn-one-key').addClass('dh');
        HashHandle.scheduleTaskTrigger();
    });

    // 重新排课
    $('.retry-scheduling').on('click',function(){
        $('.btn-one-key').addClass('dh');
        HashHandle.scheduleTaskReload();
    });

    // 查看原课表
    $('.look-origin-schedule').on('click',function(){

    });

    // 选择班级
    $("#select-class").change(function () {
        var selectedTxt = $(this).children('option:selected').text();
        var selectedV = $(this).children('option:selected').val();
        $('.scheduling-name').text(selectedTxt);
        ClassRoomTableIns.getClassRoomTable('class', {'classId': selectedV}, selectedV);
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
    $("#role-scheduling-tab li").eq(0).click(function () {
        ClassRoomTableIns.getAllQueryCourse();
    });
    if (window.location.hash == '#all') {
        ClassRoomTableIns.getAllQueryCourse();
    }
});




//导出下载课表====临时

$(document).on('click', '.output-tpl1', function () {
    window.location.href = '/scheduleTask/class/course/export.do?taskId='+taskId;
});
$(document).on('click', '.output-tpl2', function () {
    window.location.href = '/scheduleTask/teacher/course/export.do?taskId='+taskId;
});
