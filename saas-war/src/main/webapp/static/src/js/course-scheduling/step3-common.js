/**
 * 地址Hash处理
 */
var taskId = Common.cookie.getCookie('taskId');
var scheduleName = Common.cookie.getCookie('scheduleName');
$('.scheduleName').text(scheduleName);
var HashHandle = {
    init: function () {
        this.hashArr = ['#class', '#teacher', '#student', '#all'];
        this.addEvent();
        this.hashOperate();
        this.initStatus();
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
        Common.ajaxFun('/scheduleTask/queryScheduleTaskStatus', 'GET', {
            'taskId': taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                switch (parseInt(data)) {
                    case 1:
                            $('.btn-one-key').removeClass('dh');
                        break;
                    case 2:

                        break;
                    case 3:
                        $('.btn-one-key').addClass('dh');
                        $('.info-modify').removeClass('dh');
                        break;
                    default:
                        break;
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    updateStatus: function () {
        Common.ajaxFun('/scheduleTask/updateScheduleTaskStatus', 'GET', {
            'taskId': taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                $('#role-scheduling-tab, #step3-child-class').removeClass('dh');
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    }
};
HashHandle.init();


/**
 * 一键生成课表
 * lockSubject 0,1
 */
$('.btn-one-key').click(function(){
    HashHandle.updateStatus();
    //$(this).hide();
    //$('#role-scheduling-tab,#step3-child-class').show();
    //Common.cookie.setCookie('lockSubject',1);
});
//if(Common.cookie.getCookie('lockSubject') == 1){
//    $('.btn-one-key').hide();
//    $('#role-scheduling-tab, #step3-child-class').removeClass('dh');
//}
$(document).on('click', '.retry-scheduling, .look-origin-schedule', function () {
    $('#role-scheduling-tab, #step3-child-class').removeClass('dh');
});

//$(function () {
//
//    var flag = Common.checkInfoIsPerfect(taskId);
//    if (!flag) {
//        window.location.href = '/course-scheduling-step1';
//    }
//});
