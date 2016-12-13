var tnId = Common.cookie.getCookie('tnId');
var taskId = Common.cookie.getCookie('taskId');
function ClassRoomTable() {
    this.init();
    this.courseTxt='';
    this.teacherId='';
    this.teacherTxt='';
    this.className='';
    this.studentName='';
    this.classId='';
    this.studentId='';
}
ClassRoomTable.prototype = {
    constructor: ClassRoomTable,
    init: function () {
        this.getClassRoom();
        this.getQueryCourse();
        this.getQueryTeacher();
        this.getQueryClass();
        this.getQueryStudent();
        //this.getClassRoomTable();
    },
    // 拉取教室
    getClassRoom:function(){
        Common.ajaxFun('/baseResult/queryRoom.do', 'GET', {
            "taskId":taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-class option:gt(0)').remove();
                var classRoom=[];
                $.each(result.bizData,function(i,v){
                    classRoom.push('<option value="'+ v.id +'">'+ v.roomName +'</option>')
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
    getQueryCourse:function(){
        Common.ajaxFun('/baseResult/queryCourse.do', 'GET', {
            "taskId":taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-queryCourse option:gt(0)').remove();
                var queryCourse=[];
                $.each(result.bizData,function(i,v){
                    queryCourse.push('<option value="'+ v.id +'">'+ v.courseName +'</option>')
                });
                $('#select-queryCourse').append(queryCourse);
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    },
    // 拉取老师
    getQueryTeacher:function(){
        Common.ajaxFun('/baseResult/queryTeacher.do', 'GET', {
            "taskId":taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-teacher option:gt(0)').remove();
                var queryCourse=[];
                $.each(result.bizData,function(i,v){
                    queryCourse.push('<option value="'+ v.id +'">'+ v.teacherName +'</option>')
                });
                $('#select-teacher').append(queryCourse);
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
    },
    // 拉取班级
    getQueryClass:function(){
        Common.ajaxFun('/baseResult/queryClass.do', 'GET', {
            "taskId":taskId
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-classes option:gt(0)').remove();
                var queryCourse=[];
                $.each(result.bizData,function(i,v){
                    queryCourse.push('<option value="'+ v.id +'">'+ v.className +'</option>')
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
    getQueryStudent:function(){
        Common.ajaxFun('/baseResult/queryStudent.do', 'GET', {
            "taskId":taskId,
            "classId":'1'
        }, function (result) {
            if (result.rtnCode == "0000000") {
                $('#select-student option:gt(0)').remove();
                var queryCourse=[];
                $.each(result.bizData,function(i,v){
                    queryCourse.push('<option value="'+ v.id +'">'+ v.studentName +'</option>')
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
    getClassRoomTable: function (urlType,param) {
        Common.ajaxFun('/scheduleTask/'+ urlType +'/course/result.do', 'GET', {
            "taskId":taskId,
            "param":JSON.stringify(param)
        }, function (result) {
            console.log(result)
            if (result.rtnCode == "0000000") {
                var res =  result.bizData.result;
                Handlebars.registerHelper("addOne", function (index) {
                    return parseInt(index) + 1;
                });
                Handlebars.registerHelper("createN", function (res) {
                    var str = '';
                    for(var i=1;i<=res;i++){
                        str += '<p class="tbody-item">'+i+'</p>'
                    }
                    return str;
                });
                var weekList = res.teachDate.split('|');
                var theadTemplate = Handlebars.compile($("#"+ urlType +"-thead-list-template").html());
                $("#"+ urlType +"-thead-list").html(theadTemplate(weekList));
                var tbodyTemplate = Handlebars.compile($("#"+ urlType +"-tbody-list-template").html());
                $("#"+ urlType +"-tbody-list").html(tbodyTemplate(res.week));
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
    $("#select-class").change(function(){
        var selectedTxt = $(this).children('option:selected').text();
        var selectedV = $(this).children('option:selected').val();
        $('.scheduling-name').text(selectedTxt);
        ClassRoomTableIns.getClassRoomTable('room',{'room': selectedV},selectedV);
    });

    // 选择课程
    $("#select-queryCourse").change(function(){
        ClassRoomTableIns.courseTxt = $(this).children('option:selected').text();
        var selectedV = $(this).children('option:selected').val();
        $('.course-label').text(ClassRoomTableIns.courseTxt );
        ClassRoomTableIns.getClassRoomTable('teacher', {'course': ClassRoomTableIns.courseTxt,'teacherId':ClassRoomTableIns.teacherId});
    });

    // 选择老师
    $("#select-teacher").change(function(){
        ClassRoomTableIns.teacherTxt = $(this).children('option:selected').text();
        ClassRoomTableIns.teacherId = $(this).children('option:selected').val();
        $('.teacher-label').text(ClassRoomTableIns.teacherTxt + "老师");
        ClassRoomTableIns.getClassRoomTable('teacher', {'course': ClassRoomTableIns.courseTxt,'teacherId':ClassRoomTableIns.teacherId});
    });

    // 选择班级
    $("#select-classes").change(function(){
        ClassRoomTableIns.className = $(this).children('option:selected').text();
        ClassRoomTableIns.classId = $(this).children('option:selected').val();
        $('.classes-label').text(ClassRoomTableIns.className);
        ClassRoomTableIns.getClassRoomTable('student', {'classId': ClassRoomTableIns.classId,'studentId':ClassRoomTableIns.studentId});
    });

    // 选择学生
    $("#select-student").change(function(){
        ClassRoomTableIns.studentName = $(this).children('option:selected').text();
        ClassRoomTableIns.studentId = $(this).children('option:selected').val();
        $('.student-label').text(ClassRoomTableIns.studentName + "学生");
        ClassRoomTableIns.getClassRoomTable('student', {'classId': ClassRoomTableIns.classId,'studentId':ClassRoomTableIns.studentId});
    });
});


