var tnId = Common.cookie.getCookie('tnId');

function classRoomManagement() {
    this.init();
}

classRoomManagement.prototype = {
    constructor: classRoomManagement,
    init: function () {
        this.getClassRoom();
    },
    getClassRoom:function () {
        var that = this;
        Common.ajaxFun('/config/classRoom/get/'+ tnId +'.do', 'GET', {}, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                that.renderList(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderList: function (data) {
        if (data.rtnCode == "0000000") {
            var gradeArr = [];
            $.each(data.bizData.classRoom, function (i, v) {
                gradeArr.push('<tr>');
                gradeArr.push('<td class="center">');
                gradeArr.push('<label>');
                gradeArr.push('<input type="checkbox" gradename="'+ v.grade +'" gradeId = "'+ v.id +'" class="ace" />');
                gradeArr.push('<span class="lbl"></span>');
                gradeArr.push('</label>');
                gradeArr.push('</td>');
                gradeArr.push('<td class="center">'+ (i+1) +'</td>');
                gradeArr.push('<td class="center">'+ v.grade +'</td>');
                gradeArr.push('<td class="center">'+ v.number +'</td>');
                gradeArr.push('</tr>');
            });
            $('#grade-list').append(gradeArr.join(''));
        }
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            //console.log(res)
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
        //Common.modal("addRole","添加角色",contentHtml.join(''),"内容","");
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

var classRoomManagementIns = new classRoomManagement();

$('#add-btn').on('click',function(){
    classRoomManagementIns.addGrade('添加教室');
});

$('body').on('click','.save-btn',function () {
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
            classRoomManagementIns.getGrade();
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
    classRoomManagementIns.addGrade('修改教室');
    $('.save-btn').attr('gradeid',checkV);
    $('#grade-name').val(gradename);
});


$('body').on('click','.close-btn',function(){
    layer.closeAll();
});