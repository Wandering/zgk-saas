
var taskId = Common.cookie.getCookie('taskId');

function TeachDate(){
    this.init();
    this.state = false;
}

TeachDate.prototype = {
    constructor: TeachDate,
    init: function () {

    },
    // 查询学习时间
    queryTeachTime:function(taskId){
        var that = this;
        Common.ajaxFun('/teachTime/queryTeachTime.do', 'GET', {
            'taskId':taskId
        },
            function (res) {
                console.log(res.bizData)


                if (res.rtnCode == "0000000") {
                    var teachDate = res.bizData.teachDate;
                    var teachTime = res.bizData.teachTime;

                    //if(!res.bizData.teachDate && !res.bizData.teachTime){
                    //    alert(3)
                    //    that.state
                    //}else{
                    //
                    //}


                    if(teachDate){
                        for(var i in teachDate.split('|')){
                            $('.week-list input[data="'+ teachDate.split('|')[i]  +'"]').attr('checked','checked');
                        }
                    }
                    if(teachTime){
                        $('#morning-list option[value="'+ teachTime.split("")[0] +'"]').attr('selected','selected');
                        $('#afternoon-list option[value="'+ teachTime.split("")[1] +'"]').attr('selected','selected');
                        $('#evening-list option[value="'+ teachTime.split("")[2] +'"]').attr('selected','selected');
                    }
                }

            }, function (res) {
                layer.msg(res.msg);
            });
    },
    // 保存教学时间
    saveTeachTime:function(taskId,teachDate,teachTime){
        Common.ajaxFun('/teachTime/saveTeachTime.do', 'POST', {
            'taskId':taskId,
            'teachDate':teachDate,
            'teachTime':teachTime
        },
        function (res) {
            if (res.rtnCode == "0000000" && res.bizData==true) {
                layer.msg('保存成功!');
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }
};

var TeachDateIns = new TeachDate();

$(function(){
    TeachDateIns.queryTeachTime(taskId);
    // 保存
    $('#btn-save-base').on('click',function(){
        var checkboxLen = $('.week-list input:checked').length;
        var weekV= $('.week-list input:checked');
        if (checkboxLen == 0) {
            layer.tips('每周上课天数至少选择一项', $(this));
            return false;
        }
        var teachDate = [];
        $.each(weekV,function(i,v){
            teachDate.push($(this).attr('data'));
        });
        var morningNum = $('#morning-list').val();
        var afternoonNum = $('#afternoon-list').val();
        var eveningNum = $('#evening-list').val();
        var teachTime = morningNum+afternoonNum+eveningNum;
        TeachDateIns.saveTeachTime(taskId,teachDate.join('|'),teachTime);
    });


});