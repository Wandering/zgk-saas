var tnId = Common.cookie.getCookie('tnId');
var taskId = Common.cookie.getCookie('taskId');
var scheduleName = Common.cookie.getCookie('scheduleName');
$('.scheduleName').text(scheduleName);


/***
 *
 // 课表切换
 // 防止刷新|hash处理
 // 排课初始化状态
 // 一键排课触发
 // 一键排课结果
 // 排课错误规则返回
 // 重新排课
 // 拉取科目
 // 拉取老师
 // 拉取教室
 // 拉取班级
 // 获取年级班级类型
 // 拉取学生
 // 拉取课表 (班级\教师\学生\教室)
 // 拉取总课表
 // 行政班调课 根据坐标获取成功状态
 // 行政班调课 根据状态获取可调颜色类型
 // 行政班调课 拉取颜色列表
 // 行政班调课 提交两个可调课程坐标
 // 行政班调课 根据老师坐标填充空白部分
 * @constructor
 */
// 课表
function CourseTable(){
    this.init();
    this.courseTxt = '';
    this.teacherId = '';
    this.teacherTxt = '';
    this.className = '';
    this.studentName = '';
    this.classId = '';
    this.studentClassId = '';
    this.studentId = '';
    this.selectClassType = '';
    this.posX = '';
    this.posY = '';
    this.tarPosX = '';
    this.tarPosY = '';
    this.flagClassType = null;
    this.colorScheduleResultState = '';
}

CourseTable.prototype = {
    constructor:CourseTable,
    hashArr : ['#all', '#class', '#teacher','#student','#room'],
    // 初始化
    init:function(){
        this.tabEvent();
        this.hashOperate();
        this.initStatus(); // 初始化状态
        this.oneKeySchedulingEvent(); // 一键排课
        this.retrySchedulingEvent(); // 重新排课
        this.selectClassEvent(); // 选择班级
        this.selectCourseEvent(); // 选择科目
        this.selectTeacherEvent(); // 选择老师
        this.selectClassesEvent(); // 选择学生班级
        this.selectStudentEvent(); // 选择学生
        this.selectRoomEvent(); // 选择教师
        this.queryGradeClassType(); // 获取年级班级类型
        // 2：教学班走读 ，2以外：行政
        if(this.flagClassType!='2'){
            $('.student-tab,.room-tab').addClass('dh');
            this.courseTableEvent('class');
            this.courseTableEvent('teacher');
            $('.class-tab').removeClass('dh');
        }else{
            $('.student-tab,.room-tab').removeClass('dh');
            $('.class-tab').addClass('dh');
            $('#step3-child-teacher .colors-box').remove();
        }

    },
    // 课表切换
    tabEvent:function(){
        var that = this;
        $('#role-scheduling-tab .role-tab li').click(function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            $('#control-jsp .bottom-page').eq(index).removeClass('dh').siblings().addClass('dh');
            window.location.hash = that.hashArr[index];
        });
    },
    // 防止刷新|hash处理
    hashOperate: function () {
        var that = this;
        //console.log(that.hashArr)
        // 防止刷新|hash处理
        window.onhashchange = function () {
            if (window.location.hash === '') {
                window.location.reload();
            }
        };
        for (var i = 0; i < that.hashArr.length; i++) {
            if (window.location.hash === that.hashArr[i]) {
                $('#role-scheduling-tab .role-tab li').eq(i).addClass('active').siblings().removeClass('active');
                $('#control-jsp .bottom-page').eq(i).removeClass('dh').siblings().addClass('dh');
            }
        }
    },
    // 点击一键排课
    oneKeySchedulingEvent:function(){
        var that = this;
        $('.btn-one-key').on('click', function () {
            $('.btn-one-key').addClass('dh');
            that.scheduleTaskTrigger();
        });
    },
    // 重新排课
    retrySchedulingEvent:function(){
        var that = this;
        $('.retry-scheduling').on('click', function () {
            $('.btn-one-key').addClass('dh');
            that.scheduleTaskReload();
        });
    },
    // 选择班级
    selectClassEvent:function(){
        var that = this;
        $("#select-class").change(function () {
            var selectedTxt = $(this).children('option:selected').text();
            var selectedV = $(this).children('option:selected').val();
            var selectClassType = $(this).children('option:selected').attr('class_type');
            $('.scheduling-name').text(selectedTxt);
            that.getClassRoomTable('class', {'classId': selectedV,'classType':selectClassType}, selectedV);
        });
    },
    // 选择课程
    selectCourseEvent:function(){
        var that = this;
        $("#select-queryCourse").change(function () {
            that.courseTxt = $(this).children('option:selected').text();
            var selectedV = $(this).children('option:selected').val();
            $('.course-label').text(that.courseTxt);
            that.getQueryTeacher(that.courseTxt);
        });
    },
    // 选择老师
    selectTeacherEvent:function(){
        var that = this;
        $("#select-teacher").change(function () {
            that.teacherTxt = $(this).children('option:selected').text();
            that.teacherId = $(this).children('option:selected').val();
            $('.teacher-label').text(that.teacherTxt+ "老师共代"+ that.queryTeacherClassNum(that.teacherId) +"个班的");
            that.getClassRoomTable('teacher', {
                'course': that.courseTxt,
                'teacherId': that.teacherId
            });
        });
    },
    // 选择学生班级
    selectClassesEvent:function(){
        var that = this;
        $("#select-classes").change(function () {
            that.className = $(this).children('option:selected').text();
            that.studentClassId = $(this).children('option:selected').val();
            that.selectClassType = $(this).children('option:selected').attr('class_type');
            $('.classes-label').text(that.className);
            that.getQueryStudent(that.studentClassId,that.selectClassType);
        });
    },
    // 选择学生
    selectStudentEvent:function(){
        var that = this;
        $("#select-student").change(function () {
            that.studentName = $(this).children('option:selected').text();
            that.studentId = $(this).children('option:selected').val();
            $('.student-label').text(that.studentName);
            that.getClassRoomTable('student', {
                'classId': that.studentClassId,
                'studentNo': that.studentId
            });
        });
    },
    // 选择教室
    selectRoomEvent:function(){
        var that = this;
        $("#select-room").change(function () {
            var roomTxt = $(this).children('option:selected').text()
                ,roomId = $(this).children('option:selected').val();
            $('.room-name').text(roomTxt);
            that.getClassRoomTable('room', {
                'roomId': roomId
            });
        });
    },
    // 排课初始化状态
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


                        ///////////////////////////////////
                        //$('.one-key-page,.arranging-course-tips,.btn-one-key,.look-origin-schedule,.scheduling-error,.scheduling-error2').addClass('dh');
                        //$('#role-scheduling-tab,#control-jsp,.info-modify').removeClass('dh');
                        //that.getAllQueryCourse();
                        //that.getQueryCourse();
                        //that.getQueryClass("select-class");
                        //that.getQueryClass("select-classes");
                        //that.getQueryRoom();
                        ///////////////////////////////////

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
                        that.getAllQueryCourse();
                        that.getQueryCourse();
                        //console.log("flagClassType="+that.flagClassType)
                        // 2 走读, 2以外 行政
                        if(that.flagClassType=='2'){
                            that.getQueryClass("select-classes");
                            that.getQueryRoom();
                        }else{
                            that.getQueryClass("select-class");
                        }
                        break;
                    case 5:
                        console.log("排课失败2");
                        $('.btn-one-key,#role-scheduling-tab,#control-jsp,.arranging-course-tips,.scheduling-error').addClass('dh');
                        $('.scheduling-error2').removeClass('dh');


                        ///////////////////////////////////
                        //$('.one-key-page,.arranging-course-tips,.btn-one-key,.look-origin-schedule,.scheduling-error,.scheduling-error2').addClass('dh');
                        //$('#role-scheduling-tab,#control-jsp,.info-modify').removeClass('dh');
                        //that.getAllQueryCourse();
                        //that.getQueryCourse();
                        //that.getQueryClass("select-class");
                        //that.getQueryClass("select-classes");
                        //that.getQueryRoom();
                        ///////////////////////////////////

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
                var num = 0;
                Common.cookie.setCookie("resVal" + taskId, parseInt(dataNum));
                switch (dataNum) {
                    case "":
                        console.log("正在努力排课中,预计需要等待5-10分钟才能排出课表,请耐心等待哦0");
                        $('.arranging-course-tips').removeClass('dh');
                        $('.scheduling-error,#role-scheduling-tab,#control-jsp,.btn-one-key,.scheduling-error,.scheduling-error2,.info-modify').addClass('dh');
                        if (num == 2) {
                            console.log("排课任务状态返回错误");
                            clearInterval(that.items);
                            $('.btn-one-key,#role-scheduling-tab,#control-jsp,.arranging-course-tips,.scheduling-error,.info-modify').addClass('dh');
                            $('.scheduling-error2').removeClass('dh');
                        }
                        clearInterval(that.items);
                        that.items = setInterval(function () {
                            that.scheduleTaskState();
                            num++
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
                        that.getAllQueryCourse();
                        that.getQueryCourse();
                        console.log("flagClassType="+that.flagClassType)
                        // 2 走读, 2以外 行政
                        if(that.flagClassType=='2'){
                            that.getQueryClass("select-classes");
                            that.getQueryRoom();
                        }else{
                            that.getQueryClass("select-class");
                        }
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
    // 排课错误规则返回
    scheduleTaskError: function () {
        var that = this;
        $('.error-box-list').html('');
        Common.ajaxFun('/scheduleTask/error/desc', 'GET', {
            'taskId': taskId,
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var errorBoxList = [];
                for (var i = 0; i < res.bizData.length; i++) {
                    errorBoxList.push('<li>' + res.bizData[i] + '</li>');
                }
                $('.error-box-list').append(errorBoxList);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 重新排课
    scheduleTaskReload: function () {
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
                $('.teacher-label').text(selectedTxt + "老师共代"+ that.queryTeacherClassNum(selectedV) +"个班的");
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result);
        });
    },
    // 拉取教室
    getQueryRoom: function () {
        var that = this;
        Common.ajaxFun('/baseResult/queryRoom.do', 'GET', {
            "taskId": taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-room option').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    queryCourse.push('<option value="' + v.roomId + '">' + v.roomName + '</option>')
                });
                $('#select-room').append(queryCourse);
                $('#select-room option:eq(0)').attr('selected', 'selected');
                var selectedV = $('#select-room option:eq(0):selected').val()
                var selectedTxt = $('#select-room option:eq(0):selected').text()
                that.getClassRoomTable('room', {
                    'roomId': selectedV
                });
                $('.room-name').text(selectedTxt);
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result);
        });
    },
    // 拉取班级
    getQueryClass: function (classType) {
        var that = this;
        Common.ajaxFun('/baseResult/queryClass.do', 'GET', {
            "taskId": taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#'+ classType +' option:gt(0)').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    queryCourse.push('<option value="' + v.id + '" class_type="'+ v.class_type +'">' + v.class_name + '</option>')
                });
                $('#'+ classType).append(queryCourse);
                $('#'+ classType +' option:eq(1)').attr('selected', 'selected');
                var selectedV = $('#'+ classType +' option:eq(1):selected').val()
                var selectedTxt = $('#'+ classType +' option:eq(1):selected').text();
                var selectClassType = $('#'+ classType +' option:eq(1):selected').attr('class_type');
                if(classType=='select-class'){
                    $('.scheduling-name').text(selectedTxt);
                    that.getClassRoomTable('class', {'classId': selectedV,'classType':selectClassType}, selectedV);
                }else if(classType=='select-classes'){
                    that.studentClassId = selectedV;
                    $('.classes-label').text(selectedTxt + '-');
                    that.getQueryStudent(selectedV,selectClassType);
                }

            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    },
    // 获取年级班级类型
    queryGradeClassType:function(){
        var that = this;
        that.flagClassType = '';
        Common.ajaxFun('/baseResult/queryGradeClassType.do', 'GET', {
            'taskId': taskId
        }, function (res) {
            //console.log(res);
            if (res.rtnCode == '0000000') {
                that.flagClassType = res.bizData;
            }
        }, function (res) {
            layer.msg(res.msg);
        },true);
        return that.flagClassType;
    },
    // 拉取学生
    getQueryStudent: function (classId,classType) {
        var that = this;
        Common.ajaxFun('/baseResult/queryStudent.do', 'GET', {
            "taskId": taskId,
            "classId": classId,
            "classType":classType,
            "studentName":""
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-student option').remove();
                var queryCourse = [];
                $.each(result.bizData, function (i, v) {
                    queryCourse.push('<option value="' + v.studentNo + '">' + v.studentName + '</option>')
                });
                $('#select-student').append(queryCourse);
                $('#select-student option:eq(0)').attr('selected', 'selected');
                var selectedV = $('#select-student option:eq(0):selected').val()
                var selectedTxt = $('#select-student option:eq(0):selected').text();
                $('.student-label').text(selectedTxt);
                that.getClassRoomTable('student', {
                    'classId': classId,
                    'studentNo': selectedV
                });
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    },
    // 拉取课表 (班级\教师\学生\教室)
    getClassRoomTable: function (urlType, param) {
        Common.ajaxFun('/scheduleTask/' + urlType + '/course/result.do', 'GET', {
            "taskId": taskId,
            "param": JSON.stringify(param)
        }, function (result) {
            //console.log(result);
            if (result.rtnCode == "0000000") {
                var theadTemplate = Handlebars.compile($("#" + urlType + "-thead-list-template").html());
                Handlebars.registerHelper("thead", function (res) {
                    //console.log(res);
                    var resData = res.split('|');
                    var str = '<td class="center">节次</td>';
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
                        trHtml += '<td class="center">第' + (i + 1) + '节</td>';
                        for (var j = 0; j < wkList.length; j++) {
                            if (wkList[j][i] == null || wkList[j][i] == "") {
                                trHtml += '<td class="center ' + urlType + 'CourseTable" x="' + i + '" y="' + j + '"></td>';
                            } else {
                                trHtml += '<td class="center ' + urlType + 'CourseTable" x="' + i + '" y="' + j + '"><span class="course-table-name">' + wkList[j][i] + '</span></td>';
                            }
                        }
                        trHtml += '</tr>';
                    }
                    return trHtml;
                });
                $("#" + urlType + "-tbody-list").html(tbodyTemplate(result));
                if(urlType == 'teacher'){

                }
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        });
    },
    // 拉取总课表
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
                    var str = '<td class="center"></td>';
                    for (var i = 0; i < resData.length; i++) {
                        str += '<td class="center" colspan="' + itemCount + '">' + resData[i] + '</td>';
                    }
                    return str;
                });
                $("#all-thead-list").html(theadTemplate(res));


                var tbodyTemplate = Handlebars.compile($("#all-tbody-list-template").html());
                Handlebars.registerHelper("room", function (res) {
                    var rmWk = res.roomData;
                    //console.log(rmWk.length)
                    var rmList = res.room.split('|');
                    var trHtml = '';
                    for (var i = 0; i < rmList.length; i++) {
                        trHtml += '<tr>';
                        trHtml += '<td class="center"><div style="width:100px">' + rmList[i] + '</div></td>';
                        //console.log(rmWk[i])
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
    },
    // 行政班调课 根据坐标获取成功状态
    queryStatusByCoord: function (type,posX, posY, selectedV) {
        var that = this;
        var coord = [posY, posX];
        Common.ajaxFun('/scheduleTask/'+ type +'/queryStatusByCoord.do', 'GET', {
            'taskId': taskId,
            'id': selectedV,
            'coord': JSON.stringify(coord)
        }, function (res) {
            if (res.rtnCode == '0000000' && res.bizData == true) {
                console.log('根据坐标获取成功状态请求成功');
                that.colorScheduleResult(type);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 遍历坐标
    courseTableEvent:function(obj){
        var that = this;
        $('body').on('click', '.'+ obj +'CourseTable', function () {
            if ($(this).attr('flag') == undefined && $(this).text()!="") {
                that.posX = $(this).attr('x');
                that.posY = $(this).attr('y');
                var selectedV = $('#select-'+obj).children('option:selected').val();
                $('#'+ obj +'-tbody-list').find('.'+ obj +'CourseTable').attr('flag', false).removeAttr('style');
                $(this).attr('flag', true).css('color','#c00').append('<span class="wait-course">待调课</span>');
                that.queryStatusByCoord(obj,that.posX, that.posY, selectedV);
                if(obj=='teacher'){
                    that.queryClassByCoord(obj,that.posX, that.posY, selectedV);
                }
            } else if ($(this).attr('flag') == 'false' && $(this).attr('style') != undefined) {
                that.tarPosX = $(this).attr('x');
                that.tarPosY = $(this).attr('y');
                var selectedV = $('#select-'+obj).children('option:selected').val();
                $('#'+ obj +'-tbody-list').find('.'+ obj +'CourseTable[flag-txt="true"]').find('.course-table-name').html('');
                $('#'+ obj +'-tbody-list').find('.'+ obj +'CourseTable').attr('flag', false).removeAttr('style').removeAttr('flag').removeAttr('flag-txt');
                that.exchange(obj,that.posX, that.posY, selectedV, that.tarPosX, that.tarPosY);
                var originalTxt = $('.'+ obj +'CourseTable[x="'+ that.posX +'"][y="'+ that.posY +'"]').find('.course-table-name').html();
                var newsTxt = $('.'+ obj +'CourseTable[x="'+ that.tarPosX +'"][y="'+ that.tarPosY +'"]').find('.course-table-name').html();
                $('.'+ obj +'CourseTable[x="'+ that.posX +'"][y="'+ that.posY +'"]').find('.wait-course').remove();
                $('.'+ obj +'CourseTable[x="'+ that.posX +'"][y="'+ that.posY +'"]').find('.course-table-name').html(newsTxt);
                $('.'+ obj +'CourseTable[x="'+ that.tarPosX +'"][y="'+ that.tarPosY +'"]').find('.course-table-name').html(originalTxt);
            }else if ($(this).attr('flag')=='true'){
                $('#'+ obj +'-tbody-list').find('.'+ obj +'CourseTable[flag-txt="true"]').html('');
                $('#'+ obj +'-tbody-list').find('.'+ obj +'CourseTable').attr('flag', false).removeAttr('style').removeAttr('flag').removeAttr('flag-txt');
                $(this).attr('flag')=='false';
                $(this).find('.wait-course').remove();
            }
        });
    },
    // 行政班调课 根据状态获取可调颜色类型
    colorScheduleResult: function (type) {
        var that = this;
        Common.ajaxFun('/scheduleTask/adjustment/schedule/result.do', 'GET', {
            'taskId': taskId,
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == '0000000') {
                var state = res.bizData;
                /*
                 * 0:调课中
                 1:调课成功
                 -1:调课失败(数据异常)
                 -2:调课失败(系统异常)
                 * */
                //console.log(res);
                switch (state) {
                    case "0":
                        console.log("调课中");
                        clearInterval(that.items2);
                        that.items2 = setInterval(function () {
                            that.colorScheduleResult(type);
                        }, 30000);
                        layer.msg('调课失败!');
                        $('#'+ type +'-tbody-list').find('.'+ type +'CourseTable[flag-txt="true"]').find('.course-table-name').html('');
                        $('#'+ type +'-tbody-list').find('.'+ type +'CourseTable').attr('flag', false).removeAttr('style').removeAttr('flag').removeAttr('flag-txt');
                        $('.'+ type +'CourseTable').find('.wait-course').remove();
                        break;
                    case "1":
                        console.log("调课成功");
                        clearInterval(that.items2);
                        that.scheduleTaskSuccess(type);
                        break;
                    case "-1":
                        console.log("调课失败(数据异常)");
                        clearInterval(that.items2);
                        layer.msg('调课失败!');
                        $('#'+ type +'-tbody-list').find('.'+ type +'CourseTable[flag-txt="true"]').find('.course-table-name').html('');
                        $('#'+ type +'-tbody-list').find('.'+ type +'CourseTable').attr('flag', false).removeAttr('style').removeAttr('flag').removeAttr('flag-txt');
                        $('.'+ type +'CourseTable').find('.wait-course').remove();
                        break;
                    case "-2":
                        console.log("调课失败(系统异常)");
                        clearInterval(that.items2);
                        layer.msg('调课失败!');
                        $('#'+ type +'-tbody-list').find('.'+ type +'CourseTable[flag-txt="true"]').find('.course-table-name').html('');
                        $('#'+ type +'-tbody-list').find('.'+ type +'CourseTable').attr('flag', false).removeAttr('style').removeAttr('flag').removeAttr('flag-txt');
                        $('.'+ type +'CourseTable').find('.wait-course').remove();
                        break;
                    default:
                        break;

                }
            }
        }, function (res) {
            layer.msg(res.msg);
        });

    },
    // 行政班调课 拉取颜色列表
    scheduleTaskSuccess: function (type) {
        var that = this;
        Common.ajaxFun('/scheduleTask/adjustment/success.do', 'GET', {
            'taskId': taskId,
            'tnId': tnId
        }, function (res) {
            //console.log(res);
            // 0白色，1红色，2黄色，3绿色
            if (res.rtnCode == '0000000') {
                var datas = res.bizData;
                $.each($('.'+ type +'CourseTable'), function (i, v) {
                    //console.log($(v).attr('flag'));
                    var colorValue = '';
                    switch (datas[i]) {
                        case "0":
                            colorValue = "#fff";
                            //0 白色
                            break;
                        case "1":
                            colorValue = "#8EC0FA";
                            //1红色
                            break;
                        case "2":
                            colorValue = "#F7C572";
                            //2黄色
                            break;
                        case "3":
                            colorValue = "#F98F90";
                            //3绿色
                            break;
                        default:
                            break;
                    }
                    if ($(v).attr('flag') != "true" && colorValue != "#fff") {
                        $(v).css('background-color', colorValue);
                    }

                })


            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 行政班调课 提交两个可调课程坐标
    exchange: function (type,posX, posY, selectedV, tarPosX, tarPosY) {
        var that = this;
        Common.ajaxFun('/scheduleTask/'+ type +'/exchange.do', 'GET', {
            'taskId': taskId,
            'id': selectedV,
            'source': JSON.stringify([posY, posX]),
            'target': JSON.stringify([tarPosY, tarPosX])
        }, function (res) {
            //console.log(res);
            if (res.rtnCode == '0000000') {
                var datas = res.bizData;
                //console.log(datas);
                if(res.bizData==true){
                    layer.msg("调课成功!");
                }

            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 行政班调课 根据老师坐标填充空白部分
    queryClassByCoord: function (type,posX, posY,selectedV) {
        var that = this;
        Common.ajaxFun('/scheduleTask/teacher/queryClassByCoord.do', 'GET', {
            'taskId': taskId,
            'id': selectedV,
            'coord': JSON.stringify([posY, posX])
        }, function (res) {
            //console.log(res);
            var datas = res.bizData;
            if (res.rtnCode == '0000000') {
                //console.log(datas.length)
                // 一周
                for(var i=0;i<datas.length;i++){
                    //console.log(datas[i])
                    // 一天节数
                    for(var j=0;j<datas[i].length;j++){
                        console.log(datas[i][j])
                        //$('.teacherCourseTable[x="'+ j +'"][y="'+ i +'"]').text();
                        var defaultDatas = $('.'+ type +'CourseTable[x="'+ j +'"][y="'+ i +'"]').text();
                        console.log(defaultDatas)
                        //console.log('x='+j +",y="+i +"=="+ defaultDatas);
                        if(defaultDatas==""){
                            $('.'+ type +'CourseTable[x="'+ j +'"][y="'+ i +'"]').html('<span class="course-table-name">'+datas[i][j]+'</span>').attr('flag-txt','true');
                        }
                    }
                }
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 获取老师教课班级数量 /baseResult/queryTeacherClassNum.do
    queryTeacherClassNum: function (teacherId) {
        var that = this;
        var teacherClassNum = '';
        Common.ajaxFun('/baseResult/queryTeacherClassNum.do', 'GET', {
            'teacherId': teacherId
        }, function (res) {
            //console.log(res);
            if (res.rtnCode == '0000000') {
                teacherClassNum = res.bizData;
            }
        }, function (res) {
            layer.msg(res.msg);
        },true);
        return teacherClassNum;
    }
};

new CourseTable();
