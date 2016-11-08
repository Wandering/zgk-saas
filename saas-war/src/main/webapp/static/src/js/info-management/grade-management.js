var tnId = Common.cookie.getCookie('tnId');


function GradeManagement() {
    this.init();
}
GradeManagement.prototype = {
    constructor: GradeManagement,
    init: function () {
        this.getGrade();
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            //console.log(res)
            if (res.rtnCode == "0000000") {
                that.renderList(res);
            }
        }, function (res) {
            alert("出错了");
        }, true);
    },
    renderList: function (data) {
        console.log(data);
        if (data.rtnCode == "0000000") {
            var gradeArr = [];
            $.each(data.bizData.grades, function (i, v) {
                gradeArr.push('<tr>');
                gradeArr.push('<td class="center">');
                gradeArr.push('<label>');
                gradeArr.push('<input type="checkbox" gradename="'+ v.grade +'" gradeId = "'+ v.id +'" class="ace" />');
                gradeArr.push('<span class="lbl"></span>');
                gradeArr.push('</label>');
                gradeArr.push('</td>');
                gradeArr.push('<td class="center">'+ (i+1) +'</td>');
                gradeArr.push('<td class="center">'+ v.grade +'</td>');
                gradeArr.push('</tr>');
            });
            $('#grade-list').append(gradeArr.join(''));
        }
    },
    addGrade:function(title){
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal grade-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="grade-name"> 年级名称 </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<input type="text" id="grade-name" placeholder="输入年级名称" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info save-btn" >保存</button><button class="btn btn-primary close-btn">取消</button></div>');
        contentHtml.push('</div>');
        //Common.modal("addRole","添加角色",contentHtml.join(''),"内容","");
        layer.open({
            type: 1,
            title: title,
            offset: 'auto',
            area: ['362px', '185px'],
            content: contentHtml.join('')
        });
    },
    deleteGrade:function(){
        var that = this;
        var checkedLen = $("#grade-list :checkbox:checked").size();
        if(checkedLen=="0"){
            layer.tips('至少选择一项', $('.del-btn'));
            return false;
        }
        var selItem = [];
        $('#grade-list').find('input[type="checkbox"]').each(function (i, v) {
            if ($(this).is(':checked') == true) {
                selItem.push('-' + $(this).attr('gradeid'));
            }
        });
        selItem = selItem.join('');
        selItem = selItem.substring(1, selItem.length);
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            Common.ajaxFun('/manage/grade/delete/'+ selItem +'.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    if(res.bizData.result="SUCCESS"){
                        layer.closeAll();
                        $('#grade-list').html('');
                        that.getGrade();
                    }
                }
            }, function (res) {
                alert("出错了");
            });
        }, function () {
            layer.closeAll();
        });
    }
};
var GradeManagementIns = new GradeManagement();
$('#add-btn').on('click',function(){
    GradeManagementIns.addGrade('添加年级');
});
$('body').on('click','.save-btn',function () {
    var gradeName = $.trim($('#grade-name').val());
    var gradeid = $(this).attr('gradeid');
    if(gradeName==""){
        layer.tips('请填写年级名称!', $('#grade-name'));
        return false;
    }

    if(!gradeid){
        Common.ajaxFun('/manage/grade/add/'+ tnId +'.do', 'POST',{
            gradeName:gradeName
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                if(res.bizData.result=='SUCCESS'){
                    $('#grade-list').html('');
                    GradeManagementIns.getGrade();
                    layer.closeAll();
                }
            }
        }, function (res) {
            alert("出错了");
        });
    }else{
        Common.ajaxFun('/manage/grade/modify/'+ tnId +'/'+ gradeid +'.do', 'POST',{
            gradeName:gradeName
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                if(res.bizData.result=='SUCCESS'){
                    $('#grade-list').html('');
                    GradeManagementIns.getGrade();
                    layer.closeAll();
                }
            }
        }, function (res) {
            alert("出错了");
        });
    }

});
$('#modify-btn').on('click',function () {
    var that = $(this);
    var chknum = $(".check-template :checkbox:checked").size();
    var checkV = $(".check-template :checkbox:checked").attr('gradeid');
    var gradename = $(".check-template :checkbox:checked").attr('gradename');
    console.log(checkV);
    if(chknum!='1'){
        layer.tips('修改只能选择一项!',that);
        return false;
    }
    GradeManagementIns.addGrade('修改年级');
    $('.save-btn').attr('gradeid',checkV);
    $('#grade-name').val(gradename);
});



$('body').on('click','.close-btn',function(){
    layer.closeAll();
});

$('body').on('click', '.del-btn', function () {
    GradeManagementIns.deleteGrade()
});