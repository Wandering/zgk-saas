/*
 * @module:学生管理模块
 * @author:pdeng
 * @time:2016-11-9
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44436387
 *
 * */



//添加学生
var StudentAdd = {
    init: function () {
        this.tnId = Common.cookie.getCookie('tnId');
        this.addEvent();
        this.fetchGrade();
        this.fetchEntranceYear();
        this.fetchBelongClass();
    },
    addEvent: function () {
        $(document).on('click', '#student-add', function () {
            layer.open({
                type: 1,
                title: '添加学生',
                offset: 'auto',
                area: ['425px', '530px'],
                content: $('#student-add-layer').html()
            })
        })
    },
    fetchGrade: function () {
        Common.ajaxFun('/config/grade/get/' + this.tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                StudentAdd.renderGrade(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    fetchEntranceYear:function(){
        Common.ajaxFun('/config/get/school/year.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                StudentAdd.renderEntranceYear(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    fetchBelongClass:function(){
        Common.ajaxFun('/manage/class/' + this.tnId +'/getTenantCustomData.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                StudentAdd.renderBelongClass(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderGrade:function(res){
        var tpl = '<option value="00">选择年级</option>';
        $.each(res.bizData.grades, function (i, k) {
            tpl += '<option>' + k.grade + '</option>';
        });
        $('#select-grade').html(tpl)
    },
    renderEntranceYear:function(res){
        var tpl = '<option value="00">入学年份</option>';
        for(var i in res.bizData.result){
            tpl += '<option>' + res.bizData.result[i] + '</option>';
        }
        $('#select-year').html(tpl)
    },
    renderBelongClass:function(res){
        console.info(res);
    }
}

//修改
var StudentModify = {}

//删除
var StudentRemove = {}

//模板上传
var StudentTemplateDown = {}

//模板上传
var StudentUpload = {}

//学生设置
var StudentSetting = {}

/*
 * 学生管理
 * */
var StudentManage = {
    init: function () {
        StudentAdd.init();
        //StudentModify();
        //StudentRemove();
        //StudentTemplateDown();
        //StudentUpload();
        //StudentSetting();
    }
}

StudentManage.init();







