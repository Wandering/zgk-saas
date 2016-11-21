var tnId = Common.cookie.getCookie('tnId');

function ClassRoomManagement() {
    this.init();
}

ClassRoomManagement.prototype = {
    constructor: ClassRoomManagement,
    init: function () {
        this.getClassRoom();
    },
    getClassRoom:function () {
        var that = this;
        Common.ajaxFun('/config/classRoom/get/'+ tnId +'.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderList(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderList: function (data) {
        if (data.rtnCode == "0000000") {
            var classRoomHtml = [];
            $.each(data.bizData.classRoom, function (i, v) {
                classRoomHtml.push('<tr>');
                classRoomHtml.push('<td class="center">');
                classRoomHtml.push('<label>');
                classRoomHtml.push('<input type="checkbox" gradename="'+ v.grade +'" gradeId = "'+ v.gradeId +'" class="ace" />');
                classRoomHtml.push('<span class="lbl"></span>');
                classRoomHtml.push('</label>');
                classRoomHtml.push('</td>');
                classRoomHtml.push('<td class="center">'+ (i+1) +'</td>');
                classRoomHtml.push('<td class="center">'+ v.grade +'</td>');
                classRoomHtml.push('<td class="center">'+ v.number +'</td>');
                classRoomHtml.push('</tr>');
            });
            $('#classroom-table tbody').html(classRoomHtml.join(''));
        }
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var grade = [];
                grade.push('<option value="00">请选择年级</option>')
                $.each(res.bizData.grades,function(i,v){
                    grade.push('<option value="'+ v.id +'">'+ v.grade +'</option>')
                });
                $('#grade-list').append(grade.join(''));
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    addGrade:function (title) {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal grade-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="classroom-num"> 教室数量 </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<input type="text" id="classroom-num" placeholder="输入教室数量" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="grade-name"> 选择年级 </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<select id="grade-list"></select>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info save-btn" id="save-classroom-btn">保存</button><button class="btn btn-primary close-btn">取消</button></div>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: title,
            offset: 'auto',
            area: ['362px', '230px'],
            content: contentHtml.join('')
        });
        that.getGrade();
    },
    deleteGrade:function(){

    }
};

var classRoomManagement = new ClassRoomManagement();

$('#add-btn').on('click',function(){
    classRoomManagement.addGrade('添加教室');
});

$(document).on('click','.save-btn',function () {
    var classroomNum = $.trim($('#classroom-num').val());
    var gradeV = $('#grade-list').val();
    if (classroomNum == '') {
        layer.tips('请填写教室数量!', $('#classroom-num'));
        return false;
    }
    if (gradeV == '00') {
        layer.tips('请选择年级!', $('#grade-list'));
        return false;
    }

    Common.ajaxFun('/manage/classRoom/add/'+ tnId +'/' + gradeV + '.do', 'POST',{
        crNum: classroomNum
    }, function (res) {
        if (res.rtnCode == "0000000") {
            $('#grade-list').html('');
            classRoomManagement.getClassRoom();
            layer.closeAll();
        } else if (res.rtnCode == '1000001') {
            layer.closeAll();
            layer.msg(res.msg);
        }
    }, function (res) {
        layer.msg("出错了");
    });

});

$('#modify-btn').on('click',function () {
    var that = $(this);
    var chknum = $(".check-template :checkbox:checked").size();
    var checkV = $(".check-template :checkbox:checked").attr('gradeid');
    var gradename = $(".check-template :checkbox:checked").attr('gradename');
    if(chknum!='1'){
        layer.tips('修改只能选择一项!',that);
        return false;
    }
    classRoomManagement.addGrade('修改教室');
    $('.save-btn').attr('gradeid',checkV);
    $('#grade-name').val(gradename);
});


$(document).on('click','.close-btn',function(){
    layer.closeAll();
});