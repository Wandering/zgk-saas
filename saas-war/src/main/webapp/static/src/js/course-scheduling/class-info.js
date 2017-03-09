/**
 * 排选课-教室信息
 */
var GLOBAL_CONSTANT = {
    'taskId': Common.cookie.getCookie('taskId'),
    'gradeName': Common.cookie.getCookie('gradeName')
};


var ClassInfo = {
    init: function () {
        $('.grade-now').html(GLOBAL_CONSTANT.gradeName);
        this.getClassInfo();
        this.event();
    },
    //拉取教室信息
    getClassInfo: function () {
        Common.ajaxFun('/scheduleTask/getConfigRooms.do', 'GET', {
            'taskId': GLOBAL_CONSTANT.taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var d = res.bizData;
                if (typeof d.msg == 'undefined') {
                    $('#class-number').html(d.admNumber)
                    $('#optional-class').val(d.sum).attr('classRoomId', d.classRoomId);
                }
            }
        });
    },
    //保存教学
    saveClassInfo: function () {
        var $optionalClass = $('#optional-class');
        if (parseInt($optionalClass.val()) < $('#class-number').text()) {
            layer.msg('可排教室数量输入有误');
            return false;
        }
        Common.ajaxFun('/scheduleTask/updateClassRoom.do', 'post', {
            'classRoomId': $optionalClass.attr('classRoomId'),
            'scheduleNumber': $optionalClass.val()
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if (res.bizData.msg == "success") {
                    layer.msg('教室信息保存成功');
                }
            }
        });
    },
    event: function () {
        $('#save-class').click(function () {
            ClassInfo.saveClassInfo();
        })
    }
}
ClassInfo.init();

