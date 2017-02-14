var tnId = Common.cookie.getCookie('tnId');


function CourseManagement() {
    this.init();
}

CourseManagement.prototype = {
    constructor: CourseManagement,
    init: function () {
        this.getDefaultInfo();
        this.getBaseInfo();
        this.getCourse();
        this.addCourse();
        this.updateCourse();
        this.delCourse();
    },
    // 语文、数学、英语、物理、化学、生物、政治、历史、地理  默认课程不可编辑\不可修改\不可删除
    // 通用技术 不可编辑\不可修改\不可删除
    getDefaultInfo:function(){
        var that = this;
        Common.ajaxFun('/course/get/baseInfo.do', 'GET', {}, function (res) {
            console.log(res);
            if (res.rtnCode == "0000000") {

            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 查询课程基础信息
    getBaseInfo:function(){

    },
    // 获取租户下的课程设置信息
    getCourse:function(){

    },
    // 新增课程管理信息
    addCourse:function(){

    },
    // 修改课程管理信息
    updateCourse:function(){

    },
    // 删除课程管理信息
    delCourse:function(){

    }
};

var CourseManagementIns = new CourseManagement();

$(function () {



});



