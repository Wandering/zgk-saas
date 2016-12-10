/**
 * @wiki: http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44458344
 * 全局常量
 * @type {{tnId: *, typ: string}}
 */
var GLOBAL_CONSTANT = {
    tnId: Common.cookie.getCookie('tnId'), //租户ID
    taskId: Common.cookie.getCookie('taskId'), //租户ID
    grade: function(){
        var gradeId = Common.cookie.getCookie('gradeName');
        switch (gradeId){
            case '高一年级':
                gradeId = '1';
                break;
            case '高二年级':
                gradeId = '2';
                break;
            case '高三年级':
                gradeId = '2';
                break;
        }
        return gradeId;
    },
}

/**
 * 排课任务 -> 排课规则设置 ->合班
 * @type {{init: MixedClass.init, fetchAndRenderSubject: MixedClass.fetchAndRenderSubject, fetchBelongClass: MixedClass.fetchBelongClass}}
 */
var MixedClass = {
    init: function () {
        this.fetchAndRenderSubject();
        this.fetchBelongClass(MixedClass.subjectData[0].courseName,MixedClass.subjectData[0].courseId);
        this.addEvent();
    },
    fetchAndRenderSubject: function () {
        Common.ajaxFun('/scheduleTask/queryCourseInfoByTaskId.do', 'get', {
            'taskId': GLOBAL_CONSTANT.taskId
        }, function (res) {
            if (res.rtnCode == '0000000') {
                MixedClass.subjectData = res.bizData;
                var tpl = Handlebars.compile($('#course-select-tpl').html());
                $('#course-select').html(tpl(res.bizData));
            } else {
                layer.msg(res.msg);
            }
        },function(){},'true');
    },
    fetchBelongClass: function (courseName,courseId) {
        var paramsData = {
            'tnId': GLOBAL_CONSTANT.tnId,
            'taskId': GLOBAL_CONSTANT.taskId,
            'grade':GLOBAL_CONSTANT.grade,
            'courseName':courseName,
            'courseId':courseId,
        }
        Common.ajaxFun('/mergeClassController/getClassDtoByCourse.do', 'GET', paramsData, function (res) {
            if (res.rtnCode == "0000000") {
                // isMerge 1:已合过，置灰
                if(res.bizData.classBaseDtoList.length === 0){
                    $('#choose-class-list').text('暂无匹配班级');
                    return false;
                }
                var tpl = Handlebars.compile($('#choose-class-list-tpl').html());
                $('#choose-class-list').html(tpl(res.bizData.classBaseDtoList));
            }
        });
    },
    addEvent: function () {
        var that = this;
        $('.mixed-class-btn').click(function () {
            that.submitMixedClass();
        });
        $(document).on('change','#course-select',function(){
            var cName = $('#course-select option:selected').text(),
                cId = $(this).val();
            that.fetchBelongClass(cName,cId);
        });
        $(document).on('click','#mixed-list .del-class',function(){
            var cId = $(this).attr('classIds');
            that.delMixedClass(cId);
        });
    },
    submitMixedClass: function () {
        var that = this,classIds='';
        $('input[name="merge-class"]:checked').each(function(){
            classIds += $(this).val()+','
        });
        Common.ajaxFun('/mergeClassController/addMergeInfo.do', 'get', {
            'tnId': GLOBAL_CONSTANT.tnId,
            'taskId': GLOBAL_CONSTANT.taskId,
            'courseId': $('#course-select').val(),
            'classIds': classIds.substr(0,classIds.length-1),//班级Id，多个班级有英文逗号分隔
        }, function (res) {
            if (res.rtnCode === '0000000') {
                layer.msg('合班成功')
                that.fetchResult();
            } else {
                layer.msg(res.msg);
            }
        });
    },
    fetchResult: function () {
        var that = this;
        Common.ajaxFun('/mergeClassController/getMergeInfo.do', 'get', {
            'tnId': GLOBAL_CONSTANT.tnId,
            'taskId': GLOBAL_CONSTANT.taskId,
            'grade': GLOBAL_CONSTANT.grade,
        }, function (res) {
            if (res.rtnCode === '0000000') {
                that.renderResult(res.bizData)
            } else {
                layer.msg(res.msg);
            }
        });
    },
    renderResult:function(dataJson){
        var tpl = Handlebars.compile($('#mixed-list-tpl').html());
        $('#mixed-list').html(tpl(dataJson.mergeClassInfoList));
        $('.mixed-class-tips').removeClass('dh');
    },
    delMixedClass:function(cId){
        Common.ajaxFun('/mergeClassController/deleteMergeInfo.do', 'get', {
            'id': cId
        }, function (res) {
            if(res.rtnCode === '0000000'){
                layer.msg('删除成功');
                that.fetchResult();
            }else{
                layer.msg(res.msg);
            }
        });
    },
}
MixedClass.init();