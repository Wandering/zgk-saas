var taskId = Common.cookie.getCookie('taskId');

function TeachDate() {
    this.init();
    this.state = false;
}

TeachDate.prototype = {
    constructor: TeachDate,
    init: function () {
        this.queryTeachTime(taskId);
    },
    // 查询学习时间
    queryTeachTime: function (taskId) {
        var that = this;
        Common.ajaxFun('/teachTime/queryTeachTime.do', 'GET', {
                'taskId': taskId
            },
            function (res) {
                console.log(res.bizData)
                if (res.rtnCode == "0000000") {
                    var teachDate = res.bizData.teachDate;
                    var teachTime = res.bizData.teachTime;
                    if (!teachDate && !teachTime) {
                        that.state = false;
                    } else {
                        for (var i in teachDate.split('|')) {
                            $('.week-list input[data="' + teachDate.split('|')[i] + '"]').attr('checked', 'checked');
                        }
                        $('#morning-list option[value="' + teachTime.split("")[0] + '"]').attr('selected', 'selected');
                        $('#afternoon-list option[value="' + teachTime.split("")[1] + '"]').attr('selected', 'selected');
                        $('#evening-list option[value="' + teachTime.split("")[2] + '"]').attr('selected', 'selected');
                        that.state = true;
                    }
                }

            }, function (res) {
                layer.msg(res.msg);
            });
    },
    // 保存教学时间
    saveTeachTime: function (taskId, teachDate, teachTime) {
        var that = this;
        Common.ajaxFun('/teachTime/saveTeachTime.do', 'POST', {
                'taskId': taskId,
                'teachDate': teachDate,
                'teachTime': teachTime
            },
            function (res) {
                if (res.rtnCode == "0000000" && res.bizData == true) {
                    that.state = true;
                    layer.msg('保存成功!');
                }
            }, function (res) {
                layer.msg(res.msg);
            });
    }
};

var TeachDateIns = new TeachDate();

$(function () {
    // 保存确认
    $('#btn-save-base').on('click', function () {
        var checkboxLen = $('.week-list input:checked').length;
        var weekV = $('.week-list input:checked');
        if (checkboxLen == 0) {
            layer.tips('每周上课天数至少选择一项', $(this));
            return false;
        }
        var teachDate = [];
        $.each(weekV, function (i, v) {
            teachDate.push($(this).attr('data'));
        });
        console.log(teachDate)
        var morningNum = $('#morning-list').val();
        var afternoonNum = $('#afternoon-list').val();
        var eveningNum = $('#evening-list').val();
        var teachTime = morningNum + afternoonNum + eveningNum;
        console.log(TeachDateIns.state)
        if(TeachDateIns.state==true){
            layer.confirm('如果更改基本信息设置，已设置的排课条件有可能会被清除，请谨慎操作！', {
                btn: ['确定', '关闭'] //按钮
            }, function () {
                TeachDateIns.saveTeachTime(taskId, teachDate.join('|'), teachTime);
            }, function () {

            });
        }else{
            TeachDateIns.saveTeachTime(taskId, teachDate.join('|'), teachTime);
        }
    });


    $(document).on('click', '#rule-settings', function () {
        var flag = Common.checkInfoIsPerfect(taskId);
        if (flag) {
            window.location.href = '/course-scheduling-step2';
        }
    });
    $(document).on('click', '#auto-assign-course', function () {
        var flag = Common.checkInfoIsPerfect(taskId);
        if (flag) {
            window.location.href = '/course-scheduling-step3';
        }
    });
});