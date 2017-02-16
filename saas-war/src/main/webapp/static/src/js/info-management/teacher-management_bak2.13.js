var tnId = Common.cookie.getCookie('tnId');


function TeacherManagement() {
    this.init();

}

TeacherManagement.prototype = {
    constructor: TeacherManagement,
    init: function () {
        this.getThead();
    },
    // 拉取表格thead
    getThead:function(){
        var that = this;
        Common.ajaxFun('/config/get/teacher/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.configList;
                if (data.length != 0) {
                    var columnHtml = [];
                    columnHtml.push('<tr>');
                    columnHtml.push('<th class="center"><label><input type="checkbox" id="checkAll" class="ace" /><span class="lbl"></span></label></th>');
                    columnHtml.push('<th class="center">序号</th>');
                    $.each(data, function (i, k) {
                        columnHtml.push('<th class="center">' + k.name + '</th>');
                    });
                    columnHtml.push('</tr>');
                    $("#teacher-manage-table thead").html(columnHtml.join(''));
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    // 默认拉取列表
    // 提交保存
    // 所教科目
    // 所教年级
    // 最大带班数
    // 所带班级list
    // 添加教师
    addTeacherLayer:function(title){
        var addTeacherContentHtml = [];
        addTeacherContentHtml.push('<div class="add-course-box">');
        addTeacherContentHtml.push('<div class="course-box">');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span><i>*</i>课程名称：</span>');
        addTeacherContentHtml.push('<input type="text" id="" value="" placeholder="" class=""/>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<button type="button" class="save-btn" id="save-btn">保存</button>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span class="layer-title">' + title + "</span>",
            offset: 'auto',
            area: ['550px', '350px'],
            content: addTeacherContentHtml.join(''),
            success: function () {

            }
        });
    }
    // 修改教师
    // 删除教师
    // 模板下载
    // 批量上传
};

var TeacherManagementIns = new TeacherManagement();

$(function () {
    // 点击添加教师
    $('#addTeacher-btn').on('click',function(){
        TeacherManagementIns.addTeacherLayer('添加教师');
    });
    // 点击保存
    $('#add-btn').on('click',function(){

    });
    // 点击修改
    $('#updateTeacher-btn').on('click',function(){

    });
    // 点击删除
    $('#delTeacher-btn').on('click',function(){

    });
    // 点击模板下载
    $('#downloadBtn').on('click',function(){

    });
    // 点击上传
    $('#uploadBtn').on('click',function(){

    });
});



