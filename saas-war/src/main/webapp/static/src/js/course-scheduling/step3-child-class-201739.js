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
    this.studentClassId = '';
    this.studentId = '';
    this.selectClassType = '';
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
                    ClassRoomTableIns.studentClassId = selectedV;
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
                    console.log(res);
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
                                trHtml += '<td class="center ' + urlType + 'CourseTable" x="' + i + '" y="' + j + '"></td>';
                            } else {
                                trHtml += '<td class="center ' + urlType + 'CourseTable" x="' + i + '" y="' + j + '">' + wkList[j][i] + '</td>';
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
    }

};


var ClassRoomTableIns = new ClassRoomTable();


var HashHandle = {
    init: function () {
        this.hashArr = ['#all', '#class', '#teacher','#student','#room'];
        this.addEvent();
        this.hashOperate();
        this.initStatus();
        this.queryGradeClassType();
        this.posX = '';
        this.posY = '';
        this.tarPosX = '';
        this.tarPosY = '';
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


                        ///////////////////////////////////
                        //$('.one-key-page,.arranging-course-tips,.btn-one-key,.look-origin-schedule,.scheduling-error,.scheduling-error2').addClass('dh');
                        //$('#role-scheduling-tab,#control-jsp,.info-modify').removeClass('dh');
                        //ClassRoomTableIns.getAllQueryCourse();
                        //ClassRoomTableIns.getQueryCourse();
                        //ClassRoomTableIns.getQueryClass();
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
                        ClassRoomTableIns.getAllQueryCourse();
                        ClassRoomTableIns.getQueryCourse();
                        ClassRoomTableIns.getQueryClass("select-class");
                        ClassRoomTableIns.getQueryClass("select-classes");
                        ClassRoomTableIns.getQueryRoom();
                        break;
                    case 5:
                        console.log("排课失败2");
                        $('.btn-one-key,#role-scheduling-tab,#control-jsp,.arranging-course-tips,.scheduling-error').addClass('dh');
                        $('.scheduling-error2').removeClass('dh');


                        ///////////////////////////////////
                        //$('.one-key-page,.arranging-course-tips,.btn-one-key,.look-origin-schedule,.scheduling-error,.scheduling-error2').addClass('dh');
                        //$('#role-scheduling-tab,#control-jsp,.info-modify').removeClass('dh');
                        //ClassRoomTableIns.getAllQueryCourse();
                        //ClassRoomTableIns.getQueryCourse();
                        //ClassRoomTableIns.getQueryClass();
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
                        ClassRoomTableIns.getAllQueryCourse();
                        ClassRoomTableIns.getQueryCourse();
                        ClassRoomTableIns.getQueryClass("select-class");
                        ClassRoomTableIns.getQueryClass("select-classes");
                        ClassRoomTableIns.getQueryRoom();
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
    // 重新排课 /scheduleTask/reload/trigger.do
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
    // 根据坐标获取成功状态 /scheduleTask/{type}/queryStatusByCoord.do
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
    // 根据状态获取可调颜色类型 /scheduleTask/adjustment/schedule/result.do
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
                        break;
                    case "1":
                        console.log("调课中");
                        clearInterval(that.items2);
                        that.scheduleTaskSuccess(type);
                        break;
                    case "-1":
                        console.log("调课中");
                        clearInterval(that.items2);
                        break;
                    case "-2":
                        console.log("调课中");
                        clearInterval(that.items2);
                        break;
                    default:
                        break;

                }
            }


        }, function (res) {
            layer.msg(res.msg);
        });

    },
    // 拉取颜色列表
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
                            colorValue = "#f00";
                            //1红色
                            break;
                        case "2":
                            colorValue = "#ff0";
                            //2黄色
                            break;
                        case "3":
                            colorValue = "#0f0";
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
    // 提交两个可调课程坐标  /scheduleTask/{type}/exchange.do
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
    // 删除之前颜色状态
    // 根据老师坐标填充空白部分 /scheduleTask/teacher/queryClassByCoord.do  老师课表
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
                        //console.log(datas[i][j])
                        //$('.teacherCourseTable[x="'+ j +'"][y="'+ i +'"]').text();
                        var defaultDatas = $('.'+ type +'CourseTable[x="'+ j +'"][y="'+ i +'"]').text();
                        //console.log('x='+j +",y="+i +"=="+ defaultDatas);
                        if(defaultDatas==""){
                            $('.'+ type +'CourseTable[x="'+ j +'"][y="'+ i +'"]').text(datas[i][j]).attr('flag-txt','true');
                        }
                    }
                }
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    //获取年级班级类型 /baseResult/queryGradeClassType.do
    queryGradeClassType:function(){
        var that = this;
        Common.ajaxFun('/baseResult/queryGradeClassType.do', 'GET', {
            'taskId': taskId
        }, function (res) {
            //console.log(res);
            if (res.rtnCode == '0000000') {
                // 2:行政  2以外:走读
                if(res.bizData=='2'){
                    $('.student-tab,.room-tab').addClass('dh');
                }else{
                    $('.student-tab,.room-tab').removeClass('dh');
                }
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
    $('.retry-scheduling').on('click', function () {
        $('.btn-one-key').addClass('dh');
        HashHandle.scheduleTaskReload();
    });

    // 查看原课表
    $('.look-origin-schedule').on('click', function () {

    });

    // 选择班级
    $("#select-class").change(function () {
        var selectedTxt = $(this).children('option:selected').text();
        var selectedV = $(this).children('option:selected').val();
        var selectClassType = $(this).children('option:selected').attr('class_type');
        $('.scheduling-name').text(selectedTxt);
        ClassRoomTableIns.getClassRoomTable('class', {'classId': selectedV,'classType':selectClassType}, selectedV);
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

    // 选择教室
    $("#select-room").change(function () {
        var roomTxt = $(this).children('option:selected').text()
            ,roomId = $(this).children('option:selected').val();
        $('.room-name').text(roomTxt);
        ClassRoomTableIns.getClassRoomTable('room', {
            'roomId': roomId
        });
    });

    //// 选择学生班级
    $("#select-classes").change(function () {
        ClassRoomTableIns.className = $(this).children('option:selected').text();
        ClassRoomTableIns.studentClassId = $(this).children('option:selected').val();
        ClassRoomTableIns.selectClassType = $(this).children('option:selected').attr('class_type');
        $('.classes-label').text(ClassRoomTableIns.className);
        ClassRoomTableIns.getQueryStudent(ClassRoomTableIns.studentClassId,ClassRoomTableIns.selectClassType);
    });

    // 选择学生
    $("#select-student").change(function () {
        ClassRoomTableIns.studentName = $(this).children('option:selected').text();
        ClassRoomTableIns.studentId = $(this).children('option:selected').val();
        $('.student-label').text(ClassRoomTableIns.studentName);
        ClassRoomTableIns.getClassRoomTable('student', {
            'classId': ClassRoomTableIns.studentClassId,
            'studentNo': ClassRoomTableIns.studentId
        });
    });

    // 拉取所有课表
    $("#role-scheduling-tab li").eq(0).click(function () {
        ClassRoomTableIns.getAllQueryCourse();
    });
    if (window.location.hash == '#all') {
        ClassRoomTableIns.getAllQueryCourse();
    }

    // 遍历坐标
    function courseTableEvent(obj){
        $('body').on('click', '.'+ obj +'CourseTable', function () {
            if ($(this).attr('flag') == undefined && $(this).text()!="") {
                HashHandle.posX = $(this).attr('x');
                HashHandle.posY = $(this).attr('y');
                var selectedV = $('#select-'+obj).children('option:selected').val();
                $('#'+ obj +'-tbody-list').find('.'+ obj +'CourseTable').attr('flag', false).removeAttr('style');
                $(this).attr('flag', true);
                HashHandle.queryStatusByCoord(obj,HashHandle.posX, HashHandle.posY, selectedV);
                if(obj=='teacher'){
                    HashHandle.queryClassByCoord(obj,HashHandle.posX, HashHandle.posY, selectedV);
                }
            } else if ($(this).attr('flag') == 'false' && $(this).attr('style') != undefined) {
                HashHandle.tarPosX = $(this).attr('x');
                HashHandle.tarPosY = $(this).attr('y');
                var selectedV = $('#select-'+obj).children('option:selected').val();
                $('#'+ obj +'-tbody-list').find('.'+ obj +'CourseTable[flag-txt="true"]').text('');
                $('#'+ obj +'-tbody-list').find('.'+ obj +'CourseTable').attr('flag', false).removeAttr('style').removeAttr('flag').removeAttr('flag-txt');
                HashHandle.exchange(obj,HashHandle.posX, HashHandle.posY, selectedV, HashHandle.tarPosX, HashHandle.tarPosY);
                var originalTxt = $('.'+ obj +'CourseTable[x="'+ HashHandle.posX +'"][y="'+ HashHandle.posY +'"]').text();
                var newsTxt = $('.'+ obj +'CourseTable[x="'+ HashHandle.tarPosX +'"][y="'+ HashHandle.tarPosY +'"]').text();
                $('.'+ obj +'CourseTable[x="'+ HashHandle.posX +'"][y="'+ HashHandle.posY +'"]').text(newsTxt);
                $('.'+ obj +'CourseTable[x="'+ HashHandle.tarPosX +'"][y="'+ HashHandle.tarPosY +'"]').text(originalTxt);
            }
        });
    }
    courseTableEvent('class');
    courseTableEvent('teacher');


});


//导出下载课表====临时

$(document).on('click', '.output-tpl1', function () {
    window.location.href = '/scheduleTask/class/course/export.do?taskId=' + taskId;
});
$(document).on('click', '.output-tpl2', function () {
    window.location.href = '/scheduleTask/teacher/course/export.do?taskId=' + taskId;
});
$(document).on('click', '.output-tpl3', function () {
    window.location.href = '/scheduleTask/room/course/export.do?taskId=' + taskId;
});
$(document).on('click', '.output-tpl4', function () {
    window.location.href = '/scheduleTask/student/course/export.do?taskId=' + taskId;
});