var tnId = Common.cookie.getCookie('tnId');
var taskId = Common.cookie.getCookie('taskId');
function ClassRoomTable() {
    this.init();
    this.courseTxt='';
    this.teacherId='';
}
ClassRoomTable.prototype = {
    constructor: ClassRoomTable,
    init: function () {
        this.getClassRoom();
        this.getQueryCourse();
        this.getQueryTeacher();
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
                    queryCourse.push('<option value="'+ v.id +'">'+ v.courseName +'</option>')
                });
                $('#select-teacher').append(queryCourse);
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
            "taskId":"9",
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
                var theadTemplate = Handlebars.compile($("#grade-thead-list-template").html());
                $('#grade-thead-list').html(theadTemplate(weekList));
                var tbodyTemplate = Handlebars.compile($("#grade-tbody-list-template").html());
                $('#grade-tbody-list').html(tbodyTemplate(res.week));
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
        $('.teacher-name').text(selectedTxt);
        ClassRoomTableIns.getClassRoomTable('teacher', {'course': ClassRoomTableIns.courseTxt,'teacherId':ClassRoomTableIns.teacherId});
    });

    // 选择老师
    $("#select-teacher").change(function(){
        ClassRoomTableIns.teacherId = $(this).children('option:selected').val();
        //$('.teacher-name').text(selectedTxt);
        ClassRoomTableIns.getClassRoomTable('teacher', {'course': ClassRoomTableIns.courseTxt,'teacherId':ClassRoomTableIns.teacherId});
    });


    // 导出


});


