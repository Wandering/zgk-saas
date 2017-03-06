/**
 * 排选课-教室信息
 */
var GLOBAL_CONSTANT = {
    'taskId' : Common.cookie.getCookie('taskId'),
    'scheduleName' : Common.cookie.getCookie('scheduleName')
};

var ClassInfo = {
    init:function(){
        this.getClassInfo();
    },
    //拉取教室信息
    getClassInfo:function(){
        Common.ajaxFun('/scheduleTask/getConfigRooms.do', 'GET', {
            'taskId':GLOBAL_CONSTANT.taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                console.info(res)
            }
        });
    },
    saveClassInfo:function(){
        Common.ajaxFun('/scheduleTask/getConfigRooms.do', 'GET', {
            'taskId':GLOBAL_CONSTANT.taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                console.info(res)
            }
        });
    }
}
ClassInfo.init();

